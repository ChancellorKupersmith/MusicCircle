package com.example.demo.controller.user;

import com.example.demo.entity.user.User;
import com.example.demo.service.user.userService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final userService UserService;

    public UserController(userService userService) {
        UserService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> UserRegistration(@Valid User newUser) {
        return UserService.saveUser(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> UserLogin(@Valid User user){
        return UserService.loginUser(user);
    }

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return UserService.getAll();
    }

    @GetMapping("/find/{name}")
    public User getUser(@PathVariable("name") String name){
        return UserService.getUser(name);
    }
}
