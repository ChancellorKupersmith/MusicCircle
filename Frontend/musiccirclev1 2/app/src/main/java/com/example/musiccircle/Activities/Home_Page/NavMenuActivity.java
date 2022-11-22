package com.example.musiccircle.Activities.Home_Page;

import android.Manifest;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Activities.Profile_Page.ProfilePageActivity;
import com.example.musiccircle.ChoosePerformersDialogFragment;
import com.example.musiccircle.Entity.AudioFile;
import com.example.musiccircle.Entity.User;
import com.example.musiccircle.Fragments.Music_Player.UserPlaylistsFragment;
import com.example.musiccircle.Fragments.Profile.MyUserPlaylists_fragment;
import com.example.musiccircle.Fragments.Home.Home_fragment;
import com.example.musiccircle.OnSubmitClickListener;
import com.example.musiccircle.R;
//import com.example.musiccircle.Fragments.Profile.MyUserProfile_fragment;
//import com.example.musiccircle.User.UserPlaylistsFragment;
import com.example.musiccircle.Services.NotificationService;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.*;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NavMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String USER_KEY= "user";
    public static final String USER_USERNAME_KEY = "user_username";
    private static final String TRACKLIST_KEY = "track_list";
    private static final String USER_PAGE_KEY = "user_page_username";
    DrawerLayout drawerLayout;
    String value;
    View EventSheetView, GroupSheetView, AudioFileSheetView;
    Button eventbtn, groupbtn, musicplayerbtn, audiofilebtn, choose_event_performers;
    EditText eventname, eventdescription, eventaddress, eventcity, eventzipcode, eventcountry, audioFilename, audioFileartist, groupname, groupdescription, groupcreator;
    BottomSheetDialog bottomSheetDialog;

    private Spinner spinner1, spinner2;

    String username;
    ContentResolver resolver;
    ArrayList<User> users;
    User loggedInUser;
    NotificationService notificationService;

    String encodedAudioFileString;
    Uri picUri;

    private PlayerView playerView;
    private SimpleExoPlayer player;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    byte[] bytes;
    byte[] decodedImageBytes;

    String eventstate;
    ArrayList<User> usersList;

    Bitmap bitmap;
    ImageView event_img;
    String event_image_encodeImageString;

    ImageView group_img;
    String group_image_encodeImageString;

    String event_performers_list;
    ArrayList<AudioFile> tracklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_View);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            value = b.getString("name");
            loggedInUser = (User) b.getSerializable(USER_KEY);
            username = loggedInUser.getUsername();
            tracklist = (ArrayList<AudioFile>) b.getSerializable(TRACKLIST_KEY);

        }
        System.out.println(value);
        usersList = new ArrayList<>();
        getUsers();

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        Bundle b1 = new Bundle();

        b1.putString("message", value);
        b1.putSerializable(USER_KEY, loggedInUser);
        b1.putSerializable(TRACKLIST_KEY, tracklist);
        Home_fragment a = new Home_fragment();
        a.setArguments(b1);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, a).commit();

        navigationView.setCheckedItem(R.id.nav_home);

        resolver = getContentResolver();

        bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);

        //GroupSheetView = LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet_group, (LinearLayout) findViewById(R.id.bottomSheetContainer2), false);
        //EventSheetView = LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet_event, (LinearLayout) findViewById(R.id.bottomSheetContainer1), false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_user_profile) {
//            Intent intent = new Intent(this, ProfilePageActivity.class);
//            Bundle b = new Bundle();
//            b.putString(USER_USERNAME_KEY, loggedInUser.getUsername());
//            b.putSerializable(USER_KEY, loggedInUser);
//            b.putString(USER_PAGE_KEY, loggedInUser.getUsername());
//            intent.putExtras(b);
//            startActivity(intent);
//
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_user_profile) {
            Intent intent = new Intent(this, ProfilePageActivity.class);
            Bundle b = new Bundle();
            b.putString(USER_USERNAME_KEY, loggedInUser.getUsername());
            b.putSerializable(USER_KEY, loggedInUser);
            b.putString(USER_PAGE_KEY, loggedInUser.getUsername());
            intent.putExtras(b);
            startActivity(intent);
        }

        if (id == R.id.action_user_create) {
            showPopup(findViewById(R.id.action_user_create));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent notificationServiceIntent = new Intent(this, NotificationService.class);
        Bundle serviceBundle = new Bundle();
        serviceBundle.putSerializable(USER_KEY, loggedInUser);
        serviceBundle.putString(USER_USERNAME_KEY, username);
        System.out.println("navmenu user: " + loggedInUser);
        System.out.println("navmenu username: " + username);
        notificationServiceIntent.putExtras(serviceBundle);
        this.bindService(notificationServiceIntent, notificationConnection, Context.BIND_AUTO_CREATE);
    }

