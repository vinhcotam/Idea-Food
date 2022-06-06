package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ideafood.Adapter.DsachAdapter;
import com.example.ideafood.Adapter.DsachsAdapter;
import com.example.ideafood.Adapter.PostsAdapter;
import com.example.ideafood.Module.Dsach;
import com.example.ideafood.Module.Posts;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Dsachtest extends AppCompatActivity {
    ListView lv_dsach;
    Button btn_add_dsach;
    Random random=new Random();
    EditText et_namedsach;
    String username,postid;
    ArrayList<Dsach> mListDS;
    ArrayList<String>listpost;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsachtest);
        anhXa();
        ConnectDB();
        getUsername();
        loadDsach();
        setClickAdd();

    }
    DatabaseReference database;
    private void ConnectDB() {
        database = FirebaseDatabase.getInstance().getReference();
    }


    private void loadDsach() {
        mListDS=new ArrayList<>();
        listpost=new ArrayList<>();
        Query queryDS=database.child("DSxemsau");
        queryDS.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()){
                    Dsach ds=item.getValue(Dsach.class);
                    if(ds.getUsername().equals(username)){
                        mListDS.add(ds);
                    }
            }
                Adapter adapter=new DsachAdapter(mListDS);
                lv_dsach.setAdapter((ListAdapter) adapter);
            };
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        lv_dsach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Query test=database.child("DSxemsau");
                String current_dsID = mListDS.get(i).getDsachid();
                test.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot item:snapshot.getChildren()){
                            Dsach ds = item.getValue(Dsach.class);
                            if(ds.getDsachid().equals(current_dsID)){
                                key = item.getKey();
                                if(ds.getPostid()==null){
                                    ArrayList<String> newPostList = new ArrayList<>();
                                    newPostList.add(postid);
                                    ds.setPostid(newPostList);
                                }
                                else{
                                    ds.getPostid().add(postid);
                                }
                                FirebaseDatabase.getInstance().getReference("DSxemsau").child(key).setValue(ds);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }

    private void getUsername() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
         username=bundle.getString("username");
        postid=bundle.getString("postid");
    }
    String dsachid;
    private void setClickAdd() {
        listpost=new ArrayList<>();
        btn_add_dsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_namedsach.setVisibility(View.VISIBLE);
                String dsach_name=et_namedsach.getText().toString().trim();
                dsachid=String.valueOf(random.nextInt(10000));
                String date= String.valueOf(java.time.LocalDate.now());
                if(!dsach_name.equals("")){
                    Dsach dsach=new Dsach(dsachid,dsach_name,username,date, new ArrayList<String>());
                    FirebaseDatabase.getInstance().getReference().child("DSxemsau").push().setValue(dsach);
                    et_namedsach.setText("");
                    Toast.makeText(Dsachtest.this,"Tạo danh sách và thêm thành công",Toast.LENGTH_LONG).show();
                }

                loadDsach();
            }
        });


    }

    private void anhXa() {
        btn_add_dsach=findViewById(R.id.btn_add_dsach);
        lv_dsach=findViewById(R.id.lv_dsach);
        et_namedsach=findViewById(R.id.et_namedsach);
    }
}