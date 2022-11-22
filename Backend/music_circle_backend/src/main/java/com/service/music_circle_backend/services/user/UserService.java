package com.service.music_circle_backend.services.user;

import com.service.music_circle_backend.entities.audio_file.AudioFile;
import com.service.music_circle_backend.entities.playlist.Playlist;
import com.service.music_circle_backend.entities.socket.NotificationResponse;
import com.service.music_circle_backend.entities.user.Group;
import com.service.music_circle_backend.entities.user.User;
import com.service.music_circle_backend.messages.ResponseMessage;
import com.service.music_circle_backend.repos.audio_file.AudioFileRepository;
import com.service.music_circle_backend.repos.playlist.PlaylistRepository;
import com.service.music_circle_backend.repos.socket.NotificationResponseRepository;
import com.service.music_circle_backend.repos.user.UserRepository;
import com.service.music_circle_backend.services.file.PicFileStorageService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private PicFileStorageService picFileStorageService;

    @Autowired
    private AudioFileRepository audioFileRepository;
    @Autowired
    private NotificationResponseRepository notificationResponseRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getOne(Long id){
        return userRepository.getOne(id);
    }
    
    public User save(User user){
        return userRepository.save(user);
    }
    public ResponseEntity<String> saveUser(User newUser){
        List<User> users = userRepository.findAll();
        System.out.println(newUser.getUsername());
        for (User user : users) {
            if (user.getUsername().equals(newUser.getUsername())) {
                return new ResponseEntity<>("USER ALREADY EXISTS", HttpStatus.OK);
            }
        }

        userRepository.save(newUser);
        User user = userRepository.getOne(newUser.getId());
        Playlist likes = new Playlist("Liked Songs", newUser);
        Playlist mostRecentListened = new Playlist("Most Recently Listened To", newUser);
        Playlist topSongs = new Playlist("Top Songs", newUser);
        playlistRepository.save(mostRecentListened);
        playlistRepository.save(topSongs);
        playlistRepository.save(likes);
        user.getCreatedPlaylists().add(mostRecentListened);
        user.getCreatedPlaylists().add(likes);
        user.getCreatedPlaylists().add(topSongs);
        userRepository.save(user);
        return new ResponseEntity<>("SUCCESSFUL USER REGISTRATION", HttpStatus.OK);
    }

    public ResponseEntity<String> loginUser(User user) {
        List<User> users = userRepository.findAll();
        for (User other : users) {
            if (other.getPassword().equals(user.getPassword()) && other.getUsername().equals(user.getUsername())) {
                user.setLoggedIn(true);
                return new ResponseEntity<>("SUCCESSFUL USER LOGIN", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("UNSUCCESFUL USER LOGIN", HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public List<User> getAll(){
        return new ArrayList<>(userRepository.findAll());
    }

    public void deleteAll(){
        userRepository.deleteAll();
    }

    /**
     public User getUser(Long id) {
     return userRepository.findById(id)
     .orElseThrow(() -> new UsernameNotFoundException(("No user found with ID - " + id)));
     }
     **/

    public User getUser(String name){
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getUsersByName(String name){
        List<User> artistsByName = new ArrayList<User>();
        List<User> all = userRepository.findAll();

        for(User user : all){
            if((user.getArtistName().toLowerCase()).startsWith((name.toLowerCase()))){
                artistsByName.add(user);
            }
        }
        return artistsByName;

    }
    public String getPic(String username){
        try{
            User user = this.getUser(username);
            byte[] a = Files.readAllBytes(picFileStorageService.getPath(user.getPicFilename()));
            String base64 = Base64.encodeBase64String(a);
            return base64;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Set<Playlist> getPlaylists(String username){
        User user = this.getUser(username);
        return user.getCreatedPlaylists();
    }
    public Playlist getPlaylist(String username, String playlistId){
        User user = this.getUser(username);

        for(int i=0; i<user.getCreatedPlaylists().toArray().length; i++){
            if(((Playlist) (user.getCreatedPlaylists().toArray()[i])).getId() == Long.parseLong(playlistId)){
                return ((Playlist) user.getCreatedPlaylists().toArray()[i]);
            }
        }
        return null;
    }

    public List<User> getUserBychar(String s){
        List<User> users = userRepository.findAll();
        List<User> userList = new ArrayList<User>();

        for(User user : users){
            if(user.getUsername().startsWith(s)){
                userList.add(user);
            }
        }
        return userList;
    }


    public List<AudioFile> getUserTopSongs(String username){
        User user = getUser(username);
        List<AudioFile> userTopSongs = new ArrayList<AudioFile>();
        List<AudioFile> uploadedSongs = user.getUploadedSongs();
        for(int i=0; i< uploadedSongs.size(); i++){
            AudioFile song = uploadedSongs.get(i);
            userTopSongs.add(song);
            //Sort
            if(i > 0){
                for(int j=userTopSongs.size() - 1; j > 0; j--){
                    if(userTopSongs.get(j).getTopCounter() > userTopSongs.get(j-1).getTopCounter()){
                        //Swap
                        AudioFile tmp = userTopSongs.get(j -1);
                        userTopSongs.set(j-1, song);
                        userTopSongs.set(j, tmp);
                    }
                    else{
                        break;
                    }
                }
            }
        }
        return userTopSongs;
    }

    public List<NotificationResponse> userConnected(String username){
        User user = getUser(username);
        user.setOnline(true);
        saveUser(user);
        List<NotificationResponse> all = notificationResponseRepository.findAll();
        List<NotificationResponse> notifications = new ArrayList<NotificationResponse>();
        for (NotificationResponse n: all
        ) {
            if(n.getUsers_to_usernames() == username){
                notifications.add(n);
            }
        }
        return notifications;
    }

    public String getUserBio(String name){
        User a = getUser(name);
        return a.getBio();
    }
    
    public List<String> getUserfollowers(String name){
        User a = getUser(name);
        return a.getFollower_usernames();
    }

    public List<String> getUserfollowing(String name){
        User a = getUser(name);
        return a.getFollowing_usernames();
    }

    public List<Group> getUserGroups(String username){
        User user = getUser(username);
        return user.getGroups();
    }

    public ResponseEntity updateUserLikes(String username, int add_remove, Long songId){
        String message = "";
        try{
            User user = getUser(username);
            AudioFile song = audioFileRepository.getOne(songId);

            Set<Playlist> playlists = user.getCreatedPlaylists();
            Iterator<Playlist> itr = playlists.iterator();
            while(itr.hasNext()){
                Playlist likesPlaylist = itr.next();
                System.out.println("\n\n\n" + likesPlaylist + "\n\n\n\n\n");
                if(likesPlaylist.getName().equalsIgnoreCase("Liked Songs")){
                    //like song
                    if(add_remove == 1){
                        song.addLikes();
                        playlists.remove(likesPlaylist);
                        likesPlaylist.getSongs().add(song);
                        audioFileRepository.save(song);
                        playlistRepository.save(likesPlaylist);
                        playlists.add(likesPlaylist);
                        user.setCreatedPlaylists(playlists);
                        userRepository.save(user);
                        message = "Successfully liked song";
                    }
                    //unlike song
                    else{
                        song.decreaseLikes();
                        playlists.remove(likesPlaylist);
                        likesPlaylist.getSongs().remove(song);
                        audioFileRepository.save(song);
                        playlistRepository.save(likesPlaylist);
                        playlists.add(likesPlaylist);
                        user.setCreatedPlaylists(playlists);
                        userRepository.save(user);
                        message = "Successfully unliked song";
                    }
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
                }
            }
            message = "Likes Playlist not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(message));

        }catch (Exception e){
            e.printStackTrace();
            message = "Couldn't update user's likes";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }




    public boolean doesUserLikesSong(String username, Long songId){
        User user = getUser(username);
        AudioFile song = audioFileRepository.getOne(songId);

        Set<Playlist> playlists = user.getCreatedPlaylists();
        Iterator<Playlist> itr = playlists.iterator();
        while(itr.hasNext()){
            Playlist likesPlaylist = itr.next();
            if(likesPlaylist.getName() == "Liked songs"){
                return likesPlaylist.getSongs().contains(song);
            }
        }
        return false;
    }

    public ResponseEntity<String> followUser(String username1, String username2) {
        User a = getUser(username1);
        User b = getUser(username2);
        List<String>list1 = a.getFollowing_usernames();
        List<String>list3 = b.getFollower_usernames();
        if(username1.equals(username2)){
            return new ResponseEntity<>("AN USER CANNOT FOLLOW THEMSELVES", HttpStatus.OK);
        }

        if(list1.contains(username2)){
            return new ResponseEntity<>("ALREADY FOLLOWING USER", HttpStatus.OK);
        }else{
            list1.add(username2);
            a.setFollowing_usernames(list1);;
            list3.add(username1);
            b.setFollower_usernames(list3);
        }
        userRepository.save(a);
        userRepository.save(b);
        return new ResponseEntity<>("NOW FOLLOWING USER", HttpStatus.OK);
    }

    public ResponseEntity<String> unfollowUser(String username1, String username2) {
        User a = getUser(username1);
        User b = getUser(username2);
        List<String>list1 = a.getFollowing_usernames();
        List<String>list3 = b.getFollower_usernames();
        if(username1.equals(username2)){
            return new ResponseEntity<>("AN USER CANNOT FOLLOW THEMSELVES", HttpStatus.OK);
        }
        if(!list1.contains(username2)){
            return new ResponseEntity<>("CANNOT UNFOLLOW AS NOT FOLLOWING USER INITIALLY", HttpStatus.OK);
        }else{
            list1.remove(username2);
            a.setFollowing_usernames(list1);
            list3.remove(username1);
            b.setFollower_usernames(list3);
        }
        userRepository.save(a);
        userRepository.save(b);
        return new ResponseEntity<>("UNFOLLOWED USER", HttpStatus.OK);
    }

    public User findByUserid(Long id) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

}