//
//    @Override
//    public void onStop(){
//        super.onStop();
//        this.unbindService(notificationConnection);
//    }
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

    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.create_group:
                    GroupSheetView = LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet_group, findViewById(R.id.bottomSheetContainer2), false);
                    groupname = GroupSheetView.findViewById(R.id.new_group_name);
                    groupdescription = GroupSheetView.findViewById(R.id.new_group_description);
                    groupcreator = GroupSheetView.findViewById(R.id.new_group_admin);
                    resolver = getContentResolver();
                    final ContentValues values = new ContentValues(2);
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
                    final Uri contentUriFile = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    group_img = GroupSheetView.findViewById(R.id.new_group_img);
                    GroupSheetView.findViewById(R.id.choose_new_group_img).setOnClickListener(view -> {Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            getIntent.setType("image/*");

                            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickIntent.setType("image/*");

                            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
                            startActivityForResult(chooserIntent, 4);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    }).check();

                    });
                    GroupSheetView.findViewById(R.id.btn_group_1).setOnClickListener(view14 -> {
                        RegisterGroup(bottomSheetDialog);
                        bottomSheetDialog.dismiss();
                    });
                    bottomSheetDialog.setContentView(GroupSheetView);
                    setupFullHeight(bottomSheetDialog);
                    bottomSheetDialog.show();
                    break;

                case R.id.create_event:
                    EventSheetView = LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet_event, findViewById(R.id.bottomSheetContainer1), false);
                    eventname = EventSheetView.findViewById(R.id.event_name);
                    eventdescription = EventSheetView.findViewById(R.id.event_description);
                    eventaddress = EventSheetView.findViewById(R.id.event_street_address);
                    eventcity = EventSheetView.findViewById(R.id.event_city);
                    spinner1 = EventSheetView.findViewById(R.id.spinner1);
                    spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
                    choose_event_performers = EventSheetView.findViewById(R.id.btn_event_musicians_choose);
                    resolver = getContentResolver();
                    event_img = EventSheetView.findViewById(R.id.event_img);
                    EventSheetView.findViewById(R.id.choose_event_img).setOnClickListener(view -> {Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            getIntent.setType("image/*");

                            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickIntent.setType("image/*");

                            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
                            startActivityForResult(chooserIntent, 3);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    }).check();

                    });
                    choose_event_performers.setOnClickListener(view -> ChoosePerformersDialogFragment.newInstance(getApplicationContext(), usersList, new OnSubmitClickListener() {

                        @Override
                        public void onSubmitClicked(ArrayList<String> s) {
                            event_performers_list = Arrays.toString(s.toArray()).replace("[", "").replace("]", "");
                            System.out.println(event_performers_list);
                        }
                    }).show(getSupportFragmentManager(), "dialog"));
                    eventzipcode = EventSheetView.findViewById(R.id.event_zipcode);
                    eventcountry = EventSheetView.findViewById(R.id.event_country);
                    EventSheetView.findViewById(R.id.btn_event_1).setOnClickListener(view15 -> {
                        RegisterEvent(bottomSheetDialog);
                    });
                    bottomSheetDialog.setContentView(EventSheetView);
                    setupFullHeight(bottomSheetDialog);
                    bottomSheetDialog.show();
                    break;

                case R.id.upload_song:
                    AudioFileSheetView = LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet_audiofile, findViewById(R.id.bottomSheetContainer3), false);
                    audioFilename = AudioFileSheetView.findViewById(R.id.audio_file_name);
                    audioFileartist = AudioFileSheetView.findViewById(R.id.audio_file_artist);
                    playerView = AudioFileSheetView.findViewById(R.id.audio_file_preview);
                    AudioFileSheetView.findViewById(R.id.choose_audio_file).setOnClickListener(view131 -> Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            getIntent.setType("audio/*");

                            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickIntent.setType("audio/*");

                            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
                            startActivityForResult(chooserIntent, 1);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    }).check());
                    AudioFileSheetView.findViewById(R.id.btn_audiofile_1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view13) {
                            //imageUpload();
                        }
                    });
                    bottomSheetDialog.setContentView(AudioFileSheetView);
                    setupFullHeight(bottomSheetDialog);
                    bottomSheetDialog.show();
            }
            return true;
        });
        popup.show();
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Bundle b1 = new Bundle();
                b1.putSerializable(USER_KEY, loggedInUser);
                b1.putSerializable(TRACKLIST_KEY, tracklist);
                b1.putString("message", value);
                Home_fragment a = new Home_fragment();
                a.setArguments(b1);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, a).commit();
                break;

            case R.id.nav_songs:

            case R.id.nav_groups:
                Bundle b2 = new Bundle();
                MyUserPlaylists_fragment a1 = new MyUserPlaylists_fragment();
                b2.putString("message", value);
                a1.setArguments(b2);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, a1).commit();
                break;

            case R.id.nav_playlists:
                Bundle b3 = new Bundle();
                b3.putString("user_username", username);
                b3.putInt("column-count", 2);
                UserPlaylistsFragment a2 = new UserPlaylistsFragment();
                a2.setArguments(b3);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, a2).commit();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1) {
            assert data != null;
            Uri filepath = data.getData();
            Uri uri = data.getData();
            picUri = uri;
            initializePlayer(uri);
            try {
                assert filepath != null;
                InputStream inputStream = resolver.openInputStream(filepath);
                assert inputStream != null;
                bytes = IOUtils.toByteArray(inputStream);
                encodedAudioFileString = Base64.encodeToString(bytes, Base64.DEFAULT);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == 3 && resultCode == -1){
            assert data != null;
            Uri filepath = data.getData();
            try {
                assert filepath != null;
                InputStream inputStream = resolver.openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                event_img.setImageBitmap(bitmap);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                event_image_encodeImageString=android.util.Base64.encodeToString(bytes, Base64.DEFAULT);
            }catch (Exception ex){

            }
        }
        if(requestCode == 4 && resultCode == -1){
            assert data != null;
            Uri filepath = data.getData();
            try {
                assert filepath != null;
                InputStream inputStream = resolver.openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                group_img.setImageBitmap(bitmap);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                group_image_encodeImageString=android.util.Base64.encodeToString(bytes, Base64.DEFAULT);
            }catch (Exception ex){

            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        event_image_encodeImageString=android.util.Base64.encodeToString(bytes, Base64.DEFAULT);
        System.out.println(Arrays.toString(java.util.Base64.getDecoder().decode(event_image_encodeImageString)));
    }

    public void initializePlayer(Uri uri) {
        if (player == null) {
            playerView.setVisibility(View.VISIBLE);
            player = new SimpleExoPlayer.Builder(this).setTrackSelector(new DefaultTrackSelector()).build();
            playerView.setPlayer(player);
            player.setPlayWhenReady(true);
            player.seekTo(currentWindow, playbackPosition);
            MediaSource mediaSource = buildMediaSource(uri);
            player.prepare(mediaSource, true, false);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory datasourceFactroy = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "Your App Name"));
        return new ExtractorMediaSource.Factory(datasourceFactroy).createMediaSource(uri);
    }

    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        assert bottomSheet != null;
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = windowHeight;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private int getWindowHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private void RegisterGroup(final BottomSheetDialog bottomSheetDialog) {
        final String groupname = this.groupname.getText().toString().trim();
        final String groupdescription = this.groupdescription.getText().toString().trim();
        final String groupcreator = this.groupcreator.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest sr = new StringRequest(Request.Method.POST, "http://10.24.227.244:8080/group/registration",
                response -> {
                    if (response.equals("SUCCESSFUL GROUP REGISTRATION")) {
                        bottomSheetDialog.dismiss();
                    }
                    Log.e("HttpClient", "success! response: " + response);
                },
                error -> Log.e("HttpClient", "error: " + error.toString())) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("groupName", groupname);
                params.put("description", groupdescription);
                params.put("username", groupcreator);
                params.put("file",group_image_encodeImageString);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    private void RegisterEvent(final BottomSheetDialog bottomSheetDialog) {

        final String eventname = this.eventname.getText().toString().trim();
        final String eventdescription = this.eventdescription.getText().toString().trim();
        final String eventaddress = this.eventaddress.getText().toString().trim();
        final String eventcity = String.valueOf(this.spinner1.getSelectedItem());
        final String eventzipcode = this.eventzipcode.getText().toString().trim();
        final String eventcountry = this.eventcountry.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.24.227.244:8080/event/registration";
        StringRequest sr = new StringRequest(Request.Method.POST, url,
                response -> {
                    if (response.equals("SUCCESSFUL EVENT REGISTRATION")) {
                        bottomSheetDialog.dismiss();
                    }
                    Log.e("HttpClient", "success! response: " + response);
                },
                error -> Log.e("HttpClient", "error: " + error.toString())) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", eventname);
                params.put("streetAddress", eventaddress);
                params.put("state", eventstate);
                params.put("city", eventcity);
                params.put("country", eventcountry);
                params.put("description", eventdescription);
                params.put("zipcode", eventzipcode);
                params.put("creator", "iaroy98");
                params.put("performers", event_performers_list);
                params.put("eventPic",event_image_encodeImageString);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.getCache().clear();
        queue.add(sr);
    }

    public void getUsers() {
        usersList.clear();
        String search_url = "http://10.24.227.244:8080/user/topArtists";
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.getCache().clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, search_url,
                response -> {
                    try {
                        JSONArray json_response = new JSONArray(response);
                        for (int i = 0; i < json_response.length(); i++) {
                            User user = new User();
                            user.setFirstName(json_response.getJSONObject(i).getString("firstName"));
                            user.setLastName(json_response.getJSONObject(i).getString("lastName"));
                            user.setUsername(json_response.getJSONObject(i).getString("username"));
                            usersList.add(user);
                        }
                    } catch (JSONException e) {
                        Log.e("ResponseJSONException", Objects.requireNonNull(e.getMessage()));
                        e.printStackTrace();
                    }
                }, error -> {
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }

    public String getMyData() {
        return value;
    }

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            Toast.makeText(parent.getContext(),
                    "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT).show();
            eventstate = parent.getItemAtPosition(pos).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }
}