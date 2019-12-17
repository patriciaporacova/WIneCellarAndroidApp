package com.example.winecellarapp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.winecellarapp.DataView;
import com.example.winecellarapp.R;
import com.example.winecellarapp.calendar.CalendarCallback;
import com.example.winecellarapp.calendar.ICalendarCallback;
import com.example.winecellarapp.graphs.CreateGraphs;
import com.example.winecellarapp.graphs.adapters.ChartDataAdapter;
import com.example.winecellarapp.graphs.graphs.BarChartItem;
import com.example.winecellarapp.graphs.graphs.ChartItem;
import com.example.winecellarapp.graphs.graphs.LineChartItem;
import com.example.winecellarapp.graphs.graphs.PieChartItem;
import com.example.winecellarapp.graphs.setGraphData.CreateGraphsData;
import com.example.winecellarapp.graphs.setGraphData.SetGraphsData;
import com.example.winecellarapp.model.Co2;
import com.example.winecellarapp.model.Temperature;
import com.example.winecellarapp.presenters.AirPresenter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.slybeaver.slycalendarview.SlyCalendarDialog;

public class AirFragment extends Fragment implements DataView, ICalendarCallback,ISensorFragment {


    //TODO-PATRICIA: Create same layout like temperature fragment(feel free to create your own design or copy paste temperature fragment and change variables and colors)
    private View view;
    private AirPresenter airPresenter;
    private CalendarCallback calendarCallback;
    private Date[] airDates;
    private ArrayList<Integer> xAxis;
    private SetGraphsData graphsData;
    private CreateGraphsData createGraphsData;
    private CreateGraphs createGraphs;
    private ListView lv;

    private TextView co2Value, co2Date, startDate, endDate;
    private ProgressBar progressBarCo2, progressBarCo2Graphs;
    private Button changePeriodBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.airquality_fragment_layout, container, false);

        co2Value = view.findViewById(R.id.air_text);
        co2Date = view.findViewById(R.id.air_date);
        progressBarCo2 = view.findViewById(R.id.progressBarAirValue);
        progressBarCo2Graphs = view.findViewById(R.id.progressBarAirGraphs);
        startDate = view.findViewById(R.id.start_date_Air);
        endDate = view.findViewById(R.id.end_date_air);
        changePeriodBtn = view.findViewById(R.id.changePeriodAir);

        airDates = new Date[2];

        graphsData = new SetGraphsData();
        createGraphsData = new CreateGraphsData();
        createGraphs = new CreateGraphs(getContext());
        graphsData.setAction(SetGraphsData.DATATYPE.AIR);

        lv = view.findViewById(R.id.list_graphs_air);

        airPresenter= new AirPresenter(this);
        airPresenter.getAirSensorData();

        airDates = createGraphsData.setFirstAndLastDates();
        xAxis = createGraphsData.setXAxisValues(airDates);
        setDatesToTextView();

        airPresenter.getAirBetweenData(airDates[0], airDates[1]);

        calendarCallback= new CalendarCallback(this, getContext());


        changePeriodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //#region create dialog for Calendar
                new SlyCalendarDialog()
                        .setSingle(false)
                        .setSelectedTextColor(Color.parseColor("#000000"))
                        .setSelectedColor(Color.parseColor("#CCF28C0F"))
                        .setHeaderColor(Color.parseColor("#CCF28C0F"))
                        .setCallback(calendarCallback)
                        .show(getFragmentManager(), "TAG_CALENDAR");
                //#endregion
            }
        });
        return view;
    }



    @Override
    public void setData(Object obj)
    {
        progressBarCo2.setVisibility(View.INVISIBLE);
        setActualDataToTextView(obj);

    }

    @Override
    public void setListData(List data) {

        progressBarCo2Graphs.setVisibility(View.INVISIBLE);
        createGraphs.addChartDataToGraph(lv,"Temperatures",graphsData,xAxis,data);
    }



    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void setDates(Date[] dates) {
        if(dates[1] != null)
        {
            this.airDates[0] = dates[0];
            this.airDates[1] = dates[1];
            setDatesToTextView();
            xAxis = createGraphsData.setXAxisValues(dates);
            progressBarCo2Graphs.setVisibility(View.VISIBLE);
            lv.setAdapter(null);
            airPresenter.getAirBetweenData(dates[0], dates[1]);
        }
        else
        {
            if(dates[0].compareTo(this.airDates[1]) > 0)   //check if start date is before end date
                Toast.makeText(getContext(),"Start date is later than end date, please select correct values",Toast.LENGTH_LONG).show();
            else
            {
                this.airDates[0] = dates[0];
                setDatesToTextView();
                xAxis = createGraphsData.setXAxisValues(dates);
                progressBarCo2Graphs.setVisibility(View.VISIBLE);
                lv.setAdapter(null);
                airPresenter.getAirBetweenData(dates[0], this.airDates[1]);
            }
        }
    }

    @Override
    public void setDatesToTextView() {
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
        startDate.setText(dateFormat.format(airDates[0]));
        endDate.setText(dateFormat.format(airDates[1]));
    }

    @Override
    public void setActualDataToTextView(Object obj) {
        co2Value.setText(((Co2)obj).getReading().toString());
        co2Date.setText(((Co2)obj).getDate().toString() + " at " +((Co2)obj).getTime().toString());
    }
}
