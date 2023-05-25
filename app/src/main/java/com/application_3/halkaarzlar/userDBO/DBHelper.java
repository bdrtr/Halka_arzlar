package com.application_3.halkaarzlar.userDBO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "login.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS USERS ("+
                "'user_id'	INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "'user_name'	String,"+
                "'user_password'	String);");

        db.execSQL("CREATE TABLE IF NOT EXISTS STOCKS ("+
                "'id'	INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "'stock_id'	String,"+
                "'user_id', INTEGER, "+
                "FOREIGN KEY(user_id) REFERENCES USERS (user_id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS 'USERS'");
        db.execSQL("DROP TABLE IF EXISTS 'STOCKS'");
        onCreate(db);
    }
}
