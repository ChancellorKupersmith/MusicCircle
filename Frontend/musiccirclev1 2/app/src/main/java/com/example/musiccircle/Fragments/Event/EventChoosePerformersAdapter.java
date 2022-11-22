package com.example.musiccircle.Fragments.Event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.R;

import java.util.ArrayList;

public class EventChoosePerformersAdapter extends RecyclerView.Adapter<EventChoosePerformersAdapter.MultiViewHolder> {

    private Context context;
    private ArrayList<User> users;

    public EventChoosePerformersAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = new ArrayList<>();
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_item_user, viewGroup, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;

        MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        void bind(final User user) {
            imageView.setVisibility(user.isChecked() ? View.VISIBLE : View.GONE);
            textView.setText(user.getUsername());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.setChecked(!user.isChecked());
                    imageView.setVisibility(user.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    public ArrayList<User> getAll() {
        return users;
    }

    public ArrayList<User> getSelected() {
        ArrayList<User> selected = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).isChecked()) {
                selected.add(users.get(i));
            }
        }
        return selected;
    }

    public void filterList(ArrayList<User> filteredList) {
        users = filteredList;
        notifyDataSetChanged();
    }
}

