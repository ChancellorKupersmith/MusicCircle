package com.example.musiccircle.Fragments.Comments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Entity.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentFragmentRequests {
    protected List<Comment> commentList;
    protected Bitmap bmp;
    protected FragmentActivity activity;

    public CommentFragmentRequests(FragmentActivity getActivity) {
        activity = getActivity;
    }

    public List<Comment> getComments(Long songIdKey) {
        commentList = new ArrayList<Comment>();

        RequestQueue queue = Volley.newRequestQueue(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://10.24.227.244:8080/comments/by_song/" + songIdKey,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray json_response = new JSONArray(response);
                            System.out.println(response);
                            for (int i = 0; i < json_response.length(); i++) {
                                JSONObject JSONComment = json_response.getJSONObject(i);
                                Comment comment = new Comment();
                                comment.setId(JSONComment.getLong("id"));
                                comment.setAudioFileId(songIdKey);
                                JSONObject JSONUser = JSONComment.getJSONObject("commenter");
                                comment.setCommenterId(JSONUser.getString("username"));
                                comment.setText(JSONComment.getString("text"));
                                commentList.add(comment);
                            }
                        } catch (JSONException e) {
                            Log.e("ResponseJSONException", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        })
        {
            @Override
            protected Map<String,String> getParams() {
                Map<String,String> params = new HashMap<>();
                //params.put("songId", songIdKey.toString());
                return params;
            }
        };
        queue.add(stringRequest);

        return commentList;
    }

    public void postComment(String commentText, Long songIdKey, String username) {
        RequestQueue queue = Volley.newRequestQueue(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.24.227.244:8080/comments/up/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("SUCCESSFUL COMMENT REGISTRATION")){
                }
                Log.e("HttpClient", "success! response: " + response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("comment", commentText);
                params.put("songId", songIdKey.toString());
                params.put("username", username);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public Bitmap getUserProfilePic(String username) {

        RequestQueue queue = Volley.newRequestQueue(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://10.24.227.244:8080/user/pic/" + username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        byte[] decodedImageBytes = Base64.decode(response, Base64.DEFAULT);
                        bmp = BitmapFactory.decodeByteArray(decodedImageBytes, 0, decodedImageBytes.length);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

        });
        queue.add(stringRequest);

        return bmp;
    }
}
