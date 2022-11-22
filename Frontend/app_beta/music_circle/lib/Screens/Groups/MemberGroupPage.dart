import 'dart:math';

import 'package:flutter/material.dart';
import 'package:music_circle/Entities/Group.dart';
import 'package:music_circle/Entities/User.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Groups/GroupPage.dart';
import 'package:music_circle/Screens/Groups/GroupPageBody.dart';
import 'package:music_circle/UI_Tools/BackButton.dart';
import 'package:music_circle/UI_Tools/ImageButtons.dart';
import 'package:music_circle/UI_Tools/SocialMediaWidgets.dart';

class MemberGroupPage extends StatefulWidget {
  const MemberGroupPage({Key key}) : super(key: key);

  @override
  MemberGroupPageState createState() => MemberGroupPageState();
}

class MemberGroupPageState extends State<MemberGroupPage> {
  int bodyIndex = 0;
  void setBodyIndex(int index){
    if(bodyIndex != index){
      setState(() {
        bodyIndex = index;
      });
    }
  }
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return MemberGroupPageInherited(
      state: this,
      child: Scaffold(
        backgroundColor: Colors.white,
        bottomNavigationBar: GroupPageBottomNavBar(bodyIndex: bodyIndex,),
        body: Container(
          height: size.height,
          width: size.width,
          color: Colors.blueGrey.withOpacity(0.5),
          child: Container(
            height: size.height,
            width: size.width,
            color: Colors.white.withOpacity(0.775),
            child: CustomScrollView(
              slivers: [
                SliverPersistentHeader(
                  floating: bodyIndex != 1,
                  //pinned: bodyIndex == 1,
                  delegate: MemberGroupPageAppBarDelegate(maxHeight: size.height * 0.3125, minHeight: size.height * 0.3125),
                ),
                SliverToBoxAdapter(child: GroupPageBody(bodyIndex: bodyIndex,)),
              ],
            ),
          ),
        ),
      ),
    );
  }
}

class MemberGroupPageInherited extends InheritedWidget{
  final MemberGroupPageState state;

  MemberGroupPageInherited({this.state, Widget child}) : super(child: child);

  @override
  bool updateShouldNotify(covariant MemberGroupPageInherited oldWidget) {
    return true;
  }

  static MemberGroupPageInherited of(BuildContext context) =>
      context.dependOnInheritedWidgetOfExactType<MemberGroupPageInherited>();
}

class MemberGroupPageAppBarDelegate extends SliverPersistentHeaderDelegate {
  final double maxHeight, minHeight;

  MemberGroupPageAppBarDelegate({@required this.maxHeight, @required this.minHeight});
  @override
  Widget build(BuildContext context, double shrinkOffset, bool overlapsContent) {
    Size size = MediaQuery.of(context).size;
    return Container(
      decoration: BoxDecoration(
        color: Pallet.darkBlue,
        borderRadius: BorderRadius.only(
          bottomRight: Radius.circular(15),
          bottomLeft: Radius.circular(15),
        )
      ),
      child: Stack(
        children: [
          Positioned(
            // top: getTopPosBackground(context, size, shrinkOffset),
            top: 0,
            child: buildBackground(context, shrinkOffset, size, GroupPageInherited.of(context).state.group),
          ),
          Positioned(
              top: getTopPosBackBtn(context, size, shrinkOffset),
              left: 10,
              child: CircleBackButton(backgroundColor: Pallet.lightBlue.withOpacity(0.6), iconColor: Colors.white)
          ),
          Positioned(
            // top: getTopPosMemberList(context, size, shrinkOffset),
            top: size.height * 0.2,
            child: buildMembersList(context, shrinkOffset, size, GroupPageInherited.of(context).state.group)),
          // Positioned(
          //   top: max(size.height * 0.3125 - shrinkOffset, size.height * 0.3125 -  size.height * 0.2),
          //   left: size.width * 0.05,
          //   child: Container(
          //     height: 1,
          //     width: size.width * 0.9,
          //     color: Colors.blueGrey.withOpacity(0.2),
          //   ),
          // ),
        ],
      ),
    );
  }

  double getTopPosMemberList(BuildContext context, Size size, double shrinkOffset){
    if(MemberGroupPageInherited.of(context).state.bodyIndex == 1){
      return max(size.height * 0.2 - shrinkOffset, 0);
    }
    else{
      return size.height * 0.2;
    }
  }
  double getTopPosBackground(BuildContext context, Size size, double shrinkOffset){
    if(MemberGroupPageInherited.of(context).state.bodyIndex == 1){
      return shrinkOffset * -1;
    }
    else{
      return 0;
    }
  }
  double getTopPosBackBtn(BuildContext context, Size size, double shrinkOffset){
    if(MemberGroupPageInherited.of(context).state.bodyIndex == 1){
      return shrinkOffset * -1/4 + 10;
    }
    else{
      return 10;
    }
  }

  @override
  double get maxExtent => maxHeight;

  @override
  double get minExtent => minHeight;

  @override
  bool shouldRebuild(covariant MemberGroupPageAppBarDelegate  oldDelegate) {
    return true;
  }

