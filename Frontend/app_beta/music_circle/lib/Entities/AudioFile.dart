import 'dart:collection';
import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:music_circle/Entities/Entity.dart';
import 'package:music_circle/Entities/Comment.dart';

import 'Album.dart';
import 'Genre.dart';
import 'User.dart';

class AudioFile extends Entity{
  User artist;
  Album album;
  List<Genre> genres;
  int likes;
  int plays;
  bool isPublic;
  List<Comment> comments;
  Uint8List image;
  Image songImage;
  
  AudioFile(){
    comments = [];
    genres = [];
  }

  @override
  EntityType getType() {
    return EntityType.AUDIO_FILE;
  }
  @override
  String toString(){
    if(name != null){
      return name;
    }
    else{
      return 'Instance of AudioFile';
    }
  }

  testSong({String id, String name, User artist, Album album, List<Genre> genres, int likes, int plays, bool isPublic, List<Comment> comments, Uint8List image, Image songImage, @required int number}){
    if(id != null){
      this.id = id;
    } else{
      this.id = 'songID' + number.toString();
    }
    if(name != null){
      this.name = name;
    } else{
      this.name = 'Song ' + number.toString();
    }
    if(artist != null){
      this.artist = artist;
    } else{
      this.artist = new User();
      this.artist.testUser(number: number);
    }
    if(album != null){
      this.album = album;
    } else{
      this.album = new Album();
      this.album.testAlbum(number: number);
    }
    if(genres != null){
      this.genres = genres;
    }
    if(likes != null){
      this.likes = likes;
    } else{
      this.likes = number;
    }
    if(plays != null){
      this.plays = plays;
    } else{
      this.plays = number;
    }
    if(isPublic != null){
      this.isPublic = isPublic;
    } else{
      this.isPublic = number % 2 == 0;
    }
    if(comments != null){
      this.comments = comments;
    }
    if(image != null){
      this.image = image;
    }
    if(songImage != null){
      this.songImage = songImage;
    } else{
      this.songImage = Image.asset(
        'lib/Resources/Images/song1_example_pic.jpg',
        fit: BoxFit.cover,
      );
    }
  }
}