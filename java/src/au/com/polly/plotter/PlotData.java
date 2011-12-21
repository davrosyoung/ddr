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

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * represents some data to be plotted. Intended uses are for
 * modelling and plotting gateway performance related data.
 *
 */
public class PlotData<X extends Number,Y extends Number>
{
enum PointMarkerStyle { CROSSHAIR, SQUARE, FILLED_SQUARE, CIRCLE, FILLED_CIRCLE };
DataSeries<X> xDataSeries;
DataSeries<Y> yDataSeries;
List<DataPoint<X,Y>> plotData = null;
Color colour = Color.BLACK;
PointMarkerStyle markerStyle = PointMarkerStyle.CROSSHAIR;
int markerSize = 20;

public PlotData()
{
    xDataSeries = new DataSeries<X>();
    yDataSeries = new DataSeries<Y>();
    plotData = new ArrayList<DataPoint<X,Y>>();

}

public PlotData( DataSeries<X> xData, DataSeries<Y> yData )
{
    if ( ( xData == null ) || ( yData == null ) )
    {
        throw new NullPointerException(
                "Cannot create plot data from null data series. "
            +   "x series is " + ( ( xData == null ) ? "<NULL>" : "NOT <NULL>" ) + ", "
            +   "y series is " + ( ( yData == null ) ? "<NULL>" : "NOT <NULL>" )
        );
    }

    if ( xData.size() != yData.size() )
    {
        throw new IllegalArgumentException( "X (size=" + xData.size() + ") and Y (size=" + yData.size() + ") data series are not of the same size." );
    }
    List<X> rawXData = xData.getData();
    List<Y> rawYData = yData.getData();
    Iterator<X> xCursor = rawXData.iterator();
    Iterator<Y> yCursor = rawYData.iterator();
    this.plotData = new ArrayList<DataPoint<X,Y>>();

    for( int i = 0; i < xData.size(); i++ )
    {
        assert( xCursor.hasNext() );
        assert( yCursor.hasNext() );

        // don't use the add() method, as we don't want to update the X and Y data series that way,
        // instead we'll just point them to the supplied data series.
        // ------------------------------------------------------------------------------------------
        DataPoint<X,Y> dp = new DataPoint<X,Y>( xCursor.next(), yCursor.next() );
        this.plotData.add( dp );
    }

    xDataSeries = xData;
    yDataSeries = yData;

}

public int size()
{
    int result = 0;

    if ( xDataSeries != null )
    {
        result = xDataSeries.size(); 
    }
    return result;
}

public List<DataPoint<X,Y>> getDataPoints()
{
    List<DataPoint<X,Y>> result;

    result = plotData;
    
    return result;
}

public DataSeries<X> getXAxisData()
{
    return xDataSeries;
}

public DataSeries<Y> getYAxisData()
{
    return yDataSeries;
}

public void add( DataPoint<X,Y> dp )
{
    // for now ... we do not keep the data sorted, although it would really make sense to keep
    // the data points sorted along the x-axis!! otherwise, we can't really guarantee that joining
    // the points with lines is going to make much sense...
    // ---------------------------------------------------------------------------------------------
    if ( plotData == null )
    {
        plotData = new ArrayList<DataPoint<X,Y>>();
    }
    plotData.add( dp );
    xDataSeries.add( dp.getX() );
    yDataSeries.add( dp.getY() );
    return;
}

public X getMinX()
{
    return xDataSeries.getMin();
}

public Y getMinY()
{
    return yDataSeries.getMin();
}

public X getMaxX()
{
    return xDataSeries.getMax();
}

public Y getMaxY()
{
    return yDataSeries.getMax();
}

public Color getColour()
{
    return this.colour;
}

public void setColour( Color colour )
{
    this.colour = colour;
}

public void setMarkerStyle( PointMarkerStyle style )
{
    this.markerStyle = style;
}

public PointMarkerStyle getMarkerStyle()
{
    return this.markerStyle;
}

public void setMarkerSize( int size )
{
    this.markerSize = size;
}

public int getMarkerSize()
{
    return this.markerSize;
}

}