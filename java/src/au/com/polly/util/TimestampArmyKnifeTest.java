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
import org.junit.Test;

import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Tests to make sure that the timestamp army knife tests parses and formats timestamps
 * as expected. Getting date/time values parsed and formatted correctly is a big challenge
 * we face as an organisation. 
 *
 */
public class TimestampArmyKnifeTest
{

    @Test
    public void testCreatingDefault()
    {
        TimestampArmyKnife knife = new TimestampArmyKnife();
        TimeZone dephault = TimeZone.getDefault();
        TimeZone other = knife.__unit_test_backdoor_getTimeZone();
        assertEquals( dephault, other );
    }

    @Test
    public void testFormattingZeroHourWithGMT()
    {
        TimestampArmyKnife knife = new TimestampArmyKnife();
        TimeZone gmt = null;
        String representation = null;
        gmt = TimeZone.getTimeZone("GMT");
        assertNotNull( gmt );
        knife.setTimeZone( gmt );
        representation = knife.formatStamp( 0L );
        assertEquals( "01/JAN/70 00:00:00.000", representation );
    }

    @Test
    public void testFormattingZeroHourWithGMTNoLeadingZero()
    {
        TimestampArmyKnife knife = new TimestampArmyKnife();
        TimeZone gmt = null;
        String representation = null;
        gmt = TimeZone.getTimeZone("GMT");
        assertNotNull( gmt );
        knife.setTimeZone( gmt );
        representation = knife.formatStamp( 0L );
        assertEquals( "01/JAN/70 00:00:00.000", representation );
    }

    @Test
    public void testFormattingZeroHourAsNY()
    {
        TimestampArmyKnife knife = new TimestampArmyKnife();
        TimeZone ny = null;
        String representation = null;
        ny = TimeZone.getTimeZone("EST");
        assertNotNull( ny );
        knife.setTimeZone( ny );
        representation = knife.formatStamp( 0L );
        assertEquals( "31/DEC/69 19:00:00.000", representation );
    }

    @Test
    public void testFormattingZeroHourAsSydney()
    {
        TimestampArmyKnife knife = new TimestampArmyKnife();
        TimeZone syd = null;
        String representation = null;
        syd = TimeZone.getTimeZone("Australia/Sydney");
        assertNotNull( syd );
        knife.setTimeZone( syd );
        representation = knife.formatStamp( 0L );
        assertEquals( "01/JAN/70 10:00:00.000", representation );

    }

    @Test
    public void testParsingZeroHourWithGMT()
    {
        TimestampArmyKnife knife = new TimestampArmyKnife();
        TimeZone gmt = null;
        long stamp;
        gmt = TimeZone.getTimeZone("GMT");
        assertNotNull( gmt );
        knife.setTimeZone( gmt );

        // ok, let's try smack on 1st jan 1970
        stamp = knife.calculateStamp( "01", "01", "70", "00", "00", "00" );
        assertEquals( 0L, stamp );

        stamp = knife.calculateStamp( "01", "JAN", "70", "00", "00", "00" );
        assertEquals( 0L, stamp );

        // now one second past...
        stamp = knife.calculateStamp( "01", "01", "70", "00", "00", "01" );
        assertEquals( 1000L, stamp );

        stamp = knife.calculateStamp( "01", "JAN", "70", "00", "00", "01" );
        assertEquals( 1000L, stamp );

        // now one day and one second past...
        stamp = knife.calculateStamp( "02", "01", "70", "00", "00", "01" );
        assertEquals( 86401000L, stamp );

        stamp = knife.calculateStamp( "02", "JAN", "70", "00", "00", "01" );
        assertEquals( 86401000L, stamp );

    }

