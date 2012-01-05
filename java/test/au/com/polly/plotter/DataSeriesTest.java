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

import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 *
 * Battery of tests of the data series class.
 *
 */
@RunWith(JUnit4.class)
public class DataSeriesTest
{
private final static double ACCEPTABLE_ERROR = 1E-6;
    @Test
    public void testCreatingEmptyDataSeries()
    {
        DataSeries<Long> data = new DataSeries<Long>();
        assertNotNull( data );
        assertEquals( 0, data.size() );
        assertNull( data.getMin() );
        assertNull( data.getMax() );
    }

    @Test
    public void testCreatingIntegerDataSeriesFromExistingArrayList()
    {
        List<Long> list = new ArrayList<Long>();
        list.add( 57L );
        list.add( 42L );
        list.add( 21L );
        list.add( 47L );

        DataSeries<Long> data = new DataSeries<Long>( list);
        assertNotNull( data );
        assertEquals( 4, data.size() );
        assertEquals( 21L, data.getMin().longValue() );
        assertEquals( 57L, data.getMax().longValue() );
    }

    @Test
    public void testCreatingIntegerDataSeriesFromScratch()
    {
        DataSeries<Long> data = new DataSeries<Long>();
        assertNotNull( data );
        
        data.add( 42L );
        data.add( 57L );
        data.add( 21L );
        data.add( 47L );
        assertEquals( 4, data.size() );
        assertEquals( 21L, data.getMin().longValue() );
        assertEquals( 57L, data.getMax().longValue() );

    }

    @Test
    public void testCreatingFloatingPointDataSeriesFromExistingArrayList()
    {
        List<Double> list = new ArrayList<Double>();
        list.add( 57.9 );
        list.add( 42.0 );
        list.add( 21.2 );
        list.add( 47.6 );

        DataSeries<Double> data = new DataSeries<Double>( list);
        assertNotNull( data );
        assertEquals( 4, data.size() );
        assertEquals( 21.2, data.getMin(), ACCEPTABLE_ERROR );
        assertEquals( 57.9, data.getMax(), ACCEPTABLE_ERROR );
        assertEquals( 21L, data.getMin().longValue() );
        assertEquals( 57L, data.getMax().longValue() );
    }

    @Test
    public void testCreatingFloatingPointDataSeriesFromScratch()
    {
        DataSeries<Double> data = new DataSeries<Double>();
        assertNotNull( data );
        data.add( 57.9 );
        data.add( 42.0 );
        data.add( 21.2 );
        data.add( 47.6 );
        assertEquals( 4, data.size() );
        assertEquals( 21.2, data.getMin(), ACCEPTABLE_ERROR );
        assertEquals( 57.9, data.getMax(), ACCEPTABLE_ERROR );
        assertEquals( 21L, data.getMin().longValue() );
        assertEquals( 57L, data.getMax().longValue() );
    }

    @Test
    public void testCreatingDataSeriesWithNullList()
    {
        List<Double> list = null;
        DataSeries<Double> data = new DataSeries<Double>( list );
        assertNotNull( data );
        assertEquals( 0, data.size() );
        assertNull( data.getMin() );
        assertNull( data.getMax() );
    }

    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( DataSeriesTest.class );
    }

}