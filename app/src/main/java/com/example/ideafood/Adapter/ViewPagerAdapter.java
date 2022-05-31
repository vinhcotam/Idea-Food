package com.example.ideafood.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.ideafood.startLayout.Page1;
import com.example.ideafood.startLayout.Page2;
import com.example.ideafood.startLayout.Page3;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Page1();
            case 1:
                return new Page2();
            case 2:
                return new Page3();
            default:
                return new Page1();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
