package au.com.polly.ddr;

import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
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
private final static double ACCEPTABLE_ERROR = 1E-8;
GasWellDataSet littleDataSet;
DateParser dateParser;
Calendar twentyThirdApril;
Calendar twentyThirdAprilFiveInTheMorning;
Calendar twentyThirdAprilJustBeforeNoon;
Calendar endJuly;
GasWell davesWell = null;
GasWellDataSet emptyDataSet = null;
GasWellDataSet smallDataSet = null;
GasWell dummyWell;

static private final double oilFlowRates[] = {
        0000.0, 0000.0, 0000.0, 1567.5, 1342.1, 1152.6, 1133.6, 1132.8, 1127.6, 1128.3,
        1129.5, 1128.6, 1153.5, 1132.4, 1129.1, 1128.5, 1131.2, 1130.0, 1131.5, 1132.1,
        1132.7, 1133.1, 1135.8, 1138.5, 1139.1, 1142.6, 1140.7, 1141.2, 1141.8, 1140.3,
        0320.5, 0000.0, 0000.0, 0000.0, 0000.0, 0000.0, 0762.5, 1762.4, 1482.3, 1312.5,
        1274.7, 1082.5, 0995.7, 1127.5, 1138.5, 1137.6, 1139.6, 1140.6, 1137.8, 1137.6,
        1138.2, 1139.1, 1142.6, 1140.7, 1135.7, 0970.5, 0790.7, 0608.2, 0432.5, 0402.2,
        0395.7, 0403.1, 0397.5, 0399.9, 0401.5, 0400.0, 0408.2, 0405.3, 0397.5, 0401.1,
        0402.6, 0490.0, 0580.0, 0670.0, 0760.0, 0850.0, 0940.0, 1030.0, 1120.0, 1200.0,
        1200.0, 1203.8, 1197.6, 1199.9, 1202.0, 1205.5, 1203.2, 1202.0, 1201.0, 1200.0,
        1203.0, 1205.0, 1207.0, 1209.0, 1211.0, 1210.0, 1210.0, 1210.0, 1210.0, 1210.1
};

