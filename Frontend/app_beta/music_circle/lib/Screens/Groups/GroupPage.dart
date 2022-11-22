import 'package:flutter/material.dart';
import 'package:music_circle/Entities/Group.dart';
import 'package:music_circle/Entities/User.dart';
import 'package:music_circle/Screens/Groups/GroupInfoPage.dart';
import 'package:music_circle/Screens/Groups/MemberGroupPage.dart';

class GroupPage extends StatefulWidget {
  final Group group;
  const GroupPage(this.group, {Key key}) : super(key: key);

  static final String routeName = '/group-page';

  @override
  GroupPageState createState() => GroupPageState();
}

class GroupPageState extends State<GroupPage> {
  Group group;
  void setGroup(Group group){
    if(this.group == null || this.group != group){
      setState(() {
        this.group = group;
      });
    }
  }
  int groupPageType = 1;
  void setGroupPageType(int type){
    if(groupPageType != type){
      setState(() {
        groupPageType = type;
      });
    }
  }
  
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    setGroup(widget.group);
    return GroupPageInherited(
      state: this,
      child: Container(
        height: size.height,
        width: size.width,
        child: GroupPageType(type: groupPageType,),
      ),
    );
  }
}

class GroupPageInherited extends InheritedWidget{
  final GroupPageState state;

  GroupPageInherited({this.state, Widget child}) : super(child: child);

  @override
  bool updateShouldNotify(covariant GroupPageInherited oldWidget) {
    return true;
  }

  static GroupPageInherited of(BuildContext context) =>
      context.dependOnInheritedWidgetOfExactType<GroupPageInherited>();
}

class GroupPageType extends StatelessWidget {
  final int type;
  const GroupPageType({Key key, this.type}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    switch(type){
      case 1:{
        return MemberGroupPage();
      }
      break;
      default:{
        return GroupInfoPage();
      }
    }
  }
}


