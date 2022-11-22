package com.service.music_circle_backend.entities;

public interface ShareContent {
    enum entityType{
        user,
        group,
        audio_file,
        playlist,
        album,
        event
    }

    entityType getType();
}
