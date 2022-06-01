package com.example.ideafood.startLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ideafood.Adapter.ViewPagerAdapter;
import com.example.ideafood.Homepage;
import com.example.ideafood.R;


public class Welcome extends AppCompatActivity {


    private TextView skip;
    private ViewPager viewPager;
    private RelativeLayout layoutBottom;
    private LinearLayout next;

    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

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
        next = findViewById(R.id.layout_next);

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
    }
}