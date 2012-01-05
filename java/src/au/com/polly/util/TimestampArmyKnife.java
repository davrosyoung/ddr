/*
 * Copyright (c) 2011-2012 Polly Enterprises Pty Ltd and/or its affiliates.
 *  All rights reserved. This code is not to be distributed in binary
 * or source form without express consent of Polly Enterprises Pty Ltd.
 *
 *
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 *  IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 *  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 *  PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package au.com.polly.util;

import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Deprecated
public class TimestampArmyKnife
{
    private final static Logger logger = Logger.getLogger( TimestampArmyKnife.class );
    private final static int debug = 5;
    private TimeZone tz;
    private final static String[] months = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
    private final static DecimalFormat twoDigits = new DecimalFormat( "00" );
    private final static DecimalFormat threeDigits = new DecimalFormat( "000" );

    // data for formatting and parsing date/timestamps...
    enum DateFormat { WWW_MMM_DD_HH_MM_SS_ZZZ_YYYY, YYYY_MM_DD_HH_MM_SS_mmm, DD_MM_YY_HH_MM_SS };
    protected final static String REGEXP_WWW_MMM_DD_HH_MM_SS_ZZZ_YYYY = "^[A-Za-z]{3} ([A-Za-z]{3}) (\\d+) (\\d+):(\\d+):(\\d+) ([A-Z]{3}) (\\d{4})";
    protected final static String REGEXP_YYYY_MM_DD_HH_MM_SS_mmm = "^(\\d{4})-(\\d{1,2})-(\\d{1,2}) (\\d{1,2}):(\\d{2}):(\\d{2}),(\\d{3})";
    protected final static String REGEXP_DD_MM_YY_HH_MM_SS = "^(\\d{1,2})\\/(\\w{1,3})\\/(\\d{2,4}) (\\d{1,2}):(\\d{2})(:(\\d{2}))?";
    protected final static Pattern wwwMmmDdHhMmSsZzzYyyyPattern = Pattern.compile(REGEXP_WWW_MMM_DD_HH_MM_SS_ZZZ_YYYY);
    protected final static Pattern yyyyMmDdHhMmSsMmmPattern = Pattern.compile(REGEXP_YYYY_MM_DD_HH_MM_SS_mmm);
    protected final static Pattern ddMmYyHhMmSsPattern = Pattern.compile(REGEXP_DD_MM_YY_HH_MM_SS);
    protected static HashMap<DateFormat,Pattern> timeFormatPattern;


    // put it all together in a nice hashmap for easy access...
    // ... format specific code will be used to extract the values
    // -------------------------------------------------------------
    static {
        timeFormatPattern = new HashMap<DateFormat,Pattern>();
        timeFormatPattern.put( DateFormat.WWW_MMM_DD_HH_MM_SS_ZZZ_YYYY, wwwMmmDdHhMmSsZzzYyyyPattern );
        timeFormatPattern.put( DateFormat.YYYY_MM_DD_HH_MM_SS_mmm, yyyyMmDdHhMmSsMmmPattern );
        timeFormatPattern.put( DateFormat.DD_MM_YY_HH_MM_SS, ddMmYyHhMmSsPattern );
    }

    /**
     * Default constructor.
     */
    public TimestampArmyKnife()
    {
        init();
    }

    /**
     * default init() method, sets timezone to that of the local machine.
     */
    public void init()
    {
        init( TimeZone.getDefault() );
    }


    /**
     * init() method specifying actual timezone to use.
     */
    public void init( TimeZone tz )
    {
        setTimeZone( tz );
    }

    /**
     *
     * @param stamp
     * @return
     *
     * Formats the date/time to the minute, using abbreviated format, but using a mnemonic
     * for the month, to prevent confusion between european and US style date formats.
     */
    public String formatStamp( long stamp )
    {
        StringBuilder builder = new StringBuilder();
        String result = null;
        Calendar calendar = Calendar.getInstance(this.tz);
        calendar.setTimeInMillis( stamp );
        builder.append( twoDigits.format( calendar.get( Calendar.DAY_OF_MONTH ) ) );
        builder.append( "/" );
        builder.append( months[ calendar.get( Calendar.MONTH ) ] );
        builder.append( "/" );
        builder.append( twoDigits.format( calendar.get( Calendar.YEAR ) % 100 ) );
        builder.append( " " );
        builder.append( twoDigits.format( calendar.get( Calendar.HOUR_OF_DAY ) ) );
        builder.append( ":" );
        builder.append( twoDigits.format( calendar.get( Calendar.MINUTE ) ) );
        builder.append( ":" );
        builder.append( twoDigits.format( calendar.get( Calendar.SECOND ) ) );
        builder.append( "." );
        builder.append( threeDigits.format( calendar.get( Calendar.MILLISECOND ) ) );
        result = builder.toString();
        return result;
    }

    /**
     *
     * @param dom
     * @param month
     * @param year
     * @param hour
     * @param minute
     * @param second
     * @return
     * @throws NumberFormatException
     *
     * Determine the number of seconds since 1st January 1970 GMT, that the specified
     * date/time values (within the timezone for this object) represents.
     */
    public long calculateStamp(
        String dom,
        String month,
        String year,
        String hour,
        String minute,
        String second
    ) throws NumberFormatException
    {
        return calculateStamp( dom, month, year, hour, minute, second, "0" );
    }

    /**
     *
     * @param dom
     * @param month
     * @param year
     * @param hour
     * @param minute
     * @param second
     * @param millisecond
     * @return
     * @throws NumberFormatException
     *
     * Determine the number of seconds since 1st January 1970 GMT, that the specified
     * date/time values (within the timezone for this object) represents.
     */
    public long calculateStamp(
        String dom,
        String month,
        String year,
        String hour,
        String minute,
        String second,
        String millisecond
    ) throws NumberFormatException
    {
        long result;
        int domParam;
        int monthParam = -1;
        int yearParam;
        int hourParam;
        int minuteParam;
        int secondParam;
        int milliSecondParam;
        int rawYear;

        domParam = Integer.parseInt(dom);
        if ( Character.isDigit(month.charAt(0)) )
        {
            monthParam = Integer.parseInt(month) - 1;
        } else {
            String refined = month.toUpperCase().trim();
            for( int i = 0; i < months.length; i++ )
            {
                if ( refined.startsWith( months[ i ] ) )
                {
                    monthParam = i;
                    break;
                }
            }
            if ( monthParam < 0 )
            {
                throw new IllegalArgumentException( "Failed to determine month from \"" + month + "\"" );
            }
        }
        rawYear  = Integer.parseInt(year);
        if ( rawYear < 100 )
        {
            yearParam = ( rawYear < 20 ) ? rawYear + 2000 : rawYear + 1900;
        } else {
            yearParam = rawYear;
        }
        hourParam = Integer.parseInt(hour);
        minuteParam = Integer.parseInt(minute);
        secondParam = ( second != null ) ? Integer.parseInt(second) : 0;
        milliSecondParam = ( millisecond != null ) ? Integer.parseInt(millisecond) : 0;

        result = calculateStamp( domParam, monthParam, yearParam, hourParam, minuteParam, secondParam, milliSecondParam );
        return result;
    }

    /**
     *
     * @param dom day of the month, 1 - 31
     * @param month month of the year. as per Calendar.MONTH, 0=jan,11=dec
     * @param year year AD (so 2007 is 2007, not 107 as per perl)
     * @param hour
     * @param minute
     * @param second
     * @return number of milliseconds since 1st January 1970 00:00:00 GMT.
     *
     *
     * Determine the number of seconds since 1st January 1970 GMT, that the specified
     * date/time values (within the timezone for this object) represents.
     */
    public long calculateStamp(
        int dom,
        int month,
        int year,
        int hour,
        int minute,
        int second
    )
    {
        return calculateStamp( dom, month, year, hour, minute, second,  0);
    }

    /**
     *
     * @param dom day of the month, 1 - 31
     * @param month month of the year. as per Calendar.MONTH, 0=jan,11=dec
     * @param year year AD (so 2007 is 2007, not 107 as per perl)
     * @param hour
     * @param minute
     * @param second
     * @param millisecond
     * @return number of milliseconds since 1st January 1970 00:00:00 GMT.
     *
     *
     * Determine the number of seconds since 1st January 1970 GMT, that the specified
     * date/time values (within the timezone for this object) represents.
     */
    public long calculateStamp(
        int dom,
        int month,
        int year,
        int hour,
        int minute,
        int second,
        int millisecond
    )
    {
        long result;
        Calendar calendar = Calendar.getInstance( this.tz );
        calendar.set( Calendar.YEAR, year );
        calendar.set( Calendar.MONTH, month );
        calendar.set( Calendar.DAY_OF_MONTH, dom );
        calendar.set( Calendar.HOUR_OF_DAY, hour );
        calendar.set( Calendar.MINUTE, minute );
        calendar.set( Calendar.SECOND, second );
        calendar.set( Calendar.MILLISECOND, millisecond );

        result = calendar.getTimeInMillis();

        return result;
    }


    /**
     * @param dateTimeText date/time as a string
     * @return number of milliseconds since 1st Jan 1970, 00:00:00 GMT
     *
     *
     * Will parse date/time stamps in the following three formats;
     * <ul>
     *  <li>Fri Aug 17 08:20:00 EST 2007</li>
     *  <li>2007-08-16 18:20:21,103</li>
     *  <li>16/08/07 18:20:00</li>
     * </ul>
     * Timezone information is ignroed in the date/time, and the timezone information
     * from this object is used.
     *
     *
     * Whilst the date/time stamps are in different formats across the various log files,
     * trying to debug and test parsing for each of the different formats became too difficult,
     * and they were merged into the one place.
     */
    public long parse( String dateTimeText )
    {
        long result = -1L;
        String dom = null;
        String month = null;
        String year = null;
        String hour = null;
        String minute = null;
        String seconds = null;
        String milliSeconds = null;

        // determine which timestamp pattern applies...
        // ----------------------------------------------
        for( DateFormat format : DateFormat.values() )
        {
            Pattern pattern = timeFormatPattern.get( format );
            Matcher matcher = pattern.matcher( dateTimeText );
            if ( matcher.find() )
            {
                switch( format )
                {
                    case DD_MM_YY_HH_MM_SS:
                        dom = matcher.group( 1 );
                        month = matcher.group( 2 );
                        year = matcher.group( 3 );
                        hour = matcher.group( 4 );
                        minute = matcher.group( 5 );
                        seconds = matcher.group( 7 );
                        break;

                    case WWW_MMM_DD_HH_MM_SS_ZZZ_YYYY:
                        month = matcher.group( 1 );
                        dom = matcher.group( 2 );
                        hour = matcher.group( 3 );
                        minute = matcher.group( 4 );
                        seconds = matcher.group( 5 );
                        year = matcher.group( 7 );
                        break;

                    case YYYY_MM_DD_HH_MM_SS_mmm:
                        dom = matcher.group( 3 );
                        month = matcher.group( 2 );
                        year = matcher.group( 1 );
                        hour = matcher.group( 4 );
                        minute = matcher.group( 5 );
                        seconds = matcher.group( 6 );
                        milliSeconds = matcher.group( 7 );
                        break;
                }

                logger.debug( "Determined year=\"" + year + "\", month=\"" + month + "\", dom=\"" + dom + "\", hour=\"" + hour + "\", minute=\"" + minute + "\", seconds=\"" + seconds + "\", milliseconds=\"" + ( milliSeconds != null ? milliSeconds : "<NULL>" ) );

                result = calculateStamp( dom, month, year, hour, minute, seconds, milliSeconds );
            }
        }
        return result;
    }

    /**
     * @param dateTimeText date/time as a string
     * @return number of milliseconds since 1st Jan 1970, 00:00:00 GMT
     *
     *
     * Will parse date/time stamps in the following three formats;
     * <ul>
     *  <li>Fri Aug 17 08:20:00 EST 2007</li>
     *  <li>2007-08-16 18:20:21,103</li>
     *  <li>16/08/07 18:20:00</li>
     * </ul>
     * Timezone information is ignored in the date/time, and the timezone information
     * from this object is used.
     *
     * Whilst the date/time stamps are in different formats across the various log files,
     * trying to debug and test parsing for each of the different formats became too difficult,
     * and they were merged into the one place.
     */
    public String extractTimestampText( String dateTimeText )
    {
        String result = null;

        // determine which timestamp pattern applies...
        // ----------------------------------------------
        for( DateFormat format : DateFormat.values() )
        {
            Pattern pattern = timeFormatPattern.get( format );
            Matcher matcher = pattern.matcher( dateTimeText );
            if ( matcher.find() )
            {
                result = matcher.group( 0 );
                break;
            }
        }
        return result;
    }

    /**
     * Specify the timezone to use for calculations and formatting.
     *
     * @param tz
     */
    public void setTimeZone( TimeZone tz )
    {
        this.tz = tz;
    }

    public TimeZone __unit_test_backdoor_getTimeZone()
    {
        return this.tz;
    }
}
