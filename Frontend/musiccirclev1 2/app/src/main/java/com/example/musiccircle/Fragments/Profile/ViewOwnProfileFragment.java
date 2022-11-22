package com.example.musiccircle.Fragments.Profile;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Entity.Album;
import com.example.musiccircle.Entity.AudioFile;
//import com.example.musiccircle.Entity.AudioFileInfo;
import com.example.musiccircle.Entity.Event;
import com.example.musiccircle.Entity.Group;
import com.example.musiccircle.Entity.Playlist;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.R;
import com.example.musiccircle.RecyclerViewAdapters.ProfileAlbumsRecyclerAdapter;
import com.example.musiccircle.RecyclerViewAdapters.ProfileEventsRecyclerAdapter;
import com.example.musiccircle.RecyclerViewAdapters.ProfileGroupsRecyclerAdapter;
import com.example.musiccircle.RecyclerViewAdapters.ProfilePlaylistsRecyclerAdapter;
import com.example.musiccircle.RecyclerViewAdapters.ProfileSongsRecyclerAdapter;
import com.example.musiccircle.RecyclerViewAdapters.ProfileUsersRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewOwnProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewOwnProfileFragment extends Fragment {

    private static final String USER_USERNAME_KEY = "user_username";
    private static final String USER_KEY = "user";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView profilePicView;
    private TextView usernameText;
    private TextView descriptionText;
    private Button followersNum;
    private Button followersText;
    private Button followingNum;
    private Button followingText;
    private Button groupsNum;
    private Button groupsText;
    private Button eventsNum;
    private Button eventsText;
    private Button editProfileButton;

    private RecyclerView followersRecycler;
    private RecyclerView followingRecycler;
    private RecyclerView groupsRecycler;
    private RecyclerView eventsRecycler;
    private List<User> followersList = null;
    private List<User> followingList = null;
    private List<Group> groupsList = null;
    private List<Event> eventsList = null;

    private RecyclerView topSongsRecycler;
    private RecyclerView albumsRecycler;
    private RecyclerView recentSongsRecycler;
    private RecyclerView playlistsRecycler;
    private List<AudioFile> topSongsList = null;
    private List<Album> albumsList = null;
    private List<AudioFile> recentSongsList = null;
    private List<Playlist> playlistsList = null;

    private View view;

    // TODO: Rename and change types of parameters
    private User loggedInUser;
    private String loggedin_profile_username;

    private User userToDisplay = null;

    public ViewOwnProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedInUser Parameter 1.
     * @param username Parameter 2.
     * @return A new instance of fragment ProfileInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewOwnProfileFragment newInstance(User loggedInUser, String username) {
        ViewOwnProfileFragment fragment = new ViewOwnProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER_KEY, loggedInUser);
        args.putString(USER_USERNAME_KEY, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loggedInUser = (User)(getArguments().getSerializable(USER_KEY));
            loggedin_profile_username = getArguments().getString(USER_USERNAME_KEY);
        }
        initializeLists();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_own_user_profile, container, false);


        profilePicView = view.findViewById(R.id.imageview_account_profile);
        usernameText = view.findViewById(R.id.profile_username);
        descriptionText = view.findViewById(R.id.profile_description);

        followersNum = view.findViewById(R.id.button_num_user_followers);
        followersNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take you to followers page
            }
        });
        followersText = view.findViewById(R.id.button_user_followers_text);
        followersText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take you to followers page
            }
        });

        followingNum = view.findViewById(R.id.button_num_user_following);
        followingNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take you to following page
            }
        });
        followingText = view.findViewById(R.id.button_user_following_text);
        followingText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take you to following page
            }
        });

        groupsNum = view.findViewById(R.id.button_num_user_groups);
        groupsNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take you to groups page
            }
        });
        groupsText = view.findViewById(R.id.button_user_groups_text);
        groupsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take you to groups page
            }
        });

        eventsNum = view.findViewById(R.id.button_num_user_events);
        eventsNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take you to events page
            }
        });
        eventsText = view.findViewById(R.id.button_user_events_text);
        eventsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take you to events page
            }
        });

        editProfileButton = view.findViewById(R.id.follow_user);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bring up profile editor
                System.out.println(topSongsList.size());
                System.out.println(albumsList.size());
                System.out.println(recentSongsList.size());
                System.out.println(playlistsList.size());
            }
        });



        topSongsRecycler = view.findViewById(R.id.recycler_top_songs);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        topSongsRecycler.setLayoutManager(layoutManager1);

        albumsRecycler = view.findViewById(R.id.recycler_albums);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        albumsRecycler.setLayoutManager(layoutManager2);

        recentSongsRecycler = view.findViewById(R.id.recycler_recent_songs);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recentSongsRecycler.setLayoutManager(layoutManager3);

        playlistsRecycler = view.findViewById(R.id.recycler_playlists);
        RecyclerView.LayoutManager layoutManager4 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        playlistsRecycler.setLayoutManager(layoutManager4);


        followersRecycler = view.findViewById(R.id.recycler_followers);
        RecyclerView.LayoutManager layoutManager5 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        followersRecycler.setLayoutManager(layoutManager5);

        followingRecycler = view.findViewById(R.id.recycler_following);
        RecyclerView.LayoutManager layoutManager6 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        followingRecycler.setLayoutManager(layoutManager6);

        groupsRecycler = view.findViewById(R.id.recycler_groups);
        RecyclerView.LayoutManager layoutManager7 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        groupsRecycler.setLayoutManager(layoutManager7);

        eventsRecycler = view.findViewById(R.id.recycler_events);
        RecyclerView.LayoutManager layoutManager8 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        eventsRecycler.setLayoutManager(layoutManager8);

        getUserInfo();
        initializeLists();

        return view;
    }

    private void initializeLists() {

//        topSongsList = new ArrayList<>();
//        albumsList = new ArrayList<>();
//        recentSongsList = new ArrayList<>();
//        playlistsList = new ArrayList<>();
//        followersList = new ArrayList<>();
//        followingList = new ArrayList<>();
//        groupsList = new ArrayList<>();
//        eventsList = new ArrayList<>();

        // Some test cases for top songs
//        topSongsList.add(new AudioFile((long) 1, "song1.mp3", "Song 1", 23, 47, true));
//        topSongsList.add(new AudioFile((long) 1, "song2.mp3", "Song 2", 23, 47, true));
//        topSongsList.add(new AudioFile((long) 1, "song3.mp3", "Song 3", 23, 47, true));
//        topSongsList.add(new AudioFile((long) 1, "song4.mp3", "Song 4", 23, 47, true));
//        topSongsList.add(new AudioFile((long) 1, "song5.mp3", "Song 5", 23, 47, true));

        // Grab items from User, likely need to be changed to fit updated user object
//        topSongsList = loggedInUser.getUploadedSongs();
//        albumsList = loggedInUser.getUploadedAlbums();
//        recentSongsList = loggedInUser.getUploadedSongs();
//        playlistsList = loggedInUser.getPlaylists();
//        followersList = loggedInUser.getFollowers();
//        followingList = loggedInUser.getFollowing();
//        groupsList = loggedInUser.getGroups();
//        eventsList = loggedInUser.getEvents();

        String httpBaseLink = "http://10.24.227.244:8080";
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.getCache().clear();
        StringRequest sr1 = new StringRequest(Request.Method.GET, httpBaseLink + "/user/getTopUploads/" + loggedin_profile_username, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonResponse = new JSONArray(response);
                    topSongsList = new ArrayList<>();
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject jsonSong = jsonResponse.getJSONObject(i);
                        AudioFile file = new AudioFile();
                        file.setId(jsonSong.getLong("id"));
                        file.setSongName(jsonSong.getString("songName"));
                        //file.setArtist(jsonResponse.getString("artist"));
                        file.setPlays(jsonSong.getInt("plays"));
                        file.setLikes(jsonSong.getInt("likes"));
                        StringRequest imageReq = new StringRequest(Request.Method.GET, "http://10.24.227.244:8080/audioFiles/pic/" + file.getId().toString(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                file.setImage(Base64.decode(response, Base64.DEFAULT));
                                fillTopSongsRecycler();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }

                        });
                        queue.add(imageReq);
                        topSongsList.add(file);
                    }
                } catch (JSONException e) {
                    Log.e("ResponseJSONException", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        StringRequest sr2 = new StringRequest(Request.Method.GET, httpBaseLink + "/user/recentUpload?username=" + loggedin_profile_username, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponsePlaylist = new JSONObject(response);
                    JSONArray jsonResponse = jsonResponsePlaylist.getJSONArray("songs");
                    recentSongsList = new ArrayList<>();
                    for (int i = 0; i < jsonResponse.length(); i++){
                        JSONObject jsonSong = jsonResponse.getJSONObject(i);
                        AudioFile file = new AudioFile();
                        file.setId(jsonSong.getLong("id"));
                        file.setSongName(jsonSong.getString("songName"));
                        //file.setArtist(jsonSong.getString("artist"));
                        file.setPlays(jsonSong.getInt("plays"));
                        file.setLikes(jsonSong.getInt("likes"));
                        StringRequest imageReq = new StringRequest(Request.Method.GET, "http://10.24.227.244:8080/audioFiles/pic/" + file.getId().toString(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                file.setImage(Base64.decode(response, Base64.DEFAULT));
                                fillRecentSongsRecycler();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }

                        });
                        queue.add(imageReq);
                        recentSongsList.add(file);
                    }
                } catch (JSONException e) {
                    Log.e("ResponseJSONException", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        StringRequest sr3 = new StringRequest(Request.Method.GET, httpBaseLink + "/user/albums?username=" + loggedin_profile_username, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonResponse = new JSONArray(response);
                    albumsList = new ArrayList<>();
                    for (int i = 0; i < jsonResponse.length(); i++){
                        JSONObject jsonAlbum = jsonResponse.getJSONObject(i);
                        Album album = new Album();
                        album.setId(jsonAlbum.getLong("id"));
                        album.setAlbumName(jsonAlbum.getString("albumName"));
                        album.setArtist(loggedin_profile_username);
                        JSONArray jsonSongArray = jsonAlbum.getJSONArray("songs");
                        List<AudioFile> songList = new ArrayList<>();
                        for (int j = 0; j < jsonSongArray.length(); j++) {
                            JSONObject jsonSong = jsonResponse.getJSONObject(i);
                            AudioFile file = new AudioFile();
                            file.setId(jsonSong.getLong("id"));
                            file.setSongName(jsonSong.getString("songName"));
                            file.setPublic(jsonSong.getBoolean("isPublic"));
                            //file.setArtist(jsonSong.getString("artist"));
                            file.setPlays(jsonSong.getInt("plays"));
                            file.setLikes(jsonSong.getInt("likes"));
                            StringRequest imageReq = new StringRequest(Request.Method.GET, "http://10.24.227.244:8080/audioFiles/pic/" + file.getId().toString(), new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    file.setImage(Base64.decode(response, Base64.DEFAULT));
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }

                            });
                            queue.add(imageReq);
                            songList.add(file);
                        }
                        album.setTracklist(songList);
                        StringRequest imageReq = new StringRequest(Request.Method.GET, "http://10.24.227.244:8080/albums/down/album_pic?id=" + album.getId().toString(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                album.setImage(Base64.decode(response, Base64.DEFAULT));
                                fillAlbumsRecycler();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }

                        });
                        queue.add(imageReq);
                        albumsList.add(album);
                    }
                } catch (JSONException e) {
                    Log.e("ResponseJSONException", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        StringRequest sr4 = new StringRequest(Request.Method.GET, httpBaseLink + "/user/playlists/" + loggedin_profile_username, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonResponse = new JSONArray(response);
                    playlistsList = new ArrayList<>();
                    for (int i = 0; i < jsonResponse.length(); i++){
                        JSONObject jsonPlaylist = jsonResponse.getJSONObject(i);
                        Playlist playlist = new Playlist();
                        playlist.setId(jsonPlaylist.getLong("id"));
                        playlist.setCreator_username(loggedin_profile_username);
                        playlist.setName(jsonPlaylist.getString("name"));
                        JSONArray jsonSongArray = jsonPlaylist.getJSONArray("songs");
                        List<AudioFile> songList = new ArrayList<>();
                        for (int j = 0; j < jsonSongArray.length(); j++) {
                            JSONObject jsonSong = jsonResponse.getJSONObject(i);
                            AudioFile file = new AudioFile();
                            file.setId(jsonSong.getLong("id"));
                            file.setSongName(jsonSong.getString("songName"));
                            file.setPublic(jsonSong.getBoolean("isPublic"));
                            //file.setArtist(jsonSong.getString("artist"));
                            file.setPlays(jsonSong.getInt("plays"));
                            file.setLikes(jsonSong.getInt("likes"));
                            StringRequest imageReq = new StringRequest(Request.Method.GET, "http://10.24.227.244:8080/audioFiles/pic/" + file.getId().toString(), new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    file.setImage(Base64.decode(response, Base64.DEFAULT));
                                    fillPlaylistsRecycler();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }

                            });
                            queue.add(imageReq);
                            songList.add(file);
                        }
                        playlist.setSongs(songList);
                        if (songList.size() > 0)
                            playlistsList.add(playlist);
                    }
                } catch (JSONException e) {
                    Log.e("ResponseJSONException", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        sr1.setShouldCache(false);
        queue.add(sr1);
        sr2.setShouldCache(false);
        queue.add(sr2);
        sr3.setShouldCache(false);
        queue.add(sr3);
        sr4.setShouldCache(false);
        queue.add(sr4);
    }

    private void getUserInfo() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest sr = new StringRequest(Request.Method.GET, "http://10.24.227.244:8080/user/find/" + loggedin_profile_username, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    userToDisplay = new User();
                    userToDisplay.setUsername(jsonResponse.getString("artistName"));
                    usernameText.setText(userToDisplay.getUsername());
                    userToDisplay.setBio(jsonResponse.getString("bio"));
                    if (userToDisplay.getBio() == "null")
                        descriptionText.setText("");
                    else
                        descriptionText.setText(userToDisplay.getBio());

                    JSONArray jsonFollowers = jsonResponse.getJSONArray("followers");
                    followersList = new ArrayList<>();
                    for (int i = 0; i < jsonFollowers.length(); i++) {
                        JSONObject jsonUser = jsonFollowers.getJSONObject(i);
                        User follower = new User();
                        follower.setUsername(jsonUser.getString("username"));
                        StringRequest imageReq = new StringRequest(Request.Method.GET, "http://10.24.227.244:8080/user/pic/" + follower.getUsername(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                follower.setImage(Base64.decode(response, Base64.DEFAULT));
                                fillFollowersRecycler();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }

                        });
                        queue.add(imageReq);
                        followersList.add(follower);
                    }
                    followersNum.setText(followersList.size() + "");

                    JSONArray jsonFollowing = jsonResponse.getJSONArray("following");
                    followingList = new ArrayList<>();
                    for (int i = 0; i < jsonFollowing.length(); i++) {
                        JSONObject jsonUser = jsonFollowing.getJSONObject(i);
                        User following = new User();
                        following.setUsername(jsonUser.getString("username"));
                        StringRequest imageReq = new StringRequest(Request.Method.GET, "http://10.24.227.244:8080/user/pic/" + following.getUsername(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                following.setImage(Base64.decode(response, Base64.DEFAULT));
                                fillFollowingRecycler();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }

                        });
                        queue.add(imageReq);
                        followingList.add(following);
                    }
                    followingNum.setText(followingList.size() + "");

                    JSONArray jsonGroups = jsonResponse.getJSONArray("groups");
                    groupsList = new ArrayList<>();
                    for (int i = 0; i < jsonGroups.length(); i++) {
                        JSONObject jsonGroup = jsonGroups.getJSONObject(i);
                        Group group = new Group();
                        group.setName(jsonGroup.getString("name"));
                        StringRequest imageReq = new StringRequest(Request.Method.GET, "http://10.24.227.244:8080/group/pic/" + group.getId().toString(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                group.setImage(Base64.decode(response, Base64.DEFAULT));
                                fillGroupsRecycler();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }

                        });
                        queue.add(imageReq);
                        groupsList.add(group);
                    }
                    groupsNum.setText(groupsList.size() + "");

                    JSONArray jsonEventsIn = jsonResponse.getJSONArray("createdEvents");
                    eventsList = new ArrayList<>();
                    for (int i = 0; i < jsonEventsIn.length(); i++) {
                        JSONObject jsonEvent = jsonEventsIn.getJSONObject(i);
                        Event event = new Event();
                        event.setName(jsonEvent.getString("name"));
                        StringRequest imageReq = new StringRequest(Request.Method.GET, "http://10.24.227.244:8080/events/" + event.getId().toString() + "/get_pic", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                event.setImage(Base64.decode(response, Base64.DEFAULT));
                                fillEventsRecycler();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }

                        });
                        queue.add(imageReq);
                        eventsList.add(event);
                    }
                    JSONArray jsonEventsGoing = jsonResponse.getJSONArray("createdEvents");
                    for (int i = 0; i < jsonEventsGoing.length(); i++) {
                        JSONObject jsonEvent = jsonEventsGoing.getJSONObject(i);
                        Event event = new Event();
                        event.setName(jsonEvent.getString("name"));
                        StringRequest imageReq = new StringRequest(Request.Method.GET, "http://10.24.227.244:8080/events/" + event.getId().toString() + "/get_pic", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                event.setImage(Base64.decode(response, Base64.DEFAULT));
                                fillEventsRecycler();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }

                        });
                        queue.add(imageReq);
                        eventsList.add(event);
                    }
                    eventsNum.setText(eventsList.size() + "");
                } catch (JSONException e) {
                    Log.e("ResponseJSONException", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(sr);
    }

    private void fillTopSongsRecycler() {
//        ProfileSongsRecyclerAdapter.TrackListListener trackListListener1 = new ProfileSongsRecyclerAdapter.TrackListListener() {
//            @Override
//            public void onTrackClick(int position) {
//                // start music player
//            }
//        };
        topSongsRecycler.setAdapter(new ProfileSongsRecyclerAdapter(topSongsList, getContext(), loggedin_profile_username, loggedInUser));
    }
    private void fillAlbumsRecycler() {
//        ProfileAlbumsRecyclerAdapter.TrackListListener trackListListener2 = new ProfileAlbumsRecyclerAdapter.TrackListListener() {
//            @Override
//            public void onTrackClick(int position) {
//                // start music player
//            }
//        };
        albumsRecycler.setAdapter(new ProfileAlbumsRecyclerAdapter(albumsList, getContext(), loggedin_profile_username, loggedInUser));
    }
    private void fillRecentSongsRecycler() {
//        ProfileSongsRecyclerAdapter.TrackListListener trackListListener3 = new ProfileSongsRecyclerAdapter.TrackListListener() {
//            @Override
//            public void onTrackClick(int position) {
//                // start music player
//            }
//        };
        recentSongsRecycler.setAdapter(new ProfileSongsRecyclerAdapter(recentSongsList, getContext(), loggedin_profile_username, loggedInUser));
    }
    private void fillPlaylistsRecycler() {
//        ProfilePlaylistsRecyclerAdapter.TrackListListener trackListListener4 = new ProfilePlaylistsRecyclerAdapter.TrackListListener() {
//            @Override
//            public void onTrackClick(int position) {
//                // start music player
//            }
//        };
        playlistsRecycler.setAdapter(new ProfilePlaylistsRecyclerAdapter(playlistsList, getContext(), loggedin_profile_username, loggedInUser));
    }

    private void fillFollowersRecycler() {
//        ProfileUsersRecyclerAdapter.TrackListListener trackListListener5 = new ProfileUsersRecyclerAdapter.TrackListListener() {
//            @Override
//            public void onTrackClick(int position) {
//                //lead to profile page
//            }
//        };
        followersRecycler.setAdapter(new ProfileUsersRecyclerAdapter(followersList, getContext(), loggedin_profile_username, loggedInUser));
    }
    private void fillFollowingRecycler() {
//        ProfileUsersRecyclerAdapter.TrackListListener trackListListener6 = new ProfileUsersRecyclerAdapter.TrackListListener() {
//            @Override
//            public void onTrackClick(int position) {
//                //lead to profile page
//            }
//        };
        followingRecycler.setAdapter(new ProfileUsersRecyclerAdapter(followingList, getContext(), loggedin_profile_username, loggedInUser));
    }
    private void fillGroupsRecycler() {
//        ProfileGroupsRecyclerAdapter.TrackListListener trackListListener7 = new ProfileGroupsRecyclerAdapter.TrackListListener() {
//            @Override
//            public void onTrackClick(int position) {
//                //lead to profile page
//            }
//        };
        groupsRecycler.setAdapter(new ProfileGroupsRecyclerAdapter(groupsList, getContext()));
    }
    private void fillEventsRecycler() {
//        ProfileEventsRecyclerAdapter.TrackListListener trackListListener8 = new ProfileEventsRecyclerAdapter.TrackListListener() {
//            @Override
//            public void onTrackClick(int position) {
//                //lead to profile page
//            }
//        };
        eventsRecycler.setAdapter(new ProfileEventsRecyclerAdapter(eventsList, getContext()));
    }

}
