import 'package:flutter/material.dart';
import 'package:music_circle/Resources/Colors.dart';

import '../ImageButtons.dart';


class UserListHorizontal extends StatelessWidget {
  final List<Widget> userList;
  final String header;
  final Color headerColor;
  const UserListHorizontal({Key key, @required this.userList, @required this.header, @required this.headerColor}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Align(
      alignment: Alignment.topLeft,
      child: Stack(
        children: [
          Padding(
            padding: const EdgeInsets.fromLTRB(10.0, 4.0, 0.0, 0),
            child: Align(
              alignment: Alignment.topLeft,
              child: Text(
                header,
                textAlign: TextAlign.left,
                style: TextStyle(
                    fontSize: 22,
                    fontWeight: FontWeight.w600,
                    color: headerColor
                ),
              ),
            ),
          ),
          Positioned(
            top: 35,
            child: SizedBox(
              height: size.width * 0.4,
              width: size.width,
              child: ListView(
                shrinkWrap: true,
                scrollDirection: Axis.horizontal,
                children: userList,
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class FollowersListHorizontal extends StatelessWidget {
  final List<FollowerImageButton> followersList;
  const FollowersListHorizontal({Key key, this.followersList}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Align(
      alignment: Alignment.topLeft,
      child: Column(
        children: [
          // Padding(
          //   padding: const EdgeInsets.fromLTRB(8.0, 0.0, 0.0, 8.0),
          //   child: Align(
          //     alignment: Alignment.topLeft,
          //     child: Text(
          //       'Followers',
          //       textAlign: TextAlign.left,
          //       style: TextStyle(
          //           fontSize: 22,
          //           fontWeight: FontWeight.w600,
          //           color: Pallet.lightBlue
          //       ),
          //     ),
          //   ),
          // ),
          SizedBox(
            height: size.height * 0.1,
            width: size.width,
            child: Container(
                    decoration: BoxDecoration(
                        borderRadius: BorderRadius.all(Radius.circular(36.0))
                    ),
                    child: ListView(
                      scrollDirection: Axis.horizontal,
                      children: followersList,
                    ),
                  ),
            ),
        ],
      ),
    );
  }
}