@Before
public void setupData()
{
    DataVsTimeSource dataSource = new DummyGasWellDataSet();
    this.littleDataSet = dataSource.getData();

    dateParser = new AussieDateParser();
    twentyThirdApril = dateParser.parse( "23/APRIL/2011" );
    twentyThirdAprilFiveInTheMorning = dateParser.parse( "23/APRIL/2011 05:00" );
    twentyThirdAprilJustBeforeNoon = dateParser.parse( "23/APRIL/2011 11:59:59" );
    endJuly = dateParser.parse( "31/JUL/2011 23:59:59" );

    davesWell = new GasWell( "Dave's Well" );
    dummyWell = new GasWell( "Dummy" );

    emptyDataSet = new GasWellDataSet( davesWell );

    smallDataSet = new GasWellDataSet( davesWell );
    Calendar when = dateParser.parse( "23/APRIL 2011 05:00" );

    GasWellDataEntry entry = new GasWellDataEntry();
    entry.setWell( davesWell );
    entry.setStartInterval( when.getTime() );
    entry.setIntervalLength( 3600 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 15.2 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 37.6 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.89 );

    smallDataSet.addDataEntry( entry );

    when.add( Calendar.HOUR_OF_DAY,  1 );
    entry = new GasWellDataEntry();
    entry.setWell( davesWell );
    entry.setStartInterval( when.getTime() );
    entry.setIntervalLength( 3600 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 15.1 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 37.8 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.86 );

    smallDataSet.addDataEntry( entry );
    when.add( Calendar.HOUR_OF_DAY, 1 );
    entry = new GasWellDataEntry();
    entry.setWell( davesWell );
    entry.setStartInterval( when.getTime() );
    entry.setIntervalLength( 3600 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 15.3 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 37.9 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.87 );

    smallDataSet.addDataEntry( entry );
    when.add( Calendar.HOUR_OF_DAY, 1 );
    entry = new GasWellDataEntry();
    entry.setWell( davesWell );
    entry.setStartInterval( when.getTime() );
    entry.setIntervalLength( 3600 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 15.4 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 37.6 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.86 );

    smallDataSet.addDataEntry( entry );
    when.add( Calendar.HOUR_OF_DAY, 1 );
    entry = new GasWellDataEntry();
    entry.setWell( davesWell );
    entry.setStartInterval( when.getTime() );
    entry.setIntervalLength( 3600 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 15.6 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 37.7 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.85 );

    smallDataSet.addDataEntry( entry );
    when.add( Calendar.HOUR_OF_DAY, 1 );
    entry = new GasWellDataEntry();
    entry.setWell( davesWell );
    entry.setStartInterval( when.getTime() );
    entry.setIntervalLength( 3600 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 15.7 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 37.5 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.86 );

    smallDataSet.addDataEntry( entry );

    when.add( Calendar.HOUR_OF_DAY, 1 );
    entry = new GasWellDataEntry();
    entry.setWell( davesWell );
    entry.setStartInterval( when.getTime() );
    entry.setIntervalLength( 3600 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 15.8 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 37.3 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.84 );

    smallDataSet.addDataEntry( entry );

       // populate some dummy data into the gas well data set....
    // --------------------------------------------------------
    this.littleDataSet = new GasWellDataSet( dummyWell );
    DateParser dateParser = new AussieDateParser();

    when = dateParser.parse( "13/06/2011 04:00" );

    for( int i = 0; i < oilFlowRates.length; i++ )
    {
        entry = new GasWellDataEntry();
        entry.setWell( dummyWell );
        entry.setStartInterval( when.getTime() );
        when.add( Calendar.HOUR_OF_DAY, 1 );
        entry.setIntervalLength( 3600 );
        entry.setMeasurement( WellMeasurementType.OIL_FLOW, oilFlowRates[ i ] );
        this.littleDataSet.addDataEntry(entry);
    }
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
    assertEquals( twentyThirdAprilJustBeforeNoon.getTime(), until );
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
    assertEquals( twentyThirdAprilFiveInTheMorning.getTime(), entry.getStartInterval() );
    assertEquals( 37.3, entry.getMeasurement(WellMeasurementType.OIL_FLOW), ACCEPTABLE_ERROR );
    assertEquals( 0.84, entry.getMeasurement(WellMeasurementType.GAS_FLOW), ACCEPTABLE_ERROR );
    assertEquals( 15.1, entry.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
}

@Test
public void testGetMaxima()
{
    GasWellDataEntry entry = this.smallDataSet.getMaxima();
    assertNotNull( entry );
    assertEquals( twentyThirdAprilJustBeforeNoon.getTime(), entry.until() );
    assertEquals( 37.9, entry.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.89, entry.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 15.8, entry.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

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
    assertFalse( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ));
    assertFalse(entry.containsMeasurement(WellMeasurementType.GAS_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.OIL_FLOW));
    assertEquals( entry.getMeasurement( WellMeasurementType.OIL_FLOW), 0.0, ACCEPTABLE_ERROR );

    when = dateParser.parse( "13/june/2011 06:59:59" ).getTime();
    entry = littleDataSet.getEntry( when );
    assertNotNull( entry );
    assertFalse(entry.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW));
    assertFalse( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ));
    assertFalse(entry.containsMeasurement(WellMeasurementType.GAS_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.OIL_FLOW));
    assertEquals( entry.getMeasurement( WellMeasurementType.OIL_FLOW), 0.0, ACCEPTABLE_ERROR );

    when = dateParser.parse( "13/june/2011 07:00:00" ).getTime();
    entry = littleDataSet.getEntry( when );
    assertNotNull( entry );
    assertFalse(entry.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW));
    assertFalse( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ));
    assertFalse(entry.containsMeasurement(WellMeasurementType.GAS_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.OIL_FLOW));
    assertEquals( entry.getMeasurement( WellMeasurementType.OIL_FLOW), 1567.5, ACCEPTABLE_ERROR );

    when = dateParser.parse( "13/june/2011 07:00:01" ).getTime();
    entry = littleDataSet.getEntry( when );
    assertNotNull( entry );
    assertFalse(entry.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW));
    assertFalse( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ));
    assertFalse(entry.containsMeasurement(WellMeasurementType.GAS_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.OIL_FLOW));
    assertEquals( entry.getMeasurement( WellMeasurementType.OIL_FLOW), 1567.5, ACCEPTABLE_ERROR );

    when = dateParser.parse( "13/june/2011 07:59:59" ).getTime();
    entry = littleDataSet.getEntry( when );
    assertNotNull( entry );
    assertFalse(entry.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW));
    assertFalse( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ));
    assertFalse(entry.containsMeasurement(WellMeasurementType.GAS_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.OIL_FLOW));
    assertEquals( entry.getMeasurement( WellMeasurementType.OIL_FLOW), 1567.5, ACCEPTABLE_ERROR );

    when = dateParser.parse( "13/june/2011 08:00:00" ).getTime();
    entry = littleDataSet.getEntry( when );
    assertNotNull( entry );
    assertFalse(entry.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW));
    assertFalse( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ));
    assertFalse(entry.containsMeasurement(WellMeasurementType.GAS_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.OIL_FLOW));
    assertEquals( entry.getMeasurement( WellMeasurementType.OIL_FLOW), 1342.1, ACCEPTABLE_ERROR );

    // get the very last entry...
    when = dateParser.parse( "17/june/2011 07:59:59" ).getTime();
    entry = littleDataSet.getEntry( when );
    assertNotNull( entry );
    assertFalse(entry.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW));
    assertFalse( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ));
    assertFalse(entry.containsMeasurement(WellMeasurementType.GAS_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.OIL_FLOW));
    assertEquals( entry.getMeasurement( WellMeasurementType.OIL_FLOW), 1210.1, ACCEPTABLE_ERROR );

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
    entry.setStartInterval(new java.util.Date());
    entry.setIntervalLength(3600);
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
    entry.setStartInterval(new java.util.Date());
    entry.setIntervalLength(3600);
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 0.0 );
    evilTwin.addDataEntry(entry);

    assertFalse( evilTwin.equals( kindTwin ));

    entry = new GasWellDataEntry();
    entry.setWell( davesWell );
    entry.setStartInterval(new java.util.Date());
    entry.setIntervalLength(3600);
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 0.0 );
    kindTwin.addDataEntry( entry );

    assertEquals( evilTwin, kindTwin );
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
    Date finish = dateParser.parse( "23/APR/2011 12:00:00" ).getTime();
    smallDataSet.consolidateEntries( start, finish );
}

