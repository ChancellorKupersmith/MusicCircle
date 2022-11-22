package com.example.musiccircle.Entity;

import com.example.musiccircle.Fragments.Profile.UserPlaylistRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class Event implements entities {
    private Long id;
    private User user;
    private String name;
    private String location;
    private String dateTime;
    private String city, state;
    private String streetAddress;
    private String description;
    private ArrayList<UserParcelable> usersPerforming;
    private ArrayList<UserParcelable> usersAttending;
    private String eventCreator;
    private String eventName;
    private String eventLocation;
    private String eventDescription;
    private String eventPicFilename;
    private String country, zipcode;
    private byte[] image;

    public Event() {}
    public Event(User user, String name, String location, String dateTime, String description, ArrayList<UserParcelable> usersPerforming) {
        this.user = user;
        this.name = name;
        this.location = location;
        this.dateTime = dateTime;
        this.description = description;
        this.usersPerforming = usersPerforming;
        this.usersAttending = new ArrayList<UserParcelable>();
    }
    public Event(User user, String name, String location, String dateTime, String description) {
        this.user = user;
        this.name = name;
        this.location = location;
        this.dateTime = dateTime;
        this.description = description;
        this.usersPerforming = new ArrayList<UserParcelable>();
        this.usersAttending = new ArrayList<UserParcelable>();
    }
    public Event(User user, String name, String location, String dateTime, ArrayList<UserParcelable> usersPerforming) {
        this.user = user;
        this.name = name;
        this.location = location;
        this.dateTime = dateTime;
        this.usersPerforming = usersPerforming;
        this.usersAttending = new ArrayList<UserParcelable>();
    }
    public Event(User user, String name, String location, String dateTime) {
        this.user = user;
        this.name = name;
        this.location = location;
        this.dateTime = dateTime;
        this.usersPerforming = new ArrayList<UserParcelable>();
        this.usersAttending = new ArrayList<UserParcelable>();
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCountry() {
        return country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getId() { return id; }
    public User getUserId() { return user; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getDateTime() { return dateTime; }
    public String getDescription() { return description; }
    public ArrayList<UserParcelable> getUsersPerforming() { return usersPerforming; }
    public ArrayList<UserParcelable> getUsersAttending() { return usersAttending; }
    public String getEventPicFilename(){
        return eventPicFilename;
    }

    public void setId(Long id) { this.id = id; }
    public void setEventPicFilename(String eventPicFilename){
        this.eventPicFilename = eventPicFilename;
    }
    public void setUser(User user) { this.user = user; }
    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }
    public void setDescription(String description) { this.description = description; }
    public void setUsersPerforming(ArrayList<UserParcelable> usersPerforming) { this.usersPerforming = usersPerforming; }
    public void setUsersAttending(ArrayList<UserParcelable> usersAttending) { this.usersAttending = usersAttending; }
    public String getEventCreator() {
        return eventCreator;
    }

    public void setEventCreator(String eventCreator) {
        this.eventCreator = eventCreator;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    @Override
    public int getType() { return 3; }
}
