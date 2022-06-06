package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Infodetail extends AppCompatActivity {
    TextView tvname,tvemail;
    DatabaseReference database;
    Button btn_doipass, bt_xoa;
    ListView listView;
    ArrayList<Posts> listposts = null;
    AdapterPost adapterPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infodetail);
        tvname = findViewById(R.id.tv_name);
        tvemail = findViewById(R.id.tv_email);
        btn_doipass = findViewById(R.id.btn_doimk);
        bt_xoa = findViewById(R.id.btn_xoa);
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
                    listposts.add(posts);
                }
                adapterPost = new AdapterPost(Infodetail.this,R.layout.lv_bai_viet,listposts);
                listView.setAdapter(adapterPost);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}