    @Test
    public void testParsingZeroHourAsNY()
    {
        TimestampArmyKnife knife = new TimestampArmyKnife();
        TimeZone gmt = null;
        long stamp;
        gmt = TimeZone.getTimeZone("EST");
        assertNotNull( gmt );
        knife.setTimeZone( gmt );

        // ok, let's try smack on 1st jan 1970
        stamp = knife.calculateStamp( "31", "12", "69", "19", "00", "00" );
        assertEquals( 0L, stamp );

        stamp = knife.calculateStamp( "31", "DEC", "69", "19", "00", "00" );
        assertEquals( 0L, stamp );

        // now one second past...
        stamp = knife.calculateStamp( "31", "DEC", "69", "19", "00", "01" );
        assertEquals( 1000L, stamp );

        stamp = knife.calculateStamp( "31", "DEC", "69", "19", "00", "01" );
        assertEquals( 1000L, stamp );

        // now one day and one second past...
        stamp = knife.calculateStamp( "01", "01", "70", "19", "00", "01" );
        assertEquals( 86401000L, stamp );

        stamp = knife.calculateStamp( "01", "JAN", "70", "19", "00", "01" );
        assertEquals( 86401000L, stamp );

    }

    @Test
    public void testParsingZeroHourAsSydney()
    {
        TimestampArmyKnife knife = new TimestampArmyKnife();
        TimeZone syd = null;
        long stamp;
        syd = TimeZone.getTimeZone("Australia/Sydney");
        assertNotNull( syd );
        knife.setTimeZone( syd );

        // ok, let's try smack on 1st jan 1970
        stamp = knife.calculateStamp( "01", "01", "70", "10", "00", "00" );
        assertEquals( 0L, stamp );

        stamp = knife.calculateStamp( "01", "JAN", "70", "10", "00", "00" );
        assertEquals( 0L, stamp );

        // now one second past...
        stamp = knife.calculateStamp( "01", "01", "70", "10", "00", "01" );
        assertEquals( 1000L, stamp );

        stamp = knife.calculateStamp( "01", "JAN", "70", "10", "00", "01" );
        assertEquals( 1000L, stamp );

        // now one day and one second past...
        stamp = knife.calculateStamp( "02", "01", "70", "10", "00", "01" );
        assertEquals( 86401000L, stamp );

        stamp = knife.calculateStamp( "02", "JAN", "70", "10", "00", "01" );
        assertEquals( 86401000L, stamp );

    }

    @Test
    public void testFormattingBoysBirthdayWithGMT()
    {
        TimestampArmyKnife knife = new TimestampArmyKnife();
        TimeZone gmt = null;
        String representation = null;
        gmt = TimeZone.getTimeZone("GMT");
        assertNotNull( gmt );
        knife.setTimeZone( gmt );
        representation = knife.formatStamp( 1023422400000L );
        assertEquals( "07/JUN/02 04:00:00.000", representation );
    }

    @Test
    public void testFormattingBoysBirthdayAsNY()
    {
        TimestampArmyKnife knife = new TimestampArmyKnife();
        TimeZone ny = null;
        String representation = null;
        ny = TimeZone.getTimeZone("EST");
        assertNotNull( ny );
        knife.setTimeZone( ny );
        representation = knife.formatStamp( 1023422400000L );
        assertEquals( "06/JUN/02 23:00:00.000", representation );
    }

    @Test
    public void testFormattingBoysBirthdayAsSydney()
    {
        TimestampArmyKnife knife = new TimestampArmyKnife();
        TimeZone syd = null;
        String representation = null;
        syd = TimeZone.getTimeZone("Australia/Sydney");
        assertNotNull( syd );
        knife.setTimeZone( syd );
        representation = knife.formatStamp( 1023422400000L );
        assertEquals( "07/JUN/02 14:00:00.000", representation );

    }

