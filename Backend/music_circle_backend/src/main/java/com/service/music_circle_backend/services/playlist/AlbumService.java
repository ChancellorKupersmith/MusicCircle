package com.service.music_circle_backend.services.playlist;

import com.service.music_circle_backend.entities.audio_file.AudioFile;
import com.service.music_circle_backend.entities.playlist.Album;
import com.service.music_circle_backend.entities.user.User;
import com.service.music_circle_backend.exceptions.audio_file.AudioFileNotFoundException;
import com.service.music_circle_backend.exceptions.playlist.AlbumNotFoundException;
import com.service.music_circle_backend.messages.ResponseMessage;
import com.service.music_circle_backend.repos.audio_file.AudioFileRepository;
import com.service.music_circle_backend.repos.playlist.AlbumRepository;
import com.service.music_circle_backend.services.file.PicFileStorageService;
import com.service.music_circle_backend.services.user.UserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumService implements AlbumServiceInterface{
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private AudioFileRepository audioFileRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PicFileStorageService picFileStorageService;

    //Getters
    @Override
    public List<Album> all(){ return albumRepository.findAll(); }

    @Override
    public Album one(Long id){
        return albumRepository.findById(id).orElseThrow(() -> new AlbumNotFoundException(id));
    }

    @Override
    public List<Album> albumsByName(String name) {
        if(!(albumRepository.findAlbumByAlbumName(name).isEmpty())){
            return albumRepository.findAlbumByAlbumName(name);
        }
        else{
            return null;
        }
    }

    @Override
    public ResponseEntity uploadAlbumWithSongs(String name, String username, String songList) {
        String message = "";
        try{
            User artist = userService.getUser(username);
            ArrayList<AudioFile> songs = new ArrayList<AudioFile>();
            for(int i=0; i<songList.length(); i++){
                if(songList.charAt(i) != ','){
                    songs.add(audioFileRepository.getOne((long) Character.getNumericValue(songList.charAt(i))));
                }
            }
            Album newAlbum = new Album(name, artist.getUsername(), songs);
            albumRepository.save(newAlbum);
            message = "Successfully uploaded album: " + newAlbum.toString();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e){
            message = "Could not upload album: " +
                    " name: " + name +
                    ", username: " + username +
                    ", songList: " + songList;
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    public ResponseEntity setAlbumArt(Long albumId, MultipartFile albumArt){
        String message = "";
        try{
            albumRepository.findById(albumId).map(album -> {
                try {
                    album.setAlbumPicFilename(StringUtils.cleanPath(albumArt.getOriginalFilename()));
                    picFileStorageService.store(albumArt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return albumRepository.save(album);
            }).orElseThrow(() -> new AlbumNotFoundException(albumId));
            message = "Successfully changed album art" + StringUtils.cleanPath(albumArt.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e){
            message = "Could not upload album art to album: " + albumId;
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    public ResponseEntity uploadAlbum(String name, String artistUsername){
        String message = "";
        try{
            User artist = userService.getUser(artistUsername);
            Album newAlbum = new Album(name, artistUsername);
            albumRepository.save(newAlbum);
            artist.getUploadedAlbums().add(newAlbum);
            userService.save(artist);
            message = "Successfully created album";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e){
            e.printStackTrace();
            message = "Could not upload album";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }

    }
    @Override
    public ResponseEntity addSongs(Long albumId, String songList) {
        String message = "";
        try{
            Album album = albumRepository.getOne(albumId);
            for(int i=0; i<songList.length(); i++){
                if(songList.charAt(i) != ','){
                    album.getTracklist().add(audioFileRepository.getOne((long) Character.getNumericValue(songList.charAt(i))));
                }
            }
            albumRepository.save(album);
            message = "Successfully added songs: " + songList + " to album trackist";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e){
            message = "Could not upload songs: " + songList.toString() + "to albumId: " + albumId.toString();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    public List<Album> getAlbumsByName(String name){
        List<Album> albumsByName = new ArrayList<Album>();
        List<Album> all = albumRepository.findAll();
        for(Album album : all){
            if(album.getAlbumName().startsWith(name)){
                albumsByName.add(album);
            }
        }
        return albumsByName;
    }
    public ResponseEntity addSong(Long albumId, Long songId){
        String message = "";
        try{
            Album album = albumRepository.getOne(albumId);
            AudioFile song = audioFileRepository.getOne(songId);
            album.getTracklist().add(song);
            albumRepository.save(album);
            song.setAlbum(album);
            audioFileRepository.save(song);
            message ="Successfully add song to album";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }catch (Exception e){
            e.printStackTrace();
            message = "Could not add song to album";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    public String getAlbumArt(Long albumId){
        try{
            Album album = albumRepository.getOne(albumId);
            byte[] a = Files.readAllBytes(picFileStorageService.getPath(album.getAlbumPicFilename()));
            String base64 = Base64.encodeBase64String(a);
            return base64;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }
}
