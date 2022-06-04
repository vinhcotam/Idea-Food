package com.example.ideafood.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ideafood.Module.Dsach;
import com.example.ideafood.R;

import java.util.ArrayList;

public class DsachsAdapter extends RecyclerView.Adapter<DsachsAdapter.ViewHolder>{
    ArrayList<Dsach> mListDS;

    public DsachsAdapter(ArrayList<Dsach> mListDS) {
        this.mListDS = mListDS;
    }

    @NonNull
    @Override
    public DsachsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate( R.layout.lv_dsach,parent,false);
        return new DsachsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DsachsAdapter.ViewHolder holder, int position) {
        Dsach ds=mListDS.get(position);
        String iddsach=ds.getDsachid();
        String namedsach=ds.getDsachname();
        String date=ds.getDate();
        String username=ds.getUsername();
        holder.tv_sl_dsach.setText(String.valueOf(mListDS.size()));
        holder.tv_user_dsach.setText(username);
        holder.tv_name_dsach.setText(namedsach);
        holder.tv_date_dsach.setText(date);
    }

    @Override
    public int getItemCount() {
        return mListDS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name_dsach,tv_user_dsach,tv_date_dsach,tv_sl_dsach;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            tv_date_dsach=itemView.findViewById(R.id.tv_date_dsach);
            tv_name_dsach=itemView.findViewById(R.id.tv_name_dsach);
            tv_user_dsach=itemView.findViewById(R.id.tv_user_dsach);
            tv_sl_dsach=itemView.findViewById(R.id.tv_sl_dsach);
        }
    }
}
