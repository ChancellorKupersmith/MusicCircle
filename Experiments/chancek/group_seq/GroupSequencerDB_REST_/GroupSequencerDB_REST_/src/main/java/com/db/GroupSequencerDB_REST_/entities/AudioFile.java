package com.db.GroupSequencerDB_REST_.entities;

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
    private String filename;
    private double barLocation;
    @Lob
    private byte[] contents;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="songState_id")
//    private SongState songState;

    //Constructors
    protected AudioFile(){}
    public AudioFile(String filename){
        this.filename = filename;
        barLocation = 0;
    }
    public AudioFile(String filename, String filepath) throws IOException {
        this.filename = filename;
        barLocation = 0;
        byte[] contents = Files.readAllBytes(Paths.get(filepath));
        this.contents = contents;
    }
    public AudioFile(String filename, File mp3, double barLocation) throws IOException {
        this.filename = filename;
        this.barLocation = barLocation;
        byte[] contents = Files.readAllBytes(mp3.toPath());
        this.contents = contents;
//        this.songState = songState;
    }
    //Logging
    @Override
    public String toString(){
        return String.format(
                "AudioFile[ID: %d, FILENAME: '%s', BAR_LOCATION: %f]",
                id, filename, barLocation
        );
    }
    //Getters
    public Long getId(){ return id; }
    public String getFilename(){ return filename; }
    public double getBarLocation(){ return barLocation; }
    public byte[] getContents() { return this.contents; }

    //Setters
    public void setBarLocation(double barLocation) { this.barLocation = barLocation; }
    public void setFilename(String filename) { this.filename = filename; }
    public void setId(Long id){ this.id = id; }
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
        return Objects.equals(this.id, audioFile.id) && Objects.equals(this.barLocation, audioFile.barLocation);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.filename, this.barLocation, this.toString());
    }


}
