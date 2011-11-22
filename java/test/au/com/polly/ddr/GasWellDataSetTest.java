package au.com.polly.ddr;

import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

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
GasWellDataSet dataSet;
DateParser dateParser;
Calendar twentyThirdApril;
Calendar endJuly;
private final static double ACCEPTABLE_ERROR=1E-8;

@Before
public void setupData()
{
    DataVsTimeSource dataSource = new DummyGasWellDataSet();
    dataSet = dataSource.getData();

    dateParser = new AussieDateParser();
    twentyThirdApril = dateParser.parse( "23/APRIL/2011" );
    endJuly = dateParser.parse( "31/JUL/2011 23:59:59" );
}

@Test
public void testFrom()
{
    Date from = dataSet.from();
    assertNotNull( from );
    assertEquals( twentyThirdApril.getTime(), from );
}

@Test
public void testUntil()
{
    Date until = dataSet.until();
    assertNotNull( until );
    assertEquals( endJuly.getTime(), until );
}

@Test
public void testGetMinimumOilFlow()
{
    double minimum = dataSet.getMinimum(WellMeasurementType.OIL_FLOW);
    assertEquals( 0, minimum, ACCEPTABLE_ERROR );
}

@Test
public void testGetMaximumOilFlow()
{
    double maximum = dataSet.getMaximum(WellMeasurementType.OIL_FLOW);
    assertEquals( 1580, maximum, ACCEPTABLE_ERROR );
}

@Test
public void testGetMinimumGasFlow()
{
    double minimum = dataSet.getMinimum(WellMeasurementType.GAS_FLOW);
    assertEquals( 0, minimum, ACCEPTABLE_ERROR );
}

@Test
public void testGetMaximumGasFlow()
{
    double maximum = dataSet.getMaximum(WellMeasurementType.GAS_FLOW);
    assertEquals( 0.46, maximum, ACCEPTABLE_ERROR );
}

@Test
public void testGetMinimumWaterFlow()
{
    double minimum = dataSet.getMinimum(WellMeasurementType.WATER_FLOW);
    assertEquals( 0, minimum, ACCEPTABLE_ERROR );
}

@Test
public void testGetMaximumWaterFlow()
{
    double maximum = dataSet.getMaximum(WellMeasurementType.WATER_FLOW);
    assertEquals( 8640, maximum, ACCEPTABLE_ERROR );
}

@Test
public void testGetMinima()
{
    GasWellDataEntry entry = dataSet.getMinima();
    assertNotNull( entry );
    assertEquals( twentyThirdApril.getTime(), entry.getStartInterval() );
    assertEquals( 0.0, entry.getMeasurement(WellMeasurementType.OIL_FLOW), ACCEPTABLE_ERROR );
    assertEquals( 0.0, entry.getMeasurement(WellMeasurementType.GAS_FLOW), ACCEPTABLE_ERROR );
    assertEquals( 0.0, entry.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
}

@Test
public void testGetMaxima()
{
    GasWellDataEntry entry = dataSet.getMaxima();
    assertNotNull( entry );
    assertEquals( endJuly.getTime(), entry.getUntil() );
    assertEquals( 1580, entry.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.46, entry.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 8640, entry.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

}

}
