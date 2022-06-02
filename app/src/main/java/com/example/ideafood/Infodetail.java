package com.example.ideafood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class Infodetail extends AppCompatActivity {
    TextView tvname,tvemail;
    DatabaseReference database;
    Button btn_doipass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infodetail);
        tvname = findViewById(R.id.tv_name);
        tvemail = findViewById(R.id.tv_email);
        btn_doipass = findViewById(R.id.btn_doimk);


        Intent intent =getIntent();
        Bundle bundle = intent.getExtras();
        String Username = bundle.getString("Username");
        String Password = bundle.getString("Password");
        String Email = bundle.getString("Email");
        String Level = bundle.getString("Level");
        tvname.setText(Username);
        tvemail.setText(Email);
        btn_doipass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Infodetail.this,changepass.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("Username",Username);
                bundle1.putString("Password",Password);
                bundle1.putString("Email",Email);
                bundle1.putString("Level",Level);
                intent1.putExtras(bundle1);
                startActivity(intent1);
            }
        });
    }

}