import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:lit_firebase_auth/lit_firebase_auth.dart' as firebaseAuth;
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Discover/DiscoverPage.dart';
import 'package:music_circle/Screens/Login/LoginPage.dart';
import 'package:music_circle/Screens/Profile/Music/SongsPage.dart';
import 'package:music_circle/Screens/Profile/Other/UserProfilePage.dart';
import 'package:music_circle/Screens/Profile/User/LoggedInUserProfilePage.dart';

import 'LoggedInUser.dart';


void main() {
  WidgetsFlutterBinding.ensureInitialized();
  SystemChrome.setPreferredOrientations([DeviceOrientation.portraitUp]).then((value) => runApp(MyApp()));
}

class MyApp extends StatelessWidget {
  // Widget for settings
  @override
  Widget build(BuildContext context) {
    return firebaseAuth.LitAuthInit(
      child: MusicCircle(
        child: MaterialApp(
          title: 'MusicCircle',
          debugShowCheckedModeBanner: false,
          theme: ThemeData(
            visualDensity: VisualDensity.adaptivePlatformDensity,
            textTheme: GoogleFonts.muliTextTheme(),
            accentColor: Pallet.lightBlue,
            appBarTheme: const AppBarTheme(
              brightness: Brightness.dark,
              color: Colors.blueGrey,
            ),
            primarySwatch: Colors.blueGrey
          ),
          routes: {
            LoginPage.routeName: (context) => LoginPage(),
            DiscoverPage.routeName: (context) => DiscoverPage(),
            UserProfilePage.routeName: (context) => UserProfilePage(),
            LoggedInUserProfilePage.routeName: (context) => LoggedInUserProfilePage(),
          },
          home: UserInfo(),
          // LitAuthState(
          //   unauthenticated: LoginPage(),
          //   authenticated: DiscoverPage(),
          // ),
        ),
      ),
    );
  }
}

//Top level parent Widgets to hold/update any data that might change for entire app
class MusicCircle extends StatefulWidget {
  final Widget child;
  const MusicCircle({Key key, this.child}) : super(key: key);
  @override
  MusicCircleState createState() => MusicCircleState();
}

class MusicCircleState extends State<MusicCircle> {
  //Variables

  @override
  Widget build(BuildContext context) {
    Widget child = widget.child;
    return MusicCircleInherited(state: this, child: child);
  }
}

class MusicCircleInherited extends InheritedWidget{
  final MusicCircleState state;
  final Widget child;


  MusicCircleInherited({this.state, this.child}) : super(child: child);

  static MusicCircleState of(BuildContext context){
    return context.dependOnInheritedWidgetOfExactType<MusicCircleInherited>().state;
  }

  @override
  bool updateShouldNotify(covariant MusicCircleInherited oldWidget) {
    return true;
  }
}
