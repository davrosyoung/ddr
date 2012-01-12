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
import au.com.polly.util.DateRange;
import junit.framework.JUnit4TestAdapter;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Exercise the methods of the gas well data set utility class.
 */
@RunWith(JUnit4.class)
public class GasWellDataSetUtilTest
{
static Logger logger = Logger.getLogger( GasWellDataSetUtilTest.class );
private final static double ACCEPTABLE_ERROR = 1E-8;
GasWellDataSet littleDataSet;
GasWellDataSet reducedDataSet;
static DateParser dateParser;
Calendar twentyThirdApril;
Calendar twentyThirdAprilFiveInTheMorning;
Calendar twentyThirdAprilJustBeforeNoon;
Calendar endJuly;
GasWell davesWell = null;
GasWell dummyWell;
GasWellDataSet smallDataSet;
GasWellDataSet smallReducedDataSet;

public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( GasWellDataSetUtilTest.class );
}

@Before
public void setupData()
{
    dateParser = new AussieDateParser();
    twentyThirdApril = dateParser.parse( "23/APRIL/2011" );
    twentyThirdAprilFiveInTheMorning = dateParser.parse( "23/APRIL/2011 05:00" );
    twentyThirdAprilJustBeforeNoon = dateParser.parse( "23/APRIL/2011 11:59:59" );
    endJuly = dateParser.parse( "31/JUL/2011 23:59:59" );

    davesWell = new GasWell( "Dave's Well" );
    dummyWell = new GasWell( "Dummy" );
    GasWellDataEntry entry;
    Calendar when;

	TestGasWellDataSet.repopulate();


    // populate some dummy data into the gas well data set....
    // --------------------------------------------------------
    this.littleDataSet = TestGasWellDataSet.getDummyDataSet();

    reducedDataSet = new GasWellDataSet( littleDataSet, new Date[]{
            dateParser.parse( "13/JUNE/2011 04:00").getTime(),
            dateParser.parse( "14/JUNE/2011 04:00").getTime(),
            dateParser.parse( "15/JUNE/2011 04:00").getTime(),
            dateParser.parse( "16/JUNE/2011 04:00").getTime(),
            dateParser.parse( "17/JUNE/2011 04:00").getTime(),
            dateParser.parse( "17/JUNE/2011 07:59:59").getTime()
    } );

    smallDataSet = TestGasWellDataSet.getSmallDataSet();
    smallReducedDataSet = TestGasWellDataSet.getSmallReducedDataSet();

}

@Test( expected=NullPointerException.class )
public void testErrorWithNullArgs()
{
    Map<WellMeasurementType,Double> error = GasWellDataSetUtil.getError( null, null );
}

@Test( expected=NullPointerException.class )
public void testErrorWithOneNullArg()
{
    Map<WellMeasurementType,Double> error = GasWellDataSetUtil.getError( null, littleDataSet );
}


@Test( expected=NullPointerException.class )
public void testErrorWithADifferentNullArg()
{
    Map<WellMeasurementType,Double> error = GasWellDataSetUtil.getError( littleDataSet, null );
}


@Test
public void testErrorAgainstReducedDataSet()
{

    Map<WellMeasurementType,Double> error = GasWellDataSetUtil.getError( smallDataSet, smallReducedDataSet );
    assertNotNull( error );
    assertFalse( error.containsKey( WellMeasurementType.CONDENSATE_FLOW ) );
    assertTrue( error.containsKey( WellMeasurementType.GAS_FLOW ) );
    assertTrue( error.containsKey( WellMeasurementType.WATER_FLOW ) );
    assertTrue( error.containsKey( WellMeasurementType.OIL_FLOW ) );
    assertEquals( 0.9, error.get( WellMeasurementType.OIL_FLOW), ACCEPTABLE_ERROR );
    assertEquals( 0.06, error.get( WellMeasurementType.GAS_FLOW), ACCEPTABLE_ERROR );
    assertEquals( 0.6, error.get( WellMeasurementType.WATER_FLOW), ACCEPTABLE_ERROR );
}

