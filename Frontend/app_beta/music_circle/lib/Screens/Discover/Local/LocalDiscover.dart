import 'dart:math';

import 'package:auto_size_text/auto_size_text.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:music_circle/Entities/Album.dart';
import 'package:music_circle/Entities/AudioFile.dart';
import 'package:music_circle/Entities/Entity.dart';
import 'package:music_circle/Entities/Event.dart';
import 'package:music_circle/Entities/Group.dart';
import 'package:music_circle/Entities/Playlist.dart';
import 'package:music_circle/Entities/User.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Resources/Strings.dart';
import 'package:music_circle/UI_Tools/ImageButtons.dart';
import 'package:music_circle/UI_Tools/ListWidgets/EventsList.dart';
import 'package:music_circle/UI_Tools/ListWidgets/MusicEntityList.dart';
import 'package:music_circle/UI_Tools/ListWidgets/UserEntityList.dart';

import '../DiscoverPage.dart';

class LocalRecommendations extends StatefulWidget {
  const LocalRecommendations({Key key}) : super(key: key);

  @override
  _LocalRecommendationsState createState() => _LocalRecommendationsState();
}
class _LocalRecommendationsState extends State<LocalRecommendations> {
  @override
  Widget build(BuildContext context) {
    return CustomScrollView(
      slivers: [
        LocalRecommendationsPage(),
      ],
    );
  }
}

class LocalRecommendationsPage extends StatelessWidget {
  const LocalRecommendationsPage({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return SliverPersistentHeader(delegate: LocalRecommendationsPageDelegate(maxHeight: size.height * 2.225));
  }
}
class LocalRecommendationsPageDelegate extends SliverPersistentHeaderDelegate{
  final double maxHeight;

