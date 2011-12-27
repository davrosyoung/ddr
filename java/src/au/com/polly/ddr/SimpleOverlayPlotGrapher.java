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

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Produces a graph of measurement value against time for one or two data sets. the
 * first data set is represented by crosses, the second by a dashed line. The different
 * well measurement types (oil, gas, water and condensate) are repesented by different
 * colours and can be displayed or make invisible.
 *
 */
public class SimpleOverlayPlotGrapher extends JComponent implements PlotGrapher
{
private final static int CROSS_RADIUS=3;
GasWellDataSet data;
GasWellDataSet overlayData;
private static Logger logger = Logger.getLogger( SimpleOverlayPlotGrapher.class );
private Map<WellMeasurementType,Boolean> plotMeasurementType;
private Map<WellMeasurementType,Color> measurementTypePlotColour;
private Date displayFrom = null;
private Date displayUntil = null;


enum DateScale { DAY, WEEK, MONTH, YEAR, DECADE };

public SimpleOverlayPlotGrapher(GasWellDataSet data, GasWellDataSet overlayData ) throws IllegalArgumentException
{
    this.data = data;
    this.overlayData = overlayData;
    this.logger = Logger.getLogger( this.getClass() );
    setPreferredSize( new Dimension( 1400, 600 ));
    setVisible( true );
    plotMeasurementType = new HashMap<WellMeasurementType,Boolean>();
    plotMeasurementType.put(WellMeasurementType.WATER_FLOW, true);
    plotMeasurementType.put( WellMeasurementType.OIL_FLOW, true );
    plotMeasurementType.put( WellMeasurementType.GAS_FLOW, true );
    plotMeasurementType.put( WellMeasurementType.CONDENSATE_FLOW, true );

    measurementTypePlotColour = new HashMap<WellMeasurementType,Color>();
    measurementTypePlotColour.put( WellMeasurementType.WATER_FLOW, Color.BLUE );
    measurementTypePlotColour.put( WellMeasurementType.GAS_FLOW, Color.YELLOW );
    measurementTypePlotColour.put( WellMeasurementType.CONDENSATE_FLOW, Color.GREEN );
    measurementTypePlotColour.put( WellMeasurementType.OIL_FLOW, Color.RED );

}

@Override
public void loadData( GasWellDataSet dataSet )
{
    this.data = dataSet;
}

@Override
public void loadOverlayData( GasWellDataSet overlayDataSet )
{
    this.overlayData = overlayDataSet;
}

public void paintComponent( Graphics gfx )
{
    logger.debug( "about to invoke render with width=" + getWidth() + ", height=" + getHeight() );
    render((Graphics2D) gfx, this.getWidth(), this.getHeight());
}

public GasWellDataSet getData()
{
    return this.data;
}



public void render( Graphics2D gfx, int width, int height )
{
    DateScale dateScale;
    Stroke originalStroke;
    GasWellDataEntry entry = null;
    GasWellDataEntry nextEntry = null;
    double flow = 0.0;
    int px = 0;
    int px0 = 0;
    int px1 = 0;
    int py = 0;
    int py1 = 0;

    Date graphFrom = null;
    Date graphUntil = null;
    int startIdx;
    int endIdx;



    if ( width  < 200 )
    {
        throw new IllegalArgumentException( "Width must be at least 200 pixels, canvas is only " + width + " pixels wide." );
    }

    if ( height  < 200 )
    {
        throw new IllegalArgumentException( "Height must be at least 200 pixels, canvas is only " + height + " pixels wide." );
    }

    // we need 50 pixels at the left and 100 pixels at the bottom for our axes...
    // ----------------------------------------------------------------------------

    gfx.setColor( Color.BLACK );
    gfx.fillRect( 0, 0, width, height );

    gfx.setColor( Color.WHITE );
    // horizontal axis
    gfx.drawLine( 50, height  - 100, width, height - 100 );

    // vertical axis
    gfx.drawLine( 50, height - 100, 50, 0 );


    // now draw some feint dashed lines horizontally
    // ----------------------------------------------
    originalStroke = gfx.getStroke();
    gfx.setColor( Color.GRAY );
    final float dash1[] = {10.0f};
    final BasicStroke dashed = new BasicStroke(1.0f,
                                          BasicStroke.CAP_BUTT,
                                          BasicStroke.JOIN_MITER,
                                          10.0f, dash1, 0.0f);

    final float dash2[] = {4.0f};
    final BasicStroke fineDashed = new BasicStroke(2.0f,
                                          BasicStroke.CAP_BUTT,
                                          BasicStroke.JOIN_MITER,
                                          10.0f, dash2, 0.0f);
    gfx.setStroke( dashed );
    for( int x = 100; x < width; x+= 50 )
    {
        gfx.drawLine( x, height - 100, x, 0 );
    }

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
        graphFrom = data.from();
        if ( ( overlayData != null ) && ( overlayData.from().before( graphFrom ) ) )
        {
            graphFrom = overlayData.from();
        }
    }

