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

package au.com.polly.ddr;

import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import au.com.polly.util.DateRange;
import junit.framework.JUnit4TestAdapter;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 23/11/11
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith( JUnit4.class )
public class GasWellDataBoundaryTest
{
DateParser parser;

public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( GasWellDataBoundaryTest.class );
}

@Before
public void setupData()
{
    parser = new AussieDateParser();
}


@Test(expected=NullPointerException.class)
public void testConstructorWithNullDate()
{
    new GasWellDataBoundary( null, null, null );
}

@Test(expected=NullPointerException.class)
public void testConstructorWithNullComment()
{
    new GasWellDataBoundary( parser.parse( "13/JUN/2012" ).getTime(), null, null );
}

@Test(expected=IllegalArgumentException.class)
public void testConstructorWithEmptyComment()
{
    new GasWellDataBoundary( parser.parse( "13/JUN/2012" ).getTime(), " ", null );
}

@Test
public void testRegularConstructor()
{
    GasWellDataBoundary boundary = new GasWellDataBoundary( parser.parse( "12/JAN/2012 16:13:14" ).getTime(), "just a test", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_END );
    assertNotNull( boundary );
    assertEquals( parser.parse( "12/JAN/2012 16:13:14").getTime(), boundary.getTimestamp() );
    assertEquals( "just a test", boundary.getComment() );
    assertEquals( GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_END, boundary.getBoundaryType() );
}

@Test
public void testEquals()
{
    GasWellDataBoundary alpha = new GasWellDataBoundary( parser.parse( "12/JAN/2012 16:13:14" ).getTime(), "just a test", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_END );    
    GasWellDataBoundary beta = new GasWellDataBoundary( parser.parse( "12/JAN/2012 16:13:14" ).getTime(), "just a test", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_END );
    
    assertEquals( alpha, beta );
    assertEquals( alpha.hashCode(), beta.hashCode() );

    alpha = new GasWellDataBoundary( parser.parse( "12/JAN/2012 16:13:14" ).getTime(), "just a test", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_START );
    assertFalse( alpha.equals( beta ) );
    assertFalse( alpha.hashCode() == beta.hashCode() );

    beta = new GasWellDataBoundary( parser.parse( "12/JAN/2012 16:13:14" ).getTime(), "just a test", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_START );
    assertEquals( alpha, beta );
    assertEquals( alpha.hashCode(), beta.hashCode() );


    alpha = new GasWellDataBoundary( parser.parse( "12/JAN/2012 16:13:14" ).getTime(), "just testing", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_START );
    assertFalse( alpha.equals( beta ) );
    assertFalse( alpha.hashCode() == beta.hashCode() );

    beta = new GasWellDataBoundary( parser.parse( "12/JAN/2012 16:13:14" ).getTime(), "just testing", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_START );
    assertEquals( alpha, beta );
    assertEquals( alpha.hashCode(), beta.hashCode() );


    alpha = new GasWellDataBoundary( parser.parse( "12/JAN/2012 16:13" ).getTime(), "just testing", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_START );
    assertFalse( alpha.equals( beta ) );
    assertFalse( alpha.hashCode() == beta.hashCode() );

    beta = new GasWellDataBoundary( parser.parse( "12/JAN/2012 16:13" ).getTime(), "just testing", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_START );
    assertEquals( alpha, beta );
    assertEquals( alpha.hashCode(), beta.hashCode() );
}

@Test(expected = NullPointerException.class)
public void testMergingListWithNullLists()
{
    GasWellDataBoundary.merge( null, null );
}

@Test(expected = NullPointerException.class)
public void testMergingListWithNullAlphaList()
{
    GasWellDataBoundary.merge( null, new ArrayList<GasWellDataBoundary>() );
}

@Test(expected = NullPointerException.class)
public void testMergingListWithNullBetaList()
{
    GasWellDataBoundary.merge( new ArrayList<GasWellDataBoundary>(), null );
}

@Test(expected = IllegalArgumentException.class)
public void testMergingListWithEmptyLists()
{
    GasWellDataBoundary.merge( new ArrayList<GasWellDataBoundary>(), new ArrayList<GasWellDataBoundary>() );
}

@Test
public void testMergingOneElementListWithEmptyList()
{
    GasWellDataBoundary boundary = new GasWellDataBoundary( parser.parse( "13/JAN/2012 06:00" ).getTime(), "blah", GasWellDataBoundary.BoundaryType.START );
    List<GasWellDataBoundary> list = new ArrayList<GasWellDataBoundary>();
    list.add( boundary );
    List<GasWellDataBoundary> result = GasWellDataBoundary.merge( list, new ArrayList<GasWellDataBoundary>() );
    assertNotNull( result );
    assertEquals( 1, result.size() );
    assertEquals(boundary, result.get(0));
}