    @Test
    public void testParsingBoysBirthdayWithGMT()
    {
        TimestampArmyKnife knife = new TimestampArmyKnife();
        TimeZone gmt = null;
        long stamp;
        gmt = TimeZone.getTimeZone("GMT");
        assertNotNull( gmt );
        knife.setTimeZone( gmt );

        // ok, let's try smack on 1st jan 1970
        stamp = knife.calculateStamp( "07", "06", "02", "04", "00", "00" );
        assertEquals( 1023422400000L, stamp );

        stamp = knife.calculateStamp( "07", "JUN", "02", "04", "00", "00" );
        assertEquals( 1023422400000L, stamp );

        // now one second past...
        stamp = knife.calculateStamp( "07", "06", "02", "04", "00", "01" );
        assertEquals( 1023422401000L, stamp );

        stamp = knife.calculateStamp( "07", "JUN", "02", "04", "00", "01" );
        assertEquals( 1023422401000L, stamp );

        // now one day and one second past...
        stamp = knife.calculateStamp( "08", "06", "02", "04", "00", "01" );
        assertEquals( 1023508801000L, stamp );

        stamp = knife.calculateStamp( "08", "JUN", "02", "04", "00", "01" );
        assertEquals( 1023508801000L, stamp );

    }

    @Test
    public void testParsingBoysBirthdayAsNY()
    {
        TimestampArmyKnife knife = new TimestampArmyKnife();
        TimeZone gmt = null;
        long stamp;
        gmt = TimeZone.getTimeZone("EST");
        assertNotNull( gmt );
        knife.setTimeZone( gmt );

        stamp = knife.calculateStamp( "06", "06", "02", "23", "00", "00" );
        assertEquals( 1023422400000L, stamp );

        stamp = knife.calculateStamp( "06", "JUN", "02", "23", "00", "00" );
        assertEquals( 1023422400000L, stamp );

        // now one second past...
        stamp = knife.calculateStamp( "06", "06", "02", "23", "00", "01" );
        assertEquals( 1023422401000L, stamp );

        stamp = knife.calculateStamp( "06", "JUN", "02", "23", "00", "01" );
        assertEquals( 1023422401000L, stamp );

        // now one day and one second past...
        stamp = knife.calculateStamp( "07", "06", "02", "23", "00", "01" );
        assertEquals( 1023508801000L, stamp );

        stamp = knife.calculateStamp( "07", "JUN", "02", "23", "00", "01" );
        assertEquals( 1023508801000L, stamp );

    }

    @Test
    public void testParsingBoysBirthdayAsSydney()
    {
        TimestampArmyKnife knife = new TimestampArmyKnife();
        TimeZone syd = null;
        long stamp;
        syd = TimeZone.getTimeZone("Australia/Sydney");
        assertNotNull( syd );
        knife.setTimeZone( syd );

         // ok, let's try smack on 1st jan 1970
        stamp = knife.calculateStamp( "07", "06", "02", "14", "00", "00" );
        assertEquals( 1023422400000L, stamp );

        stamp = knife.calculateStamp( "07", "JUN", "02", "14", "00", "00" );
        assertEquals( 1023422400000L, stamp );

        // now one second past...
        stamp = knife.calculateStamp( "07", "06", "02", "14", "00", "01" );
        assertEquals( 1023422401000L, stamp );

        stamp = knife.calculateStamp( "07", "JUN", "02", "14", "00", "01" );
        assertEquals( 1023422401000L, stamp );

        // now one day and one second past...
        stamp = knife.calculateStamp( "08", "06", "02", "14", "00", "01" );
        assertEquals( 1023508801000L, stamp );

        stamp = knife.calculateStamp( "08", "JUN", "02", "14", "00", "01" );
        assertEquals( 1023508801000L, stamp );
    }


