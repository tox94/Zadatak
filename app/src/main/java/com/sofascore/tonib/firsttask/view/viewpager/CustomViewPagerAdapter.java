package com.sofascore.tonib.firsttask.view.viewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sofascore.tonib.firsttask.R;
import com.sofascore.tonib.firsttask.view.ui.EmptyFragment;
import com.sofascore.tonib.firsttask.view.ui.TeamFragment;

public class CustomViewPagerAdapter extends FragmentPagerAdapter {

    private static int TAB_COUNT = 2;
    private Context context;

    public CustomViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TeamFragment();
            case 1:
                return new EmptyFragment();
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
                return context.getString(R.string.empty_fragment_title);
            default:
                return context.getString(R.string.app_name);
        }
    }
}
