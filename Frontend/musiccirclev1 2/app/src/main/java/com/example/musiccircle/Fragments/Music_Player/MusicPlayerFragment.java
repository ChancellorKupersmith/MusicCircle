package com.example.musiccircle.Fragments.Music_Player;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Activities.Home_Page.DiscoveryHomePageActivity;
import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.Entity.Comment;
import com.example.musiccircle.Entity.Playlist;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.Fragments.Comments.CommentsFragment;
import com.example.musiccircle.Fragments.Profile.ViewOtherProfile_fragment;
import com.example.musiccircle.R;
import com.example.musiccircle.Services.AudioPlayerService;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MusicPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicPlayerFragment extends Fragment {
    //Variables
    private static final String USER_USERNAME_KEY = "user_username";
    private static final String TRACK_LIST_KEY = "track_list";
    private static final String AUDIO_FILES_URL =             "http://10.24.227.244:8080/audioFiles";
    private static final String GET_USER_LIKED_PLAYLIST_URL = "http://10.24.227.244:8080/user/playlist/";
    private static final String STREAM_AUDIO_URL =            "http://10.24.227.244:8080/audioFiles/downStream/";
    private static final String GET_USER_PLAYLISTS_URL = "http://10.24.227.244:8080/user/playlists/";
    private static final String USER_UPLOADED_PLAYLIST_ID = "2";
    //exoplayer
    private ArrayList<AudioFile> trackList;
    ArrayList<Playlist> playlists = new ArrayList<Playlist>();
    private PlayerView musicPlayerView;
    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    AudioFile song;
    //XML
    private Button artistLinkBtn;
    private com.github.clans.fab.FloatingActionButton likedFab, shareFab, addToPlaylistFab, viewTrackListFab, fab, commentsFabs;
    //android volley
    private String user_username;
    private boolean liked = false;


    public MusicPlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user_username User's username.
     * @param trackList List of songs to play
     * @return A new instance of fragment MusicPlayerFragment.
     */
    public static MusicPlayerFragment newInstance(String user_username, ArrayList<AudioFile> trackList) {
        MusicPlayerFragment fragment = new MusicPlayerFragment();
        Bundle args = new Bundle();
        args.putString(USER_USERNAME_KEY, user_username);
        args.putSerializable(TRACK_LIST_KEY, trackList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user_username = getArguments().getString(USER_USERNAME_KEY);
            trackList = (ArrayList<AudioFile>) getArguments().getSerializable(TRACK_LIST_KEY);
            Bundle serviceBundle = new Bundle();
            serviceBundle.putSerializable(TRACK_LIST_KEY, trackList);
            Intent intent = new Intent(getContext(), AudioPlayerService.class);
            intent.putExtras(serviceBundle);
//            Util.startForegroundService(getContext(), intent);
            getContext().bindService(intent, musicPlayerConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Playlist createNewPlaylist = new Playlist("Create New Playlist", user_username);
        playlists.add(createNewPlaylist);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music_player, container, false);
        musicPlayerView = view.findViewById(R.id.musicPlayerGui);
        System.out.println("\n\nUsername: " + user_username);
        fab = view.findViewById(R.id.btn_Artist_Profile_Page);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodiscovery = new Intent(view.getContext(), DiscoveryHomePageActivity.class);
                startActivity(gotodiscovery);
            }
        });
        artistLinkBtn = view.findViewById(R.id.btn_Song_Artist_Link);

