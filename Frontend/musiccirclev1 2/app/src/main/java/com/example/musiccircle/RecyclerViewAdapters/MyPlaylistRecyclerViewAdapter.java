package com.example.musiccircle.RecyclerViewAdapters;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musiccircle.Entity.Playlist;
import com.example.musiccircle.Fragments.Music_Player.dummy.DummyContent.DummyItem;
import com.example.musiccircle.R;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPlaylistRecyclerViewAdapter extends RecyclerView.Adapter<MyPlaylistRecyclerViewAdapter.ViewHolder> {

    private final List<Playlist> mValues;
    private PlaylistListener mPlaylistListener;

    public MyPlaylistRecyclerViewAdapter(List<Playlist> playlists, PlaylistListener playlistListener) {
        mValues = playlists;
        mPlaylistListener = playlistListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user_playlists, parent, false);
        return new ViewHolder(view, mPlaylistListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getName());
        holder.mContentView.setText(String.valueOf(mValues.get(position).getLength()));
    }

    @Override
    public int getItemCount() {
        if(mValues != null){
            return mValues.size();
        }
        else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Playlist mItem;
        public PlaylistListener mPlaylistListener;

        public ViewHolder(View view, PlaylistListener playlistListener) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            mPlaylistListener = playlistListener;
            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            mPlaylistListener.onPlaylistClick(getBindingAdapterPosition());
        }
    }
    public interface PlaylistListener{
        void onPlaylistClick(int position);
    }
}