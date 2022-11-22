package com.example.musiccircle.Entity;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class UserParcelable implements entities, Serializable,Parcelable {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private boolean isChecked = false;
    private byte[] content;
    private String artistName;
    private String city, state;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
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

    public UserParcelable(Long id, String firstName, String lastName, String username, byte[] content) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.content = content;
    }

    public UserParcelable() {

    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    public ArrayList<String> getFollower_usernames() {
        return follower_usernames;
    }

    public void setFollower_usernames(ArrayList<String> follower_usernames) {
        this.follower_usernames = follower_usernames;
    }

    public UserParcelable(Parcel in) {
        String[] data = new String[4];

        in.readStringArray(data);
        this.id = Long.valueOf(data[0]);
        this.firstName = data[1];
        this.lastName = data[2];
        this.username = data[3];
        this.content = data[4].getBytes();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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
        return 4;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{String.valueOf(this.id),
                this.firstName, this.lastName, this.username, Arrays.toString(this.content)});
    }
}