@Test
public void testMergingTwoOneElementListsWithSameDate()
{
    GasWellDataBoundary a = new GasWellDataBoundary( parser.parse( "13/JAN/2012 06:00" ).getTime(), "blah", GasWellDataBoundary.BoundaryType.START );
    GasWellDataBoundary b = new GasWellDataBoundary( parser.parse( "13/JAN/2012 06:00" ).getTime(), "blech", GasWellDataBoundary.BoundaryType.END );
    List<GasWellDataBoundary> listAlpha = new ArrayList<GasWellDataBoundary>();
    List<GasWellDataBoundary> listBeta = new ArrayList<GasWellDataBoundary>();
    listAlpha.add( a );
    listBeta.add( b );
    List<GasWellDataBoundary> result = GasWellDataBoundary.merge( listAlpha, listBeta );
    assertNotNull( result );
    assertEquals( 1, result.size() );
    assertEquals(a, result.get(0));
}

@Test
public void testMergingTwoOneElementListsWithDifferentDates()
{
    GasWellDataBoundary a = new GasWellDataBoundary( parser.parse( "13/JAN/2012 06:00:01" ).getTime(), "blah", GasWellDataBoundary.BoundaryType.START );
    GasWellDataBoundary b = new GasWellDataBoundary( parser.parse( "13/JAN/2012 06:00:02" ).getTime(), "blech", GasWellDataBoundary.BoundaryType.END );
    List<GasWellDataBoundary> listAlpha = new ArrayList<GasWellDataBoundary>();
    List<GasWellDataBoundary> listBeta = new ArrayList<GasWellDataBoundary>();
    listAlpha.add( a );
    listBeta.add( b );
    List<GasWellDataBoundary> result = GasWellDataBoundary.merge( listAlpha, listBeta );
    assertNotNull( result );
    assertEquals(2, result.size());
    assertEquals( a, result.get(0) );
    assertEquals(b, result.get(1));
}

@Test
public void testMergingTwoOneElementListsWithDifferentDatesAroundTheOtherWay()
{
    GasWellDataBoundary a = new GasWellDataBoundary( parser.parse( "13/JAN/2012 06:00:03" ).getTime(), "blah", GasWellDataBoundary.BoundaryType.START );
    GasWellDataBoundary b = new GasWellDataBoundary( parser.parse( "13/JAN/2012 06:00:02" ).getTime(), "blech", GasWellDataBoundary.BoundaryType.END );
    List<GasWellDataBoundary> listAlpha = new ArrayList<GasWellDataBoundary>();
    List<GasWellDataBoundary> listBeta = new ArrayList<GasWellDataBoundary>();
    listAlpha.add( a );
    listBeta.add( b );
    List<GasWellDataBoundary> result = GasWellDataBoundary.merge( listAlpha, listBeta );
    assertNotNull( result );
    assertEquals(2, result.size());
    assertEquals( a, result.get(1) );
    assertEquals( b, result.get( 0 ) );
}

