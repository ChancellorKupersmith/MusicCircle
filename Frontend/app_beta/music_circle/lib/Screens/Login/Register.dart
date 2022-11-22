import 'package:flutter/material.dart';
import 'package:lit_firebase_auth/lit_firebase_auth.dart';
import 'package:music_circle/Resources/Colors.dart';

import 'LoginUI.dart';

class Register extends StatelessWidget {
  final VoidCallback onSignInPressed;
  const Register({Key key, this.onSignInPressed}) : super(key: key);

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
                      title: 'Get Started.',
                    ),
                  )
              ),
              Expanded(
                flex: 4,
                child: ListView(
                  children: [
                    Padding(
                      padding: EdgeInsets.symmetric(vertical: 16),
                      child: EmailTextFormField(
                        style: TextStyle(
                            color: Colors.white
                        ),
                        decoration: registerInputDecoration(hintText: 'Email'),
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.symmetric(vertical: 16),
                      child: PasswordTextFormField(
                        style: TextStyle(
                          color: Colors.white
                        ),
                        decoration: registerInputDecoration(hintText: 'Password'),
                      ),
                    ),
                    SignUpBar(
                      label: 'Register',
                      onPressed: (){
                        context.registerWithEmailAndPassword();
                      },
                      isLoading: false,
                    ),
                    Align(
                      alignment: Alignment.centerLeft,
                      child: InkWell(
                        splashColor: Colors.white,
                        onTap: (){
                          onSignInPressed?.call();
                        },
                        child: const Text(
                          'Sign in',
                          style: TextStyle(
                            color: Colors.white,
                            fontSize: 16,
                            decoration: TextDecoration.underline,
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              )
            ],
          ),
        )
    );
  }
}