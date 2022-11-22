import 'package:auto_size_text/auto_size_text.dart';
import 'package:flutter/material.dart';
import 'package:music_circle/Entities/Chat.dart';
import 'package:music_circle/Entities/Comment.dart';
import 'package:music_circle/Entities/User.dart';
import 'package:music_circle/Main/LoggedInUser.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Groups/GroupPageBody.dart';
import 'package:music_circle/UI_Tools/SocialMediaWidgets.dart';

class GroupChat extends StatefulWidget {
  final Chat chat;
  const GroupChat(this.chat, {Key key}) : super(key: key);

  static final String routeName = '/group-chat-page';

  @override
  _GroupChatState createState() => _GroupChatState();
}

class _GroupChatState extends State<GroupChat> {
  Chat chat;
  void updateChat(Chat chat){
    if(this.chat != chat){
      setState(() {
        this.chat = chat;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    chat = widget.chat;
    return GroupChatInherited(
      state: this,
      child: Scaffold(
        backgroundColor: Colors.white,
        body: SizedBox(
          height: size.height,
          width: size.width,
          child: Material(
            color: Pallet.lightBlue.withOpacity(0.15),
            child: Stack(
              children: [
                SizedBox(
                  height: size.height * 0.925,
                  child: ListView(
                    shrinkWrap: true,
                    children: getChatMessagesList(size),
                  ),
                ),
                Positioned(
                  bottom: 0,
                  child: getTextInputWidget(size),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  List<Widget> getChatMessagesList(Size size){
    List<Widget> chatMessageWidgets = [];
    print(size.height * 0.05);
    print(size.width * 0.1);
    // print(UserInfoInherited.of(context).state);
    chat.comments.forEach((comment) {
      chatMessageWidgets.add(
          Padding(
            padding: const EdgeInsets.fromLTRB(0,0,0,0),
            child: ChatMessageWidget(comment: comment,),
          ),
      );
    });
    return chatMessageWidgets;
  }

  getTextInputWidget(Size size){
    return Container(
      height: size.height * 0.075,
      width: size.width,
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.only(
          topLeft: Radius.circular(20),
          topRight: Radius.circular(20),
        ),
      ),
      child: SizedBox(
        height: size.height * 0.1,
        width: size.width,
        child: Container(
          // margin: EdgeInsets.symmetric(horizontal: 15.0),
          padding: EdgeInsets.symmetric(horizontal: 15.0),
          // height: 54,
          decoration: BoxDecoration(
              color: Colors.white,
              borderRadius: BorderRadius.only(
                topRight: Radius.circular(20),
                topLeft: Radius.circular(20),
              ),
              boxShadow: [
                BoxShadow(
                  offset: Offset(0,10),
                  blurRadius: 50,
                  color: Pallet.lightBlue.withOpacity(0.60),
                ),
              ]
          ),
          child: Row(
            children: [
              Expanded(
                child: TextField(
                  onChanged: (value){

                  },
                  decoration: InputDecoration(
                    hintText: 'Send a chat',
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
                borderRadius: BorderRadius.circular(30),
                child: IconButton(
                  onPressed: (){

                  },
                  icon: Icon(
                    Icons.send,
                    color: Colors.blueGrey,
                  ),
                  alignment: Alignment.centerRight,
                  splashColor: Pallet.lightBlue,
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

class GroupChatInherited extends InheritedWidget{
  final _GroupChatState state;

  GroupChatInherited({@required this.state, @required Widget child}) : super(child: child);

  @override
  bool updateShouldNotify(covariant GroupChatInherited oldWidget) {
    return true;
  }

  static GroupChatInherited of(BuildContext context) =>
      context.dependOnInheritedWidgetOfExactType<GroupChatInherited>();

}

class ChatMessageWidget extends StatefulWidget {
  final Comment comment;
  const ChatMessageWidget({Key key, @required this.comment}) : super(key: key);

  @override
  _ChatMessageWidgetState createState() => _ChatMessageWidgetState();
}

class _ChatMessageWidgetState extends State<ChatMessageWidget> {
  @override
  Widget build(BuildContext context) {
    //TODO: Get user from database
    //Test User
    // commenter = new User();
    // commenter.userImage = Image.asset(
    //   'lib/Resources/Images/user5_example_pic.jpg',
    //   fit: BoxFit.cover,
    // );
    // commenter.artistName = 'Commenter Name';
    // commenter.isOnline = true;

    Size size = MediaQuery.of(context).size;
    return getComment(widget.comment.commenter, size);
  }
  Widget getComment(User commenter, Size size){
    Widget commentWidget;

    switch(commenter.id){
      case 'Logged in user':{
        commentWidget = getLoggedInUserComment(commenter, size);
      }
      break;
      default:{
        commentWidget = getUserComment(commenter, size);
      }
    }
    return commentWidget;
  }
  getUserComment(User commenter, Size size){
    return Row(
      children: [
        CircleUserPic(user: commenter, height: size.width * 0.1, width: size.width * 0.12),
        Container(
          decoration: BoxDecoration(
            color: Pallet.orange,
            borderRadius: BorderRadius.only(
              topLeft: Radius.circular(20),
              topRight: Radius.circular(20),
              bottomLeft: Radius.zero,
              bottomRight: Radius.circular(20),
            ),
          ),
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: AutoSizeText(
              widget.comment.text,
              textAlign: TextAlign.center,
              style: TextStyle(
                color: Colors.white,
                fontSize: 15,
              ),
            ),
          ),
        ),
        Expanded(child: Container()),
      ],
    );
  }
  getLoggedInUserComment(User commenter, Size size){
    return Row(
      children: [
        Expanded(child: Container()),
        Container(
          decoration: BoxDecoration(
            color: Colors.blueGrey,
            borderRadius: BorderRadius.only(
              topLeft: Radius.circular(20),
              topRight: Radius.circular(20),
              bottomLeft: Radius.circular(20),
              bottomRight: Radius.zero,
            ),
          ),
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: AutoSizeText(
              widget.comment.text,
              textAlign: TextAlign.center,
              style: TextStyle(
                color: Colors.white,
                fontSize: 15,
              ),
            ),
          ),
        ),
        CircleUserPic(user: commenter, height: size.width * 0.15, width: size.width * 0.12),

      ],
    );
  }
}

