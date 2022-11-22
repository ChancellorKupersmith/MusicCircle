package com.service.microservices.repos.audio_file;

import com.service.microservices.entities.audio_file.AudioFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioFileRepository extends JpaRepository<AudioFile, Long> {
}