package com.service.music_circle_backend.entities.user;
import com.service.music_circle_backend.entities.ShareContent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "group")
@ApiModel(description = "Details about a group")
public class Group implements ShareContent {
    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "unique id of the group")
    private Long id;

    @ApiModelProperty(notes = "group's name")
    private String name;

    @ApiModelProperty(notes = "group's description")
    private String description;

    @ElementCollection
    private List<Long> members;

    @ApiModelProperty(notes = "unique creator of group")
    private Long creator;

    private String picFilename;

    private boolean isPublic;

    private boolean freeToJoin;

    @ElementCollection
    private List<Long> admins;

    @ElementCollection
    private List<Long> groupChats;

    //List of songs, playlists, albums, events


    public Group(Long id, String name, String description, User creator) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creator = creator.getId();
    }

    public Group(String name, String description, User creator){
        this.name = name;
        this.description = description;
        this.creator = creator.getId();
    }


    public Group() {
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isFreeToJoin() {
        return freeToJoin;
    }

    public void setFreeToJoin(boolean freeToJoin) {
        this.freeToJoin = freeToJoin;
    }

    public List<Long> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Long> admins) {
        this.admins = admins;
    }

    public List<Long> getGroupChats() {
        return groupChats;
    }

    public void setGroupChats(List<Long> groupChats) {
        this.groupChats = groupChats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreator(User creator){
        this.creator = creator.getId();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getMembers() {
        return members;
    }

    public void setMembers(List<Long> members) {
        this.members = members;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator_id) {
        this.creator = creator;
    }
    
    public String getPicFilename() {
        return picFilename;
    }

    public void setPicFilename(String picFilename) {
        this.picFilename = picFilename;
    }

    @Override
    public entityType getType() {
        return entityType.group;
    }
}


