package com.sofascore.tonib.firsttask.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;

import com.sofascore.tonib.firsttask.service.model.AppDatabase;
import com.sofascore.tonib.firsttask.service.model.daos.PlayerDao;
import com.sofascore.tonib.firsttask.service.model.entities.Player;
import com.sofascore.tonib.firsttask.service.repo.ProjectRepository;
import com.sofascore.tonib.firsttask.view.adapter.PlayersAdapter;

import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class PlayersListViewModel extends AndroidViewModel {

    private final static int PLAYERS_COUNTRY_CODE = 238;
    private PlayerDao playerDao;
    private ProjectRepository repo;
    public CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<Player>> apiPlayers;
    private MutableLiveData<List<Player>> dbPlayers;

    public PlayersListViewModel(@NonNull Application application) {
        super(application);
        playerDao = AppDatabase.getInstance(application).playerDao();
        repo = new ProjectRepository();
    }

    public void fetchPlayersFromAPI(final SwipeRefreshLayout swipeRefreshLayout) {
        Disposable disposable = repo.getAllPlayers(PLAYERS_COUNTRY_CODE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(players -> {
                    Collections.sort(players, (p1, p2) -> p1.getPlayerName().compareToIgnoreCase(p2.getPlayerName()));
                    return players;
                })
                .subscribe(players -> {
                        if (swipeRefreshLayout != null) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        apiPlayers.postValue(players);
                    }
                );
        compositeDisposable.add(disposable);
    }

    public void fetchPlayersFromDB(final SwipeRefreshLayout swipeRefreshLayout) {
        Disposable disposable = playerDao.getAllPlayers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(players -> {
                    Collections.sort(players, (p1, p2) -> p1.getPlayerName().compareToIgnoreCase(p2.getPlayerName()));
                    return players;
                })
                .subscribe(players -> {
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    this.dbPlayers.postValue(players);
                });
        compositeDisposable.add(disposable);
    }

    @SuppressLint("CheckResult")
    public void insertPlayer(final Player player, PlayersAdapter playersAdapter) {
        Completable.fromAction(() -> playerDao.insertPlayer(player))
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
                        fetchPlayersFromDB(null);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void deletePlayer(final int playerId, PlayersAdapter playersAdapter) {
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
                        fetchPlayersFromDB(null);
                    }
                });
    }

    public MutableLiveData<List<Player>> getApiPlayers() {
        if (apiPlayers == null) {
            apiPlayers = new MutableLiveData<>();
        }
        return apiPlayers;
    }

    public MutableLiveData<List<Player>> getDbPlayers() {
        if (dbPlayers == null) {
            dbPlayers = new MutableLiveData<>();
        }
        return dbPlayers;
    }
}