//        for(int j=0; j<trackList.size(); j++){
//            System.out.println("Index: " + j + ", Song: " + trackList.get(j).getSongName() + ", Artist: " + trackList.get(j).getArtist().getFirstName());
//        }

        User Artist = trackList.get(0).getArtist();
        artistLinkBtn.setText(Artist.getFirstName() +  " " + Artist.getLastName());

        artistLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToArtistProfile = new Intent(getContext(), ViewOtherProfile_fragment.class);
                startActivity(goToArtistProfile);
            }
        });

        commentsFabs = view.findViewById(R.id.fab_Comments);
        commentsFabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comments();
            }
        });
        likedFab = view.findViewById(R.id.fab_Like_Song);
        likedFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like();
            }
        });

        shareFab = view.findViewById(R.id.fab_Share_Song);
        shareFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        addToPlaylistFab = view.findViewById(R.id.fab_Add_To_Playlist);
        addToPlaylistFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsersPlaylists();
                addToPlaylist();
            }
        });

        viewTrackListFab = view.findViewById(R.id.fab_Track_List);
        viewTrackListFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewTrackList();
            }
        });

        return view;
    }

    public void openArtistProfilePage(){

    }
    public void comments(){
        CommentsFragment commentsFragment = CommentsFragment.newInstance(trackList.get(audioPlayerService.getPlayer().getCurrentWindowIndex()).getId(), user_username);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.Comments_Placeholder, commentsFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
    public void like(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest getLikedPlaylist = new StringRequest(Request.Method.GET, GET_USER_LIKED_PLAYLIST_URL + user_username +"/" + "1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json_response = new JSONObject(response);
                            System.out.println(response);
                            JSONArray likedSongs = json_response.getJSONArray("songs");
                            System.out.println("\n\nLikedSongs length: " + likedSongs.length());
                            for (int i = 0; i < likedSongs.length(); i++) {
                                JSONObject JSONsong = likedSongs.getJSONObject(i);
                                System.out.println("\n\nSong Object: " + JSONsong.toString());
                                System.out.println("\n\nSong id: " + String.valueOf(JSONsong.getLong("id")));
                                System.out.println("Current Song id: " + String.valueOf(song.getId()));
                                if(song.getId() == JSONsong.getLong("id")){
                                    liked = true;
                                }
                            }
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
                params.put("playlistId", "2");
                params.put("username", "chancek1");
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
        if(liked){
            queue = Volley.newRequestQueue(getContext());
            StringRequest sr = new StringRequest(Request.Method.PUT, AUDIO_FILES_URL+"/unlike",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("Decreased Like Successfully to: " + song.getSongName() )){
                                int duration = Toast.LENGTH_SHORT;
                                Toast unlike = Toast.makeText(getContext(), "UnLiked", duration);
                                unlike.show();
                                likedFab.setLabelText("Like");
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
                    params.put("username", user_username);
                    params.put("id", String.valueOf(song.getId()) );
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
            queue = Volley.newRequestQueue(getContext());
            StringRequest sr = new StringRequest(Request.Method.PUT, AUDIO_FILES_URL+"/like",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("Decreased Like Successfully to: " + song.getSongName() )){
                                int duration = Toast.LENGTH_SHORT;
                                Toast like = Toast.makeText(getContext(), "Liked", duration);
                                like.show();
                                likedFab.setLabelText("UnLike");
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
                    params.put("username", user_username);
                    params.put("id", String.valueOf(song.getId()) );
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

    }
    public void addToPlaylist(){
            if(playlists.size() > 2){
                playlists.get(0).length = playlists.size() - 1;

                UserPlaylistsFragment userPlaylistsFragment = UserPlaylistsFragment.newInstance(1, user_username, playlists, song);
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.Comments_Placeholder, userPlaylistsFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
            else{
                int duration = Toast.LENGTH_SHORT;
                Toast noplaylists = Toast.makeText(getContext(), "we're grabbing your playlists\nplease press again", duration);
                noplaylists.show();
            }


    }

    public void getUsersPlaylists(){
        RequestQueue queue = Volley.newRequestQueue(super.getContext());
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
                                JSONArray JSONsongs = JSONplaylist.getJSONArray("songs");
                                playlist.setLength(JSONsongs.length());
                                playlists.add(playlist);
                            }
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
    public void viewTrackList(){
        TrackListAudioFileFragment trackListAudioFileFragment = TrackListAudioFileFragment.newInstance(1, trackList);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.Comments_Placeholder, trackListAudioFileFragment);
        ft.addToBackStack(null);
        ft.commit();

    }
    public void testStreamMusic(){
        byte [] mp3contents = {}; //replace with byte array from volley request
        try{
            String  mp3contents_string = new String(mp3contents, "UTF-8");
            Uri mp3Uri = Uri.parse(mp3contents_string);
        }catch (Exception e){

        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        Intent intent = new Intent(getContext(), AudioPlayerService.class);
//        Bundle serviceBundle = new Bundle();
//        serviceBundle.putSerializable(TRACK_LIST_KEY, trackList);
//        intent.putExtras(serviceBundle);
//        getContext().bindService(intent, musicPlayerConnection, Context.BIND_AUTO_CREATE);
        if (Util.SDK_INT > 23) {
            initializePlayer();

        }
        //song = trackList.get(player.getCurrentWindowIndex());
        //artistLinkBtn.setText(song.getArtist().getFirstName() + " " + song.getArtist().getLastName());
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
//            Intent intent = new Intent(getContext(), AudioPlayerService.class);
//            Bundle serviceBundle = new Bundle();
//            serviceBundle.putSerializable(TRACK_LIST_KEY, trackList);
//            intent.putExtras(serviceBundle);
//            getContext().bindService(intent, musicPlayerConnection, Context.BIND_AUTO_CREATE);
            initializePlayer();
        }
        //song = trackList.get(player.getCurrentWindowIndex());
        //artistLinkBtn.setText(song.getArtist().getFirstName() + " " + song.getArtist().getLastName());
    }

    @Override
    public void onPause() {
        super.onPause();
        //song = trackList.get(player.getCurrentWindowIndex());
        //artistLinkBtn.setText(song.getArtist().getFirstName() + " " + song.getArtist().getLastName());
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        //song = trackList.get(player.getCurrentWindowIndex());
        //artistLinkBtn.setText(song.getArtist().getFirstName() + " " + song.getArtist().getLastName());
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private AudioPlayerService audioPlayerService;
    private boolean isBound;
    public ServiceConnection musicPlayerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AudioPlayerService.LocalBinder binder = (AudioPlayerService.LocalBinder) service;
            audioPlayerService = binder.getService();
            isBound = true;
            initializePlayer();
            Log.d("ServiceConnection","connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            Log.d("ServiceConnection","disconnected");
        }
    };

    private void initializePlayer() {
        if (player == null && isBound) {

//            ExoPlayerLooperThread looperThread = new ExoPlayerLooperThread();
//            looperThread.run();
//            player = new SimpleExoPlayer.Builder(getContext()).setTrackSelector(trackSelector).setLooper(looperThread.mHandler.getLooper()).build();
//            getContext().startService(intent);
//            if(isBound){
//                player = audioPlayerService.getPlayer();
//            }
            player = audioPlayerService.getPlayer();
            if(player == null){
                DefaultTrackSelector trackSelector = new DefaultTrackSelector(getContext());
                trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd());
                player = new SimpleExoPlayer.Builder(getContext()).setTrackSelector(trackSelector).build();

                for(int i=0; i<trackList.size(); i++){
                    String id = String.valueOf(trackList.get(i).getId());
                    MediaItem mediaItem = MediaItem.fromUri(STREAM_AUDIO_URL + id);
                    player.addMediaItem(mediaItem);
                }
                //for adding multiple files like in a playlist or album
//        MediaItem secondMediaItem = MediaItem.fromUri(getString(R.string.media_url_mp3));
//        player.addMediaItem(secondMediaItem);
                player.prepare();
                player.setPlayWhenReady(playWhenReady);
                player.seekTo(currentWindow, playbackPosition);
            }
        }
        musicPlayerView.setPlayer(player);
        musicPlayerView.setShowShuffleButton(true);
        //repeat toggle for playing song
        //musicPlayerView.setRepeatToggleModes();

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
}