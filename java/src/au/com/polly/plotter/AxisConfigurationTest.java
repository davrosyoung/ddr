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

package au.com.polly.plotter;

import junit.framework.JUnit4TestAdapter;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 *
 * Battery of tests of the axis configuration class. we need to ensure that we can appropriately populate
 * the axis from a set of poperty values, even if those property values are embedded within other property
 * values with similar names.
 *
 */
public class AxisConfigurationTest
{
    private final static Logger logger = Logger.getLogger(AxisConfigurationTest.class);
    private final static double ACCEPTABLE_ERROR = 1E-8;

    Properties standardProperties;
    Properties aggregateProperties;

    @Before
    public void buildStandardProperties()
    {
        standardProperties = new Properties();
        standardProperties.put( "label", "dave's test" );
        standardProperties.put( "colour", "yellow" );
        standardProperties.put( "min", "auto" );
        standardProperties.put( "max", "auto" );

        aggregateProperties = new Properties();
        aggregateProperties.put( "axis[1].label", "dave's test" );
        aggregateProperties.put( "axis[1].colour", "yellow" );
        aggregateProperties.put( "axis[1].grid_colour", "orange" );
        aggregateProperties.put( "axis[1].min", "auto" );
        aggregateProperties.put( "axis[1].max", "auto" );
        aggregateProperties.put( "axis[1].units", "kg" );
        aggregateProperties.put( "axis[2].label", "second axis" );
        aggregateProperties.put( "axis[2].colour", "red" );
        aggregateProperties.put( "axis[2].grid_colour", "orange" );
        aggregateProperties.put( "axis[2].min", "42" );
        aggregateProperties.put( "axis[2].max", "97" );
        aggregateProperties.put( "axis[2].units", "km/h" );
        aggregateProperties.put( "axis[3].label", "third axis" );
        aggregateProperties.put( "axis[3].colour", "green" );
        aggregateProperties.put( "axis[3].min", "15" );
        aggregateProperties.put( "axis[3].max", "20" );
        aggregateProperties.put( "axis[3].units", "litre" );

    }


    /**
     * Demonstrate that the default constructor works as expected.
     */
    @Test
    public void testDefaultAxisConfiguration()
    {
        AxisConfiguration config = new AxisConfiguration();
        assertNotNull( config );
        assertNull( config.getLabel() );
        assertEquals( Color.BLACK, config.getColour() );
        assertEquals( Color.BLACK, config.getGridColour() );
        assertEquals( true, config.isAutoScale() );
        assertNull( config.getUnits() );
        assertNull( config.getMax() );
        assertNull( config.getMin() );
        assertNull( config.getUnits() );
    }

    /**
     * Demonstrate that the regular constructor works as expected.
     */
    @Test
    public void testDefaultAxisConfigurationWithConstructor()
    {
        AxisConfiguration config = new AxisConfiguration( "test", null, null, 0 );
        assertNotNull( config );
        assertEquals( "test", config.getLabel() );
        assertEquals( Color.BLACK, config.getColour() );
        assertEquals( Color.BLACK, config.getGridColour() );
        assertEquals( true, config.isAutoScale() );
        assertNull( config.getUnits() );
        assertNull( config.getMax() );
        assertNull( config.getMin() );
        assertNull( config.getUnits() );
    }


    /**
     * Demonstrate that if we define both min and max values for the axis to be autoscaled, that
     * the configuration gets appropriately populated.
     */
    @Test
    public void testPopulatingAxisFromPropertiesWithAutoScale()
    {
        AxisConfiguration config = new AxisConfiguration();
        config.populate( standardProperties );
        assertNotNull( config );
        assertEquals( "dave's test", config.getLabel() );
        assertEquals( Color.YELLOW, config.getColour() );
        assertEquals( Color.YELLOW, config.getGridColour() );
        assertEquals( true, config.isAutoScale() );
        assertNull( config.getUnits() );
        assertNull( config.getMax() );
        assertNull( config.getMin() );
    }


