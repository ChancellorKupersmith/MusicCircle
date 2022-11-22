import 'dart:math';
import 'package:auto_size_text/auto_size_text.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_xlider/flutter_xlider.dart';
import 'package:music_circle/Entities/Entity.dart';
import 'package:music_circle/UI_Tools/SocialMediaWidgets.dart';
import 'package:volume_control/volume_control.dart';
import 'package:music_circle/Entities/Album.dart';
import 'package:music_circle/Entities/Playlist.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Profile/Other/Body/UserProfileAlbumsBody.dart';
import 'package:music_circle/UI_Tools/ImageButtons.dart';

import 'AlbumsPage.dart';
import 'PlaylistsPage.dart';
import 'SongListPage.dart';
import 'SongsPage.dart';


class SongsSideMenu extends StatelessWidget {
  const SongsSideMenu({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    //final int index = UserSongsPageInherited.of(context).state.bodyIndex;

    Size size = MediaQuery.of(context).size;
    return SizedBox(
      height: size.height,
      width: size.width * 1/3,
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Expanded(
            child: SizedBox(
              height: size.height,
              child: Stack(
                children: [
                  Container(
                    height: size.height * 0.7,
                    child: Padding(
                      padding: const EdgeInsets.fromLTRB(0,60,0,0),
                      child: Column(
                        children: [
                          Expanded(
                              child: SongsMenuTextButton(index: 0, text: 'All',)
                          ),
                          Expanded(
                              child: SongsMenuTextButton(index: 1, text: 'My Likes',)
                          ),
                          Expanded(
                              child: SongsMenuTextButton(index: 2, text: 'Top Songs',)
                          ),
                          Expanded(
                              child: SongsMenuTextButton(index: 3, text: 'Recent')
                          ),
                          Expanded(
                              child: SongsMenuTextButton(index: 4, text: 'Most Liked',)
                          ),
                          Expanded(
                              child: SongsMenuTextButton(index: 5, text: 'Listening History',)
                          ),
                        ],
                      ),
                    ),
                  ),
                  Positioned(
                    bottom: 15,
                    child: Container(
                      height: size.height * 0.3,
                      width: size.width * 0.325,
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(360),
                      ),
                      child: Stack(
                        children: [
                          Positioned(
                            bottom: 10,
                            left: 0,
                            right: 0,
                            child: PlayPauseSongListMenuBtn(isPlaying: false),
                          ),
                          Positioned(
                            bottom: 10,
                            left: 0,
                            child: PrevSongListMenuBtn(hasPrevSong: false),
                          ),
                          Positioned(
                            bottom: 10,
                            right: 0,
                            child: NextSongListMenuBtn(hasNextSong: false),
                          ),
                          SizedBox(
                            height: size.height * 0.25,
                            child: VolumeSongListMenuSlider(),
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),
          Container(
            height: size.height,
            width: 1,
            color: Colors.blueGrey.withOpacity(0.2),
          ),
        ],
      ),
    );
  }

}
class SongsMenuTextButton extends StatelessWidget {
  final int index;
  final String text;
  const SongsMenuTextButton({Key key, this.index, this.text}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final Size size = MediaQuery.of(context).size;
    return TextButton(
      onPressed: (){
        SongListPageInherited.of(context).state.setIndex(index);
      },
      child: Container(
        width: size.width,
        height: size.height,
        decoration: BoxDecoration(
          border: Border(
            top: BorderSide(
              color: Colors.blueGrey.withOpacity(0.2),
              width: 0.5,
            ),
            bottom: BorderSide(
              color: Colors.blueGrey.withOpacity(0.2),
              width: 1,
            ),
          ),
        ),
        child: Center(
          child: Text(
            text,
            textAlign: TextAlign.center,
            style: TextStyle(
              color: getMenuColor(index, context),
              fontWeight: FontWeight.w500,
              fontSize: 20,
            ),
          ),
        ),
      ),
    );
  }
  Color getMenuColor(int index, BuildContext context){
    Color color = Colors.blueGrey.withOpacity(0.4);
    if(index == SongListPageInherited.of(context).state.index){
      color = Pallet.darkBlue;
    }
    return color;
  }
}

class AlbumsSideMenu extends StatefulWidget {
  final List<Album> albums;
  const AlbumsSideMenu({Key key, this.albums}) : super(key: key);

  @override
  _AlbumsSideMenuState createState() => _AlbumsSideMenuState();
}
class _AlbumsSideMenuState extends State<AlbumsSideMenu> {
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;

    return AlbumsSideMenuInherited(
      state: this,
      child: SizedBox(
        height: size.height,
        width: size.width * 1/3,
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Expanded(
              child: SizedBox(
                height: size.height,
                child: Stack(
                  children: [
                    Container(
                      height: size.height * 0.7,
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(20),
                      ),
                      child: Padding(
                        padding: const EdgeInsets.fromLTRB(0,35,0,0),
                        child: ListView(
                          shrinkWrap: true,
                          children: getAlbumButtons(widget.albums),
                        ),
                      ),
                    ),
                    Positioned(
                      bottom: 15,
                      child: Container(
                        height: size.height * 0.3,
                        width: size.width * 0.325,
                        decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(360),
                        ),
                        child: Stack(
                          children: [
                            Positioned(
                              bottom: 10,
                              left: 0,
                              right: 0,
                              child: PlayPauseSongListMenuBtn(isPlaying: false),
                            ),
                            Positioned(
                              bottom: 10,
                              left: 0,
                              child: PrevSongListMenuBtn(hasPrevSong: false),
                            ),
                            Positioned(
                              bottom: 10,
                              right: 0,
                              child: NextSongListMenuBtn(hasNextSong: false),
                            ),
                            SizedBox(
                              height: size.height * 0.25,
                              child: VolumeSongListMenuSlider(),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
            Container(
              height: size.height,
              width: 1,
              color: Colors.blueGrey.withOpacity(0.2),
            ),

          ],
        ),
      ),
    );
  }

  List<Widget> getAlbumButtons(List<Album> albums) {
    List<Widget> btnList = [];
    for (int i = 0; i < albums.length; i++) {
      btnList.add(AlbumMenuIconButton(albumIndex: i, album: albums[i]));
    }

    return btnList;
  }
}
class AlbumMenuIconButton extends StatelessWidget {
  final int albumIndex;
  final Album album;
  const AlbumMenuIconButton({Key key, @required this.albumIndex, @required this.album}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Padding(
      padding: const EdgeInsets.fromLTRB(8,0,8,16),
      child: InkWell(
        onTap: (){
          SongListPageInherited.of(context).state.setIndex(albumIndex);
        },
        child: ImageButtonSquareNotSoRounded(size.height * 0.075, size.width * 0.14, album.name, album.albumImage),
      ),
    );
  }
}
class AlbumsSideMenuInherited extends InheritedWidget{
  final _AlbumsSideMenuState state;

  AlbumsSideMenuInherited({@required this.state, @required Widget child}) : super(child: child);

  @override
  bool updateShouldNotify(covariant AlbumsSideMenuInherited oldWidget) {
    return true;
  }

  static AlbumsSideMenuInherited of(BuildContext context) =>
    context.dependOnInheritedWidgetOfExactType<AlbumsSideMenuInherited>();

}

class PlaylistsSideMenu extends StatefulWidget {
  final List<Playlist> playlists;
  const PlaylistsSideMenu({Key key, this.playlists}) : super(key: key);

  @override
  _PlaylistsSideMenuState createState() => _PlaylistsSideMenuState();
}
class _PlaylistsSideMenuState extends State<PlaylistsSideMenu> {
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;

    return PlaylistsSideMenuInherited(
      state: this,
      child: SizedBox(
        height: size.height,
        width: size.width * 1/3,
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Expanded(
              child: SizedBox(
                height: size.height,
                child: Stack(
                  children: [
                    Container(
                      height: size.height * 0.7,
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(20),
                      ),
                      child: Padding(
                        padding: const EdgeInsets.fromLTRB(0,35,0,0),
                        child: ListView(
                          shrinkWrap: true,
                          children: getPlaylistButtons(widget.playlists),
                        ),
                      ),
                    ),
                    Positioned(
                      bottom: 15,
                      child: Container(
                        height: size.height * 0.3,
                        width: size.width * 0.325,
                        decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(360),
                        ),
                        child: Stack(
                          children: [
                            Positioned(
                              bottom: 10,
                              left: 0,
                              right: 0,
                              child: PlayPauseSongListMenuBtn(isPlaying: false),
                            ),
                            Positioned(
                              bottom: 10,
                              left: 0,
                              child: PrevSongListMenuBtn(hasPrevSong: false),
                            ),
                            Positioned(
                              bottom: 10,
                              right: 0,
                              child: NextSongListMenuBtn(hasNextSong: false),
                            ),
                            SizedBox(
                              height: size.height * 0.25,
                              child: VolumeSongListMenuSlider(),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
            Container(
              height: size.height,
              width: 1,
              color: Colors.blueGrey.withOpacity(0.2),
            ),

          ],
        ),
      ),
    );
  }

  List<Widget> getPlaylistButtons(List<Playlist> playlists){
    List<Widget> btnList = [];
    for (int i = 0; i < playlists.length; i++) {
      btnList.add(PlaylistMenuIconButton(playlistIndex: i, playlist: playlists[i]));
    }
    return btnList;
  }
}
class PlaylistMenuIconButton extends StatelessWidget {
  final int playlistIndex;
  final Playlist playlist;
  const PlaylistMenuIconButton({Key key, @required this.playlistIndex, @required this.playlist}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Padding(
      padding: const EdgeInsets.fromLTRB(8,0,8,16),
      child: InkWell(
        onTap: (){
          SongListPageInherited.of(context).state.setIndex(playlistIndex);
        },
        child: ImageButtonSquareNotSoRounded(size.height * 0.075, size.width * 0.14, playlist.name, playlist.playlistImage),
      ),
    );
  }
}
class PlaylistsSideMenuInherited extends InheritedWidget{
  final _PlaylistsSideMenuState state;

  PlaylistsSideMenuInherited({@required this.state, Widget child}) : super(child: child);

  @override
  bool updateShouldNotify(covariant PlaylistsSideMenuInherited oldWidget) {
    return true;
  }

  static PlaylistsSideMenuInherited of(BuildContext context) =>
    context.dependOnInheritedWidgetOfExactType<PlaylistsSideMenuInherited>();
}

class BottomMenu extends StatelessWidget {
  const BottomMenu({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return getBottomMenu(context, size);
  }

  Widget getBottomMenu(BuildContext context, Size size){
    if(SongListPageInherited.of(context).state.isStopped){
      return getStoppedBottomMenu(context, size);
    }
    else{
      return getPlayingBottomMenu(context, size);
    }
  }

  Widget getPlayingBottomMenu(BuildContext context, Size size){
    return Container(
      height: size.height * 0.15,
      width: size.width * 2/3,

      decoration: BoxDecoration(
          color: Colors.white,
          borderRadius: BorderRadius.only(topRight: Radius.circular(20)),
          boxShadow: [BoxShadow(color: Colors.black.withOpacity(0.15), offset: Offset(3, -1), blurRadius: 4.5, spreadRadius: 2)]
      ),
      child: Row(
        children: [
          Expanded(
            child: Column(
              children: [
                Expanded(
                  child: getPlayingBottomMenuText(context),
                ),
                FlutterSlider(
                  values: [0],
                  min: 0.0,
                  max: 1.0,
                  selectByTap: false,
                  tooltip: FlutterSliderTooltip(
                    disabled: true,
                  ),
                  step: FlutterSliderStep(
                    step: 0.05,
                  ),
                  onDragging: (handlerIndex, lowerValue, upperValue){
                    // print('LowerValue: ' + lowerValue.toString());
                    // print('UpperValue: ' + upperValue.toString());
                  },
                  handler: FlutterSliderHandler(
                      decoration: BoxDecoration(
                        color: Colors.transparent,
                      ),
                      child: Icon(
                        Icons.circle,
                        color: Colors.transparent,
                        size: 14,
                      )
                  ),
                  handlerAnimation: FlutterSliderHandlerAnimation(
                    curve: Curves.elasticOut,
                    reverseCurve: Curves.elasticIn,
                    duration: Duration(microseconds: 500),
                    scale: 1.25,
                  ),
                  trackBar: FlutterSliderTrackBar(

                    inactiveTrackBar: BoxDecoration(
                      borderRadius: BorderRadius.circular(20),
                      color: Colors.blueGrey.withOpacity(0.15),
                    ),
                    activeTrackBar: BoxDecoration(
                      borderRadius: BorderRadius.circular(20),
                      color: Colors.blueGrey.withOpacity(0.8),
                    ),
                  ),
                ),
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.fromLTRB(0,0,4,0),
            child: getPlayingBottomMenuArtist(context, size),
          ),
        ],
      ),
    );
  }
  Widget getStoppedBottomMenu(BuildContext context, Size size){
    return Container(
      height: size.height * 0.15,
      width: size.width * 2/3,

      decoration: BoxDecoration(
          color: Colors.white,
          borderRadius: BorderRadius.only(topRight: Radius.circular(20)),
          boxShadow: [BoxShadow(color: Colors.black.withOpacity(0.15), offset: Offset(3, -1), blurRadius: 4.5, spreadRadius: 2)]
      ),
      child: Row(
        children: [
          Expanded(
            child: Padding(
              padding: const EdgeInsets.fromLTRB(30.0,0,0,0),
              child: Text(
                getStoppedBottomMenuText(context),
                style: TextStyle(
                  color: Colors.blueGrey,
                  fontSize: 16,
                ),
              ),
            ),
          ),
          Expanded(
            child: Material(
              clipBehavior: Clip.hardEdge,
              shape: CircleBorder(),
              child: Image.asset('lib/Resources/Images/user1_example_pic.jpg', height: 50, width: 50,),
            ),
          ),
        ],
      ),
    );
  }

  getStoppedBottomMenuText(BuildContext context){
    if(SongListPageInherited.of(context).state.widget.type == EntityType.ALBUM){
      return SongListPageInherited.of(context).state.albums[
      SongListPageInherited.of(context).state.index
      ].name;
    }
    else{
      return SongListPageInherited.of(context).state.playlists[
      SongListPageInherited.of(context).state.index
      ].name;
    }
  }
  getPlayingBottomMenuText(BuildContext context){
    if(SongListPageInherited.of(context).state.widget.type == EntityType.ALBUM){
      return Column(
        children: [
          Padding(
            padding: const EdgeInsets.fromLTRB(0,10,0,4),
            child: AutoSizeText(
              SongListPageInherited.of(context).state.albums[
              SongListPageInherited.of(context).state.index
              ].songs[
              SongListPageInherited.of(context).state.currentSongIndex
              ].name,
              textAlign: TextAlign.center,
              style: TextStyle(

              ),
            ),
          ),
          AutoSizeText(
            SongListPageInherited.of(context).state.albums[
            SongListPageInherited.of(context).state.index
            ].songs[
            SongListPageInherited.of(context).state.currentSongIndex
            ].album.name,
            textAlign: TextAlign.center,
            style: TextStyle(
              fontSize: 10,
            ),
          ),
        ],
      );
    }
    else{
      return Column(
        children: [
          Padding(
            padding: const EdgeInsets.fromLTRB(0,10,0,4),
            child: AutoSizeText(
              SongListPageInherited.of(context).state.playlists[
              SongListPageInherited.of(context).state.index
              ].songs[
              SongListPageInherited.of(context).state.currentSongIndex
              ].name,
              textAlign: TextAlign.center,
              style: TextStyle(

              ),
            ),
          ),
          AutoSizeText(
            SongListPageInherited.of(context).state.playlists[
            SongListPageInherited.of(context).state.index
            ].songs[
            SongListPageInherited.of(context).state.currentSongIndex
            ].album.name,
            textAlign: TextAlign.center,
            style: TextStyle(
              fontSize: 10,
            ),
          ),
        ],
      );
    }
  }
  getPlayingBottomMenuArtist(BuildContext context, Size size){
    if(SongListPageInherited.of(context).state.widget.type == EntityType.ALBUM){
      return SizedBox(
        height: size.width * 0.16,
        width: size.width * 0.16,
        child: Material(
          clipBehavior: Clip.hardEdge,
          shape: CircleBorder(),
          child: SongListPageInherited.of(context).state.albums[
          SongListPageInherited.of(context).state.index
          ].songs[
          SongListPageInherited.of(context).state.currentSongIndex
          ].artist.userImage,
        ),
      );
    }
    else{
      return SizedBox(
        height: size.width * 0.16,
        width: size.width * 0.16,
        child: Material(
          clipBehavior: Clip.hardEdge,
          shape: CircleBorder(),
          child: SongListPageInherited.of(context).state.playlists[
          SongListPageInherited.of(context).state.index
          ].songs[
          SongListPageInherited.of(context).state.currentSongIndex
          ].artist.userImage,
        ),
      );
    }
  }
}

//MEDIA CONTROLS
class PlayPauseSongListMenuBtn extends StatefulWidget {
  final bool isPlaying;
  const PlayPauseSongListMenuBtn({@required this.isPlaying, Key key}) : super(key: key);

  @override
  _PlayPauseSongListMenuBtnState createState() => _PlayPauseSongListMenuBtnState();
}
class _PlayPauseSongListMenuBtnState extends State<PlayPauseSongListMenuBtn> {
  bool isPlaying;
  void setIsPlaying(bool isPlaying){
    if(this.isPlaying != isPlaying){
      setState(() {
        this.isPlaying = isPlaying;
      });
    }
  }
  @override
  Widget build(BuildContext context) {
    if(isPlaying == null){
      isPlaying = !(SongListPageInherited.of(context).state.isStopped);
    }
    Size size = MediaQuery.of(context).size;
    return Material(
      color: Colors.transparent,
      shape: CircleBorder(),
      child: IconButton(
        onPressed: (){
          SongListPageInherited.of(context).state.setIsStopped(false);
          setIsPlaying(!isPlaying);
        },
        icon: getPlayPauseIcon(size),
        splashColor: Pallet.lightBlue.withOpacity(0.15),
        splashRadius: 25.0,
      ),
    );
  }

  //TODO: Change size according to screen size
  getPlayPauseIcon(Size size){
    if(isPlaying){
      return Icon(
        Icons.pause,
        color: Colors.blueGrey,
      );
    }
    else{
      return Icon(
        Icons.play_arrow_rounded,
        color: Colors.blueGrey,
      );
    }
  }
}

class PrevSongListMenuBtn extends StatefulWidget {
  final bool hasPrevSong;
  const PrevSongListMenuBtn({@required this.hasPrevSong, Key key}) : super(key: key);

  @override
  _PrevSongListMenuBtnState createState() => _PrevSongListMenuBtnState();
}
class _PrevSongListMenuBtnState extends State<PrevSongListMenuBtn> {
  bool hasPrevSong = true;
  void setHasPrevSong(bool hasPrev){
    if(hasPrevSong != hasPrev){
      setState(() {
        hasPrevSong = hasPrev;
      });
    }
  }
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Material(
      color: Colors.transparent,
      shape: CircleBorder(),
      child: IconButton(
        onPressed: (){

        },
        icon: getPrevIconBtn(size),
        splashColor: hasPrevSong ? Pallet.lightBlue.withOpacity(0.15) : Colors.transparent,
        splashRadius: hasPrevSong ? 25.0 : 0.1,
      ),
    );
  }

  //TODO: Change size according to screen size
  getPrevIconBtn(Size size){
    return Icon(
      Icons.skip_previous,
      color: hasPrevSong ? Colors.blueGrey : Colors.blueGrey.withOpacity(0.15),
    );
  }
}

class NextSongListMenuBtn extends StatefulWidget {
  final bool hasNextSong;
  const NextSongListMenuBtn({@required this.hasNextSong, Key key}) : super(key: key);

  @override
  _NextSongListMenuBtnState createState() => _NextSongListMenuBtnState();
}
class _NextSongListMenuBtnState extends State<NextSongListMenuBtn> {
  bool hasNextSong = true;
  void setHasPrevSong(bool hasNext){
    if(hasNextSong != hasNext){
      setState(() {
        hasNextSong = hasNext;
      });
    }
  }
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Material(
      color: Colors.transparent,
      shape: CircleBorder(),
      child: IconButton(
        onPressed: (){

        },
        icon: getPrevIconBtn(size),
        splashColor: hasNextSong ? Pallet.lightBlue.withOpacity(0.15) : Colors.transparent,
        splashRadius: hasNextSong ? 25.0 : 0.1,
      ),
    );
  }

  //TODO: Change size according to screen size
  getPrevIconBtn(Size size){
    return Icon(
      Icons.skip_next,
      color: hasNextSong ? Colors.blueGrey : Colors.blueGrey.withOpacity(0.15),
    );
  }
}

class VolumeSongListMenuSlider extends StatefulWidget {
  const VolumeSongListMenuSlider({Key key}) : super(key: key);

  @override
  _VolumeSongListMenuSliderState createState() => _VolumeSongListMenuSliderState();
}
class _VolumeSongListMenuSliderState extends State<VolumeSongListMenuSlider> {
  double volumeLevel = 0;
  void setVolumeLevel(double level){
    if(volumeLevel != level){
      VolumeControl.setVolume(level);
      setState(() {
        volumeLevel = level;
      });
    }
  }

  @override
  void initState(){
    super.initState();
    initVolumeState();
  }

  @override
  Widget build(BuildContext context) {
    getDeviceVolumeLevel().then((level) => {
      volumeLevel = level
    });
    return FlutterSlider(
      values: [volumeLevel],
      min: 0.0,
      max: 1.0,
      axis: Axis.vertical,
      rtl: true,
      selectByTap: true,
      tooltip: FlutterSliderTooltip(
        disabled: true,
      ),
      step: FlutterSliderStep(
        step: 0.05,
      ),
      onDragging: (handlerIndex, lowerValue, upperValue){
        // print('LowerValue: ' + lowerValue.toString());
        // print('UpperValue: ' + upperValue.toString());
        setVolumeLevel(lowerValue);
      },
      handler: FlutterSliderHandler(
        decoration: BoxDecoration(
          color: Colors.transparent,
        ),
        child: Icon(
          Icons.circle,
          color: Colors.blueGrey,
          size: 14,
        )
      ),
      handlerAnimation: FlutterSliderHandlerAnimation(
        curve: Curves.elasticOut,
        reverseCurve: Curves.elasticIn,
        duration: Duration(microseconds: 500),
        scale: 1.25,
      ),
      trackBar: FlutterSliderTrackBar(

        inactiveTrackBar: BoxDecoration(
          borderRadius: BorderRadius.circular(20),
          color: Colors.blueGrey.withOpacity(0.15),
        ),
        activeTrackBar: BoxDecoration(
          borderRadius: BorderRadius.circular(20),
          color: Colors.blueGrey.withOpacity(0.8),
        ),
      ),
    );
  }

  Future<double> getDeviceVolumeLevel() async {
    return await VolumeControl.volume;
  }

  //init volume_control plugin
  Future<void> initVolumeState() async {
    if (!mounted) return;

    //read the current volume
    volumeLevel = await VolumeControl.volume;
    setState(() {
    });
    print(volumeLevel);
  }

}
