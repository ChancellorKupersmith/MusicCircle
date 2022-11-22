package com.service.microservices.controllers.comment;

import com.service.microservices.entities.comment.Comment;
import com.service.microservices.exceptions.comment.CommentNotFoundException;
import com.service.microservices.repos.comment.CommentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @GetMapping("/comments")
    List<Comment> all() { return commentRepository.findAll(); }
    @GetMapping("/comments/{id}")
    Comment one(@PathVariable Long id) { return commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id)); }

    //For creating new comments
    @PostMapping("/comments")
    Comment newComment(@RequestBody Comment newComment) { return commentRepository.save(newComment); }
    @PostMapping("/comments/{text}/{audioFileId}/{userId}")
    Comment newComment(@PathVariable String text, @PathVariable Long audioFileId, @PathVariable Long userId) {
        Comment newComment = new Comment(text, audioFileId, userId);
        return commentRepository.save(newComment);
    }

    //For updating comments
    @PutMapping("/comments/{id}/text")
    Comment replaceCommentText(@PathVariable Long id, @RequestParam("text") String text) {
        return commentRepository.findById(id).map(comment -> {
            comment.setText(text);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new CommentNotFoundException(id));
    }

    @DeleteMapping("/comments/{id}")
    void deleteComment(@PathVariable Long id) { commentRepository.deleteById(id); }
}
