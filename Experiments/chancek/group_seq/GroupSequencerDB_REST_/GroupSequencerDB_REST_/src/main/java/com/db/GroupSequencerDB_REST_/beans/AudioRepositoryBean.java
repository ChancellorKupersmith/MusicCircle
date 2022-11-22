package com.db.GroupSequencerDB_REST_.beans;

import com.db.GroupSequencerDB_REST_.repositories.AudioFileRepository;
import org.springframework.stereotype.Component;

@Component("audioRepository")
public class AudioRepositoryBean {
    public AudioFileRepository audioFileRepository;

    public AudioFileRepository getAudioFileRepository() {
        return audioFileRepository;
    }
}
