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
import com.sun.xml.internal.bind.v2.runtime.reflect.ListTransducedAccessorImpl;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 21/11/11
 * Time: 4:28 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class GasWellDataSetTest
{
static Logger logger = Logger.getLogger( GasWellDataSetTest.class );
private final static double ACCEPTABLE_ERROR = 1E-5;
GasWellDataSet littleDataSet;
DateParser dateParser;
Calendar twentyThirdApril;
Calendar twentyThirdAprilFiveInTheMorning;
Calendar twentyThirdAprilAtNoon;
Calendar twentyThirdAprilJustBeforeNoon;
Calendar endJuly;
GasWell davesWell = null;
GasWellDataSet emptyDataSet = null;
GasWellDataSet smallDataSet = null;
GasWell dummyWell;


public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( GasWellDataSetTest.class );
}

@Before
public void setupData()
{
    dateParser = new AussieDateParser();
    twentyThirdApril = dateParser.parse( "23/APRIL/2011" );
    twentyThirdAprilFiveInTheMorning = dateParser.parse( "23/APRIL/2011 05:00" );
    twentyThirdAprilJustBeforeNoon = dateParser.parse( "23/APRIL/2011 11:59:59" );
    twentyThirdAprilAtNoon = dateParser.parse( "23/APRIL/2011 12:00:00" );
    endJuly = dateParser.parse( "31/JUL/2011 23:59:59" );

    davesWell = new GasWell( "Dave's Well" );
    dummyWell = new GasWell( "Dummy" );

    emptyDataSet = new GasWellDataSet( davesWell );

    smallDataSet = TestGasWellDataSet.getSmallDataSet();

    // populate some dummy data into the gas well data set....
    // --------------------------------------------------------
    this.littleDataSet = TestGasWellDataSet.getDummyDataSet();
}

@Test( expected=NullPointerException.class )
public void testConstructorWithNoWell()
{
    GasWellDataSet set = new GasWellDataSet( null );
}

@Test
public void testFrom()
{
    Date from = this.smallDataSet.from();
    assertNotNull( from );
    assertEquals( twentyThirdAprilFiveInTheMorning.getTime(), from );
}

@Test
public void testUntil()
{
    Date until = this.smallDataSet.until();
    assertNotNull( until );
    assertEquals( twentyThirdAprilAtNoon.getTime(), until );
}

@Test
public void testGetMinimumOilFlow()
{
    double minimum = this.smallDataSet.getMinimum(WellMeasurementType.OIL_FLOW);
    assertEquals( 37.3, minimum, ACCEPTABLE_ERROR );
}

@Test
public void testGetMaximumOilFlow()
{
    double maximum = this.smallDataSet.getMaximum(WellMeasurementType.OIL_FLOW);
    assertEquals( 37.9, maximum, ACCEPTABLE_ERROR );
}

@Test
public void testGetMinimumGasFlow()
{
    double minimum = this.smallDataSet.getMinimum(WellMeasurementType.GAS_FLOW);
    assertEquals( 0.84, minimum, ACCEPTABLE_ERROR );
}

@Test
public void testGetMaximumGasFlow()
{
    double maximum = this.smallDataSet.getMaximum(WellMeasurementType.GAS_FLOW);
    assertEquals( 0.89, maximum, ACCEPTABLE_ERROR );
}

@Test
public void testGetMinimumWaterFlow()
{
    double minimum = this.smallDataSet.getMinimum(WellMeasurementType.WATER_FLOW);
    assertEquals( 15.1, minimum, ACCEPTABLE_ERROR );
}

@Test
public void testGetMaximumWaterFlow()
{
    double maximum = this.smallDataSet.getMaximum(WellMeasurementType.WATER_FLOW);
    assertEquals( 15.8, maximum, ACCEPTABLE_ERROR );
}

