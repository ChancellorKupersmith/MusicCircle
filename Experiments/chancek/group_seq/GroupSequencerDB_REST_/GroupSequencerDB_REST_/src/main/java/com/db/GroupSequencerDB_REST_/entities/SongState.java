package com.db.GroupSequencerDB_REST_.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SongState {
    //Variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(fetch = FetchType.LAZY)
    private List<AudioFile> audioFiles;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments;
    private int readyToPost;
    private String users;
    //Constructors
    protected SongState(){}
    public SongState(String users, List<AudioFile> audioFiles, List<Comment> comments, int readyToPost){
        this.users = users;
        this.audioFiles = audioFiles;
        this.comments = comments;
        this.readyToPost = readyToPost;
    }
    //Logging
    @Override
    public String toString(){
        String audioFilesString = "", commentsString = "";
        for(AudioFile audioFile : audioFiles){
            audioFilesString += (audioFile.toString() +"\n");
        }
        for(Comment comment : comments){
            commentsString += (comment.toString() +"\n");
        }
        return String.format("SongState[ID: %d, READY_TO_POST: %d\n" +
                        "          USERS:\n"+
                        "               "+users+"\n"+
                        "          AUDIOFILES:\n"+
                        "                    "+audioFilesString+
                        "          COMMENTS:\n"+
                        " s                 "+commentsString+"]",
                id, readyToPost);

    }
    //Getters
    public Long getId() { return id; }
    public List<AudioFile> getAudioFiles() { return audioFiles; }
    public int getReadyToPost() { return readyToPost; }
    public List<Comment> getComments() { return comments; }
    public String getUsers(){ return users; }
}
