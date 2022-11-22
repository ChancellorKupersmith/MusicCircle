

import 'package:music_circle/Entities/AudioFile.dart';
import 'package:music_circle/Entities/Comment.dart';
import 'package:music_circle/Entities/Entity.dart';
import 'package:music_circle/Entities/Playlist.dart';
import 'package:music_circle/Entities/Event.dart';
import 'package:music_circle/Entities/Album.dart';
import 'package:music_circle/Entities/Group.dart';
import 'package:music_circle/Entities/User.dart';
import 'package:flutter/material.dart';

abstract class Post extends Entity{
  User creator;
  DateTime datePosted;
  Image postPic;
  String label;
  List<Comment> comments;
  int likes;

  @override
  EntityType getType() => EntityType.POST;

  bool isEqual(Post post) =>
    this.id == post.id &&
    this.comments == post.comments &&
    this.likes == post.likes;

  EntityType getContentType();
  testPost({String id, User creator, DateTime datePosted, Image postPic, String label, List<Comment> comments, int likes, AudioFile song, Album album, Playlist playlist, User artist, Group group, Event event, String text, @required int number});
}

class SongPost extends Post{
  AudioFile song;

  SongPost(this.song){
    this.comments = [];
  }

  @override
  EntityType getContentType() => EntityType.AUDIO_FILE;

  @override
  testPost({String id, User creator, DateTime datePosted, Image postPic, String label, List<Comment> comments, int likes, AudioFile song, Album album, Playlist playlist, User artist, Group group, Event event, String text, @required int number}) {
    if(id != null){
      this.id = id;
    } else{
      this.id = 'songPostID';
    }
    if(creator != null){
      this.creator = creator;
    } else{
      this.creator = new User().testUser(number: number);
    }
    if(datePosted != null){
      this.datePosted = datePosted;
    } else{
      this.datePosted = DateTime.now();
    }
    if(label != null){
      this.label = label;
    } else{
      this.label = "this post's label";
    }
    if(comments != null){
      this.comments = comments;
    } else{
      for(int i=0; i<15; i++){
        this.comments.add(new Comment().testComment(number: number));
      }
    }
    if(likes != null){
      this.likes = likes;
    } else{
      this.likes = number;
    }
    if(song != null){
      this.song = song;
    } else{
      this.song = new AudioFile().testSong(number: number);
    }
    if(postPic != null){
      this.postPic = postPic;
    } else{
      this.postPic = this.song.songImage;
    }
  }
}

class AlbumPost extends Post{
  Album album;

  AlbumPost(this.album){
    this.comments = [];
  }

  @override
  EntityType getContentType() => EntityType.ALBUM;

  @override
  testPost({String id, User creator, DateTime datePosted, Image postPic, String label, List<Comment> comments, int likes, AudioFile song, Album album, Playlist playlist, User artist, Group group, Event event, String text, @required int number}) {
    if(id != null){
      this.id = id;
    } else{
      this.id = 'albumPostID';
    }
    if(creator != null){
      this.creator = creator;
    } else{
      this.creator = new User().testUser(number: number);
    }
    if(datePosted != null){
      this.datePosted = datePosted;
    } else{
      this.datePosted = DateTime.now();
    }
    if(label != null){
      this.label = label;
    } else{
      this.label = "this post's label";
    }
    if(comments != null){
      this.comments = comments;
    } else{
      for(int i=0; i<15; i++){
        this.comments.add(new Comment().testComment(number: number));
      }
    }
    if(likes != null){
      this.likes = likes;
    } else{
      this.likes = number;
    }
    if(album != null){
      this.album = album;
    } else{
      this.album = new Album().testAlbum(number: number);
    }
    if(postPic != null){
      this.postPic = postPic;
    } else{
      this.postPic = this.album.albumImage;
    }
  }
}

class PlaylistPost extends Post{
  Playlist playlist;

  PlaylistPost(this.playlist){
    this.comments = [];
  }

  @override
  EntityType getContentType() => EntityType.PLAYLIST;


  @override
  testPost({String id, User creator, DateTime datePosted, Image postPic, String label, List<Comment> comments, int likes, AudioFile song, Album album, Playlist playlist, User artist, Group group, Event event, String text, @required int number}) {
    if(id != null){
      this.id = id;
    } else{
      this.id = 'playlistPostID';
    }
    if(creator != null){
      this.creator = creator;
    } else{
      this.creator = new User().testUser(number: number);
    }
    if(datePosted != null){
      this.datePosted = datePosted;
    } else{
      this.datePosted = DateTime.now();
    }
    if(label != null){
      this.label = label;
    } else{
      this.label = "this post's label";
    }
    if(comments != null){
      this.comments = comments;
    } else{
      for(int i=0; i<15; i++){
        this.comments.add(new Comment().testComment(number: number));
      }
    }
    if(likes != null){
      this.likes = likes;
    } else{
      this.likes = number;
    }
    if(playlist != null){
      this.playlist = playlist;
    } else{
      this.playlist = new Playlist().testPlaylist(number: number);
    }
    if(postPic != null){
      this.postPic = postPic;
    } else{
      this.postPic = this.playlist.playlistImage;
    }
  }
}

class ArtistPost extends Post{
  User artist;

