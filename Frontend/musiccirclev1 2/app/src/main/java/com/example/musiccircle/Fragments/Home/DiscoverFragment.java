package com.example.musiccircle.Fragments.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.NavType;
import androidx.navigation.Navigation;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
//
//import com.example.musiccircle.DiscoverFragmentArgs;
//import com.example.musiccircle.DiscoverFragmentDirections;
import com.example.musiccircle.Activities.Music_Player_Page.MusicPlayerActivity;
import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoverFragment extends Fragment {
    //Bundle Keys
    private static final String USER_USERNAME_KEY = "user_username";
    private static final String TRACKLIST_KEY = "track_list";

    //Bundle Variables
    private String user_username;

    //View Variables
    Button go_to_search;
    Button go_to_create_group;
    Button go_to_upload_album;
    Button go_to_create_event;
    Button go_to_music_player;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user_username User username.
     * @return A new instance of fragment DiscoverFragment.
     */
    public static DiscoverFragment newInstance(String user_username) {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle args = new Bundle();
        args.putString(USER_USERNAME_KEY, user_username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user_username = getArguments().getString(USER_USERNAME_KEY);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        user_username = DiscoverFragmentArgs.fromBundle(getArguments()).getUserUsername();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        go_to_search = view.findViewById(R.id.gotosearch);
        go_to_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = DiscoverFragmentDirections.actionDiscoverFragmentToSearchAllFragment(user_username);
                Navigation.findNavController(view).navigate(action);
            }
        });

        go_to_create_group = view.findViewById(R.id.gotocreategroup);
        go_to_create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = DiscoverFragmentDirections.actionDiscoverFragmentToCreateGroupFragment(user_username);
                Navigation.findNavController(view).navigate(action);
            }
        });

        go_to_upload_album = view.findViewById(R.id.gotoUploadAlbum);
        go_to_upload_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = DiscoverFragmentDirections.actionDiscoverFragmentToUploadAlbumFrag(user_username);
                Navigation.findNavController(view).navigate(action);
            }
        });

        go_to_create_event = view.findViewById(R.id.gotocreateevent);
        go_to_create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_discoverFragment_to_createEventFragment);
            }
        });

        go_to_music_player = view.findViewById(R.id.gotomusicplayer);
        go_to_music_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MusicPlayerActivity.class);
                Bundle bundle = new Bundle();
                ArrayList<AudioFile> trackList = new ArrayList<AudioFile>();
                User artist = new User();
                artist.setFirstName("Gorillaz");
                artist.setLastName("");
                User artist2 = new User();
                artist2.setFirstName("J.");
                artist2.setLastName("Cole");
                AudioFile song1 = new AudioFile((long) 4, "filename", artist2, "KOD", 0, 0, true);
                AudioFile song2 = new AudioFile((long) 3, "filename", artist, "DARE", 0, 0, true);
                trackList.add(song1);
                trackList.add(song2);
                bundle.putSerializable(TRACKLIST_KEY, trackList);
                bundle.putString(USER_USERNAME_KEY, "chancek1");
                intent.putExtras(bundle);
                startActivity(intent);
                //Navigation.findNavController(view).navigate(R.id.action_discoverFragment_to_musicPlayerActivity);
            }
        });

        return view;
    }
}