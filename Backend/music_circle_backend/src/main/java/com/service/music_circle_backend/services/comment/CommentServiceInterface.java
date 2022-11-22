package com.service.music_circle_backend.services.comment;

import com.service.music_circle_backend.entities.comment.Comment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentServiceInterface {
    public List<Comment> all();
    public Comment one(Long id);
    public List<Comment> getBySong(Long songId);
    public List<Comment> getByUser(String username);
    public ResponseEntity postComment(String comment, Long songId, String username);
    public void deleteComment(Long id);

}
