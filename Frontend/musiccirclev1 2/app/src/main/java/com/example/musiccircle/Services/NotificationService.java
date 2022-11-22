package com.example.musiccircle.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.example.musiccircle.R;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.musiccircle.Entity.User;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicBoolean;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class NotificationService extends Service{
//
    // Strings for creating notification channels
    public static final String USERLIKES_CHANNEL_ID = "userLikes";
    public static final CharSequence USERLIKES_CHANNEL_NAME = "New Likes";
    public static final String USERLIKES_CHANNEL_DESCRIPTION = "";

    public static final String USERCOMMENTS_CHANNEL_ID = "userComments";
    public static final CharSequence USERCOMMENTS_CHANNEL_NAME = "New Comments";
    public static final String USERCOMMENTS_CHANNEL_DESCRIPTION = "";

    public static final String USERFOLLOWS_CHANNEL_ID = "userFollows";
    public static final CharSequence USERFOLLOWS_CHANNEL_NAME = "New Follows";
    public static final String USERFOLLOWS_CHANNEL_DESCRIPTION = "";

    public static final String USERSHARES_CHANNEL_ID = "userShares";
    public static final CharSequence USERSHARES_CHANNEL_NAME = "New Shares";
    public static final String USERSHARES_CHANNEL_DESCRIPTION = "";

    public static final String FOLLOWINGNEWPOST_CHANNEL_ID = "followingNewPost";
    public static final CharSequence FOLLOWINGNEWPOST_CHANNEL_NAME = "Following Makes New Post";
    public static final String FOLLOWINGNEWPOST_CHANNEL_DESCRIPTION = "";

    public static final String FOLLOWINGATEVENT_CHANNEL_ID = "followingAtEvent";
    public static final CharSequence FOLLOWINGATEVENT_CHANNEL_NAME = "Following At An Event";
    public static final String FOLLOWINGATEVENT_CHANNEL_DESCRIPTION = "";

    public static final String GROUPINVITES_CHANNEL_ID = "groupInvites";
    public static final CharSequence GROUPINVITES_CHANNEL_NAME = "Group Invites";
    public static final String GROUPINVITES_CHANNEL_DESCRIPTION = "";

    public static final String GROUPSHARES_CHANNEL_ID = "groupShares";
    public static final CharSequence GROUPSHARES_CHANNEL_NAME = "Group Shares";
    public static final String GROUPSHARES_CHANNEL_DESCRIPTION = "";

    public static final String EVENTSOON_CHANNEL_ID = "eventSoon";
    public static final CharSequence EVENTSOON_CHANNEL_NAME = "Event Coming Soon";
    public static final String EVENTSOON_CHANNEL_DESCRIPTION = "";


    public static final String USER_KEY= "user";
    public static final String USER_USERNAME_KEY = "user_username";
    public static final String NOTIFICATION_STOMP_ENDPOINT = "10.24.227.244:8080/notifications-ws";
    
    // Notification channels, one for each notification type
    public static NotificationChannel userLikes;
    public static NotificationChannel userComments;
    public static NotificationChannel userFollows;
    public static NotificationChannel userShares;
    public static NotificationChannel followingNewPost;
    public static NotificationChannel followingAtEvent;
    public static NotificationChannel groupInvites;
    public static NotificationChannel groupShares;
    public static NotificationChannel eventSoon;
    
    private String notificationTitle;
    private String notificationText;
    private User user;
    private String username;
    private String username_to_notify;
    private String username_from;
    private Notification notification;
    private StompClient stompClient;
    private NotificationManagerCompat notificationManager;

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
     * - Direct messages (not currently set up)
     *
     * Following:
     * - New songs/albums from users you follow
     * - New events they are playing at
     *
     * Groups:
     * - New group invites
     * - Song/Album/Playlist shares
     * - New group message (not currently set up)
     *
     * Events:
     * - Event you signed up for is 12 or 24 hours away
     * - 50% of the people you follow are attending an event (not currently set up)
     */


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(){
        super.onCreate();
        //Connect to websocket
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://" + NOTIFICATION_STOMP_ENDPOINT + "/websocket");
        stompClient.connect();
        StompUtils.lifecycle(stompClient);
    }
    public class LocalBinder extends Binder {
        public NotificationService getService(){
            return NotificationService.this;
        }
    }
    //Starts Service
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println();
        if(intent.getExtras() != null){
            this.user = (User) intent.getExtras().getSerializable(USER_KEY);
            this.username = intent.getExtras().getString(USER_USERNAME_KEY);
            startListening();
        }
        return new LocalBinder();
    }
    //Cancels service
    @Override
    public void onDestroy(){
        //Notify server that user is offline
        //then destroy.
        stompClient.send("/notify/userOffline" + username).subscribe();
        stompClient.disconnect();
        super.onDestroy();
    }

    //Setters
    public void setNotificationText(String text){
        notificationText = text;
    }
    public void setNotificationTitle(String title){
        notificationTitle = title;
    }
    public void setUsername_to_notify(String username_to_notify){
        this.username_to_notify = username_to_notify;
    }
    public void setUsername_from(String username_from) {
        this.username_from = username_from;
    }
    //for accessing service outside in other classes


    //Starts listening to notifications
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startListening(){
        stompClient.topic("/notifications/user/" + user.getUsername()).subscribe(topicMessage ->{
            System.out.println("Notification Response: " + topicMessage.getPayload());
            JSONObject JSONNotification = new JSONObject(topicMessage.getPayload());
            try{
                System.out.println("response msg: " + JSONNotification.toString());
                notificationTitle = JSONNotification.getString("title");
                notificationText = JSONNotification.getString("text");
                username_from = JSONNotification.getString("user_from_username");
                username_to_notify = user.getUsername();
                this.createNotification_and_send(JSONNotification.getString("channel_id"));

                System.out.println(JSONNotification);
                Log.e("JSON OBJS", JSONNotification.toString());

            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }


    // Create notification and notify user
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotification_and_send(String notification_channel_id){

        if(notification_channel_id.trim() == USERLIKES_CHANNEL_ID){
            System.out.println("USER LIKES NOTIFICATION");
            notificationManager = NotificationManagerCompat.from(this);
            notificationManager.createNotificationChannel(createUserLikesNotifChannel());
            NotificationCompat.Builder builder= new NotificationCompat.Builder(this, USERLIKES_CHANNEL_ID);
            builder
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationText)
                    .setSmallIcon(R.drawable.ic_userlikes_notify)
//                    .setColorized(true)
//                    .setColor(getResources().getColor(R.color.holo_blue))
                    .setPriority(NotificationManager.IMPORTANCE_HIGH);
            notificationManager.notify(1, builder.build());
        }
        else if(notification_channel_id.trim() == USERCOMMENTS_CHANNEL_ID){
            notificationManager = NotificationManagerCompat.from(this);
            notificationManager.createNotificationChannel(createUserCommentsNotifChannel());
            NotificationCompat.Builder builder= new NotificationCompat.Builder(this, USERCOMMENTS_CHANNEL_ID);
            builder
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationText)
                    .setSmallIcon(R.drawable.ic_usercomments_notify)

                    .setPriority(NotificationManager.IMPORTANCE_HIGH);
            notificationManager.notify(1, builder.build());
        }
        else if(notification_channel_id.trim() == USERFOLLOWS_CHANNEL_ID){
            notificationManager = NotificationManagerCompat.from(this);
            notificationManager.createNotificationChannel(createUserFollowsNotifChannel());
            NotificationCompat.Builder builder= new NotificationCompat.Builder(this, USERFOLLOWS_CHANNEL_ID);
            builder
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationText)
                    .setSmallIcon(R.drawable.ic_userfollows_notify)
//                    .setColorized(true)
//                    .setColor(getResources().getColor(R.color.holo_blue))
                    .setPriority(NotificationManager.IMPORTANCE_HIGH);
            notificationManager.notify(1, builder.build());
        }
        else if(notification_channel_id.trim() == USERSHARES_CHANNEL_ID){
            notificationManager = NotificationManagerCompat.from(this);
            notificationManager.createNotificationChannel(createUserSharesNotifChannel());
            NotificationCompat.Builder builder= new NotificationCompat.Builder(this, USERSHARES_CHANNEL_ID);
            builder
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationText)
                    .setSmallIcon(R.drawable.ic_usershares_notify)
//                    .setColorized(true)
//                    .setColor(getResources().getColor(R.color.holo_blue))
                    .setPriority(NotificationManager.IMPORTANCE_HIGH);
            notificationManager.notify(1, builder.build());
        }
        else if(notification_channel_id.trim() == FOLLOWINGNEWPOST_CHANNEL_ID){
            notificationManager = NotificationManagerCompat.from(this);
            notificationManager.createNotificationChannel(createFollowingNewPostNotifChannel());
            NotificationCompat.Builder builder= new NotificationCompat.Builder(this, FOLLOWINGNEWPOST_CHANNEL_ID);
            builder
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationText)
                    .setSmallIcon(R.drawable.ic_followingnewpost_notify)
