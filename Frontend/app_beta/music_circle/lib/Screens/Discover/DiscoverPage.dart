import 'dart:math';

import 'package:curved_navigation_bar/curved_navigation_bar.dart';
import 'package:flutter/material.dart';
import 'dart:math' as math;

import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Discover/Global/GlobalDiscover.dart';
import 'package:music_circle/Screens/Discover/Search/SearchPage.dart';
import 'package:music_circle/Screens/Profile/User/LoggedInUserProfilePage.dart';
import 'Local/LocalDiscover.dart';

class DiscoverPage extends StatefulWidget {
  DiscoverPage({Key key}) : super(key: key);

  static final String routeName = '/discover-page';
  static MaterialPageRoute get route => MaterialPageRoute(
    builder: (context) => DiscoverPage(),
  );

  @override
  DiscoverPageState createState() => DiscoverPageState();
}

class DiscoverPageState extends State<DiscoverPage> {
  int pageIndex;
  updatePageIndex(int index){
    if(pageIndex != index && pageIndex != 4){
      setState(() {
        pageIndex = index;
      });
    }
  }
  @override
  Widget build(BuildContext context) {
    if(pageIndex == null){
      pageIndex = 2;
    }
    Size size = MediaQuery.of(context).size;
    return DiscoverPageInherited(
      state: this,
      child: Scaffold(
        backgroundColor: Colors.white,
        bottomNavigationBar: getBottomNavBar(size),
        body: Container(
          child: Container(
            color: Colors.white.withOpacity(0.775),
            child: DiscoverPageBody(pageIndex: pageIndex),
          ),
        ),
      ),
    );
  }

  getBottomNavBar(Size size){
    return SizedBox(
      height: size.height * 0.1,
      child: PageView(
        children: [
          CurvedNavigationBar(
            index: 2,
            height: 70,
            color: Colors.white,
            backgroundColor: Pallet.darkBlue,
            buttonBackgroundColor: Colors.white,
            onTap: (index){
              if(index != 4){
                updatePageIndex(index);
              }
            },
            letIndexChange: (index){
              if(index != 4){
                return true;
              }
              else{
                return false;
              }
            },
            items: [
              Icon(
                Icons.add,
                color: Pallet.darkBlue,
              ),
              Icon(
                Icons.location_city_rounded,
                color: Pallet.darkBlue,
              ),
              Icon(
                Icons.blur_circular_rounded,
                color: Pallet.darkBlue,
              ),
              Icon(
                Icons.search,
                color: Pallet.darkBlue,
              ),
              IconButton(
                icon: Icon(Icons.account_circle_outlined),
                color: Pallet.darkBlue,
                onPressed: () => {
                  Navigator.of(context).pushNamed(LoggedInUserProfilePage.routeName),
                },
              ),
            ],
          ),
          Container(
            height: size.height * 0.1,
            width: size.width,
            color: Pallet.darkBlue,
            child: Padding(
              padding: const EdgeInsets.fromLTRB(0,10.5,2,0),
              child: Container(
                decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius: BorderRadius.only(
                    topRight: Radius.circular(30),
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class DiscoverPageInherited extends InheritedWidget{
  final DiscoverPageState state;
  const DiscoverPageInherited({@required this.state, @required Widget child}) : super(child: child);

  @override
  bool updateShouldNotify(covariant DiscoverPageInherited oldWidget) {
    return true;
  }

  static DiscoverPageInherited of(BuildContext context) =>
      context.dependOnInheritedWidgetOfExactType<DiscoverPageInherited>();
}

class DiscoverPageBody extends StatelessWidget {
  final int pageIndex;
  const DiscoverPageBody({@required this.pageIndex, Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return getBody();
  }

  getBody(){
    Widget body;
    switch(pageIndex){
      case 1:{
        body = LocalRecommendations();
      }
      break;
      case 2:{
        body = GlobalRecommendations();
      }
      break;
      case 3:{
        body = MusicCircleSearch();
      }
      break;
      default:{
        body = Container();
      }
    }
    return body;
  }
}

class DiscoverPageContainer extends StatelessWidget {
  final double height, width;
  final Widget child;
  final Color backgroundColor;
  const DiscoverPageContainer({Key key, @required this.height, @required this.width, @required this.backgroundColor, @required this.child}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SizedBox(
        height: height,
        width: width,
        child: Container(
          decoration: BoxDecoration(
            color: backgroundColor,
            borderRadius: BorderRadius.only(
              topRight: Radius.circular(20),
              topLeft: Radius.circular(20),
            ),
          ),
          child: child,
        )
    );
  }
}


