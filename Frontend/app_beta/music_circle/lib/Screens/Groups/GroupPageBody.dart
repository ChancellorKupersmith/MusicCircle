import 'package:auto_size_text/auto_size_text.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:music_circle/Entities/Album.dart';
import 'package:music_circle/Entities/AudioFile.dart';
import 'package:music_circle/Entities/Chat.dart';
import 'package:music_circle/Entities/Comment.dart';
import 'package:music_circle/Entities/Event.dart';
import 'package:music_circle/Entities/Group.dart';
import 'package:music_circle/Entities/Playlist.dart';
import 'package:music_circle/Entities/Post.dart';
import 'package:music_circle/Entities/User.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Groups/GroupPage.dart';
import 'package:music_circle/Screens/Groups/MemberGroupPage.dart';
import 'package:music_circle/UI_Tools/ImageButtons.dart';
import 'package:music_circle/UI_Tools/SocialMediaWidgets.dart';

import 'GroupChat.dart';


class GroupPageBody extends StatelessWidget {
  final bodyIndex;
  const GroupPageBody({Key key, @required this.bodyIndex}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return getBodyContent();
  }

  Widget getBodyContent(){
    switch(bodyIndex){
      case 1:{
        return GroupPageChats();
      }
      break;
      default:{
        return GroupPageFeed();
      }
    }
  }
}

class GroupPageFeed extends StatefulWidget {

  const GroupPageFeed({Key key}) : super(key: key);

