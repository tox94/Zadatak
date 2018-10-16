package com.sofascore.tonib.firsttask.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.support.annotation.NonNull;

import com.sofascore.tonib.firsttask.service.model.AppDatabase;
import com.sofascore.tonib.firsttask.service.model.daos.PlayerDao;
import com.sofascore.tonib.firsttask.service.model.entities.Player;
import com.sofascore.tonib.firsttask.service.repo.ProjectRepository;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class PlayersListViewModel extends AndroidViewModel {

    private final static int PLAYERS_COUNTRY_CODE = 238;
    private static final int DELAY_TIME = 15;

    private PlayerDao playerDao;
    private ProjectRepository repo;

    public PlayersListViewModel(@NonNull Application application) {
        super(application);
        playerDao = AppDatabase.getInstance(application).playerDao();
        repo = new ProjectRepository();
    }

    public Flowable<List<Player>> fetchPlayersFromAPI() {
        return repo.getAllPlayers(PLAYERS_COUNTRY_CODE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(players -> {
                    Collections.sort(players, (p1, p2) -> p1.getPlayerName().compareToIgnoreCase(p2.getPlayerName()));
                    return players;
                })
                .repeatWhen(objectFlowable -> objectFlowable.delay(DELAY_TIME, TimeUnit.SECONDS));
    }

    public LiveData<List<Player>> getPlayersFromAPI() {
        return LiveDataReactiveStreams.fromPublisher(fetchPlayersFromAPI());
    }

    public Flowable<List<Player>> fetchPlayersFromDB() {
        return playerDao.getAllPlayers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(players -> {
                    Collections.sort(players, (p1, p2) -> p1.getPlayerName().compareToIgnoreCase(p2.getPlayerName()));
                    return players;
                })
                .repeatWhen(objectFlowable -> objectFlowable.delay(DELAY_TIME, TimeUnit.SECONDS));
    }

    public LiveData<List<Player>> getPlayersFromDB() {
        return LiveDataReactiveStreams.fromPublisher(fetchPlayersFromDB());
    }

    @SuppressLint("CheckResult")
    public void insertPlayer(final Player player) {
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
                        fetchPlayersFromDB();
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void deletePlayer(final int playerId) {
        Completable.fromAction(() -> playerDao.deletePlayer(playerId))
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
                        fetchPlayersFromDB();
                    }
                });
    }
}
