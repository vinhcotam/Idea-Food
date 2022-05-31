package com.example.ideafood.startLayout;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ideafood.Homepage;
import com.example.ideafood.R;

public class Page3 extends Fragment {

    public Page3() {
        // Required empty public constructor
    }

    public static Page3 newInstance(String param1, String param2) {
        Page3 fragment = new Page3();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button button = container.findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(container.getContext(), Homepage.class);
                startActivity(intent);
            }
        });
        return inflater.inflate(R.layout.fragment_page3, container, false);
    }
}