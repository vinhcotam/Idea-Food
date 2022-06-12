package com.example.ideafood.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ideafood.R;
import com.example.ideafood.Module.Comment;

import java.util.ArrayList;

public class Rep_Adapter extends RecyclerView.Adapter<Rep_Adapter.ViewHolder> {
    ArrayList<Comment> repList;

    public Rep_Adapter(ArrayList<Comment> repList) {
        this.repList = new ArrayList<>();
        for(int i=0;i<repList.size();i++)
        this.repList.add(repList.get(i));

    }
//    public Rep_Adapter(String fatherid){
//        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//        Query allRep = database.child("comment").orderByChild("fatherid").equalTo(fatherid);
//        allRep.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot item:snapshot.getChildren()){
//                    Comment cmt = item.getValue(Comment.class);
//                    repList.add(cmt);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_boxrep, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment cmt = repList.get(position);
        holder.tvUser.setText(cmt.getUsername());
        holder.tvContent.setText(cmt.getContent());
        holder.tvDate.setText(cmt.getDate());
    }

    @Override
    public int getItemCount() {
        return repList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUser, tvContent, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUser = itemView.findViewById(R.id.repid);
            tvContent = itemView.findViewById(R.id.repcontent);
            tvDate = itemView.findViewById(R.id.repdate);
        }
    }
}
