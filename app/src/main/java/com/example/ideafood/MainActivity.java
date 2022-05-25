package com.example.ideafood;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    VideoView vv_video;
    ImageView iv_img,iv_img1;
    int SELECT_PICTURE = 200;
    int SELECT_PICTURE1 = 220;
    int SELECT_VIDEO_REQUEST=100;
    Button btn_save,btn_preview;
    EditText et_namepost,et_namecontent1,et_namecontent2;
    MediaController mc;
    Button btn_videoupload,btn_imgload,btn_img1load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpost2);
        anhXa();
        setOnClick();

    }

    private void setOnClick() {
        btn_imgload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_img.setVisibility(View.VISIBLE);
                requestPermissions();
                imageChoose();
            }
        });
        btn_img1load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_img1.setVisibility(View.VISIBLE);
                requestPermissions();
                imageChoose1();
            }
        });
        btn_videoupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions();
                vv_video.setVisibility(View.VISIBLE);
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
                startActivityForResult(intent,SELECT_VIDEO_REQUEST);
            }
        });
        btn_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,preview.class);
                Bundle bundle=new Bundle();
                String img= String.valueOf(selectedImageUri);
                String img1= String.valueOf(selectedImageUri1);
                String content1=et_namecontent1.getText().toString().trim();
                String content2=et_namecontent2.getText().toString().trim();
                String title=et_namepost.getText().toString().trim();
                String video= String.valueOf(selectedVideoUri);
                bundle.putString("title",title);
                bundle.putString("content1",content1);
                bundle.putString("content2",content2);
                bundle.putString("img",img);
                bundle.putString("img1",img1);
                bundle.putString("video",video);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }

    private void anhXa() {
        vv_video=findViewById(R.id.vv_video);
        iv_img=findViewById(R.id.iv_img);
        iv_img1=findViewById(R.id.iv_img1);
        btn_preview=findViewById(R.id.btn_preview);
        btn_save=findViewById(R.id.btn_save);
        et_namecontent1=findViewById(R.id.et_namecontent1);
        et_namecontent2=findViewById(R.id.et_namecontent2);
        et_namepost=findViewById(R.id.et_namepost);
        mc=new MediaController(MainActivity.this);
        vv_video.setMediaController(mc);
        btn_imgload=findViewById(R.id.btn_imgload);
        btn_img1load=findViewById(R.id.btn_img1load);
        btn_videoupload=findViewById(R.id.btn_videoupload);
    }

    private void requestPermissions() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MainActivity.this, "Yêu cầu thành công", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Yêu cầu bị từ chối\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }


    private void imageChoose1() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE1);
    }

//    private void videoChoose() {
//        Intent i = new Intent();
//        i.setType("video/*");
//        i.setAction(Intent.ACTION_GET_CONTENT);
//
//        // pass the constant to compare it
//        // with the returned requestCode
//        startActivityForResult(Intent.createChooser(i, "Select Video"), SELECT_VIDEO_REQUEST);
//    }
    Uri selectedImageUri,selectedVideoUri,selectedImageUri1;
    private void imageChoose() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    iv_img.setImageURI(selectedImageUri);
                }
                selectedImageUri1=data.getData();
                if (null != selectedImageUri1) {
                    // update the preview image in the layout
                    iv_img.setImageURI(selectedImageUri1);
                }
            }else if (requestCode == SELECT_PICTURE1) {
                // Get the url of the image from data
                selectedImageUri1 = data.getData();
                if (null != selectedImageUri1) {
                    // update the preview image in the layout
                    iv_img1.setImageURI(selectedImageUri1);
                }
                selectedImageUri1=data.getData();
                if (null != selectedImageUri1) {
                    // update the preview image in the layout
                    iv_img1.setImageURI(selectedImageUri1);
                }
            }
            else if(requestCode == SELECT_VIDEO_REQUEST )
            {
                selectedVideoUri= data.getData();
                if(null!=selectedVideoUri){
                    vv_video.setVideoURI(selectedVideoUri);
                    vv_video.requestFocus();
                    vv_video.start();
                }
            }
        }


    }
}
