package com.example.musiccircle.Fragments.Home;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.Entity.Event;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.Entity.UserParcelable;
import com.example.musiccircle.Fragments.Home.HomeEventRecyclerViewAdapter;
import com.example.musiccircle.Fragments.Home.HomeSongRecyclerViewAdapter;
import com.example.musiccircle.Fragments.Home.HomeUserRecyclerViewAdapter;
import com.example.musiccircle.R;
import com.example.musiccircle.Services.AudioPlayerService;
import com.example.musiccircle.Services.NotificationService;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HomeGlobalFragment extends Fragment {

    private static final String USER_KEY = "user";
    private static final String TRACKLIST_KEY = "track_list";
    User loggednUser;
    ArrayList<AudioFile> tracklist;
    String usernameloggedin;
    TextView songs_textView, events_textView, artists_textView;

    public static final String USER_USERNAME_KEY = "user_username";
    NotificationService notificationService;
    RecyclerView event_recyclerView;
    ArrayList<Event> eventlist;
    HomeEventRecyclerViewAdapter event_recycler_adapter;

    RecyclerView song_recyclerView;
    ArrayList<AudioFile> songlist;
    ArrayList<AudioFile> songlist2;
    HomeSongRecyclerViewAdapter song_adapter;

    RecyclerView user_recyclerView;
    ArrayList<UserParcelable> usersList;
    HomeUserRecyclerViewAdapter user_adapter;

    RecyclerView most_liked_song_recyclerView;
    ArrayList<AudioFile> mostlikedsonglist;
    HomeSongRecyclerViewAdapter mostlikedsong_adapter;

    RecyclerView.LayoutManager EventRecyclerViewLayoutManager;
    RecyclerView.LayoutManager SongRecyclerViewLayoutManager;
    RecyclerView.LayoutManager MostLikedSongRecyclerViewLayoutManager;
    RecyclerView.LayoutManager UserRecyclerViewLayoutManager;
    LinearLayoutManager EventRecycler_HorizontalLayout;
    LinearLayoutManager SongRecycler_HorizontalLayout;
    LinearLayoutManager MostLikedSongRecycler_HorizontalLayout;
    LinearLayoutManager UserRecycler_HorizontalLayout;

    ProgressDialog progress;

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 1000; //Delay for 15 seconds.  One second = 1000 millisecond

    private RequestQueue requestQueue;

    View view;

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    User loggedInUser;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           loggedInUser = (User) getArguments().getSerializable("user");

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home_local, container, false);
        songs_textView = view.findViewById(R.id.top_songs_text);
        events_textView = view.findViewById(R.id.top_events_text);
        artists_textView = view.findViewById(R.id.top_artists_text);

        progress = new ProgressDialog(requireContext(),R.style.MyAlertDialogStyle);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        Bundle bundle = getArguments();
        //assert bundle != null;
        //System.out.println(bundle);
        loggednUser = (User) bundle.getSerializable(USER_KEY);
        //      tracklist = (ArrayList<AudioFile>) bundle.getSerializable(TRACKLIST_KEY);
        //    usernameloggedin = bundle.getString("message");

        event_recyclerView = view.findViewById(R.id.events_recycler_view);
        song_recyclerView = view.findViewById(R.id.songs_recycler_view);
        user_recyclerView = view.findViewById(R.id.user_recycler_view);
        most_liked_song_recyclerView = view.findViewById(R.id.most_liked_songs_recycler_view);
        UserRecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        EventRecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        SongRecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        MostLikedSongRecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        event_recyclerView.setLayoutManager(EventRecyclerViewLayoutManager);
        song_recyclerView.setLayoutManager(SongRecyclerViewLayoutManager);
        user_recyclerView.setLayoutManager(UserRecyclerViewLayoutManager);
        most_liked_song_recyclerView.setLayoutManager(MostLikedSongRecyclerViewLayoutManager);
        EventRecycler_HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        event_recyclerView.setLayoutManager(EventRecycler_HorizontalLayout);
        SongRecycler_HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        song_recyclerView.setLayoutManager(SongRecycler_HorizontalLayout);
        UserRecycler_HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        user_recyclerView.setLayoutManager(UserRecycler_HorizontalLayout);
        MostLikedSongRecycler_HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        most_liked_song_recyclerView.setLayoutManager(MostLikedSongRecycler_HorizontalLayout);
        RequestQueue setOnlineQueue = Volley.newRequestQueue(getContext());
        //setUserOnline(setOnlineQueue);
        AddSongsToRecyclerViewArrayList();
        AddUsersToRecyclerViewArrayList();
        AddEventsToRecyclerViewArrayList();


        /**
         HandlerThread handlerThread = new HandlerThread("background-thread");
         handlerThread.start();
         final Handler handler = new Handler(handlerThread.getLooper());
         handler.postDelayed(() -> {
         System.out.println("RUNNNNNNNIG");

         handlerThread.quitSafely();
         }, 2000);
         **/

