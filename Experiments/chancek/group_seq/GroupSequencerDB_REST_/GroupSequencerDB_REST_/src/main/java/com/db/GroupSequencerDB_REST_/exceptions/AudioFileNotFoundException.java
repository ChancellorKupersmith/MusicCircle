package com.db.GroupSequencerDB_REST_.exceptions;

public class AudioFileNotFoundException extends RuntimeException{
    public AudioFileNotFoundException(Long id){
        super("Could not find AudioFile: " + id);
    }

}
