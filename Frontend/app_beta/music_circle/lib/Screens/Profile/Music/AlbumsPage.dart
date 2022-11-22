import 'package:flutter/material.dart';
import 'package:music_circle/Entities/Album.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Profile/Music/SongListMenus.dart';
import 'package:music_circle/Screens/Profile/Music/SongListPage.dart';
import 'package:music_circle/Screens/Profile/Other/Body/UserProfileAlbumsBody.dart';
import 'package:music_circle/UI_Tools/ImageButtons.dart';
import 'package:music_circle/UI_Tools/ListWidgets/MusicEntityList.dart';

class UserAlbumsPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    List<Album> albums = SongListPageInherited.of(context).state.albums;
    int bodyIndex = SongListPageInherited.of(context).state.index;

    return Scaffold(
      backgroundColor: Colors.white,
      body: Stack(
        children: [
          Positioned(top: 0, left: 0, child: AlbumsSideMenu(albums: albums,)),
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
          Positioned(top: 0, right: 0, child: AlbumsPageBody(albumIndex: bodyIndex,)),
          Positioned(bottom: 0, right: 0, child: BottomMenu()),
        ],
      ),
    );
  }
}

class AlbumsPageBody extends StatelessWidget {
  final int albumIndex;

  const AlbumsPageBody({Key key, this.albumIndex}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return displayAlbumTrackList(size, context);
  }

  Widget displayAlbumTrackList(Size size, BuildContext context){
    List<ImageButtonSquareRounded> btnList = [];
    SongListPageInherited.of(context).state.albums[
    SongListPageInherited.of(context).state.index
    ].songs.forEach((song) {
      btnList.add(ImageButtonSquareRounded(size.height * 0.23, size.width * 0.5, song.name, song.songImage));
    });
    return VerticalSongList(songsList: btnList);
  }
}
