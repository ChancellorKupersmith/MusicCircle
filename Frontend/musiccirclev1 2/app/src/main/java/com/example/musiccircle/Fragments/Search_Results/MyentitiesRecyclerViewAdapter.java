package com.example.musiccircle.Fragments.Search_Results;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musiccircle.Entity.Album;
import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.Entity.Event;
import com.example.musiccircle.Entity.Group;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.Entity.entities;
import com.example.musiccircle.R;
import com.example.musiccircle.Fragments.Search_Results.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyentitiesRecyclerViewAdapter extends RecyclerView.Adapter<MyentitiesRecyclerViewAdapter.ViewHolder> {

    private final List<entities> mValues;

    public MyentitiesRecyclerViewAdapter(List<entities> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_search_all2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if(holder.mItem.getType() == 1){
            User user = (User) holder.mItem;
            holder.mTitle.setText(user.getArtistName());
            holder.mIconView.setImageResource(R.drawable.ic_user_search);
            holder.mIconText.setText(user.getFollower_usernames().size());
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getImage(), 0, user.getImage().length);
            holder.mImageView.setImageBitmap(bitmap);
            holder.mContentView.setText("Tracks: " + user.getUploadedSongs().size());
        }
        else if(holder.mItem.getType() == 2){
            Group group = (Group) holder.mItem;
            holder.mTitle.setText(group.getName());
            holder.mContentView.setText(group.getDescription());
            Bitmap bitmap = BitmapFactory.decodeByteArray(group.getImage(), 0, group.getImage().length);
            holder.mImageView.setImageBitmap(bitmap);
            holder.mIconView.setImageResource(R.drawable.ic_group_search);
            holder.mIconText.setText(group.getMember_usernames().size());
        }
        else if(holder.mItem.getType() == 3){
            Album album = (Album) holder.mItem;
            holder.mTitle.setText(album.getAlbumName());
            holder.mContentView.setText(album.getArtist());
            Bitmap bitmap = BitmapFactory.decodeByteArray(album.getImage(), 0, album.getImage().length);
            holder.mImageView.setImageBitmap(bitmap);
            holder.mIconView.setImageResource(R.drawable.ic_album_search);
            holder.mIconText.setText(album.getTracklist().size());
        }
        else if(holder.mItem.getType() == 4){
            AudioFile song = (AudioFile) holder.mItem;
            holder.mTitle.setText(song.getSongName());
            holder.mContentView.setText(song.getArtist().getArtistName());
//            Bitmap bitmap = BitmapFactory.decodeByteArray(song.getImage(), 0, song.getImage().length);
//            holder.mImageView.setImageBitmap(bitmap);
            holder.mIconView.setImageResource(R.drawable.ic_baseline_favorite_24);
            holder.mIconText.setText(String.valueOf(song.getLikes()));
        }
        else if(holder.mItem.getType() == 5){
            Event event = (Event) holder.mItem;
            holder.mTitle.setText(event.getEventName());
            holder.mContentView.setText(event.getDateTime());
            Bitmap bitmap = BitmapFactory.decodeByteArray(event.getImage(), 0, event.getImage().length);
            holder.mImageView.setImageBitmap(bitmap);
            holder.mIconView.setImageResource(R.drawable.ic_event_search);
            holder.mIconText.setText(event.getCity() + ", " + event.getState());
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitle;
        public final TextView mContentView;
        public final ImageView mImageView;
        public final ImageView mIconView;
        public final TextView mIconText;
        public entities mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.TiteTextView);
            mContentView = (TextView) view.findViewById(R.id.itemDescriptionView);
            mImageView = (ImageView) view.findViewById(R.id.imageView);
            mIconView = (ImageView) view.findViewById(R.id.itemIcon);
            mIconText = (TextView) view.findViewById(R.id.iconTextView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}