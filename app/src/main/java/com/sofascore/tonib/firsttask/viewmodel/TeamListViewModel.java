package com.sofascore.tonib.firsttask.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.sofascore.tonib.firsttask.service.model.AppDatabase;
import com.sofascore.tonib.firsttask.service.model.daos.SportDao;
import com.sofascore.tonib.firsttask.service.model.daos.TeamDao;
import com.sofascore.tonib.firsttask.service.model.entities.Team;
import com.sofascore.tonib.firsttask.service.repo.ProjectRepository;
import com.sofascore.tonib.firsttask.view.adapter.TeamAdapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TeamListViewModel extends AndroidViewModel {

    private static final int[] COUNTRY_CODES = {218, 220, 238};

    private SportDao sportDao;
    private TeamDao teamDao;
    private ProjectRepository repo;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public TeamListViewModel(@NonNull Application application) {
        super(application);
        sportDao = AppDatabase.getInstance(application).sportDao();
        teamDao = AppDatabase.getInstance(application).teamDao();
        repo = new ProjectRepository();
    }

    public void fetchTeamsFromAPI(final TeamAdapter adapter, final SwipeRefreshLayout swipeRefreshLayout) {
        Disposable disposable = Observable.merge(repo.getAllTeams(218), repo.getAllTeams(220), repo.getAllTeams(238))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(teams -> {
                    Collections.sort(teams, (t1, t2) -> t1.getTeamName().compareToIgnoreCase(t2.getTeamName()));
                    return teams;
                })
                .subscribe(teams -> {
                    adapter.updateApiList(teams);
                    swipeRefreshLayout.setRefreshing(false);
                });
        compositeDisposable.add(disposable);
    }

    public void fetchTeamsFromDB(TeamAdapter adapter, final SwipeRefreshLayout swipeRefreshLayout) {
        Disposable disposable = teamDao.getAllTeams()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(teams -> {
                    Collections.sort(teams, (t1, t2) -> t1.getTeamName().compareToIgnoreCase(t2.getTeamName()));
                    return teams;
                })
                .subscribe(teams -> {
                    HashMap<Integer, Team> map = new HashMap<>();
                    if (teams != null) {
                        for (Team t : teams) {
                            map.put(t.getTeamId(), t);
                        }
                    }
                    adapter.updateDbList(map);
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void insertTeam(final Team team, TeamAdapter teamAdapter) {
        AsyncTask.execute(() -> {
            teamDao.insertTeam(team);
            Log.d("DEBUG", "Dodano " + team.getTeamId() + " " + team.getTeamName());
            sportDao.insertSport(team.getSport());
        });
        fetchTeamsFromDB(teamAdapter, null);
    }

    public void deleteTeam(final int teamId, TeamAdapter teamAdapter) {
        AsyncTask.execute(() -> {
            Log.d("DEBUG", "Pobrisano " + teamId);
            teamDao.deleteTeam(teamId);
        });
        fetchTeamsFromDB(teamAdapter, null);
    }
}
