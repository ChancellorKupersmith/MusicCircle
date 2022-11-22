package com.service.music_circle_backend.services.audio_file;


import com.service.music_circle_backend.created_properties.AudioFileStorageProperty;
import com.service.music_circle_backend.entities.audio_file.AudioFile;
import com.service.music_circle_backend.entities.genre.Genre;
import com.service.music_circle_backend.entities.playlist.Playlist;
import com.service.music_circle_backend.entities.user.User;
import com.service.music_circle_backend.exceptions.audio_file.AudioFileNotFoundException;
import com.service.music_circle_backend.exceptions.playlist.AlbumNotFoundException;
import com.service.music_circle_backend.messages.ResponseMessage;
import com.service.music_circle_backend.repos.audio_file.AudioFileRepository;
import com.service.music_circle_backend.repos.genre.GenreRepository;
import com.service.music_circle_backend.repos.playlist.AlbumRepository;
import com.service.music_circle_backend.repos.playlist.PlaylistRepository;
import com.service.music_circle_backend.services.file.AudioFileStorageService;
import com.service.music_circle_backend.services.file.PicFileStorageService;
import com.service.music_circle_backend.services.user.UserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class AudioFileService implements AudioFileServiceInterface{
    @Autowired
    private AudioFileRepository audioFileRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AudioFileStorageService audioFileStorageService;
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    PicFileStorageService picFileStorageService;

    public AudioFile getAudioFile(Long id){
        return audioFileRepository.getOne(id);
    }

    public Stream<AudioFile> getAllAudioFiles(){
        return audioFileRepository.findAll().stream();
    }

    @Override
    public List<AudioFile> all() {
        return audioFileRepository.findAll();
    }

    @Override
    public AudioFile one(Long id) {
        return audioFileRepository.findById(id).orElseThrow(() -> new AudioFileNotFoundException(id));
    }

    //Need to do!
    @Override
    public List<AudioFile> audioFileByName(String name) {
        List<AudioFile> songsByName = new ArrayList<AudioFile>();
        List<AudioFile> all = audioFileRepository.findAll();
        for(AudioFile song : all){
            if(song.getSongName().startsWith(name)){
                songsByName.add(song);
            }
        }
        return songsByName;
    }

    @Override
    public Mono<byte[]> streamAudio(String id) {
        try{
            AudioFile audioFile = audioFileRepository.getOne(Long.parseLong(id));
            return Mono.just(Files.readAllBytes(audioFileStorageService.getPath(audioFile.getFilename())));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String getPic(String songId){
        try{
            AudioFile song = audioFileRepository.getOne(Long.parseLong(songId));
            byte[] a = Files.readAllBytes(picFileStorageService.getPath(song.getSongPicFilename()));
            String base64 = Base64.encodeBase64String(a);
            return base64;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public User getArtist(Long id){
        AudioFile song = audioFileRepository.getOne(id);
        return userService.getOne(song.getArtist());
    }

    public List<AudioFile> getTopSongs(){
        try{
            List<AudioFile> topSongs = new ArrayList<AudioFile>();
            List<AudioFile> allSongs = audioFileRepository.findAll();
            for(int i=0; i<allSongs.size(); i++){
                AudioFile song = allSongs.get(i);
                topSongs.add(song);
                //Sort
                if(i >0){
                    for(int j=topSongs.size() - 1; j > 0; j--){
                        if(topSongs.get(j).getTopCounter() > topSongs.get(j-1).getTopCounter()){
                            //Swap
                            AudioFile tmp = topSongs.get(j -1);
                            topSongs.set(j-1, song);
                            topSongs.set(j, tmp);
                        }
                        else{
                            break;
                        }
                    }
                }
            }
            return topSongs;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<AudioFile> getMostRecent(){
        List<AudioFile> mostRecent = new ArrayList<AudioFile>();
        List<AudioFile> allSongs = audioFileRepository.findAll();
        for(int i=(allSongs.size() -1); i >= 0; i--){
            mostRecent.add(allSongs.get(i));
        }
        return mostRecent;
    }
    @Override
    public AudioFile replaceAudioFile(AudioFile newAudioFile, Long id) {
        return audioFileRepository.findById(id).map(audioFile -> {
            audioFile.setFilename(newAudioFile.getFilename());
            return audioFileRepository.save(audioFile);
        }).orElseGet(() ->{
            newAudioFile.setId(id);
            return audioFileRepository.save(newAudioFile);
        });
    }

    public ResponseEntity addPlay(String username, Long id){
        String message = "";
        try{
            AudioFile audioFile = audioFileRepository.getOne(id);
            User user = userService.getUser(username);
            audioFile.addPlays();
            audioFileRepository.save(audioFile);
            Object[] list = (user.getCreatedPlaylists().toArray());
            Playlist playlist = (Playlist) list[2];
            playlist.getSongs().add(audioFile);
            playlistRepository.save(playlist);
            userService.save(user);
            message = "Successfully added play";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e){
            e.printStackTrace();
            message = "Couldn't add play";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    @Override
    public ResponseEntity uploadAudioFile(MultipartFile file, String songName, String username) {
        String message = "";
        try {
            User artist = userService.getUser(username);
            audioFileStorageService.store(file);
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            AudioFile audioFile = new AudioFile(filename, songName, artist);
            audioFile.setArtistName(artist.getArtistName());
            audioFileRepository.save(audioFile);
            artist.getUploadedSongs().add(audioFile);
            userService.save(artist);
            message = "Uploaded Successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload file: " + file.getOriginalFilename();
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    public ResponseEntity likeSong(String id, String username){
        String message = "";
        try {
            AudioFile song = audioFileRepository.getOne(Long.parseLong(id));
            song.addLikes();
            User artist = userService.getUser(username);
            Playlist likes = userService.getPlaylist(username, "1");
            audioFileRepository.save(song);
            likes.getSongs().add(song);
            playlistRepository.save(likes);
            userService.saveUser(artist);
            message = "Added Like Successfully to: " + song.getSongName();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not add like";
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    public ResponseEntity unlikeSong(String id, String username){
        String message = "";
        try {
            AudioFile song = audioFileRepository.getOne(Long.parseLong(id));
            song.decreaseLikes();
            User artist = userService.getUser(username);
            Playlist likes = (userService.getPlaylist(username, "1"));
            if(likes.getSongs().contains(song)){
                for(int i=0; i<likes.getSongs().size(); i++){
                    if(likes.getSongs().get(i).getId() == Long.parseLong(id)){
                        likes.getSongs().remove(i);
                    }
                }
            }
            audioFileRepository.save(song);
            playlistRepository.save(userService.getPlaylist(username, "1"));
            userService.saveUser(artist);
            message = "Decreased Like Successfully to: " + song.getSongName();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not decrease like";
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @Override
    public ResponseEntity changeAudioFileSongName(Long id, String songName) {
        String message = "";
        try{
            audioFileRepository.findById(id+1).map(audioFile -> {
                audioFile.setSongName(songName);
                return audioFileRepository.save(audioFile);
            }).orElseThrow(()-> new AudioFileNotFoundException(id+1));
            message = "Successfully changed audio file info songName to: " + songName;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e){
            message = "Could not change audio file info songName to: " + songName;
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @Override
    public ResponseEntity changeAudioFileAlbumId(Long id, Long albumId) {
        String message = "";
        try{
            if(albumRepository.existsById(albumId)){
                audioFileRepository.findById(id+1).map(audioFile -> {
                    audioFile.setAlbum(albumRepository.getOne(albumId));
                    return audioFileRepository.save(audioFile);
                }).orElseThrow(() -> new AudioFileNotFoundException(id));
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

    @Override
    public ResponseEntity changeAudioFileGenre(Long id, String genreName) {
        String message = "";
        Genre genre = genreRepository.getGenreByName(genreName).get(0);
        try{
            audioFileRepository.findById(id+1).map(audioFile -> {
                audioFile.setGenre(genre);
                return audioFileRepository.save(audioFile);
            }).orElseThrow(() -> new AudioFileNotFoundException(id));
            message = "Successfully changed audio file info genre to: " + genre;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e){
            message = "Could not change audio file info genre to: " + genre;
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @Override
    public ResponseEntity changeSongPic(String id, MultipartFile songPic) {
        String message = "";
        try{
            audioFileRepository.findById(Long.parseLong(id)).map(audioFile -> {
                try {
                    audioFile.setSongPicFilename(StringUtils.cleanPath(songPic.getOriginalFilename()));
                    picFileStorageService.store(songPic);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return audioFileRepository.save(audioFile);
            }).orElseThrow(() -> new AudioFileNotFoundException(Long.parseLong(id)));
            message = "Successfully changed audio file info songPic to: " + StringUtils.cleanPath(songPic.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e){
            message = "Could not change audio file info songPic to: " + StringUtils.cleanPath(songPic.getOriginalFilename());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @Override
    public void deleteAudioFile(Long id) {
        audioFileRepository.deleteById(id);
    }

}
