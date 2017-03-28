package com.example.i78k.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getreport();
            }
        });
    }

    private void getreport() {
    /* некоторое шаманство, которое мы не пониммаем >>>>> */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService service = retrofit.create(GitHubService.class);
        /* некоторое шаманство закончилось, мы получили объект, позволяющий делать http-запросы */

        Call<List<Repo>> repos = service.listRepos("IrinaKrinitsyna");
        Toast.makeText(this, "Запрос начался", Toast.LENGTH_SHORT);
        repos.enqueue(new Callback<List<Repo>>() {
                         @Override
                         public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                             if (response.isSuccessful()) {
                                 Toast.makeText(MainActivity.this, response.body().get(1).name, Toast.LENGTH_SHORT).show();
                                 // tasks available
                             } else {
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
