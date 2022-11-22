import 'dart:math';

import 'package:auto_size_text/auto_size_text.dart';
import 'package:flutter/material.dart';
import 'package:music_circle/Entities/Entity.dart';
import 'package:music_circle/Resources/Colors.dart';

import '../ImageButtons.dart';

//For now will just show songs but in future needs to shows playlists and albums
class RecentlyListened extends StatelessWidget {
  final List<ImageButtonCircle> listenedMusicList;
  const RecentlyListened({Key key, this.listenedMusicList}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Align(
      alignment: Alignment.topLeft,
      child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.fromLTRB(8.0, 0.0, 0.0, 8.0),
            child: Align(
              alignment: Alignment.topLeft,
              child: Text(
                'Recently Listened: ',
                textAlign: TextAlign.left,
                style: TextStyle(
                    fontSize: 22,
                    fontWeight: FontWeight.w600,
                    color: Pallet.lightBlue
                ),
              ),
            ),
          ),
          SizedBox(
            height: size.height * 0.4,
            width: size.width,
            child: Container(
              decoration: BoxDecoration(
                borderRadius: BorderRadius.all(Radius.circular(36.0))
              ),
              child: ListView(
                scrollDirection: Axis.horizontal,
                children: listenedMusicList,
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class SongsListHorizontal extends StatelessWidget {
  final List<Widget> songList;
  final String header;
  final Color headerColor;
  const SongsListHorizontal({Key key, @required this.songList, @required this.header, @required this.headerColor}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Align(
      alignment: Alignment.topLeft,
      child: Stack(
        children: [
          Padding(
            padding: const EdgeInsets.fromLTRB(10.0, 4.0, 0.0, 8.0),
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
            top: size.height * 0.04,
            child: SizedBox(
              height: size.width * 0.4,
              width: size.width,
              child: Container(
                decoration: BoxDecoration(
                    borderRadius: BorderRadius.all(Radius.circular(36.0))
                ),
                child: ListView(
                  scrollDirection: Axis.horizontal,
                  children: songList,
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class GlobalTopSongsListHorizontal extends StatefulWidget {
  final List<Widget> songListYear;
  final List<Widget> songListMonth;
  final String header;
  final Color headerColor;
  const GlobalTopSongsListHorizontal({Key key, @required this.songListYear, @required this.songListMonth, @required this.header, @required this.headerColor}) : super(key: key);

  @override
  _GlobalTopSongsListHorizontalState createState() => _GlobalTopSongsListHorizontalState();
}

class _GlobalTopSongsListHorizontalState extends State<GlobalTopSongsListHorizontal> {
  bool songsByYear;
  updateSongsByYear(bool songsByYear){
    if(this.songsByYear != songsByYear){
      setState(() {
        this.songsByYear = songsByYear;
      });
    }
  }
  @override
  Widget build(BuildContext context) {
    if(songsByYear == null){
      songsByYear = true;
    }
    Size size = MediaQuery.of(context).size;
    return Align(
      alignment: Alignment.topLeft,
      child: Stack(
        children: [
          //header
          Padding(
            padding: const EdgeInsets.fromLTRB(10.0, 38.0, 0.0, 8.0),
            child: Align(
              alignment: Alignment.topLeft,
              child: Text(
                widget.header,
                textAlign: TextAlign.left,
                style: TextStyle(
                    fontSize: 22,
                    fontWeight: FontWeight.w600,
                    color: widget.headerColor
                ),
              ),
            ),
          ),
          //grey bottom background
          Positioned(
            top: size.height * 0.3,
            child: SizedBox(
              height: size.width * 0.7,
              width: size.width + size.width * 0.15,
              child: Container(
                decoration: BoxDecoration(
                  color: Colors.white,
                    borderRadius: BorderRadius.only(
                      topLeft: Radius.elliptical(100, -20),
                    )
                ),
                child: Container(
                  decoration: BoxDecoration(
                      color: Pallet.lightBlue.withOpacity(0.3),
                  ),
                ),
              ),
            ),
          ),
          //Curved left side background
          Positioned(
            bottom: min(size.height * 0.13, double.maxFinite),
            child: SizedBox(
              height: size.height * 0.075,
              width: size.width,
              child: Container(
                decoration: BoxDecoration(
                  color: Pallet.darkBlue,
                  borderRadius: BorderRadius.only(
                    bottomLeft: Radius.circular(20),
                  )
                ),
              ),
            )
          ),
          //middle background
          Positioned(
            top: size.height * 0.095,
            child: SizedBox(
              height: size.height * 0.25,
              width: size.width,
              child: Container(
                decoration: BoxDecoration(
                    color: Pallet.darkBlue,
                    borderRadius: BorderRadius.only(
                      bottomLeft: Radius.circular(20),
                    )
                ),
              ),
            ),
          ),
          //Song List
          Positioned(
            top: size.height * 0.09,
            left: size.width * 0.15,
            child: SizedBox(
              height: size.width * 0.7,
              width: size.width,
              child: Container(
                decoration: BoxDecoration(
                    borderRadius: BorderRadius.all(Radius.circular(36.0))
                ),
                child: ListView(
                  // clipBehavior: Clip.none,
                  scrollDirection: Axis.horizontal,
                  children: songsByYear? widget.songListYear:widget.songListMonth,
                ),
              ),
            ),
          ),
          //month/year tab bar
          Positioned(
            top: size.height * 0.095,
            child: RotatedBox(
              quarterTurns: 3,
              child: SizedBox(
                height: size.width * 0.15,
                width: size.height * 0.25,
                child: Container(
                  decoration: BoxDecoration(
                    color: Pallet.darkBlue,
                    borderRadius: BorderRadius.only(
                      topLeft: Radius.circular(20),
                      bottomLeft: Radius.circular(20),
                    ),
                  ),
                  child: Row(
                    children: [
                      Expanded(
                        child: InkWell(
                          onTap: (){
                            updateSongsByYear(false);
                          },
                          child: Column(
                            children: [
                              Padding(
                                padding: const EdgeInsets.fromLTRB(8,20,8,0),
                                child: AutoSizeText(
                                  'Month',
                                  style: TextStyle(
                                      color: getTabTextColor(1),
                                  ),
                                ),
                              ),
                              Padding(
                                padding: const EdgeInsets.all(4),
                                child: Icon(
                                  Icons.circle,
                                  color: getTabTextColor(1) == Colors.white? Colors.white:Colors.transparent,
                                  size: 5,
                                ),
                              )
                            ],
                          ),
                        ),
                      ),
                      Expanded(
                        child: InkWell(
                          onTap: (){
                            updateSongsByYear(true);
                          },
                          child: Column(
                            children: [
                              Padding(
                                padding: const EdgeInsets.fromLTRB(8,20,8,0),
                                child: AutoSizeText(
                                  'Year',
                                  style: TextStyle(
                                    color: getTabTextColor(0),
                                  ),
                                ),
                              ),
                              Padding(
                                padding: const EdgeInsets.all(4.0),
                                child: Icon(
                                  Icons.circle,
                                  color: getTabTextColor(0) == Colors.white? Colors.white:Colors.transparent,
                                  size: 5,
                                ),
                              )
                            ],
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  Color getTabTextColor(int index){
    if(index == 0){
      if(songsByYear){
        return Colors.white;
      }
      else{
        return Colors.blueGrey;
      }
    }
    else{
      if(!songsByYear){
        return Colors.white;
      }
      else{
        return Colors.blueGrey;
      }
    }
  }
}

class GroupListHorizontal extends StatelessWidget {
  final List<Widget> groupList;
  final String header;
  final Color headerColor;
  const GroupListHorizontal({Key key, @required this.groupList, @required this.header, @required this.headerColor}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Align(
      alignment: Alignment.topLeft,
      child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.fromLTRB(10.0, 4.0, 0.0, 8.0),
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
          SizedBox(
            height: size.width * 0.5,
            width: size.width,
            child: Container(
              decoration: BoxDecoration(
                  borderRadius: BorderRadius.all(Radius.circular(36.0))
              ),
              child: ListView(
                scrollDirection: Axis.horizontal,
                children: groupList,
              ),
            ),
          ),
        ],
      ),
    );
  }
}


//30 songs recommended by user's listening and like history
class UserTop30 extends StatelessWidget {
  final List<ImageButtonSquareRounded> top30;
  const UserTop30({Key key, this.top30}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Align(
      alignment: Alignment.topLeft,
      child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.fromLTRB(8.0, 0.0, 0.0, 8.0),
            child: Align(
              alignment: Alignment.topLeft,
              child: Text(
                'Recommended For You: ',
                textAlign: TextAlign.left,
                style: TextStyle(
                    fontSize: 22,
                    fontWeight: FontWeight.w600,
                    color: Pallet.lightBlue
                ),
              ),
            ),
          ),
          SizedBox(
            height: size.height * 0.4,
            width: size.width,
            child: Container(
              decoration: BoxDecoration(
                  borderRadius: BorderRadius.all(Radius.circular(36.0))
              ),
              child: ListView(
                scrollDirection: Axis.horizontal,
                children: top30,
              ),
            ),
          ),
        ],
      ),
    );
  }
}

//Vertical Song list
class VerticalSongList extends StatelessWidget {
  final List<ImageButtonSquareRounded> songsList;
  const VerticalSongList({Key key, @required this.songsList}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return SizedBox(
      height: size.height * 0.85,
      width: size.width * 2/3,
      child: ListView(
        shrinkWrap: true,
        children: addDataToImage(size),
      ),
    );
  }

  List<Widget> addDataToImage(Size size){
    List<Widget> widgetList = [];
    songsList.forEach((button) {
      widgetList.add(
        Padding(
          padding: const EdgeInsets.fromLTRB(0,8.0,0,0),
          child: SizedBox(
            width: size.width * 2/3,
            child: Column(
              children: [
                Container(
                  height: button.height,
                  width: button.width,
                  decoration: BoxDecoration(
                    color: Colors.black,
                    borderRadius: BorderRadius.all(Radius.circular(20)),
                    boxShadow: [
                      BoxShadow(
                        color: Colors.black.withOpacity(0.2),
                        offset: Offset(4, 6),
                        blurRadius: 5.5,
                        spreadRadius: 1,
                      )
                    ],
                  ),
                  child: Stack(
                      children: [
                        button,
                        Positioned(
                          bottom: 5,
                          left: 10,
                          child: Material(
                            color: Colors.blueGrey.withOpacity(0.7),
                            borderRadius: BorderRadius.circular(15),
                            child: Padding(
                              padding: const EdgeInsets.fromLTRB(8,2,8,2),
                              child: Text(
                                button.label,
                                style: TextStyle(
                                  fontSize: 18,
                                  fontWeight: FontWeight.w400,
                                  color: Colors.white,
                                ),
                              ),
                            ),
                          ),
                        ),
                      ]
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.fromLTRB(30.0,0,0,0),
                  child: Row(
                    children: [
                      Row(
                        children: [
                          Text(
                            '2.4k'
                          ),
                          Text(
                            ' Plays',
                          )
                        ],
                      ),

                      IconButton(icon: Icon(Icons.favorite, color: Pallet.darkBlue,), iconSize: 13, onPressed: (){}),
                      IconButton(icon: Icon(Icons.share, color: Pallet.darkBlue,), iconSize: 13, onPressed: (){}),
                      IconButton(icon: Icon(Icons.add, color: Pallet.darkBlue,), iconSize: 13, onPressed: (){}),
                    ],
                  ),
                ),
              ],
            ),
          ),
        )
      );
    });
    return widgetList;
  }
}

