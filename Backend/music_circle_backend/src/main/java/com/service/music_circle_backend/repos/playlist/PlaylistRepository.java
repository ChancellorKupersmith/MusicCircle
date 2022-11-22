package com.service.music_circle_backend.repos.playlist;

import com.service.music_circle_backend.entities.playlist.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
