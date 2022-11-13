package com.stlmkvd.aston3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ViewPager2 pager;
    private final List<Class<? extends Fragment>> fragments = new ArrayList<Class<? extends Fragment>>() {
        {
            //you can easily dispatch your fragments for pager here
            add(TitleFragment.class);
            add(CountriesFragment.class);
            add(DownloadImageFragment.class);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = findViewById(R.id.viewPager);
        pager.setAdapter(new TaskFragmentsAdapter(this, fragments));
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

        private List<Class<? extends Fragment>> fragments;

        public TaskFragmentsAdapter(FragmentActivity fa, List<Class<? extends Fragment>> fragments) {
            super(fa);
            this.fragments = fragments;
        }

        @Override
        public Fragment createFragment(int position) {
            try {
                return fragments.get(position).newInstance();
            } catch (Exception e) {
                throw new IllegalArgumentException("Could not instatiate this class: "
                        + fragments.get(position).getCanonicalName());
            }
        }

        @Override
        public int getItemCount() {
            return fragments.size();
        }
    }
}