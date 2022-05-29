package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.UiAutomation;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.ideafood.Module.Tag;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class testspinner extends AppCompatActivity {
    private Spinner spinner;
    EditText et_spinnertest;
    Button btn_spinner;
    ArrayList<String> spinnerls;
    ArrayAdapter<String>adapter;
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testspinner);
        database= FirebaseDatabase.getInstance().getReference();
        et_spinnertest=findViewById(R.id.et_spinnertest);
        btn_spinner=findViewById(R.id.btn_spinner);
        spinner=findViewById(R.id.id_spinner);
        spinnerls=new ArrayList<>();
        adapter=new ArrayAdapter<String>(testspinner.this, android.R.layout.simple_spinner_dropdown_item,spinnerls);
        spinner.setAdapter(adapter);
        show();
    }

    private void show() {
        database.child("tags").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot item:snapshot.getChildren()){
                        Tag tag=item.getValue(Tag.class);
                        spinnerls.add(tag.getCategory());
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}