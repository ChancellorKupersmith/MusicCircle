package com.service.music_circle_backend.controllers.user;

import com.service.music_circle_backend.entities.BoolObj;
import com.service.music_circle_backend.entities.audio_file.AudioFile;
import com.service.music_circle_backend.entities.playlist.Album;
import com.service.music_circle_backend.entities.playlist.Playlist;
import com.service.music_circle_backend.entities.socket.NotificationResponse;
import com.service.music_circle_backend.entities.user.Group;
import com.service.music_circle_backend.entities.user.User;
import com.service.music_circle_backend.messages.ResponseMessage;
import com.service.music_circle_backend.repos.socket.NotificationResponseRepository;
import com.service.music_circle_backend.services.file.BASE64DecodedMultipartFile;
import com.service.music_circle_backend.services.file.PicFileStorageService;
import com.service.music_circle_backend.services.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("http://10.24.227.244:8080")
public class UserController {

    private final UserService userService;
    @Autowired
    private PicFileStorageService picFileStorageService;
    
    @Autowired
    private NotificationResponseRepository notificationResponseRepository;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/registration/multipart")
    @ApiOperation(value = "Registers Users by firstname,lastname,username,email,password,profile picture", notes = "Provide firstname,lastname,username,email,password and profile picture to add new user to userdb")
    public ResponseEntity<String> UserRegistration(@ApiParam(value = "user firstname", required = true) @RequestParam("firstName") String firstName,
                                                   @ApiParam(value = "user lastname", required = true) @RequestParam("lastName") String lastName,
                                                   @ApiParam(value = "user username", required = true) @RequestParam("username") String username,
                                                   @ApiParam(value = "user artist name", required = true) @RequestParam("artistName") String artistName,
                                                   @ApiParam(value = "user email", required = true) @RequestParam("email") String email,
                                                   @ApiParam(value = "user password", required = true) @RequestParam("password") String password,
                                                   @ApiParam(value = "user profile picture") @RequestParam("file") MultipartFile file) {
        User a = new User(firstName, lastName, username, email, password);
        a.setArtistName(artistName);
        a.setOnline(false);
        picFileStorageService.store(file);
        a.setPicFilename(StringUtils.cleanPath(file.getOriginalFilename()));
        return userService.saveUser(a);
    }
//Added
    @PostMapping("/user/registration")
    @ApiOperation(value = "Registers Users by firstname,lastname,username,email,password,profile picture", notes = "Provide firstname,lastname,username,email,password and profile picture to add new user to userdb")
    public ResponseEntity<String> UserRegistration(@ApiParam(value = "user firstname", required = true) @RequestParam("firstName") String firstName,
                                                   @ApiParam(value = "user lastname", required = true) @RequestParam("lastName") String lastName,
                                                   @ApiParam(value = "user username", required = true) @RequestParam("username") String username,
                                                   @ApiParam(value = "user email", required = true) @RequestParam("email") String email,
                                                   @ApiParam(value = "user artist name", required = true) @RequestParam("artistName") String artistName,
                                                   @ApiParam(value = "user password", required = true) @RequestParam("password") String password,
                                                   @ApiParam(value = "user city", required = true) @RequestParam("city") String city,
                                                   @ApiParam(value = "user state", required = true) @RequestParam("state") String state,
                                                   @ApiParam(value = "user profile picture") @RequestParam("file") String encodedString) throws IOException {

        try {
            byte[] arr = Base64.decodeBase64(encodedString);
            MultipartFile f = new BASE64DecodedMultipartFile(arr,username);
            picFileStorageService.store(f);
            User a = new User(firstName, lastName, username, email, password, username);
            a.setArtistName(artistName);
            a.setState(state);
            a.setCity(city);
            a.setPicFilename(StringUtils.cleanPath(f.getOriginalFilename()));
            return userService.saveUser(a);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @PostMapping("/user/login")
    @ApiOperation(value = "Find Users by username and password",notes = "Provide an username and password to look up specific user from userdb")
    public ResponseEntity<String> UserLogin(@ApiParam(value = "User data object", required = true) @Valid User user) {
        return userService.loginUser(user);
    }

    @GetMapping("/user/all")
    @ApiOperation(value = "Find all Users", notes = "look up all users from userdb")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/user/getTopUploads/{username}")
    public List<AudioFile> getUserTopUploads(@PathVariable String username){
        return userService.getUserTopSongs(username);
    }
    @GetMapping("/user/name/{s}")
    public List<User> getByName(@PathVariable String s){
        return userService.getUsersByName(s);
    }
    @GetMapping("/user/find/{name}")
    @ApiOperation(value = "Find Users by username",notes = "Provide an username to look up specific user from userdb")
    public User getUser(@ApiParam(value = "user username ", required = true) @PathVariable("name") String name) {
        return userService.getUser(name);
    }

    //for testing
    @GetMapping("/user/pic/{username}")
    public String getUserProfilePic(@PathVariable String username){
        return userService.getPic(username);
    }

    @GetMapping("/user/playlists/{username}")
    public Set<Playlist> getUserPlaylists(@PathVariable String username){
        return userService.getPlaylists(username);
    }
    @GetMapping("/user/playlist/{username}/{playlistId}")
    public Playlist getUserPlaylist(@PathVariable String username, @PathVariable String playlistId){
        return userService.getPlaylist(username, playlistId);
    }

    @PutMapping("/user/updateLikes/{username}/{songId}/{add_remove}")
    public ResponseEntity updateUserLikes(@PathVariable String username, @PathVariable String songId, @PathVariable String add_remove){
        return userService.updateUserLikes(username, Integer.valueOf(add_remove), Long.parseLong(songId));
    }

    @GetMapping("/user/doesLikeSong/{username}/{songId}")
    public BoolObj doesUserLikeSong(@PathVariable String username, @PathVariable Long songId){
        return new BoolObj(userService.doesUserLikesSong(username, songId));
    }

    @PutMapping("/userOnline/{username_from}")
    public ResponseEntity userConnected(@PathVariable String username_from){

        String message = "";
        try {
            User user = userService.getUser(username_from);
            user.setOnline(true);
            userService.save(user);
            message = "User: " + user.getUsername() + ", Now Online";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not connect user: " + username_from;
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    @GetMapping("/userOnline/{username_from}/getOld")
    public List<NotificationResponse> getOldNotifications(@PathVariable String username_from) {

        List<NotificationResponse> all = notificationResponseRepository.findAll();
        List<NotificationResponse> notifications = new ArrayList<NotificationResponse>();
        for (NotificationResponse n : all
        ) {
            if (n.getUsers_to_usernames() == username_from) {
                notifications.add(n);
                // notificationResponseRepository.delete(n);
            }
        }
        return notifications;
    }

    @GetMapping("/user/topArtists")
    public List<User> getTopArtists(){
        List<User> topArtists = new ArrayList<User>();
        List<User> allArtists = userService.getAll();
        for(int i=0; i< allArtists.size(); i++){
            User artist = allArtists.get(i);
            topArtists.add(artist);
            //Sort
            if(i > 0){
                for(int j=topArtists.size() -1; j > 0; j--){
                    if(topArtists.get(j).getTopCounter() > topArtists.get(j-1).getTopCounter()){
                        //swap
                        User tmp = topArtists.get(j - 1);
                        topArtists.set(j - 1, artist);
                        topArtists.set(j, tmp);
                    }
                }
            }
        }
        return topArtists;
    }

    @GetMapping("/user/recentListen")
    public Playlist getMostRecentListened(@RequestParam("username") String username){
        try{
            User user = userService.getUser(username);
            Object[] playlists = user.getCreatedPlaylists().toArray();
            return (Playlist) playlists[0];
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/user/recentUpload")
    public Playlist getMostRecentUploads(@RequestParam("username") String username){
        try{
            Playlist recentUploads = new Playlist("Recent Uploads", username);
            User user = userService.getUser(username);
            for(int i= (user.getUploadedSongs().size() - 1); i >= 0; i--){
                recentUploads.getSongs().add(user.getUploadedSongs().get(i));
            }
            return recentUploads;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/user/albums")
    public List<Album> getUserAlbums(@RequestParam("username") String username){
        try{
            User user = userService.getUser(username);
            return user.getUploadedAlbums();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/user/userbio/{name}")
    public String getUserbio(@ApiParam(value = "user username ", required = true) @PathVariable("name") String name) {
        return userService.getUserBio(name);
    }

     @GetMapping("/user/followers/{name}")
    public List<String> getUserFollowers(@PathVariable("name") String name){
        return userService.getUserfollowers(name);
    }

    @GetMapping("/user/following/{name}")
    public List<String> getUserFollowing(@PathVariable("name") String name){
        return userService.getUserfollowing(name);
    }

    @PutMapping("/user/followuser/{name1}/{name2}")
    public ResponseEntity<String> followuser(@PathVariable("name1") String name1,@PathVariable("name2") String name2){
        return userService.followUser(name1,name2);
    }

    @PutMapping("/user/unfollowuser/{name1}/{name2}")
    public ResponseEntity<String> unfollowuser(@PathVariable("name1") String name1,@PathVariable("name2") String name2){
        return userService.unfollowUser(name1,name2);
    }

    @GetMapping("/user/groups/{username}")
    public List<Group> getUserGroups(@PathVariable String username){
        return userService.getUserGroups(username);
    }

}

