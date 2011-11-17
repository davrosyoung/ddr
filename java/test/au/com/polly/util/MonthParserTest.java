package au.com.polly.util;

import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;

import static junit.framework.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 10/11/11
 * Time: 4:26 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class MonthParserTest
{

@Before
public void setUp() throws Exception
{
    //To change body of created methods use File | Settings | File Templates.
}

@Test( expected = NullPointerException.class )
public void testNullArgument() throws Exception
{
     MonthParser.parseMonth( null );
}

@Test( expected = IllegalArgumentException.class )
public void testEmptyArgument() throws Exception
{
     MonthParser.parseMonth( "" );
}

@Test( expected = IllegalArgumentException.class )
public void testBlankArgument() throws Exception
{
     MonthParser.parseMonth( " " );
}

@Test( expected = IllegalArgumentException.class )
public void testJa()
{
    MonthParser.parseMonth( "ja" );
}

@Test
public void testJanuary()
{
    assertEquals( Calendar.JANUARY, MonthParser.parseMonth( "january " ) );
    assertEquals( Calendar.JANUARY, MonthParser.parseMonth( "JAN" ) ) ;
    assertEquals( Calendar.JANUARY, MonthParser.parseMonth( "JANUAR" ) ) ;
    assertEquals( Calendar.JANUARY, MonthParser.parseMonth( "JANUAR is the month for me" ) ) ;
    assertEquals( Calendar.JANUARY, MonthParser.parseMonth( "01" ) ) ;
    assertEquals( Calendar.JANUARY, MonthParser.parseMonth( "1" ) ) ;
    assertEquals( Calendar.JANUARY, MonthParser.parseMonth( " 1 " ) ) ;
}

@Test
public void testFebruary()
{
    assertEquals( Calendar.FEBRUARY, MonthParser.parseMonth( "feb" ) );
    assertEquals( Calendar.FEBRUARY, MonthParser.parseMonth( "february" ) ) ;
    assertEquals( Calendar.FEBRUARY, MonthParser.parseMonth( "februarary is hard to spell!!" ) ) ;
    assertEquals( Calendar.FEBRUARY, MonthParser.parseMonth( "02" ) ) ;
    assertEquals( Calendar.FEBRUARY, MonthParser.parseMonth( "2" ) ) ;
    assertEquals( Calendar.FEBRUARY, MonthParser.parseMonth( " 2 " ) ) ;
}

@Test
public void testMarch()
{
    assertEquals( Calendar.MARCH, MonthParser.parseMonth( "march" ) );
    assertEquals( Calendar.MARCH, MonthParser.parseMonth( "MAR" ) ) ;
    assertEquals( Calendar.MARCH, MonthParser.parseMonth( "MARCH FOR THE FATHERLAND!!!" ) ) ;
    assertEquals( Calendar.MARCH, MonthParser.parseMonth( "3" ) ) ;
}

@Test
public void testApril()
{
    assertEquals( Calendar.APRIL, MonthParser.parseMonth( "apr" ) );
    assertEquals( Calendar.APRIL, MonthParser.parseMonth( "april" ) ) ;
    assertEquals( Calendar.APRIL, MonthParser.parseMonth( "aprilicus" ) ) ;
    assertEquals( Calendar.APRIL, MonthParser.parseMonth( "4" ) ) ;
}

@Test
public void testMay()
{
    assertEquals( Calendar.MAY, MonthParser.parseMonth( "may" ) );
    assertEquals( Calendar.MAY, MonthParser.parseMonth( "MAY" ) ) ;
    assertEquals( Calendar.MAY, MonthParser.parseMonth( "MAY...." ) ) ;
    assertEquals( Calendar.MAY, MonthParser.parseMonth( "MAYBE NOT" ) ) ;
    assertEquals( Calendar.MAY, MonthParser.parseMonth( "5" ) ) ;
}

@Test
public void testJune()
{
    assertEquals( Calendar.JUNE, MonthParser.parseMonth( "june" ) );
    assertEquals( Calendar.JUNE, MonthParser.parseMonth( "JUNE   " ) ) ;
    assertEquals( Calendar.JUNE, MonthParser.parseMonth( "jun" ) ) ;
    assertEquals( Calendar.JUNE, MonthParser.parseMonth( "6" ) ) ;
}

@Test
public void testJuly()
{
    assertEquals( Calendar.JULY, MonthParser.parseMonth( "jul" ) );
    assertEquals( Calendar.JULY, MonthParser.parseMonth( "JULY" ) ) ;
    assertEquals( Calendar.JULY, MonthParser.parseMonth( "JUL" ) ) ;
    assertEquals( Calendar.JULY, MonthParser.parseMonth( "july and perhaps august too" ) ) ;
    assertEquals( Calendar.JULY, MonthParser.parseMonth( "7" ) ) ;
}

@Test
public void testAugust()
{
    assertEquals( Calendar.AUGUST, MonthParser.parseMonth( "aug" ) );
    assertEquals( Calendar.AUGUST, MonthParser.parseMonth( "Aug" ) ) ;
    assertEquals( Calendar.AUGUST, MonthParser.parseMonth( "Auguest" ) ) ;
    assertEquals( Calendar.AUGUST, MonthParser.parseMonth( "Augustus Orelius" ) ) ;
    assertEquals( Calendar.AUGUST, MonthParser.parseMonth( "8" ) ) ;
}

@Test
public void testSeptember()
{
    assertEquals( Calendar.SEPTEMBER, MonthParser.parseMonth( "September" ) );
    assertEquals( Calendar.SEPTEMBER, MonthParser.parseMonth( "SeP" ) ) ;
    assertEquals( Calendar.SEPTEMBER, MonthParser.parseMonth( "sept." ) ) ;
    assertEquals( Calendar.SEPTEMBER, MonthParser.parseMonth( "9" ) ) ;
    assertEquals( Calendar.SEPTEMBER, MonthParser.parseMonth( "09" ) ) ;
}

@Test
public void testOctober()
{
    assertEquals( Calendar.OCTOBER, MonthParser.parseMonth( "oct" ) );
    assertEquals( Calendar.OCTOBER, MonthParser.parseMonth( "October" ) ) ;
    assertEquals( Calendar.OCTOBER, MonthParser.parseMonth( "Oktober" ) ) ;
    assertEquals( Calendar.OCTOBER, MonthParser.parseMonth( "Octopus" ) ) ;
    assertEquals( Calendar.OCTOBER, MonthParser.parseMonth( "10" ) ) ;
}

@Test
public void testNovember()
{
    assertEquals( Calendar.NOVEMBER, MonthParser.parseMonth( "nOV" ) );
    assertEquals( Calendar.NOVEMBER, MonthParser.parseMonth( "Nov" ) ) ;
    assertEquals( Calendar.NOVEMBER, MonthParser.parseMonth( "November" ) ) ;
    assertEquals( Calendar.NOVEMBER, MonthParser.parseMonth( "NOV" ) ) ;
    assertEquals( Calendar.NOVEMBER, MonthParser.parseMonth( "11" ) ) ;
}

@Test
public void testDecember()
{
    assertEquals( Calendar.DECEMBER, MonthParser.parseMonth( "dec" ) );
    assertEquals( Calendar.DECEMBER, MonthParser.parseMonth( "December" ) ) ;
    assertEquals( Calendar.DECEMBER, MonthParser.parseMonth( "dEC" ) ) ;
    assertEquals( Calendar.DECEMBER, MonthParser.parseMonth( "decedant" ) ) ;
    assertEquals( Calendar.DECEMBER, MonthParser.parseMonth( "12" ) ) ;
    assertEquals( Calendar.DECEMBER, MonthParser.parseMonth( " 12 " ) ) ;
}


public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( MonthParserTest.class );
}


}
