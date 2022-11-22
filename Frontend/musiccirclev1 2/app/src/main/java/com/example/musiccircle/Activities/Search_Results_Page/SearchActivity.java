package com.example.musiccircle.Activities.Search_Results_Page;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Entity.Album;
import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.Entity.Event;
import com.example.musiccircle.Entity.Genre;
import com.example.musiccircle.Entity.Group;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.Entity.entities;
import com.example.musiccircle.R;
import com.example.musiccircle.RecyclerViewAdapters.EventRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.musiccircle.Activities.Search_Results_Page.ui.main.SectionsPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity {

    EditText searh_btn;
    int tabPos;
    ViewPager viewPager;
    TabLayout tabs;
    public static final List<entities> ObjsByName = new ArrayList<entities>();
    SectionsPagerAdapter sectionsPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPos = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        searh_btn = findViewById(R.id.btn_search);
        searh_btn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            private Timer timer = new Timer();
            private static final long DELAY = 500;//milliseconds

            @Override
            public void afterTextChanged(Editable s) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                ObjsByName.clear();
                                searchByName(s.toString().trim());
                            }
                        }, DELAY);
            }
        });

        // Get the intent, verify the action and get the query

    }

    public void searchByName(String query){
        RequestQueue queue = Volley.newRequestQueue(this);
        if(tabPos == 0){
            //search all
            RequestQueue queue1 = Volley.newRequestQueue(this);
            RequestQueue queue2 = Volley.newRequestQueue(this);
            RequestQueue queue3 = Volley.newRequestQueue(this);
            RequestQueue queue4 = Volley.newRequestQueue(this);
            searchAlbums(query, queue);
            searchSongs(query, queue1);
            searchArtists(query, queue2);
            searchEvents(query, queue3);
            searchGroups(query, queue4);
        }
        else if(tabPos == 1){
            //search songs
            searchSongs(query, queue);
            while (!(ObjsByName.size() > 0)){

            }
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    // Stuff that updates the UI
                    sectionsPagerAdapter.instantiateItem(viewPager, 1);

                }
            });



        }
        else if(tabPos == 2){
            //search artists
            searchArtists(query, queue);
            while (!(ObjsByName.size() > 0)){

            }
            sectionsPagerAdapter.getItem(1);
        }
        else if(tabPos == 3){
            //search groups
            searchGroups(query, queue);
            while (!(ObjsByName.size() > 0)){

            }
            //sectionsPagerAdapter.notifyDataSetChanged();
        }
        else if(tabPos == 4){
            //search events
            searchEvents(query, queue);
            while (!(ObjsByName.size() > 0)){

            }
            //sectionsPagerAdapter.notifyDataSetChanged();
        }
        else if(tabPos == 5){
            //search albums
            searchAlbums(query, queue);
            while (!(ObjsByName.size() > 0)){

            }
            //sectionsPagerAdapter.notifyDataSetChanged();
        }
        else{
            //error invalid tab pos
        }

    }

    public void searchSongs(String query, RequestQueue queue){
        //search songs
        //(/audioFiles/name)
        String search_url = "http://10.24.227.244:8080/audioFiles/name/" + query;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("RESPONSE: " + response);
                            JSONArray json_response = new JSONArray(response);
                            for (int i = 0; i < json_response.length(); i++) {
                                boolean hasGenre = false, hasAlbum = false;
                                System.out.println("\n\nJSON SONG: " + json_response.getJSONObject(i));
                                JSONObject JSONSong = json_response.getJSONObject(i);
                                String artist_username = JSONSong.getString("artist");
                                User artist = getArtist(artist_username);

                                if(artist == null){
                                    artist = new User();
                                    artist.setArtistName("JPEGMAFIA");
                                }
                                long id = JSONSong.getLong("id");
                                //filename, artist, name, likes, plays, isPublic, album
                                String filename = JSONSong.getString("filename");
                                String name = JSONSong.getString("songName");
                                int likes = JSONSong.getInt("likes");
                                int plays = JSONSong.getInt("plays");
                                boolean isPublic = JSONSong.getBoolean("public");
//                                Genre genre = null;
//                                if(JSONSong.getJSONObject("genre") != null){
//                                    JSONObject JSONGenre = JSONSong.getJSONObject("genre");
//                                    genre = new Genre(JSONGenre.getString("name"));
//                                    genre.setId(JSONGenre.getLong("id"));
//                                    hasGenre = true;
//                                }
                                //Might include album depending on speed
//                                    JSONObject JSONAlbum = JSONSong.getJSONObject("album");
//                                    String albumName = JSONAlbum.getString("albumName");

                                AudioFile song;
                                song = new AudioFile(id, filename, artist, name, likes, plays, isPublic);
//                                if(hasGenre){
//                                    song = new AudioFile(id, filename, artist, name, likes, plays, isPublic, genre);
//                                }
//                                else{
//                                    song = new AudioFile(id, filename, artist, name, likes, plays, isPublic);
//                                }
                                ObjsByName.add(song);
                            }
                        } catch (JSONException e) {
                            Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
            }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void searchArtists(String query, RequestQueue queue){
        //search artists
        String search_url = "http://10.24.227.244:8080/user/name/" + query;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray json_response = new JSONArray(response);
                            System.out.println("\n\nUSERS: " + response);
                            for (int i = 0; i < json_response.length(); i++) {
                                JSONObject JSONUser = json_response.getJSONObject(i);
                                String firstname = JSONUser.getString("firstname");
                                String lastname = JSONUser.getString("lastname");
                                String username = JSONUser.getString("username");
                                User artist = new User();
                                artist.setFirstName(firstname);
                                artist.setLastName(lastname);
                                artist.setUsername(username);
                                ObjsByName.add(artist);
                            }
                        } catch (JSONException e) {
                            Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void searchGroups(String query, RequestQueue queue){
        String search_url = "http://10.24.227.244:8080/group/name/" + query;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray json_response = new JSONArray(response);
                            for (int i = 0; i < json_response.length(); i++) {
                                JSONObject JSONGroup = json_response.getJSONObject(i);
                                long id = JSONGroup.getLong("id");
                                String name = JSONGroup.getString("name");
                                int numberOfMembers = JSONGroup.getJSONArray("members").length();
                                Group group = new Group();
                                group.setId(id);
                                group.setName(name);
                                ObjsByName.add(group);
                            }
                        } catch (JSONException e) {
                            Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void searchEvents(String query, RequestQueue queue){
        String search_url = "http://10.24.227.244:8080/events/name/" + query;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray json_response = new JSONArray(response);
                            for (int i = 0; i < json_response.length(); i++) {
                                JSONObject JSONEvent = json_response.getJSONObject(i);
                                long id = JSONEvent.getLong("id");
                                String name = JSONEvent.getString("name");
                                String dateTime = JSONEvent.getString("dateTime");
                                String city = JSONEvent.getString("city");
                                String state = JSONEvent.getString("state");
                                String eventPicFilename = JSONEvent.getString("eventPicFilename");
                                Event event = new Event();
                                event.setDateTime(dateTime);
                                event.setEventLocation(city + ", " + state);
                                event.setName(name);
                                event.setId(id);
                                event.setEventPicFilename(eventPicFilename);
                                ObjsByName.add(event);
                            }
                        } catch (JSONException e) {
                            Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void searchAlbums(String query, RequestQueue queue){
        String search_url = "http://10.24.227.244:8080/albums/name/" + query;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray json_response = new JSONArray(response);
                            for (int i = 0; i < json_response.length(); i++) {
                                JSONObject JSONAlbum = json_response.getJSONObject(i);
                                String artistUsername = JSONAlbum.getString("artist");
                                User artist = getArtist(artistUsername);
                                long id = JSONAlbum.getLong("id");
                                String name = JSONAlbum.getString("albumName");
                                String albumPicFilename = JSONAlbum.getString("albumPicFilename");
                                List<AudioFile> album_trackList = new ArrayList<AudioFile>();
                                JSONArray JSONTrackList= JSONAlbum.getJSONArray("songs");
                                for(int j=0; j<JSONTrackList.length(); j++){
                                    boolean hasGenre = false;
                                    JSONObject JSONSong = JSONTrackList.getJSONObject(j);
                                    long songId = JSONSong.getLong("id");
                                    String songName = JSONSong.getString("songName");
                                    String filename = JSONSong.getString("filename");
                                    int likes = JSONSong.getInt("likes");
                                    int plays = JSONSong.getInt("plays");
                                    boolean isPublic = JSONSong.getBoolean("public");

                                    JSONObject JSONGenre = JSONSong.getJSONObject("genre");
                                    Genre genre = null;
                                    if(JSONGenre != null){
                                        long genreId = JSONGenre.getLong("id");
                                        String genreName = JSONGenre.getString("name");
                                        genre = new Genre(name);
                                        genre.setId(id);
                                        hasGenre = true;
                                    }
                                    AudioFile song = new AudioFile(songId, filename, artist, songName, likes, plays, isPublic);
                                    if(hasGenre){
                                        song.setGenre(genre);
                                    }
                                    album_trackList.add(song);
                                }

                                Album album = new Album(name, artistUsername, album_trackList);
                                if(album_trackList.size() == 1){
                                    album.setSingle(true);
                                }
                                ObjsByName.add(album);
                            }
                        } catch (JSONException e) {
                            Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public User getArtist(String username) {
        final User[] user = new User[1];
        RequestQueue queueUser = Volley.newRequestQueue(this);
        String search_url = "http://10.24.227.244:8080/user/find/" + username;
        StringRequest stringRequestUser = new StringRequest(Request.Method.GET, search_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject JSONUser = new JSONObject(response);
                            user[0] = new User();
                            user[0].setUsername(username);
                            user[0].setFirstName("firstname");
                            user[0].setLastName("lastname");
                            user[0].setId(JSONUser.getLong("id"));
                        } catch (JSONException e) {
                            Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queueUser.add(stringRequestUser);
        return user[0];
    }
}