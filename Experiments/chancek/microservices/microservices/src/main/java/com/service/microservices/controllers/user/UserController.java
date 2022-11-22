package com.service.microservices.controllers.user;

import com.service.microservices.entities.user.User;
import com.service.microservices.repos.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:8080")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("api/user/registration")
    public ResponseEntity<String> UserRegistration(@Valid @RequestBody User newUser) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.equals(newUser)) {
                return new ResponseEntity<>("USER ALREADY EXISTS", HttpStatus.CONFLICT);
            }
        }

        userRepository.save(newUser);
        return new ResponseEntity<>("SUCCESSFUL USER REGISTRATION", HttpStatus.OK);
    }

    @PostMapping("api/user/login")
    public ResponseEntity<String> UserLogin(@Valid @RequestBody User user) {
        List<User> users = userRepository.findAll();
        for (User other : users) {
            if (other.equals(user)) {
                user.setLoggedIn(true);
                userRepository.save(user);
                return new ResponseEntity<>("SUCCESSFUL USER LOGIN", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("UNSUCCESSFUL USER LOGIN", HttpStatus.CONFLICT);
    }
}
