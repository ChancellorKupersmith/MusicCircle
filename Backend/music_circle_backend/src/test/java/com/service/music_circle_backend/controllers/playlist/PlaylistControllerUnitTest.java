package com.service.music_circle_backend.controllers.playlist;

import com.service.music_circle_backend.entities.playlist.Playlist;
import com.service.music_circle_backend.entities.user.User;
import com.service.music_circle_backend.repos.audio_file.AudioFileRepository;
import com.service.music_circle_backend.repos.comment.CommentRepository;
import com.service.music_circle_backend.repos.event.EventRepository;
import com.service.music_circle_backend.repos.genre.GenreRepository;
import com.service.music_circle_backend.repos.playlist.AlbumRepository;
import com.service.music_circle_backend.repos.playlist.PlaylistRepository;
import com.service.music_circle_backend.repos.user.GroupRepository;
import com.service.music_circle_backend.repos.user.UserRepository;
import com.service.music_circle_backend.services.audio_file.AudioFileService;
import com.service.music_circle_backend.services.playlist.AlbumService;
import com.service.music_circle_backend.services.playlist.PlaylistService;
import com.service.music_circle_backend.services.user.GroupService;
import com.service.music_circle_backend.services.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest
public class PlaylistControllerUnitTest {
    @Autowired
    private MockMvc controller;

    @MockBean
    private PlaylistService playlistService;

    @MockBean
    private AudioFileRepository audioFileRepository;
    @MockBean
    private AudioFileService audioFileService;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private EventRepository eventRepository;
    @MockBean
    private GroupRepository groupRepository;
    @MockBean
    private GroupService groupService;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private AlbumRepository albumRepository;
    @MockBean
    private AlbumService albumService;
    @MockBean
    private PlaylistRepository playlistRepository;

    private Playlist playlist1, playlist2, playlist3;


    @Before
    public void setup(){
        User user1 = Mockito.mock(User.class);
        playlist1 = new Playlist("playlist1", user1.getUsername());
        playlist2 = new Playlist("playlist2", user1.getUsername());
        playlist3 = new Playlist("playlist3", user1.getUsername());

    }

    @Test
    public void testPlaylistController() throws Exception{
        List<Playlist> playlistsList = new ArrayList<Playlist>();
        playlistsList.add(playlist1);
        playlistsList.add(playlist2);
        Mockito.when(playlistService.all()).thenReturn(playlistsList);
        Mockito.when(playlistService.one((long) 1)).thenReturn(playlist1);
        Mockito.when(playlistService.one((long) 2)).thenReturn(playlist2);
        Mockito.when(playlistService.one((long) 3)).thenReturn(playlist3);
        //Mockito.when(playlistService.uploadPlaylist(playlist1)).thenReturn(ResponseEntity.ok("Uploaded playlist successfully"));
        Mockito.when(playlistService.uploadPlaylistWithSongs("Playlist 1 name", "Creator1", "1,2,3")).thenReturn(ResponseEntity.ok("Uploaded playlist successfully"));
        //Mockito.when(playlistService.addSongs((long) 1, "1,2,3")).thenReturn(ResponseEntity.ok("Successfully added songs"));
        Mockito.when(playlistService.removeSongs((long) 1, "1,2,3")).thenReturn(ResponseEntity.ok("Successfully removed songs"));

        controller.perform(get("/playlists"));
    }
}
