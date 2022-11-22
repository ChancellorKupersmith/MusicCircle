package com.service.microservices.controllers.audio_file;

import com.service.microservices.entities.audio_file.AudioFile;
import com.service.microservices.exceptions.audio_file.AudioFileNotFoundException;
import com.service.microservices.messages.ResponseFile;
import com.service.microservices.messages.ResponseMessage;
import com.service.microservices.repos.audio_file.AudioFileRepository;
import com.service.microservices.services.AudioFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:8080")
public class AudioFileController {
    private final AudioFileRepository audioFileRepository;

    @Autowired
    private AudioFileService audioFileService;

    AudioFileController(AudioFileRepository audioFileRepository){
        this.audioFileRepository = audioFileRepository;
    }
    //for listing all audio files
    @GetMapping("/audioFiles")
    List<AudioFile> all(){
        return audioFileRepository.findAll();
    }

    //for listing a specific audio file
    @GetMapping("/audioFiles/{id}")
    AudioFile one(@PathVariable Long id){
        return audioFileRepository.findById(id).orElseThrow(() -> new AudioFileNotFoundException(id));
    }
    @PutMapping("/audioFiles/{id}")
    AudioFile replaceAudioFile(@RequestBody AudioFile newAudioFile, @PathVariable Long id){
        return audioFileRepository.findById(id).map(audioFile -> {
            audioFile.setFilename(newAudioFile.getFilename());
            return audioFileRepository.save(audioFile);
        }).orElseGet(() ->{
            newAudioFile.setId(id);
            return audioFileRepository.save(newAudioFile);
        });
    }

    //for uploading an audio file
    @PostMapping("/audioFilesUp")
    public ResponseEntity uplodadAudioFile(@RequestParam("file") MultipartFile file){
        String message = "";
        try {
            audioFileService.store(file);
            message = "Uploaded Successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload file: " + file.getOriginalFilename();
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/audioFilesDown")
    public ResponseEntity<List<ResponseFile>> getListFiles(){
        List<ResponseFile> files = audioFileService.getAllAudioFiles().map(audioFile ->{
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/audioFiles/").path(String.valueOf(audioFile.getId())).toUriString();
            return new ResponseFile(audioFile.getFilename(), fileDownloadUri, "mp3", audioFile.getContents().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/audioFilesDown/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id){
        AudioFile audioFile = audioFileService.getAudioFile(id);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + audioFile.getFilename() + "\"").body(audioFile.getContents());
    }


    @DeleteMapping("/audioFiles/{id}")
    void deleteAudioFile(@PathVariable Long id) {
        audioFileRepository.deleteById(id);
    }

}
