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

import au.com.polly.plotter.csvgrapher.RootConfigurationTest;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * Battery of tests of the data series class.
 *
 */
public class DataPointTest
{
private final static double ACCEPTABLE_ERROR = 1E-6;
    @Test
    public void testCreatingFloatingPointDataPoint()
    {
        DataPoint<Double,Double> point = new DataPoint<Double,Double>( 5.6, 6.23 );
        assertNotNull( point );
        assertEquals( 5.6, point.getX(), ACCEPTABLE_ERROR );
        assertEquals( 6.23, point.getY(), ACCEPTABLE_ERROR );
        assertEquals( 5, point.getX().intValue() );
        assertEquals( 6, point.getY().intValue() );
    }

    @Test
    public void testCreatingIntegerPointDataPoint()
    {
        DataPoint<Long,Long> point = new DataPoint<Long,Long>( 5L, 6L );
        assertNotNull( point );
        assertEquals( 5L, point.getX().longValue() );  // why do we need the .longValue() here??
        assertEquals( 6L, point.getY().longValue() );  // why do we need the .longValue() here??
        assertEquals( 5.0, point.getX().doubleValue(),ACCEPTABLE_ERROR );
        assertEquals( 6.0, point.getY().doubleValue(), ACCEPTABLE_ERROR );
    }


    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( RootConfigurationTest.class );
    }

}