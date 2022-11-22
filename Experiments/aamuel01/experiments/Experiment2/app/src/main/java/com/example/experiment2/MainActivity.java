package com.example.experiment2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Send a notification when the button is pushed */
    public void notifyButton(View view){
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        NotificationChannel buttonPush = new NotificationChannel("notify_01", "button", NotificationManager.IMPORTANCE_HIGH);
        manager.createNotificationChannel(buttonPush);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, buttonPush.getId());

        builder.setSmallIcon(R.drawable.icon);
        builder.setContentTitle("Button Pushed");
        builder.setContentText("Notification successfully activated and shown");
        builder.setPriority(buttonPush.getImportance());

        manager.notify(1, builder.build());
    }
}