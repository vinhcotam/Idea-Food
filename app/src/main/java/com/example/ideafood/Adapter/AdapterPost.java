package com.example.ideafood.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ideafood.Posts;
import com.example.ideafood.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;


public class AdapterPost extends BaseAdapter {
    Context context;
    int layout;
    List<Posts> list;

    public AdapterPost(Context context, int layout, List<Posts> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        Posts post = list.get(i);
        TextView textView_category = view.findViewById(R.id.tv_category);
        TextView textView_postname = view.findViewById(R.id.tv_postname);
        TextView textView_ngay = view.findViewById(R.id.post_date);
        TextView textView_status = view.findViewById(R.id.post_status);
        textView_category.setText(post.getCategory());
        textView_postname.setText(post.getPostname());
        textView_ngay.setText(post.getDate());
        if(post.isStatus() == true){
            textView_status.setText("Đã duyệt !");
        }else {
            textView_status.setText("Đang chờ duyệt!");
        }
        ImageView imageView = view.findViewById(R.id.img_food);
        StorageReference storageReference = FirebaseStorage.getInstance("gs://idea-food-cd7e7.appspot.com").getReference().child("imgMain/"+post.getPostid()+"/"+post.getPostid());
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
               Glide.with(viewGroup.getContext()).load(uri).into(imageView);
//               imageView.setImageURI(uri);
            }
        });
        return view;
    }
}
