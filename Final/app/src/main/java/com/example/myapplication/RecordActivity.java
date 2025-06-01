package com.example.myapplication;





import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;



import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity {
    private ListView listView;
    private MatchAdapter adapter;
    private ArrayList<Match> matchList = new ArrayList<Match>();
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        listView = findViewById(R.id.matchListView);
        dbHelper = new DBHelper(this);
        matchList = new ArrayList<Match>();

        loadData();
        adapter = new MatchAdapter(this, matchList);
        listView.setAdapter(adapter);
    }

    private void loadData() {
        ArrayList<Match> matchList = dbHelper.getAllMatches();
        MatchAdapter adapter = new MatchAdapter(this, matchList);
        listView.setAdapter(adapter);


    }
}
