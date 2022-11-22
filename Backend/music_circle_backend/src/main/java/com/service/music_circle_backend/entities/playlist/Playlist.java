package com.service.music_circle_backend.entities.playlist;

import com.service.music_circle_backend.entities.ShareContent;
import com.service.music_circle_backend.entities.audio_file.AudioFile;
import com.service.music_circle_backend.entities.audio_file.AudioFile;
import com.service.music_circle_backend.entities.user.User;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Playlist implements ShareContent {
    //Variables
    @ApiModelProperty(notes = "unique id of album")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "Name of playlist")
    private String name;

    private Long creatorID;

    @ApiModelProperty(notes = "List of people able to listen to playlist")
    @ElementCollection
    List<Long> listenerIDs;

    @ApiModelProperty(notes = "List of songs in playlist")
    @ManyToMany(fetch = FetchType.LAZY)
    private List<AudioFile> songs;

    //Constructors
    protected Playlist(){}
    public Playlist(String name, User creator){
        this.name = name;
        this.creatorID = creator.getId();
        this.songs = new ArrayList<AudioFile>();
        this.listenerIDs = new ArrayList<Long>();
        listenerIDs.add(creatorID);
    }
    public Playlist(String name, User creator, AudioFile song){
        this.name = name;
        this.creatorID = creator.getId();
        this.songs = new ArrayList<AudioFile>();
        this.songs.add(song);
        this.listenerIDs = new ArrayList<Long>();
        listenerIDs.add(creatorID);
    }
    public Playlist(String name, User creator, List<AudioFile> songs){
        this.name = name;
        this.creatorID = creator.getId();
        this.songs = songs;
        this.listenerIDs = new ArrayList<Long>();
        listenerIDs.add(creatorID);
    }

    //Getters
    public String getName(){
        return this.name;
    }
    public Long getCreator(){
        return creatorID;
    }
    public Long getId(){ return id; }
    public List<AudioFile> getSongs() { return songs; }
    public List<Long> getListeners() { return listenerIDs; }

    //Setters
    public void setName(String name) { this.name = name; }
    public void setCreator(User creator) { this.creatorID = creator.getId(); }
    public void setListeners(List<Long> listeners) { this.listenerIDs = listeners; }
    public void setId(Long id){ this.id = id; }
    public void setSongs(List<AudioFile> songs) { this.songs = songs; }

    @Override
    public String toString(){
        return "Playlist: " + name;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Playlist)){
            return false;
        }
        Playlist playlist = (Playlist) o;
        return Objects.equals(this.name, playlist.name);
    }

    @Override
    public entityType getType() {
        return entityType.playlist;
    }
}
