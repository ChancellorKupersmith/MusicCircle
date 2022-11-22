import 'package:flutter/material.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Resources/Strings.dart';
import 'package:music_circle/Screens/Discover/Local/LocalDiscover.dart';
import 'package:music_circle/UI_Tools/ImageButtons.dart';
import 'package:music_circle/UI_Tools/ListWidgets/UserEntityList.dart';
import 'package:music_circle/Entities/User.dart';

class LoggedInUserProfileBodyAbout extends StatelessWidget {
  const LoggedInUserProfileBodyAbout({Key key}) : super(key: key);

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
                //color: Pallet.darkBlue.withOpacity(0.001),
                color: Pallet.darkBlue,
                borderRadius: BorderRadius.only(
                  topLeft: Radius.circular(40),
                  topRight: Radius.circular(10),
                ),
                child: Container(
                height: size.height * 0.88,
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
                  padding: const EdgeInsets.fromLTRB(8.0, 0.0, 0.0, 0.0),
                  child: Text('About Me',
                    style: TextStyle(
                      color: Colors.white,
                      fontSize: 30,
                    ),
                  ),
                ),
              ),
              Positioned(
                  left: 0,
                  top: size.height - size.height * 0.95,
                  child: SocialButtonBar(),
              ),
              Positioned(
                  left: 10,
                  top: size.height - size.height * 0.87,
                  child: UserProfession(jobTitle: 'Singer-songwriter', verified: true,)
              ),
              Positioned(
                left: 10,
                top: size.height - size.height * 0.84,
                child: Bio(bio: 'Just a musician looking to make music and new friends',
                )
              )
            ],
          )
        ],
    );
  }

  List<FollowerImageButton> getFollowers(){
    List<User> followers = [];
    List<FollowerImageButton> btnList = [];

    //TODO: Get followers

    //test list
    Image followerImage = Image.asset(
      'lib/Resources/Images/user1_example_pic.jpg',
      fit: BoxFit.cover,
    );
    for(int i=0; i<30; i++){
      User follower = new User();
      follower.name = 'User ' + i.toString();
      follower.userImage = followerImage;
      followers.add(follower);
    }

    followers.forEach((follower) {
      btnList.add(FollowerImageButton(artistName: follower.name, pic: follower.userImage,));
    });

    return btnList;
  }
}

class SocialButtonBar extends StatelessWidget {
  const SocialButtonBar({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return SizedBox(
      height: size.height * 0.3,
      width: size.width * 0.98,
      child: Row(
        children: [
          Expanded(
            child: InkWell(
              onTap: (){},
              child: Column(
                children: [
                  Padding(
                    padding: const EdgeInsets.fromLTRB(15.0, 0.0, 0.0, 0.0),
                    child: Align(
                      alignment: Alignment.topRight,
                      child: Text(
                        'Followers',
                        style: TextStyle(
                          color: Pallet.softBlue.withOpacity(0.5),
                          fontSize: 20,
                          fontWeight: FontWeight.w600,
                        ),
                      ),
                    ),
                  ),
                  Align(
                    alignment: Alignment.topRight,
                    child: Text(
                      '1.4k',
                      style: TextStyle(
                        color: Pallet.softBlue,
                        fontSize: 26,
                        fontWeight: FontWeight.w600,
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),
          Padding(
            padding: const EdgeInsets.fromLTRB(10.0, 0.0, 8.0, 8.0),
            child: Align(
              alignment: Alignment.topCenter,
              child: Container(
                height: 60,
                width: 1,
                color: Colors.white,
              ),
            ),
          ),
          InkWell(
            onTap: (){},
            child: Column(
              children: [
                Align(
                  alignment: Alignment.topRight,
                  child: Text(
                    'Following',
                    style: TextStyle(
                      color: Pallet.softBlue.withOpacity(0.5),
                      fontSize: 20,
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                ),
                Align(
                  alignment: Alignment.bottomRight,
                  child: Text(
                    '2.4k',
                    style: TextStyle(
                      color: Pallet.softBlue,
                      fontSize: 26,
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

class Bio extends StatelessWidget {
  final String bio;
  const Bio({Key key, this.bio}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return SizedBox(
      height: size.height * 0.13,
      width: size.width - 10,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Expanded(
            child: Padding(
              padding: const EdgeInsets.fromLTRB(4,4,0,0),
              child: ListView(
                physics: ClampingScrollPhysics(),
                children: [
                  Text('' + bio,
                    style: TextStyle(
                      color: Pallet.softBlue,
                      fontSize: 16,
                      fontWeight: FontWeight.w500,
                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class UserProfession extends StatelessWidget {
  final String jobTitle;
  final bool verified;
  const UserProfession({Key key, this.jobTitle, this.verified}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Text(jobTitle,
          style: TextStyle(
            color: Colors.white,
            fontSize: 16,
            fontWeight: FontWeight.bold,
          ),
        ),
        Padding(
          padding: const EdgeInsets.fromLTRB(8.0,0,0,0),
          child: Icon(
            Icons.check_circle_sharp,
            color: verified? Pallet.softBlue : Colors.transparent,
            size: 20,
          ),
        ),
      ],
    );
  }
}

