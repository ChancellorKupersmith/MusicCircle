package com.db.GroupSequencerDB_REST_.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comment {
    //Variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;
    private double barLocation;

    //Constructors
    protected Comment(){}
    public Comment(String text, double barLocation){
        this.text = text;
        this.barLocation = barLocation;
    }
    //Logging
    @Override
    public String toString(){
        return String.format("Comment[ID: %d, TEXT: '%s', BAR_LOCATION: %f]",
                id, text, barLocation);
    }

    //Getters
    public double getBarLocation() { return barLocation; }
    public Long getId() { return id; }
    public String getText() { return text; }

    //Setters
    public void setText(String text){
        this.text = text;
    }
    public void setBarLocation(double barLocation) {
        this.barLocation = barLocation;
    }
}
