package com.example.winecellarapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.winecellarapp.DataView;
import com.example.winecellarapp.Graphs.graphs.BarChartItem;
import com.example.winecellarapp.Graphs.graphs.ChartItem;
import com.example.winecellarapp.Graphs.graphs.LineChartItem;
import com.example.winecellarapp.Graphs.graphs.PieChartItem;
import com.example.winecellarapp.Graphs.adapters.ChartDataAdapter;
import com.example.winecellarapp.Graphs.setGraphData.SetGraphsData;
import com.example.winecellarapp.Model.Hello;
import com.example.winecellarapp.Model.Temperature;
import com.example.winecellarapp.Presenter;
import com.example.winecellarapp.R;

import java.util.ArrayList;

public class AirFragment extends Fragment implements DataView {

    ArrayList<Temperature> temperatures;
    View view;
    SetGraphsData graphsData;
    Presenter presenter;
    ListView lv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.airquality_fragment_layout, container, false);
        temperatures = new ArrayList<>();
        presenter = new Presenter(this);
        presenter.getSensorData();
        graphsData = new SetGraphsData();
        lv = view.findViewById(R.id.listView1);

        return view;
    }

    @Override
    public void setData(Temperature temperature)
    {
        temperatures.add(temperature);

        /**
         * Create listview and adding chart to it with getting data from sensor
         * */
        graphsData.setAction(SetGraphsData.DATATYPE.TEMPERATURE);
        ArrayList<ChartItem> list = new ArrayList<>();
        list.add(new LineChartItem(graphsData.generateDataLine("Temperature",temperatures), getContext()));
        list.add(new BarChartItem(graphsData.generateDataBar("Temperatures",temperatures), getContext()));
        list.add(new PieChartItem(graphsData.generateDataPie("Temp",temperatures), getContext()));

        ChartDataAdapter cda = new ChartDataAdapter(getContext(), list);
        lv.setAdapter(cda);
    }

    @Override
    public void setHello(Hello hello) {

    }

    @Override
    public void onErrorLoading(String message) {

    }

    //TODO:Add reference to footer for author because it is crazy great job!
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.only_github, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.viewGithub: {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/com/xxmassdeveloper/mpchartexample/ListViewMultiChartActivity.java"));
                startActivity(i);
                break;
            }
        }

        return true;
    }*/

}
