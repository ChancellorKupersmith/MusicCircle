package com.example.musiccircle.Fragments.Profile;


import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.Entity.Playlist;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyUserPlaylists_fragment extends Fragment {

    String logged_in_username;
    RecyclerView user_playlist_recyclerView;
    ArrayList<Playlist> playlistlist;
    UserPlaylistRecyclerViewAdapter userPlaylistRecyclerViewAdapter;
    EditText search_groups;
    GridLayoutManager layoutManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_user_playlists_fragment, container, false);
        user_playlist_recyclerView = view.findViewById(R.id.user_playlists);
        layoutManager = new GridLayoutManager(requireContext(), 2);
        user_playlist_recyclerView.setLayoutManager(layoutManager);
        AddPlaylistsToRecyclerViewArrayList();


        Bundle bundle = getArguments();
        assert bundle != null;
        //logged_in_username = bundle.getString("message");
        logged_in_username = "chancek";

        search_groups = view.findViewById(R.id.search_user_playlists);

        search_groups.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        return view;
    }

    private void AddPlaylistsToRecyclerViewArrayList() {
        playlistlist = new ArrayList<>();
        getPlaylists();
    }

    private void filter(String text) {
        ArrayList<Playlist> filteredList = new ArrayList<>();
        System.out.println(filteredList.size());
        for (Playlist playlist : playlistlist) {
            if (playlist.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(playlist);
            }
        }
        userPlaylistRecyclerViewAdapter.filterList(filteredList);
    }


    private void getPlaylists() {
        playlistlist.clear();
        String search_url = "http://10.24.227.244:8080/user/playlists/chancek";
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.getCache().clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    try {
                        JSONArray json_response = new JSONArray(response);
                        for (int i = 0; i < json_response.length(); i++) {
                            System.out.println(json_response);
                            JSONObject JSONplaylist = json_response.getJSONObject(i);
                            String playlist_name = JSONplaylist.getString("name");
                            int playlist_length = JSONplaylist.getJSONArray("songs").length();
                            ArrayList<AudioFile> alist = new ArrayList<>();
                            if(playlist_length > 0) {
                                for (int j = 0; j < playlist_length; j++) {
                                    alist.add(getAudioFilefromPlaylist(JSONplaylist.getJSONArray("songs").getJSONObject(j)));
                                }
                                Playlist playlist = new Playlist(playlist_name,logged_in_username,alist);
                                playlist.setLength(playlist_length);
                                playlistlist.add(playlist);
                            }
                        }
                    } catch (JSONException e) {
                        Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                        e.printStackTrace();
                    }
                }, error -> Log.e("PARSE_USR_PLAYLIST_ERR", Objects.requireNonNull(error.getMessage())));
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }

    public AudioFile getAudioFilefromPlaylist(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.setFirstName("tester");
        user.setLastName("tester1");
        user.setUsername("tester1");
        Long id = (jsonObject.getLong("id"));
        String filename = (jsonObject.getString("filename"));
        String songName = (jsonObject.getString("songName"));
        int likes = (jsonObject.getInt("likes"));
        int plays = (jsonObject.getInt("plays"));
        boolean isPublic = (jsonObject.getBoolean("public"));
        AudioFile a = new AudioFile(id, filename, user, songName, likes, plays, isPublic);
        return a;
    }

}


