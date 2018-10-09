package com.sofascore.tonib.firsttask.service.repo;

import com.sofascore.tonib.firsttask.service.model.entities.Team;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class ProjectRepository {

    private TeamsService teamsService;

    public ProjectRepository() {
        teamsService = RetrofitClientInstance.getRetrofitInstance().create(TeamsService.class);
    }


    public Observable<List<Team>> getAllTeams(int countryCode) {


        return teamsService.getAllTeams(countryCode);
                /*.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(@NonNull Call<List<Team>> call, @NonNull Response<List<Team>> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Team>> call, Throwable t) {
                // error
            }
        });*/
    }
}
