package com.service.microservices.repos.playlist;

import com.service.microservices.entities.playlist.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
