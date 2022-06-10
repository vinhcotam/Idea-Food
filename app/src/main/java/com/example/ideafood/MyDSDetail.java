package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ideafood.Module.Dsach;
import com.example.ideafood.Module.Posts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyDSDetail extends AppCompatActivity {
    String dsachid,username;
    TextView tv_bv1,tv_bv2,tv_bv3,tv_bv4,tv_nguoitao,tv_ngaytao;
    ArrayList<Posts>mListPost;
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
int i;
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
                        tv_ngaytao.append(": "+ds.getDate());
                        tv_nguoitao.append(": "+ds.getUsername());
                        if (ds.getPostid() == null) {
                            tv_bv1.setVisibility(View.VISIBLE);
                            tv_bv1.setText("Không có bài viết nào");
                        } else {
                            for (i = 0; i < ds.getPostid().size(); i++) {
//                                if(ds.getPostid().size()==1){
                                    tv_bv1.setVisibility(View.VISIBLE);
                                    String id=ds.getPostid().get(i);
                                    Query queryPost=database.child("post");
                                    queryPost.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(DataSnapshot item:snapshot.getChildren()){
                                                Posts posts=item.getValue(Posts.class);
                                                if(posts.getPostid().equals(id)){
                                                    mListPost.add(posts);
                                                    if(mListPost.size()==1){
                                                        tv_bv1.setText(posts.getPostname());
                                                    }
                                                    if(mListPost.size()==2){
                                                        tv_bv2.setVisibility(View.VISIBLE);
//                                                        tv_bv1.setText(mListPost.get(0).getPostname());
                                                        tv_bv2.setText(mListPost.get(1).getPostname());
                                                    }if(mListPost.size()==3){
                                                        tv_bv3.setVisibility(View.VISIBLE);
                                                        tv_bv3.setText(mListPost.get(2).getPostname());
                                                    }
                                                    if(mListPost.size()==4){
                                                        tv_bv4.setVisibility(View.VISIBLE);
                                                        tv_bv4.setText(mListPost.get(3).getPostname());
                                                    }

                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });



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
        tv_nguoitao=findViewById(R.id.tv_nguoitao);
        tv_ngaytao=findViewById(R.id.tv_ngaytao);
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