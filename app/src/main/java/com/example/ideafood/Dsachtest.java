package com.example.ideafood;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.ideafood.Module.Dsach;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class Dsachtest extends AppCompatActivity {
    ListView lv_dsach;
    Button btn_add_dsach;
    Random random=new Random();
    EditText et_namedsach;
    String username;
    ArrayList<Dsach> mListDsach;
    DsachAdapter dsachAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsachtest);
        anhXa();
        getUsername();
        setClickAdd();
        loadDsach();
    }

    private void loadDsach() {
        Adapter adapter=new DsachAdapter(mListDsach);
        lv_dsach.setAdapter((ListAdapter) adapter);
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
    }

    private void anhXa() {
        btn_add_dsach=findViewById(R.id.btn_add_dsach);
        lv_dsach=findViewById(R.id.lv_dsach);
        et_namedsach=findViewById(R.id.et_namedsach);
    }
}