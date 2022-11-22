import 'package:flutter/material.dart';
import 'package:music_circle/Entities/Album.dart';
import 'package:music_circle/Entities/AudioFile.dart';
import 'package:music_circle/Entities/Entity.dart';
import 'package:music_circle/Entities/Playlist.dart';
import 'package:music_circle/Entities/User.dart';
import 'package:music_circle/Screens/Profile/Music/AlbumsPage.dart';
import 'package:music_circle/Screens/Profile/Music/PlaylistsPage.dart';
import 'package:music_circle/Screens/Profile/Music/SongsPage.dart';

class SongListPage extends StatefulWidget {
  final EntityType type;
  final int index;
  final List<Playlist> playlists;
  final List<Album> albums;
  static final String routeName = '/song-list-page';
  const SongListPage({@required this.type, @required this.index, this.playlists, this.albums, Key key}) : super(key: key);

  @override
  _SongListPageState createState() => _SongListPageState();
}

class _SongListPageState extends State<SongListPage> {
  int index;
  void setIndex(int index){
    if(this.index != index){
      setState(() {
        this.index = index;
      });
    }
  }
  Playlist songList;
  void setSongList(Playlist songList){
    if(this.songList != songList){
      setState(() {
        this.songList = songList;
      });
    }
  }
  List<Playlist> playlists;
  void setPlaylists(List<Playlist> playlists){
    if(this.playlists != playlists){
      setState(() {
        this.playlists = playlists;
      });
    }
  }
  List<Album> albums;
  void setAlbums(List<Album> albums){
    if(this.albums != albums){
      setState(() {
        this.albums = albums;
      });
    }
  }
  bool isStopped = true;
  void setIsStopped(bool isStopped){
    if(this.isStopped != isStopped){
      setState(() {
        this.isStopped = isStopped;
      });
    }
  }
  int currentSongIndex = 0;
  void setCurrentSongIndex(int index){
    if(currentSongIndex != index){
      setState(() {
        currentSongIndex = index;
      });
    }
  }
  @override
  Widget build(BuildContext context) {
    if(index == null){
      index = widget.index;
    }
    return SongListPageInherited(
        state: this,
        child: navigateTo(),
    );
  }

  Widget navigateTo(){
    switch(widget.type){
      case EntityType.AUDIO_FILE:{
        if(playlists == null){
          playlists = getUserSongsPlaylist();
        }
        return UserSongsPage();
      }
      case EntityType.PLAYLIST:{
        if(playlists == null){
          playlists = getUserCreatedPlaylists();
        }
        return UserPlaylistsPage();
      }
      case EntityType.ALBUM:{
        if(albums == null){
          albums = getUserAlbums();
        }
        return UserAlbumsPage();
      }
      default:{
        return Container();
      }
    }
  }
}

class SongListPageInherited extends InheritedWidget{
  final _SongListPageState state;

  SongListPageInherited({@required this.state, @required Widget child}) : super(child: child);

  @override
  bool updateShouldNotify(covariant SongListPageInherited oldWidget) {
    return true;
  }

  static SongListPageInherited of(BuildContext context) =>
      context.dependOnInheritedWidgetOfExactType<SongListPageInherited>();

}

