import 'dart:typed_data';
import 'package:flutter/material.dart';

import 'package:music_circle/Entities/Entity.dart';

import 'Chat.dart';
import 'User.dart';

class Group extends Entity{
  String description;
  List<User> members;
  List<User> admins;
  List<Chat> chats;
  bool isPublic;
  bool freeToJoin;
  Uint8List image;
  Image groupImage;

  Group(){
    members = [];
    admins = [];
    chats = [];
  }

  @override
  EntityType getType() {
    return EntityType.GROUP;
  }

  testGroup({String id, String name, String description, List<User> members, List<User> admins, List<Chat> chatIDs, bool isPublic, bool freeToJoin, Uint8List image, Image groupImage, @required int number}){
    if(id != null){
      this.id = id;
    } else{
      this.id = 'groupID';
    }
    if(name != null){
      this.name = name;
    } else{
      this.name = 'Group ' + number.toString();
    }
    if(description != null){
      this.description = description;
    } else{
      this.description = "This group's description";
    }
    if(members != null){
      this.members = members;
    }
    if(admins != null){
      this.admins = admins;
    }
    if(chatIDs != null){
      this.chats = chatIDs;
    }
    if(isPublic != null){
      this.isPublic = isPublic;
    } else{
      this.isPublic = true;
    }
    if(freeToJoin != null){
      this.freeToJoin = freeToJoin;
    } else{
      this.freeToJoin = true;
    }
    if(image != null){
      this.image = image;
    }
    if(groupImage != null){
      this.groupImage = groupImage;
    } else{
      this.groupImage = Image.asset(
        'lib/Resources/Images/group1_example_pic.jpg',
        fit: BoxFit.cover,
      );
    }
  }
}