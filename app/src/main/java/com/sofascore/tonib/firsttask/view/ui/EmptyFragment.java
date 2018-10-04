package com.sofascore.tonib.firsttask.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sofascore.tonib.firsttask.R;
import com.sofascore.tonib.firsttask.viewmodel.EmptyViewModel;

public class EmptyFragment extends Fragment {

    private EmptyViewModel mViewModel;

    public static EmptyFragment newInstance() {
        return new EmptyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.empty_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EmptyViewModel.class);
        // TODO: Use the ViewModel
    }

}
