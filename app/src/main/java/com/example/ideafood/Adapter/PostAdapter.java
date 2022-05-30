package com.example.ideafood.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.ideafood.Module.Posts;
import com.example.ideafood.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Random;

public class PostAdapter extends BaseAdapter {
    ArrayList<Posts> postTTList;

    public PostAdapter(ArrayList<Posts> postTTList) {
        this.postTTList = postTTList;
    }

    @Override
    public int getCount() {
        return postTTList.size();
    }

    @Override
    public Object getItem(int i) {
        return postTTList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (new Random().nextLong());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewList;
        if (view == null) {
            viewList = View.inflate(viewGroup.getContext(), R.layout.bvtt_layout, null);
        } else viewList = view;
        Posts p = postTTList.get(i);
        ImageView iv_imgbvtt;
        TextView tv_categorybvtt,tv_postnamebvtt;
        iv_imgbvtt=view.findViewById(R.id.iv_imgbvtt);
        tv_postnamebvtt=view.findViewById(R.id.tv_postnamebvtt);
        tv_categorybvtt=view.findViewById(R.id.tv_categorybvtt);
        StorageReference storageReference = FirebaseStorage.getInstance("gs://idea-food-cd7e7.appspot.com").getReference().child("imgMain/"+p.getPostid()+"/"+p.getPostid());
        Log.d("link", String.valueOf(storageReference));
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(viewGroup.getContext()).load(uri).into(iv_imgbvtt);
            }
        });
        tv_categorybvtt.setText(p.getCategory());
        tv_postnamebvtt.setText(p.getPostname());
        return viewList;
    }
}
