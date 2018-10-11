package com.sofascore.tonib.firsttask.service.repo;

import com.sofascore.tonib.firsttask.service.model.entities.Player;
import com.sofascore.tonib.firsttask.service.model.entities.Team;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class ProjectRepository {

    private TeamsService teamsService;

    public ProjectRepository() {
        teamsService = RetrofitClientInstance.getRetrofitInstance().create(TeamsService.class);
    }

    public Flowable<List<Team>> getAllTeams(int countryCode) {
        return teamsService.getAllTeams(countryCode);
    }

    public Flowable<List<Player>> getAllPlayers(int countryCode) {
        return teamsService.getAllPlayers(countryCode);
    }

    public Flowable<Team> getTeamDetails(int teamId) {
        return teamsService.getTeamDetails(teamId);
    }
 }
