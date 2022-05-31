package com.example.ideafood.startLayout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ideafood.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Page1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Page1 extends Fragment {

    public Page1() {
    }
    public static Page1 newInstance(String param1, String param2) {
        Page1 fragment = new Page1();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page1, container, false);
    }
}