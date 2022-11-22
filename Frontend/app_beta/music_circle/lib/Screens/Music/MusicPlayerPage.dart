import 'package:flutter/material.dart';
import 'package:music_circle/Entities/Playlist.dart';
import 'package:music_circle/Screens/Music/BottomPage.dart';
import 'package:music_circle/Screens/Music/TopPage.dart';

class MusicPlayerMain extends StatefulWidget {
  final Playlist startingPlaylist;
  const MusicPlayerMain({Key key, @required this.startingPlaylist}) : super(key: key);

  @override
  _MusicPlayerMainState createState() => _MusicPlayerMainState();
}

class _MusicPlayerMainState extends State<MusicPlayerMain> {
  int bottomPageIndex;
  void setBottomPageIndex(int index){
    if(bottomPageIndex != index){
      setState(() {
        bottomPageIndex = index;
      });
    }
  }
  Playlist queue;
  void updateQueue(Playlist newQueue){
    if(newQueue.songs != queue.songs){
      setState(() {
        queue.songs = newQueue.songs;
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
    Size size = MediaQuery.of(context).size;
    if(queue == null){
      queue = widget.startingPlaylist;
      bottomPageIndex = 1;
    }
    return MusicPlayerMainInherited(
      state: this,
      child: Scaffold(
        backgroundColor: Colors.white,
        body: SizedBox(
          height: size.height,
          width: size.width,
          child: Stack(
            children: [
              Positioned(
                child: MusicPlayerTop(song: queue.songs[currentSongIndex],),
              ),
              Positioned(
                bottom: 0,
                child: MusicPlayerBottom(song: queue.songs[currentSongIndex],),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class MusicPlayerMainInherited extends InheritedWidget{
  final _MusicPlayerMainState state;

  MusicPlayerMainInherited({@required this.state, Widget child}) : super(child: child);

  @override
  bool updateShouldNotify(covariant MusicPlayerMainInherited oldWidget) {
    return true;
  }

  static MusicPlayerMainInherited of(BuildContext context) =>
      context.dependOnInheritedWidgetOfExactType<MusicPlayerMainInherited>();
}

