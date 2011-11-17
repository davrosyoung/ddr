package au.com.polly.util;

import au.com.polly.ddr.ExcelDateConverterTest;
import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import java.util.Calendar;


/**
 * Exercise the date parser
 */
@RunWith(JUnit4.class)
public class AussieDateParserTest
{
private final static String davesBirthdayText = "13/june/1968 04:00.924";
private final static String kertelsBirthdayText = "13/june/1968 04:00";
private final static String angusBirthdayText = "7/6/2002 15:23:09";
private DateParser aussieDateParser;
private Calendar davesBirthday;
private Calendar kertelsBirthday;
private Calendar angusBirthday;
private Calendar dayDot;
private Calendar firstJanuaryTwoThousand;
private Calendar endNineteenNinetyNine;

@Before
public void setUp() throws Exception
{
    aussieDateParser = new AussieDateParser();
    davesBirthday = Calendar.getInstance();
    davesBirthday.set( Calendar.YEAR, 1968 );
    davesBirthday.set( Calendar.MONTH, Calendar.JUNE );
    davesBirthday.set( Calendar.DAY_OF_MONTH, 13 );
    davesBirthday.set( Calendar.HOUR_OF_DAY, 4 );
    davesBirthday.set( Calendar.MINUTE, 13 );
    davesBirthday.set( Calendar.SECOND, 13 );
    davesBirthday.set( Calendar.MILLISECOND, 924 );

    kertelsBirthday = Calendar.getInstance();
    kertelsBirthday.set( Calendar.YEAR, 1966 );
    kertelsBirthday.set( Calendar.MONTH, Calendar.NOVEMBER );
    kertelsBirthday.set( Calendar.DAY_OF_MONTH, 22 );
    kertelsBirthday.set( Calendar.HOUR_OF_DAY, 13 );
    kertelsBirthday.set( Calendar.MINUTE, 27 );
    kertelsBirthday.set( Calendar.SECOND, 0 );
    kertelsBirthday.set( Calendar.MILLISECOND, 0 );


    angusBirthday = Calendar.getInstance();
    angusBirthday.set( Calendar.YEAR, 2002 );
    angusBirthday.set( Calendar.MONTH, Calendar.JUNE );
    angusBirthday.set( Calendar.DAY_OF_MONTH, 7 );
    angusBirthday.set( Calendar.HOUR_OF_DAY, 15 );
    angusBirthday.set( Calendar.MINUTE, 59 );
    angusBirthday.set( Calendar.SECOND, 59 );
    angusBirthday.set( Calendar.MILLISECOND, 0 );

    dayDot = Calendar.getInstance();
    dayDot.set( Calendar.YEAR, 1900 );
    dayDot.set( Calendar.MONTH, Calendar.JANUARY );
    dayDot.set( Calendar.DAY_OF_MONTH, 1 );
    dayDot.set( Calendar.HOUR_OF_DAY, 0 );
    dayDot.set( Calendar.MINUTE, 0 );
    dayDot.set( Calendar.SECOND, 0 );
    dayDot.set( Calendar.MILLISECOND, 0 );

    firstJanuaryTwoThousand = Calendar.getInstance();
    firstJanuaryTwoThousand.set( Calendar.YEAR, 2000 );
    firstJanuaryTwoThousand.set( Calendar.MONTH, Calendar.JANUARY );
    firstJanuaryTwoThousand.set( Calendar.DAY_OF_MONTH, 1 );
    firstJanuaryTwoThousand.set( Calendar.HOUR_OF_DAY, 0 );
    firstJanuaryTwoThousand.set( Calendar.MINUTE, 0 );
    firstJanuaryTwoThousand.set( Calendar.SECOND, 0 );
    firstJanuaryTwoThousand.set( Calendar.MILLISECOND, 0 );

    endNineteenNinetyNine = Calendar.getInstance();
    endNineteenNinetyNine.set( Calendar.YEAR, 1999 );
    endNineteenNinetyNine.set( Calendar.MONTH, Calendar.DECEMBER );
    endNineteenNinetyNine.set( Calendar.DAY_OF_MONTH, 31 );
    endNineteenNinetyNine.set( Calendar.HOUR_OF_DAY, 23 );
    endNineteenNinetyNine.set( Calendar.MINUTE, 59 );
    endNineteenNinetyNine.set( Calendar.SECOND, 59 );
    endNineteenNinetyNine.set( Calendar.MILLISECOND, 999 );
}

@Test(expected=NullPointerException.class)
public void testParsingNullString() throws Exception
{
    aussieDateParser.parse( null );
}

@Test(expected=IllegalArgumentException.class)
public void testParsingEmptySring() throws Exception
{
    aussieDateParser.parse( "" );
}

@Test
public void testParsingDavesBirthday()
{
    Calendar result;

    result = aussieDateParser.parse( "13/june/1968 04:13:13.924" );
    assertNotNull( result );
    assertEquals( davesBirthday, result );

    result = aussieDateParser.parse( "13/june/1968 4:13:13.924" );
    assertNotNull( result );
    assertEquals( davesBirthday, result );

    result = aussieDateParser.parse( "13/6/1968 4:13:13.924" );
    assertNotNull( result );
    assertEquals( davesBirthday, result );

    result = aussieDateParser.parse( "13/06/1968 4:13:13.924" );
    assertNotNull( result );
    assertEquals( davesBirthday, result );
}

@Test
public void testParsingKertelsBirthday()
{
    Calendar result;


    result = aussieDateParser.parse( "22/nov/1966 13:27" );
    assertNotNull( result );
    assertEquals( kertelsBirthday, result );

    result = aussieDateParser.parse( "22/november/1966 13:27:00" );
    assertNotNull( result );
    assertEquals( kertelsBirthday, result );

    result = aussieDateParser.parse( "22/11/1966 13:27" );
    assertNotNull( result );
    assertEquals( kertelsBirthday, result );

    result = aussieDateParser.parse( "22/11/1966 13:27:00" );
    assertNotNull( result );
    assertEquals( kertelsBirthday, result );
}


@Test
public void testParsingAngusBirthday()
{
    Calendar result;


    result = aussieDateParser.parse( "7/june/2002 15:59:59" );
    assertNotNull( result );
    assertEquals( angusBirthday, result );

    result = aussieDateParser.parse( "07/jun/02 15:59:59" );
    assertNotNull( result );
    assertEquals( angusBirthday, result );

    result = aussieDateParser.parse( "7/6/2 15:59:59" );
    assertNotNull( result );
    assertEquals( angusBirthday, result );

    result = aussieDateParser.parse( "07/06/02 15:59:59" );
    assertNotNull( result );
    assertEquals( angusBirthday, result );
}

@Test
public void testParsingDayDot()
{
    Calendar result;

    result = aussieDateParser.parse( "1st January 1900" );
    assertNotNull( result );
    assertEquals( dayDot, result );

    result = aussieDateParser.parse( "1/January/1900" );
    assertNotNull( result );
    assertEquals( dayDot, result );

    result = aussieDateParser.parse( "1/1/1900" );
    assertNotNull( result );
    assertEquals( dayDot, result );

    result = aussieDateParser.parse( "1.Jan.1900" );
    assertNotNull( result );
    assertEquals( dayDot, result );

    result = aussieDateParser.parse( "1-Jan-1900" );
    assertNotNull( result );
    assertEquals( dayDot, result );

    result = aussieDateParser.parse( "01/01/1900" );
    assertNotNull( result );
    assertEquals( dayDot, result );
}

@Test
public void testParsingFirstJanuaryTwoThousand()
{
    Calendar result;

    result = aussieDateParser.parse( "1st January 2000" );
    assertNotNull( result );
    assertEquals( firstJanuaryTwoThousand, result );

    result = aussieDateParser.parse( "1st January 2000  " );
    assertNotNull( result );
    assertEquals( firstJanuaryTwoThousand, result );

    result = aussieDateParser.parse( "1st  January  2000" );
    assertNotNull( result );
    assertEquals( firstJanuaryTwoThousand, result );

    result = aussieDateParser.parse( "1/January/00" );
    assertNotNull( result );
    assertEquals( firstJanuaryTwoThousand, result );

    result = aussieDateParser.parse( "1/January/0" );
    assertNotNull( result );
    assertEquals( firstJanuaryTwoThousand, result );

    result = aussieDateParser.parse( "1-Jan-2000" );
    assertNotNull( result );
    assertEquals( firstJanuaryTwoThousand, result );

}

@Test
public void testParsingEndNineteenNinetyNine()
{
    Calendar result;

    result = aussieDateParser.parse( "31st December 1999 23:59:59.999" );
    assertNotNull( result );
    assertEquals( endNineteenNinetyNine, result );

    result = aussieDateParser.parse( "31st  December 99 23:59:59.999  " );
    assertNotNull( result );
    assertEquals( endNineteenNinetyNine, result );

    result = aussieDateParser.parse( "31/Dec/1999 23:59:59.999" );
    assertNotNull( result );
    assertEquals( endNineteenNinetyNine, result );

    result = aussieDateParser.parse( "31/December/1999 23:59:59.999" );
    assertNotNull( result );
    assertEquals( endNineteenNinetyNine, result );

    result = aussieDateParser.parse( "31-Dec-1999 23:59:59.999" );
    assertNotNull( result );
    assertEquals( endNineteenNinetyNine, result );

    result = aussieDateParser.parse( "31.Dec.99 23:59:59.999" );
    assertNotNull( result );
    assertEquals( endNineteenNinetyNine, result );

}

public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( AussieDateParserTest.class );
}

}
