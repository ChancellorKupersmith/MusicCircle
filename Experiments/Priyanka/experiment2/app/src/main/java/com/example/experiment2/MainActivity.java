package com.example.experiment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private RequestQueue req;
    String url = "https://9c26f84a-df2f-46e5-a833-87a4fba1b17c.mock.pstmn.io/test1" ; //find out the url for the login?

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter = 5; //After 5 attempts button will be disabled

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.etName); //assigns this variable to the respective ID in XML Layout
        Password = (EditText)findViewById(R.id.etPassword);
       // Info = (TextView)findViewById(R.id.tvInfo);
        Login = (Button)findViewById(R.id.btnLogin);


        /**
         * Below Login button validates if the username and password is equal to the required data
         */
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });

        req = Volley.newRequestQueue(this);

    }
    /**
     * makes request to the server to see if correct username and password if provided.
     * @param username
     * @param password
     */
    private boolean makePostRequest(String username, final String password) {

        JSONObject user_object = new JSONObject();
        try {

            user_object.put("username", username);
            user_object.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, user_object, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();

                }
               send();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error

            }
        });
        req.add(objectRequest);
        return false;

    }

    private void send() {
        Intent intent = new Intent (MainActivity.this, SecondActivity.class); //moves from activity to another
        startActivity(intent);

    }

    private void validate(String userName, String userPassword){

        if(!makePostRequest(userName, userPassword)){

              counter--;  //Counting downwards

              Toast.makeText(this.getApplicationContext(),"No. of attempts remaining: "+counter,Toast.LENGTH_SHORT).show();

              if(counter == 0){ //this is when the Login Button will be disabled
                  Login.setEnabled(false);
              }
        }
    }
}