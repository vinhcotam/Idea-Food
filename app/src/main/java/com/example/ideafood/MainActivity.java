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

import com.example.ideafood.Module.Img;
import com.example.ideafood.Module.Posts;
import com.example.ideafood.Module.Tag;
import com.example.ideafood.Module.Video;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    VideoView vv_video;
    ImageView iv_img,iv_img1,iv_imgmain;
    int SELECT_PICTURE = 200;
    int SELECT_PICTURE1 = 220;
    int SELECT_PICTUREMAIN = 210;
    int SELECT_VIDEO_REQUEST=100;
    ArrayList content_post;
    Button btn_save,btn_preview;
    EditText et_namepost,et_namecontent1,et_namecontent2,et_categorypost,et_headerpost;
    MediaController mc;
    Button btn_videoupload,btn_imgload,btn_img1load,btn_imgmainload;
    Random random = new Random();
    final FirebaseDatabase database = FirebaseDatabase.getInstance("https://idea-food-cd7e7-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://idea-food-cd7e7.appspot.com");
    StorageReference storageRef,storageRef1,storageRef2,storageRef3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpost2);
        Intent intent=new Intent(MainActivity.this,DetailPost.class);
        startActivity(intent);
//        setContentView(R.layout.activity_testaddtag);
        anhXa();
        setOnClick();
    }

    private void setOnClick() {
        btn_imgmainload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_imgmain.setVisibility(View.VISIBLE);
                requestPermissions();
                imageMainChoose();
            }
        });
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
                String content1=et_namecontent1.getText().toString().trim();
                String content2=et_namecontent2.getText().toString().trim();
                String title=et_namepost.getText().toString().trim();
                String header=et_headerpost.getText().toString().trim();
                String category=et_categorypost.getText().toString().trim();
                String imgUri= String.valueOf(selectedImageUri);
                String img1Uri= String.valueOf(selectedImageUri1);
                String imgMainUri= String.valueOf(selectedImageMainUri);
                String videoUri= String.valueOf(selectedVideoUri);
                int idtag = random.nextInt(10000);
                String tagid= String.valueOf(idtag);
//                Tag tag=new Tag( tagid,  "đồ ăn nhanh");
//                FirebaseDatabase.getInstance().getReference().child("tags").push().setValue(tag);
                if(imgUri!=null||img1Uri!=null||videoUri!=null||content1!=null
                        ||content2!=null||title!=null||category!=null||header!=null||imgMainUri!=null){
                    Intent intent=new Intent(MainActivity.this,preview.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("category",category);
                    bundle.putString("header",header);
                    bundle.putString("title",title);
                    bundle.putString("content1",content1);
                    bundle.putString("content2",content2);
                    bundle.putString("img",imgUri);
                    bundle.putString("img1",img1Uri);
                    bundle.putString("video",videoUri);
                    bundle.putString("imgmain",imgMainUri);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this,"Vui lòng nhập đầu đủ thông tin",Toast.LENGTH_LONG).show();
                }


            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imgUri= String.valueOf(selectedImageUri);
                String img1Uri= String.valueOf(selectedImageUri1);
                String imgMainUri=String.valueOf(selectedImageMainUri);
                String videoUri= String.valueOf(selectedVideoUri);
                String content1=et_namecontent1.getText().toString().trim();
                String content2=et_namecontent2.getText().toString().trim();
                String title=et_namepost.getText().toString().trim();
                String category=et_categorypost.getText().toString().trim();
                String header=et_headerpost.getText().toString().trim();
                int idpost = random.nextInt(10000);
                int idvideo = random.nextInt(10000);
                String videoid= String.valueOf(idvideo);
                int idimg = random.nextInt(10000);
                String imgid= String.valueOf(idimg);
                int imgid1 = random.nextInt(10000);
                String date= String.valueOf(java.time.LocalDate.now());
                String postid= String.valueOf(idpost);
//                int imgname1=random.nextInt(1000);
//                int imgname2=random.nextInt(1000);
//                int videoname=random.nextInt(1000);

                if(imgUri==null||img1Uri!=null||videoUri!=null||content1!=null
                        ||content2!=null||title!=null||category!=null||header!=null||imgMainUri!=null){
                    content_post=new ArrayList();
                    content_post.add(content1);
                    content_post.add(content2);
                    storageRef = storage.getReference("img1/"+postid+"/"+postid);
                    storageRef.putFile(selectedImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                }
                            }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                        }
                    });
                    storageRef1 = storage.getReference("img2/"+postid+"/"+postid);
                    storageRef1.putFile(selectedImageUri1)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                }
                            }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                        }
                    });
                    storageRef2 = storage.getReference("video/"+postid+"/"+postid);
                    storageRef2.putFile(selectedVideoUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                }
                            }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                        }
                    });
                    storageRef3 = storage.getReference("imgMain/"+postid+"/"+postid);
                    storageRef3.putFile(selectedImageMainUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                }
                            }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                        }
                    });
//                    String date= String.valueOf(java.time.LocalDate.now());
//                    String postid= String.valueOf(idpost);
//                    Video video=new Video( videoid,  "videoname",  videoUri, postid);
//                    FirebaseDatabase.getInstance().getReference().child("videos").push().setValue(video);
//                    Img img=new Img( imgid,  imgUri,  "a",postid );
//                    FirebaseDatabase.getInstance().getReference().child("images").push().setValue(img);
                    boolean status=false;
                    Posts post= new Posts(postid,  date,  header,  title, "1",  category,  content_post,status);
                    FirebaseDatabase.getInstance().getReference().child("post").push().setValue(post);
                    et_categorypost.setText("");
                    et_headerpost.setText("");
                    et_namecontent1.setText("");
                    et_namepost.setText("");
                    et_namecontent2.setText("");
                    vv_video.setVideoURI(null);
                    iv_img.setImageURI(null);
                    iv_img1.setImageURI(null);
                    iv_imgmain.setImageURI(null);
                    Toast.makeText(MainActivity.this,"Bài đăng của bạn đang đợi được duyệt",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(MainActivity.this,"Vui lòng nhập đầu đủ thông tin",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void imageMainChoose() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTUREMAIN);
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
        et_categorypost=findViewById(R.id.et_categorypost);
        et_headerpost=findViewById(R.id.et_headerpost);
        mc=new MediaController(MainActivity.this);
        vv_video.setFocusable(true);
        vv_video.setMediaController(mc);
        btn_imgload=findViewById(R.id.btn_imgload);
        btn_img1load=findViewById(R.id.btn_img1load);
        btn_videoupload=findViewById(R.id.btn_videoupload);
        btn_imgmainload=findViewById(R.id.btn_imgmainload);
        iv_imgmain=findViewById(R.id.iv_imgmain);
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
                .setDeniedMessage("Nếu bạn từ chối cấp quyền,bạn k thể dùng chức năng này\n\nCấp lại quyền tại [Setting] > [Permission]")
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
    Uri selectedImageUri,selectedVideoUri,selectedImageUri1,selectedImageMainUri;
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
            }else if(requestCode==SELECT_PICTUREMAIN){
                selectedImageMainUri=data.getData();
                if(null!=selectedImageMainUri){
                    iv_imgmain.setImageURI(selectedImageMainUri);
                }
            }
        }


    }
}
