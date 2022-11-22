package com.example.musiccircle.Fragments.Home;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.Fragments.Search_Results.Search_fragment;
import com.example.musiccircle.RecyclerViewAdapters.EventChoosePerformersAdapter;
import com.example.musiccircle.Activities.Search_Results_Page.SearchActivity;
import com.example.musiccircle.R;

import com.example.musiccircle.Entity.User;
import com.example.musiccircle.Services.NotificationService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Home_fragment extends Fragment {

    public static final String USER_KEY= "user";
    public static final String USER_USERNAME_KEY = "user_username";
    private static final String TRACKLIST_KEY = "track_list";
    User loggednUser;
    ArrayList<AudioFile> tracklist;
    TextView textView, go_to_search;
    String username;
    ContentResolver resolver;
    ArrayList<User> users;
    Button local_home;
    Button global_home;
    NotificationService notificationService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loggednUser = (User) getArguments().getSerializable("user");
        }
        Intent notificationServiceIntent = new Intent(getContext(), NotificationService.class);
        Bundle serviceBundle = new Bundle();
        serviceBundle.putSerializable(USER_KEY, loggednUser);
        serviceBundle.putString(USER_USERNAME_KEY, username);
        System.out.println("home fragment user: " + loggednUser);
        System.out.println("home fragment username: " + username);
        notificationServiceIntent.putExtras(serviceBundle);
        getContext().bindService(notificationServiceIntent, notificationConnection, Context.BIND_AUTO_CREATE);
    }

    public ServiceConnection notificationConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            NotificationService.LocalBinder binder = (NotificationService.LocalBinder) service;
            notificationService = binder.getService();
            Log.d("ServiceConnection","connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("ServiceConnection","disconnected");
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);
        local_home = view.findViewById(R.id.local_btn);
        global_home = view.findViewById(R.id.global_btn);

        System.out.println(loggednUser);
        HomeGlobalFragment a1 = HomeGlobalFragment.newInstance(loggednUser);
        //a1.setLoggedInUser(loggednUser);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_container, a1).commit();

        local_home.setSelected(true);
        local_home.setTextColor(Color.WHITE);
        global_home.setSelected(false);
        global_home.setTextColor(Color.BLACK);

        global_home.setOnClickListener(view1 -> {
            global_home.setSelected(true);
            global_home.setTextColor(Color.WHITE);
            local_home.setSelected(false);
            local_home.setTextColor(Color.BLACK);
            System.out.println(loggednUser);
            HomeGlobalFragment a = HomeGlobalFragment.newInstance(loggednUser);
            //a.setLoggedInUser(loggednUser);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_container, a).commit();
        });

        local_home.setOnClickListener(view12 -> {
            local_home.setSelected(true);
            local_home.setTextColor(Color.WHITE);
            global_home.setSelected(false);
            global_home.setTextColor(Color.BLACK);
            HomeLocalFragment a = new HomeLocalFragment();
            a.setLoggedInUser(loggednUser);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_container, a).commit();
        });



        Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out);
        view.startAnimation(anim);
        textView = view.findViewById(R.id.welcome_text);
        Bundle bundle = getArguments();
        assert bundle != null;
        loggednUser = (User) bundle.getSerializable(USER_KEY);
        tracklist = (ArrayList<AudioFile>) bundle.getSerializable(TRACKLIST_KEY);
        String welcomeName = loggednUser.getArtistName();
        username = loggednUser.getUsername();
        textView.setText("Hello " + welcomeName);
        final Bundle b1 = new Bundle();
        b1.putString("loggedin_profile_username", username);

        go_to_search = view.findViewById(R.id.gotosearch);
        go_to_search.setOnClickListener(view1 -> {
            Fragment fragment = new Search_fragment();
            fragment.setArguments(b1);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });


        return view;
    }
}

