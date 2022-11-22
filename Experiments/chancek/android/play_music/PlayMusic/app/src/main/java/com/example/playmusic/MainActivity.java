package com.example.playmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button playBtn = findViewById(R.id.PlayBtn);
        Button pauseBtn = findViewById(R.id.PauseBtn);
        Button stopBtn = findViewById(R.id.StopBtn);
        View cursor = findViewById(R.id.CursorBar);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playAudio();
                try {
                    moveCursor(cursor);
                } catch (URISyntaxException | ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }

            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseAudio();
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAudio();
            }
        });
    }


    public void moveCursor(View view) throws URISyntaxException, ClassNotFoundException, IOException {
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationX", 500f);
        int songLength = 0;
        Class cls = Class.forName("com.example.playmusic.MainActivity");
        ClassLoader classLoader = cls.getClassLoader();
        System.out.println(classLoader.getClass());
        //String pathStr = classLoader.getResource("raw/pressure_in_my_palms.mp3").getPath();
        int mp3Id = getResources().getIdentifier("raw/pressure_in_my_palms", "raw", getPackageName());
        System.out.println("Audiofile raw id: " + mp3Id);

        InputStream ins = getApplicationContext().getResources().openRawResource (mp3Id);
        int videoSize = ins.available();
        System.out.println("Available: " + videoSize);



        //URI mp3 = new URI("raw/pressure_in_my_palms.mp3");
        //Log.d("URI_FILE", mp3.getPath());
        animation.setDuration(2000);
        animation.start();
    }
    private static String getDuration(File file) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
        String durationStr = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        return durationStr;
    }

    public void playAudio(){
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.pressure_in_my_palms);
        }
        mediaPlayer.start();
    }

    public void pauseAudio(){
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.pressure_in_my_palms);
        }
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    public void stopAudio(){
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.pressure_in_my_palms);
        }
        pauseAudio();
        mediaPlayer.seekTo(0);
    }
}