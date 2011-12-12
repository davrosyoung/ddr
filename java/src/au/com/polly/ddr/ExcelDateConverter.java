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
