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

import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import junit.framework.JUnit4TestAdapter;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 24/11/11
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class SimpleDiscontinuityDataRateReducerV2Test
{
static private final Logger logger = Logger.getLogger( SimpleDiscontinuityDataRateReducerV2Test.class );

private GasWellDataSet dataSet = null;
private DateParser dateParser = null;


public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( SimpleDiscontinuityDataRateReducerV2Test.class );
}

@Before
public void setup()
{
   // populate some dummy data into the gas well data set....
    // --------------------------------------------------------
    dataSet = TestGasWellDataSet.getDummyDataSet();
    dateParser = new AussieDateParser();
}

@Test( expected = NullPointerException.class )
public void testConstructingReducerWithNullReductionParams()
{
    new SimpleDiscontinuityDataRateReducerV2( null );
}

@Test( expected = NullPointerException.class )
public void testReducingWithoutSpecifyingAnyData()
{
    ReductionParameters params = new ReductionParameters( WellMeasurementType.OIL_FLOW );
    DataRateReducer reducer = new SimpleDiscontinuityDataRateReducerV2( params );
    reducer.reduce( null );
}

@Test
public void testReducingDataToTenIntervals()
{
    DataRateReducer reducer;
    GasWellDataSet reducedDataSet;
    ReductionParameters params = new ReductionParameters( WellMeasurementType.OIL_FLOW );
    reducer = new SimpleDiscontinuityDataRateReducerV2( params );

    reducedDataSet = reducer.reduce( dataSet);
    assertNotNull( reducedDataSet );
    assertEquals( "Dummy", reducedDataSet.getWellName() );
    assertEquals( dataSet.from(), reducedDataSet.from() );
    assertEquals( dataSet.until(), reducedDataSet.until() );

    Map<WellMeasurementType,Double> errorMap = GasWellDataSetUtil.getError( dataSet, reducedDataSet );
    if ( errorMap.containsKey( WellMeasurementType.OIL_FLOW ) )
    {
        logger.debug( "error on OIL_FLOW=" + errorMap.get( WellMeasurementType.OIL_FLOW ) );
    }

    logger.debug( reducedDataSet );


}

@Test
public void testReducingSAA2FragmentData()
{
    DataRateReducer reducer;
    GasWellDataSet reducedDataSet;
    GasWellDataSet saa2FragmentDataSet = TestGasWellDataSet.getSAA2FragmentDataSet();
    ReductionParameters params = new ReductionParameters( WellMeasurementType.OIL_FLOW );
    reducer = new SimpleDiscontinuityDataRateReducerV2( params );


    reducedDataSet = reducer.reduce( saa2FragmentDataSet);
    assertNotNull( reducedDataSet );
    assertEquals( "SAA-2", reducedDataSet.getWellName() );
    assertEquals( saa2FragmentDataSet.from(), reducedDataSet.from() );
    assertEquals( saa2FragmentDataSet.until(), reducedDataSet.until() );

    
    // intervals should contain at least;
    // b01 ..... 30/JUL/2009 - 12/AUG/2009
    // b02 ..... 12/AUG/2009 - 15/AUG/2009 (OUTAGE)
    // b03 ..... 15/AUG/2009 - 19/AUG/2009
    // b04 ..... 19/AUG/2009 - 20/AUG/2009 (OUTAGE)
    // b05 ..... 20/AUG/2009 - 21/AUG/2009 (VERY LOW FLOW RATES)
    // b06 ..... 21/AUG/2009 - 23/AUG/2009 (OUTAGE)
    // b07 ..... 23/AUG/2009 - 15/SEP/2009
    // b08 ..... 15/SEP/2009 - 16/SEP/2009 (OUTAGE)
    // b09 ..... 16/SEP/2009 - 17/SEP/2009 (VERY FLOW FLOW RATES)
    // b10 ..... 17/SEP/2009 - 19/SEP/2009 (OUTAGE)
    // b11 ..... 19/SEP/2009 - 20/SEP/2009 (LOW FLOW RATES)
    // b12 ..... 20/SEP/2009 - 21/SEP/2009 (OUTAGE)
    // b13 ..... 21/SEP/2009 - 25/NOV/2009 (HIGH FLOW RATES)
    // ---------------------
    assertTrue( reducedDataSet.getData().size() >= 13 );


    Date[] expectedBoundary = new Date[] {
        dateParser.parse( "30/JUL/2009 00:00" ).getTime(),
        dateParser.parse( "12/AUG/2009 00:00" ).getTime(),
        dateParser.parse( "15/AUG/2009 00:00" ).getTime(),
        dateParser.parse( "19/AUG/2009 00:00" ).getTime(),
        dateParser.parse( "20/AUG/2009 00:00" ).getTime(),
        dateParser.parse( "21/AUG/2009 00:00" ).getTime(),
        dateParser.parse( "23/AUG/2009 00:00" ).getTime(),
        dateParser.parse( "15/SEP/2009 00:00" ).getTime(),
        dateParser.parse( "16/SEP/2009 00:00" ).getTime(),
        dateParser.parse( "17/SEP/2009 00:00" ).getTime(),
        dateParser.parse( "19/SEP/2009 00:00" ).getTime(),
        dateParser.parse( "20/SEP/2009 00:00" ).getTime(),
        dateParser.parse( "21/SEP/2009 00:00" ).getTime()
    };

    boolean[] boundaryFound = { false, false, false, false,false, false, false, false, false, false, false, false, false };
    
    for( GasWellDataEntry entry : reducedDataSet.getData() )
    {
        for( int i = 0; i < expectedBoundary.length; i++ )
        {
            // we are going to get milliseconds, so let's have a tolerance to within one second!!
            // -----------------------------------------------------------------------------------
            long delta = Math.abs( expectedBoundary[ i ].getTime() - entry.from().getTime() );
            if ( delta < 1000 )
            {
                if ( ! boundaryFound[ i ] )
                {
                    boundaryFound[ i ] = true;
                } else {
                    fail( "GasWellDataBoundary at \"" + expectedBoundary[ i ] + " \" has already been found!!" );
                }
            }
        }
    }
    
    for( int i = 0; i < boundaryFound.length; i++ )
    {
        assertTrue( boundaryFound[ i ] );
    }
    

 /*
    Map<WellMeasurementType,Double> errorMap = GasWellDataSetUtil.getError( dataSet, reducedDataSet );
    if ( errorMap.containsKey( WellMeasurementType.OIL_FLOW ) )
    {
        logger.debug( "error on OIL_FLOW=" + errorMap.get( WellMeasurementType.OIL_FLOW ) );
    }

    logger.debug( reducedDataSet );
 */
}

