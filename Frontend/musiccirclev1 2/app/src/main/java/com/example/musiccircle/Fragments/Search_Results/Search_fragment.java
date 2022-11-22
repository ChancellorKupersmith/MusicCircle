package com.example.musiccircle.Fragments.Search_Results;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Entity.UserParcelable;
import com.example.musiccircle.Fragments.Home.HomeUserRecyclerViewAdapter;
import com.example.musiccircle.R;
import com.example.musiccircle.RecyclerViewAdapters.SearchAdapter;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.Entity.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Search_fragment extends Fragment {

    EditText search_word;
    RecyclerView search_results;
    SearchAdapter searchAdapter;
    List<entities> entitylist = new ArrayList<>();
    String loggedin_profile_username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_page, container, false);
        Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out);
        view.startAnimation(anim);
        search_word = view.findViewById(R.id.username_to_search);
        search_results = view.findViewById(R.id.searched_users);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        search_results.setLayoutManager(mLayoutManager);
        search_results.setItemAnimator(new DefaultItemAnimator());
        searchAdapter = new SearchAdapter();
        search_results.setAdapter(searchAdapter);

        Bundle bundle = getArguments();
        loggedin_profile_username = bundle.getString("loggedin_profile_username");
        System.out.println(loggedin_profile_username);

        search_word.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    private Timer timer = new Timer();
                    private final long DELAY = 500; // milliseconds

                    @Override
                    public void afterTextChanged(final Editable s) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                        search(s.toString().trim());
                                    }
                                },
                                DELAY
                        );
                    }
                }
        );
        return view;
    }

    public void search(final String key) {
        entitylist.clear();
        String search_url = "http://10.24.227.244:8080/user/name/" + key;
        String search_url2 = "http://10.24.227.244:8080/audioFiles/name/" + key;
        String search_url3 = "http://10.24.227.244:8080/group/name/" + key;
        String search_url4 = "http://10.24.227.244:8080/events/name/" + key;
        String search_url5 = "http://10.24.227.244:8080/user/find/" + key;
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray json_response = new JSONArray(response);
                            for (int i = 0; i < json_response.length(); i++) {
                                UserParcelable user = new UserParcelable();
                                user.setId(json_response.getJSONObject(i).getLong("id"));
                                user.setFirstName(json_response.getJSONObject(i).getString("firstName"));
                                user.setLastName(json_response.getJSONObject(i).getString("lastName"));
                                user.setEmail(json_response.getJSONObject(i).getString("email"));
                                user.setUsername(json_response.getJSONObject(i).getString("username"));
                                entitylist.add(user);
                            }
                        } catch (JSONException e) {
                            Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

        });
        queue.add(stringRequest);
    }

    public void getImagesUser(List<UserParcelable> a) {
        for (int i = 0; i < a.size(); i++) {
            getUserImage(a.get(i));
        }
    }

    public void getUserImage(UserParcelable userParcelable) {
        String search_url = "http://10.24.227.244:8080/user/pic/" + userParcelable.getUsername();
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        queue.getCache().clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    for (int i = 0; i < 1; i++) {
                        byte[] decodedImageBytes = Base64.decode(response, Base64.DEFAULT);
                        userParcelable.setImage(decodedImageBytes);
                    }
                    searchAdapter.setAdapterList(entitylist);
                    searchAdapter.setLoggedInUsername(loggedin_profile_username);
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }
}
