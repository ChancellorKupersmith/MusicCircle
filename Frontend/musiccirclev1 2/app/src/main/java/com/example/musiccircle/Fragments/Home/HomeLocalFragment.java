package com.example.musiccircle.Fragments.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.Entity.Event;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.Entity.UserParcelable;
import com.example.musiccircle.R;
import com.example.musiccircle.Services.AudioPlayerService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HomeLocalFragment extends Fragment{
    TextView songs_textView, events_textView, artists_textView, events_textView2;

    RecyclerView local_event_recyclerView;
    ArrayList<Event> eventlist;
    HomeEventRecyclerViewAdapter event_recycler_adapter;

    RecyclerView recent_song_recyclerView;
    ArrayList<AudioFile> songlist;
    HomeSongRecyclerViewAdapter song_adapter;

    RecyclerView following_user_recyclerView;
    ArrayList<UserParcelable> usersList;
    HomeUserRecyclerViewAdapter user_adapter;

    RecyclerView.LayoutManager EventRecyclerViewLayoutManager;
    RecyclerView.LayoutManager SongRecyclerViewLayoutManager;
    RecyclerView.LayoutManager UserRecyclerViewLayoutManager;
    LinearLayoutManager EventRecycler_HorizontalLayout;
    LinearLayoutManager SongRecycler_HorizontalLayout;
    LinearLayoutManager UserRecycler_HorizontalLayout;

    String str;

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    User loggedInUser;
    private Spinner spinner1, spinner2;

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loggedInUser = (User) getArguments().getSerializable("user");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home_global, container, false);
        songs_textView = view.findViewById(R.id.top_songs_text);
        events_textView = view.findViewById(R.id.near_events_text);
        events_textView2 = view.findViewById(R.id.near_events_text2);
        artists_textView = view.findViewById(R.id.top_artists_text);
        local_event_recyclerView = view.findViewById(R.id.local_events_recycler_view);
        recent_song_recyclerView = view.findViewById(R.id.local_songs_recycler_view);
        following_user_recyclerView = view.findViewById(R.id.following_user_recycler_view);

        spinner1 = view.findViewById(R.id.event_state_spinner);
        spinner1.setOnItemSelectedListener(new HomeLocalFragment.CustomOnItemSelectedListener());

        UserRecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        EventRecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        SongRecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        local_event_recyclerView.setLayoutManager(EventRecyclerViewLayoutManager);
        recent_song_recyclerView.setLayoutManager(SongRecyclerViewLayoutManager);
        following_user_recyclerView.setLayoutManager(UserRecyclerViewLayoutManager);
        AddEventsToRecyclerViewArrayList();
        AddSongsToRecyclerViewArrayList();
        AddUsersToRecyclerViewArrayList();
        EventRecycler_HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        local_event_recyclerView.setLayoutManager(EventRecycler_HorizontalLayout);
        SongRecycler_HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recent_song_recyclerView.setLayoutManager(SongRecycler_HorizontalLayout);
        UserRecycler_HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        following_user_recyclerView.setLayoutManager(UserRecycler_HorizontalLayout);

        return view;
    }

    private void AddEventsToRecyclerViewArrayList() {
        eventlist = new ArrayList<>();
    }

    private void AddSongsToRecyclerViewArrayList() {
        songlist = new ArrayList<>();
        getSongs();
    }

    private void getSongs() {
        songlist.clear();
        String search_url = "http://10.24.227.244:8080/user/playlist/chancek/37";
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    try {
                        JSONObject JSONplaylist = new JSONObject(response);
                        System.out.println(JSONplaylist);
                        int playlist_length = JSONplaylist.getJSONArray("songs").length();
                        System.out.println(playlist_length);
                        for (int j = 0; j < playlist_length; j++) {
                            User user = new User();
                            user.setFirstName(JSONplaylist.getJSONArray("songs").getJSONObject(j).getString("artist"));
                            Long id = (JSONplaylist.getJSONArray("songs").getJSONObject(j).getLong("id"));
                            System.out.println(id);
                            String filename = (JSONplaylist.getJSONArray("songs").getJSONObject(j).getString("filename"));
                            String songName = (JSONplaylist.getJSONArray("songs").getJSONObject(j).getString("songName"));
                            int likes = (JSONplaylist.getJSONArray("songs").getJSONObject(j).getInt("likes"));
                            int plays = (JSONplaylist.getJSONArray("songs").getJSONObject(j).getInt("plays"));
                            boolean isPublic = (JSONplaylist.getJSONArray("songs").getJSONObject(j).getBoolean("public"));
                            AudioFile a = new AudioFile(id, filename, user, songName, likes, plays, isPublic);
                            getSongImage(a);
                            songlist.add(a);
                        }
                        getImages();
                    } catch (JSONException e) {
                        Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                        e.printStackTrace();
                    }
                }, error -> Log.e("PARSE_USR_PLAYLIST_ERR", Objects.requireNonNull(error.getMessage())));

        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    public void getImages() {
        song_adapter = new HomeSongRecyclerViewAdapter(songlist, requireContext(), loggedInUser);
        song_adapter.setLoggedinUser("tester1");
        recent_song_recyclerView.setAdapter(song_adapter);
    }

    public void getSongImage(AudioFile audioFile) {
        String search_url = "http://10.24.227.244:8080/audioFiles/pic/" + audioFile.getId();
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        queue.getCache().clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    byte[] decodedImageBytes = Base64.decode(response, Base64.DEFAULT);
                    audioFile.setImage(decodedImageBytes);
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    private void AddUsersToRecyclerViewArrayList() {
        usersList = new ArrayList<>();
        getUsers();
    }

    public void getUsers() {
        String search_url = "http://192.168.1.154:8080/user/followers/tester6";
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        queue.getCache().clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    try {
                        JSONArray json_response = new JSONArray(response);
                        ArrayList<User>a = new ArrayList<>();
                        for (int i = 0; i < json_response.length(); i++) {
                            System.out.println(json_response);
                            getUser(json_response.getString(0),a);

                            //user.setFirstName(json_response.getJSONObject(i).getString("firstName"));
                            //user.setLastName(json_response.getJSONObject(i).getString("lastName"));
                            //user.setUsername(json_response.getJSONObject(i).getString("username"));
                        }
                    } catch (JSONException e) {
                        Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                        e.printStackTrace();
                    }
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }

    public void getImagesUser(List<User> a) {
        following_user_recyclerView.setHasFixedSize(true);
        user_adapter = new HomeUserRecyclerViewAdapter(usersList, loggedInUser.getUsername());
        //user_adapter.setLoggedinUser("tester1");
        following_user_recyclerView.setAdapter(user_adapter);
        user_adapter.notifyDataSetChanged();
    }

    public void getEvents(String string){
        eventlist.clear();
        String search_url = "http://10.24.227.244:8080/event/findbylocation/" + string;
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    try {
                        JSONArray json_response = new JSONArray(response);
                        System.out.println(json_response);
                        for (int i = 0; i < json_response.length(); i++) {
                            Event event = new Event();
                            event.setId(json_response.getJSONObject(i).getLong("id"));
                            event.setEventName(json_response.getJSONObject(i).getString("name"));
                            event.setEventLocation(json_response.getJSONObject(i).getString("location"));
                            event.setEventDescription(json_response.getJSONObject(i).getString("description"));
                            event.setEventCreator(json_response.getJSONObject(i).getString("event_creator"));
                            ArrayList<UserParcelable>a = new ArrayList<>();
                            for(int j=0; j<json_response.getJSONObject(i).getJSONArray("usersPerforming").length(); j++){
                                UserParcelable user = new UserParcelable();
                                user.setFirstName(json_response.getJSONObject(i).getJSONArray("usersPerforming").getJSONObject(j).getString("firstName"));
                                user.setLastName(json_response.getJSONObject(i).getJSONArray("usersPerforming").getJSONObject(j).getString("lastName"));
                                user.setUsername(json_response.getJSONObject(i).getJSONArray("usersPerforming").getJSONObject(j).getString("username"));
                                a.add(user);
                            }
                            event.setUsersPerforming(a);
                            getEventImage(event);
                            eventlist.add(event);
                        }
                        event_recycler_adapter = new HomeEventRecyclerViewAdapter(eventlist);
                        event_recycler_adapter.setLoggedinUser("tester1");
                        local_event_recyclerView.setAdapter(event_recycler_adapter);
                        //.println(eventlist);
                    } catch (JSONException e) {
                        Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                        e.printStackTrace();
                    }
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }

    public void getEventImage(Event event) {
        //http://10.24.227.244:8080/events/4/get_pic
        String search_url = "http://10.24.227.244:8080/events/" + event.getId() + "/get_pic";

        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        queue.getCache().clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    byte[] decodedImageBytes = Base64.decode(response, Base64.DEFAULT);
                    event.setImage(decodedImageBytes);
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }


    public void getUser(String string, ArrayList<User>a) {
        String search_url = "http://10.24.227.244:8080/user/find/" + string;
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        queue.getCache().clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    try {
                        JSONObject json_response = new JSONObject(response);
                        System.out.println(json_response);
                        User user = new User();
                        user.setFirstName(json_response.getString("firstName"));
                        user.setLastName(json_response.getString("lastName"));
                        user.setUsername(json_response.getString("username"));
                        getUserImage(user,a);
                    } catch (JSONException e) {
                        Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                        e.printStackTrace();
                    }
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }

    public void getUserImage(User user,ArrayList<User>a) {
        String search_url = "http://10.24.227.244:8080/user/pic/" + user.getUsername();
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        queue.getCache().clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    for (int i = 0; i < 1; i++) {
                        byte[] decodedImageBytes = Base64.decode(response, Base64.DEFAULT);
                        System.out.println(Arrays.toString(decodedImageBytes));
                        user.setContent(decodedImageBytes);
                        a.add(user);
                        //System.out.println(Arrays.toString(user.getContent()));
                    }
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            Toast.makeText(parent.getContext(),
                    "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT).show();
            str = parent.getItemAtPosition(pos).toString();
            events_textView2.setText(str);
            getEvents(str);

            System.out.println(str);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

}