@Test
public void testReducingBY11Data()
{
    SimpleDiscontinuityDataRateReducerV2 reducer;
    GasWellDataSet reducedDataSet;
    GasWellDataSet by11Data = TestGasWellDataSet.getBY11DataSet();
    ReductionParameters params = new ReductionParameters( WellMeasurementType.CONDENSATE_FLOW );
    reducer = new SimpleDiscontinuityDataRateReducerV2( params );

    reducedDataSet = reducer.reduce( by11Data);
    assertNotNull( reducedDataSet );
    assertEquals( "BY#11", reducedDataSet.getWellName() );
    assertEquals( by11Data.from(), reducedDataSet.from() );
    assertEquals( by11Data.until(), reducedDataSet.until() );


    // intervals should contain at least;
    // b01 ..... 30/JUL/2009 - 12/AUG/2009
    // b02 ..... 12/AUG/2009 - 15/AUG/2009 (OUTAGE)
    // b03 ..... 15/AUG/2009 - 19/AUG/2009
    // b04 ..... 19/AUG/2009 - 20/AUG/2009 (OUTAGE)
    // b05 ..... 20/AUG/2009 - 21/AUG/2009 (VERY LOW FLOW RATES)
    // b06 ..... 21/AUG/2009 - 23/AUG/2009 (OUTAGE)
    // b07 ..... 23/AUG/2009 - 15/SEP/2009
    // b08 ..... 15/SEP/2009 - 16/SEP/2009 (OUTAGE)
    // b09 ..... 16/SEP/2009 - 17/SEP/2009 (VERY FLOW FLOW RATES)
    // b10 ..... 17/SEP/2009 - 19/SEP/2009 (OUTAGE)
    // b11 ..... 19/SEP/2009 - 20/SEP/2009 (LOW FLOW RATES)
    // b12 ..... 20/SEP/2009 - 21/SEP/2009 (OUTAGE)
    // b13 ..... 21/SEP/2009 - 25/NOV/2009 (HIGH FLOW RATES)
    // ---------------------
    assertTrue( reducedDataSet.getData().size() >= 13 );


    Date[] expectedBoundary = new Date[] {
        dateParser.parse( "30/JUL/2009 00:00" ).getTime(),
        dateParser.parse( "12/AUG/2009 00:00" ).getTime(),
        dateParser.parse( "15/AUG/2009 00:00" ).getTime(),
        dateParser.parse( "19/AUG/2009 00:00" ).getTime(),
        dateParser.parse( "20/AUG/2009 00:00" ).getTime(),
        dateParser.parse( "21/AUG/2009 00:00" ).getTime(),
        dateParser.parse( "23/AUG/2009 00:00" ).getTime(),
        dateParser.parse( "15/SEP/2009 00:00" ).getTime(),
        dateParser.parse( "16/SEP/2009 00:00" ).getTime(),
        dateParser.parse( "17/SEP/2009 00:00" ).getTime(),
        dateParser.parse( "19/SEP/2009 00:00" ).getTime(),
        dateParser.parse( "20/SEP/2009 00:00" ).getTime(),
        dateParser.parse( "21/SEP/2009 00:00" ).getTime()
    };

    boolean[] boundaryFound = { false, false, false, false,false, false, false, false, false, false, false, false, false };

    for( GasWellDataEntry entry : reducedDataSet.getData() )
    {
        for( int i = 0; i < expectedBoundary.length; i++ )
        {
            // we are going to get milliseconds, so let's have a tolerance to within one second!!
            // -----------------------------------------------------------------------------------
            long delta = Math.abs( expectedBoundary[ i ].getTime() - entry.from().getTime() );
            if ( delta < 1000 )
            {
                if ( ! boundaryFound[ i ] )
                {
                    boundaryFound[ i ] = true;
                } else {
                    fail( "GasWellDataBoundary at \"" + expectedBoundary[ i ] + " \" has already been found!!" );
                }
            }
        }
    }

    for( int i = 0; i < boundaryFound.length; i++ )
    {
        assertTrue( boundaryFound[ i ] );
    }


 /*
    Map<WellMeasurementType,Double> errorMap = GasWellDataSetUtil.getError( dataSet, reducedDataSet );
    if ( errorMap.containsKey( WellMeasurementType.OIL_FLOW ) )
    {
        logger.debug( "error on OIL_FLOW=" + errorMap.get( WellMeasurementType.OIL_FLOW ) );
    }

    logger.debug( reducedDataSet );
 */
}

}