    if ( displayUntil != null )
    {
        graphUntil = this.displayUntil;
    } else {
        graphUntil = data.until();
        if ( ( overlayData != null ) && ( overlayData.until().after( graphUntil ) ) )
        {
            graphUntil = overlayData.until();
        }
    }
    
    logger.debug( "graphFrom=" + graphFrom + ", graphUntil=" + graphUntil + ", data.from=" + data.from() + ", data.until=" + data.until() );

    long days = ( graphUntil.getTime() - graphFrom.getTime() ) / 86400000L;
    dateScale = determineDateScale( days );
    double daysPerPixel = (double)days / ( (double)width - 50.0 );

    for( WellMeasurementType wmt : WellMeasurementType.values() )
    {

        // if we've requested this measurement type for display AND we have a maximum value for it (which means
        // that we've recorded some data against this measurement type!!)
        // ----------------------------------------------------------------------------------------------------
        if ( ( plotMeasurementType.containsKey( wmt ) ) && plotMeasurementType.get( wmt ) && ( data.getMaximum( wmt ) > 0.0 ) )
        {
            // plot the data original ...
            // -----------------
            double maxFlow = data.getMaximum( wmt );
            double minFlow = data.getMinimum( wmt );
            double range = maxFlow - minFlow;
            double flowUnitsPerPixel = range / ( (double)height - 100.0 );


            logger.debug( "days=" + days + ", daysPerPixel=" + daysPerPixel + ", data.size()=" + data.getData().size() );
            logger.debug( "measurement type=" + wmt + " ... minFlow=" + minFlow + ", maxFlow=" + maxFlow + ", range=" + range + ", oilBarrelsPerPixel=" + flowUnitsPerPixel );

            // set back to standard stroke...
            // -------------------------
            gfx.setStroke(originalStroke);
            gfx.setColor( measurementTypePlotColour.get( wmt ) );
            startIdx = data.from().before( graphFrom ) ? data.locateEntryIndex( graphFrom ) : 0;
            endIdx = ( graphUntil.getTime() >=  data.until().getTime() ) ? data.getData().size() - 1 : data.locateEntryIndex( graphUntil );
            logger.debug( "data; plotting from startIdx=" + startIdx + " to endIdx=" + endIdx );
            for( int i = startIdx; i < endIdx; i++ )
            {
                entry = data.getData().get( i );
                long daysSinceStart = ( entry.from().getTime() - graphFrom.getTime() ) / 86400000;
                if( entry.containsMeasurement( wmt ) )
                {
                    flow = entry.getMeasurement( wmt );
                    px = 50 + (int)Math.ceil( daysSinceStart / daysPerPixel );
                    py = height - 100 - (int)Math.round((flow - minFlow) / flowUnitsPerPixel);
//                  logger.debug( "flow(" + wmt + ")=" + flow + ", daysSinceStart=" + daysSinceStart + " ... about to plot point at ( px=" + px + ", py=" + py + " );" );
                    gfx.drawLine( px - CROSS_RADIUS, py, px + CROSS_RADIUS, py );
                    gfx.drawLine( px, py - CROSS_RADIUS, px, py + CROSS_RADIUS );
                }
            }

            // plot the overlay data....
            // --------------------------
            if ( overlayData != null )
            {
                gfx.setStroke(fineDashed);
                gfx.setColor( measurementTypePlotColour.get( wmt ) );
                nextEntry = null;
                startIdx = overlayData.from().before(graphFrom) ? overlayData.locateEntryIndex( graphFrom ) : 0;
                endIdx = ( graphUntil.getTime() >=  overlayData.until().getTime() ) ? overlayData.getData().size() - 1 : overlayData.locateEntryIndex( graphUntil );
                if( endIdx > overlayData.getData().size() - 1 )
                {
                    endIdx = overlayData.getData().size() - 1;
                }

                logger.debug( "Overlay data; plotting from startIdx=" + startIdx + " to endIdx=" + endIdx );
                for( int i = startIdx; i < endIdx; i++ )
                {
                    entry = ( nextEntry != null ) ? nextEntry : overlayData.getEntry( i );
//                  entry = overlayData.getData().get( i );

                    if ( entry.containsMeasurement( wmt ) )
                    {
                        flow = entry.getMeasurement( wmt );
                        long day0 = ( entry.from().getTime() - graphFrom.getTime() ) / 86400000;
                        long day1 = ( entry.until().getTime() - graphFrom.getTime() ) / 86400000;
                        px0 = 50 + (int)Math.ceil( day0 / daysPerPixel );
                        px1 = 50 + (int)Math.ceil( day1 / daysPerPixel );
                        py = height - 100 - (int)Math.round( ( flow - minFlow ) / flowUnitsPerPixel );
                        gfx.drawLine( px0, py, px1, py );
                        logger.debug( "horz. line from ( " + px0 + ", " + py + " ) to ( " + px1 + ", " + py + " )" );
                        if ( py < 0 )
                        {
                            logger.debug( "----> wmt=" + wmt + ", py=" + py + ", flow=" + flow + ", minFlow=" + minFlow + ", maxFlow=" + maxFlow );
                        }
                    }

                    // draw the line connecting to the next interval...
                    // ------------------------------------------------
                    if ( i < endIdx - 1 )
                    {
                        nextEntry = overlayData.getData().get( i + 1 );
                        if ( nextEntry.containsMeasurement( wmt ) )
                        {
                            flow = nextEntry.getMeasurement( wmt );
                            py1 = height - 100 - (int)Math.round( ( flow - minFlow ) / flowUnitsPerPixel );
                            gfx.drawLine( px1, py, px1, py1 );
                            logger.debug( "vert. line from ( " + px1 + ", " + py + " ) to ( " + px1 + ", " + py1 + " )" );

                        }
                    }

                } // end-FOR( data entries in the overlay data )
            } // end-IF( overlayData exists )
        } // end-IF( measurement type present )
    } // end-FOR( each measurement type )
}

@Override
public boolean getDisplayMeasurementType(WellMeasurementType wmt)
{
    return plotMeasurementType.containsKey( wmt ) && plotMeasurementType.get( wmt );
}

@Override
public void setDisplayMeasurementType(WellMeasurementType wmt, Boolean display)
{
    plotMeasurementType.put( wmt, display );
    repaint();
}

@Override
public GasWellDataSet getOverlayData()
{
    return overlayData;
}

@Override
public void setDisplayDateRange(Date from, Date until)
{
    this.displayFrom = from;
    this.displayUntil = until;
    repaint();
}

@Override
public void reduce(DataRateReducer reducer)
{
    this.overlayData = reducer.reduce( data);
    repaint();
}

protected static DateScale determineDateScale( long days )
{
    DateScale result = DateScale.DAY;

    do {

        if ( days < 28 )
        {
            result = DateScale.DAY;
            break;
        }

        if ( days < 141 )
        {
            result = DateScale.WEEK;
            break;
        }

        if ( days < 732 )
        {
            result = DateScale.MONTH;
            break;
        }

        if ( days < 7300 )
        {
            result = DateScale.YEAR;
            break;
        }

        result = DateScale.DECADE;

    } while( false );

    return result;
}

}
