package com.example.winecellarapp.graphs.setGraphData;

import android.graphics.Color;
import android.graphics.Typeface;


import com.example.winecellarapp.model.Co2;

import com.example.winecellarapp.model.Humidity;
import com.example.winecellarapp.model.Temperature;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**Created by Jakub Piga
 * Edited by: Eric, Patricia
 * Class sets data to 3 different type of the graphs
 * @param <T> generic type
 */
public class SetGraphsData<T>
{

    private DATATYPE action;
    public enum DATATYPE
    {
        TEMPERATURE,
        HUMIDITY,
        AIR,

    }
    /**
     * add data to ChartData object with just one DataSet
     *
     * @return Bar data
     */
    public BarData generateDataBar(String label, List<T> data, ArrayList<Integer> xAxisValue)
    {
        ArrayList<BarEntry> entries = new ArrayList<>();
            switch (action)
            {
                case AIR:
                {
                    //TODO-PATRICIA: change air
                    ArrayList<Co2> air = (ArrayList<Co2>)data;
                    for (int j = 0; j < air.size(); j++)
                    {
                        entries.add(new BarEntry(xAxisValue.get(j),Float.valueOf(String.valueOf(air.get(j).getReading()))));
                    }
                    break;
                }

                case HUMIDITY:
                {

                    //TODO-ERIC: change humidity
                    ArrayList<Humidity> humidities = (ArrayList<Humidity>)data;
                    for (int j = 0; j < humidities.size(); j++) {
                        entries.add(new BarEntry(xAxisValue.get(j), Float.valueOf(String.valueOf(humidities.get(j).getReading()))));
                    }
                    break;
                }
                case TEMPERATURE:
                {
                    ArrayList<Temperature> temperatures = (ArrayList<Temperature>)data;
                    for (int j = 0; j < temperatures.size(); j++)
                    {
                        entries.add(new BarEntry(xAxisValue.get(j),Float.valueOf(String.valueOf(temperatures.get(j).getReading()))));
                    }
                    break;
                }

                default:
                    break;
            }

        BarDataSet d = new BarDataSet(entries, label);
        d.setColors(ColorTemplate.MATERIAL_COLORS);
        d.setHighLightAlpha(255);

        BarData cd = new BarData(d);
        cd.setBarWidth(0.9f);
        return cd;
    }

    /**
     * add data to ChartData object with just one DataSet
     *
     * @return Pie data
     */
    public PieData generateDataPie(String label, List<T> data) {

        ArrayList<PieEntry> entries = new ArrayList<>();
            switch (action)
            {
                case AIR:
                {
                    //TODO-PATRICIA: change air
                    ArrayList<Co2> air = (ArrayList<Co2>)data;
                    for (int j = 0; j < air.size(); j++)
                    {
                        entries.add(new PieEntry(Float.valueOf(String.valueOf(air.get(j).getReading())), ""));
                    }
                    break;
                }

                case HUMIDITY:
                {

                    //TODO-ERIC: change humidity
                    ArrayList<Humidity> humidities = (ArrayList<Humidity>)data;
                    for (int j = 0; j < humidities.size(); j++) {
                        entries.add(new PieEntry(Float.valueOf(String.valueOf(humidities.get(j).getReading())), ""));
                    }
                    ArrayList<Humidity> humidity = (ArrayList<Humidity>)data;
                    for (int j = 0; j < humidity.size(); j++)
                    {
                        entries.add(new PieEntry(Float.valueOf(String.valueOf(humidity.get(j).getReading())), ""));

                    }
                    break;
                }
                case TEMPERATURE:
                {
                    ArrayList<Temperature> temperatures = (ArrayList<Temperature>)data;
                    for (int j = 0; j < temperatures.size(); j++)
                    {
                        entries.add(new PieEntry(Float.valueOf(String.valueOf(temperatures.get(j).getReading())), ""));
                    }
                    break;
                }

                default:
                    break;
            }


        PieDataSet d = new PieDataSet(entries, label);
        // space between slices
        d.setSliceSpace(2f);
        d.setColors(ColorTemplate.MATERIAL_COLORS);

        return new PieData(d);
    }


