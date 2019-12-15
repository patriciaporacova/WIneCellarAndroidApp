package com.example.winecellarapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.winecellarapp.DataView;
import com.example.winecellarapp.R;
import java.util.List;

public class AirFragment extends Fragment implements DataView {


    //TODO-PATRICIA: Create same layout like temperature fragment(feel free to create your own design or copy paste temperature fragment and change variables and colors)
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.airquality_fragment_layout, container, false);

        return view;
    }

    @Override
    public void setData(Object obj)
    {
    }

    @Override
    public void setListData(List data) {

    }

    @Override
    public void onErrorLoading(String message) {

    }

}
