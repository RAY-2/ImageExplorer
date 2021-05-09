package com.ray_apps.imageexplorer.Adaptors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

//A class to handle Discover and BookMark fragments
public class ViewPagerAdaptor extends FragmentPagerAdapter {

    private final List<Fragment> listFragment = new ArrayList<>();
    private final List<String> listTitle = new ArrayList<>();

    //constructor


    public ViewPagerAdaptor(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listTitle.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);

    }

    //Add fragment
    public void AddFragment(Fragment fragment, String title)
    {
        listFragment.add(fragment);
        listTitle.add(title);
    }
}