@Test
public void testGetMinima()
{
    GasWellDataEntry entry = this.smallDataSet.getMinima();
    assertNotNull( entry );
    assertEquals( twentyThirdAprilFiveInTheMorning.getTime(), entry.from() );
    assertEquals( 37.3, entry.getMeasurement(WellMeasurementType.OIL_FLOW), ACCEPTABLE_ERROR );
    assertEquals( 0.84, entry.getMeasurement(WellMeasurementType.GAS_FLOW), ACCEPTABLE_ERROR );
    assertEquals( 15.1, entry.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
}

@Test
public void testGetMaxima()
{
    GasWellDataEntry entry = this.smallDataSet.getMaxima();
    assertNotNull( entry );
    assertEquals( twentyThirdAprilAtNoon.getTime(), entry.until() );
    assertEquals( 37.9, entry.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.89, entry.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 15.8, entry.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

}

@Test
public void testContainsMeasurement()
{
    GasWellDataSet ourSet = this.smallDataSet.copy();
    
    assertTrue( ourSet.containsMeasurement( WellMeasurementType.OIL_FLOW ) );    
    assertTrue( ourSet.containsMeasurement( WellMeasurementType.WATER_FLOW ) );    
    assertTrue( ourSet.containsMeasurement( WellMeasurementType.GAS_FLOW ) );    
    assertFalse(ourSet.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW));
    
    GasWellDataEntry entry = new GasWellDataEntry( );
    entry.setWell( ourSet.getWell() );
    entry.setDateRange( new DateRange( ourSet.until(), new Date( ourSet.until().getTime() + 3600000L ) ) );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 0.00 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.00 );
    entry.setMeasurement( WellMeasurementType.CONDENSATE_FLOW, 0.00 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 0.00 );

    ourSet.addDataEntry( entry );

    assertTrue( ourSet.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    assertTrue( ourSet.containsMeasurement( WellMeasurementType.WATER_FLOW ) );
    assertTrue( ourSet.containsMeasurement( WellMeasurementType.GAS_FLOW ) );
    assertTrue(ourSet.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW) );

}