@Test
public void testCalculateVolumeAgainstSmallDataSet()
{
    Map<WellMeasurementType,Double> totalVolumeMap = GasWellDataSetUtil.calculateTotalVolume( smallDataSet );
    assertNotNull( totalVolumeMap );
    assertTrue( totalVolumeMap.containsKey( WellMeasurementType.GAS_FLOW ));
    assertTrue( totalVolumeMap.containsKey( WellMeasurementType.WATER_FLOW ));
    assertTrue( totalVolumeMap.containsKey( WellMeasurementType.OIL_FLOW ));
    assertFalse( totalVolumeMap.containsKey( WellMeasurementType.CONDENSATE_FLOW ));

    assertEquals( 10.975, totalVolumeMap.get( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.25125, totalVolumeMap.get( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 4.50416666, totalVolumeMap.get( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
}

@Test
public void testCalculateVolumeAgainstSmallReducedDataSet()
{
    Map<WellMeasurementType,Double> totalVolumeMap = GasWellDataSetUtil.calculateTotalVolume( smallReducedDataSet );
    assertNotNull( totalVolumeMap );
    assertTrue( totalVolumeMap.containsKey( WellMeasurementType.GAS_FLOW ));
    assertTrue( totalVolumeMap.containsKey( WellMeasurementType.WATER_FLOW ));
    assertTrue( totalVolumeMap.containsKey( WellMeasurementType.OIL_FLOW ));
    assertFalse( totalVolumeMap.containsKey( WellMeasurementType.CONDENSATE_FLOW ));

    assertEquals( 10.975, totalVolumeMap.get( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.25125, totalVolumeMap.get( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 4.50416666, totalVolumeMap.get( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
}

@Test
public void testDifferenceBetweenSmallAndSmallReducedDataSets()
{
    Map<WellMeasurementType,Double> deltaVolumeMap = GasWellDataSetUtil.getDelta(smallDataSet, smallReducedDataSet);
    assertNotNull( deltaVolumeMap );
    assertTrue( deltaVolumeMap.containsKey( WellMeasurementType.GAS_FLOW ));
    assertTrue( deltaVolumeMap.containsKey( WellMeasurementType.WATER_FLOW ));
    assertTrue( deltaVolumeMap.containsKey( WellMeasurementType.OIL_FLOW ));
    assertFalse( deltaVolumeMap.containsKey( WellMeasurementType.CONDENSATE_FLOW ));

    assertEquals( 0.0, deltaVolumeMap.get( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, deltaVolumeMap.get( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, deltaVolumeMap.get( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
}

@Test( expected=NullPointerException.class)
public void testCalculateVolumeWithNullDataSet()
{
    GasWellDataSetUtil.calculateTotalVolume( null, null, null );
}

@Test( expected=IllegalArgumentException.class )
public void testCalculateVolumeWithDateRangeBeforeValidRange()
{
    GasWellDataSetUtil.calculateTotalVolume(
            TestGasWellDataSet.getSAA2FragmentDataSet(),
            dateParser.parse( "23/JUL/2009" ).getTime(),
            dateParser.parse( "26/JUL/2009 23:59:59").getTime()
    );
    
}

@Test( expected = IllegalArgumentException.class )
public void testCalculateVolumeWithDateRangeAfterValidRange()
{
    GasWellDataSetUtil.calculateTotalVolume(
            TestGasWellDataSet.getSAA2FragmentDataSet(),
            dateParser.parse( "25/NOV/2009 00:00:01" ).getTime(),
            dateParser.parse( "26/NOV/2009 23:59:59").getTime()
    );    
}

@Test( expected = IllegalArgumentException.class )
public void testCalculateVolumeWithDateRangeBeyondValidRange()
{
    GasWellDataSetUtil.calculateTotalVolume(
            TestGasWellDataSet.getSAA2FragmentDataSet(),
            dateParser.parse( "26/JUL/2009 23:59:59" ).getTime(),
            dateParser.parse( "25/NOV/2009 00:00:01").getTime()
    );

}

@Test
public void testCalculateVolumeForFirstEightDays()
{
    Map<WellMeasurementType,Double> volumes = GasWellDataSetUtil.calculateTotalVolume(
            TestGasWellDataSet.getSAA2FragmentDataSet(),
            dateParser.parse( "30/JUL/2009" ).getTime(),
            dateParser.parse( "07/AUG/2009" ).getTime()
    );
    
    assertNotNull( volumes );
    assertTrue( volumes.containsKey( WellMeasurementType.GAS_FLOW ) );
    assertTrue( volumes.containsKey( WellMeasurementType.OIL_FLOW ) );
    assertTrue( volumes.containsKey( WellMeasurementType.WATER_FLOW ) );
    assertFalse( volumes.containsKey( WellMeasurementType.CONDENSATE_FLOW ) );
    
    assertEquals( 2082.9, volumes.get( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 1.41, volumes.get( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 6879.32, volumes.get( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
}

@Test
public void testCalculateVolumeForTwelveHours()
{
    Map<WellMeasurementType,Double> volumes = GasWellDataSetUtil.calculateTotalVolume(
            TestGasWellDataSet.getSAA2FragmentDataSet(),
            dateParser.parse( "30/JUL/2009 12:00" ).getTime(),
            dateParser.parse( "31/JUL/2009" ).getTime()
    );

    assertNotNull( volumes );
    assertTrue(volumes.containsKey(WellMeasurementType.GAS_FLOW));
    assertTrue(volumes.containsKey(WellMeasurementType.OIL_FLOW));
    assertTrue(volumes.containsKey(WellMeasurementType.WATER_FLOW));
    assertFalse(volumes.containsKey(WellMeasurementType.CONDENSATE_FLOW));

    assertEquals( 143.255, volumes.get( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.095, volumes.get( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 475.82, volumes.get( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
}

@Test
public void testCalculateVolumeWithoutDates()
{
    Map<WellMeasurementType,Double> volumes = GasWellDataSetUtil.calculateTotalVolume(
            TestGasWellDataSet.getSAA2FragmentDataSet()
    );

    assertNotNull( volumes );
    assertTrue( volumes.containsKey( WellMeasurementType.GAS_FLOW ) );
    assertTrue( volumes.containsKey( WellMeasurementType.OIL_FLOW ) );
    assertTrue( volumes.containsKey( WellMeasurementType.WATER_FLOW ) );
    assertFalse( volumes.containsKey( WellMeasurementType.CONDENSATE_FLOW ) );

    assertEquals( 278990.4, volumes.get( WellMeasurementType.OIL_FLOW ), 0.01 );
    assertEquals( 105.72,   volumes.get( WellMeasurementType.GAS_FLOW ), 0.01 );
    assertEquals( 395861.3, volumes.get( WellMeasurementType.WATER_FLOW ), 0.01 );

}


	@Test( expected = NullPointerException.class )
	public void testMovingNullBoundaryOnSAA2FragmentData()
	{
		GasWellDataSet ds = TestGasWellDataSet.getSAA2FragmentDataSet();
		GasWellDataSetUtil.moveAveragedDataBoundary( null, null, ds, ds );
	}

	@Test( expected = NullPointerException.class )
	public void testMovingBoundaryToNullValueOnSAA2FragmentData()
	{
		GasWellDataSet ds = TestGasWellDataSet.getSAA2FragmentDataSet();
		GasWellDataSetUtil.moveAveragedDataBoundary( null, dateParser.parse( "30/Jul/2009 12:00:00" ).getTime(), ds, ds );
	}

/*
       new OilDataSet( parser.parse( "30/Jul/2009 00:00:00" ).getTime(), 286.510,0.190,951.640, 24.0 ),
        new OilDataSet( parser.parse( "31/Jul/2009 00:00:00" ).getTime(), 276.880,0.190,923.830, 24.0 ),
        new OilDataSet( parser.parse( "01/Aug/2009 00:00:00" ).getTime(), 284.430,0.190,926.970, 24.0 ),
        new OilDataSet( parser.parse( "02/Aug/2009 00:00:00" ).getTime(), 290.260,0.190,935.960, 24.0 ),
        new OilDataSet( parser.parse( "03/Aug/2009 00:00:00" ).getTime(), 248.420,0.170,783.260, 24.0 ),
 */


	@Test( expected = IllegalArgumentException.class )
	public void testMovingStartOfDataBoundaryOnSAA2FragmentData()
	{
		GasWellDataSet ds = TestGasWellDataSet.getSAA2FragmentDataSet();
		GasWellDataSet reduced = TestGasWellDataSet.getReducedSAA2FragmentDataSet();
		GasWellDataSetUtil.moveAveragedDataBoundary( dateParser.parse( "30/Jul/2009 12:00:00" ).getTime(), dateParser.parse( "30/Jul/2009 18:00:00" ).getTime(), ds, reduced );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testMovingEndOfDataBoundaryOnSAA2FragmentData()
	{
		GasWellDataSet ds = TestGasWellDataSet.getSAA2FragmentDataSet();
		GasWellDataSet reduced = TestGasWellDataSet.getReducedSAA2FragmentDataSet();
		GasWellDataSetUtil.moveAveragedDataBoundary( dateParser.parse( "26/NOV/2009 00:00:00" ).getTime(), dateParser.parse( "25/NOV/2009 12:00:00" ).getTime(), ds, reduced );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testMovingNonExistantBoundaryOnSAA2FragmentData()
	{
		GasWellDataSet ds = TestGasWellDataSet.getSAA2FragmentDataSet();
		GasWellDataSet reduced = TestGasWellDataSet.getReducedSAA2FragmentDataSet();
		GasWellDataSetUtil.moveAveragedDataBoundary( dateParser.parse( "30/JUL/2009 12:00:00" ).getTime(), dateParser.parse( "30/JUL/2009 13:00:00" ).getTime(), ds, reduced );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testMovingBoundaryBeforeStartOfDataOnSAA2FragmentData()
	{
		GasWellDataSet ds = TestGasWellDataSet.getSAA2FragmentDataSet();
		GasWellDataSet reduced = TestGasWellDataSet.getReducedSAA2FragmentDataSet();
		GasWellDataSetUtil.moveAveragedDataBoundary( dateParser.parse( "31/JUL/2009" ).getTime(), dateParser.parse( "29/JUL/2009 13:00:00" ).getTime(), ds, reduced );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testMovingBoundaryBeforeStartOfPreviousEntryOnSAA2FragmentData()
	{
		GasWellDataSet ds = TestGasWellDataSet.getSAA2FragmentDataSet();
		GasWellDataSet reduced = TestGasWellDataSet.getReducedSAA2FragmentDataSet();
		GasWellDataSetUtil.moveAveragedDataBoundary( dateParser.parse( "31/JUL/2009" ).getTime(), dateParser.parse( "29/JUL/2009 23:59:59" ).getTime(), ds, reduced );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testMovingBoundaryToStartOfPreviousEntryOnSAA2FragmentData()
	{
		GasWellDataSet ds = TestGasWellDataSet.getSAA2FragmentDataSet();
		GasWellDataSet reduced = TestGasWellDataSet.getReducedSAA2FragmentDataSet();

		GasWellDataSetUtil.moveAveragedDataBoundary( dateParser.parse( "31/JUL/2009" ).getTime(), dateParser.parse( "30/JUL/2009" ).getTime(), ds, reduced );
	}

	@Test
	public void testMovingBoundaryToOneSecondAfterStartOfPreviousEntryOnSAA2FragmentData()
	{
		GasWellDataSet ds = TestGasWellDataSet.getSAA2FragmentDataSet();
		GasWellDataSet reduced = TestGasWellDataSet.getReducedSAA2FragmentDataSet();

		Date stampBefore = dateParser.parse( "12/AUG/2009" ).getTime();
		Date stampAfter = dateParser.parse( "30/JUL/2009 00:00:01" ).getTime();

		GasWellDataSetUtil.moveAveragedDataBoundary( stampBefore, stampAfter, ds, reduced );
		GasWellDataEntry e0 = reduced.getEntry( 0 );
		GasWellDataEntry e1 = reduced.getEntry( 1 );
		GasWellDataEntry e2 = reduced.getEntry( 2 );

		assertEquals( dateParser.parse( "30/JUL/2009").getTime(), e0.from() );
		assertEquals( dateParser.parse( "30/JUL/2009 00:00:01" ).getTime(), e0.until() );
		assertEquals( 1000, e0.getIntervalLengthMS() );

		assertEquals( dateParser.parse( "30/JUL/2009 00:00:01").getTime(), e1.from() );
		assertEquals( dateParser.parse( "15/AUG/2009" ).getTime(), e1.until() );
		assertEquals( ( 16 * 86400000 ) - 1000L, e1.getIntervalLengthMS() );

		// 14 days = 1209600 seconds
		// 3 days = 259200 seconds
		// 17 days = 1468800 seconds
		GasWellDataEntry s0 = ds.consolidateEntries( dateParser.parse(  "30/JUL/2009 00:00:00" ).getTime(), dateParser.parse(  "30/JUL/2009 00:00:01" ).getTime() );
		GasWellDataEntry s1 = ds.consolidateEntries( dateParser.parse(  "30/JUL/2009 00:00:01" ).getTime(), dateParser.parse(  "15/AUG/2009 00:00:00" ).getTime() );

		assertEquals( s0.getMeasurement(  WellMeasurementType.OIL_FLOW ), e0.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
		assertEquals( s0.getMeasurement(  WellMeasurementType.GAS_FLOW ), e0.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
		assertEquals( s0.getMeasurement(  WellMeasurementType.WATER_FLOW ), e0.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

		assertEquals( dateParser.parse( "15/AUG/09" ).getTime(), e2.from() );
		assertEquals( dateParser.parse( "19/AUG/09" ).getTime(), e2.until() );
		assertEquals( 86400000L * 4, e2.getIntervalLengthMS() );
		assertEquals( 113.1075, e2.getMeasurement(WellMeasurementType.OIL_FLOW) );
		assertEquals( 0.08, e2.getMeasurement(WellMeasurementType.GAS_FLOW) );
		assertEquals( 397.39, e2.getMeasurement( WellMeasurementType.WATER_FLOW ) );
	}





	@Test( expected = IllegalArgumentException.class )
	public void testMovingBoundaryAfterEndOfDataOnSAA2FragmentData()
	{
		GasWellDataSet ds = TestGasWellDataSet.getSAA2FragmentDataSet();
		GasWellDataSetUtil.moveAveragedDataBoundary( dateParser.parse( "25/NOV/2009" ).getTime(), dateParser.parse( "27/NOV/2009" ).getTime(), ds, ds );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testMovingBoundaryAfterEndOfNextEntryOnSAA2FragmentData()
	{
		GasWellDataSet ds = TestGasWellDataSet.getSAA2FragmentDataSet();
		GasWellDataSetUtil.moveAveragedDataBoundary( dateParser.parse( "31/JUL/2009" ).getTime(), dateParser.parse( "1/AUG/2009 00:00:01" ).getTime(), ds, ds );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testMovingBoundaryToEndOfNextEntryOnSAA2FragmentData()
	{
		GasWellDataSet ds = TestGasWellDataSet.getSAA2FragmentDataSet();
		GasWellDataSetUtil.moveAveragedDataBoundary( dateParser.parse( "31/JUL/2009" ).getTime(), dateParser.parse( "1/AUG/2009" ).getTime(),ds, ds );
	}


	@Test
	public void testMovingBoundaryForwardTwelveHoursOnSAA2FragmentData()
	{
		GasWellDataSet ds = TestGasWellDataSet.getSAA2FragmentDataSet();
		GasWellDataSetUtil.moveAveragedDataBoundary( dateParser.parse( "31/JUL/2009" ).getTime(), dateParser.parse( "31/JUL/2009 12:00:00" ).getTime(), ds, ds );
		GasWellDataEntry e0 = ds.getEntry( 0 );
		GasWellDataEntry e1 = ds.getEntry( 1 );
		GasWellDataEntry e2 = ds.getEntry( 2 );

		assertEquals( dateParser.parse( "30/JUL/2009").getTime(), e0.from() );
		assertEquals( dateParser.parse( "31/JUL/2009 12:00:00" ).getTime(), e0.until() );
		assertEquals( ( 86400 + 43200 ) * 1000L, e0.getIntervalLengthMS() );

		assertEquals( ( ( 2.0 / 3.0 ) * 286.51 ) + ( ( 1.0 / 3.0 ) * 276.88 ), e0.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
		assertEquals( 0.19, e0.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
		assertEquals( ( ( 2.0 / 3.0 ) * 951.64 ) + ( ( 1.0 / 3.0 ) * 923.83) , e0.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

		assertEquals( dateParser.parse( "31/JUL/2009 12:00:00").getTime(), e1.from() );
		assertEquals( dateParser.parse( "1/AUG/2009" ).getTime(), e1.until() );
		assertEquals( 43200000, e1.getIntervalLengthMS() );

		assertEquals( 276.88, e1.getMeasurement( WellMeasurementType.OIL_FLOW ),   ACCEPTABLE_ERROR );
		assertEquals( 0.19,   e1.getMeasurement( WellMeasurementType.GAS_FLOW ),   ACCEPTABLE_ERROR );
		assertEquals( 923.83, e1.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

		assertEquals( dateParser.parse( "1/AUG/09" ).getTime(), e2.from() );
		assertEquals( dateParser.parse( "2/AUG/09" ).getTime(), e2.until() );
		assertEquals( 86400000L, e2.getIntervalLengthMS() );
		assertEquals( 284.43, e2.getMeasurement( WellMeasurementType.OIL_FLOW ) );
		assertEquals( 0.19, e2.getMeasurement( WellMeasurementType.GAS_FLOW ) );
		assertEquals( 926.97, e2.getMeasurement( WellMeasurementType.WATER_FLOW ) );

	}



	@Test
	public void testMovingBoundaryBackwardsTwelveHoursOnSAA2FragmentData()
	{
		GasWellDataSet ds = TestGasWellDataSet.getSAA2FragmentDataSet();
		GasWellDataSetUtil.moveAveragedDataBoundary( dateParser.parse( "31/JUL/2009" ).getTime(), dateParser.parse( "30/JUL/2009 06:00:00" ).getTime(), ds, ds );
		GasWellDataEntry e0 = ds.getEntry( 0 );
		GasWellDataEntry e1 = ds.getEntry( 1 );
		GasWellDataEntry e2 = ds.getEntry( 2 );

		assertEquals( dateParser.parse( "30/JUL/2009").getTime(), e0.from() );
		assertEquals( dateParser.parse( "30/JUL/2009 06:00" ).getTime(), e0.until() );
		assertEquals( 21600000L, e0.getIntervalLengthMS() );
		assertEquals( 286.51, e0.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
		assertEquals( 0.19,   e0.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
		assertEquals( 951.64, e0.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

		assertEquals( dateParser.parse( "30/JUL/2009 06:00:00").getTime(), e1.from() );
		assertEquals( dateParser.parse( "1/AUG/2009" ).getTime(), e1.until() );
		assertEquals( ( 86400 + 86400 - 21600 ) * 1000L, e1.getIntervalLengthMS() );
		assertEquals( ( ( 3.0 / 7.0 ) * 286.51 ) + ( ( 4.0 / 7.0 ) * 276.88 ), e1.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
		assertEquals( 0.19, e1.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
		assertEquals( ( ( 3.0 / 7.0 ) * 951.64 ) + ( ( 4.0 / 7.0 ) * 923.83) , e1.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );


		assertEquals( dateParser.parse( "1/AUG/09" ).getTime(), e2.from() );
		assertEquals( dateParser.parse( "2/AUG/09" ).getTime(), e2.until() );
		assertEquals( 86400000L, e2.getIntervalLengthMS() );
		assertEquals( 284.43, e2.getMeasurement( WellMeasurementType.OIL_FLOW ) );
		assertEquals( 0.19, e2.getMeasurement( WellMeasurementType.GAS_FLOW ) );
		assertEquals( 926.97, e2.getMeasurement( WellMeasurementType.WATER_FLOW ) );

	}

	@Test
	public void testMovingBoundaryForwardSixHoursOnSAA2FragmentData()
	{
		GasWellDataSet ds = TestGasWellDataSet.getSAA2FragmentDataSet();
		GasWellDataSet reduced = TestGasWellDataSet.getReducedSAA2FragmentDataSet();
		GasWellDataSetUtil.moveAveragedDataBoundary( dateParser.parse( "15/AUG/2009" ).getTime(), dateParser.parse( "15/AUG/2009 6:00:00" ).getTime(), ds, reduced );
		GasWellDataEntry e0 = reduced.getEntry( 0 );
		GasWellDataEntry e1 = reduced.getEntry( 1 );
		GasWellDataEntry e2 = reduced.getEntry( 2 );

		assertEquals( dateParser.parse( "30/JUL/2009").getTime(), e0.from() );
		assertEquals( dateParser.parse( "12/AUG/2009" ).getTime(), e0.until() );
		assertEquals( 86400000L * 13, e0.getIntervalLengthMS() );
		assertEquals( 238.44923077, e0.getMeasurement(WellMeasurementType.OIL_FLOW), ACCEPTABLE_ERROR );
		assertEquals( 0.16846154, e0.getMeasurement(WellMeasurementType.GAS_FLOW), ACCEPTABLE_ERROR );
		assertEquals( 799.06384615, e0.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

		assertEquals( dateParser.parse( "12/AUG/2009").getTime(), e1.from() );
		assertEquals( dateParser.parse( "15/AUG/2009 06:00" ).getTime(), e1.until() );
		GasWellDataEntry s0 = ds.consolidateEntries( new DateRange(  "12/AUG/2009", "15/AUG/2009 06:00" ) );
		assertEquals( 21600000L + ( 3 * 86400000L ), e1.getIntervalLengthMS() );
		assertEquals( s0.getMeasurement(  WellMeasurementType.OIL_FLOW ), e1.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
		assertEquals( s0.getMeasurement(  WellMeasurementType.GAS_FLOW ), e1.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
		assertEquals( s0.getMeasurement(  WellMeasurementType.WATER_FLOW ), e1.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

		assertEquals( dateParser.parse( "15/AUG/2009 06:00:00").getTime(), e2.from() );
		assertEquals( dateParser.parse( "19/AUG/2009" ).getTime(), e2.until() );
		assertEquals( (long)( 86400000L * 3.75 ), e2.getIntervalLengthMS() );

		s0 = ds.consolidateEntries( new DateRange( "15/AUG/2009 06:00", "19/AUG/2009" ) );

		assertEquals( s0.getMeasurement(  WellMeasurementType.OIL_FLOW ), e2.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
		assertEquals( s0.getMeasurement( WellMeasurementType.GAS_FLOW ),   e2.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
		assertEquals( s0.getMeasurement( WellMeasurementType.WATER_FLOW ), e2.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
	}


}