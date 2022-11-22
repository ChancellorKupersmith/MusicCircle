package com.db.GroupSequencerDB_REST_.repositories;


import com.db.GroupSequencerDB_REST_.entities.AudioFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@RepositoryRestResource(collectionResourceRel = "audio_files", path = "audio_files")
//public interface AudioFileRepository extends PagingAndSortingRepository<AudioFile, Long> {
//    AudioFile findById(long id);
//    List<AudioFile> findByBarLocation(@Param("barLocation") double barLocation);
//    List<AudioFile> findByFilename(@Param("filename") String filename);
//}
@RepositoryRestResource(collectionResourceRel = "audio_files", path = "audio_files")
public interface AudioFileRepository extends JpaRepository<AudioFile, Long>{
    List<AudioFile> findByBarLocation(@Param("barLocation") double barLocation);
    List<AudioFile> findByFilename(@Param("filename") String filename);

}
