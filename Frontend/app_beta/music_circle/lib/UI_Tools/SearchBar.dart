import 'package:flutter/material.dart';
import 'package:music_circle/Resources/Colors.dart';

class SearchBar extends StatefulWidget {
  final double shrinkOffset;
  const SearchBar({Key key, this.shrinkOffset}) : super(key: key);

  @override
  _SearchBarState createState() => _SearchBarState(shrinkOffset);
}

class _SearchBarState extends State<SearchBar> {
  double shrinkOffset;
  _SearchBarState(this.shrinkOffset);
  @override
  Widget build(BuildContext context) {
    return Stack(
      children:[

        Positioned(
            top: 200 - shrinkOffset - 60/2,
            left: 0,
            right: 0,
            child: Container(
              margin: EdgeInsets.symmetric(horizontal: 15.0),
              padding: EdgeInsets.symmetric(horizontal: 15.0),
              height: 54,
              decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius: BorderRadius.circular(20.0),
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
                          //print('Value: ' + value);
                        },
                        decoration: InputDecoration(
                          hintText: 'Search',
                          hintStyle: TextStyle(
                              color: Pallet.lightBlue
                          ),
                          enabledBorder: InputBorder.none,
                          focusedBorder: InputBorder.none,
                        ),
                      )
                  ),

                  IconButton(
                    icon: Icon(Icons.search),
                    onPressed: (){

                    },
                    iconSize: 25,
                    alignment: Alignment.centerRight,
                    splashColor: Colors.transparent,
                    splashRadius: 5.0,
                    color: Pallet.lightBlue,
                  )
                ],
              ),
            )
        ),],
    );
  }
}

