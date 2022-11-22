package com.service.music_circle_backend.services.playlist;

import com.service.music_circle_backend.entities.playlist.Playlist;
import com.service.music_circle_backend.entities.user.User;
import com.service.music_circle_backend.messages.ResponseMessage;
import com.service.music_circle_backend.repos.audio_file.AudioFileRepository;
import com.service.music_circle_backend.repos.playlist.PlaylistRepository;
import com.service.music_circle_backend.services.user.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class PlaylistServiceUnitTest {
    @TestConfiguration
    static class PlaylistServiceTestConfig{
        @Bean
        public PlaylistServiceInterface playlistServiceInterface(){
            return new PlaylistService();
        }
        @Bean
        Playlist getPlaylist(){ return Mockito.mock(Playlist.class); }
        @Bean
        User getUser(){ return Mockito.mock(User.class); }

        @Bean
        ResponseMessage getMessage(){ return  Mockito.mock(ResponseMessage.class); }




    }
    @Autowired
    private PlaylistServiceInterface playlistService;
    @MockBean
    private PlaylistRepository playlistRepository;
    @MockBean
    private AudioFileRepository audioFileRepository;
    @MockBean
    private UserService userService;
    @Autowired
    private Playlist playlist1;
    @Autowired
    private User user1;
    @Autowired
    private ResponseMessage message;

    @Test
    public void testPlaylistService(){
        List<Playlist> playlistList = new ArrayList<Playlist>();
        playlistList.add(playlist1);
        Playlist playlist2 = Mockito.mock(Playlist.class);

        Mockito.when(playlistRepository.findAll()).thenReturn(playlistList);
        Mockito.when(playlistRepository.findById((long)1)).thenReturn(java.util.Optional.ofNullable(playlist1));
        Mockito.when(playlistRepository.save(playlist1)).thenReturn(playlist1);
        Mockito.when(userService.getUser("Test Username")).thenReturn(user1);
        Mockito.when(playlist1.toString()).thenReturn("playlist1");
        //Mockito.when(new ResponseMessage("Uploaded playlist successfully: " + playlist1.toString())).thenReturn(message);
        Mockito.when(playlist1.toString()).thenReturn("playlist1");
        Assert.assertEquals(playlistList, playlistService.all());
        Assert.assertEquals(playlist1, playlistService.one((long)1));
        //Assert.assertEquals(ResponseEntity.status(HttpStatus.OK).body(message), playlistService.uploadPlaylist(playlist1));

    }

}
