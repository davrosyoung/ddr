package au.com.polly.ddr;

import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 23/11/11
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class GasWellDataEntryTest
{
DateParser dateParser;
Calendar twentyThirdApril;
Calendar endJuly;


@Before
public void setupData()
{
    dateParser = new AussieDateParser();
    twentyThirdApril = dateParser.parse( "23/APRIL/2011" );
    endJuly = dateParser.parse( "31/JUL/2011 23:59:59" );
}

@Test
public void testNullConstructor()
{
    GasWellDataEntry entry = new GasWellDataEntry();
    assertNotNull( entry );
    assertNull( entry.getStartInterval() );
    assertEquals(0, entry.getIntervalLength());
    assertNull(entry.until());
    assertNull(entry.getWell());
    assertFalse(entry.containsMeasurement(WellMeasurementType.WATER_FLOW));
    assertFalse(entry.containsMeasurement(WellMeasurementType.GAS_FLOW));
    assertFalse(entry.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW));
    assertFalse( entry.containsMeasurement( WellMeasurementType.OIL_FLOW ) );

}

@Test
public void testSerialization()
{
    GasWellDataEntry entry = new GasWellDataEntry();
    GasWell davesWell = new GasWell( "Dave's Well" );
    entry.setWell( davesWell );
    entry.setStartInterval( twentyThirdApril.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 51.6 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.56 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 73.5 );
    entry.setMeasurement( WellMeasurementType.CONDENSATE_FLOW, 16.2 );

    ObjectOutputStream oos = null;
    try
    {
        oos = new ObjectOutputStream( new FileOutputStream( new File( "wxz.obj" ) ) );
    } catch (IOException e) {
        fail("Failed to create object output stream to file \"wxz.obj\"");
    }

    try
    {
        oos.writeObject( entry );
        oos.close();
    } catch (IOException e)
    {
        fail( "Failed to write out gas well data entry!!" + e.getClass().getName() + " - " + e.getMessage() );
    }

    ObjectInputStream ois = null;
    try {
        ois = new ObjectInputStream( new FileInputStream( new File( "wxz.obj" ) ) );
    } catch( IOException ioe ) {
        fail( "Failed to open file \"oxz.obj\" for reading." + ioe.getMessage() );
    }

    GasWellDataEntry extract = null;
    try
    {
        extract = (GasWellDataEntry)ois.readObject();

        ois.close();
    } catch ( Exception e) {
        fail("Failed to extract gas well data entry from wxz.obj!! " + e.getClass().getName() + " - " + e.getMessage());
    }

    assertNotNull( extract );
    assertNotNull( extract.getWell() );
    assertEquals( "Dave's Well", extract.getWell().getName() );
    assertEquals( twentyThirdApril.getTime(), extract.getStartInterval());
    assertEquals( 86400, extract.getIntervalLength());
    assertEquals( 51.6, extract.getMeasurement(WellMeasurementType.WATER_FLOW), 0.0001);
    assertEquals( 73.5, extract.getMeasurement(WellMeasurementType.OIL_FLOW), 0.0001 );
    assertEquals( 0.56, extract.getMeasurement(WellMeasurementType.GAS_FLOW), 0.001 );
    assertEquals( 16.2, extract.getMeasurement(WellMeasurementType.CONDENSATE_FLOW), 0.0001 );

    assertTrue( entry.equals( extract ) );
    assertEquals( entry.hashCode(), extract.hashCode() );
}

@Test
public void testEqualityNoMeasurements()
{
    GasWell davesWell = new GasWell( "Dave's well" );
    GasWell davesOtherWell = new GasWell( "Dave's well" );
    GasWell alansWell = new GasWell( "Alan's well" );
    GasWellDataEntry alpha = new GasWellDataEntry();
    GasWellDataEntry beta = new GasWellDataEntry();
    Date now = new Date();

    assertEquals( alpha, beta );

    alpha.setWell( davesWell );
    assertFalse( alpha.equals( beta ) );
    beta.setWell( alansWell );
    assertFalse( alpha.equals( beta ) );
    beta.setWell( davesOtherWell );
    assertEquals( alpha, beta );

    alpha.setStartInterval( now );
    assertFalse( alpha.equals( beta ) );
    beta.setStartInterval( now );
    assertEquals( alpha, beta );

    alpha.setIntervalLength( 3600 );
    assertFalse( alpha.equals( beta ) );
    beta.setIntervalLength( 3601 );
    assertFalse( alpha.equals( beta ) );
    beta.setIntervalLength( 3600 );
    assertEquals( alpha, beta );

}

@Test
public void testEqualityWithMeasurements()
{
    GasWell davesWell = new GasWell( "Dave's well" );
    GasWell davesOtherWell = new GasWell( "Dave's well" );
    GasWell alansWell = new GasWell( "Alan's well" );
    GasWellDataEntry alpha = new GasWellDataEntry();
    GasWellDataEntry beta = new GasWellDataEntry();
    Date now = new Date();

    assertEquals( alpha, beta );

    beta.setWell( davesWell );
    assertFalse( alpha.equals( beta ) );
    alpha.setWell( alansWell );
    assertFalse( alpha.equals( beta ) );
    alpha.setWell( davesOtherWell );
    assertEquals( alpha, beta );

    beta.setStartInterval( now );
    assertFalse( alpha.equals( beta ) );
    alpha.setStartInterval( now );
    assertEquals( alpha, beta );

    alpha.setIntervalLength( 3600 );
    assertFalse( alpha.equals( beta ) );
    beta.setIntervalLength( 3601 );
    assertFalse( alpha.equals( beta ) );
    beta.setIntervalLength( 3600 );
    assertEquals( alpha, beta );

    alpha.setMeasurement( WellMeasurementType.WATER_FLOW, 0.0 );
    assertFalse( beta.equals( alpha ) );
    beta.setMeasurement( WellMeasurementType.WATER_FLOW, 0.001 );
    assertFalse( alpha.equals( beta ) );
    alpha.setMeasurement( WellMeasurementType.WATER_FLOW, 0.001 );
    assertEquals( alpha, beta );

    beta.setMeasurement( WellMeasurementType.GAS_FLOW, 57.6 );
    assertFalse( alpha.equals( beta ) );
    alpha.setMeasurement( WellMeasurementType.GAS_FLOW, 5.76 );
    assertFalse( alpha.equals( beta ) );
    beta.setMeasurement( WellMeasurementType.GAS_FLOW, 5.76 );
    assertEquals( alpha, beta );

    beta.setMeasurement( WellMeasurementType.OIL_FLOW, 57.6 );
    assertFalse( alpha.equals( beta ) );
    alpha.setMeasurement( WellMeasurementType.OIL_FLOW, 5.76 );
    assertFalse( alpha.equals( beta ) );
    beta.setMeasurement( WellMeasurementType.OIL_FLOW, 5.76 );
    assertEquals( alpha, beta );

    beta.setMeasurement( WellMeasurementType.CONDENSATE_FLOW, 57.6 );
    assertFalse( alpha.equals( beta ) );
    alpha.setMeasurement( WellMeasurementType.CONDENSATE_FLOW, 5.76 );
    assertFalse( alpha.equals( beta ) );
    beta.setMeasurement( WellMeasurementType.CONDENSATE_FLOW, 5.76 );
    assertEquals( alpha, beta );
}

}