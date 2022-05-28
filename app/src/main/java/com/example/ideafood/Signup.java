package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ConnectDB();
        SetEvent();
    }
    void SetEvent(){
        Button button = findViewById(R.id.signup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et;
                et = findViewById(R.id.sign_username);
                String username = et.getText().toString();
                et = findViewById(R.id.sign_email);
                String email = et.getText().toString();
                et = findViewById(R.id.sign_password);
                String password = et.getText().toString();
                Account a = new Account(username, password, "1", email);
                Query allAccount = database.child("Account").orderByChild("username").equalTo(username);
                allAccount.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Boolean contain = false;
                        for(DataSnapshot item:snapshot.getChildren()){
                            Account a = item.getValue(Account.class);
                            Toast.makeText(Signup.this,"Username này đã tồn tại", Toast.LENGTH_LONG).show();
                            contain = true;
                        }
                        if(!contain){
                            FirebaseDatabase.getInstance().getReference().child("Account").push().setValue(a);
                            Toast.makeText(Signup.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
    DatabaseReference database;
    void ConnectDB(){
        database = FirebaseDatabase.getInstance().getReference();
    }
}