    @Test
    public void testAccuracy()
    {
        long stamp = System.currentTimeMillis();
        TimestampArmyKnife sydney = null;
        TimestampArmyKnife gmt = null;
        TimestampArmyKnife vegas = null;
        TimestampArmyKnife ny = null;

        TimeZone sydneyTZ = TimeZone.getTimeZone("Australia/Sydney");
        TimeZone gmtTZ = TimeZone.getTimeZone("GMT");
        TimeZone vegasTZ = TimeZone.getTimeZone("America/Los_Angeles");
        TimeZone nyTZ = TimeZone.getTimeZone("US/Eastern");

        assertNotNull( sydneyTZ );
        assertNotNull( gmtTZ );
        assertNotNull( vegasTZ );
        assertNotNull( nyTZ );

        String[] names = TimeZone.getAvailableIDs();
        for( String name : names )
        {
            System.out.println( name );
        }
 
        sydney = new TimestampArmyKnife();
        sydney.setTimeZone( sydneyTZ );

        vegas = new TimestampArmyKnife();
        vegas.setTimeZone( vegasTZ );

        ny = new TimestampArmyKnife();
        ny.setTimeZone( nyTZ );

        gmt = new TimestampArmyKnife();
        gmt.setTimeZone( gmtTZ );

        System.out.println( "Sydney --> " + sydney.formatStamp( stamp ) );
        System.out.println( "Vegas --> " + vegas.formatStamp( stamp ) );
        System.out.println( "GMT --> " + gmt.formatStamp( stamp ) );
        System.out.println( "NY--> " + ny.formatStamp( stamp ) );
    }

    @Test
    public void testParsing()
    {
        long stamp = System.currentTimeMillis();
        TimestampArmyKnife sydney = null;
        TimestampArmyKnife ny = null;
        TimestampArmyKnife est = null;

        TimeZone sydneyTZ = TimeZone.getTimeZone("Australia/Sydney");
        TimeZone estTZ = TimeZone.getTimeZone("US/Eastern");
        TimeZone nyTZ = TimeZone.getTimeZone("America/New_York");

        assertNotNull( sydneyTZ );
        assertNotNull( nyTZ );

        long sydneyStamp;
        long nyStamp;
        long estStamp;


        sydney = new TimestampArmyKnife();
        sydney.setTimeZone( sydneyTZ );

        ny = new TimestampArmyKnife();
        ny.setTimeZone( nyTZ );

        est = new TimestampArmyKnife();
        est.setTimeZone( estTZ );

        sydneyStamp = sydney.calculateStamp( 27, 6, 2007, 9, 40, 00 );
        estStamp = ny.calculateStamp( 26, 6, 2007, 19, 40, 00 );
        nyStamp = ny.calculateStamp( 26, 6, 2007, 19, 40, 00 );

        assertEquals( sydneyStamp, nyStamp );
        assertEquals( nyStamp, 1185493200000L );
        assertEquals( sydneyStamp, 1185493200000L );
        assertEquals( estStamp, 1185493200000L );

        System.out.println( "Sydney --> " + sydney.formatStamp( nyStamp ) );
        System.out.println( "NY--> " + ny.formatStamp( nyStamp ) );
        System.out.println( "EST--> " + est.formatStamp( nyStamp ) );
    }

    @Test
    public void testAargh()
    {
        long stamp = System.currentTimeMillis();
        TimestampArmyKnife sydney = null;
        TimestampArmyKnife ny = null;

        TimeZone sydneyTZ = TimeZone.getTimeZone("Australia/Sydney");
        TimeZone nyTZ = TimeZone.getTimeZone("US/Eastern");

        assertNotNull( sydneyTZ );
        assertNotNull( nyTZ );

        long sydneyStamp;
        long nyStamp;


        sydney = new TimestampArmyKnife();
        sydney.setTimeZone( sydneyTZ );

        ny = new TimestampArmyKnife();
        ny.setTimeZone( nyTZ );

        System.out.println( "Sydney --> " + sydney.formatStamp( 1185493200000L ) );
        System.out.println( "NY--> " + ny.formatStamp( 1185493200000L ) );
    }


