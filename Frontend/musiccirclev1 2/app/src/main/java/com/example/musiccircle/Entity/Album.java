package com.example.musiccircle.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Album implements entities, Serializable {
    //Variables
    private Long id;
    private String albumName;
    private String artist;
    private boolean single;
    private List<AudioFile> songs;
    private String albumPicFilename;
    private byte[] image;

    //Constructors
    public Album(){}
    public Album(String albumName, String artist, List<AudioFile> songs){
        this.albumName = albumName;
        this.artist = artist;
        this.songs = songs;
        this.single = false;
    }
    public Album(String albumName, String artist, AudioFile song){
        this.albumName = albumName;
        this.artist = artist;
        this.songs = new ArrayList<AudioFile>();
        this.songs.add(song);
        this.single = true;
    }

    //Getters
    public String getAlbumName() { return albumName; }
    public String getArtist() { return artist; }
    public Long getId() { return id; }
    public List<AudioFile> getTracklist() { return songs; }
    public boolean isSingle() { return single; }
    public String getAlbumPicFilename() {
        return albumPicFilename;
    }

    public byte[] getImage() {
        return image;
    }

    @Override
    public int getType() {
        return 3;
    }

    //Setters


    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setAlbumPicFilename(String albumPicFilename) {
        this.albumPicFilename = albumPicFilename;
    }

    public void setAlbumName(String albumName) { this.albumName = albumName; }
    public void setArtist(String artist) { this.artist = artist; }
    public void setId(Long id) { this.id = id; }
    public void setTracklist(List<AudioFile> songs) { this.songs = songs; }
    public void setSingle(boolean single) { this.single = single; }

}
