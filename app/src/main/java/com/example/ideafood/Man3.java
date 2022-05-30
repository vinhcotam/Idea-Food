package com.example.ideafood;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ideafood.MainActivity;
import com.example.ideafood.R;


public class Man3 extends Fragment {
    private Button btnbd;
    private View view;

    public Man3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_man3, container, false);
        btnbd = view.findViewById(R.id.start);
        btnbd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BoxAddComment.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}