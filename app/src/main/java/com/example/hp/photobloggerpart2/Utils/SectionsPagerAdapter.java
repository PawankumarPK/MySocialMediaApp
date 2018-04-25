package com.example.hp.photobloggerpart2.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;
import java.util.List;

//class that stores fragment tabs

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "SectionsPagerAdapter";
    private final List<Fragment>mfragmentList = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mfragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mfragmentList.size();
    }
    public void addFragment(Fragment fragment){
        mfragmentList.add(fragment);
    }
}
