package au.com.polly.ddr;


import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.Date;


public class SimplePlotGrapher extends JComponent
{
private final static int MIN_WIDTH = 200;
private final static int MIN_HEIGHT=200;
GasWellDataSet data;
private static Logger logger = Logger.getLogger( SimplePlotGrapher.class );



enum DateScale { DAY, WEEK, MONTH, YEAR, DECADE };

public SimplePlotGrapher( DataVsTimeSource dataSource ) throws IllegalArgumentException
{
    this.data = dataSource.getData();
    this.logger = Logger.getLogger( this.getClass() );
    setPreferredSize( new Dimension( 1400, 600 ));
    setVisible( true );

}

public void paintComponent( Graphics gfx )
{
    logger.debug( "about to invoke render with width=" + getWidth() + ", height=" + getHeight() );
    render((Graphics2D) gfx, this.getWidth(), this.getHeight());
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

    // plot the data...
    // -----------------
    double maxOilFlow = data.getMaximum( WellMeasurementType.OIL_FLOW );
    double minOilFlow = data.getMinimum( WellMeasurementType.OIL_FLOW );
    double range = maxOilFlow - minOilFlow;

    long days = ( data.until().getTime() - data.from().getTime() ) / 86400000L;
    dateScale = determineDateScale( days );

    double daysPerPixel = (double)days / ( (double)width - 50.0 );
    double oilBarrelsPerPixel = range / ( (double)height - 100.0 );

    logger.debug( "days=" + days + ", daysPerPixel=" + daysPerPixel );
    logger.debug( "minOilFlow=" + minOilFlow + ", maxOilFlow=" + maxOilFlow + ", range=" + range + ", oilBarrelsPerPixel=" + oilBarrelsPerPixel );

    // set back to standard stroke...
    // -------------------------
    gfx.setStroke( originalStroke );
    gfx.setColor( Color.RED );
    for( GasWellDataEntry entry : data.getData() )
    {
        double oilFlow = entry.getMeasurement( WellMeasurementType.OIL_FLOW );
        long daysSinceStart = ( entry.getStartInterval().getTime() - data.from().getTime() ) / 86400000;
        logger.debug( "oilFlow=" + oilFlow + ", daysSinceStart=" + daysSinceStart );
        int px = 50 + (int)Math.ceil( daysSinceStart / daysPerPixel );
        int py = height - 100 - (int)Math.round((oilFlow - minOilFlow) / oilBarrelsPerPixel);
        logger.debug( "About to plot point at px=" + px + ", py=" + py );
        gfx.drawLine( px - 5, py, px + 5, py );
        gfx.drawLine( px, py - 5, px, py + 5 );
    }
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
