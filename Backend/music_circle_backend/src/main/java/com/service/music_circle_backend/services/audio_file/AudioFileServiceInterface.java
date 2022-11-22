package com.service.music_circle_backend.services.audio_file;

import com.service.music_circle_backend.entities.audio_file.AudioFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AudioFileServiceInterface {
    List<AudioFile> all();
    AudioFile one(Long id);
    List<AudioFile> audioFileByName(String name);
    Mono<byte[]> streamAudio(String id);
    AudioFile replaceAudioFile(AudioFile newAudioFile, Long id);
    ResponseEntity uploadAudioFile(MultipartFile file, String songName, String username);
    ResponseEntity changeAudioFileSongName(Long id, String songName);
    ResponseEntity changeAudioFileAlbumId(Long id, Long albumId);
    ResponseEntity changeAudioFileGenre(Long id, String genreName);
    ResponseEntity changeSongPic(String id, MultipartFile songPic);
    void deleteAudioFile(Long id);




}
