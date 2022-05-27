package com.example.ideafood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ideafood.Module.Posts;
import com.example.ideafood.R;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
    private Context context;
    String username;
    private ArrayList<Posts> mListPost;

    public PostAdapter(Context context, String username, ArrayList<Posts> mListPost) {
        this.context = context;
        this.username = username;
        this.mListPost = mListPost;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bvtt_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        Posts posts=mListPost.get(position);
        String postname=posts.getPostname();
        String header=posts.getHeader();
        String date=posts.getDate();
        ArrayList content_post=posts.getContent_post();
        String postid=posts.getPostid();
        String userid=posts.getUserid();
        String category=posts.getCategory();
//        holder.tv_contentbvtt.setText((CharSequence) content_post);
        holder.tv_postnamebvtt.setText(postname);
    }

    @Override
    public int getItemCount() {
        return mListPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_postnamebvtt,tv_contentbvtt;
        private ImageView iv_imgbvtt;
            public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_contentbvtt=itemView.findViewById(R.id.tv_contentbvtt);
            tv_postnamebvtt=itemView.findViewById(R.id.tv_postnamebvtt);
        }
    }
}
