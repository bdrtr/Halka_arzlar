package com.application_3.halkaarzlar.stockDatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.application_3.halkaarzlar.objects.Stock;

import java.util.ArrayList;

public class StockDBO {

    public ArrayList<Stock> getAllStocks(DBHelper vt) {

        ArrayList<Stock> stocks = new ArrayList<>();

        SQLiteDatabase dbx = vt.getReadableDatabase();
        Cursor c = dbx.rawQuery("SELECT * FROM Stocks ",null);

        while (c.moveToNext()) {
            Stock mStock = new Stock(c.getString(c.getColumnIndexOrThrow("name")),
                    c.getString(c.getColumnIndexOrThrow("pop")),
                    c.getString(c.getColumnIndexOrThrow("target_price")),
                    c.getString(c.getColumnIndexOrThrow("stock_id")),
                    c.getString(c.getColumnIndexOrThrow("date")),
                    c.getString(c.getColumnIndexOrThrow("plan")),
                    c.getString(c.getColumnIndexOrThrow("share_rate")),
                    c.getString(c.getColumnIndexOrThrow("name_code")),
                    c.getString(c.getColumnIndexOrThrow("bazaar")));

            mStock.setImgPic(c.getString(c.getColumnIndexOrThrow("picture_url")));
            stocks.add(mStock);
        }
        dbx.close();
        return stocks;
    }

    public void DelALL(DBHelper vt) {
        SQLiteDatabase dbx = vt.getWritableDatabase();
        dbx.delete("Stocks", null,null);
        dbx.close();
    }

    public void AddList(DBHelper vt, ArrayList<Stock> stock) {

        boolean addFlag = true;

        //data basede var olan değerler güncellensin yoksa yeni değerler oluşturulsun

        ArrayList<Stock> alreadyThere = getAllStocks(vt);
        for (int i=0;i<stock.size();i++) {
            addFlag = true; //bu değer database eklencek

            for (int j=0;j<alreadyThere.size();j++) {

                if (stock.get(i).getId().equals(alreadyThere.get(j).getId())) {
                    Update(vt,stock.get(i),alreadyThere.get(j).getId());
                    addFlag = false; // zaten varmış eklenmiycek
                }
            }
            if (addFlag)
                Add(vt,stock.get(i));
        }
    }

    public void Add(DBHelper vt, Stock stock) {

        SQLiteDatabase dbx = vt.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name",stock.getName());
        values.put("pop",stock.getPop());
        values.put("target_price",stock.getCurrent_price());
        values.put("stock_id",stock.getId());
        values.put("date",stock.getDate());
        values.put("plan",stock.getPlan());
        values.put("share_rate",stock.getShare_rate());
        values.put("name_code",stock.getName_code());
        values.put("picture_url",stock.getImgPic());
        values.put("bazaar",stock.getBazaar());

        dbx.insertOrThrow("Stocks",null,values);
        dbx.close();
    }

    public void Update(DBHelper vt, Stock stock, String id) {
        SQLiteDatabase dbx = vt.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name",stock.getName());
        values.put("pop",stock.getPop());
        values.put("target_price",stock.getCurrent_price());
        values.put("stock_id",stock.getId());
        values.put("date",stock.getDate());
        values.put("plan",stock.getPlan());
        values.put("share_rate",stock.getShare_rate());
        values.put("name_code",stock.getName_code());
        values.put("picture_url",stock.getImgPic());
        values.put("bazaar",stock.getBazaar());

        dbx.update("Stocks",values,"stock_id=?",new String[]{id});
        dbx.close();
    }
}
