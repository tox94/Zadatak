package com.sofascore.tonib.firsttask.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sofascore.tonib.firsttask.service.model.AppDatabase;
import com.sofascore.tonib.firsttask.service.model.daos.ManagerDao;
import com.sofascore.tonib.firsttask.service.model.daos.SportDao;
import com.sofascore.tonib.firsttask.service.model.daos.TeamDao;
import com.sofascore.tonib.firsttask.service.model.entities.Team;
import com.sofascore.tonib.firsttask.service.repo.ProjectRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function3;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class TeamListViewModel extends AndroidViewModel {

    private static final int DELAY_TIME = 15;
    private static final int NUMBER_OF_TEAMS = 50;

    private SportDao sportDao;
    private TeamDao teamDao;
    private ManagerDao managerDao;
    private ProjectRepository repo;

    public TeamListViewModel(@NonNull Application application) {
        super(application);
        sportDao = AppDatabase.getInstance(application).sportDao();
        teamDao = AppDatabase.getInstance(application).teamDao();
        managerDao = AppDatabase.getInstance(application).managerDao();
        repo = new ProjectRepository();
    }

    public Flowable<List<Team>> fetchTeamsFromAPI() {
        return Flowable.zip(repo.getAllTeams(218), repo.getAllTeams(220),
                repo.getAllTeams(238), (Function3<List<Team>, List<Team>, List<Team>, List<Team>>) (teams, teams2, teams3) -> {
                    ArrayList<Team> list = new ArrayList<>();
                    Set<Team> set = new HashSet<>();
                    set.addAll(teams);
                    set.addAll(teams2);
                    set.addAll(teams3);
                    list.addAll(set);
                    return list;
                })
                .flatMap(Flowable::fromIterable)
                .take(NUMBER_OF_TEAMS)
                .flatMap(team -> repo.getTeamDetails(team.getTeamId()))
                .toSortedList((t1, t2) -> t1.getTeamName().compareToIgnoreCase(t2.getTeamName()))
                .toFlowable()
                .observeOn(AndroidSchedulers.mainThread())
                .repeatWhen(objectFlowable -> objectFlowable.delay(DELAY_TIME, TimeUnit.SECONDS));
    }

    public LiveData<List<Team>> getTeamsFromAPI() {
        return LiveDataReactiveStreams.fromPublisher(fetchTeamsFromAPI());
    }

    public Flowable<List<Team>> fetchTeamsFromDB() {
        return teamDao.getAllTeams()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(teams -> {
                    Collections.sort(teams, (t1, t2) -> t1.getTeamName().compareToIgnoreCase(t2.getTeamName()));
                    return teams;
                })
                .repeatWhen(objectFlowable -> objectFlowable.delay(DELAY_TIME, TimeUnit.SECONDS));
    }

    public LiveData<List<Team>> getTeamsFromDB() {
        return LiveDataReactiveStreams.fromPublisher(fetchTeamsFromDB());
    }

    @SuppressLint("CheckResult")
    public void insertTeam(final Team team) {
        Completable.fromAction(() -> teamDao.insertTeam(team))
                .subscribeWith(new DisposableCompletableObserver() {

                    @Override
                    public void onStart() {
                        sportDao.insertSport(team.getSport());
                        if (team.getManager() != null) {
                            managerDao.insertManager(team.getManager());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // report Error
                    }

                    @Override
                    public void onComplete() {
                        fetchTeamsFromDB();
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void deleteTeam(final Team team) {
        Completable.fromAction(() -> teamDao.deleteTeam(team.getTeamId()))
                .subscribeWith(new DisposableCompletableObserver() {

                    @Override
                    public void onStart() {
                        if (team.getManager() != null) {
                            managerDao.deleteManager(team.getManager());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // report Error
                    }

                    @Override
                    public void onComplete() {
                        fetchTeamsFromDB();
                    }
                });
    }
}
