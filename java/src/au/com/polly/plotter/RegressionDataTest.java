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
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 *
 * Ensure that we are able to accurately perform regression analysis
 * (obtain the correlation coefficient) for a set of X-Y data.
 *
 */
public class RegressionDataTest
{

    @Test(expected=NullPointerException.class)
    public void testBothSeriesNull()
    {
        RegressionData<Double,Double> rd = null;
        rd = new RegressionData<Double,Double>( null, null );
    }

    @Test(expected=NullPointerException.class)
    public void testXSeriesNull()
    {
        RegressionData<Double,Double> rd = null;
        DataSeries<Double> x = new DataSeries<Double>();
        x.add( 4.2 );
        x.add( 6.1 );
        x.add( 7.3 );
        x.add( 3.7 );
        x.add( 9.5 );
        DataSeries<Double> y = null;
        rd = new RegressionData<Double,Double>( x, y );
    }

    @Test(expected=NullPointerException.class)
    public void testYSeriesNull()
    {
        RegressionData<Double,Double> rd = null;
        DataSeries<Double> x = null;
        DataSeries<Double> y = new DataSeries<Double>();
        y.add( 8.4 );
        y.add( 12.2 );
        y.add( 14.6 );
        y.add( 7.4 );
        y.add( 18.0 );

        rd = new RegressionData<Double,Double>( x, y );
    }

    @Test(expected=IllegalArgumentException.class)
    public void testMismatchedSizedSeries()
    {
        RegressionData<Double,Double> rd = null;
        DataSeries<Double> x = new DataSeries<Double>();
        x.add( 4.2 );
        x.add( 6.1 );
        x.add( 7.3 );
        x.add( 3.7 );
        x.add( 9.5 );
        DataSeries<Double> y = new DataSeries<Double>();
        y.add( 8.4 );
        y.add( 12.2 );
        y.add( 14.6 );
        y.add( 7.4 );
        y.add( 18.0 );
        y.add( 18.1 );

        rd = new RegressionData<Double,Double>( x, y );
    }

    @Test
    public void testPerfectFitInts()
    {
        RegressionData<Long,Long> rd = null;
        DataSeries<Long> x = new DataSeries<Long>();
        x.add( 4L );
        x.add( 6L );
        x.add( 7L );
        x.add( 3L );
        x.add( 9L );
        DataSeries<Long> y = new DataSeries<Long>();
        y.add( 8L );
        y.add( 12L );
        y.add( 14L );
        y.add( 6L );
        y.add( 18L );
        
        try {
            rd = new RegressionData<Long,Long>( x, y );
            assertEquals( 1.0, rd.getCorrelationCoefficient(), 0.01 );
            assertEquals( 1.0, rd.getCorrelationDetermination(), 0.01 );
            assertEquals( 2.0, rd.getGradient(), 0.01 );
            assertEquals( 0.0, rd.getIntercept(), 0.01 );
        } catch (Exception e) {
            fail( "Did not expect exception " + e.getClass().getName() );
        }
    }

    @Test
    public void testPerfectFitIntDouble()
    {
        RegressionData<Long,Double> rd = null;
        DataSeries<Long> x = new DataSeries<Long>();
        x.add( 4L );
        x.add( 6L );
        x.add( 7L );
        x.add( 3L );
        x.add( 9L );
        DataSeries<Double> y = new DataSeries<Double>();
        y.add( 13.0 );
        y.add( 17.0 );
        y.add( 19.0 );
        y.add( 11.0 );
        y.add( 23.0 );

        try {
            rd = new RegressionData<Long,Double>( x, y );
            assertEquals( 1.0, rd.getCorrelationCoefficient(), 0.01 );
            assertEquals( 1.0, rd.getCorrelationDetermination(), 0.01 );
            assertEquals( 2.0, rd.getGradient(), 0.01 );
            assertEquals( 5.0, rd.getIntercept(), 0.01  );
        } catch (Exception e) {
            fail( "Did not expect exception " + e.getClass().getName() );
        }
    }

    @Test
    public void testPerfectFitDouble()
    {
        RegressionData<Double,Double> rd = null;
        DataSeries<Double> x = new DataSeries<Double>();
        x.add( 4.2 );
        x.add( 6.2 );
        x.add( 7.4 );
        x.add( 3.6 );
        x.add( 9.4 );
        DataSeries<Double> y = new DataSeries<Double>();
        y.add( 1.6 );
        y.add( 2.6 );
        y.add( 3.2 );
        y.add( 1.3 );
        y.add( 4.2 );

        try {
            rd = new RegressionData<Double,Double>( x, y );
            assertEquals( 1.0, rd.getCorrelationCoefficient(), 0.01 );
            assertEquals( 1.0, rd.getCorrelationDetermination(), 0.01 );
            assertEquals( 0.5, rd.getGradient() , 0.01 );
            assertEquals( -0.5, rd.getIntercept(), 0.01 );
        } catch (Exception e) {
            fail( "Did not expect exception " + e.getClass().getName() );
        }
    }

    @Test
    public void testExample()
    {
        RegressionData<Integer,Integer> rd = null;
        DataSeries<Integer> x = new DataSeries<Integer>();
        x.add( 1 );
        x.add( 4 );
        x.add( 3 );
        x.add( 4 );
        x.add( 8 );
        x.add( 9 );
        x.add( 8 );

        DataSeries<Integer> y = new DataSeries<Integer>();
        y.add( 2);
        y.add( 5 );
        y.add( 8 );
        y.add( 12 );
        y.add( 14 );
        y.add( 19 );
        y.add( 22 );

        rd = new RegressionData<Integer,Integer>( x, y );
        assertEquals( rd.getCorrelationCoefficient() , 0.9014, 0.01 );
        assertEquals( 2.157, rd.getGradient(), 0.01 );
        assertEquals( 0.312, rd.getIntercept(), 0.01 );

    }



    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( RegressionDataTest.class );
    }

}