    @Test
    public void testParse()
    {
        long stamp;
        TimestampArmyKnife gmtKnife = new TimestampArmyKnife();
        TimestampArmyKnife sydneyKnife = new TimestampArmyKnife();
        TimestampArmyKnife nyKnife = new TimestampArmyKnife();

        TimeZone gmtTz = TimeZone.getTimeZone("GMT");
        TimeZone sydneyTz = TimeZone.getTimeZone("Australia/Sydney");
        TimeZone nyTz = TimeZone.getTimeZone("America/New_York");

        gmtKnife.setTimeZone( gmtTz );
        sydneyKnife.setTimeZone( sydneyTz );
        nyKnife.setTimeZone( nyTz );

        // ok, parse date/time we know the timestamp for in sydney (AEST) time...
        // ------------------------------------------------------------------------
        stamp = sydneyKnife.parse( "Fri Aug 17 08:20:21 EST 2007" );
        assertEquals( 1187302821000L, stamp );

        stamp = sydneyKnife.parse( "Fri Aug 17 8:20:21 EST 2007" );
        assertEquals( 1187302821000L, stamp );

        stamp = sydneyKnife.parse( "2007-08-17 08:20:21,103" );
        assertEquals( 1187302821103L, stamp );

        stamp = sydneyKnife.parse( "17/08/07 08:20:21" );
        assertEquals( 1187302821000L, stamp );

        stamp = sydneyKnife.parse( "17/08/07 08:20" );
        assertEquals( 1187302800000L, stamp );

        stamp = sydneyKnife.parse( "17/08/07 8:20" );
        assertEquals( 1187302800000L, stamp );

        stamp = sydneyKnife.parse( "17/8/07 08:20" );
        assertEquals( 1187302800000L, stamp );

        stamp = sydneyKnife.parse( "17/8/07 8:20" );
        assertEquals( 1187302800000L, stamp );

        stamp = sydneyKnife.parse( "17/08/2007 08:20:21" );
        assertEquals( 1187302821000L, stamp );

        stamp = sydneyKnife.parse( "17/08/2007 08:20" );
        assertEquals( 1187302800000L, stamp );

        stamp = sydneyKnife.parse( "17/AUG/07 08:20:21" );
        assertEquals( 1187302821000L, stamp );

        stamp = sydneyKnife.parse( "17/AUG/07 08:20" );
        assertEquals( 1187302800000L, stamp );

        stamp = sydneyKnife.parse( "17/AUG/2007 08:20:21" );
        assertEquals( 1187302821000L, stamp );

        stamp = sydneyKnife.parse( "17/AUG/2007 08:20" );
        assertEquals( 1187302800000L, stamp );


        // ny is 14 hours behind sydney ... so it means that when the "same date/time"
        // occurs, it's going to be fourteen hours later. so add 14 * 3600 * 1000 =  50400000
        // ------------------------------------------------------------------------------------
        stamp = nyKnife.parse( "Fri Aug 17 08:20:21 EST 2007" );
        assertEquals( 1187302821000L + 50400000L, stamp );

        stamp = nyKnife.parse( "2007-08-17 08:20:21,103" );
        assertEquals( 1187302821103L + 50400000L, stamp );

        stamp = nyKnife.parse( "17/08/07 08:20:21" );
        assertEquals( 1187302821000L + 50400000L, stamp );


        // gmt is 10 hours behind sydney ... so add 10 * 3600 * 1000 = 36000000
        // --------------------------------------------------------------------
        stamp = gmtKnife.parse( "Fri Aug 17 08:20:21 EST 2007" );
        assertEquals( 1187302821000L + 36000000L, stamp );

        stamp = gmtKnife.parse( "2007-08-17 08:20:21,103" );
        assertEquals( 1187302821103L + 36000000L, stamp );

        stamp = gmtKnife.parse( "17/08/07 08:20:21" );
        assertEquals( 1187302821000L + 36000000L, stamp );


        // Now parse a date/time stamp that we know the timestamp
        // for in NY time.
        // for in NY time.
        // -------------------------------------------------
        stamp = nyKnife.parse( "Thu Aug 16 18:20:21 EST 2007" );
        assertEquals( 1187302821000L, stamp );

        stamp = nyKnife.parse( "2007-08-16 18:20:21,103" );
        assertEquals( 1187302821103L, stamp );

        stamp = nyKnife.parse( "16/08/07 18:20:21" );
        assertEquals( 1187302821000L, stamp );


        // gmt is 4 hours ahead of ny time 4 * 3600 * 1000 = 14400000
        // -------------------------------------------------------------
        stamp = gmtKnife.parse( "Thu Aug 16 18:20:21 EST 2007" );
        assertEquals( 1187302821000L - 14400000L, stamp );

        stamp = gmtKnife.parse( "2007-08-16 18:20:21,103" );
        assertEquals( 1187302821103L - 14400000L, stamp );

        stamp = gmtKnife.parse( "16/08/07 18:20:21" );
        assertEquals( 1187302821000L - 14400000L, stamp );

        stamp = gmtKnife.parse( "16/AUG/07 18:20:21" );
        assertEquals( 1187302821000L - 14400000L, stamp );

        // syd is 14 hours ahead of ny time 14 * 3600 * 1000 = 50400000
        // -------------------------------------------------------------
        stamp = sydneyKnife.parse( "Thu Aug 16 18:20:21 EST 2007" );
        assertEquals( 1187302821000L - 50400000L, stamp );

        stamp = sydneyKnife.parse( "2007-08-16 18:20:21,103" );
        assertEquals( 1187302821103L - 50400000L, stamp );

        stamp = sydneyKnife.parse( "16/08/07 18:20:21" );
        assertEquals( 1187302821000L - 50400000L, stamp );

        stamp = sydneyKnife.parse( "16/AUG/07 18:20:21" );
        assertEquals( 1187302821000L - 50400000L, stamp );
        
        stamp = sydneyKnife.parse( "16/AUG/07 18:20" );
        assertEquals( 1187302800000L - 50400000L, stamp );

        stamp = sydneyKnife.parse( "16/AUG/2007 18:20" );
        assertEquals( 1187302800000L - 50400000L, stamp );
    }

