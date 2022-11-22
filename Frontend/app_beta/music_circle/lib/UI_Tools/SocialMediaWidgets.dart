import 'package:auto_size_text/auto_size_text.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:music_circle/Entities/AudioFile.dart';
import 'package:music_circle/Entities/Comment.dart';
import 'package:music_circle/Entities/Entity.dart';
import 'package:music_circle/Entities/Playlist.dart';
import 'package:music_circle/Entities/Post.dart';
import 'package:music_circle/Entities/User.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Music/MusicPlayerPage.dart';
import 'package:music_circle/Screens/Profile/Other/UserProfilePage.dart';
import 'package:music_circle/UI_Tools/ImageButtons.dart';

class CircleUserPic extends StatelessWidget {
  final User user;
  final double height, width;
  const CircleUserPic({@required this.user, @required this.height, @required this.width, Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Container(
      height: width,
      decoration: BoxDecoration(
        border: Border.all(
          color: userOnlineColor(user),
          width: width * 0.055,
        ),
        shape: BoxShape.circle,
      ),
      child: Container(
          margin: EdgeInsets.all(1),
          child: ImageButtonCircle(width, width, user.name, user.userImage),
      ),
    );
  }
  Color userOnlineColor(User user){
    if(user.isOnline){
      return Pallet.darkBlue.withOpacity(0.8);
    }
    else{
      return Colors.white.withOpacity(0.5);
    }
  }
}

class PostHeader extends StatelessWidget {
  final Post post;
  final Size size;
  const PostHeader({Key key, @required this.post, @required this.size}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: size.height * 0.06,
      width: size.width,
      child: Container(
        color: Colors.blueGrey.withOpacity(0.5),
        child: Container(
          color: Colors.white.withOpacity(0.775),
          child: Row(
            children: [
              Padding(
                padding: const EdgeInsets.fromLTRB(8,0,0,0),
                child: ImageButtonCircle(size.height * 1/17.5, size.height * 1/17.5, '', post.creator.userImage),
              ),
              Expanded(
                child: Padding(
                  padding: const EdgeInsets.fromLTRB(10,0,0,0),
                  child: Text(
                    post.creator.name,
                    style: TextStyle(

                    ),
                  ),
                ),
              ),
              Padding(
                padding: const EdgeInsets.fromLTRB(8,0,8,0),
                child: Text(
                  post.datePosted.month.toString() + '/' + post.datePosted.day.toString() + '/' + post.datePosted.year.toString(),
                  style: TextStyle(

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

class PostFooter extends StatefulWidget {
  final Post post;
  const PostFooter({Key key, @required this.post}) : super(key: key);

  @override
  _PostFooterState createState() => _PostFooterState();
}
class _PostFooterState extends State<PostFooter> {
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return PostFooterInherited(
      state: this,
      child: SizedBox(
        height: size.height * 1/10,
        width: size.width,
        child: Material(
          color: getBackgroundColor(),
          // borderRadius: BorderRadius.circular(20),
          child:
              Container(
                child: SingleChildScrollView(
                  child: Column(
                    children: [
                      CommentsWidget(parent: widget.post),
                    ],
                  ),
                ),
              ),
        ),
      ),
    );
  }
  getBackgroundColor(){
    if(PostWidgetInherited.of(context).state.commentsExpanded){
      return Colors.blueGrey.withOpacity(0.35);
    }
    else{
      return Colors.transparent;
    }
  }
}
class PostFooterInherited extends InheritedWidget{
  final _PostFooterState state;

  PostFooterInherited({@required this.state, @required Widget child}) : super(child: child);

  @override
  bool updateShouldNotify(covariant PostFooterInherited oldWidget) {
    return true;
  }

  static PostFooterInherited of(BuildContext context) =>
      context.dependOnInheritedWidgetOfExactType<PostFooterInherited>();

}

class CommentsWidget extends StatefulWidget {
  final Entity parent;
  const CommentsWidget({@required this.parent, Key key}) : super(key: key);

  @override
  _CommentsWidgetState createState() => _CommentsWidgetState();
}
class _CommentsWidgetState extends State<CommentsWidget> {
  List<Comment> comments;
  void updateComments(List<Comment> comments){
    if(this.comments != comments){
      setState(() {
        this.comments = comments;
      });
    }
  }

  UniqueKey keyTile;
  bool isExpanded;

  void expandTile(){
    setState(() {
      isExpanded = !isExpanded;
      keyTile = UniqueKey();
    });
  }

  @override
  Widget build(BuildContext context) {
    if(comments == null){
      comments = getInitialComments(widget.parent);
      isExpanded = false;
    }
    return Theme(
      data: Theme.of(context).copyWith(dividerColor: Colors.transparent),
      child: getWidget(widget.parent, context),
    );
  }

  //TODO: get comments from database
  getInitialComments(Entity parent){
    List<Comment> comments;
    //Test comments
    if(parent.getType() == EntityType.POST){
      comments = PostWidgetInherited.of(context).state.post.comments;
    }
    else if(parent.getType() == EntityType.AUDIO_FILE){
      comments = getSongsComments(
          MusicPlayerMainInherited.of(context).state.queue.songs[
          MusicPlayerMainInherited.of(context).state.currentSongIndex
          ]
      );
    }

    return comments;
  }

  //TODO: get songs comments from database
  getSongsComments(AudioFile song){
    List<Comment> comments = [];
    //test comments
    for(int i=0; i<30; i++){
      Comment songComment = new Comment();
      songComment.text = "this song's comment";
      songComment.likes = i;
      User commenter = new User();
      if(i%3 == 0){
        commenter.name = 'Commenter 1';
        commenter.userImage = Image.asset('lib/Resources/Images/user1_example_pic.jpg', fit: BoxFit.cover,);
      }
      else if(i%3 == 1){
        commenter.name = 'Commenter 2';
        commenter.userImage = Image.asset('lib/Resources/Images/user8_example_pic.jpg', fit: BoxFit.cover,);
      }
      else{
        commenter.name = 'Commenter 3';
        commenter.userImage = Image.asset('lib/Resources/Images/user5_example_pic.jpg', fit: BoxFit.cover,);
      }
      songComment.commenter = commenter;

      comments.add(songComment);
    }

    return comments;
  }
  getWidget(Entity parent, BuildContext context){
    Widget commentsWidget;
    Size size = MediaQuery.of(context).size;
    if(parent.getType() == EntityType.POST){
      commentsWidget = ExpansionTile(
        // key: keyTile,
        initiallyExpanded: isExpanded,
        // trailing: SizedBox(height: 0.01, width: 0.01,),
        trailing: getCommentsIconBtn(widget.parent, context),
        onExpansionChanged: (isExpanded){
          expandComments(widget.parent, isExpanded);
        },
        title: getCommentsTitle(widget.parent, context),
        children: [
          ListView.builder(
              itemCount: comments.length,
              primary: false,
              shrinkWrap: true,
              itemBuilder: (context, index){
                return Container(
                  height: size.height * 0.1,
                  width: size.width,
                  child: Row(
                    children: [
                      CircleUserPic(
                        user: comments[index].commenter,
                        height: size.height * 0.05,
                        width: size.height * 0.05,
                      ),
                      Expanded(
                        child: AutoSizeText(
                          comments[index].text,
                          style: TextStyle(

                          ),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.fromLTRB(2,0,0,0),
                        child: SizedBox(
                          child: Column(
                            children: [
                              IconButton(
                                onPressed: (){

                                },
                                icon: Icon(
                                  Icons.favorite_outline_rounded,

                                ),
                              ),
                              AutoSizeText(
                                comments[index].likes.toString(),
                                style: TextStyle(

                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                    ],
                  ),
                );
              }),
        ],
      );
    }
    else if(parent.getType() == EntityType.AUDIO_FILE){

      commentsWidget = Column(
        children: [
          CommentInputWidget(),
          Expanded(
            child: ListView.builder(
                itemCount: comments.length,
                primary: false,
                shrinkWrap: true,
                itemBuilder: (context, index){
                  return Container(
                    height: size.height * 0.1,
                    width: size.width,
                    child: Row(
                      children: [
                        // Column(
                        //   //crossAxisAlignment: CrossAxisAlignment.start,
                        //   children: [
                        //     CircleUserPic(
                        //       user: comments[index].commenter,
                        //       height: size.height * 0.05,
                        //       width: size.height * 0.05,
                        //     ),
                        //     // SizedBox(
                        //     //   width: size.height * 0.08,
                        //     //   child: AutoSizeText(
                        //     //       comments[index].commenter.name,
                        //     //       minFontSize: 5,
                        //     //       maxFontSize: 10,
                        //     //   ),
                        //     // ),
                        //   ],
                        // ),
                        CircleUserPic(
                          user: comments[index].commenter,
                          height: size.height * 0.06,
                          width: size.height * 0.06,
                        ),
                        Expanded(
                          child: Padding(
                            padding: const EdgeInsets.fromLTRB(8,0,8,0),
                            child: AutoSizeText(
                              comments[index].text,
                              style: TextStyle(

                              ),
                            ),
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.fromLTRB(2,0,0,0),
                          child: SizedBox(
                            child: Column(
                              children: [
                                IconButton(
                                  onPressed: (){

                                  },
                                  icon: Icon(
                                    Icons.favorite_outline_rounded,

                                  ),
                                ),
                                AutoSizeText(
                                  comments[index].likes.toString(),
                                  style: TextStyle(

                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                      ],
                    ),
                  );
                }),
          ),
        ],
      );
    }
    return commentsWidget;
  }

  getCommentsTitle(Entity parent, BuildContext context){
    Size size = MediaQuery.of(context).size;
    if(parent.getType() == EntityType.POST){
      return Row(
        children: [
          Expanded(
            child: AutoSizeText(
              PostFooterInherited.of(context).state.widget.post.label,
              style: TextStyle(

              ),
            ),
          ),
          SizedBox(
            width: size.width * 0.11,
            child: InkWell(
              onTap: (){
                //Like post
              },
              child: Row(
                children: [
                  Expanded(
                    child: Padding(
                      padding: const EdgeInsets.fromLTRB(0,0,2,0),
                      child: Icon(
                        Icons.favorite_outline_rounded,
                        color: Pallet.lightBlue,
                      ),
                    ),
                  ),
                  AutoSizeText(
                    // post.likes.toString(),
                    PostFooterInherited.of(context).state.widget.post.likes.toString(),
                    textAlign: TextAlign.right,
                    style: TextStyle(

                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      );
    }
  }
  getCommentsIconBtn(Entity parent, BuildContext context){
    Size size = MediaQuery.of(context).size;
    if(parent.getType() == EntityType.POST){
      return SizedBox(
        width: size.width * 0.11,
        child: InkWell(
          onTap: (){
            //expand to show comments
            expandTile();
            expandComments(parent, isExpanded);
          },
          child: Row(
            children: [
              Expanded(
                child: Icon(
                  Icons.comment_outlined,
                  color: Pallet.lightBlue,
                ),
              ),
              AutoSizeText(
                PostFooterInherited.of(context).state.widget.post.comments.length.toString(),
                textAlign: TextAlign.right,
                style: TextStyle(

                ),
              ),
            ],
          ),
        ),
      );
    }
  }
  expandComments(Entity parent, bool isExpanded){
    if(parent.getType() == EntityType.POST){
      PostWidgetInherited.of(context).state.setCommentsExpanded(isExpanded);
    }
  }

}

class PostWidget extends StatefulWidget {
  final Post post;
  const PostWidget({Key key, @required this.post}) : super(key: key);

  @override
  PostWidgetState createState() => PostWidgetState();
}
class PostWidgetState extends State<PostWidget> {
  Post post;
  void updatePost(Post post){
    if(this.post != post){
      setState(() {
        this.post = post;
      });
    }
  }
  bool commentsExpanded;
  void setCommentsExpanded(bool isExpanded){
    if(commentsExpanded != isExpanded){
      setState(() {
        commentsExpanded = isExpanded;
      });
    }
  }
  @override
  Widget build(BuildContext context) {
    if(post == null){
      post = widget.post;
      commentsExpanded = false;
    }
    Size size = MediaQuery.of(context).size;
    return PostWidgetInherited(
      state: this,
      child: Container(
        height: getTextPostHeight(post, size),
        width: size.width,
        color: Colors.blueGrey.withOpacity(0.5),
        child: Material(
          color: Colors.white.withOpacity(0.775),
          child: Column(
            children: [
              Container(
                decoration: BoxDecoration(
                  color: Colors.blueGrey.withOpacity(0.2),
                ),
                height: 1,
                width: size.width,

              ),
              PostHeader(post: post, size: size),
              getPostType(post),
              getCommentInputWidget(size),
              Expanded(
                child: PostFooter(post: post),
              ),
            ],
          ),
        ),
      ),
    );
  }
  double getTextPostHeight(Post post, Size size){
    double height;
    if(post.getContentType() == EntityType.COMMENT){
      height = size.height * 0.35 + 1;
    }
    else if(post.getContentType() == EntityType.AUDIO_FILE){
      height = size.height * 0.6 + 1;
    }
    else{
      height = size.height * 0.5 + 1;
    }
    if(commentsExpanded){
      height += size.height * 0.4;
    }
    return height;
  }
  Widget getPostType(Post post){
    Widget postWidget;
    switch(post.getContentType()){
      case EntityType.AUDIO_FILE:{
        postWidget = SongPostWidget(post: post);
      }
      break;
      case EntityType.ALBUM:{
        postWidget = AlbumPostWidget(post: post);
      }
      break;
      case EntityType.PLAYLIST:{
        postWidget = PlaylistPostWidget(post: post);
      }
      break;
      case EntityType.USER:{
        postWidget = ArtistPostWidget(post: post);
      }
      break;
      case EntityType.GROUP:{
        postWidget = GroupPostWidget(post: post);
      }
      break;
      case EntityType.EVENT:{
        postWidget = EventPostWidget(post: post);
      }
      break;
      case EntityType.COMMENT:{
        postWidget = TextPostWidget(post: post);
      }
      break;
      default:{
        postWidget = Container();
      }
      break;
    }
    return postWidget;
  }

  getCommentInputWidget(Size size){
    if(commentsExpanded){
      return CommentInputWidget();
    }
    else{
      return SizedBox(height: 0.000000000000000000001, width: 0.000000000000000000001,);
    }
  }
}
class CommentInputWidget extends StatelessWidget {
  const CommentInputWidget({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Container(
      height: size.height * 0.075,
      width: size.width,
      decoration: BoxDecoration(
        color: Colors.transparent,
        //borderRadius: BorderRadius.circular(20),
      ),
      child: SizedBox(
        height: size.height * 0.1,
        width: size.width,
        child: Container(
          padding: EdgeInsets.symmetric(horizontal: 15.0),
          decoration: BoxDecoration(

            //borderRadius: BorderRadius.circular(20),
            // boxShadow: [
            //   BoxShadow(
            //     offset: Offset(0,10),
            //     blurRadius: 50,
            //     color: Colors.white.withOpacity(0.60),
            //   ),
            // ]
          ),
          child: Row(
            children: [
              Expanded(
                child: TextField(
                  onChanged: (value){

                  },
                  decoration: InputDecoration(
                    hintText: 'Add Comment',
                    hintStyle: TextStyle(
                      color: Colors.blueGrey,
                    ),
                    enabledBorder: InputBorder.none,
                    focusedBorder: InputBorder.none,
                  ),
                ),
              ),
              Material(
                color: Colors.transparent,
                shape: CircleBorder(),
                child: IconButton(
                  onPressed: (){

                  },
                  icon: Icon(
                    Icons.send,
                    color: Colors.blueGrey,
                  ),
                  alignment: Alignment.centerRight,
                  splashColor: Pallet.darkBlue,
                  splashRadius: 25.0,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class PostWidgetInherited extends InheritedWidget{
  final PostWidgetState state;

  PostWidgetInherited({@required this.state, @required Widget child}) : super(child: child);

  @override
  bool updateShouldNotify(covariant PostWidgetInherited oldWidget) {
    return true;
  }

  static PostWidgetInherited of(BuildContext context) =>
      context.dependOnInheritedWidgetOfExactType<PostWidgetInherited>();
}

class SongPostWidget extends StatelessWidget {
  final SongPost post;
  const SongPostWidget({Key key, @required this.post}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return SizedBox(
      height: size.height * 0.44,
      width: size.width,
      child: Material(
        color: Colors.transparent,
        child: Container(
          
          child: Stack(
            children: [
              Center(
                  child: InkWell(
                      onTap: (){
                        Playlist song = new Playlist();
                        song.songs = [post.song];
                        Navigator.push(
                          context,
                          MaterialPageRoute(builder: (context) =>
                             MusicPlayerMain(startingPlaylist: song,),
                          ),
                        );
                      },
                      child: ImageButtonSquareRounded(size.width * 0.95, size.width * 0.95, '', post.postPic))),
              Text(
                '',
                style: TextStyle(

                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class AlbumPostWidget extends StatelessWidget {
  final AlbumPost post;
  const AlbumPostWidget({Key key, @required this.post}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return SizedBox(
      height: size.height * 0.34,
      width: size.width,
      child: Material(
        color: Colors.transparent,
        child: Container(
          
          child: Stack(
            children: [
              Positioned(
                left: size.width * 0.05,
                top: size.height * 0.005,
                child: InkWell(
                    onTap: (){
                      Navigator.push(
                        context,
                        MaterialPageRoute(builder: (context) =>
                            MusicPlayerMain(startingPlaylist: post.album,),
                        ),
                      );
                    },
                    child: ImageButtonSquareRounded(size.height * 0.3, size.width * 0.9, '', post.postPic)
                ),
              ),
              Positioned(
                left: size.width * 0.04,
                bottom: 0,
                child: SizedBox(
                  width: size.width * 0.5,
                  child: AutoSizeText(
                    post.album.name,
                    style: TextStyle(
                      fontSize: 20,
                      color: Pallet.darkBlue,
                      //fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
              ),
              Positioned(
                right: size.width * 0.04,
                bottom: 0,
                child: SizedBox(
                  width: size.width * 0.5,
                  child: Row(
                    children: [
                      Expanded(child: Container()),
                      InkWell(
                        onTap: (){
                          Navigator.push(
                              context,
                              MaterialPageRoute(builder: (context) =>
                                  UserProfilePage(),
                              ),
                          );
                        },
                        child: Row(
                          children: [
                            Icon(
                              Icons.person,
                              color: Pallet.darkBlue.withOpacity(0.8),
                            ),
                            AutoSizeText(
                              post.album.artist.name,
                              style: TextStyle(
                                fontSize: 20,
                                color: Pallet.darkBlue,
                              ),
                            ),
                          ],
                        ),
                      ),
                    ],
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

class PlaylistPostWidget extends StatelessWidget {
  final PlaylistPost post;
  const PlaylistPostWidget({Key key, @required this.post}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    List<Widget> tracklist = getTrackList(size);
    return SizedBox(
      height: size.height * 0.34,
      width: size.width,
      child: Material(
        color: Colors.transparent,
        child: Container(
          
          child: Stack(
            children: [
              RotatedBox(
                quarterTurns: 3,
                child: SizedBox(
                  width: size.height * 0.3,
                  height: size.width * 0.1,
                  child: Row(
                    children: [
                      Expanded(
                        child: AutoSizeText(
                          post.playlist.name,
                          textAlign: TextAlign.center,
                          style: TextStyle(
                            fontSize: 20,
                            color: Pallet.darkBlue.withOpacity(0.7),
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              PageView.builder(
                itemBuilder: (context, index){
                  return tracklist[index];
                },
                itemCount: tracklist.length,
              ),
            ],
          ),
        ),
      ),
    );
  }

  List<Widget> getTrackList(Size size){
    List<Widget> songWidgets = [];
    post.playlist.songs.forEach((song) {
      Widget songWidget = SizedBox(
        height: size.height * 0.34,
        width: size.width * 0.9,
        child:
        Stack(
          children: [
            Positioned(
              right: 2,

              child: InkWell(
                onTap: (){

                },
                child: ImageButtonSquareRounded(size.height * 0.3, size.width * 0.9, '', song.songImage),
              ),
            ),
            Positioned(
              right: 0,
              bottom: 0,
              child: SizedBox(
                width: size.width * 0.9,
                child: Row(
                  children: [
                    Expanded(
                      child: AutoSizeText(
                        song.name,
                        textAlign: TextAlign.center,
                        style: TextStyle(
                          fontSize: 16,
                          color: Pallet.darkBlue
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      );
      songWidgets.add(songWidget);
    });

    return songWidgets;
  }
}

class ArtistPostWidget extends StatelessWidget {
  final ArtistPost post;
  const ArtistPostWidget({Key key, @required this.post}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return SizedBox(
      height: size.height * 0.34,
      width: size.width,
      child: Material(
        color: Colors.transparent,
        child: Container(
          
          child: Stack(
            children: [
              Positioned(
                top: 10,
                left: size.width * 0.0125,
                  child: ImageButtonSquareRounded(size.height * 0.285, size.width * 0.975, '', post.postPic)
              ),
              Positioned(
                left: size.width * 0.04,
                bottom:0,
                child: SizedBox(
                  width: size.width,
                  child: Row(
                          children: [
                            Text(
                              post.artist.name,
                              style: TextStyle(

                              ),
                            ),
                            Icon(
                              Icons.check_circle,
                              color: userIsChecked(post.artist),
                            ),
                          ],
                        ),
                    ),
              ),
              Positioned(
                right: size.width * 0.04,
                bottom: 0,
                child: Text(
                  post.artist.followers.length.toString() + ' followers',
                  textAlign: TextAlign.left,
                  style: TextStyle(

                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Color userIsChecked(User user){
    if(user.isChecked){
      return Pallet.lightBlue;
    }
    else{
      return Colors.transparent;
    }
  }
}

class GroupPostWidget extends StatelessWidget {
  final GroupPost post;
  const GroupPostWidget({Key key, @required this.post}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return SizedBox(
      height: size.height * 0.34,
      width: size.width,
      child: Material(
        color: Colors.transparent,
        child: Container(
          
          child: Stack(
            children: [
              InkWell(
                onTap: (){

                },
                child: ImageButtonSquareNotSoRounded(size.height * 0.3125, size.width, '', post.postPic)),
              Positioned(
                bottom: 0,
                child: Container(
                  width: size.width,
                  child: Center(
                    child: Text(
                      post.group.name,
                      style: TextStyle(
                        fontSize: 20,
                      ),
                    ),
                  ),
                ),
              ),
              Positioned(
                bottom: 0,
                right: size.width * 0.04,
                child: Padding(
                  padding: const EdgeInsets.fromLTRB(0,0,8.0,0),
                  child: Row(
                    children: [
                      Icon(
                        Icons.people_alt_outlined,
                        color: Pallet.darkBlue,
                      ),
                      AutoSizeText(
                        post.group.members.length.toString(),
                        textAlign: TextAlign.right,
                        style: TextStyle(

                        ),
                      ),
                    ],
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

class EventPostWidget extends StatelessWidget {
  final EventPost post;
  const EventPostWidget({Key key, @required this.post}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return SizedBox(
      height: size.height * 0.34,
      width: size.width,
      child: Material(
        color: Colors.transparent,
        child: Container(
          
          child: Stack(
            children: [
              InkWell(
                  onTap: (){

                  },
                  child: ImageButtonSquareNotSoRounded(size.height * 0.3125, size.width, '', post.postPic)),
              Positioned(
                bottom: 0,
                child: Container(
                  width: size.width,
                  child: Center(
                    child: Text(
                      post.event.name,
                      style: TextStyle(
                        fontSize: 20,
                      ),
                    ),
                  ),
                ),
              ),
              Positioned(
                bottom: 0,
                right: 0,
                child: Padding(
                  padding: const EdgeInsets.fromLTRB(0,0,11,0),
                  child: Row(
                    children: [
                      Padding(
                        padding: const EdgeInsets.fromLTRB(0,0,12,0),
                        child: Icon(
                          Icons.date_range,
                          color: Pallet.darkBlue,
                        ),
                      ),
                      Text(
                        post.event.dateTime.month.toString() + '/' + post.event.dateTime.day.toString() + '/' + post.event.dateTime.year.toString(),
                        style: TextStyle(

                        ),
                      ),
                    ],
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

class TextPostWidget extends StatelessWidget {
  final TextPost post;
  const TextPostWidget({Key key, @required this.post}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return SizedBox(
      height: size.height * 0.19,
      width: size.width,
      child: Container(
        color: Colors.transparent,
        child: Container(
          
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: AutoSizeText(
              post.text,
              style: TextStyle(
                fontSize: 40,
              ),
            ),
          ),
        ),
      ),
    );
  }
}


