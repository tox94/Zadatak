package com.sofascore.tonib.firsttask.service.repo;

import android.arch.lifecycle.MutableLiveData;

import com.sofascore.tonib.firsttask.service.model.entities.Team;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectRepository {

    private TeamsService teamsService;

    public ProjectRepository() {
        teamsService = RetrofitClientInstance.getRetrofitInstance().create(TeamsService.class);
    }


    public MutableLiveData<List<Team>> getAllTeams(int[] countryCodes) {

        final MutableLiveData<List<Team>> data = new MutableLiveData<>();

        for (int i : countryCodes) {
            teamsService.getAllTeams(i).enqueue(new Callback<List<Team>>() {
                @Override
                public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                    data.postValue(response.body());
                }

                @Override
                public void onFailure(Call<List<Team>> call, Throwable t) {
                    // error
                }
            });
        }
        return data;
    }
}
