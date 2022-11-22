package com.example.musiccircle;

import android.os.Build;

import com.example.musiccircle.Activities.Music_Player_Page.MusicPlayerActivity;
import com.example.musiccircle.Entity.Comment;
import com.example.musiccircle.Fragments.Comments.CommentFragmentRequests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class CommentFragmentUnitTest {

    @Mock
    CommentFragmentRequests requests;

    @Test
    public void test() {
        List<Comment> commentList = new ArrayList<Comment>();
        commentList.add(new Comment("Comment text 1", (long) 1, "Commenter 1"));
        commentList.add(new Comment("Comment text 2", (long) 1, "Commenter 2"));
        commentList.add(new Comment("Comment text 3\nWith multiple lines\nto see how that works", (long) 1, "Commenter 1"));
        commentList.add(new Comment("Comment text 4 but I test what happens when the text is naturally too long to be on one line", (long) 1, "Commenter 3"));


        when(requests.getComments("song_id")).thenReturn(commentList);
        doNothing().when(requests).postComment("","song_id","user_username");

        MusicPlayerActivity activity = Robolectric.buildActivity(MusicPlayerActivity.class).create().get();

        verify(requests).getComments("song_id");
    }
}
