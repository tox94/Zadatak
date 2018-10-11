package com.sofascore.tonib.firsttask.view.viewpager;

import android.arch.persistence.room.Embedded;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.sofascore.tonib.firsttask.R;
import com.sofascore.tonib.firsttask.view.ui.EmptyFragment;
import com.sofascore.tonib.firsttask.view.ui.FavoritesFragment;
import com.sofascore.tonib.firsttask.view.ui.PlayerFragment;
import com.sofascore.tonib.firsttask.view.ui.TeamFragment;
import com.sofascore.tonib.firsttask.viewmodel.TeamListViewModel;

public class CustomViewPagerAdapter extends FragmentPagerAdapter {

    private static int TAB_COUNT = 4;
    private Context context;
    private TeamFragment teamFragment;
    private PlayerFragment playerFragment;
    private FavoritesFragment favoritesFragment;
    private EmptyFragment emptyFragment;

    public CustomViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.teamFragment = new TeamFragment();
        this.playerFragment = new PlayerFragment();
        this.favoritesFragment = new FavoritesFragment();
        this.emptyFragment = new EmptyFragment();
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return teamFragment;
            case 1:
                return playerFragment;
            case 2:
                return favoritesFragment;
            case 3:
                return emptyFragment;
            default:
                return null;
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.team_fragment_title);
            case 1:
                return context.getString(R.string.player_fragment_title);
            case 2:
                return context.getString(R.string.favorites_fragment_title);
            case 3:
                return context.getString(R.string.empty_fragment_title);
            default:
                return context.getString(R.string.app_name);
        }
    }
}
