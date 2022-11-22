package com.example.musiccircle.RecyclerViewAdapters;


import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musiccircle.Activities.Events_Page.MapActivity;
import com.example.musiccircle.Entity.Event;
import com.example.musiccircle.Activities.Home_Page.NavMenuActivity;
import com.example.musiccircle.R;

import java.util.List;

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.MyView> {

    private List<Event> list;
    private String loggedinUser;

    public static class MyView extends RecyclerView.ViewHolder {

        TextView textView;

        public MyView(View view) {
            super(view);
            textView = view.findViewById(R.id.eventtextview);
        }
    }

    public EventRecyclerViewAdapter(List<Event> horizontalList) {
        this.list = horizontalList;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        holder.textView.setText(list.get(position).getEventName());
        holder.textView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        Intent intent = new Intent(activity, MapActivity.class);
                        intent.putExtra("name", list.get(position).getEventName());
                        intent.putExtra("description", list.get(position).getEventDescription());
                        intent.putExtra("destlocation", list.get(position).getEventLocation());
                        intent.putExtra("creator",list.get(position).getEventCreator());
                        intent.putExtra("loggedinuser",loggedinUser);
                        activity.startActivity(intent);
                    }
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


