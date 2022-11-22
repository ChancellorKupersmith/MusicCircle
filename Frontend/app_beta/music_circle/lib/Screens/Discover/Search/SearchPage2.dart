import 'dart:math';

import 'package:auto_size_text/auto_size_text.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:music_circle/Entities/Album.dart';
import 'package:music_circle/Entities/AudioFile.dart';
import 'package:music_circle/Entities/Entity.dart';
import 'package:music_circle/Entities/Group.dart';
import 'package:music_circle/Entities/Playlist.dart';
import 'package:music_circle/Entities/User.dart';
import 'package:music_circle/Entities/Event.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/UI_Tools/ImageButtons.dart';

class SearchPage2 extends StatefulWidget {
  final searchIndex;
  final bool curator, today, thisWeek;
  final String searchText, genre;
  const SearchPage2({@required this.searchIndex, @required this.curator, @required this.today, @required this.thisWeek, @required this.searchText, @required this.genre, Key key}) : super(key: key);

  @override
  _SearchPage2State createState() => _SearchPage2State();
}
class _SearchPage2State extends State<SearchPage2> {
  List<List<Entity>> searchResults;
  updateSearchResults(List<List<Entity>> results){
    if(searchResults != results){
      setState(() {
        searchResults = results;
      });
    }
  }
  int userIndex, songIndex, playlistIndex, albumIndex, eventIndex, groupIndex;
  int searchIndex;
  updateSearchIndex(int index){
    if(searchIndex != index){
      setState(() {
        searchIndex = index;
      });
    }
  }
  bool curatorFilter;
  updateCuratorFilter(bool curatorFilter){
    if(this.curatorFilter != curatorFilter){
      setState(() {
        this.curatorFilter = curatorFilter;
      });
    }
  }
  bool todayFilter;
  updateTodayFilter(bool todayFilter){
    if(this.todayFilter !=  todayFilter){
      setState(() {
        this.todayFilter = todayFilter;
      });
    }
  }
  bool thisWeekFilter;
  updateThisWeekFilter(bool thisWeekFilter){
    if(this.thisWeekFilter != thisWeekFilter){
      setState(() {
        this.thisWeekFilter = thisWeekFilter;
      });
    }
  }
  String searchText;
  updateSearchText(String searchTerm){
    if(searchText != searchTerm){
      setState(() {
        searchText = searchTerm;
      });
    }
  }
  String genre;
  updateGenre(String genre){
    if(this.genre != genre){
      setState(() {
        this.genre = genre;
      });
    }
  }
  bool showFilter;
  updateShowFilter(bool showFilter){
    if(this.showFilter != showFilter){
      setState(() {
        this.showFilter = showFilter;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    //print('Search index: ');
   // print(searchIndex);
    //init
    if(searchIndex == null){
      getSearchResults();
      searchIndex = widget.searchIndex;
      curatorFilter = widget.curator;
      todayFilter = widget.today;
      thisWeekFilter = widget.thisWeek;
      searchText = widget.searchText;
      genre = widget.genre;
      showFilter = false;
      userIndex = 0;
      songIndex = 0;
      playlistIndex = 0;
      albumIndex = 0;
      eventIndex = 0;
      groupIndex = 0;
    }
    return MusicCircleSearchPage2Inherited(
      state: this,
      child: CustomScrollView(
        slivers: [
          SliverPersistentHeader(delegate: SearchPage2Delegate(maxHeight: size.height * 2)),
        ],
      ),
    );
  }

  //TODO: get search results from database
  getSearchResults(){
    List<User> usersByName = [];
    List<AudioFile> songsByName = [];
    List<Playlist> playlistsByName = [];
    List<Album> albumsByName = [];
    List<Event> eventsByName = [];
    List<Group> groupsByName = [];

    //test results
    for(int i=0; i<30; i++){
      //users
      User user = new User();
      user.testUser(number: i);
      usersByName.add(user);
      print(user);
      //songs
      AudioFile song = new AudioFile();
      song.testSong(number: i);
      songsByName.add(song);
      //playlists
      Playlist playlist = new Playlist();
      playlist.testPlaylist(number: i);
      playlistsByName.add(playlist);
      //albums
      Album album = new Album();
      album.testAlbum(number: i);
      albumsByName.add(album);
      //events
      Event event = new Event();
      event.testEvent(number: i);
      eventsByName.add(event);
      //groups
      Group group = new Group();
      group.testGroup(number: i);
      groupsByName.add(group);
    }

    this.searchResults = [
      usersByName,
      songsByName,
      playlistsByName,
      albumsByName,
      eventsByName,
      groupsByName
    ];
  }
}
class MusicCircleSearchPage2Inherited extends InheritedWidget{
  final _SearchPage2State state;

  MusicCircleSearchPage2Inherited({@required this.state, Widget child}) : super(child: child);

  @override
  bool updateShouldNotify(covariant MusicCircleSearchPage2Inherited oldWidget) {
    return true;
  }

  static MusicCircleSearchPage2Inherited of(BuildContext context) =>
      context.dependOnInheritedWidgetOfExactType<MusicCircleSearchPage2Inherited>();
}

class SearchPage2Delegate extends SliverPersistentHeaderDelegate{
  final double maxHeight;

  SearchPage2Delegate({@required this.maxHeight});
  @override
  Widget build(BuildContext context, double shrinkOffset, bool overlapsContent) {
    Size size = MediaQuery.of(context).size;
    return Container(
      height: maxHeight,
      width: size.width,
      color: Pallet.softBlue.withOpacity(0.3),
      child: Stack(
        children: [
          //Search Results
          Positioned(
            top: size.height * 0.055 + 50 + size.height * 0.075 + size.height * 0.05 - 20,
            child: searchResults(context, size),
          ),
          //Tabs
          Positioned(
            top: size.height * 0.055 + 50 + size.height * 0.075,
            child: SizedBox(
              height: size.height * 0.05,
              width: size.width,
              child: ListView(
                scrollDirection: Axis.horizontal,
                children: [
                  searchIndexTab(context, size, 'All', 0),
                  searchIndexTab(context, size, 'People', 1),
                  searchIndexTab(context, size, 'Songs', 2),
                  searchIndexTab(context, size, 'Playlists', 3),
                  searchIndexTab(context, size, 'Albums', 4),
                  searchIndexTab(context, size, 'Events', 5),
                  searchIndexTab(context, size, 'Groups', 6),
                ],
              ),
            ),
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
          //Filter button
          Positioned(
            top: size.height * 0.055 + 50,
            right: -5,
            child: filterButton(context),
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
      width: size.width * 0.875,
      decoration: BoxDecoration(
          color: Colors.white,
          borderRadius: BorderRadius.circular(20)
      ),
      child: Container(
        height: size.height * 0.075,
        width: size.width * 0.875,
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
                      MusicCircleSearchPage2Inherited.of(context).state.setState(() {
                        MusicCircleSearchPage2Inherited.of(context).state.getSearchResults();
                      });

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

  filterButton(BuildContext context){
    if(MusicCircleSearchPage2Inherited.of(context).state.showFilter){
      return IconButton(
        onPressed: (){
          MusicCircleSearchPage2Inherited.of(context).state.updateShowFilter(false);
        },
        icon: Icon(
          Icons.keyboard_arrow_up_rounded,
          size: 30,
          color: Pallet.darkBlue.withOpacity(0.8),
        ),
      );
    }
    else{
      return IconButton(
        onPressed: (){
          MusicCircleSearchPage2Inherited.of(context).state.updateShowFilter(true);
        },
        icon: Icon(
          Icons.more_vert_rounded,
          size: 30,
          color: Pallet.darkBlue.withOpacity(0.8),
        ),
      );
    }
  }

  searchIndexTab(BuildContext context, Size size, String label, int index){
    return InkWell(
      onTap: (){
        MusicCircleSearchPage2Inherited.of(context).state.updateSearchIndex(index);
      },
      child: Container(
        height: size.height * 0.05,
        width: size.width * 0.22,
        decoration: BoxDecoration(
          color: MusicCircleSearchPage2Inherited.of(context).state.searchIndex == index? Pallet.darkBlue:Colors.blueGrey.withOpacity(0.5),
          borderRadius: BorderRadius.circular(20),
        ),
        child: Center(
          child: AutoSizeText(
            label,
            textAlign: TextAlign.center,
            style: TextStyle(
              color: MusicCircleSearchPage2Inherited.of(context).state.searchIndex == index? Pallet.softBlue:Pallet.darkBlue,
            ),
          ),
        ),
      ),
    );
  }

  searchResults(BuildContext context, Size size){
    Widget results;
    switch(MusicCircleSearchPage2Inherited.of(context).state.searchIndex){
      case 0:{
        if(MusicCircleSearchPage2Inherited.of(context).state.searchResults == null){
          MusicCircleSearchPage2Inherited.of(context).state.searchResults = MusicCircleSearchPage2Inherited.of(context).state.getSearchResults();
        }
        if(
          MusicCircleSearchPage2Inherited.of(context).state.searchResults[0].length
            + MusicCircleSearchPage2Inherited.of(context).state.searchResults[1].length
            + MusicCircleSearchPage2Inherited.of(context).state.searchResults[2].length
            + MusicCircleSearchPage2Inherited.of(context).state.searchResults[3].length
            + MusicCircleSearchPage2Inherited.of(context).state.searchResults[4].length
            + MusicCircleSearchPage2Inherited.of(context).state.searchResults[5].length
          == 0
        ){
          results = AutoSizeText(
            "No results with name '" + MusicCircleSearchPage2Inherited.of(context).state.searchText + "'.",
            style: TextStyle(

            ),
          );
        }
        else{
          results = SizedBox(
            height: size.height,
            width: size.width,
            child: ListView.builder(
                shrinkWrap: true,
                primary: false,
                itemCount:
                MusicCircleSearchPage2Inherited.of(context).state.searchResults[0].length
                    + MusicCircleSearchPage2Inherited.of(context).state.searchResults[1].length
                    + MusicCircleSearchPage2Inherited.of(context).state.searchResults[2].length
                    + MusicCircleSearchPage2Inherited.of(context).state.searchResults[3].length
                    + MusicCircleSearchPage2Inherited.of(context).state.searchResults[4].length
                    + MusicCircleSearchPage2Inherited.of(context).state.searchResults[5].length,
                itemBuilder: (BuildContext context, int index){
                  //TODO: order list by entities activity variable
                  //for now random ordering
                  var rand = new Random();
                  int entityType = rand.nextInt(6);
                  int entityIndex = 0;
                  switch(entityType){

                    case 0:{
                      print(MusicCircleSearchPage2Inherited.of(context).state.userIndex);
                      if(29 > MusicCircleSearchPage2Inherited.of(context).state.userIndex){
                        entityIndex = MusicCircleSearchPage2Inherited.of(context).state.userIndex++;
                      }
                      else{
                        MusicCircleSearchPage2Inherited.of(context).state.userIndex = 0;
                      }
                    }
                    break;
                    case 1:{
                      if(29 > MusicCircleSearchPage2Inherited.of(context).state.songIndex){
                        entityIndex = MusicCircleSearchPage2Inherited.of(context).state.songIndex++;
                      }
                    }
                    break;
                    case 2:{
                      if(29 > MusicCircleSearchPage2Inherited.of(context).state.playlistIndex){
                        entityIndex = MusicCircleSearchPage2Inherited.of(context).state.playlistIndex++;
                      }
                    }
                    break;
                    case 3:{
                      if(29 > MusicCircleSearchPage2Inherited.of(context).state.albumIndex){
                        entityIndex = MusicCircleSearchPage2Inherited.of(context).state.albumIndex++;
                      }
                    }
                    break;
                    case 4:{
                      if(29 > MusicCircleSearchPage2Inherited.of(context).state.eventIndex){
                        entityIndex = MusicCircleSearchPage2Inherited.of(context).state.eventIndex++;
                      }
                    }
                    break;
                    case 5:{
                      if(29 > MusicCircleSearchPage2Inherited.of(context).state.groupIndex){
                        entityIndex = MusicCircleSearchPage2Inherited.of(context).state.groupIndex++;
                      }
                    }
                    break;
                    default:{}
                  }
                  //print('entity type: ' + entityType.toString());
                  //print('entity index: ' + entityIndex.toString());
                  //print(MusicCircleSearchPage2Inherited.of(context).state.searchIndex);
                  //print(MusicCircleSearchPage2Inherited.of(context).state.searchResults[entityType][entityIndex]);
                  return SearchItem(
                    entity: MusicCircleSearchPage2Inherited.of(context).state.searchResults[entityType][entityIndex],
                    pageIndex: MusicCircleSearchPage2Inherited.of(context).state.searchIndex
                  );
                  //return Container();
                }
            ),
          );
        }
      }
      break;
      case 1:{
        if(MusicCircleSearchPage2Inherited.of(context).state.searchResults == null){
          MusicCircleSearchPage2Inherited.of(context).state.searchResults = MusicCircleSearchPage2Inherited.of(context).state.getSearchResults();
        }
        if(
        MusicCircleSearchPage2Inherited.of(context).state.searchResults[0].length
            + MusicCircleSearchPage2Inherited.of(context).state.searchResults[1].length
            + MusicCircleSearchPage2Inherited.of(context).state.searchResults[2].length
            + MusicCircleSearchPage2Inherited.of(context).state.searchResults[3].length
            + MusicCircleSearchPage2Inherited.of(context).state.searchResults[4].length
            + MusicCircleSearchPage2Inherited.of(context).state.searchResults[5].length
            == 0
        ){
          results = AutoSizeText(
            "No results with name '" + MusicCircleSearchPage2Inherited.of(context).state.searchText + "'.",
            style: TextStyle(

            ),
          );
        }
        else{
          results = SizedBox(
            height: size.height,
            width: size.width,
            child: GridView.builder(
              shrinkWrap: true,
              primary: false,
              itemCount:
              MusicCircleSearchPage2Inherited.of(context).state.searchResults[0].length
              + MusicCircleSearchPage2Inherited.of(context).state.searchResults[1].length
              + MusicCircleSearchPage2Inherited.of(context).state.searchResults[2].length
              + MusicCircleSearchPage2Inherited.of(context).state.searchResults[3].length
              + MusicCircleSearchPage2Inherited.of(context).state.searchResults[4].length
              + MusicCircleSearchPage2Inherited.of(context).state.searchResults[5].length,
              itemBuilder: (BuildContext context, int index){
                var rand = new Random();
                int entityType = rand.nextInt(6);
                int entityIndex = 0;
                switch(entityType){
                  case 0:{
                    print(MusicCircleSearchPage2Inherited.of(context).state.userIndex);
                    if(29 > MusicCircleSearchPage2Inherited.of(context).state.userIndex){
                      entityIndex = MusicCircleSearchPage2Inherited.of(context).state.userIndex++;
                    }
                    else{
                      MusicCircleSearchPage2Inherited.of(context).state.userIndex = 0;
                    }
                  }
                  break;
                  case 1:{
                    if(29 > MusicCircleSearchPage2Inherited.of(context).state.songIndex){
                      entityIndex = MusicCircleSearchPage2Inherited.of(context).state.songIndex++;
                    }
                  }
                  break;
                  case 2:{
                    if(29 > MusicCircleSearchPage2Inherited.of(context).state.playlistIndex){
                      entityIndex = MusicCircleSearchPage2Inherited.of(context).state.playlistIndex++;
                    }
                  }
                  break;
                  case 3:{
                    if(29 > MusicCircleSearchPage2Inherited.of(context).state.albumIndex){
                      entityIndex = MusicCircleSearchPage2Inherited.of(context).state.albumIndex++;
                    }
                  }
                  break;
                  case 4:{
                    if(29 > MusicCircleSearchPage2Inherited.of(context).state.eventIndex){
                      entityIndex = MusicCircleSearchPage2Inherited.of(context).state.eventIndex++;
                    }
                  }
                  break;
                  case 5:{
                    if(29 > MusicCircleSearchPage2Inherited.of(context).state.groupIndex){
                      entityIndex = MusicCircleSearchPage2Inherited.of(context).state.groupIndex++;
                    }
                  }
                  break;
                  default:{}
                }
                //print('entity type: ' + entityType.toString());
                //print('entity index: ' + entityIndex.toString());
                //print(MusicCircleSearchPage2Inherited.of(context).state.searchIndex);
                //print(MusicCircleSearchPage2Inherited.of(context).state.searchResults[entityType][entityIndex]);
                return SearchItem(
                    entity: MusicCircleSearchPage2Inherited.of(context).state.searchResults[entityType][entityIndex],
                    pageIndex: MusicCircleSearchPage2Inherited.of(context).state.searchIndex
                );
              },
              gridDelegate: null,
            ),
          );
        }
      }
      break;
      default:{
       results = Container();
      }
    }
    return results;
  }
}

class SearchItem extends StatelessWidget {
  final Entity entity;
  final int pageIndex;
  const SearchItem({@required this.entity, @required this.pageIndex, Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    //print(this.entity);
    return getPageTypeItem(size);
  }
  Widget getPageTypeItem(Size size){
    Widget searchItem;
    switch(pageIndex){
      case 0:{
        searchItem = InkWell(
          onTap: (){
            //TODO: different functionality depending on entity
          },
          child: SizedBox(
            height: size.height * 0.1,
            width: size.width,
            child: Row(
              children: [
                getEntityPic(size, entity),
                getEntityTitle(size, entity),
                getEntityDescription(size, entity),
                getEntityIcons(size),
              ],
            ),
          ),
        );
      }
      break;
      default:{
        searchItem = Container();
      }
    }
    return searchItem;
  }
  //TODO
  getEntityPic(Size size, Entity entity){
    Widget entityPic;
    double imgSize = size.width * 0.155;
    switch(entity.getType()){
      case EntityType.USER:{
        User user = entity;
        entityPic = ImageButtonCircle(imgSize, imgSize, user.name, user.userImage);
      }
      break;
      case EntityType.AUDIO_FILE:{
        AudioFile song = entity;
        entityPic = ImageButtonSquareNotSoRounded(imgSize, imgSize, song.name, song.songImage);
      }
      break;
      case EntityType.ALBUM:{
        Album album = entity;
        entityPic = ImageButtonSquareNotSoRounded(imgSize, imgSize, album.name, album.albumImage);
      }
      break;
      case EntityType.PLAYLIST:{
        entityPic = Padding(
          padding: const EdgeInsets.fromLTRB(16,0,0,0),
          child: Icon(
            Icons.playlist_play,
            color: Pallet.blueGrey.withOpacity(0.7),
            size: 30,
          ),
        );
      }
      break;
      case EntityType.EVENT:{
        Event event = entity;
        entityPic = ImageButtonSquareNotSoRounded(imgSize, imgSize, event.name, event.eventImage);
      }
      break;
      case EntityType.GROUP:{
        Group group = entity;
        entityPic = ImageButtonSquareNotSoRounded(imgSize, imgSize, group.name, group.groupImage);
      }
      break;
      default:{
        entityPic = Container();
      }
    }

    return entityPic;
  }
  //TODO
  getEntityTitle(Size size, Entity entity){
    Widget entityTitle;
    if(entity.getType() == EntityType.PLAYLIST){
      entityTitle = Padding(
        padding: const EdgeInsets.fromLTRB(11.575,0,0,0),
        child: AutoSizeText(
          entity.name,
          style: TextStyle(
            fontWeight: FontWeight.w600,
            color: Pallet.blueGrey.withOpacity(0.6),
          ),
        ),
      );
    } else{
      entityTitle = AutoSizeText(
        entity.name,
        style: TextStyle(
          fontWeight: FontWeight.bold,
          color: Pallet.blueGrey.withOpacity(0.6),
        ),
      );
    }
    return Padding(
      padding: const EdgeInsets.fromLTRB(10, 0, 20,0),
      child: entityTitle,
    );
  }
  //TODO
  getEntityDescription(Size size, Entity entity){
    Widget entityDesc;

    switch(entity.getType()){
      case EntityType.USER:{
        User user = entity;
        if(user.curator && user.artist){
          String genreText = '';
          for(int i=0; (i<3 && i<user.genres.length); i++) {
            if (i == 2 || i == user.genres.length - 1) {
              genreText += user.genres[i].name;
            }
            else {
              genreText += user.genres[i].name + ', ';
            }
          }
          String desc;
          if(genreText != ''){
            desc = 'Artist, Curator, Genre: ' + genreText;
          } else{
            desc = 'Artist, Curator';
          }
          entityDesc = AutoSizeText(
            desc,
            style: TextStyle(
              fontStyle: FontStyle.italic
            ),
          );
        }
        else if(user.artist){
          String genreText = '';
          for(int i=0; (i<3 && i<user.genres.length); i++) {
            if (i == 2 || i == user.genres.length - 1) {
              genreText += user.genres[i].name;
            }
            else {
              genreText += user.genres[i].name + ', ';
            }
          }
          String desc;
          if(genreText != ''){
            desc = 'Artist, Genre: ' + genreText;
          } else{
            desc = 'Artist';
          }
          entityDesc = AutoSizeText(
            desc,
            style: TextStyle(
                fontStyle: FontStyle.italic
            ),
          );
        }
        else if(user.curator){
          String genreText = '';
          for(int i=0; (i<3 && i<user.genres.length); i++){
            if(i == 2 || i == user.genres.length - 1){
              genreText += user.genres[i].name;
            }
            else{
              genreText += user.genres[i].name + ', ';
            }
          }
          String desc;
          if(genreText != ''){
            desc = 'Curator, Genre: ' + genreText;
          } else{
            desc = 'Curator';
          }
          entityDesc = AutoSizeText(
            desc,
            style: TextStyle(
                fontStyle: FontStyle.italic
            ),
          );
        }
        else{
          entityDesc = Container();
        }
      }
      break;
      case EntityType.AUDIO_FILE:{
        AudioFile song = entity;
        entityDesc = AutoSizeText(
          song.artist.name,
          textAlign: TextAlign.end,
          style: TextStyle(

          ),
        );
      }
      break;
      case EntityType.PLAYLIST:{
        Playlist playlist = entity;
        String genreText = '';
        for(int i=0; i<3 && i < playlist.genres.length; i++){
          genreText += playlist.genres[i].name + ' ';
        }
        String desc;
        if(genreText != ''){
          desc = playlist.creator.name + ', Genre: ' + genreText;
        } else{
          desc = playlist.creator.name;
        }
        entityDesc = AutoSizeText(
          desc,
          textAlign: TextAlign.end,
          style: TextStyle(

          ),
        );
      }
      break;
      case EntityType.ALBUM:{
        Album album = entity;
        entityDesc = AutoSizeText(
          album.artist.name,
          textAlign: TextAlign.end,
          style: TextStyle(

          ),
        );
      }
      break;
      case EntityType.EVENT:{
        Event event = entity;
        entityDesc = AutoSizeText(
          event.city + ', ' + event.state,
          style: TextStyle(
              fontStyle: FontStyle.italic
          ),
        );
      }
      break;
      case EntityType.GROUP:{
        Group group = entity;
        String groupDesc = '';
        //get paraphrased description
        if(group.description.length > 30){
          for(int i=29; group.description[i] == ' '; i--){
            groupDesc = group.description.substring(0, i) + '...';
          }
        }
        else{
          groupDesc = group.description;
        }
        entityDesc = AutoSizeText(
          groupDesc,
          textAlign: TextAlign.end,
          style: TextStyle(
              fontStyle: FontStyle.italic
          ),
        );
      }
      break;
      default:{
        entityDesc = Container();
      }
    }

    return Expanded(
      child: Padding(
        padding: const EdgeInsets.fromLTRB(0,0,20,0),
        child: entityDesc,
      ),
    );
  }
  //TODO
  getEntityIcons(Size size){
    Widget entityIcons;

    switch(entity.getType()){
      case EntityType.USER:{
        User user = entity;
        if(user.curator && user.artist){
          entityIcons = SizedBox(
            height: size.height * 0.1,
            width: size.width * 0.2,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Icon(
                      Icons.album,
                      color: Pallet.lightBlue,
                    ),
                    AutoSizeText(
                      user.songs.length.toString(),
                      style: TextStyle(

                      ),
                    ),
                  ],
                ),
                Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Icon(
                      Icons.playlist_play,
                      color: Pallet.lightBlue,
                    ),
                    AutoSizeText(
                      user.playlists.length.toString(),
                      style: TextStyle(

                      ),
                    ),
                  ],
                ),
                Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Icon(
                      Icons.people_rounded,
                      color: Pallet.lightBlue,
                    ),
                    AutoSizeText(
                      user.followers.length.toString(),
                      style: TextStyle(

                      ),
                    ),
                  ],
                ),
              ],
            ),
          );
        }
        else if(user.curator){
          entityIcons = Row(
            children: [
              Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Icon(
                    Icons.playlist_play,
                    color: Pallet.lightBlue,
                  ),
                  AutoSizeText(
                    user.playlists.length.toString(),
                    style: TextStyle(

                    ),
                  ),
                ],
              ),
              Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Icon(
                    Icons.people_rounded,
                    color: Pallet.lightBlue,
                  ),
                  AutoSizeText(
                    user.followers.length.toString(),
                    style: TextStyle(

                    ),
                  ),
                ],
              ),
            ],
          );
        }
        else if(user.artist){
          entityIcons = Row(
            children: [
              Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Icon(
                    Icons.album,
                    color: Pallet.lightBlue,
                  ),
                  AutoSizeText(
                    user.songs.length.toString(),
                    style: TextStyle(

                    ),
                  ),
                ],
              ),
              Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Icon(
                    Icons.people_rounded,
                    color: Pallet.lightBlue,
                  ),
                  AutoSizeText(
                    user.followers.length.toString(),
                    style: TextStyle(

                    ),
                  ),
                ],
              ),
            ],
          );
        }
        else{
          entityIcons = Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Icon(
                Icons.people_rounded,
                color: Pallet.lightBlue,
              ),
              AutoSizeText(
                user.followers.length.toString(),
                style: TextStyle(

                ),
              ),
            ],
          );
        }
      }
      break;
      case EntityType.AUDIO_FILE:{
        AudioFile song = entity;
        entityIcons = Column(
          mainAxisAlignment: MainAxisAlignment.center,

          children: [
            Icon(
              Icons.favorite,
              color: Pallet.lightBlue,
            ),
            AutoSizeText(
              song.likes.toString(),
              style: TextStyle(

              ),
            ),
          ],
        );
      }
      break;
      case EntityType.PLAYLIST:{
        Playlist playlist = entity;
        entityIcons = Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              Icons.album,
              color: Pallet.lightBlue,
            ),
            AutoSizeText(
              playlist.songs.length.toString(),
              style: TextStyle(

              ),
            ),
          ],
        );
      }
      break;
      case EntityType.ALBUM:{
        Album album = entity;
        entityIcons = Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              Icons.album,
              color: Pallet.lightBlue,
            ),
            AutoSizeText(
              album.songs.length.toString(),
              style: TextStyle(

              ),
            ),
          ],
        );
      }
      break;
      case EntityType.EVENT:{
        Event event = entity;
        entityIcons = Column(
          crossAxisAlignment: CrossAxisAlignment.end,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              Icons.event,
              color: Pallet.lightBlue,
            ),
            AutoSizeText(
              event.dateTime.month.toString() + '/' + event.dateTime.day.toString() + '/' + event.dateTime.year.toString(),
              style: TextStyle(

              ),
            ),
          ],
        );
      }
      break;
      case EntityType.GROUP:{
        Group group = entity;
        entityIcons = Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              Icons.people_rounded,
              color: Pallet.lightBlue,
            ),
            AutoSizeText(
              group.members.length.toString(),
              style: TextStyle(

              ),
            ),
          ],
        );
      }
      break;
      default:{
        entityIcons = Container();
      }
    }

    return entityIcons;
  }
}


