package com.example.ideafood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
        TextView textView_content = view.findViewById(R.id.tv_content);
        textView_category.setText(post.getCategory());
        textView_postname.setText(post.getPostname());
        textView_ngay.setText(post.getDate());
        textView_content.setText(post.getContent_post());
        return view;
    }
}
