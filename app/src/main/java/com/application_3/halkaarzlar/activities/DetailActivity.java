package com.application_3.halkaarzlar.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import android.content.Intent;
import android.os.Bundle;

import com.application_3.halkaarzlar.R;
import com.application_3.halkaarzlar.databinding.ActivityDetailBinding;
import com.application_3.halkaarzlar.objects.Stock;
import com.application_3.halkaarzlar.viewholders.DetailViewmodel;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding activityDetailBinding;
    DetailViewmodel detailViewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detail);
        activityDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_detail);
        Stock stock = (Stock) getIntent().getSerializableExtra("Stock");
        activityDetailBinding.setStockObject(stock);
    }
}