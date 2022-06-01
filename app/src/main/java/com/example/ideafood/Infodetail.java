package com.example.ideafood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class Infodetail extends AppCompatActivity {
    TextView tvname,tvemail;
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infodetail);
        tvname = findViewById(R.id.tv_name);
        tvemail = findViewById(R.id.tv_email);


        Intent intent =getIntent();
        Bundle bundle = intent.getExtras();
        String Username = bundle.getString("Username");
        String Password = bundle.getString("Password");
        String Email = bundle.getString("Email");
        String Level = bundle.getString("Level");
        tvname.setText(Username);
        tvemail.setText(Email);
    }
    void loadpost(){

    }
}