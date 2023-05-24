package com.application_3.halkaarzlar.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.application_3.halkaarzlar.R;
import com.application_3.halkaarzlar.adapters.StockAdapter;
import com.application_3.halkaarzlar.connects.GetVeriablesStock;
import com.application_3.halkaarzlar.databinding.ActivityMainBinding;
import com.application_3.halkaarzlar.objects.Stock;
import com.application_3.halkaarzlar.viewholders.MainViewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    private ActivityMainBinding activityMainBinding;
    private MainViewmodel mainViewmodel;
    private GetVeriablesStock getS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setObjectMain(this);
        mainViewmodel = new ViewModelProvider(this).get(MainViewmodel.class);

        activityMainBinding.toolbar.setTitle("Bilgi sayfası");
        activityMainBinding.toolbar.setSubtitle("hisseler");
        setSupportActionBar(activityMainBinding.toolbar);

        getS = new GetVeriablesStock(this);
        getS.getURL();
        //listeyi güncelle

        mainViewmodel.stockDatas.observe(this,list ->  {
            StockAdapter adapter = new StockAdapter(this, list);
            activityMainBinding.setAdapter(adapter);
            activityMainBinding.recyclerViewMain.setHasFixedSize(true);

        });

    }


    public void loadDatas() {

        //activityMainBinding.textView.setText(s.getName());
        mainViewmodel.update(getS.getStocks());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ust_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.account_login) {
            Toast.makeText(this,"login",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (item.getItemId() == R.id.update_veriables) {
            loadDatas();
            return true;
        }
        return false;
    }

}