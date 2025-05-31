package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecordActivity extends AppCompatActivity {

    TextView txtHistory;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);  // XML 이름에 맞게 수정

        // XML 요소 연결
        txtHistory = findViewById(R.id.txt_history);
        btnBack = findViewById(R.id.btn_history_back);

        // DB에서 기록 가져오기
        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getAllHistory();

        StringBuilder resultText = new StringBuilder();
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);        // playerName
            String character = cursor.getString(2);   // character
            String result = cursor.getString(3);      // result
            String dateTime = cursor.getString(4);    // dateTime

            resultText.append("플레이어: ").append(name)
                    .append(" (").append(character).append(")\n")
                    .append("결과: ").append(result).append("\n")
                    .append("시간: ").append(dateTime).append("\n\n");
        }

        txtHistory.setText(resultText.toString());

        // 뒤로가기 버튼
        btnBack.setOnClickListener(v -> finish());
    }
}
