package com.sofascore.tonib.firsttask.service.repo;

import com.sofascore.tonib.firsttask.service.model.entities.Player;
import com.sofascore.tonib.firsttask.service.model.entities.Team;

import java.util.List;

import io.reactivex.Observable;

public class ProjectRepository {

    private TeamsService teamsService;

    public ProjectRepository() {
        teamsService = RetrofitClientInstance.getRetrofitInstance().create(TeamsService.class);
    }

    public Observable<List<Team>> getAllTeams(int countryCode) {
        return teamsService.getAllTeams(countryCode);
    }

    public Observable<List<Player>> getAllPlayers(int countryCode) {
        return teamsService.getAllPlayers(countryCode);
    }

    public Observable<Team> getTeamDetails(int teamId) {
        return teamsService.getTeamDetails(teamId);
    }
 }
