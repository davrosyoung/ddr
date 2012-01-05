/*
 * Copyright (c) 2011-2012 Polly Enterprises Pty Ltd and/or its affiliates.
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

import au.com.polly.plotter.csvgrapher.*;
import au.com.polly.util.TimestampArmyKnife;
import junit.framework.JUnit4TestAdapter;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;


/**
 *
 * run some tests to ensure that the csv plotter is correctly building up the data set, as specififed by
 * the configuration.
 *
 */
@RunWith(JUnit4.class)
public class CSVPlotterTest
{
private final static Logger logger = Logger.getLogger( CSVPlotterTest.class );
private final static double ACCEPTABLE_ERROR = 1E-8;
Properties fakeProps;
String fakeData;

@Before
public void buildFakeData()
{
    fakeData = "Departure,Dummy,Trip Duration,Average Speed,Temperature\n";
    fakeData += "15/MAR/07 08:36,blah blah,37,24.5,32.4\n";
    fakeData += "16/MAR/07 08:23,blah blah,39,22.7,31.7\n";
    fakeData += "17/MAR/07 08:49,blah blah,36,25.1,32.9\n";
    fakeData += "18/MAR/07 08:41,blah blah,36,24.7,32.6\n";            
}

@Before
public void buildFakeConfiguration()
{

    // first graph is journey time and average speed against time.
    // second graph is an X-Y scatter plot of journey time against temparature.
    // ----------------------------------------------------------------------------
    fakeProps = new Properties();
    fakeProps.put( "source_file", "logs/combined_report.csv" );
    fakeProps.put( "tz", "US/New_York" );
    fakeProps.put( "graph[0].title", "journey time to work" );
    fakeProps.put( "graph[0].axis[0].label", "date/time" );
    fakeProps.put( "graph[0].axis[1].label", "journey time" );
    fakeProps.put( "graph[0].axis[1].units", "minutes" );
    fakeProps.put( "graph[0].axis[1].colour", "blue" );
    fakeProps.put( "graph[0].axis[2].label", "average speed" );
    fakeProps.put( "graph[0].axis[2].units", "miles per hour" );
    fakeProps.put( "graph[0].axis[2].colour", "red" );

    fakeProps.put( "graph[0].x.type", "timestamp" );
    fakeProps.put( "graph[0].x.column", "A" );
    fakeProps.put( "graph[0].x.axis", "0" );
    fakeProps.put( "graph[0].y[0].type", "long" );
    fakeProps.put( "graph[0].y[0].column", "C" );
    fakeProps.put( "graph[0].y[0].axis", "1" );
    fakeProps.put( "graph[0].y[0].colour", "blue" );
    fakeProps.put( "graph[0].y[1].type", "double" );
    fakeProps.put( "graph[0].y[1].column", "D" );
    fakeProps.put( "graph[0].y[1].axis", "2" );
    fakeProps.put( "graph[0].y[1].colour", "red" );

    fakeProps.put( "graph[1].title", "temp vs journey time" );
    fakeProps.put( "graph[1].axis[0].label", "temperature" );
    fakeProps.put( "graph[1].axis[0].units", "C" );
    fakeProps.put( "graph[1].axis[1].label", "journey time" );
    fakeProps.put( "graph[1].axis[1].units", "minutes" );

    fakeProps.put( "graph[1].x.type", "double" );
    fakeProps.put( "graph[1].x.column", "E" );
    fakeProps.put( "graph[1].x.axis", "0" );
    fakeProps.put( "graph[1].y.type", "long" );
    fakeProps.put( "graph[1].y.column", "C" );
    fakeProps.put( "graph[1].y.axis", "1" );

}


@Test
public void testDefaultConstructor()
{
    au.com.polly.plotter.csvgrapher.CSVPlotter plotter = new au.com.polly.plotter.csvgrapher.CSVPlotter();
    assertNotNull( plotter );
}


/**
 * Do a small fake test run against the fake data and properties specified elsewhere. the resultant
 * data set is small enough for us to examine and check that everything has been placed into the
 * expected spots.
 *
 */
@Test
@Ignore( "Let's get this working when we have time!!" )
public void testReadData()
{
    RootConfigurationFactory factory = RootConfigurationFactory.getInstance();
    RootConfiguration config = factory.extract( fakeProps, null );
    StringReader sr = new StringReader( fakeData );
    BufferedReader reader = new BufferedReader( sr );
    Map<Integer, DataSeries> data = null;
    au.com.polly.plotter.csvgrapher.CSVPlotter plotter = new au.com.polly.plotter.csvgrapher.CSVPlotter();
    TimeZone tz = TimeZone.getTimeZone( fakeProps.getProperty( "tz"  ) );
    TimestampArmyKnife knife = new TimestampArmyKnife();
    knife.setTimeZone( tz );

    assertNotNull( plotter );
    assertNotNull( knife );
    assertNotNull( config );

    try {
        data = plotter.readData( config, reader );
    } catch (IOException e) {
        fail( "Ouch!!" );
    }

    // check that the overall shape of the data looks ok...
    // -----------------------------------------------------
    assertNotNull( data );
    assertNull( data.get( 0 ) );
    assertNotNull( data.get( 1 ) ); // date/time in column A
    assertNull( data.get( 2 ) );
    assertNotNull( data.get( 3 ) );  // journey time in column C
    assertNotNull( data.get( 4 ) );  // average speed in column D
    assertNotNull( data.get( 5 ) );  // temperature in column E


    // now examine each of the columns of data for correctness...
    // ----------------------------------------------------------
    assertEquals( 4, data.get( 1 ).size() );
    assertEquals( knife.parse( "15/MAR/2007 08:36:00" ) , data.get( 1 ).getData().get( 0 ) );
    assertEquals( knife.parse( "16/MAR/2007 08:23:00" ) , data.get( 1 ).getData().get( 1 ) );
    assertEquals( knife.parse( "17/MAR/2007 08:49:00" ) , data.get( 1 ).getData().get( 2 ) );
    assertEquals( knife.parse( "18/MAR/2007 08:41:00" ) , data.get( 1 ).getData().get( 3 ) );

    assertEquals( 4, data.get( 3 ).size() );
    assertEquals( 37L, data.get( 3 ).getData().get( 0 ) );
    assertEquals( 39L, data.get( 3 ).getData().get( 1 ) );
    assertEquals( 36L, data.get( 3 ).getData().get( 2 ) );
    assertEquals( 36L, data.get( 3 ).getData().get( 3 ) );

    assertEquals( 4, data.get( 4 ).size() );
    assertEquals( 24.5, data.get( 4 ).getData().get( 0 ) );
    assertEquals( 22.7, data.get( 4 ).getData().get( 1 ) );
    assertEquals( 25.1, data.get( 4 ).getData().get( 2 ) );
    assertEquals( 24.7, data.get( 4 ).getData().get( 3 ) );

    assertEquals( 4, data.get( 5 ).size() );
    assertEquals( 32.4, data.get( 5 ).getData().get( 0 ) );
    assertEquals( 31.7, data.get( 5 ).getData().get( 1 ) );
    assertEquals( 32.9, data.get( 5 ).getData().get( 2 ) );
    assertEquals( 32.6, data.get( 5 ).getData().get( 3 ) );
}

public static junit.framework.Test suite()
{
    return new JUnit4TestAdapter( CSVPlotterTest.class );
}
    
}
