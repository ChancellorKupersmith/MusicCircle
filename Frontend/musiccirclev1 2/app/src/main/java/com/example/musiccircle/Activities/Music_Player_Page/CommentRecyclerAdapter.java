package com.example.musiccircle.Activities.Music_Player_Page;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Activities.Home_Page.DiscoveryHomePageActivity;
import com.example.musiccircle.Entity.Comment;
import com.example.musiccircle.Fragments.Comments.CommentFragmentRequests;
import com.example.musiccircle.R;

import androidx.fragment.app.Fragment;

import java.util.List;

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.CommentViewHolder> {
    private List<Comment> localCommentList;
    private CommentFragmentRequests localServerRequester;

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
    public CommentRecyclerAdapter(List<Comment> commentList, CommentFragmentRequests serverRequester) {
        localCommentList = commentList;
        localServerRequester = serverRequester;
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
                Intent gotodiscovery = new Intent(v.getContext(), DiscoveryHomePageActivity.class);
                v.getContext().startActivity(gotodiscovery);
            }
        });

        holder.getCommentText().setText(localCommentList.get(position).getText());

        holder.getProfilePic().setImageBitmap(localServerRequester.getUserProfilePic(localCommentList.get(position).getCommenter()));
        holder.getProfilePic().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotodiscovery = new Intent(v.getContext(), DiscoveryHomePageActivity.class);
                v.getContext().startActivity(gotodiscovery);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localCommentList.size();
    }
}
