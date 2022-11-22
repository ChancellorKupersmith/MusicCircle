package com.example.musiccircle.RecyclerViewAdapters;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.R;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link AudioFile}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAudioFileRecyclerViewAdapter extends RecyclerView.Adapter<MyAudioFileRecyclerViewAdapter.ViewHolder> {

    private final List<AudioFile> mValues;
    private TrackListListener mTrackListListener;

    public MyAudioFileRecyclerViewAdapter(List<AudioFile> songs, TrackListListener trackListListener) {
        mValues = songs;
        mTrackListListener = trackListListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_track_list_audio_file, parent, false);
        return new ViewHolder(view, mTrackListListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.mIdView.setText(String.valueOf(position + 1));
        holder.mContentView.setText(mValues.get(position).getSongName());
        holder.mSongArtist.setText(mValues.get(position).getArtist().getArtistName());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        //public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mSongArtist;
        public AudioFile mItem;
        TrackListListener trackListListener;

        public ViewHolder(View view, TrackListListener trackListListener) {
            super(view);
            mView = view;
            //mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            mSongArtist = (TextView) view.findViewById(R.id.song_artist);
            this.trackListListener = trackListListener;
            mView.setOnClickListener(this);
        }



        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            trackListListener.onTrackClick(getBindingAdapterPosition());
        }
    }
    public interface TrackListListener{
        void onTrackClick(int position);
    }
}