  LocalRecommendationsPageDelegate({@required this.maxHeight});
  @override
  Widget build(BuildContext context, double shrinkOffset, bool overlapsContent) {
    Size size = MediaQuery.of(context).size;
    return Stack(
      children: [
        //Events near
        Positioned(
          top: min(size.height * 0.1, double.maxFinite) - shrinkOffset,
          child: DiscoverPageContainer(
            backgroundColor: Pallet.darkBlue,
            height: size.height * 0.33,
            width: size.width,
            child: EventsNear(),
          ),
        ),
        //Recommended for you
        Positioned(
          top: min(size.height * 0.1 + size.height * 0.3, double.maxFinite) - shrinkOffset,
          child: DiscoverPageContainer(
            backgroundColor: Colors.blueGrey,
            height: size.height * 0.33,
            width: size.width,
            child: SongsListHorizontal(
              header: 'Recommended for You',
              headerColor: Pallet.softBlue,
              songList: getUserTop30(size, 'userID'),
            ),
          ),
        ),
        //Recently listened to
        //TODO: Make this a stateful widget to update while songs are played
        Positioned(
          top: min(size.height * 0.1 + size.height * 0.3 + size.height * 0.25, double.maxFinite) - shrinkOffset,
          child: DiscoverPageContainer(
            backgroundColor: Pallet.softBlue,
            height: size.height * 0.33,
            width: size.width,
            child: SongsListHorizontal(
              header: 'Recently Listened',
              headerColor: Pallet.darkBlue,
              songList: getRecentlyListenedTo(size, 'userID'),
            ),
          ),
        ),
        //Top local artists
        Positioned(
          top: min(size.height * 0.1 + size.height * 0.3 + size.height * 0.25 + size.height * 0.25, double.maxFinite) - shrinkOffset,
          child: DiscoverPageContainer(
            backgroundColor: Pallet.darkBlue,
            height: size.height * 0.33,
            width: size.width,
            child: UserListHorizontal(
              header: 'Top Local Artists',
              headerColor: Pallet.softBlue,
              userList: getTopLocalArtists(size, 'userID'),
            ),
          ),
        ),
        //Most Listened
        Positioned(
          top: min(size.height * 0.1 + size.height * 0.3 + size.height * 0.25 + size.height * 0.25 + size.height * 0.275, double.maxFinite) - shrinkOffset,
          child: DiscoverPageContainer(
            backgroundColor: Pallet.lightBlue,
            height: size.height * 0.33,
            width: size.width,
            child: SongsListHorizontal(
              header: 'Most Listened',
              headerColor: Pallet.softBlue,
              songList: getMostPlaysLocal(size, 'userID'),
            ),
          ),
        ),
        //Most Liked
        Positioned(
          top: min(size.height * 0.1 + size.height * 0.3 + size.height * 0.25 + size.height * 0.25 + size.height * 0.275 + size.height * 0.25, double.maxFinite) - shrinkOffset,
          child: DiscoverPageContainer(
            backgroundColor: Colors.blueGrey,
            height: size.height * 0.33,
            width: size.width,
            child: SongsListHorizontal(
              header: 'Most Liked',
              headerColor: Colors.white.withOpacity(0.8),
              songList: getMostLikedLocal(size, 'userID'),
            ),
          ),
        ),
        //Groups near you
        Positioned(
          top: min(size.height * 0.1 + size.height * 0.3 + size.height * 0.25 + size.height * 0.25 + size.height * 0.275 + size.height * 0.25 + size.height * 0.25, double.maxFinite) - shrinkOffset,
          child: DiscoverPageContainer(
            backgroundColor: Pallet.softBlue,
            height: size.height * 0.33,
            width: size.width,
            child: GroupListHorizontal(
              header: 'Groups Near You',
              headerColor: Pallet.darkBlue,
              groupList: getLocalGroups(size, 'userID'),
            ),
          ),
        ),
        //Top local playlists
        Positioned(
          top: min(size.height * 0.1 + size.height * 0.3 + size.height * 0.25 + size.height * 0.25 + size.height * 0.275 + size.height * 0.25 + size.height * 0.25 + size.height * 0.3, double.maxFinite) - shrinkOffset,
          child: DiscoverPageContainer(
            backgroundColor: Pallet.darkBlue,
            height: size.height * 0.33,
            width: size.width,
            child: SongsListHorizontal(
              header: 'Local Playlists',
              headerColor: Pallet.lightBlue,
              songList: getMostLikedLocalPlaylists(size, 'userID'),
            ),
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
                ),
              ),
              child: Stack(
                children: [
                  Positioned(
                    bottom: 0,
                    left: 8,
                    child: AutoSizeText(
                      'Discover Local.',
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
      ],
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
}

//Widget For displaying events near a given location
//TODO
class EventsNear extends StatefulWidget {
  const EventsNear({Key key}) : super(key: key);

  @override
  _EventsNearState createState() => _EventsNearState();
}
class _EventsNearState extends State<EventsNear> {
  String dropdownValue = 'Me';
  //TODO
  //List<ImageButton> eventsBtnList = [ImageButton('Event 1', Io.File('lib/Resources/Images/event_default_pic.jpg').readAsBytesSync())];
  List<Widget> eventsBtnList;
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    if(eventsBtnList == null){
      eventsBtnList = getNearByEvents(size, dropdownValue);
    }
    return Column(
      children: [
        Row(
          children: [
            Padding(
              padding: const EdgeInsets.fromLTRB(10,8,8,8),
              child: Text(
                'Events Near: ',
                style: TextStyle(
                    fontSize: 22,
                    fontWeight: FontWeight.w600,
                    color: Pallet.lightBlue,
                ),
              ),
            ),
            Padding(
              padding: const EdgeInsets.fromLTRB(0.0,2.5,0.0,0.0),
              child: DropdownButton(
                value: dropdownValue,
                icon: const Icon(
                  Icons.expand_more,
                  color: Pallet.lightBlue,
                ),
                onChanged: (String newLocation){
                  setState(() {
                    dropdownValue = newLocation;
                    eventsBtnList = getNearByEvents(size, newLocation);
                  });
                },
                items: StringsEvent.states.map<DropdownMenuItem<String>>((String value) {
                  return DropdownMenuItem(
                    value: value,
                    child: Text(
                      value,
                      style: TextStyle(
                        fontSize: 19,
                        fontWeight: FontWeight.w500,
                          color: Colors.blueGrey,
                      ),
                    ),
                  );
                }).toList(),
              ),
            )
          ],
        ),
        EventsList(
          eventBtnList: eventsBtnList,
        ),
      ],
    );
  }

  //TODO
  List<Widget> getNearByEvents(Size size, String location){
    List<Event> events = [];
    List<Widget> imageBtnList = [];
    //Get request to pull events by location
    //TODO


    //test list
    for(int i=0; i<11; i++){
      Event event = new Event();
      event.name = 'Event ' + i.toString();
      if(location == 'Me'){
        Image eventImage = Image.asset(
          'lib/Resources/Images/song2_example_pic.jpg',
          fit: BoxFit.cover,
        );
        event.eventImage = eventImage;
      }
      else{
        Image eventImage = Image.asset(
          'lib/Resources/Images/test_event_pic.jpg',
          fit: BoxFit.cover,
        );
        event.eventImage = eventImage;
      }

      //TODO
      // final pic = Io.File('lib/Resources/Images/event_default_pic.jpg').readAsBytesSync();
      // event.image = pic;
      events.add(event);
    }

    events.forEach((event) {
      imageBtnList.add(
        eventButton(size, event)
      );
    });

    //TODO
    //imageBtnList.add(ImageButton(event.name, event.image));

    return imageBtnList;
  }

  eventButton(Size size, Event event){
    return Padding(
      padding: const EdgeInsets.fromLTRB(0, 8, 10, 0),
      child: Stack(
        children: [
          ImageButtonSquareRounded(size.height * 0.4, size.width * 0.4, event.name, event.eventImage),
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
    );
  }
}
//TODO
//Get request to music circle's api to get user's most recently listened to list
List<Widget> getRecentlyListenedTo(Size size, String userID) {
  List<Entity> recentListenedMusic = [];
  List<Widget> btnList = [];
  //Get request for getting recently listened to list.
  //TODO

  //Test list
  Image entityImage = Image.asset(
    'lib/Resources/Images/album3_example_pic.jpg',
    fit: BoxFit.cover,
  );
  for (int i = 0; i < 11; i++) {
    if (i % 3 == 0) {
      AudioFile song = new AudioFile();
      song.name = 'Song ' + i.toString();
      song.songImage = entityImage;
      recentListenedMusic.add(song);
    }
    if (i % 3 == 1) {
      Album album = new Album();
      album.name = 'Album ' + i.toString();
      album.albumImage = entityImage;
      recentListenedMusic.add(album);
    }
    if (i % 3 == 2) {
      Playlist playlist = new Playlist();
      playlist.name = 'Playlist ' + i.toString();
      playlist.playlistImage = entityImage;
      recentListenedMusic.add(playlist);
    }
  }

  recentListenedMusic.forEach((entity) {
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
    else if(entity.getType() == EntityType.PLAYLIST){
      Playlist playlist = entity;
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
    }
  });
  return btnList;
}
//TODO
//Get request to music circle's api to get user's top 30 recommended songs
List<Widget> getUserTop30(Size size, String userID){
  List<AudioFile> top30 = [];
  List<Widget> btnList = [];

  //Get user top 30 recommended songs
  //TODO

  //test list
  Image entityImage = Image.asset(
    'lib/Resources/Images/group1_example_pic.jpg',
    fit: BoxFit.cover,
  );

  for (int i = 0; i < 30; i++) {
    AudioFile song = new AudioFile();
    song.name = 'Song ' + i.toString();
    song.songImage = entityImage;
    top30.add(song);
  }

  top30.forEach((song) {
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
  });
  return btnList;
}
//TODO
//Get request to music circle's api to get top artists in user's area
List<Widget> getTopLocalArtists(Size size, String userID){
  List<User> topLocalArtists = [];
  List<Widget> btnList = [];

  //Get top local artists
  //TODO

  //test list
  Image entityImage = Image.asset(
    'lib/Resources/Images/user2_example_pic.jpg',
    fit: BoxFit.cover,
  );
  for (int i = 0; i < 11; i++) {
    User artist = new User();
    artist.name = 'Artist ' + i.toString();
    artist.userImage = entityImage;
    topLocalArtists.add(artist);
  }

  topLocalArtists.forEach((artist) {
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
//Get request to music circle's api to get most liked music in user's area
List<Widget> getMostLikedLocal(Size size, String userID){
  List<Entity> mostLikedMusic = [];
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
      mostLikedMusic.add(song);
    }
    if (i % 3 == 1) {
      Album album = new Album();
      album.name = 'Album ' + i.toString();
      album.albumImage = entityImage;
      mostLikedMusic.add(album);
    }
    if (i % 3 == 2) {
      Playlist playlist = new Playlist();
      playlist.name = 'Playlist ' + i.toString();
      playlist.playlistImage = entityImage;
      mostLikedMusic.add(playlist);
    }
  }

  mostLikedMusic.forEach((entity) {
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
    else if(entity.getType() == EntityType.PLAYLIST){
      Playlist playlist = entity;
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
    }
  });
  return btnList;
}
//TODO
//Get request to music circle's api to get most active local groups
List<Widget> getLocalGroups(Size size, String userID){
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
    if(i%2 == 0){
      group.freeToJoin = true;
    }
    else{
      group.freeToJoin = false;
    }
    localGroups.add(group);
  }

  localGroups.forEach((group) {
    btnList.add(
      Padding(
        padding:const EdgeInsets.fromLTRB(6, 8, 4, 0),
        child: Stack(
          children: [
            ImageButtonSquareRounded(size.width * 0.6, size.width * 0.4, group.name, group.groupImage),
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
//TODO
//Get request to music circle's api to get most played songs in user's area
List<Widget> getMostPlaysLocal(Size size, String userID){
  List<AudioFile> mostPlays = [];
  List<Widget> btnList = [];

  //Get request for most locally played songs
  //TODO

  //test list
  Image entityImage = Image.asset(
    'lib/Resources/Images/album1_example_pic.jpg',
    fit: BoxFit.cover,
  );

  for (int i = 0; i < 30; i++) {
    AudioFile song = new AudioFile();
    song.name = 'Song ' + i.toString();
    song.songImage = entityImage;
    mostPlays.add(song);
  }

  mostPlays.forEach((song) {
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
  });
  return btnList;
}
//TODO
//Get request to music circle's api to get most liked public playlists in user's area
List<Widget> getMostLikedLocalPlaylists(Size size, String userID){
  List<Playlist> mostLikedPlaylists = [];
  List<Widget> btnList = [];

  //Get top local artists
  //TODO

  //test list
  Image entityImage = Image.asset(
    'lib/Resources/Images/playlist_default_pic.png',
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