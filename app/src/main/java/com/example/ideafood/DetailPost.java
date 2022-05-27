package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.ideafood.Module.Posts;
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
    }

    private void loadDetailPost() {
        mListPost=new ArrayList<>();
        Query qr_getDetailPost=database.child("post").orderByChild("postid").equalTo("8479");
        qr_getDetailPost.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()){
                    Posts post=item.getValue(Posts.class);
                    mListPost.add(post);
                    DisplayDetail();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void DisplayDetail(int index) {
        tv_headerdp.setText(mListPost.get(index).getHeader());
        tv_date_postdp.setText(mListPost.get(index).getDate());
        tv_user_post.setText(mListPost.get(index).getUserid());
        tv_content1dp.setText(mListPost.get(index).getContent_post().get(0).toString());
        tv_content2dp.setText(mListPost.get(index).getContent_post().get(1).toString());
    }


    DatabaseReference database;
    private void ConnectDB() {
        database = FirebaseDatabase.getInstance().getReference();
    }
}