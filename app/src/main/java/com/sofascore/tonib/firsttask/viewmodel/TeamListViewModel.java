package com.sofascore.tonib.firsttask.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.sofascore.tonib.firsttask.service.model.AppDatabase;
import com.sofascore.tonib.firsttask.service.model.daos.SportDao;
import com.sofascore.tonib.firsttask.service.model.daos.TeamDao;
import com.sofascore.tonib.firsttask.service.model.entities.Team;
import com.sofascore.tonib.firsttask.service.repo.ProjectRepository;

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

    public MutableLiveData<List<Team>> getAllTeams() {
        MutableLiveData<List<Team>> allTeams = repo.getAllTeams(COUNTRY_CODES);
        return allTeams;
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
