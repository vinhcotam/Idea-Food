package com.example.ideafood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ideafood.Module.Account;
import com.example.ideafood.R;

import java.util.List;

public class AdapterUser extends BaseAdapter {
    Context context;
    int layout;
    List<Account> list;

    public AdapterUser(Context context, int layout, List<Account> list) {
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
        Account user = list.get(i);
        TextView tv = view.findViewById(R.id.tvnd);
        tv.setText(user.getUsername());
        return view;
    }
}
