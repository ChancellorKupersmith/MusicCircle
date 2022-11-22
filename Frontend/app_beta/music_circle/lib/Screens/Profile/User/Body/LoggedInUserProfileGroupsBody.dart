import 'package:flutter/material.dart';
import 'package:music_circle/Entities/Group.dart';
import 'package:music_circle/Entities/User.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Groups/GroupPage.dart';
import 'package:music_circle/UI_Tools/ImageButtons.dart';

class LoggedInUserProfileGroupBody extends StatelessWidget {
  const LoggedInUserProfileGroupBody({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return ListView(
      shrinkWrap: true,
      primary: false,
      children: [
        Stack(
          children: [
            Material(
              //color: Pallet.darkBlue.withOpacity(0.85),
              color: Pallet.darkBlue,
              borderRadius: BorderRadius.only(
                topLeft: Radius.circular(40),
                topRight: Radius.circular(10),
              ),
              child: Container(
                height: size.height - size.height * 0.1,
                decoration: BoxDecoration(
                  color: Pallet.lightBlue.withOpacity(0.2),
                  borderRadius: BorderRadius.only(
                    topLeft: Radius.circular(40),
                    topRight: Radius.circular(10),
                  ),
                ),
              ),
            ),
            Positioned(
              top: 1,
              left: 8,
              child: Padding(
                padding: const EdgeInsets.fromLTRB(8.0, 4.0, 0.0, 0.0),
                child: Text('Groups',
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 30,
                  ),
                ),
              ),
            ),
            Positioned(top: size.height * 0.06, left: 0, child: LoggedInUserProfileGroupList(),)
          ],
        )
      ],
    );
  }
}

class LoggedInUserProfileGroupListInherited extends InheritedWidget{
  final _LoggedInUserProfileGroupListState state;
  LoggedInUserProfileGroupListInherited({@required this.state, @required Widget child}) : super(child: child);

  @override
  bool updateShouldNotify(covariant LoggedInUserProfileGroupListInherited oldWidget) {
    return true;
  }

  static LoggedInUserProfileGroupListInherited of(BuildContext context) =>
      context.dependOnInheritedWidgetOfExactType<LoggedInUserProfileGroupListInherited>();
}

class LoggedInUserProfileGroupList extends StatefulWidget {
  const LoggedInUserProfileGroupList({Key key}) : super(key: key);

  @override
  _LoggedInUserProfileGroupListState createState() => _LoggedInUserProfileGroupListState();
}

class _LoggedInUserProfileGroupListState extends State<LoggedInUserProfileGroupList> {
  List<Group> groups = [];
  void addGroups(Group group){
    setState(() {
      groups.add(group);
    });
  }

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    List<Widget> userGroups = getUsersGroups('userID', size);
    return LoggedInUserProfileGroupListInherited(
      state: this,
      child: SizedBox(
        width: size.width,
        height: size.height,
        child: PageView.builder(
          itemBuilder: (context, index){
            return userGroups[index];
          },
          itemCount: userGroups.length,
        ),
      ),
    );
  }

  //TODO: get user groups from database.
  List<Widget> getUsersGroups(String groupID, Size size){
    List<Widget> colList = [];
    List<Widget> grid = [];

    //test groups
    for(int i=0; i<15; i++){
      Group group = new Group();
      group.name = 'Group ' + i.toString();
      group.groupImage = Image.asset(
        'lib/Resources/Images/group1_example_pic.jpg',
        fit: BoxFit.cover,
      );
      group.description = 'Group description';
      for(int j=0; j<15; j++){
        User user = new User();
        user.id = j.toString();
        user.name = 'User ' + j.toString();
        user.userImage = Image.asset(
          'lib/Resources/Images/user3_example_pic.jpg',
          fit: BoxFit.cover,
        );
        group.members.add(user);
        group.freeToJoin = true;
        user.groups.add(group);
      }
      addGroups(group);
    }
    Widget col;
    for(int i=0; i<groups.length; i++){
      grid.add(
        SquareGroupButton(group: groups[i], size: size),
      );
      if(grid.length == 6 || i == groups.length - 1){
        col = SizedBox(
          height: size.height,
          child: new Column(
            children: [
              Expanded(
                child: GridView.count(
                  mainAxisSpacing: 16,
                  primary: false,
                  physics: NeverScrollableScrollPhysics(),
                  crossAxisCount: 2,
                  childAspectRatio: 0.95,
                  children: grid,
                ),
              ),
            ],
          ),
        );
        colList.add(col);
        grid = [];
      }
    }
    return colList;
  }
}


class SquareGroupButton extends StatelessWidget {
  final Group group;
  final Size size;
  const SquareGroupButton({Key key, @required this.size, @required this.group}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Center(
      child: InkWell(
        onTap: (){
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context) =>
                GroupPage(group),
            ),
          );
        },
        child: Stack(
          children: [
            ImageButtonSquareRounded(size.height * 0.5, size.width * 0.45, group.name, group.groupImage),
          ],
        ),
      ),
    );
  }
}