@Test
public void testMergingTwoListsWithFiveBoundariesEach()
{
    GasWellDataBoundary a0 = new GasWellDataBoundary( parser.parse( "13/JAN/2012 06:00:03" ).getTime(), "start", GasWellDataBoundary.BoundaryType.START );
    GasWellDataBoundary a1 = new GasWellDataBoundary( parser.parse( "14/JAN/2012 06:00:03" ).getTime(), "day two", GasWellDataBoundary.BoundaryType.AUTOMATED_REGULAR_BOUNDARY );
    GasWellDataBoundary a2 = new GasWellDataBoundary( parser.parse( "15/JAN/2012 06:00:03" ).getTime(), "day three", GasWellDataBoundary.BoundaryType.AUTOMATED_REGULAR_BOUNDARY );
    GasWellDataBoundary a3 = new GasWellDataBoundary( parser.parse( "16/JAN/2012 06:00:03" ).getTime(), "day four", GasWellDataBoundary.BoundaryType.AUTOMATED_REGULAR_BOUNDARY );
    GasWellDataBoundary a4 = new GasWellDataBoundary( parser.parse( "17/JAN/2012 06:00:03" ).getTime(), "day five", GasWellDataBoundary.BoundaryType.END );

    GasWellDataBoundary b0 = new GasWellDataBoundary( parser.parse( "13/JAN/2012 18:45:47" ).getTime(), "start maintenance", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_START );
    GasWellDataBoundary b1 = new GasWellDataBoundary( parser.parse( "14/JAN/2012 05:59:59" ).getTime(), "end maintenance", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_END );
    GasWellDataBoundary b2 = new GasWellDataBoundary( parser.parse( "15/JAN/2012 08:43:17" ).getTime(), "there she blows!", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_MEDIAN_CROSSING );
    GasWellDataBoundary b3 = new GasWellDataBoundary( parser.parse( "17/JAN/2012 08:45:16" ).getTime(), "pipe broken", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_START );
    GasWellDataBoundary b4 = new GasWellDataBoundary( parser.parse( "17/JAN/2012 13:35:28" ).getTime(), "pipe fixed", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_END );

    List<GasWellDataBoundary> listAlpha = new ArrayList<GasWellDataBoundary>();
    List<GasWellDataBoundary> listBeta = new ArrayList<GasWellDataBoundary>();
    listAlpha.add( a0 );
    listAlpha.add( a1 );
    listAlpha.add( a2 );
    listAlpha.add( a3 );
    listAlpha.add( a4 );
    listBeta.add( b0 );
    listBeta.add( b1 );
    listBeta.add( b2 );
    listBeta.add( b3 );
    listBeta.add( b4 );
    List<GasWellDataBoundary> result = GasWellDataBoundary.merge( listAlpha, listBeta );
    assertNotNull(result);
    assertEquals(10, result.size());
    assertEquals(a0, result.get(0));
    assertEquals(b0, result.get(1));
    assertEquals(b1, result.get(2));
    assertEquals(a1, result.get(3));
    assertEquals(a2, result.get(4));
    assertEquals(b2, result.get(5));
    assertEquals(a3, result.get(6));
    assertEquals( a4, result.get(7) );
    assertEquals( b3, result.get( 8 ) );
    assertEquals( b4, result.get( 9 ) );
}

@Test
public void testMergingTwoListsWithFiveBoundariesEachAndTwoClashes()
{
    GasWellDataBoundary a0 = new GasWellDataBoundary( parser.parse( "13/JAN/2012 06:00:03" ).getTime(), "start", GasWellDataBoundary.BoundaryType.START );
    GasWellDataBoundary a1 = new GasWellDataBoundary( parser.parse( "14/JAN/2012 06:00:03" ).getTime(), "day two", GasWellDataBoundary.BoundaryType.AUTOMATED_REGULAR_BOUNDARY );
    GasWellDataBoundary a2 = new GasWellDataBoundary( parser.parse( "15/JAN/2012 06:00:03" ).getTime(), "day three", GasWellDataBoundary.BoundaryType.AUTOMATED_REGULAR_BOUNDARY );
    GasWellDataBoundary a3 = new GasWellDataBoundary( parser.parse( "16/JAN/2012 06:00:03" ).getTime(), "day four", GasWellDataBoundary.BoundaryType.AUTOMATED_REGULAR_BOUNDARY );
    GasWellDataBoundary a4 = new GasWellDataBoundary( parser.parse( "17/JAN/2012 06:00:03" ).getTime(), "day five", GasWellDataBoundary.BoundaryType.END );

    GasWellDataBoundary b0 = new GasWellDataBoundary( parser.parse( "13/JAN/2012 18:45:47" ).getTime(), "start maintenance", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_START );
    GasWellDataBoundary b1 = new GasWellDataBoundary( parser.parse( "14/JAN/2012 05:59:59" ).getTime(), "end maintenance", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_END );
    GasWellDataBoundary b2 = new GasWellDataBoundary( parser.parse( "15/JAN/2012 06:00:03" ).getTime(), "there she blows!", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_MEDIAN_CROSSING );
    GasWellDataBoundary b3 = new GasWellDataBoundary( parser.parse( "17/JAN/2012 06:00:03" ).getTime(), "pipe broken", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_START );
    GasWellDataBoundary b4 = new GasWellDataBoundary( parser.parse( "17/JAN/2012 13:35:28" ).getTime(), "pipe fixed", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_END );

    List<GasWellDataBoundary> listAlpha = new ArrayList<GasWellDataBoundary>();
    List<GasWellDataBoundary> listBeta = new ArrayList<GasWellDataBoundary>();
    listAlpha.add( a0 );
    listAlpha.add( a1 );
    listAlpha.add( a2 );
    listAlpha.add( a3 );
    listAlpha.add( a4 );
    listBeta.add( b0 );
    listBeta.add( b1 );
    listBeta.add( b2 );
    listBeta.add( b3 );
    listBeta.add( b4 );
    List<GasWellDataBoundary> result = GasWellDataBoundary.merge( listAlpha, listBeta );
    assertNotNull(result);
    
    assertFalse(result.contains(b2));
    assertFalse(result.contains(b3));

    assertEquals( 8, result.size());
    assertEquals( a0, result.get( 0 ) );
    assertEquals( b0, result.get( 1 ) );
    assertEquals( b1, result.get( 2 ) );
    assertEquals( a1, result.get( 3 ) );
    assertEquals( a2, result.get( 4 ) );
    assertEquals( a3, result.get( 5 ) );
    assertEquals( a4, result.get( 6 ) );
    assertEquals( b4, result.get( 7 ) );
}

