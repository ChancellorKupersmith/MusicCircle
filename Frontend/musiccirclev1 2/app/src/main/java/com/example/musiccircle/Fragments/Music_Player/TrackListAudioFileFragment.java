package com.example.musiccircle.Fragments.Music_Player;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.R;
import com.example.musiccircle.Fragments.Music_Player.dummy.DummyContent;
import com.example.musiccircle.RecyclerViewAdapters.MyAudioFileRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class TrackListAudioFileFragment extends Fragment  implements MyAudioFileRecyclerViewAdapter.TrackListListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TRACK_LIST_KEY = "tracklist";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private ArrayList<AudioFile> tracklist;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TrackListAudioFileFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TrackListAudioFileFragment newInstance(int columnCount, ArrayList<AudioFile> tracklist) {
        TrackListAudioFileFragment fragment = new TrackListAudioFileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putSerializable(TRACK_LIST_KEY, tracklist);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            tracklist = (ArrayList<AudioFile>) (getArguments().getSerializable(TRACK_LIST_KEY));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_list_audio_file_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyAudioFileRecyclerViewAdapter(tracklist, this));
        }
        return view;
    }

    private static final String PLAY_SONG_AT_INDEX_KEY = "play_song_at_index";
    @Override
    public void onTrackClick(int position) {
        Intent playSongAt = new Intent("play-song-at");
        playSongAt.putExtra(PLAY_SONG_AT_INDEX_KEY, position);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(playSongAt);
    }
}