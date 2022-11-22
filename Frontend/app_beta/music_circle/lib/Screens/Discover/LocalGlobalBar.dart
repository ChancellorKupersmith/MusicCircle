// import 'package:flutter/material.dart';
//
// import 'package:music_circle/Resources/Colors.dart';
// import 'DiscoverPageConfig.dart';
//
//
// class LocalGlobalBar extends StatelessWidget {
//   const LocalGlobalBar({Key key}) : super(key: key);
//
//   @override
//   Widget build(BuildContext context) {
//     return Row(
//       children: [
//         Expanded(
//           child: Padding(
//             padding: const EdgeInsets.fromLTRB(3.0, 20.0, 1.0, 0.0),
//             child: SizedBox(
//               height: 50.0,
//               child: TextButton(onPressed: ()=>{
//                 DiscoverBody.of(context).state.setLocalPage(true),
//                 print(DiscoverBody.of(context).state.localPage)
//               },
//                   style: ButtonStyle(
//                       backgroundColor: DiscoverBody.of(context).state.localPage ? MaterialStateProperty.all(Pallet.lightBlue) : MaterialStateProperty.all(Colors.transparent),
//                       shadowColor: MaterialStateProperty.all(Pallet.lightBlue),
//                       shape: MaterialStateProperty.all<RoundedRectangleBorder>(
//                           RoundedRectangleBorder(
//                             borderRadius: BorderRadius.circular(10.0),
//                           )
//                       )
//                   ),
//                   child: Text(
//                     'Local',
//                     style: TextStyle(
//                       fontSize: 20.0,
//                       color: DiscoverBody.of(context).state.localPage ? Colors.white : Pallet.lightBlue,
//                     ),
//                   )
//               ),
//             ),
//           ),
//         ),
//         Expanded(
//           child: Padding(
//             padding: const EdgeInsets.fromLTRB(1.0, 20.0, 3.0, 0.0),
//             child: SizedBox(
//               height: 50.0,
//               child: TextButton(onPressed: ()=>{
//                 DiscoverBody.of(context).state.setLocalPage(false),
//                 print(DiscoverBody.of(context).state.localPage)
//               },
//                   style: ButtonStyle(
//                       backgroundColor: !(DiscoverBody.of(context).state.localPage) ? MaterialStateProperty.all(Pallet.lightBlue) : MaterialStateProperty.all(Colors.transparent),
//                       shadowColor: MaterialStateProperty.all(Pallet.lightBlue),
//                       shape: MaterialStateProperty.all<RoundedRectangleBorder>(
//                           RoundedRectangleBorder(
//                             borderRadius: BorderRadius.circular(10.0),
//                           )
//                       )
//                   ),
//                   child: Text(
//                     'Global',
//                     style: TextStyle(
//                       fontSize: 20.0,
//                       color: !(DiscoverBody.of(context).state.localPage) ? Colors.white : Pallet.lightBlue,
//                     ),
//                   )
//               ),
//             ),
//           ),
//         ),
//       ],
//     );
//   }
// }

//
// class LocalGlobalBar extends StatefulWidget {
//   const LocalGlobalBar({Key key}) : super(key: key);
//
//   @override
//   _LocalGlobalBarState createState() => _LocalGlobalBarState();
// }
//
// class _LocalGlobalBarState extends State<LocalGlobalBar> {
//   bool _localPage;
//   @override
//   Widget build(BuildContext context) {
//     _localPage = DiscoverBody.of(context).parentState.localPage;
//     return Row(
//       children: [
//         Expanded(
//           child: Padding(
//             padding: const EdgeInsets.fromLTRB(3.0, 20.0, 1.0, 0.0),
//             child: SizedBox(
//               height: 50.0,
//               child: TextButton(onPressed: ()=>{
//                 if(!_localPage){
//                   setState(() {
//                     DiscoverBody.of(context).parentState.setState(() {
//                       DiscoverBody.of(context).parentState.localPage = true;
//                     });
//                     // _localPage = true;
//                     // _recommendations = LocalRecommendations();
//                   })
//                 }
//               },
//                   style: ButtonStyle(
//                       backgroundColor: _localPage ? MaterialStateProperty.all(Pallet.lightBlue) : MaterialStateProperty.all(Colors.transparent),
//                       shadowColor: MaterialStateProperty.all(Pallet.lightBlue),
//                       shape: MaterialStateProperty.all<RoundedRectangleBorder>(
//                           RoundedRectangleBorder(
//                             borderRadius: BorderRadius.circular(10.0),
//                           )
//                       )
//                   ),
//                   child: Text(
//                     'Local',
//                     style: TextStyle(
//                       fontSize: 20.0,
//                       color: _localPage ? Colors.white : Pallet.lightBlue,
//                     ),
//                   )
//               ),
//             ),
//           ),
//         ),
//         Expanded(
//           child: Padding(
//             padding: const EdgeInsets.fromLTRB(1.0, 20.0, 3.0, 0.0),
//             child: SizedBox(
//               height: 50.0,
//               child: TextButton(onPressed: ()=>{
//                 if(_localPage){
//                   setState(() {
//                     DiscoverBody.of(context).parentState.localPage = false;
//                     // _localPage = !_localPage;
//                     // _recommendations = GlobalRecommendations();
//                   })
//                 }
//               },
//                   style: ButtonStyle(
//                       backgroundColor: !_localPage ? MaterialStateProperty.all(Pallet.lightBlue) : MaterialStateProperty.all(Colors.transparent),
//                       shadowColor: MaterialStateProperty.all(Pallet.lightBlue),
//                       shape: MaterialStateProperty.all<RoundedRectangleBorder>(
//                           RoundedRectangleBorder(
//                             borderRadius: BorderRadius.circular(10.0),
//                           )
//                       )
//                   ),
//                   child: Text(
//                     'Global',
//                     style: TextStyle(
//                       fontSize: 20.0,
//                       color: !_localPage ? Colors.white : Pallet.lightBlue,
//                     ),
//                   )
//               ),
//             ),
//           ),
//         ),
//       ],
//     );
//   }
// }
