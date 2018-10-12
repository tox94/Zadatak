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
import com.sofascore.tonib.firsttask.viewmodel.PlayersListViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.PlayerViewHolder> {
    private List<Player> apiPlayers;
    private HashMap<Integer, Player> dbPlayers;
    private PlayersListViewModel playersListViewModel;


    public PlayersAdapter(PlayersListViewModel playersListViewModel) {
        this.playersListViewModel = playersListViewModel;
    }

    public void updateApiPlayers(List<Player> list) {
        apiPlayers = list;
        notifyDataSetChanged();
    }

    public void updateDbPlayers(List<Player> list) {
        HashMap<Integer, Player> map = new HashMap<>();
        if (list != null) {
            for (Player p : list) {
                map.put(p.getPlayerId(), p);
            }
        }
        this.dbPlayers = map;
        notifyDataSetChanged();
    }

    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        private TextView detailsTextView;
        private CheckBox checkBox;

        public PlayerViewHolder(View v) {
            super(v);
            detailsTextView = v.findViewById(R.id.playerDetailsTextView);
            checkBox = v.findViewById(R.id.playerSaveCheckBox);
        }
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlayerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.player_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder viewHolder, int position) {
        final Player player;
        if (apiPlayers != null) {
            player = apiPlayers.get(position);
        } else {
            player = dbPlayers.get(dbPlayers.keySet().toArray()[position]);
        }
        if (player != null) {
            TextView tv = viewHolder.detailsTextView;
            CheckBox cb = viewHolder.checkBox;
            tv.setText(player.getDetails());
            Boolean contains = false;
            if (dbPlayers != null) {
                contains = dbPlayers.containsKey(player.getPlayerId());
            }
            if (contains) {
                cb.setChecked(true);
            } else {
                cb.setChecked(false);
            }
            cb.setOnClickListener(v -> {
                if (((CheckBox) v).isChecked()) {
                    Log.d("CHECKBOXCLICK", "Dodaj " + player.getPlayerName());
                    playersListViewModel.insertPlayer(player);
                } else {
                    Log.d("CHECKBOXCLICK", "Brisi " + player.getPlayerName());
                    playersListViewModel.deletePlayer(player.getPlayerId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (apiPlayers != null) {
            return apiPlayers.size();
        } else if (dbPlayers != null) {
            return dbPlayers.size();
        } else {
            return 0;
        }
    }
}
