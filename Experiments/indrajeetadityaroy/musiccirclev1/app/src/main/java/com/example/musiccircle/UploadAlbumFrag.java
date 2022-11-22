package com.example.musiccircle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class UploadAlbumFrag extends Fragment {

    private Button postButton;
    private EditText albumName, username, songId;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_upload_album, container, false);
        //View btn_upload_album = view.findViewById(R.id.btn_Upload_Album);
        postButton = (Button) view.findViewById(R.id.btn_Upload_Album);;
        albumName = view.findViewById(R.id.Album_Name_Input);
        username = view.findViewById(R.id.Artist_Name_Input);
        songId = view.findViewById(R.id.Song_Id_Input);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadAlbum();
            }
        });
        return view;
    }

    private void UploadAlbum(){
        final String name = this.albumName.getText().toString().trim();
        final String creatorUsername = this.username.getText().toString().trim();
        final String song = this.songId.getText().toString().trim();


        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest sr = new StringRequest(Request.Method.POST, "http://192.168.56.1:8080/albumsUp",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("SUCCESSFUL GROUP REGISTRATION")){
                            Navigation.findNavController(view).navigate(R.id.action_uploadAlbumFrag_to_fragment_4);
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
                params.put("name", name);
                params.put("username", creatorUsername);
                params.put("songId", song);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }
}