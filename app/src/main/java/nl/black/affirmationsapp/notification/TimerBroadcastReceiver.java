/*
 * Created by MJ Zwart on 8/26/20 6:58 PM.
 * Copyright (c) 2020. All rights reserved
 * Last modified 8/25/20 11:44 AM
 */

package nl.black.affirmationsapp.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class TimerBroadcastReceiver extends BroadcastReceiver {

    public static final int REQUEST_CODE = 12345;
    public static final String ACTION_TIMER = "nl.black.affirmationsapp.notification.ACTION_TIMER";

    @Override
    public void onReceive(Context context, Intent intent){
        Intent i = new Intent(context, NotificationService.class);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(i);
        } else {
            context.startService(i);
        }
    }
}
