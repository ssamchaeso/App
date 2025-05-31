package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MenuActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        // 버튼 연결
        Button startButton = findViewById(R.id.btn_gameStart);
        Button btnRecord = findViewById(R.id.btn_record);


        // 클릭 시 MainActivity로 이동
        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnRecord.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecordActivity.class);
            startActivity(intent);
        });

}
}
