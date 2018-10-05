package com.sofascore.tonib.firsttask.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sofascore.tonib.firsttask.service.model.AppDatabase;
import com.sofascore.tonib.firsttask.service.model.daos.SportDao;
import com.sofascore.tonib.firsttask.service.model.daos.TeamDao;
import com.sofascore.tonib.firsttask.service.model.entities.Team;
import com.sofascore.tonib.firsttask.service.repo.ProjectRepository;

import java.util.HashMap;
import java.util.List;

public class TeamListViewModel extends AndroidViewModel {

    private static final int[] COUNTRY_CODES = {218, 220, 238};

    private SportDao sportDao;
    private TeamDao teamDao;
    private ProjectRepository repo;

    public TeamListViewModel(@NonNull Application application) {
        super(application);
        sportDao = AppDatabase.getInstance(application).sportDao();
        teamDao = AppDatabase.getInstance(application).teamDao();
        repo = new ProjectRepository();
    }

    public MediatorLiveData<List<Team>> getAllTeams() {
        MediatorLiveData<List<Team>> mediator = new MediatorLiveData<>();
        /*for (int i : COUNTRY_CODES) {
            LiveData<List<Team>> team = repo.getAllTeams(i);
            mediator.addSource(team, value -> mediator.setValue(value));
        }*/
        LiveData<List<Team>> team1 = repo.getAllTeams(218);
        mediator.addSource(team1, value -> mediator.setValue(value));
        LiveData<List<Team>> team2 = repo.getAllTeams(220);
        mediator.addSource(team2, value -> mediator.setValue(value));
        LiveData<List<Team>> team3 = repo.getAllTeams(234);
        mediator.addSource(team3, value -> mediator.setValue(value));
        return mediator;
    }

    public LiveData<List<Team>> getTeamsFromDb() {
        LiveData<List<Team>> temp = teamDao.getAllTeams();
        return temp;
    }

    public void insertTeam(final Team team) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                teamDao.insertTeam(team);
                Log.d("DEBUG", "Dodano " + team.getTeamId() + " " + team.getTeamName());
                sportDao.insertSport(team.getSport());
            }
        });
    }

    public void deleteTeam(final int teamId) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("DEBUG", "Pobrisano " + teamId);
                teamDao.deleteTeam(teamId);
            }
        });
    }
}
