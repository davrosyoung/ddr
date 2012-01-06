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

package au.com.polly.ddr.ui;

import au.com.polly.ddr.DataRateReducer;
import au.com.polly.ddr.GasWellDataSet;
import au.com.polly.ddr.WellMeasurementType;
import au.com.polly.plotter.Axis;
import au.com.polly.plotter.AxisConfiguration;
import au.com.polly.plotter.DataPlotter;
import au.com.polly.plotter.NumericAxis;
import au.com.polly.plotter.PlotData;
import au.com.polly.plotter.TimestampAxis;
import au.com.polly.util.DateArmyKnife;
import au.com.polly.util.DateRange;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Produces a graph of measurement value against time for one or two data sets. the
 * first data set is represented by crosses, the second by a dashed line. The different
 * well measurement types (oil, gas, water and condensate) are repesented by different
 * colours and can be displayed or make invisible.
 *
 */
public class DDRPlotGrapher extends JComponent implements PlotGrapher
{
private final static Logger logger = Logger.getLogger( DDRPlotGrapher.class );
private GasWellDataSet data;
private GasWellDataSet overlayData;
private Map<WellMeasurementType,Boolean> plotMeasurementType;
private static Map<WellMeasurementType,Color> measurementTypePlotColour;
private Date displayFrom = null;
private Date displayUntil = null;
private AxisConfiguration xAxisConfig = null;
private AxisConfiguration yAxisConfig = null;
private AxisConfiguration y2AxisConfig = null;
private Axis<Long> xAxis = null;
private Axis<Double> yAxis = null;
private Axis<Double> y2Axis = null;
private DataPlotter plotter = null; // does the heavy lifting, actually draws the graph!!
private boolean plotDataStale = true;
private Map<WellMeasurementType,PlotData<Long,Double>> reducedPlotDataMap = new HashMap<WellMeasurementType,PlotData<Long,Double>>();
private Map<WellMeasurementType,PlotData<Long,Double>> plotDataMap = new HashMap<WellMeasurementType,PlotData<Long,Double>>();


static {
    measurementTypePlotColour = new HashMap<WellMeasurementType,Color>();
    measurementTypePlotColour.put( WellMeasurementType.WATER_FLOW, Color.BLUE );
    measurementTypePlotColour.put( WellMeasurementType.GAS_FLOW, Color.YELLOW );
    measurementTypePlotColour.put( WellMeasurementType.CONDENSATE_FLOW, Color.GREEN );
    measurementTypePlotColour.put( WellMeasurementType.OIL_FLOW, Color.RED );
}


public DDRPlotGrapher( GasWellDataSet data, GasWellDataSet overlayData, int width, int height ) throws IllegalArgumentException
{
    loadData( data );
    if ( overlayData != null )
    {
        loadOverlayData( overlayData );
    }
    setPreferredSize( new Dimension( 1400, 600 ));
    setVisible( true );
    plotMeasurementType = new HashMap<WellMeasurementType,Boolean>();
    plotter = new DataPlotter( width, height );
}

@Override
public void loadData( GasWellDataSet dataSet )
{
    if ( dataSet != null )
    {
        if ( ! dataSet.equals( this.data ) )
        {
            this.data = dataSet;
            this.plotDataStale = true;
        }
    }
}

@Override
public void loadOverlayData( GasWellDataSet overlayDataSet )
{
    if ( overlayDataSet != null )
    {
        if ( ! overlayDataSet.equals( this.overlayData ) )
        {
            this.overlayData = overlayDataSet;
            this.plotDataStale = true;
        }
    }
}

public void paintComponent( Graphics gfx )
{
    logger.debug( "about to invoke render with width=" + getWidth() + ", height=" + getHeight() + ", plotDataStale=" + plotDataStale );
    if ( plotDataStale )
    {
        determinePlotData();
    }
    render((Graphics2D) gfx, this.getWidth(), this.getHeight());
}

public GasWellDataSet getData()
{
    return this.data;
}

/**
 * If the original data or the overlay (reduced) data is modified, then this method needs to
 * be invoked in order to calculate the actual data to be plotted!!
 */
protected void determinePlotData()
{

    Date graphFrom;
    Date graphUntil;
    DateRange plotDateRange = null;

    // determine the date range that we are going to be displaying......
    // we want to consider;
    // - range of dates in the original data set.
    // - range of dates in the overlay data set.
    // - any restriction in dates to be displayed.
    // -----------------------------------------------------------------
    if ( displayFrom != null )
    {
        graphFrom = this.displayFrom;
    } else {
        graphFrom = ( data != null ) ? data.from() : null;
        if ( ( overlayData != null ) && ( ( graphFrom == null ) || ( overlayData.from().before( graphFrom ) ) ) )
        {
            graphFrom = overlayData.from();
        }
    }

    if ( displayUntil != null )
    {
        graphUntil = this.displayUntil;
    } else {
        graphUntil = ( data != null ) ? data.until() : null;
        if ( ( overlayData != null ) && ( ( graphUntil == null ) || ( overlayData.until().after( graphUntil ) ) ) )
        {
            graphUntil = overlayData.until();
        }
    }
    
    this.xAxis = new TimestampAxis<Long>();
    this.yAxis = new NumericAxis<Double>();
    this.y2Axis = new NumericAxis<Double>();

    this.xAxisConfig = new AxisConfiguration( "Date/Time", null, Color.WHITE,  Color.GRAY, 600 );
    this.yAxisConfig = new AxisConfiguration( "Oil & Water flow", null, Color.WHITE, Color.GRAY,  600 );
    this.y2AxisConfig = new AxisConfiguration( "Gas Flow", "bbls/day", Color.YELLOW, 600 );

    if ( ( graphFrom != null ) && ( graphUntil != null ) )
    {
        plotDateRange = new DateRange( graphFrom, graphUntil );
        logger.debug( "Set plotDateRange to " + plotDateRange );
    
        for( WellMeasurementType wmt : WellMeasurementType.values() )
        {
            boolean isGas = ( wmt == WellMeasurementType.GAS_FLOW );
            
            if ( data.containsMeasurement( wmt ) )
            {
                PlotData<Long,Double> pd = data.getPlotData( wmt, plotDateRange );
                
                logger.debug( "Just got plotData for wmt=" + wmt + ", dateRange=" + plotDateRange + ", pd.size()=" + ( ( pd != null ) ? Integer.toString( pd.size() ) : "NULL" ) );
                
                pd.setColour( measurementTypePlotColour.get( wmt ) );
                pd.setMarkerSize( 4 );
                pd.setMarkerStyle( PlotData.PointMarkerStyle.CIRCLE );
                plotDataMap.put( wmt, pd );
                xAxis.autoScale( pd.getXAxisData() );
    
                // gas flow is measured differently (different units) and is
                // plotted against the 2nd y axis.
                // -----------------------------------------------------------
                if ( isGas )
                {
                    y2Axis.autoScale(pd.getYAxisData());
                } else {
                    yAxis.autoScale( pd.getYAxisData() );
                }
            }
            
            setDisplayMeasurementType( wmt, data.containsMeasurement( wmt ), false );
        }
    
        if ( overlayData != null )
        {
            for( WellMeasurementType wmt : WellMeasurementType.values() )
            {
                if ( overlayData.containsMeasurement( wmt ) )
                {
                    PlotData<Long,Double> reducedPlotData = overlayData.getPlotData( wmt, plotDateRange );
                    reducedPlotData.setColour( measurementTypePlotColour.get( wmt ) );
                    reducedPlotData.setMarkerSize( 8 );
                    reducedPlotData.setMarkerStyle( PlotData.PointMarkerStyle.SQUARE );
                    reducedPlotData.setLineStyles(EnumSet.of(PlotData.LineStyle.STEPPED, PlotData.LineStyle.FINE_DASHED, PlotData.LineStyle.THICK));
                    reducedPlotDataMap.put( wmt, reducedPlotData );
                }
            }
        }
        
        plotDataStale = false;
        
    }  else {
        logger.error( "NO DATA TO PLOT!!" );
    }
}



public void render( Graphics2D gfx, int width, int height )
{
    if ( plotDataStale )
    {
        determinePlotData();
    }

    plotter.setWidth( width );
    plotter.setHeight( height );
    plotter.clearPlotData();

    for( WellMeasurementType wmt : WellMeasurementType.values() )
    {
        boolean isGas = ( wmt == WellMeasurementType.GAS_FLOW );

        if ( plotDataMap.containsKey(wmt) && getDisplayMeasurementType( wmt ))
        {
            plotter.addPlotData( plotDataMap.get( wmt ), xAxis, isGas ? y2Axis : yAxis, xAxisConfig, isGas ? y2AxisConfig : yAxisConfig);
        }

        if ( ( reducedPlotDataMap != null ) && ( reducedPlotDataMap.containsKey( wmt ) ) && getDisplayMeasurementType( wmt ))
        {
            plotter.addPlotData( reducedPlotDataMap.get( wmt ), xAxis, isGas ? y2Axis : yAxis, xAxisConfig, isGas ? y2AxisConfig : yAxisConfig );
        }

    }


    plotter.paint( gfx );
}

@Override
public boolean getDisplayMeasurementType(WellMeasurementType wmt)
{
    return plotMeasurementType.containsKey( wmt ) && plotMeasurementType.get( wmt );
}

/**
 *
 * @param wmt the measurement type
 * @param display whether or not to display the specified measurement type.
 *
 * Enables us to select which measurement types are to be displayed or not.
 */
@Override
public void setDisplayMeasurementType(WellMeasurementType wmt, Boolean display)
{
    setDisplayMeasurementType( wmt, display, true );
}

public void setDisplayMeasurementType(WellMeasurementType wmt, Boolean display, boolean doRepaint )
{
    plotMeasurementType.put( wmt, display );
    if ( doRepaint )
    {
        repaint();
    }
}

@Override
public GasWellDataSet getOverlayData()
{
    return overlayData;
}

@Override
public void setDisplayDateRange(Date from, Date until)
{
    if ( ! DateArmyKnife.areDatesEqual( from, this.displayFrom ) )
    {
        this.displayFrom = from;
        plotDataStale = true;
    }
    
    if ( !DateArmyKnife.areDatesEqual( until, this.displayUntil ) )
    {
        this.displayUntil = until;
        plotDataStale = true;
    }
    repaint();
}

@Override
public void reduce(DataRateReducer reducer)
{
    this.overlayData = reducer.reduce( data );
    plotDataStale = true;
    repaint();
}


}
