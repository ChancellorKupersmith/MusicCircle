package com.service.music_circle_backend.repos.playlist;

import com.service.music_circle_backend.entities.playlist.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findAlbumByAlbumName(String name);
    List<Album> findAlbumsByAlbumName(String name);
}