    @Test
    public void testExtractTimestamp()
    {
        String serverLogInput = "16/08/07 18:20:01:Session> Too many high priority messages ... priority (5) of send request id=10964318 exceeds default message priority (4), changed to 2";
        String queueLogInput = "16/08/07 18:20:05:queues[ 0 ]: name=\"incoming, size=125";
        String dispatcherLogInput = "2007-08-16 18:39:56,552 INFO  processSendRequest(DispatcherQueueWorker.java:2006) it took 2156ms to perform operations after DB persistence.";
        String ioStatInput = "Fri Aug 17 08:21:00 EST 2007";
        String commandLineInput = "16/08/07 18:25";
        String publicLogInput = "16/08/07 0:00:00:session id=\"aab421b0-a5f6-484f-92ca-a9db261ddce4/MM> read line [<?xml";
        String publicLogInput2 = "16/08/07 9:59:53:session.id=b6f60ac0-d8f4-46d5-9d7d-2b1f63021962/CELLCANADA> sent [<?xml ver";

        TimestampArmyKnife knife = new TimestampArmyKnife();

        assertEquals( "16/08/07 18:20:01", knife.extractTimestampText( serverLogInput ) );
        assertEquals( "16/08/07 18:20:05", knife.extractTimestampText( queueLogInput ) );
        assertEquals( "2007-08-16 18:39:56,552", knife.extractTimestampText( dispatcherLogInput ) );
        assertEquals( "Fri Aug 17 08:21:00 EST 2007", knife.extractTimestampText( ioStatInput ) );
        assertEquals( "16/08/07 18:25", knife.extractTimestampText( commandLineInput ) );
        assertEquals( "16/08/07 0:00:00", knife.extractTimestampText( publicLogInput ) );
        assertEquals( "16/08/07 9:59:53", knife.extractTimestampText( publicLogInput2 ) );
        assertEquals( "1/1/07 9:59:53", knife.extractTimestampText( "1/1/07 9:59:53> How about that?!?" ) );
        assertEquals( "1/JAN/07 9:59:53", knife.extractTimestampText( "1/JAN/07 9:59:53> How about that?!?" ) );
        assertEquals( "1/JAN/2007 9:59:53", knife.extractTimestampText( "1/JAN/2007 9:59:53> How about that?!?" ) );
    }

    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( TimestampArmyKnifeTest.class );
    }

}