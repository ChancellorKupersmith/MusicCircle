import 'package:flutter/material.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Profile/User/LoggedInUserProfileConfig.dart';
import 'package:music_circle/Screens/Profile/User/LoggedInUserProfilePage.dart';

class ProfileTableOfContents extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.fromLTRB(8, 0, 0, 0),
      child: Container(
        width: 170,
        height: 360,
        decoration: BoxDecoration(
          borderRadius: BorderRadius.only(
            topLeft: Radius.circular(10),
            bottomLeft: Radius.circular(10),
          ),
          color: Pallet.lightBlue.withOpacity(0.3),
        ),
        child: Align(
          alignment: Alignment.center,
          child: Column(
            children: [
              Expanded(child: contentsButton('Settings', context)),
              Expanded(child: contentsButton('About', context)),
              Expanded(child: contentsButton('Events', context)),
              Expanded(child: contentsButton('Songs', context)),
              Expanded(child: contentsButton('Albums', context)),
              Expanded(child: contentsButton('Playlists', context)),
              Expanded(child: contentsButton('Groups', context)),
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
          LoggedInUserProfileInherited.of(context).state.setBodyIndex(0)
        }
        else if(label.toLowerCase() == 'events'){
          LoggedInUserProfileInherited.of(context).state.setBodyIndex(1)
        }
        else if(label.toLowerCase() == 'songs'){
          LoggedInUserProfileInherited.of(context).state.setBodyIndex(2)
        }
        else if(label.toLowerCase() == 'albums'){
          LoggedInUserProfileInherited.of(context).state.setBodyIndex(3)
        }
        else if(label.toLowerCase() == 'playlists'){
          LoggedInUserProfileInherited.of(context).state.setBodyIndex(4)
        }
        else if(label.toLowerCase() == 'groups'){
            LoggedInUserProfileInherited.of(context).state.setBodyIndex(5)
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
      if(LoggedInUserProfileInherited.of(context).state.bodyIndex == 0){
        color = Colors.white;
      }
    }
    break;
    case 'events': {
      if(LoggedInUserProfileInherited.of(context).state.bodyIndex == 1){
        color = Colors.white;
      }
    }
    break;
    case 'songs': {
      if(LoggedInUserProfileInherited.of(context).state.bodyIndex == 2){
        color = Colors.white;
      }
    }
    break;
    case 'albums': {
      if(LoggedInUserProfileInherited.of(context).state.bodyIndex == 3){
        color = Colors.white;
      }
    }
    break;
    case 'playlists': {
      if(LoggedInUserProfileInherited.of(context).state.bodyIndex == 4){
        color = Colors.white;
      }
    }
    break;
    case 'groups': {
      if(LoggedInUserProfileInherited.of(context).state.bodyIndex == 5){
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