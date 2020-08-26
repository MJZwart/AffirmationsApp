/*
 * Created by MJ Zwart on 8/26/20 6:58 PM.
 * Copyright (c) 2020. All rights reserved
 * Last modified 8/21/20 7:27 PM
 */

package nl.black.affirmationsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AffirmationHandler {

    private SharedPreferences sharedPreferences;
    private Resources resources;

    private final static String AFF_TYPE_LOOKS = "aff_type_looks";
    private final static String AFF_TYPE_SOCIAL = "aff_type_social";
    private final static String AFF_TYPE_MENTAL_HEALTH = "aff_type_mental_health";
    private final static String AFF_TYPE_PRODUCTIVITY = "aff_type_productivity";
    private final static String AFF_TYPE_SELF_WORTH = "aff_type_self_worth";

    public AffirmationHandler(){
    }

    public AffirmationHandler(Context context){
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.resources = context.getResources();
    }

    //This generates a random affirmation after fetching a complete list.
    //Due to the nature of this Random generator, it is possible to get the same affirmation twice in a row.
    public String getRandomAffirmation() {
        System.out.println("getting Random affirmation");
        List<String> affirmationList = getAffirmationList();
        System.out.println(affirmationList.size());
        Random r = new Random();
        String affirmation = affirmationList.get(r.nextInt(affirmationList.size()));
        System.out.println(affirmation);
        return affirmation;
    }

    private List<String> getAffirmationList(){
        List<String> totalAffirmationList = new ArrayList<>(
                Arrays.asList(resources.getStringArray(R.array.global_affirmations)));
        if(sharedPreferences.getBoolean(AFF_TYPE_LOOKS, false)){
            totalAffirmationList.addAll(new ArrayList<>(
                    Arrays.asList(resources.getStringArray(R.array.looks_affirmations))));
        }
        if(sharedPreferences.getBoolean(AFF_TYPE_SOCIAL, false)){
            totalAffirmationList.addAll(new ArrayList<>(
                    Arrays.asList(resources.getStringArray(R.array.social_affirmations))));
        }
        if(sharedPreferences.getBoolean(AFF_TYPE_SELF_WORTH, false)){
            totalAffirmationList.addAll(new ArrayList<>(
                    Arrays.asList(resources.getStringArray(R.array.self_worth_affirmations))));
        }
        if(sharedPreferences.getBoolean(AFF_TYPE_PRODUCTIVITY, false)){
            totalAffirmationList.addAll(new ArrayList<>(
                    Arrays.asList(resources.getStringArray(R.array.productivity_affirmations))));
        }
        if(sharedPreferences.getBoolean(AFF_TYPE_MENTAL_HEALTH, false)){
            totalAffirmationList.addAll(new ArrayList<>(
                    Arrays.asList(resources.getStringArray(R.array.mental_health_affirmations))));
        }
        return totalAffirmationList;
    }
}