@Test
public void testConsolidatingDataFirstTwoHours()
{
    GasWellDataEntry consolidated = null;

    Date start = dateParser.parse( "23/APR/2011 05:00:00" ).getTime();
    Date finish = dateParser.parse( "23/APR/2011 06:59:59" ).getTime();

    consolidated = smallDataSet.consolidateEntries( start, finish );
    assertNotNull( consolidated );
    assertTrue( consolidated.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    assertEquals( ( 37.6 + 37.8 ) / 2.0, consolidated.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertTrue( consolidated.containsMeasurement( WellMeasurementType.GAS_FLOW ) );
    assertEquals( ( 0.89 + 0.86 ) / 2.0, consolidated.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertTrue( consolidated.containsMeasurement( WellMeasurementType.WATER_FLOW ) );
    assertEquals( ( 15.2 + 15.1 ) / 2.0, consolidated.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
    assertFalse( consolidated.containsMeasurement( WellMeasurementType.CONDENSATE_FLOW ) );

    assertEquals( start, consolidated.getStartInterval() );
    assertEquals( 7200, consolidated.getIntervalLength() );
}


@Test( expected=NullPointerException.class )
public void testExtractingReducedDataSetViaConstructorWithNullIntervals()
{
    GasWellDataSet reduced = new GasWellDataSet( littleDataSet, null );
}

@Test( expected=IllegalArgumentException.class )
public void testExtractingReducedDataSetViaConstructorWithNoIntervals()
{
    GasWellDataSet reduced = new GasWellDataSet( littleDataSet, new Date[]{} );
}

@Test( expected=IllegalArgumentException.class )
public void testExtractingReducedDataSetViaConstructorWithBackToFrontIntervals()
{
    Date alpha =   dateParser.parse( "14/JUNE/2011 13:00").getTime();
    Date beta =   dateParser.parse( "14/JUNE/2011 12:00").getTime();
    GasWellDataSet reduced = new GasWellDataSet( littleDataSet, new Date[]{ alpha, beta } );
}

@Test( expected=IllegalArgumentException.class )
public void testExtractingReducedDataSetViaConstructorWithOutOfBoundsIntervalsTooEarly()
{
    Date alpha = dateParser.parse( "13/JUNE/2011 03:59").getTime();
    Date beta = dateParser.parse( "14/JUNE/2011 12:00").getTime();
    GasWellDataSet reduced = new GasWellDataSet( littleDataSet, new Date[]{ alpha, beta } );
}

@Test( expected=IllegalArgumentException.class )
public void testExtractingReducedDataSetViaConstructorWithOutOfBoundsIntervalsTooLate()
{
    Date alpha = dateParser.parse( "13/JUNE/2011 08:00").getTime();
    Date beta = dateParser.parse( "20/JUNE/2011 12:00").getTime();
    GasWellDataSet reduced = new GasWellDataSet( littleDataSet, new Date[]{ alpha, beta } );
}


@Test
public void testExtractingReducedDataSetViaConstructor()
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

    assertNotNull( reduced );
    assertNotNull( reduced.getData() );
    logger.debug( "reduced data:" );
    logger.debug( reduced );
    assertEquals( 5, reduced.getData().size() );
    assertEquals( dateParser.parse( "13/JUNE/2011 04:00").getTime(), reduced.from() );
    assertEquals( dateParser.parse( "17/JUNE/2011 07:59:59").getTime(), reduced.until() );

    GasWellDataEntry entry;

    entry = reduced.getEntry( dateParser.parse( "13/JUNE/2011 04:00").getTime() );
    assertNotNull( entry );
    assertEquals( dateParser.parse( "13/JUNE/2011 04:00").getTime(), entry.getStartInterval() );
    assertEquals( dateParser.parse( "14/JUNE/2011 03:59:59").getTime(), entry.until() );
    assertFalse( entry.containsMeasurement( WellMeasurementType.GAS_FLOW ) );
    assertFalse( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ) );
    assertFalse( entry.containsMeasurement( WellMeasurementType.CONDENSATE_FLOW ) );
    assertTrue( entry.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    double expected = ( 1567.5 + 1342.1 + 1152.6 + 1133.6 + 1132.8 + 1127.6 + 1128.3 + 1129.5 + 1128.6+ 1153.5+ 1132.4+ 1129.1+ 1128.5+ 1131.2+ 1130.0+ 1131.5+ 1132.1 + 1132.7+ 1133.1+ 1135.8+ 1138.5 ) / 24.0;
    assertEquals( expected, entry.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    
    entry = reduced.getEntry( dateParser.parse( "14/JUNE/2011 04:00" ).getTime() );
    assertNotNull( entry );
    assertEquals( dateParser.parse( "14/JUNE/2011 04:00").getTime(), entry.getStartInterval() );
    assertEquals( dateParser.parse( "15/JUNE/2011 03:59:59").getTime(), entry.until() );
    assertFalse( entry.containsMeasurement( WellMeasurementType.GAS_FLOW ) );
    assertFalse( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ) );
    assertFalse( entry.containsMeasurement( WellMeasurementType.CONDENSATE_FLOW ) );
    assertTrue( entry.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    expected = (1139.1 + 1142.6 + 1140.7 + 1141.2 + 1141.8 + 1140.3 + 0320.5 + 0000.0 + 0000.0 + 0000.0 + 0000.0 + 0000.0 + 0762.5 + 1762.4 + 1482.3 + 1312.5 + 1274.7 + 1082.5 + 0995.7 + 1127.5 + 1138.5 + 1137.6 + 1139.6 + 1140.6) / 24.0;
    assertEquals( expected, entry.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );

}
}
