package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "MatchHistoryDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MatchHistory (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "playerName TEXT," +
                "character TEXT," +
                "result TEXT," +
                "dateTime TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS MatchHistory");
        onCreate(db);
    }

    public void insertHistory(String playerName, String character, String result, String dateTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("playerName", playerName);
        values.put("character", character);
        values.put("result", result);
        values.put("dateTime", dateTime);
        db.insert("MatchHistory", null, values);
    }

    public Cursor getAllHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM MatchHistory", null);
    }
}