  ArtistPost(this.artist){
    this.comments = [];
  }

  @override
  EntityType getContentType() => EntityType.USER;

  @override
  testPost({String id, User creator, DateTime datePosted, Image postPic, String label, List<Comment> comments, int likes, AudioFile song, Album album, Playlist playlist, User artist, Group group, Event event, String text, @required int number}) {
    if(id != null){
      this.id = id;
    } else{
      this.id = 'artistPostID';
    }
    if(creator != null){
      this.creator = creator;
    } else{
      this.creator = new User().testUser(number: number);
    }
    if(datePosted != null){
      this.datePosted = datePosted;
    } else{
      this.datePosted = DateTime.now();
    }
    if(label != null){
      this.label = label;
    } else{
      this.label = "this post's label";
    }
    if(comments != null){
      this.comments = comments;
    } else{
      for(int i=0; i<15; i++){
        this.comments.add(new Comment().testComment(number: number));
      }
    }
    if(likes != null){
      this.likes = likes;
    } else{
      this.likes = number;
    }
    if(artist != null){
      this.artist = artist;
    } else{
      this.artist = new User().testUser(number: number);
    }
    if(postPic != null){
      this.postPic = postPic;
    } else{
      this.postPic = this.artist.userImage;
    }
  }
}

class GroupPost extends Post{
  Group group;

  GroupPost(this.group){
    this.comments = [];
  }

  @override
  EntityType getContentType() => EntityType.GROUP;

  @override
  testPost({String id, User creator, DateTime datePosted, Image postPic, String label, List<Comment> comments, int likes, AudioFile song, Album album, Playlist playlist, User artist, Group group, Event event, String text, @required int number}) {
    if(id != null){
      this.id = id;
    } else{
      this.id = 'groupPostID';
    }
    if(creator != null){
      this.creator = creator;
    } else{
      this.creator = new User().testUser(number: number);
    }
    if(datePosted != null){
      this.datePosted = datePosted;
    } else{
      this.datePosted = DateTime.now();
    }
    if(label != null){
      this.label = label;
    } else{
      this.label = "this post's label";
    }
    if(comments != null){
      this.comments = comments;
    } else{
      for(int i=0; i<15; i++){
        this.comments.add(new Comment().testComment(number: number));
      }
    }
    if(likes != null){
      this.likes = likes;
    } else{
      this.likes = number;
    }
    if(group != null){
      this.group = group;
    } else{
      this.group = new Group().testGroup(number: number);
    }
    if(postPic != null){
      this.postPic = postPic;
    } else{
      this.postPic = this.group.groupImage;
    }
  }
}

class EventPost extends Post{
  Event event;

  EventPost(this.event){
    this.comments = [];
  }

  @override
  EntityType getContentType() => EntityType.EVENT;

  @override
  testPost({String id, User creator, DateTime datePosted, Image postPic, String label, List<Comment> comments, int likes, AudioFile song, Album album, Playlist playlist, User artist, Group group, Event event, String text, @required int number}) {
    if(id != null){
      this.id = id;
    } else{
      this.id = 'eventPostID';
    }
    if(creator != null){
      this.creator = creator;
    } else{
      this.creator = new User().testUser(number: number);
    }
    if(datePosted != null){
      this.datePosted = datePosted;
    } else{
      this.datePosted = DateTime.now();
    }
    if(label != null){
      this.label = label;
    } else{
      this.label = "this post's label";
    }
    if(comments != null){
      this.comments = comments;
    } else{
      for(int i=0; i<15; i++){
        this.comments.add(new Comment().testComment(number: number));
      }
    }
    if(likes != null){
      this.likes = likes;
    } else{
      this.likes = number;
    }
    if(event != null){
      this.event = event;
    } else{
      this.event = new Event().testEvent(number: number);
    }
    if(postPic != null){
      this.postPic = postPic;
    } else{
      this.postPic = this.event.eventImage;
    }
  }
}

class TextPost extends Post{
  String text;

  TextPost(this.text){
    this.comments = [];
  }

  @override
  EntityType getContentType() => EntityType.COMMENT;

  @override
  testPost({String id, User creator, DateTime datePosted, Image postPic, String label, List<Comment> comments, int likes, AudioFile song, Album album, Playlist playlist, User artist, Group group, Event event, String text, @required int number}) {
    if(id != null){
      this.id = id;
    } else{
      this.id = 'textPostID';
    }
    if(creator != null){
      this.creator = creator;
    } else{
      this.creator = new User().testUser(number: number);
    }
    if(datePosted != null){
      this.datePosted = datePosted;
    } else{
      this.datePosted = DateTime.now();
    }
    if(label != null){
      this.label = label;
    } else{
      this.label = "this post's label";
    }
    if(comments != null){
      this.comments = comments;
    } else{
      for(int i=0; i<15; i++){
        this.comments.add(new Comment().testComment(number: number));
      }
    }
    if(likes != null){
      this.likes = likes;
    } else{
      this.likes = number;
    }
    if(text != null){
      this.text = text;
    } else{
      this.text = 'Text post';
    }
    // if(postPic != null){
    //   this.postPic = postPic;
    // } else{
    //   this.postPic = this.event.eventImage;
    // }
  }
}

