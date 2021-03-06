package com.example.ideafood.Adapter;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ideafood.Module.Posts;
import com.example.ideafood.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Random;


public class ListView_Post_Adapter extends BaseAdapter {
    ArrayList<Posts> postList;

    public ListView_Post_Adapter(ArrayList<Posts> postList) {
        this.postList = postList;
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int position) {
        return postList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (new Random().nextLong());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewList;
        if (convertView == null) {
            viewList = View.inflate(parent.getContext(), R.layout.item_postlist, null);
        } else viewList = convertView;

        Posts p = postList.get(position);
        ImageView tmv = viewList.findViewById(R.id.header_postimage);
        StorageReference storageReference = FirebaseStorage.getInstance("gs://idea-food-cd7e7.appspot.com").getReference().child("imgMain/"+p.getPostid()+"/"+p.getPostid());
        Log.d("link", String.valueOf(storageReference));
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(parent.getContext()).load(uri).into(tmv);
            }
        });


        TextView tv = viewList.findViewById(R.id.post_header);
        tv.setText(p.getHeader());
        tv = viewList.findViewById(R.id.post_author);
        tv.setText(p.getUsername()+" | ");
        tv = viewList.findViewById(R.id.post_category);
        tv.setText(p.getCategory());
        tv = viewList.findViewById(R.id.post_date);
        String datestr = p.getDate();
        String year = datestr.substring(0,4);
        String month = datestr.substring(5,7);
        String day = datestr.substring(8,10);
        String date = "ng??y "+day+" th??ng "+month+" n??m "+ year;
//        LocalDate date = LocalDate.parse(datestr);

        tv.setText(date.toString());
        return viewList;
    }
}
