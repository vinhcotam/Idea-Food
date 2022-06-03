package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.ideafood.Adapter.ListView_Post_Adapter;
import com.example.ideafood.Module.Posts;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        getDatafromIntent();
        ConnectDB();
        SetEvent();
    }
    String category="";
    String username="";
    void getDatafromIntent(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        try {
            username = bundle.getString("username");
        }
        catch (Exception e){
            username="";
        }
        try {
            category = bundle.getString("category");
        }
        catch (Exception e){
            category="";
        }
    }
    void SetEvent(){
        mDrawerLayout = findViewById(R.id.drawerlayout);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        NavigationView navigationView = findViewById(R.id.homepage_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_gohome){
                    Intent intent = Homepage.this.getIntent();
                    Homepage.this.finish();
                    startActivity(intent);
                    return true;
                }
                if(item.getItemId()==R.id.nav_login){
                    Intent intent = new Intent(Homepage.this, Login.class);
                    startActivity(intent);
                    return true;
                }
                if(item.getGroupId()==R.id.category){
                    GetPost(item.getTitle().toString(), 2);
                    return  true;

                }
                if(item.getItemId()==R.id.createapost){
                    Intent intent = new Intent(Homepage.this, createpost2.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    return  true;
                }
                return false;
            }
        });

        EditText editText = findViewById(R.id.ET_SearchPost);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && i == KeyEvent.KEYCODE_ENTER) {
                    String tukhoa = editText.getText().toString();
                    GetPost(tukhoa, 1);
                    return true;
                }
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    DrawerLayout mDrawerLayout;
    ArrayList<Posts> postList;
    ArrayList<Posts> postListFind; //dùng khi tìm kiếm
    DatabaseReference database;
    void ConnectDB(){
        database = FirebaseDatabase.getInstance().getReference();
        GetPost("", 0);
    }
    //FIND_MODE =1: tìm theo header, 2: tìm theo category, 0: hiển thị hết
    void GetPost(String keyvalue, int FIND_MODE){
        postList = new ArrayList<Posts>();
        postListFind = new ArrayList<Posts>();
        Query allPost = database.child("post").orderByChild("status").equalTo(true);
        allPost.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()){
                    Posts p = item.getValue(Posts.class);
                    postList.add(p);
                }
                if(keyvalue.trim().equals("") || FIND_MODE==0){
                    postListFind = postList;
                }
                else if(FIND_MODE ==1){
                    for(int i=0;i<postList.size();i++){
                        if(postList.get(i).getHeader().trim().contains(keyvalue.trim())){
                            postListFind.add(postList.get(i));
                        }
                    }
                    TextView tv = findViewById(R.id.noidungtimkiem);
                    tv.setText("Có "+ postListFind.size() +" bài viết liên quan tới từ khóa \""+keyvalue+"\"");
                }
                else if(FIND_MODE ==2){

                    for(int i=0;i<postList.size();i++){
                        if(postList.get(i).getCategory().trim().contains(keyvalue.trim())){
                            postListFind.add(postList.get(i));
                        }
                    }
                    TextView tv = findViewById(R.id.noidungtimkiem);
                    tv.setText("Có "+ postListFind.size() +" bài viết  thuộc loại \""+keyvalue+"\"");
                }
                ListView lv = findViewById(R.id.homepage_lv_post);
                Adapter adapter = new ListView_Post_Adapter(postListFind);
                lv.setAdapter((ListAdapter) adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(Homepage.this, DetailPost.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("category", postListFind.get(position).getCategory());
                        bundle.putString("username", username);
                        bundle.putString("postid", postListFind.get(position).getPostid());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}