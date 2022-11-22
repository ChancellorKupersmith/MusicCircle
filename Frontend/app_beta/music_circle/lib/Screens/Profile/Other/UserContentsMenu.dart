import 'package:flutter/material.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Profile/Other/UserProfileConfig.dart';
import 'package:music_circle/Screens/Profile/Other/UserProfilePage.dart';

class ProfileTableOfContents extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.fromLTRB(8, 0, 0, 0),
      child: Container(
        width: 170,
        height: 263,
        decoration: BoxDecoration(
          borderRadius: BorderRadius.only(
            topLeft: Radius.circular(10),
            bottomLeft: Radius.circular(10),
          ),
          color: Pallet.lightBlue.withOpacity(0.2),
        ),
        child: Align(
          alignment: Alignment.center,
          child: Column(
            children: [
              Expanded(child: contentsButton('About', context)),
              Expanded(child: contentsButton('Events', context)),
              Expanded(child: contentsButton('Songs', context)),
              Expanded(child: contentsButton('Albums', context)),
              Expanded(child: contentsButton('Playlists', context)),
            ],
          ),
        ),
      ),
    );
  }
}

Widget contentsButton(String label, BuildContext context){
  return Container(
    width: 170,
    decoration: BoxDecoration(
      borderRadius: BorderRadius.only(
        topLeft: Radius.circular(10),
        bottomLeft: Radius.circular(10),
      ),
      // color: buttonColor(label.toLowerCase(), context)
    ),
    child: TextButton(
      onPressed: ()=> {
        if(label.toLowerCase() == 'about'){
          UserProfileInherited.of(context).state.setBodyIndex(0)
        }
        else if(label.toLowerCase() == 'events'){
          UserProfileInherited.of(context).state.setBodyIndex(1)
        }
        else if(label.toLowerCase() == 'songs'){
          UserProfileInherited.of(context).state.setBodyIndex(2)
        }
        else if(label.toLowerCase() == 'albums'){
          UserProfileInherited.of(context).state.setBodyIndex(3)
        }
        else if(label.toLowerCase() == 'playlists'){
          UserProfileInherited.of(context).state.setBodyIndex(4)
        }
      },
      style: ButtonStyle(
        backgroundColor: MaterialStateProperty.all(buttonColor(label.toLowerCase(), context)),
      ),
      child: Text(
        label,
        textAlign: TextAlign.center,
        style: TextStyle(
          fontSize: 25,
          color: Pallet.darkBlue,
        ),
      ),
    ),
  );
}

Color buttonColor(String label, BuildContext context){
  Color color = Colors.transparent;
  switch(label){
    case 'about' : {
      if(UserProfileInherited.of(context).state.bodyIndex == 0){
        color = Colors.white;
      }
    }
    break;
    case 'events': {
      if(UserProfileInherited.of(context).state.bodyIndex == 1){
        color = Colors.white;
      }
    }
    break;
    case 'songs': {
      if(UserProfileInherited.of(context).state.bodyIndex == 2){
        color = Colors.white;
      }
    }
    break;
    case 'albums': {
      if(UserProfileInherited.of(context).state.bodyIndex == 3){
        color = Colors.white;
      }
    }
    break;
    case 'playlists': {
      if(UserProfileInherited.of(context).state.bodyIndex == 4){
        color = Colors.white;
      }
    }
    break;
    default:{
      print('Invalid button index');
    }
    break;
  }

  return color;
}