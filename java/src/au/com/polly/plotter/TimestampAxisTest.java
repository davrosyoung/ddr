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

package au.com.polly.plotter;

import junit.framework.JUnit4TestAdapter;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 *
 * Battery of tests of the axisw class.
 *
 */
public class TimestampAxisTest
{

    // demonstrate that we can accurately calculate the next smallest power of ten
    // for a given value. our tests show that we are accurate to within about twelve
    // decimal places.
    // --------------------------------------------------------------------------------
    @Test
    public void testCalculateTimeUnit()
    {
        TimestampAxis<Long> axis = new TimestampAxis<Long>();
        assertEquals( TimestampAxis.TimeUnit.MILLISECOND, axis.calculateAxisTimeUnit( 0L ) );
        assertEquals( TimestampAxis.TimeUnit.MILLISECOND, axis.calculateAxisTimeUnit( 1L ) );
        assertEquals( TimestampAxis.TimeUnit.MILLISECOND, axis.calculateAxisTimeUnit( 999L ) );
        assertEquals( TimestampAxis.TimeUnit.SECOND, axis.calculateAxisTimeUnit( 1000L ) );
        assertEquals( TimestampAxis.TimeUnit.SECOND, axis.calculateAxisTimeUnit( 59999L ) );
        assertEquals( TimestampAxis.TimeUnit.MINUTE, axis.calculateAxisTimeUnit( 60000L ) );
        assertEquals( TimestampAxis.TimeUnit.MINUTE, axis.calculateAxisTimeUnit( 3599999L ) );
        assertEquals( TimestampAxis.TimeUnit.HOUR, axis.calculateAxisTimeUnit( 3600000L ) );
        assertEquals( TimestampAxis.TimeUnit.HOUR, axis.calculateAxisTimeUnit( 86399999L ) );
        assertEquals( TimestampAxis.TimeUnit.DAY, axis.calculateAxisTimeUnit( 86400000L ) );
        assertEquals( TimestampAxis.TimeUnit.DAY, axis.calculateAxisTimeUnit( 10000086400000L ) );
    }

    // demonstrate that we can accurately calculate the next smallest power of ten
    // for a given value. our tests show that we are accurate to within about twelve
    // decimal places.
    // --------------------------------------------------------------------------------
    @Test
    public void testCalculateIntervalFactor()
    {
        TimestampAxis<Long> axis = new TimestampAxis<Long>();
        assertEquals( 0.1, axis.calculateIntervalFactor( 0.9999999999 ) );
        assertEquals( 0.1, axis.calculateIntervalFactor( 0.1 ) );
        assertEquals( 0.1, axis.calculateIntervalFactor( 0.10000000001 ) );
        assertEquals( 0.1, axis.calculateIntervalFactor( 0.287 ) );
        assertEquals( 1.0, axis.calculateIntervalFactor( 2.87 ) );
        assertEquals( 10.0, axis.calculateIntervalFactor( 28.7 ) );
        assertEquals( 100.0, axis.calculateIntervalFactor( 100.0 ) );
        assertEquals( 100.0, axis.calculateIntervalFactor( 100.1 ) );
        assertEquals( 100.0, axis.calculateIntervalFactor( 287 ) );
        assertEquals( 100.0, axis.calculateIntervalFactor( 999.9999999999 ) );
        assertEquals( 1000.0, axis.calculateIntervalFactor( 1000.0 ) );
        assertEquals( 1000.0, axis.calculateIntervalFactor( 1000.00000000001 ) );
    }


    @Test
    public void testAutoScalingSmallSetOfTimestampsWithinASecond()
    {
        DataSeries<Long> times = new DataSeries<Long>();
        long CHRISTOPHER_AUA_BORN = 1023422400000L;

        // let's start with values between 32ms and 50ms
        // expect 19 intervals of 1ms each.
        // -----------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 32L );
        times.add( CHRISTOPHER_AUA_BORN + 38L );
        times.add( CHRISTOPHER_AUA_BORN + 43L );
        times.add( CHRISTOPHER_AUA_BORN + 37L );
        times.add( CHRISTOPHER_AUA_BORN + 50L );
        times.add( CHRISTOPHER_AUA_BORN + 46L );
        times.add( CHRISTOPHER_AUA_BORN + 35L );

        TimestampAxis<Long> axis = new TimestampAxis<Long>();
        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 1.0, axis.intervalSize );
        assertEquals( 18, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN + 32L, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 50L, (long) Math.round(axis.max) );

        // ok, now extend the range from 18ms to 50ms, now expect 25 intervals of 2ms each
        // --------------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 82L );
        assertEquals( 8, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN + 32L, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 82L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 2.0, axis.intervalSize );
        assertEquals( 25, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN + 32L, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 82L, (long) Math.round(axis.max) );

