package com.application_3.halkaarzlar.stockRepo;

import androidx.lifecycle.MutableLiveData;

import com.application_3.halkaarzlar.stockDatabase.DBHelper;
import com.application_3.halkaarzlar.objects.Stock;

import java.util.ArrayList;
import java.util.List;

public class StockRepo {

    DBHelper vt;
    private MutableLiveData<List<Stock>> stockDatas;

    public StockRepo() {
        this.stockDatas = new MutableLiveData<>();
        //getObj();
    }

    public MutableLiveData<List<Stock>> returnStockDatas() {
        return this.stockDatas;
    }

    public void getObj() {

        Stock s1 = new Stock("1","1.2 tl","1.3","1","1232","eşit","23","CWE","YILDIZ");
        ArrayList<Stock> a = new ArrayList<>();
        a.add(s1);

        this.stockDatas.setValue(a);
    }

    public void update(ArrayList<Stock> list) {
        this.stockDatas.setValue(list);
    }
}
