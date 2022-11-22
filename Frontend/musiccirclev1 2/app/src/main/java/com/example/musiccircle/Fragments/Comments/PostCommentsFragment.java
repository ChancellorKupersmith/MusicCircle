package com.example.musiccircle.Fragments.Comments;


import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Activities.Music_Player_Page.CommentRecyclerAdapter;
import com.example.musiccircle.Activities.Music_Player_Page.MusicPlayerActivity;
import com.example.musiccircle.Entity.Comment;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostCommentsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SONG_ID_KEY = "song_id";
    private static final String USER_USERNAME_KEY = "user_username";

    // Bundle parameters
    private Long mParam1;
    private String mParam2;

    public EditText writeText;
    protected Button postButton;
    protected View view;
    private Animation commentTranslateUp, commentTranslateDown;



    protected CommentFragmentRequests serverRequester;

    public PostCommentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param songId Parameter 1.
     * @return A new instance of fragment CommentsFragment.
     */
    public static PostCommentsFragment newInstance(Long songId, String username) {
        PostCommentsFragment fragment = new PostCommentsFragment();
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
            mParam1 = getArguments().getLong(SONG_ID_KEY);
            mParam2 = getArguments().getString(USER_USERNAME_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_post_comment, container, false);

        serverRequester = new CommentFragmentRequests(getActivity());

        writeText = view.findViewById(R.id.write_comment_text);
        postButton = view.findViewById(R.id.post_comment_button);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post();
            }
        });

        return view;
    }

    /**
     * Use Volley to post a new comment to the database
     */

    public void post() {
        final String commentText = this.writeText.getText().toString();

        serverRequester.postComment(commentText, getArguments().getLong(SONG_ID_KEY), getArguments().getString(USER_USERNAME_KEY));
    }

}
