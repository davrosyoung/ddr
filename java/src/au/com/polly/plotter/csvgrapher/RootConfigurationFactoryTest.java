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
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 *
 * Battery of tests of the data series class.
 *
 */
public class RootConfigurationFactoryTest
{
Properties singleGraphProps;
Properties dualGraphProps;
TimeZone sydTZ = null;
TimeZone gmtTZ = null;
TimeZone nyTZ = null;



@Before
public void createProperties()
{
    sydTZ = TimeZone.getTimeZone("Australia/Sydney");
    gmtTZ = TimeZone.getTimeZone("GMT");
    nyTZ = TimeZone.getTimeZone("America/New_York");

    singleGraphProps = new Properties();
    singleGraphProps.put( "source_file", "logs/combined_report.csv" );
    singleGraphProps.put( "tz", "Australia/Sydney" );
    singleGraphProps.put( "graph[0].title", "volume over time" );
    singleGraphProps.put( "graph[0].file", "logs/combined_graph.png" );
    singleGraphProps.put( "graph[0].type", "png" );
    singleGraphProps.put( "graph[0].width", "1600" );
    singleGraphProps.put( "graph[0].height", "1200" );
    singleGraphProps.put( "graph[0].axis[0].label", "date/time" );
    singleGraphProps.put( "graph[0].axis[0].min", "auto" );
    singleGraphProps.put( "graph[0].axis[0].max", "auto" );
    singleGraphProps.put( "graph[0].axis[0].colour", "black" );
    singleGraphProps.put( "graph[0].axis[1].label", "send request volume" );
    singleGraphProps.put( "graph[0].axis[1].units", "messages" );
    singleGraphProps.put( "graph[0].axis[1].min", "auto" );
    singleGraphProps.put( "graph[0].axis[1].max", "auto" );
    singleGraphProps.put( "graph[0].axis[1].colour", "blue" );
    singleGraphProps.put( "graph[0].axis[2].label", "incoming queue" );
    singleGraphProps.put( "graph[0].axis[2].min", "auto" );
    singleGraphProps.put( "graph[0].axis[2].max", "auto" );
    singleGraphProps.put( "graph[0].axis[2].colour", "red" );
    singleGraphProps.put( "graph[0].x.type", "timestamp" );
    singleGraphProps.put( "graph[0].x.column", "1" );
    singleGraphProps.put( "graph[0].x.axis" ,"0" );
    singleGraphProps.put( "graph[0].y[0].type" ,"long" );
    singleGraphProps.put( "graph[0].y[0].column" ,"2" );
    singleGraphProps.put( "graph[0].y[0].axis", "1" );
    singleGraphProps.put( "graph[0].y[0].colour" ,"black" );
    singleGraphProps.put( "graph[0].y[1].type" ,"long" );
    singleGraphProps.put( "graph[0].y[1].column", "3" );
    singleGraphProps.put( "graph[0].y[1].axis" ,"2" );
    singleGraphProps.put( "graph[0].y[1].colour" ,"blue" );

    dualGraphProps = new Properties();
    dualGraphProps.put( "source_file", "logs/combined_report.csv" );
    dualGraphProps.put( "tz", "America/New_York" );
    dualGraphProps.put( "graph[0].title", "volume over time" );
    dualGraphProps.put( "graph[0].file", "logs/combined_graph.png" );
    dualGraphProps.put( "graph[0].type", "png" );
    dualGraphProps.put( "graph[0].width", "1600" );
    dualGraphProps.put( "graph[0].height", "1200" );
    dualGraphProps.put( "graph[0].axis[0].label", "date/time" );
    dualGraphProps.put( "graph[0].axis[0].min", "auto" );
    dualGraphProps.put( "graph[0].axis[0].max", "auto" );
    dualGraphProps.put( "graph[0].axis[0].colour", "black" );
    dualGraphProps.put( "graph[0].axis[1].label", "send request volume" );
    dualGraphProps.put( "graph[0].axis[1].units", "messages" );
    dualGraphProps.put( "graph[0].axis[1].min", "auto" );
    dualGraphProps.put( "graph[0].axis[1].max", "auto" );
    dualGraphProps.put( "graph[0].axis[1].colour", "blue" );
    dualGraphProps.put( "graph[0].axis[2].label", "incoming queue" );
    dualGraphProps.put( "graph[0].axis[2].min", "auto" );
    dualGraphProps.put( "graph[0].axis[2].max", "auto" );
    dualGraphProps.put( "graph[0].axis[2].colour", "red" );
    dualGraphProps.put( "graph[0].x.type", "timestamp" );
    dualGraphProps.put( "graph[0].x.column", "1" );
    dualGraphProps.put( "graph[0].x.axis" ,"0" );
    dualGraphProps.put( "graph[0].y[0].type" ,"long" );
    dualGraphProps.put( "graph[0].y[0].column" ,"2" );
    dualGraphProps.put( "graph[0].y[0].axis", "1" );
    dualGraphProps.put( "graph[0].y[0].colour" ,"black" );
    dualGraphProps.put( "graph[0].y[1].type" ,"long" );
    dualGraphProps.put( "graph[0].y[1].column", "3" );
    dualGraphProps.put( "graph[0].y[1].axis" ,"2" );
    dualGraphProps.put( "graph[0].y[1].colour" ,"blue" );

    dualGraphProps.put( "graph[1].title", "send reqest volume vs incoming queue size" );
    dualGraphProps.put( "graph[1].file", "logs/combined_graph02.png" );
    dualGraphProps.put( "graph[1].type", "png" );
    dualGraphProps.put( "graph[1].width", "1200" );
    dualGraphProps.put( "graph[1].height", "800" );
    dualGraphProps.put( "graph[1].axis[0].label", "send request volume" );
    dualGraphProps.put( "graph[1].axis[0].min", "auto" );
    dualGraphProps.put( "graph[1].axis[0].max", "auto" );
    dualGraphProps.put( "graph[1].axis[0].colour", "green" );
    dualGraphProps.put( "graph[1].axis[1].label", "incoming queue size" );
    dualGraphProps.put( "graph[1].axis[1].units", "messages" );
    dualGraphProps.put( "graph[1].axis[1].min", "40000" );
    dualGraphProps.put( "graph[1].axis[1].max", "60000" );
    dualGraphProps.put( "graph[1].axis[1].colour", "red" );
    dualGraphProps.put( "graph[1].x.type", "long" );
    dualGraphProps.put( "graph[1].x.column", "2" );
    dualGraphProps.put( "graph[1].x.axis" ,"0" );
    dualGraphProps.put( "graph[1].y.type" ,"long" );
    dualGraphProps.put( "graph[1].y.column" ,"3" );
    dualGraphProps.put( "graph[1].y.axis", "1" );
    dualGraphProps.put( "graph[1].y.colour" ,"black" );


}

@Test( expected=NullPointerException.class )
public void testCreatingRootConfigWithNullProperties()
{
    RootConfiguration config = null;
    RootConfigurationFactory factory = RootConfigurationFactory.getInstance();
    config = factory.extract( null, null );
}

@Test
public void testCreatingRootConfigWithEmptyProperties()
{
    RootConfiguration config = null;
    RootConfigurationFactory factory = RootConfigurationFactory.getInstance();
    config = factory.extract( new Properties(), null );
    assertNull( config );
}

@Test
public void testCreatingRootConfigWithSingleGraph()
{
    RootConfiguration config = null;
    RootConfigurationFactory factory = RootConfigurationFactory.getInstance();
    List<GraphConfiguration> graphConfigList;
    GraphConfiguration graphConfig;
    List<AxisConfiguration> axisConfigurationList;
    List<DataSeriesConfiguration> multiYSeriesConfigList;
    DataSeriesConfiguration xSeriesConfig;
    DataSeriesConfiguration ySeriesConfig;

    assertNotNull( factory );
    config = factory.extract( singleGraphProps, null );

    assertNotNull( config );
    assertEquals( "logs/combined_report.csv", config.getSourceDataPath() );
    assertNotNull( config.getTimezone() );
    assertEquals( sydTZ, config.getTimezone() );
    graphConfigList = config.getGraphConfig();
    assertNotNull( graphConfigList );
    assertEquals( 1, graphConfigList.size() );
    graphConfig = graphConfigList.get( 0 );

    assertNotNull( graphConfig );
    assertEquals( "volume over time", graphConfig.getTitle() );
    assertEquals( "logs/combined_graph.png", graphConfig.getDestinationPath() );
    assertEquals( "png", graphConfig.getImageTypeSuffix() );
    assertEquals( 1600, graphConfig.getWidth() );
    assertEquals( 1200, graphConfig.getHeight() );

    axisConfigurationList = graphConfig.getAxisConfigurations();
    assertNotNull( axisConfigurationList );
    assertEquals( 3, axisConfigurationList.size() );

    assertEquals( Color.BLACK, axisConfigurationList.get( 0 ).getColour() );
    assertTrue( axisConfigurationList.get( 0 ).isAutoScale() );
    assertNull( axisConfigurationList.get( 0 ).getUnits() );
    assertNull( axisConfigurationList.get( 0 ).getMin() );
    assertNull( axisConfigurationList.get( 0 ).getMax() );
    assertEquals( "date/time", axisConfigurationList.get( 0 ).getLabel() );

    assertEquals( Color.BLUE, axisConfigurationList.get( 1 ).getColour() );
    assertTrue( axisConfigurationList.get( 1 ).isAutoScale() );
    assertEquals( "send request volume", axisConfigurationList.get( 1 ).getLabel() );
    assertEquals( "messages", axisConfigurationList.get( 1 ).getUnits() );
    assertNull( axisConfigurationList.get( 1 ).getMin() );
    assertNull( axisConfigurationList.get( 1 ).getMax() );

    assertEquals( Color.RED, axisConfigurationList.get( 2 ).getColour() );
    assertTrue( axisConfigurationList.get( 2 ).isAutoScale() );
    assertEquals( "incoming queue", axisConfigurationList.get( 2 ).getLabel() );
    assertNull( axisConfigurationList.get( 2 ).getUnits() );
    assertNull( axisConfigurationList.get( 2 ).getMin() );
    assertNull( axisConfigurationList.get( 2 ).getMax() );

    // we should have a single x-axis and two y axes....
    // ---------------------------------------------------
    xSeriesConfig = graphConfig.getXSeriesConfiguration();
    assertNotNull( xSeriesConfig );
    ySeriesConfig = graphConfig.getYSeriesConfiguration();
    assertNull( ySeriesConfig );
    multiYSeriesConfigList = graphConfig.getMultiYSeriesConfiguration();
    assertNotNull( multiYSeriesConfigList );
    assertEquals( 2, multiYSeriesConfigList.size() );

    assertEquals( DataType.TIMESTAMP, xSeriesConfig.getType() );
    assertEquals( 1, xSeriesConfig.getColumnID() );
    assertEquals( 0, xSeriesConfig.getAxisID() );

    assertEquals( DataType.LONG, multiYSeriesConfigList.get( 0 ).getType() );
    assertEquals( 2, multiYSeriesConfigList.get( 0 ).getColumnID() );
    assertEquals( 1, multiYSeriesConfigList.get( 0 ).getAxisID() );
    assertEquals( Color.BLACK, multiYSeriesConfigList.get( 0 ).getColour() );

    assertEquals( DataType.LONG, multiYSeriesConfigList.get( 1 ).getType() );
    assertEquals( 3, multiYSeriesConfigList.get( 1 ).getColumnID() );
    assertEquals( 2, multiYSeriesConfigList.get( 1 ).getAxisID() );
    assertEquals( Color.BLUE, multiYSeriesConfigList.get( 1 ).getColour() );
}


@Test
public void testCreatingRootConfigWithTimeBasedAndScatterGraph()
{
    RootConfiguration config = null;
    RootConfigurationFactory factory = RootConfigurationFactory.getInstance();
    List<GraphConfiguration> graphConfigList;
    GraphConfiguration graphConfig;
    List<AxisConfiguration> axisConfigurationList;
    List<DataSeriesConfiguration> multiYSeriesConfigList;
    DataSeriesConfiguration xSeriesConfig;
    DataSeriesConfiguration ySeriesConfig;

    assertNotNull( factory );
    config = factory.extract( dualGraphProps, null );

    assertNotNull( config );

    assertEquals( "logs/combined_report.csv", config.getSourceDataPath() );
    assertNotNull( config.getTimezone() );
    assertEquals( nyTZ, config.getTimezone() );
    assertEquals( "logs/combined_graph.png", config.getGraphConfig().get( 0 ).getDestinationPath() );
    assertEquals( "volume over time", config.getGraphConfig().get( 0 ).getTitle() );
    assertEquals( "png", config.getGraphConfig().get( 0 ).getImageTypeSuffix() );
    assertEquals( 1600, config.getGraphConfig().get( 0 ).getWidth() );
    assertEquals( 1200, config.getGraphConfig().get( 0 ).getHeight() );

    assertNotNull( config.getGraphConfig() );
    assertEquals( 2, config.getGraphConfig().size() );
    assertNotNull( config.getGraphConfig().get( 0 ).getAxisConfigurations() );
    assertEquals( 3, config.getGraphConfig().get( 0 ).getAxisConfigurations().size() );

    assertNotNull( config.getGraphConfig().get( 0 ).getAxisConfigurations().get( 0 ) );
    assertEquals( "date/time", config.getGraphConfig().get( 0 ).getAxisConfigurations().get( 0 ).getLabel() );
    assertEquals( Color.BLACK, config.getGraphConfig().get( 0 ).getAxisConfigurations().get( 0 ).getColour() );
    assertTrue( config.getGraphConfig().get( 0 ).getAxisConfigurations().get( 0 ).isAutoScale() );

    assertNotNull( config.getGraphConfig().get( 0 ).getAxisConfigurations().get( 1 ) );
    assertEquals( "send request volume", config.getGraphConfig().get( 0 ).getAxisConfigurations().get( 1 ).getLabel() );
    assertEquals( Color.BLUE, config.getGraphConfig().get( 0 ).getAxisConfigurations().get( 1 ).getColour() );
    assertTrue( config.getGraphConfig().get( 0 ).getAxisConfigurations().get( 1 ).isAutoScale() );

    assertNotNull( config.getGraphConfig().get( 0 ).getAxisConfigurations().get( 2 ) );
    assertEquals( "incoming queue", config.getGraphConfig().get( 0 ).getAxisConfigurations().get( 2 ).getLabel() );
    assertEquals( Color.RED, config.getGraphConfig().get( 0 ).getAxisConfigurations().get( 2 ).getColour() );
    assertTrue( config.getGraphConfig().get( 0 ).getAxisConfigurations().get( 2 ).isAutoScale() );


    assertNotNull( config.getGraphConfig().get( 0 ).getXSeriesConfiguration() );
    assertEquals( DataType.TIMESTAMP, config.getGraphConfig().get( 0 ).getXSeriesConfiguration().getType() );
    assertEquals( 1, config.getGraphConfig().get( 0 ).getXSeriesConfiguration().getColumnID() );
    assertEquals( 0, config.getGraphConfig().get( 0 ).getXSeriesConfiguration().getAxisID() );
    assertEquals( Color.BLACK, config.getGraphConfig().get( 0 ).getXSeriesConfiguration().getColour() );

    assertNotNull( config.getGraphConfig().get( 0 ).getMultiYSeriesConfiguration() );

    assertEquals( 2, config.getGraphConfig().get( 0 ).getMultiYSeriesConfiguration().size() );

    assertEquals( DataType.LONG, config.getGraphConfig().get( 0 ).getMultiYSeriesConfiguration().get( 0 ).getType() );
    assertEquals( 2, config.getGraphConfig().get( 0 ).getMultiYSeriesConfiguration().get( 0 ).getColumnID() );
    assertEquals( 1, config.getGraphConfig().get( 0 ).getMultiYSeriesConfiguration().get( 0 ).getAxisID() );
    assertEquals( Color.BLACK, config.getGraphConfig().get( 0 ).getMultiYSeriesConfiguration().get( 0 ).getColour() );

    assertEquals( DataType.LONG, config.getGraphConfig().get( 0 ).getMultiYSeriesConfiguration().get( 1 ).getType() );
    assertEquals( 3, config.getGraphConfig().get( 0 ).getMultiYSeriesConfiguration().get( 1 ).getColumnID() );
    assertEquals( 2, config.getGraphConfig().get( 0 ).getMultiYSeriesConfiguration().get( 1 ).getAxisID() );
    assertEquals( Color.BLUE, config.getGraphConfig().get( 0 ).getMultiYSeriesConfiguration().get( 1 ).getColour() );


    assertEquals( "logs/combined_graph02.png", config.getGraphConfig().get( 1 ).getDestinationPath() );
    assertEquals( "send reqest volume vs incoming queue size", config.getGraphConfig().get( 1 ).getTitle() );
    assertEquals( "png", config.getGraphConfig().get( 1 ).getImageTypeSuffix() );
    assertEquals( 1200, config.getGraphConfig().get( 1 ).getWidth() );
    assertEquals( 800, config.getGraphConfig().get( 1 ).getHeight() );

    assertNotNull( config.getGraphConfig().get( 1 ).getAxisConfigurations() );
    assertEquals( 2, config.getGraphConfig().get( 1 ).getAxisConfigurations().size() );
    assertNotNull( config.getGraphConfig().get( 1 ).getAxisConfigurations().get( 0 ) );
    assertEquals( "send request volume", config.getGraphConfig().get( 1 ).getAxisConfigurations().get( 0 ).getLabel() );
    assertNull( config.getGraphConfig().get( 1 ).getAxisConfigurations().get( 0 ).getUnits() );
    assertTrue( config.getGraphConfig().get( 1 ).getAxisConfigurations().get( 0 ).isAutoScale() );
    assertEquals( Color.GREEN, config.getGraphConfig().get( 1 ).getAxisConfigurations().get( 0 ).getColour() );

    assertNotNull( config.getGraphConfig().get( 1 ).getAxisConfigurations().get( 1 ) );
    assertEquals( "incoming queue size", config.getGraphConfig().get( 1 ).getAxisConfigurations().get( 1 ).getLabel() );
    assertNotNull( config.getGraphConfig().get( 1 ).getAxisConfigurations().get( 1 ).getUnits() );
    assertEquals( "messages", config.getGraphConfig().get( 1 ).getAxisConfigurations().get( 1 ).getUnits() );
    assertFalse( config.getGraphConfig().get( 1 ).getAxisConfigurations().get( 1 ).isAutoScale() );
    assertEquals( 40000, config.getGraphConfig().get( 1 ).getAxisConfigurations().get( 1 ).getMin() );
    assertEquals( 60000, config.getGraphConfig().get( 1 ).getAxisConfigurations().get( 1 ).getMax() );
    assertEquals( Color.RED, config.getGraphConfig().get( 1 ).getAxisConfigurations().get( 1 ).getColour() );

    assertNotNull( config.getGraphConfig().get( 1 ).getXSeriesConfiguration() );
    assertEquals( DataType.LONG, config.getGraphConfig().get( 1 ).getXSeriesConfiguration().getType() );
    assertEquals( 0, config.getGraphConfig().get( 1 ).getXSeriesConfiguration().getAxisID() );
    assertEquals( 2, config.getGraphConfig().get( 1 ).getXSeriesConfiguration().getColumnID() );
    assertEquals( Color.BLACK, config.getGraphConfig().get( 1 ).getXSeriesConfiguration().getColour() );

    assertNotNull( config.getGraphConfig().get( 1 ).getYSeriesConfiguration() );
    assertEquals( DataType.LONG, config.getGraphConfig().get( 1 ).getYSeriesConfiguration().getType() );
    assertEquals( 1, config.getGraphConfig().get( 1 ).getYSeriesConfiguration().getAxisID() );
    assertEquals( 3, config.getGraphConfig().get( 1 ).getYSeriesConfiguration().getColumnID() );
    assertEquals( Color.BLACK, config.getGraphConfig().get( 1 ).getYSeriesConfiguration().getColour() );

    assertNull( config.getGraphConfig().get( 1 ).getMultiYSeriesConfiguration() );

}

public static junit.framework.Test suite()
{
    return new JUnit4TestAdapter( RootConfigurationFactoryTest.class );
}
}
