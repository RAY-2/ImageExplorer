package com.ray_apps.imageexplorer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.ray_apps.imageexplorer.Adaptors.ViewPagerAdaptor;
import com.ray_apps.imageexplorer.Fragments.Bookmark;
import com.ray_apps.imageexplorer.Fragments.Discover;

public class MainActivity extends AppCompatActivity {

    private ViewPagerAdaptor viewPagerAdaptor;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        //Tabbed Activity

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        viewPagerAdaptor = new ViewPagerAdaptor(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        //viewPagerAdaptor

        viewPagerAdaptor.AddFragment(new Discover(),"Discover");
        viewPagerAdaptor.AddFragment(new Bookmark(),"Bookmarks");

        viewPager.setAdapter(viewPagerAdaptor);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.discover);
        tabLayout.getTabAt(1).setIcon(R.drawable.bookmark);
    }

}