package com.application_3.halkaarzlar.userDBO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.application_3.halkaarzlar.objects.Stock;
import com.application_3.halkaarzlar.objects.User;

import java.util.ArrayList;

public class UserDBO {

    public Boolean LOGIN(DBHelper vt, User user) {

        ArrayList<Stock> stocks = new ArrayList<>();

        SQLiteDatabase dbx = vt.getReadableDatabase();
        Cursor c = dbx.rawQuery("SELECT * FROM USERS ",null);

        while (c.moveToNext()) {
            User user1 = new User(
                    c.getInt(c.getColumnIndexOrThrow("user_id")),
                    c.getString(c.getColumnIndexOrThrow("user_name")),
                    c.getString(c.getColumnIndexOrThrow("user_password")));

            if (user.getName().equals(user1.getName()) && user.getPasswd().equals(user1.getPasswd())) {
                dbx.close();
                return true;
            }
        }
        dbx.close();
        return false;
    }

    public void DelALL(com.application_3.halkaarzlar.stockDatabase.DBHelper vt) {
        SQLiteDatabase dbx = vt.getWritableDatabase();
        dbx.rawQuery("DELETE FROM Stocks",null);
        dbx.close();
    }

    public void SIGNUP(DBHelper vt, User user) {

        SQLiteDatabase dbx = vt.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("user_name",user.getName());
        values.put("user_password",user.getPasswd());

        dbx.insertOrThrow("USERS",null,values);
        dbx.close();

    }


    public void Update(DBHelper vt, User user) {
        SQLiteDatabase dbx = vt.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("user_name",user.getName());
        values.put("user_password",user.getPasswd());

        dbx.update("USERS",values,"user_id=?",new String[]{String.valueOf(user.getId())});
        dbx.close();
    }

}
