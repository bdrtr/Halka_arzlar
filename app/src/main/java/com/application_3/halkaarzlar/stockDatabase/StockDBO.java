package com.application_3.halkaarzlar.stockDatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public void AddList(DBHelper vt, ArrayList<Stock> stock, ArrayList<Stock> lastStock) {


        SQLiteDatabase dbx = vt.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i=0;i<stock.size();i++) {
            values.put("name",stock.get(i).getName());
            values.put("pop",stock.get(i).getPop());
            values.put("target_price",stock.get(i).getCurrent_price());
            values.put("stock_id",stock.get(i).getId());
            values.put("date",stock.get(i).getDate());
            values.put("plan",stock.get(i).getPlan());
            values.put("share_rate",stock.get(i).getShare_rate());
            values.put("name_code",stock.get(i).getName_code());
            values.put("bazaar",stock.get(i).getBazaar());

            dbx.insertOrThrow("Stocks",null,values);
        }
        dbx.close();
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
        values.put("bazaar",stock.getBazaar());

        dbx.update("Stocks",values,"stock_id=?",new String[]{id});
        dbx.close();
    }
}
