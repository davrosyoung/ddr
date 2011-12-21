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

package au.com.polly.plotter.csvgrapher;

import au.com.polly.util.DataType;
import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 *
 */
public class DataSeriesConfigurationTest
{
Properties qualifiedProperties;
Properties relativeProperties;

@Before
public void buildStandardProperties()
{
    qualifiedProperties = new Properties();
    qualifiedProperties.put( "graph[0].x.type", "timestamp" );
    qualifiedProperties.put( "graph[0].x.column", "1" );
    qualifiedProperties.put( "graph[0].x.axis", "0" );
    qualifiedProperties.put( "graph[0].y[0].type", "long" );
    qualifiedProperties.put( "graph[0].y[0].column", "2" );
    qualifiedProperties.put( "graph[0].y[0].axis", "1" );
    qualifiedProperties.put( "graph[0].y[0].colour", "black" );
    qualifiedProperties.put( "graph[0].y[1].type", "long" );
    qualifiedProperties.put( "graph[0].y[1].column", "3" );
    qualifiedProperties.put( "graph[0].y[1].axis", "1" );
    qualifiedProperties.put( "graph[0].y[1].colour", "blue" );

    relativeProperties = new Properties();
    relativeProperties.put( "type", "timestamp" );
    relativeProperties.put( "column", "1" );
    relativeProperties.put( "axis", "0" );
}


/**
 * Demonstrate that the default constructor works as expected.
 */
@Test
public void testDefaultDataSeriesConfiguration()
{
    DataSeriesConfiguration config = new DataSeriesConfiguration();
    assertNotNull( config );
    assertEquals( Color.BLACK, config.getColour() );
    assertEquals( DataType.UNKNOWN, config.getType() );
    assertEquals(-1,  config.getAxisID() );
    assertEquals( -1, config.getColumnID() );
}


/**
 * Demonstrate populating data series configuration with relative
 * properties for a single data series configuration
 */
@Test
public void testDefaultDataSeriesConfigurationWithConstructor()
{
    DataSeriesConfiguration config = new DataSeriesConfiguration();
    assertNotNull( config );
    assertEquals( -1, config.getAxisID() );
    assertEquals( -1, config.getColumnID() );
    assertEquals( Color.BLACK, config.getColour() );
    assertEquals( DataType.UNKNOWN, config.getType() );
}


/**
 * Demonstrate that if we populate this configuration with a set of null properties
 * a null pointer exception is thrown.
 */
@Test( expected = NullPointerException.class )
public void testPopulatingDataSeriesFromNullProperties()
{
    DataSeriesConfiguration config = new DataSeriesConfiguration();
    assertNotNull( config );
    config.populate( null, null );    
}


/**
 * Demonstrate that if we populate this configuration with a set of empty properties
 * that the method returns the correct value.
 */
@Test
public void testPopulatingDataSeriesFromEmptyProperties()
{
    DataSeriesConfiguration config = new DataSeriesConfiguration();
    Properties props = new Properties();
    boolean populated;
    assertNotNull( config );
    populated = config.populate( props, null );
    assertFalse( populated );
    assertEquals( -1, config.getAxisID() );
    assertEquals( -1, config.getColumnID() );
    assertEquals( Color.BLACK, config.getColour() );
    assertEquals( DataType.UNKNOWN, config.getType() );
}


/**
 * Demonstrate that if we populate this configuration with a set of properties which
 * are not specific to this data series, that the appropriate return value is given.
 */
@Test
public void testPopulatingDataSeriesFromPropertiesWithMismatchQualifier()
{
    DataSeriesConfiguration config = new DataSeriesConfiguration();
    Properties props = new Properties();
    boolean populated;
    assertNotNull( config );
    populated = config.populate( qualifiedProperties, "graph[1].x" );
    assertFalse( populated );
    assertEquals( -1, config.getAxisID() );
    assertEquals( -1, config.getColumnID() );
    assertEquals( Color.BLACK, config.getColour() );
    assertEquals( DataType.UNKNOWN, config.getType() );
}

/**
 * Demonstrate that if we populate this configuration with a set of properties which
 * are not specific to this data series, that the appropriate return value is given.
 */
@Test
public void testPopulatingDataSeriesFromPropertiesWithAppropriateQualifier()
{
    DataSeriesConfiguration config = new DataSeriesConfiguration();
    Properties props = new Properties();
    boolean populated;
    assertNotNull( config );
    populated = config.populate( qualifiedProperties, "graph[0].x" );
    assertTrue( populated );
    assertEquals( 0, config.getAxisID() );
    assertEquals( 1, config.getColumnID() );
    assertEquals( Color.BLACK, config.getColour() );
    assertEquals( DataType.TIMESTAMP, config.getType() );
}


/**
 * Demonstrate that if we populate this configuration with a set of properties which
 * are not specific to this data series, that the appropriate return value is given.
 */
@Test
public void testPopulatingDataSeriesFromRelativeProperties()
{
    DataSeriesConfiguration config = new DataSeriesConfiguration();
    Properties props = new Properties();
    boolean populated;
    assertNotNull( config );
    populated = config.populate( relativeProperties, null );
    assertTrue( populated );
    assertEquals( 0, config.getAxisID() );
    assertEquals( 1, config.getColumnID() );
    assertEquals( Color.BLACK, config.getColour() );
    assertEquals( DataType.TIMESTAMP, config.getType() );
}

@Test( expected = NullPointerException.class )
public void testSettingColumnIDWithNullValue()
{
    DataSeriesConfiguration config = new DataSeriesConfiguration();
    assertEquals( -1, config.getColumnID() );

    // this should throw a null pointer exception...
    // ------------------------------------------------
    config.setColumnID( null );
}

@Test
public void testSettingColumnIDWithEmptyValue()
{
    DataSeriesConfiguration config = new DataSeriesConfiguration();
    assertEquals( -1, config.getColumnID() );

    config.setColumnID( 5 );
    assertEquals( 5, config.getColumnID() );

    config.setColumnID( "" );
    assertEquals( -1, config.getColumnID() );

    config.setColumnID( 5 );
    assertEquals( 5, config.getColumnID() );

    config.setColumnID( "   " );
    assertEquals( -1, config.getColumnID() );
}

@Test
public void testSettingColumnIDWithNonsensicalValue()
{
    DataSeriesConfiguration config = new DataSeriesConfiguration();
    assertEquals( -1, config.getColumnID() );

    config.setColumnID( 5 );
    assertEquals( 5, config.getColumnID() );

    config.setColumnID( "A5" );
    assertEquals( -1, config.getColumnID() );

    config.setColumnID( 5 );
    assertEquals( 5, config.getColumnID() );

    config.setColumnID( "Appple" );
    assertEquals( -1, config.getColumnID() );
}

@Test
public void testSettingColumnIDWithNumericStringValue()
{
    DataSeriesConfiguration config = new DataSeriesConfiguration();
    assertEquals( -1, config.getColumnID() );

    config.setColumnID( 5 );
    assertEquals( 5, config.getColumnID() );

    config.setColumnID( "4" );
    assertEquals( 4, config.getColumnID() );

    config.setColumnID( 5 );
    assertEquals( 5, config.getColumnID() );

    config.setColumnID( " 42 " );
    assertEquals( 42, config.getColumnID() );
}

@Test
public void testSettingColumnIDWithSingleCharacterExcelValue()
{
DataSeriesConfiguration config = new DataSeriesConfiguration();
    assertEquals( -1, config.getColumnID() );

    config.setColumnID( 5 );
    assertEquals( 5, config.getColumnID() );

    config.setColumnID( "A" );
    assertEquals( 1, config.getColumnID() );

    config.setColumnID( 5 );
    assertEquals( 5, config.getColumnID() );

    config.setColumnID( "M" );
    assertEquals( 13, config.getColumnID() );

    config.setColumnID( "Z" );
    assertEquals( 26, config.getColumnID() );
}

@Test
public void testSettingColumnIDWithDoubleCharacterExcelValue()
{
    DataSeriesConfiguration config = new DataSeriesConfiguration();
    assertEquals( -1, config.getColumnID() );

    config.setColumnID( "AA" );
    assertEquals( 27, config.getColumnID() );

    config.setColumnID( "AZ" );
    assertEquals( 52, config.getColumnID() );

    config.setColumnID( "ba" );
    assertEquals( 53, config.getColumnID() );

    config.setColumnID( "za" );
    assertEquals( ( 26 * 26 ) + 1, config.getColumnID() );

    config.setColumnID( "zz" );
    assertEquals( 27 * 26, config.getColumnID() );

}


public static junit.framework.Test suite()
{
    return new JUnit4TestAdapter( DataSeriesConfigurationTest.class );
}

}
