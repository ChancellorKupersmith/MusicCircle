package com.service.microservices.services;

import com.service.microservices.entities.audio_file.AudioFile;
import com.service.microservices.repos.audio_file.AudioFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class AudioFileService {

    @Autowired
    private AudioFileRepository audioFileRepository;

    public AudioFile store(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        AudioFile audioFile = new AudioFile(filename, file.getBytes());

        return audioFileRepository.save(audioFile);
    }

    public AudioFile getAudioFile(Long id){

        return audioFileRepository.getOne(id);
    }

    public Stream<AudioFile> getAllAudioFiles(){

        return audioFileRepository.findAll().stream();
    }
}


