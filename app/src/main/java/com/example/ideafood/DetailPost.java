package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.ideafood.Adapter.PostsAdapter;
import com.example.ideafood.Adapter.Rep_Adapter;
import com.example.ideafood.Module.Posts;
import com.example.ideafood.Module.Comment;
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
import java.util.Random;

public class DetailPost extends AppCompatActivity {
    ArrayList<Posts> mListPost;
    TextView tv_headerdp,tv_user_post,tv_date_postdp,tv_categorydp,tv_content1dp,tv_content2dp,tv_videohd;
    ImageView iv_imgdp,iv_img1dp;
    VideoView vv_videodp;
    MediaController mc;
    RecyclerView rcv_bvtt;
    Button add_dsach;
    PostsAdapter postsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        getDatafromIntent();
        anhXa();
        ConnectDB();
        loadDetailPost();
        loadPostTT();
        RealtimeComment a = new RealtimeComment();
        a.start();
        SetEvent();
    }
    private void loadPostTT() {
        mListPost=new ArrayList<>();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DetailPost.this,RecyclerView.HORIZONTAL,false);
        rcv_bvtt.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(DetailPost.this,DividerItemDecoration.HORIZONTAL);
        rcv_bvtt.addItemDecoration(dividerItemDecoration);
        Query postTT=database.child("post").orderByChild("category").equalTo(category);
        postTT.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()){
                    Posts post=item.getValue(Posts.class);
                    if(!post.getPostid().equals(postid) &&post.isStatus()==true){
                        mListPost.add(post);
                    }
                }
                postsAdapter=new PostsAdapter(DetailPost.this,mListPost);
                rcv_bvtt.setAdapter(postsAdapter);
                getDatafromIntent();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Obj
    TextView textView;
    Button button;
    EditText editText;
    TextView tvname, tvdate, tvcontent, tvrep, tvhiderep;
    LinearLayout layoutcmt;
    RecyclerView rcv;

    //Values
    String username = "????y l?? user ???o";
    String postid = "????y l?? post m???u";
    String fatherid="-1";
    String content="";
    String date="";
    String commentid="";
    String category;
    ArrayList<Comment> fatherList;
    ArrayList<Comment> repList;
    int page = 1;
    int maxPage = 1;
    ArrayList<Comment> commentList;


    void getDatafromIntent(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        try{
            postid = bundle.getString("postid");
        }
        catch (Exception e){

        }
        try {
            username = bundle.getString("username");
        }
        catch (Exception e){
            username = "kh??ch";
        }
        try{
            category=bundle.getString("category");
        }catch (Exception e){

        }
    }

    private void anhXa() {
        tv_videohd=findViewById(R.id.tv_videohd);
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
        add_dsach=findViewById(R.id.add_dsach);

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
                        if(post.getPostid().equals(postid)){
                                tv_headerdp.setText(post.getHeader());
                                tv_user_post.setText(post.getUsername());
                                tv_date_postdp.append(" "+post.getDate());
                                tv_categorydp.setText(post.getCategory());
                                for(int i=0;i<post.getContent_post().size();i++){
                                    if(post.getContent_post().get(0)==null){
                                        tv_content1dp.setVisibility(View.GONE);
                                    }else{
                                        tv_content1dp.setText(post.getContent_post().get(0));
                                    }
                                    if(post.getContent_post().get(1)==null){
                                        tv_content2dp.setVisibility(View.GONE);
                                    }else{
                                        tv_content2dp.setText(post.getContent_post().get(1));
                                    }

                                }
                        }
                    }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        loadVideoImg();

    }
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://idea-food-cd7e7.appspot.com");
    StorageReference storageRef;
    private void loadVideoImg() {
        iv_imgdp.setVisibility(View.GONE);
        storageRef=storage.getReference().child("img1/"+postid+"/"+postid);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
            if(uri!=null){
                String urlImage = uri.toString();
                iv_imgdp.setVisibility(View.VISIBLE);
                Glide.with(DetailPost.this).load(urlImage).into(iv_imgdp);
            }
            }
        });
        iv_img1dp.setVisibility(View.GONE);
        storageRef=storage.getReference().child("img2/"+postid+"/"+postid);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
//                iv_imgdp.setImageURI(uri);
                if (uri!=null){
                    String urlImage = uri.toString();
                    iv_img1dp.setVisibility(View.VISIBLE);
                    Glide.with(DetailPost.this).load(urlImage).into(iv_img1dp);
                }



            }
        });
        vv_videodp.setVisibility(View.GONE);
        tv_videohd.setVisibility(View.GONE);
        storageRef=storage.getReference().child("video/"+postid+"/"+postid);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if(uri!=null){
                    tv_videohd.setVisibility(View.VISIBLE);
                    vv_videodp.setMediaController(new MediaController(DetailPost.this));
                    vv_videodp.setVisibility(View.VISIBLE);
                    vv_videodp.setVideoURI(uri);
                }else{

                }


