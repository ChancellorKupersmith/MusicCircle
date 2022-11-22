package com.example.musiccircle.Services;

import android.app.Notification;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.offline.Download;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.scheduler.Scheduler;

//ServerSideStreaming

import java.util.List;

public class Mp3DownloadService extends DownloadService {

    Mp3DownloadService(){
        super(FOREGROUND_NOTIFICATION_ID_NONE);
    }
    @Override
    protected DownloadManager getDownloadManager() {
        //DownloadManager downloadManager = new DownloadManager(DownloadConst)
        return null;
    }

    @Nullable
    @Override
    protected Scheduler getScheduler() {
        return null;
    }

    @Override
    protected Notification getForegroundNotification(List<Download> downloads) {
        return null;
    }
}
