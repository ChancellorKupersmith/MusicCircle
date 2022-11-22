import 'package:flutter/material.dart';
import 'package:lit_firebase_auth/lit_firebase_auth.dart' as firebaseAuth;

import 'package:music_circle/Screens/Profile/User/Body/LoggedInUserProfileAboutBody.dart';
import 'package:music_circle/Screens/Profile/User/Body/LoggedInUserProfileEventsBody.dart';
import 'package:music_circle/Screens/Profile/User/Body/LoggedInUserProfileGroupsBody.dart';
import 'package:music_circle/Screens/Profile/User/Body/LoggedInUserProfilePlaylistsBody.dart';
import 'package:music_circle/Screens/Profile/User/Body/LoggedInUserProfileSongsBody.dart';
import 'package:music_circle/Screens/Profile/User/LoggedInUserProfileAppBar.dart';
import 'package:music_circle/Screens/Profile/User/LoggedInUserProfileConfig.dart';

import 'Body/LoggedInUserProfileAlbumsBody.dart';


class LoggedInUserProfilePage extends StatefulWidget {
  const LoggedInUserProfilePage({Key key}) : super(key: key);

  static final String routeName = '/logged-in-user-profile-page';

  static MaterialPageRoute get route => MaterialPageRoute(
    builder: (context) => const LoggedInUserProfilePage(),
  );

  @override
  LoggedInUserProfilePageState createState() => LoggedInUserProfilePageState();
}

class LoggedInUserProfilePageState extends State<LoggedInUserProfilePage> {
  //body indexes{About: 0, Events: 1, Songs: 2, Albums: 3, Playlists: 4}
  int bodyIndex = 0;
  void setBodyIndex(int index) {
    if(bodyIndex != index){
      setState(() {
        bodyIndex = index;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return LoggedInUserProfileInherited(
      state: this,
      child: Container(
        height: size.height,
        width: size.width,
        color: Colors.white,
        child: Scaffold(
          // extendBodyBehindAppBar: true,
          backgroundColor: Colors.blueGrey.withOpacity(0.5),
         // backgroundColor: Colors.white,
          body: Container(
            color: Colors.white.withOpacity(0.775),
            child: NestedScrollView(
              headerSliverBuilder: (BuildContext context, bool innerBoxIsScrolled){
                return [
                  SliverOverlapAbsorber(
                    handle: NestedScrollView.sliverOverlapAbsorberHandleFor(context),
                    sliver: ProfileAppBar(minHeight: getAppBarMinHeight(bodyIndex, size),),
                  )
                ];
              },
              body: Builder(
                builder: (BuildContext context){
                  return CustomScrollView(
                    slivers: [
                      SliverOverlapInjector(handle: NestedScrollView.sliverOverlapAbsorberHandleFor(context)),
                      SliverToBoxAdapter(child: LoggedInUserProfilePageBody(index: bodyIndex,))
                    ],
                  );
                },
              )
              //UserProfilePageBody(index: bodyIndex,),
              // CustomScrollView(
              //   slivers: [
              //     ProfileAppBar(),
              //     SliverToBoxAdapter(child: LoggedInUserProfilePageBody(index: bodyIndex,)),
              //   ],
              ),
          ),
          )
        ),
      );
    // );
  }
  double getAppBarMinHeight(int index, Size size){
    double minHeight;
    switch(index){
      case 0:{
        minHeight = size.height * 0.1;
      }
      break;
      case 2:{
        minHeight = size.height * 0.25;
      }
      break;
      case 3:{
        minHeight = size.height * 0.2;
      }
      break;
      case 4:{
        minHeight = size.height * 0.2;
      }
      break;
      case 5:{
        minHeight = size.height * 0.1;
      }
      break;
      default:{
        minHeight = 0;
      }
    }
    return minHeight;
  }
}

class LoggedInUserProfilePageBody extends StatelessWidget {
  final int index;
  const LoggedInUserProfilePageBody({Key key, this.index}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    switch(index){
      case 0:{
        return LoggedInUserProfileBodyAbout();
      }
      break;
      case 1:{
        return LoggedInUserProfileEvents();
      }
      break;
      case 2:{
        return LoggedInUserProfileSongsBody();
      }
      break;
      case 3:{
        return LoggedInUserProfileAlbumsBody();
      }
      break;
      case 4:{
        return LoggedInUserProfilePlaylistBody();
      }
      break;
      case 5:{
        return LoggedInUserProfileGroupBody();
      }
      break;
      default:{
        return Container();
      }
    }
  }
}



