package com.example.musiccircle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Fragment_1 extends Fragment {

    private Button RegisterButton;
    private Button LoginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        View registbtn = view.findViewById(R.id.btn_register);
        RegisterButton = (Button) registbtn;

        View loginbtn = view.findViewById(R.id.btn_login);
        LoginButton = (Button) loginbtn;

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_1_to_fragment_2);
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_1_to_fragment_3);
            }
        });

        return view;
    }
}