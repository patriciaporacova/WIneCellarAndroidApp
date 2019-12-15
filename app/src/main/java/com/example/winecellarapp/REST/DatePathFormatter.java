package com.example.winecellarapp.REST;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**Code provided by JakeWharton
 * from  https://github.com/square/retrofit/issues/291
 */
public class DatePathFormatter
{
        private final Date date;
        private static final ThreadLocal<DateFormat> DF = new ThreadLocal<DateFormat>() {
            @Override public DateFormat initialValue() {
                return new SimpleDateFormat("dd-MM-yyyy");
            }
        };

        public DatePathFormatter(Date date) {
            this.date = date;
        }

        @Override public String toString() {
            return DF.get().format(date);
        }
}
