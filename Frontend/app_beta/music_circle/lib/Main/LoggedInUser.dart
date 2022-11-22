import 'package:flutter/material.dart';
import 'package:lit_firebase_auth/lit_firebase_auth.dart' as firebaseAuth;

import 'package:music_circle/Entities/User.dart';
import 'package:music_circle/Screens/Discover/DiscoverPage.dart';
import 'package:music_circle/Screens/Login/LoginPage.dart';
import 'package:music_circle/Screens/Login/SplashScreen.dart';


//Widget to hold all logged in user info
class UserInfo extends StatefulWidget {
  const UserInfo({Key key}) : super(key: key);
  @override
  UserInfoState createState() => UserInfoState();
}

class UserInfoState extends State<UserInfo> {
  User _loggedInUser = new User();
  User get loggedInUser => _loggedInUser;
  void init_loggedInUser(String id, String artistName, String email){
    setState(() {
      _loggedInUser.id = id;
      _loggedInUser.name = artistName;
      _loggedInUser.email = email;
    });
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      getLoggedInUserInfo();
    });
  }
  @override
  Widget build(BuildContext context) {
    // WidgetsBinding.instance.addPostFrameCallback((_) => onAfterBuild(context));
    // final user = context.watchSignedInUser();
    // user.map(
    //       (value) {
    //     Navigator.pushReplacementNamed(context, DiscoverPage.routeName);
    //   },
    //   empty: (_){
    //     Navigator.pushReplacementNamed(context, LoginPage.routeName);
    //   },
    //   initializing: (_){},
    // );
    //TODO: figure out what is the best child of this
    // return SplashScreen();
    return UserInfoInherited(state: this, child: SplashScreen());
  }
  void onAfterBuild(BuildContext context){
    getLoggedInUserInfo();
  }


  //Gets User's info from database
//TODO
  getLoggedInUserInfo(){
    //JSON get request to get user info
    //TODO

    //Test user
    init_loggedInUser('testID', 'Test User', 'test_user@gmail.com');
  }
}

class UserInfoInherited extends InheritedWidget{
  final UserInfoState state;


  UserInfoInherited({@required this.state, @required Widget child}) : super(child: child);

  static UserInfoInherited of(BuildContext context){
    return context.dependOnInheritedWidgetOfExactType<UserInfoInherited>();
  }

  @override
  bool updateShouldNotify(covariant UserInfoInherited oldWidget) =>
      state._loggedInUser != oldWidget.state.loggedInUser;

}