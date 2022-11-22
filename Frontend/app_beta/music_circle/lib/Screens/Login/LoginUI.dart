import 'package:auto_size_text/auto_size_text.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:music_circle/Resources/Colors.dart';
import 'package:lit_firebase_auth/lit_firebase_auth.dart';
class LoginTitle extends StatelessWidget {
  final String title;
  const LoginTitle({@required this.title});

  @override
  Widget build(BuildContext context) {
    if(title != 'MusicCircle'){
      return Text(
        title,
        style: const TextStyle(
          color: Colors.white,
          fontWeight: FontWeight.w600,
          fontSize: 34,
        ),
      );
    }
    else{
      return AutoSizeText.rich(
        TextSpan(
          style: const TextStyle(
            color: Colors.white,
            fontWeight: FontWeight.w600,
            fontSize: 34,
          ),
          children: [
            TextSpan(
              text: 'Music',
            ),
            TextSpan(
              text: 'Circle',
              style: TextStyle(
                color: Pallet.lightBlue,
              )
            ),
          ],
        ),
      );
    }

  }
}

class SignInBar extends StatelessWidget {
  final String label;
  final VoidCallback onPressed;
  final bool isLoading;
  const SignInBar({
    Key key,
    @required this.label,
    @required this.onPressed,
    @required this.isLoading
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 16.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(
            label,
            style: TextStyle(
              fontWeight: FontWeight.w800,
              color: Pallet.darkBlue,
              fontSize: 24,
            ),
          ),
          Expanded(
              child: Center(
                child: _LoadingIndicator(isLoading: isLoading),
              )
          ),
          _RoundContinueButton(
            onPressed: onPressed,
          )
        ],
      ),
    );
  }
}

class SignUpBar extends StatelessWidget {
  final String label;
  final VoidCallback onPressed;
  final bool isLoading;
  const SignUpBar({
    Key key,
    @required this.label,
    @required this.onPressed,
    @required this.isLoading
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 16.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(
            label,
            style: TextStyle(
              fontWeight: FontWeight.w800,
              color: Colors.white,
              fontSize: 24,
            ),
          ),
          Expanded(
              child: Center(
                child: _LoadingIndicator(isLoading: isLoading),
              )
          ),
          _RoundContinueButton(
            onPressed: onPressed,
          )
        ],
      ),
    );
  }
}



class _LoadingIndicator extends StatelessWidget {
  final bool isLoading;
  const _LoadingIndicator({Key key, @required this.isLoading}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final isSubmitting = context.isSubmitting();
    return ConstrainedBox(
      constraints: const BoxConstraints(maxWidth: 100),
      child: Visibility(
        visible: isSubmitting || isLoading,
        child: const LinearProgressIndicator(
          backgroundColor: Pallet.darkBlue,
        ),
      ),
    );
  }
}


class _RoundContinueButton extends StatelessWidget {
  final VoidCallback onPressed;
  const _RoundContinueButton({Key key, @required this.onPressed}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return RawMaterialButton(
        onPressed: onPressed,
      elevation: 0.0,
      fillColor: Pallet.darkBlue,
      splashColor: Pallet.darkOrange,
      padding: const EdgeInsets.all(22.0),
      shape: const CircleBorder(),
      child: const Icon(
        FontAwesomeIcons.longArrowAltRight,
        color: Colors.white,
        size: 24.0,
      ),
    );
  }
}



InputDecoration registerInputDecoration({String hintText}){
  return InputDecoration(
    contentPadding: const EdgeInsets.symmetric(vertical: 18.0),
    hintStyle: const TextStyle( color: Colors.white, fontSize: 18),
    hintText: hintText,

    focusedBorder: const UnderlineInputBorder(
      borderSide: BorderSide(color: Colors.white, width: 2),
    ),
    enabledBorder: const UnderlineInputBorder(
      borderSide: BorderSide(color: Colors.white),
    ),
    errorBorder: const UnderlineInputBorder(
      borderSide: BorderSide(color: Pallet.orange, width: 2),
    ),
    focusedErrorBorder: const UnderlineInputBorder(
      borderSide: BorderSide(color: Pallet.orange, width: 2),
    ),
    errorStyle: const TextStyle(color: Pallet.darkOrange),
  );
}

InputDecoration signInInputDecoration({String hintText}){
  return InputDecoration(
    contentPadding: const EdgeInsets.symmetric(vertical: 18.0),
    hintStyle: const TextStyle( color: Pallet.darkBlue, fontSize: 18),
    hintText: hintText,
    focusedBorder: const UnderlineInputBorder(
      borderSide: BorderSide(color: Pallet.darkBlue, width: 2),
    ),
    enabledBorder: const UnderlineInputBorder(
      borderSide: BorderSide(color: Pallet.darkBlue),
    ),
    errorBorder: const UnderlineInputBorder(
      borderSide: BorderSide(color: Pallet.darkOrange, width: 2),
    ),
    focusedErrorBorder: const UnderlineInputBorder(
      borderSide: BorderSide(color: Pallet.darkOrange, width: 2),
    ),
    errorStyle: const TextStyle(color: Pallet.darkOrange),
  );
}