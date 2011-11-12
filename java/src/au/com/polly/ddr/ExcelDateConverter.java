package au.com.polly.ddr;

import com.sun.tools.javac.resources.javac;

import java.util.Calendar;
import java.util.Date;

/**
 * Allows us to obtain a java style date object, for a date/time value within a
 * microsoft spreadsheet.
 *
 *
 */
public class ExcelDateConverter
{
Calendar dayDot;
static ExcelDateConverter singleton = null;
final static Object lock = new java.util.Date();

public static ExcelDateConverter getInstance()
{
    synchronized ( lock )
    {
        if ( singleton == null )
        {
            singleton = new ExcelDateConverter();
        }
    }
    return singleton;
}

protected ExcelDateConverter()
{
    dayDot = Calendar.getInstance();
    dayDot.set( Calendar.YEAR, 1899 );
    dayDot.set( Calendar.MONTH, Calendar.DECEMBER  );
    dayDot.set( Calendar.DAY_OF_MONTH, 31 );
    dayDot.set( Calendar.HOUR_OF_DAY, 0 );
    dayDot.set( Calendar.MINUTE, 0 );
    dayDot.set( Calendar.SECOND, 0 );
    dayDot.set( Calendar.MILLISECOND, 0 );
}

protected Date convert( double xlDate )
{
    Calendar cal;
    Date result;

    cal = convertToCalendar( xlDate );
    result = cal.getTime();
    return result;
}

protected Calendar convertToCalendar( double xlDate )
{
    int days =(int) Math.floor( xlDate );
    int seconds = (int)Math.round( xlDate - Math.floor( xlDate ) ) * 86400;

    // excel incorrectly considers 1900 a leap year. which means that it incorrectly
    // matches serial day 60 as february 29th 1900, when it should be 1st march 1900.
    // so, if we've been given a day after 29th february 1900, we need to subtract
    // one day to make it match the real calendar, rather than bill gate's version.
    // ------------------------------------------------------------------------------
    if ( days > 60 ) { days--; }

    Calendar result = (Calendar) dayDot.clone();
    result.add( Calendar.DATE, days );
    result.add( Calendar.SECOND, seconds );
    // force recomputation of fields...
    result.getTime();

    return result;
}

}
