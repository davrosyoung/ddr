package au.com.polly.ddr;


import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class SimplePlotGrapher extends JComponent implements PlotGrapher
{
private final static int MIN_WIDTH = 200;
private final static int MIN_HEIGHT=200;
GasWellDataSet data;
private static Logger logger = Logger.getLogger( SimplePlotGrapher.class );
private Map<WellMeasurementType,Boolean> plotMeasurementType;
private Map<WellMeasurementType,Color> measurementTypePlotColour;
private Date displayFrom = null;
private Date displayUntil = null;


enum DateScale { DAY, WEEK, MONTH, YEAR, DECADE };

public SimplePlotGrapher( GasWellDataSet dataSource ) throws IllegalArgumentException
{
    this.data = dataSource;
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

public void paintComponent( Graphics gfx )
{
    logger.debug( "about to invoke render with width=" + getWidth() + ", height=" + getHeight() );
    render((Graphics2D) gfx, this.getWidth(), this.getHeight());
}

@Override
public void loadData(GasWellDataSet dataSet)
{
    this.data = dataSet;
}

@Override
public void loadOverlayData(GasWellDataSet overlayDataSet)
{
    throw new IllegalStateException( "DO NOT SUPPORT OVERLAYS!!" );
}

@Override
public void reduce(DataRateReducer reducer)
{
    throw new IllegalStateException( "DO NOT SUPPORT REDUCTION!!!!" );
}

public void render( Graphics2D gfx, int width, int height )
{
    DateScale dateScale;
    Stroke originalStroke;


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
    gfx.setStroke( dashed );
    for( int x = 100; x < width; x+= 50 )
    {
        gfx.drawLine( x, height - 100, x, 0 );
    }

    for( WellMeasurementType wmt : WellMeasurementType.values() )
    {

        if ( ( plotMeasurementType.containsKey( wmt ) ) && plotMeasurementType.get( wmt ) )
        {
            // plot the data...
            // -----------------
            double maxFlow = data.getMaximum( wmt );
            double minFlow = data.getMinimum( wmt );
            double range = maxFlow - minFlow;

            long days = ( data.until().getTime() - data.from().getTime() ) / 86400000L;
            dateScale = determineDateScale( days );

            double daysPerPixel = (double)days / ( (double)width - 50.0 );
            double flowUnitsPerPixel = range / ( (double)height - 100.0 );

            logger.debug( "days=" + days + ", daysPerPixel=" + daysPerPixel );
            logger.debug( "minOilFlow=" + minFlow + ", maxOilFlow=" + maxFlow + ", range=" + range + ", oilBarrelsPerPixel=" + flowUnitsPerPixel );

            // set back to standard stroke...
            // -------------------------
            gfx.setStroke( originalStroke );
            gfx.setColor( measurementTypePlotColour.get( wmt ) );
            for( GasWellDataEntry entry : data.getData() )
            {
                double flow = entry.getMeasurement( wmt );
                long daysSinceStart = ( entry.getStartInterval().getTime() - data.from().getTime() ) / 86400000;
                logger.debug( "flow(" + wmt + ")=" + flow + ", daysSinceStart=" + daysSinceStart );
                int px = 50 + (int)Math.ceil( daysSinceStart / daysPerPixel );
                int py = height - 100 - (int)Math.round((flow - minFlow) / flowUnitsPerPixel);
                logger.debug( "About to plot point at px=" + px + ", py=" + py );
                gfx.drawLine( px - 5, py, px + 5, py );
                gfx.drawLine( px, py - 5, px, py + 5 );
            }

        }

    }
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
public void setDisplayDateRange(Date from, Date until)
{
    this.displayFrom = from;
    this.displayUntil = until;
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
