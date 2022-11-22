import 'package:flutter/material.dart';
import 'package:music_circle/Screens/Profile/User/LoggedInUserProfilePage.dart';
class LoggedInUserProfileInherited extends InheritedWidget{

  final LoggedInUserProfilePageState state;

  LoggedInUserProfileInherited({this.state, Widget child}) : super(child: child);

  @override
  bool updateShouldNotify(covariant LoggedInUserProfileInherited oldWidget) {
    return true;
  }

  static LoggedInUserProfileInherited of(BuildContext context) =>
      context.dependOnInheritedWidgetOfExactType<LoggedInUserProfileInherited>();

}