  @override
  _GroupPageFeedState createState() => _GroupPageFeedState();
}
class _GroupPageFeedState extends State<GroupPageFeed> {
  List<Widget> groupPosts = [];
  void updateGroupPosts(List<Widget> posts){
    if(groupPosts != posts){
      setState(() {
        groupPosts = posts;
      });
    }
  }
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    groupPosts = getGroupPosts(GroupPageInherited.of(context).state.group);
    return SizedBox(
      width: size.width,
      child: CustomScrollView(
        shrinkWrap: true,
        primary: false,
        slivers:[
          // SliverOverlapInjector(handle: NestedScrollView.sliverOverlapAbsorberHandleFor(context)),
          SliverList(
            delegate: SliverChildBuilderDelegate(
                (context, index){
                  return groupPosts[index];
                },
                childCount: groupPosts.length,
            ),
          ),
        ],
      ),
    );
  }

  List<Widget> getGroupPosts(Group group){
    List<Widget> postsList = [];
    List<Post> groupPosts = [];

    //test posts
    for(int i=0; i<15; i++){
      Post post;
      User artist = new User();
      artist.name = 'Artist ' + i.toString();
      artist.userImage = Image.asset(
        'lib/Resources/Images/user5_example_pic.jpg',
        fit: BoxFit.cover,
      );
      if(i % 7 == 0){
        AudioFile song = new AudioFile();
        song.name = 'Song ' + i.toString();
        song.artist = artist;
        song.likes = 4;
        song.plays = 400;
        song.songImage = Image.asset(
          'lib/Resources/Images/song1_example_pic.jpg',
          fit: BoxFit.cover,
        );
        post = new SongPost(song);
        post.postPic = song.songImage;
      }
      else if(i % 7 == 1){
        Album album = new Album();
        album.name = 'Album ' + i.toString();
        album.artist = artist;
        album.songs = [];
        for(int j=0; j<5; j++){
          AudioFile song = new AudioFile();
          song.name = 'Song ' + j.toString();
          song.artist = artist;
          song.songImage = Image.asset(
            'lib/Resources/Images/album1_example_pic.jpg',
            fit: BoxFit.cover,
          );
          album.songs.add(song);
        }
        album.albumImage = Image.asset(
          'lib/Resources/Images/album1_example_pic.jpg',
          fit: BoxFit.cover,
        );
        post = new AlbumPost(album);
        post.postPic = album.albumImage;
      }
      else if(i % 7 == 2){
        Playlist playlist = new Playlist();
        playlist.name = 'Playlist ' + i.toString();
        playlist.playlistImage = Image.asset(
          'lib/Resources/Images/playlist_default_pic.png',
          fit: BoxFit.cover,
        );
        playlist.songs = [];
        for(int j=0; j<15; j++){
          AudioFile song = new AudioFile();
          song.name = 'Song ' + j.toString();
          song.artist = artist;
          song.songImage = Image.asset(
            'lib/Resources/Images/song1_example_pic.jpg',
            fit: BoxFit.cover,
          );
          playlist.songs.add(song);
        }
        post = new PlaylistPost(playlist);
      }
      else if(i % 7 == 3){
        artist.isChecked = true;
        artist.city = 'Ames';
        artist.state = 'IA';
        artist.groups.add(group);
        for(int j=0; j<15; j++){
          artist.followers.add(new User().testUser(number: i));
          artist.following.add(new User().testUser(number: i));
        }
        post = new ArtistPost(artist);
        post.postPic = artist.userImage;
      }
      else if(i % 7 == 4){
        Group sharedGroup = new Group();
        sharedGroup.name = 'Group ' + i.toString();
        sharedGroup.freeToJoin = true;
        sharedGroup.description = 'This is ' + sharedGroup.name + '\'s description.';
        sharedGroup.groupImage = Image.asset(
          'lib/Resources/Images/group1_example_pic.jpg',
          fit: BoxFit.cover,
        );
        post = new GroupPost(sharedGroup);
        post.postPic = sharedGroup.groupImage;
      }
      else if(i % 7 == 5){
        Event event = new Event();
        event.name = 'Event ' + i.toString();
        event.description = 'This is ' + event.name + '\'s description.';
        event.city = 'Ames';
        event.state = 'IA';
        event.creator = artist;
        event.dateTime = DateTime.now();
        event.eventImage = Image.asset(
          'lib/Resources/Images/test_event_pic.jpg',
         fit: BoxFit.cover,
        );
        for(int j=0; j<15; j++){
          User performer = new User();
          User attender = new User();
          event.usersPerforming.add(performer);
          event.usersAttending.add(attender);
        }
        post = new EventPost(event);
        post.postPic = event.eventImage;
      }
      else{
        String text = '';
        for(int i=0; i<10; i++){
          text += 'T';
        }
        post = new TextPost(text);
        post.postPic = group.groupImage;
      }

      post.creator = artist;
      post.likes = i;
      post.label = 'This posts label.';
      post.datePosted = DateTime.now();
      User commenter1 = new User();
      commenter1.name = 'User 1';
      commenter1.userImage = Image.asset(
        'lib/Resources/Images/user2_example_pic.jpg',
        fit: BoxFit.cover,
      );
      User commenter2 = new User();
      commenter2.name = 'User 2';
      commenter2.userImage = Image.asset(
        'lib/Resources/Images/user3_example_pic.jpg',
        fit: BoxFit.cover,
      );
      for(int i=0; i<30; i++){
        Comment comment = new Comment();
        comment.text = "this post's comment " + i.toString() + ' tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt';
        comment.likes = i;
        if(i%2 == 0){
          comment.commenter = commenter1;
        }
        else{
          comment.commenter = commenter2;
        }
        post.comments.add(comment);
      }
      groupPosts.add(post);
    }
    groupPosts.forEach((post) {
      postsList.add(PostWidget(post: post));
    });
    return postsList;
  }
}

class GroupPageChats extends StatefulWidget {
  const GroupPageChats({Key key}) : super(key: key);

