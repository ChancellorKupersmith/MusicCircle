package com.example.demo.service.user;

import com.example.demo.entity.user.Group;
import com.example.demo.repository.user.GroupRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class groupService {

    private final GroupRepository groupRepository;

    public groupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public ResponseEntity<String> saveGroup(Group newGroup){
        List<Group> groups = groupRepository.findAll();
        for (Group group : groups) {
            if (group.getName().equals(newGroup.getName())) {
                return new ResponseEntity<>("GROUP ALREADY EXISTS", HttpStatus.OK);
            }
        }
        groupRepository.save(newGroup);
        return new ResponseEntity<>("SUCCESSFUL GROUP REGISTRATION", HttpStatus.OK);
    }

    public Group getGroup(String name){
        List<Group> groups = groupRepository.findAll();
        for (Group group : groups) {
            if (group.getName().equals(name)) {
                return group;
            }
        }
        return null;
    }
}
