package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;


public class MenuActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);


        Button startButton = findViewById(R.id.btn_gameStart);
        Button btnRecord = findViewById(R.id.btn_record);
        Button btn_custom =findViewById(R.id.btn_custom);



        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnRecord.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecordActivity.class);
            startActivity(intent);
        });
        btn_custom.setOnClickListener(v -> {
            Intent intent = new Intent(this, CustomizeActivity.class);
            startActivity(intent);
        });

}
}
