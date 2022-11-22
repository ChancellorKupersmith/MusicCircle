package com.example.musiccircle.Fragments.Home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musiccircle.Entity.Event;
import com.example.musiccircle.Entity.UserParcelable;
import com.example.musiccircle.Fragments.Event.ViewEvent_fragment;
import com.example.musiccircle.R;

import java.util.List;

public class HomeEventRecyclerViewAdapter extends RecyclerView.Adapter<HomeEventRecyclerViewAdapter.MyView> {
    private List<Event> list;
    private String loggedinUser;

    public static class MyView extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public MyView(View view) {
            super(view);
            textView = view.findViewById(R.id.eventtextview);
            imageView = view.findViewById(R.id.eventImageView);
        }
    }

    public HomeEventRecyclerViewAdapter(List<Event> horizontalList) {
        this.list = horizontalList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new MyView(itemView);
    }

    Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        holder.textView.setText(list.get(position).getEventName());


        holder.imageView.setOnClickListener(
                view -> {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Bundle b1 = new Bundle();
                    System.out.println(list.get(position).getEventName());
                    b1.putString("name", list.get(position).getEventName());
                    b1.putString("description", list.get(position).getEventDescription());
                    //b1.putString("destlocation", list.get(position).getEventLocation());
                    b1.putString("creator",list.get(position).getEventCreator());
                    //b1.putString("loggedin_profile_username",loggedinUser);
                    b1.putParcelableArrayList("performers",list.get(position).getUsersPerforming());
                    b1.putByteArray("eventImage",list.get(position).getImage());
                    //b1.putParcelable("user",new UserParcelable(list.get(position).getId(),list.get(position).,list.get(position).getLastName(),list.get(position).getUsername(),list.get(position).getContent()));
                    Fragment myFragment = new ViewEvent_fragment();
                    myFragment.setArguments(b1);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
                }
        );
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public String getLoggedinUser() {
        return loggedinUser;
    }

    public void setLoggedinUser(String loggedinUser) {
        this.loggedinUser = loggedinUser;
    }
}


