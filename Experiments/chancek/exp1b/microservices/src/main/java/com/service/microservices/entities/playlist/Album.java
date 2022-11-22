package com.service.microservices.entities.playlist;

import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

public class Album {
    //Variables
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String albumName;
    private Long userId;
    private boolean single;
    @ElementCollection
    private List<Long> audioFilesList;

    //Constructors
    protected Album(){}
    public Album(String albumName, Long userId, List<Long> audioFilesList){
        this.albumName = albumName;
        this.userId = userId;
        this.audioFilesList = audioFilesList;
        this.single = false;
    }
    public Album(String albumName, Long userId, Long audioFileId){
        this.albumName = albumName;
        this.userId = userId;
        this.audioFilesList = new ArrayList<Long>();
        this.audioFilesList.add(audioFileId);
        this.single = true;
    }


    //Getters
    public String getAlbumName() { return albumName; }
    public Long getUserId() { return userId; }
    public Long getId() { return id; }
    public List<Long> getAudioFilesList() { return audioFilesList; }
    public boolean isSingle() { return single; }

    //Setters
    public void setAlbumName(String albumName) { this.albumName = albumName; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setId(Long id) { this.id = id; }
    public void setAudioFilesList(List<Long> audioFilesList) { this.audioFilesList = audioFilesList; }
    public void setSingle(boolean single) { this.single = single; }
}
