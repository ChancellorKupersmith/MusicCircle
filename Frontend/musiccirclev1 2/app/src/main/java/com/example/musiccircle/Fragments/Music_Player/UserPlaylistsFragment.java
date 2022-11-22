package com.example.musiccircle.Fragments.Music_Player;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.musiccircle.Fragments.Comments.CommentsFragment;
import com.example.musiccircle.R;
import com.example.musiccircle.Fragments.Music_Player.dummy.DummyContent;
import com.example.musiccircle.RecyclerViewAdapters.MyPlaylistRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 */
public class UserPlaylistsFragment extends Fragment implements MyPlaylistRecyclerViewAdapter.PlaylistListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String USER_USERNAME_KEY = "user_username";
    private static final String GET_USER_PLAYLISTS_URL = "http://10.24.227.244:8080/user/playlists/";
    private static final String ADD_SONG_TO_PLAYLIST_URL = "http://10.24.227.244:8080/playlists/addSongs";
    private static final String PLAYLISTS_KEY = "playlists";
    private static final String SONG_KEY = "song_id";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private String user_username;
    private AudioFile song;
    private boolean addedSong;
    //Variables
    private ArrayList<Playlist> playlists;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserPlaylistsFragment() {
    }

    @SuppressWarnings("unused")
    public static UserPlaylistsFragment newInstance(int columnCount, String user_username, ArrayList<Playlist> playlists, AudioFile song) {
        UserPlaylistsFragment fragment = new UserPlaylistsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(USER_USERNAME_KEY, user_username);
        args.putSerializable(PLAYLISTS_KEY, playlists);
        args.putSerializable(SONG_KEY, song);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            user_username = getArguments().getString(USER_USERNAME_KEY);
            playlists = (ArrayList<Playlist>) getArguments().getSerializable(PLAYLISTS_KEY);
            song = (AudioFile) getArguments().getSerializable(SONG_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_playlists_list, container, false);
//        getPlaylists(user_username);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyPlaylistRecyclerViewAdapter(playlists, this));
        }
        return view;
    }

    @Override
    public void onPlaylistClick(int position) {
        if(position == 0){
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            ft.replace(R.id.Comments_Placeholder, CreateNewPlaylistFragment.newInstance(user_username, playlists, song));
            ft.commit();
        }
        else{
            if(playlists.get(position).hasSong(song)){
                int duration = Toast.LENGTH_SHORT;
                Toast songsThere = Toast.makeText(getContext(), song.getSongName() + " already exists in" + playlists.get(position).getName() , duration);
                songsThere.show();
            }
            else{
                addSong(position);
            }
        }
    }

    public void addSong(int position){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest sr = new StringRequest(Request.Method.PUT, ADD_SONG_TO_PLAYLIST_URL ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String g = response;
                        System.out.println(response);
                        if (response.equals("Uploaded audio file successfully")) {
                            int duration = Toast.LENGTH_SHORT;
                            Toast addedPlaylist = Toast.makeText(getContext(), "Added to playlist: " + playlists.get(position).getName() , duration);
                            addedPlaylist.show();
                            playlists.get(position).getSongs().add(song);
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
                String pId = String.valueOf(playlists.get(position).getId());
                String sId = String.valueOf(song.getId());
                params.put("playlistId", pId);
                params.put("songId", sId);
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
}