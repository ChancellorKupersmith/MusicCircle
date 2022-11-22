package com.example.musiccircle.RecyclerViewAdapters;

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
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Activities.Music_Player_Page.MusicPlayerActivity;
import com.example.musiccircle.Entity.Album;
import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.Entity.Playlist;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.R;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


public class ProfilePlaylistsRecyclerAdapter extends RecyclerView.Adapter<ProfilePlaylistsRecyclerAdapter.ProfileViewHolder> {
    private static final String USER_USERNAME_KEY = "user_username";
    private static final String USER_KEY = "user";

    List<Playlist> playlistList;
    Context context;
    String loggedInUsername;
    User loggedInUser;
    Bitmap bmp;

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        ImageView playlistPic;
        TextView playlistName;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            playlistPic = itemView.findViewById(R.id.item_image_view);
            playlistName = itemView.findViewById(R.id.item_name_view);
        }

        public ImageView getPlaylistPic() { return playlistPic; }
        public TextView getPlaylistName() { return playlistName; }
    }

    public ProfilePlaylistsRecyclerAdapter(List<Playlist> songList, Context context, String loggedInUsername, User loggedInUser) {
        this.playlistList = songList;
        this.context = context;
        this.loggedInUsername = loggedInUsername;
        this.loggedInUser = loggedInUser;
    }


    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_lists_item, parent, false);

        return new ProfileViewHolder(view);
    }

    Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        holder.playlistName.setText(playlistList.get(position).getName());

        holder.setIsRecyclable(false);

        new Thread(() -> {
            mainHandler.post(() -> holder.playlistPic.setImageDrawable(null));
            if (playlistList.get(position).getSongs().get(0).getImage() == null) {
                System.out.println(playlistList.get(position).getName() + Arrays.toString(playlistList.get(position).getSongs().get(0).getImage()));
            } else {
                mainHandler.post(() -> holder.playlistPic.setImageDrawable(null));
                byte[] decodedByte = playlistList.get(position).getSongs().get(0).getImage();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length, options);
                mainHandler.post(() -> holder.playlistPic.setImageBitmap(bitmap));
            }
        }).start();

        List<AudioFile> trackList = playlistList.get(position).getSongs();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Bundle b = new Bundle();
                b.putSerializable("track_list", (Serializable) trackList);
                Intent intent = new Intent(activity, MusicPlayerActivity.class);
                intent.putExtras(b);
                activity.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return playlistList.size();
    }
}
