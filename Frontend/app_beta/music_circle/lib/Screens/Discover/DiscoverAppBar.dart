// import 'dart:math';
//
// import 'package:flutter/material.dart';
// import 'package:music_circle/Resources/Colors.dart';
// import 'package:music_circle/Screens/Discover/DiscoverPage.dart';
// import 'package:music_circle/Screens/Discover/Local/LocalDiscover.dart';
// import 'package:music_circle/Screens/Discover/LocalGlobalBar.dart';
// import 'package:music_circle/Screens/Profile/User/LoggedInUserProfilePage.dart';
// import 'package:music_circle/UI_Tools/SearchBar.dart';
//
// class MainAppBar extends StatelessWidget {
//   const MainAppBar({Key key}) : super(key: key);
//
//   @override
//   Widget build(BuildContext context) {
//     return Row(
//       children: [
//         Expanded(
//           child: Row(
//             mainAxisAlignment: MainAxisAlignment.start,
//             children: <Widget>[
//               IconButton(icon: Icon(
//                   Icons.menu
//               ), onPressed: () => {
//
//               },
//                 color: Colors.white,
//               ),
//               Padding(
//                 padding: const EdgeInsets.fromLTRB(10.0, 0.0, 0.0, 0.0),
//                 child: Text(
//                   'MusicCircle',
//                   style: TextStyle(
//                     color: Colors.white,
//                   ),
//                 ),
//               )
//             ],
//           ),
//         ),
//         Expanded(
//           child: Row(
//             mainAxisAlignment: MainAxisAlignment.end,
//             children: <Widget>[
//               Padding(
//                 padding: const EdgeInsets.all(0.0),
//                 child: IconButton(icon: Icon(
//                   Icons.add,
//                 ), onPressed: ()=>{
//
//                 },
//                   color: Colors.white,
//                 ),
//               ),
//               IconButton(icon: Icon(
//                   Icons.person
//               ), onPressed: () => {
//                 Navigator.of(context).pushNamed(LoggedInUserProfilePage.routeName)
//               },
//                 color: Colors.white,
//               ),
//               IconButton(icon: Icon(
//                   Icons.more_vert
//               ), onPressed: () => {
//
//               },
//                 color: Colors.white,
//               ),
//             ],
//           ),
//         )
//       ],
//     );
//   }
// }
//
//
// class CustomSliverAppBar extends StatelessWidget {
//   const CustomSliverAppBar({Key key}) : super(key: key);
//
//   @override
//   Widget build(BuildContext context) {
//     Size size = MediaQuery.of(context).size;
//     return SliverPersistentHeader(
//       floating: true,
//       delegate: _CustomSliverAppBarDelegate(expandedHeight: 200),
//     );
//   }
// }
//
// class _CustomSliverAppBarDelegate extends SliverPersistentHeaderDelegate{
//   final double expandedHeight;
//
//   const _CustomSliverAppBarDelegate({@required this.expandedHeight});
//
//   @override
//   Widget build(BuildContext context, double shrinkOffset, bool overlapsContent) {
//     final top = 200 - shrinkOffset - 30;
//
//     return Stack(
//       clipBehavior: Clip.none,
//       fit: StackFit.expand,
//       children: [
//         buildBackground(shrinkOffset),
//         buildShrinkAppBar(shrinkOffset),
//         Positioned(
//           top: 80 - shrinkOffset,
//             left: 0,
//             right: 0,
//             child: LocalGlobalBar()
//         ),
//         Positioned(
//           top: top,
//           left: 0,
//           right: 0,
//           child: buildFloating(shrinkOffset),
//         ),
//
//       ],
//     );
//   }
//
//   @override
//   double get maxExtent => expandedHeight;
//
//   @override
//   double get minExtent => 0;
//
//   @override
//   bool shouldRebuild(covariant SliverPersistentHeaderDelegate oldDelegate) {
//     return maxExtent != oldDelegate.maxExtent ||
//         minExtent != oldDelegate.minExtent;
//   }
//
//   buildShrinkAppBar(double shrinkOffset) {
//     return Opacity(
//       opacity: disappear(shrinkOffset),
//       child: AppBar(
//         shape: RoundedRectangleBorder(
//                         borderRadius: BorderRadius.only(
//                           bottomLeft: Radius.circular(56),
//                           bottomRight: Radius.circular(56),
//                         )
//                     ),
//
//         automaticallyImplyLeading: false,
//         title: MainAppBar(),
//         backgroundColor: Pallet.darkBlue,
//       ),
//     );
//   }
//
//   buildBackground(double shrinkOffset){
//     return Opacity(
//       opacity: appear(shrinkOffset),
//       child: Container(
//         decoration: BoxDecoration(
//           color: Pallet.lightBlue,
//           borderRadius: BorderRadius.only(
//           bottomLeft: Radius.circular(56),
//           bottomRight: Radius.circular(56),
//           ),
//         )
//       ),
//     );
//   }
//
//   buildFloating(double shrinkOffset) {
//     return Opacity(
//       opacity: disappear(shrinkOffset),
//       child: Container(
//         margin: EdgeInsets.symmetric(horizontal: 15.0),
//         padding: EdgeInsets.symmetric(horizontal: 15.0),
//         // height: 54,
//         decoration: BoxDecoration(
//             color: Colors.white,
//             borderRadius: BorderRadius.circular(20.0),
//             boxShadow: [
//               BoxShadow(
//                 offset: Offset(0,10),
//                 blurRadius: 50,
//                 color: Pallet.lightBlue.withOpacity(0.60),
//               ),
//             ]
//         ),
//         child: Row(
//           children: [
//             Expanded(
//                 child: TextField(
//                   onChanged: (value){
//                     //print('Value: ' + value);
//                   },
//                   decoration: InputDecoration(
//                     hintText: 'Search',
//                     hintStyle: TextStyle(
//                         color: Pallet.lightBlue
//                     ),
//                     enabledBorder: InputBorder.none,
//                     focusedBorder: InputBorder.none,
//                   ),
//                 )
//             ),
//
//             IconButton(
//               icon: Icon(Icons.search),
//               onPressed: (){
//
//               },
//               iconSize: 25,
//               alignment: Alignment.centerRight,
//               splashColor: Colors.transparent,
//               splashRadius: 5.0,
//               color: Pallet.lightBlue,
//             )
//           ],
//         ),
//       ),
//     );
//   }
//
//   appear(double shrinkOffset) {
//     return shrinkOffset / expandedHeight;
//   }
//
//   disappear(double shrinkOffset) {
//     return 1 - shrinkOffset / expandedHeight;
//   }
//
// }
//
// class _DiscoveryPageHeaderDelegate extends SliverPersistentHeaderDelegate{
//   final double minHeight, maxHeight;
//   final Widget child;
//
//   _DiscoveryPageHeaderDelegate({
//     @required this.minHeight,
//     @required this.maxHeight,
//     @required this.child,
//   });
//
//   @override
//   Widget build(BuildContext context, double shrinkOffset, bool overlapsContent) {
//     return new SizedBox.expand(child: child);
//   }
//
//   @override
//   double get maxExtent => max(maxHeight, minHeight);
//
//   @override
//   double get minExtent => minHeight;
//
//   @override
//   bool shouldRebuild(_DiscoveryPageHeaderDelegate oldDelegate) {
//     return maxHeight != oldDelegate.maxHeight ||
//         minHeight != oldDelegate.minHeight ||
//         child != oldDelegate.child;
//   }
// }