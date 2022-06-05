package com.example.ideafood.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ideafood.Module.Dsach;
import com.example.ideafood.R;

import java.util.ArrayList;
import java.util.Random;

public class DsachAdapter extends BaseAdapter {
    ArrayList<Dsach> mListDsach;
    public DsachAdapter(ArrayList<Dsach> mListDsach) {
        this.mListDsach = mListDsach;
    }

    @Override
    public int getCount() {
        return mListDsach.size();
    }

    @Override
    public Object getItem(int i) {
        return mListDsach.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (new Random().nextLong());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewList;
        if (view == null) {
            viewList = View.inflate(viewGroup.getContext(), R.layout.lv_dsach, null);
        } else viewList = view;
        Dsach dsach=mListDsach.get(i);
        TextView tv_name_dsach,tv_user_dsach,tv_date_dsach,tv_sl_dsach;
        tv_date_dsach=viewList.findViewById(R.id.tv_date_dsach);
        tv_name_dsach=viewList.findViewById(R.id.tv_name_dsach);
        tv_user_dsach=viewList.findViewById(R.id.tv_user_dsach);
        tv_sl_dsach=viewList.findViewById(R.id.tv_sl_dsach);
        tv_name_dsach.setText(dsach.getDsachname());
        tv_date_dsach.setText(dsach.getDate());
        tv_user_dsach.setText(dsach.getUsername());
//        tv_sl_dsach.setText(String.valueOf(dsach.getPostid().size()));
        tv_sl_dsach.setText(String.valueOf(mListDsach.size()));
        return viewList;
    }
}
