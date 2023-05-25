package com.application_3.halkaarzlar.viewholders;


import android.content.Context;
import android.util.ArraySet;
import android.widget.ArrayAdapter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.application_3.halkaarzlar.objects.Stock;
import com.application_3.halkaarzlar.stockRepo.StockRepo;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

public class MainViewmodel extends ViewModel {

    private StockRepo stockRepo;
    public MutableLiveData<List<Stock>> stockDatas;

    public MainViewmodel() {
        this.stockRepo = new StockRepo();
        this.stockDatas = stockRepo.returnStockDatas();
    }

    public List<Stock> returnStocks() {
        return stockDatas.getValue();
    }

    public void update(ArrayList<Stock> stocks) {
        stockRepo.update(stocks); // repo güncellendi
        stockDatas = stockRepo.returnStockDatas(); //stock datas güncellendi
    }
}
