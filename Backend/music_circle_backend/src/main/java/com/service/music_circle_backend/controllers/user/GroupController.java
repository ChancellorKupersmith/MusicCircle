package com.service.music_circle_backend.controllers.user;

import com.service.music_circle_backend.entities.user.Group;
import com.service.music_circle_backend.entities.user.User;
import com.service.music_circle_backend.services.user.GroupService;
import com.service.music_circle_backend.entities.audio_file.AudioFile;
import com.service.music_circle_backend.services.file.BASE64DecodedMultipartFile;
import com.service.music_circle_backend.services.file.PicFileStorageService;
import com.service.music_circle_backend.services.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("http://10.24.227.244:8080")
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;
    @Autowired
    private PicFileStorageService picFileStorageService;

    public GroupController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    @PostMapping("/group/registration")
    @ApiOperation(value = "Registers Groups by groupname,description,username", notes = "Provide groupname,group description and user who created group to add new group to groupdb")
    public ResponseEntity<String> GroupRegistration(@ApiParam(value = "group name", required = true) @RequestParam("groupName") String groupName,
                                                    @ApiParam(value = "group description", required = true) @RequestParam("description") String description,
                                                    @ApiParam(value = "group creator", required = true) @RequestParam("username") String username,
                                                    @ApiParam(value = "user profile picture") @RequestParam("file") String encodedString) throws IOException {
    
        try {
            byte[] arr = Base64.decodeBase64(encodedString);
            MultipartFile f = new BASE64DecodedMultipartFile(arr,username);
            picFileStorageService.store(f);
            User b = userService.getUser(username);
            Group a = new Group(groupName, description, b);
            a.setPicFilename(StringUtils.cleanPath(f.getOriginalFilename()));
            return groupService.saveGroup(a);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

//    @GetMapping("/group/find/{name}")
//    @ApiOperation(value = "Find Groups by groupname", notes = "Provide a groupname to look up specific group from groupdb")
//    public Group getGroup(@ApiParam(value = "group groupname ", required = true) @PathVariable("name") String name) {
//        return groupService.getGroup(name);
//    }

    @GetMapping("/group/find/{name}")
    @ApiOperation(value = "Find Groups by groupname", notes = "Provide a groupname to look up specific group from groupdb")
    public Group getGroup(@ApiParam(value = "group groupname ", required = true) @PathVariable("id") String id) {
        return groupService.getGroup(Long.parseLong(id));
    }

    @GetMapping("/group/name/{s}")
    public List<Group> getGroupsByName(@PathVariable String s){
        return groupService.getAllGroupsByName(s);
    }
    
    @PostMapping("/group/addtogroup/{name1}/{name2}")
    public ResponseEntity<String> joingroup(@PathVariable("name1") String name1,@PathVariable("name2") String name2){
        return groupService.addUsertoGroupList(Long.parseLong(name1),name2);
    }

    @PostMapping("/group/removefromgroup/{name1}/{name2}")
    public ResponseEntity<String> leavegroup(@PathVariable("name1") String name1,@PathVariable("name2") String name2){
        return groupService.RemoveUserfromGroupList(Long.parseLong(name1),name2);
    }

}

