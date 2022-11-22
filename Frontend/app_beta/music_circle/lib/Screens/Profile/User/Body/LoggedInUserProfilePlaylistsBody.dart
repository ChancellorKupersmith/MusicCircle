import 'package:flutter/material.dart';
import 'package:music_circle/Entities/Album.dart';
import 'package:music_circle/Entities/AudioFile.dart';
import 'package:music_circle/Entities/Entity.dart';
import 'package:music_circle/Entities/Playlist.dart';
import 'package:music_circle/Entities/User.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Profile/Music/PlaylistsPage.dart';
import 'package:music_circle/Screens/Profile/Music/SongListPage.dart';
import 'package:music_circle/UI_Tools/ImageButtons.dart';

class LoggedInUserProfilePlaylistBody extends StatelessWidget {
  const LoggedInUserProfilePlaylistBody({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return ListView(
      shrinkWrap: true,
      primary: false,
      children: [
        Stack(
          children: [
            //Body background
            Material(
              color: Pallet.darkBlue,
              borderRadius: BorderRadius.only(
                topLeft: Radius.circular(40),
                topRight: Radius.circular(10),
              ),
              child: Container(
                height: size.height - size.height * 0.2,
                decoration: BoxDecoration(
                  color: Pallet.lightBlue.withOpacity(0.2),
                  borderRadius: BorderRadius.only(
                    topLeft: Radius.circular(40),
                    topRight: Radius.circular(10),
                  ),
                ),
              ),
            ),
            //Body title
            Positioned(
              top: 1,
              left: 10,
              child: Padding(
                padding: const EdgeInsets.fromLTRB(10.0, 4.0, 0.0, 0.0),
                child: Text('Playlists',
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 30,
                  ),
                ),
              ),
            ),
            Positioned(top: size.height * 0.05, left: 0, child: LoggedInUserProfilePlaylistList()),
          ],
        )
      ],
    );
  }
}

class LoggedInUserProfilePlaylistListInherited extends InheritedWidget{
  final _LoggedInUserProfilePlaylistListState state;

  LoggedInUserProfilePlaylistListInherited({@required this.state, @required Widget child}) : super(child: child);

  @override
  bool updateShouldNotify(covariant LoggedInUserProfilePlaylistListInherited oldWidget) {
    return true;
  }

  static LoggedInUserProfilePlaylistListInherited of(BuildContext context) =>
      context.dependOnInheritedWidgetOfExactType<LoggedInUserProfilePlaylistListInherited>();

}

class LoggedInUserProfilePlaylistList extends StatefulWidget {
  const LoggedInUserProfilePlaylistList({Key key}) : super(key: key);

  @override
  _LoggedInUserProfilePlaylistListState createState() => _LoggedInUserProfilePlaylistListState();
}

class _LoggedInUserProfilePlaylistListState extends State<LoggedInUserProfilePlaylistList> {
  List<Playlist> playlists = [];
  void addPlaylists(Playlist playlist){
    setState(() {
      playlists.add(playlist);
    });
  }
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    List<Widget> userPlaylists = getUserPlaylists('userID', size);
    return LoggedInUserProfilePlaylistListInherited(
        state: this,
        child: SizedBox(
          width: size.width,
          height: size.height - size.height * 0.2,
          child: PageView.builder(
              itemBuilder: (context, index){
                return userPlaylists[index];
              },
              itemCount: userPlaylists.length,
          ),
        )
    );
  }

  List<Widget> getUserPlaylists(String userID, Size size){
    List<Widget> colList = [];
    List<Widget> grid = [];
    //test playlists
    for(int i=0; i<15; i++){
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
          'lib/Resources/Images/playlist1_example_pic.JPG',
          fit: BoxFit.cover,
        );
      }
      else{
        playlist.playlistImage = Image.asset(
          'lib/Resources/Images/user3_example_pic.jpg',
          fit: BoxFit.cover,
        );
      }
      for(int j=0; j<20; j++){
        AudioFile song = new AudioFile();
        song.name = 'Song ' + j.toString();
        Album album = new Album();
        album.name = 'This songs album';
        song.album = album;
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
        playlist.songs.add(song);
      }
      addPlaylists(playlist);
    }
    Widget col;
    for(int i=0; i<playlists.length; i++){
      grid.add(
        SquarePlaylistButton(playlists: playlists, size: size, playlistName: playlists[i].name, pageIndex: i, playlistArt: ImageButtonSquareRounded(size.height * 0.2, size.width * 0.4, playlists[i].name, playlists[i].playlistImage)),
      );
      if(grid.length == 6 || i == playlists.length - 1){
        col = SizedBox(
          height: size.height - size.height * 0.2,
          child: new Column(
              children: [
                Expanded(
                  child: GridView.count(
                    primary: false,
                    physics: NeverScrollableScrollPhysics(),
                    crossAxisCount: 2,
                    children: grid,
                  ),
                )
              ]
          ),
        );
        colList.add(col);
        grid = [];
      }
    }
    return colList;
  }
}

class SquarePlaylistButton extends StatelessWidget {
  final String playlistName;
  final Widget playlistArt;
  final int pageIndex;
  final List<Playlist> playlists;
  final Size size;
  const SquarePlaylistButton({Key key, @required this.size, @required this.playlistName, @required this.playlistArt, @required this.playlists, @required this.pageIndex}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Padding(
        padding: const EdgeInsets.fromLTRB(0,8.0,0,8),
        child: InkWell(
          onTap: (){
            // print('SquareAlbumButton Context:' + context.toString());
            Navigator.push(
              context,
              MaterialPageRoute(builder: (context) =>
                  SongListPage(type: EntityType.PLAYLIST, index: pageIndex, playlists: playlists,),
              ),
            );
          },
          child: Stack(
            children: [
              playlistArt,
              Positioned(
                left: 10,
                bottom: size.height * 0.005,
                child: Material(
                  color: Pallet.darkBlue.withOpacity(0.7),
                  borderRadius: BorderRadius.circular(15),
                  child: Padding(
                    padding: const EdgeInsets.fromLTRB(8,2,8,2),
                    child: Text(
                      playlistName,
                      style: TextStyle(
                          color: Colors.white,
                          fontWeight: FontWeight.w600,
                          fontSize: 20
                      ),
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
