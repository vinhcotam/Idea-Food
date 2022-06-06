package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ideafood.Adapter.DsachAdapter;
import com.example.ideafood.Module.Dsach;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class MyDS extends AppCompatActivity {
    ListView lv_myds;
    Random random = new Random();
    String username;
    ArrayList<Dsach> mListMyDS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ds);
        anhXa();
        ConnectDB();
        getUsername();
        loadMyDS();
    }

    private void loadMyDS() {
        mListMyDS = new ArrayList<>();

        Query queryDS = database.child("DSxemsau");
        queryDS.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    Dsach ds = item.getValue(Dsach.class);
                    if (ds.getUsername().equals(username)) {
                        mListMyDS.add(ds);
                    }
                }
                Adapter adapter = new DsachAdapter(mListMyDS);
                lv_myds.setAdapter((ListAdapter) adapter);
            }

            ;

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void getUsername() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        username=bundle.getString("username");

    }

    private void anhXa() {
        lv_myds=findViewById(R.id.lv_myds);
    }

    DatabaseReference database;

    private void ConnectDB() {
        database = FirebaseDatabase.getInstance().getReference();
    }
}


