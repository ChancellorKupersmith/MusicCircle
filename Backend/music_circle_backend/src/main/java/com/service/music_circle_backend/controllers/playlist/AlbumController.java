package com.service.music_circle_backend.controllers.playlist;

import com.service.music_circle_backend.entities.playlist.Album;
import com.service.music_circle_backend.services.playlist.AlbumService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("http://10.24.227.244:8080")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

//
//    AlbumController(AlbumRepository albumRepository, AudioFileInfoRepository audioFileInfoRepository, UserService userService){
//        this.albumRepository = albumRepository;
//        this.audioFileInfoRepository = audioFileInfoRepository;
//        this.userService = userService;
//    }

    //Getters
    //for listing all albums
    @ApiOperation(value = "Returns a list of json objects of albums", notes = "List comes from database: musiccirclev1")
    @GetMapping("/albums")
    public List<Album> all(){ return albumService.all(); }

    //for listing a specific album
    @ApiOperation(value = "Returns a json object of a album", notes = "Album found by id in path variable")
    @GetMapping("/albums/find/{id}")
    public Album one(@PathVariable Long id){
        return albumService.one(id);
    }

    @ApiOperation(value = "Returns a json object of a album", notes = "Album found by name in path variable")
    @GetMapping("/albums/find/name")
    public List<Album> albumsByName(@RequestParam("name") String name){
       return albumService.albumsByName(name);
    }
    //for listing by artist

    //Setters
    //Create new album
    @ApiOperation(value = "Returns a response message", notes = "Uploads a new album with name given by name param, artist given by username param, and adds at least one song to it with songId param")
    @PostMapping("/albums/up/with_songs")
    public ResponseEntity uploadAlbumWithSongs(@RequestParam("name") String name, String username, String songList){
        return albumService.uploadAlbumWithSongs(name, username, songList);
    }
    @PostMapping("/albums/up")
    public ResponseEntity uploadAlbum(@RequestParam("name") String name, @RequestParam("username") String artistUsername){
        return albumService.uploadAlbum(name, artistUsername);
    }
    @PutMapping("/albums/up/album_pic")
    public ResponseEntity setAlbumArt(@RequestParam("albumArt") MultipartFile albumArt, @RequestParam("albumId") String albumId){
        return albumService.setAlbumArt(Long.parseLong(albumId), albumArt);
    }
    @ApiOperation(value = "Returns a response message", notes = "Adds existing song to album found with songId param")
    @PutMapping("/albums/addSongs")
    public ResponseEntity addSongs(@RequestParam("albumId") String albumId, @RequestParam("songList") String songList){
        return albumService.addSongs(Long.parseLong(albumId), songList);
    }
    @GetMapping("/albums/name")
    public List<Album> getAlbumsByName(@RequestParam("name") String name){
        return albumService.getAlbumsByName(name);
    }
    @PutMapping("/albums/up/addSong")
    public ResponseEntity addSong(@RequestParam("albumId") String albumId, @RequestParam("songId") String songId){
        return albumService.addSong(Long.parseLong(albumId), Long.parseLong(songId));
    }

    @GetMapping("/albums/down/album_pic")
    public String getAlbumArt(@RequestParam("id") String id){
        return albumService.getAlbumArt(Long.parseLong(id));
    }
    @ApiOperation(value = "Deletes album from database", notes = "Album found with id param")
    @DeleteMapping("albums/delete")
    void deleteAlbum(@RequestParam Long id){ albumService.deleteAlbum(id); }

}
