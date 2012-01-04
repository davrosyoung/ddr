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

import au.com.polly.util.DateArmyKnife;
import au.com.polly.util.TimestampArmyKnife;

import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Represents either a horizontal timestamp axis.
 * Scaling is a bit tricker. We consider Date/times to have the following "scales" or orders of magnitude;
 * - days
 * - hours
 * - minutes
 * - seconds
 * - milliseconds
 */
public class TimestampAxis<Long extends Number>
extends BaseAxis<Long>
implements Axis<Long>
{
DateArmyKnife knife = new DateArmyKnife();
// our units of time.    
enum TimeUnit { WEEK, DAY, HOUR, MINUTE, SECOND, MILLISECOND };
boolean empty = true;

// map to represent how large each unit of time is, in milliseconds.
// -------------------------------------------------------------------- 
private static Map<TimeUnit,java.lang.Long> unitSize = new HashMap<TimeUnit,java.lang.Long>();

static {
    unitSize.put( TimeUnit.WEEK, 7 * 86400000L );
    unitSize.put( TimeUnit.DAY, 86400000L );
    unitSize.put( TimeUnit.HOUR, 3600000L );
    unitSize.put( TimeUnit.MINUTE, 60000L );
    unitSize.put( TimeUnit.SECOND, 1000L );
    unitSize.put( TimeUnit.MILLISECOND, 1L );
}

/**
 * Invoking this method will cause the axis to determine the most appropriate scaling and
 * intervals for the data set.
 */
public void autoScale( DataSeries<Long> data )
{
    long min = 0L;
    long max = 0L;
    long range = 0L;
    double intervalFactor;
    long unitLength;

    boolean changed = false;

    // determine the minimum and maximum values...
    // ------------------------------------------
    if ( empty || data.getMin().longValue() < this.min )
    {
        min = data.getMin().longValue();
        changed = true;
    } else {
        min = (long)this.min;
    }

    if ( empty || data.getMax().longValue() > this.max )
    {
        max = data.getMax().longValue();
        changed = true;
    } else {
        max = (long)this.max;
    }

    if ( changed )
    {
        calculate( min, max );
    }
}

public void scale( Number min, Number max )
{
    calculate( min.longValue(), max.longValue() );
}


/**
 *
 * @param min
 * @param max
 */
public void calculate( long min, long max )
{
    long range = 0L;
    double intervalFactor;      // how many unit lengths each interval should span.
    long unitLength;            // how long the selected units are in seconds...


    // determine the minimum and maximum values...
    // ------------------------------------------
    range = max - min;

    TimeUnit unit = calculateAxisTimeUnit( range );
    unitLength =  unitSize.get( unit );
    double numberUnits = (double)range / (double)unitLength;

    // figuring out the interval factor, according to powers of ten, only really works
    // for seconds and milliseconds....
    // ----------------------------------------------------------------------------------
    if ( unitLength < 60000L )
    {
        intervalFactor = calculateIntervalFactor( numberUnits );
    } else {
        intervalFactor = 1;
    }

    // for timestamps, we prefer to have more intervals if possible,
    // so try to aim for about 15 - 30 intervals.... but the
    // interval size needs to be a whole number.
    // ----------------------------------------------------------------
    do {

        // if we already satisfy our criteria, then just stick with
        // what we've got..................
        // ---------------------------------------------------------
        if ( ( numberUnits / intervalFactor) > 15 )
        {
            this.intervalSize = intervalFactor * unitLength;
            break;
        }

        // If we've already got at least eight intervals,
        // then create interval size of half the interval factor.
        // this will give us between 16 and 30 intervals
        // ---------------------------------------------------------
        if ( ( numberUnits / intervalFactor) >= 8 )
        {
            this.intervalSize = (int) Math.ceil((intervalFactor / 2) * unitLength);
            break;
        }

        // If we've already got at least four intervals,
        // then create four times that many ... so somewhere
        // between 20 and 40 intervals
        // ---------------------------------------------------------
        if ( ( numberUnits / intervalFactor) >= 4 )
        {
            // once we get into units of a minute or more, then split into 6, rather than five.
            // this is because once we get past seconds, into minutes, things divide better
            // by six than by five..
            // -----------------------------------------------------------------------------------------------------
            this.intervalSize = (int) Math.ceil((intervalFactor / ((unitLength >= 60000 ? 6 : 5))) * unitLength);
            break;
        }

        // OK, this will give us between 10 and 40 intervals.....
        // ---------------------------------------------------------
//        this.intervalSize = Math.ceil( intervalFactor / 10 ) * unitLength;
        // once we get past minutes, divide by 12 rather than by 10
        this.intervalSize = (int) Math.ceil((intervalFactor / ((unitLength >= 60000 ? 12 : 10))) * unitLength);

    } while( false ); // end-do-WHILE(false)


    long minInterval = (long) Math.floor(min / intervalSize);
    long maxInterval = (long) Math.ceil(max / intervalSize);
    this.numberIntervals = (int)( maxInterval - minInterval );
    this.min = minInterval * intervalSize;
    this.max = maxInterval * intervalSize;
    this.empty = false;
}

/**
 *
 * @param range the timespan in millisconds
 * @return the "smallest" unit of time, which can represent at least one unit of time expressed by range.
 */
public TimeUnit calculateAxisTimeUnit( long range )
{
    int i;
    TimeUnit result = TimeUnit.MILLISECOND;

    // ok, find the appropriate time unit, given the range supplied...
    // ... iterate through the time units, which start with the biggest
    // and end with the smallest....
    // -----------------------------------------------------
    for( TimeUnit unit : TimeUnit.values() )
    {
        long size = unitSize.get( unit );
        if ( range >= size )
        {
            result = unit;
            break;
        }
    }
    
    return result;
}

/**
 * @param value the data point to obtain a label for
 * @return appropriate text for the data point.
 */
public String getDataLabel( Long value)
{
    NumberFormat formatter;
    String result = null;

    result = knife.formatJustDate( new Date( value.longValue() ), false );

    return result;
}


}