package com.example.ideafood.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ideafood.R;
import com.example.ideafood.classs.Comment;

import java.util.ArrayList;

public class ListRep_Adapter extends BaseAdapter {

    ArrayList<Comment> repList;

    public ListRep_Adapter(ArrayList<Comment> repList) {
        this.repList = repList;
    }

    @Override
    public int getCount() {
        return repList.size();
    }

    @Override
    public Object getItem(int position) {
        return repList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.getLong(repList.get(position).getCommentid());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewRep;
        if(convertView==null){
            viewRep = View.inflate(parent.getContext(), R.layout.item_boxrep, null);
        }
        else viewRep = convertView;
        Comment cmt = (Comment) getItem(position);
        TextView tv = viewRep.findViewById(R.id.repid);
        tv.setText(cmt.getUserid());
        tv = viewRep.findViewById(R.id.repcontent);
        tv.setText(cmt.getContent());
        return viewRep;
    }
}
