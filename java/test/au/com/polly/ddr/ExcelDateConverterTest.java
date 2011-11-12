package au.com.polly.ddr;

import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Exercise the excel date conversion routines. These tests are especially important because the
 * Bill Gate's calendar considers 1900 a leap year!! This means that dates after 28th March 1900
 * are represented by a number one larger than they actually should be!! Notice the difference
 * between the value used to test 28th february 1900 and 1st march 1900!! Also that we have
 * to supply a value of 367 to represent 1st january 1901 (366 larger than 1st january 1900,
 * as opposed to 365 larger!!). Thanks Bill!!
 *
 *
 */
@RunWith(JUnit4.class)
public class ExcelDateConverterTest
{
DateParser dateParser;

@Before
public void setUp() throws Exception
{
    dateParser = new AussieDateParser();
}


@Test
public void testEndEighteenNinetyNine()
{
    Calendar cal = dateParser.parse( "31/12/1899" );
    Date when = cal.getTime();
    Date result = ExcelDateConverter.getInstance().convert( 0 );
    assertNotNull( result );
    assertEquals( when, result );
}

@Test
public void testFirstJanuaryNineteenHundred()
{
    Calendar cal = dateParser.parse( "1/1/1900" );
    Date when = cal.getTime();
    Date result = ExcelDateConverter.getInstance().convert( 1 );
    assertNotNull( result );
    assertEquals( when, result );
}

@Test
public void testTwentyEighthFebruary1900()
{
    Calendar cal = dateParser.parse( "28/FEB/1900" );
    Date when = cal.getTime();
    Date result = ExcelDateConverter.getInstance().convert( 59 );
    assertNotNull( result );
    assertEquals( when, result );

}

@Test
public void testFirstMarch1900()
{
    Calendar cal = dateParser.parse( "1/MAR/1900" );
    Date when = cal.getTime();
    Date result = ExcelDateConverter.getInstance().convert( 61 );
    assertNotNull( result );
    assertEquals( when, result );
}


@Test
public void testSecondMarch1900()
{
    Calendar cal = dateParser.parse( "2/MAR/1900" );
    Date when = cal.getTime();
    Date result = ExcelDateConverter.getInstance().convert( 62 );
    assertNotNull( result );
    assertEquals( when, result );
}

@Test
public void testFirstJanuaryNineteenHundredAndOne()
{
    Calendar cal = dateParser.parse( "1/1/1901" );
    Date when = cal.getTime();
    Date result = ExcelDateConverter.getInstance().convert( 367 );
    assertNotNull( result );
    assertEquals( when, result );
}


@Test
public void testDavesBirthday2006()
{
    Calendar cal = dateParser.parse( "13/6/2006" );
    Date when = cal.getTime();
    Date result = ExcelDateConverter.getInstance().convert( 38881.0 );
    assertNotNull( result );
    assertEquals( when, result );
}

@Test
public void testDavesActualBirthday()
{
    Calendar cal = dateParser.parse( "13/6/1968" );
    Date when = cal.getTime();
    Date result = ExcelDateConverter.getInstance().convert( 25002.0 );
    assertNotNull( result );
    assertEquals( when, result );
}

@Test
public void testEndNineteenNinetyNine()
{
    Calendar cal = dateParser.parse( "31/12/1999" );
    Date when = cal.getTime();
    Date result = ExcelDateConverter.getInstance().convert( 36525.0 );
    assertNotNull( result );
    assertEquals( when, result );
}

@Test
public void testStartTwoThousand()
{
    Calendar cal = dateParser.parse( "1st January 2000" );
    Date when = cal.getTime();
    Date result = ExcelDateConverter.getInstance().convert( 36526.0 );
    assertNotNull( result );
    assertEquals( when, result );
}


public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( ExcelDateConverterTest.class );
}

}
