import 'dart:math';

import 'package:auto_size_text/auto_size_text.dart';
import 'package:flutter/material.dart';
import 'package:music_circle/Entities/Genre.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Discover/Search/SearchPage2.dart';

class MusicCircleSearch extends StatefulWidget {

  const MusicCircleSearch({Key key}) : super(key: key);

  @override
  _MusicCircleSearchState createState() => _MusicCircleSearchState();
}

class _MusicCircleSearchState extends State<MusicCircleSearch> {
  List<Genre> genres;
  int searchPage;
  void updateSearchPage(int index){
    if(searchPage != index){
      setState(() {
        searchPage = index;
      });
    }
  }
  //Search page 2 variables
  int searchIndex = 0;
  bool curator = false, today = false, thisWeek = false;
  String searchText, genre;
  @override
  Widget build(BuildContext context) {
    if(searchPage == null){
      searchPage = 0;
    }
    genres = getTopGenres();
    return MusicCircleSearchInherited(
      state: this,
      child: getSearchPage(),
    );
  }

  List<Genre> getTopGenres(){
    List<Genre> genres = [];

    Genre hiphop = new Genre();
    hiphop.name = 'Hip-Hop';
    genres.add(hiphop);

    Genre experimental = new Genre();
    experimental.name = 'Experimental';
    genres.add(experimental);

    Genre rock = new Genre();
    rock.name = 'Rock';
    genres.add(rock);

    Genre nineties = new Genre();
    nineties.name = "90's";
    genres.add(nineties);

    Genre lofi = new Genre();
    lofi.name = 'lofi';
    genres.add(lofi);

    Genre cover = new Genre();
    cover.name = 'Cover';
    genres.add(cover);

    Genre underground = new Genre();
    underground.name = 'Underground';
    genres.add(underground);

    Genre instrumental = new Genre();
    instrumental.name = 'Instrumental';
    genres.add(instrumental);

    Genre all = new Genre();
    all.name = 'All';
    genres.add(all);

    return genres;


  }

  getSearchPage(){
    if(searchPage == 0){
      return CustomScrollView(
        slivers: [
          SearchPage1(),
        ],
      );
    }
    else{
      return SearchPage2(searchIndex: searchIndex, curator: curator, today: today, thisWeek: thisWeek, searchText: searchText, genre: genre);
    }
  }
}

class MusicCircleSearchInherited extends InheritedWidget{
  final _MusicCircleSearchState state;

  MusicCircleSearchInherited({@required this.state, Widget child}) : super(child: child);

  @override
  bool updateShouldNotify(covariant MusicCircleSearchInherited oldWidget) {
    return true;
  }

  static MusicCircleSearchInherited of(BuildContext context) =>
      context.dependOnInheritedWidgetOfExactType<MusicCircleSearchInherited>();
}

class SearchPage1 extends StatelessWidget {
  const SearchPage1({Key key,}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return SliverPersistentHeader(delegate: SearchPageDelegate(maxHeight: size.height * 1.645));
  }
}

class SearchPageDelegate extends SliverPersistentHeaderDelegate {
  final maxHeight;

  SearchPageDelegate({@required this.maxHeight});

