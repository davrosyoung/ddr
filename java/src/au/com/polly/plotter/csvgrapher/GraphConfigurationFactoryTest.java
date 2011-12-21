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

import au.com.polly.plotter.AxisConfiguration;
import au.com.polly.util.DataType;
import junit.framework.JUnit4TestAdapter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 *
 * Battery of tests of the data series class.
 *
 */
public class GraphConfigurationFactoryTest
{
Properties singleYAxisAbsoluteProps;
Properties singleYAxisRelativeProps;
Properties dualYAxisAbsoluteProps;
Properties dualYAxisRelativeProps;

@Before
public void createProperties()
{
    singleYAxisAbsoluteProps = new Properties();
    singleYAxisRelativeProps = new Properties();
    dualYAxisAbsoluteProps = new Properties();
    dualYAxisRelativeProps = new Properties();

    singleYAxisAbsoluteProps.put( "graph[0].title", "race against time" );
    singleYAxisAbsoluteProps.put( "graph[0].file", "logs/combined_graph_02.png" );
    singleYAxisAbsoluteProps.put( "graph[0].width", "600" );
    singleYAxisAbsoluteProps.put( "graph[0].height", "200" );
    singleYAxisAbsoluteProps.put( "graph[0].axis[2].label", "outgoing queue" );
    singleYAxisAbsoluteProps.put( "graph[0].axis[2].colour", "yellow" );
    singleYAxisAbsoluteProps.put( "graph[0].x.type", "timestamp" );
    singleYAxisAbsoluteProps.put( "graph[0].x.column", "4" );
    singleYAxisAbsoluteProps.put( "graph[0].x.axis" ,"3" );
    singleYAxisAbsoluteProps.put( "graph[0].y[0].type" ,"double" );
    singleYAxisAbsoluteProps.put( "graph[0].y[0].column" ,"3" );
    singleYAxisAbsoluteProps.put( "graph[0].y[0].axis", "1" );
    singleYAxisAbsoluteProps.put( "graph[0].y[0].colour" ,"red" );
    singleYAxisAbsoluteProps.put( "graph[0].y[1].type" ,"double" );
    singleYAxisAbsoluteProps.put( "graph[0].y[1].column", "2" );
    singleYAxisAbsoluteProps.put( "graph[0].y[1].axis" ,"1" );
    singleYAxisAbsoluteProps.put( "graph[0].y[1].colour" ,"green" );
    singleYAxisAbsoluteProps.put( "graph[1].title", "volume over time" );
    singleYAxisAbsoluteProps.put( "graph[1].image_type", "jpeg" );
    singleYAxisAbsoluteProps.put( "graph[1].file", "logs/combined_graph.png" );
    singleYAxisAbsoluteProps.put( "graph[1].width", "1600" );
    singleYAxisAbsoluteProps.put( "graph[1].height", "1200" );
    singleYAxisAbsoluteProps.put( "graph[1].axis[0].label", "date/time" );
    singleYAxisAbsoluteProps.put( "graph[1].axis[0].min", "auto" );
    singleYAxisAbsoluteProps.put( "graph[1].axis[0].max", "auto" );
    singleYAxisAbsoluteProps.put( "graph[1].axis[0].colour", "black" );
    singleYAxisAbsoluteProps.put( "graph[1].axis[1].label", "send request volume" );
    singleYAxisAbsoluteProps.put( "graph[1].axis[1].units", "messages" );
    singleYAxisAbsoluteProps.put( "graph[1].axis[1].min", "auto" );
    singleYAxisAbsoluteProps.put( "graph[1].axis[1].max", "auto" );
    singleYAxisAbsoluteProps.put( "graph[1].axis[1].colour", "blue" );
    singleYAxisAbsoluteProps.put( "graph[1].axis[2].label", "incoming queue" );
    singleYAxisAbsoluteProps.put( "graph[1].axis[2].min", "auto" );
    singleYAxisAbsoluteProps.put( "graph[1].axis[2].max", "auto" );
    singleYAxisAbsoluteProps.put( "graph[1].axis[2].colour", "red" );
    singleYAxisAbsoluteProps.put( "graph[1].x.type", "timestamp" );
    singleYAxisAbsoluteProps.put( "graph[1].x.column", "1" );
    singleYAxisAbsoluteProps.put( "graph[1].x.axis" ,"0" );
    singleYAxisAbsoluteProps.put( "graph[1].y[0].type" ,"long" );
    singleYAxisAbsoluteProps.put( "graph[1].y[0].column" ,"2" );
    singleYAxisAbsoluteProps.put( "graph[1].y[0].axis", "1" );
    singleYAxisAbsoluteProps.put( "graph[1].y[0].colour" ,"black" );
    singleYAxisAbsoluteProps.put( "graph[1].y[1].type" ,"long" );
    singleYAxisAbsoluteProps.put( "graph[1].y[1].column", "3" );
    singleYAxisAbsoluteProps.put( "graph[1].y[1].axis" ,"2" );
    singleYAxisAbsoluteProps.put( "graph[1].y[1].colour" ,"blue" );
    singleYAxisAbsoluteProps.put( "graph[2].title", "race against time" );
    singleYAxisAbsoluteProps.put( "graph[2].file", "logs/combined_graph_02.png" );
    singleYAxisAbsoluteProps.put( "graph[2].width", "600" );
    singleYAxisAbsoluteProps.put( "graph[2].height", "200" );
    singleYAxisAbsoluteProps.put( "graph[2].axis[2].label", "outgoing queue" );
    singleYAxisAbsoluteProps.put( "graph[2].axis[2].colour", "yellow" );
    singleYAxisAbsoluteProps.put( "graph[2].x.type", "timestamp" );
    singleYAxisAbsoluteProps.put( "graph[2].x.column", "4" );
    singleYAxisAbsoluteProps.put( "graph[2].x.axis" ,"3" );
    singleYAxisAbsoluteProps.put( "graph[2].y[0].type" ,"double" );
    singleYAxisAbsoluteProps.put( "graph[2].y[0].column" ,"3" );
    singleYAxisAbsoluteProps.put( "graph[2].y[0].axis", "1" );
    singleYAxisAbsoluteProps.put( "graph[2].y[0].colour" ,"red" );
    singleYAxisAbsoluteProps.put( "graph[2].y[1].type" ,"double" );
    singleYAxisAbsoluteProps.put( "graph[2].y[1].column", "2" );
    singleYAxisAbsoluteProps.put( "graph[2].y[1].axis" ,"1" );
    singleYAxisAbsoluteProps.put( "graph[2].y[1].colour" ,"green" );

    singleYAxisRelativeProps.put( "title", "volume over time" );
    singleYAxisRelativeProps.put( "file", "logs/combined_graph02.png" );
    singleYAxisRelativeProps.put( "width", "1600" );
    singleYAxisRelativeProps.put( "height", "1200" );
    singleYAxisRelativeProps.put( "axis[0].label", "date/time" );
    singleYAxisRelativeProps.put( "axis[0].min", "auto" );
    singleYAxisRelativeProps.put( "axis[0].max", "auto" );
    singleYAxisRelativeProps.put( "axis[0].colour", "black" );
    singleYAxisRelativeProps.put( "axis[1].label", "send request volume" );
    singleYAxisRelativeProps.put( "axis[1].units", "messages" );
    singleYAxisRelativeProps.put( "axis[1].min", "auto" );
    singleYAxisRelativeProps.put( "axis[1].max", "auto" );
    singleYAxisRelativeProps.put( "axis[1].colour", "blue" );
    singleYAxisRelativeProps.put( "axis[2].label", "incoming queue" );
    singleYAxisRelativeProps.put( "axis[2].min", "auto" );
    singleYAxisRelativeProps.put( "axis[2].max", "auto" );
    singleYAxisRelativeProps.put( "axis[2].colour", "red" );
    singleYAxisRelativeProps.put( "x.type", "timestamp" );
    singleYAxisRelativeProps.put( "x.column", "1" );
    singleYAxisRelativeProps.put( "x.axis" ,"0" );
    singleYAxisRelativeProps.put( "y[0].type" ,"long" );
    singleYAxisRelativeProps.put( "y[0].column" ,"2" );
    singleYAxisRelativeProps.put( "y[0].axis", "1" );
    singleYAxisRelativeProps.put( "y[0].colour" ,"black" );
    singleYAxisRelativeProps.put( "y[1].type" ,"long" );
    singleYAxisRelativeProps.put( "y[1].column", "3" );
    singleYAxisRelativeProps.put( "y[1].axis" ,"2" );
    singleYAxisRelativeProps.put( "y[1].colour" ,"blue" );

    dualYAxisAbsoluteProps.put( "source_file", "logs/combined_report.csv" );
    dualYAxisAbsoluteProps.put( "graph[0].title", "volume over time" );
    dualYAxisAbsoluteProps.put( "graph[0].file", "logs/combined_graph.png" );
    dualYAxisAbsoluteProps.put( "graph[0].width", "1600" );
    dualYAxisAbsoluteProps.put( "graph[0].height", "1200" );
    dualYAxisAbsoluteProps.put( "graph[0].axis[0].label", "date/time" );
    dualYAxisAbsoluteProps.put( "graph[0].axis[0].min", "auto" );
    dualYAxisAbsoluteProps.put( "graph[0].axis[0].max", "auto" );
    dualYAxisAbsoluteProps.put( "graph[0].axis[0].colour", "black" );
    dualYAxisAbsoluteProps.put( "graph[0].axis[1].label", "send request volume" );
    dualYAxisAbsoluteProps.put( "graph[0].axis[1].units", "messages" );
    dualYAxisAbsoluteProps.put( "graph[0].axis[1].min", "auto" );
    dualYAxisAbsoluteProps.put( "graph[0].axis[1].max", "auto" );
    dualYAxisAbsoluteProps.put( "graph[0].axis[1].colour", "blue" );
    dualYAxisAbsoluteProps.put( "graph[0].axis[2].label", "incoming queue" );
    dualYAxisAbsoluteProps.put( "graph[0].axis[2].min", "auto" );
    dualYAxisAbsoluteProps.put( "graph[0].axis[2].max", "auto" );
    dualYAxisAbsoluteProps.put( "graph[0].axis[2].colour", "red" );
    dualYAxisAbsoluteProps.put( "graph[0].x.type", "timestamp" );
    dualYAxisAbsoluteProps.put( "graph[0].x.column", "1" );
    dualYAxisAbsoluteProps.put( "graph[0].x.axis" ,"0" );
    dualYAxisAbsoluteProps.put( "graph[0].y[0].type" ,"long" );
    dualYAxisAbsoluteProps.put( "graph[0].y[0].column" ,"2" );
    dualYAxisAbsoluteProps.put( "graph[0].y[0].axis", "1" );
    dualYAxisAbsoluteProps.put( "graph[0].y[0].colour" ,"black" );
    dualYAxisAbsoluteProps.put( "graph[0].y[1].type" ,"long" );
    dualYAxisAbsoluteProps.put( "graph[0].y[1].column", "3" );
    dualYAxisAbsoluteProps.put( "graph[0].y[1].axis" ,"2" );
    dualYAxisAbsoluteProps.put( "graph[0].y[1].colour" ,"blue" );

    dualYAxisRelativeProps.put( "title", "volume over time" );
    dualYAxisRelativeProps.put( "file", "logs/combined_graph.png" );
    dualYAxisRelativeProps.put( "width", "1600" );
    dualYAxisRelativeProps.put( "height", "1200" );
    dualYAxisRelativeProps.put( "axis[0].label", "date/time" );
    dualYAxisRelativeProps.put( "axis[0].min", "auto" );
    dualYAxisRelativeProps.put( "axis[0].max", "auto" );
    dualYAxisRelativeProps.put( "axis[0].colour", "black" );
    dualYAxisRelativeProps.put( "axis[1].label", "send request volume" );
    dualYAxisRelativeProps.put( "axis[1].units", "messages" );
    dualYAxisRelativeProps.put( "axis[1].min", "auto" );
    dualYAxisRelativeProps.put( "axis[1].max", "auto" );
    dualYAxisRelativeProps.put( "axis[1].colour", "blue" );
    dualYAxisRelativeProps.put( "axis[2].label", "incoming queue" );
    dualYAxisRelativeProps.put( "axis[2].min", "auto" );
    dualYAxisRelativeProps.put( "axis[2].max", "auto" );
    dualYAxisRelativeProps.put( "axis[2].colour", "red" );
    dualYAxisRelativeProps.put( "x.type", "timestamp" );
    dualYAxisRelativeProps.put( "x.column", "1" );
    dualYAxisRelativeProps.put( "x.axis" ,"0" );
    dualYAxisRelativeProps.put( "y[0].type" ,"long" );
    dualYAxisRelativeProps.put( "y[0].column" ,"2" );
    dualYAxisRelativeProps.put( "y[0].axis", "1" );
    dualYAxisRelativeProps.put( "y[0].colour" ,"black" );
    dualYAxisRelativeProps.put( "y[1].type" ,"long" );
    dualYAxisRelativeProps.put( "y[1].column", "3" );
    dualYAxisRelativeProps.put( "y[1].axis" ,"2" );
    dualYAxisRelativeProps.put( "y[1].colour" ,"blue" );

}

@Test( expected = NullPointerException.class )
public void testCreatingGraphConfigWithNullProperties()
{
    GraphConfigurationFactory factory = GraphConfigurationFactory.getInstance();
    GraphConfiguration config = factory.extract( null, null );
}

@Test
@Ignore( "Not yet implemented" )
public void testCreatingGraphConfigWithEmptyProperties()
{
    Properties props = new Properties();
    GraphConfigurationFactory factory = GraphConfigurationFactory.getInstance();
    GraphConfiguration config = factory.extract( props, null );
    Assert.assertNull(config);
}

@Test
@Ignore( "Not yet implemented" )
public void testCreatingGraphConfigWithMismatchedProperties()
{
    GraphConfigurationFactory factory = GraphConfigurationFactory.getInstance();
    GraphConfiguration config = factory.extract( singleYAxisAbsoluteProps, "graph[4]" );
    Assert.assertNull(config);
}

@Test
public void testCreatingGraphConfigWithQualifiedProperties()
{
    GraphConfigurationFactory factory = GraphConfigurationFactory.getInstance();
    GraphConfiguration config = factory.extract( singleYAxisAbsoluteProps, "graph[1]" );
    Assert.assertNotNull(config);

    assertEquals( "volume over time", config.getTitle() );
    assertEquals( "logs/combined_graph.png", config.getDestinationPath() );
    assertEquals( "jpeg", config.getImageTypeSuffix() );
    assertEquals( 1600, config.getWidth() );
    assertEquals( 1200, config.getHeight() );

    List<AxisConfiguration> axisConfigs = config.getAxisConfigurations();
    Assert.assertNotNull(axisConfigs);
    Assert.assertEquals(3, axisConfigs.size());

    DataSeriesConfiguration xSeriesConfig = config.getXSeriesConfiguration();
    DataSeriesConfiguration ySeriesConfig = config.getYSeriesConfiguration();
    List<DataSeriesConfiguration> multiYSeriesConfig = config.getMultiYSeriesConfiguration();

    assertNotNull( xSeriesConfig );
    assertNull( ySeriesConfig );
    assertNotNull( multiYSeriesConfig );

    assertEquals( DataType.TIMESTAMP, xSeriesConfig.getType() );
    assertEquals( 1, xSeriesConfig.getColumnID() );
    assertEquals( 0, xSeriesConfig.getAxisID() );
    assertEquals( Color.BLACK, xSeriesConfig.getColour() );

    assertEquals( 2, multiYSeriesConfig.size() );
    assertEquals( DataType.LONG, multiYSeriesConfig.get( 0 ).getType() );
    assertEquals( 2, multiYSeriesConfig.get( 0 ).getColumnID() );
    assertEquals( 1, multiYSeriesConfig.get( 0 ).getAxisID() );
    assertEquals( Color.BLACK, multiYSeriesConfig.get( 0 ).getColour() );

    assertEquals( DataType.LONG, multiYSeriesConfig.get( 1 ).getType() );
    assertEquals( 3, multiYSeriesConfig.get( 1 ).getColumnID() );
    assertEquals( 2, multiYSeriesConfig.get( 1 ).getAxisID() );
    assertEquals( Color.BLUE, multiYSeriesConfig.get( 1 ).getColour() );
}

@Test
public void testCreatingGraphConfigWithRelativeProperties()
{
    Properties props = new Properties();
    GraphConfigurationFactory factory = GraphConfigurationFactory.getInstance();
    GraphConfiguration config = factory.extract( singleYAxisRelativeProps, null );
    Assert.assertNotNull(config);

    assertEquals( "volume over time", config.getTitle() );
    assertEquals( "logs/combined_graph02.png" , config.getDestinationPath() );
    assertEquals( "png" , config.getImageTypeSuffix() );
    assertEquals( 1600, config.getWidth() );
    assertEquals( 1200, config.getHeight() );

    List<AxisConfiguration> axisConfigs = config.getAxisConfigurations();
    Assert.assertNotNull(axisConfigs);
    Assert.assertEquals(3, axisConfigs.size());

    DataSeriesConfiguration xSeriesConfig = config.getXSeriesConfiguration();
    DataSeriesConfiguration ySeriesConfig = config.getYSeriesConfiguration();
    List<DataSeriesConfiguration> multiYSeriesConfig = config.getMultiYSeriesConfiguration();

    assertNotNull( xSeriesConfig );
    assertNull( ySeriesConfig );
    assertNotNull( multiYSeriesConfig );

    assertEquals( DataType.TIMESTAMP, xSeriesConfig.getType() );
    assertEquals( 1, xSeriesConfig.getColumnID() );
    assertEquals( 0, xSeriesConfig.getAxisID() );
    assertEquals( Color.BLACK, xSeriesConfig.getColour() );

    assertEquals( 2, multiYSeriesConfig.size() );
    assertEquals( DataType.LONG, multiYSeriesConfig.get( 0 ).getType() );
    assertEquals( 2, multiYSeriesConfig.get( 0 ).getColumnID() );
    assertEquals( 1, multiYSeriesConfig.get( 0 ).getAxisID() );
    assertEquals( Color.BLACK, multiYSeriesConfig.get( 0 ).getColour() );

    assertEquals( DataType.LONG, multiYSeriesConfig.get( 1 ).getType() );
    assertEquals( 3, multiYSeriesConfig.get( 1 ).getColumnID() );
    assertEquals( 2, multiYSeriesConfig.get( 1 ).getAxisID() );
    assertEquals( Color.BLUE, multiYSeriesConfig.get( 1 ).getColour() );
}

@Test
public void testExtractingGraphConfigWithDualAxesAndQualifiedProperties()
{
    GraphConfigurationFactory factory = GraphConfigurationFactory.getInstance();
    GraphConfiguration config = factory.extract( dualYAxisAbsoluteProps, "graph[0]" );
    Assert.assertNotNull(config);

    Assert.assertEquals("volume over time", config.getTitle());

    List<AxisConfiguration> axisConfigs = config.getAxisConfigurations();
    Assert.assertNotNull(axisConfigs);
    Assert.assertEquals(3, axisConfigs.size());

    AxisConfiguration axisConfig = axisConfigs.get( 0 );
    assertNotNull( axisConfig );
    assertEquals( "date/time", axisConfig.getLabel() );
    assertEquals( Color.BLACK, axisConfig.getColour() );
    assertEquals( null, axisConfig.getMin() );
    assertEquals( null, axisConfig.getMax() );

    axisConfig = axisConfigs.get( 1 );
    assertNotNull( axisConfig );
    assertEquals( "send request volume", axisConfig.getLabel() );
    assertEquals( Color.BLUE, axisConfig.getColour() );
    assertEquals( null, axisConfig.getMin() );
    assertEquals( null, axisConfig.getMax() );

    axisConfig = axisConfigs.get( 2 );
    assertNotNull( axisConfig );
    assertEquals( "incoming queue", axisConfig.getLabel() );
    assertEquals( Color.RED, axisConfig.getColour() );
    assertEquals( null, axisConfig.getMin() );
    assertEquals( null, axisConfig.getMax() );
}


public static junit.framework.Test suite()
{
    return new JUnit4TestAdapter( GraphConfigurationFactoryTest.class );
}
}
