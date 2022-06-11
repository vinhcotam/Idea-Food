package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Postdetail extends AppCompatActivity {
    Button btn_xoa;
    TextView tv_header,tv_category,tv_postname,tv_date,tv_status,tv_content1,tv_content2;
    ImageView img_fd;
    DatabaseReference database;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postdetail);
        btn_xoa = findViewById(R.id.bt_xoa);
        tv_header = findViewById(R.id.text_header);
        tv_category =findViewById(R.id.text_category);
        tv_postname = findViewById(R.id.text_postname);
        tv_date = findViewById(R.id.text_date);
        tv_status = findViewById(R.id.text_status);
        tv_content1 = findViewById(R.id.text_content1);
        tv_content2 = findViewById(R.id.text_content2);
        img_fd = findViewById(R.id.image_fd);

        database = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String postID = bundle.getString("postid");
        String postname = bundle.getString("postname");
        String header = bundle.getString("header");
        String category = bundle.getString("category");
        String date = bundle.getString("date");
        Boolean status = bundle.getBoolean("status");
        String content1 = bundle.getString("content1");
        String content2 = bundle.getString("content2");
        StorageReference storageReference = FirebaseStorage.getInstance("gs://idea-food-cd7e7.appspot.com").getReference().child("imgMain/"+postID+"/"+postID);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
//                Glide.with(viewGroup.getContext()).load(uri).into(imageView);
               img_fd.setImageURI(uri);
            }
        });

        tv_header.setText(header);
        tv_postname.setText(postname);
        tv_category.setText(category);
        tv_content1.setText(content1);
        tv_content2.setText(content2);
        tv_date.setText(date);
        if(status == true){
            tv_status.setText("Đã duyệt bài !");
        }else {
            tv_status.setText("Đang chờ duyệt !");
        }

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