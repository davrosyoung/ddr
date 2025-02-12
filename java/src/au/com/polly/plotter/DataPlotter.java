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

import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import sun.beans.editors.FontEditor;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


/**
 * This is where the graph gets drawn...
 *
 * It contains;
 * <ul>
 * <li>left hand (vertical) axis</li>
 * <li>horizontal axis (only one)</li>
 * <li>potentially a second vertical axis</li>
 * <li>title</li>
 * <li>legend</li>
 * <li>plot area which contains
 *      <ul>
 *          <li>data points</li>
 *          <li>line of regression (optional)</li>
 *      </ul>
 * </li>
 * </ul>
 *
 * It is assumed that if subsequent sets of plot data are added, that they
 * share the same x-axis.
 *
 */
public class DataPlotter
{
private final static Logger logger = Logger.getLogger( DataPlotter.class );
private List<PlotData<Integer,Integer>> graphData;
private Axis                        xAxis;
private AxisConfiguration           xAxisConfig;
private List<Axis> yAxis;
private List<AxisConfiguration> yAxisConfig;

final static int LEFT_AXIS_BORDER = 40;
final static int RIGHT_AXIS_BORDER = 80;
final static int BOTTOM_AXIS_BORDER = 150;
final static int TITLE_BORDER = 20;    
/*
protected boolean showGrid = false;
protected double minY;
protected double minX;
protected double maxY;
protected double maxX;
protected double xInterval;
protected double yInterval;
protected double xScale;
protected double yScale;
protected boolean dirty = true;
*/
protected String title;
protected boolean doRegression = false;

private Font smallFont;
private Font axisTitleFont;
private Font axisLabelFont;
private Font titleFont;

private static Stroke fineDashedStroke;
private static Stroke regularDashedStroke;

protected int pointSize = 10;

private int width;  // width in pixels of our drawing region
private int height; // height (in pixels) of our drawing region/canvas

private final static Stroke thinNoDashStroke = new BasicStroke( 1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f );
private final static Stroke thinFineDashStroke = new BasicStroke( 1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, new float[] { 3, 3, 3, 3 }, 0.0f );
private final static Stroke thinRegularDashStroke = new BasicStroke( 1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, new float[] { 6, 6, 6, 6 }, 0.0f );
private final static Stroke regularNoDashStroke = new BasicStroke( 2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f );
private final static Stroke regularFineDashStroke = new BasicStroke( 2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, new float[] { 3, 3, 3, 3 }, 0.0f );
private final static Stroke regularRegularDashStroke = new BasicStroke( 2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, new float[] { 6, 6, 6, 6 }, 0.0f );
private final static Stroke thickNoDashStroke = new BasicStroke( 3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f );
private final static Stroke thickFineDashStroke = new BasicStroke( 3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, new float[] { 4, 4, 4, 4 }, 0.0f );
private final static Stroke thickRegularDashStroke = new BasicStroke( 3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, new float[] { 8, 8, 8, 8 }, 0.0f );
private final static Stroke extraThickNoDashStroke = new BasicStroke( 5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f );
private final static Stroke extraThickFineDashStroke = new BasicStroke( 5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, new float[] { 5, 5, 5, 5 }, 0.0f );
private final static Stroke extraThickRegularDashStroke = new BasicStroke( 5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, new float[] { 10, 10, 10, 10 }, 0.0f );

public DataPlotter( int width, int height )
{
    super();
    this.width = width;
    this.height = height;
    this.yAxis = new ArrayList<Axis>();
    this.yAxisConfig = new ArrayList<AxisConfiguration>();
    this.graphData = new ArrayList<PlotData<Integer,Integer>>();
}

public void setTitle( String title )
{
    this.title = title;
}

/**
 * 
 * @return number of pixels wide that we have to draw upon.
 */
public int getWidth()
{
    return width;
}


/**
 *
 * @return number of pixels high that we have to draw upon.
 */
public int getHeight()
{
    return height;
}

public void setDimension( Dimension size )
{
    setWidth( size.width );
    setHeight( size.height );
}

public void setWidth( int pixels )
{
    this.width = pixels;
}

public void setHeight( int pixels )
{
    this.height = pixels;
}

public void clearPlotData()
{
    this.graphData = new ArrayList<PlotData<Integer,Integer>>();
    this.xAxis = null;
    this.xAxisConfig = null;
    this.yAxis = new ArrayList<Axis>();
    this.yAxisConfig = new ArrayList<AxisConfiguration>();
}

/**
 *
 * @param plotData the data to be plotted. multiple sets of plot data may be applied. but only one y-axis
 * will be applied.
 * @param xAxis
 * @param yAxis
 * @param xAxisConfig
 * @param yAxisConfig
 */
public void addPlotData( PlotData plotData, Axis xAxis, Axis yAxis, AxisConfiguration xAxisConfig, AxisConfiguration yAxisConfig )
{
    PlotData<Integer,Integer> currGraphData = new PlotData<Integer,Integer>();
    List<DataPoint> points = plotData.getDataPoints();

    // set the x-axis to be the supplied x-axis, if we don't already have one ...
    // -----------------------------------------------------------------------------
    if ( xAxis != null )
    {
//      xAxis.autoScale( plotData.getXAxisData() );
        if ( this.xAxis == null )
        {
            this.xAxis = xAxis;
            xAxisConfig.setPlotLength( getWidth() - ( LEFT_AXIS_BORDER + RIGHT_AXIS_BORDER ) );
            this.xAxisConfig = xAxisConfig;
        }
    }

    // add this y-axis to our collection. we only really support one or two..
    // ... we determine which points belong to which axis, by their colours.
    // -----------------------------------------------------------------------------
    if ( yAxis != null )
    {
//      yAxis.autoScale( plotData.getYAxisData() );
        this.yAxis.add( yAxis );
        yAxisConfig.setPlotLength( getHeight() - ( BOTTOM_AXIS_BORDER + TITLE_BORDER ) );
        if ( ! this.yAxis.contains( yAxisConfig ) )
        {
            this.yAxisConfig.add( yAxisConfig );
        }
    }

    // if an x-axis hasn't been supplied, then use whatever has already been set...
    // -------------------------------------------------------------------------------
    if ( xAxis == null )
    {
        xAxis = this.xAxis;
        xAxisConfig = this.xAxisConfig;
    }

    // if no y-axis has been supplied, then use the default 1st y-axis....
    // ---------------------------------------------------------------------
    if ( yAxis == null )
    {
        yAxis = this.yAxis.get( 0 );
        yAxisConfig = this.yAxisConfig.get( 0 );
    }

    currGraphData.setMarkerSize(plotData.getMarkerSize());
    currGraphData.setMarkerStyle(plotData.getMarkerStyle());
    currGraphData.setColour( plotData.getColour() );
    currGraphData.setLineStyles( plotData.getLineStyles() );
    
    // use the axis information to translate the data points into
    // points relative to the x and y axis.
    // ----------------------------------------------------------------
    for( DataPoint dp : points )
    {
        DataPoint<Integer,Integer> gdp = new DataPoint<Integer,Integer>(
                                                xAxis.getPosition( dp.getX().doubleValue(), xAxisConfig ),
                                                yAxis.getPosition( dp.getY().doubleValue(), yAxisConfig )
                                            );
        currGraphData.add( gdp );
    }


    currGraphData.setColour( plotData.getColour() );
    this.graphData.add( currGraphData );
}


public void paint(Graphics g)
{
    Graphics2D g2d = (Graphics2D)g;
    Color origColour = g2d.getColor();
/*  only use these for primitive testing of the canvas extent.
    int minX;
    int midX;
    int maxX;
    int minY;
    int midY;
    int maxY;
*/
    Font font = null;
    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    env.getAvailableFontFamilyNames();
    // force a lookup on local fonts..
    // ----------------------------

    font = new Font("SansSerif", Font.PLAIN, 100 );


    this.smallFont = new Font("SansSerif", Font.PLAIN, 10 );
    this.axisLabelFont = new Font("SansSerif", Font.PLAIN, 12 );
    this.axisTitleFont = new Font("SansSerif", Font.PLAIN, 16 );
    this.titleFont = new Font("SansSerif", Font.PLAIN, 24 );

    // ok, let's start with a *bleck* background...
    // -------------------------------------------
    g.setColor( Color.BLACK );
    g.fillRect( 0, 0, getWidth(), getHeight() );
    
    g.setColor(Color.WHITE);
    
    logger.debug( "yAxis.size()=" + yAxis.size() );
    
    if ( ( xAxis != null ) && ( xAxisConfig != null ) )
    {
        renderXAxis(g2d, xAxis, xAxisConfig);
    }
    
    if ( ( yAxis != null ) && ( yAxis.size() > 0 ) && ( yAxisConfig != null ) && ( yAxisConfig.size() > 0 ) )
    {
        renderLeftYAxis( g2d, yAxis.get( 0 ), yAxisConfig.get( 0 ) );
        if ( yAxis.size() > 1 )
        {
            renderRightYAxis( g2d, yAxis.get( 1 ), yAxisConfig.get( 1 ) );
        }
    }
/*  ONLY UNCOMMENT FOR PRIMITIVE TESTING OF CANVAS EXTENT!!
    minX = (int) 0;
    maxX = (int) getSize().getWidth() - 1;
    midX = ( minX + maxX ) / 2;

    minY = (int) 0;
    maxY = (int) getSize().getHeight() - 1;
    midY = ( minY + maxY ) / 2;

    g2d.drawLine(  minX, minY, maxX, minY );
    g2d.drawLine(  minX, maxY, maxX, maxY );
    g2d.drawLine(  minX, minY, minX, maxY );
    g2d.drawLine(  maxX, minY, maxX, maxY );

    g2d.drawLine(  minX, midY, maxX, midY );
    g2d.drawLine(  midX, minY, midX, maxY );
*/
    if ( graphData != null )
    {
        plotData( g2d, graphData );
    }

    // now place the title... roughly in the middle ;-)
    // -----------------------------------------------------
    if ( this.title != null )
    {
        g2d.setFont( titleFont );
        g2d.drawString( title, LEFT_AXIS_BORDER +  ( xAxisConfig.getPlotLength() / 3 ) , TITLE_BORDER );
    }

    g2d.setColor( origColour );
}

public void plotData( Graphics2D g, List<PlotData<Integer,Integer>> data )
{
    List<DataPoint<Integer,Integer>> pointList;
    int lastX = -1;
    int lastY = -1;
    Stroke originalStroke = g.getStroke();
    Color origColour = g.getColor();
    AffineTransform identityTransformation = g.getTransform();
    identityTransformation.setToIdentity();
    double theta;


    // for each set of data points to be plotted...
    // ---------------------------------------------
    for( PlotData<Integer,Integer> plotData : data )
    {
        pointList = plotData.getDataPoints();
        Color c = plotData.getColour();
        g.setColor( c );
        lastX = -1;
        lastY = -1;
        
        logger.debug( "plotData=" + plotData );
        
        // only bother plotting lines if we have been requested to..
        // we only plot lines between points for time based data, because we don't know the
        // order of the points, we might just end up with a scribble...
        // ------------------------------------------------------------
        if ( ( !plotData.getLineStyles().contains( PlotData.LineStyle.NONE ) && !plotData.getLineStyles().isEmpty() ) && ( xAxis instanceof TimestampAxis ) )
        {
            Stroke drawingStroke = obtainAppropriateStroke( plotData.getLineStyles() );
            g.setStroke( drawingStroke );
            
            
            for( int i = 0; i < pointList.size(); i++ )
            {
                DataPoint<Integer,Integer> point = pointList.get( i );
                int x = point.getX() + LEFT_AXIS_BORDER;
                int y = getHeight() - ( BOTTOM_AXIS_BORDER + point.getY() );

                if ( plotData.getLineStyles().contains( PlotData.LineStyle.JOINED ) )
                {
                    if ( ( lastX >= 0 ) && ( lastY >= 0 ) )
                    {
                        g.drawLine( lastX, lastY, x, y );
                    }
                }
                
                if ( plotData.getLineStyles().contains( PlotData.LineStyle.STEPPED ) )
                {
                    if ( ( lastX >= 0 ) && ( lastY >= 0 ) )
                    {
                        g.drawLine( lastX, lastY, x, lastY );
                        g.drawLine( x, lastY, x, y );
                        
                        if ( i == pointList.size() - 1 )
                        {
                            g.drawLine( x, y, getWidth() - RIGHT_AXIS_BORDER, y );
                        }
                    }
                    
                }

                lastX = x;
                lastY = y;
            }
            
            g.setStroke( originalStroke );
        }

            // ok ... first draw the lines, then overlay the markers on the top...
            // --------------------------------------------------------------------
/*
            float[] dashPattern = { 5, 5, 5, 5 };
            g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT,
                                          BasicStroke.JOIN_MITER, 10,
                                          dashPattern, 0));
*/


		pointSize = plotData.getMarkerSize();

        // now overlay the data markers.... with the original pen stroke
        // --------------------------------------------------------------
        g.setStroke( originalStroke );
        for( DataPoint<Integer,Integer> point : pointList )
        {

            int x = point.getX() + LEFT_AXIS_BORDER;
            int y = getHeight() - ( BOTTOM_AXIS_BORDER + point.getY() );
            int a = plotData.getMarkerSize() / 2;

            switch( plotData.getMarkerStyle() )
            {
                case CIRCLE:
                    g.drawOval( x - a, y - a, pointSize, pointSize );
                    break;
                case FILLED_CIRCLE:
                    g.fillOval( x - a, y - a, pointSize, pointSize );
                    break;
                case SQUARE:
                    g.drawRect( x - a, y - a, pointSize, pointSize );
                    break;
                case FILLED_SQUARE:
                    g.fillRect( x - a, y - a, pointSize, pointSize );
                    break;
                default:
                case CROSSHAIR:
                    g.drawLine( x - a, y, x + a, y );
                    g.drawLine( x, y - a, x, y + a );
                    break;
            }
        }

    }

    g.setColor( origColour );

    logger.debug("graphData.size()=" + graphData.size() );

    // ok ... line of regression??? only if there is only one pair of data...
    // ------------------------------------------------------------------------
    if ( graphData.size() == 1 )
    {
        RegressionData<Integer,Integer> rd = new RegressionData<Integer,Integer>( graphData.get( 0 ) );
        int x0, y0;
        int x1, y1;
        int tx, ty;
        double x00, y00;
        double x01, y01;
        x00 = 0;
        x01 = x00 + xAxisConfig.getPlotLength();
        y00 = ( rd.getGradient() * x00 ) + rd.getIntercept();
        y01 = ( rd.getGradient() * x01 ) + rd.getIntercept();
        x0 = (int)x00 + LEFT_AXIS_BORDER;
        x1 = (int)x01 + LEFT_AXIS_BORDER;
        y0 = getHeight() - ( BOTTOM_AXIS_BORDER + (int)y00 );
        y1 = getHeight() - ( BOTTOM_AXIS_BORDER + (int)y01 );

        g.drawLine( x0, y0, x1, y1 );

        // now try to place the correlation coefficient near the line..
        // ... whilst the proper equation is y = mx + b, remember that
        // the java graphics y-axis is reversed!!!
        // ---------------------------------------------------------------
        tx = x0 + 100;
        ty = y0 - (int)( 100.0 * rd.getGradient() );
        theta = Math.atan2(x01 - x00, y01 - y00);
        g.translate( tx, ty );
        g.rotate( -theta );
        g.drawString( "r2=" + rd.getCorrelationDetermination(), 0, 5 );

        // set the graphics transformation back to the identity version ...
        // ------------------------------------------------------------------
        g.setTransform( identityTransformation );
    }

}

public void renderXAxis( Graphics2D g, Axis axis, AxisConfiguration config )
{
    int x0 = LEFT_AXIS_BORDER;
    int y0 = getHeight() - BOTTOM_AXIS_BORDER;
    int x1 = LEFT_AXIS_BORDER + ( ( config != null ) ? config.getPlotLength() : 0 );
    double c;
    String units;
    Stroke standardStroke;
    Stroke dashedStroke;
    AffineTransform identity = new AffineTransform();
    identity.setToIdentity();
    Color origColor = g.getColor();

    logger.debug( "------------------------------------------------------------" );
    logger.debug( "width=" + getWidth() + ", height=" + getHeight() );
    logger.debug( "x0=" + x0 + ", y0=" + y0 + ", x1=" + x1 );
    logger.debug( "------------------------------------------------------------" );

    standardStroke = g.getStroke();
    dashedStroke = new BasicStroke( 1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, new float[] { 2, 2, 2, 2 }, 0 );


    // draw the axis line itself...
    // ------------------------
    g.setColor( config != null ? config.getColour() : Color.WHITE );
    g.drawLine(x0, y0, x1, y0);

    Font rotatedFont;
    AffineTransform fat = new AffineTransform();
    fat.rotate( Math.PI / 2 );
    rotatedFont = axisLabelFont.deriveFont( fat );
    
    g.setFont( rotatedFont );

    if ( axis != null )
    {
        logger.debug( "axis.getNumberIntervals=" + axis.getNumberIntervals() + ", axis.getMinimumValue()=" + axis.getMinimumValue() );

        // now to mark the intervals...
        // ------------------------------
        c = axis.getMinimumValue();
        for( int i = 0; i <= axis.getNumberIntervals(); i++, c += axis.getIntervalSize() )
        {
            String dateText;
            g.setColor( config != null ? config.getColour() : Color.WHITE );
            g.setStroke( standardStroke );
            x0 = axis.getPosition( c, config ) + LEFT_AXIS_BORDER;
            dateText = axis.getDataLabel(c);
            logger.debug("for i=" + i + ", c=" + c + ", tickLabel=" + dateText);
            g.drawLine( x0, y0, x0, y0 + 5 );

            // We use a modified/dervied font which causes the x-axis label to be drawn on an angle.
            // -----------------------------------------------------------------------------------
            g.drawString(dateText, x0 - 4, y0 + 8);
    //        g.drawString(dateText, x0 - 4, getHeight() - 15 );
            g.setTransform( identity );

            // now draw a thin dashed line to mark this interval
            // -----------------------------------------------------
            g.setColor( config != null ? config.getGridColour() : Color.WHITE );
            g.setStroke( dashedStroke );
            g.drawLine( x0, y0, x0, TITLE_BORDER );
        }
    }

    // restore the original stroke..
    // -------------------------------
    g.setStroke( standardStroke );


    g.setFont( axisLabelFont );


    // And now place the label...
    // ------------------------------
    if ( config != null )
    {
        if (
                ( ( units = config.getUnits() ) != null )
             && ( ( units.length() > 0 ) )
            )
        {
            g.drawString( config.getLabel() + " (" + units + ")" ,  ( LEFT_AXIS_BORDER / 2 ), getHeight() - 5 );
        } else {
            g.drawString( config.getLabel(), ( LEFT_AXIS_BORDER / 2 ), getHeight() - 5);
        }
    }
}

public void renderLeftYAxis( Graphics2D g, Axis axis, AxisConfiguration config )
{
    int x0 = LEFT_AXIS_BORDER;
    int x1 = this.getWidth() - RIGHT_AXIS_BORDER;
    int y0 = getHeight() - BOTTOM_AXIS_BORDER;
    int y1 = ( getHeight() - BOTTOM_AXIS_BORDER ) - ( config.getPlotLength() );
    double c;
    String tickLabel;
    String units;
    Stroke standardStroke;
    Stroke dashedStroke;
    AffineTransform identity = new AffineTransform();
    identity.setToIdentity();
    AffineTransform vertical = new AffineTransform();
    vertical.rotate( Math.PI / 2 );
    Color origColor = g.getColor();
    Font verticalAxisFont = axisTitleFont.deriveFont( vertical );

    standardStroke = g.getStroke();
    dashedStroke = new BasicStroke( 1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, new float[] { 2, 2, 2, 2 }, 0 );

    logger.debug( "------------------------------------------------------------" );
    logger.debug( "width=" + getWidth() + ", height=" + getHeight() );
    logger.debug( "x0=" + x0 + ", y0=" + y0 + ", y1=" + y1 );
    logger.debug( "------------------------------------------------------------" );

    // draw the axis line itself...
    // ------------------------
    g.setColor( config.getColour() );
    g.drawLine( x0, y0, x0, y1 );
    g.drawLine( x1, y0, x1, y1 );

    g.setFont( smallFont );


    // now to mark the intervals...
    // ------------------------------
    c = axis.getMinimumValue();
    for( int i = 0; i <= axis.getNumberIntervals(); i++ )
    {
		g.setColor(config.getColour());
        g.setStroke( standardStroke );
        y1 = ( getHeight() - BOTTOM_AXIS_BORDER ) - axis.getPosition( c, config );
        tickLabel = axis.getDataLabel( c );
        g.drawLine( x0 - 5, y1, x0, y1 );
        c += axis.getIntervalSize();
        g.drawString( tickLabel, x0 - 20, y1 + 4 );


        // now draw a thin dashed line to mark this interval
        // -----------------------------------------------------
		g.setColor( config.getGridColour() );
        g.setStroke( dashedStroke );
        g.drawLine( x0, y1, x1, y1 );

    }
    g.setStroke( standardStroke );


    g.setFont( verticalAxisFont );
    g.setColor( config.getColour() );


    // And now place the label...
    // ------------------------------
    if (
            ( ( units = config.getUnits() ) != null )
         && ( ( units.length() > 0 ) )
    )
    {
        g.drawString( config.getLabel() + " (" + units + ")" ,  0, 50 );
    } else {
        g.drawString( config.getLabel(), 0, 50 );
    }

    g.setTransform( identity );
    g.setColor( origColor );
}

public void renderRightYAxis( Graphics2D g, Axis axis, AxisConfiguration config )
{
    int x0 = this.getWidth() - RIGHT_AXIS_BORDER;
    int y0 = getHeight() - BOTTOM_AXIS_BORDER;
    int y1 = ( getHeight() -BOTTOM_AXIS_BORDER ) - ( config.getPlotLength() );
    double c;
    String tickLabel;
    String units;
    Stroke standardStroke;
    Stroke dashedStroke;
    AffineTransform identity = null;
    identity = g.getTransform();
    identity.setToIdentity();
    Color origColor = g.getColor();


    standardStroke = g.getStroke();
    dashedStroke = new BasicStroke( 1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, new float[] { 2, 2, 2, 2 }, 0 );


    logger.debug("------------------------------------------------------------" );
    logger.debug("width=" + getWidth() + ", height=" + getHeight() );
    logger.debug("x1=" + x0 + ", y0=" + y0 + ", y1=" + y1 );
    logger.debug("------------------------------------------------------------" );

    // draw the axis line itself...
    // ------------------------
    g.setColor( config.getColour() );
    g.drawLine( x0, y0, x0, y1 );

    g.setFont( smallFont );


    // now to mark the intervals...
    // ------------------------------
    c = axis.getMinimumValue();
    for( int i = 0; i <= axis.getNumberIntervals(); i++ )
    {
        g.setStroke( standardStroke );
        y1 = ( getHeight() - BOTTOM_AXIS_BORDER ) - axis.getPosition( c, config );
        tickLabel = axis.getDataLabel( c );
        g.drawLine( x0, y1, x0 + 5, y1 );
        c += axis.getIntervalSize();
        g.drawString( tickLabel, x0 + 2, y1 );

    }

    g.translate( x0 + 2, getHeight() - ( BOTTOM_AXIS_BORDER + 40 ) );
    g.rotate( - ( Math.PI / 2 ) );

    g.setFont( axisLabelFont );


    // And now place the label...
    // ------------------------------
    if (
            ( ( units = config.getUnits() ) != null )
         && ( ( units.length() > 0 ) )
    )
    {
        g.drawString( config.getLabel() + " (" + units + ")" ,  0, 0 );
    } else {
        g.drawString( config.getLabel(), 0, 0 );
    }

    g.setTransform( identity );
    g.setColor( origColor );
}


public void setDoRegression( boolean flag )
{
    this.doRegression = flag;
}

public boolean doRegression()
{
    return this.doRegression;
}

/**
 * 
 * @param styles the line styles which have been requested
 * @return a line drawing stroke appropriate to the requested line style.
 * 
 * This method could probably be deferred off into a utility class of some description.
 * Doesn't really fit perfectly in this class!!
 */
public static Stroke obtainAppropriateStroke( EnumSet<PlotData.LineStyle> styles )
{
    Stroke result = null;
    
    do {
        
        // if NONE specified, or no styles, then we are not drawing any lines...
        // ----------------------------------------------------------------------
        if ( styles.contains( PlotData.LineStyle.NONE ) || styles.isEmpty() )
        {
            break;
        }
        
        // the line style must be either joined or stepped, or no line is to be drawn at all...
        // --------------------------------------------------------------------------------------
        if ( !styles.contains( PlotData.LineStyle.JOINED ) && !styles.contains( PlotData.LineStyle.STEPPED ) )
        {
            break;
        }
        
        if ( styles.contains( PlotData.LineStyle.THIN ) )
        {
            if ( styles.contains( PlotData.LineStyle.DASHED ) )
            {
                result = thinRegularDashStroke;
                break;
            }
            
            if ( styles.contains( PlotData.LineStyle.FINE_DASHED ) )
            {
                result = thinFineDashStroke;
                break;
            }

            result = thinNoDashStroke;
            break;
        }

        if ( styles.contains( PlotData.LineStyle.THICK ) )
        {
            if ( styles.contains( PlotData.LineStyle.DASHED ) )
            {
                result = thickRegularDashStroke;
                break;
            }

            if ( styles.contains( PlotData.LineStyle.FINE_DASHED ) )
            {
                result = thickFineDashStroke;
                break;
            }

            result = thickNoDashStroke;
            break;
        }

        if ( styles.contains( PlotData.LineStyle.EXTRA_THICK ) )
        {
            if ( styles.contains( PlotData.LineStyle.DASHED ) )
            {
                result = extraThickRegularDashStroke;
                break;
            }

            if ( styles.contains( PlotData.LineStyle.FINE_DASHED ) )
            {
                result = extraThickFineDashStroke;
                break;
            }

            result = extraThickNoDashStroke;
            break;
        }

        if ( styles.contains( PlotData.LineStyle.DASHED ) )
        {
            result = regularRegularDashStroke;
            break;
        }

        if ( styles.contains( PlotData.LineStyle.FINE_DASHED ) )
        {
            result = regularFineDashStroke;
            break;
        }

        result = regularNoDashStroke;
        break;

    } while( false );
    
    return result;
}

}