package com.sofascore.tonib.firsttask.view.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.sofascore.tonib.firsttask.R;
import com.sofascore.tonib.firsttask.view.viewpager.CustomViewPagerAdapter;


public class MainActivity extends AppCompatActivity {

    public static final int ANIMATION_DURATION = 300;

    private ViewPager vp;
    private FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vp = findViewById(R.id.viewPager);
        adapterViewPager = new CustomViewPagerAdapter(getSupportFragmentManager(), this);
        vp.setAdapter(adapterViewPager);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        TeamFragment teamFragment = (TeamFragment) adapterViewPager.getItem(0);
                        teamFragment.getDataFromDb();
                        break;
                    case 1:
                        PlayerFragment playerFragment = (PlayerFragment) adapterViewPager.getItem(1);
                        playerFragment.getDataFromDb();
                        break;
                    case 2:
                        FavoritesFragment favoritesFragment = (FavoritesFragment) adapterViewPager.getItem(2);
                        favoritesFragment.getData();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }
}
