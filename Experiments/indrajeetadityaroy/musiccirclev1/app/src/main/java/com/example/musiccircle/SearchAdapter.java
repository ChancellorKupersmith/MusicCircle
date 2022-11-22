package com.example.musiccircle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musiccircle.Entity.Album;
import com.example.musiccircle.Entity.Group;
import com.example.musiccircle.Entity.User;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<entities> EntityList;

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

            case entities.TYPE_ALBUM:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item,parent,false);
                return new AlbumViewHolder(itemView);

            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case entities.TYPE_USER:
                ((UserViewHolder) holder).bindView(position);
                break;

            case entities.TYPE_GROUP:
                ((GroupViewHolder) holder).bindView(position);
                break;

            case entities.TYPE_ALBUM:
                ((AlbumViewHolder) holder).bindView(position);
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
            User user = (User) EntityList.get(position);
            user_userName.setText(user.getUsername());
            user_firstName.setText(user.getFirstName());
            search_type.setText(user.getClass().getSimpleName());
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
    class AlbumViewHolder extends RecyclerView.ViewHolder{

        TextView album_name, artist_name, search_type;
        ImageView album_art_pic;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            album_art_pic = itemView.findViewById(R.id.imageView);
            album_name = itemView.findViewById(R.id.textView);
            artist_name = itemView.findViewById(R.id.rowCountTextView);
            search_type = itemView.findViewById(R.id.userTypeView);
        }

        void bindView(int position){

            Album album = (Album) EntityList.get(position);
            album_name.setText(album.getAlbumName());
            artist_name.setText(album.getArtist());
            search_type.setText(album.getClass().getSimpleName());

        }
    }
}
