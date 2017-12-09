package com.example.rahulk.simplycustomkeyboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    AppCompatButton btnSetUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnSetUp = (AppCompatButton) findViewById(R.id.btn_set_up);

        btnSetUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivitySetUp();

            }
        });


    }

    private void startActivitySetUp() {
        startActivity(new Intent(MainActivity.this, SetUpActivity.class));
        this.finish();

    }
}

