import 'dart:typed_data';


import 'package:flutter/material.dart';
import 'package:music_circle/Entities/Entity.dart';
import 'package:music_circle/Entities/Playlist.dart';

import 'AudioFile.dart';
import 'Genre.dart';
import 'User.dart';

class Album extends Playlist{
  User artist;
  bool single;
  Uint8List image;
  Image albumImage;

  Album(){
    songs = [];
    genres = [];
    listeners = [];
  }

  @override
  EntityType getType() {
    return EntityType.ALBUM;
  }

  testAlbum({String id, String name, User artist, bool single, Uint8List image, Image albumImage, List<AudioFile> songs, List<Genre> genres, @required int number}){
    if(id != null){
      this.id = id;
    } else{
      this.id = 'testAlbum' + number.toString();
    }
    if(name != null){
      this.name = name;
    } else{
      this.name = 'Album ' + number.toString();
    }
    if(artist != null){
      this.artist = artist;
    } else{
      this.artist = new User();
      this.artist.testUser(number: number, );
    }
    if(single != null){
      this.single = single;
    } else{
      this.single = true;
    }
    if(image != null){
      this.image = image;
    }
    if(albumImage != null){
      this.albumImage = albumImage;
    } else{
      this.albumImage = Image.asset(
        'lib/Resources/Images/album1_example_pic.jpg',
        fit: BoxFit.cover,
      );
    }
    if(songs != null){
      this.songs = songs;
    }
    if(genres != null){
      this.genres = genres;
    }
  }
  
}