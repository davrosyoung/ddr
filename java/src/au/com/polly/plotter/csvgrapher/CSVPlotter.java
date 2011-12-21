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

import au.com.polly.plotter.Axis;
import au.com.polly.plotter.AxisConfiguration;
import au.com.polly.plotter.DataSeries;
import au.com.polly.plotter.Grapher;
import au.com.polly.plotter.NumericAxis;
import au.com.polly.plotter.PlotData;
import au.com.polly.plotter.TimeBasedGrapher;
import au.com.polly.plotter.XYScatterGrapher;
import au.com.polly.util.DataType;
import au.com.polly.util.StringArmyKnife;
import au.com.polly.util.TimestampArmyKnife;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Read data from a csv file and plot graphs appropriately.
 * 
 */
public class CSVPlotter
{
final static int DEFAULT_GRAPH_WIDTH = 1600;
final static int DEFAULT_GRAPH_HEIGHT = 1000;
RootConfiguration config = null;
Map<Integer,DataSeries> data = null;
TimestampArmyKnife tsArmyKnife = null;



/**
 * Generate either time based or X-Y scatter plots from CSV files.
 * Dave wrote this, because he could not find any easily accessible tools to do the same
 * job for him, and he got totally frustrated with the plot features in M$ Excel.
 *
 */
public CSVPlotter()
{
    super();
}

public void run()
{
    Properties props = null;
    File dataFile = null;
    FileReader fileReader = null;
    BufferedReader reader = null;
    String propsPath;
    File propsFile;

    System.out.println( "System.getProperty( \"properties\" )=\"" + System.getProperty("properties") + "\"." );
    System.out.println( "System.getProperty( \"from\" )=\"" + System.getProperty("from") + "\"." );
    System.out.println( "System.getProperty( \"until\" )=\"" + System.getProperty("until") + "\"." );

    // if an argument has been supplied, see if it's the path to a properties file..
    // -------------------------------------------------------------------------------
    if ( ( propsPath = System.getProperty("properties") ) != null )
    {
        System.out.println( "main(): propsPath=\"" + propsPath + "\"" );
        propsFile = new File( propsPath );
        props = new Properties();
        try {
            props.load( new FileInputStream( propsFile  ) );
        } catch (IOException e) {
            props = null;
        }
    }

    this.config = readConfig( props );
    tsArmyKnife = new TimestampArmyKnife();
    tsArmyKnife.setTimeZone( config.getTimezone() );
    setTimestampArmyKnife( tsArmyKnife );

    try {
        dataFile = new File( config.getSourceDataPath() );
        fileReader = new FileReader( dataFile );
        reader = new BufferedReader( fileReader );
        data = readData( config, reader );
        generateGraphs( config, data );
    } catch ( java.io.IOException e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
}

/**
 *
 * @param props
 * @return
 */
public RootConfiguration readConfig( Properties props )
{
    RootConfigurationFactory factory = null;
    RootConfiguration result = null;
    factory = RootConfigurationFactory.getInstance();
    result = factory.extract( props, null );
    return result;
}

public static void main( String[] argv )
{
    CSVPlotter plotter = new CSVPlotter();
    plotter.run();
}

/**
 *
 * @param config
 * @param reader
 * @return
 * @throws java.io.IOException
 *
 * Examines the provided configuration, to determine what columns of data are to be extracted
 * from the supplied reader.
 *
 */
public Map<Integer,DataSeries> readData( RootConfiguration config, BufferedReader reader ) throws IOException
{
    Map<Integer,DataSeries> result = new HashMap<Integer,DataSeries>();
    String line;
    String[] bits;
    HashMap<Integer,DataType> columnTypeLUT = new HashMap<Integer, DataType>();
    DataSeriesConfiguration xSeriesConfig;
    DataSeriesConfiguration ySeriesConfig;
    List<DataSeriesConfiguration> multiYSeriesConfig;
    char[] commaSeparator = new char[] { ',' };

    // interrogate the supplied configuration to determine which columns have which
    // data types (timestamp/long/double).
    // ----------------------------------------------------------------------------------------
    for( GraphConfiguration graphConfig : config.getGraphConfig() )
    {
        if ( ( xSeriesConfig = graphConfig.getXSeriesConfiguration() ) != null )
        {
            columnTypeLUT.put( graphConfig.getXSeriesConfiguration().getColumnID(), graphConfig.getXSeriesConfiguration().getType() );
        }

        if ( ( ySeriesConfig = graphConfig.getYSeriesConfiguration() ) != null )
        {
            columnTypeLUT.put( graphConfig.getYSeriesConfiguration().getColumnID(), graphConfig.getYSeriesConfiguration().getType() );
        }

        if ( ( multiYSeriesConfig = graphConfig.getMultiYSeriesConfiguration() ) != null )
        {
            for( DataSeriesConfiguration seriesConfig : multiYSeriesConfig )
            {
                columnTypeLUT.put( seriesConfig.getColumnID(), seriesConfig.getType() );                
            }
        }
    }

    // now populate empty data series, of the appropriate type, for each
    // of the columns that we need to populate....
    // ---------------------------------------------------------------------
    for( Integer columnID : columnTypeLUT.keySet() )
    {
        switch( columnTypeLUT.get( columnID ) )
        {
            case LONG:
                result.put( columnID, new DataSeries<Long>() );
                break;

            case DOUBLE:
                result.put( columnID, new DataSeries<Double>() );
                break;

            case TIMESTAMP:
                result.put( columnID, new DataSeries<Long>() );
                break;
        }
    }

    // now let's read that data in.....
    // -------------------------------------------------------------------------------
    while( ( line = reader.readLine() ) != null )
    {
        // if all of the data in a given line is invalid, then we skip that line...
        // ---------------------------------------------------------------------------
        boolean invalidLine = true;
        bits = line.split( "," );
        for( Integer columnID : columnTypeLUT.keySet() )
        {
            DataSeries ds = result.get( columnID );
            long stamp = -1L;
            long lValue;
            double dValue;

            // if we're reading the initial column headings, and the worksheet contained merged cells,
            // it's possible that there won't be a fully populated bits[] array...
            // -------------------------------------------------------------------------------------
            if ( columnID > bits.length )
            {
                continue;
            }

            switch( columnTypeLUT.get( columnID ) )
            {
                case LONG:

                    try {
                        lValue = Long.parseLong(bits[columnID.intValue() - 1]);
                        ds.add( lValue );
                        invalidLine = false;
                    } catch (NumberFormatException e) {
                    }
                    break;

                case DOUBLE:
                    try {
                        dValue = Double.parseDouble(bits[columnID.intValue() - 1]);
                        ds.add( dValue );
                        invalidLine = false;
                    } catch (NumberFormatException e) {
                    }
                    break;

                case TIMESTAMP:
                    stamp = tsArmyKnife.parse( bits[ columnID.intValue() - 1 ] );
                    if ( stamp >= 0L )
                    {
                        invalidLine = false;
                        ds.add( stamp );
                    }
                    break;
            }
        }

        // this should just spit out any lines of column headings...
        // ----------------------------------------------------------
        if ( invalidLine )
        {
            System.out.println( "INVALID Line [" + line + "]\n" );
            System.out.flush();
        }

    }

    return result;
}

public void generateGraphs( RootConfiguration rootConfiguration, Map<Integer,DataSeries> data )
{
    int width;
    int height;
    String outputPath;
    Grapher grapher;
    PlotData[] dataPlots;
    PlotData<Number,Number> singleDataPlot;
    AxisConfiguration[] axesConfig;
    Axis[] axes;
    BufferedImage result = null;
    Axis<Long> queueAxis = new NumericAxis<Long>();
    AxisConfiguration queueAxisConfig = new AxisConfiguration( "Queue Size", null, Color.BLACK, 800 );
    Axis<Long> eventQueueAxis = new NumericAxis<Long>();
    AxisConfiguration eventQueueAxisConfig = new AxisConfiguration( "Event Queue Size", null, Color.RED, 800 );

    for( GraphConfiguration graphConfig : rootConfiguration.graphConfig )
    {
        // is it an x-y scatter plot?? if so, then;
        // a) ... the x-axis will not be a timestamp based axis
        // b) ... there will only be a single y-axis plot
        // -----------------------------------------------------
        if (
                ( graphConfig.getXSeriesConfiguration().getType() != DataType.TIMESTAMP )
             && ( graphConfig.getMultiYSeriesConfiguration() == null )
        )
        {
            DataSeries<Number> x = data.get( graphConfig.getXSeriesConfiguration().getColumnID() );
            DataSeries<Number> y = data.get( graphConfig.getYSeriesConfiguration().getColumnID() );
            singleDataPlot = new PlotData<Number,Number>( x, y );
            singleDataPlot.setMarkerSize( graphConfig.getYSeriesConfiguration().getMarkerSize() );
            
            grapher = new XYScatterGrapher<Number,Number>(
                            graphConfig.getTitle(),
                            graphConfig.getWidth(),
                            graphConfig.getHeight(),
                            singleDataPlot,
                            graphConfig.getAxisConfigurations().get( 0 ),   // x-axis
                            graphConfig.getAxisConfigurations().get( 1 )    // y-axis

                    );
        } else {
            // we only need to supply the y-axes
            // ------------------------------------
            dataPlots = new PlotData[ graphConfig.getMultiYSeriesConfiguration().size() ];
            axes = new Axis[ graphConfig.getMultiYSeriesConfiguration().size() ];
            axesConfig = new AxisConfiguration[ graphConfig.getMultiYSeriesConfiguration().size() ];
            Map<Integer,Boolean> axisAlreadySpecified = new HashMap<Integer,Boolean>();

            ArrayList<PlotData> dataPlotList = new ArrayList<PlotData>();
            int i = 0;
            for( DataSeriesConfiguration yConfig : graphConfig.getMultiYSeriesConfiguration() )
            {
               dataPlots[ i ] = new PlotData(
                                        data.get( graphConfig.getXSeriesConfiguration().getColumnID() ),
                                        data.get( yConfig.getColumnID() )
                                );
                dataPlots[ i ].setColour( yConfig.getColour() );
                dataPlots[ i ].setMarkerSize( yConfig.getMarkerSize() );

                if (
                        ( ! axisAlreadySpecified.containsKey( yConfig.getAxisID() ) )
                   ||   ( !axisAlreadySpecified.get( yConfig.getAxisID() ) )
                )
                {

                    axesConfig[ i ] = ( yConfig.getAxisID() >= 0 )
                                            ? graphConfig.getAxisConfigurations().get( yConfig.getAxisID() )
                                            : null;
                    axes[ i ] = new NumericAxis();
                    axisAlreadySpecified.put( yConfig.getAxisID(), true );
                } else {
                    axesConfig[ i ] = null;
                    axes[ i ] = null;
                }
                i++;
            }

            grapher = new TimeBasedGrapher(
                            graphConfig.getTitle(),
                            graphConfig.getWidth(),
                            graphConfig.getHeight(),
                            dataPlots,
                            axes,
                            axesConfig
                    );
            ((TimeBasedGrapher)grapher).setTimestampKnife( tsArmyKnife );

        }

        result = grapher.render();

        try {
            ImageIO.write(result, graphConfig.getImageTypeSuffix(), new File(graphConfig.getDestinationPath()));
        } catch (IOException e) {
            e.printStackTrace( System.err );
            System.err.flush();
        }


    }

    return;
}


public TimestampArmyKnife getTimestampArmyKnife()
{
    return tsArmyKnife;
}

public void setTimestampArmyKnife(TimestampArmyKnife tsArmyKnife)
{
    this.tsArmyKnife = tsArmyKnife;
}


}
