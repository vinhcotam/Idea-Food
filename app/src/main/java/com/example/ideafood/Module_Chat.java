package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.ideafood.Adapter.Rep_Adapter;
import com.example.ideafood.classs.Comment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class Module_Chat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_chat);
        ConnectDB();
        LoadComment();
        SetEvent();
    }
    //Obj
    TextView textView;
    Button button;
    EditText editText;

    //Values
    String userid = "đây là user ảo";
    String postid = "đây là post mẫu";
    String fatherid="-1";
    String content="";
    String date="";
    String commentid="";
    ArrayList<Comment> repList;
    int page = 1;
    ArrayList<Comment> commentList;
    DatabaseReference database;
    //tạo các sự kiện
    void SetEvent(){
        textView = findViewById(R.id.titlecommentpost);
        button = findViewById(R.id.button_sendnewcomment);
        editText = findViewById(R.id.enternewcomment);
        //click gửi bình luận
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendComment();
                LoadComment();
            }
        });

    }
    //gửi bình luận
    void SendComment(){
        content = editText.getText().toString();
        date = java.time.LocalDate.now().toString();
        commentid = Integer.toString((new Random()).nextInt());
        Comment cmt = new Comment(commentid, content, date, fatherid, postid, userid);
        FirebaseDatabase.getInstance().getReference().child("comments").push().setValue(cmt);
        Toast.makeText(this, "Đã bình luận", Toast.LENGTH_SHORT).show();
    }
    void ConnectDB(){
        database = FirebaseDatabase.getInstance().getReference();
    }
    //tải các comment trong post
    void LoadComment(){
        commentList = new ArrayList<>();
        Query Qget_comment = database.child("comments").orderByChild("fatherid").equalTo("-1");
        Qget_comment.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()){
                    Comment cmt = item.getValue(Comment.class);
                    commentList.add(cmt);
                }
                DisplayComment();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void DisplayComment(){
        textView.setText("Bình luận("+commentList.size()+" bình luận)");
        setEvent_5comment();
    }
    TextView tvname, tvdate, tvcontent, tvrep;
    RecyclerView rcv;
    void setEvent_5comment(){
        tvname = findViewById(R.id.username1);
        tvdate = findViewById(R.id.time1);
        tvcontent = findViewById(R.id.content1);
        tvrep = findViewById(R.id.rep1);
        rcv = findViewById(R.id.rcv1);
        Display_5comment(0);
        tvname = findViewById(R.id.username2);
        tvdate = findViewById(R.id.time2);
        tvcontent = findViewById(R.id.content2);
        tvrep = findViewById(R.id.rep2);
        rcv = findViewById(R.id.rcv2);
        Display_5comment(1);
        tvname = findViewById(R.id.username3);
        tvdate = findViewById(R.id.time3);
        tvcontent = findViewById(R.id.content3);
        tvrep = findViewById(R.id.rep3);
        rcv = findViewById(R.id.rcv3);
        Display_5comment(2);
        tvname = findViewById(R.id.username4);
        tvdate = findViewById(R.id.time4);
        tvcontent = findViewById(R.id.content4);
        tvrep = findViewById(R.id.rep4);
        rcv = findViewById(R.id.rcv4);
        Display_5comment(3);
        tvname = findViewById(R.id.username5);
        tvdate = findViewById(R.id.time5);
        tvcontent = findViewById(R.id.content5);
        tvrep = findViewById(R.id.rep5);
        rcv = findViewById(R.id.rcv5);
        Display_5comment(4);
    }
    void Display_5comment(int index){
        tvname.setText(commentList.get((page-1)*5+index).getUserid());
        tvdate.setText(commentList.get((page-1)*5+index).getDate());
        tvcontent.setText(commentList.get((page-1)*5+index).getContent());
        Query allrep = database.child("comments").orderByChild("fatherid").equalTo(commentList.get((page-1)*5+index).getCommentid());
        allrep.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                repList = new ArrayList<>();
                for(DataSnapshot item: snapshot.getChildren()){
                    Comment cmt = item.getValue(Comment.class);
                    repList.add(cmt);
                }
                Rep_Adapter chat_adapter = new Rep_Adapter(Module_Chat.this,repList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(Module_Chat.this);
                rcv.setLayoutManager(layoutManager);
                rcv.setAdapter(chat_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        tvrep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.rep1:{
                        Rep((page-1)*5+0);
                        break;
                    }
                    case R.id.rep2:{
                        Rep((page-1)*5+1);
                        break;
                    }
                    case R.id.rep3:{
                        Rep((page-1)*5+2);
                        break;
                    }
                    case R.id.rep4:{
                        Rep((page-1)*5+3);
                        break;
                    }
                    case R.id.rep5:{
                        Rep((page-1)*5+4);
                        break;
                    }
                }
            }
        });
    }
    void Rep(int index){
        Toast.makeText(this, "Bạn đang trả lời" + index, Toast.LENGTH_SHORT).show();
    }
}