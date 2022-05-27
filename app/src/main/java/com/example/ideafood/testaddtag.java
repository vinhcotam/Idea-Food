package com.example.ideafood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ideafood.Module.Tag;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class testaddtag extends AppCompatActivity {
    EditText et_nhaptl;
    Button btn_add;
    Random random=new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testaddtag);
        btn_add=findViewById(R.id.btn_add);
        et_nhaptl=findViewById(R.id.et_nhaptl);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idtag = random.nextInt(10000);
                String tagid= String.valueOf(idtag);
                String category=et_nhaptl.getText().toString().trim();
                Tag tag=new Tag( tagid,  category);
                FirebaseDatabase.getInstance().getReference().child("tags").push().setValue(tag);
            }
        });
    }
}