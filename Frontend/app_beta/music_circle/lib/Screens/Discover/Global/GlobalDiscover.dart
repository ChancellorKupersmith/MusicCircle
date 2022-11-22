import 'dart:math';

import 'package:auto_size_text/auto_size_text.dart';
import 'package:flutter/material.dart';
import 'package:music_circle/Entities/Album.dart';
import 'package:music_circle/Entities/AudioFile.dart';
import 'package:music_circle/Entities/Entity.dart';
import 'package:music_circle/Entities/Event.dart';
import 'package:music_circle/Entities/Group.dart';
import 'package:music_circle/Entities/Playlist.dart';
import 'package:music_circle/Entities/User.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Discover/DiscoverPage.dart';
import 'package:music_circle/UI_Tools/ImageButtons.dart';
import 'package:music_circle/UI_Tools/ListWidgets/AlbumEntityList.dart';
import 'package:music_circle/UI_Tools/ListWidgets/EventsList.dart';
import 'package:music_circle/UI_Tools/ListWidgets/MusicEntityList.dart';
import 'package:music_circle/UI_Tools/ListWidgets/UserEntityList.dart';

class GlobalRecommendations extends StatefulWidget {
  const GlobalRecommendations({Key key}) : super(key: key);

  @override
  _GlobalRecommendationsState createState() => _GlobalRecommendationsState();
}
class _GlobalRecommendationsState extends State<GlobalRecommendations> {
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return CustomScrollView(
      slivers: [
        GlobalRecommendationsPage(),
      ],
    );
  }
}

class GlobalRecommendationsPage extends StatelessWidget {
  const GlobalRecommendationsPage({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return SliverPersistentHeader(delegate: GlobalRecommendationsPageDelegate(maxHeight: size.height * 2.375));

  }
}
class GlobalRecommendationsPageDelegate extends SliverPersistentHeaderDelegate{
  final double maxHeight;

