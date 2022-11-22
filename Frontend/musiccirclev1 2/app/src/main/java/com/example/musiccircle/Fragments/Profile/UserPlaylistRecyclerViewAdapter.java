package com.example.musiccircle.Fragments.Profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Activities.Music_Player_Page.MusicPlayerActivity;
import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.Entity.Playlist;
import com.example.musiccircle.Entity.User;
//import com.example.musiccircle.ExpandPlaylist;
import com.example.musiccircle.Activities.Music_Player_Page.MusicPlayerActivity;
import com.example.musiccircle.R;

import java.util.ArrayList;
import java.util.Arrays;

public class UserPlaylistRecyclerViewAdapter extends RecyclerView.Adapter<UserPlaylistRecyclerViewAdapter.RecyclerViewHolder> {

    private Context mcontext;
    private ArrayList<Playlist> playlists;
    private ArrayList<byte[]> list;

    public UserPlaylistRecyclerViewAdapter(ArrayList<Playlist> recyclerDataArrayList, Context mcontext, ArrayList<byte[]> list) {
        this.list = list;
        this.mcontext = mcontext;
        this.playlists = recyclerDataArrayList;
        System.out.println(this.list.size());
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Playlist recyclerData = playlists.get(position);
        System.out.println(list.size());

        holder.courseTV.setText(recyclerData.getName());

        ArrayList<AudioFile> a = new ArrayList<>();
        if (playlists.get(position).getLength() > 0) {
            for (int i = 0; i < playlists.get(position).getLength(); i++) {
                a.add(playlists.get(position).getSongs().get(i));
            }
            holder.courseIV.setOnClickListener(view -> {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Bundle b = new Bundle();
                b.putSerializable("track_list", a);
                Intent intent = new Intent(activity, MusicPlayerActivity.class);
                intent.putExtras(b);
                activity.startActivity(intent);
            });
        }
    }

    public void getSongImage(AudioFile audioFile) {
        String search_url = "http://10.24.227.244:8080/audioFiles/pic/" + audioFile.getId();
        RequestQueue queue = Volley.newRequestQueue(mcontext);
        queue.getCache().clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    byte[] decodedImageBytes = Base64.decode(response, Base64.DEFAULT);
                    list.add(decodedImageBytes);
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public void filterList(ArrayList<Playlist> filteredList) {
        playlists = filteredList;
        notifyDataSetChanged();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView courseTV;
        private ImageView courseIV;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            courseTV = itemView.findViewById(R.id.idTVCourse);
            courseIV = itemView.findViewById(R.id.idIVcourseIV);
        }
    }
}
