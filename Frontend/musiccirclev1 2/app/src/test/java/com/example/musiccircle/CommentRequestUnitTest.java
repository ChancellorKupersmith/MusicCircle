package com.example.musiccircle;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.RequestQueue;
import com.example.musiccircle.Activities.Music_Player_Page.MusicPlayerActivity;
import com.example.musiccircle.Fragments.Comments.CommentFragmentRequests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.Robolectric;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentRequestUnitTest {

    public final String AUDIOFILEID_KEY = "1";
    public final String USERNAME_KEY = "user_username";

    @Mock
    Context mockContext;
    @Mock
    FragmentActivity activity;

    @Test
    public void testGetComments() {
        CommentFragmentRequests serverRequest = new CommentFragmentRequests(activity);

        System.out.println(serverRequest.getComments(AUDIOFILEID_KEY));
    }

    @Test
    public void testPostComments() {
        CommentFragmentRequests serverRequest = new CommentFragmentRequests(activity);

        serverRequest.postComment("New Comment", AUDIOFILEID_KEY, USERNAME_KEY);
    }
}
