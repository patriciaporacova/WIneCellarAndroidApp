package com.example.winecellarapp.graphs.setGraphData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jakub Piga
 * Class creates data necessary for creating correct graphs
 */
public class CreateGraphsData
{



    /**Set first and last date in current month*/
    public Date[] setFirstAndLastDates()
    {
        Date[] dates = new Date[2];
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        dates[0] = cal.getTime();
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE)); // changed calendar to cal
        dates[1] = cal.getTime();
        return   dates;
    }

    /**
     * FIll up ArrayList with values needed for xAxis labels
     * @param dates with start and end date of the chosen values
     * @return List of the Integers with labels for graphs
     */
    public ArrayList<Integer> setXAxisValues(Date[] dates)
    {
        ArrayList<Integer> values = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(dates[0]);
        while(c.getTime().compareTo(dates[1]) < 0) //"Date1 is before Date2"
        {
            values.add(c.get(Calendar.DAY_OF_MONTH));
            c.add(Calendar.DATE,1); //number of days to add
        }
        if(values.size() < 1) //dates are the same, it means graphs will show day avarage(24 values)
        {
            for (int i = 0; i < 24; i++)
            {
                values.add(i);
            }
        }
        else
            values.add(c.get(Calendar.DAY_OF_MONTH)); //add last date because while didn't add same date

        return values;
    }
}
