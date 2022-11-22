package com.example.musiccircle.RecyclerViewAdapters;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musiccircle.Entity.Group;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.R;
import com.example.musiccircle.Fragments.Profile.ViewOtherProfile_fragment;
import com.example.musiccircle.Entity.entities;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<entities> EntityList;
    String loggedin_profile_username;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        switch (viewType){
            case entities.TYPE_USER:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item,parent,false);
                return new UserViewHolder(itemView);

            case entities.TYPE_GROUP:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item,parent,false);
                return new GroupViewHolder(itemView);

            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)){
            case entities.TYPE_USER:
                ((UserViewHolder) holder).bindView(position);
                break;

            case entities.TYPE_GROUP:
                ((GroupViewHolder) holder).bindView(position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (EntityList == null) {
            return 0;
        } else {
            return EntityList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return EntityList.get(position).getType();
    }

    public void setAdapterList(List<? extends entities> entitylist) {
        if (EntityList == null){
            EntityList = new ArrayList<>();
        }
        EntityList.clear();
        EntityList.addAll(entitylist);
        notifyDataSetChanged();
    }

    public void setLoggedInUsername(String s){
        loggedin_profile_username = s;
    }


    class UserViewHolder extends RecyclerView.ViewHolder{

        TextView user_userName, user_firstName, search_type;
        ImageView user_profile_pic;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            user_profile_pic = itemView.findViewById(R.id.imageView);
            user_userName = itemView.findViewById(R.id.textView);
            user_firstName = itemView.findViewById(R.id.rowCountTextView);
            search_type = itemView.findViewById(R.id.userTypeView);
        }

        void bindView(int position){
            final User user = (User) EntityList.get(position);
            user_userName.setText(user.getUsername());
            user_firstName.setText(user.getFirstName());
            search_type.setText(user.getClass().getSimpleName());
            user_userName.setOnClickListener(
                    view -> {
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        Bundle b1 = new Bundle();
                        b1.putString("user_profile_username", user.getUsername());
                        b1.putString("loggedin_profile_username",loggedin_profile_username);
                        Fragment myFragment = new ViewOtherProfile_fragment();
                        myFragment.setArguments(b1);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
                    }
            );
        }
    }

    class GroupViewHolder extends RecyclerView.ViewHolder{

        TextView group_groupName, group_groupDescription, search_type;
        ImageView group_profile_pic;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            group_profile_pic = itemView.findViewById(R.id.imageView);
            group_groupName = itemView.findViewById(R.id.textView);
            group_groupDescription = itemView.findViewById(R.id.rowCountTextView);
            search_type = itemView.findViewById(R.id.userTypeView);
        }

        void bindView(int position){

            Group group = (Group) EntityList.get(position);
            group_groupName.setText(group.getName());
            group_groupDescription.setText(group.getDescription());
            search_type.setText(group.getClass().getSimpleName());

        }
    }
}

