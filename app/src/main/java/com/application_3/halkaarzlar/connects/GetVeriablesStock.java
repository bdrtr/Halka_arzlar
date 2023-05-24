package com.application_3.halkaarzlar.connects;

import android.content.Context;
import android.os.AsyncTask;
import android.util.ArraySet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.application_3.halkaarzlar.objects.Stock;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.jar.Attributes;

public class GetVeriablesStock  {

    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private String URL = "https://halkarz.com/";

    private Context cnt;
    public String Res;
    public ArrayList<String> Links;
    private Stock tempStock;

    public GetVeriablesStock(Context cnt) {
        this.cnt = cnt;
        this.Links = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(cnt);
    }
    public void getURL() {
        stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document doc = Jsoup.parse(response);
                Elements indexes = doc.select("a[href]");
                for (Element e: indexes) {
                    Links.add(e.attr("href").toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(cnt,"hata",Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }

    public ArrayList<Stock> getStocks() {
        ArrayList<Stock> array = new ArrayList<>();
        for (int i=5;i<10;i++) {
            String url = this.Links.get(i);
            stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Document doc = Jsoup.parse(response);
                    Elements codeName = doc.select("h2");
                    Elements name = doc.select("h1.il-halka-arz-sirket");
                    String dateT = doc.select("time").attr("datetime");
                    Elements elem = doc.select("strong");

                    tempStock = new Stock(name.text(),elem.get(0).text(),1.2,codeName.text(),dateT,elem.get(1).text(),
                            elem.get(6).text(),codeName.text(),elem.get(8).text());
                    array.add(tempStock);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("----","baglanti hatasi");
                }
            });

            requestQueue.add(stringRequest);
        }
        return array;

    }

}
