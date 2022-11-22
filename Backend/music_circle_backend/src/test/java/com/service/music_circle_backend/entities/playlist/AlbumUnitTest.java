package com.service.music_circle_backend.entities.playlist;

import com.service.music_circle_backend.entities.audio_file.AudioFile;
import com.service.music_circle_backend.entities.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class AlbumUnitTest {
    @TestConfiguration
    static class AlbumTestConfig{
        @Bean
        public Album newAlbum(){ return new Album("Test Album Name", getUser().getUsername(), newAudioFile()); }
        @Bean
        User getUser(){ return Mockito.mock(User.class); }
        @Bean
        AudioFile newAudioFile(){ return Mockito.mock(AudioFile.class); }
    }
    @Autowired
    private User artist;
    @Autowired
    private AudioFile song;
    @Autowired
    private Album album;


    @Test
    public void testAlbumEntity(){
        Mockito.when(artist.getUsername()).thenReturn("Test Artist Username");
        Mockito.when(song.getFilename()).thenReturn("Test/AudioFile/Filename");

        List<AudioFile> mockSongs = new ArrayList<AudioFile>();
        mockSongs.add(song);
        Assert.assertEquals("Test Album Name", album.getAlbumName());
        Assert.assertEquals("Test Artist Username", album.getArtist());
        Assert.assertEquals(true, album.isSingle());
        album.setSingle(false);
        Assert.assertEquals(false, album.isSingle());
        Assert.assertEquals(mockSongs.get(0).getFilename(), album.getTracklist().get(0).getFilename());
        AudioFile newSong = Mockito.mock(AudioFile.class);
        mockSongs.add(newSong);
        album.getTracklist().add(newSong);
        Assert.assertEquals(2, album.getTracklist().size());
        Assert.assertEquals(mockSongs.get(1), album.getTracklist().get(1));
        album.setSingle(true);
        Assert.assertEquals(false, album.isSingle());
        album.setTracklist(mockSongs);
        Assert.assertEquals(mockSongs, album.getTracklist());


    }
}