/**
 songs_textView.setVisibility(View.GONE);
 events_textView.setVisibility(View.GONE);
 artists_textView.setVisibility(View.GONE);
 song_recyclerView.setVisibility(View.GONE);
 user_recyclerView.setVisibility(View.GONE);
 most_liked_song_recyclerView.setVisibility(View.GONE);
 **/
        return view;
    }

    public static HomeGlobalFragment newInstance(User user){
        HomeGlobalFragment fragment = new HomeGlobalFragment();
        Bundle b = new Bundle();
        b.putSerializable(USER_KEY, user);
        fragment.setArguments(b);
        return fragment;
    }
    @Override
    public void onStart(){
        super.onStart();
        Intent notificationServiceIntent = new Intent(getContext(), NotificationService.class);
        Bundle serviceBundle = new Bundle();
        serviceBundle.putSerializable(USER_KEY, loggedInUser);
        serviceBundle.putString(USER_USERNAME_KEY, loggedInUser.getUsername());
        System.out.println("HomeGlobalFragment user: " + loggedInUser);
        System.out.println("HomeGlobalFragment username: " + loggedInUser.getUsername());
        notificationServiceIntent.putExtras(serviceBundle);
        getContext().bindService(notificationServiceIntent, notificationConnection, Context.BIND_AUTO_CREATE);
    }
    @Override
    public void onResume() {
        handler.postDelayed( runnable = () -> {
            if(isRecyclerScrollable(user_recyclerView) && isRecyclerScrollable(song_recyclerView) && isRecyclerScrollable(most_liked_song_recyclerView)){
                progress.dismiss();
            }

            handler.postDelayed(runnable, delay);
        }, delay);

        super.onResume();
        if (getArguments() != null) {
            loggedInUser = (User) getArguments().getSerializable("user");
        }
        Intent notificationServiceIntent = new Intent(getContext(), NotificationService.class);
        Bundle serviceBundle = new Bundle();
        serviceBundle.putSerializable(USER_KEY, loggedInUser);
        serviceBundle.putString(USER_USERNAME_KEY, loggedInUser.getUsername());
        notificationServiceIntent.putExtras(serviceBundle);
        getContext().bindService(notificationServiceIntent, notificationConnection, Context.BIND_AUTO_CREATE);
    }

    public ServiceConnection notificationConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            NotificationService.LocalBinder binder = (NotificationService.LocalBinder) service;
            notificationService = binder.getService();
            Log.d("ServiceConnection","connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("ServiceConnection","disconnected");
        }
    };

