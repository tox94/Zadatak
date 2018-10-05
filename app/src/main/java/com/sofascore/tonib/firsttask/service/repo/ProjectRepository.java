package com.sofascore.tonib.firsttask.service.repo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sofascore.tonib.firsttask.service.model.entities.Team;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectRepository {

    private TeamsService teamsService;

    public ProjectRepository() {
        teamsService = RetrofitClientInstance.getRetrofitInstance().create(TeamsService.class);
    }


    public LiveData<List<Team>> getAllTeams(int countryCode) {

        final MutableLiveData<List<Team>> data = new MutableLiveData<>();

        teamsService.getAllTeams(countryCode).enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(@NonNull Call<List<Team>> call, @NonNull Response<List<Team>> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Team>> call, Throwable t) {
                // error
            }
        });

        return data;
    }
}
