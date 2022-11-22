package com.example.musiccircle.Entity;

import java.util.List;
import java.util.Set;

public class Genre{
    //Variables
    private Long id;
    private String name;
    private List<AudioFile> audioFiles;

    //Constructors
    protected Genre(){}
    public Genre(String name){
        this.name = name;
    }

    //Getters
    public Long getId() { return id; }
    public String getName() { return name; }

    //Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }

    //Methods
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Genre)){
            return false;
        }
        Genre genre = (Genre) o;
        if(this.name.toLowerCase() == genre.name.toLowerCase()){
            return true;
        }
        else{
            return false;
        }
    }
}
