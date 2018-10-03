package com.sofascore.tonib.firsttask.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sofascore.tonib.firsttask.service.model.entities.Team;
import com.sofascore.tonib.firsttask.service.repo.ProjectRepository;

import java.util.List;

public class TeamListViewModel extends ViewModel {
    private LiveData<List<Team>> teamDataObservable;

    public void init(){
        teamDataObservable = new ProjectRepository().getAllTeams();

    }

    public LiveData<List<Team>> getTeamListObservable(){
        return teamDataObservable;
    }
}
