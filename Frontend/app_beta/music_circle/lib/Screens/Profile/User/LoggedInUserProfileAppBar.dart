import 'dart:math';

import 'package:auto_size_text/auto_size_text.dart';
import 'package:flutter/material.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Profile/User/LoggedInUserProfileConfig.dart';

import 'LoggedInUserContentsMenu.dart';

class ProfileAppBar extends StatelessWidget {
  final double minHeight;
  const ProfileAppBar({Key key, @required this.minHeight}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return SliverPersistentHeader(
      delegate: LoggedInUserProfileAppBarDelegate(expandedHeight: size.height * 0.55, minHeight: minHeight),
      pinned: true,
    );
  }
}


class LoggedInUserProfileAppBarDelegate extends SliverPersistentHeaderDelegate{
  final double expandedHeight, minHeight;

  LoggedInUserProfileAppBarDelegate({@required this.expandedHeight, @required this.minHeight});

  @override
  Widget build(BuildContext context, double shrinkOffset, bool overlapsContent) {
    Size size = MediaQuery.of(context).size;
    final bottom = expandedHeight - shrinkOffset - kToolbarHeight * 0.5 - size.height * 0.025;
    final top = max(minExtent - shrinkOffset - kToolbarHeight, kToolbarHeight - shrinkOffset) - size.height * 0.025;
    int bodyIndex = LoggedInUserProfileInherited.of(context).state.bodyIndex;

    return Stack(
      clipBehavior: Clip.none,
      fit: StackFit.expand,
      children: [
        Positioned(
          top: min(top, size.height*0.1 - shrinkOffset - kToolbarHeight),
          left: 0,
          child: buildBackground(shrinkOffset),
        ),
        Positioned(
          top: min(top, size.height*0.08 - shrinkOffset - kToolbarHeight) + size.height * 0.45,
          left: min(top, size.height*0.1 - shrinkOffset - kToolbarHeight - 10),
          child: buildArtistLabel(shrinkOffset),
        ),
        Positioned(
          top: kToolbarHeight * 0.15 + 10,
          left: 2.5,
          child: BackButton(
            color: Pallet.darkBlue,
          ),
        ),
        Positioned(
            top: min(top, size.height*0.1 - shrinkOffset - kToolbarHeight) + size.height * 0.07,
            right: 0,
            child: Padding(
              padding: const EdgeInsets.fromLTRB(4,0, 0,0),
              child: ProfileTableOfContents(),
            ),
        ),
      ],
    );
  }

  @override
  double get maxExtent => expandedHeight;

  @override
  double get minExtent => minHeight;

  @override
  bool shouldRebuild(covariant SliverPersistentHeaderDelegate oldDelegate) {
    return true;
  }

  Widget buildAppBar(double shrinkOffset) => Opacity(
    opacity: disappear(shrinkOffset),
    child: AppBar(
      backgroundColor: Colors.white.withOpacity(0.6),
      leading: Container(
        decoration: BoxDecoration(
            color: Colors.blueGrey
        ),
        child: Icon(
          Icons.arrow_back_ios_rounded,
          color: Pallet.darkBlue,
        ),
      ),
      actions: [
        Row(
          children: [
            Opacity(
              opacity: disappear(shrinkOffset),
              child: Text(
                'Test User',
                style: TextStyle(
                    fontSize: 25,
                    fontWeight: FontWeight.bold,
                    color: Colors.white
                ),
              ),
            ),
          ],
        )
      ],

    ),
  );

  appear(double shrinkOffset) => shrinkOffset / expandedHeight;
  disappear(double shrinkOffset) => 1 - shrinkOffset / expandedHeight;
  displayFollowButton(double shrinkOffset){
    if(shrinkOffset >= 475){
      return 0.0;
    }
    else{
      return disappear(shrinkOffset) / 8 + 0.875;
    }
  }
  double getFollowButtonTopPosition(int bodyIndex, Size size, double top, double shrinkOffset){
    double pos;
    switch(bodyIndex){
      case 0:{
        pos = max(size.height * 0.09, min(top, size.height*0.1 - shrinkOffset - kToolbarHeight) + size.height * 0.055);
      }
      break;
      case 1:{
        pos = min(size.height * 0.09, max(size.height * 0.073/ shrinkOffset, min(top, size.height*0.1 - shrinkOffset - kToolbarHeight) + size.height * 0.06));
      }
      break;
      default:{
        pos = size.height * 0.09 - shrinkOffset;
      }
      break;
    }
    return pos;
  }

