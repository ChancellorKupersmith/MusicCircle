package com.service.music_circle_backend.services.playlist;

import com.service.music_circle_backend.entities.audio_file.AudioFile;
import com.service.music_circle_backend.entities.playlist.Album;
import com.service.music_circle_backend.entities.playlist.Playlist;
import com.service.music_circle_backend.entities.user.User;
import com.service.music_circle_backend.exceptions.playlist.PlaylistNotFoundException;
import com.service.music_circle_backend.messages.ResponseMessage;
import com.service.music_circle_backend.repos.audio_file.AudioFileRepository;
import com.service.music_circle_backend.repos.playlist.PlaylistRepository;
import com.service.music_circle_backend.repos.user.UserRepository;
import com.service.music_circle_backend.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistService implements PlaylistServiceInterface{
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private AudioFileRepository audioFileRepository;
    @Autowired
    private UserService userService;

    public PlaylistService(){}
    //Getters
    @Override
    public List<Playlist> all() { return playlistRepository.findAll(); }
    @Override
    public Playlist one(Long id) {
        return playlistRepository.findById(id).orElseThrow(()->new PlaylistNotFoundException(id));
    }

    //Setters
    @Override
    public ResponseEntity uploadPlaylist(Playlist newPlaylist, User user) {
        String message = "";
        try{
            playlistRepository.save(newPlaylist);
            user.getCreatedPlaylists().add(newPlaylist);
            userService.save(user);
            message = "Uploaded playlist successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }
        catch(Exception e){
            message = "Could not upload playlist";
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @Override
    public ResponseEntity uploadPlaylistWithSongs(String name, String username, String songList) {
        String message = "";
        try{
            User creator = userService.getUser(username);
            ArrayList<AudioFile> songs = new ArrayList<AudioFile>();
            for(int i=0; i<songList.length(); i++){
                if(songList.charAt(i) != ','){
                    songs.add(audioFileRepository.getOne((long) Character.getNumericValue(songList.charAt(i))));
                }
            }
            Playlist newPlaylist = new Playlist(name, creator.getUsername(), songs);
            playlistRepository.save(newPlaylist);
            message = "Uploaded audio file successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }
        catch(Exception e){
            message = "Could not upload playlist";
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @Override
    public ResponseEntity addSongs(Long playlistId, Long songId) {
        String message = "";
        try{
            Playlist playlist = playlistRepository.getOne(playlistId);
            AudioFile song = audioFileRepository.getOne(songId);
            playlist.getSongs().add(song);
            playlistRepository.save(playlist);
            message = "Successfully added song to playlist";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e){
            message = "Could not add songId";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    @Override
    public ResponseEntity removeSongs(Long playlistId, String songList) {
        String message = "";
        try{
            Playlist playlist = playlistRepository.getOne(playlistId);
            for(int i=0; i<songList.length(); i++){
                if(songList.charAt(i) != ','){
                    playlist.getSongs().remove(audioFileRepository.getOne((long) Character.getNumericValue(songList.charAt(i))));
                }
            }
            playlistRepository.save(playlist);
            message = "Successfully removed songs: " + songList + " to album trackist";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e){
            message = "Could not upload songs: " + songList.toString() + "to playlistId: " + playlistId.toString();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @Override
    public void deletePlaylist(Long id) { playlistRepository.deleteById(id); }
}
