package com.sofascore.tonib.firsttask.service.repo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.sofascore.tonib.firsttask.service.model.entities.Team;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectRepository {

    private TeamsService teamsService;

    public LiveData<List<Team>> getAllTeams(){
        final MutableLiveData<List<Team>> data = new MutableLiveData<>();

        teamsService.getAllTeams().enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                // error
            }
        });
        return data;
    }
}
