
import 'package:flutter/material.dart';
import 'package:music_circle/Entities/AudioFile.dart';
import 'package:music_circle/Entities/Entity.dart';

import 'Genre.dart';
import 'User.dart';

class Playlist extends Entity{
  User creator;
  bool isPublic;
  List<User> listeners;
  List<AudioFile> songs;
  List<Genre> genres;
  Image playlistImage;
  Playlist(){
    songs = [];
    genres = [];
    listeners = [];
  }

  @override
  EntityType getType() {
    return EntityType.PLAYLIST;
  }

  testPlaylist({String id, String name, User creator, bool isPublic, List<User> listeners, List<AudioFile> songs, List<Genre> genres, Image playlistImage, @required int number}){
    if(id != null){
      this.id = id;
    } else{
      this.id = 'playlistID';
    }
    if(name != null){
      this.name = name;
    } else{
      this.name = 'Playlist ' + number.toString();
    }
    if(creator != null){
      this.creator = creator;
    } else{
      this.creator = new User();
      this.creator.testUser(number: number);
    }
    if(isPublic != null){
      this.isPublic = isPublic;
    } else{
      this.isPublic = true;
    }
    if(listeners != null){
      this.listeners = listeners;
    }
    if(songs != null){
      this.songs = songs;
    }
    if(genres != null){
      this.genres = genres;
    }
    if(playlistImage != null){
      this.playlistImage = playlistImage;
    } else{
      this.playlistImage = Image.asset(
        'lib/Resources/Images/album3_example_pic.jpg',
        fit: BoxFit.cover,
      );
    }
  }

}