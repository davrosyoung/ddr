package au.com.polly.ddr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 12/11/11
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(JUnit4.class)
public class ExcelCellLocationTest
{


@Test
public void testObtainExcelColumnSpecifier()
{
    assertEquals( "A", ExcelCellLocation.obtainExcelColumnSpecifier( 0 ) );
    assertEquals( "B", ExcelCellLocation.obtainExcelColumnSpecifier( 1 ) );
    assertEquals( "Z", ExcelCellLocation.obtainExcelColumnSpecifier( 25 ) );
    assertEquals( "AA", ExcelCellLocation.obtainExcelColumnSpecifier( 26 ) );
    assertEquals( "AB", ExcelCellLocation.obtainExcelColumnSpecifier( 27 ) );
    assertEquals( "AZ", ExcelCellLocation.obtainExcelColumnSpecifier( 51 ) );
    assertEquals( "BA", ExcelCellLocation.obtainExcelColumnSpecifier( 52 ) );
    assertEquals( "ZZ", ExcelCellLocation.obtainExcelColumnSpecifier( ( 26 * 27 ) - 1 ) );
    assertEquals( "AAA", ExcelCellLocation.obtainExcelColumnSpecifier( 26 * 27 ) );
    assertEquals( "AAB", ExcelCellLocation.obtainExcelColumnSpecifier( ( 26 * 27 ) + 1 ) );
    assertEquals( "AAC", ExcelCellLocation.obtainExcelColumnSpecifier( ( 26 * 27 ) + 2 ) );
    assertEquals( "AAZ", ExcelCellLocation.obtainExcelColumnSpecifier( ( 26 * 27 ) + 25 ) );
    assertEquals( "ABA", ExcelCellLocation.obtainExcelColumnSpecifier( ( 26 * 27 ) + 26 ) );
}

@Test
public void testDecodingColumnSpecifier()
{
    assertEquals( 0, ExcelCellLocation.decodeExcelColumnSpecifier( "A" ) );
    assertEquals( 1, ExcelCellLocation.decodeExcelColumnSpecifier( "B" ) );
    assertEquals( 25, ExcelCellLocation.decodeExcelColumnSpecifier( "Z" ) );
    assertEquals( 26, ExcelCellLocation.decodeExcelColumnSpecifier( "AA" ) );
    assertEquals( 27, ExcelCellLocation.decodeExcelColumnSpecifier( "AB" ) );
    assertEquals( 51, ExcelCellLocation.decodeExcelColumnSpecifier( "AZ" ) );
    assertEquals( 52, ExcelCellLocation.decodeExcelColumnSpecifier( "BA" ) );
    assertEquals( 53, ExcelCellLocation.decodeExcelColumnSpecifier( "BB" ) );
    assertEquals( 676, ExcelCellLocation.decodeExcelColumnSpecifier( "ZA" ) );
    assertEquals( 701, ExcelCellLocation.decodeExcelColumnSpecifier( "ZZ" ) );
    assertEquals( 702, ExcelCellLocation.decodeExcelColumnSpecifier( "AAA" ) );
    assertEquals( 703, ExcelCellLocation.decodeExcelColumnSpecifier( "AAB" ) );
}

@Test( expected=NullPointerException.class)
public void testNullConstructor()
{
    ExcelCellLocation loc = new ExcelCellLocation( null );
}

@Test( expected=IllegalArgumentException.class)
public void testEmptyConstructor()
{
    ExcelCellLocation loc = new ExcelCellLocation( "" );
}

@Test( expected=IllegalArgumentException.class)
public void testBackToFrontConstructor()
{
    ExcelCellLocation loc = new ExcelCellLocation( "1A" );
}

@Test( expected=IllegalArgumentException.class)
public void testSillyConstructor()
{
    ExcelCellLocation loc = new ExcelCellLocation( "AAAAA999999" );
}

@Test
public void testA1()
{
    ExcelCellLocation a1 = new ExcelCellLocation( "A1" );
    assertEquals( 0, a1.getRow() );
    assertEquals( 0, a1.getColumn() );
    assertEquals( "A1", a1.toString() );
}

@Test
public void testRowZeroColumnZero()
{
    ExcelCellLocation a1 = new ExcelCellLocation( 0, 0 );
    assertEquals( 0, a1.getRow() );
    assertEquals( 0, a1.getColumn() );
    assertEquals( "A1", a1.toString() );
}

@Test
public void testB2()
{
    ExcelCellLocation locator = new ExcelCellLocation( "B2" );
    assertEquals( 1, locator.getRow() );
    assertEquals( 1, locator.getColumn() );
    assertEquals( "B2", locator.toString() );
}

@Test
public void testD2()
{
    ExcelCellLocation locator = new ExcelCellLocation( "D2" );
    assertEquals( 1, locator.getRow() );
    assertEquals( 3, locator.getColumn() );
    assertEquals( "D2", locator.toString() );
}

@Test
public void testD5()
{
    ExcelCellLocation locator = new ExcelCellLocation( "D5" );
    assertEquals( 4, locator.getRow() );
    assertEquals( 3, locator.getColumn() );
    assertEquals( "D5", locator.toString() );
}

@Test
public void testRowOneColumnOne()
{
    ExcelCellLocation a1 = new ExcelCellLocation( 1, 1 );
    assertEquals( 1, a1.getRow() );
    assertEquals( 1, a1.getColumn() );
    assertEquals( "B2", a1.toString() );
}


@Test
public void testAA2()
{
    ExcelCellLocation locator = new ExcelCellLocation( "AA2" );
    assertEquals( 26, locator.getColumn() );
    assertEquals( 1, locator.getRow() );
    assertEquals( "AA2", locator.toString() );
}

@Test
public void testRow27ColumnOne()
{
    ExcelCellLocation loc = new ExcelCellLocation( 1, 26 );
    assertEquals(1, loc.getRow());
    assertEquals( 26, loc.getColumn() );
    assertEquals("AA2", loc.toString());
}

@Test
public void testRow1001Column702()
{
    ExcelCellLocation loc = new ExcelCellLocation( 1001, 702 );
    assertEquals( 1001, loc.getRow() );
    assertEquals( 702, loc.getColumn() );
    assertEquals( "AAA1002", loc.toString() );

}

@Test
public void testAAA1001()
{
    ExcelCellLocation loc = new ExcelCellLocation( "AAA1001" );
    assertEquals( 1000, loc.getRow() );
    assertEquals( 702, loc.getColumn() );
    assertEquals("AAA1001", loc.toString());

}

}
