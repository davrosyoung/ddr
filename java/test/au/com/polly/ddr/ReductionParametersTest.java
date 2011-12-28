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

import au.com.polly.plotter.TimeUnit;
import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import au.com.polly.util.DateRange;
import junit.framework.JUnit4TestAdapter;
import org.apache.log4j.Logger;
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
public class ReductionParametersTest
{
private final static Logger logger = Logger.getLogger( ReductionParametersTest.class );
private final static double ACCEPTABLE_ERROR = 1E-8;
private final static DateParser parser = new AussieDateParser();


public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( ReductionParametersTest.class );
}


@Before
public void setupData()
{
}

@Test
public void testNullConstructor()
{
    ReductionParameters recipe = new ReductionParameters( null );
    assertNotNull( recipe );
    assertEquals(null, recipe.primaryIndicator);
    assertEquals(null, recipe.secondaryIndicator);
    assertNull(recipe.regularBoundaryStart);
    assertNull(recipe.regularBoundarySpanUnit);
    assertEquals(-1, recipe.regularBoundarySpanQuantity);
}

@Test
public void testConstructorWithSingleIndicator()
{
    ReductionParameters recipe = new ReductionParameters( WellMeasurementType.OIL_FLOW );
    assertNotNull( recipe );
    assertEquals( WellMeasurementType.OIL_FLOW, recipe.primaryIndicator );
    assertEquals( null, recipe.secondaryIndicator );
    assertNull( recipe.regularBoundaryStart );
    assertNull( recipe.regularBoundarySpanUnit );
    assertEquals( -1, recipe.regularBoundarySpanQuantity );
    
}

@Test
public void testConstructorWithTwoIndicators()
{
    ReductionParameters recipe = new ReductionParameters( WellMeasurementType.OIL_FLOW, WellMeasurementType.WATER_FLOW );
    assertNotNull( recipe );
    assertEquals( WellMeasurementType.OIL_FLOW, recipe.primaryIndicator );
    assertEquals( WellMeasurementType.WATER_FLOW, recipe.secondaryIndicator );
    assertNull( recipe.regularBoundaryStart );
    assertNull( recipe.regularBoundarySpanUnit );
    assertEquals( -1, recipe.regularBoundarySpanQuantity );
}

@Test
public void testConstructortWithTwoIdenticalIndicators()
{
    ReductionParameters recipe = new ReductionParameters( WellMeasurementType.WATER_FLOW, WellMeasurementType.WATER_FLOW );
    assertNotNull( recipe );
    assertEquals( WellMeasurementType.WATER_FLOW, recipe.primaryIndicator );
    assertEquals( WellMeasurementType.WATER_FLOW, recipe.secondaryIndicator );
    assertNull( recipe.regularBoundaryStart );
    assertNull( recipe.regularBoundarySpanUnit );
    assertEquals( -1, recipe.regularBoundarySpanQuantity );
    
}

@Test
public void testConstructorWithRegularBoundariesImplicit()
{
    ReductionParameters recipe = new ReductionParameters(
            WellMeasurementType.WATER_FLOW,
            WellMeasurementType.OIL_FLOW,
            parser.parse( "15/MAR/2012 04:00").getTime(), 2, TimeUnit.MONTH );
    assertNotNull(recipe);
    assertEquals( WellMeasurementType.WATER_FLOW, recipe.primaryIndicator );
    assertEquals(WellMeasurementType.OIL_FLOW, recipe.secondaryIndicator);
    assertNotNull(recipe.regularBoundaryStart);
    assertNotNull(recipe.regularBoundarySpanUnit);
    assertEquals( parser.parse( "15/MAR/2012 04:00").getTime(), recipe.regularBoundaryStart );
    assertEquals( 2, recipe.regularBoundarySpanQuantity );
    assertEquals( TimeUnit.MONTH, recipe.regularBoundarySpanUnit );
}

@Test
public void testConstructorWithFalseFlagButBoundaryValues()
{
    ReductionParameters recipe = new ReductionParameters(
            WellMeasurementType.WATER_FLOW,
            WellMeasurementType.OIL_FLOW,
            false,
            parser.parse( "15/MAR/2012 04:00").getTime(), 2, TimeUnit.MONTH );
    assertNotNull( recipe );
    assertEquals( WellMeasurementType.WATER_FLOW, recipe.primaryIndicator );
    assertEquals( WellMeasurementType.OIL_FLOW, recipe.secondaryIndicator );
    assertNull(recipe.regularBoundaryStart);
    assertNull(recipe.regularBoundarySpanUnit);
    assertEquals( -1, recipe.regularBoundarySpanQuantity );
}

}
