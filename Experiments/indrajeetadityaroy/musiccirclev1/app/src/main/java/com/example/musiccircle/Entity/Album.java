package com.example.musiccircle.Entity;

import com.example.musiccircle.entities;

import java.util.ArrayList;
import java.util.List;

public class Album implements entities {
    //Variables
    private Long id;
    private String albumName;
    private String artist;
    private boolean single;
    private List<AudioFileInfo> songs;

    //Constructors
    public Album(){}
    public Album(String albumName, String artist, List<AudioFileInfo> songs){
        this.albumName = albumName;
        this.artist = artist;
        this.songs = songs;
        this.single = false;
    }
    public Album(String albumName, String artist, AudioFileInfo song){
        this.albumName = albumName;
        this.artist = artist;
        this.songs = new ArrayList<AudioFileInfo>();
        this.songs.add(song);
        this.single = true;
    }


    //Getters
    public String getAlbumName() { return albumName; }
    public String getArtist() { return artist; }
    public Long getId() { return id; }
    public List<AudioFileInfo> getTracklist() { return songs; }
    public boolean isSingle() { return single; }

    @Override
    public int getType() {
        return 3;
    }

    //Setters
    public void setAlbumName(String albumName) { this.albumName = albumName; }
    public void setArtist(String artist) { this.artist = artist; }
    public void setId(Long id) { this.id = id; }
    public void setTracklist(List<AudioFileInfo> songs) { this.songs = songs; }
    public void setSingle(boolean single) { this.single = single; }
}
