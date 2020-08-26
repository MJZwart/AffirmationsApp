/*
 * Created by MJ Zwart on 8/26/20 6:58 PM.
 * Copyright (c) 2020. All rights reserved
 * Last modified 8/22/20 7:21 PM
 */

package nl.black.affirmationsapp.notification;

import android.app.IntentService;
import android.content.Intent;

import nl.black.affirmationsapp.AffirmationHandler;

public class NotificationService extends IntentService {

    private static final String serviceName = "Affirmation Notification Sender";

    public NotificationService(){
        super(serviceName);
    }

    @Override
    protected void onHandleIntent(Intent intent){
        AffirmationHandler affirmationHandler = new AffirmationHandler(getApplicationContext());
        String affirmation = affirmationHandler.getRandomAffirmation();
        NotificationHandler notificationHandler = new NotificationHandler();;
        notificationHandler.sendAffirmationNotification(affirmation, getApplicationContext());
    }
}