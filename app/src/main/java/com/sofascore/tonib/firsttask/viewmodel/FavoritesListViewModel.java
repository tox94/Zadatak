package com.sofascore.tonib.firsttask.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;

import com.sofascore.tonib.firsttask.service.model.AppDatabase;
import com.sofascore.tonib.firsttask.service.model.daos.PlayerDao;
import com.sofascore.tonib.firsttask.service.model.daos.SportDao;
import com.sofascore.tonib.firsttask.service.model.daos.TeamDao;
import com.sofascore.tonib.firsttask.service.model.entities.Player;
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
    private PlayerDao playerDao;
    public CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<Team>> favoriteTeams;
    private MutableLiveData<List<Player>> favoritePlayers;

    public FavoritesListViewModel(@NonNull Application application) {
        super(application);
        teamDao = AppDatabase.getInstance(application).teamDao();
        playerDao = AppDatabase.getInstance(application).playerDao();
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
                    adapter.updateFavoriteTeams(teams);
                });
        compositeDisposable.add(disposable);
    }

    public void fetchPlayersFromDB(FavoritesAdapter adapter) {
        Disposable disposable = playerDao.getAllPlayers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(players -> {
                    Collections.sort(players, (p1, p2) -> p1.getPlayerName().compareToIgnoreCase(p2.getPlayerName()));
                    return players;
                })
                .subscribe(players -> {
                    adapter.updateFavoritePlayers(players);
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

    @SuppressLint("CheckResult")
    public void deletePlayer(final int playerId, FavoritesAdapter favoritesAdapter) {
        Completable.fromAction(()->playerDao.deletePlayer(playerId))
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
                        fetchPlayersFromDB(favoritesAdapter);
                    }
                });
    }

    public MutableLiveData<List<Team>> getFavoriteTeams() {
        if (favoriteTeams == null) {
            favoriteTeams = new MutableLiveData<List<Team>>();
        }
        return favoriteTeams;
    }

    public MutableLiveData<List<Player>> getFavoritePlayers() {
        if (favoritePlayers == null) {
            favoritePlayers = new MutableLiveData<List<Player>>();
        }
        return favoritePlayers;
    }
}
