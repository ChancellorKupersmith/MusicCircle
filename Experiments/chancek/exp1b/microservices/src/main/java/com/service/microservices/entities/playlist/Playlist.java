package com.service.microservices.entities.playlist;

import com.service.microservices.entities.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Playlist {
    //Variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Long userId;

    @ElementCollection
    private List<Long> audioFilesList;

    //Constructors
    protected Playlist(){}
    public Playlist(String name, Long userId){
        this.name = name;
        this.userId = userId;
        this.audioFilesList = new ArrayList<Long>();
    }
    public Playlist(String name, Long userId, Long audioFileId){
        this.name = name;
        this.userId = userId;
        this.audioFilesList = new ArrayList<Long>();
        this.audioFilesList.add(audioFileId);
    }
    public Playlist(String name, Long userId, List<Long> audioFilesList){
        this.name = name;
        this.userId = userId;
        this.audioFilesList = audioFilesList;
    }

    //Getters
    public String getName(){
        return this.name;
    }
    public Long getUserId() {
        return userId;
    }
    public Long getId(){ return id; }
    public List<Long> getAudioFilesList() { return audioFilesList; }
    public boolean hasAudioFileId(Long audioFileId){ return audioFilesList.contains(audioFileId); }

    //Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public void setId(Long id){ this.id = id; }
    public void setAudioFilesList(List<Long> audioFilesList) { this.audioFilesList = audioFilesList; }
    public void addTo(Long audioFileId){ audioFilesList.add(audioFileId); }
    public void removeAudioFile(Long audioFileId){ audioFilesList.remove(audioFileId); }
}
