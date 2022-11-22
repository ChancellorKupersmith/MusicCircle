package com.example.musiccircle.Entity;

import java.io.Serializable;

public interface entities extends Serializable {
    int TYPE_USER = 1;
    int TYPE_GROUP = 2;
    int TYPE_ALBUM = 3;
    int TYPE_AUDIO_FILE = 4;
    int TYPE_EVENT = 5;
    int TYPE_GENRE = 6;
    int TYPE_PLAYLIST = 7;

    int getType();
}
