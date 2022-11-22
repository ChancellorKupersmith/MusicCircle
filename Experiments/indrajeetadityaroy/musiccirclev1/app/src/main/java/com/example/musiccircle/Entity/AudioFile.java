package com.example.musiccircle.Entity;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class AudioFile{
    //Variables
    private Long id;
    private byte[] contents;
    private String filename;
    private AudioFileInfo audioFileInfo;

    //Constructors
    protected AudioFile(){}
    public AudioFile(String filename){
        this.filename = filename;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public AudioFile(String filename, String filepath) throws IOException {
        this.filename = filename;
        byte[] contents = Files.readAllBytes(Paths.get(filepath));
        this.contents = contents;
    }
    public AudioFile(String filename, byte[] contents){
        this.filename= filename;
        this.contents = contents;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public AudioFile(String filename, File mp3) throws IOException {
        this.filename = filename;
        byte[] contents = Files.readAllBytes(mp3.toPath());
        this.contents = contents;
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
    public byte[] getContents() { return this.contents; }

    //Setters
    public void setFilename(String filename) { this.filename = filename; }
    public void setId(Long id){ this.id = id; }
    public void setContents(byte[] contents) { this.contents = contents; }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setContents(File mp3) throws IOException {
        byte[] contents = Files.readAllBytes(mp3.toPath());
        this.contents = contents;
    }

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

}
