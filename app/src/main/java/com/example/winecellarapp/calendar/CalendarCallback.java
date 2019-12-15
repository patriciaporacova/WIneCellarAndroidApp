package com.example.winecellarapp.calendar;

import android.content.Context;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;
import ru.slybeaver.slycalendarview.SlyCalendarDialog;

/**Created by Jakub Piga
 * Class is working with result of user input from calendar dialog and sending
 * it to view through the ICalendarCallback interface
 */
public class CalendarCallback implements SlyCalendarDialog.Callback
{
    private Context context;
    private ICalendarCallback callback;

    /**Constructor for the class*/
    public CalendarCallback(ICalendarCallback callback,Context context)
    {
        this.callback = callback;
        this.context = context;
    }

    /**Method is called when user presses Cancel button in dialog window*/
    @Override
    public void onCancelled()
    {
        Toast.makeText(context,"Time period wasn't changed",Toast.LENGTH_LONG).show();

    }

    /**Method is called when user presses Save button in dialog window*/
    @Override
    public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {
        Date[] dates = new Date[2];

        //checking user input from calendar
        if (firstDate != null)
        {
            if (secondDate == null)
                dates[0] = firstDate.getTime();
            else
            {
                dates[0] = firstDate.getTime();
                dates[1] = secondDate.getTime();
            }
        }
        callback.setDates(dates);

    }
}
