package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "MatchHistoryDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE match_records (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "player1 TEXT, " +
                "player1Result TEXT, " +
                "player2 TEXT, " +
                "player2Result TEXT);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS match_records");
        onCreate(db);
    }
    //경기 결과 저장
    public void insertMatch(String p1, String p1Result, String p2, String p2Result) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("player1", p1);
        values.put("player1Result", p1Result);
        values.put("player2", p2);
        values.put("player2Result", p2Result);
        db.insert("match_records", null, values);

    }
    //경기 기록 보여주기
    public ArrayList<Match> getAllMatches() {
        ArrayList<Match> matchList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM match_records  order by player1 ASC,player1Result ASC,_id DESC ", null);

        if (cursor.moveToFirst()) {
            do {
                String p1 = cursor.getString(cursor.getColumnIndexOrThrow("player1"));
                String p1Result = cursor.getString(cursor.getColumnIndexOrThrow("player1Result"));
                String p2 = cursor.getString(cursor.getColumnIndexOrThrow("player2"));
                String p2Result = cursor.getString(cursor.getColumnIndexOrThrow("player2Result"));

                matchList.add(new Match(p1, p1Result, p2, p2Result));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return matchList;
    }
    //경기기록삭제
    public void reset() {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("match_records",null,null);

    }
}
