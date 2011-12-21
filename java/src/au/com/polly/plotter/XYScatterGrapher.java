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

package au.com.polly.plotter;

/**
 * Convenience class, to help create a graph of one data series
 * against another (two axis only). Enables us to plot a line
 * of regression and output a correlation determination.
 * Avoids putting too much format specific logic into the
 * output() method of the
 * IntervalDataPlotFormatter class, or into messy subroutines
 * in that class.
 *
 */
public class XYScatterGrapher<T extends Number,U extends Number>
extends BaseGrapher<T,U>    
implements Grapher<T,U>
{
    PlotData<T,U> plotData;
    AxisConfiguration xAxisConfig;
    AxisConfiguration yAxisConfig;

    /**
     * Creates a default instance, need to set attributes by hand.
     */
    public XYScatterGrapher()
    {

    }

    /**
     *
     * @param graphTitle What the graph should be called.
     * @param width desired width of the resultant image.
     * @param height desired height of the resultant image.
     * @param plotData array of data sets to be plotted.
     * @param xAxisConfig formatting config (color,title) for the x-axis
     * @param yAxisConfig formatting config (color,title) for the y-axis.
     *
     * Convenience object, to graph one series of values
     * against another, and plot the line of regression.
     */
    public XYScatterGrapher(
            String graphTitle,
            int width,
            int height,
            PlotData<T,U> plotData,
            AxisConfiguration xAxisConfig,
            AxisConfiguration yAxisConfig
    )
    {
        setTitle( graphTitle );
        setWidth( width );
        setHeight( height );
        setPlotData( plotData );
        setXAxisConfig( xAxisConfig );
        setYAxisConfig( yAxisConfig );
    }



    /**
     * format specific rendering logic, invoked by the render() method.
     */
    public void doRender( PlotCanvas canvas )
    {
        Axis<T> xAxis = new NumericAxis<T>();
        if ( xAxisConfig.isAutoScale() )
        {
            xAxis.autoScale( plotData.getXAxisData() );
        } else {
            xAxis.scale( xAxisConfig.getMin(), xAxisConfig.getMax() );
        }
        Axis<U> yAxis = new NumericAxis<U>();
        if ( yAxisConfig.isAutoScale() )
        {
            yAxis.autoScale( plotData.getYAxisData() );
        } else {
            yAxis.scale( yAxisConfig.getMin().doubleValue(), yAxisConfig.getMax().doubleValue() );
        }
        canvas.addPlotData( plotData, xAxis, yAxis, xAxisConfig, yAxisConfig );
    }

    public PlotData<T, U> getPlotData() {
        return plotData;
    }

    public void setPlotData(PlotData<T, U> plotData) {
        this.plotData = plotData;
    }

    public AxisConfiguration getXAxisConfig() {
        return xAxisConfig;
    }

    public void setXAxisConfig(AxisConfiguration xAxisConfig) {
        this.xAxisConfig = xAxisConfig;
    }

    public AxisConfiguration getYAxisConfig() {
        return yAxisConfig;
    }

    public void setYAxisConfig(AxisConfiguration yAxisConfig) {
        this.yAxisConfig = yAxisConfig;
    }

}