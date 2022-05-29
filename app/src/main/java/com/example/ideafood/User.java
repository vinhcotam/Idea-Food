package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class User extends AppCompatActivity {
    ListView listView;
    ArrayList<Nguoidung> listnguoidung= null;
    AdapterUser adapterUser;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        database = FirebaseDatabase.getInstance().getReference();
        Query Qget_user = database.child("Account");
        Qget_user.addListenerForSingleValueEvent(new ValueEventListener() {
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
        listView = findViewById(R.id.listview_user);
        adapterUser = new AdapterUser(this, R.layout.lv_user, listnguoidung);
        listView.setAdapter(adapterUser);

    }
}