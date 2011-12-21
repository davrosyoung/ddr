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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 *
 * Battery of tests of the axis configuration class. we need to ensure that we can appropriately populate
 * the axis from a set of poperty values, even if those property values are embedded within other property
 * values with similar names.
 *
 */
public class DataSeriesConfigurationFactoryTest
{
Properties relativeProperties;
Properties qualifiedProperties;

@Before
public void buildProperties()
{
    relativeProperties = new Properties();
    relativeProperties.put( "x.type", "timestamp" );
    relativeProperties.put( "x.axis", "0" );
    relativeProperties.put( "x.column", "5" );

    relativeProperties.put( "y[0].type", "long" );
    relativeProperties.put( "y[0].axis", "1" );
    relativeProperties.put( "y[0].column", "3" );

    relativeProperties.put( "y[1].type", "long" );
    relativeProperties.put( "y[1].axis", "1" );
    relativeProperties.put( "y[1].column", "2" );
    relativeProperties.put( "y[1].marker_size", "5" );

    relativeProperties.put( "y[2].type", "double" );
    relativeProperties.put( "y[2].axis", "2" );
    relativeProperties.put( "y[2].column", "4" );


    qualifiedProperties = new Properties();
    qualifiedProperties.put( "graph[4].x.type", "long" );
    qualifiedProperties.put( "graph[4].x.axis", "0" );
    qualifiedProperties.put( "graph[4].x.column", "1" );
    qualifiedProperties.put( "graph[4].y.type", "long" );
    qualifiedProperties.put( "graph[4].y.axis", "1" );
    qualifiedProperties.put( "graph[4].y.column", "2" );

    qualifiedProperties.put( "graph[5].x.type", "timestamp" );
    qualifiedProperties.put( "graph[5].x.axis", "0" );
    qualifiedProperties.put( "graph[5].x.column", "1" );
    qualifiedProperties.put( "graph[5].y.type", "double" );
    qualifiedProperties.put( "graph[5].y.axis", "1" );
    qualifiedProperties.put( "graph[5].y.column", "2" );
    qualifiedProperties.put( "graph[5].y.marker_size", "8" );

    qualifiedProperties.put( "graph[6].x.type", "timestamp" );
    qualifiedProperties.put( "graph[6].x.axis", "0" );
    qualifiedProperties.put( "graph[6].x.column", "1" );
    qualifiedProperties.put( "graph[6].y[0].type", "double" );
    qualifiedProperties.put( "graph[6].y[0].axis", "1" );
    qualifiedProperties.put( "graph[6].y[0].column", "2" );
    qualifiedProperties.put( "graph[6].y[1].type", "long" );
    qualifiedProperties.put( "graph[6].y[1].axis", "2" );
    qualifiedProperties.put( "graph[6].y[1].column", "3" );
    qualifiedProperties.put( "graph[6].y[2].type", "double" );
    qualifiedProperties.put( "graph[6].y[2].axis", "1" );
    qualifiedProperties.put( "graph[6].y[2].column", "4" );

}


/**
 * Demonstrate that the data series configuration factory is a singleton.
 *
 */
@Test
public void testCreatingDataSeriesConfigFactory()
{
    DataSeriesConfigurationFactory factory = DataSeriesConfigurationFactory.getInstance();
    DataSeriesConfigurationFactory factory2 = DataSeriesConfigurationFactory.getInstance();
    Assert.assertNotNull(factory);
    Assert.assertNotNull(factory2);
    Assert.assertTrue(factory == factory2);
}

/**
 * Demonstrate that if a null set of properties is passed when attempting to
 * extract the x data series, a null pointer exception is thrown.
 *
 */
@Test(expected = NullPointerException.class)
public void testExtactingXSeriesConfigFromNullProperties()
{
    DataSeriesConfiguration config = new DataSeriesConfiguration();
    DataSeriesConfigurationFactory factory = DataSeriesConfigurationFactory.getInstance();
    config = factory.extractXSeries( null, null );
}

/**
 * Demonstrate that if a null set of properties is passed when attempting to
 * extract the y data series, a null pointer exception is thrown.
 *
 */
@Test(expected = NullPointerException.class)
public void testExtactingYSeriesConfigFromNullProperties()
{
    DataSeriesConfiguration config = new DataSeriesConfiguration();
    DataSeriesConfigurationFactory factory = DataSeriesConfigurationFactory.getInstance();
    config = factory.extractYSeries( null, null );
}


/**
 * Demonstrate that if a null set of properties is passed when attempting to
 * extract the multiple y data series, a null pointer exception is thrown.
 *
 */
@Test(expected = NullPointerException.class)
public void testExtactingMultiYSeriesConfigFromNullProperties()
{
    List<DataSeriesConfiguration> config = new ArrayList<DataSeriesConfiguration>();
    DataSeriesConfigurationFactory factory = DataSeriesConfigurationFactory.getInstance();
    config = factory.extractMultiYSeries( null, null );
}

/**
 * Demonstrate that if a null set of properties is passed when attempting to
 * extract the x data series, a null pointer exception is thrown.
 *
 */
@Test
public void testExtactingXSeriesConfigFromEmptyProperties()
{
    DataSeriesConfiguration config = new DataSeriesConfiguration();
    Properties props = new Properties();
    DataSeriesConfigurationFactory factory = DataSeriesConfigurationFactory.getInstance();
    config = factory.extractXSeries( props, null );
    assertNull( config );
}

/**
 * Demonstrate that if a null set of properties is passed when attempting to
 * extract the y data series, a null pointer exception is thrown.
 *
 */
@Test
public void testExtactingYSeriesConfigFromEmptyProperties()
{
    DataSeriesConfiguration config = new DataSeriesConfiguration();
    Properties props = new Properties();
    DataSeriesConfigurationFactory factory = DataSeriesConfigurationFactory.getInstance();
    config = factory.extractYSeries( props, null );
    assertNull( config );
}


/**
 * Demonstrate that if a null set of properties is passed when attempting to
 * extract the multiple y data series, a null pointer exception is thrown.
 *
 */
@Test
public void testExtactingMultiYSeriesConfigFromEmptyProperties()
{
    Properties props = new Properties();
    List<DataSeriesConfiguration> config = new ArrayList<DataSeriesConfiguration>();
    DataSeriesConfigurationFactory factory = DataSeriesConfigurationFactory.getInstance();
    config = factory.extractMultiYSeries( props, null );
    assertNull( config );
}

/**
 *
 *
 */
@Test
public void testExtactingDataSeriesConfigFromRelativeProperties()
{
    DataSeriesConfiguration config = new DataSeriesConfiguration();
    DataSeriesConfigurationFactory factory = DataSeriesConfigurationFactory.getInstance();
    List<DataSeriesConfiguration> multiYSeriesConfig = null;
    DataSeriesConfiguration xSeriesConfig = null;
    DataSeriesConfiguration ySeriesConfig = null;
    DataSeriesConfiguration y0, y1, y2;
    int count=0;

    xSeriesConfig = factory.extractXSeries( relativeProperties, null );
    assertNotNull( xSeriesConfig );
    assertEquals( DataType.TIMESTAMP, xSeriesConfig.getType() );
    assertEquals( 0, xSeriesConfig.getAxisID() );
    assertEquals( 5, xSeriesConfig.getColumnID() );
    assertEquals( 8, xSeriesConfig.getMarkerSize() );

    ySeriesConfig = factory.extractYSeries( relativeProperties, null );
    assertNull( ySeriesConfig );

    multiYSeriesConfig = factory.extractMultiYSeries( relativeProperties, null );
    assertNotNull( multiYSeriesConfig );

    assertEquals( 3, multiYSeriesConfig.size() );

    y0 = multiYSeriesConfig.get( 0 );
    y1 = multiYSeriesConfig.get( 1 );
    y2 = multiYSeriesConfig.get( 2 );

    assertNotNull( y0 );
    assertNotNull( y1 );
    assertNotNull( y2 );

    assertEquals( DataType.LONG, y0.getType() );
    assertEquals( 1, y0.getAxisID() );
    assertEquals( 3, y0.getColumnID() );
    assertEquals( 8, y0.getMarkerSize() );

    assertEquals( DataType.LONG, y1.getType() );
    assertEquals( 1, y1.getAxisID() );
    assertEquals( 2, y1.getColumnID() );
    assertEquals( 5, y1.getMarkerSize() );

    assertEquals( DataType.DOUBLE, y2.getType() );
    assertEquals( 2, y2.getAxisID() );
    assertEquals( 4, y2.getColumnID() );
    assertEquals( 8, y0.getMarkerSize() );
}

/**
 * Demonstrate that if we define both min and max values for the axis to be autoscaled, that
 * the configuration gets appropriately populated.
 */
@Test
@Ignore( "Not yet implemented" )
public void testExtactingDataSeriesConfigFromQualifiedProperties()
{
}


public static junit.framework.Test suite()
{
    return new JUnit4TestAdapter( DataSeriesConfigurationFactoryTest.class );
}

}
