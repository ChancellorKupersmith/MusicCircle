package com.example.musiccircle.Fragments.Profile;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class ViewOtherProfile_fragment extends Fragment {
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    ImageView imageView;
    String user_profile_username;
    String loggedin_profile_username;
    Button follow;
    Button message;

    String loggedInUserId;
    String UserId;
    UserParcelable user;

    User loggedInUser;
    RecyclerView song_recyclerView;
    ArrayList<AudioFile> songlist;
    HomeSongRecyclerViewAdapter song_adapter;
    RecyclerView.LayoutManager SongRecyclerViewLayoutManager;
    LinearLayoutManager SongRecycler_HorizontalLayout;

    RecyclerView event_recyclerView;
    ArrayList<Event> eventlist;
    HomeEventRecyclerViewAdapter event_recycler_adapter;
    RecyclerView.LayoutManager EventRecyclerViewLayoutManager;
    LinearLayoutManager EventRecycler_HorizontalLayout;

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 1000; //Delay for 15 seconds.  One second = 1000 millisecond

    @Override
    public void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            user = (UserParcelable) getArguments().getSerializable("userParceable");
            loggedInUser = (User) getArguments().getSerializable("user");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_other_user_profile, container, false);
        Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out);
        view.startAnimation(anim);
        textView1 = view.findViewById(R.id.profile_username);
        textView4 = view.findViewById(R.id.num_user_followers);
        textView5 = view.findViewById(R.id.num_user_following);
        textView6 = view.findViewById(R.id.num_user_songs);
        imageView = view.findViewById(R.id.imageview_account_profile);
        song_recyclerView = view.findViewById(R.id.user_songs_recycler_view);
        event_recyclerView = view.findViewById(R.id.artist_events_recycler_view);
        follow = view.findViewById(R.id.follow_user);

        Bundle bundle = getArguments();
        assert bundle != null;
        user_profile_username = bundle.getString("user_profile_username");
        loggedin_profile_username = bundle.getString("loggedin_profile_username");

        user = bundle.getParcelable("userParceable");
        loggedInUser = (User) bundle.getSerializable("user");
        //loggedin_profile_username = loggedInUser.getUsername();
        assert user != null;
        System.out.println("----->" + user.getUsername());
        System.out.println("----->" + loggedin_profile_username);

        SongRecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        song_recyclerView.setLayoutManager(SongRecyclerViewLayoutManager);
        AddSongsToRecyclerViewArrayList();
        SongRecycler_HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        song_recyclerView.setLayoutManager(SongRecycler_HorizontalLayout);

        EventRecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        event_recyclerView.setLayoutManager(EventRecyclerViewLayoutManager);
        EventRecycler_HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        event_recyclerView.setLayoutManager(EventRecycler_HorizontalLayout);

        if (user.getContent() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getContent(), 0, user.getContent().length);
            imageView.setImageBitmap(bitmap);
        }

        textView1.setText(user_profile_username);


        follow.setOnClickListener(view1 -> {
            if (follow.getText().equals("FOLLOWING")) {
                unfollow_user(loggedin_profile_username, user.getUsername());
            } else {
                follow_user(loggedin_profile_username, user.getUsername());
            }
        });

        song_recyclerView.setHasFixedSize(true);
        song_recyclerView.setDrawingCacheEnabled(true);
        song_recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        getUserUploadedSongs(user);
        refreshCount();
        getEvents();

        return view;
    }

    @Override
    public void onResume() {
        handler.postDelayed( runnable = () -> {
            refreshCount();
            handler.postDelayed(runnable, delay);
        }, delay);

        super.onResume();
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }

    private void AddSongsToRecyclerViewArrayList() {
        songlist = new ArrayList<>();
    }

    void refreshCount() {
        String search_url = "http://10.24.227.244:8080/user/followers/" + user.getUsername();
        String search_url1 = "http://10.24.227.244:8080/user/following/" + user.getUsername();
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        queue.getCache().clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    try {
                        JSONArray json_response = new JSONArray(response);
                        if(!(Integer.parseInt(textView4.getText().toString()) == json_response.length())){
                            textView4.setText(String.valueOf(json_response.length()));
                        }
                        if(alreadyfollowing(json_response,"iaroy88")){
                            follow.setText("FOLLOWING");
                            follow.setSelected(true);
                        }else{
                            follow.setText("FOLLOW");
                            follow.setSelected(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
        });
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, search_url1,
                response -> {
                    try {
                        JSONArray json_response = new JSONArray(response);
                        textView5.setText(String.valueOf(json_response.length()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        stringRequest1.setShouldCache(false);
        queue.add(stringRequest);
        queue.add(stringRequest1);
    }

    void getEvents() {
        eventlist = new ArrayList<>();
        eventlist.clear();
        String search_url = "http://10.24.227.244:8080/events/by_user?username=" + user.getUsername();
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        queue.getCache().clear();
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
                            for (int j = 0; j < json_response.getJSONObject(i).getJSONArray("usersPerforming").length(); j++) {
                                UserParcelable user = new UserParcelable();
                                user.setFirstName(json_response.getJSONObject(i).getJSONArray("usersPerforming").getJSONObject(j).getString("firstName"));
                                user.setLastName(json_response.getJSONObject(i).getJSONArray("usersPerforming").getJSONObject(j).getString("lastName"));
                                user.setUsername(json_response.getJSONObject(i).getJSONArray("usersPerforming").getJSONObject(j).getString("username"));
                                getUserImage(user);
                                a.add(user);
                            }
                            event.setUsersPerforming(a);
                            getEventImage(event);
                            eventlist.add(event);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
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
                    event_recycler_adapter.setLoggedinUser("tester1");
                    event_recyclerView.setAdapter(event_recycler_adapter);
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
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
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    private boolean alreadyfollowing(JSONArray jsonArray, String usernameToFind) {
        return jsonArray.toString().contains(usernameToFind);
    }

    void follow_user(String str1, String str2) {
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        queue.getCache().clear();
        String search_url = "http://10.24.227.244:8080/user/followuser/" + str1 + "/" + str2;
        StringRequest sr = new StringRequest(Request.Method.PUT, search_url,
                response -> {
                    if (response.equals("NOW FOLLOWING USER")) {
                        follow.setSelected(true);
                        follow.setText("FOLLOWING");
                    }
                    Log.e("HttpClient", "success! response: " + response);
                },
                error -> Log.e("HttpClient", "error: " + error.toString())) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    void unfollow_user(final String str1, final String str2) {
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String search_url = "http://10.24.227.244:8080/user/unfollowuser/" + str1 + "/" + str2;
        StringRequest sr = new StringRequest(Request.Method.PUT, search_url,
                response -> {
                    if (response.equals("UNFOLLOWED USER")) {
                        follow.setSelected(false);
                        follow.setText("FOLLOW");
                    }
                    Log.e("HttpClient", "success! response: " + response);
                },
                error -> Log.e("HttpClient", "error: " + error.toString())) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    public void getUserUploadedSongs(UserParcelable user) {
        songlist.clear();
        String search_url2 = "http://10.24.227.244:8080/user/recentUpload?username=" + user.getUsername();
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        queue.getCache().clear();
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, search_url2,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        //System.out.println(jsonObject.getJSONArray("songs"));
                        textView6.setText(String.valueOf(jsonObject.getJSONArray("songs").length()));
                        for (int i = 0; i < jsonObject.getJSONArray("songs").length(); i++) {
                            User user1 = new User();
                            user1.setUsername(user.getUsername());
                            user1.setArtistName(user.getArtistName());
                            Long id = jsonObject.getJSONArray("songs").getJSONObject(i).getLong("id");
                            String filename = jsonObject.getJSONArray("songs").getJSONObject(i).getString("filename");
                            String songName = jsonObject.getJSONArray("songs").getJSONObject(i).getString("songName");
                            int likes = (jsonObject.getJSONArray("songs").getJSONObject(i).getInt("likes"));
                            int plays = (jsonObject.getJSONArray("songs").getJSONObject(i).getInt("plays"));
                            boolean isPublic = (jsonObject.getJSONArray("songs").getJSONObject(i).getBoolean("public"));
                            AudioFile a = new AudioFile(id, filename, user1, songName, likes, plays, isPublic);
                            songlist.add(a);
                            System.out.println(a.getSongName());
                        }
                        getImagesSong(songlist);
                    } catch (JSONException e) {
                        Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                        e.printStackTrace();
                    }
                }, error -> Log.e("PARSE_USR_PLAYLIST_ERR", Objects.requireNonNull(error.getMessage()))) {
        };
        stringRequest1.setShouldCache(false);
        queue.add(stringRequest1);
    }

    public void getImagesSong(List<AudioFile> a) {
        for(int i=0; i<a.size(); i++){
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
                    song_adapter = new HomeSongRecyclerViewAdapter(songlist, requireContext(),loggedInUser);
                    song_adapter.setLoggedinUser(user.getUsername());
                    song_recyclerView.setAdapter(song_adapter);
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }

    public boolean isRecyclerScrollable(RecyclerView recyclerView) {
        song_recyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (layoutManager == null || adapter == null) return false;
        return layoutManager.findLastCompletelyVisibleItemPosition() < adapter.getItemCount() - 1;
    }
}
