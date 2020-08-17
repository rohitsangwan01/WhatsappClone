package com.example.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;

import com.example.whatsappclone.Fragment.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    ViewPager view_pager;
    TabLayout tab_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view_pager = findViewById(R.id.view_pager);
        view_pager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),0));
        tab_layout = findViewById(R.id.tab_layout);
        tab_layout.setupWithViewPager(view_pager);




    }

}