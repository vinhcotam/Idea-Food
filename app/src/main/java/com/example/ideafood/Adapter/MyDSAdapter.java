package com.example.ideafood.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ideafood.Module.Dsach;
import com.example.ideafood.R;

import java.util.ArrayList;
import java.util.Random;

public class MyDSAdapter extends BaseAdapter {
    ArrayList<Dsach> mListMyDS;
    public MyDSAdapter(ArrayList<Dsach> mListMyDS) {
        this.mListMyDS = mListMyDS;
    }

    @Override
    public int getCount() {
        return mListMyDS.size();
    }

    @Override
    public Object getItem(int i) {
        return mListMyDS.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (new Random().nextLong());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewList;
        if (view == null) {
            viewList = View.inflate(viewGroup.getContext(), R.layout.lv_myds, null);
        } else viewList = view;
        Dsach dsach=mListMyDS.get(i);
        TextView tv_name_mydsach,tv_user_mydsach,tv_date_mydsach,tv_sl_mydsach;
        tv_date_mydsach=viewList.findViewById(R.id.tv_date_mydsach);
        tv_name_mydsach=viewList.findViewById(R.id.tv_name_mydsach);
        tv_user_mydsach=viewList.findViewById(R.id.tv_user_mydsach);
        tv_sl_mydsach=viewList.findViewById(R.id.tv_sl_mydsach);
        tv_name_mydsach.setText(dsach.getDsachname());
        tv_date_mydsach.setText(dsach.getDate());
        tv_user_mydsach.setText(dsach.getUsername());
//        tv_sl_dsach.setText(String.valueOf(dsach.getPostid().size()));
//        tv_sl_dsach.setText(String.valueOf(mListDsach.size()));
//        tv_sl_dsach.setText(dsach.getDsachid());
        return viewList;
    }
}
