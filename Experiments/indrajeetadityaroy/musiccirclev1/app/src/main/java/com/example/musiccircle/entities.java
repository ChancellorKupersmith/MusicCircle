package com.example.musiccircle;

public interface entities {
    int TYPE_USER = 1;
    int TYPE_GROUP = 2;
    int TYPE_ALBUM = 3;
    int TYPE_AUDIO_FILE = 4;
    int TYPE_AUDIO_FILE_INFO = 5;
    int TYPE_GENRE = 6;
    int TYPE_PLAYLIST = 7;

    int getType();
}
