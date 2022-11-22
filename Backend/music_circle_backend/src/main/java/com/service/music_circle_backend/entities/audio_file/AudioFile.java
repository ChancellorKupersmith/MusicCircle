package com.service.music_circle_backend.entities.audio_file;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.service.music_circle_backend.entities.ShareContent;
import com.service.music_circle_backend.entities.genre.Genre;
import com.service.music_circle_backend.entities.playlist.Album;
import com.service.music_circle_backend.entities.user.User;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "audio_file")
public class AudioFile implements ShareContent {
    //Variables
    @ApiModelProperty(notes = "unique id of audio file")
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @ApiModelProperty(notes = "File name of mp3 file")
    private String filename;
    @ApiModelProperty(notes = "Id of user who uploaded song")
    private Long artistID;

    private Long chatID;

    public Long getChatID() {
        return chatID;
    }

    public void setChatID(Long chatID) {
        this.chatID = chatID;
    }

    private String artistName;
    @ApiModelProperty(notes = "Id of album song belongs to")
    private Long albumID;
    @ApiModelProperty(notes = "Name of song")
    private String songName;
    @ApiModelProperty(notes = "Genre of song")
    @ManyToOne(fetch = FetchType.LAZY)
    private Genre genre;
    @ApiModelProperty(notes = "Number of likes for song")
    private int likes;
    @ApiModelProperty(notes = "Number of plays for song")
    private int plays;
    @ApiModelProperty(notes = "Boolean if song is can be listened to by public")
    private boolean isPublic;
    @Lob
    @ApiModelProperty(notes = "Artwork for song")
    private String songPicFilename;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(notes = "Date and time song was uploaded")
    private LocalDateTime uploadDate;



    //Constructors
    protected AudioFile(){}
    public AudioFile(String filename){
        this.filename = filename;
        uploadDate = LocalDateTime.now();
    }
    public AudioFile(String filename, String songName, User artist) throws IOException {
        this.filename = filename;
        this.songName = songName;
        this.artistID = artist.getId();
        uploadDate = LocalDateTime.now();
        isPublic = true;
        likes = 0;
        plays = 0;
    }
    public AudioFile(String filename, String songName, User artist, String songPicFilename) throws IOException {
        this.filename = filename;
        this.songName = songName;
        this.artistID = artist.getId();
        this.songPicFilename = songPicFilename;
        uploadDate = LocalDateTime.now();
        isPublic = true;
        likes = 0;
        plays = 0;
    }
    public AudioFile(String filename, String songName, User artist, Album album) throws IOException {
        this.filename = filename;
        this.songName = songName;
        this.artistID = artist.getId();
        this.albumID = album.getId();
        uploadDate = LocalDateTime.now();
        isPublic = true;
        likes = 0;
        plays = 0;
    }
    public AudioFile(String filename, String songName, User artist, Genre genre) throws IOException {
        this.filename = filename;
        this.songName = songName;
        this.artistID = artist.getId();
        this.genre = genre;
        uploadDate = LocalDateTime.now();
        isPublic = true;
        likes = 0;
        plays = 0;
    }
    public AudioFile(String filename, String songName, User artist, Genre genre, String songPicFilename) throws IOException {
        this.filename = filename;
        this.songName = songName;
        this.artistID = artist.getId();
        this.genre = genre;
        this.songPicFilename = songPicFilename;
        uploadDate = LocalDateTime.now();
        isPublic = true;
        likes = 0;
        plays = 0;
    }
    public AudioFile(String filename, String songName, User artist, Album album, Genre genre) throws IOException {
        this.filename = filename;
        this.songName = songName;
        this.artistID = artist.getId();
        this.albumID = album.getId();
        this.genre = genre;
        uploadDate = LocalDateTime.now();
        isPublic = true;
        likes = 0;
        plays = 0;
    }

    //Logging
    @Override
    public String toString(){
        return String.format(
                "AudioFile[ID: %d, FILENAME: '%s']",
                id, filename
        );
    }

    //Getters
    public Long getId(){ return id; }
    public String getFilename(){ return filename; }
    public Long getArtist() { return artistID; }
    public Long getAlbum() { return albumID; }
    public String getSongName() { return songName; }
    public Genre getGenre() { return genre; }
    public int getLikes() { return likes; }
    public int getPlays() { return plays; }
    public boolean isPublic() { return isPublic; }
    public String getSongPicFilename(){ return songPicFilename; }
    public LocalDateTime getUploadDate() { return uploadDate; }
    public String getArtistName() {
        return artistName;
    }
    public int getTopCounter() {
        return plays + likes;
    }

    //Setters
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    public void setFilename(String filename) { this.filename = filename; }
    public void setId(Long id){ this.id = id; }
    public void setSongPicFilename(String songPicFilename) { this.songPicFilename = songPicFilename; }
    public void setAlbum(Album album) { this.albumID = album.getId(); }
    public void setSongName(String songName) { this.songName = songName; }
    public void setGenre(Genre genre) { this.genre = genre; }
    public void setLikes(int likes) { this.likes = likes; }
    public void setPlays(int plays) { this.plays = plays; }
    public void setPublic(boolean isPublic) { this.isPublic = isPublic; }
    public void addLikes(){ likes++; }
    public void decreaseLikes(){ likes--; }
    public void addPlays(){ plays++; }
    public void decreasePlays(){ plays--; }
    public void setUploadDate(LocalDateTime uploadDate) { this.uploadDate = uploadDate; }

    //Methods
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof AudioFile)){
            return false;
        }
        AudioFile audioFile = (AudioFile) o;
        return Objects.equals(this.id, audioFile.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.filename, this.toString());
    }

    @Override
    public entityType getType() {
        return entityType.audio_file;
    }
}
