package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity {

    private ListView listView;
    private MatchAdapter adapter;
    private ArrayList<Match> matchList;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        listView = findViewById(R.id.matchListView);
        dbHelper = new DBHelper(this);

        loadData(); // 데이터 로딩

        Button backButton = findViewById(R.id.btn_history_back); // 뒤로가기 버튼 처리
        backButton.setOnClickListener(v -> finish());
    }

    private void loadData() {
        matchList = dbHelper.getAllMatches(); // 전역 변수에 할당
        adapter = new MatchAdapter(this, matchList); // 전역 변수에 할당
        listView.setAdapter(adapter);
    }
}
