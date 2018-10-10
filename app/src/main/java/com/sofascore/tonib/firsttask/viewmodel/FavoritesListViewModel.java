package com.sofascore.tonib.firsttask.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;

import com.sofascore.tonib.firsttask.service.model.AppDatabase;
import com.sofascore.tonib.firsttask.service.model.daos.SportDao;
import com.sofascore.tonib.firsttask.service.model.daos.TeamDao;
import com.sofascore.tonib.firsttask.service.model.entities.Team;
import com.sofascore.tonib.firsttask.service.repo.ProjectRepository;
import com.sofascore.tonib.firsttask.view.adapter.FavoritesAdapter;
import com.sofascore.tonib.firsttask.view.adapter.TeamAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class FavoritesListViewModel extends AndroidViewModel {

    private TeamDao teamDao;
    public CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FavoritesListViewModel(@NonNull Application application) {
        super(application);
        teamDao = AppDatabase.getInstance(application).teamDao();
    }

    public void fetchTeamsFromDB(FavoritesAdapter adapter) {
        Disposable disposable = teamDao.getAllTeams()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(teams -> {
                    Collections.sort(teams, (t1, t2) -> t1.getTeamName().compareToIgnoreCase(t2.getTeamName()));
                    return teams;
                })
                .subscribe(teams -> {
                    adapter.updateFavoritesList(teams);
                });
        compositeDisposable.add(disposable);
    }

    @SuppressLint("CheckResult")
    public void deleteTeam(final int teamId, FavoritesAdapter favoritesAdapter) {
        Completable.fromAction(()->teamDao.deleteTeam(teamId))
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
                    public void onComplete(){
                        fetchTeamsFromDB(favoritesAdapter);
                    }
                });
    }
}
