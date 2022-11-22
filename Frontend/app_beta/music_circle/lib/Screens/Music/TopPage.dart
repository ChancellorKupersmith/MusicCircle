import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:music_circle/Entities/AudioFile.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:music_circle/UI_Tools/BackButton.dart';

class MusicPlayerTop extends StatelessWidget {
  final AudioFile song;
  const MusicPlayerTop({@required this.song, Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return SizedBox(
      height: size.height * 0.6,
      width: size.width,
      child: Stack(
        children: [
          buildBackground(size),
          Positioned(
            left: 10,
            top: 11,
            child: Container(
              height: size.height * 0.1,
              width: size.width * 0.1,
              decoration: BoxDecoration(
                color: Colors.white.withOpacity(0.9),
                shape: BoxShape.circle,
                boxShadow: [
                  BoxShadow(
                    color: Pallet.darkBlue.withOpacity(0.4),
                    offset: Offset(3, 5),
                    spreadRadius: 1,
                    blurRadius: 10,
                  ),
                  BoxShadow(
                    color: Colors.white.withOpacity(0.99),
                    offset: Offset(-3, -4),
                    spreadRadius: -2,
                    blurRadius: 20,
                  ),
                ],
              ),
              child: Container(
                decoration: BoxDecoration(
                  color: Colors.blueGrey.withOpacity(0.2),
                  shape: BoxShape.circle,
                ),


                  child: CircleBackButton(backgroundColor: Colors.white.withOpacity(0.755), iconColor: Pallet.darkBlue.withOpacity(0.8))),
            ),
          ),
        ],
      ),
    );
  }

  buildBackground(Size size){
    return SizedBox(
      height: size.height * 0.6,
      width: size.width,
      child: Material(
        child: song.songImage,
      ),
    );
  }
}
