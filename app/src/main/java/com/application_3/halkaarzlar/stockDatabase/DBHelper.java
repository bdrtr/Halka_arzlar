package com.application_3.halkaarzlar.stockDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "StockBase2.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Stocks ("+
                "'stock_id'	String,"+
                "'name'	String,"+
                "'pop'	String,"+
                "'target_price'	String,"+
                "'date'	String,"+
                "'plan'	String,"+
                "'share_rate'	String,"+
                "'name_code'	String,"+
                "'bazaar'	String);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS 'Stocks'");
        onCreate(db);
    }
}