  appear(BuildContext context, double shrinkOffset) => shrinkOffset / maxHeight;
  disappear(BuildContext context, double shrinkOffset) => 1 - shrinkOffset / maxHeight;
  Widget buildBackground(BuildContext context, double shrinkOffset, Size size, Group group) => Opacity(
    opacity: max(disappear(context, shrinkOffset * 1.75), 0),
    child: Stack(
      children: [
        Container(
          height: size.height * 0.2,
          width: size.width,
          child: group.groupImage,
        ),
        Positioned(
          top: size.height * 0.125,
          left: 20,
          child: Text(
            group.name,
            style: TextStyle(
              color: Colors.white,
              fontSize: 35,
              fontWeight: FontWeight.bold,
            ),
          ),
        ),
      ],
    ),
  );

  Widget buildMembersList(BuildContext context, double shrinkOffset, Size size, Group group) {
    return Opacity(
      opacity: max(disappear(context, shrinkOffset * 1.5), 0),
      child: Container(
        color: Colors.white,
        child: Material(
           // color: Colors.blueGrey.withOpacity(0.5),
            color: Colors.blueGrey.withOpacity(0.5),
            borderRadius: BorderRadius.only(
              bottomLeft: Radius.circular(10),
              bottomRight: Radius.circular(10),
            ),
            child: Container(
              decoration: BoxDecoration(
                color: Colors.white.withOpacity(0.775),
                borderRadius: BorderRadius.only(
                  bottomLeft: Radius.circular(10),
                  bottomRight: Radius.circular(10),
                ),
              ),
              child: SizedBox(
                height: size.height * 0.115,
                width: size.width,
                child: ListView(
                  scrollDirection: Axis.horizontal,
                  children: getGroupMembers(group, size),
                ),
              ),
            ),
         ),
      ),
    );
  }
  //TODO: get group members from database
  List<Widget> getGroupMembers(Group group, Size size){
    List<User> members = [];
    List<Widget> memberBtnList = [];

    //test group members
    group.members = [];
    for(int i=0; i<15; i++){
      User user = new User();
      user.id = i.toString();
      user.name = 'User ' + i.toString();
      if(i%3 == 0){
        user.isOnline = true;
      }
      else{
        user.isOnline = false;
      }
      if(i%2 == 0){
        user.userImage = Image.asset(
          'lib/Resources/Images/user3_example_pic.jpg',
          fit: BoxFit.cover,
        );
      }
      else{
        user.userImage = Image.asset(
          'lib/Resources/Images/user5_example_pic.jpg',
          fit: BoxFit.cover,
        );
      }

      group.members.add(user);
      user.groups.add(group);
      members.add(user);
    }

    members.forEach((user) { memberBtnList.add(
        Padding(
          padding: const EdgeInsets.fromLTRB(4, 4, 0, 0),
          child: Container(
              margin: EdgeInsets.all(1),
              decoration: BoxDecoration(
                shape: BoxShape.circle,
              ),
              child: CircleUserPic(user: user, height: size.width * 0.15, width: size.width * 0.15,)
          ),
        ));
    });

    return memberBtnList;
  }

  Color userOnlineColor(User user){
    if(user.isOnline){
      return Pallet.lightBlue;
    }
    else{
      return Colors.transparent;
    }
  }
}

class GroupPageBottomNavBar extends StatelessWidget {
  final int bodyIndex;
  const GroupPageBottomNavBar({Key key, @required this.bodyIndex}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Container(
      height: size.height * 0.075,
      width: size.width,
      decoration: BoxDecoration(
        color: Pallet.darkBlue.withOpacity(0.9),
        borderRadius: BorderRadius.only(topRight: Radius.circular(30)),
        boxShadow: [BoxShadow(color: Colors.black.withOpacity(0.15), offset: Offset(1, -1), blurRadius: 4.5, spreadRadius: 2)],
      ),
      child: Padding(
        padding: const EdgeInsets.fromLTRB(8.0,0,0,0),
        child: Row(
          children: [
            Expanded(
              child: Material(
                color: Colors.transparent,
                child: InkWell(
                  onTap: (){
                    MemberGroupPageInherited.of(context).state.setBodyIndex(0);
                  },
                  child: Column(
                    children: [
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Text(
                          'feed',
                          style: TextStyle(
                            fontSize: 20,
                            color: getTextColor(context, 0),
                          ),
                        ),
                      ),
                      Container(
                        height: 5,
                        width: 5,
                        decoration: BoxDecoration(
                          shape: BoxShape.circle,
                          color: getCircleColor(context, 0),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
            Expanded(
              child: Material(
                color: Colors.transparent,
                child: InkWell(
                  onTap: (){
                    MemberGroupPageInherited.of(context).state.setBodyIndex(1);
                  },
                  child: Column(
                    children: [
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Text(
                          'chat',
                          style: TextStyle(
                            fontSize: 20,
                            color: getTextColor(context, 1),
                          ),
                        ),
                      ),
                      Container(
                        height: 5,
                        width: 5,
                        decoration: BoxDecoration(
                          shape: BoxShape.circle,
                          color: getCircleColor(context,1),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Color getCircleColor(BuildContext context, int index){
    if(index == MemberGroupPageInherited.of(context).state.bodyIndex){
      return Colors.blueGrey;
    }
    else{
      return Colors.transparent;
    }
  }
  Color getTextColor(BuildContext context, int index){
    if(index == MemberGroupPageInherited.of(context).state.bodyIndex){
      return Colors.white;
    }
    else{
      return Colors.blueGrey;
    }
  }
}
