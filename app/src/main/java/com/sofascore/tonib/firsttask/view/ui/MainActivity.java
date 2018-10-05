package com.sofascore.tonib.firsttask.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.sofascore.tonib.firsttask.R;
import com.sofascore.tonib.firsttask.service.model.AppDatabase;
import com.sofascore.tonib.firsttask.view.viewpager.CustomViewPagerAdapter;
import com.sofascore.tonib.firsttask.viewmodel.TeamListViewModel;


public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private ViewPager vp;
    private FragmentPagerAdapter adapterViewPager;
    private TeamListViewModel teamListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room
                .databaseBuilder(getApplicationContext(), AppDatabase.class, "teams-database")
                .build();
        vp = findViewById(R.id.viewPager);
        adapterViewPager = new CustomViewPagerAdapter(getSupportFragmentManager(), this);
        vp.setAdapter(adapterViewPager);
        teamListViewModel = ViewModelProviders.of(this).get(TeamListViewModel.class);
    }


}
