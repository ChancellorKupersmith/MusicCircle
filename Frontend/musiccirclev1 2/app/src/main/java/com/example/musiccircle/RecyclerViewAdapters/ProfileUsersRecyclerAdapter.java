package com.example.musiccircle.RecyclerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.example.musiccircle.Activities.Profile_Page.ProfilePageActivity;
import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.R;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musiccircle.Entity.User;

import java.util.Arrays;
import java.util.List;


public class ProfileUsersRecyclerAdapter extends RecyclerView.Adapter<ProfileUsersRecyclerAdapter.ProfileViewHolder> {
    private static final String USER_USERNAME_KEY = "user_username";
    private static final String USER_KEY = "user";
    private static final String USER_PAGE_KEY = "user_page_username";

    List<User> userList;
    Context context;
    String loggedInUsername;
    User loggedInUser;
    Bitmap bmp;

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        ImageView userPic;
        TextView userName;

       public ProfileViewHolder(@NonNull View itemView) {
           super(itemView);
           userPic = itemView.findViewById(R.id.item_image_view_v2);
           userName = itemView.findViewById(R.id.item_name_view_v2);
       }

        public ImageView getUserPic() { return userPic; }
        public TextView getUserName() { return userName; }
    }

    public ProfileUsersRecyclerAdapter(List<User> userList, Context context, String loggedInUsername, User loggedInUser) {
        this.userList = userList;
        this.context = context;
        this.loggedInUsername = loggedInUsername;
        this.loggedInUser = loggedInUser;
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
        holder.userName.setText(userList.get(position).getUsername());

        holder.setIsRecyclable(false);

        new Thread(() -> {
            mainHandler.post(() -> holder.userPic.setImageDrawable(null));
            if (userList.get(position).getImage() == null) {
                System.out.println(userList.get(position).getUsername() + Arrays.toString(userList.get(position).getImage()));
            } else {
                mainHandler.post(() -> holder.userPic.setImageDrawable(null));
                byte[] decodedByte = userList.get(position).getImage();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length, options);
                mainHandler.post(() -> holder.userPic.setImageBitmap(bitmap));
            }
        }).start();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Bundle b = new Bundle();
                b.putString(USER_USERNAME_KEY, loggedInUsername);
                b.putSerializable(USER_KEY, loggedInUser);
                b.putString(USER_PAGE_KEY, userList.get(position).getUsername());
                Intent intent = new Intent(activity, ProfilePageActivity.class);
                intent.putExtras(b);
                activity.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }
}
