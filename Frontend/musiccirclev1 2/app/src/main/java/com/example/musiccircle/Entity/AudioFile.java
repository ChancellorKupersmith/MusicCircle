package com.example.musiccircle.Entity;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class AudioFile implements entities, Serializable {
    //Variables
    private Long id;
    private String filename;
    private User artist;
    private Album album;
    private String songName;
    private Genre genre;
    private int likes;
    private int plays;
    private boolean isPublic;
    private byte[] image;

    //Constructors
    public AudioFile(){}
    public AudioFile(Long id, String filename, User artist, String songName, int likes, int plays, boolean isPublic){
        this.id = id;
        this.filename = filename;
        this.artist = artist;
        this.songName = songName;
        this.likes = likes;
        this.plays = plays;
        this.isPublic = isPublic;
    }
    public AudioFile(Long id, String filename, User artist, String songName, int likes, int plays, boolean isPublic, Album album){
        this.id = id;
        this.filename = filename;
        this.artist = artist;
        this.songName = songName;
        this.likes = likes;
        this.plays = plays;
        this.isPublic = isPublic;
        this.album = album;
    }
    public AudioFile(Long id, String filename, User artist, String songName, int likes, int plays, boolean isPublic, Genre genre){
        this.id = id;
        this.filename = filename;
        this.artist = artist;
        this.songName = songName;
        this.likes = likes;
        this.plays = plays;
        this.isPublic = isPublic;
        this.genre = genre;
    }
    public AudioFile(Long id, String filename, User artist, String songName, int likes, int plays, boolean isPublic, Album album, Genre genre){
        this.id = id;
        this.filename = filename;
        this.artist = artist;
        this.songName = songName;
        this.likes = likes;
        this.plays = plays;
        this.isPublic = isPublic;
        this.album = album;
        this.genre = genre;
    }


    //Allows to be Parcelable
    //Constructoir for testing
    public AudioFile(Long id, String filename, String songName, int likes, int plays, boolean isPublic){
        this.id = id;
        this.filename = filename;
        this.songName = songName;
        this.likes = likes;
        this.plays = plays;
        this.isPublic = isPublic;
    }
//    public AudioFile(Parcel in) {
//        if (in.readByte() == 0) {
//            id = null;
//        } else {
//            id = in.readLong();
//        }
//        filename = in.readString();
//        songName = in.readString();
//        likes = in.readInt();
//        plays = in.readInt();
//        isPublic = in.readByte() != 0;
//    }


    //Logging
    @Override
    public String toString(){
        return String.format(
                "AudioFile[ID: %d, FILENAME: '%s' Song Name: '%s']",
                id, filename, songName
        );
    }
    //Getters
    public Long getId(){ return id; }
    public String getFilename(){ return filename; }
    public User getArtist() {
        return artist;
    }
    public Genre getGenre(){ return genre; }
    public Album getAlbum() { return album; }
    public boolean isPublic() { return isPublic; }
    public int getLikes() { return likes; }
    public int getPlays() { return plays; }
    public String getSongName() { return songName; }

    public byte[] getImage() {
        return image;
    }

    //Setters

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setFilename(String filename) { this.filename = filename; }
    public void setId(Long id){ this.id = id; }
    public void setAlbum(Album album) { this.album = album; }
    public void setArtist(User artist) { this.artist = artist; }
    public void setGenre(Genre genre) { this.genre = genre; }
    public void setLikes(int likes) { this.likes = likes; }
    public void setPlays(int plays) { this.plays = plays; }
    public void setPublic(boolean aPublic) { isPublic = aPublic; }
    public void setSongName(String songName) { this.songName = songName; }

    //Methods
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.filename, this.toString());
    }

    @Override
    public int getType() {
        return 4;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    //    private Long id;
//    //    private byte[] contents;
//    //    private String filename;
//    //    private User artist;
//    //    private Album album;
//    //    private String songName;
//    //    private Genre genre;
//    @RequiresApi(api = Build.VERSION_CODES.Q)
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeLong(id);
//        dest.writeString(filename);
//        dest.writeString(songName);
//        dest.writeInt(likes);
//        dest.writeInt(plays);
//        byte binary = (byte)(isPublic? 1:0);
//        dest.writeByte(binary);
//    }
}
