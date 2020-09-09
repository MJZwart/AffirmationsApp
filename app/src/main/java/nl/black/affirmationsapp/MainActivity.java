/*
 * Created by MJ Zwart on 8/26/20 6:58 PM.
 * Copyright (c) 2020. All rights reserved
 * Last modified 8/26/20 5:50 PM
 */

package nl.black.affirmationsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import nl.black.affirmationsapp.notification.NotificationHandler;
import nl.black.affirmationsapp.notification.TimerBroadcastReceiver;

public class MainActivity extends AppCompatActivity {

    //TODO !!! Check if Notifications on is toggled on

    //TODO add an explanation
    //TODO add another button to the settings in the main menu
    //TODO maybe overhaul the entire menu into a side menu or main page
    //TODO add the option to add extra notification times
    //TODO add those extra times to the service to be sent automatically
    //TODO add the option to see all the affirmations (based on chosen list)
    //TODO add the option to preview all affirmations in one type
    //TODO overhaul the chosen affirmation list (database?) to allow for better randoms and choose favourites
    //TODO add option to favourite certain affirmations to make them more common
    //TODO add option to pick which affirmations you want and which not (database?)
    // In this case I would add all affirmations into a database and have them checked 'active' or 'inactive'
    // Initially all affirmations per type will be toggled, but will not override personal choices
    // Then the user can view all affirmations in a list and toggle them
    // The user will also be able to see disabled affirmations (provided the type is toggled on)

    private final int MENU_SETTINGS = 1;
    private final String NOTIFICATION_TIME = "notification_time";
    private final String NOTIFS_ON = "notifs_on";

    private SharedPreferences sharedPreferences;
    private NotificationHandler notificationHandler;
    private AffirmationHandler affirmationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpPreferences();
        //setUpMisc();
        setUpHandlers();
        updateScheduledTime();
        scheduleAffirmationService(getApplicationContext());
    }

    @Override
    protected void onStart(){
        super.onStart();
        updateScheduledTime();
    }

    private void setUpHandlers() {
        notificationHandler = new NotificationHandler();
        affirmationHandler = new AffirmationHandler(this);
    }

    private void setUpPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
    }

//    private void setUpMisc(){
//        TextView textViewUsername = (TextView) findViewById(R.id.usernameShow);
//        String username = sharedPreferences.getString("username", null);
//        if (username == null) {
//            textViewUsername.setText("Hello");
//        } else {
//            textViewUsername.setText("Hello " + username);
//        }
//    }

    //Homepage button to go to settings
    public void goToSettings(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    //Button to send random affirmation
    public void sendRandomAffirmation(View view) {
        String affirmation = affirmationHandler.getRandomAffirmation();
        notificationHandler.sendAffirmationNotification(affirmation, this);
    }

    public void scheduleAffirmationService(Context context){
        Intent intent = new Intent(context, TimerBroadcastReceiver.class);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(context, TimerBroadcastReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = extractPreferredTime(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
        //updateScheduledTime(firstMillis);
    }

    public void updateScheduledTime(){
        TextView scheduledTimeText = (TextView) findViewById(R.id.scheduledTime);
        if(sharedPreferences.getBoolean(NOTIFS_ON, true)) {
            Date time = new Date(extractPreferredTime(this));
            String timeText = getString(R.string.next_notification_at) + time;
            scheduledTimeText.setText(timeText);
        } else if (!sharedPreferences.getBoolean(NOTIFS_ON, true)){
            String timeTextTurnedOff = getString(R.string.notifications_turned_off_go_to_settings);
            scheduledTimeText.setText(timeTextTurnedOff);
        }
    }

    public long extractPreferredTime(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String timeString = sharedPreferences.getString(NOTIFICATION_TIME, "10:00");
        String hh = timeString.substring(0,2);
        String mm = timeString.substring(3,5);
        Date date = new Date();
        Calendar pickedTime = Calendar.getInstance();
        Calendar timeNow = Calendar.getInstance();
        timeNow.setTime(date);
        pickedTime.setTime(date);
        pickedTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hh).intValue());
        pickedTime.set(Calendar.MINUTE, Integer.valueOf(mm).intValue());
        pickedTime.set(Calendar.SECOND, 0);
        //For some reason .getTime() needs to be called in order for the Calendar date to work.
        pickedTime.getTime();
        if(pickedTime.before(timeNow)){
            pickedTime.add(Calendar.DATE, 1);
        }
        return pickedTime.getTimeInMillis();
    }

    public void cancelAffirmationService(Context context){
        Intent intent = new Intent(context, TimerBroadcastReceiver.class);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(context, TimerBroadcastReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        //updateScheduledTime();
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(0, MENU_SETTINGS, 0, R.string.menu_settings);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //Code to update menu on runtime
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_SETTINGS:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
            default:
                return super.onContextItemSelected(item);
        }
    }
    */
}