package com.example.musiccircle.Fragments.Comments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musiccircle.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommentsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SONG_ID_KEY = "song_id";
    private static final String USER_USERNAME_KEY = "user_username";

    // TODO: Rename and change types of parameters
    private Long songId;
    private String user_username;

    public CommentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Id of audio file.
     * @param username username of current user.
     * @return A new instance of fragment CommentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommentsFragment newInstance(Long id, String username) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putLong(SONG_ID_KEY, id);
        args.putString(USER_USERNAME_KEY, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            songId = getArguments().getLong(SONG_ID_KEY);
            user_username = getArguments().getString(USER_USERNAME_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.Comments_List_Placeholder, CommentsListFragment.newInstance(songId, user_username));
        ft.replace(R.id.Post_Comments_Placeholder, PostCommentsFragment.newInstance(songId, user_username));
        ft.commit();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comments, container, false);
    }
}