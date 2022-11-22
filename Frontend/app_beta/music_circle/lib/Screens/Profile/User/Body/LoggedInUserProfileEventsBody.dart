import 'package:flutter/material.dart';
import 'package:music_circle/Entities/Event.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/Screens/Events/EventPage.dart';
import 'package:music_circle/UI_Tools/ImageButtons.dart';
import 'package:music_circle/UI_Tools/ListWidgets/EventsList.dart';
import 'package:music_circle/Entities/User.dart';

class LoggedInUserProfileEvents extends StatelessWidget {
  const LoggedInUserProfileEvents({Key key}) : super(key: key);

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
              //color: Pallet.darkBlue.withOpacity(0.1),
              color: Pallet.darkBlue,
              borderRadius: BorderRadius.only(
                topLeft: Radius.circular(40),
                topRight: Radius.circular(10),
              ),
              child: Container(
                height: size.height * 7/6,
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
                padding: const EdgeInsets.fromLTRB(10.0, 4.0, 0.0, 0.0),
                child: Text('My Events',
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 30,
                  ),
                ),
              ),
            ),
            Positioned(
                top: 40,
                child: ProfilePerformingEvents(),
            )
          ],
        )
      ],
    );
  }
}

class ProfilePerformingEvents extends StatelessWidget {
  const ProfilePerformingEvents({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return SizedBox(
      child: Column(
        children: [
          SizedBox(
            width: size.width,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text(
                    'Performing',
                    style: TextStyle(
                      color: Pallet.softBlue,
                      fontWeight: FontWeight.bold,
                      fontSize: 14,
                    ),
                  ),
                ),
                InkWell(
                  onTap: (){

                  },
                  child: Row(
                    children: [
                      Text('See all',
                        style: TextStyle(
                          color: Pallet.softBlue,
                        ),
                      ),
                      Icon(
                          Icons.arrow_forward_ios_rounded,
                          color: Pallet.softBlue,
                      )
                    ],
                  )
                )
              ],
            ),
          ),

          ProfileEventsList( eventsList: getUsersPerformingEvents(context, 'userID', size),),

          SizedBox(
            width: size.width,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text(
                    'Hosting',
                    style: TextStyle(
                      color: Pallet.softBlue,
                      fontWeight: FontWeight.bold,
                      fontSize: 14,
                    ),
                  ),
                ),
                InkWell(
                    onTap: (){

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
                    )
                )
              ],
            ),
          ),

          ProfileEventsList( eventsList: getUsersCreatedEvents(context, 'userID', size),),

          SizedBox(
            width: size.width,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text(
                    'Attending',
                    style: TextStyle(
                      color: Pallet.softBlue,
                      fontWeight: FontWeight.bold,
                      fontSize: 14,
                    ),
                  ),
                ),
                InkWell(
                    onTap: (){

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
                )
              ],
            ),
          ),

          ProfileEventsList( eventsList: getUsersAttendingEvents(context, 'userID', size),),
        ],
      ),
    );
  }
}

//TODO
List<Widget> getUsersPerformingEvents(BuildContext context, String userID, Size size){
  List<Event> events = [];
  List<Widget> btnList = [];

  //TODO: get user's events by id from database
  //get user events

  //test event list
  Image eventImage = Image.asset(
      'lib/Resources/Images/user3_example_pic.jpg',
    fit: BoxFit.cover,
  );
  User creator = new User();
  creator.userImage = Image.asset(
    'lib/Resources/Images/user5_example_pic.jpg',
    fit: BoxFit.cover,
  );
  for(int i=0; i<20; i++){
    Event event = new Event();
    event.name = 'Event ' + i.toString();
    event.city = 'Ames';
    event.state = 'IA';
    event.dateTime = DateTime.now();
    event.eventImage = eventImage;
    event.creator = creator;
    events.add(event);
  }

  events.forEach((event) {
    btnList.add(Padding(
      padding: const EdgeInsets.all(8.0),
      child: InkWell(
          child: ImageButtonSquareNotSoRounded(size.height * 0.3, size.width * 0.5, '', event.eventImage),
        onTap: (){
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context) =>
                EventPage(event: event),
            ),
          );
        },
      ),
    )
    );
  });
  return btnList;
}
//TODO
List<Widget> getUsersAttendingEvents(BuildContext context, String userID, Size size){
  List<Event> events = [];
  List<Widget> btnList = [];

  //TODO: get user's events by id from database
  //get user events

  //test event list
  Image eventImage = Image.asset(
    'lib/Resources/Images/test_event_pic.jpg',
    fit: BoxFit.cover,
  );
  User creator = new User();
  creator.userImage = Image.asset(
    'lib/Resources/Images/user5_example_pic.jpg',
    fit: BoxFit.cover,
  );
  for(int i=0; i<20; i++){
    Event event = new Event();
    event.name = 'Event ' + i.toString();
    event.city = 'Ames';
    event.state = 'IA';
    event.dateTime = DateTime.now();
    event.eventImage = eventImage;
    event.creator = creator;
    events.add(event);
  }

  events.forEach((event) {
    btnList.add(Padding(
      padding: const EdgeInsets.all(8.0),
      child: InkWell(
        child: ImageButtonSquareNotSoRounded(size.height * 0.3, size.width * 0.5, '', event.eventImage),
        onTap: (){
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context) =>
                EventPage(event: event),
            ),
          );
        },
      ),
    )
    );
  });
  return btnList;
}
//TODO
List<Widget> getUsersCreatedEvents(BuildContext context, String userID, Size size){
  List<Event> events = [];
  List<Widget> btnList = [];

  //TODO: get user's events by id from database
  //get user events

  //test event list
  Image eventImage = Image.asset(
    'lib/Resources/Images/test_event_pic.jpg',
    fit: BoxFit.cover,
  );
  User creator = new User();
  creator.userImage = Image.asset(
    'lib/Resources/Images/user5_example_pic.jpg',
    fit: BoxFit.cover,
  );
  for(int i=0; i<20; i++){
    Event event = new Event();
    event.name = 'Event ' + i.toString();
    event.city = 'Ames';
    event.state = 'IA';
    event.dateTime = DateTime.now();
    event.eventImage = eventImage;
    event.creator = creator;
    events.add(event);
  }

  events.forEach((event) {
    btnList.add(Padding(
      padding: const EdgeInsets.all(8.0),
      child: InkWell(
        child: ImageButtonSquareNotSoRounded(size.height * 0.3, size.width * 0.5, '', event.eventImage),
        onTap: (){
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context) =>
                EventPage(event: event),
            ),
          );
        },
      ),
    )
    );
  });
  return btnList;
}