  buildFollowButton(double shrinkOffset, BuildContext context) => Opacity(
    opacity: displayFollowButton(shrinkOffset),
    child: Container(
      height: 50,
      width: 140,
      decoration: BoxDecoration(
        color: Colors.white.withOpacity(1),
        borderRadius: BorderRadius.circular(20),
      ),
      child: Center(
        child: Text(
          'Follow',
          textAlign: TextAlign.center,
          style: TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.w600,
              color: Pallet.lightBlue
          ),
        ),
      ),
    ),
  );

  buildBackground(double shrinkOffset) => Opacity(
    opacity: disappear(shrinkOffset),
    child: Padding(
      padding: const EdgeInsets.fromLTRB(0, 30.0, 0, 0),
      child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.fromLTRB(8, 18, 0, 6),
            child: Row(
              children: [
                Opacity(
                  opacity: disappear(shrinkOffset),
                  child: Text(
                    '.alivevibe',
                    style: TextStyle(
                        fontSize: 40,
                        fontWeight: FontWeight.bold,
                        color: Pallet.darkBlue,
                    ),
                  ),
                ),
              ],
            ),
          ),
          Row(
            children: [
              Padding(
                padding: const EdgeInsets.fromLTRB(0, 8.0, 0, 0),
                child: SizedBox(
                  height: 300,
                  width: 200,
                  child: Container(
                    decoration: BoxDecoration(
                        borderRadius: BorderRadius.only(
                          bottomLeft: Radius.circular(40),
                          topRight: Radius.circular(40),
                          bottomRight: Radius.circular(40),
                        ),
                        boxShadow: [
                          BoxShadow(
                              color: Pallet.darkBlue.withOpacity(0.09),
                              offset: Offset(3,2),
                              blurRadius: 1,
                              spreadRadius: 2
                          ),
                          BoxShadow(
                              color: Colors.white.withOpacity(0.3),
                              offset: Offset(-3,-2),
                              blurRadius: 1,
                              spreadRadius: 2
                          ),
                          BoxShadow(
                              color: Pallet.darkBlue.withOpacity(0.09),
                              offset: Offset(-140,2),
                              blurRadius: 1,
                              spreadRadius: 2
                          ),
                        ]
                    ),
                    child: Material(
                      clipBehavior: Clip.hardEdge,
                      borderRadius: BorderRadius.only(
                        topRight: Radius.circular(40),
                        bottomRight: Radius.circular(40),
                      ),
                      child: Image.asset(
                        'lib/Resources/Images/album3_example_pic.jpg',
                        //height: expandedHeight,
                        fit: BoxFit.cover,
                      ),
                    ),
                  ),
                ),
              ),
              // Padding(
              //   padding: const EdgeInsets.fromLTRB(4, 0, 0, 30),
              //   child: ProfileTableOfContents(),
              // ),
            ],
          ),
        ],
      ),
    ),
  );

  buildArtistLabel(double shrinkOffset) => Opacity(
    opacity: disappear(shrinkOffset),
    child: Container(
      decoration: BoxDecoration(
        color: Pallet.lightBlue.withOpacity(0.2),
        borderRadius: BorderRadius.circular(20),
      ),
      child: Padding(
        padding: const EdgeInsets.fromLTRB(8,2,8,4),
        child: AutoSizeText(
          'Ames, IA',
          style: TextStyle(
              fontSize: 25,
              fontWeight: FontWeight.bold,
              color: Colors.white,
          ),
        ),
      ),
    ),
  );
}