@Test
public void testGetDataMeasurementsAgainstDateTime()
{
    Date when = dateParser.parse( "13/june/2011 03:59:59").getTime();
    GasWellDataEntry entry = null;
    
    entry = littleDataSet.getEntry( when );
    assertNull( entry );

    when = dateParser.parse( "13/june/2011 04:00:00" ).getTime();
    entry = littleDataSet.getEntry( when );
    assertNotNull( entry );
    assertFalse( entry.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW));
    assertTrue( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ));
    assertTrue(entry.containsMeasurement(WellMeasurementType.GAS_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.OIL_FLOW));
    assertEquals( entry.getMeasurement( WellMeasurementType.OIL_FLOW), 0.0, ACCEPTABLE_ERROR );

    when = dateParser.parse( "13/june/2011 06:59:59" ).getTime();
    entry = littleDataSet.getEntry( when );
    assertNotNull( entry );
    assertFalse(entry.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW));
    assertTrue( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ));
    assertTrue(entry.containsMeasurement(WellMeasurementType.GAS_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.OIL_FLOW));
    assertEquals( entry.getMeasurement( WellMeasurementType.OIL_FLOW), 0.0, ACCEPTABLE_ERROR );

    when = dateParser.parse( "13/june/2011 07:00:00" ).getTime();
    entry = littleDataSet.getEntry( when );
    logger.debug( entry.toString() );
    assertNotNull( entry );
    assertFalse(entry.containsMeasurement( WellMeasurementType.CONDENSATE_FLOW ) );
    assertTrue( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ));
    assertTrue(entry.containsMeasurement( WellMeasurementType.GAS_FLOW ));
    assertTrue(entry.containsMeasurement( WellMeasurementType.OIL_FLOW ));
    assertEquals( entry.getMeasurement( WellMeasurementType.OIL_FLOW ), 1567.5, ACCEPTABLE_ERROR );

    when = dateParser.parse( "13/june/2011 07:00:01" ).getTime();
    entry = littleDataSet.getEntry( when );
    assertNotNull( entry );
    assertFalse(entry.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW));
    assertTrue( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ));
    assertTrue(entry.containsMeasurement(WellMeasurementType.GAS_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.OIL_FLOW));
    assertEquals( entry.getMeasurement( WellMeasurementType.OIL_FLOW), 1567.5, ACCEPTABLE_ERROR );

    when = dateParser.parse( "13/june/2011 07:59:59" ).getTime();
    entry = littleDataSet.getEntry( when );
    assertNotNull( entry );
    assertFalse(entry.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW));
    assertTrue( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ));
    assertTrue(entry.containsMeasurement(WellMeasurementType.GAS_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.OIL_FLOW));
    assertEquals( entry.getMeasurement( WellMeasurementType.OIL_FLOW), 1567.5, ACCEPTABLE_ERROR );

    when = dateParser.parse( "13/june/2011 08:00:00" ).getTime();
    entry = littleDataSet.getEntry( when );
    assertNotNull( entry );
    assertFalse(entry.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW));
    assertTrue( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ));
    assertTrue(entry.containsMeasurement(WellMeasurementType.GAS_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.OIL_FLOW));
    assertEquals( entry.getMeasurement( WellMeasurementType.OIL_FLOW), 1342.1, ACCEPTABLE_ERROR );

    // get an entry close to the end...
    when = dateParser.parse( "17/june/2011 01:59:59" ).getTime();
    entry = littleDataSet.getEntry( when );
    assertNotNull( entry );
    assertEquals( dateParser.parse( "17/june/2011 01:00:00").getTime(), entry.from() );
    assertEquals( dateParser.parse( "17/june/2011 02:00:00").getTime(), entry.until() );
    assertFalse(entry.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW));
    assertTrue( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ));
    assertTrue(entry.containsMeasurement(WellMeasurementType.GAS_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.OIL_FLOW));
    assertEquals( 1209.0, entry.getMeasurement( WellMeasurementType.OIL_FLOW), ACCEPTABLE_ERROR );

    // get the very last entry...
    when = dateParser.parse( "17/june/2011 07:59:59" ).getTime();
    entry = littleDataSet.getEntry(when);
    assertNotNull( entry );
    assertEquals(dateParser.parse("17/june/2011 07:00:00").getTime(), entry.from());
    assertEquals(dateParser.parse("17/june/2011 08:00:00").getTime(), entry.until());
    assertFalse(entry.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.WATER_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.GAS_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.OIL_FLOW));
    assertEquals(1210.1, entry.getMeasurement(WellMeasurementType.OIL_FLOW), ACCEPTABLE_ERROR);
    assertEquals(0.21, entry.getMeasurement(WellMeasurementType.GAS_FLOW), ACCEPTABLE_ERROR);
    assertEquals( 4325.0, entry.getMeasurement( WellMeasurementType.WATER_FLOW), ACCEPTABLE_ERROR );

    // try getting entry past the very last one!!
    when = dateParser.parse( "17/june/2011 08:00:00" ).getTime();
    entry = littleDataSet.getEntry( when );
    assertNull(entry);
}

@Test
public void testHashCode()
{
    assertTrue(emptyDataSet.hashCode() != 0);

    GasWellDataSet evilTwin = emptyDataSet.copy();
    assertEquals(emptyDataSet.hashCode(), evilTwin.hashCode());

    assertTrue( smallDataSet.hashCode() != 0 );
    evilTwin = smallDataSet.copy();
    assertEquals( smallDataSet.hashCode(), evilTwin.hashCode() );

    GasWellDataEntry entry = new GasWellDataEntry();
    entry.setWell( davesWell );
    entry.setDateRange( new DateRange( new java.util.Date(), 3600000L, 1000L ) );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 0.0 );
    evilTwin.addDataEntry( entry );

    assertFalse( smallDataSet.hashCode() == evilTwin.hashCode() );
}

@Test
public void testEquality()
{
    GasWellDataSet evilTwin;
    GasWellDataSet kindTwin;
    GasWellDataEntry entry;

    evilTwin = emptyDataSet.copy();
    kindTwin = emptyDataSet.copy();

    assertEquals( emptyDataSet, evilTwin );
    assertEquals( evilTwin, kindTwin );
    entry = new GasWellDataEntry();
    entry.setWell( davesWell );
    entry.setDateRange( new DateRange( new java.util.Date(), 3600000L, 1000L ) );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 0.0 );
    evilTwin.addDataEntry(entry);

    assertFalse( evilTwin.equals( kindTwin ));

    entry = new GasWellDataEntry();
    entry.setWell( davesWell );
    entry.setDateRange( new DateRange( new java.util.Date(), 3600000L, 1000L ) );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 0.0 );
    kindTwin.addDataEntry( entry );

    assertEquals( evilTwin, kindTwin );
}

