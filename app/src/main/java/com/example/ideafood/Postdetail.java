package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Postdetail extends AppCompatActivity {
    Button btn_xoa;
    DatabaseReference database;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postdetail);
        btn_xoa = findViewById(R.id.bt_xoa);
        database = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String postID = bundle.getString("postid");
        String postname = bundle.getString("postname");
        String header = bundle.getString("header");
        String category = bundle.getString("category");
        String date = bundle.getString("date");



        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query ps = database.child("post");
                ps.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot item : snapshot.getChildren()){
                            Posts posts = item.getValue(Posts.class);
                            if(posts.getPostid().equals(postID)){
                                key = item.getKey();
                            }
                        }
                        database.child("post").child(key).removeValue();
                        Toast.makeText(Postdetail.this,"Đã xóa bài viết !",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}