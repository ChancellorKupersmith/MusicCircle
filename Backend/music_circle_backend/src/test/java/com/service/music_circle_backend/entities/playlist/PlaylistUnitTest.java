package com.service.music_circle_backend.entities.playlist;

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

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class PlaylistUnitTest {
    @TestConfiguration
    static class AlbumTestConfig{
        @Bean
        public Playlist newPlaylist(){ return new Playlist("Test Playlist Name", getUser()); }
        @Bean
        User getUser(){ return Mockito.mock(User.class); }
        @Bean
        AudioFile newAudioFile(){ return Mockito.mock(AudioFile.class); }
    }
    @Autowired
    private User creator;
    @Autowired
    private AudioFile song;
    @Autowired
    private Playlist playlist;

    @Test
    public void testPlaylist(){
        Mockito.when(creator.getUsername()).thenReturn("Test Artist Username");
        Mockito.when(song.getFilename()).thenReturn("Test/AudioFile/Filename");
        List<AudioFile> mockSongs = new ArrayList<AudioFile>();
        mockSongs.add(song);
        List<Long> mockListeners = new ArrayList<Long>();
        mockListeners.add(creator.getId());

        Assert.assertEquals("Test Playlist Name", playlist.getName());
        Assert.assertEquals("Test Artist Username", playlist.getCreator());
        Assert.assertEquals(1, playlist.getListeners().size());
        Assert.assertEquals(true, playlist.getSongs().isEmpty());
        playlist.setName("Test Playlist Name 2");
        Assert.assertEquals("Test Playlist Name 2", playlist.getName());
        User creator2 = Mockito.mock(User.class);
        playlist.setCreator(creator2);
        Assert.assertEquals(false, (creator2.getId() == playlist.getCreator()));
        Assert.assertEquals(creator2, playlist.getCreator());
        mockListeners.add(creator2.getId());
        playlist.setListeners(mockListeners);
        Assert.assertEquals(2, playlist.getListeners().size());
        AudioFile song2 = Mockito.mock(AudioFile.class);
        mockSongs.add(song2);
        playlist.setSongs(mockSongs);
        Assert.assertEquals(mockSongs, playlist.getSongs());
    }

}
