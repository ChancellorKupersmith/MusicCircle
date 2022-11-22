package com.example.musiccircle.Fragments.Music_Player;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.musiccircle.Entity.Album;
import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.Entity.Event;
import com.example.musiccircle.Entity.Group;
import com.example.musiccircle.Entity.Playlist;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.Entity.entities;
import com.example.musiccircle.R;
import com.example.musiccircle.Fragments.Music_Player.dummy.DummyContent;
import com.example.musiccircle.RecyclerViewAdapters.ShareWithRecyclerViewAdapter;
import com.example.musiccircle.Services.NotificationService;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class ShareWithFragment extends DialogFragment  implements ShareWithRecyclerViewAdapter.ShareWithListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String SHARE_LIST_KEY = "shareList";
    private static final String USER_USERNAME_KEY = "user_username";
    private static final String USER_KEY = "user";
    private static final String CONTENT_TO_SHARE = "content_to_share";
    // TODO: Customize parameters
    private int mColumnCount = 4;
    private ArrayList<entities> shareList;
    private ArrayList<String> sendToUsernamesList = new ArrayList<String>();
    private ArrayList<Group> sendToGroupList = new ArrayList<Group>();
    private NotificationService notificationService;
    private entities contentToShare;
    private User user;
    private String user_username;
    private Button shareBtn;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShareWithFragment() {
    }

    @SuppressWarnings("unused")
    public static ShareWithFragment newInstance(int columnCount, ArrayList<entities> shareList, User user, String username, entities contentToShare) {
        ShareWithFragment fragment = new ShareWithFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putSerializable(SHARE_LIST_KEY, shareList);
        args.putSerializable(USER_KEY, user);
        args.putString(USER_USERNAME_KEY, username);
        args.putSerializable(CONTENT_TO_SHARE, contentToShare);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            shareList = (ArrayList<entities>) getArguments().getSerializable(SHARE_LIST_KEY);
            user = (User) getArguments().getSerializable(USER_KEY);
            user_username = getArguments().getString(USER_USERNAME_KEY);
            contentToShare = (entities) getArguments().getSerializable(CONTENT_TO_SHARE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_with_list, container, false);

        //Bind to sNotification service
        Intent notificationServiceIntent = new Intent(getContext(), NotificationService.class);
        Bundle serviceBundle = new Bundle();
        serviceBundle.putSerializable(USER_KEY, user);
        serviceBundle.putString(USER_USERNAME_KEY, user_username);
        notificationServiceIntent.putExtras(serviceBundle);
        getContext().bindService(notificationServiceIntent, notificationConnection, Context.BIND_AUTO_CREATE);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new ShareWithRecyclerViewAdapter(shareList));
        }
        shareBtn = view.findViewById(R.id.btn_share);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //sendToUsernamesList.add("chancek");
                sendToUsernamesList.add("westside_gunn");
                share();
            }
        });
        return view;
    }

    public ServiceConnection notificationConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            NotificationService.LocalBinder binder = (NotificationService.LocalBinder) service;
            notificationService = binder.getService();
            Log.d("ServiceConnection","connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("ServiceConnection","disconnected");
        }
    };

    @Override
    public void onShareWithClick(int position) {
        if(shareList.get(position).getType() == 1){
            //Send notification
            User user = (User) shareList.get(position);
            //sendToUsernamesList.add("chancek");
            sendToUsernamesList.add("westside_gunn");
            sendToUsernamesList.add("ta_account");
            if(user.isChecked()){
                sendToUsernamesList.remove(user);
            }
            else{
                //Check user and add to send to list
                sendToUsernamesList.add(user.getUsername());
            }
        }
        else{
            //Send Notification and post to group page
            Group group = (Group) shareList.get(position);
            if(group.isChecked()){
                sendToGroupList.remove(group);
            }
            else{
                sendToGroupList.add(group);
            }
        }
    }

    //When share button is clicked goes through both send to lists and sends notification to those usernames
    //For each group in groups send to list it also adds to that groups share feed on group page.
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void share(){
        //1. Send notification to individual users
        notificationService.setUsername_from(user.getUsername());
        notificationService.setNotificationText("from " + user.getArtistName());
        String title = null;
        if(contentToShare.getType() == 3){
            title = "Album: " + ((Album) contentToShare).getAlbumName();
        }
        else if(contentToShare.getType() == 4){
            title = "Song: " + ((AudioFile) contentToShare).getSongName() + ", " + ((AudioFile) contentToShare).getArtist().getArtistName();
        }
        else if(contentToShare.getType() == 5){
            title = "Event: " + ((Event) contentToShare).getEventName();
        }
        else if(contentToShare.getType() == 7){
            title = "Playlist: " + ((Playlist) contentToShare).getName();
        }
        else if(contentToShare.getType() == 1){
            title = "Artist: " + ((User) contentToShare).getArtistName();
        }
        else if(contentToShare.getType() == 2){
            title = "Group: " + ((Group) contentToShare).getName();
        }
        notificationService.setNotificationTitle(title);

        for(String username : sendToUsernamesList){
            notificationService.setUsername_to_notify(username);
            notificationService.sendNotification("userShares");
        }
        //2. Send notification to groups and users in group
        for(Group group : sendToGroupList){
            notificationService.setNotificationTitle(group.getName());
            for(String member_username : group.getMember_usernames()){
                notificationService.setUsername_to_notify(member_username);
                notificationService.sendNotification("groupShares");
            }
        }
        //3. Post to group page
    }
}