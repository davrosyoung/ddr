/*
 * Copyright (c) 2011-2011 Polly Enterprises Pty Ltd and/or its affiliates.
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


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Convert between month names and calendar month field values.
 */
public class MonthParser
{
    static protected Map<String,Integer> monthMap =  new HashMap<String,Integer>();
    static protected Map<Integer,Integer> numericMonthMap = new HashMap<Integer,Integer>();
    static protected String[] monthAbbreviation = { "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec" };
    static protected String[] monthName = { "january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december" };
    static protected Pattern numberPattern = Pattern.compile( "\\d+" );

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

/**
 * 
 * @param month the month between 0 and 11 (as per java.util.Calendar!!!)
 * @return name of the month (in lower case)
 */
public static String monthName( int month )
{
    if ( ( month < 0 ) || ( month > 11 ) )
    {
        throw new IllegalArgumentException( "Month must be between 0 & 11, you specified (" + month + ")" );
    }
    return monthName[ month ];
}

/**
 * 
 * @param month the month between 0 and 11 (as per java.util.Calendar!!!)
 * @return name of the month (in lower case)
 */
public static String monthAbbreviation( int month )
{
    if ( ( month < 0 ) || ( month > 11 ) )
    {
        throw new IllegalArgumentException( "Month must be between 0 & 11, you specified (" + month + ")" );
    }
    return monthAbbreviation[ month ];

}

}