//import android.app.Activity;
//import android.app.Notification;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.os.Build;
//import android.os.Bundle;
//
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.DialogFragment;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.recyclerview.widget.DividerItemDecoration;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.IBinder;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.musiccircle.Activities.Events_Page.MapActivity;
//import com.example.musiccircle.Activities.Music_Player_Page.MusicPlayerActivity;
//import com.example.musiccircle.Activities.Search_Results_Page.SearchActivity;
//import com.example.musiccircle.Entity.AudioFile;
//import com.example.musiccircle.Entity.Playlist;
//import com.example.musiccircle.Fragments.Search_Results.Search_fragment;
//import com.example.musiccircle.R;
//import com.example.musiccircle.RecyclerViewAdapters.EventChoosePerformersAdapter;
//import com.example.musiccircle.RecyclerViewAdapters.EventRecyclerViewAdapter;
//import com.example.musiccircle.Entity.Event;
//import com.example.musiccircle.Entity.User;
//import com.example.musiccircle.Services.AudioPlayerService;
//import com.example.musiccircle.Services.NotificationService;
//import com.google.android.material.bottomsheet.BottomSheetBehavior;
//import com.google.android.material.bottomsheet.BottomSheetDialog;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
//public class Home_fragment extends Fragment {
//    TextView textView, go_to_search;
//    View EventSheetView, GroupSheetView;
//    Button eventbtn, groupbtn, musicplayerbtn;
//    EditText eventname, eventdescription, eventaddress, eventcity, eventstate, eventzipcode, eventcountry;
//    private static final String USER_USERNAME_KEY = "user_username";
//    private static final String USER_KEY = "user";
//    private static final String TRACKLIST_KEY = "track_list";
//    RecyclerView recyclerView;
//    ArrayList<Event> entitylist;
//    RecyclerView.LayoutManager RecyclerViewLayoutManager;
//    EventRecyclerViewAdapter adapter;
//    LinearLayoutManager HorizontalLayout;
//    String username;
//    private User user;
//    private NotificationService notificationService;
//    private boolean firstLogin = true;
//    ArrayList<User> users;
//    ArrayList<AudioFile> trackList;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.home_fragment, container, false);
//        Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out);
//        view.startAnimation(anim);
//
//        trackList = new ArrayList<AudioFile>();
//        testGetSongs();
//
//
//
//        textView = view.findViewById(R.id.welcome_text);
//        Bundle bundle = getArguments();
//        assert bundle != null;
//        username = bundle.getString("message");
//        user = (User) bundle.getSerializable(USER_KEY);
//        textView.setText("Hello " + username);
//        final Bundle b1 = new Bundle();
//        b1.putString("loggedin_profile_username", username);
//        //Start Notification service
//        Intent notificationServiceIntent = new Intent(getContext(), NotificationService.class);
//        Bundle serviceBundle = new Bundle();
//        serviceBundle.putSerializable(USER_KEY, user);
//        serviceBundle.putString(USER_USERNAME_KEY, username);
//        notificationServiceIntent.putExtras(serviceBundle);
//        getContext().bindService(notificationServiceIntent, notificationConnection, Context.BIND_AUTO_CREATE);
//        if(firstLogin){
//            getOldNotifications();
//            firstLogin = false;
//        }
//        go_to_search = view.findViewById(R.id.gotosearch);
//        eventbtn = view.findViewById(R.id.event_btn_2);
//        groupbtn = view.findViewById(R.id.event_btn_4);
//        musicplayerbtn = view.findViewById(R.id.event_btn_5);
//
//        recyclerView = view.findViewById(R.id.events_recycler_view);
//        RecyclerViewLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(RecyclerViewLayoutManager);
//        AddItemsToRecyclerViewArrayList();
//        HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(HorizontalLayout);
//
//        musicplayerbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                Intent intent = new Intent(activity, MusicPlayerActivity.class);
//                Bundle bundle = new Bundle();
////                User artist = new User();
////                artist.setFirstName("Gorillaz");
////                artist.setLastName("");
////                artist.setUsername("gorillazBand");
////                User artist1 = new User();
////                artist1.setFirstName("Amine");
////                artist1.setLastName("");
////                artist1.setUsername("Amine");
////                User artist2 = new User();
////                artist2.setFirstName("J.");
////                artist2.setLastName("Cole");
////                artist2.setUsername("JCole");
////                AudioFile song1 = new AudioFile((long) 10, "filename", artist2, "KOD", 0, 0, true);
////                AudioFile song2 = new AudioFile((long) 9, "filename", artist, "DARE", 0, 0, true);
////                AudioFile song3 = new AudioFile((long) 13, "filename", artist2, "Window Pain (Outro)", 0, 0, true);
////                AudioFile song4 = new AudioFile((long) 12, "filename", artist1, "DR. WHOEVER", 0, 0, true);
////                AudioFile song5 = new AudioFile((long) 11, "filename", artist, "Hillbilly Man", 0, 0, true);
////                trackList.add(song1);
////                trackList.add(song2);
////                trackList.add(song3);
////                trackList.add(song4);
////                trackList.add(song5);
////                testGetSongs();
//                bundle.putSerializable(TRACKLIST_KEY, trackList);
//                bundle.putString(USER_USERNAME_KEY, username);
//                intent.putExtras(bundle);
//                if(trackList.size() > 0){
//                    startActivity(intent);
//                }
//                else{
//                    int duration = Toast.LENGTH_SHORT;
//                    Toast loading = Toast.makeText(getContext(), "Loading track list\none moment", duration);
//                    loading.show();
//                }
//
//            }
//        });
//
//        go_to_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent go_to_search = new Intent(getContext(), SearchActivity.class);
//                startActivity(go_to_search);
////                Fragment fragment = new Search_fragment();
////                fragment.setArguments(b1);
////                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
////                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////                fragmentTransaction.replace(R.id.fragment_container, fragment);
////                fragmentTransaction.addToBackStack(null);
////                fragmentTransaction.commit();
//            }
//        });
//
//        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
//
//        GroupSheetView = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_sheet_group, (LinearLayout) view.findViewById(R.id.bottomSheetContainer2), false);
//
//        groupbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                GroupSheetView.findViewById(R.id.btn_group_1).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        bottomSheetDialog.dismiss();
//                    }
//                });
//                bottomSheetDialog.setContentView(GroupSheetView);
//                setupFullHeight(bottomSheetDialog);
//                bottomSheetDialog.show();
//            }
//        });
//
//        EventSheetView = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_sheet_event, (LinearLayout) view.findViewById(R.id.bottomSheetContainer1), false);
//
//        eventbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                eventname = EventSheetView.findViewById(R.id.event_name);
//                eventdescription = EventSheetView.findViewById(R.id.event_description);
//                eventaddress = EventSheetView.findViewById(R.id.event_street_address);
//                eventcity = EventSheetView.findViewById(R.id.event_city);
//                eventstate = EventSheetView.findViewById(R.id.event_state);
//                eventzipcode = EventSheetView.findViewById(R.id.event_zipcode);
//                eventcountry = EventSheetView.findViewById(R.id.event_country);
//                EventSheetView.findViewById(R.id.btn_event_1).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        RegisterEvent(bottomSheetDialog);
//                    }
//                });
//                bottomSheetDialog.setContentView(EventSheetView);
//                setupFullHeight(bottomSheetDialog);
//                bottomSheetDialog.show();
//            }
//        });
//
//        users = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            User user = new User();
//            user.setUsername("User" + (i + 1));
//            users.add(user);
//        }
//
//        return view;
//    }
//
//    private void getOldNotifications(){
//        RequestQueue queue = Volley.newRequestQueue(getContext());
//        StringRequest userOnline = new StringRequest(Request.Method.PUT, "http://10.24.227.244:8080/userOnline/" + username,
//                new Response.Listener<String>() {
//                    @RequiresApi(api = Build.VERSION_CODES.O)
//                    @Override
//                    public void onResponse(String response) {
//                        System.out.println(response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //Log.e("GET_OLD_NOTFI_ERROR", error.getMessage());
//            }
//        })
//        {
//            @Override
//            protected Map<String,String> getParams() {
//                Map<String,String> params = new HashMap<>();
//                return params;
//            }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("Content-Type","application/x-www-form-urlencoded");
//                return params;
//            }
//        };
//        StringRequest getOldNotfis = new StringRequest(Request.Method.GET, "http://10.24.227.244:8080/userOnline/" + username +"/getOld",
//                new Response.Listener<String>() {
//                    @RequiresApi(api = Build.VERSION_CODES.O)
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONArray JSONNotifications = new JSONArray(response);
//                            System.out.println(response);
//                            for(int i=0; i<JSONNotifications.length(); i++){
//                                notificationService.setNotificationText(JSONNotifications.getJSONObject(i).getString("text"));
//                                notificationService.setNotificationTitle(JSONNotifications.getJSONObject(i).getString("title"));
//                                notificationService.setUsername_from(JSONNotifications.getJSONObject(i).getString("user_from_username"));
//                                notificationService.setUsername_to_notify(username);
//                                notificationService.createNotification_and_send(JSONNotifications.getJSONObject(i).getString("channel_id"));
//                            }
//                        } catch (JSONException e) {
//                            Log.e("ResponseJSONException", e.getMessage());
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //Log.e("GET_OLD_NOTFI_ERROR", error.getMessage());
//            }
//        })
//        {
//            @Override
//            protected Map<String,String> getParams() {
//                Map<String,String> params = new HashMap<>();
//                return params;
//            }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("Content-Type","application/x-www-form-urlencoded");
//                return params;
//            }
//        };
//
//        queue.add(userOnline);
//        queue.add(getOldNotfis);
//    }
//    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
//        FrameLayout bottomSheet = bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
//        assert bottomSheet != null;
//        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
//        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
//
//        int windowHeight = getWindowHeight();
//        if (layoutParams != null) {
//            layoutParams.height = windowHeight;
//        }
//        bottomSheet.setLayoutParams(layoutParams);
//        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//    }
//
//    private int getWindowHeight() {
//        // Calculate window height for fullscreen use
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        ((Activity) requireContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        return displayMetrics.heightPixels;
//    }
//
//    public void AddItemsToRecyclerViewArrayList() {
//        entitylist = new ArrayList<>();
//        getEvents();
//
//    }
//
//    public void getEvents() {
//        entitylist.clear();
//        String search_url = "http://10.24.227.244:8080/event/all";
//        RequestQueue queue = Volley.newRequestQueue(requireActivity());
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONArray json_response = new JSONArray(response);
//                            for (int i = 0; i < json_response.length(); i++) {
//                                Event event = new Event();
//                                event.setId(json_response.getJSONObject(i).getLong("id"));
//                                event.setEventName(json_response.getJSONObject(i).getString("name"));
//                                event.setEventLocation(json_response.getJSONObject(i).getString("location"));
//                                event.setEventDescription(json_response.getJSONObject(i).getString("description"));
//                                event.setEventCreator(json_response.getJSONObject(i).getString("event_creator"));
//                                entitylist.add(event);
//                            }
//                            adapter = new EventRecyclerViewAdapter(entitylist);
//                            adapter.setLoggedinUser(username);
//                            recyclerView.setAdapter(adapter);
//                            System.out.println(entitylist);
//                        } catch (JSONException e) {
//                            Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//
//        });
//
//        queue.add(stringRequest);
//    }
//
//    public ServiceConnection notificationConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            NotificationService.LocalBinder binder = (NotificationService.LocalBinder) service;
//            notificationService = binder.getService();
//            Log.d("ServiceConnection","connected");
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            Log.d("ServiceConnection","disconnected");
//        }
//    };
//    private void RegisterEvent(final BottomSheetDialog bottomSheetDialog) {
//
//        final String eventname = this.eventname.getText().toString().trim();
//        final String eventdescription = this.eventdescription.getText().toString().trim();
//        final String eventaddress = this.eventaddress.getText().toString().trim();
//        final String eventcity = this.eventcity.getText().toString().trim();
//        final String eventstate = this.eventstate.getText().toString().trim();
//        final String eventzipcode = this.eventzipcode.getText().toString().trim();
//        final String eventcountry = this.eventcountry.getText().toString().trim();
//
//        RequestQueue queue = Volley.newRequestQueue(requireActivity());
//
//        StringRequest sr = new StringRequest(Request.Method.POST, "http://192.168.1.154:8080/event/registration",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if (response.equals("SUCCESSFUL EVENT REGISTRATION")) {
//                            bottomSheetDialog.dismiss();
//                            getEvents();
//                        }
//                        Log.e("HttpClient", "success! response: " + response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("HttpClient", "error: " + error.toString());
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("name", eventname);
//                params.put("streetAddress", eventaddress);
//                params.put("state", eventstate);
//                params.put("city", eventcity);
//                params.put("country", eventcountry);
//                params.put("description", eventdescription);
//                params.put("zipcode", eventzipcode);
//                params.put("creator", username);
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/x-www-form-urlencoded");
//                return params;
//            }
//        };
//        queue.add(sr);
//    }
//
//    public static class ChoosePerformersDialogFragment extends DialogFragment {
//        private EventChoosePerformersAdapter adapter;
//        private Context context;
//        private ArrayList<User> users;
//        private ArrayList<String> performers = new ArrayList<>();
//        Button btn;
//        EditText editText;
//
//        ChoosePerformersDialogFragment(Context context, ArrayList<User> users) {
//            this.context = context;
//            this.users = users;
//        }
//
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            View view = inflater.inflate(R.layout.fragment_dialog, container, false);
//            RecyclerView recyclerView = view.findViewById(R.id.recyclerView_event_choose);
//            ;
//            recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
//            adapter = new EventChoosePerformersAdapter(context, users);
//            recyclerView.setAdapter(adapter);
//            btn = view.findViewById(R.id.btnGetSelected);
//            editText = view.findViewById(R.id.btnSearchSelectedPerformers);
//            editText.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//                    filter(editable.toString());
//                }
//            });
//
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (adapter.getSelected().size() > 0) {
//                        for (int i = 0; i < adapter.getSelected().size(); i++) {
//                            performers.add(adapter.getSelected().get(i).getUsername());
//                        }
//                    }
//                    dismiss();
//                }
//            });
//
//            return view;
//        }
//
//        private void filter(String text) {
//            ArrayList<User> filteredList = new ArrayList<>();
//            for (User user : users) {
//                if (user.getUsername().toLowerCase().contains(text.toLowerCase())) {
//                    filteredList.add(user);
//                }
//            }
//            adapter.filterList(filteredList);
//        }
//    }
//
//
//    public void testGetSongs(){
//        RequestQueue queue = Volley.newRequestQueue(getContext());
//        StringRequest getAllSongsReq = new StringRequest(Request.Method.GET, "http://10.24.227.244:8080/audioFiles",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        User artist = null;
//                        try {
//                            System.out.println(response);
//                            JSONArray allSongs = new JSONArray(response);
//                            for (int i = 0; i < allSongs.length(); i++) {
//                                JSONObject JSONSong = allSongs.getJSONObject(i);
//                                //System.out.println("\n\nPlaylist Object: " + JSONplaylist.toString());
//                                long id = JSONSong.getLong("id");
//                                String filename = JSONSong.getString("filename");
//                                String songName = JSONSong.getString("songName");
//                                int likes = JSONSong.getInt("likes");
//                                int plays = 0;
//                                //JSONObject JSONArtist = JSONSong.getJSONObject("artist");
//                                String userName = JSONSong.getString("artist");
//                                artist = getArtist(userName);
//                                while(artist == null){
//
//                                }
//                                AudioFile newSong = new AudioFile(id, filename, artist, songName, likes, plays, true);
//                                trackList.add(newSong);
//                            }
//                        } catch (JSONException e) {
//                            Log.e("ResponseJSONException", e.getMessage());
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("GET_ALL_SONGS_ERROR", error.getMessage());
//            }
//        })
//        {
//            @Override
//            protected Map<String,String> getParams() {
//                Map<String,String> params = new HashMap<>();
//                return params;
//            }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("Content-Type","application/x-www-form-urlencoded");
//                return params;
//            }
//        };
//        queue.add(getAllSongsReq);
//    }
//
//    public User getArtist(String username) {
//        final User[] user = {null};
//        RequestQueue queueUser = Volley.newRequestQueue(getContext());
//        String search_url = "http://10.24.227.244:8080/user/find/" + username;
//        StringRequest stringRequestUser = new StringRequest(Request.Method.GET, search_url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject JSONUser = new JSONObject(response);
//                            user[0] = new User();
//                            user[0].setUsername(JSONUser.getString("username"));
//                            user[0].setFirstName(JSONUser.getString("firstName"));
//                            user[0].setLastName(JSONUser.getString("lastName"));
//                            user[0].setArtistName(JSONUser.getString("artistName"));
////                            JSONArray JSONUploads = JSONUser.getJSONArray("")
//                            user[0].setId(JSONUser.getLong("id"));
//                        } catch (JSONException e) {
//                            Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        });
//
//        queueUser.add(stringRequestUser);
//        while(user[0] == null){
//
//        }
//        return user[0];
//    }
//}

