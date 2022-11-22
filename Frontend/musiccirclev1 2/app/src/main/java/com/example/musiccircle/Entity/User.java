package com.example.musiccircle.Entity;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class User implements entities, Serializable{
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private boolean isChecked = false;
    private byte[] content;
    private String artistName;
    private String city, state;
    private ArrayList<Group> groups;

    public User(Parcel in) {

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public void setState(String state) {
        this.state = state;
    }

    private ArrayList<String> follower_usernames;
    private ArrayList<String> following_usernames;
    private byte[] image;
    private ArrayList<AudioFile> uploadedSongs;
    private String bio;
    private ArrayList<Event> userEvents;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public ArrayList<Event> getUserEvents() {
        return userEvents;
    }

    public void setUserEvents(ArrayList<Event> userEvents) {
        this.userEvents = userEvents;
    }

    public ArrayList<String> getFollowing_usernames() {
        return following_usernames;
    }

    public void setFollowing_usernames(ArrayList<String> following_usernames) {
        this.following_usernames = following_usernames;
    }

    public User(Long id, String firstName, String lastName, String username, byte[] content ){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.content = content;
    }

    public User(){

    }

    public ArrayList<String> getFollower_usernames() {
        return follower_usernames;
    }

    public void setFollower_usernames(ArrayList<String> follower_usernames) {
        this.follower_usernames = follower_usernames;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getArtistName() {
        return artistName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setUploadedSongs(ArrayList<AudioFile> uploadedSongs) {
        this.uploadedSongs = uploadedSongs;
    }

    public ArrayList<AudioFile> getUploadedSongs() {
        return uploadedSongs;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int getType() {
        return 1;
    }

}

