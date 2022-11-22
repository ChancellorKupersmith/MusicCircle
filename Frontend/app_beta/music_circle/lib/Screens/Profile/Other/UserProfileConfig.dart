import 'package:flutter/material.dart';
import 'package:music_circle/Screens/Profile/Other/UserProfilePage.dart';
class UserProfileInherited extends InheritedWidget{

  final UserProfilePageState state;

  UserProfileInherited({this.state, Widget child}) : super(child: child);

  @override
  bool updateShouldNotify(covariant UserProfileInherited oldWidget) {
    return true;
  }

  static UserProfileInherited of(BuildContext context) =>
      context.dependOnInheritedWidgetOfExactType<UserProfileInherited>();

}