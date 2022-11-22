package com.example.musiccircle.Fragments.Music_Player;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.Entity.Playlist;
import com.example.musiccircle.Fragments.Group.Group_fragment;
import com.example.musiccircle.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateNewPlaylistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateNewPlaylistFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String USER_USERNAME_KEY = "user_username";
    private static final String PLAYLISTS_KEY = "playlists";
    private static final String SONG_KEY = "song_id";
    private static final String UPLOAD_PLAYLIST_URL = "http://10.24.227.244:8080/playlists/up/";
    private static final String GET_USER_PLAYLISTS_URL = "http://10.24.227.244:8080/user/playlists/";

    private String username;
    private ArrayList<Playlist> playlists;
    private AudioFile song;
    private EditText playlistNameInput;
    private boolean addedPlaylist;
    private Button save;
    private View view;

    public CreateNewPlaylistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param username User's username.
     * @return A new instance of fragment CreateNewPlaylistFragment.
     */
    public static CreateNewPlaylistFragment newInstance(String username, ArrayList<Playlist> playlists, AudioFile song) {
        CreateNewPlaylistFragment fragment = new CreateNewPlaylistFragment();
        Bundle args = new Bundle();
        args.putString(USER_USERNAME_KEY, username);
        args.putSerializable(SONG_KEY, song);
        args.putSerializable(PLAYLISTS_KEY, playlists);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(USER_USERNAME_KEY);
            playlists = (ArrayList<Playlist>) getArguments().getSerializable(PLAYLISTS_KEY);
            song = (AudioFile) getArguments().getSerializable(SONG_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_new_playlist, container, false);
        playlistNameInput = view.findViewById(R.id.Playlist_Name_Input);
        save = view.findViewById(R.id.btn_Playlist_Save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedPlaylist = false;
                System.out.println("PLAYLIST NAME: " + playlistNameInput.getText().toString().trim());
                saveNewPlaylist(playlistNameInput.getText().toString().trim());
                if (addedPlaylist) {
                    int duration = Toast.LENGTH_SHORT;
                    Toast playlistCreated = Toast.makeText(getContext(), "Created New Playlist: " + playlistNameInput.getText().toString(), duration);
                    playlistCreated.show();
                    getUsersPlaylists();
                    getUsersPlaylists();
                    UserPlaylistsFragment userPlaylistsFragment = UserPlaylistsFragment.newInstance(1, username, playlists, song);
                    FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                    ft.replace(R.id.Comments_Placeholder, userPlaylistsFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                } else {
                    int duration = Toast.LENGTH_SHORT;
                    Toast playlistNotCreated = Toast.makeText(getContext(), "Failed to create new playlist: " + playlistNameInput.getText().toString(), duration);
                    playlistNotCreated.show();
                }
            }
        });
        return view;

    }

    public void saveNewPlaylist(String name) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest sr = new StringRequest(Request.Method.POST, UPLOAD_PLAYLIST_URL + username + "/" + name,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Uploaded playlist successfully")) {
                            addedPlaylist = true;
                        }
                        Log.e("HttpClient", "success! response: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    public void getUsersPlaylists(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest getUserPlaylists = new StringRequest(Request.Method.GET, GET_USER_PLAYLISTS_URL + username,
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
                                Playlist playlist = new Playlist(JSONplaylist.getString("name"), username);
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
}