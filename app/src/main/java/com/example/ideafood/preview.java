package com.example.ideafood;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toolbar;
import android.widget.VideoView;

public class preview extends AppCompatActivity {
    TextView tv_content1,tv_content2,tv_title,tv_categorypost_preview,tv_headerpost_preview;
    ImageView iv_img11,iv_img21;
    VideoView vv_video_preview;
    MediaController mc_preview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        anhXa();
        Bundle bundle=getIntent().getExtras();
        String title=bundle.getString("title");
        String content1=bundle.getString("content1");
        String content2=bundle.getString("content2");
        String img=bundle.getString("img");
        String img1=bundle.getString("img1");
        String video=bundle.getString("video");
        String category=bundle.getString("category");
        String header=bundle.getString("header");
        if(!video.equals("null")){
            Uri videoUri= Uri.parse(video);
            mc_preview=new MediaController(preview.this);
            vv_video_preview.setMediaController(mc_preview);
            vv_video_preview.setVideoURI(videoUri);
            vv_video_preview.start();
        }else{
            vv_video_preview.setVisibility(View.GONE);
        }
        if(!img1.equals("null")){
            Uri imgUri1= Uri.parse(img1);
            iv_img21.setImageURI(imgUri1);
        }else{
            iv_img21.setVisibility(View.GONE);
        }
        Uri imgUri= Uri.parse(img);
        tv_title.setText(title);
        tv_content1.setText(content1);
        if(content2=="null"){
            tv_content2.setVisibility(View.GONE);
        }else{
            tv_content2.setText(content2);
        }
        iv_img11.setImageURI(imgUri);
        tv_categorypost_preview.append(" "+category);
        tv_headerpost_preview.append(" "+header);


    }
    private void anhXa() {
        tv_title=findViewById(R.id.tv_title);
        tv_content1=findViewById(R.id.tv_content1);
        tv_content2=findViewById(R.id.tv_content2);
        iv_img11=findViewById(R.id.iv_img11);
        iv_img21=findViewById(R.id.iv_img21);
        vv_video_preview=findViewById(R.id.vv_video_preview);
        tv_categorypost_preview=findViewById(R.id.tv_categorypost_preview);
        tv_headerpost_preview=findViewById(R.id.tv_headerpost_preview);
    }
}
