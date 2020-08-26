/*
 * Created by MJ Zwart on 8/26/20 6:58 PM.
 * Copyright (c) 2020. All rights reserved
 * Last modified 5/24/20 3:46 PM
 */

package nl.black.affirmationsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ListAdapter affirmationsAdapter = new ArrayAdapter<String>

        setContentView(R.layout.activity_list);
        //TODO Show the full list of Affirmations as ListView
    }

    private void pickAffirmations(){
        //TODO Get a full list of Affirmations based on the preferences stored
    }
}
