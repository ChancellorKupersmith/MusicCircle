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

public class fragment7 extends Fragment {
    private Button postButton;
    private EditText username, eventName, eventLocation, eventDate, eventTime;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment7, container, false);

        View event_register_btn = view.findViewById(R.id.button_event_register);
        postButton = (Button)event_register_btn;

        username = view.findViewById(R.id.username_input);
        eventName = view.findViewById(R.id.event_name_input);
        eventLocation = view.findViewById(R.id.event_location_input);
        eventDate = view.findViewById(R.id.date_input);
        eventTime = view.findViewById(R.id.time_input);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });

        return view;
    }

    private void Register() {
        final String username = this.username.getText().toString().trim();
        final String eventName = this.eventName.getText().toString().trim();
        final String eventLocation = this.eventLocation.getText().toString().trim();
        final String eventDateTime = this.eventDate.getText().toString().trim() + " " + this.eventTime.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest sr = new StringRequest(Request.Method.POST, "http://10.36.168.120:8080/events/user/name/location/dateTime", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("SUCCESSFUL EVENT REGISTRATION")){
                    Navigation.findNavController(view).navigate(R.id.action_fragment7_to_fragment_4);
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
                params.put("user", username);
                params.put("name", eventName);
                params.put("location", eventLocation);
                params.put("dateTime", eventDateTime);
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