  @override
  Widget build(BuildContext context, double shrinkOffset, bool overlapsContent) {
    Size size = MediaQuery.of(context).size;
    return Container(
      height: maxHeight,
      width: size.width,
      color: Pallet.softBlue.withOpacity(0.3),
      child: Stack(
        children: [
          //Contents
            //Songs
            Positioned(
              top: size.height * 0.055 + 50 + size.height * 0.075 - shrinkOffset,
              left: size.width * 0.0125,
              child: InkWell(
                onTap: (){
                  MusicCircleSearchInherited.of(context).state.searchIndex = 2;
                  MusicCircleSearchInherited.of(context).state.updateSearchPage(1);
                },
                child: Container(
                  height: size.height * 0.1,
                  width: size.width * 0.975,
                  decoration: BoxDecoration(
                    color: Pallet.darkBlue.withOpacity(0.8),
                    borderRadius: BorderRadius.circular(40),
                  ),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Padding(
                        padding: const EdgeInsets.fromLTRB(20, 0, 0, 0),
                        child: AutoSizeText(
                          'Songs',
                          style: TextStyle(
                            color: Colors.white,
                          ),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.fromLTRB(0, 0, 20, 0),
                        child: Icon(
                          Icons.album,
                          color: Colors.white,
                        ),
                      )
                    ],
                  ),
                ),
              ),
            ),
            //Albums
            Positioned(
              top: size.height * 0.055 + 50 + size.height * 0.075 + size.height * 0.1 + 10 - shrinkOffset,
              left: size.width * 0.0125,
              child: InkWell(
                onTap: (){
                  MusicCircleSearchInherited.of(context).state.searchIndex = 4;
                  MusicCircleSearchInherited.of(context).state.updateSearchPage(1);
                },
                child: Container(
                  height: size.height * 0.1,
                  width: size.width * 0.975,
                  decoration: BoxDecoration(
                    color: Colors.blueGrey,
                    borderRadius: BorderRadius.circular(40),
                  ),
                  child:  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Padding(
                        padding: const EdgeInsets.fromLTRB(20, 0, 0, 0),
                        child: AutoSizeText(
                          'Albums',
                          style: TextStyle(
                            color: Colors.white,
                          ),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.fromLTRB(0, 0, 20, 0),
                        child: Icon(
                          Icons.music_video,
                          color: Colors.white,
                        ),
                      )
                    ],
                  ),
                ),
              ),
            ),
            //Playlists
            Positioned(
              top: size.height * 0.055 + 50 + size.height * 0.075 + size.height * 0.1 + 10 + size.height * 0.1 + 10 - shrinkOffset,
              left: size.width * 0.0125,
              child: InkWell(
                onTap: (){
                  MusicCircleSearchInherited.of(context).state.searchIndex = 3;
                  MusicCircleSearchInherited.of(context).state.updateSearchPage(1);
                },
                child: Container(
                  height: size.height * 0.1,
                  width: size.width * 0.975,
                  decoration: BoxDecoration(
                    color: Pallet.lightBlue.withOpacity(0.5),
                    borderRadius: BorderRadius.circular(40),
                  ),
                  child:  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Padding(
                        padding: const EdgeInsets.fromLTRB(20, 0, 0, 0),
                        child: AutoSizeText(
                          'Playlists',
                          style: TextStyle(
                            color: Colors.white,
                          ),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.fromLTRB(0, 0, 20, 0),
                        child: Icon(
                          Icons.playlist_play,
                          color: Colors.white,
                        ),
                      )
                    ],
                  ),
                ),
              ),
            ),
            //Events
            Positioned(
              top: size.height * 0.055 + 50 + size.height * 0.075 + size.height * 0.1 + 10 + size.height * 0.1 + 10 + size.height * 0.1 + 10 - shrinkOffset,
              left: size.width * 0.0125,
              child: InkWell(
                onTap: (){
                  MusicCircleSearchInherited.of(context).state.searchIndex = 5;
                  MusicCircleSearchInherited.of(context).state.updateSearchPage(1);
                },
                child: Container(
                  height: size.height * 0.1,
                  width: size.width * 0.975,
                  decoration: BoxDecoration(
                    color: Pallet.darkOrange.withOpacity(0.5),
                    borderRadius: BorderRadius.circular(40),
                  ),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Padding(
                        padding: const EdgeInsets.fromLTRB(20, 0, 0, 0),
                        child: AutoSizeText(
                          'Events',
                          style: TextStyle(
                            color: Colors.white,
                          ),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.fromLTRB(0, 0, 20, 0),
                        child: Icon(
                          Icons.event,
                          color: Colors.white,
                        ),
                      )
                    ],
                  ),
                ),
              ),
            ),
            //Today
            Positioned(
              top: size.height * 0.055 + 50 + size.height * 0.075 + size.height * 0.1 + 10 + size.height * 0.1 + 10 + size.height * 0.1 + 10 + size.height * 0.1 + 20 - shrinkOffset,
              left: size.width * 0.0125,
              child: InkWell(
                onTap: (){
                  MusicCircleSearchInherited.of(context).state.searchIndex = 0;
                  MusicCircleSearchInherited.of(context).state.today = true;
                  MusicCircleSearchInherited.of(context).state.updateSearchPage(1);
                },
                child: SizedBox(
                  height: size.height * 0.2,
                  width: size.width * 0.975 / 2.1,
                  child: Stack(
                    children: [
                      //Background
                      Container(
                        height: size.height * 0.2,
                        width: size.width * 0.975 / 2.1,
                        decoration: BoxDecoration(
                          color: Pallet.blueGrey,
                          borderRadius: BorderRadius.circular(40),
                        ),
                        child: Padding(
                          padding: const EdgeInsets.fromLTRB(20, 30, 0, 0),
                          child: AutoSizeText(
                            'Today',
                            style: TextStyle(
                              color: Colors.white,
                            ),
                          ),
                        ),
                      ),
                      //Date
                      getWeekday(DateTime.now().weekday),
                    ],
                  ),
                ),
              ),
            ),
            //This Week
            Positioned(
              top: size.height * 0.055 + 50 + size.height * 0.075 + size.height * 0.1 + 10 + size.height * 0.1 + 10 + size.height * 0.1 + 10 + size.height * 0.1 + 20 - shrinkOffset,
              right: size.width * 0.0125,
              child: InkWell(
                onTap: (){
                  MusicCircleSearchInherited.of(context).state.searchIndex = 0;
                  MusicCircleSearchInherited.of(context).state.thisWeek = true;
                  MusicCircleSearchInherited.of(context).state.updateSearchPage(1);
                },
                child: SizedBox(
                  height: size.height * 0.2,
                  width: size.width * 0.975 / 2.1,
                  child: Stack(
                    children: [
                      //Background
                      Container(
                        height: size.height * 0.2,
                        width: size.width * 0.975 / 2.1,
                        decoration: BoxDecoration(
                          color: Pallet.darkBlue,
                          borderRadius: BorderRadius.circular(40),
                        ),
                        child: Padding(
                          padding: const EdgeInsets.fromLTRB(20, 30, 0, 0),
                          child: AutoSizeText(
                            'This Week',
                            style: TextStyle(
                              color: Colors.white,
                            ),
                          ),
                        ),
                      ),
                      //Date
                      getWeek(),
                    ],
                  ),
                ),
              ),
            ),
            //Groups
            Positioned(
              top: size.height * 0.055 + 50 + size.height * 0.075 + size.height * 0.1 + 10 + size.height * 0.1 + 10 + size.height * 0.1 + 10 + size.height * 0.1 + 10 + size.height * 0.2 + 30 - shrinkOffset,
              left: size.width * 0.0125,
              child: InkWell(
                onTap: (){
                  MusicCircleSearchInherited.of(context).state.searchIndex = 6;
                  MusicCircleSearchInherited.of(context).state.updateSearchPage(1);
                },
                child: SizedBox(
                  height: size.height * 0.2,
                  width: size.width * 0.975 / 3.1,
                  child: Stack(
                    children: [
                      Container(
                        height: size.height * 0.2,
                        width: size.width * 0.975 / 3.1,
                        decoration: BoxDecoration(
                          color: Pallet.lightBlue,
                          borderRadius: BorderRadius.circular(40),
                        ),
                        child: Padding(
                          padding: const EdgeInsets.fromLTRB(20, 30, 0, 0),
                          child: AutoSizeText(
                            'Groups',
                            style: TextStyle(
                              color: Colors.white,
                            ),
                          ),
                        ),
                      ),
                      Center(
                        child: Padding(
                          padding: const EdgeInsets.fromLTRB(0,8,0,0),
                          child: Icon(
                            Icons.people_rounded,
                            color: Colors.white,
                            size: 40,
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
            //Artists
            Positioned(
              top: size.height * 0.055 + 50 + size.height * 0.075 + size.height * 0.1 + 10 + size.height * 0.1 + 10 + size.height * 0.1 + 10 + size.height * 0.1 + 10 + size.height * 0.2 + 30 - shrinkOffset,
              left: size.width * 0.3425,
              child: InkWell(
                onTap: (){
                  MusicCircleSearchInherited.of(context).state.searchIndex = 2;
                  MusicCircleSearchInherited.of(context).state.updateSearchPage(1);
                },
                child: SizedBox(
                  height: size.height * 0.2,
                  width: size.width * 0.975 / 3.1,
                  child: Stack(
                    children: [
                      Container(
                        height: size.height * 0.2,
                        width: size.width * 0.975 / 3.1,
                        decoration: BoxDecoration(
                          color: Pallet.softBlue,
                          borderRadius: BorderRadius.circular(40),
                        ),
                        child: Padding(
                          padding: const EdgeInsets.fromLTRB(20, 30, 0, 0),
                          child: AutoSizeText(
                            'Artists',
                            style: TextStyle(
                              color: Colors.white,
                            ),
                          ),
                        ),
                      ),
                      Center(
                        child: Padding(
                          padding: const EdgeInsets.fromLTRB(0,8,0,0),
                          child: Icon(
                            Icons.person_rounded,
                            color: Colors.white,
                            size: 40,
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
            //Curators
            Positioned(
              top: size.height * 0.055 + 50 + size.height * 0.075 + size.height * 0.1 + 10 + size.height * 0.1 + 10 + size.height * 0.1 + 10 + size.height * 0.1 + 10 + size.height * 0.2 + 30 - shrinkOffset,
              right: size.width * 0.0125,
              child: InkWell(
                onTap: (){
                  MusicCircleSearchInherited.of(context).state.searchIndex = 1;
                  MusicCircleSearchInherited.of(context).state.curator = true;
                  MusicCircleSearchInherited.of(context).state.updateSearchPage(1);
                },
                child: SizedBox(
                  height: size.height * 0.2,
                  width: size.width * 0.975 / 3.1,
                  child: Stack(
                    children: [
                      Container(
                        height: size.height * 0.2,
                        width: size.width * 0.975 / 3.1,
                        decoration: BoxDecoration(
                          color: Colors.blueGrey.withOpacity(0.5),
                          borderRadius: BorderRadius.circular(40),
                        ),
                        child: Padding(
                          padding: const EdgeInsets.fromLTRB(20, 30, 0, 0),
                          child: AutoSizeText(
                            'Curators',
                            style: TextStyle(
                              color: Colors.white,
                            ),
                          ),
                        ),
                      ),
                      Center(
                        child: Padding(
                          padding: const EdgeInsets.fromLTRB(0,8,0,0),
                          child: Icon(
                            Icons.headset,
                            color: Colors.white,
                            size: 40,
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
            //Genre
            Positioned(
              top: size.height * 0.055 + 50 + size.height * 0.075 + size.height * 0.1 + 10 + size.height * 0.1 + 10 + size.height * 0.1 + 10 + size.height * 0.1 + 20 + size.height * 0.2 + 30 + size.height * 0.2 + 10 - shrinkOffset,
              left: size.width * 0.0125,
              child: Container(
                height: size.height * 0.535,
                width: size.width * 0.975,
                decoration: BoxDecoration(
                    color: Colors.white70.withOpacity(0.9),
                    borderRadius: BorderRadius.circular(20),
                  ),
                child: Column(
                  children: [
                    Expanded(
                      child: Padding(
                        padding: const EdgeInsets.fromLTRB(0,20,0,0),
                        child: AutoSizeText(
                          'Genre',
                          style: TextStyle(
                            color: Colors.blueGrey,
                            fontSize: 22,
                          ),
                        ),
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.fromLTRB(20,0,0,0),
                      child: GridView.builder(
                        shrinkWrap: true,
                        primary: false,
                        gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                          crossAxisCount: 3,
                        ),
                        itemCount: 9,
                        itemBuilder: (BuildContext context, int index){
                          return InkWell(
                            onTap: (){
                              MusicCircleSearchInherited.of(context).state.searchIndex = 0;
                              MusicCircleSearchInherited.of(context).state.genre = MusicCircleSearchInherited.of(context).state.genres[index].name;
                              MusicCircleSearchInherited.of(context).state.updateSearchPage(1);
                            },
                            child: SizedBox(
                              height: size.width * 0.25,
                              width: size.width * 0.25,
                              child: Stack(
                                children: [
                                  Container(
                                    height: size.width * 0.25,
                                    width: size.width * 0.25,
                                    decoration: BoxDecoration(
                                      color: getGenreBoxColor(index),
                                      borderRadius: BorderRadius.circular(10),
                                    ),
                                  ),
                                  Positioned(
                                    bottom: 35,
                                    left: 10,
                                    child: SizedBox(
                                      width: size.width * 0.2,
                                      child: AutoSizeText(
                                        MusicCircleSearchInherited.of(context).state.genres[index].name,
                                        style: TextStyle(
                                          color: Colors.white,
                                          fontSize: 10,
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          );
                        },

                      ),
                    ),
                  ],
                ),
              )
            ),

          //Title
          Positioned(
            top: 0,
            child: SizedBox(
              height: size.height * 0.055 + 30,
              width: size.width,
              child: Container(
                decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius: BorderRadius.only(
                    bottomRight: Radius.circular(15),
                    bottomLeft: Radius.circular(15),
                  ),
                ),
                child: Stack(
                  children: [
                    Positioned(
                      bottom: 0,
                      left: 8,
                      child: AutoSizeText(
                        "Find You're Circle.",
                        style: TextStyle(
                          color: Pallet.darkBlue,
                          fontSize: 40,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ),
          //Search bar
          Positioned(
            top: size.height * 0.055 + 40,
            left: size.width * 0.025,
            child: searchInputWidget(context, size),
          ),
        ],
      ),
    );
  }

  @override
  double get maxExtent => maxHeight;

  @override
  double get minExtent => 0;

  @override
  bool shouldRebuild(covariant SliverPersistentHeaderDelegate oldDelegate) {
    return true;
  }

  searchInputWidget(BuildContext context, Size size){
    return Container(
      height: size.height * 0.075,
      width: size.width * 0.95,
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(20)
      ),
      child: Container(
        height: size.height * 0.075,
        width: size.width * 0.95,
        decoration: BoxDecoration(
          color: Pallet.softBlue.withOpacity(0.4),
          borderRadius: BorderRadius.circular(20),
        ),
        child: SizedBox(
          height: size.height * 0.1,
          child: Container(
            // margin: EdgeInsets.symmetric(horizontal: 15.0),
            padding: EdgeInsets.symmetric(horizontal: 15.0),
            // height: 54,
            decoration: BoxDecoration(
                color: Colors.blueGrey.withOpacity(0.1),
                borderRadius: BorderRadius.circular(20),
                // boxShadow: [
                //   BoxShadow(
                //     offset: Offset(0,3),
                //     blurRadius: 10,
                //     color: Pallet.lightBlue.withOpacity(0.60),
                //   ),
                // ],
            ),
            child: Row(
              children: [
                Expanded(
                  child: TextField(
                    onChanged: (value){
                      MusicCircleSearchInherited.of(context).state.searchText = value;
                    },
                    decoration: InputDecoration(
                      hintText: 'Search',
                      hintStyle: TextStyle(
                        color: Colors.blueGrey,
                      ),
                      enabledBorder: InputBorder.none,
                      focusedBorder: InputBorder.none,
                    ),
                  ),
                ),
                Material(
                  color: Colors.transparent,
                  borderRadius: BorderRadius.circular(30),
                  child: IconButton(
                    onPressed: (){
                      MusicCircleSearchInherited.of(context).state.searchIndex = 0;
                      MusicCircleSearchInherited.of(context).state.updateSearchPage(1);
                    },
                    icon: Icon(
                      Icons.search,
                      color: Colors.blueGrey,
                    ),
                    alignment: Alignment.centerRight,
                    splashColor: Pallet.lightBlue,
                    splashRadius: 25.0,
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  getGenreBoxColor(int index){
    Color color;
    if(index % 5 == 0){
      color = Pallet.blueGrey;
    }
    else if(index % 5 == 1){
      color = Pallet.lightBlue;
    }
    else if(index % 5 == 2){
      color = Pallet.darkOrange;
    }
    else if(index % 5 == 3){
      color = Pallet.darkBlue;
    }
    else{
      color = Colors.blueGrey;
    }

    return color;
  }

  getWeekday(int day){
    Widget weekday;
    switch(day){
      case 1:{
        weekday = Center(
          child: AutoSizeText(
            'Monday',
            style: TextStyle(
              color: Colors.white,
              fontSize: 37.5,
              fontWeight: FontWeight.bold,
            ),
          ),
        );
      }
      break;
      case 2:{
        weekday = Center(
          child: AutoSizeText(
            'Tuesday',
            style: TextStyle(
              color: Colors.white,
              fontSize: 37.5,
              fontWeight: FontWeight.bold,
            ),
          ),
        );
      }
      break;
      case 3:{
        weekday = Center(
          child: AutoSizeText(
            'Wednesday',
            style: TextStyle(
              color: Colors.white,
              fontSize: 25,
              fontWeight: FontWeight.bold,
            ),
          ),
        );
      }
      break;
      case 4:{
        weekday = Center(
          child: AutoSizeText(
            'Thursday',
            style: TextStyle(
              color: Colors.white,
              fontSize: 35,
              fontWeight: FontWeight.bold,
            ),
          ),
        );
      }
      break;
      case 5:{
        weekday = Center(
          child: AutoSizeText(
            'Friday',
            style: TextStyle(
              color: Colors.white,
              fontSize: 40,
              fontWeight: FontWeight.bold,
            ),
          ),
        );
      }
      break;
      case 6:{
        weekday = Center(
          child: AutoSizeText(
            'Saturday',
            style: TextStyle(
              color: Colors.white,
              fontSize: 35,
              fontWeight: FontWeight.bold,
            ),
          ),
        );
      }
      break;
      case 7:{
        weekday = Center(
          child: AutoSizeText(
            'Sunday',
            style: TextStyle(
              color: Colors.white,
              fontSize: 38,
              fontWeight: FontWeight.bold,
            ),
          ),
        );
      }
      break;
    }

    return weekday;
  }

  getWeek(){
    int month = DateTime.now().month;
    int day = DateTime.now().day - DateTime.now().weekday + 1;
    int endMonth = month;
    int endDay = day + 7;
    switch(month){
      case 2:{
        int year = DateTime.now().year;
        if(year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)){
          if(day < 1){
            month--;
            day = 31 + day;
          }
          else if(endDay > 29){
            endMonth++;
            endDay = endDay - 29;
          }
        }
        else{
          if(day < 1){
            month--;
            day = 28 + day;
          }
          else if(endDay > 28){
            endMonth++;
            endDay = endDay - 28;
          }
        }
      }
      break;
      case 4:{
        if(day < 1){
          month--;
          day = 31 + day;
        }
        else if(endDay > 30){
          endMonth++;
          endDay = endDay - 30;
        }
      }
      break;
      case 6:{
        if(day < 1){
          month--;
          day = 31 + day;
        }
        else if(endDay > 30){
          endMonth++;
          endDay = endDay - 30;
        }
      }
      break;
      case 9:{
        if(day < 1){
          month--;
          day = 31 + day;
        }
        else if(endDay > 30){
          endMonth++;
          endDay = endDay - 30;
        }
      }
      break;
      case 11:{
        if(day < 1){
          month--;
          day = 31 + day;
        }
        else if(endDay > 30){
          endMonth++;
          endDay = endDay - 30;
        }
      }
      break;
      default:{
        if(day < 1){
          month--;
          if(month == 4 || month == 6 || month == 9 || month == 11){
            day = 30 + day;
          }
          else{
            day = 31 + day;
          }
        }
        else if(endDay > 31){
          endMonth++;
          endDay = endDay - 31;
        }
      }
    }
    return Center(
      child: AutoSizeText(
        month.toString() + '/' + day.toString() + ' - ' + endMonth.toString() + '/' + endDay.toString(),
        style: TextStyle(
          color: Colors.white,
          fontSize: 25,
          fontWeight: FontWeight.bold
        ),
      ),
    );
  }
}


