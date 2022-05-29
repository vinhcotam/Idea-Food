package com.example.ideafood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ideafood.classs.Comment;
import com.google.firebase.database.FirebaseDatabase;

public class BoxAddComment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_add_comment);
        bt = findViewById(R.id.addcomment_send);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et = findViewById(R.id.addcomment_entercontent);
                String content = et.getText().toString();
//                Comment cmt = new Comment(content);
//                FirebaseDatabase.getInstance().getReference().child("comments").push().setValue(cmt);
//                Toast.makeText(BoxAddComment.this, "Binh luan thanh cong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    EditText et;
    Button bt;

}