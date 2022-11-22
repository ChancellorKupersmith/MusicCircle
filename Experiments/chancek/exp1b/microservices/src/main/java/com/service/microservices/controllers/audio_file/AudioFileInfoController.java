package com.service.microservices.controllers.audio_file;

import com.service.microservices.entities.audio_file.AudioFile;
import com.service.microservices.entities.audio_file.AudioFileInfo;
import com.service.microservices.exceptions.User.UserNotFoundException;
import com.service.microservices.exceptions.audio_file.AudioFileInfoNotFoundException;
import com.service.microservices.exceptions.audio_file.AudioFileNotFoundException;
import com.service.microservices.exceptions.playlist.AlbumNotFoundException;
import com.service.microservices.messages.ResponseMessage;
import com.service.microservices.repos.audio_file.AudioFileInfoRepository;
import com.service.microservices.repos.audio_file.AudioFileRepository;
import com.service.microservices.repos.playlist.AlbumRepository;
import com.service.microservices.repos.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:8080")
public class AudioFileInfoController {
    @Autowired
    private final AudioFileInfoRepository audioFileInfoRepository;
    @Autowired
    private final AudioFileRepository audioFileRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final AlbumRepository albumRepository;
    AudioFileInfoController(AudioFileInfoRepository audioFileInfoRepository, AudioFileRepository audioFileRepository, UserRepository userRepository, AlbumRepository albumRepository){
        this.audioFileInfoRepository =  audioFileInfoRepository;
        this.audioFileRepository = audioFileRepository;
        this.userRepository = userRepository;
        this.albumRepository = albumRepository;
    }
    //Getters
    //for listing all audio files info
    @GetMapping("/audioFilesInfo")
    List<AudioFileInfo> all(){ return audioFileInfoRepository.findAll(); }

    //for listing a specific audio file info
    @GetMapping("/audioFilesInfo/{id}")
    AudioFileInfo one(@PathVariable Long id){
        return audioFileInfoRepository.findById(id).orElseThrow(() -> new AudioFileInfoNotFoundException(id));
    }

    //Setters
    //Replace with new entity
    @PutMapping("/audioFilesInfo/{id}")
    AudioFileInfo replaceAudioFileInfo(@RequestBody AudioFileInfo newAudioFileInfo, @PathVariable Long id){
        return audioFileInfoRepository.findById(id).map(audioFileInfo-> {
            audioFileInfo.setAudioFileId(newAudioFileInfo.getAudioFileId());
            audioFileInfo.setAlbumId(newAudioFileInfo.getAlbumId());
            audioFileInfo.setUserId(newAudioFileInfo.getUserId());
            return audioFileInfoRepository.save(audioFileInfo);
        }).orElseGet(() ->{
            newAudioFileInfo.setId(id);
            return audioFileInfoRepository.save(newAudioFileInfo);
        });
    }
    //Change audioFileId
    @PutMapping("/audioFiles/{id}/audioFileId")
    public ResponseEntity changeAudioFileInfoAudioFileId(@PathVariable Long id, @RequestParam("audioFileId") Long audioFileId){
        String message = "";
        try{
            if(audioFileRepository.existsById(audioFileId)){
                audioFileInfoRepository.findById(id).map(audioFileInfo -> {
                    audioFileInfo.setAudioFileId(audioFileId);
                    return audioFileInfoRepository.save(audioFileInfo);
                }).orElseThrow(() -> new AudioFileInfoNotFoundException(id));
                message = "Successfully changed audio file info audioFileId to: " + audioFileId.toString();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            }
            else{
                throw new AudioFileNotFoundException(audioFileId);
            }
        }catch (Exception e){
            message = "Could not change audio file info audioFileId to: " + audioFileId.toString();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    //Change userId
    @PutMapping("/audioFilesInfo/{id}/userId")
    public ResponseEntity changeAudioFileInfoUserId(@PathVariable Long id, @RequestParam("userId") Long userId){
        String message = "";
        try{
            if(userRepository.existsById(userId)){
                audioFileInfoRepository.findById(id).map(audioFileInfo -> {
                    audioFileInfo.setUserId(userId);
                    return audioFileInfoRepository.save(audioFileInfo);
                }).orElseThrow(() -> new AudioFileInfoNotFoundException(id));
                message = "Successfully changed audio file info userId to: " + userId.toString();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            }
            else{
                throw new UserNotFoundException(userId);
            }
        }catch (Exception e){
            message = "Could not change audio file info userId to: " + userId.toString();
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    //Change albumId
    @PutMapping("/audioFilesInfo/{id}/albumId")
    public ResponseEntity changeAudioFileInfoAlbumId(@PathVariable Long id, @RequestParam("albumId") Long albumId){
        String message = "";
        try{
            if(albumRepository.existsById(albumId)){
                audioFileInfoRepository.findById(id).map(audioFileInfo -> {
                    audioFileInfo.setAlbumId(albumId);
                    return audioFileInfoRepository.save(audioFileInfo);
                }).orElseThrow(() -> new AudioFileInfoNotFoundException(id));
                message = "Successfully changed audio file info albumId to: " + albumId.toString();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            }
            else{
                throw new AlbumNotFoundException(albumId);
            }
        }catch (Exception e){
            message = "Could not change audio file info albumId to: " + albumId.toString();
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    //Change songName
    @PutMapping("/audioFilesInfo/{id}/songName")
    public ResponseEntity changeAudioFileInfoSongName(@PathVariable Long id, @RequestParam("songName") String songName){
        String message = "";
        try{
            audioFileInfoRepository.findById(id).map(audioFileInfo -> {
                audioFileInfo.setSongName(songName);
                return audioFileInfoRepository.save(audioFileInfo);
            }).orElseThrow();
            message = "Successfully changed audio file info songName to: " + songName;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e){
            message = "Could not change audio file info songName to: " + songName;
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    //Change genre
    @PutMapping("/audioFilesInfo/{id}/genre")
    public ResponseEntity changeAudioFileInfoGenre(@PathVariable Long id, @RequestParam("genre") String genre){
        String message = "";
        try{
            audioFileInfoRepository.findById(id).map(audioFileInfo -> {
                audioFileInfo.setGenre(genre);
                return audioFileInfoRepository.save(audioFileInfo);
            }).orElseThrow();
            message = "Successfully changed audio file info genre to: " + genre;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e){
            message = "Could not change audio file info genre to: " + genre;
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    //for uploading new audio file info
    @PostMapping("/audioFilesInfoUp")
    public ResponseEntity uploadAudioFileInfo(@RequestParam("audioFileId") Long audioFileId, @RequestParam("userId") Long userId, @RequestParam("albumId") Long albumId, @RequestParam("songName") String songName){
        String message = "";
        try {
            AudioFileInfo newAudioFileInfo = new AudioFileInfo(audioFileId, userId, albumId, songName);
            audioFileInfoRepository.save(newAudioFileInfo);
            message = "Uploaded audio file info successfully: " + newAudioFileInfo.toString();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e) {
            message = "Could not upload audio file info:" +
                    " audioFileId=" + audioFileId.toString() +
                    ", userId=" + userId.toString() +
                    ", albumId=" + albumId.toString() +
                    ", songName='" + songName + "'";
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    //Delete audio file info by id
    @DeleteMapping("audioFilesInfo/{id}")
    void deleteAudioFileInfo(@PathVariable Long id) { audioFileInfoRepository.deleteById(id); }
}
