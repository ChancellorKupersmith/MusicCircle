package com.example.musiccircle.Entity;

import com.example.musiccircle.entities;

import java.util.ArrayList;
import java.util.List;

public class Event implements entities {
    private Long id;
    private User user;
    private String name;
    private String location;
    private String dateTime;
    private String description;
    private List<User> usersPerforming;
    private List<User> usersAttending;

    public Event() {}
    public Event(User user, String name, String location, String dateTime, String description, List<User> usersPerforming) {
        this.user = user;
        this.name = name;
        this.location = location;
        this.dateTime = dateTime;
        this.description = description;
        this.usersPerforming = usersPerforming;
        this.usersAttending = new ArrayList<User>();
    }
    public Event(User user, String name, String location, String dateTime, String description) {
        this.user = user;
        this.name = name;
        this.location = location;
        this.dateTime = dateTime;
        this.description = description;
        this.usersPerforming = new ArrayList<User>();
        this.usersAttending = new ArrayList<User>();
    }
    public Event(User user, String name, String location, String dateTime, List<User> usersPerforming) {
        this.user = user;
        this.name = name;
        this.location = location;
        this.dateTime = dateTime;
        this.usersPerforming = usersPerforming;
        this.usersAttending = new ArrayList<User>();
    }
    public Event(User user, String name, String location, String dateTime) {
        this.user = user;
        this.name = name;
        this.location = location;
        this.dateTime = dateTime;
        this.usersPerforming = new ArrayList<User>();
        this.usersAttending = new ArrayList<User>();
    }

    public Long getId() { return id; }
    public User getUserId() { return user; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getDateTime() { return dateTime; }
    public String getDescription() { return description; }
    public List<User> getUsersPerforming() { return usersPerforming; }
    public List<User> getUsersAttending() { return usersAttending; }

    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }
    public void setDescription(String description) { this.description = description; }
    public void setUsersPerforming(List<User> usersPerforming) { this.usersPerforming = usersPerforming; }
    public void setUsersAttending(List<User> usersAttending) { this.usersAttending = usersAttending; }

    @Override
    public int getType() { return 3; }
}
