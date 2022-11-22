package com.example.musiccircle.Activities.Search_Results_Page.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.musiccircle.Fragments.Search_Results.SearchAlbumsFragment;
import com.example.musiccircle.Fragments.Search_Results.SearchAllFragment;
import com.example.musiccircle.Fragments.Search_Results.SearchArtistsFragment;
import com.example.musiccircle.Fragments.Search_Results.SearchEventsFragment;
import com.example.musiccircle.Fragments.Search_Results.SearchGroupsFragment;
import com.example.musiccircle.Fragments.Search_Results.SearchSongsFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES = new String[]{"All", "Songs", "Artist", "Group", "Event", "Album"};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        //if 0 display all fragment, ..., if 5 dislay album fragment
        if(position == 0){
            return SearchAllFragment.newInstance(1);
        }
        else if(position == 1){
            return SearchSongsFragment.newInstance(1);
        }
        else if(position == 2){
            return SearchArtistsFragment.newInstance(1);
        }
        else if(position == 3){
            return SearchGroupsFragment.newInstance(1);
        }
        else if(position == 4){
            return SearchEventsFragment.newInstance(1);
        }
        else {
            return SearchAlbumsFragment.newInstance(1);
        }
        //return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 6;
    }
}