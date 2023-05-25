package com.application_3.halkaarzlar.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.application_3.halkaarzlar.R;
import com.application_3.halkaarzlar.databinding.ActivityLoginBinding;
import com.application_3.halkaarzlar.objects.User;
import com.application_3.halkaarzlar.userDBO.DBHelper;
import com.application_3.halkaarzlar.userDBO.UserDBO;

import java.util.Timer;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding;
    DBHelper vt;
    private int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);

        vt = new DBHelper(getApplicationContext());
        activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        activityLoginBinding.setLOGin(this);

    }

    public String UserName() {
        return activityLoginBinding.editTextText.getText().toString();
    }

    public String Password() {
        return activityLoginBinding.editTextText2.getText().toString();
    }

    public int count() {
        return activityLoginBinding.editTextText2.getText().length();
    }
    public boolean Login() {

        User user = new User(0,UserName(),Password());
        if (new UserDBO().LOGIN(vt,user)) {
            Intent i = new Intent(this,MainActivity.class);
            i.putExtra("id",1);
            i.putExtra("user",user.getName());
            i.putExtra("pass",user.getPasswd());
            startActivity(i);
            return true;
        }

        new UserDBO().SIGNUP(vt,user);
        Toast.makeText(getApplicationContext(),"kayıt tamamlandı",Toast.LENGTH_LONG).show();
        return true;
    }

}