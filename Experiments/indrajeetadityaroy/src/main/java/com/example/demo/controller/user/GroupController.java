package com.example.demo.controller.user;

import com.example.demo.entity.user.Group;
import com.example.demo.service.user.groupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/group")
public class GroupController {

    private final groupService GroupService;

    public GroupController(groupService groupService) {
        GroupService = groupService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> GroupRegistration(@Valid Group newGroup) {
        return GroupService.saveGroup(newGroup);
    }

    @GetMapping("/find/{name}")
    public Group getGroup(@PathVariable("name") String name){ return GroupService.getGroup(name); }

}