        // ok, now extend the range from 50ms to 99ms, now expect 20 intervals of 5ms each..
        // -------------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 131L );
        assertEquals( 9, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN + 32L, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 131L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 5.0, axis.intervalSize );
        assertEquals( 21, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN + 30L, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 135L, (long) Math.round(axis.max) );

        // ok, now extend the range from 99ms to 100ms, now expect 10 intervals of 100ms each..
        // -------------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 31L );
        assertEquals( 10, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN + 31L, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 131L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 5.0, axis.intervalSize );
        assertEquals( 21, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN + 30L, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 135L, (long) Math.round(axis.max) );

        // ok, now extend the range from 100ms to 300ms, we should end up with 30 intervals...
        // ---------------------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 331L );
        assertEquals( 11, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN + 31L, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 331L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 10.0, axis.intervalSize );
        assertEquals( 31, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN + 30L, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 340L, (long) Math.round(axis.max) );

        // ok, now from 100ms to 399ms, should end up with 40 intervals.
        // --------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 430L );
        assertEquals( 12, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN + 31L, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 430L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 20.0, axis.intervalSize );
        assertEquals( 21, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN + 20L, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 440L, (long) Math.round(axis.max) );

        // ok, if we up it to 400ms, we should get intervals of 20, making
        // up 21 intervals from 20 ... 440 
        // -------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 431L );
        assertEquals( 13, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN + 31L, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 431L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 20.0, axis.intervalSize );
        assertEquals( 21, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN + 20L, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 440L, (long) Math.round(axis.max) );

        // ok, now up the gap to 799, we should still get interval size of 20
        // -----------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 830L );
        assertEquals( 14, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN + 31L, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 830L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 50.0, axis.intervalSize );
        assertEquals( 17, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN + 0L, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 850L, (long) Math.round(axis.max) );


        // ok, now up the gap to 800, we should get an interval size of 50
        // -----------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 30L );
        assertEquals( 15, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN + 30L, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 830L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 50.0, axis.intervalSize );
        assertEquals( 17, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN + 0L, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 850L, (long) Math.round(axis.max) );

