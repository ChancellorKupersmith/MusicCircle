package com.example.musiccircle.Fragments.Comments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musiccircle.Activities.Music_Player_Page.CommentRecyclerAdapter;
import com.example.musiccircle.Entity.Comment;
import com.example.musiccircle.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommentsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommentsListFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SONG_ID_KEY = "song_id";
    private static final String USER_USERNAME_KEY = "user_username";

    // Bundle parameters
    private Long songId;
    private String user_username;

    protected RecyclerView recyclerView;
    protected View view;
    protected List<Comment> commentList;

    protected CommentFragmentRequests serverRequester;

    public CommentsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param songId Parameter 1.
     * @return A new instance of fragment CommentsFragment.
     */
    public static CommentsListFragment newInstance(Long songId, String username) {
        CommentsListFragment fragment = new CommentsListFragment();
        Bundle args = new Bundle();
        args.putLong(SONG_ID_KEY, songId);
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_comments_list, container, false);

        serverRequester = new CommentFragmentRequests(getActivity());
        initializeList();

        recyclerView = view.findViewById(R.id.comments_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CommentRecyclerAdapter(commentList, serverRequester));

        return view;
    }

    private void initializeList() {
        commentList = serverRequester.getComments(getArguments().getLong(SONG_ID_KEY));
    }
}