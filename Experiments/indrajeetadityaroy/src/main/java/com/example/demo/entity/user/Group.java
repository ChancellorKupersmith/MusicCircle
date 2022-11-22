package com.example.demo.entity.user;

import com.example.demo.entity.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "music_group")
public class Group {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    @OneToMany(fetch = FetchType.LAZY)
    private List<User> members;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Group(Long id, String name, String description, List<User> members, User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.members = members;
        this.user = user;
    }

    public Group() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
