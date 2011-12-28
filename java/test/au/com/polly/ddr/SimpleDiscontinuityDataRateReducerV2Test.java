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

import au.com.polly.plotter.TimeUnit;
import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import junit.framework.JUnit4TestAdapter;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Battery of tests for the revised discontinuity reducer...
 */
@RunWith(JUnit4.class)
public class SimpleDiscontinuityDataRateReducerV2Test
{
static private final Logger logger = Logger.getLogger( SimpleDiscontinuityDataRateReducerV2Test.class );
private final static double ACCEPTABLE_ERROR = 1E-8;

private GasWellDataSet dataSet = null;
private DateParser parser = null;


public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( SimpleDiscontinuityDataRateReducerV2Test.class );
}

@Before
public void setup()
{
   // populate some dummy data into the gas well data set....
    // --------------------------------------------------------
    dataSet = TestGasWellDataSet.getDummyDataSet();
    parser = new AussieDateParser();
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
public void testReducingTestReductionData()
{
    SimpleDiscontinuityDataRateReducerV2 reducer;
    GasWellDataSet reducedDataSet;
    GasWellDataSet originalData = TestGasWellDataSet.getReductionTestDataSet();
    ReductionParameters params = new ReductionParameters( WellMeasurementType.OIL_FLOW, WellMeasurementType.WATER_FLOW );
    params.detectMedianCrossers = true;
    reducer = new SimpleDiscontinuityDataRateReducerV2( params );
    GasWellDataEntry entry;

    reducedDataSet = reducer.reduce( originalData );
    assertNotNull( reducedDataSet );
    assertEquals( "osho", reducedDataSet.getWellName() );
    assertEquals( originalData.from(), reducedDataSet.from() );
    assertEquals( originalData.until(), reducedDataSet.until() );
    
    logger.debug( reducedDataSet.toString() );

    // intervals should contain at least;
    // b01 ..... 01/JUN/2012 - 05/JUN/2012 (START)
    // b02 ..... 05/JUN/2012 - 08/JUN/2012 (PRIMARY_OUTAGE_START)
    // b03 ..... 08/JUN/2012 - 09/JUN/2012 (PRIMARY_OUTAGE_END)
    // b04 ..... 09/JUN/2012 - 11/JUN/2012 (PRIMARY_OUTAGE_START)
    // b05 ..... 11/JUN/2012 - 12/JUN/2012 (PRIMARY_OUTAGE_END)
    // b06 ..... 12/JUN/2012 - 22/JUN/2012 (SECONDARY_OUTAGE_END)
    // b06 ..... 22/JUN/2012 - 25/JUN/2012 (SECONDARY_OUTAGE_START)
    // b07 ..... 25/JUN/2012 - 27/JUN/2012 (SECONDARY_OUTAGE_END)
    // and some median crossing events too...
    // --------------------------------------------------------------
    assertTrue( reducedDataSet.getData().size() == 8 );

    entry = reducedDataSet.getEntry( 0 );
    assertNotNull( entry );
    assertTrue( entry.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    assertTrue( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ) );
    assertEquals( parser.parse( "01/JUN/2012" ).getTime(), entry.from() );
    assertEquals( parser.parse( "05/JUN/2012" ).getTime(), entry.until() );
    assertEquals( ( 10.0 + 11.0 + 9.5 + 5.0 ) / 4.0, entry.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( ( 8.0 + 9.0 + 8.2 + 6.0 ) / 4.0, entry.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
    
    entry = reducedDataSet.getEntry( 1 );
    assertNotNull( entry );
    assertTrue( entry.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    assertTrue( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ) );
    assertEquals( parser.parse( "05/JUN/2012" ).getTime(), entry.from() );
    assertEquals( parser.parse( "08/JUN/2012" ).getTime(), entry.until() );
    assertEquals( 0.0, entry.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 2.0 / 3.0, entry.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

    entry = reducedDataSet.getEntry( 2 );
    assertNotNull( entry );
    assertTrue( entry.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    assertTrue( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ) );
    assertEquals( parser.parse( "08/JUN/2012" ).getTime(), entry.from() );
    assertEquals( parser.parse( "09/JUN/2012" ).getTime(), entry.until() );
    assertEquals( 0.6, entry.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, entry.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

    entry = reducedDataSet.getEntry( 3 );
    assertNotNull( entry );
    assertTrue( entry.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    assertTrue( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ) );
    assertEquals( parser.parse( "09/JUN/2012" ).getTime(), entry.from() );
    assertEquals( parser.parse( "11/JUN/2012" ).getTime(), entry.until() );
    assertEquals( 0.0, entry.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, entry.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

    entry = reducedDataSet.getEntry( 4 );
    assertNotNull( entry );
    assertTrue( entry.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    assertTrue( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ) );
    assertEquals( parser.parse( "11/JUN/2012" ).getTime(), entry.from() );
    assertEquals( parser.parse( "12/JUN/2012" ).getTime(), entry.until() );
    assertEquals( 5.0, entry.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, entry.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

    entry = reducedDataSet.getEntry( 5 );
    assertNotNull(entry);
    assertTrue( entry.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    assertTrue( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ) );
    assertEquals( parser.parse( "12/JUN/2012" ).getTime(), entry.from() );
    assertEquals( parser.parse( "22/JUN/2012" ).getTime(), entry.until() );
    assertEquals( ( 9.0 + 12.0 + 12.7 + 11.4 + 14.0 + 15.0 + 14.4 + 13.0 + 14.0 + 13.4  ) / 10.0, entry.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( ( 1.0 + 5.0 + 9.0 + 10.9 + 12.2 + 12.8 + 13.4 + 14.8 + 13.9 + 13.2  ) / 10.0, entry.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

    entry = reducedDataSet.getEntry( 6 );
    assertNotNull( entry );
    assertTrue( entry.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    assertTrue( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ) );
    assertEquals( parser.parse( "22/JUN/2012" ).getTime(), entry.from() );
    assertEquals( parser.parse( "25/JUN/2012" ).getTime(), entry.until() );
    assertEquals( ( 12.6 + 11.0 + 10.4  ) / 3.0, entry.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, entry.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

    entry = reducedDataSet.getEntry( 7 );
    assertNotNull( entry );
    assertTrue( entry.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    assertTrue( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ) );
    assertEquals( parser.parse( "25/JUN/2012" ).getTime(), entry.from() );
    assertEquals( parser.parse( "27/JUN/2012" ).getTime(), entry.until() );
    assertEquals( ( 10.9 + 10.0 ) / 2.0, entry.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( ( 13.0 + 11.8 ) / 2.0, entry.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
}

@Test
public void testInsertingRegularThreeMonthBoundaries()
{
    dataSet = TestGasWellDataSet.getOldFaithfulDataSet();
    assertNotNull( dataSet );
    assertEquals(parser.parse("01/JAN/2012").getTime(), dataSet.from());
    assertEquals(parser.parse("01/JAN/2015").getTime(), dataSet.until());
    assertEquals( 365 + 366 + 365, dataSet.getData().size() );
    
    ReductionParameters recipe = new ReductionParameters( WellMeasurementType.OIL_FLOW, WellMeasurementType.WATER_FLOW, true, dataSet.from(), 3, TimeUnit.MONTH );
    DataRateReducer reducer = new SimpleDiscontinuityDataRateReducerV2( recipe );
    GasWellDataSet reduced = reducer.reduce( dataSet );
    
    assertNotNull( reduced );
    assertEquals( 12, reduced.getData().size() );
    
    GasWellDataEntry entry;
    
    entry = reduced.getData().get( 0 );
    assertEquals( parser.parse( "01/JAN/2012" ).getTime(), entry.from() );
    assertEquals( parser.parse( "01/APR/2012" ).getTime(), entry.until() );

    entry = reduced.getData().get( 1 );
    assertEquals( parser.parse( "01/APR/2012" ).getTime(), entry.from() );
    assertEquals( parser.parse( "01/JUL/2012" ).getTime(), entry.until() );

    entry = reduced.getData().get( 2 );
    assertEquals( parser.parse( "01/JUL/2012" ).getTime(), entry.from() );
    assertEquals( parser.parse( "01/OCT/2012" ).getTime(), entry.until() );

    entry = reduced.getData().get( 3 );
    assertEquals( parser.parse( "01/OCT/2012" ).getTime(), entry.from() );
    assertEquals( parser.parse( "01/JAN/2013" ).getTime(), entry.until() );

    entry = reduced.getData().get( 4 );
    assertEquals( parser.parse( "01/JAN/2013" ).getTime(), entry.from() );
    assertEquals( parser.parse( "01/APR/2013" ).getTime(), entry.until() );

    entry = reduced.getData().get( 5 );
    assertEquals( parser.parse( "01/APR/2013" ).getTime(), entry.from() );
    assertEquals( parser.parse( "01/JUL/2013" ).getTime(), entry.until() );

    entry = reduced.getData().get( 6 );
    assertEquals( parser.parse( "01/JUL/2013" ).getTime(), entry.from() );
    assertEquals( parser.parse( "01/OCT/2013" ).getTime(), entry.until() );

    entry = reduced.getData().get( 7 );
    assertEquals( parser.parse( "01/OCT/2013" ).getTime(), entry.from() );
    assertEquals( parser.parse( "01/JAN/2014" ).getTime(), entry.until() );

    entry = reduced.getData().get( 8 );
    assertEquals( parser.parse( "01/JAN/2014" ).getTime(), entry.from() );
    assertEquals( parser.parse( "01/APR/2014" ).getTime(), entry.until() );

    entry = reduced.getData().get( 9 );
    assertEquals( parser.parse( "01/APR/2014" ).getTime(), entry.from() );
    assertEquals( parser.parse( "01/JUL/2014" ).getTime(), entry.until() );

    entry = reduced.getData().get( 10 );
    assertEquals( parser.parse( "01/JUL/2014" ).getTime(), entry.from() );
    assertEquals( parser.parse( "01/OCT/2014" ).getTime(), entry.until() );

    entry = reduced.getData().get( 11 );
    assertEquals( parser.parse( "01/OCT/2014" ).getTime(), entry.from() );
    assertEquals( parser.parse( "01/JAN/2015" ).getTime(), entry.until() );
}

}
