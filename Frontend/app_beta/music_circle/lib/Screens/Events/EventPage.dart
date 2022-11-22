import 'package:flutter/material.dart';
import 'package:music_circle/Entities/Event.dart';
import 'package:music_circle/Entities/User.dart';
import 'package:music_circle/Screens/Events/EventAppBar.dart';

class EventPage extends StatefulWidget {
  final Event event;
  const EventPage({Key key, @required this.event}) : super(key: key);
  static final String routeName = '/event-page';

  @override
  _EventPageState createState() => _EventPageState();
}

class _EventPageState extends State<EventPage> {
  Event event;
  updateEventInfo(Event e){
    setState(() {
      event = getEventInfo(widget.event);
    });
  }
  int bodyIndex = 0;
  setBodyIndex(int index){
    setState(() {
      bodyIndex = index;
    });
  }
  @override
  Widget build(BuildContext context) {
    if(event == null){
      event = widget.event;
    }
    Size size = MediaQuery.of(context).size;
      return Scaffold(
        body: NestedScrollView(
          headerSliverBuilder: (BuildContext context, bool innerBoxIsScrolled){
            return [
              SliverOverlapAbsorber(
                handle: NestedScrollView.sliverOverlapAbsorberHandleFor(context),
                sliver: EventAppBar(minHeight: size.height * 0.15, event: event),
              )
            ];
          },
          body: Builder(
            builder: (BuildContext context) => CustomScrollView(
              slivers: [
                SliverOverlapInjector(handle: NestedScrollView.sliverOverlapAbsorberHandleFor(context)),
                SliverToBoxAdapter(
                  child: EventPageBody(index: bodyIndex),
                ),
              ],
            ),
          ),
        ),
      );
  }


  Event getEventInfo(Event event){
    //Test event
    event.state = 'Iowa';
    event.city = 'Ames';
    event.dateTime = DateTime.now();
    event.streetAddress = 'Somewhere Dr.';
    event.description = 'Small concert at house party';
    event.zipcode = '50014';
    event.creator = new User();
    event.creator.name = 'Event Creator';
    event.creator.userImage = Image.asset('lib/Resources/Images/user3_example_pic.jpg');
    for(int i=0; i<20; i++){
      User performer = new User();
      performer.name = 'Performer ' + i.toString();
      performer.userImage = Image.asset('lib/Resources/Images/user4_example_pic.jpg');
      event.usersPerforming.add(performer);
      User attender = new User();
      attender.name = 'Attender ' + i.toString();
      attender.userImage = Image.asset('lib/Resources/Images/user2_example_pic.jpg');
      event.usersAttending.add(attender);
    }
    return event;
  }
}

class EventPageBody extends StatelessWidget {
  final int index;
  const EventPageBody({Key key, @required this.index}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return getPage(size);
  }

  getPage(Size size){
    Widget body;
    switch(index){
      case 0:{
        //Event Page Feed
        body = SizedBox(
          height: size.height,
          child: ListView(
            children: [
              Container(),
            ],
          ),
        );
      }
      break;
      case 1:{
        //Event Info
        body = SizedBox(
          height: size.height,
          child: ListView(
            children: [
              Container(),
            ],
          ),
        );
      }
      break;
      default:{
        body = SizedBox(
          height: size.height,
          child: ListView(
            children: [
              Container(),
            ],
          ),
        );
      }
      break;
    }
    return body;
  }
}

