package com.example.santiago.bakingapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.santiago.bakingapp.R;

/**
 * Created by Santiago on 7/02/2018.
 */

public class IngredientsStepsViewPagerFragment extends Fragment {
    private ViewPager viewPager;
    private String mRecipeId;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients_and_steps_view_pager, container, false);
        progressBar = rootView.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        viewPager = rootView.findViewById(R.id.ingredients_steps_view_pager);
        MyPagerAdapter adapterViewPager = new MyPagerAdapter(getFragmentManager());
        adapterViewPager.setRecipeId(mRecipeId);
        TabLayout tabLayout = rootView.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.setAdapter(adapterViewPager);
        return rootView;
    }
    // ...

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;
        private String mRecipeId;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    IngredientsListFragment ingredientsListFragment = new IngredientsListFragment();
                    ingredientsListFragment.setRecipeId(mRecipeId);
                    return ingredientsListFragment;
                case 1: // Fragment # 0 - This will show FirstFragment
                    StepsListFragment stepsListFragment = new StepsListFragment();
                    stepsListFragment.setRecipeId(Integer.valueOf(mRecipeId));
                    return stepsListFragment;
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Ingredients";
                case 1:
                    return "Steps";
                default:
                    return "nn";
            }
        }

        public void setRecipeId(String id) {
            mRecipeId = id;
        }

    }

    public void setRecipeId(String id) {
        mRecipeId = id;
    }

}

