package com.example.winecellarapp.Graphs.setGraphData;

import android.graphics.Color;

import com.example.winecellarapp.Model.Temperature;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class SetGraphsData<T>
{
    private DATATYPE action;
    public enum DATATYPE
    {
        TEMPERATURE,
        HUMUDITY,
        AIR,

    }
    /**
     * add data to ChartData object with just one DataSet
     *
     * @return Bar data
     */
    public BarData generateDataBar(String label, ArrayList<T> data)
    {
        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < data.size(); i++)
        {
            switch (action)
            {
                case AIR:
                {
                    //TODO:change air
                    /*ArrayList<Temperature> air = (ArrayList<Temperature>)data;
                    entries.add(new BarEntry(i,Float.valueOf(String.valueOf(air.get(i).getReading()))));*/
                    break;
                }

                case HUMUDITY:
                    //TODO:change humidity
                   /* ArrayList<Temperature> humidity = (ArrayList<Temperature>)data;
                    entries.add(new BarEntry(i,Float.valueOf(String.valueOf(humidity.get(i).getReading()))));*/
                    break;
                case TEMPERATURE:
                {
                    //TODO:delete multiple data
                    ArrayList<Temperature> temperatures = (ArrayList<Temperature>)data;
                    entries.add(new BarEntry(i,Float.valueOf(String.valueOf(temperatures.get(i).getReading()))));
                    entries.add(new BarEntry(i+1,Float.valueOf(String.valueOf(temperatures.get(i).getReading()+4))));
                    entries.add(new BarEntry(i+2,Float.valueOf(String.valueOf(temperatures.get(i).getReading()-5))));
                    entries.add(new BarEntry(i+3,Float.valueOf(String.valueOf(temperatures.get(i).getReading()+2))));

                    break;
                }

                default:
                    break;
            }

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
    public PieData generateDataPie(String label, ArrayList<T> data) {

        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < data.size(); i++)
        {
            switch (action)
            {
                case AIR:
                {
                    //TODO:change air
                    /*ArrayList<Temperature> air = (ArrayList<Temperature>)data;
                    entries.add(new BarEntry(i,Float.valueOf(String.valueOf(air.get(i).getReading()))));*/
                    break;
                }

                case HUMUDITY:
                    //TODO:change humidity
                   /* ArrayList<Temperature> humidity = (ArrayList<Temperature>)data;
                    entries.add(new BarEntry(i,Float.valueOf(String.valueOf(humidity.get(i).getReading()))));*/
                    break;
                case TEMPERATURE:
                {
                    //TODO:delete multiple data
                    ArrayList<Temperature> temperatures = (ArrayList<Temperature>)data;
                    entries.add(new PieEntry(Float.valueOf(String.valueOf(temperatures.get(i).getReading())), label + " " + (i+1)));
                    entries.add(new PieEntry(Float.valueOf(String.valueOf(temperatures.get(i).getReading() +2)), label + " " + (i+1)));
                    entries.add(new PieEntry(Float.valueOf(String.valueOf(temperatures.get(i).getReading()-5)), label + " " + (i+1)));
                    entries.add(new PieEntry(Float.valueOf(String.valueOf(temperatures.get(i).getReading()+10)), label + " " + (i+1)));

                    break;
                }

                default:
                    break;
            }
        }


        PieDataSet d = new PieDataSet(entries, "");
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
    public LineData generateDataLine(String label, ArrayList<T> data) {

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            switch (action)
            {
                case AIR:
                {
                    //TODO:change air
                    /*ArrayList<Temperature> air = (ArrayList<Temperature>)data;
                    entries.add(new BarEntry(i,Float.valueOf(String.valueOf(air.get(i).getReading()))));*/
                    break;
                }

                case HUMUDITY:
                    //TODO:change humidity
                   /* ArrayList<Temperature> humidity = (ArrayList<Temperature>)data;
                    entries.add(new BarEntry(i,Float.valueOf(String.valueOf(humidity.get(i).getReading()))));*/
                    break;
                case TEMPERATURE:
                {
                    //TODO:delete multiple data
                    ArrayList<Temperature> temperatures = (ArrayList<Temperature>)data;
                    values1.add(new Entry(i, Float.valueOf(String.valueOf(temperatures.get(i).getReading()))));
                    values1.add(new Entry(i+1, Float.valueOf(String.valueOf(temperatures.get(i).getReading()))));
                    values1.add(new Entry(i+2, Float.valueOf(String.valueOf(temperatures.get(i).getReading()+2))));
                    values1.add(new Entry(i+3, Float.valueOf(String.valueOf(temperatures.get(i).getReading()-7))));

                    break;
                }

                default:
                    break;
            }
        }

        LineDataSet d1 = new LineDataSet(values1, label);
        d1.setLineWidth(12.5f);
        d1.setCircleRadius(15.5f);
        d1.setHighLightColor(Color.rgb(0, 0, 0));
        d1.setHighlightLineWidth(5.0f);
        d1.setColor(ColorTemplate.MATERIAL_COLORS[0]);
        d1.setCircleColor(ColorTemplate.MATERIAL_COLORS[0]);
        d1.setDrawValues(false);

       /* ArrayList<Entry> values2 = new ArrayList<>();

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

    public DATATYPE getAction() {
        return action;
    }

    public void setAction(DATATYPE action) {
        this.action = action;
    }
}
