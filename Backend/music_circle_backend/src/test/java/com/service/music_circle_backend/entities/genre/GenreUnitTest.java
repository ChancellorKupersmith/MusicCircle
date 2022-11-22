package com.service.music_circle_backend.entities.genre;

import com.service.music_circle_backend.entities.audio_file.AudioFile;
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
public class GenreUnitTest {
    @TestConfiguration
    static class GenreTestConfig{
        @Bean
        AudioFile newSong(){ return Mockito.mock(AudioFile.class); }
    }

    @Autowired
    AudioFile song;
    @Test
    public void testGenreEntity(){
        List<AudioFile> mockSongList = new ArrayList<AudioFile>();
        mockSongList.add(song);
        Genre genre = new Genre("Test Genre");
        Assert.assertEquals("Test Genre", genre.getName());
        Assert.assertEquals(0, genre.getSongs().size());
        genre.getSongs().add(song);
        Assert.assertEquals(song, genre.getSongs().get(0));
        AudioFile song2 = Mockito.mock(AudioFile.class);
        mockSongList.add(song2);
        genre.setSongs(mockSongList);
        Assert.assertEquals(mockSongList, genre.getSongs());
        Genre genre2 = new Genre("Test Genre");
        Assert.assertEquals(true, genre.equals(genre2));


    }
}
