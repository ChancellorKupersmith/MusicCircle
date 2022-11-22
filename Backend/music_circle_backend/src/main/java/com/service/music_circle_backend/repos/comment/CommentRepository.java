package com.service.music_circle_backend.repos.comment;
import com.service.music_circle_backend.entities.audio_file.AudioFile;
import com.service.music_circle_backend.entities.comment.Comment;
import com.service.music_circle_backend.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsBySong(AudioFile song);
    List<Comment> findCommentsByCommenter(User commenter);
}
