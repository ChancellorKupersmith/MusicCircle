package com.example.musiccircle.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Playlist implements entities, Serializable {
    //Variables
    private Long id;

    private String name;
    private String creator_username;
    List<User> listeners;
    public int length;
    private List<AudioFile> songs;

    //Constructors
    public Playlist(){}
    public Playlist(String name, String creator_username){
        this.name = name;
        this.creator_username = creator_username;
        this.songs = new ArrayList<AudioFile>();
    }
    public Playlist(String name, String creator_username, AudioFile song){
        this.name = name;
        this.creator_username = creator_username;
        this.songs = new ArrayList<AudioFile>();
        this.songs.add(song);
    }
    public Playlist(String name, String creator_username, List<AudioFile> songs){
        this.name = name;
        this.creator_username = creator_username;
        this.songs = songs;
    }

    //Getters
    public String getName(){
        return name;
    }
    public String getCreator_username(){
        return creator_username;
    }

    public int getLength() {
        return length;
    }

    public Long getId(){ return id; }
    public List<AudioFile> getSongs() { return songs; }
    public boolean hasSong(AudioFile song){ return songs.contains(song); }
    public List<User> getListeners() { return listeners; }

    //Setters

    public void setLength(int length) {
        this.length = length;
    }

    public void setName(String name) { this.name = name; }
    public void setCreator_username(String creator_username) { this.creator_username = creator_username; }
    public void setListeners(List<User> listeners) { this.listeners = listeners; }
    public void setId(Long id){ this.id = id; }
    public void setSongs(List<AudioFile> songs) { this.songs = songs; }
    public void addTo(AudioFile song){ songs.add(song); }
    public void removeAudioFile(AudioFile song){ songs.remove(song); }

    @Override
    public int getType() {
        return 7;
    }
}