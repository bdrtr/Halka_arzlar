package com.application_3.halkaarzlar.connects;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

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
import java.util.TimerTask;

public class GetVeriablesStock extends TimerTask {
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private String URL = "https://halkarz.com/";

    private Context cnt;
    public ArrayList<Stock> array;
    public ArrayList<String> Links;
    private Stock tempStock;

    public GetVeriablesStock(@NonNull Context context) {
        this.cnt = context;
        this.Links = new ArrayList<>();
        this.array = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(context);
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
                Log.i("----","linkde hata");
            }
        });

        requestQueue.add(stringRequest);

    }

    public void getStocks() {
        this.array.clear();

        for (int i=5;i<30;i+=2) {
            String url = this.Links.get(i);

            stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Document doc = Jsoup.parse(response);
                    Elements codeName = doc.select("h2");
                    Elements name = doc.select("h1.il-halka-arz-sirket");
                    String dateT = doc.select("time").attr("datetime");


                    Element halkaArzFiyatiElement = doc.select("td:has(em:contains(Halka Arz Fiyatı/Aralığı)) + td strong").first();
                    String halkaArzFiyati = halkaArzFiyatiElement.text();

                    Element dagitimYontemiElement = doc.select("td:has(em:contains(Dağıtım Yöntemi)) + td strong").first();
                    String dagitimYontemi = dagitimYontemiElement.text();

                    /*
                    Element payElement = doc.select("td:has(em:contains(Pay)) + td strong").first();
                    String pay = payElement.text();

                    Element ekPayElement = doc.select("td:has(em:contains(Ek Pay)) + td strong").first();
                    String ekPay = ekPayElement.text();


                    Element araciKurumElement = doc.select("td:has(em:contains(Aracı Kurum)) + td strong").first();
                    String araciKurum = araciKurumElement.text();

                    /*
                    Element fiiliDolasimdakiPayElement = doc.select("td:has(em:contains(Fiili Dolaşımdaki Pay)) + td strong").first();
                    String fiiliDolasimdakiPay = fiiliDolasimdakiPayElement.text();
                    /*

                     */

                    Element fiiliDolasimdakiPayOraniElement = doc.select("td:has(em:contains(Fiili Dolaşımdaki Pay Oranı)) + td strong").first();
                    String fiiliDolasimdakiPayOrani = fiiliDolasimdakiPayOraniElement.text();

                    /*
                    Element bistKoduElement = doc.select("td:has(em:contains(Bist Kodu)) + td strong").first();
                    String bistKodu = bistKoduElement.text();

                     */
                    Element pazarElement = doc.select("td:has(em:contains(Pazar)) + td strong").first();
                    String pazar = pazarElement.text();

                    /*
                    Element bistIlkIslemTarihiElement = doc.select("td:has(em:contains(Bist İlk İşlem Tarihi)) + td strong").first();
                    String bistIlkIslemTarihi = bistIlkIslemTarihiElement.text();

                     */
                    tempStock = new Stock(name.text(),halkaArzFiyati,"-",codeName.text(),
                            dateT,dagitimYontemi,fiiliDolasimdakiPayOrani,codeName.text(),pazar);

                    array.add(tempStock);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("----","veri çekerken hata");
                }
            });

            requestQueue.add(stringRequest);
        }

    }

    public ArrayList<Stock> returnStockList() {
        return this.array;
    }

    @Override
    public void run() {
        getStocks();
    }

}
