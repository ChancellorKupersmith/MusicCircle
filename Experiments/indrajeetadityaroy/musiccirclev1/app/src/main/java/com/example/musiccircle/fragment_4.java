package com.example.musiccircle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class fragment_4 extends Fragment {

    fragment_4Args args;
    TextView textView;
    Button go_to_search;
    Button go_to_create_group;
    Button go_to_upload_album;
    Button go_to_create_event;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        assert getArguments() != null;
        //args = fragment_4Args.fromBundle(getArguments());
        //String name = args.getUsername();

        View view = inflater.inflate(R.layout.fragment_4, container, false);
       // textView =  view.findViewById(R.id.textview);
      // textView.setText(name);

        go_to_search = (Button) view.findViewById(R.id.gotosearch);
        go_to_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_4_to_fragment_5);
            }
        });

        go_to_create_group = (Button) view.findViewById(R.id.gotocreategroup);
        go_to_create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_4_to_fragment_6);
            }
        });

        go_to_upload_album = (Button) view.findViewById(R.id.gotoUploadAlbum);
        go_to_upload_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_4_to_uploadAlbumFrag);
            }
        });

        go_to_create_event = (Button) view.findViewById(R.id.gotocreateevent2);
        go_to_create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_4_to_fragment7);
            }
        });

        return view;
    }
}