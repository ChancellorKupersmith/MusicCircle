package com.example.musiccircle.Activities.Music_Player_Page;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Activities.Home_Page.DiscoveryHomePageActivity;
import com.example.musiccircle.Activities.Home_Page.NavMenuActivity;
import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.Entity.Comment;
import com.example.musiccircle.Entity.Group;
import com.example.musiccircle.Entity.Playlist;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.Entity.entities;
import com.example.musiccircle.Fragments.Comments.CommentsFragment;
import com.example.musiccircle.Fragments.Comments.PostCommentsFragment;
import com.example.musiccircle.Fragments.Music_Player.MusicPlayerFragment;
import com.example.musiccircle.Fragments.Music_Player.ShareWithFragment;
import com.example.musiccircle.Fragments.Music_Player.TrackListAudioFileFragment;
import com.example.musiccircle.Fragments.Music_Player.UserPlaylistsFragment;
import com.example.musiccircle.Fragments.Profile.ViewOtherProfile_fragment;
import com.example.musiccircle.R;
import com.example.musiccircle.Services.AudioPlayerService;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicPlayerActivity extends AppCompatActivity{
    //Variables
    private static final String USER_USERNAME_KEY = "user_username";
    private static final String USER_KEY = "user";
    private static final String AUDIO_FILES_URL =             "http://10.24.227.244:8080/audioFiles";
    private static final String LIKE_SONG_URL =               "http://10.24.227.244:8080/user/updateLikes/";
    private static final String GET_USER_PLAYLIST_URL =       "http://10.24.227.244:8080/user/playlist/";
    private static final String STREAM_AUDIO_URL =            "http://10.24.227.244:8080/audioFiles/downStream/";
    private static final String GET_USER_PLAYLISTS_URL =      "http://10.24.227.244:8080/user/playlists/";
    private static final String GET_USER_FOLLOWERS_URL =      "http://10.24.227.244:8080/user/followers/";
    private static final String GET_USER_GROUPS_URL =         "http://10.24.227.244:8080/user/groups/";
    private static final String GET_DOES_USER_LIKE_SONG_URL = "http://10.24.227.244:8080/user/doesLikeSong/";
    private static final String GET_COMMENTS_URL =            "http://10.24.227.244:8080/comments/by_song/";
    private static final String USER_UPLOADED_PLAYLIST_ID = "2";
    private static final String PLAY_MUSIC_CHANNEL_ID = "musicId";
    private static final int PLAY_MUSIC_NOTIFICATION_ID = 123;
    private static final String TRACKLIST_KEY = "track_list";
    private static final String PLAY_PAUSE_KEY = "play-pause";
    private static final String SET_SHUFFLE_KEY = "set-shuffle";
    private static final String FIRST_CREATE_KEY = "firstCreate";
    //exoplayer
    private ArrayList<AudioFile> trackList;
    private ArrayList<Playlist> playlists = new ArrayList<Playlist>();
    private PlayerView musicPlayerView;
    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private int currentBottomWindow;
    AudioFile song;
    ArrayList<String> followers = new ArrayList<String>();
    private boolean listen = true;
    //XML
    private Button artistLinkBtn;
    private com.github.clans.fab.FloatingActionButton likedFab, shareFab, addToPlaylistFab, viewTrackListFab, fab, commentsFabs;
    //android volley
    RequestQueue queue;
    private String user_username;
    private User user;
    private boolean liked, firstCreate;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        //Get Bundle Args
        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.getSerializable(USER_KEY);
        user_username = user.getUsername();
        firstCreate = bundle.getBoolean(FIRST_CREATE_KEY);
        trackList = (ArrayList<AudioFile>) bundle.getSerializable(TRACKLIST_KEY);
        song = trackList.get(0);
        liked = false;

        queue = Volley.newRequestQueue(getApplicationContext());

//        Intent intent = new Intent(this, AudioPlayerService.class);
//        Bundle serviceArgs = new Bundle();
//        serviceArgs.putSerializable(TRACKLIST_KEY, trackList);
//        intent.putExtras(serviceArgs);
//
        Intent updateTrackList = new Intent("update-track-list");
        updateTrackList.putExtra(TRACKLIST_KEY, trackList);
        LocalBroadcastManager.getInstance(this).sendBroadcast(updateTrackList);

        //Create MusicPLayer and Comments fragments
        Playlist createNewPlaylist = new Playlist("Create New Playlist", user_username);
        playlists.add(createNewPlaylist);
        // Inflate the layout for this fragment
        musicPlayerView = findViewById(R.id.musicPlayerGui);
//        initializePlayer();

        System.out.println("\n\nUsername: " + user_username);
        fab = findViewById(R.id.btn_Artist_Profile_Page);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohome = new Intent(getApplicationContext(), NavMenuActivity.class);
                Bundle b = new Bundle();
                b.putSerializable(TRACKLIST_KEY, trackList);
                b.putString("name", user_username);
                b.putSerializable(USER_KEY, user);
                gotohome.putExtras(b);
                startActivity(gotohome);
            }
        });

        artistLinkBtn = findViewById(R.id.btn_Song_Artist_Link);
        User Artist = trackList.get(0).getArtist();
        artistLinkBtn.setText(song.getArtist().getArtistName() + ": " +song.getSongName());
        artistLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToArtistProfile = new Intent(getApplicationContext(), ViewOtherProfile_fragment.class);
                startActivity(goToArtistProfile);
            }
        });

        commentsFabs = findViewById(R.id.fab_Comments);
        commentsFabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBottomWindow = 0;
                comments();
            }
        });

        likedFab = findViewById(R.id.fab_Like_Song);
        likedFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if user has liked song and like or unlike accordingly.
                getSongLikedBool(false);
                if(liked){
                    int duration = Toast.LENGTH_SHORT;
                    Toast unlike = Toast.makeText(getApplicationContext(), "UnLiked", duration);
                    unlike.show();
                    likedFab.setLabelText("Like");

                }
                else{
                    int duration = Toast.LENGTH_SHORT;
                    Toast like = Toast.makeText(getApplicationContext(), "Liked", duration);
                    like.show();
                    likedFab.setLabelText("UnLike");
                }
            }
        });

        shareFab = findViewById(R.id.fab_Share_Song);
        shareFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBottomWindow = 1;
                share();
            }
        });

        addToPlaylistFab = findViewById(R.id.fab_Add_To_Playlist);
        addToPlaylistFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBottomWindow = 2;
                getUsersPlaylists();
            }
        });

        viewTrackListFab = findViewById(R.id.fab_Track_List);
        viewTrackListFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBottomWindow = 3;
                viewTrackList();
            }
        });
        
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Comments_Placeholder, CommentsFragment.newInstance(song.getId(), user_username));
        ft.commit();
        currentBottomWindow = 0;
    }

    public void openArtistProfilePage(){

    }
    public void comments(){
//        ArrayList<Comment> commentList = new ArrayList<Comment>();
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_COMMENTS_URL + song.getId(),
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONArray json_response = new JSONArray(response);
//                            System.out.println(response);
//                            for (int i = 0; i < json_response.length(); i++) {
//                                JSONObject JSONComment = json_response.getJSONObject(i);
//                                Comment comment = new Comment();
//                                comment.setId(JSONComment.getLong("id"));
//                                comment.setAudioFileId(song.getId());
//                                JSONObject JSONUser = JSONComment.getJSONObject("commenter");
//                                comment.setCommenterId(JSONUser.getString("username"));
//                                comment.setText(JSONComment.getString("text"));
//                                commentList.add(comment);
//                            }
//                        } catch (JSONException e) {
//                            Log.e("ResponseJSONException", e.getMessage());
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        })
//        {
//            @Override
//            protected Map<String,String> getParams() {
//                Map<String,String> params = new HashMap<>();
//                //params.put("songId", songIdKey.toString());
//                return params;
//            }
//        };
//
//        queue.add(stringRequest);



        CommentsFragment commentsFragment = CommentsFragment.newInstance(trackList.get(player.getCurrentWindowIndex()).getId(), user_username);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Comments_Placeholder, commentsFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void getUserLikedPlaylist(){
        StringRequest getLikedPlaylist = new StringRequest(Request.Method.GET, GET_USER_PLAYLISTS_URL + user_username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        String g = response;
                        Log.d("LIKES", g);
                        try {
                            JSONObject JSONPlaylist = null;
                            JSONArray JSONPlaylists = new JSONArray(response);
                            for(int i=0; i<JSONPlaylists.length(); i++){
                                JSONPlaylist = JSONPlaylists.getJSONObject(i);
                                if(JSONPlaylist.getString("name") == "Liked Songs"){
                                    break;
                                }
                            }
                            System.out.println("Liked playlist name: " + JSONPlaylist.getString("name"));


                            JSONArray likedSongs = JSONPlaylist.getJSONArray("songs");
                            System.out.println("\n\nLikedSongs length: " + likedSongs.length());
                            for (int j = 0; j<likedSongs.length(); j++) {
                                JSONObject JSONsong = likedSongs.getJSONObject(j);
                                System.out.println("\n\nSong Object: " + JSONsong.toString());
                                System.out.println("\n\nSong id: " + String.valueOf(JSONsong.getLong("id")));
                                System.out.println("Current Song id: " + String.valueOf(song.getId()));
                                if(song.getId() == JSONsong.getLong("id")){
                                    liked = true;
                                }
                            }
                            like();
                        } catch (JSONException e) {
                            Log.e("ResponseJSONException", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("PARSE_LIKED_SONGS_ERROR", error.getMessage());
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
        queue.add(getLikedPlaylist);
    }

    public void getSongLikedBool(boolean updateLike){
        StringRequest doesUserLikeSong;
        if(updateLike){
            doesUserLikeSong = new StringRequest(Request.Method.GET, GET_DOES_USER_LIKE_SONG_URL + user_username + "/" + song.getId(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject JSONSongLikedBool = new JSONObject(response);
                                //System.out.println(response);
                                System.out.println("Song is liked: " + JSONSongLikedBool);
                                liked = JSONSongLikedBool.getBoolean("bool");
                            } catch (JSONException e) {
                                Log.e("ResponseJSONException", e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("GET_LIKED_BOOL_ERR", error.getMessage());
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
        }
        else{
            doesUserLikeSong = new StringRequest(Request.Method.GET, GET_DOES_USER_LIKE_SONG_URL + user_username + "/" + song.getId(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject JSONSongLikedBool = new JSONObject(response);
                                //System.out.println(response);
                                System.out.println("Song is liked: " + JSONSongLikedBool);
                                liked = JSONSongLikedBool.getBoolean("bool");
                                like();
                            } catch (JSONException e) {
                                Log.e("ResponseJSONException", e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("GET_LIKED_BOOL_ERR", error.getMessage());
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
        }

        queue.add(doesUserLikeSong);
    }

    public void like(){
//        queue = Volley.newRequestQueue(getApplicationContext());

        if(liked){
//            queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest sr = new StringRequest(Request.Method.PUT, LIKE_SONG_URL + user_username + "/" + song.getId() + "/" + "2",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            liked = false;

                            Log.e("HttpClient", "success! response: " + response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("HttpClient", "error: " + error.toString());
                        }
                    })
            {
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/x-www-form-urlencoded");
                    return params;
                }
            };
            queue.add(sr);
        }

        else{
//            queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest sr = new StringRequest(Request.Method.PUT, LIKE_SONG_URL + user_username + "/" + song.getId() + "/" + "1",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            liked = true;
                            Log.e("HttpClient", "success! response: " + response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("HttpClient", "error: " + error.toString());
                        }
                    })
            {
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/x-www-form-urlencoded");
                    return params;
                }
            };
            queue.add(sr);
        }
    }

    public void share(){
        getUserFollowers();
//        ShareWithFragment shareWithFragment = ShareWithFragment.newInstance(4, shareList, user, user_username, trackList.get(player.getCurrentWindowIndex()));
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.Comments_Placeholder, shareWithFragment);
//        ft.addToBackStack(null);
//        ft.commit();
    }

    public void getUserFollowers(){
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        ArrayList<entities> shareList = new ArrayList<entities>();
        StringRequest sr = new StringRequest(Request.Method.GET, GET_USER_FOLLOWERS_URL + user_username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray JSONFollowers = new JSONArray(response);
                            //Check if user followers list has changed
                            if(user.getFollower_usernames().size() != JSONFollowers.length()){
                                //Update user followers list
                                user.setFollower_usernames(new ArrayList<String>());
                                for(int i=0; i<JSONFollowers.length(); i++){
                                    user.getFollower_usernames().add(JSONFollowers.getString(i));
                                    User follower = new User();
                                    follower.setUsername(JSONFollowers.getString(i));
                                    shareList.add(follower);
                                }
                            }
                            //Add followers to shareList
                            else{
                                for(String follower_username : user.getFollower_usernames()){
                                    User follower = new User();
                                    follower.setUsername(follower_username);
                                    shareList.add(follower);
                                }
                            }
                            //Add user's groups to share list and open share fragment
                            getUserGroups(shareList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("HttpClient", "success! response: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    public void getUserGroups(ArrayList<entities> shareList){

        StringRequest sr = new StringRequest(Request.Method.GET, GET_USER_GROUPS_URL + user_username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray JSONGroups = new JSONArray(response);
                            //Check if user groups list has changed
                            if(user.getGroups() == null){
                                user.setGroups(new ArrayList<Group>());
                            }
                            if(user.getGroups().size() != JSONGroups.length()){
                                //Update user group list
                                user.setGroups(new ArrayList<Group>());
                                for(int i=0; i<JSONGroups.length(); i++){

                                    JSONObject JSONGroup = JSONGroups.getJSONObject(i);
                                    Group group = new Group();
                                    group.setId(JSONGroup.getLong("id"));
                                    group.setName(JSONGroup.getString("name"));
                                    JSONArray JSONGroup_members = JSONGroup.getJSONArray("members");
                                    ArrayList<String> group_members = new ArrayList<String>();
                                    for(int j=0; j<JSONGroup_members.length(); j++){
                                        group_members.add(JSONGroup_members.getString(i));
                                    }
                                    group.setMember_usernames(group_members);

                                    shareList.add(group);
                                }
                            }
                            //Add followers to shareList
                            else{
                                for(Group group : user.getGroups()){
                                    shareList.add(group);
                                }
                            }

                            if(shareList.size() > 0){
                                //Open share list in share with fragment
                                ShareWithFragment shareWithFragment = ShareWithFragment.newInstance(4, shareList, user, user_username, trackList.get(player.getCurrentWindowIndex()));
                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.Comments_Placeholder, shareWithFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                            else{
                                int duration = Toast.LENGTH_SHORT;
                                Toast emptyShareList = Toast.makeText(getApplicationContext(), "No Followers or Groups", duration);
                                emptyShareList.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("HttpClient", "success! response: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    public void getUsersPlaylists(){
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest getUserPlaylists = new StringRequest(Request.Method.GET, GET_USER_PLAYLISTS_URL + user_username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray json_response = new JSONArray(response);
                            //System.out.println(response);
                            System.out.println("PLAYLISTS SIZE OUT: " + playlists.size());
                            for (int i = playlists.size() - 1; i < json_response.length(); i++) {
                                JSONObject JSONplaylist = json_response.getJSONObject(i);
                                //System.out.println("\n\nPlaylist Object: " + JSONplaylist.toString());
                                Playlist playlist = new Playlist(JSONplaylist.getString("name"), user_username);
                                playlist.setId(JSONplaylist.getLong("id"));
                                JSONArray JSONsongs = JSONplaylist.getJSONArray("songs");
                                playlist.setLength(JSONsongs.length());
                                playlists.add(playlist);
                            }

                            addToPlaylist();
                        } catch (JSONException e) {
                            Log.e("ResponseJSONException", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("PARSE_USR_PLAYLIST_ERR", error.getMessage());
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
        queue.add(getUserPlaylists);
    }

    public void addToPlaylist(){
        if(playlists.size() > 2){
            playlists.get(0).length = playlists.size() - 1;

            UserPlaylistsFragment userPlaylistsFragment = UserPlaylistsFragment.newInstance(1, user_username, playlists, song);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.Comments_Placeholder, userPlaylistsFragment);
            ft.addToBackStack(null);
            ft.commit();
        }
        else{
            int duration = Toast.LENGTH_SHORT;
            Toast noplaylists = Toast.makeText(this, "we're grabbing your playlists\nplease press again", duration);
            noplaylists.show();
        }


    }

    public void viewTrackList(){
        TrackListAudioFileFragment trackListAudioFileFragment = TrackListAudioFileFragment.newInstance(1, trackList);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Comments_Placeholder, trackListAudioFileFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void startSilentMusicPlayer(){
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);
        trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd());
        player = new SimpleExoPlayer.Builder(this).setTrackSelector(trackSelector).build();
        for(AudioFile song : trackList){
            MediaItem mediaItem = MediaItem.fromUri(STREAM_AUDIO_URL + String.valueOf(song.getId()) );
            player.addMediaItem(mediaItem);
        }
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare();
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMusicPlayerServiceReciever, new IntentFilter("update-gui-player"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mTrackListReceiver, new IntentFilter("play-song-at"));
        Intent updateGuiIntent = new Intent("start-gui-player");
        LocalBroadcastManager.getInstance(this).sendBroadcast(updateGuiIntent);
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMusicPlayerServiceReciever, new IntentFilter("update-gui-player"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mTrackListReceiver, new IntentFilter("play-song-at"));
        Intent updateGuiIntent = new Intent("start-gui-player");
        LocalBroadcastManager.getInstance(this).sendBroadcast(updateGuiIntent);
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMusicPlayerServiceReciever);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mTrackListReceiver);
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMusicPlayerServiceReciever);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mTrackListReceiver);
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void initializePlayer() {
        if(player == null){
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);
            trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd());
            player = new SimpleExoPlayer.Builder(this).setTrackSelector(trackSelector).build();

            for(int i=0; i<trackList.size(); i++){
                String id = String.valueOf(trackList.get(i).getId());
                MediaItem mediaItem = MediaItem.fromUri(STREAM_AUDIO_URL + id);
                player.addMediaItem(mediaItem);
            }

            player.prepare();
            player.setVolume(0);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
            player.addListener(playerListener);

        }
        musicPlayerView.setPlayer(player);
        musicPlayerView.setShowShuffleButton(true);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        musicPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private static final String PLAYER_WINDOW_INDEX = "player-window-index";
    private static final String PLAYER_POSITION_KEY = "player-position";
    public void updateAudioService(){
        Intent intent = new Intent("update-service-player");
        intent.putExtra(PLAY_PAUSE_KEY, player.isPlaying());
        intent.putExtra(PLAYER_WINDOW_INDEX, player.getCurrentWindowIndex());
        intent.putExtra(PLAYER_POSITION_KEY, player.getCurrentPosition());
        intent.putExtra(SET_SHUFFLE_KEY, player.getShuffleModeEnabled());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private Player.EventListener playerListener = new Player.EventListener() {
        @Override
        public void onMediaItemTransition(MediaItem mediaItem, int reason) {
            // addPlay();
            //Update Music Player Screen Data
            if(reason == 1 || reason == 2 || reason == 3){
                song = trackList.get(player.getCurrentWindowIndex());
                artistLinkBtn.setText(song.getArtist().getArtistName() + ": " +song.getSongName());
                //Update like button text
                getSongLikedBool(true);
                if(liked){
                    likedFab.setLabelText("UnLike");
                }
                else{
                    likedFab.setLabelText("Like");
                }
                //Update bottom fragment window
                if(currentBottomWindow == 0){
                    comments();
                }
                else if(currentBottomWindow == 3){
                    viewTrackList();
                }
            }
        }

        @Override
        public void onEvents(Player player, Player.Events events){
//            song = trackList.get(player.getCurrentWindowIndex());
//            artistLinkBtn.setText(song.getArtist().getArtistName() + ": " +song.getSongName());
            if(listen){
                updateAudioService();
            }
        }
    };

    private BroadcastReceiver mMusicPlayerServiceReciever = new BroadcastReceiver(){
        boolean isPlaying, shuffleSongs;
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getExtras() != null){
                trackList = (ArrayList<AudioFile>) intent.getSerializableExtra(TRACKLIST_KEY);
                isPlaying = intent.getBooleanExtra(PLAY_PAUSE_KEY, player.isPlaying());
                currentWindow = intent.getIntExtra(PLAYER_WINDOW_INDEX, player.getCurrentWindowIndex());
                playbackPosition = intent.getLongExtra(PLAYER_POSITION_KEY, player.getCurrentPosition());
                shuffleSongs = intent.getBooleanExtra(SET_SHUFFLE_KEY, player.getShuffleModeEnabled());
                listen = false;
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
                listen = true;
            }
        }
    };
    private static final String PLAY_SONG_AT_INDEX_KEY = "play_song_at_index";
    private BroadcastReceiver mTrackListReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getExtras() != null){
                player.seekTo(intent.getIntExtra(PLAY_SONG_AT_INDEX_KEY, player.getCurrentWindowIndex()), 0);
            }
        }
    };
}