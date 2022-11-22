import 'package:flutter/material.dart';
import 'package:music_circle/Entities/Playlist.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Profile/Music/SongListPage.dart';
import 'package:music_circle/UI_Tools/ImageButtons.dart';
import 'package:music_circle/UI_Tools/ListWidgets/MusicEntityList.dart';
import 'SongListMenus.dart';


class UserPlaylistsPage extends StatelessWidget {
  const UserPlaylistsPage({Key key}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    List<Playlist> playlists = SongListPageInherited.of(context).state.playlists;
    int bodyIndex = SongListPageInherited.of(context).state.index;

    return Scaffold(
      backgroundColor: Colors.white,
      body: Stack(
        children: [
          Positioned(top: 0, left: 0, child: PlaylistsSideMenu(playlists: playlists,)),
          Positioned(
            top: kToolbarHeight * 0.15 + 12,
            left: 2.5,
            child: Container(
              height: size.height * 0.05,
              width: size.width * 0.325,
              decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.only(
                  bottomLeft: Radius.circular(10),
                )
              ),
              child: Row(
                children: [
                  BackButton(color: Pallet.darkBlue,),
                  Expanded(child: Container()),
                ],
              )),
          ),
          Positioned(top: 0, right: 0, child: PlaylistsPageBody(playlistIndex: bodyIndex)),
          Positioned(bottom: 0, right: 0, child: BottomMenu()),
        ],
      ),
    );
  }
}

class PlaylistsPageBody extends StatelessWidget {
  final int playlistIndex;
  const PlaylistsPageBody({Key key, this.playlistIndex}) : super(key: key);

  @override
  Widget build(BuildContext context) {

    Size size = MediaQuery.of(context).size;
    return displayPlaylistTrackList(size, context);
  }

  Widget displayPlaylistTrackList(Size size, BuildContext context){
    List<ImageButtonSquareRounded> btnList = [];
    SongListPageInherited.of(context).state.playlists[
      SongListPageInherited.of(context).state.index
    ].songs.forEach((song) {
      btnList.add(ImageButtonSquareRounded(size.height * 0.23, size.width * 0.5, song.name, song.songImage));
    });
    return VerticalSongList(songsList: btnList);
  }
}
