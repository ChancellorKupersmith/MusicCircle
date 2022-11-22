import 'package:flutter/material.dart';
import 'package:music_circle/Resources/Colors.dart';

import '../ImageButtons.dart';
class EventsList extends StatelessWidget {
  final List<Widget> eventBtnList;
  const EventsList({Key key, this.eventBtnList}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Padding(
      padding: const EdgeInsets.fromLTRB(10.0, 2.0, 8.0, 8.0),
      child: Container(
        height: size.height * 0.2,
        width: size.width,
        child: ListView(
            scrollDirection: Axis.horizontal,
            shrinkWrap: true,
            children: eventBtnList
        ),
      ),
    );
  }
}

class EventsListHorizontal extends StatelessWidget {
  final List<Widget> eventsList;
  final String header;
  const EventsListHorizontal({Key key, this.eventsList, this.header}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Align(
      alignment: Alignment.topLeft,
      child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.fromLTRB(8.0, 0.0, 0.0, 8.0),
            child: Align(
              alignment: Alignment.topLeft,
              child: Text(
                header,
                textAlign: TextAlign.left,
                style: TextStyle(
                    fontSize: 22,
                    fontWeight: FontWeight.w600,
                    color: Pallet.darkBlue
                ),
              ),
            ),
          ),
          SizedBox(
            height: size.height * 0.4,
            width: size.width,
            child: Container(
              decoration: BoxDecoration(
                  borderRadius: BorderRadius.all(Radius.circular(36.0))
              ),
              child: ListView(
                scrollDirection: Axis.horizontal,
                children: eventsList,
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class EventsListHorizontalGlobal extends StatelessWidget {
  final List<Widget> eventList;
  final String header;
  final Color headerColor;
  const EventsListHorizontalGlobal({Key key, @required this.eventList, @required this.header, @required this.headerColor}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Align(
      alignment: Alignment.topLeft,
      child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.fromLTRB(10.0, 4.0, 0.0, 8.0),
            child: Align(
              alignment: Alignment.topLeft,
              child: Text(
                header,
                textAlign: TextAlign.left,
                style: TextStyle(
                    fontSize: 22,
                    fontWeight: FontWeight.w600,
                    color: headerColor
                ),
              ),
            ),
          ),
          SizedBox(
            height: size.width * 0.5,
            width: size.width,
            child: Container(
              decoration: BoxDecoration(
                  borderRadius: BorderRadius.all(Radius.circular(36.0))
              ),
              child: ListView(
                scrollDirection: Axis.horizontal,
                children: eventList,
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class ProfileEventsList extends StatelessWidget {
  final List<Widget> eventsList;
  const ProfileEventsList({Key key, this.eventsList}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Container(
      decoration: BoxDecoration(
        borderRadius: BorderRadius.all(Radius.circular(20)),
      ),
      child: SizedBox(
        width: size.width,
        height: size.height * 0.33,
        child: ListView(
          scrollDirection: Axis.horizontal,
          children: eventsList,
        ),
      ),
    );
  }
}


