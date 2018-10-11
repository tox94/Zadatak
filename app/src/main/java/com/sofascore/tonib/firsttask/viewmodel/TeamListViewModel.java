package com.sofascore.tonib.firsttask.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sofascore.tonib.firsttask.service.model.AppDatabase;
import com.sofascore.tonib.firsttask.service.model.daos.SportDao;
import com.sofascore.tonib.firsttask.service.model.daos.TeamDao;
import com.sofascore.tonib.firsttask.service.model.entities.Team;
import com.sofascore.tonib.firsttask.service.repo.ProjectRepository;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class TeamListViewModel extends AndroidViewModel {

    private static final int[] COUNTRY_CODES = {218, 220, 238};

    private SportDao sportDao;
    private TeamDao teamDao;
    private ProjectRepository repo;
    public CompositeDisposable teamsCompositeDisposable = new CompositeDisposable();
    public Disposable apiDisposable;
    public Disposable dbDisposable;
    private MutableLiveData<List<Team>> apiTeams;
    private MutableLiveData<List<Team>> dbTeams;

    public TeamListViewModel(@NonNull Application application) {
        super(application);
        sportDao = AppDatabase.getInstance(application).sportDao();
        teamDao = AppDatabase.getInstance(application).teamDao();
        repo = new ProjectRepository();
    }

    public void fetchTeamsFromAPI() {
        apiDisposable = Flowable.zip(repo.getAllTeams(218), repo.getAllTeams(220),
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
                .take(5)
                .flatMap(team -> repo.getTeamDetails(team.getTeamId()))
                .toSortedList((t1, t2) -> t1.getTeamName().compareToIgnoreCase(t2.getTeamName()))
                .toFlowable()
                .observeOn(AndroidSchedulers.mainThread())
                .repeatWhen(new Function<Flowable<Object>, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(Flowable<Object> objectFlowable) throws Exception {
                        return objectFlowable.delay(10, TimeUnit.SECONDS);
                    }
                })
                .subscribe(teams -> {
                    apiTeams.postValue(teams);
                }, throwable -> {

                });
        teamsCompositeDisposable.add(apiDisposable);
    }

    public void fetchTeamsFromDB() {
        dbDisposable = teamDao.getAllTeams()
                .repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(teams -> {
                    Collections.sort(teams, (t1, t2) -> t1.getTeamName().compareToIgnoreCase(t2.getTeamName()));
                    return teams;
                })
                .repeatWhen(new Function<Flowable<Object>, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(Flowable<Object> objectFlowable) throws Exception {
                        return objectFlowable.delay(10, TimeUnit.SECONDS);
                    }
                })
                .subscribe(teams -> {
                    dbTeams.postValue(teams);
                });
        teamsCompositeDisposable.add(dbDisposable);
    }

    @SuppressLint("CheckResult")
    public void insertTeam(final Team team) {
        Completable.fromAction(() -> teamDao.insertTeam(team))
                .subscribeWith(new DisposableCompletableObserver() {

                    @Override
                    public void onStart() {
                        sportDao.insertSport(team.getSport());
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
    public void deleteTeam(final int teamId) {
        Completable.fromAction(() -> teamDao.deleteTeam(teamId))
                .subscribeWith(new DisposableCompletableObserver() {

                    @Override
                    public void onStart() {
                        // do nothing
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

    public MutableLiveData<List<Team>> getApiTeams() {
        if (apiTeams == null) {
            apiTeams = new MutableLiveData<>();
        }
        return apiTeams;
    }

    public MutableLiveData<List<Team>> getDbTeams() {
        if (dbTeams == null) {
            dbTeams = new MutableLiveData<>();
        }
        return dbTeams;
    }
}