  @override
  _GroupPageChatsState createState() => _GroupPageChatsState();
}
class _GroupPageChatsState extends State<GroupPageChats> {
  List<Widget> groupChats;
  void updateGroupChats(List<Widget> chats){
    if(groupChats != chats){
      setState(() {
        groupChats = chats;
      });
    }
  }
  List<Chat> chats = [];
  void updateChats(List<Chat> chats){
    if(this.chats != chats){
      setState(() {
        this.chats = chats;
      });
    }
  }
  int chatIndex = 0;
  void updateChatIndex(index){
    if(chatIndex != index){
      setState(() {
        chatIndex = index;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    groupChats = [];
    getGroupChats(GroupPageInherited.of(context).state.group, size);
    return GroupPageChatsInherited(
      state: this,
      child: SizedBox(
        width: size.width,
        height: size.height,
        // child: ListView.builder(
        //   shrinkWrap: true,
        //   primary: false,
        //   itemBuilder: (BuildContext context, int index){
        //     return groupChats[index];
        //   },
        //   itemCount: groupChats.length,
        // ),
        child: CustomScrollView(
          primary: false,
          shrinkWrap: true,
          slivers: [
            SliverList(delegate: SliverChildBuilderDelegate((context, index) {
              return groupChats[index];
            },
              childCount: groupChats.length,

            )),
          ],
        ),
      ),
    );
  }

  //TODO: get group chats from database
  getGroupChats(Group group, Size size){
    // List<Widget> chatWidgetList = [];
    List<Chat> newChats= [];
    List<User> commenters = [];
    User commenter;
    Comment comment;
    //Test group chats
    for(int i=0; i<10; i++){
      Chat chat = new Chat();
      chat.name = 'Chat ' + i.toString();
      chat.id = i.toString();
      chat.index = i;
      if(i % 2 == 0){
        chat.unreadComments = true;
      }
      else{
        chat.unreadComments = false;
      }
      User loggedInUser = new User();
      loggedInUser.name = 'Logged in user';
      loggedInUser.isOnline = true;
      loggedInUser.userImage = Image.asset(
        'lib/Resources/Images/user1_example_pic.jpg',
        fit: BoxFit.cover,
      );
      for(int j=0; j<30; j++){
        if(j%3 == 0){
          commenter = loggedInUser;
        }
        else if(j%5 == 0){
          commenter = new User();
          commenter.name = 'User ' + j.toString();
          commenter.userImage = Image.asset(
            'lib/Resources/Images/user3_example_pic.jpg',
            fit: BoxFit.cover,
          );
        }
        else{
          commenter = new User();
          commenter.name = 'User ' + j.toString();
          commenter.isOnline = true;
          commenter.userImage = Image.asset(
            'lib/Resources/Images/user5_example_pic.jpg',
            fit: BoxFit.cover,
          );
        }
        commenter.id = commenter.name;
        commenters.add(commenter);
        comment = new Comment();
        comment.text = 'Comment ' + j.toString();
        comment.likes = j;
        comment.commenter = commenter;
        chat.comments.add(comment);
        //print(chat.comments);
      }
      chat.chatImage = Image.asset(
        'lib/Resources/Images/album2_example_pic.jpg'
      );
      newChats.add(chat);
    }
    updateChats(newChats);
    chats.forEach((chat) {
      Widget chatWidget = SizedBox(
        height: size.height * 0.1,
        width: size.width,
        child: Material(
          color: Colors.transparent,
          child: InkWell(
            onTap: (){
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) =>
                    GroupChat(chat),
                ),
              );
            },
            child: Stack(
              children: [
                Positioned(
                  right: 10,
                  child: Container(
                    width: size.width * 0.8,
                    height: 1,
                    alignment: Alignment.centerRight,
                    color: Colors.blueGrey.withOpacity(0.3),
                  ),
                ),
                Row(
                  children: [
                    Padding(
                      padding: const EdgeInsets.fromLTRB(4,0,0,0),
                      child: Stack(
                        children: [
                          ImageButtonCircle(size.height * 0.075, size.height * 0.075, '', chat.chatImage),
                          Positioned(
                            bottom: size.height * 0.0125,
                            right: 0,
                            child: Padding(
                              padding: const EdgeInsets.fromLTRB(0,0,8,0),
                              child: Icon(
                                Icons.circle,
                                color: getUnreadChatIconColor(chat),
                                size: 13,
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                    Expanded(
                      child: Padding(
                        padding: const EdgeInsets.fromLTRB(16,0,8,0),
                        child: AutoSizeText(
                          chat.name,
                          style: TextStyle(
                            fontSize: 20,
                          ),
                        ),
                      ),
                    ),
                    Expanded(
                      child: Padding(
                        padding: const EdgeInsets.fromLTRB(0,0,8,0),
                        child: AutoSizeText(
                          chat.comments[chat.comments.length - 1].text,
                          textAlign: TextAlign.right,
                          style: TextStyle(
                            fontSize: 14,
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),
        ),
      );
      groupChats.add(chatWidget);
    });
    // return groupChats;
  }

  Color getUnreadChatIconColor(Chat chat){
    if(chat.unreadComments){
      return Pallet.lightBlue;
    }
    else{
      return Colors.transparent;
    }
  }
}
class GroupPageChatsInherited extends InheritedWidget{
  final _GroupPageChatsState state;

  GroupPageChatsInherited({@required this.state, @required Widget child}) : super(child: child);

  @override
  bool updateShouldNotify(covariant GroupPageChatsInherited oldWidget) {
    return true;
  }

  static GroupPageChatsInherited of(BuildContext context) =>
      context.dependOnInheritedWidgetOfExactType<GroupPageChatsInherited>();
}

