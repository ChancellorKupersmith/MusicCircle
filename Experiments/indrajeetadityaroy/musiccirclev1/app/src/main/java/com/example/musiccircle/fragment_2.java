package com.example.musiccircle;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.O)
public class fragment_2 extends Fragment {

    private Button postButton;
    private EditText firstname, lastname, username, email, password;
    LocalTime localTime = LocalTime.now();
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_2, container, false);

        View registbtn = view.findViewById(R.id.btn_register_2);
        postButton = (Button) registbtn;

        firstname = view.findViewById(R.id.firstname);
        lastname = view.findViewById(R.id.lastname);
        username = view.findViewById(R.id.username);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Register();
            }
        });

        return view;
    }

    private void Register() {

        final String firstname = this.firstname.getText().toString().trim();
        final String lastname = this.lastname.getText().toString().trim();
        final String username = this.username.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(getActivity());

       StringRequest sr = new StringRequest(Request.Method.POST, "http://192.168.56.1:8080/user/registration",
        //StringRequest sr = new StringRequest(Request.Method.POST, "http://10.24.227.244:8080/api/v1/user/registration",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("SUCCESSFUL USER REGISTRATION")){
                            Navigation.findNavController(view).navigate(R.id.action_fragment_2_to_fragment_3);
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
                params.put("firstName",firstname);
                params.put("lastName",lastname);
                params.put("username",username);
                params.put("email",email);
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
        queue.add(sr);
    }
}