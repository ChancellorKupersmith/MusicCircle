import 'package:flutter/cupertino.dart';
import 'package:music_circle/Entities/AudioFile.dart';
import 'package:music_circle/Entities/Entity.dart';

import 'Chat.dart';
import 'User.dart';

class Comment extends Entity{
  String text;
  User commenter;
  Chat chat;
  int likes;
  Comment();

  @override
  EntityType getType() {
    return EntityType.COMMENT;
  }

  testComment({String id, String text, User commenter, Chat chat, int likes, @required int number}){
    if(id != null){
      this.id = id;
    } else{
      this.id = 'commentID';
    }
    if(text != null){
      this.text = text;
    } else{
      this.text = "this comment's text " + number.toString();
    }
    if(chat != null){
      this.chat = chat;
    } else{
      this.chat = new Chat();
      this.chat.testChat(index: number);
    }
    if(likes != null){
      this.likes = likes;
    } else{
      this.likes = number;
    }
  }
}