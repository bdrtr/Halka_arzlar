package com.application_3.halkaarzlar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.application_3.halkaarzlar.R;
import com.application_3.halkaarzlar.adapters.StockAdapter;
import com.application_3.halkaarzlar.connects.GetVeriablesStock;
import com.application_3.halkaarzlar.databinding.ActivityMainBinding;
import com.application_3.halkaarzlar.objects.Stock;
import com.application_3.halkaarzlar.objects.User;
import com.application_3.halkaarzlar.stockDatabase.DBHelper;
import com.application_3.halkaarzlar.stockDatabase.StockDBO;
import com.application_3.halkaarzlar.viewholders.MainViewmodel;

import java.util.ArrayList;
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
        //setContentView(R.layout.activity_main); //databinding kullanılıdıgı icin iptal edildi
        currentUser = new User(-1,"def","def"); // o anki kullanıcı misafir
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
        dbo.AddList(vt,stc);
        mainViewmodel.update(dbo.getAllStocks(vt));
        //dbo.DelALL(vt);
        Toast.makeText(getApplicationContext(),"guncellendi",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ust_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.account_login) {
            if (currentUser.getId() == -1)
                startActivity(new Intent(this,LoginActivity.class));
            else
                Toast.makeText(getApplicationContext(),"önce hesabınızdan çıkış yapınız. ",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (item.getItemId() == R.id.exit_account) {
            if (currentUser.getId() == -1) {
                Toast.makeText(getApplicationContext(),"zaten bir hesap açık değil",Toast.LENGTH_SHORT).show();
                return true;
            }
            currentUser = new User(-1,"guest","guest");
            adapter.setUser(currentUser);
            Toast.makeText(getApplicationContext(),"hesaptan çıkıldı",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (item.getItemId() == R.id.next_page) {
            if (currentUser.getId() == -1 ) {
                Toast.makeText(getApplicationContext(),"sadece kayıtlı kullanıcılar. ",Toast.LENGTH_SHORT).show();
                return true;
            }
            Intent myInt = new Intent(getApplicationContext(),LogInsideActivity.class);
            myInt.putExtra("userData",currentUser);
            startActivity(myInt);
            return true;
        }

        return true;
    }


    public void ConnectnUpdate() {
        tmr = new Timer();
        long delay = 1000*10;
        long period = 1000*60;
        myRunnable.getURL();
        tmr.scheduleAtFixedRate(myRunnable,delay,period); //her bir dakikada bir linkler güncellenir
        //dbo.DelALL(vt);
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

    public void userData() {
        currentUser = new User(getIntent().getIntExtra("id",-1)
                ,getIntent().getStringExtra("user"),getIntent().getStringExtra("pass"));

        User tempUser =  (User) getIntent().getSerializableExtra("userData");

        if (tempUser != null)
            currentUser = tempUser;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tmr.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userData();
        adapter.setUser(currentUser);

    }
}