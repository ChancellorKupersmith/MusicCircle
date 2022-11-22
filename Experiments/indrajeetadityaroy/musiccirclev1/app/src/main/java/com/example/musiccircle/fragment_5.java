package com.example.musiccircle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Entity.Album;
import com.example.musiccircle.Entity.Event;
import com.example.musiccircle.Entity.Group;
import com.example.musiccircle.Entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class fragment_5 extends Fragment {

    EditText search_word;
    Button searchButton;
    RecyclerView search_results;
    SearchAdapter searchAdapter;
    List<entities> entitylist = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_5, container, false);

        search_word = view.findViewById(R.id.username_to_search);
        searchButton = view.findViewById(R.id.search_username);
        search_results = view.findViewById(R.id.searched_users);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        search_results.setLayoutManager(mLayoutManager);
        search_results.setItemAnimator(new DefaultItemAnimator());
        searchAdapter = new SearchAdapter();
        search_results.setAdapter(searchAdapter);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        return view;
    }

    public void search() {
        entitylist.clear();
        String key = search_word.getText().toString();
        String search_url4 = "http://192.168.56.1:8080/events/" + key;
        String search_url3 = "http://192.168.56.1:8080/albums/find/" + key;
        String search_url2 = "http://192.168.56.1:8080/group/find/" + key;
        String search_url = "http://192.168.56.1:8080/user/find/" + key;
//        String search
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json_response = new JSONObject(response);
                            System.out.println(response);
                            for (int i = 0; i < 1; i++) {
                                User user = new User();
                                user.setId(json_response.getLong("id"));
                                user.setFirstName(json_response.getString("firstName"));
                                user.setLastName(json_response.getString("lastName"));
                                user.setEmail(json_response.getString("email"));
                                user.setUsername(json_response.getString("username"));
                                entitylist.add(user);
                            }
                            searchAdapter.setAdapterList(entitylist);
                        } catch (JSONException e) {
                            Log.e("ResponseJSONException", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

        });
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, search_url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json_response2 = new JSONObject(response);
                            System.out.println(response);
                            for (int i = 0; i < 1; i++) {
                                Group group = new Group();
                                group.setId(json_response2.getLong("id"));
                                group.setName(json_response2.getString("name"));
                                group.setDescription(json_response2.getString("description"));
                                entitylist.add(group);
                            }
                            searchAdapter.setAdapterList(entitylist);
                        } catch (JSONException e) {
                            Log.e("ResponseJSONException", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

        });
        StringRequest stringRequest3 = new StringRequest(Request.Method.GET, search_url3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json_response3 = new JSONObject(response);
                            System.out.println(response);
                            for (int i = 0; i < 1; i++) {
                                Album album = new Album();
                                album.setId(json_response3.getLong("id"));
                                album.setAlbumName(json_response3.getString("albumName"));
                                album.setArtist(json_response3.getString("artist"));
                                entitylist.add(album);
                            }
                            searchAdapter.setAdapterList(entitylist);
                        } catch (JSONException e) {
                            Log.e("ResponseJSONException", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

        });
        StringRequest stringRequest4 = new StringRequest(Request.Method.GET, search_url4, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json_response = new JSONObject(response);
                    System.out.println(response);
                    for (int i = 0; i < 1; i++) {
                        Event event = new Event();
                        event.setId(json_response.getLong("id"));
                        event.setName(json_response.getString("name"));
                        event.setLocation(json_response.getString("location"));
                        event.setDateTime(json_response.getString("dateTime"));
                        entitylist.add(event);
                    }
                    searchAdapter.setAdapterList(entitylist);
                } catch (JSONException e) {
                    Log.e("ResponseJSONException", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError error) {}
        });
        queue.add(stringRequest);
        queue.add(stringRequest2);
        queue.add(stringRequest3);
        queue.add(stringRequest4);
    }
}