package com.example.musiccircle.Fragments.Home;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.Activities.Events_Page.MapActivity;
//import com.example.musiccircle.ImageResizer;
import com.example.musiccircle.Activities.Music_Player_Page.MusicPlayerActivity;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeSongRecyclerViewAdapter extends RecyclerView.Adapter<HomeSongRecyclerViewAdapter.MyView> {

    private final Context context;
    private List<AudioFile> list;
    private String loggedinUser;
    private User user;

    public static class MyView extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public MyView(View view) {
            super(view);
            textView = view.findViewById(R.id.Songtextview);
            imageView = view.findViewById(R.id.songImageView);
        }
    }

    public HomeSongRecyclerViewAdapter(List<AudioFile> horizontalList, Context context, User user) {
        this.list = horizontalList;
        this.context = context;
        this.notifyDataSetChanged();
        this.user = user;
    }

    @NonNull
    @Override
    public HomeSongRecyclerViewAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false);
        return new HomeSongRecyclerViewAdapter.MyView(itemView);
    }

    /**
     * @Override public void onBindViewHolder(final HomeSongRecyclerViewAdapter.MyView holder, final int position) {
     * <p>
     * holder.textView.setText(list.get(position).getSongName());
     * if (list.get(position).getPic()!= null) {
     * holder.imageView.setImageBitmap(null);
     * Glide.with(context).asBitmap().fitCenter().centerCrop()
     * .load(list.get(position).getPic())
     * .into(holder.imageView);
     * }
     * <p>
     * byte[] bytes = new byte[100];
     * ArrayList<AudioFile> a = new ArrayList<>();
     * a.add(list.get(position));
     * <p>
     * holder.itemView.setOnClickListener(view -> {
     * list.get(position).setPic(bytes);
     * AppCompatActivity activity = (AppCompatActivity) view.getContext();
     * Bundle b = new Bundle();
     * b.putSerializable("track_list",a);
     * Intent intent = new Intent(activity, MusicPlayerActivity.class);
     * intent.putExtras(b);
     * activity.startActivity(intent);
     * });
     * }
     **/

    Handler mainHandler = new Handler(Looper.getMainLooper());


    @Override
    public void onBindViewHolder(@NonNull HomeSongRecyclerViewAdapter.MyView holder, int position) {

        holder.setIsRecyclable(false);

        new Thread(() -> {
            mainHandler.post(() -> holder.imageView.setImageDrawable(null));
            if (list.get(position).getImage() == null) {
                System.out.println(list.get(position).getSongName() + Arrays.toString(list.get(position).getImage()));
            } else {
                mainHandler.post(() -> holder.imageView.setImageDrawable(null));
                byte[] decodedByte = list.get(position).getImage();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length, options);
                mainHandler.post(() -> holder.imageView.setImageBitmap(bitmap));
                mainHandler.post(() -> holder.textView.setText(list.get(position).getSongName()));
            }
        }).start();


        ArrayList<AudioFile> a = new ArrayList<>();

        if(list.size() > 1){
            for(int i = position; i < list.size(); i++){
                a.add(list.get(i));
            }
            for(int i = position - 1; i >= 0; i--){
                a.add(list.get(i));
            }
        }
        else{
            a.add(list.get(position));
        }




        holder.itemView.setOnClickListener(view -> {
            list.get(position).getArtist().setContent(null);
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Bundle b = new Bundle();
            b.putSerializable("track_list", a);
            b.putSerializable("user", user);
            Intent intent = new Intent(activity, MusicPlayerActivity.class);
            intent.putExtras(b);
            activity.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String getLoggedinUser() {
        return loggedinUser;
    }

    public void setLoggedinUser(String loggedinUser) {
        this.loggedinUser = loggedinUser;
    }
}

