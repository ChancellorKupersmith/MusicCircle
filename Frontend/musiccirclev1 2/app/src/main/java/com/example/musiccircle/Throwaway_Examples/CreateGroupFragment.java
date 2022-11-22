package com.example.musiccircle.Throwaway_Examples;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
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
//import com.example.musiccircle.CreateGroupFragmentDirections;
import com.example.musiccircle.R;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateGroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateGroupFragment extends Fragment {
    //Bundle Keys
    private static final String USER_USERNAME_KEY = "user_username";

    //Bundle Variables
    private String user_username;

    //View Variables
    private Button postButton;
    private EditText groupname, groupdescrip;
    View view;

    public CreateGroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user_username User username
     * @return A new instance of fragment CreateGroupFragment.
     */
    public static CreateGroupFragment newInstance(String user_username) {
        CreateGroupFragment fragment = new CreateGroupFragment();
        Bundle args = new Bundle();
        args.putString(USER_USERNAME_KEY, user_username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user_username = getArguments().getString(USER_USERNAME_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_group, container, false);
        postButton = view.findViewById(R.id.btn_group_register_2);
        groupname = view.findViewById(R.id.group_name);
        groupdescrip = view.findViewById(R.id.group_description);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
        return view;
    }

    private void Register(){

        final String groupname = this.groupname.getText().toString().trim();
        final String groupdescrip = this.groupdescrip.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest sr = new StringRequest(Request.Method.POST, "http://10.24.227.244:8080/group/registration",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("SUCCESSFUL GROUP REGISTRATION")){
                            NavDirections action = CreateGroupFragmentDirections.actionCreateGroupFragmentToDiscoverFragment(user_username);
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
                params.put("name",groupname);
                params.put("description",groupdescrip);
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