package com.example.musiccircle.Entity;

public class AudioFileInfo{
    //Variables
    private Long id;
    private AudioFile audioFile;
    private User artist;
    private Album album;
    private String songName;
    private Genre genre;


    //Constuctors
    protected AudioFileInfo(){}
    public AudioFileInfo(AudioFile audioFile, User artist, Album album, String songName){
        this.audioFile = audioFile;
        this.artist = artist;
        this.songName = songName;
        this.album = album;
    }
    public AudioFileInfo(AudioFile audioFile, User artist, String songName, Genre genre){
        this.audioFile = audioFile;
        this.artist = artist;
        this.songName = songName;
//        this.album = new Album(songName + " (Single)", artist, this);
        this.genre = genre;
    }
    public AudioFileInfo(AudioFile audioFile, User artist, String songName){
        this.audioFile = audioFile;
        this.artist = artist;
        this.songName = songName;
//        this.album = new Album(songName + " (Single)", artist, this);
    }

    //Logging
    @Override
    public String toString(){
        return String.format(
                "AudioFileInfo[ID: %d, artist: %d, album: %d]",
                id, artist, album
        );
    }

    //Getters
    public Long getId(){ return id; }
    public User getArtist() { return artist; }
    public Album getAlbum() { return album; }
    public String getSongName() { return songName; }
    public AudioFile getAudioFile() { return audioFile; }
    public Genre getGenre() { return genre; }

    //Setters

    public void setId(Long id) { this.id = id; }
    public void setAudioFile(AudioFile audioFile) { this.audioFile = audioFile; }
    public void setArtist(User artist) { this.artist = artist; }
    public void setAlbum(Album album) { this.album = album; }
    public void setSongName(String songName) { this.songName = songName; }
    public void setGenre(Genre genre) { this.genre = genre; }
}
