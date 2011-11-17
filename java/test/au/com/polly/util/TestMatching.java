package au.com.polly.util;

import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 10/11/11
 * Time: 1:32 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class TestMatching
{


final static String dateSeparator = "((\\s+)|(\\s?[\\/\\-\\.]\\s?))";
final static String oneOrTwoDigits = "(\\d{1,2})";
final static String dayOfMonthRegexp = oneOrTwoDigits + "([snrt][tdh])?";
final static String milliSecondRegexp = "\\.(\\d{3})";
final static String monthRegexp = "((\\d+)|([a-zA-Z]{3,}))";
final static String yearRegexp = "(\\d{2,4})";
final static String dateRegexp = dayOfMonthRegexp + dateSeparator + monthRegexp + dateSeparator + yearRegexp;
final static String timeRegexp = oneOrTwoDigits + ":" + oneOrTwoDigits + "(:" + oneOrTwoDigits + "(" + milliSecondRegexp + ")?)?";
final static String dateTimeRegexp = dateRegexp + "(\\s+" + timeRegexp + ")?";

@Test
public void testReadingOneOrTwoDigits()
{
    Pattern p1 = Pattern.compile( oneOrTwoDigits );
    Matcher m = p1.matcher( "" );
    assertFalse( m.matches() );

    m = p1.matcher( "0" );
    assertTrue( m.matches() );
    assertEquals("0", m.group( 1 ));

    m = p1.matcher( "99" );
    assertTrue( m.matches() );
    assertEquals( "99", m.group( 1 ) );

    m = p1.matcher( "999" );
    assertFalse( m.matches() );

    m = p1.matcher( "1968" );
    assertFalse( m.matches() );

    m = p1.matcher( " 0" );
    assertFalse( m.matches() );

    m = p1.matcher( "0 " );
    assertFalse( m.matches() );

    m = p1.matcher( " 99" );
    assertFalse( m.matches() );

    m = p1.matcher( "99 " );
    assertFalse( m.matches() );
}

@Test
public void testDayOfMonthWithOptionalSuffix()
{
    Pattern numberWithSuffix = Pattern.compile( dayOfMonthRegexp );
    Matcher m = numberWithSuffix.matcher( "" );
    assertFalse( m.matches() );

    m = numberWithSuffix.matcher( "2" );
    assertTrue( m.matches() );
    assertEquals("2", m.group(1));
    assertNull( m.group(2));

    m = numberWithSuffix.matcher( "2nd" );
    assertTrue(m.matches());
    assertEquals( "2", m.group( 1 ));
    assertEquals( "nd", m.group( 2 ) );

    m = numberWithSuffix.matcher( "3rd" );
    assertTrue(m.matches());
    assertEquals( "3", m.group( 1 ));
    assertEquals( "rd", m.group( 2 ) );

    m = numberWithSuffix.matcher( "3 rd" );
    assertFalse(m.matches());

    m = numberWithSuffix.matcher( "4th" );
    assertTrue(m.matches());
    assertEquals( "4", m.group( 1 ));
    assertEquals( "th", m.group( 2 ) );


    m = numberWithSuffix.matcher( "13th" );
    assertTrue(m.matches());
    assertEquals( "13", m.group( 1 ));
    assertEquals( "th", m.group( 2 ) );


    m = numberWithSuffix.matcher( "13rd" );
    assertTrue(m.matches());
    assertEquals( "13", m.group( 1 ));
    assertEquals( "rd", m.group( 2 ) );


    m = numberWithSuffix.matcher( "4th " );
    assertFalse(m.matches());

    m = numberWithSuffix.matcher( "2rds" );
    assertFalse(m.matches());
}

@Test
public void testDateAndMonth()
{
    Pattern numberWithSuffix = Pattern.compile( dayOfMonthRegexp + dateSeparator + monthRegexp );
    Matcher m = numberWithSuffix.matcher( "" );
    assertFalse( m.matches() );

    m = numberWithSuffix.matcher( "2 JUNE" );
    assertTrue( m.matches() );
    assertEquals("2", m.group( 1 ));
    assertNull( m.group(2));
    assertEquals( " ", m.group(3) );
    assertEquals( " ", m.group(4) );
    assertNull(  m.group(5) );
    assertEquals( "JUNE", m.group(6));
    assertEquals( null, m.group(7));
    assertEquals( "JUNE", m.group(8));

    m = numberWithSuffix.matcher( "2nd June" );
    assertTrue( m.matches() );
    assertEquals("2", m.group( 1 ));
    assertEquals( "nd", m.group(2));
    assertEquals( " ", m.group(3) );
    assertEquals( " ", m.group(4) );
    assertNull( m.group(5) );
    assertEquals( "June", m.group(6));
    assertEquals( null, m.group(7));
    assertEquals( "June", m.group(8));

    m = numberWithSuffix.matcher( "2-JUNE" );
    assertTrue( m.matches() );
    assertEquals("2", m.group( 1 ));
    assertNull( m.group(2));
    assertEquals( "-", m.group(3) );
    assertNull( m.group(4) );
    assertEquals( "-", m.group(5) );
    assertEquals( "JUNE", m.group(6));
    assertEquals( null, m.group(7));
    assertEquals( "JUNE", m.group(8));

    m = numberWithSuffix.matcher( "2 - JUNE" );
    assertTrue( m.matches() );
    assertEquals("2", m.group( 1 ));
    assertNull( m.group(2));
    assertEquals( " - ", m.group(3) );
    assertNull( m.group(4) );
    assertEquals( " - ", m.group(5) );
    assertEquals( "JUNE", m.group(6));
    assertEquals( null, m.group(7));
    assertEquals( "JUNE", m.group(8));

    m = numberWithSuffix.matcher( "2nd/JUNE" );
    assertTrue( m.matches() );
    assertEquals("2", m.group( 1 ));
    assertEquals( "nd", m.group(2));
    assertEquals( "/", m.group(3) );
    assertNull( m.group(4) );
    assertEquals( "/", m.group(5) );
    assertEquals( "JUNE", m.group(6));
    assertEquals( null, m.group(7));
    assertEquals( "JUNE", m.group(8));

    m = numberWithSuffix.matcher( "2nd.June" );
    assertTrue( m.matches() );
    assertEquals("2", m.group( 1 ));
    assertEquals( "nd", m.group(2));
    assertEquals( ".", m.group(3) );
    assertNull( m.group(4) );
    assertEquals( ".", m.group(5) );
    assertEquals( "June", m.group(6));
    assertEquals( null, m.group(7));
    assertEquals( "June", m.group(8));
}

@Test
public void testDateMonthAndYear()
{
    Pattern datePattern = Pattern.compile( dateRegexp );

    Matcher m = datePattern.matcher( "" );
    assertFalse( m.matches() );

    m = datePattern.matcher( "2 JUNE 68" );
    assertTrue( m.matches() );
    assertEquals("2", m.group( 1 ));
    assertNull( m.group(2));
    assertEquals( " ", m.group(3) );
    assertEquals( " ", m.group(4) );
    assertNull(  m.group(5) );
    assertEquals( "JUNE", m.group(6));
    assertEquals( null, m.group(7));
    assertEquals( "JUNE", m.group(8));
    assertEquals( " ", m.group( 9 ) );
    assertEquals( " ", m.group( 10 ) );
    assertNull( m.group( 11 ) );
    assertEquals( "68", m.group( 12 ) );

    m = datePattern.matcher( "13th 6 1968" );
    assertTrue( m.matches() );
    assertEquals("13", m.group( 1 ));
    assertEquals( "th", m.group(2));
    assertEquals( " ", m.group(3) );
    assertEquals( " ", m.group(4) );
    assertNull(  m.group(5) );
    assertEquals( "6", m.group(6));
    assertEquals( "6", m.group(7));
    assertNull( m.group(8));
    assertEquals( " ", m.group( 9 ) );
    assertEquals( " ", m.group( 10 ) );
    assertNull( m.group( 11 ) );
    assertEquals( "1968", m.group( 12 ) );

    m = datePattern.matcher( "13 06 1968" );
    assertTrue( m.matches() );
    assertEquals("13", m.group( 1 ));
    assertNull( m.group(2) );
    assertEquals( " ", m.group(3) );
    assertEquals( " ", m.group(4) );
    assertNull(  m.group(5) );
    assertEquals( "06", m.group(6));
    assertEquals( "06", m.group(7));
    assertNull( m.group(8));
    assertEquals( " ", m.group( 9 ) );
    assertEquals( " ", m.group( 10 ) );
    assertNull( m.group( 11 ) );
    assertEquals( "1968", m.group( 12 ) );
}

@Test
public void testMilliseconds()
{
    Pattern milliPattern = Pattern.compile( milliSecondRegexp );
    Matcher m;

    m = milliPattern.matcher( ".383" );
    assertTrue( m.matches() );
    assertEquals( "383", m.group( 1 ) );

    m = milliPattern.matcher( ".073" );
    assertTrue( m.matches() );
    assertEquals( "073", m.group( 1 ) );
}

@Test
public void testJustTime()
{

    Pattern timePattern = Pattern.compile( timeRegexp );
    Matcher m;

    m = timePattern.matcher( "13:06:68" );
    assertTrue( m.matches() );
    assertEquals( "13", m.group( 1 ) );
    assertEquals( "06", m.group( 2 ) );
    assertEquals( ":68", m.group( 3 ) );
    assertEquals( "68", m.group( 4 ) );

    m = timePattern.matcher( "13:006:08" );
    assertFalse( m.matches() );

    m = timePattern.matcher( "13:6:8" );
    assertTrue( m.matches() );
    assertEquals( "13", m.group( 1 ) );
    assertEquals( "6", m.group( 2 ) );
    assertEquals( ":8", m.group( 3 ) );
    assertEquals( "8", m.group( 4 ) );

    m = timePattern.matcher( "13:6:8.527" );
    assertTrue( m.matches() );
    assertEquals( "13", m.group( 1 ) );
    assertEquals( "6", m.group( 2 ) );
    assertEquals( ":8.527", m.group( 3 ) );
    assertEquals( "8", m.group( 4 ) );
    assertEquals( ".527", m.group( 5 ) );
    assertEquals( "527", m.group( 6 ) );

}

@Test
public void testDateAndTime()
{
   Pattern timePattern = Pattern.compile( dateTimeRegexp );
    Matcher m;

    m = timePattern.matcher( "13th June 1968 04:00" );
    assertTrue( m.matches() );
    assertEquals("13", m.group( 1 ));
    assertEquals("th", m.group(2));
    assertEquals( " ", m.group(3) );
    assertEquals( " ", m.group(4) );
    assertNull(  m.group(5) );
    assertEquals( "June", m.group(6));
    assertEquals( null, m.group(7));
    assertEquals("June", m.group(8));
    assertEquals( " ", m.group( 9 ) );
    assertEquals( " ", m.group( 10 ) );
    assertNull(m.group(11));
    assertEquals( "1968", m.group( 12 ) );
    assertNotNull( m.group( 13 ) );
    assertEquals( " 04:00", m.group( 13 ) );
    assertEquals( "04", m.group( 14 ) );
    assertEquals( "00", m.group( 15 ) );
    assertNull( m.group( 16 ) );
    assertNull( m.group( 17 ) );

    m = timePattern.matcher( "13th June 1968 04:00:07" );
    assertTrue( m.matches() );
    assertEquals("13", m.group( 1 ));
    assertEquals("th", m.group(2));
    assertEquals( " ", m.group(3) );
    assertEquals( " ", m.group(4) );
    assertNull(  m.group(5) );
    assertEquals( "June", m.group(6));
    assertEquals( null, m.group(7));
    assertEquals("June", m.group(8));
    assertEquals( " ", m.group( 9 ) );
    assertEquals( " ", m.group( 10 ) );
    assertNull(m.group(11));
    assertEquals( "1968", m.group( 12 ) );
    assertNotNull( m.group( 13 ) );
    assertEquals( " 04:00:07", m.group( 13 ) );
    assertEquals( "04", m.group( 14 ) );
    assertEquals( "00", m.group( 15 ) );
    assertNotNull( m.group( 16 ) );
    assertEquals( ":07", m.group( 16 ) );
    assertEquals( "07", m.group( 17 ) );
}


public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( TestMatching.class );
}


}
