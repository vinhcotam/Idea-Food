package com.example.ideafood.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ideafood.Module.Posts;
import com.example.ideafood.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Posts> mListPost;

    public PostsAdapter(Context context, ArrayList<Posts> mListPost) {
        this.context = context;
        this.mListPost = mListPost;
    }

    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate( R.layout.bvtt_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {
        Posts post=mListPost.get(position);
        String postid=post.getPostid();
        String postname=post.getPostname();
        String category=post.getCategory();
        StorageReference storageReference = FirebaseStorage.getInstance("gs://idea-food-cd7e7.appspot.com").getReference().child("imgMain/"+ post.getPostid()+"/"+post.getPostid());
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(holder.iv_imgbvtt);
            }
        });
        holder.tv_postnamebvtt.setText(postname);
        holder.tv_categorybvtt.setText(category);
    }

    @Override
    public int getItemCount() {
        return mListPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_imgbvtt;
        TextView tv_postnamebvtt,tv_categorybvtt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_imgbvtt=itemView.findViewById(R.id.iv_imgbvtt);
            tv_postnamebvtt=itemView.findViewById(R.id.tv_postnamebvtt);
            tv_categorybvtt=itemView.findViewById(R.id.tv_categorybvtt);
        }
    }
}
