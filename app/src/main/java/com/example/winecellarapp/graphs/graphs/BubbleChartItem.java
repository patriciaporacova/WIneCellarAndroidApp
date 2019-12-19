package com.example.winecellarapp.graphs.graphs;
/*
CHARTS LIBRARY:
(implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0')
Copyright 2019 Philipp Jahoda

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.annotation.SuppressLint;

import com.example.winecellarapp.R;
import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.ChartData;

public class BubbleChartItem extends ChartItem
{


        private LineChart chart;
        private SeekBar seekBarX, seekBarY;
        private TextView tvX, tvY;
        private final Typeface mTf;

        public BubbleChartItem(ChartData<?> cd, Context c)
        {
        super(cd);
        mTf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");
        }

        @Override
        public int getItemType() {

        return TYPE_BUBBLECHART;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, Context c)
        {
            ViewHolder holder;

        if (convertView == null) {

            holder = new BubbleChartItem.ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_bubblechart, null);
            holder.chart = convertView.findViewById(R.id.bubble_chart);

            convertView.setTag(holder);

        }
        else {
            holder = (BubbleChartItem.ViewHolder) convertView.getTag();
        }

            holder.chart.getDescription().setEnabled(false);

            holder.chart.setDrawGridBackground(false);

            holder.chart.setTouchEnabled(true);

            // enable scaling and dragging
            holder.chart.setDragEnabled(true);
            holder.chart.setScaleEnabled(true);

            holder.chart.setMaxVisibleValueCount(200);
            holder.chart.setPinchZoom(true);
            holder.chart.setVisibleXRange(1,33);
            XAxis xAxis = holder.chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTypeface(mTf);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawAxisLine(true);

            YAxis leftAxis = holder.chart.getAxisLeft();
            leftAxis.setTypeface(mTf);
            leftAxis.setLabelCount(5, false);
            leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

            YAxis rightAxis = holder.chart.getAxisRight();
            rightAxis.setTypeface(mTf);
            rightAxis.setLabelCount(5, false);
            rightAxis.setDrawGridLines(false);
            rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
            // do not forget to refresh the chart
            holder.chart.animateX(750);
            holder.chart.getAxisRight().setEnabled(false);
            // set data
            holder.chart.invalidate();
            holder.chart.setData((BubbleData) mChartData);

            return convertView;
        }

    private static class ViewHolder {
        BubbleChart chart;
    }
}
