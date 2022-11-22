package com.service.music_circle_backend.repos.user;

import com.service.music_circle_backend.entities.user.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group,Long> {
    List<Group> findGroupsByName(String name);

}
