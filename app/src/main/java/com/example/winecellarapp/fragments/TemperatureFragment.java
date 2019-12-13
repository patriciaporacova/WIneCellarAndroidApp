package com.example.winecellarapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.winecellarapp.DataView;
import com.example.winecellarapp.Model.Hello;
import com.example.winecellarapp.Model.Temperature;
import com.example.winecellarapp.Presenter;
import com.example.winecellarapp.R;

public class TemperatureFragment extends Fragment implements DataView {

    View view;
    Presenter presenter;
    TextView tempValue, tempDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.temperature_fragment_layout, container, false);

        presenter = new Presenter(this);
        presenter.getSensorData();

        return view;
    }

    @Override
    public void setData(Temperature temperature) {
        tempValue = view.findViewById(R.id.temperature_text);
        tempValue.setText(temperature.getReading().toString());



        tempDate = view.findViewById(R.id.temperature_date);
        tempDate.setText(temperature.getDate().toString() + " at " +temperature.getTime().toString());
    }

    @Override
    public void setHello(Hello hello) {

    }

    @Override
    public void onErrorLoading(String message) {

    }
}
