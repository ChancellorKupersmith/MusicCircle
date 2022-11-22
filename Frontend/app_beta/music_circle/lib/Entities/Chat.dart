import 'dart:typed_data';
import 'package:flutter/material.dart';

import 'package:music_circle/Entities/Comment.dart';
import 'package:music_circle/Entities/Entity.dart';

import 'Group.dart';

class Chat extends Entity{
  int index;
  List<Comment> comments;
  Uint8List image;
  Image chatImage;
  bool unreadComments;

  Chat(){
    comments = [];
  }

  @override
  EntityType getType() {
    return EntityType.CHAT;
  }

  testChat({String id, String name, @required int index, List<Comment> comments, Uint8List image, Image chatImage, bool unreadComments}){
    if(id != null){
      this.id = id;
    } else{
      this.id = 'chatID';
    }
    if(name != null){
      this.name = name;
    } else{
      this.name = 'Chat ' + index.toString();
    }
    if(comments != null){
      this.comments = comments;
    }
    if(image != null){
      this.image = image;
    }
    if(chatImage != null){
      this.chatImage = chatImage;
    } else{
      this.chatImage = Image.asset(
        'lib/Resources/Images/album2_example_pic.jpg',
        fit: BoxFit.cover,
      );
    }
    if(unreadComments != null){
      this.unreadComments = unreadComments;
    } else{
      this.unreadComments = index % 3 == 0;
    }

  }

}