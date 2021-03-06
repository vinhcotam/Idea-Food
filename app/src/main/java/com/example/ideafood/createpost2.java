package com.example.ideafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.ideafood.Module.Posts;
import com.example.ideafood.Module.Tag;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class createpost2 extends AppCompatActivity {
    String username;
    String category;
    VideoView vv_video;
    ImageView iv_img,iv_img1,iv_imgmain;
    Spinner id_spinner1;
    int SELECT_PICTURE = 200;
    int SELECT_PICTURE1 = 220;
    int SELECT_PICTUREMAIN = 210;
    int SELECT_VIDEO_REQUEST=100;
    ArrayList content_post;
    ArrayList<String> spinnerls;
    TextView tv_category;
    ArrayAdapter<String> adapter;
    Button btn_save,btn_preview;
    EditText et_namepost,et_namecontent1,et_namecontent2,et_headerpost;
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
        anhXa();
        tv_category=findViewById(R.id.tv_category);
        id_spinner1=findViewById(R.id.id_spinner1);
        spinnerls=new ArrayList<>();
        getDatafromIntent();
        showCategory();
        setOnClick();
    }
    void getDatafromIntent(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        try {
            username = bundle.getString("username");
        }
        catch (Exception e){
            username="";
        }
    }
    private void showCategory() {
        ref.child("tags").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()){
                    Tag tag=item.getValue(Tag.class);
                    spinnerls.add(tag.getCategory());
                }
                ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,spinnerls);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                id_spinner1.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                String category=id_spinner1.getSelectedItem().toString();
                String imgUri= String.valueOf(selectedImageUri);
                String img1Uri= String.valueOf(selectedImageUri1);
                String imgMainUri= String.valueOf(selectedImageMainUri);
                String videoUri= String.valueOf(selectedVideoUri);
                int idtag = random.nextInt(10000);
                String tagid= String.valueOf(idtag);
//                Tag tag=new Tag( tagid,  "????? ??n nhanh");
//                FirebaseDatabase.getInstance().getReference().child("tags").push().setValue(tag);
                if(imgUri!=null||content1!=null ||title!=null||category!=null||header!=null||imgMainUri!=null){


                    Intent intent=new Intent(createpost2.this,preview.class);
                    Bundle bundle=new Bundle();
                    if(img1Uri!=null){
                        bundle.putString("img1",img1Uri);
                    }
                    if(videoUri!=null){
                        bundle.putString("video",videoUri);
                    }
                    bundle.putString("category",category);
                    bundle.putString("header",header);
                    bundle.putString("title",title);
                    bundle.putString("content1",content1);
                    bundle.putString("content2",content2);
                    bundle.putString("img",imgUri);
                    bundle.putString("imgmain",imgMainUri);
                    bundle.putString("username", username);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    Toast.makeText(createpost2.this,"Vui l??ng nh???p ?????u ????? th??ng tin",Toast.LENGTH_LONG).show();
                }


            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category=id_spinner1.getSelectedItem().toString();
                String imgUri= String.valueOf(selectedImageUri);
                String img1Uri= String.valueOf(selectedImageUri1);
                String imgMainUri=String.valueOf(selectedImageMainUri);
                String videoUri= String.valueOf(selectedVideoUri);
                String content1=et_namecontent1.getText().toString().trim();
                String content2=et_namecontent2.getText().toString().trim();
                String title=et_namepost.getText().toString().trim();
                String header=et_headerpost.getText().toString().trim();
                int idpost = random.nextInt(10000);
                int idvideo = random.nextInt(10000);
                String videoid= String.valueOf(idvideo);
                int idimg = random.nextInt(10000);
                String imgid= String.valueOf(idimg);
                int imgid1 = random.nextInt(10000);
                String date= String.valueOf(java.time.LocalDate.now());
                String postid= String.valueOf(idpost);
                content_post=new ArrayList();


                if(content1!=null ||title!=null||category!=null||header!=null){
                    content_post.add(content1);
                    content_post.add(content2);
                    if(videoUri!="null"){
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
                    }else{
                        Log.d("test",videoUri);
                    }
                    if(imgUri!="null"){
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
                    }
                    if(img1Uri!="null"){
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
                    }
                    if(imgMainUri!="null"){
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
                    }
                    boolean status=false;
                    Posts post= new Posts(postid,  date,  header,  title, username,  category,  content_post,status);
                    FirebaseDatabase.getInstance().getReference().child("post").push().setValue(post);
                    et_headerpost.setText("");
                    et_namecontent1.setText("");
                    et_namepost.setText("");
                    et_namecontent2.setText("");
                    vv_video.setVideoURI(null);
                    iv_img.setImageURI(null);
                    iv_img1.setImageURI(null);
                    iv_imgmain.setImageURI(null);
                    Toast.makeText(createpost2.this,"B??i ????ng c???a b???n ??ang ?????i ???????c duy???t",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(createpost2.this,"Vui l??ng nh???p ?????u ????? th??ng tin",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void imageMainChoose() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
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
        et_headerpost=findViewById(R.id.et_headerpost);
        mc=new MediaController(createpost2.this);
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
                Toast.makeText(createpost2.this, "Y??u c???u th??nh c??ng", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(createpost2.this, "Y??u c???u b??? t??? ch???i\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("N???u b???n t??? ch???i c???p quy???n,b???n k th??? d??ng ch???c n??ng n??y\n\nC???p l???i quy???n t???i [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }


    private void imageChoose1() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE1);
    }

    Uri selectedImageUri=null,selectedVideoUri=null,selectedImageUri1=null,selectedImageMainUri=null;
    private void imageChoose() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
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
            }else if (requestCode == SELECT_PICTURE1) {
                // Get the url of the image from data
                selectedImageUri1 = data.getData();
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
//
//        }i
//        if(resultCode == RESULT_OK &&requestCode == SELECT_PICTURE){
//            selectedImageUri = data.getData();
//            iv_img.setImageURI(selectedImageUri);
//        }else{
//            selectedImageUri=null;
////            iv_img.setVisibility(View.GONE);
//
//        }
//        if(resultCode == RESULT_OK &&requestCode == SELECT_PICTURE1){
//            selectedImageUri1 = data.getData();
//            iv_img.setImageURI(selectedImageUri1);
//        }else{
//            selectedImageUri1=null;
////            iv_img1.setVisibility(View.GONE);
//
//        }
//        if(resultCode == RESULT_OK &&requestCode == SELECT_PICTUREMAIN){
//            selectedImageMainUri = data.getData();
//            iv_imgmain.setImageURI(selectedImageMainUri);
//        }else{
//            selectedImageMainUri=null;
////            iv_imgmain.setVisibility(View.GONE);
//        }
//        if(resultCode == RESULT_OK &&requestCode == SELECT_VIDEO_REQUEST){
//            selectedVideoUri = data.getData();
//            vv_video.setVideoURI(selectedVideoUri);
//            vv_video.requestFocus();
//
//        }else{
//            selectedVideoUri=null;
////            vv_video.setVisibility(View.GONE);
//        }



    }
}