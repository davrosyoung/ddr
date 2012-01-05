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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.EnumSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 * Battery of tests of the data series class.
 *
 */
@RunWith(JUnit4.class)
public class PlotDataTest
{
private final static double ACCEPTABLE_ERROR = 1E-6;

    @Test
    public void testCreatingPlotData()
    {
        PlotData<Long,Double> data = new PlotData<Long,Double>();
        assertNotNull ( data );
        assertEquals( 0, data.size() );
        
        data.setLineStyles(EnumSet.of( PlotData.LineStyle.STEPPED, PlotData.LineStyle.THICK ) );
        
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.NONE ) );
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.JOINED ) );
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.THIN ) );
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.DASHED ) );
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.FINE_DASHED ) );
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.EXTRA_THICK ) );
        
        assertTrue( data.getLineStyles().contains( PlotData.LineStyle.STEPPED ) );
        assertTrue( data.getLineStyles().contains( PlotData.LineStyle.THICK ) );
    }

    @Test
    public void testCreatingPlotDataFromEmptyDataSeries()
    {
        DataSeries<Long> timeSeries = new DataSeries<Long>();
        DataSeries<Double> dataSeries = new DataSeries<Double>();

        PlotData<Long,Double> data = new PlotData<Long,Double>( timeSeries, dataSeries );
        assertNotNull( data );
        assertEquals( 0, data.size() );
        assertTrue(data.getLineStyles().contains(PlotData.LineStyle.NONE));
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.JOINED ) );
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.THIN ) );
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.DASHED ) );
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.FINE_DASHED ) );
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.EXTRA_THICK ) );
        assertFalse(data.getLineStyles().contains(PlotData.LineStyle.STEPPED));
        assertFalse(data.getLineStyles().contains(PlotData.LineStyle.THICK));

    }

    @Test(expected=NullPointerException.class)
    public void testCreatingPlotDataFromNullDataSeries()
    {
        DataSeries<Long> timeSeries = null;
        DataSeries<Double> dataSeries = null;

        PlotData<Long,Double> data = null;
        data = new PlotData<Long,Double>( timeSeries, dataSeries );
    }

    @Test(expected=NullPointerException.class)
    public void testCreatingPlotDataFromOneOKOneNullDataSeries()
    {
        ArrayList<Long> rawTimeData = new ArrayList<Long>();
        rawTimeData.add( 42L );
        rawTimeData.add( 93L );
        rawTimeData.add( 57L );
        rawTimeData.add( 16L );
        DataSeries<Long> timeSeries = new DataSeries<Long>( rawTimeData );
        DataSeries<Double> dataSeries = null;

        PlotData<Long,Double> data = null;
        data = new PlotData<Long,Double>( timeSeries, dataSeries );
    }


    @Test(expected=IllegalArgumentException.class)
    public void testCreatingPlotDataFromDifferentSizedDataSeries()
    {
        ArrayList<Long> rawTimeData = new ArrayList<Long>();
        rawTimeData.add( 42L );
        rawTimeData.add( 93L );
        rawTimeData.add( 57L );
        rawTimeData.add( 16L );

        ArrayList<Double> rawData = new ArrayList<Double>();
        rawData.add( 42.7 );
        rawData.add( 93.9 );
        rawData.add( 57.2 );

        DataSeries<Long> timeSeries = new DataSeries<Long>( rawTimeData );
        DataSeries<Double> dataSeries = new DataSeries<Double>( rawData );

        PlotData<Long,Double> data = null;

        data = new PlotData<Long,Double>( timeSeries, dataSeries );
    }

    @Test
    public void testCreatingPlotDataFromIntegerDataSeries()
    {
        ArrayList<Long> rawTimeData = new ArrayList<Long>();
        rawTimeData.add( 42L );
        rawTimeData.add( 93L );
        rawTimeData.add( 57L );
        rawTimeData.add( 16L );

        ArrayList<Long> rawData = new ArrayList<Long>();
        rawData.add( 84L );
        rawData.add( 186L );
        rawData.add( 114L );
        rawData.add( 32L );

        DataSeries<Long> timeSeries = new DataSeries<Long>( rawTimeData );
        DataSeries<Long> dataSeries = new DataSeries<Long>( rawData );

        PlotData<Long,Long> data = null;
        data = new PlotData<Long,Long>( timeSeries, dataSeries );
        assertNotNull( data );
        assertEquals( 4, data.size() );
        assertEquals( 16L, data.getMinX().longValue() );
        assertEquals( 93L, data.getMaxX().longValue() );
        assertEquals( 32L, data.getMinY().longValue() );
        assertEquals( 186L, data.getMaxY().longValue() );
    }

    @Test
    public void testCreatingPlotDataFromIntegerDoubleDataSeries()
    {
        ArrayList<Long> rawTimeData = new ArrayList<Long>();
        rawTimeData.add( 42L );
        rawTimeData.add( 93L );
        rawTimeData.add( 57L );
        rawTimeData.add( 16L );

        ArrayList<Double> rawData = new ArrayList<Double>();
        rawData.add( 42.7 );
        rawData.add( 93.9 );
        rawData.add( 16.4 );
        rawData.add( 57.2 );

        DataSeries<Long> timeSeries = new DataSeries<Long>( rawTimeData );
        DataSeries<Double> dataSeries = new DataSeries<Double>( rawData );

        PlotData<Long,Double> data = null;
        data = new PlotData<Long,Double>( timeSeries, dataSeries );
        assertNotNull( data );
        assertEquals( 4, data.size() );
        assertEquals( 16L, data.getMinX().longValue() );
        assertEquals( 93L, data.getMaxX().longValue() );
        assertEquals( 16.4, data.getMinY(), ACCEPTABLE_ERROR );
        assertEquals( 93.9, data.getMaxY(), ACCEPTABLE_ERROR );

        assertNotNull( data.getDataPoints() );
        assertEquals( 4, data.getDataPoints().size() );
    }

    @Test
    public void testCreatingPlotDataFromDoubleDataSeries()
    {
        ArrayList<Double> rawTimeData = new ArrayList<Double>();
        rawTimeData.add( 85.4 );
        rawTimeData.add( 187.8 );
        rawTimeData.add( 32.8 );
        rawTimeData.add( 114.4 );

        ArrayList<Double> rawData = new ArrayList<Double>();
        rawData.add( 42.7 );
        rawData.add( 93.9 );
        rawData.add( 16.4 );
        rawData.add( 57.2 );

        DataSeries<Double> timeSeries = new DataSeries<Double>( rawTimeData );
        DataSeries<Double> dataSeries = new DataSeries<Double>( rawData );

        PlotData<Double,Double> data = null;
        data = new PlotData<Double,Double>( timeSeries, dataSeries );
        assertNotNull( data );
        assertEquals( 4, data.size() );
        assertEquals( 32.8, data.getMinX(), ACCEPTABLE_ERROR );
        assertEquals( 187.8, data.getMaxX(), ACCEPTABLE_ERROR );
        assertEquals( 16.4, data.getMinY(), ACCEPTABLE_ERROR );
        assertEquals( 93.9, data.getMaxY(), ACCEPTABLE_ERROR );
    }

    @Test(expected=NullPointerException.class)
    public void testAddingNullDataPoints()
    {
        PlotData<Double,Double> data = new PlotData<Double,Double>();
        DataPoint<Double,Double> point = null;
        data.add( point );
    }

    @Test
    public void testAddingDoubleDoubleDataPoints()
    {
        PlotData<Double,Double> data = new PlotData<Double,Double>();
        DataPoint<Double,Double> pointA;
        DataPoint<Double,Double> pointB;
        DataPoint<Double,Double> pointC;

        pointA = new DataPoint<Double,Double>( 42.7, 86.2 );
        pointB = new DataPoint<Double,Double>( 48.4, 90.8 );
        pointC = new DataPoint<Double,Double>( 37.3, 74.7 );

        data.add( pointA );
        assertEquals( 1, data.size() );

        data.add( pointB );
        assertEquals( 2, data.size() );

        data.add( pointC );
        assertEquals( 3, data.size() );

        assertEquals( 37.3, data.getMinX(), ACCEPTABLE_ERROR );
        assertEquals( 48.4, data.getMaxX(), ACCEPTABLE_ERROR );
        assertEquals( 74.7, data.getMinY(), ACCEPTABLE_ERROR );
        assertEquals( 90.8, data.getMaxY(), ACCEPTABLE_ERROR );

    }

    @Test
    public void testAddingLongDoubleDataPoints()
    {
        PlotData<Long,Double> data = new PlotData<Long,Double>();
        DataPoint<Long,Double> pointA;
        DataPoint<Long,Double> pointB;
        DataPoint<Long,Double> pointC;

        pointA = new DataPoint<Long,Double>( 42L, 86.2 );
        pointB = new DataPoint<Long,Double>( 48L, 90.8 );
        pointC = new DataPoint<Long,Double>( 37L, 74.7 );

        data.add( pointA );
        assertEquals( 1, data.size() );

        data.add( pointB );
        assertEquals( 2, data.size() );

        data.add( pointC );
        assertEquals( 3, data.size() );

        assertEquals( 37L, data.getMinX().longValue() );
        assertEquals( 48L, data.getMaxX().longValue() );
        assertEquals( 74.7, data.getMinY(), ACCEPTABLE_ERROR );
        assertEquals( 90.8, data.getMaxY(), ACCEPTABLE_ERROR );
    }

    @Test
    public void testSettingLineStyles()
    {
        PlotData<Long,Double> data = new PlotData<Long,Double>();
        assertNotNull ( data );
        assertEquals( 0, data.size() );
        
        data.add( new DataPoint<Long, Double>( 1000L, 5.23 ) );
        data.add( new DataPoint<Long, Double>( 1100L, 6.03 ) );
        data.add( new DataPoint<Long, Double>( 1200L, 5.86 ) );
        data.add( new DataPoint<Long, Double>( 1300L, 5.23 ) );
        
        assertEquals( 4, data.size() );
        assertEquals( 1000L, (long)data.getMinX() );
        assertEquals( 1300L, (long)data.getMaxX() );
        assertEquals( 5.23, data.getMinY(), ACCEPTABLE_ERROR );
        assertEquals( 6.03, data.getMaxY(), ACCEPTABLE_ERROR );
        
        assertTrue( data.getLineStyles().contains( PlotData.LineStyle.NONE ));
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.FINE_DASHED ));
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.JOINED ));
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.STEPPED ));
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.DASHED ));
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.THIN ));
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.THICK ));
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.EXTRA_THICK ));

        data.setLineStyles(EnumSet.of( PlotData.LineStyle.STEPPED, PlotData.LineStyle.THICK ) );

        assertFalse(data.getLineStyles().contains(PlotData.LineStyle.NONE));
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.FINE_DASHED ));
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.JOINED ));
        assertTrue(data.getLineStyles().contains(PlotData.LineStyle.STEPPED));
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.DASHED ));
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.THIN ));
        assertTrue(data.getLineStyles().contains(PlotData.LineStyle.THICK));
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.EXTRA_THICK ));

        data.setLineStyles(EnumSet.of( PlotData.LineStyle.JOINED ) );

        assertFalse(data.getLineStyles().contains(PlotData.LineStyle.NONE));
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.FINE_DASHED ));
        assertTrue(data.getLineStyles().contains(PlotData.LineStyle.JOINED));
        assertFalse(data.getLineStyles().contains(PlotData.LineStyle.STEPPED));
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.DASHED ));
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.THIN ));
        assertFalse(data.getLineStyles().contains(PlotData.LineStyle.THICK));
        assertFalse( data.getLineStyles().contains( PlotData.LineStyle.EXTRA_THICK ));
    }

    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( PlotDataTest.class );
    }
}