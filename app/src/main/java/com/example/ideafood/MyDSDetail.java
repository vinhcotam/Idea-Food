package com.example.ideafood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyDSDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dsdetail);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String dsachid=bundle.getString("dsachid");
        Log.d("id",dsachid);
    }
}