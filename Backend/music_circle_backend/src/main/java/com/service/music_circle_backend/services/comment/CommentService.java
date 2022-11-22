package com.service.music_circle_backend.services.comment;

import com.service.music_circle_backend.entities.audio_file.AudioFile;
import com.service.music_circle_backend.entities.comment.Comment;
import com.service.music_circle_backend.entities.user.User;
import com.service.music_circle_backend.exceptions.comment.CommentNotFoundException;
import com.service.music_circle_backend.exceptions.playlist.AlbumNotFoundException;
import com.service.music_circle_backend.messages.ResponseMessage;
import com.service.music_circle_backend.repos.audio_file.AudioFileRepository;
import com.service.music_circle_backend.repos.comment.CommentRepository;
import com.service.music_circle_backend.repos.user.UserRepository;
import com.service.music_circle_backend.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements CommentServiceInterface{
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AudioFileRepository audioFileRepository;
    @Autowired
    private UserService userService;
    @Override
    public List<Comment> all() {
        return commentRepository.findAll();
    }

    @Override
    public Comment one(Long id) { return commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id)); }

    @Override
    public List<Comment> getBySong(Long songId) {
        if((audioFileRepository.findById(songId).isPresent())){
            AudioFile song = audioFileRepository.getOne(songId);
            return commentRepository.findCommentsBySong(song);
        }
        else{
            return null;
        }
    }

    @Override
    public List<Comment> getByUser(String username) {
        try{
            User commenter = userService.getUser(username);
            return commentRepository.findCommentsByCommenter(commenter);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseEntity postComment(String comment, Long songId, String username) {
        String message = "";
        try{
            User commenter = userService.getUser(username);
            AudioFile song = audioFileRepository.getOne(songId);
            Comment newComment = new Comment(comment, commenter);
            commentRepository.save(newComment);
            message = "Successfully uploaded comment.";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e){
            message = "Could not post comment";
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @Override
    public void deleteComment(Long id) { commentRepository.deleteById(id); }
}
