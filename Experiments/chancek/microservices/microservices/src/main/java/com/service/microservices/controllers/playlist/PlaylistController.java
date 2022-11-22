package com.service.microservices.controllers.playlist;
import com.service.microservices.entities.playlist.Playlist;
import com.service.microservices.messages.ResponseFile;
import com.service.microservices.repos.playlist.PlaylistRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:8080")
public class PlaylistController {

    private final PlaylistRepository playlistRepository;

    PlaylistController(PlaylistRepository playlistRepository){
        this.playlistRepository = playlistRepository;
    }

    @GetMapping("/playlists")
    List<Playlist> all(){
        return playlistRepository.findAll();
    }
    @GetMapping("/playlists/{id}")
    Playlist one(@PathVariable Long id){
        return playlistRepository.findById(id).orElseThrow();
    }
//    @GetMapping("/playlists/audioFiles")
//    public ResponseEntity<List<ResponseFile>> getAudioInPlaylist(){
//
//    }

    @PostMapping("/playlists")
    Playlist newPlaylist(@RequestBody Playlist newPlaylist){
        return playlistRepository.save(newPlaylist);
    }
    @PostMapping("/playlist/{name}/{audioFileId1}.{audioFileId2}")
    Playlist newPlaylist(@RequestBody Playlist newPlaylist, @PathVariable String name, @PathVariable Long audioFileId1, @PathVariable Long audioFileId2){
        newPlaylist.setName(name);
        List<Long> audioFiles = new ArrayList<Long>();
        audioFiles.add(audioFileId1);
        audioFiles.add(audioFileId2);
        newPlaylist.setAudioFilesList(audioFiles);
        return playlistRepository.save(newPlaylist);
    }
    @PutMapping("/playlists/{id}")
    Playlist replacePlaylist(@RequestBody Playlist newPlaylist, @PathVariable Long id){
        return playlistRepository.findById(id).map(playlist -> {
            playlist.setName(newPlaylist.getName());
            playlist.setUserId(newPlaylist.getUserId());
            return playlistRepository.save(playlist);
        }).orElseGet(() ->{
            newPlaylist.setId(id);
            return playlistRepository.save(newPlaylist);
        });
    }

    @DeleteMapping("/playlists/{id}")
    void deletePlaylist(@PathVariable Long id) {
        playlistRepository.deleteById(id);
    }

}
