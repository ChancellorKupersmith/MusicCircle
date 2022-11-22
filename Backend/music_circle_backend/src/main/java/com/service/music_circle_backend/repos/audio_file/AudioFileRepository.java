package com.service.music_circle_backend.repos.audio_file;

import com.service.music_circle_backend.entities.audio_file.AudioFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AudioFileRepository extends JpaRepository<AudioFile, Long> {
    List<AudioFile> findAudioFileBySongName(String songName);
}
