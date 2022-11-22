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
import com.example.musiccircle.Entity.Group;
import com.example.musiccircle.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musiccircle.Entity.User;

import java.util.Arrays;
import java.util.List;

public class ProfileGroupsRecyclerAdapter extends RecyclerView.Adapter<ProfileGroupsRecyclerAdapter.ProfileViewHolder> {
    List<Group> groupList;
    Context context;
    Bitmap bmp;

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        ImageView groupPic;
        TextView groupName;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            groupPic = itemView.findViewById(R.id.item_image_view_v2);
            groupName = itemView.findViewById(R.id.item_name_view_v2);
        }

        public ImageView getGroupPic() { return groupPic; }
        public TextView getGroupName() { return groupName; }
    }

    public ProfileGroupsRecyclerAdapter(List<Group> groupList, Context context) {
        this.groupList = groupList;
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
        holder.groupName.setText(groupList.get(position).getName());

        holder.setIsRecyclable(false);

        new Thread(() -> {
            mainHandler.post(() -> holder.groupPic.setImageDrawable(null));
            if (groupList.get(position).getImage() == null) {
                System.out.println(groupList.get(position).getName() + Arrays.toString(groupList.get(position).getImage()));
            } else {
                mainHandler.post(() -> holder.groupPic.setImageDrawable(null));
                byte[] decodedByte = groupList.get(position).getImage();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length, options);
                mainHandler.post(() -> holder.groupPic.setImageBitmap(bitmap));
            }
        }).start();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start group page
            }
        });
    }


    @Override
    public int getItemCount() {
        return groupList.size();
    }
}
