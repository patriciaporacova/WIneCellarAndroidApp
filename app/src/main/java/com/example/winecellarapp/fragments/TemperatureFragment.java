package com.example.winecellarapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.winecellarapp.DataView;
import com.example.winecellarapp.Model;
import com.example.winecellarapp.Presenter;
import com.example.winecellarapp.R;

import java.util.List;

public class TemperatureFragment extends Fragment implements DataView {


    View view;
    Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.temperature_fragment_layout, container, false);

        presenter.getSensorData();

        return view;
    }

    @Override
    public void setData(List<Model.Data> data) {
        TextView tempList= view.findViewById(R.id.temperature_text);
        tempList.setText(data.toArray().toString());
        
    }

    @Override
    public void onErrorLoading(String message) {

    }
}
