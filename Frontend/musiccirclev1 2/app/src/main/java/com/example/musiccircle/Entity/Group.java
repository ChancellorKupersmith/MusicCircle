package com.example.musiccircle.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements entities, Serializable {
    private Long id;
    private String name;
    private String description;
    private ArrayList<String> member_usernames;
    private byte[] image;
    private boolean isChecked;

    public Group(){}
    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public Long getId() {
        return id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public ArrayList<String> getMember_usernames() {
        return member_usernames;
    }

    public void setMember_usernames(ArrayList<String> member_usernames) {
        this.member_usernames = member_usernames;
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

    @Override
    public int getType() {
        return 2;
    }
}
