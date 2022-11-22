package com.example.musiccircle.Fragments.Search_Results;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musiccircle.Entity.Event;
import com.example.musiccircle.Entity.entities;
import com.example.musiccircle.R;
import com.example.musiccircle.Fragments.Search_Results.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyEventRecyclerViewAdapter extends RecyclerView.Adapter<MyEventRecyclerViewAdapter.ViewHolder> {

    private final List<entities> mValues;

    public MyEventRecyclerViewAdapter(List<entities> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_search_events, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Event event = (Event) holder.mItem;
        holder.mTitle.setText(event.getEventName());
        holder.mContentView.setText(event.getDateTime());
        Bitmap bitmap = BitmapFactory.decodeByteArray(event.getImage(), 0, event.getImage().length);
        holder.mImageView.setImageBitmap(bitmap);
        holder.mIconView.setImageResource(R.drawable.ic_event_search);
        holder.mIconText.setText(event.getCity() + ", " + event.getState());
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