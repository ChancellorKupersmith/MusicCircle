package com.service.music_circle_backend.repos.socket;

import com.service.music_circle_backend.entities.socket.NotificationResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationResponseRepository extends JpaRepository<NotificationResponse, Long> {
//    List<NotificationResponse> findAllByUser_to_username(String user_to_username);
//    void deleteAllByUser_to_username(String user_to_username);
}
