package com.example.winecellarapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.winecellarapp.R;
import com.example.winecellarapp.views.fragments.AirFragment;
import com.example.winecellarapp.views.fragments.HumidityFragment;
import com.example.winecellarapp.views.fragments.TemperatureFragment;

/**
 * Home fragment with all buttons
 */
public class HomeFragment extends Fragment implements View.OnClickListener {


    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.home_fragment, container, false);

        CardView cardTemperature = (CardView) view.findViewById(R.id.cardTemperature);
        cardTemperature.setOnClickListener(this);
        CardView cardHumidity = (CardView) view.findViewById(R.id.cardHumidity);
        cardHumidity.setOnClickListener(this);
        CardView cardAir = (CardView) view.findViewById(R.id.cardAir);
        cardAir.setOnClickListener(this);
        CardView cardNotifications = (CardView) view.findViewById(R.id.cardNotifications);
        cardNotifications.setOnClickListener(this);

        return view;
    }

    /**
     * Click on the buttons
     * @param v actual View
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.cardTemperature:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new TemperatureFragment()).commit();
                break;
            case R.id.cardHumidity:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new HumidityFragment()).commit();
                break;
            case R.id.cardAir:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new AirFragment()).commit();
                break;
            case R.id.cardNotifications:
                startActivity(new Intent(getContext(), SettingsActivity.class));
                break;
        }


    }
}
