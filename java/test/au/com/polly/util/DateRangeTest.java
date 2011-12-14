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

package au.com.polly.util;

import junit.framework.JUnit4TestAdapter;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertTrue;


/**
 * Exercise the date parser
 */
@RunWith(JUnit4.class)
public class DateRangeTest
{
Logger logger = Logger.getLogger( DateRangeTest.class );
DateParser parser = null;

@Before
public void setUp() throws Exception
{
    parser = new AussieDateParser();
}

@Test(expected=NullPointerException.class)
public void testConstructorWithNullArgs()
{
    new DateRange( (Date)null, (Date)null );    
}

@Test(expected=NullPointerException.class)
public void testConstructorWithNullFromArg()
{
    new DateRange( null, parser.parse( "13/JUN/1968").getTime() );
}

@Test(expected=NullPointerException.class)
public void testConstructorWithNullUntilArg()
{
    new DateRange( parser.parse( "13/JUN/1968").getTime(), null );
}

@Test( expected=IllegalArgumentException.class)
public void testConstructorWithFromArgAfterUntilArg()
{
    new DateRange( "13/JUN/1968", "12/JUN/1968" );
}

@Test( expected=IllegalArgumentException.class )
public void testConstructorWithZeroMillisecondAccuracy()
{
    new DateRange( "13/JUN/1968", "14/JUN/1968", 0L );
}

@Test( expected=IllegalArgumentException.class )
public void testConstructorWithMoreThanOneYearAccuracy()
{
    new DateRange( "13/JUN/1968", "14/JUN/1968", ( 86400000L * 365L ) + 1L );
}

@Test
public void testLegitimateConstructor()
{
    DateRange range = new DateRange( "13/JUN/1968", "14/JUN/1968" );
    assertEquals( parser.parse( "13/JUN/1968 00:00:00.000" ).getTime(), range.from );
    assertEquals( parser.parse( "14/JUN/1968 00:00:00.000" ).getTime(), range.until );
    assertEquals( 86400000, range.span() );
}

@Test( expected=IllegalArgumentException.class )
public void testConstructorSameDateTimesWithOneDefaultAccuracy()
{
    new DateRange( "13/JUN/1968 04:13:59.252", "13/JUN/1968 04:13:59.252" );
}

@Test
public void testConstructingDateUsingSpanDefaultAccuracy()
{
    DateRange range = new DateRange( parser.parse( "13/JUN/1968 04:13:59.252" ).getTime(), 86400000L );
    assertNotNull( range );
    assertEquals( parser.parse( "13/JUN/1968 04:13:59.252" ).getTime(), range.from() );
    assertEquals( parser.parse( "14/JUN/1968 04:13:59.252" ).getTime(), range.until() );
    assertEquals( 86400000L, range.span() );
    assertEquals( "13/JUN/1968 04:13:59.252 - 14/JUN/1968 04:13:59.252", range.toString() );
}

@Test
public void testConstructingDateUsingSpanOneSecondAccuracy()
{
    Date from = parser.parse( "13/JUN/1968 04:13:59.252" ).getTime();
    DateRange range = new DateRange( from, 86400000L, 1000L );
    assertNotNull( range );
    assertEquals( parser.parse( "13/JUN/1968 04:13:59.000" ).getTime(), range.from() );
    assertEquals( parser.parse( "14/JUN/1968 04:13:59.000" ).getTime(), range.until() );
    assertEquals( 86400000L, range.span() );
    assertEquals( "13/JUN/1968 04:13:59 - 14/JUN/1968 04:13:59", range.toString() );
}

@Test
public void testConstructingDateUsingSpanOneMinuteAccuracy()
{
    DateRange range = new DateRange( parser.parse( "13/JUN/1968 04:13:59.252" ).getTime(), 86400000L, 60000L );
    assertNotNull( range );
    assertEquals( parser.parse( "13/JUN/1968 04:13:00.000" ).getTime(), range.from() );
    assertEquals( parser.parse( "14/JUN/1968 04:13:00.000" ).getTime(), range.until() );
    assertEquals( 86400000L, range.span() );
    assertEquals( "13/JUN/1968 04:13 - 14/JUN/1968 04:13", range.toString() );
}

@Test
public void testConstructingDateUsingSpanHourAccuracy()
{
    DateRange range = new DateRange( parser.parse( "13/JUN/1968 04:13:59.252" ).getTime(), 86400000L, 3600000L );
    assertNotNull( range );
    assertEquals( parser.parse( "13/JUN/1968 04:00:00.000" ).getTime(), range.from() );
    assertEquals( parser.parse( "14/JUN/1968 04:00:00.000" ).getTime(), range.until() );
    assertEquals( 86400000L, range.span() );
    assertEquals( "13/JUN/1968 04:00 - 14/JUN/1968 04:00", range.toString() );
}

@Test
public void testConstructingRecentDateRangeUsingSpanHourAccuracy()
{
    DateRange range = new DateRange( parser.parse( "13/JUN/2011 04:13:59.252" ).getTime(), 86400000L, 3600000L );
    assertNotNull( range );
    assertEquals( parser.parse( "13/JUN/2011 04:00:00.000" ).getTime(), range.from() );
    assertEquals( parser.parse( "14/JUN/2011 04:00:00.000" ).getTime(), range.until() );
    assertEquals( 86400000L, range.span() );
    assertEquals( "13/JUN/2011 04:00 - 14/JUN/2011 04:00", range.toString() );
}

@Test
public void testConstructingOneMillisecondRangeWithDefaultAccuracy()
{
    DateRange range = new DateRange( "13/JUN/1968 04:13:59.252", "13/JUN/1968 04:13:59.253" );
    assertNotNull(range);
    assertEquals( 1, range.span());
    assertEquals(parser.parse("13/JUN/1968 04:13:59.252").getTime(), range.from);
    assertEquals( parser.parse( "13/JUN/1968 04:13:59.253" ).getTime(), range.until );
    assertEquals( "13/JUN/1968 04:13:59.252 - 13/JUN/1968 04:13:59.253", range.toString() );
}

@Test
public void testConstructingRecentOneMillisecondRangeWithDefaultAccuracy()
{
    DateRange range = new DateRange( "13/JUN/2011 04:13:59.252", "13/JUN/2011 04:13:59.253" );
    assertNotNull(range);
    assertEquals( 1, range.span());
    assertEquals(parser.parse("13/JUN/2011 04:13:59.252").getTime(), range.from);
    assertEquals( parser.parse( "13/JUN/2011 04:13:59.253" ).getTime(), range.until );
    assertEquals( "13/JUN/2011 04:13:59.252 - 13/JUN/2011 04:13:59.253", range.toString() );
}

@Test
public void testConstructingTenMinuteRangeWithDefaultAccuracy()
{
    DateRange range = new DateRange( "13/JUN/1968 04:13:59.252", "13/JUN/1968 04:23:59.768" );
    assertNotNull(range);
    assertEquals( 600516, range.span());
    assertEquals(parser.parse("13/JUN/1968 04:13:59.252").getTime(), range.from);
    assertEquals( parser.parse( "13/JUN/1968 04:23:59.768" ).getTime(), range.until );
    assertEquals( "13/JUN/1968 04:13:59.252 - 13/JUN/1968 04:23:59.768", range.toString() );
}

@Test
public void testConstructingRecentTenMinuteRangeWithDefaultAccuracy()
{
    DateRange range = new DateRange( "13/JUN/2010 04:13:59.252", "13/JUN/2010 04:23:59.768" );
    assertNotNull(range);
    assertEquals( 600516, range.span());
    assertEquals(parser.parse("13/JUN/2010 04:13:59.252").getTime(), range.from);
    assertEquals( parser.parse( "13/JUN/2010 04:23:59.768" ).getTime(), range.until );
    assertEquals( "13/JUN/2010 04:13:59.252 - 13/JUN/2010 04:23:59.768", range.toString() );
}

@Test
public void testConstructingTenMinuteRangeWithOneSecondAccuracy()
{
    DateRange range = new DateRange( "13/JUN/1968 04:13:59.252", "13/JUN/1968 04:23:59.768", 1000L );
    assertNotNull(range);
    assertEquals( 600000, range.span());
    assertEquals(parser.parse("13/JUN/1968 04:13:59.000").getTime(), range.from);
    assertEquals( parser.parse( "13/JUN/1968 04:23:59.000" ).getTime(), range.until );
    assertEquals( "13/JUN/1968 04:13:59 - 13/JUN/1968 04:23:59", range.toString() );
}

@Test
public void testConstructingRecentTenMinuteRangeWithOneSecondAccuracy()
{
    DateRange range = new DateRange( "13/JUN/2010 04:13:59.252", "13/JUN/2010 04:23:59.768", 1000L );
    assertNotNull(range);
    assertEquals( 600000, range.span());
    assertEquals(parser.parse("13/JUN/2010 04:13:59.000").getTime(), range.from);
    assertEquals( parser.parse( "13/JUN/2010 04:23:59.000" ).getTime(), range.until );
    assertEquals( "13/JUN/2010 04:13:59 - 13/JUN/2010 04:23:59", range.toString() );
}

@Test( expected=IllegalArgumentException.class)
public void testConstructingTenMinuteRangeWithOneHourAccuracy()
{
    DateRange range = new DateRange( "13/JUN/1968 04:13:59.252", "13/JUN/1968 04:23:59.768", 1000L * 3600L );
    assertNotNull(range);
    assertEquals( 600000, range.span());
    assertEquals(parser.parse("13/JUN/1968 04:00:00.000").getTime(), range.from);
    assertEquals( parser.parse( "13/JUN/1968 05:00:00.000" ).getTime(), range.until );
    assertEquals( "13/JUN/1968 04:00 - 13/JUN/1968 05:00", range.toString() );
}


@Test
public void testConstructingRangeWithOneHourAccuracy()
{
    DateRange range = new DateRange( "13/JUN/1968 04:13:59.252", "13/JUN/1968 06:23:59.768", 1000L * 3600L );
    assertNotNull(range);
    assertEquals( 7200000L, range.span());
    assertEquals(parser.parse("13/JUN/1968 04:00:00.000").getTime(), range.from);
    assertEquals( parser.parse( "13/JUN/1968 06:00:00.000" ).getTime(), range.until );
    assertEquals( "13/JUN/1968 04:00 - 13/JUN/1968 06:00", range.toString() );
}

@Test
public void testConstructingRecentRangeWithOneHourAccuracy()
{
    DateRange range = new DateRange( "13/JUN/2011 04:13:59.252", "13/JUN/2011 06:23:59.768", 1000L * 3600L );
    assertNotNull(range);
    assertEquals( 7200000L, range.span());
    assertEquals(parser.parse("13/JUN/2011 04:00:00.000").getTime(), range.from);
    assertEquals( parser.parse( "13/JUN/2011 06:00:00.000" ).getTime(), range.until );
    assertEquals( "13/JUN/2011 04:00 - 13/JUN/2011 06:00", range.toString() );
}


@Test(expected=NullPointerException.class)
public void testContainsDateWithNullArgs() throws Exception
{
    DateRange range = new DateRange( parser.parse( "12/JUN/1968" ).getTime(), parser.parse( "13/JUN/1968" ).getTime()  );
    range.contains( (Date)null );
}

@Test
public void testContainsDateWithCandidateBeforeRange()
{
    DateRange range = new DateRange( "13/JUN/1968 04:13:59.252", "13/JUN/1968 04:13:59.253" );
    assertFalse(range.contains(parser.parse("13/JUN/1968 04:13:59.251").getTime()));
}

@Test
public void testContainsDateWithCandidateAtStartOfRange()
{
    DateRange range = new DateRange( "13/JUN/1968 04:13:59.252", "13/JUN/1968 04:13:59.253" );
    assertTrue(range.contains(parser.parse("13/JUN/1968 04:13:59.252").getTime()));
}

@Test
public void testContainsDateWithCandidateAtEndOfRange()
{
    DateRange range = new DateRange( "13/JUN/1968 04:13:59.252", "13/JUN/1968 04:13:59.253" );
    assertFalse(range.contains(parser.parse("13/JUN/1968 04:13:59.253").getTime()));
}

@Test
public void testContainsDateAtStartOfRangeWithOneSecondPrecision()
{
    DateRange range = new DateRange( "13/JUN/1968 04:13:59.252", "13/JUN/1968 04:14:00.245", 1000L );
    assertEquals( parser.parse( "13/JUN/1968 04:13:59.000" ).getTime(), range.from );
    assertEquals( parser.parse( "13/JUN/1968 04:14:00.000" ).getTime(), range.until );
    assertEquals( "13/JUN/1968 04:13:59 - 13/JUN/1968 04:14:00", range.toString() );
    assertTrue( range.contains( parser.parse( "13/JUN/1968 04:13:59.999" ).getTime() ) );
    assertTrue( range.contains( parser.parse( "13/JUN/1968 04:13:59.000" ).getTime() ) );
    assertFalse( range.contains( parser.parse( "13/JUN/1968 04:13:58.999" ).getTime() ) );
    assertFalse(range.contains(parser.parse("13/JUN/1968 04:14:00.000").getTime()));
}

@Test( expected=NullPointerException.class)
public void testContainsDateRangeWithNullCandidate()
{
    DateRange range = new DateRange( "13/JUN/1968 04:13:59.252", "13/JUN/1968 04:14:00.245", 1000L );
    range.contains((DateRange) null);
}

@Test
public void testContainsDateRangeWithDefaultAccuracy()
{
    DateRange range = new DateRange( "13/JUN/1968 04:13:59.252", "13/JUN/1968 04:14:00.245" );
    DateRange other = new DateRange( "13/JUN/1968 04:13:59.252", "13/JUN/1968 04:14:00.245" );
    assertTrue( range.contains( other ) );
    
    other = new DateRange( "13/JUN/1968 04:13:59.252", "13/JUN/1968 04:14:00.246" );
    assertFalse( range.contains( other ) );
}

@Test
public void testContainsDateRangeWithOneSecondAccuracy()
{
    DateRange range = new DateRange( "13/JUN/1968 04:13:59.252", "13/JUN/1968 04:14:00.245", 1000L );
    DateRange other = new DateRange( "13/JUN/1968 04:13:59.252", "13/JUN/1968 04:14:00.245" );
    assertFalse(range.contains(other));

    other = new DateRange( "13/JUN/1968 04:13:59.252", "13/JUN/1968 04:14:00.245", 1000L );
    assertTrue( range.contains( other ) );
}

@Test(expected=NullPointerException.class)
public void testOverlapWithNullCandidateRange()
{
    DateRange range = new DateRange( "13/JUN/1968 04:13:59.252", "13/JUN/1968 04:14:00.245", 1000L );
    range.overlap( null );
}

@Test
public void testOverlapWithCandidateTotallyBeforeRange()
{
    DateRange range = new DateRange( "15/NOV/2011 16:00", "07/DEC/2011 04:00", 1000L );
    DateRange other = new DateRange( "12/NOV/2011", "15/NOV/2011" );
    assertEquals( 0, range.overlap( other ) );
}

@Test
public void testOverlapWithCandidateTotallyAfterRange()
{
    DateRange range = new DateRange( "15/NOV/2011 16:00", "07/DEC/2011 04:00", 1000L );
    DateRange other = new DateRange( "12/DEC/2011", "15/DEC/2011" );
    assertEquals( 0, range.overlap( other ) );
}

@Test
public void testOverlapWithCandidateOverStartInterval()
{
    DateRange range = new DateRange( "15/NOV/2011 16:00", "07/DEC/2011 04:00", 1000L );
    DateRange other = new DateRange( "15/NOV/2011 12:00", "16/NOV/2011", 1000L );
    assertEquals( 1000L * 3600L * 8, range.overlap( other ) );
}

@Test
public void testOverlapWithCandidateWithinRange()
{
    DateRange range = new DateRange( "15/NOV/2011 16:00", "07/DEC/2011 04:00", 1000L );
    DateRange other = new DateRange( "16/NOV/2011", "24/NOV/2011", 1000L );
    assertEquals( 1000L * 86400L * 8, range.overlap( other ) );
}

@Test
public void testOverlapWithCandidateStraddlingEndDate()
{
    DateRange range = new DateRange( "15/NOV/2011 16:00", "07/DEC/2011 04:00", 1000L );
    DateRange other = new DateRange( "06/DEC/2011", "08/DEC/2011", 1000L );
    assertEquals( 1000L * 3600L * 28, range.overlap( other ) );
}

@Test
public void testOverlapWithCandidateEncompassingRange()
{
    DateRange range = new DateRange( "15/NOV/2011 16:00", "07/DEC/2011 04:00", 1000L );
    DateRange other = new DateRange( "15/NOV/2011", "08/DEC/2011", 1000L );
    assertEquals( ( ( 21 * 24 ) + 12  ) * 3600L * 1000L, range.overlap( other ) );
}

@Test
public void testSerialization()
{
    DateRange birthRange = new DateRange( "13/JUNE/1968 04:15:23.582", "13/JUNE/1968 05:15:23.582" );
    File objFile = new File( "test_data/blah.obj" );
    FileOutputStream fos;
    FileInputStream fis;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    try
    {
        fos = new FileOutputStream( objFile );
        oos = new ObjectOutputStream( fos );
        oos.writeObject( birthRange );
        oos.close();
        fos.close();
    } catch (IOException e) {
        logger.error( "Failed to write out DateRange object!!" );
        logger.error( e.getClass().getName() + " - " + e.getMessage() );
        fail("Failed to write out object file!");
    }

    DateRange readRange = null;
    try
    {
        fis = new FileInputStream( objFile );
        ois = new ObjectInputStream( fis );
        readRange = (DateRange)ois.readObject();
        ois.close();
        fis.close();
    } catch (IOException e)
    {
        logger.error( "Failed to read in DateRange object!!" );
        logger.error( e.getClass().getName() + " - " + e.getMessage() );

        fail( "Failed to read date range object from file." );
    } catch (ClassNotFoundException e)
    {
        fail( "Object in file was not a DateRange object!!" );
    }
    
    assertNotNull( readRange );
    assertEquals( birthRange, readRange );
    assertEquals( birthRange.hashCode(), readRange.hashCode() );

    objFile.delete();
}


public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( DateRangeTest.class );
}

}
