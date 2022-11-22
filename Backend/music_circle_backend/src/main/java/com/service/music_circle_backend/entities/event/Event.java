package com.service.music_circle_backend.entities.event;
import com.service.music_circle_backend.entities.ShareContent;
import com.service.music_circle_backend.entities.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event implements ShareContent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private User event_creator_user;
//    private String event_creator;
    private String name;
    private String streetAddress;
    private String state;
    private String city;
    private String country;
    private String zipcode;
    private String location;
    private String dateTime;
    private String description;
    @OneToMany
    @JoinColumn
    private List<User> usersPerforming;
//    @ElementCollection(targetClass=String.class)
//    private List<String> usersPerforming_usernames;
    @OneToMany
    @JoinColumn
    private List<User> usersAttending;
//    @ElementCollection(targetClass=String.class)
//    private List<String> usersAttending_usernames;

    private Long chatID;
    private String eventPicFilename;


    protected Event(){

    }

//    public Event(String name, String streetAddress, String state, String city, String country, String zipcode, String description, String event_creator) {
//        this.name = name;
//        this.streetAddress = streetAddress;
//        this.state = state;
//        this.city = city;
//        this.country = country;
//        this.zipcode = zipcode;
//        this.description = description;
//        this.location = streetAddress + ", " + city + ", " + state + " " + zipcode + ", " + country;
//        this.event_creator = event_creator;
//    }

    public Event(String name, String streetAddress, String state, String city, String country, String zipcode, String description, User event_creator) {
        this.name = name;
        this.streetAddress = streetAddress;
        this.state = state;
        this.city = city;
        this.country = country;
        this.zipcode = zipcode;
        this.description = description;
        this.location = streetAddress + ", " + city + ", " + state + " " + zipcode + ", " + country;
        this.event_creator_user = event_creator;
    }

//    public Event(String name, String streetAddress, String state, String city, String country, String zipcode, String description, String event_creator, List<String> usersPerforming) {
//        this.name = name;
//        this.streetAddress = streetAddress;
//        this.state = state;
//        this.city = city;
//        this.country = country;
//        this.zipcode = zipcode;
//        this.description = description;
//        this.location = streetAddress + ", " + city + ", " + state + " " + zipcode + ", " + country;
//        this.event_creator = event_creator;
//        this.usersPerforming_usernames = usersPerforming;
//    }
    public Event(String name, String streetAddress, String state, String city, String country, String zipcode, String description, User event_creator, List<User> usersPerforming) {
        this.name = name;
        this.streetAddress = streetAddress;
        this.state = state;
        this.city = city;
        this.country = country;
        this.zipcode = zipcode;
        this.description = description;
        this.location = streetAddress + ", " + city + ", " + state + " " + zipcode + ", " + country;
        this.event_creator_user = event_creator;
        this.usersPerforming = usersPerforming;
    }
//
//    public Event(String name, String streetAddress, String state, String city, String country, String zipcode, String description, ArrayList<String>list) {
//        this.name = name;
//        this.streetAddress = streetAddress;
//        this.state = state;
//        this.city = city;
//        this.country = country;
//        this.zipcode = zipcode;
//        this.description = description;
//        this.usersPerforming_usernames = list;
//        this.location = streetAddress + ", " + city + ", " + state + " " + zipcode + ", " + country;
//    }
    public Event(String name, String streetAddress, String state, String city, String country, String zipcode, String description, ArrayList<User>list) {
        this.name = name;
        this.streetAddress = streetAddress;
        this.state = state;
        this.city = city;
        this.country = country;
        this.zipcode = zipcode;
        this.description = description;
        this.usersPerforming = list;
        this.location = streetAddress + ", " + city + ", " + state + " " + zipcode + ", " + country;
    }
    public Event(String name, String streetAddress, String state, String city, String country, String zipcode, String description, List<User> usersPerforming, User event_creator_user) {
        this.name = name;
        this.streetAddress = streetAddress;
        this.state = state;
        this.city = city;
        this.country = country;
        this.zipcode = zipcode;
        this.description = description;
        this.usersPerforming = usersPerforming;
        this.event_creator_user = event_creator_user;
        this.location = streetAddress + ", " + city + ", " + state + " " + zipcode + ", " + country;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getEventPicFilename() {
        return eventPicFilename;
    }

    public void setEventPicFilename(String eventPicFilename) {
        this.eventPicFilename = eventPicFilename;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setUsersPerforming_usernames(List<String> usersPerforming_usernames) {
        this.usersPerforming = usersPerforming;
    }

    public Long getChatID() {
        return chatID;
    }

    public void setChatID(Long chatID) {
        this.chatID = chatID;
    }

    public List<User> getUsersAttending_usernames() {
        return usersAttending;
    }

    public void setUsersAttending(List<User> usersAttending) {
        this.usersAttending = usersAttending;
    }

    public Long getId() {
        return id;
    }

    public User getEvent_creator_user() {
        return event_creator_user;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public List<User> getUsersPerforming() {
        return usersPerforming;
    }

    public List<User> getUsersAttending() {
        return usersAttending;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEvent_creator_user(User user) {
        this.event_creator_user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUsersPerforming(List<User> usersPerforming) {
        this.usersPerforming = usersPerforming;
    }

    public User getEvent_creator() {
        return event_creator_user;
    }

    public void setEvent_creator(User event_creator) {
        this.event_creator_user = event_creator;
    }

    @Override
    public entityType getType() {
        return entityType.event;
    }
}
