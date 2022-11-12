package com.stlmkvd.aston3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

public class MainActivity extends FragmentActivity {

    private static final int NUM_PAGES = 3;
    private ViewPager2 pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = findViewById(R.id.viewPager);
        pager.setAdapter(new TaskFragmentsAdapter(this));
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }


    private static class TaskFragmentsAdapter extends FragmentStateAdapter {


        public TaskFragmentsAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new TitleFragment();
                case 1:
                    return new CountriesFragment();
                case 2:
                    return new DownloadImageFragment();
                default:
                    throw new IllegalArgumentException("NUM_PAGES variable is bigger," +
                            " than actual number of fragments provided");
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}