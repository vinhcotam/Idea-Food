package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;

import com.example.ideafood.Adapter.ListRep_Adapter;
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
    ArrayList<Comment> fatherList;
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
        //thoát khỏi trạng thái trả lời bình luận của 1 ai đó
        TextView tv = findViewById(R.id.repwho);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText("Trả lời");
                fatherid = "-1";
                tv.setVisibility(View.INVISIBLE);
            }
        });
        Button nextpage = findViewById(R.id.nextPage);
        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                LoadComment();
            }
        });
        Button prevpage = findViewById(R.id.prevPage);
        prevpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page--;
                LoadComment();
            }
        });
        EditText enterPage = findViewById(R.id.enterPage);
        enterPage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    page = Integer.getInteger(enterPage.getText().toString());
                    LoadComment();
                }
                return false;
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
        Query Qget_comment = database.child("comments");
        Qget_comment.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()){
                    Comment cmt = item.getValue(Comment.class);
                    commentList.add(cmt);
                }
                fatherList = new ArrayList<>();
                repList = new ArrayList<>();
                for(int i=0;i<commentList.size();i++){
                    if(commentList.get(i).getFatherid().equals("-1")){
                        fatherList.add(commentList.get(i));
                    }
                    else {
                        repList.add(commentList.get(i));
                    }
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
        if((page-1)*5+index>=fatherList.size()){
            return;
        }
        tvname.setText(fatherList.get((page-1)*5+index).getUserid());
        tvdate.setText(fatherList.get((page-1)*5+index).getDate());
        tvcontent.setText(fatherList.get((page-1)*5+index).getContent());
        ArrayList<Comment> current_repList = new ArrayList<>();
        for(int i=0;i<repList.size();i++){
            if(repList.get(i).getFatherid().equals(fatherList.get((page-1)*5+index).getCommentid())){
                current_repList.add(repList.get(i));
            }
        }
        Rep_Adapter rep_adapter = new Rep_Adapter(current_repList);
        rcv.setAdapter(rep_adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Module_Chat.this);
        rcv.setLayoutManager(layoutManager);
        tvrep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.rep1:{
                        Rep((page-1)*5);
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
        TextView tv = findViewById(R.id.repwho);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fatherid = "-1";
                tv.setVisibility(View.INVISIBLE);
                button.setText("Bình luận");
            }
        });
        tv.setVisibility(View.VISIBLE);
        tv.setText("Bạn đang trả lời "+ fatherList.get((page-1)*5+index).getUserid() + "\nNhấn vào dòng chữ này để bình luận bài viết");
        fatherid = fatherList.get((page-1)*5+index).getCommentid();
    }
}