package com.example.jihu02.planet;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class CustomAdapter  extends FragmentStatePagerAdapter {

    public CustomAdapter(FragmentManager fm){
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 1:
                return new TwoFragment();
            case 2:
                return new ThreeFragment();
            default :
                return new OneFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }


}
