package com.example.i78k.retrofit.model;


import java.util.ArrayList;
import java.util.List;

public class Room {
    public String name = "3 + 4";
    public List<Timing> timings;

    public Room(String name) {
        this.name = name;
        timings =new ArrayList<Timing>();
        for (int i =0;i<10;i++)
        {
            timings.add(new Timing());
        }
    }

    public String getTimingString ()
    {
        return ""; // TODO вывести в строку список timing
    }

}
