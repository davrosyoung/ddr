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
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 *
 * Battery of tests of the axisw class.
 *
 */
@RunWith(JUnit4.class)
public class TimeUnitTest
{
private final static Logger logger = Logger.getLogger( TimeUnitTest.class );
private final static double ACCEPTABLE_ERROR = 1E-8;

// demonstrate that we can accurately calculate the next smallest power of ten
// for a given value. our tests show that we are accurate to within about twelve
// decimal places.
// --------------------------------------------------------------------------------
@Test
public void testGettingUnitsInReverseOrder()
{
    List<TimeUnit> upwards = Arrays.asList( TimeUnit.values() );
    logger.debug("upwards.size()=" + upwards.size());

    List<TimeUnit> downwards = new ArrayList<TimeUnit>( upwards );
    Collections.sort( downwards, TimeUnit.MILLISECOND.descending() );
    logger.debug( "downwards.size()=" + downwards.size() );

    Comparator<TimeUnit> comparator = TimeUnit.MILLISECOND.ascending();
    Comparator<TimeUnit> reverseComparator = TimeUnit.MILLISECOND.descending();

    assertNotNull( downwards );
    assertEquals( upwards.size(), downwards.size() );
    
    assertEquals( 0, comparator.compare( TimeUnit.DAY, TimeUnit.DAY ) );
    assertEquals( 1, comparator.compare(TimeUnit.DAY, TimeUnit.MINUTE));
    assertEquals( -1, comparator.compare(TimeUnit.DAY, TimeUnit.MONTH) );

    assertEquals( 0, reverseComparator.compare( TimeUnit.DAY, TimeUnit.DAY ) );
    assertEquals( -1, reverseComparator.compare( TimeUnit.DAY, TimeUnit.MINUTE ) );
    assertEquals( 1, reverseComparator.compare( TimeUnit.DAY, TimeUnit.MONTH ) );

    assertEquals(TimeUnit.MILLISECOND, upwards.get(0));
    assertEquals( TimeUnit.SECOND, upwards.get( 1 ) );
    assertEquals( TimeUnit.MINUTE, upwards.get( 2 ) );
    assertEquals( TimeUnit.HOUR, upwards.get( 3 ) );
    assertEquals( TimeUnit.DAY, upwards.get( 4 ) );
    assertEquals( TimeUnit.WEEK, upwards.get( 5 ) );
    assertEquals( TimeUnit.MONTH, upwards.get( 6 ) );
    assertEquals(TimeUnit.YEAR, upwards.get(7));

    assertEquals( TimeUnit.MILLISECOND, downwards.get( 7 ) );
    assertEquals( TimeUnit.SECOND, downwards.get( 6 ) );
    assertEquals( TimeUnit.MINUTE, downwards.get( 5 ) );
    assertEquals( TimeUnit.HOUR, downwards.get( 4 ) );
    assertEquals( TimeUnit.DAY, downwards.get( 3 ) );
    assertEquals( TimeUnit.WEEK, downwards.get( 2 ) );
    assertEquals( TimeUnit.MONTH, downwards.get( 1 ) );
    assertEquals( TimeUnit.YEAR, downwards.get( 0 ) );
}

public static junit.framework.Test suite()
{
    return new JUnit4TestAdapter( TimeUnitTest.class );
}
    
}