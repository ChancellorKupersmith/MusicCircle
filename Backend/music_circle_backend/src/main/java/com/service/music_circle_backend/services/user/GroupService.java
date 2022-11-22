package com.service.music_circle_backend.services.user;

import com.service.music_circle_backend.entities.user.Group;
import com.service.music_circle_backend.messages.ResponseMessage;
import com.service.music_circle_backend.repos.user.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
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

//    public Group getGroup(String name){
//        List<Group> groups = groupRepository.findAll();
//        for (Group group : groups) {
//            if (group.getName().equals(name)) {
//                return group;
//            }
//        }
//        return null;
//    }

    public Group getGroup(Long id){
        return groupRepository.getOne(id);
    }

    public List<Group> getAllGroupsByName(String name){
        List<Group> groupsByName = new ArrayList<Group>();
        List<Group> all = groupRepository.findAll();
        for(Group group : all){
            if(group.getName().startsWith(name)){
                groupsByName.add(group);
            }
        }
        return groupsByName;
    }

    public ResponseEntity<String> addUsertoGroupList(Long id, String name2) {
        Group a = getGroup(id);

        if(a.getMembers().contains(name2)){
            return new ResponseEntity<>("ALREADY GROUP MEMBER", HttpStatus.OK);
        }else{
            a.getMembers().add(name2);
        }
        groupRepository.save(a);
        return new ResponseEntity<>("SUCCESSFUL GROUP ADDITION", HttpStatus.OK);
    }


    public ResponseEntity<String> RemoveUserfromGroupList(Long id, String name2) {
        Group a = getGroup(id);
        if(!a.getMembers().contains(name2)){
            return new ResponseEntity<>("USER NOT IN GROUP INITIALLY", HttpStatus.OK);
        }else{
            a.getMembers().remove(name2);
        }
        groupRepository.save(a);
        return new ResponseEntity<>("SUCCESSFUL GROUP REMOVAL", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> removeGroup(String name){
        List<Group> groups = groupRepository.findAll();
        for (Group group : groups) {
            if (group.getName().equals(name)) {
                groupRepository.delete(group);
                return new ResponseEntity<>("SUCCESSFUL GROUP DELETION", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("UNSUCCESSFUL GROUP DELETION", HttpStatus.OK);
    }



    public List<Group> findAll() {
        return groupRepository.findAll();
    }


    public Group saveOrUpdateGroup(Group group) {
        return groupRepository.save(group);
    }
    


//    public ResponseEntity changeGroupPic(Long id, MultipartFile groupPic){
//        String message = "";
//        try{
//            Group group = groupRepository.getOne(id);
//
//        }catch (Exception e){
//            e.printStackTrace();
//            message = "Couldn't Change Group Picture";
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//        }
//    }
}
