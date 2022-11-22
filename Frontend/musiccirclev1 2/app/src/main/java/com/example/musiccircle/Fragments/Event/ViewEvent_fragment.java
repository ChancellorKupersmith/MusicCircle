package com.example.musiccircle.Fragments.Event;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.Entity.UserParcelable;
import com.example.musiccircle.Fragments.Home.HomeUserRecyclerViewAdapter;
import com.example.musiccircle.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ViewEvent_fragment extends Fragment {
    String destination;
    String event_name;
    String event_description;
    String event_creator;
    String loggedin_user;
    TextView event_name_view;
    TextView event_performer_count;
    TextView event_description_view;
    ImageView eventImageView;

    ArrayList<UserParcelable> a;
    byte[] eventImage;
    Button delete;

    RecyclerView performer_recyclerView;
    HomeUserRecyclerViewAdapter performer_adapter;
    RecyclerView.LayoutManager PerformersRecyclerViewLayoutManager;
    LinearLayoutManager UserRecycler_HorizontalLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event, container, false);
        Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out);
        view.startAnimation(anim);

        Bundle bundle = getArguments();
        assert bundle != null;
        destination = bundle.getString("destlocation");
        event_name = bundle.getString("name");
        event_description = bundle.getString("description");
        event_creator = bundle.getString("creator");
        a = bundle.getParcelableArrayList("performers");
        eventImage = bundle.getByteArray("eventImage");
        //System.out.println(Arrays.toString(eventImage));
        loggedin_user = bundle.getString("loggedin_profile_username");

        eventImageView = view.findViewById(R.id.imageview_event);
        event_name_view = view.findViewById(R.id.event_name);
        event_performer_count = view.findViewById(R.id.num_user_performers);

        performer_recyclerView = view.findViewById(R.id.event_performers_recycler_view);
        PerformersRecyclerViewLayoutManager = new LinearLayoutManager(requireContext());
        performer_recyclerView.setLayoutManager(PerformersRecyclerViewLayoutManager);
        UserRecycler_HorizontalLayout = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        performer_recyclerView.setLayoutManager(UserRecycler_HorizontalLayout);
        PerformersRecyclerViewLayoutManager = new LinearLayoutManager(requireContext());
        UserRecycler_HorizontalLayout = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        getUserImage(a.get(0));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeByteArray(eventImage, 0, eventImage.length, options);
        eventImageView.setImageBitmap(bitmap);
        event_name_view.setText(event_name);

        event_performer_count.setText(String.valueOf(a.size()));
        return view;
    }

    public void getImagesUser(List<UserParcelable> a) {
        for (int i = 0; i < a.size(); i++) {
            getUserImage(a.get(i));
        }

        /**
         performer_recyclerView.setHasFixedSize(true);
         performer_adapter = new HomeUserRecyclerViewAdapter(performersList);
         performer_adapter.setLoggedinUser("tester1");
         performer_recyclerView.setAdapter(performer_adapter);
         performer_adapter.notifyDataSetChanged();
         **/

    }

    public void getUserImage(UserParcelable user) {
        String search_url = "http://10.24.227.244:8080/user/pic/" + user.getUsername();
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        queue.getCache().clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    for (int i = 0; i < 1; i++) {
                        byte[] decodedImageBytes = Base64.decode(response, Base64.DEFAULT);
                        System.out.println(Arrays.toString(decodedImageBytes));
                        user.setImage(decodedImageBytes);
                    }
                    performer_recyclerView.setHasFixedSize(true);
                    performer_adapter = new HomeUserRecyclerViewAdapter(a, loggedin_user);
                    performer_adapter.setLoggedinUser("tester1");
                    performer_recyclerView.setAdapter(performer_adapter);
                    performer_adapter.notifyDataSetChanged();
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }
}

