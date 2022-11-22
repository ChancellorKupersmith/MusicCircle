package com.service.music_circle_backend.repos.user;

import com.service.music_circle_backend.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findUsersByFirstName(String first);
    List<User> findUsersByLastName(String last);
}
