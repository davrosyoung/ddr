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

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.Properties;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 *
 * Battery of tests of the data series class.
 *
 */
@RunWith(JUnit4.class)
public class RootConfigTest
{
    @Test(expected=NullPointerException.class)
    public void testCreatingRootConfigWithNullProperties()
    {
        Properties props = null;
        RootConfig config = new RootConfig();
        List<GraphConfig> graphConfigList;
        GraphConfig graphConfig;
        assertNotNull( config );
        config.populate( props );
    }

    @Test
    public void testCreatingRootConfigWithEmptyProperties()
    {
        Properties props = new Properties();
        RootConfig config = new RootConfig();
        List<GraphConfig> graphConfigList;
        assertNotNull( config );
        config.populate( props );

        assertNull( config.getSourceDataPath() );
        graphConfigList = config.getGraphConfig();
        assertNotNull( graphConfigList );
        assertEquals( 0, graphConfigList.size() );
    }

    @Test
    @Ignore( "need to get around to this one!!" )
    public void testCreatingRootConfigWithSingleGraph()
    {
        Properties props = new Properties();
        RootConfig config = new RootConfig();
        List<GraphConfig> graphConfigList;
        GraphConfig graphConfig;
        assertNotNull( config );
        props.put( "source_file", "logs/combined_report.csv" );
        props.put( "graph[0].title", "volume over time" );
        props.put( "graph[0].file", "logs/combined_graph.png" );
        props.put( "graph[0].type", "png" );
        props.put( "graph[0].width", "1600" );
        props.put( "graph[0].height", "1200" );
        props.put( "graph[0].axes[0].label", "date/time" );
        props.put( "graph[0].axes[0].min", "auto" );
        props.put( "graph[0].axes[0].max", "auto" );
        props.put( "graph[0].axes[0].colour", "black" );
        props.put( "graph[0].axes[1].label", "send request volume" );
        props.put( "graph[0].axes[1].units", "messages" );
        props.put( "graph[0].axes[1].min", "auto" );
        props.put( "graph[0].axes[1].max", "auto" );
        props.put( "graph[0].axes[1].colour", "blue" );
        props.put( "graph[0].axes[2].label", "incoming queue" );
        props.put( "graph[0].axes[2].min", "auto" );
        props.put( "graph[0].axes[2].max", "auto" );
        props.put( "graph[0].axes[2].colour", "red" );
        props.put( "graph[0].x.type", "timestamp" );
        props.put( "graph[0].x.column", "1" );
        props.put( "graph[0].x.axis" ,"0" );
        props.put( "graph[0].y[0].type" ,"long" );
        props.put( "graph[0].y[0].column" ,"2" );
        props.put( "graph[0].y[0].axis", "1" );
        props.put( "graph[0].y[0].colour" ,"black" );
        props.put( "graph[0].y[1].type" ,"long" );
        props.put( "graph[0].y[1].column", "3" );
        props.put( "graph[0].y[1].axis" ,"2" );
        props.put( "graph[0].y[1].colour" ,"blue" );

        config.populate( props );
        assertNotNull( config.getSourceDataPath() );
        assertEquals( "logs/combined_report.csv", config.getSourceDataPath() );
        graphConfigList = config.getGraphConfig();
        assertNotNull( graphConfigList );
        assertEquals( 1, graphConfigList.size() );

        graphConfig = graphConfigList.get( 0 );
        assertNotNull( graphConfig );
        assertEquals( "volume over time", graphConfig.getTitle() );
        assertEquals( "1600", graphConfig.getWidth() );
        assertEquals( "1200", graphConfig.getHeight() );
       

    }

    @Test
    @Ignore( "Not yet implemented" )
    public void testCreatingRootConfigWithTimeBasedAndScatterGraph()
    {
        fail( "Not yet implemented." );
    }

    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( RootConfigTest.class );
    }
}