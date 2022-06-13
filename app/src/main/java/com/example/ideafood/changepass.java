package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ideafood.Module.Account;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class changepass extends AppCompatActivity {
    Button btn_pass;
    EditText edt_passcu,edt_pass,edt_pass2;
    DatabaseReference database;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);
        btn_pass = findViewById(R.id.btn_doi);
        edt_passcu = findViewById(R.id.edt_passcu);
        edt_pass = findViewById(R.id.edt_pass);
        edt_pass2 = findViewById(R.id.edt_pass2);
        database = FirebaseDatabase.getInstance().getReference();



        Intent intent =getIntent();
        Bundle bundle = intent.getExtras();
       String username = bundle.getString("username");
       String password = bundle.getString("password");
       String email = bundle.getString("email");
       String level = bundle.getString("level");


        btn_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passcu = edt_passcu.getText().toString();
                String pass1 = edt_pass.getText().toString();
                String pass2 = edt_pass2.getText().toString();
                HashMap updateacc = new HashMap<>();
                updateacc.put("password",pass2);

               Query acc = database.child("Account").orderByChild("username").equalTo(username);
                acc.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot item : snapshot.getChildren()){
                            Account ng = item.getValue(Account.class);
                            if(ng.getUsername().equals(username)){
                                key = item.getKey();
                            }
                        }
                        if(edt_passcu.getText().length() == 0 || edt_pass.getText().length() == 0 || edt_pass2.getText().length() == 0 )
                        {
                            Toast.makeText(changepass.this,"Vui lòng nhập đầy đủ",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(passcu.equals(password)) {
                            if (!pass1.equals(pass2)) {
                                Toast.makeText(changepass.this, "Mật khẩu không khớp !", Toast.LENGTH_SHORT).show();
                            } else {
                                database.child("Account").child(key).updateChildren(updateacc).addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        Toast.makeText(changepass.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                                return;
                            }
                        }else {
                            Toast.makeText(changepass.this,"Mật khẩu không đúng! ",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

}