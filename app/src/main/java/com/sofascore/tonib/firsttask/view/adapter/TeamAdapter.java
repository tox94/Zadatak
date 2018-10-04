package com.sofascore.tonib.firsttask.view.adapter;

import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sofascore.tonib.firsttask.R;
import com.sofascore.tonib.firsttask.service.model.entities.Team;
import com.sofascore.tonib.firsttask.viewmodel.TeamListViewModel;

import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder>{
    private List<Team> apiTeams;
    private List<Team> dbTeams;
    private TeamListViewModel teamListViewModel;
    private ArrayList<Team> adapterTeams = new ArrayList<>();


    public TeamAdapter(TeamListViewModel teamListViewModel) {
        this.teamListViewModel = teamListViewModel;
    }

    public void updateApiList(List<Team> apiTeams){
        this.apiTeams = apiTeams;
        //notifyItemChanged(0, getItemCount());
        notifyDataSetChanged();
    }

    public void updateDbList(List<Team> dbTeams){
        this.dbTeams = dbTeams;
        //notifyItemChanged(0, getItemCount());
        notifyDataSetChanged();
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder{
        private TextView detailsTextView;
        private CheckBox checkBox;

        public TeamViewHolder(View v){
            super(v);
            detailsTextView = v.findViewById(R.id.teamDetailsTextView);
            checkBox = v.findViewById(R.id.saveCheckBox);
        }
    }

    @Override
    public TeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new TeamViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.team_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(TeamViewHolder viewHolder, int position) {
        final Team team = apiTeams.get(position);
        TextView tv = viewHolder.detailsTextView;
        CheckBox cb = viewHolder.checkBox;
        tv.setText(team.getTeamName());
        Boolean contains = false;
        for(Team t : dbTeams){
            if (t.getTeamId() == team.getTeamId())
                contains = true;
        }
        if (contains){
            cb.setChecked(true);
        } else{
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
        if (apiTeams != null){
            return apiTeams.size();
        }
        else {
            return 0;
        }
    }
}
