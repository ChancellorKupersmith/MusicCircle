package com.service.microservices.repos.playlist;

import com.service.microservices.entities.playlist.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
