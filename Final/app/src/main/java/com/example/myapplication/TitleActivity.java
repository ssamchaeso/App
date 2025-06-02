package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;



public class TitleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_activity);

        // 전체 화면 뷰 가져오기
        View rootView = findViewById(android.R.id.content);

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // 터치되면 게임 화면으로 전환
                    Intent intent = new Intent(TitleActivity.this,MenuActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
    }
}
