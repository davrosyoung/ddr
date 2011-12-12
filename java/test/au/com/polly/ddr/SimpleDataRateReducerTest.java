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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 24/11/11
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class SimpleDataRateReducerTest
{
static private final Logger logger = Logger.getLogger( SimpleDataRateReducerTest.class );
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
        1203.0, 1205.0, 1207.0, 1209.0, 1211.0, 1210.0, 1210.0, 1210.0, 1210.0, 1210.0
};
private final static GasWell dummyWell = new GasWell( "Dummy" );
private GasWellDataSet dataSet = null;


public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( SimpleDataRateReducerTest.class );
}

@Before
public void setup()
{
   // populate some dummy data into the gas well data set....
    // --------------------------------------------------------
    dataSet = new GasWellDataSet( dummyWell );
    GasWellDataEntry entry;
    Calendar when;
    DateParser dateParser = new AussieDateParser();

    when = dateParser.parse( "13/06/2011 04:00" );

    for( int i = 0; i < oilFlowRates.length; i++ )
    {
        entry = new GasWellDataEntry();
        entry.setWell( dummyWell );
        entry.setStartInterval( when.getTime() );
        when.add( Calendar.DATE, 1 );
        entry.setIntervalLength( 86400 );
        entry.setMeasurement( WellMeasurementType.OIL_FLOW, oilFlowRates[ i ] );
        dataSet.addDataEntry( entry );
    }
}

@Test
public void testReducingDataToTenIntervals()
{
    SimpleDataRateReducer reducer = new SimpleDataRateReducer();
    GasWellDataSet reducedDataSet;

    reducedDataSet = reducer.reduce( dataSet, 10 );
    assertNotNull( reducedDataSet );
    assertEquals( "Dummy", reducedDataSet.getWellName() );
    assertEquals( 10, reducedDataSet.getData().size() );
    logger.debug( reducedDataSet );

}

}
