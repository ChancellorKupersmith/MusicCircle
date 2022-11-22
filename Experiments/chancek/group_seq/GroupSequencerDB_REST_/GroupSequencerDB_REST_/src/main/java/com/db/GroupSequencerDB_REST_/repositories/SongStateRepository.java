package com.db.GroupSequencerDB_REST_.repositories;

import com.db.GroupSequencerDB_REST_.entities.Comment;
import com.db.GroupSequencerDB_REST_.entities.SongState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
@RepositoryRestResource(collectionResourceRel = "song_states", path = "song_states")
public interface SongStateRepository extends PagingAndSortingRepository<SongState, Long> {
    SongState findById(long id);
    List<SongState> findByUsers(@Param("users") String users);
}
