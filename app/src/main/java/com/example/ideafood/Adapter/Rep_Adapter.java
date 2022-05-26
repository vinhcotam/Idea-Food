package com.example.ideafood.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ideafood.R;
import com.example.ideafood.classs.Comment;

import java.util.ArrayList;

public class Rep_Adapter extends RecyclerView.Adapter<Rep_Adapter.ViewHolder> {
    Context context;
    ArrayList<Comment> repList;

    public Rep_Adapter(Context context, ArrayList<Comment> repList) {
        this.context = context;
        this.repList = new ArrayList<>();
        for(int i=0;i<repList.size();i++)
        this.repList.add(repList.get(i));
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_boxrep, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment cmt = repList.get(position);
        holder.tvUser.setText(cmt.getUserid());
        holder.tvContent.setText(cmt.getContent());
    }

    @Override
    public int getItemCount() {
        return repList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUser, tvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUser = itemView.findViewById(R.id.repid);
            tvContent = itemView.findViewById(R.id.repcontent);
        }
    }
}
