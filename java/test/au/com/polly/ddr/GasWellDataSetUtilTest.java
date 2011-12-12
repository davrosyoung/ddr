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
DateParser dateParser;
Calendar twentyThirdApril;
Calendar twentyThirdAprilFiveInTheMorning;
Calendar twentyThirdAprilJustBeforeNoon;
Calendar endJuly;
GasWell davesWell = null;
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
}
