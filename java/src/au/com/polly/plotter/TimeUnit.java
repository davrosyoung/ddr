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

package au.com.polly.plotter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Represents the units of time which can be used to represent the horizontal time axis upon a graph.
 * The year unit is intentionally approximate, and is sufficient for presenting data upon a graph
 * where the time axis unit is in years. Months cannot be sufficiently expressed in seconds and
 * need to be calculated differently. Intended only for making guesses about appropriate scaling
 * for timescale axes!!
 *
 * @author Dave Young
 * 
 * 
 */
public enum TimeUnit implements Comparator<TimeUnit>
{
    MILLISECOND( Calendar.MILLISECOND, 1L, "milliseconds" ),
    SECOND( Calendar.SECOND, 1000L, "seconds" ),
    MINUTE( Calendar.MINUTE, 60 * 1000L, "minutes" ),
    HOUR( Calendar.HOUR, 3600 * 1000L, "hours" ),
    DAY( Calendar.DAY_OF_YEAR, 86400 * 1000L, "days" ),
    WEEK( Calendar.WEEK_OF_YEAR, 7 * 86400 * 1000L, "weeks" ),
    MONTH( Calendar.MONTH, 31 * 86400L * 1000L, "months" ),
    YEAR( Calendar.YEAR, 365243 * 86400L, "years" );

private static List<TimeUnit> downwards = null;

static {
    downwards = new ArrayList<TimeUnit>( Arrays.asList( TimeUnit.values() ) );
    Collections.sort( downwards, TimeUnit.MILLISECOND.descending() );
}

/**
 * the field in java.util.Calendar which this unit of time can be represented by.
 * by obtaining this value, one can perform operations such as add() and roll()
 * on a calendar object.
 */
    private final int calendarField;

/**
 * A rough estimate of how many milliseconds duration this time unit encompasses. It's
 * just fine until we get to months and years, where it is an approximation at best!!
 */
    private final long milliSeconds;

/**
 *  How this field is to be represented in human readable form.
 */
    private final String label;


    private TimeUnit( int calendarField, long ms, String label )
    {
        this.calendarField = calendarField;
        this.milliSeconds = ms;
        this.label = label;
    }

    public String toString()
    {
        return label;
    }

    public int getCalendarField()
    {
        return calendarField;
    }

    /**
     * @return approximate length of this time unit in milliseconds. It's sort of
     * a guess for MONTH and YEAR units, where they can depend in length depending
     * upon the actual calendar month & year in question.
     *
     */
    public long getDurationMS()
    {
        return this.milliSeconds;
    }

/**
 * compares one time unit against another by interrogating the duration field.
 *
 *
 * @param o1
 * @param o2
 * @return  <0 if o1 is smaller than o2, 0 if equal, >0 if o1 is larger than o2
 */
    @Override
    public int compare( TimeUnit o1, TimeUnit o2 )
    {
        return ((Long)o1.getDurationMS()).compareTo( o2.getDurationMS() );
    }
    

    public Comparator<TimeUnit> ascending()
    {
        return this;
    }

    public Comparator<TimeUnit> descending()
    {
        return Collections.reverseOrder( ascending() );
    }

    public static List<TimeUnit> getReverseOrder()
    {
        return downwards;
    }


}
