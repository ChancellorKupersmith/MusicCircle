package com.example.musiccircle.RecyclerViewAdapters;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musiccircle.Entity.Group;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.Entity.entities;
import com.example.musiccircle.Fragments.Music_Player.dummy.DummyContent.DummyItem;
import com.example.musiccircle.R;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ShareWithRecyclerViewAdapter extends RecyclerView.Adapter<ShareWithRecyclerViewAdapter.ViewHolder> {

    private final List<entities> mValues;

    public ShareWithRecyclerViewAdapter(List<entities> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_share_with, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if(mValues.get(position).getType() == 1){
            User user = (User) holder.mItem;
            //Change to pic and name
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getImage(), 0, user.getImage().length);
            holder.mImageView.setImageBitmap(bitmap);
            holder.mContentView.setText(user.getArtistName());
        }
        else if(holder.mItem.getType() == 2){
            Group group = (Group) holder.mItem;
            //Change to pic and name
            Bitmap bitmap = BitmapFactory.decodeByteArray(group.getImage(), 0, group.getImage().length);
            holder.mImageView.setImageBitmap(bitmap);
            holder.mContentView.setText(group.getName());
        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final ImageView mImageView;
        public entities mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.itemDescriptionView);
            mImageView = (ImageView) view.findViewById(R.id.imageView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
    public interface ShareWithListener{
        void onShareWithClick(int position);
    }
}