    /**
     * Add data to lineData chart
     *
     * @return Line data
     */
    public LineData generateDataLine(String label, List<T> data, ArrayList<Integer> xAxisValue) {

        ArrayList<Entry> values1 = new ArrayList<>();

            switch (action)
            {
                case AIR:
                {
                    //TODO-PATRICIA: change air
                    ArrayList<Co2> air = (ArrayList<Co2>)data;
                    for (int j = 0; j < air.size(); j++)
                    {
                        values1.add(new Entry(xAxisValue.get(j), Float.valueOf(String.valueOf(air.get(j).getReading()))));
                    }
                    break;


                }

                case HUMIDITY:
                {

                    //TODO-ERIC: change humidity


                    ArrayList<Humidity> humidity = (ArrayList<Humidity>)data;
                    for (int j = 0; j < humidity.size(); j++)
                    {
                        values1.add(new Entry(xAxisValue.get(j), Float.valueOf(String.valueOf(humidity.get(j).getReading()))));
                    }
                    break;
                }
                case TEMPERATURE:
                {
                    ArrayList<Temperature> temperatures = (ArrayList<Temperature>)data;
                    for (int j = 0; j < temperatures.size(); j++)
                    {
                        values1.add(new Entry(xAxisValue.get(j), Float.valueOf(String.valueOf(temperatures.get(j).getReading()))));
                    }
                    break;
                }

                default:
                    break;
            }

        LineDataSet d1 = new LineDataSet(values1, label);
        d1.setLineWidth(5.5f);
        d1.setCircleRadius(5.5f);
        d1.setHighLightColor(Color.rgb(0, 0, 0));
        d1.setHighlightLineWidth(3.0f);
        d1.setColor(ColorTemplate.MATERIAL_COLORS[0]);
        d1.setCircleColor(ColorTemplate.MATERIAL_COLORS[0]);
        d1.setDrawValues(false);

       /* Example for 2 sets yo
       ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            values2.add(new Entry(i, values1.get(i).getY() - 30));
        }

        LineDataSet d2 = new LineDataSet(values2, "New DataSet (2)");
        d2.setLineWidth(2.5f);
        d2.setCircleRadius(4.5f);
        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setDrawValues(false);
        */
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
       //sets.add(d2);

        return new LineData(sets);
    }


    //TODO-EXTRA: add new chart if there will be time
    /**
     * Add data to bubbleData chart
     *
     * @return Bubble data
     */
    public BubbleData generateDataBubble(String label, List<T> data) {

        ArrayList<BubbleEntry> values1 = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            switch (action)
            {
                case AIR:
                {
                    //TODO-PATRICIA: change air
                    ArrayList<Co2> aires = (ArrayList<Co2>)data;
                    for (int j = 0; j < aires.size(); j++)
                    {
                        values1.add(new BubbleEntry(i,Float.valueOf(String.valueOf(aires.get(i).getReading())),i));
                    }
                    break;
                }

                case HUMIDITY:
                {

                    //TODO-ERIC: change humidity
                    ArrayList<Humidity> humidities = (ArrayList<Humidity>)data;
                    for (int j = 0; j < humidities.size(); j++) {
                        values1.add(new BubbleEntry(i, Float.valueOf(String.valueOf(humidities.get(i).getReading())), i));
                    }
                    ArrayList<Humidity> humidity = (ArrayList<Humidity>)data;
                    for (int j = 0; j < humidity.size(); j++)
                    {
                        values1.add(new BubbleEntry(i,Float.valueOf(String.valueOf(humidity.get(i).getReading())),i));

                    }
                    break;
                }
                case TEMPERATURE:
                {
                    ArrayList<Temperature> temperatures = (ArrayList<Temperature>)data;
                    for (int j = 0; j < temperatures.size(); j++)
                    {
                        values1.add(new BubbleEntry(i,Float.valueOf(String.valueOf(temperatures.get(i).getReading())),i));
                    }
                    break;
                }

                default:
                    break;
            }
        }

        // create a dataset and give it a type
        BubbleDataSet d1 = new BubbleDataSet(values1, label);
        d1.setColor(ColorTemplate.MATERIAL_COLORS[0]);
        d1.setDrawValues(true);
        d1.setDrawIcons(false);

        ArrayList<IBubbleDataSet> dataSets = new ArrayList<>();
        dataSets.add(d1); // add the data sets

        // create a data object with the data sets
        BubbleData data_bubble = new BubbleData(dataSets);
        data_bubble.setDrawValues(false);
        data_bubble.setValueTextSize(8f);
        data_bubble.setValueTextColor(Color.WHITE);
        data_bubble.setHighlightCircleWidth(1.5f);


        return new BubbleData(dataSets);
    }

    public DATATYPE getAction() {
        return action;
    }

    public void setAction(DATATYPE action) {
        this.action = action;
    }
}
