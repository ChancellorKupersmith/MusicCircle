import 'package:flutter/material.dart';
import 'package:lit_firebase_auth/lit_firebase_auth.dart' as firebaseAuth;

import 'package:music_circle/Screens/Profile/Other/Body/UserProfileAboutBody.dart';
import 'package:music_circle/Screens/Profile/Other/Body/UserProfileEventsBody.dart';
import 'package:music_circle/Screens/Profile/Other/Body/UserProfilePlaylistsBody.dart';
import 'package:music_circle/Screens/Profile/Other/Body/UserProfileSongsBody.dart';
import 'package:music_circle/Screens/Profile/Other/UserProfileAppBar.dart';
import 'package:music_circle/Screens/Profile/Other/UserProfileConfig.dart';

import 'Body/UserProfileAlbumsBody.dart';


class UserProfilePage extends StatefulWidget {
  const UserProfilePage({Key key}) : super(key: key);

  static final String routeName = '/user-profile-page';

  static MaterialPageRoute get route => MaterialPageRoute(
    builder: (context) => const UserProfilePage(),
  );

  @override
  UserProfilePageState createState() => UserProfilePageState();
}

class UserProfilePageState extends State<UserProfilePage> {
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
    return UserProfileInherited(
      state: this,
      child: Container(
        height: size.height,
        width: size.width,
        color: Colors.white,
        child: Scaffold(
          // extendBodyBehindAppBar: true,
          backgroundColor: Colors.blueGrey.withOpacity(0.5),
          body: NestedScrollView(
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
                    SliverToBoxAdapter(child: UserProfilePageBody(index: bodyIndex,))
                  ],
                );
              },
            )
            //UserProfilePageBody(index: bodyIndex,),
            // CustomScrollView(
            //   slivers: [
            //     ProfileAppBar(),
            //     SliverToBoxAdapter(child: UserProfilePageBody(index: bodyIndex,)),
            //   ],
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
      default:{
        minHeight = 0;
      }
    }
    return minHeight;
  }
}

class UserProfilePageBody extends StatelessWidget {
  final int index;
  const UserProfilePageBody({Key key, this.index}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    switch(index){
      case 0:{
        return UserProfileBodyAbout();
      }
      break;
      case 1:{
        return UserProfileEvents();
      }
      break;
      case 2:{
        return UserProfileSongsBody();
      }
      break;
      case 3:{
        return UserProfileAlbumsBody();
      }
      break;
      case 4:{
        return UserProfilePlaylistBody();
      }
      break;
      default:{
        return Container();
      }
    }
  }
}



