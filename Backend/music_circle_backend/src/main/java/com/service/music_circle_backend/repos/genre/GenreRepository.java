package com.service.music_circle_backend.repos.genre;

import com.service.music_circle_backend.entities.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> getGenreByName(String name);
}
