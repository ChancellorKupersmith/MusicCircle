package com.service.music_circle_backend.entities.user;
import com.service.music_circle_backend.entities.ShareContent;
import com.service.music_circle_backend.entities.audio_file.AudioFile;
import com.service.music_circle_backend.entities.event.Event;
import com.service.music_circle_backend.entities.playlist.Album;
import com.service.music_circle_backend.entities.playlist.Playlist;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@ApiModel(description = "Details about a user")
public class User implements ShareContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "unique id of the user")
    private Long id;

    @ApiModelProperty(notes = "user's username")
    private String username;

    @ApiModelProperty(notes = "user's email")
    private String email;

    @ApiModelProperty(notes = "user's password")
    private String password;

    @ApiModelProperty(notes = "creation date of the user")
    private Instant created;

    @ApiModelProperty(notes = "user's biography")
    private String bio;

    @ApiModelProperty(notes = "active status of the user")
    private boolean loggedIn = false;

    @OneToMany(fetch = FetchType.LAZY)
    @ApiModelProperty(notes = "user's uploaded songs")
    @JoinColumn
    private List<AudioFile> uploadedSongs;

    @OneToMany(fetch = FetchType.LAZY)
    @ApiModelProperty(notes = "user's created groups")
    @JoinColumn
    private List<Group> Groups;

    @OneToMany(fetch = FetchType.LAZY)
    @ApiModelProperty(notes = "user's uploaded albums")
    @JoinColumn
    private List<Album> uploadedAlbums;

    @OneToMany(fetch = FetchType.LAZY)
    @ApiModelProperty(notes = "user's created playlists")
    @JoinColumn
    private Set<Playlist> createdPlaylists;

    @OneToMany(fetch = FetchType.LAZY)
    @ApiModelProperty(notes = "user's playing events")
    @JoinColumn
    private List<Event> createdEvents;

    @OneToMany(fetch = FetchType.LAZY)
    @ApiModelProperty(notes = "user's attending events")
    @JoinColumn
    private List<Event> attendingEvents;

    private String artistName;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    @ApiModelProperty(notes = "user's user follows")
    private List<User> following;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    @ApiModelProperty(notes = "user's followers")
    private List<User> followers;

    private String picFilename;

    @ElementCollection(targetClass=String.class)
    private List<String> following_usernames;

    private String city;

    private String state;

    @ElementCollection(targetClass=String.class)
    private List<String> follower_usernames;

    private int topCounter;

    private boolean online;

    public User(Long id, String username, String email, String password, Instant created, String bio, boolean loggedIn) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.created = created;
        this.bio = bio;
        this.loggedIn = loggedIn;
        createdPlaylists = new HashSet<Playlist>();
    }

    public User(String username, String email, String password, String state, String city) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.state = state;
        this.city = city;
    }


    public User(String username, String email, String password, String picFilename) {
        this.username = username;
        this.email = email;
        this.picFilename = picFilename;
        this.password = password;
        createdPlaylists = new HashSet<Playlist>();
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        createdPlaylists = new HashSet<Playlist>();
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public List<AudioFile> getUploadedSongs() {
        return uploadedSongs;
    }

    public void setUploadedSongs(List<AudioFile> uploadedSongs) {
        this.uploadedSongs = uploadedSongs;
    }

    public List<Group> getGroups() {
        return Groups;
    }

    public void setGroups(List<Group> groups) {
        Groups = groups;
    }

    public List<Album> getUploadedAlbums() {
        return uploadedAlbums;
    }

    public void setUploadedAlbums(List<Album> uploadedAlbums) {
        this.uploadedAlbums = uploadedAlbums;
    }

    public Set<Playlist> getCreatedPlaylists() {
        return createdPlaylists;
    }

    public void setCreatedPlaylists(Set<Playlist> createdPlaylists) {
        this.createdPlaylists = createdPlaylists;
    }

    public List<Event> getCreatedEvents() {
        return createdEvents;
    }

    public void setCreatedEvents(List<Event> createdEvents) {
        this.createdEvents = createdEvents;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public List<Event> getAttendingEvents() {
        return attendingEvents;
    }

    public void setAttendingEvents(List<Event> attendingEvents) {
        this.attendingEvents = attendingEvents;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public String getPicFilename() {
        return picFilename;
    }

    public void setPicFilename(String picFilename) {
        this.picFilename = picFilename;
    }

    public List<String> getFollowing_usernames() {
        return following_usernames;
    }

    public void setFollowing_usernames(List<String> following_usernames) {
        this.following_usernames = following_usernames;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getFollower_usernames() {
        return follower_usernames;
    }

    public void setFollower_usernames(List<String> follower_usernames) {
        this.follower_usernames = follower_usernames;
    }

    public int getTopCounter() {
        return topCounter;
    }

    public void setTopCounter(int topCounter) {
        this.topCounter = topCounter;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public entityType getType() {
        return entityType.user;
    }
}