//TODO: Get "user's songs" playlists from database
List<Playlist> getUserSongsPlaylist(){
  List<Playlist> userSongs;
  //Test user songs playlist list
  Playlist allSongs = new Playlist();
  allSongs.name = 'All Songs';
  Playlist topSongs = new Playlist();
  topSongs.name = 'Top Songs';
  Playlist recentSongs = new Playlist();
  recentSongs.name = 'Most Recent Songs';
  Playlist mostLikedSongs = new Playlist();
  mostLikedSongs.name = 'Most Liked Songs';
  Playlist myLikes = new Playlist();
  myLikes.name = 'My Likes';
  Playlist listeningHistory = new Playlist();
  listeningHistory.name = 'Listening History';
  userSongs = [allSongs, myLikes, topSongs, recentSongs, mostLikedSongs, listeningHistory];

  userSongs.forEach((playlist) {
    for(int i=0; i<30; i++){
      Image songPic;
      User artist = new User();
      switch(playlist.name){
        case 'All Songs':{
          if(i%5 == 0){
            songPic = Image.asset(
              'lib/Resources/Images/user3_example_pic.jpg',
              fit: BoxFit.cover,
            );
            artist.name = 'Test User';
            artist.userImage = Image.asset(
              'lib/Resources/Images/user1_example_pic.jpg',
              fit: BoxFit.cover,
            );
          }
          else if(i%5 == 1){
            songPic = Image.asset(
              'lib/Resources/Images/user5_example_pic.jpg',
              fit: BoxFit.cover,
            );
            artist.name = 'Test User';
            artist.userImage = Image.asset(
              'lib/Resources/Images/user1_example_pic.jpg',
              fit: BoxFit.cover,
            );
          }
          else if(i%5 == 2){
            songPic = Image.asset(
              'lib/Resources/Images/song1_example_pic.jpg',
              fit: BoxFit.cover,
            );
            artist.name = 'Test User';
            artist.userImage = Image.asset(
              'lib/Resources/Images/user1_example_pic.jpg',
              fit: BoxFit.cover,
            );
          }
          else if(i%5 == 3){
            songPic = Image.asset(
              'lib/Resources/Images/group1_example_pic.jpg',
              fit: BoxFit.cover,
            );
            artist.name = 'Artist ' + i.toString();
            artist.userImage = Image.asset(
              'lib/Resources/Images/user5_example_pic.jpg',
              fit: BoxFit.cover,
            );
          }
          else{
            songPic = Image.asset(
              'lib/Resources/Images/album2_example_pic.jpg',
              fit: BoxFit.cover,
            );
            artist.name = 'Artist ' + i.toString();
            artist.userImage = Image.asset(
              'lib/Resources/Images/user5_example_pic.jpg',
              fit: BoxFit.cover,
            );
          }
        }
        break;
        case 'Top Songs':{
          songPic = Image.asset(
            'lib/Resources/Images/user3_example_pic.jpg',
            fit: BoxFit.cover,
          );
          artist.name = 'Test User';
          artist.userImage = Image.asset(
            'lib/Resources/Images/user1_example_pic.jpg',
            fit: BoxFit.cover,
          );
        }
        break;
        case 'Most Recent Songs':{
          songPic = Image.asset(
            'lib/Resources/Images/user5_example_pic.jpg',
            fit: BoxFit.cover,
          );
          artist.name = 'Test User';
          artist.userImage = Image.asset(
            'lib/Resources/Images/user1_example_pic.jpg',
            fit: BoxFit.cover,
          );
        }
        break;
        case 'Most Liked Songs':{
          songPic = Image.asset(
            'lib/Resources/Images/song1_example_pic.jpg',
            fit: BoxFit.cover,
          );
          artist.name = 'Test User';
          artist.userImage = Image.asset(
            'lib/Resources/Images/user1_example_pic.jpg',
            fit: BoxFit.cover,
          );
        }
        break;
        case 'My Likes':{
          songPic = Image.asset(
            'lib/Resources/Images/group1_example_pic.jpg',
            fit: BoxFit.cover,
          );
          artist.name = 'Artist ' + i.toString();
          artist.userImage = Image.asset(
            'lib/Resources/Images/user5_example_pic.jpg',
            fit: BoxFit.cover,
          );
        }
        break;
        case 'Listening History':{
          songPic = Image.asset(
            'lib/Resources/Images/album2_example_pic.jpg',
            fit: BoxFit.cover,
          );
          artist.name = 'Artist ' + i.toString();
          artist.userImage = Image.asset(
            'lib/Resources/Images/user5_example_pic.jpg',
            fit: BoxFit.cover,
          );
        }
        break;
        default:{

        }
      }

      AudioFile song = new AudioFile();
      song.name = 'Song ' + i.toString();
      song.songImage  = songPic;
      song.artist = artist;
      playlist.songs.add(song);
      Album album = new Album();
      album.name = 'This songs album';
      song.album = album;
    }
  });

  return userSongs;
}
//TODO: Get user's playlists from database
List<Playlist> getUserCreatedPlaylists(){
  List<Playlist> playlists = [];
  //Test playlists
  for(int i=0; i<15; i++) {
    Playlist playlist = new Playlist();
    playlist.name = 'Playlist ' + i.toString();
    if(i%5 == 0){
      playlist.playlistImage = Image.asset(
        'lib/Resources/Images/song2_example_pic.jpg',
        fit: BoxFit.cover,
      );
    }
    else if(i%5 == 1){
      playlist.playlistImage = Image.asset(
        'lib/Resources/Images/album3_example_pic.jpg',
        fit: BoxFit.cover,
      );
    }
    else if(i%5 == 2){
      playlist.playlistImage = Image.asset(
        'lib/Resources/Images/user1_example_pic.jpg',
        fit: BoxFit.cover,
      );
    }
    else if(i%5 == 3){
      playlist.playlistImage = Image.asset(
        'lib/Resources/Images/group2_example_pic.jpg',
        fit: BoxFit.cover,
      );
    }
    else{
      playlist.playlistImage = Image.asset(
        'lib/Resources/Images/user3_example_pic.jpg',
        fit: BoxFit.cover,
      );
    }
    for (int j = 0; j < 20; j++) {
      AudioFile song = new AudioFile();
      song.name = 'Song ' + j.toString();
      Album album = new Album();
      album.name = 'This songs album';
      song.album = album;
      if (i == 0) {
        song.songImage = Image.asset(
          'lib/Resources/Images/user2_example_pic.jpg',
          fit: BoxFit.cover,
        );
        User artist = new User();
        artist.name = 'Song Artist';
        artist.userImage = Image.asset(
          'lib/Resources/Images/user4_example_pic.jpg',
          fit: BoxFit.cover,
        );
        song.artist = artist;
      }
      else {
        song.songImage = Image.asset(
          'lib/Resources/Images/song1_example_pic.jpg',
          fit: BoxFit.cover,
        );
        User artist = new User();
        artist.name = 'Song Artist';
        artist.userImage = Image.asset(
          'lib/Resources/Images/user5_example_pic.jpg',
          fit: BoxFit.cover,
        );
        song.artist = artist;
      }
      playlist.songs.add(song);
    }
    playlists.add(playlist);
  }
  return playlists;
}
//TODO: Get user's albums from database
List<Album> getUserAlbums(){
  List<Album> albums = [];
  //Test albums
  for(int i=0; i<15; i++){
    Album album = new Album();
    album.name = 'Album ' + i.toString();
    album.albumImage = Image.asset(
      'lib/Resources/Images/album1_example_pic.jpg',
      fit: BoxFit.cover,
    );
    for(int j=0; j<20; j++){
      AudioFile song = new AudioFile();
      song.name = 'Song ' + j.toString();
      if(i==0){
        song.songImage = Image.asset(
          'lib/Resources/Images/user2_example_pic.jpg',
          fit: BoxFit.cover,
        );
        User artist = new User();
        artist.name = 'Song Artist';
        artist.userImage = Image.asset(
          'lib/Resources/Images/user4_example_pic.jpg',
          fit: BoxFit.cover,
        );
        song.artist = artist;
      }
      else {
        song.songImage = Image.asset(
          'lib/Resources/Images/song1_example_pic.jpg',
          fit: BoxFit.cover,
        );
        User artist = new User();
        artist.name = 'Song Artist';
        artist.userImage = Image.asset(
          'lib/Resources/Images/user5_example_pic.jpg',
          fit: BoxFit.cover,
        );
        song.artist = artist;
      }
      song.album = album;
      album.songs.add(song);
    }
    albums.add(album);
  }

  return albums;
}