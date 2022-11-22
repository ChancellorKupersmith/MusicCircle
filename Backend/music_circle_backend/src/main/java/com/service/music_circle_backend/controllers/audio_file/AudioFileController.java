package com.service.music_circle_backend.controllers.audio_file;

import com.service.music_circle_backend.entities.audio_file.AudioFile;
import com.service.music_circle_backend.entities.user.User;
import com.service.music_circle_backend.messages.ResponseFile;
import com.service.music_circle_backend.messages.ResponseMessage;
import com.service.music_circle_backend.services.audio_file.AudioFileService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
//@CrossOrigin("http://localhost:8080")
@CrossOrigin("http://10.24.227.244:8080")
public class AudioFileController {
    @Autowired
    private AudioFileService audioFileService;

    public AudioFileController(){}
    //for listing all audio files
    @ApiOperation(value = "Returns a list json objects representing all audio files", notes = "List comes from database: musiccirclev1")
    @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Success|OK"),
    @ApiResponse(code = 401, message = "not authorized!"),
    @ApiResponse(code = 403, message = "forbidden!!!"),
    @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping("/audioFiles")
    List<AudioFile> all(){
        return audioFileService.all();
    }
    //for listing a specific audio file
    @ApiOperation(value = "Returns one audio file", notes = "returned audio file based on id param")
    @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Success|OK"),
    @ApiResponse(code = 401, message = "not authorized!"),
    @ApiResponse(code = 403, message = "forbidden!!!"),
    @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping("/audioFiles/{id}")
    AudioFile one(@PathVariable Long id){
        return audioFileService.one(id);
    }

    @ApiOperation(value = "Returns one json object representing an audio file's info", notes = "Audio file's info found by name param")
    @GetMapping("/audioFiles/name/{s}")
    public List<AudioFile> audioFileByName(@PathVariable String s){ return audioFileService.audioFileByName(s); }

    //Get artist username
    @GetMapping("/audioFiles/artist/username")
    public User getArtist(@RequestParam("id") String id){
        return audioFileService.getArtist(Long.parseLong(id));
    }

    //for uploading an audio file
    @ApiOperation(value = "Returns response message", notes = "Uploads an audio file using multi-part file file param")
    @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Success|OK"),
    @ApiResponse(code = 401, message = "not authorized!"),
    @ApiResponse(code = 403, message = "forbidden!!!"),
    @ApiResponse(code = 404, message = "not found!!!") })
    @PostMapping("/audioFiles/up")
    public ResponseEntity uploadAudioFile(@RequestParam("file") MultipartFile file, @RequestParam String songName, @RequestParam String username){
        return audioFileService.uploadAudioFile(file, songName, username);
    }
    //Change albumId
    @ApiOperation(value = "Replaces audio file's album with new album", notes = "updates audio file found by id param with new album found by albumId param")
    @PutMapping("/audioFiles/up/albumId")
    public ResponseEntity changeAudioFileAlbumId(@RequestParam("id") String id, @RequestParam("albumId") String albumId){
        return audioFileService.changeAudioFileAlbumId(Long.parseLong(id), Long.parseLong(albumId));
    }
    //Change songName
    @ApiOperation(value = "Replaces audio file's song name with new song name", notes = "updates audio file found by id param with new song name by songName param")
    @PutMapping("/audioFiles/up/songName")
    public ResponseEntity changeAudioFileSongName(@RequestParam  String id, @RequestParam("songName") String songName){
        return audioFileService.changeAudioFileSongName(Long.parseLong(id), songName);
    }
    //Change genre
    @ApiOperation(value = "Replaces audio file's genre with new genre", notes = "updates audio file found by id param with new genre by genre param")
    @PutMapping("/audioFiles/up/genre")
    public ResponseEntity changeAudioFileGenre(@RequestParam("id") String id, @RequestParam("genre") String genreName){
        return audioFileService.changeAudioFileGenre(Long.parseLong(id), genreName);
    }
    //Change songPic
    @ApiOperation(value = "Replaces audio file's songPic with new song pico", notes = "updates audio file found by id param with new song picture by songPic param")
    @PutMapping("/audioFiles/up/song_pic")
    public ResponseEntity changeSongPic(@RequestParam("id") String id, @RequestParam("songPic") MultipartFile songPic){
        return audioFileService.changeSongPic(id,songPic);
    }

    //Add Like count
    @PutMapping("/audioFiles/like")
    public ResponseEntity likeSong(@RequestParam("id") String id, @RequestParam String username){
        return audioFileService.likeSong(id, username);
    }
    //Decrease Like count
    @PutMapping("/audioFiles/unlike")
    public ResponseEntity unlikeSong(@RequestParam("id") String id, @RequestParam String username){
        return audioFileService.unlikeSong(id, username);
    }

    @PutMapping("/audioFiles/addPlay/{username}/{id}")
    public ResponseEntity addPlay(@PathVariable String username, @PathVariable Long id){
        return audioFileService.addPlay(username, id);
    }
    //comment

    @GetMapping("/audioFiles/downStream/{id}")
    public Mono<byte[]> streamAudio(@PathVariable String id){
        return audioFileService.streamAudio(id);
    }

    @GetMapping("/audioFiles/pic/{id}")
    public String getSongPic(@PathVariable String id){
        return audioFileService.getPic(id);
    }

    @GetMapping("/audioFiles/getTop")
    public List<AudioFile> getTopSongs(){
        return audioFileService.getTopSongs();
    }
    @GetMapping("audioFiles/mostRecent")
    public List<AudioFile> getMostRecent(){
        return audioFileService.getMostRecent();
    }
    @ApiOperation(value = "Deletes audio file from database", notes = "Audio file deleted is found by id param")
    @DeleteMapping("/audioFiles/{id}")
    void deleteAudioFile(@PathVariable Long id) {
        audioFileService.deleteAudioFile(id);
    }

    @ApiOperation(value = "Returns response message", notes = "Uploads an audio file using multi-part file file param")
    @PostMapping("/audioFilesUpEx")
    public String uploadAudioFileEx(@RequestParam("song") Byte[] encodedString, @RequestParam String songName, @RequestParam String username){
        System.out.println(Arrays.toString(encodedString));
        String a="";
        if(encodedString.length > 0){
            a= "AWESOME";
        }
        return a;
    }

}

