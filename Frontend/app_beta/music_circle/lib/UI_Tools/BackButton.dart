import 'package:flutter/material.dart';

class CircleBackButton extends StatelessWidget {
  final Color backgroundColor, iconColor;
  const CircleBackButton({Key key, @required this.backgroundColor, @required this.iconColor}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size =  MediaQuery.of(context).size;
    return SizedBox(
      height: size.height * 0.1,
      width: size.width * 0.1,
      child: Material(
        color: backgroundColor,
        shape: CircleBorder(),
        child: BackButton(color: iconColor,),
      ),
    );
  }
}
