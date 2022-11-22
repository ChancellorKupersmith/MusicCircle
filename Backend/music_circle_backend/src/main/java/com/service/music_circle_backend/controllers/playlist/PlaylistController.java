package com.service.music_circle_backend.controllers.playlist;

import com.service.music_circle_backend.entities.playlist.Playlist;
import com.service.music_circle_backend.entities.user.User;
import com.service.music_circle_backend.exceptions.playlist.PlaylistNotFoundException;
import com.service.music_circle_backend.messages.ResponseMessage;
import com.service.music_circle_backend.repos.playlist.PlaylistRepository;
import com.service.music_circle_backend.repos.user.UserRepository;
import com.service.music_circle_backend.services.playlist.PlaylistService;
import com.service.music_circle_backend.services.user.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://10.24.227.244:8080")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private UserService userService;
//    PlaylistController(PlaylistRepository playlistRepository, UserRepository userRepository){
//        this.playlistRepository = playlistRepository;
//        this.userRepository = userRepository;
//    }
    @ApiOperation(value = "Returns a list of json objects representing playlists", notes = "List comes from database: musiccirclev1")
    @GetMapping("/playlists")
    List<Playlist> all(){ return playlistService.all(); }
    @ApiOperation(value = "Returns a json object of a playlist", notes = "Playlist found by id in path variable")
    @GetMapping("/playlists/{id}")
    Playlist one(@PathVariable Long id){
        return playlistService.one(id);
    }

    //for creating new Playlist
    @ApiOperation(value = "Returns a response message", notes = "Uploads a new playlist with name given by 'name' in path variable and links creator to user found by userId path variable")
    @PostMapping("/playlists/up/{username}/{name}")
    public ResponseEntity newPlaylist(@PathVariable("name") String name, @PathVariable("username") String username){
        User creator = userService.getUser(username);
        Playlist newPlaylist = new Playlist(name, creator.getUsername());
        return playlistService.uploadPlaylist(newPlaylist, creator);
    }
    //for updating playlist
    @ApiOperation(value = "Replaces playlist with new playlist", notes = "new playlist is given by newPlaylist param")
    @PostMapping("/playlistsWithSongsUp")
    public ResponseEntity newPlaylistWithSongs(@RequestParam String name, @RequestParam String username, @RequestParam String songList){
        return playlistService.uploadPlaylistWithSongs(name, username, songList);
    }
    @ApiOperation(value = "Adds songs to playlist", notes = "Playlist found by id in path variable, and new songs added are found with song list request param")
    @PutMapping("/playlists/addSongs")
    public ResponseEntity addSongs(@RequestParam String playlistId, @RequestParam String songId) {
        return playlistService.addSongs(Long.parseLong(playlistId), Long.parseLong(songId));
    }

    @ApiOperation(value = "Removes songs from playlist", notes = "Playlist found by id in path variable, and songs removed are found with song list request param")
    @PutMapping("/playlists/removeSongs")
    public ResponseEntity removeSongs(@RequestParam String playlistId, @RequestParam String songList) {
        return playlistService.removeSongs(Long.parseLong(playlistId), songList);
    }

    @ApiOperation(value = "Deletes playlist from database", notes = "Playlist found by path variable id")
    @DeleteMapping("/playlists/{id}")
    void deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
    }

}
