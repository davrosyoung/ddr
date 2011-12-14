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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    assertNotNull( littleDataSet );
    assertNotNull( littleDataSet.getData() );
    assertEquals( littleDataSet.getData().size(), 100 );
    assertNotNull( littleDataSet.from() );
    assertNotNull( littleDataSet.until() );

    GasWellDataSet reduced = new GasWellDataSet( littleDataSet, new Date[]{
            dateParser.parse( "13/JUNE/2011 04:00").getTime(),
            dateParser.parse( "14/JUNE/2011 04:00").getTime(),
            dateParser.parse( "15/JUNE/2011 04:00").getTime(),
            dateParser.parse( "16/JUNE/2011 04:00").getTime(),
            dateParser.parse( "17/JUNE/2011 04:00").getTime(),
            dateParser.parse( "17/JUNE/2011 07:59:59").getTime()
    } );

    Map<WellMeasurementType,Double> error = GasWellDataSetUtil.getError( littleDataSet, reducedDataSet );
    assertNotNull( error );
    assertFalse( error.containsKey( WellMeasurementType.CONDENSATE_FLOW ) );
    assertFalse( error.containsKey( WellMeasurementType.GAS_FLOW ) );
    assertFalse( error.containsKey( WellMeasurementType.WATER_FLOW ) );
    assertTrue( error.containsKey( WellMeasurementType.OIL_FLOW ) );
    assertEquals( 27453.9, error.get( WellMeasurementType.OIL_FLOW), ACCEPTABLE_ERROR );
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
    
    assertEquals( 2259.44, volumes.get( WellMeasurementType.OIL_FLOW ), 0.01 );
    assertEquals( 1.57, volumes.get( WellMeasurementType.OIL_FLOW ), 0.01 );
    assertEquals( 7620.72, volumes.get( WellMeasurementType.OIL_FLOW ), 0.01 );
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

    assertEquals( 143.26, volumes.get( WellMeasurementType.OIL_FLOW ), 0.01 );
    assertEquals( 0.095, volumes.get( WellMeasurementType.GAS_FLOW ), 0.01 );
    assertEquals( 475.82, volumes.get( WellMeasurementType.WATER_FLOW ), 0.01 );
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
    assertEquals( 105.72, volumes.get( WellMeasurementType.GAS_FLOW ), 0.01 );
    assertEquals( 395861.3, volumes.get( WellMeasurementType.WATER_FLOW ), 0.01 );

}

}
