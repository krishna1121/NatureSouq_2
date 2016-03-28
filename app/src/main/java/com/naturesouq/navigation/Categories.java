package com.naturesouq.navigation;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naturesouq.R;

/**
 * Created by SI-Andriod on 15-Oct-15.
 */
public class Categories extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.homefragment, container, false);
        return view ;

    }
}
