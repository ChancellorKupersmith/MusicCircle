package com.service.music_circle_backend.services.playlist;

import com.service.music_circle_backend.entities.playlist.Album;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AlbumServiceInterface {
    public List<Album> all();
    public Album one(Long id);
    public List<Album> albumsByName(String name);
    //Need to create parser for getting song ids from string
    public ResponseEntity uploadAlbumWithSongs(String name, String username, String songList);
    public ResponseEntity addSongs(Long albumId, String songList);
    public void deleteAlbum(Long id);
}
