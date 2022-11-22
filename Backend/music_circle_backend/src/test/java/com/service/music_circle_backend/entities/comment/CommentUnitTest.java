package com.service.music_circle_backend.entities.comment;

import com.service.music_circle_backend.entities.audio_file.AudioFile;
import com.service.music_circle_backend.entities.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CommentUnitTest {
    @TestConfiguration
    static class CommentTestConfig{
        @Bean
        User newUser(){ return Mockito.mock(User.class); }
        @Bean
        AudioFile newAudioFile(){ return Mockito.mock(AudioFile.class); }
    }

    @Autowired
    User commenter;
    @Autowired
    AudioFile song;

    @Test
    public void testCommentEntity(){
        Comment comment = new Comment("Test Comment", commenter);
        Assert.assertEquals("Test Comment", comment.getText());
        Assert.assertEquals(commenter.getId(), comment.getCommenter());
        comment.setText("Test Comment 2");
        Assert.assertEquals("Test Comment 2", comment.getText());
        User commenter2 = Mockito.mock(User.class);
        comment.setCommenter(commenter2);
        Assert.assertEquals(commenter2.getId(), comment.getCommenter());
    }

}