@Test( expected = NullPointerException.class )
public void testNullListContainsDate()
{
    List<GasWellDataBoundary> listAlpha = null;
    
    GasWellDataBoundary.listContains( listAlpha, parser.parse( "13/JUN/2012 05:24" ).getTime() );
   
}

@Test( expected=IllegalArgumentException.class)
public void testEmptyListContainsDate()
{
    List<GasWellDataBoundary> listAlpha = new ArrayList<GasWellDataBoundary>();
    assertFalse(GasWellDataBoundary.listContains(listAlpha, parser.parse("13/JUN/2012 05:24").getTime()));
}

@Test
public void singleElementListContainsDateExactly()
{
    List<GasWellDataBoundary> listAlpha = new ArrayList<GasWellDataBoundary>();
    listAlpha.add( new GasWellDataBoundary( parser.parse( "13/JUN/2012 05:24" ).getTime(), "hello", GasWellDataBoundary.BoundaryType.AUTOMATED_REGULAR_BOUNDARY ) );
    assertTrue(GasWellDataBoundary.listContains(listAlpha, parser.parse("13/JUN/2012 05:24").getTime()));
}

@Test
public void listContainsDateExactly()
{
    List<GasWellDataBoundary> listAlpha = new ArrayList<GasWellDataBoundary>();
    listAlpha.add( new GasWellDataBoundary( parser.parse( "11/JUN/2012 05:24" ).getTime(), "hello", GasWellDataBoundary.BoundaryType.AUTOMATED_REGULAR_BOUNDARY ) );
    listAlpha.add(new GasWellDataBoundary(parser.parse("12/JUN/2012 05:24").getTime(), "hello", GasWellDataBoundary.BoundaryType.AUTOMATED_REGULAR_BOUNDARY));
    listAlpha.add( new GasWellDataBoundary( parser.parse( "13/JUN/2012 05:24:13.456" ).getTime(), "hello", GasWellDataBoundary.BoundaryType.AUTOMATED_REGULAR_BOUNDARY ) );
    listAlpha.add( new GasWellDataBoundary( parser.parse( "14/JUN/2012 05:24" ).getTime(), "hello", GasWellDataBoundary.BoundaryType.AUTOMATED_REGULAR_BOUNDARY ) );
    assertTrue(GasWellDataBoundary.listContains(listAlpha, parser.parse("13/JUN/2012 05:24:13.456").getTime()));
}

@Test
public void listContainsDateOutByOneMillisecond()
{
    List<GasWellDataBoundary> listAlpha = new ArrayList<GasWellDataBoundary>();
    listAlpha.add( new GasWellDataBoundary( parser.parse( "11/JUN/2012 05:24" ).getTime(), "hello", GasWellDataBoundary.BoundaryType.AUTOMATED_REGULAR_BOUNDARY ) );
    listAlpha.add(new GasWellDataBoundary(parser.parse("12/JUN/2012 05:24").getTime(), "hello", GasWellDataBoundary.BoundaryType.AUTOMATED_REGULAR_BOUNDARY));
    listAlpha.add( new GasWellDataBoundary( parser.parse( "13/JUN/2012 05:24:13.456" ).getTime(), "hello", GasWellDataBoundary.BoundaryType.AUTOMATED_REGULAR_BOUNDARY ) );
    listAlpha.add( new GasWellDataBoundary( parser.parse( "14/JUN/2012 05:24" ).getTime(), "hello", GasWellDataBoundary.BoundaryType.AUTOMATED_REGULAR_BOUNDARY ) );
    assertFalse(GasWellDataBoundary.listContains(listAlpha, parser.parse("13/JUN/2012 05:24:13.455").getTime()));
}

