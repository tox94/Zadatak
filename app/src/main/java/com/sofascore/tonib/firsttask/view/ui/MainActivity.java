package com.sofascore.tonib.firsttask.view.ui;

import android.arch.persistence.room.Room;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sofascore.tonib.firsttask.R;
import com.sofascore.tonib.firsttask.service.model.AppDatabase;
import com.sofascore.tonib.firsttask.view.viewpager.CustomViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    //private static final String
    private FragmentPagerAdapter adapterViewPager;
    private ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = Room
                .databaseBuilder(getApplicationContext(), AppDatabase.class, "teams-database")
                .build();
        vp = findViewById(R.id.viewPager);
        adapterViewPager = new CustomViewPagerAdapter(getSupportFragmentManager(), this);
        vp.setAdapter(adapterViewPager);

    }
}
