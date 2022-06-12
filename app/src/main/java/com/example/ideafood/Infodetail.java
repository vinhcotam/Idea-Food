package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Infodetail extends AppCompatActivity {
    TextView tvname,tvemail;
    DatabaseReference database;
    Button btn_doipass;
    ListView listView;
    ArrayList<Posts> listposts = null;
    AdapterPost adapterPost;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infodetail);
        tvname = findViewById(R.id.tv_name);
        tvemail = findViewById(R.id.tv_email);
        btn_doipass = findViewById(R.id.btn_doimk);

        listView = findViewById(R.id.lv_baiviet);

        database = FirebaseDatabase.getInstance().getReference();


        Intent intent =getIntent();
        Bundle bundle = intent.getExtras();
        String username = bundle.getString("username");
        String password = bundle.getString("password");
        String email = bundle.getString("email");
        String level = bundle.getString("level");
        tvname.setText(username);
        tvemail.setText(email);
        btn_doipass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Infodetail.this,changepass.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("username",username);
                bundle1.putString("password",password);
                bundle1.putString("email",email);
                bundle1.putString("level",level);
                intent1.putExtras(bundle1);
                startActivity(intent1);
            }
        });
        Query mypost = database.child("post").orderByChild("username").equalTo(username);
        mypost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listposts = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()){
                    Posts posts = item.getValue(Posts.class);
                    key = item.getKey();
                    listposts.add(posts);
                }
                adapterPost = new AdapterPost(Infodetail.this,R.layout.bai_viet,listposts);
                listView.setAdapter(adapterPost);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent1 = new Intent(Infodetail.this,Postdetail.class);
            Bundle bundle1 = new Bundle();
            bundle1.putString("postid",listposts.get(i).postid);
            bundle1.putString("postname",listposts.get(i).postname);
            bundle1.putString("header",listposts.get(i).header);
            bundle1.putString("date",listposts.get(i).date);
            bundle1.putString("category",listposts.get(i).category);
            bundle1.putBoolean("status",listposts.get(i).isStatus());
            bundle1.putString("content1",listposts.get(i).content_post.get(0));
            bundle1.putString("content2",listposts.get(i).content_post.get(1));
            intent1.putExtras(bundle1);
            startActivity(intent1);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String postid = listposts.get(i).postid;
                AlertDialog.Builder aldialog = new AlertDialog.Builder(Infodetail.this);
                aldialog.setMessage("Bạn có muốn xóa bài viết này? ").setCancelable(false)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                database.child("post").child(key).removeValue();
                                StorageReference storageReference = FirebaseStorage.getInstance("gs://idea-food-cd7e7.appspot.com").getReference().child("imgMain/"+postid+"/"+postid);
                                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Infodetail.this,"Đã xóa ảnh !",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                StorageReference storageimg1 = FirebaseStorage.getInstance("gs://idea-food-cd7e7.appspot.com").getReference().child("img1/"+postid+"/"+postid);
                                storageimg1.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Infodetail.this,"Đã xóa ảnh 1 !",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                StorageReference storageimg2 = FirebaseStorage.getInstance("gs://idea-food-cd7e7.appspot.com").getReference().child("img2/"+postid+"/"+postid);
                                storageimg2.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Infodetail.this,"Đã xóa ảnh 2 !",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                StorageReference storagevideo = FirebaseStorage.getInstance("gs://idea-food-cd7e7.appspot.com").getReference().child("video/"+postid+"/"+postid);
                                storagevideo.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Infodetail.this,"Đã xóa video !",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Toast.makeText(Infodetail.this,"Đã xóa bài viết !",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = aldialog.create();
                alertDialog.setTitle("Xác nhận xóa ");
                alertDialog.show();
                return true;
            }
        });
    }


}