@Test( expected = NullPointerException.class )
public void testExtractingTimestampArrayFromNullBoundaryList()
{
    long[] stamps = GasWellDataBoundary.getTimestampList( null );
}

@Test( expected = IllegalArgumentException.class )
public void testExtractingTimestampArrayFromEmptyBoundaryList()
{
    long[] stamps = GasWellDataBoundary.getTimestampList( new ArrayList<GasWellDataBoundary>() );
}

@Test
public void testExtractingTimestampArrayFromSingleElementBoundaryList()
{
    List<GasWellDataBoundary> list = new ArrayList<GasWellDataBoundary>();
    list.add( new GasWellDataBoundary( parser.parse( "13/JAN/2012 06:00:03" ).getTime(), "start", GasWellDataBoundary.BoundaryType.START ) );

    long[] stamps = GasWellDataBoundary.getTimestampList( list );
    assertNotNull( stamps );
    assertEquals( 1, stamps.length );
    assertEquals( parser.parse( "13/JAN/2012 06:00:03" ).getTime().getTime(), stamps[ 0 ] );
}

@Test
public void testExtractingTimestampArrayFromLargeBoundaryList()
{
    List<GasWellDataBoundary> list = new ArrayList<GasWellDataBoundary>();
    list.add( new GasWellDataBoundary( parser.parse( "13/JAN/2012 06:00:03" ).getTime(), "start", GasWellDataBoundary.BoundaryType.START ) );
    list.add( new GasWellDataBoundary( parser.parse( "13/JAN/2012 18:45:47" ).getTime(), "start maintenance", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_START ) );
    list.add( new GasWellDataBoundary( parser.parse( "14/JAN/2012 05:59:59" ).getTime(), "end maintenance", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_END ) );
    list.add( new GasWellDataBoundary( parser.parse( "14/JAN/2012 06:00:03" ).getTime(), "day two", GasWellDataBoundary.BoundaryType.AUTOMATED_REGULAR_BOUNDARY ) );
    list.add( new GasWellDataBoundary( parser.parse( "15/JAN/2012 06:00:03" ).getTime(), "day three", GasWellDataBoundary.BoundaryType.AUTOMATED_REGULAR_BOUNDARY ) );
    list.add( new GasWellDataBoundary( parser.parse( "15/JAN/2012 08:43:17" ).getTime(), "there she blows!", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_MEDIAN_CROSSING ) );
    list.add( new GasWellDataBoundary( parser.parse( "16/JAN/2012 06:00:03" ).getTime(), "day four", GasWellDataBoundary.BoundaryType.AUTOMATED_REGULAR_BOUNDARY ) );
    list.add( new GasWellDataBoundary( parser.parse( "17/JAN/2012 06:00:03" ).getTime(), "day five", GasWellDataBoundary.BoundaryType.END ) );
    list.add( new GasWellDataBoundary( parser.parse( "17/JAN/2012 08:45:16" ).getTime(), "pipe broken", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_START ) );
    list.add( new GasWellDataBoundary( parser.parse( "17/JAN/2012 13:35:28" ).getTime(), "pipe fixed", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_END ) );
    
    long[] stamps = GasWellDataBoundary.getTimestampList( list );
    assertNotNull( stamps );
    assertEquals( 10, stamps.length);
    assertEquals( parser.parse( "13/JAN/2012 06:00:03" ).getTime().getTime(), stamps[0]);
    assertEquals( parser.parse( "13/JAN/2012 18:45:47" ).getTime().getTime(), stamps[1]);
    assertEquals( parser.parse( "14/JAN/2012 05:59:59" ).getTime().getTime(), stamps[2]);
    assertEquals( parser.parse( "14/JAN/2012 06:00:03" ).getTime().getTime(), stamps[3]);
    assertEquals( parser.parse( "15/JAN/2012 06:00:03" ).getTime().getTime(), stamps[4]);
    assertEquals( parser.parse( "15/JAN/2012 08:43:17" ).getTime().getTime(), stamps[5]);
    assertEquals( parser.parse( "16/JAN/2012 06:00:03" ).getTime().getTime(), stamps[6]);
    assertEquals( parser.parse( "17/JAN/2012 06:00:03" ).getTime().getTime(), stamps[7]);
    assertEquals( parser.parse( "17/JAN/2012 08:45:16" ).getTime().getTime(), stamps[ 8 ] );
    assertEquals( parser.parse( "17/JAN/2012 13:35:28" ).getTime().getTime(), stamps[ 9 ] );
}

}