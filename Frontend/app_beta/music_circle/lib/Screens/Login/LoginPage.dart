import 'package:flutter/material.dart';
import 'package:lit_firebase_auth/lit_firebase_auth.dart';
import 'package:music_circle/Screens/Discover/DiscoverPage.dart';
import 'package:music_circle/Screens/Login/LoginUI.dart';
import 'package:music_circle/Screens/Login/Register.dart';
import 'package:music_circle/UI_Tools/BackgroundPainter.dart';
import 'package:animations/animations.dart';
import 'SignIn.dart';
class LoginPage extends StatefulWidget {
  const LoginPage({Key key}) : super(key: key);

  static final String routeName = '/login-page';
  static MaterialPageRoute get route => MaterialPageRoute(
    builder: (context) => const LoginPage(),
  );

  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> with SingleTickerProviderStateMixin{
  AnimationController _animationController;
  ValueNotifier<bool> showSignInPage = ValueNotifier<bool>(true);

  @override
  void initState(){
    _animationController = AnimationController(vsync: this, duration: Duration(seconds: 2));
        super.initState();
  }
  @override
  void dispose() {
    _animationController.dispose();
    super.dispose();
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: LitAuth.custom(
        onAuthSuccess: () => Navigator.of(context).pushReplacement(DiscoverPage.route),
        child: Stack(
          children: [
            SizedBox.expand(
              child: CustomPaint(
                painter: BackgroundPainter(
                  animation: _animationController.view,
                ),
              ),
            ),
            Center(
              child: ConstrainedBox(
                constraints: BoxConstraints(maxWidth: 800),
                child: ValueListenableBuilder<bool>(
                  valueListenable: showSignInPage,
                  builder: (context, value, child){
                    return PageTransitionSwitcher(
                      reverse: !value,
                      duration: Duration(milliseconds: 800),
                      transitionBuilder: (child, animation, secondaryAnimation){
                        return SharedAxisTransition(
                          animation: animation,
                          secondaryAnimation: secondaryAnimation,
                          transitionType: SharedAxisTransitionType.vertical,
                          fillColor: Colors.transparent,
                          child: child,
                        );
                      },
                      child: value ? SignIn(
                        onRegisterClicked: (){
                          context.resetSignInForm();
                          showSignInPage.value = false;
                          _animationController.forward();
                        },
                      ) : Register(
                        onSignInPressed: (){
                          context.resetSignInForm();
                          showSignInPage.value = true;
                          _animationController.reverse();
                        },
                      ),
                    );
                  },
                ),
              ),
            ),
            //Center(child: RaisedButton(onPressed: (){_animationController.forward(from: 0);}, child: Text('click me'),)),
          ],
        ),
      ),
    );
  }
}
