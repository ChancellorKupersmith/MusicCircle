package com.service.microservices.entities.event;

import javax.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event {
	//Variables
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long userId;
	private String location;
	private Date dateTime;
	private String description;
	@ElementCollection
	private List<Long> usersPerforming;
	@ElementCollection
	private List<Long> usersAttending;
	
	//Constructors
	protected Event() {}
	public Event(Long userId, String location, Date dateTime, String description, List<Long> usersPerforming) {
		this.userId = userId;
		this.location = location;
		this.dateTime = dateTime;
		this.description = description;
		this.usersPerforming = usersPerforming;
		this.usersAttending = new ArrayList<Long>();
	}
	public Event(Long userId, String location, Date dateTime, String description) {
		this.userId = userId;
		this.location = location;
		this.dateTime = dateTime;
		this.description = description;
		this.usersPerforming = new ArrayList<Long>();
		this.usersAttending = new ArrayList<Long>();
	}
	public Event(Long userId, String location, Date dateTime, List<Long> usersPerforming) {
		this.userId = userId;
		this.location = location;
		this.dateTime = dateTime;
		this.usersPerforming = usersPerforming;
		this.usersAttending = new ArrayList<Long>();
	}
	public Event(Long userId, String location, Date dateTime) {
		this.userId = userId;
		this.location = location;
		this.dateTime = dateTime;
		this.usersPerforming = new ArrayList<Long>();
		this.usersAttending = new ArrayList<Long>();
	}

	//Getters
	public Long getId() { return id; }
	public Long getUserId() { return userId; }
	public String getLocation() { return location; }
	public Date getDateTime() { return dateTime; }
	public String getDescription() { return description; }
	public List<Long> getUsersPerforming() { return usersPerforming; }
	public List<Long> getUsersAttending() { return usersAttending; }

	//Setters
	public void setId(Long id) { this.id = id; }
	public void setUserId(Long userId) { this.userId = userId; }
	public void setLocation(String location) { this.location = location; }
	public void setDateTime(Date dateTime) { this.dateTime = dateTime; }
	public void setDescription(String description) { this.description = description; }
	public void setUsersPerforming(List<Long> usersPerforming) { this.usersPerforming = usersPerforming; }
	public void setUsersAttending(List<Long> usersAttending) { this.usersAttending = usersAttending; }
}
