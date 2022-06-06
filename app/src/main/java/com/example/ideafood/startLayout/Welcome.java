package com.example.ideafood.startLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ideafood.Adapter.ViewPagerAdapter;
import com.example.ideafood.Homepage;
import com.example.ideafood.Login;
import com.example.ideafood.R;


public class Welcome extends AppCompatActivity {


    private TextView skip, back, next;
    private ViewPager viewPager;
    private ConstraintLayout layoutBottom;String username="", password="";

    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if(!FirstTime()){
            LocalAccount();
            Intent intent =new Intent(Welcome.this, Login.class);
            Bundle bundle = new Bundle();
            bundle.putString("username", username);
            bundle.putString("password", password);
            intent.putExtras(bundle);
            this.finish();
            startActivity(intent);
        }
        initUI();
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position ==2){
                    skip.setVisibility(View.GONE);
                    layoutBottom.setVisibility(View.GONE);
                    Button button = findViewById(R.id.start);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Welcome.this, Homepage.class);
                            Welcome.this.finish();
                            startActivity(intent);
                        }
                    });
                } else {
                    skip.setVisibility(View.VISIBLE);
                    layoutBottom.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initUI(){
        skip = findViewById(R.id.skip);
        viewPager = findViewById(R.id.view);
        layoutBottom = findViewById(R.id.layout_bottom);
        next = findViewById(R.id.welcome_next_content);
        back = findViewById(R.id.welcome_back_content);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem()<2){
                    viewPager.setCurrentItem(viewPager.getCurrentItem() +1);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem()>0){
                    viewPager.setCurrentItem(viewPager.getCurrentItem() -1);
                }
            }
        });
    }
    Boolean FirstTime(){
        SQLiteDatabase db = openOrCreateDatabase("FirstUse.db", MODE_PRIVATE, null);
        try {
            db.execSQL("create table if not exists FirstUse(first int unique)");
            db.execSQL("insert into FirstUse values(1)");
            db.execSQL("create table if not exists Account(keylocal text unique ,username text, password text)");
            db.execSQL("insert into Account values('localaccount', '', '')");
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    Boolean LocalAccount(){
        SQLiteDatabase db = openOrCreateDatabase("FirstUse.db", MODE_PRIVATE, null);
        try {
            String sql = "select * from Account where keylocal ='localaccount'";
            Cursor cursor = db.rawQuery(sql, null);
            if(cursor.moveToFirst()){
                username = cursor.getString(1);
                password = cursor.getString(2);
            }
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}