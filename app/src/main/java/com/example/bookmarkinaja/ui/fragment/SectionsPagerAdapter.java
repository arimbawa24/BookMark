package com.example.bookmarkinaja.ui.fragment;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.bookmarkinaja.R;


public class SectionsPagerAdapter extends FragmentPagerAdapter {


    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
      switch (position){
          case 0 :
              TabHome tab1 = new TabHome();
          return tab1;
          case 1 :
              TabData tab2 = new TabData();
              return tab2;
          case 2 :
              TabSettings tab3 = new TabSettings();
              return tab3;
          default:
              return null;
      }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:

                return "HOME";
            case 1:

                return "DATA";
            case 2:

                return "SETTINGS";
        }
                return null;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }


}