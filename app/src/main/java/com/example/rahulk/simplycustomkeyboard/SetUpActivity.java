package com.example.rahulk.simplycustomkeyboard;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class SetUpActivity extends AppCompatActivity {

    AppCompatButton btn_enable_keyboard, btn_set_default;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        btn_enable_keyboard = (AppCompatButton) findViewById(R.id.btn_enable_keyboard);

        btn_enable_keyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$InputMethodAndLanguageSettingsActivity"));
                startActivity(intent);
            }
        });

        btn_set_default = (AppCompatButton) findViewById(R.id.btn_set_default);
        btn_set_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager mgr =
                        (InputMethodManager) getSystemService(SetUpActivity.INPUT_METHOD_SERVICE);
                if (mgr != null) {
                    mgr.showInputMethodPicker();
                }

            }
        });
    }
}
