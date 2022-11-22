package com.example.musiccircle.Activities.Profile_Page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.musiccircle.Entity.User;
import com.example.musiccircle.Fragments.Profile.ProfileInfoFragment;
import com.example.musiccircle.Fragments.Profile.ViewOwnProfileFragment;
import com.example.musiccircle.R;

public class ProfilePageActivity extends AppCompatActivity {

    private static final String USER_USERNAME_KEY = "user_username";
    private static final String USER_KEY = "user";
    private static final String USER_PAGE_KEY = "user_page_username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        // Get bundle args
        Bundle bundle = getIntent().getExtras();
        String loggedInUser = bundle.getString(USER_USERNAME_KEY);
        String userVisiting = bundle.getString(USER_PAGE_KEY);
        if (loggedInUser.equals(userVisiting)){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.profile_placeholder, ViewOwnProfileFragment.newInstance((User)bundle.getSerializable(USER_KEY), loggedInUser));
            ft.addToBackStack(null);
            ft.commit();
        }
        else{
            //ProfileInfoFragment profileFragment = ViewOtherProfileFragment.newInstance((User)savedInstanceState.getSerializable(USER_KEY), loggedInUser);
            //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //ft.replace(R.id.profile_placeholder, profileFragment);
            //ft.addToBackStack(null);
            //ft.commit();
        }
    }
}
