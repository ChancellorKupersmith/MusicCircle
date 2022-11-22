package com.example.musiccircle.Fragments.Group;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Activities.Group.GroupActivity;
import com.example.musiccircle.Activities.Home_Page.NavMenuActivity;
import com.example.musiccircle.ChoosePerformersDialogFragment;
import com.example.musiccircle.Activities.Home_Page.NavMenuActivity;
import com.example.musiccircle.Entity.Event;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.R;
import com.example.musiccircle.Fragments.Event.EventChoosePerformersAdapter;
import com.example.musiccircle.Fragments.Home.HomeEventRecyclerViewAdapter;
import com.example.musiccircle.ViewDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ViewGroupProfile_fragment extends Fragment {
    TextView textView1;
    TextView textView2;
    ImageView image;
    String group_profile_name;
    String group_Id;
    String loggedin_profile_username;
    String loggedInUserId;
    String group_creator_username;
    Button join_group;
    Button join_group_chat;
    Button see_group_members;
    Button delete_group;
    ArrayList<User> group_members = new ArrayList<>();
    byte[] data;

    RecyclerView events_recyclerView;
    ArrayList<Event> entitylist;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    HomeEventRecyclerViewAdapter adapter;
    LinearLayoutManager HorizontalLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_other_group_profile, container, false);
        Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out);
        view.startAnimation(anim);

        textView1 = view.findViewById(R.id.group_name);
        textView2 = view.findViewById(R.id.num_group_members);
        image = view.findViewById(R.id.imageview_group_profile);
        join_group = view.findViewById(R.id.join_group);
        join_group_chat = view.findViewById(R.id.join_group_chat);
        delete_group = view.findViewById(R.id.delete_group);
        see_group_members = view.findViewById(R.id.see_group_members);
        join_group_chat.setActivated(false);

        events_recyclerView = view.findViewById(R.id.group_events_recycler_view);
        RecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        events_recyclerView.setLayoutManager(RecyclerViewLayoutManager);
        AddItemsToRecyclerViewArrayList();
        HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        events_recyclerView.setLayoutManager(HorizontalLayout);

        Bundle bundle = getArguments();
        assert bundle != null;
        group_profile_name = bundle.getString("group_profile_name");
        loggedin_profile_username = bundle.getString("loggedin_profile_username");
        textView1.setText(group_profile_name);

        refreshCount();
        fetchProfile();

        join_group.setOnClickListener(view1 -> {
            if (join_group.getText().equals("LEAVE GROUP")) {
                leave_group(group_profile_name,loggedin_profile_username);
            } else {
                join_group(group_profile_name,loggedin_profile_username);
            }
        });

        join_group_chat.setOnClickListener(view12 -> {
            if(join_group.isSelected()){
                Intent intent = new Intent();
                intent.setClass(requireContext(), GroupActivity.class);
                intent.putExtra("loggedin_profile_username",loggedin_profile_username);
                intent.putExtra("userId",loggedInUserId);
                intent.putExtra("groupId",group_Id);
                startActivity(intent);
            }else{
                ViewDialog alert = new ViewDialog();
                alert.showDialog(requireContext(),"NOT A GROUP MEMBER");
            }

        });

        delete_group.setOnClickListener(view13 -> deleteGroup(requireContext(),group_profile_name));

        if(loggedin_profile_username.equals(group_creator_username)){
            delete_group.setVisibility(View.VISIBLE);
        }else{
            delete_group.setVisibility(View.GONE);
        }

        //see_group_members.setOnClickListener(view14 -> new ChoosePerformersDialogFragment(requireContext(),group_members).show(getChildFragmentManager(), "dialog"));

        return view;
    }

    void refreshCount() {
        String search_url = "http://192.168.1.154:8080/group/getmembers/" + group_profile_name;
        String search_url2 = "http://192.168.1.154:8080/group/find/" + group_profile_name;
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        if(alreadyfollowing(jsonArray,loggedin_profile_username)){
                            join_group.setSelected(true);
                            join_group_chat.setSelected(true);
                            join_group.setText("LEAVE GROUP");
                        }else{
                            join_group.setSelected(false);
                            join_group_chat.setSelected(false);
                            join_group.setText("JOIN GROUP");
                        }
                        textView2.setText(String.valueOf(jsonArray.length()));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            User a = new User();
                            a.setUsername(jsonArray.getString(i));
                            group_members.add(a);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println(response);
                }, error -> {
        });
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, search_url2,
                response -> {
                    try {
                        JSONObject json_response = new JSONObject(response);
                        for (int i = 0; i < 1; i++) {
                            group_creator_username = json_response.getString("creator");
                            group_Id = json_response.getString("id");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println(response);
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        stringRequest1.setShouldCache(false);
        queue.add(stringRequest);
        queue.add(stringRequest1);
    }

    private boolean alreadyfollowing(JSONArray jsonArray, String usernameToFind) {
        return jsonArray.toString().contains(usernameToFind);
    }

    void join_group(final String str1, final String str2) {

        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String search_url = "http://192.168.1.154:8080/group/addtogroup/" + str1 + "/" + str2;
        StringRequest sr = new StringRequest(Request.Method.POST, search_url,
                response -> {
                    if (response.equals("SUCCESSFUL GROUP ADDITION")) {
                        refreshCount();
                    }
                    Log.e("HttpClient", "success! response: " + response);
                },
                error -> Log.e("HttpClient", "error: " + error.toString())) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name1", str1);
                params.put("name2", str2);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        sr.setShouldCache(false);
        queue.add(sr);
    }

    void leave_group(final String str1, final String str2) {
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String search_url = "http://192.168.1.154:8080/group/removefromgroup/" + str1 + "/" + str2;
        StringRequest sr = new StringRequest(Request.Method.POST, search_url,
                response -> {
                    if (response.equals("SUCCESSFUL GROUP REMOVAL")) {
                        refreshCount();
                    }
                    Log.e("HttpClient", "success! response: " + response);
                },
                error -> Log.e("HttpClient", "error: " + error.toString())) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name1", str1);
                params.put("name2", str2);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        sr.setShouldCache(false);
        queue.add(sr);
    }

    void deleteGroup(final Context context, final String str1) {
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String search_url = "http://192.168.1.154:8080/group/delete/" + str1;
        StringRequest sr = new StringRequest(Request.Method.DELETE, search_url,
                response -> {
                    System.out.println(response);
                    if (response.equals("SUCCESSFUL GROUP DELETION")) {
                        Intent i = new Intent(context, NavMenuActivity.class);
                        i.putExtra("name", loggedin_profile_username);
                        startActivity(i);
                    }
                },
                error -> {

                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", str1);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        sr.setShouldCache(false);
        queue.add(sr);
    }

    void fetchProfile() {
        String search_url = "http://192.168.1.154:8080/user/find/" + loggedin_profile_username;
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    try {
                        JSONObject json_response = new JSONObject(response);
                        for (int i = 0; i < 1; i++) {
                            loggedInUserId = json_response.getString("id");
                        }
                    } catch (JSONException e) {
                        Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                        e.printStackTrace();
                    }
                }, error -> {

        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }

    public void AddItemsToRecyclerViewArrayList() {
        entitylist = new ArrayList<>();
        getEvents();
    }

    public void getEvents() {
        entitylist.clear();
        String search_url = "http://10.24.227.244:8080/event/all";
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    try {
                        JSONArray json_response = new JSONArray(response);
                        for (int i = 0; i < json_response.length(); i++) {
                            Event event = new Event();
                            event.setId(json_response.getJSONObject(i).getLong("id"));
                            event.setEventName(json_response.getJSONObject(i).getString("name"));
                            event.setEventLocation(json_response.getJSONObject(i).getString("location"));
                            event.setEventDescription(json_response.getJSONObject(i).getString("description"));
                            event.setEventCreator(json_response.getJSONObject(i).getString("event_creator"));
                            entitylist.add(event);
                        }
                        adapter = new HomeEventRecyclerViewAdapter(entitylist);
                        System.out.println(entitylist);
                    } catch (JSONException e) {
                        Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                        e.printStackTrace();
                    }
                }, error -> {
        });

        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }
}

