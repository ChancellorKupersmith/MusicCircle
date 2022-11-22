package com.service.music_circle_backend.entities.playlist;

import com.service.music_circle_backend.entities.ShareContent;
import com.service.music_circle_backend.entities.audio_file.AudioFile;
import com.service.music_circle_backend.entities.user.User;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Album implements ShareContent {
    //Variables
    //@ApiModelProperty(notes = "Id of album", name = "id", required = true, value = "test id")
    @ApiModelProperty(notes = "unique id of album")
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    //@ApiModelProperty(notes = "Name of album", name = "albumName", required = true, value = "test albumName")
    @ApiModelProperty(notes = "Name of album")
    private String albumName;
    private Long artistID;
    @ApiModelProperty(notes = "Boolean value if album contains only one song")
    private boolean single;
    private String albumPicFilename;
    @OneToMany(fetch = FetchType.LAZY)
    private List<AudioFile> songs;

    //Constructors
    protected Album(){}
        public Album(String albumName, User artist, List<AudioFile> songs){
        this.albumName = albumName;
        this.artistID = artist.getId();
        this.songs = songs;
        this.single = false;
    }
    public Album(String albumName, User artist){
        this.albumName = albumName;
        this.artistID = artist.getId();
        this.single = false;
    }
    public Album(String albumName, User artist, AudioFile song){
        this.albumName = albumName;
        this.artistID = artist.getId();
        this.songs = new ArrayList<AudioFile>();
        this.songs.add(song);
        this.single = true;
    }

    //Getters
    public String getAlbumName() { return albumName; }
    public Long getArtist() { return artistID; }
    public Long getId() { return id; }
    public List<AudioFile> getTracklist() { return songs; }
    public boolean isSingle() { return (single && songs.size() < 2); }

    public String getAlbumPicFilename() {
        return albumPicFilename;
    }

    //Setters
    public void setAlbumName(String albumName) { this.albumName = albumName; }
    public void setArtist(User artist) { this.artistID = artist.getId(); }
    public void setId(Long id) { this.id = id; }
    public void setTracklist(List<AudioFile> songs) { this.songs = songs; }
    public void setSingle(boolean single) { this.single = single; }

    public void setAlbumPicFilename(String albumPicFilename) {
        this.albumPicFilename = albumPicFilename;
    }

    @Override
    public entityType getType() {
        return entityType.album;
    }
}

