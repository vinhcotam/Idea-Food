package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class Dsachtest extends AppCompatActivity {
    ListView lv_dsach;
    Button btn_add_dsach;
    Random random=new Random();
    EditText et_namedsach;
    RecyclerView rcv_ds;
    String username;
    ArrayList<Dsach> mListDS;
    DsachsAdapter dsachAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsachtest);
        anhXa();
        ConnectDB();
        getUsername();
        setClickAdd();
        loadDsach();
    }
    DatabaseReference database;
    private void ConnectDB() {
        database = FirebaseDatabase.getInstance().getReference();
    }

//    private void loadDsach() {
//        mListDS=new ArrayList<>();
//    }

    private void loadDsach() {
        mListDS=new ArrayList<>();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Dsachtest.this,RecyclerView.VERTICAL,false);
        rcv_ds.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(Dsachtest.this,DividerItemDecoration.HORIZONTAL);
        rcv_ds.addItemDecoration(dividerItemDecoration);
        Query queryDS=database.child("DSxemsau");
        queryDS.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()){
                    Dsach ds=item.getValue(Dsach.class);
                    if(ds.getUsername().equals("q")){
                        mListDS.add(ds);
                    }
//                    mListDS.add(ds);

            }
                dsachAdapter=new DsachsAdapter(mListDS);
                rcv_ds.setAdapter(dsachAdapter);
//                Adapter adapter=new DsachAdapter(mListDS);
//                lv_dsach.setAdapter((ListAdapter) adapter);
            };
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        getUsername();
    }

    private void getUsername() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
         username=bundle.getString("username");
    }

    private void setClickAdd() {

        btn_add_dsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_namedsach.setVisibility(View.VISIBLE);
                String dsach_name=et_namedsach.getText().toString().trim();
                String dsachid=String.valueOf(random.nextInt(10000));
                String date= String.valueOf(java.time.LocalDate.now());
                if(!dsach_name.equals("")){
                    Dsach dsach=new Dsach(dsachid,dsach_name,username,date,null);
                    FirebaseDatabase.getInstance().getReference().child("DSxemsau").push().setValue(dsach);
                    et_namedsach.setText("");
                    Toast.makeText(Dsachtest.this,"Tạo danh sách thành công",Toast.LENGTH_LONG).show();
                }


            }
        });
        loadDsach();
    }

    private void anhXa() {
        btn_add_dsach=findViewById(R.id.btn_add_dsach);
//        lv_dsach=findViewById(R.id.lv_dsach);
        et_namedsach=findViewById(R.id.et_namedsach);
        rcv_ds=findViewById(R.id.rcv_ds);
    }
}