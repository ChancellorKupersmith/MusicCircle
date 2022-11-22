package com.service.microservices.repos.audio_file;

import com.service.microservices.entities.audio_file.AudioFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioFileInfoRepository extends JpaRepository<AudioFileInfo, Long> {
}
