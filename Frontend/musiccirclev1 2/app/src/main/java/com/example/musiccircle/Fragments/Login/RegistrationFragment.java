package com.example.musiccircle.Fragments.Login;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musiccircle.Activities.Home_Page.NavMenuActivity;
import com.example.musiccircle.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistrationFragment extends Fragment {

    private EditText firstname, lastname, username, artistname, email, password, city;
    Bitmap bitmap;
    ImageView img;
    String encodeImageString;
    ContentResolver resolver;
    View view;
    String userState;
    private Spinner spinner1, spinner2;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registration, container, false);
        Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out);
        view.startAnimation(anim);
        View registbtn = view.findViewById(R.id.btn_register_2);
        View imgbtn = view.findViewById(R.id.choose_img);
        Button postButton = (Button) registbtn;
        img = view.findViewById(R.id.img);

        firstname = view.findViewById(R.id.firstname);
        lastname = view.findViewById(R.id.lastname);
        username = view.findViewById(R.id.username);
        artistname = view.findViewById(R.id.artistname);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        spinner1 = view.findViewById(R.id.state_spinner);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        city = view.findViewById(R.id.city);

        resolver = requireContext().getContentResolver();

        postButton.setOnClickListener(view -> Register());

//        final ContentValues values = new ContentValues(2);
//        values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
//        final Uri contentUriFile = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//
        imgbtn.setOnClickListener(view -> Dexter.withContext(getContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

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
//        final ContentValues values = new ContentValues(2);
//        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//        final Uri contentUriFile = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//
//        imgbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Dexter.withContext(getContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                        Intent intent = new Intent(Intent.ACTION_PICK);
//                        intent.setType("image/jpeg");
//                        intent.putExtra(android.content.Intent.EXTRA_STREAM, contentUriFile);
//                        startActivityForResult(Intent.createChooser(intent, "Browse Image"), 1);
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//                        permissionToken.continuePermissionRequest();
//                    }
//                }).check();
//            }
//        });
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.action_registrationFragment_to_welcomeLoginRegistrationFragment,null, getNavOptions());
            }
        });

        return view;
    }

    protected NavOptions getNavOptions() {

        return new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in)
                .build();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==-1){
            assert data != null;
            Uri filepath = data.getData();
            try {
                assert filepath != null;
                InputStream inputStream = resolver.openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            }catch (Exception ex){

            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        encodeImageString=android.util.Base64.encodeToString(bytes, Base64.DEFAULT);
        System.out.println(Arrays.toString(java.util.Base64.getDecoder().decode(encodeImageString)));
    }

    private void Register() {

        final String firstname = this.firstname.getText().toString().trim();
        final String lastname = this.lastname.getText().toString().trim();
        final String username = this.username.getText().toString().trim();
        final String artistname = this.artistname.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String city = this.city.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        StringRequest sr = new StringRequest(Request.Method.POST, "http://10.24.227.244:8080/user/registration",
                response -> {
                    if (response.equals("SUCCESSFUL USER REGISTRATION")) {
                        Navigation.findNavController(view).navigate(R.id.action_registrationFragment_to_welcomeLoginRegistrationFragment);
                    }
                    Log.e("HttpClient", "success! response: " + response);
                },
                error -> {

                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("firstName", firstname);
                params.put("lastName", lastname);
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                params.put("file",encodeImageString);
                params.put("state", userState);
                params.put("city", city);
                params.put("artistName", artistname);
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

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            Toast.makeText(parent.getContext(),
                    "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT).show();
            userState = parent.getItemAtPosition(pos).toString();
            System.out.println(userState);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

}

