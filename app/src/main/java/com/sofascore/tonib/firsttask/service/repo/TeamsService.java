package com.sofascore.tonib.firsttask.service.repo;

import com.sofascore.tonib.firsttask.service.model.entities.Team;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TeamsService {

    public final String TEAMS_API_URL = "http://mobile.sofascore.com/mobile/v4/mcc/234/teams";

    @GET()
    Call<List<Team>> getAllTeams();

    @GET("/{id}")
    Call<List<Team>> getTeam(@Path("id") int id);

}
