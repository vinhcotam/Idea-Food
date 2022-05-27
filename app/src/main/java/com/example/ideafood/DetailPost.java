package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.ideafood.Module.Img;
import com.example.ideafood.Module.Posts;
import com.example.ideafood.Module.Video;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailPost extends AppCompatActivity {
    ArrayList<Posts> mListPost;
    TextView tv_headerdp,tv_user_post,tv_date_postdp,tv_categorydp,tv_content1dp,tv_content2dp;
    ImageView iv_imgdp,iv_img1dp;
    VideoView vv_videodp;
    RecyclerView rcv_bvtt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        anhXa();
        ConnectDB();
        loadDetailPost();
    }

    private void anhXa() {
        tv_categorydp=findViewById(R.id.tv_categorydp);
        tv_content1dp=findViewById(R.id.tv_content1dp);
        tv_content2dp=findViewById(R.id.tv_content2dp);
        tv_headerdp=findViewById(R.id.tv_headerdp);
        tv_user_post=findViewById(R.id.tv_user_post);
        tv_date_postdp=findViewById(R.id.tv_date_postdp);
        iv_imgdp=findViewById(R.id.iv_imgdp);
        iv_img1dp=findViewById(R.id.iv_img1dp);
        vv_videodp=findViewById(R.id.vv_videodp);
        rcv_bvtt=findViewById(R.id.rcv_bvtt);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DetailPost.this);
    }

    private void loadDetailPost() {
            Query qr_getDetailPost=database.child("post");
        qr_getDetailPost.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot item:snapshot.getChildren()){
                        Posts post=item.getValue(Posts.class);
//                        Img img=item.getValue(Img.class);
//                        Log.d("name","name: "+img.getLinkimg());
                        Log.d("name","name: "+post.getPostid());
                        if(post.getPostid().equals("8799")){
                                tv_headerdp.setText(post.getHeader());
                                tv_user_post.setText(post.getUserid());
                        }
                    }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        Query qr_getImgPost=database.child("images");
//        qr_getDetailPost.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot item:snapshot.getChildren()){
//                    Img img=item.getValue(Img.class);
//                    int test=0;
//                    if(img.getPostid().equals("9569")){
//                        test=test+1;
//                        if(test==1){
//                            iv_imgdp.setImageURI(Uri.parse(img.getLinkimg()));
//                        }else if (test==2){
//                            iv_img1dp.setImageURI(Uri.parse(img.getLinkimg()));
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        Query qr_getVideoPost=database.child("videos");
        qr_getVideoPost.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()){
                    Video video=item.getValue(Video.class);
                    if(video.getPostid().equals("8799")){
                        vv_videodp.setVideoURI(Uri.parse(video.getLink()));
                        vv_videodp.start();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




    DatabaseReference database;
    private void ConnectDB() {
        database = FirebaseDatabase.getInstance().getReference();
    }
}