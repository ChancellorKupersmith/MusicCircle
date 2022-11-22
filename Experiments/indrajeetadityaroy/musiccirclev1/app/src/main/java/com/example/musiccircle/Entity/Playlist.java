package com.example.musiccircle.Entity;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    //Variables
    private Long id;

    private String name;
    private User creator;
    List<User> listeners;
    private List<AudioFileInfo> songs;

    //Constructors
    protected Playlist(){}
    public Playlist(String name, User creator){
        this.name = name;
        this.creator = creator;
        this.songs = new ArrayList<AudioFileInfo>();
    }
    public Playlist(String name, User creator, AudioFileInfo song){
        this.name = name;
        this.creator = creator;
        this.songs = new ArrayList<AudioFileInfo>();
        this.songs.add(song);
    }
    public Playlist(String name, User creator, List<AudioFileInfo> songs){
        this.name = name;
        this.creator = creator;
        this.songs = songs;
    }

    //Getters
    public String getName(){
        return this.name;
    }
    public User getCreator(){
        return creator;
    }
    public Long getId(){ return id; }
    public List<AudioFileInfo> getSongs() { return songs; }
    public boolean hasSong(AudioFileInfo song){ return songs.contains(song); }
    public List<User> getListeners() { return listeners; }

    //Setters
    public void setName(String name) { this.name = name; }
    public void setCreator(User creator) { this.creator = creator; }
    public void setListeners(List<User> listeners) { this.listeners = listeners; }
    public void setId(Long id){ this.id = id; }
    public void setSongs(List<AudioFileInfo> songs) { this.songs = songs; }
    public void addTo(AudioFileInfo song){ songs.add(song); }
    public void removeAudioFile(AudioFileInfo song){ songs.remove(song); }
}