//    @Override
//    public void onStop(){
//        super.onStop();
//        getContext().unbindService(notificationConnection);
//    }
    @Override
    public void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
        getContext().unbindService(notificationConnection);
    }

    public void AddEventsToRecyclerViewArrayList() {
        eventlist = new ArrayList<>();
        getEvents();
    }

    public void AddSongsToRecyclerViewArrayList() {
        mostlikedsonglist = new ArrayList<>();
        getMostLikedSongs();
        songlist = new ArrayList<>();
        getSongs();
    }

    public void AddUsersToRecyclerViewArrayList() {
        usersList = new ArrayList<>();
        getUsers();
    }

    public void setUserOnline(RequestQueue queue){
        StringRequest setOnline = new StringRequest(Request.Method.PUT,"http://10.24.227.244:8080/userOnline/"+ loggedInUser.getUsername(),
                response -> {
                    System.out.println(response);
                    Log.e("HttpClient", "success! response: " + response);
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
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
        setOnline.setShouldCache(false);
        queue.add(setOnline);
    }

    public void getEvents() {
        eventlist.clear();
        String search_url = "http://10.24.227.244:8080/events";
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
                            ArrayList<UserParcelable> a = new ArrayList<>();
                            for (int j = 0; j < json_response.getJSONObject(i).getJSONArray("usersPerforming_usernames").length(); j++) {
                                a.add(getUser(json_response.getJSONObject(i).getJSONArray("usersPerforming_usernames").getString(j)));
                            }
                            System.out.println(a);
                            event.setUsersPerforming(a);
                            System.out.println(event.getUsersPerforming().size());
                            eventlist.add(event);
                        }
                        getImagesEvent(eventlist);
                    } catch (JSONException e) {
                        Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                        e.printStackTrace();
                    }
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }


    public UserParcelable getUser(String string) {
        String search_url = "http://10.24.227.244:8080/user/find/" + string;
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        queue.getCache().clear();
        UserParcelable user = new UserParcelable();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    try {
                        JSONObject json_response = new JSONObject(response);
                        System.out.println(json_response);
                        user.setFirstName(json_response.getString("firstName"));
                        user.setLastName(json_response.getString("lastName"));
                        user.setUsername(json_response.getString("username"));
                        getUserImage(user);
                        //getUserImage(user,a);
                    } catch (JSONException e) {
                        Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                        e.printStackTrace();
                    }
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
        return user;
    }

    public void getImagesEvent(List<Event> a) {
        for (int i = 0; i < a.size(); i++) {
            getEventImage(a.get(i));
        }
    }

    public void getEventImage(Event event) {
        String search_url = "http://10.24.227.244:8080/events/" + event.getId() + "/get_pic";
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        queue.getCache().clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    for (int i = 0; i < 1; i++) {
                        byte[] decodedImageBytes = Base64.decode(response, Base64.DEFAULT);
                        event.setImage(decodedImageBytes);
                    }
                    event_recycler_adapter = new HomeEventRecyclerViewAdapter(eventlist);
                    event_recycler_adapter.setLoggedinUser(usernameloggedin);
                    event_recyclerView.setAdapter(event_recycler_adapter);
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }

    public void getSongs() {
        songlist.clear();
        song_recyclerView.getRecycledViewPool().clear();
        String search_url = "http://10.24.227.244:8080/audioFiles";
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        queue.getCache().clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    try {
                        JSONArray json_response = new JSONArray(response);
                        for (int i = 0; i < json_response.length(); i++) {
                            User user = new User();
                            user.setUsername(json_response.getJSONObject(i).getString("artist"));
                            user.setArtistName(json_response.getJSONObject(i).getString("artistName"));
                            Long id = (json_response.getJSONObject(i).getLong("id"));
                            String filename = (json_response.getJSONObject(i).getString("filename"));
                            String songName = (json_response.getJSONObject(i).getString("songName"));
                            int likes = (json_response.getJSONObject(i).getInt("likes"));
                            int plays = (json_response.getJSONObject(i).getInt("plays"));
                            boolean isPublic = (json_response.getJSONObject(i).getBoolean("public"));
                            AudioFile a = new AudioFile(id, filename, user, songName, likes, plays, isPublic);
                            songlist.add(a);
                        }
                        getImagesSong(songlist);
                    } catch (JSONException e) {
                        Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                        e.printStackTrace();
                    }
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    public void getMostLikedSongs() {
        mostlikedsonglist.clear();
        most_liked_song_recyclerView.getRecycledViewPool().clear();
        String search_url2 = "http://10.24.227.244:8080/audioFiles/getTop";
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        queue.getCache().clear();
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, search_url2,
                response -> {
                    try {
                        JSONArray json_response = new JSONArray(response);
                        for (int i = 0; i < json_response.length(); i++) {
                            User user = new User();
                            user.setUsername(json_response.getJSONObject(i).getString("artist"));
                            Long id = (json_response.getJSONObject(i).getLong("id"));
                            String filename = (json_response.getJSONObject(i).getString("filename"));
                            String songName = (json_response.getJSONObject(i).getString("songName"));
                            int likes = (json_response.getJSONObject(i).getInt("likes"));
                            int plays = (json_response.getJSONObject(i).getInt("plays"));
                            boolean isPublic = (json_response.getJSONObject(i).getBoolean("public"));
                            AudioFile a = new AudioFile(id, filename, user, songName, likes, plays, isPublic);
                            mostlikedsonglist.add(a);
                        }
                        getImagesPopularSong(mostlikedsonglist);
                    } catch (JSONException e) {
                        Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                        e.printStackTrace();
                    }
                }, error -> {
        });
        stringRequest2.setShouldCache(false);
        stringRequest2.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest2);
    }

    public void getImagesPopularSong(List<AudioFile> a) {
        for (int i = 0; i < a.size(); i++) {
            getPopularSongImage(a.get(i));
        }
    }

    public void getPopularSongImage(AudioFile audioFile) {
        String search_url = "http://10.24.227.244:8080/audioFiles/pic/" + audioFile.getId();
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        queue.getCache().clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    for (int i = 0; i < 1; i++) {
                        byte[] decodedImageBytes = Base64.decode(response, Base64.DEFAULT);
                        audioFile.setImage(decodedImageBytes);
                    }
                    most_liked_song_recyclerView.setHasFixedSize(true);
                    song_adapter = new HomeSongRecyclerViewAdapter(songlist, requireContext(), loggedInUser);
                    //song_adapter.setLoggedinUser("tester1");
                    most_liked_song_recyclerView.setAdapter(song_adapter);
                    song_adapter.notifyDataSetChanged();
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }

    public void getImagesSong(List<AudioFile> a) {
        for (int i = 0; i < a.size(); i++) {
            getSongImage(a.get(i));
        }
    }

    public void getSongImage(AudioFile audioFile) {
        String search_url = "http://10.24.227.244:8080/audioFiles/pic/" + audioFile.getId();
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        queue.getCache().clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    for (int i = 0; i < 1; i++) {
                        byte[] decodedImageBytes = Base64.decode(response, Base64.DEFAULT);
                        audioFile.setImage(decodedImageBytes);
                    }
                    song_recyclerView.setHasFixedSize(true);
                    song_adapter = new HomeSongRecyclerViewAdapter(songlist, requireContext(), loggedInUser);
                    //song_adapter.setLoggedinUser("tester1");
                    song_recyclerView.setAdapter(song_adapter);
                    song_adapter.notifyDataSetChanged();
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }

    public void getUsers() {
        usersList.clear();
        String search_url = "http://10.24.227.244:8080/user/all";
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        queue.getCache().clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    try {
                        JSONArray json_response = new JSONArray(response);
                        for (int i = 0; i < json_response.length(); i++) {
                            UserParcelable user = new UserParcelable();
                            user.setFirstName(json_response.getJSONObject(i).getString("firstName"));
                            user.setLastName(json_response.getJSONObject(i).getString("lastName"));
                            user.setUsername(json_response.getJSONObject(i).getString("username"));
                            user.setArtistName(json_response.getJSONObject(i).getString("artistName"));
                            usersList.add(user);
                        }
                        getImagesUser(usersList);
                    } catch (JSONException e) {
                        Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                        e.printStackTrace();
                    }
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }

    public void getImagesUser(List<UserParcelable> a) {
        for (int i = 0; i < a.size(); i++) {
            getUserImage(a.get(i));
        }
        /**
         if (isRecyclerScrollable(user_recyclerView) && isRecyclerScrollable(song_recyclerView) && isRecyclerScrollable(most_liked_song_recyclerView)) {
         song_recyclerView.post(() -> song_recyclerView.smoothScrollToPosition(songlist.size() - 1));
         user_recyclerView.post(() -> user_recyclerView.smoothScrollToPosition(usersList.size() - 1));
         most_liked_song_recyclerView.post(() -> most_liked_song_recyclerView.smoothScrollToPosition(mostlikedsonglist.size() - 1));
         progress.dismiss();
         }
         **/
    }

    public void getUserImage(UserParcelable userParcelable) {
        String search_url = "http://10.24.227.244:8080/user/pic/" + userParcelable.getUsername();
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        queue.getCache().clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    for (int i = 0; i < 1; i++) {
                        byte[] decodedImageBytes = Base64.decode(response, Base64.DEFAULT);
                        userParcelable.setContent(decodedImageBytes);
                    }
                    user_recyclerView.setHasFixedSize(true);
                    user_adapter = new HomeUserRecyclerViewAdapter(usersList, loggednUser.getUsername());
                    //user_adapter.setLoggedinUser("tester1");
                    user_recyclerView.setAdapter(user_adapter);
                    user_adapter.notifyDataSetChanged();
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }


    public boolean isRecyclerScrollable(RecyclerView recyclerView) {
        song_recyclerView.setVisibility(View.VISIBLE);
        user_recyclerView.setVisibility(View.VISIBLE);
        most_liked_song_recyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (layoutManager == null || adapter == null) return false;
        return layoutManager.findLastCompletelyVisibleItemPosition() < adapter.getItemCount() - 1;
    }
}