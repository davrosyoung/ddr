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

import java.util.ArrayList;
import java.util.List;

/**
 * Uses generics to implement the data series interface. Whilst we'd really like to be able to "extend"
 * java.lang.Long, to represent a timestamp, we can't do that. so we resort to using a boolean flag to
 * indicate whether or not this data series represents timestamps or not.
 *
 */
public class DataSeries<T extends Number>
{
    ArrayList<T> data;
    T min;
    T max;

    public DataSeries()
    {
        this.data = new ArrayList<T>();
    }

    /**
     *
     * @param data the set of values for the data series.
     */
    public DataSeries( List<T> data )
    {
        this();
        
        // if we've been supplied with some initial data, then feed it in, but keep tally of
        // the minimum and maximum values....
        // -----------------------------------------------------------------------------------
        if ( data != null )
        {
            add( data );

        }
    }

    /**
     *
     * @param data set of data to be added to this data series.
     */
    public void add( List<T> data )
    {
        for( T value: data )
        {
            add( value );
        }
        return;
    }

    /**
     *
     * @param value data point to be added into our data series.
     */
    public void add( T value )
    {
        Number nValue = (Number)value;
        if ( ( size() == 0 ) || ((Number)value).doubleValue() < ((Number)min).doubleValue() )
        {
            min = value;
        }

        if ( ( size() == 0 ) || ((Number)value).doubleValue() > ((Number)max).doubleValue() )
        {
            max = value;
        }

        data.add( value );
    }
    

    /**
     *
     * @return the underlying list of data values.
     */
    public List<T> getData() {
        return data;
    }

    /**
     *
     * @return the number of data points in the series.
     */
    public int size()
    {
        return data.size();
    }


    public T getMax()
    {
        T result;

        result = max;

        return result;
    }


    public T getMin()
    {
        T result;

        result = min;

        return result;
    }
}
