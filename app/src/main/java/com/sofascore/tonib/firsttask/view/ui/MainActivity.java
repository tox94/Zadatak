package com.sofascore.tonib.firsttask.view.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.sofascore.tonib.firsttask.R;
import com.sofascore.tonib.firsttask.service.InternetUtils;
import com.sofascore.tonib.firsttask.service.model.AppDatabase;
import com.sofascore.tonib.firsttask.view.viewpager.CustomViewPagerAdapter;
import com.sofascore.tonib.firsttask.viewmodel.TeamListViewModel;

import java.util.List;

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
