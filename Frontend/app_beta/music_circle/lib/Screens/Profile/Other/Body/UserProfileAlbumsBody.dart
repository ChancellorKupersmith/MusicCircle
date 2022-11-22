import 'package:flutter/material.dart';
import 'package:music_circle/Entities/AudioFile.dart';
import 'package:music_circle/Entities/Entity.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Profile/Music/AlbumsPage.dart';
import 'package:music_circle/Screens/Profile/Music/SongListPage.dart';
import 'package:music_circle/UI_Tools/ImageButtons.dart';
import 'package:music_circle/Entities/Album.dart';

class UserProfileAlbumsBody extends StatelessWidget {
  const UserProfileAlbumsBody({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return ListView(
      shrinkWrap: true,
      primary: false,
      children: [
        Stack(
          children: [
            Material(
              color: Colors.white.withOpacity(0.8),
              borderRadius: BorderRadius.only(
                topLeft: Radius.circular(40),
                topRight: Radius.circular(10),
              ),
              child: Container(
                height: size.height - size.height * 0.2,
                decoration: BoxDecoration(
                  color: Pallet.lightBlue.withOpacity(0.3),
                  borderRadius: BorderRadius.only(
                    topLeft: Radius.circular(40),
                    topRight: Radius.circular(10),
                  ),
                ),
              ),
            ),
            Positioned(
              top: 1,
              left: 8,
              child: Padding(
                padding: const EdgeInsets.fromLTRB(8.0, 0.0, 0.0, 0.0),
                child: Text('Albums',
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 30,
                  ),
                ),
              ),
            ),
            Positioned(top: size.height * 0.05, left: 0, child: UserProfileAlbumList())
          ],
        ),
      ],
    );
  }
}

class UserProfileAlbumListInherited extends InheritedWidget{
  final _UserProfileAlbumListState state;

  UserProfileAlbumListInherited({@required this.state, @required Widget child}) : super(child: child);

  @override
  bool updateShouldNotify(covariant UserProfileAlbumListInherited oldWidget) {
    return true;
  }

  static UserProfileAlbumListInherited of(BuildContext context) =>
      context.dependOnInheritedWidgetOfExactType<UserProfileAlbumListInherited>();

}

class UserProfileAlbumList extends StatefulWidget {

  const UserProfileAlbumList({Key key}) : super(key: key);

  @override
  _UserProfileAlbumListState createState() => _UserProfileAlbumListState();
}

class _UserProfileAlbumListState extends State<UserProfileAlbumList> {
  List<Album> albums = [];
  void addAlbums(Album album){
    setState(() {
      albums.add(album);
    });
  }
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    //print('AlbumListState Context:' + context.toString());
    List<Widget> userAlbums = getUsersAlbums('userID', size);
    // print('AlbumListState1 Context:' + context.toString());
    // print('this: ' + this.toString());
    return UserProfileAlbumListInherited(
      state: this,
      child: SizedBox(
        width: size.width,
        height: size.height - size.height * 0.2,
        child: PageView.builder(
          itemBuilder: (context, index){
            return userAlbums[index];
          },
          itemCount: userAlbums.length,
        ),
      ),
    );
  }
  //TODO: get user albums from database.
  List<Widget> getUsersAlbums(String userID, Size size){
    List<Widget> colList = [];
    List<Widget> grid = [];
    //test albums
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
        }
        else{
          song.songImage = Image.asset(
            'lib/Resources/Images/user3_example_pic.jpg',
            fit: BoxFit.cover,
          );
        }

        album.songs.add(song);
      }
      addAlbums(album);
    }
    Widget col;
    for(int i=0; i<albums.length; i++){
      // print('getUserAlbum Context:' + context.toString());
      grid.add(
        SquareAlbumButton(albums: albums, size: size, albumName: albums[i].name, pageIndex: i, albumArt: ImageButtonSquareRounded(size.height * 0.2, size.width * 0.4, albums[i].name, albums[i].albumImage)),
      );
      if(grid.length == 6 || i == albums.length - 1){
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

class SquareAlbumButton extends StatelessWidget {
  final String albumName;
  final Widget albumArt;
  final int pageIndex;
  final List<Album> albums;
  final Size size;
  const SquareAlbumButton({Key key,this.albums, @required this.size, @required this.albumName, @required this.albumArt, @required this.pageIndex}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Padding(
        padding: const EdgeInsets.fromLTRB(0,8,0,8),
        child: InkWell(
          onTap: (){
            // print('SquareAlbumButton Context:' + context.toString());
            Navigator.push(
              context,
              MaterialPageRoute(builder: (context) =>
                  SongListPage(type: EntityType.ALBUM, index: pageIndex),
              ),
            );
          },
          child: Stack(
            children: [
              albumArt,
              Positioned(
                left: 10,
                bottom: size.height * 0.005,
                child: Material(
                  color: Colors.blueGrey.withOpacity(0.7),
                  borderRadius: BorderRadius.circular(15),
                  child: Padding(
                    padding: const EdgeInsets.fromLTRB(8,2,8,2),
                    child: Text(
                      albumName,
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

