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
@RunWith( JUnit4.class )
public class GasWellDataEntryTest
{
DateParser dateParser;
Calendar twentyThirdApril;
Calendar endJuly;


public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( GasWellDataEntryTest.class );
}


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
    assertNull( entry.from() );
    assertEquals(0, entry.getIntervalLengthMS());
    assertNull(entry.until());
    assertNull(entry.getWell());
    assertFalse(entry.containsMeasurement(WellMeasurementType.WATER_FLOW));
    assertFalse(entry.containsMeasurement(WellMeasurementType.GAS_FLOW));
    assertFalse(entry.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW));
    assertFalse( entry.containsMeasurement( WellMeasurementType.OIL_FLOW ) );
    assertNull( entry.getComment() );

}

@Test
public void testSerialization()
{
    GasWellDataEntry entry = new GasWellDataEntry();
    GasWell davesWell = new GasWell( "Dave's Well" );
    entry.setWell(davesWell);
    entry.setDateRange(new DateRange(twentyThirdApril.getTime(), 86400000L, 1000L));
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 51.6 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.56 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 73.5 );
    entry.setMeasurement( WellMeasurementType.CONDENSATE_FLOW, 16.2 );
    entry.setComment( " Just for fun. ");

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
    assertEquals( twentyThirdApril.getTime(), extract.from());
    assertEquals( 86400000, extract.getIntervalLengthMS());
    assertEquals( 51.6, extract.getMeasurement(WellMeasurementType.WATER_FLOW), 0.0001);
    assertEquals( 73.5, extract.getMeasurement(WellMeasurementType.OIL_FLOW), 0.0001 );
    assertEquals( 0.56, extract.getMeasurement(WellMeasurementType.GAS_FLOW), 0.001 );
    assertEquals( 16.2, extract.getMeasurement(WellMeasurementType.CONDENSATE_FLOW), 0.0001 );
    assertNotNull( extract.getComment() );
    assertEquals( "Just for fun.", extract.getComment() );

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

    alpha.setDateRange(new DateRange(now, 360000L, 1000L));
    assertFalse( alpha.equals( beta ) );
    beta.setDateRange(new DateRange(now, 360000L, 1000L));
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
    assertEquals( alpha.hashCode(), beta.hashCode() );

    beta.setWell( davesWell );
    assertFalse( alpha.equals( beta ) );
    assertFalse( alpha.hashCode() == beta.hashCode() );
    alpha.setWell( alansWell );
    assertFalse( alpha.equals( beta ) );
    assertFalse( alpha.hashCode() == beta.hashCode() );
    alpha.setWell( davesOtherWell );
    assertEquals( alpha, beta );
    assertEquals( alpha.hashCode(), beta.hashCode() );


    alpha.setDateRange(new DateRange(now, 360000L, 1000L));
    assertFalse( alpha.equals( beta ) );
    assertFalse( alpha.hashCode() == beta.hashCode() );
    beta.setDateRange(new DateRange(now, 360000L, 1000L));
    assertEquals( alpha, beta );
    assertEquals( alpha.hashCode(), beta.hashCode() );

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
    assertFalse( alpha.hashCode() == beta.hashCode() );
    beta.setMeasurement( WellMeasurementType.OIL_FLOW, 5.76 );
    assertEquals( alpha, beta );
    assertEquals( alpha.hashCode(), beta.hashCode() );

    beta.setMeasurement( WellMeasurementType.CONDENSATE_FLOW, 57.6 );
    assertFalse(alpha.equals(beta));
    alpha.setMeasurement(WellMeasurementType.CONDENSATE_FLOW, 5.76);
    assertFalse(alpha.equals(beta));
    beta.setMeasurement(WellMeasurementType.CONDENSATE_FLOW, 5.76);
    assertEquals( alpha, beta );
    
    alpha.setComment( " " );
    assertEquals( alpha, beta );
    assertEquals( alpha.hashCode(), beta.hashCode() );

    alpha.setComment( "hello world" );
    assertFalse( alpha.equals( beta ) );
    assertFalse(alpha.hashCode() == beta.hashCode());
    beta.setComment( "hello world " );
    assertEquals( alpha, beta );
    assertEquals( alpha.hashCode(), beta.hashCode() );
}

}
