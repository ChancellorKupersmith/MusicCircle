package com.example.musiccircle.RecyclerViewAdapters;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Entity.Event;
import com.example.musiccircle.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musiccircle.Entity.User;

import java.util.Arrays;
import java.util.List;

public class ProfileEventsRecyclerAdapter extends RecyclerView.Adapter<ProfileEventsRecyclerAdapter.ProfileViewHolder> {
    List<Event> eventList;
    Context context;
    Bitmap bmp;

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        ImageView eventPic;
        TextView eventName;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            eventPic = itemView.findViewById(R.id.item_image_view_v2);
            eventName = itemView.findViewById(R.id.item_name_view_v2);
        }

        public ImageView getEventPic() { return eventPic; }
        public TextView getEventName() { return eventName; }
    }

    public ProfileEventsRecyclerAdapter(List<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_lists_itemv2, parent, false);

        return new ProfileViewHolder(view);
    }

    Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        holder.eventName.setText(eventList.get(position).getName());

        holder.setIsRecyclable(false);

        new Thread(() -> {
            mainHandler.post(() -> holder.eventPic.setImageDrawable(null));
            if (eventList.get(position).getImage() == null) {
                System.out.println(eventList.get(position).getName() + Arrays.toString(eventList.get(position).getImage()));
            } else {
                mainHandler.post(() -> holder.eventPic.setImageDrawable(null));
                byte[] decodedByte = eventList.get(position).getImage();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length, options);
                mainHandler.post(() -> holder.eventPic.setImageBitmap(bitmap));
            }
        }).start();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start event page
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
