package com.application_3.halkaarzlar.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.application_3.halkaarzlar.R;
import com.application_3.halkaarzlar.databinding.ActivityDetailPrivateBinding;
import com.application_3.halkaarzlar.objects.Stock;
import com.application_3.halkaarzlar.objects.User;

public class DetailPrivateActivity extends AppCompatActivity {

    private Stock stock;
    private User user;
    ActivityDetailPrivateBinding activityDetailPrivateBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detail_private);
        activityDetailPrivateBinding = DataBindingUtil.setContentView(this,R.layout.activity_detail_private);
        activityDetailPrivateBinding.gif.setImageResource(R.raw.arka2);
        //
        stock = (Stock) getIntent().getSerializableExtra("Stock");
        user = (User) getIntent().getSerializableExtra("user");
        //
        activityDetailPrivateBinding.setDetailpageprivate(this);
        activityDetailPrivateBinding.setUser(user);
        activityDetailPrivateBinding.setStock(stock);
    }

    public void calculate() {
        String text = "";
        Double lot_count = Double.parseDouble(activityDetailPrivateBinding.lotCount.getText().toString());
        double lot_price = Double.parseDouble(activityDetailPrivateBinding.lotPrice.getText().toString());
        double first_lot = lot_price;
        double principial = lot_count*lot_price;

        text += "ana para: "+principial+" tl \n";

        for (int i=1;i<11;i++) {
            lot_price += lot_price/10;
            text += i+".gün toplam ana para "+Math.floor(lot_count*lot_price)+"tl \n"+
                    "lot fiyatı "+Math.floor(lot_price)+" tl \n"+"KAR -> "+Math.floor((lot_price-first_lot)*lot_count) +" tl \n\n";
        }

        activityDetailPrivateBinding.resultText.setText(text);
    }
}