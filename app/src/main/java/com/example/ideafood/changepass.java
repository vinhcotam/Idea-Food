package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class changepass extends AppCompatActivity {
    Button btn_pass;
    EditText edt_pass,edt_pass2;
    DatabaseReference database;
    ArrayList<Nguoidung> listnguoidung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);
        btn_pass = findViewById(R.id.btn_doi);
        edt_pass = findViewById(R.id.edt_pass);
        edt_pass2 = findViewById(R.id.edt_pass2);
        database = FirebaseDatabase.getInstance().getReference();
        String pass1 = edt_pass.getText().toString();
        String pass2 = edt_pass2.getText().toString();

        Query allAccount = database.child("Account");
        allAccount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listnguoidung = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()) {
                    Nguoidung nguoidung = item.getValue(Nguoidung.class);
                    listnguoidung.add(nguoidung);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        btn_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pass1 != pass2 ){
                    Toast.makeText(changepass.this,"Mật khẩu không khớp !",Toast.LENGTH_SHORT).show();
                }
                FirebaseDatabase.getInstance().getReference().child("Account").push().setValue(pass2);

            }
        });
    }

}