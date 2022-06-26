package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ConnectDB();
        getDatafromIntent();
        SetEvent();
    }
    ArrayList<Account> accountList;
    DatabaseReference database;
    SQLiteDatabase db;
    void ConnectDB(){
        if(!isNetworkAvailable()){
            Toast.makeText(this, "Kết nối mạng không khả dụng", Toast.LENGTH_LONG).show();
        }
        database = FirebaseDatabase.getInstance().getReference();
        db = openOrCreateDatabase("FirstUse.db", MODE_PRIVATE, null);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
                if(username.trim().equals("")){
                    Toast.makeText(Login.this, "Bạn chưa nhập tài khoản", Toast.LENGTH_SHORT).show();
                    return;
                }
                et = findViewById(R.id.ET_password);
                String password = et.getText().toString();
                if(password.trim().equals("")){
                    Toast.makeText(Login.this, "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                Query checkAccount = database.child("Account").orderByChild("username").equalTo(username);
                checkAccount.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Boolean checkOK = false;
                        for(DataSnapshot item:snapshot.getChildren()){
                            Account a = item.getValue(Account.class);
                            if(a.getPassword().equals(password)){
                                Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                                CheckBox cb_saveaccount = findViewById(R.id.saveaccount);
                                if(cb_saveaccount.isChecked()){
                                    String sql = "Update Account set username='"+username+"', password='"+password+"' where keylocal='localaccount'";
                                    db.execSQL(sql);
                                }
                                else{
                                    String sql = "Update Account set username='', password='' where keylocal='localaccount'";
                                    db.execSQL(sql);
                                }
                                checkOK=true;
                                Intent intent = new Intent(Login.this, Homepage.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("username", username);
                                intent.putExtras(bundle);
                                startActivity(intent);
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
        TextView tv = findViewById(R.id.notaccount);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sql = "Update Account set username='', password='' where keylocal='localaccount'";
                db.execSQL(sql);
                Intent intent = new Intent(Login.this, Homepage.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", "");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    void getDatafromIntent(){
        Intent intent = getIntent();
        try {
            Bundle bundle = intent.getExtras();
            String username = bundle.getString("username");
            String password = bundle.getString("password");
            EditText et = findViewById(R.id.ET_username);
            et.setText(username);
            et = findViewById(R.id.ET_password);
            et.setText(password);
        }
        catch(Exception e){
            return;
        }
    }
}