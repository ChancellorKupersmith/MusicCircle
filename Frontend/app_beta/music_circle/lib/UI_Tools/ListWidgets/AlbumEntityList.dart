import 'package:flutter/material.dart';
import 'package:music_circle/Resources/Colors.dart';

import '../ImageButtons.dart';


class AlbumListHorizontal extends StatelessWidget {
  final List<ImageButtonSquareRounded> albumList;
  final String header;
  const AlbumListHorizontal({Key key, this.albumList, this.header}) : super(key: key);

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
                    color: Pallet.lightBlue,
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
                children: albumList,
              ),
            ),
          ),
        ],
      ),
    );
  }
}
