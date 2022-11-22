import 'package:flutter/material.dart';
import 'package:lit_firebase_auth/lit_firebase_auth.dart';
import 'package:music_circle/Resources/Colors.dart';

import 'LoginUI.dart';
class SignIn extends StatelessWidget {
  final VoidCallback onRegisterClicked;
  const SignIn({Key key, @required this.onRegisterClicked}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SignInForm(
        child: Padding(
          padding: const EdgeInsets.all(32.0),
          child: Column(
            children: [
              Expanded(
                flex: 3,
                  child: Align(
                    alignment: Alignment.centerLeft,
                    child: LoginTitle(
                      title: 'MusicCircle',
                    ),
                )
              ),
              Expanded(
                flex: 5,
                  child: ListView(
                    children: [
                      Padding(
                          padding: EdgeInsets.symmetric(vertical: 16),
                        child: EmailTextFormField(
                          decoration: signInInputDecoration(hintText: 'Email'),
                        ),
                      ),
                      Padding(
                        padding: EdgeInsets.symmetric(vertical: 16),
                        child: PasswordTextFormField(
                          decoration: signInInputDecoration(hintText: 'Password'),
                        ),
                      ),
                      SignInBar(
                          label: 'Sign in',
                          onPressed: (){
                            context.signInWithEmailAndPassword();
                          },
                         isLoading: false,
                      ),
                      Align(
                        alignment: Alignment.centerLeft,
                        child: InkWell(
                          splashColor: Colors.white,
                          onTap: (){
                            onRegisterClicked?.call();
                          },
                          child: const Text(
                            'Create new account',
                            style: TextStyle(
                              color: Pallet.darkBlue,
                              fontSize: 16,
                              decoration: TextDecoration.underline,
                            ),
                          ),
                        ),
                      )
                    ],
                  ),
              )
            ],
          ),
        )
    );
  }
}

