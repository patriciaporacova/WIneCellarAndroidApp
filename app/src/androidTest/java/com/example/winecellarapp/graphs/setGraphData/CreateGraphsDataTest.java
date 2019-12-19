package com.example.winecellarapp.graphs.setGraphData;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class CreateGraphsDataTest {

    CreateGraphsData createGraphsData;
    @Before
    public void setUp() throws Exception
    {
        createGraphsData = new CreateGraphsData();
    }

    @Test
    public void setFirstAndLastDates()
    {
       Date[] date =  createGraphsData.setFirstAndLastDates();

        //Testing in december it has 31 days
        assertEquals(date[1].getDate(),31);

        //always 1(First day)
        assertEquals(date[0].getDate(),1);

    }

    @Test
    public void setXAxisValues()
    {
        Date[] dates = new Date[2];
        Calendar c = Calendar.getInstance();
        c.set(2019,12,5);
        dates[0] = c.getTime();
        c.set(2019,12,24);
        dates[1] = c.getTime();
        ArrayList<Integer> result = createGraphsData.setXAxisValues(dates);

        assertEquals(result.size(),20);
        assertEquals((int)result.get(0),5);
        assertEquals((int)result.get(19),24);
    }
}