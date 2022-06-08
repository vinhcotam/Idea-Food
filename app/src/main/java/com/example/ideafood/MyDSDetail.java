package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ideafood.Module.Dsach;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyDSDetail extends AppCompatActivity {
    String dsachid,username;
    TextView tv_bv1,tv_bv2,tv_bv3,tv_bv4;
    ArrayList<String>mListPost;
    ArrayList<Dsach> mListDetailDS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dsdetail);
        anhXa();
        connectDB();
        getDsachID();
        loadDetail();
    }
    DatabaseReference database;
    private void connectDB() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    private void loadDetail() {
        mListPost=new ArrayList<>();
        mListDetailDS=new ArrayList<>();
        Query query=database.child("DSxemsau");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    Dsach ds = item.getValue(Dsach.class);
                    if (ds.getDsachid().equals(dsachid) && ds.getUsername().equals(username)) {
                        mListDetailDS.add(ds);
                        if (ds.getPostid() == null) {
                            tv_bv1.setVisibility(View.VISIBLE);
                            tv_bv1.setText("Không có bài viết nào");
                        } else {
                            for (int i = 0; i < mListDetailDS.get(i).getPostid().size(); i++) {
                                if (mListDetailDS.get(i).getPostid().size() == 1) {
                                    tv_bv1.setVisibility(View.VISIBLE);
                                    tv_bv1.setText(mListDetailDS.get(i).getPostid().get(0));
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void anhXa() {
        tv_bv4=findViewById(R.id.tv_bv4);
        tv_bv1=findViewById(R.id.tv_bv1);
        tv_bv2=findViewById(R.id.tv_bv2);
        tv_bv3=findViewById(R.id.tv_bv3);
    }

    private void getDsachID() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
         dsachid=bundle.getString("dsachid");
        username=bundle.getString("username");
        Log.d("id",dsachid);
        Log.d("username",username);
    }
}