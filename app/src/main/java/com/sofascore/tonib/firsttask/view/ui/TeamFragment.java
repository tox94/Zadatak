package com.sofascore.tonib.firsttask.view.ui;

import android.Manifest;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sofascore.tonib.firsttask.R;
import com.sofascore.tonib.firsttask.service.InternetUtils;
import com.sofascore.tonib.firsttask.service.model.AppDatabase;
import com.sofascore.tonib.firsttask.service.model.entities.Team;
import com.sofascore.tonib.firsttask.view.adapter.TeamAdapter;
import com.sofascore.tonib.firsttask.viewmodel.TeamListViewModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TeamFragment extends Fragment {

    private static final int MY_PERMISSION = 0;
    private static final int MY_PERIOD = 30000;

    private TeamListViewModel teamListViewModel;
    private RecyclerView recyclerView;
    private TeamAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler;
    private Runnable runnable;

    public static TeamFragment newInstance() {
        return new TeamFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.team_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        teamListViewModel = ViewModelProviders.of(this).get(TeamListViewModel.class);
        adapter = new TeamAdapter(teamListViewModel);

        initViews();

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                checkForInternetConnectionAndPermissions();
                handler.postDelayed(this, MY_PERIOD);
            }
        };
        handler.post(runnable);
    }

    private void checkForInternetConnectionAndPermissions(){
        if (InternetUtils.isInternetAvailable(getActivity())){
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSION);
            } else {
                getDataFromApi();
            }
        } else {
            Toast.makeText(getActivity(), "Please turn on internet access.", Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews(){
        recyclerView = getActivity().findViewById(R.id.teamRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = getActivity().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
                handler.removeCallbacksAndMessages(null);
                checkForInternetConnectionAndPermissions();
                handler.postDelayed(runnable, MY_PERIOD);
            }
        });
    }

    private void getDataFromApi(){
        Log.d("SYNCANJE", "Sve");
        final MutableLiveData<List<Team>> apiTeams = teamListViewModel.getAllTeams();
        final LiveData<List<Team>> dbTeams = teamListViewModel.getTeamsFromDb();

        apiTeams.observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(@Nullable List<Team> teams) {
                Log.d("SYNCANJE", "Api sync");
                adapter.updateApiList(teams);
            }
        });

        dbTeams.observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(@Nullable List<Team> teams) {
                Log.d("SYNCANJE", "DB sync");
                adapter.updateDbList(teams);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch(requestCode){
            case MY_PERMISSION:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getDataFromApi();
                } else {
                    Toast.makeText(getActivity(), "Please enable all permissions.", Toast.LENGTH_SHORT).show();
                }
            }
            return;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        handler.removeCallbacksAndMessages(runnable);
    }
}
