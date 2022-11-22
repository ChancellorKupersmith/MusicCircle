package com.example.musiccircle.Services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Activities.Home_Page.MainActivity;
import com.example.musiccircle.Activities.Music_Player_Page.MusicPlayerActivity;
import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.Fragments.Home.Home_fragment;
import com.example.musiccircle.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AudioPlayerService extends Service {
    private static final String PLAY_MUSIC_CHANNEL_ID = "musicId";
    private static final int PLAY_MUSIC_NOTIFICATION_ID = 123;
    private static final String PLAY_MUSIC_CHANNEL_NAME = "play music channel";
    private static final String TRACKLIST_KEY = "track_list";
    private static final String STREAM_AUDIO_URL = "http://10.24.227.244:8080/audioFiles/downStream/";
    private static final String ADD_TO_URL = "http://10.24.227.244:8080/audioFiles/downStream/";
    private static final String PLAY_PAUSE_KEY = "play-pause";
    private static final String NEXT_SONG_KEY = "next-song";
    private static final String PREV_SONG_KEY = "prev-song";
    private static final String PLAY_SONG_INDEX_KEY = "play-song-at";
    private static final String SET_SHUFFLE_KEY = "set-shuffle";
    private static final String SEEK_SONG_TO_KEY = "seek-to";
    private static final String PLAYER_WINDOW_INDEX = "player-window-index";
    private static final String PLAYER_POSITION_KEY = "player-position";
    private final IBinder binder = new LocalBinder();
    private SimpleExoPlayer player;
    private PlayerNotificationManager playerNotificationManager;
    private boolean playWhenReady = true, isPlaying, shuffleSongs;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private ArrayList<AudioFile> tracklist;
    private String user_username;

    @Override
    public void onCreate(){
        super.onCreate();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMusicPlayerGuiReceiver, new IntentFilter("update-service-player"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mStartMusicPlayerGuiReceiver, new IntentFilter("start-gui-player"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mUpdateTrackList, new IntentFilter("update-track-list"));
    }

    private BroadcastReceiver mUpdateTrackList = new BroadcastReceiver() {
        ArrayList<AudioFile> tempTrackList;
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getExtras() != null){
                tempTrackList = (ArrayList<AudioFile>) intent.getSerializableExtra(TRACKLIST_KEY);
            }
            if(tracklist.size() <= 0 && tempTrackList.size() > 0){
                tracklist = tempTrackList;
                initializePlayer();
                return;
            }
            else{
                for(int i=0; i<tempTrackList.size(); i++){
                    long d = tracklist.get(i).getId();
                    long c = tempTrackList.get(i).getId();
                    if(d != c){
                        System.out.println("tracklist audio id: " + tracklist.get(i).getId() + ", temp tracklist audio id: " + tempTrackList.get(i).getId());
                        tracklist = tempTrackList;
                        player.clearMediaItems();
                        initializePlayer();
                        return;
                    }
                }
            }

        }
    };
    //For updating Music player gui when its started
    private BroadcastReceiver mStartMusicPlayerGuiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateMusicPlayerGui();
        }
    };
    //For receiving Intents from Music player gui
    private BroadcastReceiver mMusicPlayerGuiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getExtras() != null){
                isPlaying = intent.getBooleanExtra(PLAY_PAUSE_KEY, player.isPlaying());
                currentWindow = intent.getIntExtra(PLAYER_WINDOW_INDEX, player.getCurrentWindowIndex());
                playbackPosition = intent.getLongExtra(PLAYER_POSITION_KEY, player.getCurrentPosition());
                shuffleSongs = intent.getBooleanExtra(SET_SHUFFLE_KEY, player.getShuffleModeEnabled());

                if(isPlaying != player.isPlaying()){
                    if(isPlaying == true){
                        player.play();
                    }
                    else {
                        player.pause();
                    }
                }
                player.seekTo(currentWindow, playbackPosition);
                player.setShuffleModeEnabled(shuffleSongs);
            }
        }
    };


    @Override
    public void onDestroy(){
        playerNotificationManager.setPlayer(null);
        player.release();
        player = null;
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMusicPlayerGuiReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mStartMusicPlayerGuiReceiver);
        super.onDestroy();
    }
    @Override
    public int onStartCommand(@NotNull Intent intent, int flags, int startId){
        tracklist = (ArrayList<AudioFile>) (intent.getSerializableExtra(TRACKLIST_KEY));
        user_username = intent.getStringExtra("username");
        final Context context = this;
        initializePlayer();
        return START_STICKY;
    }

    static final int GET_SERVICE = 1;


    public void initializePlayer(){
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);
        trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd());
        player = new SimpleExoPlayer.Builder(this).setTrackSelector(trackSelector).build();
        for(AudioFile song : tracklist){
            MediaItem mediaItem = MediaItem.fromUri(STREAM_AUDIO_URL + String.valueOf(song.getId()) );
            player.addMediaItem(mediaItem);
        }
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare();
        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(
                this, PLAY_MUSIC_CHANNEL_ID, R.string.play_music_channel_name,
                PLAY_MUSIC_NOTIFICATION_ID,
                new PlayerNotificationManager.MediaDescriptionAdapter() {
                    @Override
                    public CharSequence getCurrentContentTitle(Player player) {
                        if(tracklist.size() > 0){

                            return tracklist.get(player.getCurrentWindowIndex()).getSongName();
                        }
                        else{
                            return "Not Playing";
                        }
                    }

                    @Nullable
                    @Override
                    public PendingIntent createCurrentContentIntent(Player player) {
                        Intent intent = new Intent(getApplicationContext(), Home_fragment.class);
                        return PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    }

                    @Nullable
                    @Override
                    public CharSequence getCurrentContentText(Player player) {
                        CharSequence description = "";
                        if(tracklist.size() > 0){
                            try{
                                AudioFile song = tracklist.get(player.getCurrentWindowIndex());
                                User artist = song.getArtist();
                                if(song.getAlbum() != null){
                                    description = song.getAlbum().getAlbumName() + ", " + artist.getArtistName();
                                }
                                else{
                                    description = artist.getArtistName();
                                }
                            }catch (Exception e){
                                Log.e("AUDIO_NOTIFY_ERROR", e.getStackTrace().toString());
                            }
                        }
                        return description;
                    }

                    @Nullable
                    @Override
                    public Bitmap getCurrentLargeIcon(Player player, PlayerNotificationManager.BitmapCallback callback) {
                        return null;
                    }
                }, new PlayerNotificationManager.NotificationListener(){
                    @Override
                    public void onNotificationPosted(int notificationId, Notification notification, boolean ongoing) {
                        startForeground(notificationId, notification);
                    }

                    @Override
                    public void onNotificationCancelled(int notificationId, boolean dismissedByUser) {
                        stopSelf();
                    }


                }
        );
        playerNotificationManager.setPlayer(player);
        playerNotificationManager.setUseNextAction(true);
        playerNotificationManager.setUsePlayPauseActions(true);
        playerNotificationManager.setUsePreviousAction(true);
        playerNotificationManager.setColorized(true);
        playerNotificationManager.setPriority(NotificationCompat.PRIORITY_HIGH);
        playerNotificationManager.setSmallIcon(R.drawable.exo_notification_small_icon);

