import 'package:flutter/material.dart';
import 'package:music_circle/Resources/Colors.dart';

class FollowerImageButton extends StatelessWidget {
  final Image pic;
  final String artistName;
  const FollowerImageButton({Key key, this.artistName, this.pic}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.fromLTRB(2, 0, 16.0, 2),
      child: Column(
        children: [
          Container(
            height: 60,
            width: 60,
            decoration: BoxDecoration(
              shape: BoxShape.circle,
              boxShadow: [
                BoxShadow(
                    color: Pallet.lightBlue.withOpacity(0.6),
                    offset: Offset(1,2),
                    spreadRadius: 0.5,
                    blurRadius: 5
                ),
                BoxShadow(
                    color: Colors.white.withOpacity(0.5), offset: Offset(-2,-3), spreadRadius: -2, blurRadius: 20
                )
              ],
              color: Pallet.darkBlue.withOpacity(0.2),
            ),
            child: Padding(
              padding: const EdgeInsets.all(2),
              child: Material(
                clipBehavior: Clip.hardEdge,
                shape: CircleBorder(),
                child: pic,
              ),
            ),
          ),
          Padding(
            padding: const EdgeInsets.fromLTRB(0, 0, 0, 0),
            child: Text(
              '',
              style: TextStyle(
                color: Pallet.darkBlue,
                fontWeight: FontWeight.w600,
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class ImageButtonCircle extends StatelessWidget {
  final double height, width;
  final Image pic;
  final String label;
  const ImageButtonCircle(this.height, this.width, this.label, this.pic);


  @override
  Widget build(BuildContext context) {
    return Container(
      height: this.height,
      width: this.width,
      child: Material(
        clipBehavior: Clip.hardEdge,
        shape: CircleBorder(),
        child: pic,
      ),
    );
  }
}

// class ImageButtonCircle extends StatelessWidget {
//   //final Uint8List pic;
//   final double height, width;
//   final Image pic;
//   final String label;
//   const ImageButtonCircle(this.height, this.width, this.label, this.pic);
//
//   @override
//   Widget build(BuildContext context) {
//     return Padding(
//       padding: const EdgeInsets.fromLTRB(16.0, 4.0, 16.0, 40.0),
//       child: Column(
//         children: [
//           // Container(
//           //   height: this.height,
//           //   width: this.width,
//           //   decoration: BoxDecoration(
//           //     shape: BoxShape.circle,
//           //     boxShadow: [
//           //       BoxShadow(
//           //           color: Pallet.lightBlue.withOpacity(0.6),
//           //           offset: Offset(5,10),
//           //           spreadRadius: 3,
//           //           blurRadius: 11
//           //       ),
//           //       BoxShadow(
//           //           color: Colors.white.withOpacity(0.5), offset: Offset(-2,-3), spreadRadius: -2, blurRadius: 20
//           //       )
//           //     ],
//           //     color: Pallet.darkBlue.withOpacity(0.4),
//           //   ),
//           //   child: Padding(
//           //     padding: const EdgeInsets.fromLTRB(14.0, 14.0, 14.0, 14.0),
//           //     child: Material(
//           //       clipBehavior: Clip.hardEdge,
//           //       shape: CircleBorder(),
//           //       child: pic,
//           //     ),
//           //   ),
//           // ),
//           Container(
//             height: this.height,
//             width: this.width,
//             child: Material(
//               clipBehavior: Clip.hardEdge,
//               shape: CircleBorder(),
//               child: pic,
//             ),
//           ),
//           // Padding(
//           //   padding: const EdgeInsets.fromLTRB(0, 2, 0, 0),
//           //   child: Text(
//           //     label,
//           //     style: TextStyle(
//           //       color: Pallet.darkBlue,
//           //       fontSize: 20,
//           //       fontWeight: FontWeight.w600,
//           //     ),
//           //   ),
//           // ),
//         ],
//       ),
//     );
//   }
  // @override
  // Widget build(BuildContext context) {
  //   return Container(
  //     height: this.height,
  //     width: this.width,
  //     decoration: BoxDecoration(
  //       color: Pallet.softBlue,
  //       boxShadow: [
  //         BoxShadow(
  //             color: Pallet.darkBlue,
  //             offset: Offset(this.width + 5, this.width + 10),
  //             spreadRadius: 3,
  //             blurRadius: 10,
  //         )
  //       ]
  //     ),
  //     child:Column(
  //       children: [
  //         Padding(
  //           padding: const EdgeInsets.fromLTRB(0.0, 0.0, 6.0, 0.0),
  //           child: Material(
  //             shape: CircleBorder(),
  //             clipBehavior: Clip.none,
  //             color: Colors.transparent,
  //             child: pic,
  //           ),
  //         ),
  //         Padding(
  //           padding: const EdgeInsets.all(4.0),
  //           child: Text(label, style: TextStyle(color: Pallet.darkBlue),),
  //         )
  //       ],
  //     ),
  //   );
  // }
// }

class ImageButtonSquareRounded extends StatelessWidget {
  final double height, width;
  final Image pic;
  final String label;
  const ImageButtonSquareRounded(this.height, this.width, this.label, this.pic);

  @override
  Widget build(BuildContext context) {
    return Container(
      height: this.height,
      width: this.width,
      decoration: BoxDecoration(
      borderRadius: BorderRadius.circular(20),
    ),
      child: Material(
        clipBehavior: Clip.hardEdge,
        borderRadius: BorderRadius.circular(20),
        child: ClipRRect(

            child: pic,
            borderRadius: BorderRadius.circular(20),
        )
      ),
    );
  }
}

class ImageButtonSquareNotSoRounded extends StatelessWidget {
  final double height, width;
  final Image pic;
  final String label;
  const ImageButtonSquareNotSoRounded(this.height, this.width, this.label, this.pic);

  @override
  Widget build(BuildContext context) {
    return Container(
      height: this.height,
      width: this.width,
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(10),
      ),
      child: Material(
          clipBehavior: Clip.hardEdge,
          borderRadius: BorderRadius.circular(10),
          child: ClipRRect(

            child: pic,
            borderRadius: BorderRadius.circular(10),
          )
      ),
    );
  }
}