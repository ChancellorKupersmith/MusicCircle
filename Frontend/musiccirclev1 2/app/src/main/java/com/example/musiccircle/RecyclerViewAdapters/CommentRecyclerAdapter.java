package com.example.musiccircle.RecyclerViewAdapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musiccircle.Entity.Comment;
import com.example.musiccircle.R;
import com.example.musiccircle.Activities.Home_Page.NavMenuActivity;

import java.util.List;

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.CommentViewHolder> {
    private List<Comment> localCommentList;

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        Button usernameLink;
        ImageButton profilePic;
        TextView commentText;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameLink = itemView.findViewById(R.id.comment_username_link);
            profilePic = itemView.findViewById(R.id.comment_profile_pic);
            commentText = itemView.findViewById(R.id.comment_content);
        }

        public Button getUsernameLink() { return usernameLink; }
        public ImageButton getProfilePic() { return profilePic; }
        public TextView getCommentText() { return commentText; }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param commentList List</Comment> containing the data to populate views to be used
     * by RecyclerView.
     */
    public CommentRecyclerAdapter(List<Comment> commentList) {
        localCommentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);

        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.getUsernameLink().setText(localCommentList.get(position).getCommenter());
        holder.getUsernameLink().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotodiscovery = new Intent(v.getContext(), NavMenuActivity.class);
                v.getContext().startActivity(gotodiscovery);
            }
        });
        holder.getCommentText().setText(localCommentList.get(position).getText());
        /**
         * Send GET request for the User profile pic?
         * localCommentList.get(position).getCommenter()
         * holder.getProfilePic().setImageResource();
         */
        holder.getProfilePic().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotodiscovery = new Intent(v.getContext(), NavMenuActivity.class);
                v.getContext().startActivity(gotodiscovery);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localCommentList.size();
    }
}
