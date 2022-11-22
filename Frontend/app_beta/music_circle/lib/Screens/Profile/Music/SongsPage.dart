import 'package:flutter/material.dart';
import 'package:music_circle/Entities/AudioFile.dart';
import 'package:music_circle/Entities/Playlist.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Profile/Music/SongListPage.dart';
import 'package:music_circle/UI_Tools/ImageButtons.dart';
import 'package:music_circle/UI_Tools/ListWidgets/MusicEntityList.dart';

import 'SongListMenus.dart';

class UserSongsPage extends StatelessWidget {
  const UserSongsPage({Key key}) : super(key: key);
  @override
  Widget build(BuildContext context) {
      int bodyIndex;
      if(bodyIndex == null) {
        bodyIndex = SongListPageInherited.of(context).state.index;
      }
      return Scaffold(
        backgroundColor: Colors.white,
        body: Stack(
          children: [
            Positioned(top: 0, left: 0, child: SongsSideMenu()),
            Positioned(top: kToolbarHeight * 0.15 + 10, left: 2.5, child: BackButton(color: Pallet.darkBlue,)),
            Positioned(top: 0, right: 0, child: SongsPageBody(index: bodyIndex,),),
            Positioned(bottom: 0, right: 0, child: BottomMenu(),),
          ],
        ),
      );
  }
}

class SongsPageBody extends StatelessWidget {
  final int index;
  const SongsPageBody({Key key, @required this.index}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return displayBody(context, size);
  }


  Widget displayBody(BuildContext context, Size size){
    VerticalSongList songList;
    switch(index){
      case 0:{
        songList = VerticalSongList(songsList: getAllUserUploads(context, size, 'userID'));
      }
      break;
      case 1:{
        songList = VerticalSongList(songsList: getMyLikesUserUploads(context, size, 'userID'));
      }
      break;
      case 2:{
        songList = VerticalSongList(songsList: getTopUserUploads(context, size, 'userID'));
      }
      break;
      case 3:{
        songList = VerticalSongList(songsList: getRecentUserUploads(context, size, 'userID'));
      }
      break;
      case 4:{
        songList = VerticalSongList(songsList: getMostLikedUserUploads(context, size, 'userID'));
      }
      break;
      case 5:{
        songList = VerticalSongList(songsList: getUserListeningHistory(context, size, 'userID'));
      }
      break;
      default:{
        songList = VerticalSongList(songsList: []);
      }
    }
    return songList;
  }

  //TODO: Get user uploaded songs from database
  List<ImageButtonSquareRounded> getAllUserUploads(BuildContext context, Size size, String userID){
    List<ImageButtonSquareRounded> btnList = [];
    List<AudioFile> songs = SongListPageInherited.of(context).state.playlists[0].songs;

    songs.forEach((song) {
      btnList.add(ImageButtonSquareRounded(size.height * 0.23, size.width * 0.5, song.name, song.songImage));
    });
    return btnList;
  }

  //TODO: Get user uploaded songs liked by logged in user from database
  List<ImageButtonSquareRounded> getMyLikesUserUploads(BuildContext context, Size size, String userID){
    List<ImageButtonSquareRounded> btnList = [];
    List<AudioFile> songs = SongListPageInherited.of(context).state.playlists[4].songs;

    songs.forEach((song) {
      btnList.add(ImageButtonSquareRounded(size.height * 0.23, size.width * 0.5, song.name, song.songImage));
    });

    return btnList;
  }

  List<ImageButtonSquareRounded> getTopUserUploads(BuildContext context, Size size, String userID){
    List<ImageButtonSquareRounded> btnList = [];
    List<AudioFile> songs = SongListPageInherited.of(context).state.playlists[1].songs;

    songs.forEach((song) {
      btnList.add(ImageButtonSquareRounded(size.height * 0.23, size.width * 0.5, song.name, song.songImage));
    });

    return btnList;
  }

  //TODO: Get user most recent uploaded songs from database
  List<ImageButtonSquareRounded> getRecentUserUploads(BuildContext context, Size size, String userID){
    List<ImageButtonSquareRounded> btnList = [];
    List<AudioFile> songs = SongListPageInherited.of(context).state.playlists[2].songs;

    songs.forEach((song) {
      btnList.add(ImageButtonSquareRounded(size.height * 0.23, size.width * 0.5, song.name, song.songImage));
    });

    return btnList;
  }

  //TODO: Get user most liked uploaded songs from database
  List<ImageButtonSquareRounded> getMostLikedUserUploads(BuildContext context, Size size, String userID){
    List<ImageButtonSquareRounded> btnList = [];
    List<AudioFile> songs = SongListPageInherited.of(context).state.playlists[3].songs;
    songs.forEach((song) {
      btnList.add(ImageButtonSquareRounded(size.height * 0.23, size.width * 0.5, song.name, song.songImage));
    });

    return btnList;
  }

  //TODO: Get user listening history from database
  List<ImageButtonSquareRounded> getUserListeningHistory(BuildContext context, Size size, String userID){
    List<ImageButtonSquareRounded> btnList = [];
    List<AudioFile> songs = SongListPageInherited.of(context).state.playlists[5].songs;
    songs.forEach((song) {
      btnList.add(ImageButtonSquareRounded(size.height * 0.23, size.width * 0.5, song.name, song.songImage));
    });

    return btnList;
  }
}
