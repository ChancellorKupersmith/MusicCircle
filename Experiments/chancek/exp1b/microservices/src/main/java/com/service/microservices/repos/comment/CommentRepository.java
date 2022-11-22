package com.service.microservices.repos.comment;

import com.service.microservices.entities.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
