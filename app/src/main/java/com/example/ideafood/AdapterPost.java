package com.example.ideafood;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView textView_ngay = view.findViewById(R.id.tv_ngay);
        TextView textView_content1 = view.findViewById(R.id.tv_content1);
        TextView textView_content2 = view.findViewById(R.id.tv_content2);
        textView_category.setText(post.getCategory());
        textView_postname.setText(post.getPostname());
        textView_ngay.setText(post.getDate());
        textView_content1.setText(post.getContent_post().get(0));
        textView_content2.setText(post.getContent_post().get(1));
        ImageView imageView = view.findViewById(R.id.img_food);
        StorageReference storageReference = FirebaseStorage.getInstance("gs://idea-food-cd7e7.appspot.com").getReference().child("imgMain/"+post.getPostid()+"/"+post.getPostid());
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
//                Glide.with(viewGroup.getContext()).load(uri).into(imageView);
                imageView.setImageURI(uri);
            }
        });
        return view;
    }
}