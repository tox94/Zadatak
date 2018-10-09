package com.sofascore.tonib.firsttask.service.repo;

import com.sofascore.tonib.firsttask.service.model.entities.Team;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TeamsService {

    @GET("mcc/{countryCode}/teams/")
    Observable  <List<Team>> getAllTeams(@Path("countryCode") int countryCode);

    @GET("mcc/{countryCode}/teams/{id}")
    Single<Team> getTeam(@Path("id") int id, @Path("countryCode") int countryCode);
}
