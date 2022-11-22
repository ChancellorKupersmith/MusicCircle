package com.db.GroupSequencerDB_REST_.controllers;

import com.db.GroupSequencerDB_REST_.entities.AudioFile;
import com.db.GroupSequencerDB_REST_.exceptions.AudioFileNotFoundException;
import com.db.GroupSequencerDB_REST_.repositories.AudioFileRepository;
import com.db.GroupSequencerDB_REST_.services.AudioFileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
public class AudioFileController {
    private final AudioFileRepository audioFileRepository;
    AudioFileController(AudioFileRepository audioFileRepository){
        this.audioFileRepository = audioFileRepository;
    }
//    private AudioFileUploadService audioFileUploadService;

//    @Autowired
//    public AudioFileController(AudioFileRepository audioFileRepository){
//        this.audioFileRepository = audioFileRepository;
//    }

    //Aggregate root
    //tag::gtet-aggregate-root[]
    @GetMapping("/audio_files")
    List<AudioFile> all(){
        return audioFileRepository.findAll();
    }
    //end::get-aggregate

    @PostMapping("/audio_files")
    AudioFile newAudioFile(@RequestBody AudioFile newAudioFile){
        return audioFileRepository.save(newAudioFile);
    }

    @GetMapping("/audio_files/{id}")
    AudioFile one(@PathVariable Long id){
        return audioFileRepository.findById(id).orElseThrow(() -> new AudioFileNotFoundException(id));

    }

    @PutMapping("/audio_files/{id}")
    AudioFile replaceAudioFile(@RequestBody AudioFile newAudioFile, @PathVariable Long id){
        return audioFileRepository.findById(id).map(audioFile -> {
            audioFile.setFilename(newAudioFile.getFilename());
            audioFile.setBarLocation(newAudioFile.getBarLocation());
            return audioFileRepository.save(audioFile);
        }).orElseGet(() -> {
           newAudioFile.setId(id);
           return audioFileRepository.save(newAudioFile);
        });
    }

    @PutMapping("/audio_files/+file/{id}/{mp3File}")
    AudioFile uploadFile(@RequestBody AudioFile newAudioFile, @PathVariable String id, @PathVariable File mp3File) throws IOException {
        byte[] contents = Files.readAllBytes(mp3File.toPath());
        if(!(id.equals("?"))){
            Long identifier = Long.parseLong(id);
            return audioFileRepository.findById(identifier).map(audioFile -> {
                try {
                    audioFile.setContents(mp3File);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return audioFileRepository.save(audioFile);
            }).orElseGet(() ->

            {
                try {
                    newAudioFile.setContents(mp3File);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return audioFileRepository.save(newAudioFile);
            }

            );
        }
        else {
            try {
                newAudioFile.setContents(mp3File);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return audioFileRepository.save(newAudioFile);
        }

    }

    @DeleteMapping("/audio_files/{id}")
    void deleteAudioFile(@PathVariable Long id){
        audioFileRepository.deleteById(id);
    }



//    @Autowired
//    public void AudioFileUploadService(AudioFileUploadService audioFileUploadService){
//        this.audioFileUploadService = audioFileUploadService;
//
//    }
//    @PostMapping//(value = "/audio_files/+{file_path}")
//    public void uploadAudioFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
//        audioFileUploadService.uploadAudioFile(multipartFile);
//    }
//    @PostMapping(value = "/audio_files/+{file_path}")
//    Long uploadAudioFile(@RequestParam("file") MultipartFile multipartFile) throws Exception{
//        String filename = multipartFile.getOriginalFilename();
//        byte[] contents = multipartFile.getBytes();
//        AudioFile dbAudioFile = new AudioFile(filename, contents);
//        return audioFileRepository.save(dbAudioFile).getId();
//    }

//    @GetMapping(value = "/audio_files/{audio_filesId}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
//    Resource downloadAudioFile(@PathVariable Long audio_filesId){
//        byte[] contents = audioFileRepository.findById(audio_filesId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getContents();
//        return new ByteArrayResource(contents);
//    }
}