@Test
public void testCompareTo()
{
     assertTrue( (new GasWellDataSet( new GasWell( "alpha" ) ) ).compareTo( new GasWellDataSet( new GasWell( "alpha" ) ) ) == 0);
     assertTrue( (new GasWellDataSet( new GasWell( "alpha" ) ) ).compareTo( new Date() ) == 0);
     assertTrue( (new GasWellDataSet( new GasWell( "alpha" ) ) ).compareTo( new GasWellDataSet( new GasWell( "alpha00" ) ) ) < 0);
     assertTrue( (new GasWellDataSet( new GasWell( "alpha" ) ) ).compareTo( new GasWellDataSet( new GasWell( "beta" ) ) ) < 0);
     assertTrue( (new GasWellDataSet( new GasWell( "beta" ) ) ).compareTo( new GasWellDataSet( new GasWell( "alpga" ) ) ) > 0);
}

@Test
public void testSerializingEmptyDataSet()
{
    ObjectOutputStream oos = null;
    String filename="empty_well_data_set.obj";
    try
    {
        oos = new ObjectOutputStream( new FileOutputStream( new File( filename ) ) );
    } catch (IOException e) {
        logger.error( e );
        e.printStackTrace();
        Assert.fail( "Failed to create object output stream to file \"" + filename + "\"" );
    }

    try
    {
        oos.writeObject( emptyDataSet );
        oos.close();
    } catch (IOException e)
    {
        logger.error( e );
        Assert.fail("Failed to write out gas well data entry!!" + e.getClass().getName() + " - " + e.getMessage());
    }

    ObjectInputStream ois = null;
    try {
        ois = new ObjectInputStream( new FileInputStream( new File( filename ) ) );
    } catch( IOException ioe ) {
        logger.error( ioe );
        Assert.fail("Failed to open file \"" + filename + "\" for reading." + ioe.getMessage() );
    }

    GasWellDataSet extract = null;
    try
    {
        extract = (GasWellDataSet)ois.readObject();
        ois.close();
    } catch ( Exception e) {
        logger.error( e );
        e.printStackTrace();
        Assert.fail("Failed to extract gas well data set from \"" + filename + "\"!! " + e.getClass().getName() + " - " + e.getMessage());
    }

    Assert.assertNotNull(extract);
    Assert.assertEquals( emptyDataSet, extract);
    Assert.assertEquals( emptyDataSet.hashCode(), extract.hashCode());
    assertEquals( 0, extract.getData().size() );

}

@Test
public void testSerializingSmallDataSet()
{
    ObjectOutputStream oos = null;
    String filename="small_well_data_set.obj";
    try
    {
        oos = new ObjectOutputStream( new FileOutputStream( new File( filename ) ) );
    } catch (IOException e) {
        Assert.fail("Failed to create object output stream to file \"" + filename + "\"");
    }

    try
    {
        oos.writeObject( smallDataSet );
        oos.close();
    } catch (IOException e)
    {
        Assert.fail("Failed to write out gas well data entry!!" + e.getClass().getName() + " - " + e.getMessage());
    }

    ObjectInputStream ois = null;
    try {
        ois = new ObjectInputStream( new FileInputStream( new File( filename ) ) );
    } catch( IOException ioe ) {
        Assert.fail("Failed to open file \"" + filename + "\" for reading." + ioe.getMessage());
    }

    GasWellDataSet extract = null;
    try
    {
        extract = (GasWellDataSet)ois.readObject();
        ois.close();
    } catch ( Exception e) {
        Assert.fail("Failed to extract gas well from daves_well.obj!! " + e.getClass().getName() + " - " + e.getMessage());
    }

    Assert.assertNotNull(extract);
    Assert.assertEquals( smallDataSet, extract);
    Assert.assertEquals(smallDataSet.hashCode(), extract.hashCode());
    assertEquals( 7, extract.getData().size() );
}

