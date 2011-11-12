package au.com.polly.util;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 10/11/11
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class MonthParser
{
    static Map<String,Integer> monthMap =  new HashMap<String,Integer>();
    static Map<Integer,Integer> numericMonthMap = new HashMap<Integer,Integer>();
    static Pattern numberPattern = Pattern.compile( "\\d+" );


    static {
        monthMap.put( "jan", Calendar.JANUARY );
        monthMap.put( "feb", Calendar.FEBRUARY );
        monthMap.put( "mar", Calendar.MARCH );
        monthMap.put( "apr", Calendar.APRIL );
        monthMap.put( "may", Calendar.MAY );
        monthMap.put( "mai", Calendar.MAY );
        monthMap.put( "jun", Calendar.JUNE );
        monthMap.put( "jul", Calendar.JULY );
        monthMap.put( "aug", Calendar.AUGUST );
        monthMap.put( "sep", Calendar.SEPTEMBER );
        monthMap.put( "oct", Calendar.OCTOBER );
        monthMap.put( "okt", Calendar.OCTOBER );
        monthMap.put( "nov", Calendar.NOVEMBER );
        monthMap.put( "dec", Calendar.DECEMBER );

        numericMonthMap.put( 1, Calendar.JANUARY );
        numericMonthMap.put( 2, Calendar.FEBRUARY );
        numericMonthMap.put( 3, Calendar.MARCH );
        numericMonthMap.put( 4, Calendar.APRIL );
        numericMonthMap.put( 5, Calendar.MAY );
        numericMonthMap.put( 6, Calendar.JUNE );
        numericMonthMap.put( 7, Calendar.JULY );
        numericMonthMap.put( 8, Calendar.AUGUST );
        numericMonthMap.put( 9, Calendar.SEPTEMBER );
        numericMonthMap.put( 10, Calendar.OCTOBER );
        numericMonthMap.put( 11, Calendar.NOVEMBER );
        numericMonthMap.put( 12, Calendar.DECEMBER );

    }



public static int parseMonth( String text )
{
    String key;
    int result = -1;

    if ( text.length() == 0 )
    {
        throw new IllegalArgumentException( "Unable to parse empty month specifier!! Give me a break!!" );
    }

    key = text.trim().toLowerCase();

    Matcher matcher = numberPattern.matcher( key );
    if ( matcher.matches() )
    {
        int val = Integer.parseInt( key );
        if ( numericMonthMap.containsKey( val ) )
        {
            result = numericMonthMap.get( val );
        } else {
            throw new IllegalArgumentException( "Month specifier \"" + key + "\" must be between 1 & 12!!" );
        }
    } else {

        if ( key.length() < 3 )
        {
            throw new IllegalArgumentException( "Month specifier must be at least three characters in length. [" + text + "] was specified!" );
        }
        key = key.toLowerCase().substring( 0, 3 );
        if ( monthMap.containsKey( key ) )
        {
            result = monthMap.get( key );
        } else {
            throw new IllegalArgumentException( "Month specifier \"" + text + "\" not recognized. Sorry about that!!" );
        }
    }

    return result;
}

}
