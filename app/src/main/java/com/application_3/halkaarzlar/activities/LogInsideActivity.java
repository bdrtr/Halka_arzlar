package com.application_3.halkaarzlar.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.application_3.halkaarzlar.R;
import com.application_3.halkaarzlar.databinding.ActivityLogInsideBinding;
import com.application_3.halkaarzlar.objects.User;

public class LogInsideActivity extends AppCompatActivity {

    private User currentUser;
    ActivityLogInsideBinding activityLogInsideBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_log_inside);
        activityLogInsideBinding = DataBindingUtil.setContentView(this,R.layout.activity_log_inside);

        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        NavigationUI.setupWithNavController(activityLogInsideBinding.bottomMenu, navHostFragment.getNavController());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myInt = new Intent(getApplicationContext(),MainActivity.class);
        myInt.putExtra("userData",currentUser);
        startActivity(myInt);
    }


    public void getUserInfo() {
        currentUser = new User(-1,"user","pass");

        User tempUser = (User) getIntent().getSerializableExtra("userData");
        if (tempUser != null)
            currentUser = tempUser;

    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }
}