    /**
     * Demonstrate that if we define just a minimum value, but an autoscale for the maximum value, that in fact,
     * both the minimum and maximum values will get autoscaled.
     */
    @Test
    public void testPopulatingAxisFromPropertiesWithAutoScaleAndMin()
    {
        AxisConfiguration config = new AxisConfiguration();
        standardProperties.put( "min", "27.4" );
        config.populate( standardProperties );
        assertNotNull( config );
        assertEquals( "dave's test", config.getLabel() );
        assertEquals( Color.YELLOW, config.getColour() );
        assertEquals( Color.YELLOW, config.getGridColour() );
        assertEquals( true, config.isAutoScale() );
        assertNull( config.getUnits() );
        assertNull( config.getMax() );
        assertNull( config.getMin() );
    }


    /**
     * Demonstrate that if we defined both a minimum and maximum value for the axis, within the configuration
     * properties, that they get correctly populated.
     */
    @Test
    public void testPopulatingAxisFromPropertiesWithMinAndMax()
    {
        AxisConfiguration config = new AxisConfiguration();
        standardProperties.put( "min", "27.4" );
        standardProperties.put( "max", "58.2" );
        assertEquals( "27.4", standardProperties.get( "min" ) );
        assertEquals( "58.2", standardProperties.get( "max" ) );
        config.populate( standardProperties );
        assertNotNull( config );
        assertEquals( "dave's test", config.getLabel() );
        assertEquals( Color.YELLOW, config.getColour() );
        assertEquals( Color.YELLOW, config.getGridColour() );
        assertFalse( config.isAutoScale() );
        assertNull( config.getUnits() );
        assertNotNull( config.getMin() );
        assertNotNull( config.getMax() );
        assertEquals( 27.4, config.getMin().doubleValue(), ACCEPTABLE_ERROR );
        assertEquals( 58.2, config.getMax().doubleValue(), ACCEPTABLE_ERROR );
    }

    /**
     * Demonstrate that we can extract axis configuration embedded within other axis configureations
     * in a set of property definitions.
     */
    @Test
    public void testPopulatingAxisFromQualifiedPropertiesWithMinAndMax()
    {
        AxisConfiguration config = new AxisConfiguration();
        aggregateProperties.put( "min", "27.4" );
        aggregateProperties.put( "min", "58.2" );
        config.populate( aggregateProperties, "axis[2]" );
        assertNotNull( config );
        assertEquals( "second axis", config.getLabel() );
        assertEquals( Color.RED, config.getColour() );
        assertEquals( Color.ORANGE, config.getGridColour() );
        assertFalse( config.isAutoScale() );
        assertNotNull( config.getUnits() );
        assertNotNull( config.getMin() );
        assertNotNull( config.getMax() );
        assertEquals( 42L, config.getMin().intValue() );
        assertEquals( 97L, config.getMax().intValue() );
        assertNotNull( config.getUnits() );
        assertEquals( "km/h", config.getUnits() );

    }
	
	@Test
	public void testConstructorWithSingleColour()
	{
		AxisConfiguration config = new AxisConfiguration( "test", "volts", Color.BLUE, 400 );
		assertNotNull( config );
		assertEquals(  "test", config.getLabel() );
		assertEquals( "volts", config.getUnits() );
		assertEquals(  Color.BLUE, config.getColour() );
		assertEquals(  Color.BLUE, config.getGridColour() );
		assertEquals(  400, config.getPlotLength() );
		assertEquals(  null, config.getMin() );
		assertEquals(  null, config.getMax() );
	}

	@Test
	public void testConstructorWithSeparateGridColour()
	{
		AxisConfiguration config = new AxisConfiguration( "test", "volts", Color.BLUE, Color.GREEN, 400 );
		assertNotNull( config );
		assertEquals(  "test", config.getLabel() );
		assertEquals( "volts", config.getUnits() );
		assertEquals(  Color.BLUE, config.getColour() );
		assertEquals(  Color.GREEN, config.getGridColour() );
		assertEquals(  400, config.getPlotLength() );
		assertEquals(  null, config.getMin() );
		assertEquals(  null, config.getMax() );
	}

    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( AxisConfigurationTest.class );
    }

}
