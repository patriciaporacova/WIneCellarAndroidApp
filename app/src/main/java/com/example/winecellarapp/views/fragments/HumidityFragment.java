package com.example.winecellarapp.views.fragments;

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

import com.example.winecellarapp.notification.GlobalNotificationSharedPref;
import com.example.winecellarapp.R;
import com.example.winecellarapp.calendar.CalendarCallback;
import com.example.winecellarapp.calendar.ICalendarCallback;
import com.example.winecellarapp.graphs.CreateGraphs;
import com.example.winecellarapp.graphs.setGraphData.CreateGraphsData;
import com.example.winecellarapp.graphs.setGraphData.SetGraphsData;
import com.example.winecellarapp.model.Humidity;
import com.example.winecellarapp.presenters.HumidityPresenter;
import com.example.winecellarapp.views.DataView;
import com.example.winecellarapp.views.ISensorFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.slybeaver.slycalendarview.SlyCalendarDialog;

public class HumidityFragment extends Fragment implements DataView, ICalendarCallback, ISensorFragment
{


    //TODO-ERIC: Create same layout like temperature fragment(feel free to create your own design or copy paste temperature fragment and change variables and colors)
    private View view;
    private HumidityPresenter humidityPresenter;
    private CalendarCallback calendarCallback;
    private Date[] dates;
    private SetGraphsData graphsData;
    private CreateGraphsData createGraphsData;
    private CreateGraphs createGraphs;
    private ArrayList<Integer> xAxis;
    private ListView lv;

    private TextView humidityValue, humidityDate, startDate, endDate;
    private ProgressBar progressBarHumidity, progressBarHumidityGraphs;
    private Button changePeriodBtn;
    private GlobalNotificationSharedPref notificationSharedPref;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.humidity_fragment_layout, container, false);

        humidityValue = view.findViewById(R.id.humidity_text);
        humidityDate = view.findViewById(R.id.humidity_date);
        progressBarHumidity = view.findViewById(R.id.progressBarHumidityValue);
        progressBarHumidityGraphs = view.findViewById(R.id.progressBarHumidityGraphs);
        startDate = view.findViewById(R.id.start_date_humidity);
        endDate = view.findViewById(R.id.end_date_humidity);
        changePeriodBtn = view.findViewById(R.id.changePeriod);
        notificationSharedPref = new GlobalNotificationSharedPref(getContext());
        if(notificationSharedPref.getSharedPreference().equalsIgnoreCase("humidity"))
            notificationSharedPref.editSharedPreferences("eraseWarning");
        dates = new Date[2];

        //data for the graphs, set data type humidity
        graphsData = new SetGraphsData();
        createGraphsData = new CreateGraphsData();
        createGraphs = new CreateGraphs(getContext());
        graphsData.setAction(SetGraphsData.DATATYPE.HUMIDITY);
        //list view for the graphs
        lv = view.findViewById(R.id.list_graphs_humidity);
        //callback class for API calls
        humidityPresenter = new HumidityPresenter(this);

        //getting latest humidity from database
        humidityPresenter.getHumiditySensorData();

        //set first and last date of the actual month and update text views and graphs
        dates = createGraphsData.setFirstAndLastDates();
        xAxis = createGraphsData.setXAxisValues(dates);
        setDatesToTextView();


        //getting latest temperature from database
        humidityPresenter.getHumidityBetweenData(dates[0], dates[1]);

        //creating callback for calendar
        calendarCallback = new CalendarCallback(this, getContext());

        //change button on click listener
        changePeriodBtn.setOnClickListener(new View.OnClickListener()
        {
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

    /**
     * Callback method from HumidityPresenter
     * It is called when API calls was successful and returned humidity value
     *
     * @param obj contains latest humidity value from database
     */
    @Override
    public void setData(Object obj)
    {
        progressBarHumidity.setVisibility(View.INVISIBLE);
        setActualDataToTextView(obj);
    }

    /**
     * Callback method from HumidityPresenter
     * It is called when API call was successful and returns list with humidity
     *
     * @param data contains list with humidity
     */
    @Override
    public void setListData(List data)
    {
        progressBarHumidityGraphs.setVisibility(View.INVISIBLE);
        createGraphs.addChartDataToGraph(lv,"Humidity",graphsData,xAxis,data);


    }

    /**
     * Callback method from TemperaturePresenter
     * This method is called when there was problem with API
     * Method prints error message to user
     *
     * @param message contain error message
     */
    @Override
    public void onErrorLoading(String message)
    {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * Callback method from CalendarCallback
     * it is called when user pressed save button on calendar dialog window
     * Method saves new dates and updates text views
     *
     * @param dates contains dates which user chose on calendar dialog window
     */
    @Override
    public void setDates(Date[] dates)
    {
        //check if user choose end date
        if (dates[1] != null)
        {
            this.dates[0] = dates[0];
            this.dates[1] = dates[1];
            setDatesToTextView();
            xAxis = createGraphsData.setXAxisValues(dates);
            progressBarHumidityGraphs.setVisibility(View.VISIBLE);
            lv.setAdapter(null);
            humidityPresenter.getHumidityBetweenData(dates[0], dates[1]);
        } else
        {
            if (dates[0].compareTo(this.dates[1]) > 0)   //check if start date is before end date
                Toast.makeText(getContext(), "Start date is later than end date, please select correct values", Toast.LENGTH_LONG).show();
            else
            {
                this.dates[0] = dates[0];
                setDatesToTextView();
                xAxis = createGraphsData.setXAxisValues(dates);
                progressBarHumidityGraphs.setVisibility(View.VISIBLE);
                lv.setAdapter(null);
                humidityPresenter.getHumidityBetweenData(dates[0], this.dates[1]);
            }
        }
    }

    /**
     * Set new dates to text views
     */
    @Override
    public void setDatesToTextView()
    {
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
        startDate.setText(dateFormat.format(dates[0]));
        endDate.setText(dateFormat.format(dates[1]));
    }

    @Override
    public void setActualDataToTextView(Object obj) {
        humidityValue.setText(((Humidity) obj).getReading().toString());
        humidityDate.setText(((Humidity) obj).getDate().toString() + " at " + ((Humidity) obj).getTime().toString());
    }
}