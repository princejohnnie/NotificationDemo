package com.yungjohn.notificationdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_NOTIFICATION = 0;
    private NotificationManagerCompat mNotificationManager;
    private EditText mEditTextTitle;
    private EditText mEditTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextTitle = findViewById(R.id.et_title);
        mEditTextMessage = findViewById(R.id.et_message);

        mNotificationManager = NotificationManagerCompat.from(this);
    }

    public void sendOnChannel1(View view) {
        String title = mEditTextTitle.getText().toString();
        String message = mEditTextMessage.getText().toString();

        if (!title.trim().isEmpty()) {

            Intent activityIntent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, REQUEST_CODE_NOTIFICATION, activityIntent, 0);

            Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
            broadcastIntent.putExtra("toastMessage", message);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, REQUEST_CODE_NOTIFICATION, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Bitmap bigPicture = BitmapFactory.decodeResource(getResources(), R.drawable.ic_puppy);

            Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_one)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .setBigContentTitle("Big Content title")
                            .setSummaryText("Summary Text")
                            .bigText(getString(R.string.long_text)))
                    .setLargeIcon(bigPicture)
                    .setContentIntent(contentIntent)
                    .setColor(Color.BLUE)
                    .setAutoCancel(true)
                    .addAction(R.mipmap.ic_launcher, "Toast", pendingIntent)
                    .build();

            mNotificationManager.notify(1, notification);
        } else {
            Toast.makeText(this, "Please enter a Title", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendOnChannel2(View view) {
        String title = mEditTextTitle.getText().toString();
        String message = mEditTextMessage.getText().toString();

        Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.ic_puppy);

        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_two)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(picture)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(picture)
                        .bigLargeIcon(null))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        mNotificationManager.notify(2, notification);
    }
}