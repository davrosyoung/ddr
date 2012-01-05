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

import junit.framework.JUnit4TestAdapter;
import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * Battery of tests for the numeric axes class.
 *
 */
public class NumericAxisTest
{
private final static Logger logger = Logger.getLogger( NumericAxisTest.class );
private final static double ACCEPTABLE_ERROR = 1E-8;

// demonstrate that we can accurately calculate the next smallest power of ten
// for a given value. our tests show that we are accurate to within about twelve
// decimal places.
// --------------------------------------------------------------------------------
@Test
public void testCalculateIntervalFactor()
{
    NumericAxis<Double> axis = new NumericAxis<Double>();
    assertEquals( 0.1, axis.calculateIntervalFactor( 0.9999999999 ), ACCEPTABLE_ERROR );
    assertEquals( 0.1, axis.calculateIntervalFactor( 0.1 ), ACCEPTABLE_ERROR );
    assertEquals( 0.1, axis.calculateIntervalFactor( 0.10000000001 ), ACCEPTABLE_ERROR );
    assertEquals( 0.1, axis.calculateIntervalFactor( 0.287 ), ACCEPTABLE_ERROR );
    assertEquals( 1.0, axis.calculateIntervalFactor( 2.87 ), ACCEPTABLE_ERROR );
    assertEquals( 10.0, axis.calculateIntervalFactor( 28.7 ), ACCEPTABLE_ERROR );
    assertEquals( 100.0, axis.calculateIntervalFactor( 100.0 ), ACCEPTABLE_ERROR );
    assertEquals( 100.0, axis.calculateIntervalFactor( 100.1 ), ACCEPTABLE_ERROR );
    assertEquals( 100.0, axis.calculateIntervalFactor( 287 ), ACCEPTABLE_ERROR );
    assertEquals( 100.0, axis.calculateIntervalFactor( 999.9999999999 ), ACCEPTABLE_ERROR );
    assertEquals( 1000.0, axis.calculateIntervalFactor( 1000.0 ), ACCEPTABLE_ERROR );
    assertEquals( 1000.0, axis.calculateIntervalFactor( 1000.00000000001 ), ACCEPTABLE_ERROR );
}

@Test
public void testAutoScalingSmallSetOfDoubles()
{
    DataSeries<Double> ddSet = new DataSeries<Double>();

    // start with a set of data, spread over a range of about 22.
    // rather than 5 intervals of 5, this will create 11 intervals,
    // each covering 2.0.
    // -----------------------------------------------------------------
    ddSet.add( 42.6 );
    ddSet.add( 63.7 );
    ddSet.add( 54.2 );
    ddSet.add( 47.8 );
    ddSet.add( 62.6 );


    NumericAxis<Double> axis = new NumericAxis<Double>();
    axis.autoScale( ddSet );

    assertNotNull( axis );
    assertEquals( 2.0, axis.intervalSize, ACCEPTABLE_ERROR );
    assertEquals( 11, axis.numberIntervals );
    assertEquals( 42.0, axis.min, ACCEPTABLE_ERROR );
    assertEquals( 64.0, axis.max, ACCEPTABLE_ERROR );


    // ok, now extend the range "a bit".... to about 35, this should
    // now create 8 intervals of five, rather than >15 intervals of 2.
    // -----------------------------------------------------------------
    ddSet.add( 27.4 );
    ddSet.add( 31.2 );
    axis.autoScale( ddSet );

    // now recalculate...
    // ----------------------------
    assertNotNull( axis );
    assertEquals( 5.0, axis.intervalSize, ACCEPTABLE_ERROR );
    assertEquals( 8, axis.numberIntervals );
    assertEquals( 25.0, axis.min, ACCEPTABLE_ERROR );
    assertEquals( 65.0, axis.max, ACCEPTABLE_ERROR );


    // ok, now extend the range out to about 60 ... this should just create
    // seven intervals, each of 10.0
    // ------------------------------------------------------------------------
    ddSet.add( 12.1 );
    ddSet.add( 69.9999 );
    axis.autoScale( ddSet );
    assertEquals( 10.0, axis.intervalSize, ACCEPTABLE_ERROR );
    assertEquals( 6, axis.numberIntervals );
    assertEquals( 10.0, axis.min, ACCEPTABLE_ERROR );
    assertEquals( 70.0, axis.max, ACCEPTABLE_ERROR );

    // ok, now just push it out to 70, then just beyond 70.
    // 70.0 will fall within 60-70 interval, 70.00001 should cause a
    // new interval to be created.
    // ------------------------------------------------------------------------
    ddSet.add( 70.0 );
    axis.autoScale( ddSet );
    assertEquals( 10.0, axis.intervalSize, ACCEPTABLE_ERROR );
    assertEquals( 6, axis.numberIntervals );
    assertEquals( 10.0, axis.min, ACCEPTABLE_ERROR );
    assertEquals( 70.0, axis.max, ACCEPTABLE_ERROR );

    ddSet.add( 70.00000001 );
    axis.autoScale( ddSet );
    assertEquals( 10.0, axis.intervalSize, ACCEPTABLE_ERROR );
    assertEquals( 7, axis.numberIntervals );
    assertEquals( 10.0, axis.min, ACCEPTABLE_ERROR );
    assertEquals( 80.0, axis.max, ACCEPTABLE_ERROR );

    // ok, now add a data point of 112.1, this should cause our interval factor
    // to be "stepped up" by a factor of ten. it should create six intervals
    // each 20.0 across...
    // --------------------------------------------------------------------------
    ddSet.add( 112.1 );
    axis.autoScale( ddSet );
    assertEquals( 20.0, axis.intervalSize, ACCEPTABLE_ERROR );
    assertEquals( 6, axis.numberIntervals );
    assertEquals( 0.0, axis.min, ACCEPTABLE_ERROR );
    assertEquals( 120.0, axis.max, ACCEPTABLE_ERROR );
}

@Test
public void testTroublesomeSetOfDoubles()
{
    DataSeries<Double> monkeys = new DataSeries<Double>();
    monkeys.add( 24.6 );
    monkeys.add( 27.2 );
    monkeys.add( 25.1 );
    monkeys.add( 25.0 );
    monkeys.add( 24.7 );
    monkeys.add( 23.9 );
    monkeys.add( 21.6 );
    monkeys.add( 19.2 );
    monkeys.add( 15.4 );

    NumericAxis<Double> axis = new NumericAxis<Double>();
    axis.autoScale( monkeys );

    assertNotNull( axis );
    assertEquals( 2.0, axis.intervalSize, ACCEPTABLE_ERROR );
    assertEquals( 7, axis.numberIntervals );
    assertEquals( 14.0, axis.min, ACCEPTABLE_ERROR );
    assertEquals( 28.0, axis.max, ACCEPTABLE_ERROR );

    monkeys.add( 7.3 );
    axis.autoScale( monkeys );

    assertNotNull( axis );
    assertEquals( 2.0, axis.intervalSize, ACCEPTABLE_ERROR );
    assertEquals( 11, axis.numberIntervals );
    assertEquals( 6.0, axis.min, ACCEPTABLE_ERROR );
    assertEquals( 28.0, axis.max, ACCEPTABLE_ERROR );

}

@Test
public void testAutoScalingSmallSetOfIntegers()
{
    DataSeries<Long> ddSet = new DataSeries<Long>();

    // start with a set of data, spread over a range of about 22.
    // rather than 5 intervals of 5, this will create 11 intervals,
    // each covering 2.0.
    // -----------------------------------------------------------------
    ddSet.add( 42L );
    ddSet.add( 63L );
    ddSet.add( 54L );
    ddSet.add( 47L );
    ddSet.add( 62L );


    NumericAxis<Long> axis = new NumericAxis<Long>();
    axis.autoScale( ddSet );

    assertNotNull( axis );
    assertEquals( 2.0, axis.intervalSize, ACCEPTABLE_ERROR );
    assertEquals( 11, axis.numberIntervals );
    assertEquals( 42.0, axis.min, ACCEPTABLE_ERROR );
    assertEquals( 64.0, axis.max, ACCEPTABLE_ERROR );


    // ok, now extend the range "a bit".... to about 35, this should
    // now create 8 intervals of five, rather than >15 intervals of 2.
    // -----------------------------------------------------------------
    ddSet.add( 27L );
    ddSet.add( 31L );
    axis.autoScale( ddSet );

    // now recalculate...
    // ----------------------------
    assertNotNull( axis );
    assertEquals( 5.0, axis.intervalSize, ACCEPTABLE_ERROR );
    assertEquals( 8, axis.numberIntervals );
    assertEquals( 25.0, axis.min, ACCEPTABLE_ERROR );
    assertEquals( 65.0, axis.max, ACCEPTABLE_ERROR );


    // ok, now extend the range out to about 60 ... this should just create
    // seven intervals, each of 10.0
    // ------------------------------------------------------------------------
    ddSet.add( 12L );
    ddSet.add( 60L );
    axis.autoScale( ddSet );
    assertEquals( 10.0, axis.intervalSize, ACCEPTABLE_ERROR );
    assertEquals( 6, axis.numberIntervals );
    assertEquals( 10.0, axis.min, ACCEPTABLE_ERROR );
    assertEquals( 70.0, axis.max, ACCEPTABLE_ERROR );

    // ok, now just push it out to 70, then just beyond 70.
    // 70.0 will fall within 60-70 interval, 71 should cause a
    // new interval to be created.
    // ------------------------------------------------------------------------
    ddSet.add( 70L );
    axis.autoScale( ddSet );
    assertEquals( 10.0, axis.intervalSize, ACCEPTABLE_ERROR );
    assertEquals( 6, axis.numberIntervals );
    assertEquals( 10.0, axis.min, ACCEPTABLE_ERROR );
    assertEquals( 70.0, axis.max, ACCEPTABLE_ERROR );

    ddSet.add( 71L );
    axis.autoScale( ddSet );
    assertEquals( 10.0, axis.intervalSize, ACCEPTABLE_ERROR );
    assertEquals( 7, axis.numberIntervals );
    assertEquals( 10.0, axis.min, ACCEPTABLE_ERROR );
    assertEquals( 80.0, axis.max, ACCEPTABLE_ERROR );

    // ok, now add a data point of 112, this should cause our interval factor
    // to be "stepped up" by a factor of ten. it should create six intervals
    // each 20.0 across...
    // --------------------------------------------------------------------------
    ddSet.add( 112L );
    axis.autoScale( ddSet );
    assertEquals( 20.0, axis.intervalSize, ACCEPTABLE_ERROR );
    assertEquals( 6, axis.numberIntervals );
    assertEquals( 0.0, axis.min, ACCEPTABLE_ERROR );
    assertEquals( 120.0, axis.max, ACCEPTABLE_ERROR );
}


@Test
public void testManuallyScalingAxisSimple()
{
    DataSeries<Long> ddSet = new DataSeries<Long>();


    // let's have data betweem 0 and 100, but only plot between 0 and 20
    // ------------------------------------------------------------------
    ddSet.add( 5L );
    ddSet.add( 7L );
    ddSet.add( 12L );
    ddSet.add( 9L );
    ddSet.add( 15L );
    ddSet.add( 19L );
    ddSet.add( 42L );
    ddSet.add( 100L );

    NumericAxis<Long> axis = new NumericAxis<Long>();
    axis.scale( 0L, 20L );

    assertNotNull( axis );
    assertEquals( 2.0, axis.intervalSize, ACCEPTABLE_ERROR );
    assertEquals( 10, axis.numberIntervals );
    assertEquals( 0.0, axis.min, ACCEPTABLE_ERROR );
    assertEquals( 20.0, axis.max, ACCEPTABLE_ERROR );
}

@Test
public void testManuallyScalingAxisSomeMore()
{
    DataSeries<Long> ddSet = new DataSeries<Long>();


    // let's have data betweem 0 and 100, but only plot between 30 and 40
    // ------------------------------------------------------------------
    ddSet.add( 5L );
    ddSet.add( 7L );
    ddSet.add( 12L );
    ddSet.add( 9L );
    ddSet.add( 15L );
    ddSet.add( 19L );
    ddSet.add( 42L );
    ddSet.add( 100L );

    NumericAxis<Long> axis = new NumericAxis<Long>();
    axis.scale( 30L, 40L );

    assertNotNull( axis );
    assertEquals( 2.0, axis.intervalSize, ACCEPTABLE_ERROR );
    assertEquals( 5, axis.numberIntervals );
    assertEquals( 30.0, axis.min, ACCEPTABLE_ERROR );
    assertEquals( 40.0, axis.max, ACCEPTABLE_ERROR );
}


public static junit.framework.Test suite()
{
    return new JUnit4TestAdapter( NumericAxisTest.class );
}


}