@Test( expected=IllegalArgumentException.class)
public void testGetByNegativeIndex()
{
    smallDataSet.getEntry( -1 );
}


@Test( expected=IllegalArgumentException.class)
public void testGetByTooLargeIndex()
{
    smallDataSet.getEntry( 7 );
}


@Test
public void testGetByIndex()
{
    GasWellDataEntry entry;
    entry = smallDataSet.getEntry( 6 );
    assertNotNull( entry );
    assertTrue( entry.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    assertEquals( 37.3, entry.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );

    entry = smallDataSet.getEntry( 0 );
    assertNotNull( entry );
    assertTrue(entry.containsMeasurement(WellMeasurementType.OIL_FLOW));
    assertEquals(37.6, entry.getMeasurement(WellMeasurementType.OIL_FLOW), ACCEPTABLE_ERROR );
}


@Test( expected=NullPointerException.class )
public void testConsolidatingDataWithNullArgs()
{
    smallDataSet.consolidateEntries( null, null );
}

@Test( expected=IllegalArgumentException.class)
public void testConsolidatingDataStartAfterFinish()
{
    Date start = dateParser.parse( "23/APRIL/2011 09:00:00" ).getTime();
    Date finish = dateParser.parse( "23/APRIL/2011 08:00:00" ).getTime();
    smallDataSet.consolidateEntries( start, finish );
}

@Test( expected=IllegalArgumentException.class )
public void testConsolidatingDataBeforeStart()
{
    Date start = dateParser.parse( "23/APR/2011 04:59:59" ).getTime();
    Date finish = dateParser.parse( "23/APR/2011 11:59:59" ).getTime();
    smallDataSet.consolidateEntries( start, finish );
}

@Test( expected=IllegalArgumentException.class)
public void testConsolidatingDataAfterFinish()
{
    Date start = dateParser.parse( "23/APR/2011 05:00:00" ).getTime();
    Date finish = dateParser.parse( "23/APR/2011 12:00:01" ).getTime();
    smallDataSet.consolidateEntries( start, finish );
}

@Test
public void testConsolidatingDataFirstTwoHours()
{
    GasWellDataEntry consolidated = null;
    DateRange firstTwoHours = new DateRange( "23/APR/2011 05:00", "23/APR/2011 07:00" );

    consolidated = smallDataSet.consolidateEntries( firstTwoHours );
    assertNotNull( consolidated );
    assertTrue( consolidated.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    assertEquals( ( 37.6 + 37.8 ) / 2.0, consolidated.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertTrue( consolidated.containsMeasurement( WellMeasurementType.GAS_FLOW ) );
    assertEquals( ( 0.89 + 0.86 ) / 2.0, consolidated.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertTrue( consolidated.containsMeasurement( WellMeasurementType.WATER_FLOW ) );
    assertEquals( ( 15.2 + 15.1 ) / 2.0, consolidated.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
    assertFalse( consolidated.containsMeasurement( WellMeasurementType.CONDENSATE_FLOW ) );

    assertEquals( dateParser.parse( "23/APR/2011 05:00").getTime(), consolidated.from() );
    assertEquals( dateParser.parse("23/APR/2011 07:00").getTime(), consolidated.until() );
    assertEquals( 7200000, consolidated.getIntervalLengthMS() );
}


@Test
public void testConsolidatingDataFirstNinetyMinutes()
{
    GasWellDataEntry consolidated = null;

    Date start = dateParser.parse( "23/APR/2011 05:00:00" ).getTime();
    Date finish = dateParser.parse( "23/APR/2011 06:30:00" ).getTime();

    consolidated = smallDataSet.consolidateEntries( start, finish );
    assertNotNull( consolidated );
    assertTrue( consolidated.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    assertEquals( ( 37.6 + ( 37.8 * 0.5 ) ) / 1.5, consolidated.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertTrue( consolidated.containsMeasurement( WellMeasurementType.GAS_FLOW ) );
    assertEquals( ( 0.89 + ( 0.86 * 0.5 ) ) / 1.5, consolidated.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertTrue( consolidated.containsMeasurement( WellMeasurementType.WATER_FLOW ) );
    assertEquals( ( 15.2 + ( 15.1 * 0.5 ) ) / 1.5, consolidated.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
    assertFalse( consolidated.containsMeasurement( WellMeasurementType.CONDENSATE_FLOW ) );

    assertEquals( start, consolidated.from() );
    assertEquals( 5400000, consolidated.getIntervalLengthMS() );
}

@Test
public void testConsolidatingDataFirstHundredAndFiftyMinutes()
{
    GasWellDataEntry consolidated = null;

    Date start = dateParser.parse( "23/APR/2011 05:00:00" ).getTime();
    Date finish = dateParser.parse( "23/APR/2011 07:30:00" ).getTime();

    consolidated = smallDataSet.consolidateEntries( start, finish );
    assertNotNull( consolidated );
    assertTrue( consolidated.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    assertEquals( ( 37.6 + 37.8 + ( 37.9 * 0.5 ) ) / 2.5, consolidated.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertTrue( consolidated.containsMeasurement( WellMeasurementType.GAS_FLOW ) );
    assertEquals( ( 0.89 + 0.86 + ( 0.87 * 0.5 ) ) / 2.5, consolidated.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertTrue( consolidated.containsMeasurement( WellMeasurementType.WATER_FLOW ) );
    assertEquals( ( 15.2 + 15.1 + ( 15.3 * 0.5 ) ) / 2.5, consolidated.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
    assertFalse( consolidated.containsMeasurement( WellMeasurementType.CONDENSATE_FLOW ) );

    assertEquals( start, consolidated.from() );
    assertEquals( 9000000, consolidated.getIntervalLengthMS() );
}


@Test( expected=NullPointerException.class )
public void testExtractingReducedDataSetViaConstructorWithNullIntervals()
{
    GasWellDataSet reduced = new GasWellDataSet( littleDataSet, (List<GasWellDataBoundary>)null );
}

@Test( expected=IllegalArgumentException.class )
public void testExtractingReducedDataSetViaConstructorWithNoIntervals()
{
    List<GasWellDataBoundary> boundaryList = new ArrayList<GasWellDataBoundary>();
    GasWellDataSet reduced = new GasWellDataSet( littleDataSet, boundaryList );
}

@Test( expected=IllegalArgumentException.class )
public void testExtractingReducedDataSetViaConstructorWithBackToFrontIntervals()
{
    GasWellDataBoundary alpha = new GasWellDataBoundary( dateParser.parse( "14/JUNE/2011 13:00").getTime(), "start" );
    GasWellDataBoundary beta = new GasWellDataBoundary( dateParser.parse( "14/JUNE/2011 12:00").getTime(), "finish" );
    List<GasWellDataBoundary> boundaryList = new ArrayList<GasWellDataBoundary>();
    boundaryList.add( alpha );
    boundaryList.add( beta );
    GasWellDataSet reduced = new GasWellDataSet( littleDataSet, boundaryList );
}

@Test( expected=IllegalArgumentException.class )
public void testExtractingReducedDataSetViaConstructorWithOutOfBoundsIntervalsTooEarly()
{
    GasWellDataBoundary alpha = new GasWellDataBoundary( dateParser.parse( "13/JUNE/2011 03:59").getTime(), "start" );
    GasWellDataBoundary beta = new GasWellDataBoundary( dateParser.parse( "14/JUNE/2011 12:00").getTime(), "finish" );
    List<GasWellDataBoundary> boundaryList = new ArrayList<GasWellDataBoundary>();
    boundaryList.add( alpha );
    boundaryList.add( beta );
    GasWellDataSet reduced = new GasWellDataSet( littleDataSet, boundaryList );

}

@Test( expected=IllegalArgumentException.class )
public void testExtractingReducedDataSetViaConstructorWithOutOfBoundsIntervalsTooLate()
{
    GasWellDataBoundary alpha = new GasWellDataBoundary( dateParser.parse( "13/JUNE/2011 08:00").getTime(), "start" );
    GasWellDataBoundary beta = new GasWellDataBoundary( dateParser.parse( "20/JUNE/2011 12:00").getTime(), "finish" );
    List<GasWellDataBoundary> boundaryList = new ArrayList<GasWellDataBoundary>();
    boundaryList.add( alpha );
    boundaryList.add( beta );
    GasWellDataSet reduced = new GasWellDataSet( littleDataSet, boundaryList );

}


@Test
public void testExtractingReducedDataSetViaConstructor()
{
    List<GasWellDataBoundary> boundaryList = new ArrayList<GasWellDataBoundary>();

    assertNotNull( littleDataSet );
    assertNotNull( littleDataSet.getData() );
    assertEquals( littleDataSet.getData().size(), 100 );
    assertNotNull( littleDataSet.from() );
    assertNotNull( littleDataSet.until() );
    
    boundaryList.add( new GasWellDataBoundary( dateParser.parse( "13/JUNE/2011 04:00" ).getTime(), "eins" ) );
    boundaryList.add( new GasWellDataBoundary( dateParser.parse( "14/JUNE/2011 04:00" ).getTime(), "zwei" ) );
    boundaryList.add( new GasWellDataBoundary( dateParser.parse( "15/JUNE/2011 04:00" ).getTime(), "drei" ) );
    boundaryList.add( new GasWellDataBoundary( dateParser.parse( "16/JUNE/2011 04:00" ).getTime(), "vier" ) );
    boundaryList.add( new GasWellDataBoundary( dateParser.parse( "17/JUNE/2011 04:00" ).getTime(), "funf" ) );

    GasWellDataSet reduced = new GasWellDataSet( littleDataSet, boundaryList );

    assertNotNull( reduced );
    assertNotNull( reduced.getData() );
    logger.debug( "reduced data:" );
    logger.debug( reduced );
    assertEquals( 5, reduced.getData().size() );
    assertEquals( dateParser.parse( "13/JUNE/2011 04:00").getTime(), reduced.from() );
    assertEquals( dateParser.parse( "17/JUNE/2011 08:00:00").getTime(), reduced.until() );

    GasWellDataEntry entry;

    entry = reduced.getEntry( dateParser.parse( "13/JUNE/2011 04:00").getTime() );
    assertNotNull( entry );
    assertEquals( dateParser.parse( "13/JUNE/2011 04:00").getTime(), entry.from() );
    assertEquals( dateParser.parse( "14/JUNE/2011 04:00").getTime(), entry.until() );
    assertEquals( "eins", entry.getComment() );
    assertTrue( entry.containsMeasurement( WellMeasurementType.GAS_FLOW ) );
    assertTrue( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ) );
    assertFalse( entry.containsMeasurement( WellMeasurementType.CONDENSATE_FLOW ) );
    assertTrue( entry.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    double expected = ( 1567.5 + 1342.1 + 1152.6 + 1133.6 + 1132.8 + 1127.6 + 1128.3 + 1129.5 + 1128.6+ 1153.5+ 1132.4+ 1129.1+ 1128.5+ 1131.2+ 1130.0+ 1131.5+ 1132.1 + 1132.7+ 1133.1+ 1135.8+ 1138.5 ) / 24.0;
    assertEquals( expected, entry.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    
    entry = reduced.getEntry( dateParser.parse( "14/JUNE/2011 04:00" ).getTime() );
    assertNotNull( entry );
    assertEquals( dateParser.parse( "14/JUNE/2011 04:00").getTime(), entry.from() );
    assertEquals( dateParser.parse( "15/JUNE/2011 04:00").getTime(), entry.until() );
    assertEquals( "zwei", entry.getComment() );
    assertTrue( entry.containsMeasurement( WellMeasurementType.GAS_FLOW ) );
    assertTrue( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ) );
    assertFalse( entry.containsMeasurement( WellMeasurementType.CONDENSATE_FLOW ) );
    assertTrue( entry.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    expected = ( 1139.1 + 1142.6 + 1140.7 + 1141.2 + 1142.8 + 1140.3 + 0320.5 + 0000.0 + 0000.0 + 0000.0 + 0000.0 + 0000.0 + 0762.5 + 1762.4 + 1482.3 + 1312.5 + 1274.7 + 1082.5 + 0995.7 + 1127.5 + 1138.5 + 1137.6 + 1139.6 + 1140.6) / 24.0;
    assertEquals( expected, entry.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );


}
@Test
public void testExtractingReducedDataSetViaConstructorWithEndBoundary()
{
    List<GasWellDataBoundary> boundaryList = new ArrayList<GasWellDataBoundary>();

    assertNotNull( littleDataSet );
    assertNotNull( littleDataSet.getData() );
    assertEquals( littleDataSet.getData().size(), 100 );
    assertNotNull( littleDataSet.from() );
    assertNotNull( littleDataSet.until() );

    boundaryList.add( new GasWellDataBoundary( dateParser.parse( "13/JUNE/2011 04:00" ).getTime(), "eins" ) );
    boundaryList.add( new GasWellDataBoundary( dateParser.parse( "14/JUNE/2011 04:00" ).getTime(), "zwei" ) );
    boundaryList.add( new GasWellDataBoundary( dateParser.parse( "15/JUNE/2011 04:00" ).getTime(), "drei" ) );
    boundaryList.add( new GasWellDataBoundary( dateParser.parse( "16/JUNE/2011 04:00" ).getTime(), "vier" ) );
    boundaryList.add( new GasWellDataBoundary( dateParser.parse( "17/JUNE/2011 04:00" ).getTime(), "funf", GasWellDataBoundary.BoundaryType.END ) );

    GasWellDataSet reduced = new GasWellDataSet( littleDataSet, boundaryList );

    assertNotNull( reduced );
    assertNotNull( reduced.getData() );
    logger.debug( "reduced data:" );
    logger.debug( reduced );
    assertEquals( 4, reduced.getData().size() );
    assertEquals( dateParser.parse( "13/JUNE/2011 04:00").getTime(), reduced.from() );
    assertEquals( dateParser.parse( "17/JUNE/2011 04:00").getTime(), reduced.until() );

    GasWellDataEntry entry;

    entry = reduced.getEntry( dateParser.parse( "13/JUNE/2011 04:00").getTime() );
    assertNotNull(entry);
    assertEquals( dateParser.parse( "13/JUNE/2011 04:00").getTime(), entry.from() );
    assertEquals( dateParser.parse( "14/JUNE/2011 04:00").getTime(), entry.until() );
    assertEquals( "eins", entry.getComment() );
    assertTrue( entry.containsMeasurement( WellMeasurementType.GAS_FLOW ) );
    assertTrue( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ) );
    assertFalse( entry.containsMeasurement( WellMeasurementType.CONDENSATE_FLOW ) );
    assertTrue( entry.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    double expected = ( 1567.5 + 1342.1 + 1152.6 + 1133.6 + 1132.8 + 1127.6 + 1128.3 + 1129.5 + 1128.6+ 1153.5+ 1132.4+ 1129.1+ 1128.5+ 1131.2+ 1130.0+ 1131.5+ 1132.1 + 1132.7+ 1133.1+ 1135.8+ 1138.5 ) / 24.0;
    assertEquals( expected, entry.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );

    entry = reduced.getEntry( dateParser.parse( "14/JUNE/2011 04:00" ).getTime() );
    assertNotNull( entry );
    assertEquals( dateParser.parse( "14/JUNE/2011 04:00").getTime(), entry.from() );
    assertEquals( dateParser.parse( "15/JUNE/2011 04:00").getTime(), entry.until() );
    assertEquals( "zwei", entry.getComment() );
    assertTrue( entry.containsMeasurement( WellMeasurementType.GAS_FLOW ) );
    assertTrue( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ) );
    assertFalse( entry.containsMeasurement( WellMeasurementType.CONDENSATE_FLOW ) );
    assertTrue( entry.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    expected = ( 1139.1 + 1142.6 + 1140.7 + 1141.2 + 1142.8 + 1140.3 + 0320.5 + 0000.0 + 0000.0 + 0000.0 + 0000.0 + 0000.0 + 0762.5 + 1762.4 + 1482.3 + 1312.5 + 1274.7 + 1082.5 + 0995.7 + 1127.5 + 1138.5 + 1137.6 + 1139.6 + 1140.6) / 24.0;
    assertEquals( expected, entry.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );


}
}