        // ok, now push it upto 999ms, we should still have an interval size of 50, and we should have 20 intervals
        // ----------------------------------------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 0L );
        times.add( CHRISTOPHER_AUA_BORN + 999L );
        assertEquals( 17, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN + 0L, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 999L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 50.0, axis.intervalSize );
        assertEquals( 20, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN + 0L, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 1000L, (long) Math.round(axis.max) );

    }

    @Test
    public void testAutoScalingSmallSetOfTimestampsWithinAMinute()
    {
        DataSeries<Long> times = new DataSeries<Long>();
        long CHRISTOPHER_AUA_BORN = 1023422400000L;

        // let's start with two values, one second apart..
        // -----------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN );
        times.add( CHRISTOPHER_AUA_BORN + 1000L );
        assertEquals( 2, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 1000L, times.getMax().longValue() );


        TimestampAxis<Long> axis = new TimestampAxis<Long>();
        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 100.0, axis.intervalSize );
        assertEquals( 10, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN , (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 1000L, (long) Math.round(axis.max) );

        // ok, now extend the range from 1000ms to 1999ms, now expect 20 intervals of 100ms each
        // --------------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 1999L );
        assertEquals( 3, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 1999L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 100.0, axis.intervalSize );
        assertEquals( 20, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 2000L, (long) Math.round(axis.max) );

        // ok, now extend the range from 1999ms to 4000ms, this should cause our intervals of 1s to be
        // split into four, giving us intervals of 200ms, we should then have 20 of them...
        // ---------------------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 4000L );
        assertEquals( 4, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 4000L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 200.0, axis.intervalSize );
        assertEquals( 20, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 4000L, (long) Math.round(axis.max) );

        // once we get to 8s, the intervals will be split into 500ms, giving us 16 of them.
        // -----------------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 8000L );
        assertEquals( 5, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 8000L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 500.0, axis.intervalSize );
        assertEquals( 16, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 8000L, (long) Math.round(axis.max) );

        // at 15s, we end up with intervals of 1s each ... giving us 15 intervals..
        // ---------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 15000L );
        assertEquals( 6, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 15000L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 1000.0, axis.intervalSize );
        assertEquals( 15, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 15000L, (long) Math.round(axis.max) );

        // at 39.99s, we still have 1s intervals
        // -----------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 39999L );
        assertEquals( 7, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 39999L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 1000.0, axis.intervalSize );
        assertEquals( 40, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 40000L, (long) Math.round(axis.max) );

        // at 40s, we end up with interals of 2s
        // -----------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 40000L );
        assertEquals( 8, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 40000L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 1000.0, axis.intervalSize );
        assertEquals( 40, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 40000L, (long) Math.round(axis.max) );


        // this continues on upto 59s, so with 59.999s, we have 30 intervals of 2s each.
        // ------------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 59999L );
        assertEquals( 9, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 59999L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 2000.0, axis.intervalSize );
        assertEquals( 30, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 60000L, (long) Math.round(axis.max) );

    }

    @Test
    public void testAutoScalingSmallSetOfTimestampsWithinAnHour()
    {
        DataSeries<Long> times = new DataSeries<Long>();
        long CHRISTOPHER_AUA_BORN = 1023422400000L;

        // let's start with two values, one minute apart..
        // -----------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN );
        times.add( CHRISTOPHER_AUA_BORN + 60000L );
        assertEquals( 2, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 60000L, times.getMax().longValue() );


        TimestampAxis<Long> axis = new TimestampAxis<Long>();
        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 5000.0, axis.intervalSize );
        assertEquals( 12, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN , (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 60000L, (long) Math.round(axis.max) );

        // ok, now extend the range from 1m 0s to 1m 59.999s, now expect 24 intervals of 5s each
        // --------------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 119999L );
        assertEquals( 3, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 119999L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 5000.0, axis.intervalSize );
        assertEquals( 24, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 120000L, (long) Math.round(axis.max) );

        // ok, now extend the range from 1m 59.99s to 4m 0s, this should cause our intervals of 1m to be
        // split into six, giving us intervals of 10s, we should then have 24 of them...
        // ---------------------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 240000L );
        assertEquals( 4, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 240000L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 10000.0, axis.intervalSize );
        assertEquals( 24, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 240000L, (long) Math.round(axis.max) );

        // once we get to 8m, the intervals will be split into 30s, giving us 16 of them.
        // -----------------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 480000L );
        assertEquals( 5, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 480000L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 30000.0, axis.intervalSize );
        assertEquals( 16, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 480000L, (long) Math.round(axis.max) );

        // upto 15m, we end up with intervals of 30s each ... giving us 30 intervals..
        // ---------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + ( 15 * 60000L ) );
        assertEquals( 6, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + ( 15 * 60000L ), times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 30000.0, axis.intervalSize );
        assertEquals( 30, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + ( 15 * 60000L ), (long) Math.round(axis.max) );

        // after 15m, we end up with intervals of 1m each ... giving us 16 intervals..
        // ---------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 900001L );
        assertEquals( 7, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 900001L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 60000.0, axis.intervalSize );
        assertEquals( 16, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + ( 960000L ), (long) Math.round(axis.max) );

        // at 39m 59.99s, we still have 1m intervals
        // -----------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + ( 39 * 60000L) + 59999L );
        assertEquals( 8, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + ( 39 * 60000L ) + 59999, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 60000.0, axis.intervalSize );
        assertEquals( 40, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 2400000L, (long) Math.round(axis.max) );

        // at 40m, we still have interals of 1m
        // ----------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 2400000L );
        assertEquals( 9, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 2400000L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 60000.0, axis.intervalSize );
        assertEquals( 40, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 2400000L, (long) Math.round(axis.max) );


        // this continues on upto 59s, so with 59.999s, we have 60 intervals of 1m each.
        // ------------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 3599999L );
        assertEquals( 10, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 3599999L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 60000.0, axis.intervalSize );
        assertEquals( 60, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 3600000L, (long) Math.round(axis.max) );

    }

    @Test
    public void testAutoScalingSmallSetOfTimestampsWithinADay()
    {
        DataSeries<Long> times = new DataSeries<Long>();
        long CHRISTOPHER_AUA_BORN = 1023422400000L;

        // let's start with two values, one hour apart..
        // ... we expect the intervals to be five minutes in size.
        // -------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN );
        times.add( CHRISTOPHER_AUA_BORN + 3600000L );
        assertEquals( 2, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 3600000L, times.getMax().longValue() );


        TimestampAxis<Long> axis = new TimestampAxis<Long>();
        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 300000.0, axis.intervalSize );
        assertEquals( 12, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN , (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 3600000L, (long) Math.round(axis.max) );

        // ok, now extend the range from 1h 0s to 1h 59m 59.999s, now expect 24 intervals of 5 mins each
        // ------------------------------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 7199999L );
        assertEquals( 3, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 7199999L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 300000.0, axis.intervalSize );
        assertEquals( 24, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 7200000, (long) Math.round(axis.max) );


        // ok, now extend the range from 1h 59m 59.99s to 4 hours, this should cause our intervals of 1hour to be
        // split into six, giving us intervals of 10 minutes, we should then have 24 of them...
        // ---------------------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 14400000L );
        assertEquals( 4, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 14400000L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 600000.0, axis.intervalSize );
        assertEquals( 24, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 14400000L, (long) Math.round(axis.max) );

        // once we get to 8h, the intervals will be split into 30 minutes, giving us 16 of them.
        // -----------------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 28800000L );
        assertEquals( 5, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 28800000L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 1800000.0, axis.intervalSize );
        assertEquals( 16, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 28800000L, (long) Math.round(axis.max) );

        // upto 15h, we still end up with intervals of 30 mins each ... giving us 30 intervals..
        // ---------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + ( 15 * 3600000L ) );
        assertEquals( 6, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + ( 15 * 3600000L ), times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 1800000.0, axis.intervalSize );
        assertEquals( 30, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + ( 15 * 3600000L ), (long) Math.round(axis.max) );

        // after 15h, we end up with intervals of 1hour each ... giving us 16 intervals..
        // ---------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + ( 15 * 3600000L ) + 1L );
        assertEquals( 7, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + ( 15 * 3600000L ) + 1L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 3600000.0, axis.intervalSize );
        assertEquals( 16, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + ( 16 * 3600000L ), (long) Math.round(axis.max) );

        // this continues on upto 23h 59m 59.99s, so with 59.999s, we have 24 intervals of 1h each.
        // ------------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + 86399999L );
        assertEquals( 8, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 86399999L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 3600000.0, axis.intervalSize );
        assertEquals( 24, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 86400000L, (long) Math.round(axis.max) );

    }

    @Test
    public void testAutoScalingSmallSetOfTimestampsWithinAWeek()
    {
        DataSeries<Long> times = new DataSeries<Long>();
        long CHRISTOPHER_AUA_BORN = 1023422400000L;

        // let's start with two values, one day apart..
        // ... we expect the intervals to be two hours in size.
        // -------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN );
        times.add( CHRISTOPHER_AUA_BORN + 86400000L );
        assertEquals( 2, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + 86400000L, times.getMax().longValue() );


        TimestampAxis<Long> axis = new TimestampAxis<Long>();
        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 7200000.0, axis.intervalSize );
        assertEquals( 12, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN , (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + 86400000L, (long) Math.round(axis.max) );


        // ok, now extend the 4 days, this should cause our intervals of 1 day to be
        // split into six, giving us intervals of 4 hours, we should then have 24 of them...
        // ---------------------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + ( 4 * 86400000L ) );
        assertEquals( 3, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + ( 4 * 86400000L ), times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( ( 4 * 3600000.0 ), axis.intervalSize );
        assertEquals( 24, axis.numberIntervals );
        assertEquals( CHRISTOPHER_AUA_BORN, (long) Math.round(axis.min) );
        assertEquals( CHRISTOPHER_AUA_BORN + ( 4 * 86400000L ), (long) Math.round(axis.max) );

        // once we get to 8 days, the intervals will be split into 12 hours, giving us 17 of them.
        // -----------------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + ( 8 * 86400000L ) + 60000L );
        assertEquals( 4, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + ( 8 * 86400000L ) + 60000L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 43200000.0, axis.intervalSize );
        assertEquals( 17, axis.numberIntervals );
        assertEquals( ((long)( CHRISTOPHER_AUA_BORN / 86400000L )) * 86400000L, (long) Math.round(axis.min) );
        assertEquals( ( (long) Math.floor(CHRISTOPHER_AUA_BORN / 86400000L) * 86400000L ) + ( 8L * 86400000L ) + ( 12L * 3600000L ), (long) Math.round(axis.max) );


        // after 16 days, we end up with intervals of 1 day each ... giving us 16 days..
        // ---------------------------------------------------------------------------
        times.add( CHRISTOPHER_AUA_BORN + ( 16 * 86400000L ) + 60000L );
        assertEquals( 5, times.size() );
        assertEquals( CHRISTOPHER_AUA_BORN, times.getMin().longValue() );
        assertEquals( CHRISTOPHER_AUA_BORN + ( 16 * 86400000L ) + 60000L, times.getMax().longValue() );

        axis.autoScale( times );
        assertNotNull( axis );
        assertEquals( 86400000.0, axis.intervalSize );
        assertEquals( 17, axis.numberIntervals );
        assertEquals( ((long)( CHRISTOPHER_AUA_BORN / 86400000L )) * 86400000L, (long) Math.round(axis.min) );
        assertEquals( ( ( (long) Math.floor(CHRISTOPHER_AUA_BORN / 86400000L) + 17 ) * 86400000L ) , (long) Math.round(axis.max) );
    }

    @Test
    @Ignore("Implement this test when we have some more time")
    public void testAutoScalingSmallSetOfTimestampsWithinAYear()
    {
        fail( "Not yet implmented." );
    }

public static junit.framework.Test suite()
{
    return new JUnit4TestAdapter( TimestampAxisTest.class );
}
    
}