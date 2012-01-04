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

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Date/Time utility methods
 */
public class DateArmyKnife
{
private static NumberFormat twoDigitFormatter;
private static NumberFormat threeDigitFormatter;
private static NumberFormat fourDigitFormatter;

static {
    twoDigitFormatter = NumberFormat.getIntegerInstance();
    twoDigitFormatter.setMinimumIntegerDigits( 2 );
    twoDigitFormatter.setMaximumIntegerDigits( 2 );
    twoDigitFormatter.setGroupingUsed( false );

    threeDigitFormatter = NumberFormat.getIntegerInstance();
    threeDigitFormatter.setMinimumIntegerDigits( 3 );
    threeDigitFormatter.setMaximumIntegerDigits( 3 );
    threeDigitFormatter.setGroupingUsed( false );

    fourDigitFormatter = NumberFormat.getIntegerInstance();
    fourDigitFormatter.setMinimumIntegerDigits( 4 );
    fourDigitFormatter.setMaximumIntegerDigits(4);
    fourDigitFormatter.setGroupingUsed( false );
}

/**
 *
 * @param alpha
 * @param beta
 * @return whether alpha and beta are the same date/time
 *
 * takes care of worrying about nulls!!
 */
public static boolean areDatesEqual( Date alpha, Date beta )
{

    boolean result = false;

    do {
        // if both strings are null, then they are equal!!
        // ------------------------------------------------
        if ( result = ( ( alpha == null ) && ( beta == null ) ) )
        {
            break;
        }

        // if one or tuther is null, then they are not equal!!
        // --------------------------------------------------
        if ( ( alpha == null ) || ( beta == null ) )
        {
            result = false;
            break;
        }

        result = alpha.equals( beta );

    } while( false );

    return result;

}

/**
 *
 * @param stamp
 * @return date formatted as dd/MMM/yyyy hh:mm:ss.mmm
 */
public static String format( Date stamp )
{
    return format( stamp, true );
}

/**
 *
 * @param stamp
 * @param fourDigitYear whether year should be output as four digits instead of two.
 * @return date formatted as dd/MMM/yyyy hh:mm:ss.mmm or dd/MMM/yy hh:mm:ss.mmm
 */
public static String format( Date stamp, boolean fourDigitYear )
{
    Calendar cal = Calendar.getInstance();
    cal.setTime( stamp );
    return format( cal, fourDigitYear );
}

/**
 *
 * @param cal
 * @return date formatted as dd/MMM/yyyy hh:mm:ss.mmm
 */
public static String format( Calendar cal, boolean fourDigitYear )
{
    StringBuilder out = new StringBuilder();
    out.append( formatWithSeconds( cal, fourDigitYear ) );
    out.append( "." );
    out.append( threeDigitFormatter.format( cal.get( Calendar.MILLISECOND ) ) );
    return out.toString();
}


/**
 *
 * @param stamp
 * @return date formatted as dd/MMM/yyyy hh:mm:ss
 */
public static String formatWithSeconds( Date stamp )
{
    return formatWithSeconds( stamp, true );
}


/**
 *
 * @param stamp
 * @param fourDigitYear whether or not to output year as four digits (instead of two)
 * @return date formatted as dd/MMM/yyyy hh:mm:ss or dd/MMM/yy hh:mm:ss
 */
public static String formatWithSeconds( Date stamp, boolean fourDigitYear )
{
    Calendar cal = Calendar.getInstance();
    cal.setTime( stamp );
    return formatWithSeconds( cal, fourDigitYear );
}

/**
 * @param cal
 * @return date formatted as dd/MMM/yyyy hh:mm:ss
 */

public static String formatWithSeconds( Calendar cal )
{
    return formatWithSeconds( cal, true );
}

/**
 * @param cal
 * @param fourDigitYear whether year should contain four digits (true) or two( false).
 * @return date formatted as dd/MMM/yyyy hh:mm:ss or dd/MMM/yy hh:mm:ss
 */
public static String formatWithSeconds( Calendar cal, boolean fourDigitYear )
{
    StringBuilder out = new StringBuilder();
    
    out.append( formatWithMinutes( cal, fourDigitYear ) );
    out.append( ":" );
    out.append( twoDigitFormatter.format( cal.get( Calendar.SECOND ) ) );

    return out.toString();

}


/**
 *
 * @param stamp
 * @return date formatted as dd/MMM/yyyy hh:mm
 */
public static String formatWithMinutes( Date stamp )
{
    return formatWithMinutes( stamp, true );
}

/**
 * @param stamp
 * @param fourDigitYear whether or not to output the year with four digits.
 * @return date formatted as dd/MMM/yyyy hh:mm
 */
public static String formatWithMinutes( Date stamp, boolean fourDigitYear )
{
    Calendar cal = Calendar.getInstance();
    cal.setTime( stamp );
    return formatWithMinutes( cal, fourDigitYear );
}


/**
 *
 *
 * @param cal
 * @return date formatted as dd/MMM/yyyy hh:mm
 */
public static String formatWithMinutes( Calendar cal )
{
    return formatWithMinutes( cal, true );
}

/**
 *
 *
 * @param cal
 * @param fourDigitYear whether to output four digit year or not.
 * @return date formatted as dd/MMM/yyyy hh:mm  or dd/MMM/yy hh:mm
 */
public static String formatWithMinutes(Calendar cal, boolean fourDigitYear )
{
    StringBuilder out = new StringBuilder();
    
    out.append( formatJustDate( cal, fourDigitYear ) );
    out.append( " " );
    out.append( twoDigitFormatter.format( cal.get( Calendar.HOUR_OF_DAY ) ) );
    out.append( ":" );
    out.append( twoDigitFormatter.format( cal.get( Calendar.MINUTE ) ) );
    
    return out.toString();
}

/**
 * 
 * @param stamp
 * @return  date formatted as dd/MMM/yyyy
 */
public static String formatJustDate( Date stamp )
{
    return formatJustDate(stamp, true);
}

/**
 * 
 * @param stamp
 * @param fourDigitYear whether or not to output year as four digits.
 * @return date formatted as dd/MMM/yy or dd/MMM/yyyy
 */
public static String formatJustDate( Date stamp, boolean fourDigitYear )
{
    Calendar cal = Calendar.getInstance();
    cal.setTime( stamp );
    return formatJustDate( cal, fourDigitYear );    
}

/**
 * @param cal
 * @return date formatted as dd/MMM/yyyy
 */
public static String formatJustDate( Calendar cal )
{
    return formatJustDate( cal, true );
}

/**
 * @param cal
 * @param fourDigitYear whether to output four digit year or not.
 * @return date formatted as dd/MMM/yyyy  or dd/MMM/yy
 */
public static String formatJustDate(Calendar cal, boolean fourDigitYear )
{
    StringBuilder out = new StringBuilder();

    out.append( twoDigitFormatter.format( cal.get( Calendar.DAY_OF_MONTH ) ));
    out.append( "/" );
    out.append( MonthParser.monthAbbreviation( cal.get( Calendar.MONTH ) ).toUpperCase() );
    out.append( "/" );
    out.append( fourDigitYear ? fourDigitFormatter.format( cal.get( Calendar.YEAR ) ) : twoDigitFormatter.format( cal.get( Calendar.YEAR ) ) );
    return out.toString();
}

}
