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
import com.example.winecellarapp.calendar.CalendarCallback;
import com.example.winecellarapp.calendar.ICalendarCallback;
import com.example.winecellarapp.graphs.CreateGraphs;
import com.example.winecellarapp.graphs.setGraphData.CreateGraphsData;
import com.example.winecellarapp.graphs.setGraphData.SetGraphsData;
import com.example.winecellarapp.model.Temperature;
import com.example.winecellarapp.presenters.TemperaturePresenter;
import com.example.winecellarapp.R;
import com.example.winecellarapp.views.DataView;
import com.example.winecellarapp.views.ISensorFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.slybeaver.slycalendarview.SlyCalendarDialog;

/**
 * Created by Jakub Piga
 * Fragment class for temperature*/
public class TemperatureFragment extends Fragment implements DataView, ICalendarCallback, ISensorFragment {

    private View view;
    private TemperaturePresenter temperaturePresenter;
    private CalendarCallback calendarCallback;
    private Date[] dates;
    private ArrayList<Integer> xAxis;
    private SetGraphsData graphsData;
    private CreateGraphsData createGraphsData;
    private CreateGraphs createGraphs;
    private ListView lv;

    private TextView tempValue, tempDate, startDate, endDate;
    private ProgressBar progressBarTemp, progressBarTempGraphs;
    private Button changePeriodBtn;
    private GlobalNotificationSharedPref notificationSharedPref;

    /**onCreateView for fragment*/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.temperature_fragment_layout, container, false);


        tempValue = view.findViewById(R.id.temperature_text);
        tempDate = view.findViewById(R.id.temperature_date);
        progressBarTemp = view.findViewById(R.id.progressBarTempValue);
        progressBarTempGraphs = view.findViewById(R.id.progressBarTempGraphs);
        startDate = view.findViewById(R.id.start_date_temp);
        endDate = view.findViewById(R.id.end_date_temp);
        changePeriodBtn = view.findViewById(R.id.changePeriod);
        notificationSharedPref = new GlobalNotificationSharedPref(getContext());
        if(notificationSharedPref.getSharedPreference().equalsIgnoreCase("temperature"))
            notificationSharedPref.editSharedPreferences("eraseWarning");
        dates = new Date[2];

        //data for the graphs, set data type temperature
        graphsData = new SetGraphsData();
        createGraphsData = new CreateGraphsData();
        createGraphs = new CreateGraphs(getContext());
        graphsData.setAction(SetGraphsData.DATATYPE.TEMPERATURE);
        //list view for the graphs
        lv = view.findViewById(R.id.list_graphs_temperature);
        //callback class for API calls
        temperaturePresenter = new TemperaturePresenter(this);

        //getting latest temperature from database
        temperaturePresenter.getTempSensorData();

        //set first and last date of the actual month and update text views and graphs
        dates = createGraphsData.setFirstAndLastDates();
        xAxis = createGraphsData.setXAxisValues(dates);
        setDatesToTextView();

        //getting latest temperature from database
        temperaturePresenter.getTempBetweenData(dates[0], dates[1]);

        //creating callback for calendar
        calendarCallback = new CalendarCallback(this,getContext());

        //change button on click listener
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

    /**Callback method from TemperaturePresenter
     * It is called when API calls was successful and returned temperature value
     * @param obj contains latest temperature value from database
     */
    @Override
    public void setData(Object obj)
    {
        progressBarTemp.setVisibility(View.INVISIBLE);
        setActualDataToTextView(obj);
    }

    /**Callback method from TemperaturePresenter
     * It is called when API call was successful and returns list with temperatures
     * @param data contains list with temperatures
     */
    @Override
    public void setListData(List data)
    {
        progressBarTempGraphs.setVisibility(View.INVISIBLE);
        createGraphs.addChartDataToGraph(lv,"Temperatures",graphsData,xAxis,data);

    }

    /**Callback method from TemperaturePresenter
     * This method is called when there was problem with API
     * Method prints error message to user
     * @param message contain error message
     */
    @Override
    public void onErrorLoading(String message)
    {
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }

    /**Callback method from CalendarCallback
     * it is called when user pressed save button on calendar dialog window
     * Method saves new dates and updates text views
     * @param dates contains dates which user chose on calendar dialog window
     */
    @Override
    public void setDates(Date[] dates)
    {
        //check if user choose end date
        if(dates[1] != null)
        {
            this.dates[0] = dates[0];
            this.dates[1] = dates[1];
            setDatesToTextView();
            xAxis = createGraphsData.setXAxisValues(dates);
            progressBarTempGraphs.setVisibility(View.VISIBLE);
            lv.setAdapter(null);
            temperaturePresenter.getTempBetweenData(dates[0], dates[1]);
        }
        else
        {
            if(dates[0].compareTo(this.dates[1]) > 0)   //check if start date is before end date
                Toast.makeText(getContext(),"Start date is later than end date, please select correct values",Toast.LENGTH_LONG).show();
            else
            {
                this.dates[0] = dates[0];
                setDatesToTextView();
                xAxis = createGraphsData.setXAxisValues(dates);
                progressBarTempGraphs.setVisibility(View.VISIBLE);
                lv.setAdapter(null);
                temperaturePresenter.getTempBetweenData(dates[0], this.dates[1]);
            }
        }
    }

    /**Set new dates to text views*/
    @Override
    public void setDatesToTextView()
    {
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
        startDate.setText(dateFormat.format(dates[0]));
        endDate.setText(dateFormat.format(dates[1]));
    }

    @Override
    public void setActualDataToTextView(Object obj)
    {
        tempValue.setText(((Temperature)obj).getReading().toString()+" °C");
        tempDate.setText(((Temperature)obj).getDate().toString() + " at " +((Temperature)obj).getTime().toString());
    }

}