//                    .setColorized(true)
//                    .setColor(getResources().getColor(R.color.holo_blue))
                    .setPriority(NotificationManager.IMPORTANCE_HIGH);
            notificationManager.notify(1, builder.build());
        }
        else if(notification_channel_id.trim() == FOLLOWINGATEVENT_CHANNEL_ID){
            notificationManager = NotificationManagerCompat.from(this);
            notificationManager.createNotificationChannel(createFollowingAtEventNotifChannel());
            NotificationCompat.Builder builder= new NotificationCompat.Builder(this, FOLLOWINGATEVENT_CHANNEL_ID);
            builder
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationText)
                    .setSmallIcon(R.drawable.ic_followingatevent_notify)
//                    .setColorized(true)
//                    .setColor(getResources().getColor(R.color.holo_blue))
                    .setPriority(NotificationManager.IMPORTANCE_HIGH);
            notificationManager.notify(1, builder.build());
        }
        else if(notification_channel_id.trim() == GROUPINVITES_CHANNEL_ID){
            notificationManager = NotificationManagerCompat.from(this);
            notificationManager.createNotificationChannel(createGroupInvitesNotifChannel());
            NotificationCompat.Builder builder= new NotificationCompat.Builder(this, GROUPINVITES_CHANNEL_ID);
            builder
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationText)
                    .setSmallIcon(R.drawable.ic_groupinvites_notify)