  GlobalRecommendationsPageDelegate({@required this.maxHeight});
  @override
  Widget build(BuildContext context, double shrinkOffset, bool overlapsContent) {
    Size size = MediaQuery.of(context).size;
    return Stack(
      children: [
        //top songs
        Positioned(
          top: min(size.height * 0.1 + size.height * 0.055 - 30, double.maxFinite) - shrinkOffset,
          child: DiscoverPageContainer(
            height: size.height * 0.45,
            width: size.width,
            backgroundColor: Pallet.darkBlue,
            child: GlobalTopSongsListHorizontal(
              header: 'Top Songs',
              headerColor: Pallet.lightBlue,
              songListYear: getTopSongsGlobalYear(size),
              songListMonth: getTopSongsGlobalMonth(size),

            )
          ),
        ),
        //Greeting message
        Positioned(
          top: min(size.height * 0.1 - 30, double.maxFinite) - shrinkOffset,
          child: SizedBox(
            height: size.height * 0.055 + 30,
            width: size.width,
            child: Container(
              decoration: BoxDecoration(
                color: Pallet.softBlue,
                borderRadius: BorderRadius.only(
                  bottomRight: Radius.circular(15),
                  bottomLeft: Radius.circular(15),
                ),
              ),
              child: Stack(
                children: [
                  Positioned(
                    bottom: 10,
                    left: 8,
                    child: AutoSizeText(
                      getGreetingMessage(),
                      style: TextStyle(
                        color: Pallet.darkBlue,
                        fontSize: 30,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ),
        //top artists
        Positioned(
          top: min(size.height * 0.1 + size.height * 0.055 + size.height * 0.373, double.maxFinite) - shrinkOffset,
          child: DiscoverPageContainer(
              height: size.height * 0.3,
              width: size.width,
              backgroundColor: Colors.white,
              child: Container(
                decoration: BoxDecoration(
                  color: Pallet.lightBlue.withOpacity(0.3),
                  borderRadius: BorderRadius.only(
                    topRight: Radius.circular(20),
                    topLeft: Radius.circular(20),
                  ),
                ),
                child: UserListHorizontal(
                  header: 'Hot Artists',
                  headerColor: Colors.brown,
                  userList: getHotArtistsGlobal(size),
                ),
              ),
          ),
        ),
        //new releases
        Positioned(
          top: min(size.height * 0.1 + size.height * 0.055 + size.height * 0.375 + size.height * 0.25, double.maxFinite) - shrinkOffset,
          child: DiscoverPageContainer(
            height: size.height * 0.3,
            width: size.width,
            backgroundColor: Pallet.softBlue,
            child: SongsListHorizontal(
              header: 'New Releases',
              headerColor: Pallet.darkBlue,
              songList: getNewReleasesGlobal(size, 'userID'),
            ),
          ),
        ),
        //biggest events
        Positioned(
          top: min(size.height * 0.1 + size.height * 0.055 + size.height * 0.375 + size.height * 0.25 + size.height * 0.25, double.maxFinite) - shrinkOffset,
          child: DiscoverPageContainer(
            height: size.height * 0.35,
            width: size.width,
            backgroundColor: Pallet.darkBlue,
            child: EventsListHorizontalGlobal(
              header: 'Biggest Events',
              headerColor: Colors.white,
              eventList: getBiggestEventsGlobal(size),
            ),
          ),
        ),
        //top playlists
        Positioned(
          top: min(size.height * 0.1 + size.height * 0.055 + size.height * 0.375 + size.height * 0.25 + size.height * 0.25 + size.height * 0.3, double.maxFinite) - shrinkOffset,
          child: DiscoverPageContainer(
            height: size.height * 0.3,
            width: size.width,
            backgroundColor: Pallet.lightBlue,
            child: SongsListHorizontal(
              header: 'Top Playlists',
              headerColor: Pallet.softBlue,
              songList: getTopPlaylists(size, 'userID'),
            ),
          ),
        ),
        //top curators
        Positioned(
          top: min(size.height * 0.1 + size.height * 0.055 + size.height * 0.375 + size.height * 0.25 + size.height * 0.25 + size.height * 0.3 + size.height * 0.25, double.maxFinite) - shrinkOffset,
          child: DiscoverPageContainer(
            height: size.height * 0.3,
            width: size.width,
            backgroundColor: Pallet.softBlue,
            child: UserListHorizontal(
              header: 'Top Curators',
              headerColor: Pallet.blueGrey,
              userList: getTopCuratorsGlobal(size),
            ),
          ),
        ),
        //top albums
        Positioned(
          top: min(size.height * 0.1 + size.height * 0.055 + size.height * 0.375 + size.height * 0.25 + size.height * 0.25 + size.height * 0.3 + size.height * 0.25 + size.height * 0.25, double.maxFinite) - shrinkOffset,
          child: DiscoverPageContainer(
            height: size.height * 0.3,
            width: size.width,
            backgroundColor: Colors.blueGrey,
            child: SongsListHorizontal(
              header: 'Top Albums',
              headerColor: Colors.white,
              songList: getTopAlbumsGlobal(size),
            )
          ),
        ),
        //up and coming groups
        Positioned(
          top: min(size.height * 0.1 + size.height * 0.055 + size.height * 0.375 + size.height * 0.25 + size.height * 0.25 + size.height * 0.3 + size.height * 0.25 + size.height * 0.25 + size.height * 0.25, double.maxFinite) - shrinkOffset,
          child: DiscoverPageContainer(
              height: size.height * 0.3,
              width: size.width,
              backgroundColor: Pallet.darkBlue,
              child: GroupListHorizontal(
                header: 'Growing Groups',
                headerColor: Pallet.softBlue,
                groupList: getGrowingGroups(size),
              )
          ),
        ),

        //title
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
                  )
              ),
              child: Stack(
                children: [
                  Positioned(
                    bottom: 0,
                    left: 8,
                    child: AutoSizeText.rich(
                      TextSpan(
                        style: TextStyle(
                          color: Pallet.darkBlue,
                          fontWeight: FontWeight.bold,
                          fontSize: 40,
                        ),
                        children: [
                          TextSpan(
                            text: 'Music',
                          ),
                          TextSpan(
                            text: 'Circle',
                            style: TextStyle(
                              color: Pallet.lightBlue.withOpacity(0.9),
                            ),
                          ),
                          TextSpan(
                            text: ' Global',
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ),
      ],
    );
  }

  getGreetingMessage(){
    String greeting;
    DateTime time = DateTime.now();
    if(time.hour < 4){
      greeting = "I'm getting sleepy,";
    }
    else if(time.hour < 11){
      greeting = 'Good Morning,';
    }
    else if(time.hour < 18){
      greeting = 'hello there,';
    }
    else if(time.hour < 22){
      greeting = 'Good Evening,';
    }
    else{
      greeting = "Hello Night Owl,";
    }
    return greeting;
  }
  @override
  double get maxExtent => maxHeight;
  @override
  double get minExtent => 0;
  @override
  bool shouldRebuild(covariant SliverPersistentHeaderDelegate oldDelegate) {
    return true;
  }
}

//TODO
//Get request to music circle's api to get top global songs for year
List<Widget> getTopSongsGlobalYear(Size size){
  List<AudioFile> topSongs = [];
  List<Widget> btnList = [];

  //Get top global songs
  //TODO

  //test list
  Image entityImageYear = Image.asset(
    'lib/Resources/Images/album2_example_pic.jpg',
    fit: BoxFit.cover,
  );

  for (int i = 0; i < 30; i++) {
    AudioFile song = new AudioFile();
    song.name = 'Song ' + i.toString();
    song.songImage = entityImageYear;
    topSongs.add(song);
  }

  topSongs.forEach((song) {
    btnList.add(
      Padding(
        padding:const EdgeInsets.fromLTRB(6, 8, 4, 0),
        child: Stack(
          children: [
            ImageButtonSquareRounded(size.width * 0.65, size.width * 0.55, song.name, song.songImage),
            Positioned(
              left: 10,
              bottom: 20,
              child: Container(
                decoration: BoxDecoration(
                  color: Pallet.lightBlue.withOpacity(0.8),
                  borderRadius: BorderRadius.circular(20),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: AutoSizeText(
                    song.name,
                    style: TextStyle(
                      color: Colors.white,
                    ),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  });
  return btnList;
}
//TODO
//Get request to music circle's api to get top global songs for month
List<Widget> getTopSongsGlobalMonth(Size size){
  List<AudioFile> topSongs = [];
  List<Widget> btnList = [];

  //Get top global songs
  //TODO

  //test list
  Image entityImageYear = Image.asset(
    'lib/Resources/Images/user8_example_pic.jpg',
    fit: BoxFit.cover,
  );



  for (int i = 0; i < 30; i++) {
    AudioFile song = new AudioFile();
    song.name = 'Song ' + i.toString();
    song.songImage = entityImageYear;
    topSongs.add(song);
  }

  topSongs.forEach((song) {
    btnList.add(
      Padding(
        padding:const EdgeInsets.fromLTRB(6, 8, 4, 0),
        child: Stack(
          children: [
            ImageButtonSquareRounded(size.width * 0.65, size.width * 0.55, song.name, song.songImage),
            Positioned(
              left: 10,
              bottom: 20,
              child: Container(
                decoration: BoxDecoration(
                  color: Pallet.lightBlue.withOpacity(0.8),
                  borderRadius: BorderRadius.circular(20),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: AutoSizeText(
                    song.name,
                    style: TextStyle(
                      color: Colors.white,
                    ),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  });
  return btnList;
}
//TODO
//Get request to music circle's api to get global hot artists
List<Widget> getHotArtistsGlobal(Size size){
  List<User> hotArtists = [];
  List<Widget> btnList = [];

  //Get top local artists
  //TODO

  //test list
  Image entityImage = Image.asset(
    'lib/Resources/Images/user5_example_pic.jpg',
    fit: BoxFit.cover,
  );
  for (int i = 0; i < 11; i++) {
    User artist = new User();
    artist.name = 'Artist ' + i.toString();
    artist.userImage = entityImage;
    hotArtists.add(artist);
  }

  hotArtists.forEach((artist) {
    btnList.add(
      Padding(
        padding:const EdgeInsets.fromLTRB(0, 8, 0, 0),
        child: Stack(
          children: [
            ImageButtonCircle(size.width * 0.4, size.width * 0.4, artist.name, artist.userImage),
            Positioned(
              left: min(size.width * 0.115, double.maxFinite),
              bottom: 10,
              child: Container(
                decoration: BoxDecoration(
                  color: Pallet.darkBlue.withOpacity(0.8),
                  borderRadius: BorderRadius.circular(20),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: AutoSizeText(
                    artist.name,
                    style: TextStyle(
                      color: Colors.white,
                    ),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  });
  return btnList;
}
//TODO
//Get request to music circle's api to get global hot artists
List<Widget> getTopCuratorsGlobal(Size size){
  List<User> topCurators = [];
  List<Widget> btnList = [];

  //Get top local artists
  //TODO

  //test list
  Image entityImage = Image.asset(
    'lib/Resources/Images/user1_example_pic.jpg',
    fit: BoxFit.cover,
  );
  for (int i = 0; i < 11; i++) {
    User artist = new User();
    artist.name = 'Artist ' + i.toString();
    artist.userImage = entityImage;
    topCurators.add(artist);
  }

  topCurators.forEach((artist) {
    btnList.add(
      Padding(
        padding:const EdgeInsets.fromLTRB(0, 8, 0, 0),
        child: Stack(
          children: [
            ImageButtonCircle(size.width * 0.4, size.width * 0.4, artist.name, artist.userImage),
            Positioned(
              left: min(size.width * 0.115, double.maxFinite),
              bottom: 10,
              child: Container(
                decoration: BoxDecoration(
                  color: Pallet.darkBlue.withOpacity(0.8),
                  borderRadius: BorderRadius.circular(20),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: AutoSizeText(
                    artist.name,
                    style: TextStyle(
                      color: Colors.white,
                    ),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  });
  return btnList;
}
//TODO
//Get request to music circle's api to get biggest global events
List<Widget> getBiggestEventsGlobal(Size size){
  List<Event> biggestEvents = [];
  List<Widget> btnList = [];

  //Get top global songs
  //TODO

  //test list
  Image entityImage = Image.asset(
    'lib/Resources/Images/song2_example_pic.jpg',
    fit: BoxFit.cover,
  );

  for (int i = 0; i < 30; i++) {
    Event event = new Event();
    event.name = 'Event ' + i.toString();
    event.eventImage = entityImage;
    biggestEvents.add(event);
  }

  biggestEvents.forEach((event) {
    btnList.add(
      Padding(
        padding:const EdgeInsets.fromLTRB(6, 8, 4, 0),
        child: Stack(
          children: [
            ImageButtonSquareRounded(size.width * 0.5, size.width * 0.4, event.name, event.eventImage),
            Positioned(
              left: 10,
              bottom: 10,
              child: Container(
                decoration: BoxDecoration(
                  color: Pallet.darkBlue.withOpacity(0.8),
                  borderRadius: BorderRadius.circular(20),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: AutoSizeText(
                    event.name,
                    style: TextStyle(
                      color: Colors.white,
                    ),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  });
  return btnList;


}
//TODO
//Get request to music circle's api to get newly released global songs
List<Widget> getNewReleasesGlobal(Size size, String userID){
  List<Entity> mostRecentMusic = [];
  List<Widget> btnList = [];
  //Get request for getting most liked local music list.
  //TODO

  //Test list
  Image entityImage = Image.asset(
    'lib/Resources/Images/song1_example_pic.jpg',
    fit: BoxFit.cover,
  );
  for (int i = 0; i < 11; i++) {
    if (i % 3 == 0) {
      AudioFile song = new AudioFile();
      song.name = 'Song ' + i.toString();
      song.songImage = entityImage;
      mostRecentMusic.add(song);
    }
    if (i % 3 == 1) {
      Album album = new Album();
      album.name = 'Album ' + i.toString();
      album.albumImage = entityImage;
      mostRecentMusic.add(album);
    }
  }

  mostRecentMusic.forEach((entity) {
    if(entity.getType() == EntityType.AUDIO_FILE){
      AudioFile song = entity;
      btnList.add(
        Padding(
          padding:const EdgeInsets.fromLTRB(6, 8, 4, 0),
          child: Stack(
            children: [
              ImageButtonSquareRounded(size.width * 0.4, size.width * 0.4, song.name, song.songImage),
              Positioned(
                left: 10,
                bottom: 10,
                child: Container(
                  decoration: BoxDecoration(
                    color: Pallet.darkBlue.withOpacity(0.8),
                    borderRadius: BorderRadius.circular(20),
                  ),
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: AutoSizeText(
                      song.name,
                      style: TextStyle(
                        color: Colors.white,
                      ),
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      );
    }
    else if(entity.getType() == EntityType.ALBUM){
      Album album = entity;
      btnList.add(
        Padding(
          padding:const EdgeInsets.fromLTRB(6, 8, 4, 0),
          child: Stack(
            children: [
              ImageButtonSquareRounded(size.width * 0.4, size.width * 0.4, album.name, album.albumImage),
              Positioned(
                left: 10,
                bottom: 10,
                child: Container(
                  decoration: BoxDecoration(
                    color: Pallet.darkBlue.withOpacity(0.8),
                    borderRadius: BorderRadius.circular(20),
                  ),
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: AutoSizeText(
                      album.name,
                      style: TextStyle(
                        color: Colors.white,
                      ),
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      );
    }
  });
  return btnList;
}
//TODO
//Get request to music circle's api to get top global playlists
List<Widget> getTopPlaylists(Size size, String userID){
  List<Playlist> mostLikedPlaylists = [];
  List<Widget> btnList = [];

  //Get top local artists
  //TODO

  //test list
  Image entityImage = Image.asset(
    'lib/Resources/Images/group2_example_pic.jpg',
    fit: BoxFit.cover,
  );
  for (int i = 0; i < 11; i++) {
    Playlist playlist = new Playlist();
    playlist.name = 'Playlist ' + i.toString();
    playlist.playlistImage = entityImage;
    mostLikedPlaylists.add(playlist);
  }

  mostLikedPlaylists.forEach((playlist) {
    btnList.add(
      Padding(
        padding:const EdgeInsets.fromLTRB(6, 8, 4, 0),
        child: Stack(
          children: [
            ImageButtonSquareRounded(size.width * 0.4, size.width * 0.4, playlist.name, playlist.playlistImage),
            Positioned(
              left: 10,
              bottom: 10,
              child: Container(
                decoration: BoxDecoration(
                  color: Pallet.darkBlue.withOpacity(0.8),
                  borderRadius: BorderRadius.circular(20),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: AutoSizeText(
                    playlist.name,
                    style: TextStyle(
                      color: Colors.white,
                    ),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  });
  return btnList;
}
//TODO
//Get request to music circle's api to get top global albums
List<Widget> getTopAlbumsGlobal(Size size){
  List<Album> topAlbums = [];
  List<Widget> btnList = [];

  //Get top global albums
  //TODO

  //test list
  Image entityImage = Image.asset(
    'lib/Resources/Images/album3_example_pic.jpg',
    fit: BoxFit.cover,
  );

  for (int i = 0; i < 30; i++) {
    Album album = new Album();
    album.name = 'Album ' + i.toString();
    album.albumImage = entityImage;
    topAlbums.add(album);
  }

  topAlbums.forEach((album) {
    btnList.add(
      Padding(
        padding:const EdgeInsets.fromLTRB(6, 8, 4, 0),
        child: Stack(
          children: [
            ImageButtonSquareRounded(size.width * 0.4, size.width * 0.4, album.name, album.albumImage),
            Positioned(
              left: 10,
              bottom: 10,
              child: Container(
                decoration: BoxDecoration(
                  color: Pallet.darkBlue.withOpacity(0.8),
                  borderRadius: BorderRadius.circular(20),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: AutoSizeText(
                    album.name,
                    style: TextStyle(
                      color: Colors.white,
                    ),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  });
  return btnList;
}
//TODO
//Get request to music circle's api to get most active groups
List<Widget> getGrowingGroups(Size size) {
  List<Group> localGroups = [];
  List<Widget> btnList = [];
  //Get request for getting most liked local music list.
  //TODO

  //Test list
  Image entityImage = Image.asset(
    'lib/Resources/Images/group1_example_pic.jpg',
    fit: BoxFit.cover,
  );
  for (int i = 0; i < 11; i++) {
    Group group = new Group();
    group.name = 'Group ' + i.toString();
    group.groupImage = entityImage;
    if (i % 2 == 0) {
      group.freeToJoin = true;
    }
    else {
      group.freeToJoin = false;
    }
    localGroups.add(group);
  }

  localGroups.forEach((group) {
    btnList.add(
      Padding(
        padding: const EdgeInsets.fromLTRB(6, 8, 4, 0),
        child: Stack(
          children: [
            ImageButtonSquareRounded(
                size.width * 0.6, size.width * 0.4, group.name,
                group.groupImage),
            Positioned(
              left: 10,
              bottom: 10,
              child: Container(
                decoration: BoxDecoration(
                  color: Pallet.darkBlue.withOpacity(0.8),
                  borderRadius: BorderRadius.circular(20),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: AutoSizeText(
                    group.name,
                    style: TextStyle(
                      color: Colors.white,
                    ),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  });
  return btnList;
}