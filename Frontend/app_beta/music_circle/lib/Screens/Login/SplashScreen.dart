import 'package:flutter/material.dart';
import 'package:lit_firebase_auth/lit_firebase_auth.dart';
import 'package:music_circle/Main/LoggedInUser.dart';
import 'package:music_circle/Screens/Discover/DiscoverPage.dart';

import 'LoginPage.dart';

class SplashScreen extends StatelessWidget {
  const SplashScreen({Key key}) : super(key: key);

  static MaterialPageRoute get route => MaterialPageRoute(
      builder: (context) => const SplashScreen(),
  );


  @override
  Widget build(BuildContext context) {
    final user = context.watchSignedInUser();
    user.map(
        (value){
          navigateToDiscoverPage(context);
        },
        empty: (_){
          navigateToLoginPage(context);
        },
        initializing: (_){},
    );
    return const Scaffold(
      body: Center(
        child: CircularProgressIndicator(),
      ),
    );
  }
}

void navigateToLoginPage(BuildContext context){
  WidgetsBinding.instance.addPostFrameCallback(
        (_) => Navigator.of(context).pushReplacementNamed(LoginPage.routeName)
  );
}

void navigateToDiscoverPage(BuildContext context){
  WidgetsBinding.instance.addPostFrameCallback(
        (_) => Navigator.of(context).pushReplacementNamed(DiscoverPage.routeName)
  );
}
