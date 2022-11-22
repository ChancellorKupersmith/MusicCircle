import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:music_circle/Entities/AudioFile.dart';
import 'package:music_circle/Entities/Entity.dart';

import 'Album.dart';
import 'Genre.dart';
import 'Group.dart';
import 'Playlist.dart';

class User extends Entity{
  String username;
  String email;
  bool isChecked;
  Uint8List image;
  String city, state;
  List<Group> groups;
  Image userImage;
  List<User> followers, following;
  bool isOnline;
  bool curator, artist;
  List<AudioFile> songs;
  List<Playlist> playlists;
  List<Album> albums;
  List<Genre> genres;
  User(){
    groups = [];
    songs = [];
    playlists = [];
    albums = [];
    genres = [];
    followers = [];
    following = [];
    isOnline = false;
    isChecked = false;
  }

  @override
  EntityType getType() {
    return EntityType.USER;
  }

  testUser({String id, String name, String username, String email, bool isChecked, Uint8List image, String city, String state, Image userImage, List<User> followers, List<User> following, List<Group> groups, bool isOnline, bool curator, bool artist, List<AudioFile> songs, List<Playlist> playlists, List<Album> albums, List<Genre> genres, @required int number}){
    if(id != null){
      this.id = id;
    } else{
      this.id = 'userID' + number.toString();
    }
    if(name != null){
      this.name = name;
    } else{
      this.name = 'User ' + number.toString();
    }
    if(username != null){
      this.username = username;
    } else{
      this.username = 'test_username';
    }
    if(email != null){
      this.email = email;
    } else{
      this.email = 'testUser@gmail.com';
    }
    if(isChecked != null){
      this.isChecked = isChecked;
    } else{
      this.isChecked = number % 3 == 0;
    }
    if(image != null){
      this.image = image;
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
    if(userImage != null){
      this.userImage = userImage;
    } else{
      this.userImage = Image.asset(
        'lib/Resources/Images/user5_example_pic.jpg',
        fit: BoxFit.cover,
      );
    }
    if(isOnline != null){
      this.isOnline = isOnline;
    } else{
      this.isOnline = number % 4 == 0;
    }
    if(curator != null){
      this.curator = curator;
    } else{
      this.curator = number % 5 == 1 || number % 5 == 3;
    }
    if(artist != null){
      this.artist = artist;
    } else{
      this.artist = number % 3 == 0;
    }
    if(followers != null){
      this.followers = followers;
    }
    if(following != null){
      this.following = following;
    }
    if(groups != null){
      this.groups = groups;
    }
    if(songs != null){
      this.songs = songs;
    }
    if(albums != null){
      this.albums = albums;
    }
    if(playlists != null){
      this.playlists = playlists;
    }
    if(genres != null){
      this.genres = genres;
    }
  }
}