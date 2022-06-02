package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

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
    DatabaseReference database,databaseReference;
    ArrayList<String> listkey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        listView = findViewById(R.id.listview_user);
        ConnectDB();
        loaduser ();
        infodetail();
    }
    void ConnectDB(){
        database = FirebaseDatabase.getInstance().getReference();
    }
    void loaduser(){
        Query allAccount = database.child("Account");
        allAccount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listnguoidung = new ArrayList<>();
                listkey = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()) {
                    Nguoidung nguoidung = item.getValue(Nguoidung.class);
                    listnguoidung.add(nguoidung);
                    String key = item.getKey();
                    listkey.add(key);
                }
                adapterUser = new AdapterUser(User.this, R.layout.lv_user, listnguoidung);
                listView.setAdapter(adapterUser);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    void infodetail(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(User.this,Infodetail.class);
                Bundle bundle = new Bundle();
                bundle.putString("Username",listnguoidung.get(i).username);
                bundle.putString("Password",listnguoidung.get(i).password);
                bundle.putString("Email",listnguoidung.get(i).email);
                bundle.putString("Level",listnguoidung.get(i).level);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

}