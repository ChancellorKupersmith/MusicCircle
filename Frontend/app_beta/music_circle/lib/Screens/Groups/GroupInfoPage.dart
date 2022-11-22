import 'package:flutter/material.dart';
import 'package:music_circle/Entities/Group.dart';
import 'package:music_circle/Entities/User.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Groups/GroupPage.dart';
import 'package:music_circle/UI_Tools/BackButton.dart';
import 'package:music_circle/UI_Tools/ImageButtons.dart';

class GroupInfoPage extends StatefulWidget {
  const GroupInfoPage({Key key}) : super(key: key);

  @override
  _GroupInfoPageState createState() => _GroupInfoPageState();
}

class _GroupInfoPageState extends State<GroupInfoPage> {
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Scaffold(
      body: SizedBox(
        height: size.height,
        width: size.width,
        child: Stack(
          children: [
            //Group Background Image
            createBackground(size),
            //Group Info container
            createGroupInfoContainer(size),
            //Join button
            createJoinButton(size),
            // BackButton(color: Pallet.lightBlue,),
            Positioned(
              left: 10,
              child: CircleBackButton(backgroundColor: Pallet.lightBlue, iconColor: Colors.white)
            ),
          ],
        ),
      ),
    );
  }

  //Create Background
  Widget createBackground(Size size){
    return Stack(
        children: [
          Container(
            height: size.height * 0.7,
            width: size.width,
            child: GroupPageInherited.of(context).state.group.groupImage),
          Positioned(
            top: size.height * 0.5,
            left: 10,
            child: SizedBox(
              width: 148,
              height: size.height * 0.1,
              child: Stack(
                children: [
                  Text(
                    GroupPageInherited.of(context).state.group.name,
                    style: TextStyle(
                      color: Colors.white,
                      fontSize: 40,
                      fontWeight: FontWeight.bold,
                    ),
                    textAlign: TextAlign.left,
                  ),
                  Positioned(
                    bottom: 4,
                    right: 6,
                    child: Text(
                      groupIsFreeToJoin(GroupPageInherited.of(context).state.group),
                      style: TextStyle(
                        color: Colors.white,
                        fontSize: 17,
                        fontWeight: FontWeight.bold,
                      ),
                      textAlign: TextAlign.right,
                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      );
  }
  String groupIsFreeToJoin(Group group){
    String subtitle = '';
    if(group.freeToJoin){
      subtitle = 'Unrestricted';
    }
    else{
      subtitle = 'restricted';
    }
    return subtitle;
  }
  //Create join button
  Widget createJoinButton(Size size){
    return Positioned(
      top: size.height * 0.6,
      right: 2,
      child: Container(
        height: 50,
        width: 140,
        decoration: BoxDecoration(
          color: Pallet.lightBlue,
          borderRadius: BorderRadius.circular(20),
        ),
        child: Center(
          child: Text(
            'Join',
            textAlign: TextAlign.center,
            style: TextStyle(
              fontSize: 20,
              fontWeight: FontWeight.w600,
              color: Colors.white.withOpacity(1),
            ),
          ),
        ),
      ),
    );
  }

  //Create group info container
  Widget createGroupInfoContainer(Size size){
    return Positioned(
      top: size.height * 0.625,
      child: Container(
        decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.only(
              topLeft: Radius.circular(20),
              topRight: Radius.circular(20),
            )
        ),
        height: size.height * 0.375,
        width: size.width,
        child: Stack(
          children: [
            //Group description box
            Positioned(
              top: size.height * 0.045,
              left: size.width * 0.05,
              child: Text(
                GroupPageInherited.of(context).state.group.description,
                style: TextStyle(
                  fontSize: 15,
                  color: Colors.grey.shade600,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
            //Line divider
            Positioned(
              top: size.height * 0.25,
              left: size.width * 0.05,
              child: Container(
                width: size.width * 0.9,
                height: 1,
                color: Colors.grey.withOpacity(0.5),
              ),
            ),
            //Group members box
            Positioned(
              top: size.height * 0.255,
              child: SizedBox(
                width: size.width,
                child: Row(
                  children: [
                    Material(
                      child: InkWell(
                        onTap: (){

                        },
                        child: SizedBox(
                          width: size.width * 0.33,
                          height: size.height * 0.1,
                          child: Padding(
                            padding: const EdgeInsets.fromLTRB(20, 0, 0, 0),
                            child: Row(
                              children: [
                                Icon(Icons.group_rounded, size: 40, color: Colors.grey.shade600,),
                                Padding(
                                  padding: const EdgeInsets.fromLTRB(10, 20, 0, 20),
                                  child: Column(
                                    children: [
                                      Text(
                                          GroupPageInherited.of(context).state.group.members.length.toString()
                                      ),
                                      Text(
                                        'members',
                                        textAlign: TextAlign.center,
                                      ),
                                    ],
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                      ),
                    ),
                    Expanded(
                      child: SizedBox(height: size.height * 0.1,
                        child: ListView(
                          shrinkWrap: true,
                          scrollDirection: Axis.horizontal,
                          children: getGroupMembers(GroupPageInherited.of(context).state.group, size),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            )
          ],
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
    for(int j=0; j<10; j++){
      User user = new User();
      user.id = j.toString();
      user.name = 'User ' + j.toString();
      user.userImage = Image.asset(
        'lib/Resources/Images/user3_example_pic.jpg',
        fit: BoxFit.cover,
      );
      group.members.add(user);
      user.groups.add(group);
      members.add(user);
    }

    members.forEach((user) { memberBtnList.add(
        Padding(
         padding: const EdgeInsets.fromLTRB(8, 8, 0, 0),
          child: ImageButtonCircle(size.height * 0.2, size.width * 0.2, user.name, user.userImage),
    ));});
    return memberBtnList;
  }
}
