package com.example.musiccircle.Fragments.Login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.example.musiccircle.LoginFragmentDirections;
import com.example.musiccircle.Activities.Home_Page.NavMenuActivity;
import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.Entity.Event;
import com.example.musiccircle.Entity.Genre;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.R;
import com.example.musiccircle.Services.AudioPlayerService;
import com.example.musiccircle.ViewDialog;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {
    private static final String USER_KEY = "user";
    private static final String TRACKLIST_KEY = "track_list";
    private Button loginButton;
    private EditText username, password;
    View view;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, container, false);
        Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out);
        view.startAnimation(anim);
        loginButton = view.findViewById(R.id.btn_login_2);
        username = view.findViewById(R.id.login_username);
        password = view.findViewById(R.id.login_password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        /**
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.action_fragment_3_to_fragment_1,null, getNavOptions());
            }
        });
         **/

        return view;
    }

    protected NavOptions getNavOptions() {

        return new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in)
                .build();
    }

    private void Login(){

        final String username = this.username.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        setUserOnline(queue, username);
        StringRequest sr = new StringRequest(Request.Method.POST,"http://10.24.227.244:8080/user/login",
                response -> {
                    if(response.equals("SUCCESSFUL USER LOGIN")){
                        ArrayList<AudioFile> tracklist = new ArrayList<AudioFile>();
                        Intent startAudioService = new Intent(getContext(), AudioPlayerService.class);
                        Bundle serviceArgs = new Bundle();
                        serviceArgs.putSerializable(TRACKLIST_KEY, tracklist);
                        serviceArgs.putString("username", username);
                        startAudioService.putExtras(serviceArgs);
                        Util.startForegroundService(getContext(), startAudioService);
                        getUser(username, tracklist);

                    }else{
                        ViewDialog alert = new ViewDialog();
                        alert.showDialog(requireContext(),response);
                    }
                    Log.e("HttpClient", "success! response: " + response);
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        sr.setShouldCache(false);
        queue.add(sr);
    }

    public void setUserOnline(RequestQueue queue, String username){
        StringRequest setOnline = new StringRequest(Request.Method.PUT,"http://10.24.227.244:8080/userOnline/"+ username,
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

    public void getUser(String username, ArrayList<AudioFile> tracklist){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final User[] loggedInUser = new User[1];
        StringRequest sr = new StringRequest(Request.Method.GET, "http://10.24.227.244:8080/user/find/" + username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            loggedInUser[0] = new User();
                            JSONObject JSUONUser = new JSONObject(response);
                            loggedInUser[0].setArtistName(JSUONUser.getString("artistName"));
                            loggedInUser[0].setId(JSUONUser.getLong("id"));
                            loggedInUser[0].setBio(JSUONUser.getString("bio"));
                            loggedInUser[0].setState(JSUONUser.getString("state"));
                            loggedInUser[0].setCity(JSUONUser.getString("city"));
                            //get user uploads
                            ArrayList<AudioFile> uploadedSongs = new ArrayList<AudioFile>();
                            JSONArray JSONUploads = JSUONUser.getJSONArray("uploadedAlbums");
                            for(int i=0; i< JSONUploads.length(); i++){
                                JSONObject JSONSong = JSONUploads.getJSONObject(i);
                                AudioFile song = getSong(JSONSong);
                                song.setArtist(loggedInUser[0]);
                                uploadedSongs.add(song);
                            }
                            loggedInUser[0].setUploadedSongs(uploadedSongs);
                            //get user events
                            /**
                            ArrayList<Event> userEvents = new ArrayList<Event>();
                            JSONArray JSONAttendingEvents = JSUONUser.getJSONArray("attendingEvents");
                            for(int i=0; i<JSONAttendingEvents.length(); i++){
                                JSONObject JSONEvent = JSONAttendingEvents.getJSONObject(i);
                                Event event = getEvent(JSONEvent);
                                userEvents.add(event);

                            }
                            JSONArray JSONCreatedEvents = JSUONUser.getJSONArray("createdEvents");
                            for(int i=0; i<JSONCreatedEvents.length(); i++){
                                JSONObject JSONEvent = JSONCreatedEvents.getJSONObject(i);
                                Event event = getEvent(JSONEvent);
                                userEvents.add(event);
                            }
                            loggedInUser[0].setUserEvents(userEvents);
                             **/
                            //get user followers
                            ArrayList<String> follower_usernames = new ArrayList<String>();
                            JSONArray JSONFollowers = JSUONUser.getJSONArray("follower_usernames");
                            for(int i=0; i < JSONFollowers.length(); i++){
                                follower_usernames.add(JSONFollowers.getString(i));
                            }
                            loggedInUser[0].setFollower_usernames(follower_usernames);
                            //get following
                            ArrayList<String> following_usernames = new ArrayList<String>();
                            JSONArray JSONFollowing = JSUONUser.getJSONArray("following_usernames");
                            for(int i=0; i<JSONFollowing.length(); i++){
                                following_usernames.add(JSONFollowing.getString(i));
                            }
                            loggedInUser[0].setFollowing_usernames(following_usernames);



                            Intent i = new Intent(getContext(), NavMenuActivity.class);
                            Bundle b = new Bundle();

                            b.putSerializable(TRACKLIST_KEY, tracklist);
                            loggedInUser[0].setUsername(username);
                            b.putString("name", loggedInUser[0].getArtistName());
                            b.putSerializable(USER_KEY, loggedInUser[0]);
                            i.putExtras(b);
                            startActivity(i);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //fragment_3Directions.ActionFragment3ToFragment4 action = fragment_3Directions.actionFragment3ToFragment4(username);
                        //Navigation.findNavController(view).navigate(action);

                        Log.e("HttpClient", "success! response: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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
        sr.setShouldCache(false);
        requestQueue.add(sr);
    }

    /**
    public Event getEvent(JSONObject JSONEvent) throws JSONException {
        Event event = new Event();
        long id = JSONEvent.getLong("id");
        String eventCreator = JSONEvent.getString("event_creator");
        String name = JSONEvent.getString("name");
        String streetAddress = JSONEvent.getString("streetAddress");
        String state = JSONEvent.getString("state");
        String city = JSONEvent.getString("city");
        String country = JSONEvent.getString("country");
        String zipcode = JSONEvent.getString("zipcode");
        String location = JSONEvent.getString("location");
        String dateTime = JSONEvent.getString("dateTime");
        String description = JSONEvent.getString("description");
        event.setId(id);
        event.setEventCreator(eventCreator);
        event.setName(name);
        event.setEventLocation(location);
        event.setStreetAddress(streetAddress);
        event.setState(state);
        event.setCity(city);
        event.setDateTime(dateTime);
        event.setDescription(description);
        event.setCountry(country);
        event.setZipcode(zipcode);
        return event;
    }
     **/

    public AudioFile getSong(JSONObject JSONSong) throws JSONException {
        boolean hasGenre = false;
        long id = JSONSong.getLong("id");
        //filename, artist, name, likes, plays, isPublic, album
        String filename = JSONSong.getString("filename");
        String name = JSONSong.getString("songName");
        int likes = JSONSong.getInt("likes");
        boolean isPublic = JSONSong.getBoolean("isPublic");
        JSONObject JSONGenre = JSONSong.getJSONObject("genre");
        Genre genre = null;
        if(JSONGenre != null){
            genre = new Genre(JSONGenre.getString("name"));
            genre.setId(JSONGenre.getLong("id"));
            hasGenre = true;
        }
        //Might include album depending on speed
//                                    JSONObject JSONAlbum = JSONSong.getJSONObject("album");
//                                    String albumName = JSONAlbum.getString("albumName");

        AudioFile song = new AudioFile();
        song.setSongName(name);
        song.setFilename(filename);
        song.setLikes(likes);
        song.setPublic(isPublic);
        song.setId(id);
        if(hasGenre){
            song.setGenre(genre);
        }
        return song;
    }
}