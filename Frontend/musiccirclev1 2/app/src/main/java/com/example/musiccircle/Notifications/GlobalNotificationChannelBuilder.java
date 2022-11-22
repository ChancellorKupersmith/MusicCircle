package com.example.musiccircle.Notifications;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;

public class GlobalNotificationChannelBuilder extends Application {
    /**
     * List of Notification Instances
     *
     * Peer To Peer:
     * - User likes your song/album/playlist
     * - User comments on your song
     * - New user follows you
     *
     * Personal Messages:
     * - Song/Album/Playlist shares
     * - Direct messages?
     *
     * Following:
     * - New songs/albums from users you follow
     * - New events they are playing at
     *
     * Groups:
     * - New group invites
     * - Song/Album/Playlist shares
     * - New group message
     *
     * Events:
     * - Event you signed up for is 12 or 24 hours away
     * - 50% of the people you follow are attending an event
     */

    // Strings for creating notification channels
    private static final String PEER_TO_PEER_CHANNEL_ID = "peerToPeerId";
    private static final CharSequence PEER_TO_PEER_CHANNEL_NAME = "Peer To Peer";
    private static final String PEER_TO_PEER_CHANNEL_DESCRIPTION = "Likes, new Comments, and new Followers";

    private static final String PERSONAL_MESSAGES_CHANNEL_ID = "personalMessagesId";
    private static final CharSequence PERSONAL_MESSAGES_CHANNEL_NAME = "Personal Messages";
    private static final String PERSONAL_MESSAGES_CHANNEL_DESCRIPTION = "Shares from friends and other personal messages";

    private static final String FOLLOWING_CHANNEL_ID = "followingId";
    private static final CharSequence FOLLOWING_CHANNEL_NAME = "Following";
    private static final String FOLLOWING_CHANNEL_DESCRIPTION = "New Posts and Updates from Users you follow";

    private static final String GROUPS_CHANNEL_ID = "groups";
    private static final CharSequence GROUPS_CHANNEL_NAME = "Groups";
    private static final String GROUPS_CHANNEL_DESCRIPTION = "New group invites or updates";

    private static final String PLAY_MUSIC_CHANNEL_ID = "musicId";
    private static final String PLAY_MUSIC_CHANNEL_NAME = "play music channel";
    private static final String PLAY_MUSIC_CHANNEL_DESCRIPTION = "Allows playing of music outside of app (might cause bugs while using app if turned off).";

    private static final String EVENTS_CHANNEL_ID = "events";
    private static final CharSequence EVENTS_CHANNEL_NAME = "Events";
    private static final String EVENTS_CHANNEL_DESCRIPTION = "Reminders about events you signed up for or about events your fried=nds are going to";


    // Notification channels, one for each notification type
    private static NotificationChannel peerToPeer;
    private static NotificationChannel personalMessages;
    private static NotificationChannel following;
    private static NotificationChannel groups;
    private static NotificationChannel events;
    private static PlayerNotificationManager playerNotificationManager;


    @Override
    public void onCreate(){
        super.onCreate();

       // playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(getApplicationContext(), PLAY_MUSIC_CHANNEL_ID, PLAY_MUSIC_CHANNEL_NAME, PLAY_MUSIC_CHANNEL_DESCRIPTION, 1, PlayerNotificationManager.MediaDescriptionAdapter)



    }
    // Empty constructor
    public GlobalNotificationChannelBuilder() {}

    // Static methods for creating each type of notification channel

    // Peer To Peer channel
    public void createMusicNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel musicChannel = new NotificationChannel(
                    PLAY_MUSIC_CHANNEL_ID,
                    PLAY_MUSIC_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(musicChannel);
            }
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static NotificationChannel createPeerToPeerNotifChannel() {
        peerToPeer = new NotificationChannel(PEER_TO_PEER_CHANNEL_ID, PEER_TO_PEER_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        peerToPeer.setDescription(PEER_TO_PEER_CHANNEL_DESCRIPTION);
        peerToPeer.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        return peerToPeer;
    }
    // Personal Messages channel
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static NotificationChannel createPersonalMessagesNotifChannel() {
        personalMessages = new NotificationChannel(PERSONAL_MESSAGES_CHANNEL_ID, PERSONAL_MESSAGES_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        personalMessages.setDescription(PERSONAL_MESSAGES_CHANNEL_DESCRIPTION);
        personalMessages.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        return personalMessages;
    }
    // Following channel
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static NotificationChannel createFollowingNotifChannel() {
        following = new NotificationChannel(FOLLOWING_CHANNEL_ID, FOLLOWING_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        following.setDescription(FOLLOWING_CHANNEL_DESCRIPTION);
        following.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        return following;
    }
    // Groups channel
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static NotificationChannel createGroupsNotifChannel() {
        groups = new NotificationChannel(GROUPS_CHANNEL_ID, GROUPS_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        groups.setDescription(GROUPS_CHANNEL_DESCRIPTION);
        groups.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        return groups;
    }
    // Events channel
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static NotificationChannel createEventsNotifChannel() {
        events = new NotificationChannel(EVENTS_CHANNEL_ID, EVENTS_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        events.setDescription(EVENTS_CHANNEL_DESCRIPTION);
        events.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        return events;
    }
}

