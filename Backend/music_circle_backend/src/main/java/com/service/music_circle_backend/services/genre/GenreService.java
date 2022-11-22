package com.service.music_circle_backend.services.genre;

import com.service.music_circle_backend.entities.genre.Genre;
import com.service.music_circle_backend.exceptions.genre.GenreNotFoundException;
import com.service.music_circle_backend.messages.ResponseMessage;
import com.service.music_circle_backend.repos.genre.GenreRepository;
import com.service.music_circle_backend.services.user.GroupServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class GenreService implements GenreServiceInterface {
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<Genre> all() {
        return genreRepository.findAll();
    }

    @Override
    public Genre oneById(Long id) {
        return genreRepository.findById(id).orElseThrow(() -> new GenreNotFoundException(id));
    }

    @Override
    public List<Genre> getByName(String name) {
        return genreRepository.getGenreByName(name);
    }

    @Override
    public ResponseEntity uploadGenre(String name) {
        String message = "";
        try {
            Genre newGenre = new Genre(name);
            genreRepository.save(newGenre);
            message = "Uploaded genre successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e){
            message = "Could not upload genre : " +
                    "Genre name= '" + name.toString() + "'";
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @Override
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }
}
