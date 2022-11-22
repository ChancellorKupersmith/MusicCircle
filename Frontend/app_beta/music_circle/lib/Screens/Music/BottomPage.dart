import 'dart:collection';

import 'package:auto_size_text/auto_size_text.dart';
import 'package:double_linked_list/double_linked_list.dart';
import 'package:flutter/material.dart';
import 'package:flutter_xlider/flutter_xlider.dart';
import 'package:music_circle/Entities/AudioFile.dart';
import 'package:music_circle/Entities/Playlist.dart';
import 'package:music_circle/Entities/User.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Music/MusicPlayerPage.dart';
import 'package:music_circle/UI_Tools/SocialMediaWidgets.dart';
import 'package:music_circle/Entities/Comment.dart';

class MusicPlayerBottom extends StatefulWidget {
  final AudioFile song;

  const MusicPlayerBottom({Key key, @required this.song}) : super(key: key);

  @override
  _MusicPlayerBottomState createState() => _MusicPlayerBottomState();
}

class _MusicPlayerBottomState extends State<MusicPlayerBottom> {
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    double borderRadius = 30;
    final PageController controller = PageController(initialPage: MusicPlayerMainInherited.of(context).state.bottomPageIndex,);
    return SizedBox(
      height: size.height * 0.475,
      width: size.width,
      child: Container(
        decoration: BoxDecoration(
          color: Colors.white,
          borderRadius: BorderRadius.only(
            topLeft: Radius.circular(borderRadius),
            topRight: Radius.circular(borderRadius),
          ),
        ),
        child: Material(
           // color: Colors.blueGrey.withOpacity(0.2),
          color: Colors.blueGrey.withOpacity(0.5),
          borderRadius: BorderRadius.only(
            topLeft: Radius.circular(borderRadius),
            topRight: Radius.circular(borderRadius),
          ),
          child: Container(
            decoration: BoxDecoration(
              color: Colors.white.withOpacity(0.99),
              borderRadius: BorderRadius.only(
                topLeft: Radius.circular(borderRadius),
                topRight: Radius.circular(borderRadius),
              ),
            ),
            child: SizedBox(
              height: size.height * 0.475,
              width: size.width,
              child: Column(
                children: [
                  getSongInfo(context, size, controller),
                  Expanded(
                    child: PageView(
                      controller: controller,
                      children: [
                        MusicPlayerCommentsView(),
                        MusicPlayerMediaControlsView(),
                        MusicPlayerTrackListView(trackList: MusicPlayerMainInherited.of(context).state.queue.songs,),
                        MusicPlayerPlaylistView(),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }

  getSongInfo(BuildContext context, Size size, PageController controller){
    AudioFile song = MusicPlayerMainInherited.of(context).state.queue.songs[
      MusicPlayerMainInherited.of(context).state.currentSongIndex
    ];
    return Row(
      children: [
        //Song Info
        Expanded(
          child: Padding(
            padding: const EdgeInsets.fromLTRB(20, 30, 0, 0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                AutoSizeText(
                  song.name,
                  style: TextStyle(
                    fontSize: 25,
                    color: Pallet.darkBlue.withOpacity(0.8),
                    fontWeight: FontWeight.bold,
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.fromLTRB(0,6,0,0),
                  child: InkWell(
                    onTap: (){

                    },
                    borderRadius: BorderRadius.circular(50),
                    child: AutoSizeText(
                      song.artist.name,
                      style: TextStyle(
                        fontSize: 17,
                        color: Pallet.darkBlue.withOpacity(0.6),
                        // fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
        //Like button
        IconButton(
          onPressed: (){
            //TODO: like song function
          },
          icon: Icon(
            Icons.favorite_outline_rounded,
            color: Pallet.darkBlue.withOpacity(0.8),
            size: 28,
          ),
        ),
        //Add to playlist button
        Padding(
          padding: const EdgeInsets.fromLTRB(0, 0, 8, 0),
          child: IconButton(
            onPressed: (){
              //TODO: add to playlist function
              controller.jumpToPage(3);
            },
            icon: Icon(
              Icons.add,
              color: Pallet.darkBlue.withOpacity(0.8),
              size: 28,
            ),
          ),
        ),
      ],
    );
  }
}

class MusicPlayerMediaControlsView extends StatefulWidget {
  const MusicPlayerMediaControlsView({Key key}) : super(key: key);

  @override
  _MusicPlayerMediaControlsViewState createState() => _MusicPlayerMediaControlsViewState();
}
class _MusicPlayerMediaControlsViewState extends State<MusicPlayerMediaControlsView> {
  bool isPlaying = true;
  void setIsPlaying(bool isPlaying){
    if(this.isPlaying != isPlaying){
      setState(() {
        this.isPlaying = isPlaying;
      });
    }
  }
  int repeatMode = 0;
  void setRepeatMode(int mode){
    if(repeatMode != mode){
      setState(() {
        repeatMode = mode;
      });
    }
  }
  bool shuffle = false;
  void setShuffle(bool shuffle){
    if(this.shuffle != shuffle){
      setState(() {
        this.shuffle = shuffle;
      });
    }
  }
  double songDurationIndex = 0;
  void setSongDurationIndex(double index){
    if(songDurationIndex != index){
      setState(() {
        songDurationIndex = index;
      });
    }
  }
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Column(
      children: [
        Padding(
          padding: const EdgeInsets.fromLTRB(0,20,0,0),
          child: getSongDurationSlideBar(),
        ),
        Padding(
          padding: const EdgeInsets.fromLTRB(0,30,0,0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              getRepeatModeBtn(size),
              getPrevBtn(size),
              getPlayPauseBtn(size),
              getNextBtn(size),
              getShuffleBtn(size),
            ],
          ),
        )
      ],
    );
  }

  getSongDurationSlideBar(){
    return FlutterSlider(
      values: [songDurationIndex],
      min: 0.0,
      max: 1.0,
      axis: Axis.horizontal,
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
        setSongDurationIndex(lowerValue);
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
    );
  }
  getRepeatModeBtn(Size size){
    return Container(
      height: size.width * 0.125,
      width: size.width * 0.125,
      decoration: BoxDecoration(
        color: Colors.white,
        shape: BoxShape.circle,
        boxShadow: [
          BoxShadow(
            color: Colors.blueGrey.withOpacity(0.5),
            offset: Offset(5, 10),
            spreadRadius: 3,
            blurRadius: 10,
          ),
          BoxShadow(
            color: Colors.white.withOpacity(0.99),
            offset: Offset(-3, -4),
            spreadRadius: -2,
            blurRadius: 20,
          ),
        ],
      ),
      child: Container(
        height: size.width * 0.125,
        width: size.width * 0.125,
        decoration: BoxDecoration(
          //color: Colors.blueGrey.withOpacity(0.2),
          color: Colors.blueGrey.withOpacity(0.2),
          shape: BoxShape.circle,
        ),
        child: Container(
          height: size.width * 0.125,
          width: size.width * 0.125,
          decoration: BoxDecoration(
            //color: Colors.blueGrey.withOpacity(0.2),
            color: Colors.white.withOpacity(0.775),
            shape: BoxShape.circle,
          ),
          child: InkWell(
            customBorder: CircleBorder(),
            onTap: (){
              if(repeatMode == 2){
                setRepeatMode(0);
              }
              else{
                setRepeatMode(repeatMode + 1);
              }
            },
            child: Stack(
              children: [
                Center(
                  child: Container(
                    margin: EdgeInsets.all(5),
                    decoration: BoxDecoration(
                      color: getActiveRingColor(repeatMode != 0),
                      shape: BoxShape.circle,
                    ),
                  ),
                ),
                Center(
                  child: Container(
                    margin: EdgeInsets.all(7),
                    decoration: BoxDecoration(
                      color: Colors.white,
                      shape: BoxShape.circle,
                    ),
                  ),
                ),
                Center(
                  child: Container(
                    margin: EdgeInsets.all(7),
                    decoration: BoxDecoration(
                      color: Colors.blueGrey.withOpacity(0.2),
                      shape: BoxShape.circle,
                    ),
                    child: Container(
                      decoration: BoxDecoration(
                        color: Colors.white.withOpacity(0.775),
                        shape: BoxShape.circle,
                      ),
                      child: Center(
                        child: getRepeatIcon(),
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
  getPlayPauseBtn(Size size){
    return Container(
      height: size.width * 0.2,
      width: size.width * 0.2,
      decoration: BoxDecoration(
        color: Colors.white,
        shape: BoxShape.circle,
        boxShadow: [
          BoxShadow(
            color: Colors.blueGrey.withOpacity(0.5),
            offset: Offset(5, 10),
            spreadRadius: 3,
            blurRadius: 10,
          ),
          BoxShadow(
            color: Colors.white.withOpacity(0.99),
            offset: Offset(-3, -4),
            spreadRadius: -2,
            blurRadius: 20,
          ),
        ],
      ),
      child: Container(
        height: size.width * 0.2,
        width: size.width * 0.2,
        decoration: BoxDecoration(
          color: Colors.blueGrey.withOpacity(0.2),
          shape: BoxShape.circle,
        ),
        child: Container(
          height: size.width * 0.2,
          width: size.width * 0.2,
          decoration: BoxDecoration(
            color: Colors.white.withOpacity(0.775),
            shape: BoxShape.circle,
          ),
          child: InkWell(
            customBorder: CircleBorder(),
            onTap: (){
              //TODO: play/pause song function
              setIsPlaying(!isPlaying);
            },
            child: Stack(
              children: [
                Center(
                  child: Container(
                    margin: EdgeInsets.all(5),
                    decoration: BoxDecoration(
                      color: Pallet.darkBlue.withOpacity(0.5),
                      shape: BoxShape.circle,
                    ),
                  ),
                ),
                Center(
                  child: Container(
                    margin: EdgeInsets.all(7),
                    decoration: BoxDecoration(
                      color: Colors.white,
                      shape: BoxShape.circle,
                    ),
                  ),
                ),
                Center(
                  child: Container(
                    margin: EdgeInsets.all(7),
                    decoration: BoxDecoration(
                      color: Colors.blueGrey.withOpacity(0.2),
                      shape: BoxShape.circle,
                    ),
                    child: Container(
                      decoration: BoxDecoration(
                        color: Colors.white.withOpacity(0.775),
                        shape: BoxShape.circle,
                      ),
                      child: Center(
                        child: getPlayPauseIcon(),
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
  getPrevBtn(Size size){
    return Container(
      height: size.width * 0.16,
      width: size.width * 0.16,
      decoration: BoxDecoration(
        color: Colors.white,
        shape: BoxShape.circle,
        boxShadow: [
          BoxShadow(
            color: Colors.blueGrey.withOpacity(0.5),
            offset: Offset(5, 10),
            spreadRadius: 3,
            blurRadius: 10,
          ),
          BoxShadow(
            color: Colors.white.withOpacity(0.99),
            offset: Offset(-3, -4),
            spreadRadius: -2,
            blurRadius: 20,
          ),
        ],
      ),
      child: Container(
        height: size.width * 0.16,
        width: size.width * 0.16,
        decoration: BoxDecoration(
          color: Colors.blueGrey.withOpacity(0.2),
          shape: BoxShape.circle,
        ),
        child: Container(
          height: size.width * 0.16,
          width: size.width * 0.16,
          decoration: BoxDecoration(
            color: Colors.white.withOpacity(0.775),
            shape: BoxShape.circle,
          ),
          child: InkWell(
            customBorder: CircleBorder(),
            onTap: (){
              //TODO: previous song function
              if(MusicPlayerMainInherited.of(context).state.currentSongIndex != 0){
                MusicPlayerMainInherited.of(context).state.setCurrentSongIndex(
                  MusicPlayerMainInherited.of(context).state.currentSongIndex - 1
                );
              }
            },
            onLongPress: (){
              //TODO: rewind seek function
            },
            child: Stack(
              children: [
                Center(
                  child: Container(
                    margin: EdgeInsets.all(5),
                    decoration: BoxDecoration(
                      color: getActiveRingColor(
                          MusicPlayerMainInherited.of(context).state.currentSongIndex != 0
                      ),
                      shape: BoxShape.circle,
                    ),
                  ),
                ),
                Center(
                  child: Container(
                    margin: EdgeInsets.all(7),
                    decoration: BoxDecoration(
                      color: Colors.white,
                      shape: BoxShape.circle,
                    ),
                  ),
                ),
                Center(
                  child: Container(
                    margin: EdgeInsets.all(7),
                    decoration: BoxDecoration(
                      color: Colors.blueGrey.withOpacity(0.2),
                      shape: BoxShape.circle,
                    ),
                    child: Container(
                      decoration: BoxDecoration(
                        color: Colors.white.withOpacity(0.775),
                        shape: BoxShape.circle,
                      ),
                      child: Center(
                        child: Icon(
                          Icons.skip_previous_rounded,
                          color: getActiveIconColor(
                            MusicPlayerMainInherited.of(context).state.currentSongIndex != 0
                          ),
                        ),
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
  getNextBtn(Size size){
    return Container(
      height: size.width * 0.16,
      width: size.width * 0.16,
      decoration: BoxDecoration(
        color: Colors.white,
        shape: BoxShape.circle,
        boxShadow: [
          BoxShadow(
            color: Colors.blueGrey.withOpacity(0.5),
            offset: Offset(5, 10),
            spreadRadius: 3,
            blurRadius: 10,
          ),
          BoxShadow(
            color: Colors.white.withOpacity(0.99),
            offset: Offset(-3, -4),
            spreadRadius: -2,
            blurRadius: 20,
          ),
        ],
      ),
      child: Container(
        height: size.width * 0.16,
        width: size.width * 0.16,
        decoration: BoxDecoration(
          color: Colors.blueGrey.withOpacity(0.2),
          shape: BoxShape.circle,
        ),
        child: Container(
          height: size.width * 0.16,
          width: size.width * 0.16,
          decoration: BoxDecoration(
            color: Colors.white.withOpacity(0.775),
            shape: BoxShape.circle,
          ),
          child: InkWell(
            customBorder: CircleBorder(),
            onTap: (){
              //TODO: previous song function
              if(MusicPlayerMainInherited.of(context).state.currentSongIndex != MusicPlayerMainInherited.of(context).state.queue.songs.length - 1){
                MusicPlayerMainInherited.of(context).state.setCurrentSongIndex(
                    MusicPlayerMainInherited.of(context).state.currentSongIndex + 1
                );
              }
            },
            onLongPress: (){
              //TODO: rewind seek function
            },
            child: Stack(
              children: [
                Center(
                  child: Container(
                    margin: EdgeInsets.all(5),
                    decoration: BoxDecoration(
                      color: getActiveRingColor(
                          MusicPlayerMainInherited.of(context).state.currentSongIndex != MusicPlayerMainInherited.of(context).state.queue.songs.length - 1
                      ),
                      shape: BoxShape.circle,
                    ),
                  ),
                ),
                Center(
                  child: Container(
                    margin: EdgeInsets.all(7),
                    decoration: BoxDecoration(
                      color: Colors.white,
                      shape: BoxShape.circle,
                    ),
                  ),
                ),
                Center(
                  child: Container(
                    margin: EdgeInsets.all(7),
                    decoration: BoxDecoration(
                      color: Colors.blueGrey.withOpacity(0.2),
                      shape: BoxShape.circle,
                    ),
                    child: Container(
                      decoration: BoxDecoration(
                        color: Colors.white.withOpacity(0.775),
                        shape: BoxShape.circle,
                      ),
                      child: Center(
                        child: Icon(
                          Icons.skip_next_rounded,
                          color: getActiveIconColor(
                              MusicPlayerMainInherited.of(context).state.currentSongIndex != MusicPlayerMainInherited.of(context).state.queue.songs.length - 1
                          ),
                        ),
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
  getShuffleBtn(Size size){
    return Container(
      height: size.width * 0.125,
      width: size.width * 0.125,
      decoration: BoxDecoration(
        color: Colors.white,
        shape: BoxShape.circle,
        boxShadow: [
          BoxShadow(
            color: Colors.blueGrey.withOpacity(0.5),
            offset: Offset(5, 10),
            spreadRadius: 3,
            blurRadius: 10,
          ),
          BoxShadow(
            color: Colors.white.withOpacity(0.99),
            offset: Offset(-3, -4),
            spreadRadius: -2,
            blurRadius: 20,
          ),
        ],
      ),
      child: Container(
        height: size.width * 0.125,
        width: size.width * 0.125,
        decoration: BoxDecoration(
          color: Colors.blueGrey.withOpacity(0.2),
          shape: BoxShape.circle,
        ),
        child: Container(
          decoration: BoxDecoration(
            color: Colors.white.withOpacity(0.775),
            shape: BoxShape.circle,
          ),
          child: InkWell(
            customBorder: CircleBorder(),
            onTap: (){
              setShuffle(!shuffle);
            },
            child: Stack(
              children: [
                Center(
                  child: Container(
                    margin: EdgeInsets.all(5),
                    decoration: BoxDecoration(
                      color: getActiveRingColor(shuffle),
                      shape: BoxShape.circle,
                    ),
                  ),
                ),
                Center(
                  child: Container(
                    margin: EdgeInsets.all(7),
                    decoration: BoxDecoration(
                      color: Colors.white,
                      shape: BoxShape.circle,
                    ),
                  ),
                ),
                Center(
                  child: Container(
                    margin: EdgeInsets.all(7),
                    decoration: BoxDecoration(
                      color: Colors.blueGrey.withOpacity(0.2),
                      shape: BoxShape.circle,
                    ),
                    child: Container(
                      decoration: BoxDecoration(
                        color: Colors.white.withOpacity(0.775),
                        shape: BoxShape.circle,
                      ),
                      child: Center(
                        child: Icon(
                          Icons.shuffle_rounded,
                          color: getActiveIconColor(shuffle),
                        ),
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  getActiveRingColor(bool active){
    if(active){
      return Pallet.darkBlue.withOpacity(0.5);
    }
    else{
      return Colors.transparent;
    }
  }
  getActiveIconColor(bool active){
    if(active){
      return Pallet.darkBlue.withOpacity(0.7);
    }
    else{
      return Colors.blueGrey.withOpacity(0.4);
    }
  }
  getRepeatIcon(){
    if(repeatMode != 2){
      return Icon(
        Icons.repeat_rounded,
        color: getActiveIconColor(repeatMode != 0),
      );
    }
    else{
      return Icon(
        Icons.repeat_one_rounded,
        color: Pallet.darkBlue.withOpacity(0.7),
      );
    }
  }
  getPlayPauseIcon(){
    if(isPlaying){
      return Icon(
        Icons.pause,
        color: Pallet.darkBlue.withOpacity(0.7),
      );
    }
    else{
      return Icon(
        Icons.play_arrow_rounded,
        color: Pallet.darkBlue.withOpacity(0.7),
      );
    }
  }
}

class MusicPlayerTrackListView extends StatefulWidget {
  final List<AudioFile> trackList;

  const MusicPlayerTrackListView({Key key, @required this.trackList}) : super(key: key);

  @override
  _MusicPlayerTrackListViewState createState() => _MusicPlayerTrackListViewState();
}
class _MusicPlayerTrackListViewState extends State<MusicPlayerTrackListView> {
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    //List<AudioFile> trackList = MusicPlayerMainInherited.of(context).state.queue.songs;
    //print(widget.trackList);
    //List<AudioFile> trackList = widget.trackList;
    return MusicPlayerTrackListInherited(
      state: this,
      child: SingleChildScrollView(
        child: Stack(
          children:[
            //visible draggable list
            Column(
              children: getDraggableList(context, size, widget.trackList),
            ),
            //non visible drag target list
            Column(
              children: getDragTargetList(context, size, widget.trackList.length),
            ),
          ]
        ),
      ),
    );
  }

  getDraggableList(BuildContext context, Size size, List<AudioFile> trackList){
    List<Widget> dragList = [];

    for(int i = 0; i<trackList.length; i++){
      if(i == MusicPlayerMainInherited.of(context).state.currentSongIndex){
        dragList.add(
          Container(
            height: size.height * 0.1,
            width: size.width,
            decoration: BoxDecoration(
              color: Colors.transparent,
            ),
            child: Row(
              children: [
                Expanded(
                  child: Padding(
                    padding: const EdgeInsets.all(20),
                    child: AutoSizeText(
                      trackList[i].name,
                      style: TextStyle(
                        color: Colors.blueGrey.withOpacity(0.8),
                      ),
                    ),
                  ),
                ),
                Expanded(
                  child: Padding(
                    padding: const EdgeInsets.all(12.0),
                    child: AutoSizeText(
                      trackList[i].artist.name,
                      textAlign: TextAlign.right,
                      style: TextStyle(
                        color: Colors.blueGrey.withOpacity(0.8),
                      ),
                    ),
                  ),
                ),
              ],
            ),
          )
        );
      }
      else{
        dragList.add(
          LongPressDraggable<AudioFile>(
            data: trackList[i],
            maxSimultaneousDrags: 1,
            child: InkWell(
              onTap: (){
                MusicPlayerMainInherited.of(context).state.setCurrentSongIndex(i);
              },
              child: Container(
                height: size.height * 0.1,
                width: size.width,
                decoration: BoxDecoration(
                  color: Colors.transparent,
                ),
                child: Row(
                  children: [
                    Expanded(
                      child: Padding(
                        padding: const EdgeInsets.all(12.0),
                        child: AutoSizeText(
                          trackList[i].name,
                          style: TextStyle(
                            color: Pallet.darkBlue,
                          ),
                        ),
                      ),
                    ),
                    Expanded(
                      child: Padding(
                        padding: const EdgeInsets.all(12.0),
                        child: AutoSizeText(
                          trackList[i].artist.name,
                          textAlign: TextAlign.right,
                          style: TextStyle(
                            color: Pallet.darkBlue,
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
            feedback: Container(
              height: size.height * 0.1,
              width: size.width,
              decoration: BoxDecoration(
                color: Colors.transparent,
              ),
              child: Row(
                children: [
                  Expanded(
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: AutoSizeText(
                        trackList[i].name,
                        maxFontSize: 14,
                        style: TextStyle(
                          decoration: TextDecoration.none,
                          color: Pallet.darkBlue,
                        ),
                      ),
                    ),
                  ),
                  Expanded(
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: AutoSizeText(
                        trackList[i].artist.name,
                        maxFontSize: 14,
                        textAlign: TextAlign.right,
                        style: TextStyle(
                          decoration: TextDecoration.none,
                          color: Pallet.darkBlue,
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ),
            childWhenDragging: Container(),
          ),
        );
      }
    }

    return dragList;
  }

  getDragTargetList(BuildContext context, Size size, int length){
    List<Widget> targetList = [];
    for(int i=0; i<length; i++){
      if(i == MusicPlayerMainInherited.of(context).state.currentSongIndex){
        targetList.add(Container(height: size.height * 0.1, width: size.width,));
      }
      else{
        targetList.add(InvisibleDragTarget(index: i));
      }
    }
    return targetList;
  }
}
class MusicPlayerTrackListInherited extends InheritedWidget{
  final _MusicPlayerTrackListViewState state;

  MusicPlayerTrackListInherited({@required this.state, Widget child}) : super(child: child);

  @override
  bool updateShouldNotify(covariant MusicPlayerTrackListInherited oldWidget) {
    return true;
  }

  static MusicPlayerTrackListInherited of(BuildContext context) =>
      context.dependOnInheritedWidgetOfExactType<MusicPlayerTrackListInherited>();

}

class InvisibleDragTarget extends StatelessWidget {
  final int index;
  const InvisibleDragTarget({@required this.index, Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return DragTarget<AudioFile>(
      builder: (BuildContext context, List<Object> candidateData, List<dynamic> rejectedData) {
        return Container(
          height: size.height * 0.1,
          width: size.width,
        );
      },
      onWillAccept: (data) => true,
      onAccept: (data){
          Playlist newQueue = new Playlist();
          List<AudioFile> newTrackList = MusicPlayerTrackListInherited.of(context).state.widget.trackList;
          newTrackList.remove(data);
          newTrackList.insert(index, data);
          newQueue.songs = newTrackList;

          MusicPlayerMainInherited.of(context).state.updateQueue(newQueue);
          //TODO: fix current song index bug
          MusicPlayerTrackListInherited.of(context).state.setState(() {

          });
      },
    );
  }
}

class MusicPlayerPlaylistView extends StatefulWidget {
  const MusicPlayerPlaylistView({Key key}) : super(key: key);

  @override
  _MusicPlayerPlaylistViewState createState() => _MusicPlayerPlaylistViewState();
}
class _MusicPlayerPlaylistViewState extends State<MusicPlayerPlaylistView> {
  List<Playlist> userPlaylists;
  void updatePlaylists(List<Playlist> playlists){
    if(userPlaylists != playlists){
      setState(() {
        userPlaylists = playlists;
      });
    }
  }
  @override
  Widget build(BuildContext context) {
    if(userPlaylists == null){
      getUserPlaylists();
    }
    return ListView(
      shrinkWrap: true,
      primary: false,
      children: getPlaylistWidgetList(),
    );
  }

  //TODO: get logged in user's playlists from database
  getUserPlaylists(){
    userPlaylists = [];
    //test playlists
    for(int i=0; i<15; i++){
      Playlist playlist = new Playlist();
      playlist.name = 'Playlist ' + i.toString();
      userPlaylists.add(playlist);
    }
  }
  getPlaylistWidgetList(){
    List<Widget> widgetList = [];
    Size size = MediaQuery.of(context).size;

    userPlaylists.forEach((playlist) {
      widgetList.add(
          SizedBox(
            height: size.height * 0.1,
            width: size.width,
            child: Row(
                  children: [
                    Expanded(
                      child: Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: AutoSizeText(
                          playlist.name,
                          style: TextStyle(

                          ),
                        ),
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: InkWell(
                        onTap: (){
                          //If queue isn't one of the playlist
                          setState(() {
                            if(playlist.songs.contains(
                                MusicPlayerMainInherited.of(context).state.queue.songs[
                                MusicPlayerMainInherited.of(context).state.currentSongIndex
                                ]
                            )){
                              playlist.songs.remove(
                                  MusicPlayerMainInherited.of(context).state.queue.songs[
                                  MusicPlayerMainInherited.of(context).state.currentSongIndex
                                  ]
                              );
                            }
                            else{
                              playlist.songs.add(
                                  MusicPlayerMainInherited.of(context).state.queue.songs[
                                  MusicPlayerMainInherited.of(context).state.currentSongIndex
                                  ]
                              );
                            }

                          });
                        },
                        child: Container(
                          height: size.width * 0.05,
                          width: size.width * 0.05,
                          decoration: BoxDecoration(
                              shape: BoxShape.circle,
                              color: Colors.transparent,
                              border: Border.all(
                                color: Pallet.darkBlue,

                              )
                          ),
                          child: Container(
                            margin: EdgeInsets.all(1),
                            decoration: BoxDecoration(
                              shape: BoxShape.circle,
                              color: isInPlaylist(context, playlist),
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

    return widgetList;
  }
  Color isInPlaylist(BuildContext context, Playlist playlist){
    Color color;
    if(playlist.songs.contains(
        MusicPlayerMainInherited.of(context).state.queue.songs[
          MusicPlayerMainInherited.of(context).state.currentSongIndex])){
      color = Pallet.darkBlue;
    }
    else{
      color = Colors.transparent;
    }
    return color;
  }
}

class MusicPlayerCommentsView extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return CommentsWidget(
        parent: MusicPlayerMainInherited.of(context).state.queue.songs[
          MusicPlayerMainInherited.of(context).state.currentSongIndex
        ]
    );
  }

}


