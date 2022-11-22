package com.service.music_circle_backend.controllers.comment;

import com.service.music_circle_backend.entities.comment.Comment;
import com.service.music_circle_backend.services.comment.CommentService;
import com.service.music_circle_backend.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://10.24.227.244:8080")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/comments")
    List<Comment> all(){ return commentService.all(); }

    @GetMapping("/comments/{id}")
    Comment one(@PathVariable Long id){ return commentService.one(id); }

    @GetMapping("/comments/by_song/{songId}")
    List<Comment> getBySong(@PathVariable String songId){ return commentService.getBySong(Long.parseLong(songId)); }

    @GetMapping("/comments/by_user")
    List<Comment> getByUser(@RequestParam("username") String username){ return commentService.getByUser(username); }

    @PostMapping("/comments/up")
    ResponseEntity postComment(@RequestParam("comment") String comment, @RequestParam("songId") String songId, @RequestParam("username") String username){ return commentService.postComment(comment, Long.parseLong(songId), username); }

    @DeleteMapping("/comments/delete/{id}")
    void deleteComment(@PathVariable Long id){ commentService.deleteComment(id); }

}
