import 'dart:math';
import 'package:auto_size_text/auto_size_text.dart';
import 'package:flutter/material.dart';
import 'package:music_circle/Resources/Colors.dart';

import 'package:music_circle/Entities/Event.dart';
import 'package:music_circle/Entities/User.dart';
import 'package:music_circle/UI_Tools/BackButton.dart';
import 'package:music_circle/UI_Tools/ImageButtons.dart';

class EventAppBar extends StatelessWidget {
  final double minHeight;
  final Event event;
  const EventAppBar({Key key, @required this.minHeight, @required this.event}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return SliverPersistentHeader(
      delegate: EventAppBarDelegate(expandedHeight: size.height * 0.6, minHeight: minHeight, event: event),
      pinned: true,
    );
  }
}

class EventAppBarDelegate extends SliverPersistentHeaderDelegate{
  final double expandedHeight, minHeight;
  final Event event;

  EventAppBarDelegate({@required this.expandedHeight, @required this.minHeight, @required this.event});

  @override
  Widget build(BuildContext context, double shrinkOffset, bool overlapsContent) {
    Size size = MediaQuery.of(context).size;
    double bottom = expandedHeight - shrinkOffset;
    double top = min(minHeight, bottom);

    return Stack(
      fit: StackFit.expand,
      children: [
        //Background pic
        Positioned(
          top: 0 - shrinkOffset,
          left: 0,
          child: buildBackground(shrinkOffset, event, size),
        ),
        Positioned(
          top: shrinkOffset * -1/5 + 10,
          left: 10,
          child: CircleBackButton(backgroundColor: Colors.blueGrey.withOpacity(0.99), iconColor: Colors.white)
        ),
        Positioned(
          bottom: size.height * 0.15,
          child: buildAttendPerformBtns(shrinkOffset, event, size),
        ),
        //Creator profile pic
        Positioned(
          // top: bottom + size.height * 0.076,
          top: expandedHeight - size.height * 0.36 - shrinkOffset,
          left: 0,
          right: 0,
          child: buildCreatorPic(shrinkOffset, event, size),
        ),
        Positioned(
            bottom: size.height * 0.1,
            left: 0,
            right: 0,
            child: Padding(
              padding: const EdgeInsets.fromLTRB(0,0,0,2),
              child: Text(
                'Event name',
                textAlign: TextAlign.center,
                style: TextStyle(
                  color: Pallet.darkBlue,
                  fontSize: 40,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
        ),
        Positioned(
          bottom: size.height * 0.15,
          left: 0,
          right: 0,
          child: Padding(
            padding: EdgeInsets.fromLTRB(size.width * 0.4975, 0, size.width * 0.4975, 0),
            child: Container(
              height: 28,
              width: 3,
              color: Colors.blueGrey.withOpacity(0.2),
            ),
          ),
        ),
        Positioned(
          bottom: 0 + size.height * 0.04,
          left: size.width * 1/3,
          right: size.width * 1/3,
          child: buildAttendBtn(shrinkOffset, event, size),
        ),
        Positioned(
          bottom: 0 + size.height * 0.04,
          right: 0,
          child: buildGoogleMapsButton(event, size),
        ),
        Positioned(
          bottom: 0 + size.height * 0.04,
          left: 0,
          child: buildDateTimeWidget(event, size),
        ),
      ],
    );
  }

  @override
  double get maxExtent => expandedHeight;

  @override
  double get minExtent => minHeight;

  @override
  bool shouldRebuild(covariant SliverPersistentHeaderDelegate oldDelegate) => true;

  appear(double shrinkOffset) => shrinkOffset / expandedHeight;
  disappear(double shrinkOffset) => 1 - shrinkOffset / expandedHeight;
  buildBackground(double shrinkOffset, Event event, Size size)=> Opacity(
    opacity: disappear(shrinkOffset),
    child: SizedBox(height: size.height * 1/3, width: size.width,child: event.eventImage),
  );

  buildDateTimeWidget(Event event, Size size) => SizedBox(
    width: size.width * 1/3,
    child: Padding(
      padding: const EdgeInsets.fromLTRB(0,0,0,12),
      child: AutoSizeText(
        event.dateTime.month.toString() + '/' + event.dateTime.day.toString() + '/' + event.dateTime.year.toString(),
        textAlign: TextAlign.center,
        style: TextStyle(
          fontSize: 16,
        ),
      ),
    ),
  );

  buildGoogleMapsButton(Event event, Size size) => SizedBox(
    width: size.width * 1/3,
    height: size.height * 0.055,
    child: Padding(
      padding: const EdgeInsets.fromLTRB(2,0,0,0),
      child: Row(
        children: [
          Expanded(
            child: AutoSizeText(
              event.city + ', ' + event.state,
              textAlign: TextAlign.right,
              style: TextStyle(
                fontSize: 16,
              ),
            ),
          ),
          Padding(
            padding: const EdgeInsets.fromLTRB(0,0,0,16),
            child: Material(
              child: InkWell(
                onTap: (){

                },
                customBorder: CircleBorder(side: BorderSide.none),
                child: Icon(
                  Icons.location_on_outlined,
                  color: Colors.blueGrey,
                  size: 40,
                ),
              ),
            ),
          ),
        ],
      ),
    ),
  );

  buildAttendPerformBtns(double shrinkOffset, Event event, Size size) => SizedBox(
    width: size.width,
    child: Row(
      children: [
        Expanded(
          child: Material(
            child: InkWell(
              child: SizedBox(
                height: size.height *0.12,
                child: Column(
                  children: [
                    Padding(
                      padding: const EdgeInsets.fromLTRB(0,40,20,0),
                      child: Text(
                        'Attending',
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.fromLTRB(0,0,20,0),
                      child: Text(
                        event.usersAttending.length.toString(),
                      ),
                    ),
                  ],
                ),
              ),
              onTap: (){

              },
            ),
          ),
        ),
        Expanded(
          child: Material(
            child: InkWell(
              child: SizedBox(
                height: size.height *0.12,
                child: Column(
                  children: [
                    Padding(
                      padding: const EdgeInsets.fromLTRB(20,40,0,0),
                      child: Text(
                        'Artists',
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.fromLTRB(20,0,0,0),
                      child: Text(
                        event.usersPerforming.length.toString(),
                      ),
                    ),
                  ],
                ),
              ),
              onTap: (){

              },
            ),
          ),
        ),
      ],
    ),
  );

  buildAttendBtn(double shrinkOffset, Event event, Size size) => SizedBox(
    height: size.height * 0.055,
    child: Material(
      borderRadius: BorderRadius.circular(20),
      color: Colors.blueGrey,
      child: InkWell(
        onTap: (){

        },
        borderRadius: BorderRadius.circular(20),
        child: Padding(
          padding: const EdgeInsets.fromLTRB(0,12,0,0),
          child: Text(
            'Attend',
            textAlign: TextAlign.center,
            style: TextStyle(
              color: Colors.white,
              fontSize: 20,
              fontWeight: FontWeight.w500,
            ),
          ),
        ),
      ),
    ),
  );

  buildCreatorPic(double shrinkOffset, Event event, Size size) => Opacity(
      opacity: disappear(shrinkOffset),
    child: Padding(
      padding: const EdgeInsets.all(8.0),
      child: SizedBox(
        height: size.width * 1/3, width: size.width * 1/3,
        child: Container(
          height: size.width * 1/3 * 0.9,
          width: size.width * 1/3 * 0.9,
          decoration: BoxDecoration(
            color: Colors.transparent,
            border: Border.all(
              color: Pallet.softBlue,
              width: 3.5,
            ),
            shape: BoxShape.circle,
            //borderRadius: BorderRadius.circular(100),
          ),
          child: SizedBox(height: size.width * 0.3, width: size.width * 0.3, child: ImageButtonCircle(size.width * 0.3, size.width * 0.3, 'label', event.creator.userImage)),
        ),
      ),
    ),
  );
}
