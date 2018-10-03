package com.sofascore.tonib.firsttask.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sofascore.tonib.firsttask.R;
import com.sofascore.tonib.firsttask.service.model.entities.Team;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder>{
    private List<Team> apiTeams;
    private List<Team> dbTeams;

    public TeamAdapter(List<Team> apiTeams, List<Team> dbTeams) {
        this.apiTeams = apiTeams;
        this.dbTeams = dbTeams;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView detailsTextView;
        public CheckBox checkBox;

        public ViewHolder(View v){
            super(v);

            detailsTextView = v.findViewById(R.id.teamDetailsTextView);
            checkBox = v.findViewById(R.id.saveCheckBox);
        }
    }

    public TeamAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater lf = LayoutInflater.from(context);

        View teamView = lf.inflate(R.layout.team_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(teamView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamAdapter.ViewHolder viewHolder, int position) {
        Team team = apiTeams.get(position);

        TextView tv = viewHolder.detailsTextView;
        CheckBox cb = viewHolder.checkBox;
        tv.setText(team.getDetails());
        if (dbTeams.contains(team)){
            cb.setChecked(true);
        } else{
            cb.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return apiTeams.size();
    }
}
