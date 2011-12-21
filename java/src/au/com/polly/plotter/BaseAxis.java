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
import java.text.NumberFormat;

/**
 * Base implementation of an axis, used by both numeric and time based axes.
 * 
 */
public abstract class BaseAxis<T extends Number>
implements Axis<T>
{
String label;
//PlotData data;
Dimension size;
int numberIntervals;
double max;
double min;
double intervalSize;
NumberFormat intFormatter = null;
NumberFormat doubleFormatter = null;

public abstract void autoScale(DataSeries<T> data);


/**
 *
 * @param min
 * @param max
 *
 * Invoke this method, if the axis is not supposed to autoscale based upon it's values, but instead is
 * supposed to be between aribitary specified points.
 */
public abstract void scale( Number min, Number max );

public BaseAxis()
{
    intFormatter = NumberFormat.getIntegerInstance();
    intFormatter.setGroupingUsed( false );
    doubleFormatter = NumberFormat.getNumberInstance();
    doubleFormatter.setGroupingUsed( false );
}

/**
 *
  * @param range
 * @return the next smallest number, which is ten to the power of some integer.
 *
 * For example;
 * if range is 99.99, returned value would be 10
 * if range is 100, returned value would be 100
 * if range is 0.2985, returned value would be 0.1
 */    
public double calculateIntervalFactor(double range)
{
    int orderOfMagnitude;
    double result;
    double ratio;

    // determine the largest value power of ten, which is the same size or
    // smaller than the range of data to be plotted...
    // ------------------------------------------------------------------------
    ratio = Math.log(range) / Math.log(10.0);
    orderOfMagnitude= (int) Math.floor(ratio + 0.00000000000001);
    result = Math.pow(10, orderOfMagnitude);

    return result;
}

/**
 * @return the number of intervals that this axis covers. each interval will be delinieated
 *         by a tick mark when rendered.
 */
public int getNumberIntervals()
{
    return numberIntervals;
}

/**
 * @return the value represented by the start of the first interval, this is generally smaller
 *         than the actual minimum data value that was submitted to the autoScale() method.
 */
public double getMinimumValue()
{
    return min;
}

/**
 * @return the value represented by the end of the last interval, this is generally larger
 *         than the actual maximum data value that was submitted to the autoScale() method.
 */
public double getMaximumValue()
{
    return max;
}

/**
 * @return the length/size of each interval
 */
public double getIntervalSize()
{
    return intervalSize;
}

/**
 * @param value the data point to obtain a label for
 * @return appropriate text for the data point.
 */
public String getDataLabel(T value)
{
    NumberFormat formatter;
    String result = null;

    if (
            ( value instanceof Integer)
        ||  ( value instanceof Long)
    )
    {
        result = intFormatter.format( value.intValue() );
    }

    if (
           ( value instanceof Float)
         ||( value instanceof Double)
            )
    {
        result = doubleFormatter.format( value.doubleValue() );
    }

    return result;
}

/**
 * @param value the value from the data series, applicable to this axis, to be plotted.
 * @return the number of pixels along this axis that the specified value should be plotted at.
 */
public int getPosition(double value, AxisConfiguration configuration) {
    int size = configuration.getPlotLength();
    double x0 = getMinimumValue();
    double x1 = getMaximumValue();
    double r = x1 - x0;
    double r0 = ( value - x0 ) / r;
    int result = (int)( r0 * size );
    return result;
}
}