//        playerNotificationManager.setLargeIcon(R.drawable.exo_artwork);
    }

    public class LocalBinder extends Binder {
        public AudioPlayerService getService(){
            return AudioPlayerService.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if(intent.getExtras() != null){
            tracklist = (ArrayList<AudioFile>) (intent.getSerializableExtra(TRACKLIST_KEY));
        }
//        Messenger messenger = new Messenger(new IncomingHandler(this));
//
//        return messenger.getBinder();
        return binder;
    }

    private Notification buildAudioServiceNotification(Player player){
        CharSequence description = "";
        try{
            AudioFile song = tracklist.get(player.getCurrentWindowIndex());
            User artist = song.getArtist();
            if(song.getAlbum() != null){
                description = song.getAlbum().getAlbumName() + ", " + artist.getFirstName() + " " + artist.getLastName();
            }
            else{
                description = artist.getFirstName() + " " + artist.getLastName();
            }
        }catch (Exception e){
            Log.e("AUDIO_NOTIFY_ERROR", e.getStackTrace().toString());
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, PLAY_MUSIC_CHANNEL_ID);
        builder.setContentTitle(tracklist.get(player.getCurrentWindowIndex()).getSongName())
                .setContentText(description)
                .setSmallIcon(R.drawable.exo_notification_small_icon);
        return (builder.build());
    }
    public SimpleExoPlayer getPlayer(){
        return player;
    }

    public void updateMusicPlayerGui(){
        Intent intent = new Intent("update-gui-player");
        intent.putExtra(PLAY_PAUSE_KEY, player.isPlaying());
        intent.putExtra(PLAYER_WINDOW_INDEX, player.getCurrentWindowIndex());
        intent.putExtra(PLAYER_POSITION_KEY, player.getCurrentPosition());
        intent.putExtra(SET_SHUFFLE_KEY, player.getShuffleModeEnabled());
        intent.putExtra(TRACKLIST_KEY, tracklist);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private Player.EventListener playerListener = new Player.EventListener() {
        @Override
        public void onMediaItemTransition(MediaItem mediaItem, int reason) {
           // addPlay();
            if(reason == 0 || reason == 1 || reason == 2){
                addPlay();
            }
        }
    };

    private void addPlay(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest AddSongPlay = new StringRequest(Request.Method.PUT, "http://10.24.227.244:8080/audioFiles/addPlay/" + user_username + "/" +tracklist.get(player.getCurrentWindowIndex()).getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        return;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ADD_SONG_PLAY_ERROR", error.getMessage());
            }
        })
        {
            @Override
            protected Map<String,String> getParams() {
                Map<String,String> params = new HashMap<>();
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(AddSongPlay);
    }
}
