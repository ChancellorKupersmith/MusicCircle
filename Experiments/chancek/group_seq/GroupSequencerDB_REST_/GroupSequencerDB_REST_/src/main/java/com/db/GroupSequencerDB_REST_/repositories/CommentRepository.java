package com.db.GroupSequencerDB_REST_.repositories;

import com.db.GroupSequencerDB_REST_.entities.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "comments", path = "comments")
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    Comment findById(long id);
    List<Comment> findByBarLocation(@Param("barLocation") double barLocation);
    //List<Comment> findCommentsByBarLocation(double barLocation);
}
