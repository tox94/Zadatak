package com.sofascore.tonib.firsttask.service.repo;

import com.sofascore.tonib.firsttask.service.model.entities.Player;
import com.sofascore.tonib.firsttask.service.model.entities.Team;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TeamsService {

    @GET("mcc/{countryCode}/teams/")
    Flowable<List<Team>> getAllTeams(@Path("countryCode") int countryCode);

    @GET("team/{teamId}/details/")
    Flowable<Team> getTeamDetails(@Path("teamId") int teamId);

    @GET("mcc/{countryCode}/players/")
    Flowable<List<Player>> getAllPlayers(@Path("countryCode") int countryCode);

    @GET("mcc/{countryCode}/teams/{id}")
    Single<Team> getTeam(@Path("id") int id, @Path("countryCode") int countryCode);
}
