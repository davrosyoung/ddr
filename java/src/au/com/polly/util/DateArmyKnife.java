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
 * @param stamp
 * @return date formatted as dd/MMM/yyyy hh:mm:ss.mmm
 */
public static String format(Date stamp)
{
    Calendar cal = Calendar.getInstance();
    cal.setTime( stamp );
    return format( cal );
}

/**
 *
 * @param cal
 * @return date formatted as dd/MMM/yyyy hh:mm:ss.mmm
 */
public static String format( Calendar cal )
{
    StringBuilder out = new StringBuilder();
    out.append( formatWithSeconds( cal ) );
    out.append( "." );
    out.append( threeDigitFormatter.format( cal.get( Calendar.MILLISECOND ) ) );
    return out.toString();
}


/**
 *
 * @param stamp
 * @return date formatted as dd/MMM/yyyy hh:mm:ss
 */
public static String formatWithSeconds(Date stamp)
{
    Calendar cal = Calendar.getInstance();
    cal.setTime( stamp );
    return formatWithSeconds( cal );
}

/**
 *
 * @param cal
 * @return date formatted as dd/MMM/yyyy hh:mm:ss
 */
public static String formatWithSeconds( Calendar cal )
{
    StringBuilder out = new StringBuilder();
    
    out.append( formatWithMinutes( cal ) );
    out.append( ":" );
    out.append( twoDigitFormatter.format( cal.get( Calendar.SECOND ) ) );

    return out.toString();

}


/**
 *
 * @param stamp
 * @return date formatted as dd/MMM/yyyy hh:mm
 */
public static String formatWithMinutes(Date stamp)
{
    Calendar cal = Calendar.getInstance();
    cal.setTime( stamp );
    return formatWithMinutes( cal );
}
        

/**
 *
 *
 * @param cal
 * @return date formatted as dd/MMM/yyyy hh:mm
 */
public static String formatWithMinutes(Calendar cal)
{
    StringBuilder out = new StringBuilder();
    
    out.append( twoDigitFormatter.format( cal.get( Calendar.DAY_OF_MONTH ) ));
    out.append( "/" );
    out.append( MonthParser.monthAbbreviation( cal.get( Calendar.MONTH ) ).toUpperCase() );
    out.append( "/" );
    out.append( fourDigitFormatter.format( cal.get( Calendar.YEAR ) ) );
    out.append( " " );
    out.append( twoDigitFormatter.format( cal.get( Calendar.HOUR_OF_DAY ) ) );
    out.append( ":" );
    out.append( twoDigitFormatter.format( cal.get( Calendar.MINUTE ) ) );
    
    return out.toString();
}
        

}
