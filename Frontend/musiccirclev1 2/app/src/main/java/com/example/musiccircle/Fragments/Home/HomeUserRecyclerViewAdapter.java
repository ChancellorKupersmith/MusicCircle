package com.example.musiccircle.Fragments.Home;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.Entity.Event;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.Activities.Events_Page.MapActivity;
import com.example.musiccircle.Activities.Music_Player_Page.MusicPlayerActivity;
import com.example.musiccircle.Entity.UserParcelable;
import com.example.musiccircle.R;
import com.example.musiccircle.Fragments.Profile.ViewOtherProfile_fragment;

import java.util.ArrayList;
import java.util.List;

public class HomeUserRecyclerViewAdapter extends RecyclerView.Adapter<HomeUserRecyclerViewAdapter.MyView> {

    private List<UserParcelable> list;
    private String loggedinUser_username;

    public static class MyView extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public MyView(View view) {
            super(view);
            textView = view.findViewById(R.id.Songtextview);
            imageView = view.findViewById(R.id.songImageView);
        }
    }

    public HomeUserRecyclerViewAdapter(List<UserParcelable> horizontalList, String loggedinUser_username) {
        this.list = horizontalList;
        this.loggedinUser_username = loggedinUser_username;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_artist_item, parent, false);
        return new MyView(itemView);
    }

    Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        new Thread(() -> {
            if (list.get(position).getContent()!= null) {
                byte[] decodedByte = list.get(position).getContent();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length,options);
                mainHandler.post(() -> holder.imageView.setImageBitmap(bitmap));
            }

        }).start();

        holder.textView.setText(list.get(position).getArtistName());

        holder.itemView.setOnClickListener(view -> {
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Bundle b1 = new Bundle();
            b1.putString("user_profile_username", list.get(position).getUsername());
            b1.putString("loggedin_profile_username",loggedinUser_username);
            UserParcelable userParcelable = new UserParcelable(list.get(position).getId(),list.get(position).getFirstName(),list.get(position).getLastName(),list.get(position).getUsername(),list.get(position).getContent());
            userParcelable.setArtistName(list.get(position).getArtistName());
            b1.putParcelable("userParceable", userParcelable);

            User user = new User();
            user.setId(list.get(position).getId());
            user.setArtistName(list.get(position).getArtistName());
            user.setUsername(list.get(position).getUsername());
            b1.putSerializable("user", user);
            Fragment myFragment = new ViewOtherProfile_fragment();
            myFragment.setArguments(b1);

            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String getLoggedinUser_username() {
        return loggedinUser_username;
    }

    public void setLoggedinUser(String loggedinUser_username) {
        this.loggedinUser_username = loggedinUser_username;
    }
}

