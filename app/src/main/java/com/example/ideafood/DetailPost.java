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
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.ideafood.Module.Img;
import com.example.ideafood.Module.Posts;
import com.example.ideafood.Module.Video;
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

public class DetailPost extends AppCompatActivity {
    ArrayList<Posts> mListPost;
    TextView tv_headerdp,tv_user_post,tv_date_postdp,tv_categorydp,tv_content1dp,tv_content2dp;
    ImageView iv_imgdp,iv_img1dp;
    VideoView vv_videodp;
    RecyclerView rcv_bvtt;
    MediaController mc;

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
                        if(post.getPostid().equals("5790")){
                                tv_headerdp.setText(post.getHeader());
                                tv_user_post.setText(post.getUserid());
                                tv_date_postdp.append(" "+post.getDate());
                                tv_categorydp.append(" "+post.getCategory());
                                for(int i=0;i<post.getContent_post().size();i++){
                                    tv_content1dp.setText(post.getContent_post().get(0));
                                    tv_content2dp.setText(post.getContent_post().get(1));
                                }
                        }
                    }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        loadVideo();

    }
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://idea-food-cd7e7.appspot.com");
    StorageReference storageRef;
    private void loadVideo() {
        storageRef=storage.getReference().child("img1/5790/5790");
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                String urlImage = uri.toString();
                Log.d("img",urlImage);
                Glide.with(DetailPost.this).load(urlImage).into(iv_imgdp);
            }
        });
//        return storageRef.toString();

        storageRef=storage.getReference().child("img2/5790/5790");
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
//                iv_imgdp.setImageURI(uri);

                String urlImage = uri.toString();
                Log.d("img1",urlImage);
                Glide.with(DetailPost.this).load(urlImage).into(iv_img1dp);
            }
        });
//        mc=new MediaController(DetailPost.this);
//        vv_videodp.setMediaController(mc);
//        String path="https://firebasestorage.googleapis.com/v0/b/idea-food-cd7e7.appspot.com/o/video%2F7872%2F7872?alt=media&token=cf17fa1a-c420-4e31-b66f-6ac7e3d21744";
//        vv_videodp.setVideoURI(Uri.parse(path));
//        Log.d("video",path);
//        vv_videodp.requestFocus();
//        vv_videodp.start();
        storageRef=storage.getReference().child("video/7872/7872");
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("video",uri.toString());
//                vv_videodp.setVideoPath(uri.toString());
                vv_videodp.setVideoURI(uri);
                vv_videodp.start();
            }
        });
    }


    DatabaseReference database;
    private void ConnectDB() {
        database = FirebaseDatabase.getInstance().getReference();
    }
}