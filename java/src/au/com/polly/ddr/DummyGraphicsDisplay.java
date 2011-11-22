package au.com.polly.ddr;


import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Displays a grid upon axes like you'd expect to see on a graph...
 * ... simplified version of the graph we want to create, for testing/prototyping...
 */
public class DummyGraphicsDisplay extends JComponent
{
private final static Logger logger = Logger.getLogger( DummyGraphicsDisplay.class );
private final static int PREFERRED_WIDTH = 2560;
private final static int PREFERRED_HEIGHT = 1920;
private final static int MINIMUM_WIDTH = 200;
private final static int MINIMUM_HEIGHT = 150;
private final static int MAXIMUM_WIDTH = 2560;
private final static int MAXIMUM_HEIGHT = 1920;
private final static int HORIZONTAL_SEGMENTS = 5;
private final static int VERTICAL_SEGMENTS = 5;
private final static int HORIZONTAL_AXIS_ALLOWANCE = 50;
private final static int VERTICAL_AXIS_ALLOWANCE = 50;
private final static Color BACKGROUND_COLOUR = Color.BLACK;
private final static Color AXIS_COLOUR = Color.WHITE;
private final static Color GRID_COLOUR = Color.DARK_GRAY;
private final static Color AXIS_LABEL_COLOUR = AXIS_COLOUR;

public DummyGraphicsDisplay()
{
//    setPreferredSize( new Dimension( PREFERRED_WIDTH, PREFERRED_HEIGHT ) );
//    setMinimumSize(new Dimension( MINIMUM_WIDTH, MINIMUM_HEIGHT ) );
//  setMaximumSize( new Dimension( MAXIMUM_WIDTH, MAXIMUM_HEIGHT ) );
}

@Override
protected void paintComponent(Graphics g)
{
    Graphics2D gfx = (Graphics2D)g;
    Stroke originalStroke;

     logger.debug( "invoked with width=" + getWidth() + ", height=" + getHeight() );

    // origin is at x=VERTICAL_AXIS_ALLOWANCE, y=height() - HORIZONTAL_AXIS_ALLOWANCE
    // -----------------------------------------------------------------------------
    gfx.setColor( BACKGROUND_COLOUR );
    gfx.fillRect( 0, 0, getWidth(), getHeight()  );

    gfx.setColor( AXIS_COLOUR );
    gfx.drawLine( VERTICAL_AXIS_ALLOWANCE, getHeight() - HORIZONTAL_AXIS_ALLOWANCE, getWidth(), getHeight() - HORIZONTAL_AXIS_ALLOWANCE );
    gfx.drawLine( VERTICAL_AXIS_ALLOWANCE, getHeight() - HORIZONTAL_AXIS_ALLOWANCE, VERTICAL_AXIS_ALLOWANCE, 0 );

    double segmentWidth = (double)( getWidth() - VERTICAL_AXIS_ALLOWANCE ) / (double)HORIZONTAL_SEGMENTS;
    double segmentHeight = (double)( getHeight() - HORIZONTAL_AXIS_ALLOWANCE ) / (double)VERTICAL_SEGMENTS;

    originalStroke = gfx.getStroke();

    final float dash1[] = {10.0f};
    final BasicStroke dashed = new BasicStroke(1.0f,
                                          BasicStroke.CAP_BUTT,
                                          BasicStroke.JOIN_MITER,
                                          10.0f, dash1, 0.0f);
    gfx.setStroke( dashed );
    gfx.setColor( GRID_COLOUR );

    // let's draw the horizontal and vertical grid lines....
    // ------------------------------------------------------
    for( int i = 0; i < HORIZONTAL_SEGMENTS; i++ )
    {
        int gx = VERTICAL_AXIS_ALLOWANCE + (int)Math.round( segmentWidth * i );
        if ( i > 0 )
        {
            gfx.setColor( GRID_COLOUR );
            gfx.drawLine( gx, getHeight() - HORIZONTAL_AXIS_ALLOWANCE, gx, 0  );
        }
        gfx.setColor( AXIS_LABEL_COLOUR );
        gfx.drawString( Integer.toString( i ), gx - 3, getHeight() - ( HORIZONTAL_AXIS_ALLOWANCE - 15 ) );
    }

    for( int i = 0; i < VERTICAL_SEGMENTS; i++ )
    {
        int gy = getHeight() - ( HORIZONTAL_AXIS_ALLOWANCE + (int)Math.round( segmentHeight * i ) );
        if ( i > 0 )
        {
            gfx.setColor( GRID_COLOUR );
            gfx.drawLine(VERTICAL_AXIS_ALLOWANCE, gy, getWidth(), gy);
        }
        gfx.setColor( AXIS_LABEL_COLOUR );
        gfx.drawString( Integer.toString( i ), VERTICAL_AXIS_ALLOWANCE - 10, gy + 5 );
    }

    gfx.setStroke( originalStroke );

}


}