//                    .setColorized(true)
//                    .setColor(getResources().getColor(R.color.holo_blue))
                    .setPriority(NotificationManager.IMPORTANCE_HIGH);
            notificationManager.notify(1, builder.build());
        }
        else if(notification_channel_id.trim() == GROUPSHARES_CHANNEL_ID){
            notificationManager = NotificationManagerCompat.from(this);
            notificationManager.createNotificationChannel(createGroupSharesNotifChannel());
            NotificationCompat.Builder builder= new NotificationCompat.Builder(this, GROUPSHARES_CHANNEL_ID);
            builder
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationText)
                    .setSmallIcon(R.drawable.ic_groupshares_notify)
//                    .setColorized(true)
//                    .setColor(getResources().getColor(R.color.holo_blue))
                    .setPriority(NotificationManager.IMPORTANCE_HIGH);
            notificationManager.notify(1, builder.build());
        }
        else{
            notificationManager = NotificationManagerCompat.from(this);
            notificationManager.createNotificationChannel(createEventSoonNotifChannel());
            NotificationCompat.Builder builder= new NotificationCompat.Builder(this, EVENTSOON_CHANNEL_ID);
            builder
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationText)
                    .setSmallIcon(R.drawable.ic_eventsoon_notify)
//                    .setColorized(true)
//                    .setColor(getResources().getColor(R.color.holo_blue))
                    .setPriority(NotificationManager.IMPORTANCE_HIGH);
            notificationManager.notify(1, builder.build());
        }

    }

    //Send Notification
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendNotification(String notificationChannelId){
        System.out.println("Sending notification: " + notificationChannelId);
        try{
            if(notificationChannelId.trim() == USERLIKES_CHANNEL_ID){
                JSONObject JSONNotification = new JSONObject();
                JSONNotification.put("title", notificationTitle);
                JSONNotification.put("text", notificationText);
                JSONNotification.put("channel_id", USERLIKES_CHANNEL_ID);
                JSONNotification.put("user_from_username", username_from);
                JSONNotification.put("user_to_username", username_to_notify);
                stompClient.send("/notify/to_user", JSONNotification.toString()).subscribe();
            }
            if(notificationChannelId.trim() == USERCOMMENTS_CHANNEL_ID){
                JSONObject JSONNotification = new JSONObject();
                JSONNotification.put("title", notificationTitle);
                JSONNotification.put("text", notificationText);
                JSONNotification.put("channel_id", USERCOMMENTS_CHANNEL_ID);
                JSONNotification.put("user_from_username", username_from);
                JSONNotification.put("user_to_username", username_to_notify);
                stompClient.send("/notify/to_user", JSONNotification.toString()).subscribe();
            }
            if(notificationChannelId.trim() == USERFOLLOWS_CHANNEL_ID){
                JSONObject JSONNotification = new JSONObject();
                JSONNotification.put("title", notificationTitle);
                JSONNotification.put("text", notificationText);
                JSONNotification.put("channel_id", USERFOLLOWS_CHANNEL_ID);
                JSONNotification.put("user_from_username", username_from);
                JSONNotification.put("user_to_username", username_to_notify);
                stompClient.send("/notify/to_user", JSONNotification.toString()).subscribe();
            }
            if(notificationChannelId.trim() == USERSHARES_CHANNEL_ID){
                JSONObject JSONNotification = new JSONObject();
                JSONNotification.put("title", notificationTitle);
                JSONNotification.put("text", notificationText);
                JSONNotification.put("channel_id", USERSHARES_CHANNEL_ID);
                JSONNotification.put("user_from_username", username_from);
                JSONNotification.put("user_to_username", username_to_notify);
                stompClient.send("/notify/to_user", JSONNotification.toString()).subscribe();
            }
            if(notificationChannelId.trim() == FOLLOWINGNEWPOST_CHANNEL_ID){
                JSONObject JSONNotification = new JSONObject();
                JSONNotification.put("title", notificationTitle);
                JSONNotification.put("text", notificationText);
                JSONNotification.put("channel_id", FOLLOWINGNEWPOST_CHANNEL_ID);
                JSONNotification.put("user_from_username", username_from);
                JSONNotification.put("user_to_username", username_to_notify);
                stompClient.send("/notify/to_followers", JSONNotification.toString()).subscribe();
            }
            if(notificationChannelId.trim() == FOLLOWINGATEVENT_CHANNEL_ID){
                JSONObject JSONNotification = new JSONObject();
                JSONNotification.put("title", notificationTitle);
                JSONNotification.put("text", notificationText);
                JSONNotification.put("channel_id", FOLLOWINGATEVENT_CHANNEL_ID);
                JSONNotification.put("user_from_username", username_from);
                JSONNotification.put("user_to_username", username_to_notify);
                stompClient.send("/notify/to_followers", JSONNotification.toString()).subscribe();
            }
            if(notificationChannelId.trim() == GROUPINVITES_CHANNEL_ID){
                JSONObject JSONNotification = new JSONObject();
                JSONNotification.put("title", notificationTitle);
                JSONNotification.put("text", notificationText);
                JSONNotification.put("channel_id", GROUPINVITES_CHANNEL_ID);
                JSONNotification.put("user_from_username", username_from);
                JSONNotification.put("user_to_username", username_to_notify);
                stompClient.send("/notify/to_user", JSONNotification.toString()).subscribe();
            }
            if(notificationChannelId.trim() == GROUPSHARES_CHANNEL_ID){
                JSONObject JSONNotification = new JSONObject();
                JSONNotification.put("title", notificationTitle);
                JSONNotification.put("text", notificationText);
                JSONNotification.put("channel_id", GROUPSHARES_CHANNEL_ID);
                JSONNotification.put("user_from_username", username_from);
                JSONNotification.put("user_to_username", username_to_notify);
                stompClient.send("/notify/to_followers", JSONNotification.toString()).subscribe();
            }
            if(notificationChannelId.trim() == EVENTSOON_CHANNEL_ID){
                JSONObject JSONNotification = new JSONObject();
                JSONNotification.put("title", notificationTitle);
                JSONNotification.put("text", notificationText);
                JSONNotification.put("channel_id", EVENTSOON_CHANNEL_ID);
                JSONNotification.put("user_from_username", username_from);
                JSONNotification.put("user_to_username", username_to_notify);
                stompClient.send("/notify/to_followers", JSONNotification.toString()).subscribe();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    // Static methods for creating each type of notification channel

    // User Likes channel
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static NotificationChannel createUserLikesNotifChannel() {
        userLikes = new NotificationChannel(USERLIKES_CHANNEL_ID, USERLIKES_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        userLikes.setDescription(USERLIKES_CHANNEL_DESCRIPTION);
        userLikes.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        return userLikes;
    }
    // User Comments channel
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static NotificationChannel createUserCommentsNotifChannel() {
        userComments = new NotificationChannel(USERCOMMENTS_CHANNEL_ID, USERCOMMENTS_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        userComments.setDescription(USERCOMMENTS_CHANNEL_DESCRIPTION);
        userComments.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        return userComments;
    }
    // User Follows channel
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static NotificationChannel createUserFollowsNotifChannel() {
        userFollows = new NotificationChannel(USERFOLLOWS_CHANNEL_ID, USERFOLLOWS_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        userFollows.setDescription(USERFOLLOWS_CHANNEL_DESCRIPTION);
        userFollows.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        return userFollows;
    }
    // User Shares channel
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static NotificationChannel createUserSharesNotifChannel() {
        userShares = new NotificationChannel(USERSHARES_CHANNEL_ID, USERSHARES_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        userShares.setDescription(USERSHARES_CHANNEL_DESCRIPTION);
        userShares.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        return userShares;
    }
    // Following New Post channel
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static NotificationChannel createFollowingNewPostNotifChannel() {
        followingNewPost = new NotificationChannel(FOLLOWINGNEWPOST_CHANNEL_ID, FOLLOWINGNEWPOST_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        followingNewPost.setDescription(FOLLOWINGNEWPOST_CHANNEL_DESCRIPTION);
        followingNewPost.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        return followingNewPost;
    }
    // Following At Event channel
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static NotificationChannel createFollowingAtEventNotifChannel() {
        followingAtEvent = new NotificationChannel(FOLLOWINGATEVENT_CHANNEL_ID, FOLLOWINGATEVENT_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        followingAtEvent.setDescription(FOLLOWINGATEVENT_CHANNEL_DESCRIPTION);
        followingAtEvent.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        return followingAtEvent;
    }
    // Group Invites channel
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static NotificationChannel createGroupInvitesNotifChannel() {
        groupInvites = new NotificationChannel(GROUPINVITES_CHANNEL_ID, GROUPINVITES_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        groupInvites.setDescription(GROUPINVITES_CHANNEL_DESCRIPTION);
        groupInvites.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        return groupInvites;
    }
    // Group Shares channel
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static NotificationChannel createGroupSharesNotifChannel() {
        groupShares = new NotificationChannel(GROUPSHARES_CHANNEL_ID, GROUPSHARES_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        groupShares.setDescription(GROUPSHARES_CHANNEL_DESCRIPTION);
        groupShares.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        return groupShares;
    }
    // Event Soon channel
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static NotificationChannel createEventSoonNotifChannel() {
        eventSoon = new NotificationChannel(EVENTSOON_CHANNEL_ID, EVENTSOON_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        eventSoon.setDescription(EVENTSOON_CHANNEL_DESCRIPTION);
        eventSoon.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        return eventSoon;
    }

}
