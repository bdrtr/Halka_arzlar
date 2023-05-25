package com.application_3.halkaarzlar.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.application_3.halkaarzlar.R;
import com.application_3.halkaarzlar.activities.DetailActivity;
import com.application_3.halkaarzlar.activities.DetailPrivateActivity;
import com.application_3.halkaarzlar.databinding.StockShowBinding;
import com.application_3.halkaarzlar.objects.Stock;
import com.application_3.halkaarzlar.objects.User;

import java.util.List;

public class StockAdapter  extends  RecyclerView.Adapter<StockAdapter.myStockShowDesign>{

    public Context cnt;
    public List<Stock> stocks;
    public List<Stock> filter;

    private User user;
    public StockAdapter (Context cnt, List<Stock> stocks, User user) {
        this.cnt = cnt;
        this.stocks = stocks;
        this.user = user;
    }

    public void filter(List<Stock> st) {
        filter.clear();
        this.stocks = st;
        notifyDataSetChanged();
    }
    public void setUser(User user) {
        this.user = user;
    }

    public class myStockShowDesign extends RecyclerView.ViewHolder {
        private StockShowBinding designBinding;

        public myStockShowDesign(StockShowBinding designBinding) {
            super(designBinding.getRoot());
            this.designBinding = designBinding;
        }
    }

    @NonNull
    @Override
    public myStockShowDesign onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(cnt);
        StockShowBinding design = DataBindingUtil.inflate(layout, R.layout.stock_show,parent,false);
        //burda xml sınıfının bir adi mevcut "NAMES" ordan geliyor.
        return new myStockShowDesign(design);
    }

    @Override
    public void onBindViewHolder(@NonNull myStockShowDesign holder, int position) {
        Stock cStock = stocks.get(position);
        holder.designBinding.setStock(cStock);
        holder.designBinding.Image.setImageResource(R.raw.back);

        holder.designBinding.allCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user.getId() >= 0) {
                    Intent i = new Intent(cnt, DetailPrivateActivity.class);
                    i.putExtra("Stock",cStock);
                    i.putExtra("user",user);
                    cnt.startActivity(i);

                } else {
                    Intent i = new Intent(cnt, DetailActivity.class);
                    i.putExtra("Stock",cStock);
                    cnt.startActivity(i);
                }
                //detail page geçiş
            }
        });
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }
}
