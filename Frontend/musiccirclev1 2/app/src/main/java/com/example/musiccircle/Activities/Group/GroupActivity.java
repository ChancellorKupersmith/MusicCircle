package com.example.musiccircle.Activities.Group;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.musiccircle.Fragments.Profile.ViewOtherProfile_fragment;
import com.example.musiccircle.R;
import com.example.musiccircle.Services.StompUtils;

import org.json.JSONException;
import org.json.JSONObject;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

@SuppressWarnings({"FieldCanBeLocal", "ResultOfMethodCallIgnored", "CheckResult"})
public class GroupActivity extends AppCompatActivity {
    private EditText groupIdText;
    private EditText nameText;
    private Button sendButton;

    private String groupId;
    private String userId;
    private String loggedin_profile_username;

    LinearLayout a;

    private void init() {
        groupIdText = findViewById(R.id.group_id);
        nameText = findViewById(R.id.name);
        sendButton = findViewById(R.id.send);
        sendButton.setEnabled(true);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        a = findViewById(R.id.group_chat_linear);

        Intent intent3 = getIntent();
        userId = intent3.getStringExtra("userId");
        groupId = intent3.getStringExtra("groupId");
        loggedin_profile_username = intent3.getStringExtra("loggedin_profile_username");
        System.out.println(userId);
        System.out.println(loggedin_profile_username);
        this.init();
        groupIdText.setText(groupId);
        groupIdText.setVisibility(View.GONE);

        StompClient stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://192.168.1.154:8080/im/websocket");
        Toast.makeText(this, "Start connecting to server", Toast.LENGTH_SHORT).show();
        stompClient.connect();
        StompUtils.lifecycle(stompClient);

        groupIdText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                a.removeAllViewsInLayout();
            }
        });

        stompClient.topic(("/g/" + groupId)).subscribe(stompMessage -> {
            JSONObject jsonObject = new JSONObject(stompMessage.getPayload());
            Log.i("TAG", "Receive: " + stompMessage.getPayload());
            runOnUiThread(() -> {
                try {
                    TextView tv = new TextView(this);
                    TextView tv1 = new TextView(this);
                    String s= jsonObject.getString("response");
                    String s1= jsonObject.getString("fromUserName");
                    tv.setText(s);
                    tv1.setOnClickListener(view -> {
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        Bundle b1 = new Bundle();
                        b1.putString("user_profile_username", tv1.getText().toString().trim());
                        b1.putString("loggedin_profile_username",loggedin_profile_username);
                        Fragment myFragment = new ViewOtherProfile_fragment();
                        myFragment.setArguments(b1);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.group_chat_container, myFragment).addToBackStack(null).commit();
                    });
                    tv1.setText(s1);
                    tv.setTextColor(Color.WHITE);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) LinearLayout.LayoutParams.WRAP_CONTENT, (int) LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = 50;
                    params.topMargin = 50;
                    tv.setPadding(20, 20, 20, 20);
                    tv.setLayoutParams(params);
                    tv1.setBackground(getResources().getDrawable(R.drawable.circular_box));
                    tv1.setLayoutParams(params);
                    tv.setBackground(getResources().getDrawable(R.drawable.tvbackground));
                    a.addView(tv1);
                    a.addView(tv);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        });

        sendButton.setOnClickListener(v -> {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("fromUserID", userId);
                jsonObject.put("name", nameText.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (groupId == null || groupId.length() == 0) {
                groupId = groupIdText.getText().toString();
            }
            stompClient.send(("/group/" + groupId), jsonObject.toString()).subscribe();
            nameText.setText("");
        });

    }
}



