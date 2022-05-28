package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ideafood.classs.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ConnectDB();
        SetEvent();
    }
    ArrayList<Account> accountList;
    DatabaseReference database;
    void ConnectDB(){
        database = FirebaseDatabase.getInstance().getReference();
    }
    void SetEvent(){
        Button button = findViewById(R.id.BT_signup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });
        button = findViewById(R.id.BT_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.ET_username);
                String username = et.getText().toString();
                et = findViewById(R.id.ET_password);
                String password = et.getText().toString();
                Query checkAccount = database.child("Account").orderByChild("username").equalTo(username);
                checkAccount.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Boolean checkOK = false;
                        for(DataSnapshot item:snapshot.getChildren()){
                            Account a = item.getValue(Account.class);
                            if(a.getPassword().equals(password)){
                                Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                                checkOK=true;
                            }
                        }
                        if(!checkOK){
                            Toast.makeText(Login.this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Login.this, "??", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}