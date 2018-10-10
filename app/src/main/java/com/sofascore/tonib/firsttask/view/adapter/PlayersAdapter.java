package com.sofascore.tonib.firsttask.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sofascore.tonib.firsttask.R;
import com.sofascore.tonib.firsttask.service.model.entities.Player;
import com.sofascore.tonib.firsttask.service.model.entities.Team;
import com.sofascore.tonib.firsttask.viewmodel.FavoritesListViewModel;
import com.sofascore.tonib.firsttask.viewmodel.PlayersListViewModel;

import java.util.ArrayList;
import java.util.List;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.TeamViewHolder> {
    private List<Player> players;
    private PlayersListViewModel playersListViewModel;


    public PlayersAdapter(PlayersListViewModel playersListViewModel) {
        this.playersListViewModel = playersListViewModel;
        players = new ArrayList<>();
    }

    public void updatePlayers(List<Player> list) {
        this.players = list;
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
        final Player player = players.get(position);
        TextView tv = viewHolder.detailsTextView;
        CheckBox cb = viewHolder.checkBox;
        tv.setText(player.getDetails());
        cb.setChecked(true);
        cb.setOnClickListener(v -> {
            Log.d("CHECKBOXCLICK", "Brisi " + player.getPlayerName());
            playersListViewModel.deletePlayer(player.getPlayerId(), this);
        });
    }

    @Override
    public int getItemCount() {
        if (players != null) {
            return players.size();
        } else {
            return 0;
        }
    }
}
