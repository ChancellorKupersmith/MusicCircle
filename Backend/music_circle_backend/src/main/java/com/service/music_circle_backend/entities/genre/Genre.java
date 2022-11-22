package com.service.music_circle_backend.entities.genre;

import com.service.music_circle_backend.entities.audio_file.AudioFile;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Genre")
public class Genre{
    //Variables
    //@ApiModelProperty(notes = "Id of genre", name = "id", required = true, value = "test id")
    @ApiModelProperty(notes = "unique id of genre")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //@ApiModelProperty(notes = "Name of genre", name = "name", required = true, value = "test name")
    @ApiModelProperty(notes = "Name of genre")
    private String name;

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
        if(this.name.equalsIgnoreCase(genre.name)){
            return true;
        }
        else{
            return false;
        }
    }
}

