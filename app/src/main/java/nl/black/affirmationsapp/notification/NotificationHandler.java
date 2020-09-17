/*
 * Created by MJ Zwart on 9/17/20 2:46 PM.
 * Copyright (c) 2020. All rights reserved
 * Last modified 9/17/20 2:41 PM
 */

package nl.black.affirmationsapp.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import nl.black.affirmationsapp.R;

public class NotificationHandler {

    private final String CHANNEL_ID = "notifications";

    public void sendAffirmationNotification(String affirmation, Context context){
        System.out.println("Starting to send affirmation notification");
        Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .build();
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Notifications",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("All app notifications");
            channel.setSound(notificationSoundUri, audioAttributes);
            channel.setLightColor(Color.BLUE);
            channel.enableLights(true);
            channel.enableVibration(true);
            NotificationManager notificationManager = getNotificationManager(context);
            notificationManager.createNotificationChannel(channel);
        }

//        Intent goToAppIntent = new Intent();
//        goToAppIntent.setClassName(context, "MainActivity.class");
//        goToAppIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        //goToAppIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent contentIntent = PendingIntent.getActivity(
//                context,
//                0,
//                goToAppIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new
                NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.daily_affirmations_icon)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(affirmation)
                .setSound(notificationSoundUri)
                .setLights(Color.BLUE, 500, 500)
                .setVibrate(new long[]{250,500,250,500,250,500})
                .setDefaults(Notification.DEFAULT_LIGHTS |
                        Notification.DEFAULT_VIBRATE)
//                .setContentIntent(contentIntent)
                ;
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(0, notificationBuilder.build());
        System.out.println("End of sending affirmation notification");
    }

    private NotificationManager getNotificationManager(Context context) {
        NotificationManager notificationManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            notificationManager = context.getSystemService(NotificationManager.class);
        }
        return notificationManager;
    }}
