import 'dart:typed_data';
import 'package:flutter/material.dart';
import 'package:music_circle/Entities/User.dart';
import 'Entity.dart';
class Event extends Entity{
  User creator;
  String location;
  DateTime dateTime;
  String city, state;
  String streetAddress;
  String description;
  List<User> usersPerforming;
  List<User> usersAttending;
  String country, zipcode;
  Uint8List image;
  Image eventImage;

  Event(){
    this.usersPerforming = [];
    this.usersAttending = [];
  }

  @override
  EntityType getType() {
    return EntityType.EVENT;
  }

  testEvent({String id, String name, User creator, String location, DateTime dateTime, String city, String state, String streetAddress, String description, List<User> performing, List<User> attending, String country, zipcode, Uint8List image, Image eventImage, @required int number}){
    if(id != null){
      this.id = id;
    } else{
      this.id = 'eventID';
    }
    if(name != null){
      this.name = name;
    } else{
      this.name = 'Event ' + number.toString();
    }
    if(creator != null){
      this.creator = creator;
    } else{
      this.creator = new User();
      this.creator.testUser(number: number);
    }
    if(location != null){
      this.location = location;
    } else{
      this.location = 'Address of ' + this.name;
    }
    if(dateTime != null){
      this.dateTime = dateTime;
    } else{
      this.dateTime = DateTime.now();
    }
    if(city != null){
      this.city = city;
    } else{
      this.city = 'Ames';
    }
    if(state != null){
      this.state = state;
    } else{
      this.state = 'IA';
    }
    if(streetAddress != null){
      this.streetAddress = streetAddress;
    } else{
      this.streetAddress = "this event's street address";
    }
    if(description != null){
      this.description = description;
    } else{
      this.description = this.name + "'s description";
    }
    if(performing != null){
      this.usersPerforming = performing;
    }
    if(attending != null){
      this.usersAttending = attending;
    }
    if(country != null){
      this.country = country;
    } else{
      this.country = 'USA';
    }
    if(zipcode != null){
      this.zipcode = zipcode;
    } else{
      this.zipcode = '50014';
    }
    if(image != null){
      this.image = image;
    }
    if(eventImage != null){
      this.eventImage = eventImage;
    } else{
      this.eventImage = Image.asset(
        'lib/Resources/Images/song2_example_pic.jpg',
        fit: BoxFit.cover,
      );
    }
  }

}