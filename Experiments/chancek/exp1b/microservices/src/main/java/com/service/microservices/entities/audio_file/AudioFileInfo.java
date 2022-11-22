package com.service.microservices.entities.audio_file;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class AudioFileInfo {
    //Variables
    private Long audioFileId, userId, albumId;
    private String genre, songName;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    //Constuctors
    protected AudioFileInfo(){}
    public AudioFileInfo(Long audioFileId, Long userId, Long albumId, String songName){
        this.audioFileId = audioFileId;
        this.userId = userId;
        this.songName = songName;
        this.albumId = albumId;
    }
    public AudioFileInfo(Long audioFileId, Long userId, String songName, String genre){
        this.audioFileId = audioFileId;
        this.userId = userId;
        this.songName = songName;
    }

    //Logging
    @Override
    public String toString(){
        return String.format(
                "AudioFileInfo[ID: %d, AudioFileID: %d, UserID: %d, AlbumID: %d, Genre: '%s']",
                id, audioFileId, userId, albumId, genre
        );
    }

    //Getters
    public Long getId(){ return id; }
    public Long getAudioFileId() { return audioFileId; }
    public Long getUserId() { return userId; }
    public String getGenre() { return genre; }
    public Long getAlbumId() { return albumId; }
    public String getSongName() { return songName; }

    //Setters
    public void setAudioFileId(Long audioFileId) { this.audioFileId = audioFileId; }
    public void setId(Long id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setAlbumId(Long albumId) { this.albumId = albumId; }
    public void setSongName(String songName) { this.songName = songName; }
}
