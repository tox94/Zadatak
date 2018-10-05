package com.sofascore.tonib.firsttask.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sofascore.tonib.firsttask.R;
import com.sofascore.tonib.firsttask.service.model.entities.Team;
import com.sofascore.tonib.firsttask.viewmodel.TeamListViewModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {
    private List<Team> apiTeams;
    private HashMap<Integer, Team> dbTeams;
    private TeamListViewModel teamListViewModel;


    public TeamAdapter(TeamListViewModel teamListViewModel) {
        this.teamListViewModel = teamListViewModel;
    }

    public void updateApiList(List<Team> apiTeams) {
        this.apiTeams = apiTeams;
        if (apiTeams != null) {
            Collections.sort(apiTeams, new Comparator<Team>() {
                @Override
                public int compare(Team o1, Team o2) {
                    return o1.getTeamName().compareToIgnoreCase(o2.getTeamName());
                }
            });
            Log.d("API broj", String.valueOf(apiTeams.size()));
        }
        notifyDataSetChanged();
    }

    public void updateDbList(HashMap<Integer, Team> dbTeams) {
        this.dbTeams = dbTeams;
        notifyDataSetChanged();
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {
        private TextView detailsTextView;
        private CheckBox checkBox;

        public TeamViewHolder(View v) {
            super(v);
            detailsTextView = v.findViewById(R.id.teamDetailsTextView);
            checkBox = v.findViewById(R.id.saveCheckBox);
        }
    }

    @Override
    public TeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeamViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.team_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(TeamViewHolder viewHolder, int position) {
        final Team team = apiTeams.get(position);
        TextView tv = viewHolder.detailsTextView;
        CheckBox cb = viewHolder.checkBox;
        tv.setText(team.getDetails());
        Boolean contains = false;
        if (dbTeams != null) {
            contains = dbTeams.containsKey(team.getTeamId());
        }
        if (contains) {
            cb.setChecked(true);
        } else {
            cb.setChecked(false);
        }
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    teamListViewModel.insertTeam(team);
                } else {
                    teamListViewModel.deleteTeam(team.getTeamId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (apiTeams != null) {
            return apiTeams.size();
        } else {
            return 0;
        }
    }
}
