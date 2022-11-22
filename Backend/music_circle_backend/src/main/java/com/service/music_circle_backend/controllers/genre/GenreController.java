package com.service.music_circle_backend.controllers.genre;

import com.service.music_circle_backend.entities.genre.Genre;
import com.service.music_circle_backend.exceptions.genre.GenreNotFoundException;
import com.service.music_circle_backend.messages.ResponseMessage;
import com.service.music_circle_backend.repos.genre.GenreRepository;
import com.service.music_circle_backend.services.genre.GenreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://10.24.227.244:8080")
public class GenreController {

    private GenreService genreService;

    //Getters
    //for listing all genres
    @ApiOperation(value = "Returns list of json objects representing all genres", notes = "List comes from database: musiccirclev1")
    @GetMapping("/genres")
    List<Genre> all(){ return genreService.all(); }
    //for listing a specific genre
    @ApiOperation(value = "Returns a json object of a genre", notes = "Genre found by id in path variable")
    @GetMapping("/genres/{id}")
    Genre oneById(@PathVariable Long id){
        return genreService.oneById(id);
    }
    @ApiOperation(value = "Returns a list of json objects of genres", notes = "Genres found by name in path variable")
    @GetMapping("/genres/{name}")
    List<Genre> getByName(@PathVariable String name){
        return genreService.getByName(name);
    }

    //Setters
    //for uploading new genre
    @ApiOperation(value = "Returns a response message", notes = "Uploads new genre with name given by name param")
    @PostMapping("/genresUp")
    public ResponseEntity uploadGenre(@RequestParam("name") String name){
        return genreService.uploadGenre(name);
    }
    @ApiOperation(value = "Deletes genre from database", notes = "Genre found by id in path variable")
    @DeleteMapping("genres/{id}")
    void deleteGenre(@PathVariable Long id) { genreService.deleteGenre(id); }
}
