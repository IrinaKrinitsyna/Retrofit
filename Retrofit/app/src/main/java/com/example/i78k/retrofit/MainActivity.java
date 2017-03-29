package com.example.i78k.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    protected ArrayAdapter<String> adapter;
    protected ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter=new ArrayAdapter<String>(MainActivity.this,R.layout.post_item);
                listview=(ListView)findViewById(R.id.listview);
                listview.setAdapter(adapter);
                adapter.clear();


                EditText Text = (EditText) findViewById(R.id.edittext);
                String User = Text.getText().toString();
                getreport(User);

            }
        });
    }

    private void getreport(String User) {
    /* некоторое шаманство, которое мы не пониммаем >>>>> */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService service = retrofit.create(GitHubService.class);
        /* некоторое шаманство закончилось, мы получили объект, позволяющий делать http-запросы */

        Call<List<Repo>> repos = service.listRepos(User);
        Toast.makeText(this, "Запрос начался", Toast.LENGTH_SHORT);
        repos.enqueue(new Callback<List<Repo>>() {
                         @Override
                         public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                             if (response.isSuccessful()) {

                                 adapter=new ArrayAdapter<String>(MainActivity.this,R.layout.post_item);
                                 listview=(ListView)findViewById(R.id.listview);
                                 listview.setAdapter(adapter);

                                 try
                                 {
                                     for(int i = 0; i < 100; i++)
                                     {
                                         adapter.add(response.body().get(i).full_name +"\n" +response.body().get(i).created_at +" / "+  response.body().get(i).updated_at);
                                     }
                                 }
                                 catch (Exception e)//ArrayIndexOutOfBoundsException
                                 {}

                                 //Toast.makeText(MainActivity.this, response.body().get(1).name, Toast.LENGTH_SHORT).show();
                                 // tasks available
                             } else {
                                 Toast.makeText(MainActivity.this, "Не найдено", Toast.LENGTH_SHORT).show();
                                 // error response, no access to resource?
                             }
                         }

                         @Override
                         public void onFailure(Call<List<Repo>> call, Throwable t) {
                             // something went completely south (like no internet connection)
                             Log.d("Error", t.getMessage());
                             Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     });

       /* List<Repo> reposList;
        try {

            reposList = repos.execute().body();

        } catch (IOException e) {

        }*/
    }
}
