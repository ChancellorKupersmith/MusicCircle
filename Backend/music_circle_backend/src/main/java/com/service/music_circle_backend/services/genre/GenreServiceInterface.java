package com.service.music_circle_backend.services.genre;

import com.service.music_circle_backend.entities.genre.Genre;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GenreServiceInterface {
    List<Genre> all();
    Genre oneById(Long id);
    List<Genre> getByName(String name);
    ResponseEntity uploadGenre(String name);
    void deleteGenre(Long id);

}
