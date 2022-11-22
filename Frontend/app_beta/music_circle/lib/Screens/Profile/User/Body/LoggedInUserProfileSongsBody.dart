import 'package:flutter/material.dart';
import 'package:music_circle/Entities/Entity.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Profile/Music/SongListPage.dart';
import 'package:music_circle/Screens/Profile/Music/SongsPage.dart';

class LoggedInUserProfileSongsBody extends StatelessWidget {
  const LoggedInUserProfileSongsBody({Key key}) : super(key: key);

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
            //color: Pallet.darkBlue.withOpacity(0.4),
            color: Pallet.darkBlue,
            borderRadius: BorderRadius.only(
              topLeft: Radius.circular(40),
              topRight: Radius.circular(10),
            ),
            child: Container(
              height: size.height * 0.75,
              decoration: BoxDecoration(
                color: Pallet.lightBlue.withOpacity(0.2),
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
                padding: const EdgeInsets.fromLTRB(8.0, 4.0, 0.0, 0.0),
                    child: Text('Songs',
                    style: TextStyle(
                    color: Colors.white,
                    fontSize: 31,
                  ),
                ),
              ),
            ),
            Positioned(
                top: 12,
                right: 4,
                child: InkWell(
                  onTap: (){
                    Navigator.push(
                        context,
                      MaterialPageRoute(builder: (context) =>
                          SongListPage(type: EntityType.AUDIO_FILE, index: 0),
                      )
                    );
                  },
                  child: Row(
                    children: [
                      Text('See all',
                        style: TextStyle(
                          color: Pallet.softBlue
                        ),
                      ),
                      Icon(
                        Icons.arrow_forward_ios_rounded,
                        color: Pallet.softBlue,
                      )
                    ],
                  ),
              ),
            ),
            Positioned(
                top: 70,
                child: LoggedInUserProfileSongCategories()
            ),
          ]
        ),
      ],
    );
  }
}

class LoggedInUserProfileSongCategories extends StatelessWidget {
  const LoggedInUserProfileSongCategories({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return SizedBox(
      width: size.width,
      height: size.height * 0.88 - 70,
      child: Column(
        children: [
          //Top Songs
          WideSongButton(label: 'Top', pic: getTopSongImage('userID'), pageIndex: 2,),
          Expanded(
            child: SizedBox(
              width: size.width,
              height: size.height * 0.88,
              child: GridView.count(
                primary: false,
                physics: NeverScrollableScrollPhysics(),
                crossAxisCount: 2,
                padding: const EdgeInsets.fromLTRB(10,25,10,0),
                crossAxisSpacing: 10,
                mainAxisSpacing: 0,
                children: [
                  //Recent Songs
                  SquareSongButton(label: 'Recent', pic: getRecentUploadSongImage('userID'), pageIndex: 3,),
                  //Most Liked Songs
                  SquareSongButton(label: 'Most Liked', pic: getMostLikedSongImage('userID'), pageIndex: 4,),
                  //My Likes
                  SquareSongButton(label: 'My Likes', pic: getMyLikedSongImage('userID'), pageIndex: 1,),
                  //Listening history
                  SquareSongButton(label: 'Listening History', pic: getMostRecentListenedSongImage('userID'), pageIndex: 5,),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class WideSongButton extends StatelessWidget {
  final Image pic;
  final String label;
  final int pageIndex;
  const WideSongButton({Key key, @required this.label, @required this.pic, @required this.pageIndex}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final Size size = MediaQuery.of(context).size;
    return InkWell(
      onTap: (){
        Navigator.push(
            context,
            MaterialPageRoute(builder: (context) =>
                SongListPage(type: EntityType.AUDIO_FILE, index: pageIndex),
            )
        );

      },
      child: Stack(
        children: [
          Padding(
            padding: const EdgeInsets.fromLTRB(8.0,0,8,0),
            child: Container(
              width: size.width,
              height: size.height * 0.2,
              child: Material(
                clipBehavior: Clip.hardEdge,
                borderRadius: BorderRadius.all(Radius.circular(20)),
                child: pic,
              ),
            ),
          ),
          Positioned(
            left: size.width * 0.02,
            bottom: size.height * 0.015,
            child: Padding(
              padding: const EdgeInsets.fromLTRB(12, 0, 0, 0),
              child: Material(
                color: Pallet.darkBlue.withOpacity(0.7),
                borderRadius: BorderRadius.circular(15),
                child: Padding(
                  padding: const EdgeInsets.fromLTRB(8,2,8,2),
                  child: Text(
                    label,
                    style: TextStyle(
                      color: Colors.white,
                      fontWeight: FontWeight.w600,
                      fontSize: 20
                    ),
                  ),
                ),
              ),
            ),
          )
        ],
      ),
    );
  }
}

class SquareSongButton extends StatelessWidget {
  final String label;
  final Image pic;
  final int pageIndex;
  const SquareSongButton({Key key, @required this.label, @required this.pic, @required this.pageIndex}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final Size size = MediaQuery.of(context).size;
    return InkWell(
      onTap: (){
        Navigator.push(
            context,
            MaterialPageRoute(builder: (context) =>
                SongListPage(type: EntityType.AUDIO_FILE, index: pageIndex),
            )
        );
      },
      child: Stack(
        children: [
        Container(
          width: size.width,
            height: size.height * 0.2,
            child: Material(
              clipBehavior: Clip.hardEdge,
              borderRadius: BorderRadius.all(Radius.circular(20)),
              child: pic,
            ),
          ),
          Positioned(
            left: getTextPos(size, pageIndex),
            bottom: size.height * 0.035,
            child: Material(
              color: Pallet.darkBlue.withOpacity(0.7),
              borderRadius: BorderRadius.circular(15),
              child: Padding(
              padding: const EdgeInsets.fromLTRB(8,2,8,2),
                child: Text(
                label,
                  style: TextStyle(
                    color: Colors.white,
                    fontWeight: FontWeight.w600,
                    fontSize: 20
                  ),
                ),
              ),
            ),
          )
        ],
      ),
    );
  }

  getTextPos(Size size, int index){
    if(index == 5){
      return size.width * 0.0065;
    }
    else{
      return size.width * 0.03;
    }
  }
}

//TODO: get user's top song image from database
Image getTopSongImage(String userID){
  Image pic;
  //test image
  pic = Image.asset(
    'lib/Resources/Images/user3_example_pic.jpg',
    fit: BoxFit.cover,
  );
  return pic;
}

//TODO: get user's most recent song uploaded image from database
Image getRecentUploadSongImage(String userID){
  Image pic;
  //test image
  pic = Image.asset(
    'lib/Resources/Images/user5_example_pic.jpg',
    fit: BoxFit.cover,
  );
  return pic;
}

//TODO: get user's most liked song uploaded image from database
Image getMostLikedSongImage(String userID){
  Image pic;
  //test image
  pic = Image.asset(
    'lib/Resources/Images/song1_example_pic.jpg',
    fit: BoxFit.cover,
  );
  return pic;
}

//TODO: get user's most recent liked song from logged in user image from database
Image getMyLikedSongImage(String userID){
  Image pic;
  //test image
  pic = Image.asset(
    'lib/Resources/Images/group1_example_pic.jpg',
    fit: BoxFit.cover,
  );
  return pic;
}

//TODO: get user's most recent listened to song image from database
Image getMostRecentListenedSongImage(String userID){
  Image pic;
  //test image
  pic = Image.asset(
    'lib/Resources/Images/song1_example_pic.jpg',
    fit: BoxFit.cover,
  );
  return pic;
}