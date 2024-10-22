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

import sun.util.resources.CalendarData_ro;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 10/11/11
 * Time: 12:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class AussieDateParser extends BaseDateParser implements DateParser
{
final static String dateSeparator = "((\\s+)|(\\s?[\\/\\-\\.]\\s?))";
final static String oneOrTwoDigits = "(\\d{1,2})";
final static String dayOfMonthRegexp = oneOrTwoDigits + "([snrt][tdh])?";
final static String milliSecondRegexp = "\\.(\\d{3})";
final static String monthRegexp = "((\\d+)|([a-zA-Z]{3,}))";
final static String yearRegexp = "(\\d{1,4})";
final static String dateRegexp = dayOfMonthRegexp + dateSeparator + monthRegexp + dateSeparator + yearRegexp;
final static String timeRegexp = oneOrTwoDigits + ":" + oneOrTwoDigits + "(:" + oneOrTwoDigits + "(" + milliSecondRegexp + ")?)?";
final static String dateTimeRegexp = dateRegexp + "(\\s+" + timeRegexp + ")?";

/**
 * these constants define which pattern matching groups the date fields can be extracted from.
 */
final static protected int DOM_GROUP=1;
final static protected int MONTH_GROUP=6;
final static protected int YEAR_GROUP=12;
final static protected int HOUR_GROUP=14;
final static protected int MINUTE_GROUP=15;
final static protected int SECOND_GROUP=17;
final static protected int MILLISECOND_GROUP=19;

final static protected Pattern dateTimePattern = Pattern.compile( dateTimeRegexp );

public AussieDateParser()
{
    this(TimeZone.getDefault());
}

public AussieDateParser( TimeZone tz )
{
    super( tz );
}

@Override
public Calendar parse(String text)
{
    Calendar result = null;
    String trimmed;
    Matcher matcher;
    Matcher numberMatcher;
    String hourText;
    String minuteText;
    String secondText;
    String millisecondText;

    if ( text == null )
    {
        throw new NullPointerException( "NULL string passed as date to be parsed." );
    }

    trimmed = text.trim();
    if ( trimmed.length() == 0 )
    {
        throw new IllegalArgumentException( "Empty string passed as date to be parsed" );
    }

    matcher = dateTimePattern.matcher( trimmed );
    if ( ! matcher.matches() )
    {
        throw new IllegalArgumentException( "Failed to parse date from [" + trimmed + "]" );
    }

    result = Calendar.getInstance( getTimeZone() );
    result.set( Calendar.DAY_OF_MONTH, Integer.parseInt( matcher.group( DOM_GROUP ) ) );
    result.set( Calendar.MONTH, MonthParser.parseMonth( matcher.group( MONTH_GROUP )));
    int year = Integer.parseInt( matcher.group( YEAR_GROUP ) );
    if ( year < 100 )
    {
        year = ( year < 70 ) ? 2000 + year : 1900 + year;
    }
    result.set( Calendar.YEAR, year );

    result.set( Calendar.HOUR_OF_DAY, ( ( hourText = matcher.group( HOUR_GROUP ) ) != null ) ? Integer.parseInt( hourText ) : 0 );
    result.set( Calendar.MINUTE, ( ( minuteText = matcher.group( MINUTE_GROUP ) ) != null ) ? Integer.parseInt( minuteText ) : 0 );
    result.set( Calendar.SECOND, ( ( secondText = matcher.group( SECOND_GROUP ) ) != null ) ? Integer.parseInt( secondText ) : 0 );
    result.set( Calendar.MILLISECOND, ( ( millisecondText = matcher.group( MILLISECOND_GROUP ) ) != null ) ? Integer.parseInt( millisecondText ) : 0 );

    return result;
}

}
