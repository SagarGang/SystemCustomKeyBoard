package com.example.rahulk.simplycustomkeyboard;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        intent.setComponent( new ComponentName("com.android.settings","com.android.settings.Settings$InputMethodAndLanguageSettingsActivity" ));
        startActivity(intent);
    }
       }

