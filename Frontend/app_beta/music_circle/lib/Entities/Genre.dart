import 'package:flutter/cupertino.dart';
import 'package:music_circle/Entities/Entity.dart';

class Genre extends Entity{
  Genre();

  @override
  EntityType getType() {
    return EntityType.GENRE;
  }

  testGenre({String id, String name, @required int number}){
    if(id != null){
      this.id = id;
    } else{
      this.id = 'genreID';
    }
    if(name != null){
      this.name = name;
    } else{
      this.name = 'Genre ' + number.toString();
    }
  }
}