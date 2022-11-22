package com.service.microservices.entities.audio_file;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@Entity
public class AudioFile {
    //Variables
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Lob
    private byte[] contents;

    private Long userId;
    private String filename;


    //Constructors
    protected AudioFile(){}
    public AudioFile(String filename, Long userId){
        this.filename = filename;

        this.userId = userId;
    }
    public AudioFile(String filename, Long userId, String filepath) throws IOException {
        this.filename = filename;

        byte[] contents = Files.readAllBytes(Paths.get(filepath));
        this.contents = contents;
        this.userId = userId;
    }
    public AudioFile(String filename, byte[] contents){
        this.filename= filename;
        this.contents = contents;
    }
    public AudioFile(String filename, Long userId, byte[] contents){
        this.filename = filename;
        this.userId = userId;
        this.contents = contents;
    }
    public AudioFile(String filename, Long userId, File mp3) throws IOException {
        this.filename = filename;

        byte[] contents = Files.readAllBytes(mp3.toPath());
        this.contents = contents;
        this.userId = userId;
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
    public Long getUserId(){ return this.userId; }

    //Setters
    public void setFilename(String filename) { this.filename = filename; }
    public void setId(Long id){ this.id = id; }
    public void setUserId(Long id){ this.userId = id; }

    public void setContents(byte[] contents) { this.contents = contents; }

    public void setContents(File mp3) throws IOException {
        byte[] contents = Files.readAllBytes(mp3.toPath());
        this.contents = contents; }

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

}
