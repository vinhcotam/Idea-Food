package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideafood.Module.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ConnectDB();
        SetEvent();
    }
    TextView errEmail;
    TextView errPassword;
    TextView errUsername;
    Boolean contain;
    void SetEvent(){
        errEmail = findViewById(R.id.checkemail);
        errPassword = findViewById(R.id.checkpassword);
        errUsername = findViewById(R.id.checkusername);
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
                Account a = new Account(username, password, "2", email);
                Query allAccount = database.child("Account");
                allAccount.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        contain = false;
                        errUsername.setText("");
                        errPassword.setText("");
                        errEmail.setText("");
                        for(DataSnapshot item:snapshot.getChildren()){
                            Account a = item.getValue(Account.class);
                            CheckLoi(a, username, password, email);
                        }
                        if(!contain){
                            FirebaseDatabase.getInstance().getReference().child("Account").push().setValue(a);
                            Toast.makeText(Signup.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                            Signup.this.finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
    void CheckLoi(Account a, String newUsername, String newPassword, String newEmail){
        if(newEmail.length()==0){
            contain = true;
            errEmail.setText("Email không được để trống");
        }
        if(newPassword.length()==0){
            contain = true;
            errPassword.setText("Mật khẩu không được để trống");
        }
        if(newUsername.length()==0){
            contain = true;
            errUsername.setText("Tên người dùng không được để trống");
        }
        if(a.getEmail().equals(newEmail)){
            contain = true;
            errEmail.setText("Email này đã được sử dụng");
        }
        if(a.getUsername().equals(newUsername)){
            contain = true;
            errUsername.setText("Tên người dùng này đã được sử dụng");
        }
        if(newPassword.contains(" ")){
            contain = true;
            errEmail.setText("Mật khẩu không được chứa dấu cách");
        }
    }
    DatabaseReference database;
    void ConnectDB(){
        database = FirebaseDatabase.getInstance().getReference();
    }
}