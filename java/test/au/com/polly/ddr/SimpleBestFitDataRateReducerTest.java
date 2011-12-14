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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Map;

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
public class SimpleBestFitDataRateReducerTest
{
static private final Logger logger = Logger.getLogger( SimpleBestFitDataRateReducerTest.class );

private final static GasWell dummyWell = new GasWell( "Dummy" );
private GasWellDataSet dataSet = null;


public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( SimpleBestFitDataRateReducerTest.class );
}

@Before
public void setup()
{
   // populate some dummy data into the gas well data set....
    // --------------------------------------------------------
    dataSet = TestGasWellDataSet.getDummyDataSet();
}

@Test
public void testReducingData()
{
    DataRateReducer reducer = new SimpleBestFitDataRateReducer();
    GasWellDataSet reducedDataSet;

    reducedDataSet = reducer.reduce( dataSet, 10 );
    assertNotNull( reducedDataSet );
    assertEquals( "Dummy", reducedDataSet.getWellName() );
    assertEquals( dataSet.from(), reducedDataSet.from() );
    assertEquals( dataSet.from(), reducedDataSet.from() );

    Map<WellMeasurementType,Double> errorMap = GasWellDataSetUtil.getError( dataSet, reducedDataSet );
    if ( errorMap.containsKey( WellMeasurementType.OIL_FLOW ) )
    {
        logger.debug( "error on OIL_FLOW=" + errorMap.get( WellMeasurementType.OIL_FLOW ) );
    }

    logger.debug( reducedDataSet );



}

}
