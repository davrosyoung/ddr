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

import au.com.polly.util.DateArmyKnife;
import au.com.polly.util.TimestampArmyKnife;
import org.apache.log4j.Logger;

import java.awt.*;

/**
 * Convenience class, to help create a time based graph. Avoids putting
 * too much format specific logic into the output() method of the
 * IntervalDataPlotFormatter class, or into messy subroutines
 * in that class.
 *
 */
public class TimeBasedGrapher<Long extends Number,T extends Number>
extends BaseGrapher<Long,T>
implements Grapher<Long,T>
{
private final static Logger logger = Logger.getLogger( TimeBasedGrapher.class );
private PlotData<Long,T>[] plotData;
private Axis<T>[] axes;
private AxisConfiguration[] axesConfig;


//    TimestampArmyKnife knife;



    /**
     * Creates a default instance, need to set attributes by hand.
     */
    public TimeBasedGrapher()
    {

    }

    /**
     *
     * @param graphTitle What the graph should be called.
     * @param width desired width of the resultant image.
     * @param height desired height of the resultant image.
     * @param plotData array of data sets to be plotted.
     * @param axes matching array of axes, time axis is created internally. these should
     * correspond to any y-axes to be used upon the graph. at least one is required. upto
     * two are supported.
     * @param axesConfig the configuration for any supplied axes.
     *
     * Convenience object, to place one or more data plots onto a time
     * based graph.
     */
    public TimeBasedGrapher(
            String graphTitle,
            int width,
            int height,
            PlotData<Long,T>[] plotData,
            Axis<T>[] axes,
            AxisConfiguration[] axesConfig
    )
    {
        setTitle( graphTitle );
        setWidth( width );
        setHeight( height );
        setPlotData( plotData );
        setAxes( axes );
        setAxesConfig( axesConfig );
    }


    /**
     * format specific rendering logic, invoked by the render() method.
     */
    protected void doRender( DataPlotter plotter, Graphics2D gfx )
    {
        TimestampAxis<java.lang.Long> timeAxis;
        AxisConfiguration timeAxisConfig;

        timeAxis = new TimestampAxis<java.lang.Long>();
        timeAxis.knife = new DateArmyKnife();
        timeAxisConfig = new AxisConfiguration( "Date/Time", null, Color.BLACK, width );

        for( PlotData data : plotData )
        {
            timeAxis.autoScale( data.getXAxisData() );
        }

        for( int i = 0; i < plotData.length; i++ )
        {
            PlotData data = plotData[ i ];
            if ( ( axes.length >= ( i - 1 ) ) && ( axes[ i ] != null ) )
            {
                if ( axesConfig[ i ].isAutoScale() )
                {
                    axes[ i ].autoScale( data.getYAxisData() );
                } else {
                    axes[ i ].scale( axesConfig[ i ].getMin(), axesConfig[ i ].getMax() );
                }
            }
            plotter.addPlotData(data, timeAxis, axes[i], timeAxisConfig, axesConfig[i]);
        }
    }


    public PlotData<Long, T>[] getPlotData() {
        return plotData;
    }

    public void setPlotData(PlotData<Long, T>[] plotData) {
        this.plotData = plotData;
    }

    public Axis<T>[] getAxes() {
        return axes;
    }

    public void setAxes(Axis<T>[] axes) {
        this.axes = axes;
    }

    public AxisConfiguration[] getAxesConfig() {
        return axesConfig;
    }

    public void setAxesConfig(AxisConfiguration[] axesConfig) {
        this.axesConfig = axesConfig;
    }
}
