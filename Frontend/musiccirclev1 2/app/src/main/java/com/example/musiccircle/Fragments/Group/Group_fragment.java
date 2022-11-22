package com.example.musiccircle.Fragments.Group;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.musiccircle.R;

public class Group_fragment extends Fragment {

    Button go_to_create_group;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_playlists_page, container, false);
        go_to_create_group = (Button) view.findViewById(R.id.gotocreategroup);
        Bundle bundle = getArguments();
        final String message = bundle.getString("message");

        go_to_create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle b2 = new Bundle();
//                b2.putString("message", message);
//                //Fragment fragment = new GroupRegistration_fragment();
//                fragment.setArguments(b2);
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }
        });
        return view;
    }
}
