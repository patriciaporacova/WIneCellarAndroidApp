package com.example.winecellarapp.graphs;

import android.content.Context;
import android.widget.ListView;

import com.example.winecellarapp.graphs.adapters.ChartDataAdapter;
import com.example.winecellarapp.graphs.graphs.BarChartItem;
import com.example.winecellarapp.graphs.graphs.ChartItem;
import com.example.winecellarapp.graphs.graphs.LineChartItem;
import com.example.winecellarapp.graphs.graphs.PieChartItem;
import com.example.winecellarapp.graphs.setGraphData.SetGraphsData;

import java.util.ArrayList;
import java.util.List;

/**
 * Create graphs and add them to the ListView
 * @param <T> for implementing generic values
 */
public class CreateGraphs<T>
{
    private Context context;

    public CreateGraphs(Context context)
    {
        this.context = context;
    }

    /**Method create new  list with graphs and sets adapter with new created graphs
     *
     * @param data contains list with temperatures from which should be graphs created
     */
    public void addChartDataToGraph(ListView lv, String label, SetGraphsData graphsData, ArrayList<Integer> xAxis, List<T> data)
    {
                ArrayList<ChartItem> list = new ArrayList<>();
                list.add(new LineChartItem(graphsData.generateDataLine(label,data,xAxis), context));
                list.add(new BarChartItem(graphsData.generateDataBar(label,data,xAxis), context));
                list.add(new PieChartItem(graphsData.generateDataPie(label,data), context));
                ChartDataAdapter cda = new ChartDataAdapter(context, list);
                lv.setAdapter(cda);
    }
}
