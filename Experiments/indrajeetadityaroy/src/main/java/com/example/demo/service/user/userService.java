package com.example.demo.service.user;

import com.example.demo.entity.user.User;
import com.example.demo.repository.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class userService {

    private final UserRepository userRepository;

    public userService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> saveUser(User newUser){
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getUsername().equals(newUser.getUsername())) {
                return new ResponseEntity<>("USER ALREADY EXISTS", HttpStatus.OK);
            }
        }
        userRepository.save(newUser);
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
}
