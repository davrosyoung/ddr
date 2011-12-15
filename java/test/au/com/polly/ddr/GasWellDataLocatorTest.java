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
public class GasWellDataLocatorTest
{


public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( GasWellDataLocatorTest.class );
}


@Before
public void setupData()
{

}

@Test
public void testNullConstructor()
{
    GasWellDataLocator locator = new GasWellDataLocator();
    assertNotNull( locator );
    assertFalse( locator.containsMeasurementCellLocation( WellMeasurementType.CONDENSATE_FLOW ) );
    assertFalse( locator.containsMeasurementCellLocation( WellMeasurementType.WATER_FLOW ) );
    assertFalse( locator.containsMeasurementCellLocation( WellMeasurementType.GAS_FLOW ) );
    assertFalse( locator.containsMeasurementCellLocation( WellMeasurementType.OIL_FLOW ) );
    
    assertNull( locator.getWellCellLocation() );
    assertNull( locator.getIntervalLengthLocation() );
    assertEquals( -1, locator.getDateColumn() );
    assertEquals( -1, locator.getStartDataRow() );
    assertEquals( -1, locator.getEndDataRow() );
    
    assertEquals( "well \"null\", NO interval length location, NO gas cell location, NO condensate cell location, NO oil cell location, NO water cell location", locator.toString() );
}

@Test
public void testSettingUpWithCondensateAndNoOil()
{
    GasWellDataLocator locator = new GasWellDataLocator();
    locator.setMeasurementCellLocation( WellMeasurementType.CONDENSATE_FLOW, new ExcelCellLocation( 1, 5 ) );
    locator.setMeasurementCellLocation( WellMeasurementType.WATER_FLOW, new ExcelCellLocation( 1, 6 ) );
    locator.setMeasurementCellLocation(WellMeasurementType.GAS_FLOW, new ExcelCellLocation(1, 8));
    locator.setStartDataRow( 3 );
    locator.setEndDataRow( 8523 );
    locator.setDateColumn( 2 );
    locator.setWellName( "osho" );

    assertTrue(locator.containsMeasurementCellLocation(WellMeasurementType.CONDENSATE_FLOW));
    assertTrue(locator.containsMeasurementCellLocation(WellMeasurementType.WATER_FLOW));
    assertTrue(locator.containsMeasurementCellLocation(WellMeasurementType.GAS_FLOW));
    assertFalse( locator.containsMeasurementCellLocation( WellMeasurementType.OIL_FLOW ) );

    assertNull( locator.getWellCellLocation() );
    assertNull(locator.getIntervalLengthLocation());
    assertEquals( 2, locator.getDateColumn() );
    assertEquals( 3, locator.getStartDataRow() );
    assertEquals( 8523, locator.getEndDataRow() );
    assertEquals( "well \"osho\", startDataRow=3, endDataRow=8523, dateColumn=2, NO interval length location, gas cell location=I2, condensate cell location=F2, NO oil cell location, water cell location=G2", locator.toString() );

}

@Test
public void testSettingUpWithIntervalLengthOilAndNoCondensate()
{
    GasWellDataLocator locator = new GasWellDataLocator();
    locator.setMeasurementCellLocation(WellMeasurementType.OIL_FLOW, new ExcelCellLocation(1, 7) );
    locator.setMeasurementCellLocation(WellMeasurementType.WATER_FLOW, new ExcelCellLocation(1, 6) );
    locator.setMeasurementCellLocation(WellMeasurementType.GAS_FLOW, new ExcelCellLocation(1, 8) );
    locator.setStartDataRow(2);
    locator.setEndDataRow(42);
    locator.setDateColumn(3);
    locator.setWellName("osho");
    locator.setWellCellLocation( new ExcelCellLocation( 0, 1 ) );
    locator.setIntervalLengthLocation( new ExcelCellLocation( 1, 4 ) );

    assertFalse(locator.containsMeasurementCellLocation(WellMeasurementType.CONDENSATE_FLOW));
    assertTrue(locator.containsMeasurementCellLocation(WellMeasurementType.WATER_FLOW));
    assertTrue( locator.containsMeasurementCellLocation( WellMeasurementType.GAS_FLOW ) );
    assertTrue(locator.containsMeasurementCellLocation(WellMeasurementType.OIL_FLOW));

    assertNotNull(locator.getIntervalLengthLocation());
    assertNotNull( locator.getWellCellLocation() );
    assertEquals( "B1", locator.getWellCellLocation().toString() );
    assertEquals( 3, locator.getDateColumn() );
    assertEquals( 2, locator.getStartDataRow() );
    assertEquals( 42, locator.getEndDataRow() );
    assertEquals( "well \"osho\", startDataRow=2, endDataRow=42, dateColumn=3, interval length location=E2, gas cell location=I2, NO condensate cell location, oil cell location=H2, water cell location=G2", locator.toString() );

}



}
