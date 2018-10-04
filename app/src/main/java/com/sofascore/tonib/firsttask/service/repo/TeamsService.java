package com.sofascore.tonib.firsttask.service.repo;

import android.arch.lifecycle.MutableLiveData;

import com.sofascore.tonib.firsttask.service.model.entities.Team;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TeamsService {

    @GET("teams/")
    Call<List<Team>> getAllTeams();

    @GET("/{id}")
    Call<List<Team>> getTeam(@Path("id") int id);

}
