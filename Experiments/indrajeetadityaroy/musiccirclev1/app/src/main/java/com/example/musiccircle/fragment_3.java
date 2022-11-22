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
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class fragment_3 extends Fragment {

    private Button loginButton;
    private EditText username, password;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_3, container, false);

        loginButton = view.findViewById(R.id.btn_login_2);
        username = view.findViewById(R.id.login_username);
        password = view.findViewById(R.id.login_password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
        return view;
    }

    private void Login(){

        final String username = this.username.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        //StringRequest sr = new StringRequest(Request.Method.POST, "http://10.24.227.244:8080/api/v1/user/login",
        StringRequest sr = new StringRequest(Request.Method.POST, "http://192.168.56.1:8080/user/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("SUCCESSFUL USER LOGIN")){
                            fragment_3Directions.ActionFragment3ToFragment4 action = fragment_3Directions.actionFragment3ToFragment4(username);
                            Navigation.findNavController(view).navigate(action);
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
                params.put("username",username);
                params.put("password",password);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
//        sr.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {
//                return 30000;
//            }
//
//            @Override
//            public int getCurrentRetryCount() {
//                return 30000;
//            }
//
//            @Override
//            public void retry(VolleyError error) throws VolleyError {
//
//            }
//        });
        queue.add(sr);
    }
}