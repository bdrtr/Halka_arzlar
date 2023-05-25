package com.application_3.halkaarzlar.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.application_3.halkaarzlar.objects.User;
import com.application_3.halkaarzlar.stockDatabase.DBHelper;
import com.application_3.halkaarzlar.stockDatabase.StockDBO;
import com.application_3.halkaarzlar.R;
import com.application_3.halkaarzlar.adapters.StockAdapter;
import com.application_3.halkaarzlar.connects.GetVeriablesStock;
import com.application_3.halkaarzlar.databinding.ActivityMainBinding;
import com.application_3.halkaarzlar.objects.Stock;
import com.application_3.halkaarzlar.viewholders.MainViewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {
    private Timer tmr;
    private ActivityMainBinding activityMainBinding;
    private MainViewmodel mainViewmodel;
    private GetVeriablesStock myRunnable;

    private DBHelper vt;
    private StockDBO dbo;

    private User currentUser;
    StockAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        currentUser = new User(-1,"def","def");
        //
        //
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setObjectMain(this);
        mainViewmodel = new ViewModelProvider(this).get(MainViewmodel.class);

        activityMainBinding.toolbar.setTitle("Bilgi sayfası");
        activityMainBinding.toolbar.setSubtitle("hisseler");
        activityMainBinding.toolbar.setTitleTextColor(getColor(R.color.Linen));
        activityMainBinding.toolbar.setSubtitleTextColor(getColor(R.color.Linen));

        setSupportActionBar(activityMainBinding.toolbar);
        myRunnable = new GetVeriablesStock(this);

        //
        vt = new DBHelper(getApplicationContext());
        dbo =  new StockDBO();
        ConnectnUpdate();
        mainViewmodel.update(dbo.getAllStocks(vt)); //verileri hemen yukle
        //
        loadDatas();

        Refresh();

        //ConnectnUpdate(); //get the veriables

        mainViewmodel.stockDatas.observe(this,list ->  {
            adapter = new StockAdapter(this, list,currentUser);
            activityMainBinding.setAdapter(adapter);
            activityMainBinding.recyclerViewMain.setHasFixedSize(true);
        });

    }


    public void loadDatas() {
        ArrayList<Stock> stc = myRunnable.returnStockList();
        dbo.AddList(vt,stc,(ArrayList<Stock>) mainViewmodel.returnStocks());
        mainViewmodel.update(dbo.getAllStocks(vt));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ust_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.account_login) {
            startActivity(new Intent(this,LoginActivity.class));
            return true;
        }
        else if (item.getItemId() == R.id.exit_account) {
            currentUser = new User(-1,"guest","guest");
            adapter.setUser(currentUser);
            Toast.makeText(getApplicationContext(),"hesaptan çıkıldı",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (item.getItemId() == R.id.app_search) {
            SearchView sc = findViewById(R.id.app_search);
            sc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    List<Stock> st = new ArrayList<>();
                    for (Stock s: myRunnable.returnStockList()) {
                        if (s.getId().contains(query.toUpperCase())) {{
                            st.add(s);
                        }}
                    }
                    adapter.filter(st);

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    //adapter.filter(newText);
                    return true;
                }
            });
            //adapter.filter(activityMainBinding.)
        }
        return false;
    }


    public void ConnectnUpdate() {
        tmr = new Timer();
        long delay = 1000*10;
        long period = 1000*10;
        myRunnable.getURL();
        tmr.scheduleAtFixedRate(myRunnable,delay,period);
    }

    public void Refresh() {
        activityMainBinding.recyclerViewMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy < -100 ) {
                    loadDatas();
                    Toast.makeText(getApplicationContext(),"güncellendi",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void userVeri() {
        currentUser = new User(getIntent().getIntExtra("id",-1)
                ,getIntent().getStringExtra("user"),getIntent().getStringExtra("pass"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tmr.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userVeri();
        adapter.setUser(currentUser);
        Log.i("----","current user name: "+currentUser.getId());
    }
}