//                vv_videodp.start();
            }
        });
    }


    DatabaseReference database;
    private void ConnectDB() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    //t???o c??c s??? ki???n
    void SetEvent(){
        textView = findViewById(R.id.titlecommentpost);
        button = findViewById(R.id.button_sendnewcomment);
        editText = findViewById(R.id.enternewcomment);
        //click g???i b??nh lu???n
        add_dsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!username.equals("")){
                    Intent intent=new Intent(DetailPost.this,Dsachtest.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("username",username);
                    bundle.putString("postid",postid);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(DetailPost.this);
                        builder1.setMessage("B???n c???n ????ng nh???p ????? s??? d???ng ch???c n??ng n??y");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "????ng nh???p",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        Intent intent = new Intent(DetailPost.this, Login.class);
                                        startActivity(intent);
                                    }
                                });

                        builder1.setNegativeButton(
                                "Th??i",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                }

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.equals("")){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(DetailPost.this);
                    builder1.setMessage("B???n c???n ????ng nh???p ????? s??? d???ng ch???c n??ng n??y");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "????ng nh???p",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    Intent intent = new Intent(DetailPost.this, Login.class);
                                    startActivity(intent);
                                }
                            });

                    builder1.setNegativeButton(
                            "Th??i",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else {
                    SendComment();
                    LoadComment();
                }
            }
        });
        //tho??t kh???i tr???ng th??i tr??? l???i b??nh lu???n c???a 1 ai ????
        TextView tv = findViewById(R.id.repwho);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText("Tr??? l???i");
                fatherid = "-1";
                tv.setVisibility(View.INVISIBLE);
            }
        });
        Button nextpage = findViewById(R.id.nextPage);
        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                checkPage();
                DisplayComment();
            }
        });
        Button prevpage = findViewById(R.id.prevPage);
        prevpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page--;
                checkPage();
                DisplayComment();
            }
        });
        EditText enterPage = findViewById(R.id.enterPage);
        enterPage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    page = Integer.parseInt(enterPage.getText().toString());
                    checkPage();
                    DisplayComment();
                }
                return false;
            }
        });
    }
    //g???i b??nh lu???n(fatherid=-1) v?? tr??? l???i(fatherid!=-1)
    void SendComment(){
        content = editText.getText().toString();
        if(content.trim().equals("")){
            Toast.makeText(this, "B???n ch??a nh???p n???i dung", Toast.LENGTH_LONG).show();
            return;
        }
        date = java.time.LocalDate.now().toString();
        commentid = Integer.toString((new Random()).nextInt());
        Comment cmt = new Comment(commentid, content, date, fatherid, postid, username);
        FirebaseDatabase.getInstance().getReference().child("comments").push().setValue(cmt);
        editText.setText("");
        Toast.makeText(this, "???? b??nh lu???n", Toast.LENGTH_SHORT).show();
    }
    //ki???m tra xem trang hi???n t???i ???? l?? min ho???c max ch??a
    void checkPage(){
        //gi???i h???n ph???m vi
        if(page<1){
            page=1;
        }
        if(page>maxPage){
            page=maxPage;
        }
        //hi???n th??? trang hi???n t???i
        EditText et = findViewById(R.id.enterPage);
        et.setText(Integer.toString(page));
        Button btnext = findViewById(R.id.nextPage);
        Button btprev = findViewById(R.id.prevPage);
        if(page==1){
            btprev.setEnabled(false);
        }
        else{
            btprev.setEnabled(true);
        }
        if(page==maxPage){
            btnext.setEnabled(false);
        }
        else{
            btnext.setEnabled(true);
        }
    }
    //t???i c??c comment trong post v?? ????a v??o m???ng
    void LoadComment(){
        commentList = new ArrayList<>();
        Query Qget_comment = database.child("comments");
        Qget_comment.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()){
                    Comment cmt = item.getValue(Comment.class);
                    if(cmt.getPostid().equals(postid)){

                        commentList.add(cmt);
                    }
                }
                //ph??n lo???i comment
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
                maxPage = fatherList.size()/5+1;
                if(maxPage%5==0)
                    maxPage--;
                checkPage();
                DisplayComment();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //hi???n th??? comment ra m??n h??nh
    void DisplayComment(){
        TextView tv = findViewById(R.id.maxPage);
        tv.setText("/"+maxPage+" trang");
        textView.setText("B??nh lu???n("+fatherList.size()+" b??nh lu???n, "+repList.size()+" tr??? l???i)");
        setEvent_5comment();
    }
    //tham chi???u t???i c??c ?????i t?????ng v?? hi???n th??? t???ng ph???n
    void setEvent_5comment(){
        tvname = findViewById(R.id.username1);
        tvdate = findViewById(R.id.time1);
        tvcontent = findViewById(R.id.content1);
        tvrep = findViewById(R.id.rep1);
        rcv = findViewById(R.id.rcv1);
        layoutcmt = findViewById(R.id.layoutcomment1);
        tvhiderep = findViewById(R.id.hiderep1);
        Display_5comment(0);
        tvname = findViewById(R.id.username2);
        tvdate = findViewById(R.id.time2);
        tvcontent = findViewById(R.id.content2);
        tvrep = findViewById(R.id.rep2);
        rcv = findViewById(R.id.rcv2);
        layoutcmt = findViewById(R.id.layoutcomment2);
        tvhiderep = findViewById(R.id.hiderep2);
        Display_5comment(1);
        tvname = findViewById(R.id.username3);
        tvdate = findViewById(R.id.time3);
        tvcontent = findViewById(R.id.content3);
        tvrep = findViewById(R.id.rep3);
        rcv = findViewById(R.id.rcv3);
        layoutcmt = findViewById(R.id.layoutcomment3);
        tvhiderep = findViewById(R.id.hiderep3);
        Display_5comment(2);
        tvname = findViewById(R.id.username4);
        tvdate = findViewById(R.id.time4);
        tvcontent = findViewById(R.id.content4);
        tvrep = findViewById(R.id.rep4);
        rcv = findViewById(R.id.rcv4);
        layoutcmt = findViewById(R.id.layoutcomment4);
        tvhiderep = findViewById(R.id.hiderep4);
        Display_5comment(3);
        tvname = findViewById(R.id.username5);
        tvdate = findViewById(R.id.time5);
        tvcontent = findViewById(R.id.content5);
        tvrep = findViewById(R.id.rep5);
        rcv = findViewById(R.id.rcv5);
        layoutcmt = findViewById(R.id.layoutcomment5);
        tvhiderep = findViewById(R.id.hiderep5);
        Display_5comment(4);
    }
    //hi???n th??? fathercomment v?? repcomment tr??n c??c ?????i t?????ng ??ang tham chi???u
    void Display_5comment(int index){
        if((page-1)*5+index>=fatherList.size()){
            layoutcmt.setVisibility(View.GONE);
            return;
        }
        layoutcmt.setVisibility(View.VISIBLE);
        tvname.setText(fatherList.get((page-1)*5+index).getUsername());
        tvdate.setText(fatherList.get((page-1)*5+index).getDate());
        tvcontent.setText(fatherList.get((page-1)*5+index).getContent());
        ArrayList<Comment> current_repList = new ArrayList<>();
        for(int i=0;i<repList.size();i++){
            if(repList.get(i).getFatherid().equals(fatherList.get((page-1)*5+index).getCommentid())){
                current_repList.add(repList.get(i));
            }
        }
        if(current_repList.size()!=0){
            tvhiderep.setVisibility(View.VISIBLE);
        }
        else{
            tvhiderep.setVisibility(View.GONE);
        }
        Rep_Adapter rep_adapter = new Rep_Adapter(current_repList);
        rcv.setAdapter(rep_adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DetailPost.this);
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
        tvhiderep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.hiderep1:{
                        Hide(findViewById(R.id.hiderep1), findViewById(R.id.rcv1));
                        break;
                    }
                    case R.id.hiderep2:{
                        Hide(findViewById(R.id.hiderep2), findViewById(R.id.rcv2));
                        break;
                    }
                    case R.id.hiderep3:{
                        Hide(findViewById(R.id.hiderep3), findViewById(R.id.rcv3));
                        break;
                    }
                    case R.id.hiderep4:{
                        Hide(findViewById(R.id.hiderep4), findViewById(R.id.rcv4));
                        break;
                    }
                    case R.id.hiderep5:{
                        Hide(findViewById(R.id.hiderep5), findViewById(R.id.rcv5));
                        break;
                    }
                }
            }
        });
    }
    //t??nh n??ng tr??? l???i fathercomment
    void Rep(int index){
        TextView tv = findViewById(R.id.repwho);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fatherid = "-1";
                tv.setVisibility(View.INVISIBLE);
                button.setText("B??nh lu???n");
            }
        });
        tv.setVisibility(View.VISIBLE);
        tv.setText("B???n ??ang tr??? l???i "+ fatherList.get((page-1)*5+index).getUsername() + "\nNh???n v??o d??ng ch??? n??y ????? b??nh lu???n b??i vi???t");
        fatherid = fatherList.get((page-1)*5+index).getCommentid();
    }
    //t??nh n??ng ???n ph???n repcomment
    void Hide(TextView tvhide, RecyclerView current){
        if(current.getVisibility()==View.VISIBLE){
            current.setVisibility(View.GONE);
            tvhide.setText("hi???n tr??? l???i");
        }
        else{
            current.setVisibility(View.VISIBLE);
            tvhide.setText("???n tr??? l???i");
        }
    }
    class RealtimeComment implements Runnable{
        Thread thread;
        @Override
        public void run() {
            try {
                while (true){
                    thread.sleep(1000);
                    LoadComment();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        void start(){
            if(thread == null){
                thread = new Thread(this);
                thread.start();
            }
        }
    }
}