package com.service.music_circle_backend.services.playlist;

import com.service.music_circle_backend.entities.playlist.Playlist;
import com.service.music_circle_backend.exceptions.playlist.PlaylistNotFoundException;
import com.service.music_circle_backend.repos.playlist.PlaylistRepository;
import com.service.music_circle_backend.repos.user.UserRepository;
import com.service.music_circle_backend.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface PlaylistServiceInterface {
    public List<Playlist> all();
    public Playlist one(Long id);
    public ResponseEntity uploadPlaylist(Playlist newPlaylist, User user);
    public ResponseEntity uploadPlaylistWithSongs(String name, String username, String songList);
    public ResponseEntity addSongs(Long playlistId, Long songList);
    public ResponseEntity removeSongs(Long playlistId, String songList);
    public void deletePlaylist(Long id);

}
