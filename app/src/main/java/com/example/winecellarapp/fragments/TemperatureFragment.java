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
import androidx.viewpager.widget.ViewPager;

import com.example.winecellarapp.DataView;
import com.example.winecellarapp.calendar.CalendarCallback;
import com.example.winecellarapp.calendar.ICalendarCallback;
import com.example.winecellarapp.graphs.adapters.ChartDataAdapter;
import com.example.winecellarapp.graphs.graphs.BarChartItem;
import com.example.winecellarapp.graphs.graphs.BubbleChartItem;
import com.example.winecellarapp.graphs.graphs.ChartItem;
import com.example.winecellarapp.graphs.graphs.LineChartItem;
import com.example.winecellarapp.graphs.graphs.PieChartItem;
import com.example.winecellarapp.graphs.setGraphData.SetGraphsData;
import com.example.winecellarapp.model.Temperature;
import com.example.winecellarapp.presenters.TemperaturePresenter;
import com.example.winecellarapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;



import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import ru.slybeaver.slycalendarview.SlyCalendarDialog;

/**Fragment class for temperature*/
public class TemperatureFragment extends Fragment implements DataView, ICalendarCallback {

    private View view;
    private TemperaturePresenter temperaturePresenter;
    private CalendarCallback calendarCallback;
    private Date[] dates;
    private SetGraphsData graphsData;
    private ListView lv;

    private TextView tempValue, tempDate, startDate, endDate;
    private ProgressBar progressBarTemp, progressBarTempGraphs;
    private Button changePeriodBtn;



   /* @BindView(R.id.temperature_text)
    TextView tempValue;
    @BindView(R.id.temperature_date)
    TextView tempDate;
    @BindView(R.id.start_date_temp)
    TextView startDate;
    @BindView(R.id.end_date_temp)
    TextView endDate;
    @BindView(R.id.progressBarTempValue)
    ProgressBar progressBarTemp;
    @BindView(R.id.progressBarTempGraphs)
    ProgressBar progressBarTempGraphs;
    @BindView(R.id.changePeriod)
    Button changePeriodBtn;*/


    /**onCreateView for fragment*/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.temperature_fragment_layout, container, false);

        //ButterKnife.bind(this, view);

        tempValue = view.findViewById(R.id.temperature_text);
        tempDate = view.findViewById(R.id.temperature_date);
        progressBarTemp = view.findViewById(R.id.progressBarTempValue);
        progressBarTempGraphs = view.findViewById(R.id.progressBarTempGraphs);
        startDate = view.findViewById(R.id.start_date_temp);
        endDate = view.findViewById(R.id.end_date_temp);
        changePeriodBtn = view.findViewById(R.id.changePeriod);

        dates = new Date[2];

        //data for the graphs, set data type temperature
        graphsData = new SetGraphsData();
        graphsData.setAction(SetGraphsData.DATATYPE.TEMPERATURE);
        //list view for the graphs
        lv = view.findViewById(R.id.list_graphs_temperature);
        //callback class for API calls
        temperaturePresenter = new TemperaturePresenter(this);

        //getting latest temperature from database
        temperaturePresenter.getTempSensorData();

        //set first and last date of the actual month and update text views and graphs
        setFirstAndLastDates();
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
        tempValue.setText(((Temperature)obj).getReading().toString());
        tempDate.setText(((Temperature)obj).getDate().toString() + " at " +((Temperature)obj).getTime().toString());
    }

    /**Callback method from TemperaturePresenter
     * It is called when API call was successful and returns list with temperatures
     * @param data contains list with temperatures
     */
    @Override
    public void setListData(List data)
    {
        progressBarTempGraphs.setVisibility(View.INVISIBLE);
        createGraphs((List<Temperature>)data);

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
                progressBarTempGraphs.setVisibility(View.VISIBLE);
                lv.setAdapter(null);
                temperaturePresenter.getTempBetweenData(dates[0], this.dates[1]);
            }
        }
    }

    /**Set new dates to text views*/
    private void setDatesToTextView()
    {
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
        startDate.setText(dateFormat.format(dates[0]));
        endDate.setText(dateFormat.format(dates[1]));
    }

    /**Set first and last date in current month*/
    private void setFirstAndLastDates()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        dates[0] = cal.getTime();
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE)); // changed calendar to cal
        dates[1] = cal.getTime();
    }

    /**Method create new  list with graphs and sets adapter with new created graphs
     *
     * @param temperatures contains list with temperatures from which should be graphs created
     */
    private void createGraphs(List<Temperature> temperatures)
    {
        ArrayList<ChartItem> list = new ArrayList<>();
        list.add(new LineChartItem(graphsData.generateDataLine("Temperature",temperatures), getContext()));
        list.add(new BarChartItem(graphsData.generateDataBar("Temperatures",temperatures), getContext()));
        list.add(new PieChartItem(graphsData.generateDataPie("Temperatures",temperatures), getContext()));
        ChartDataAdapter cda = new ChartDataAdapter(getContext(), list);
        lv.setAdapter(cda);
    }
}
