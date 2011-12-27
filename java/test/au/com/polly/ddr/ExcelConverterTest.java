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

package au.com.polly.ddr;

import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import junit.framework.JUnit4TestAdapter;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

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
public class ExcelConverterTest
{
private final static Logger logger = Logger.getLogger( ExcelConverterTest.class );
static protected DateParser parser = new AussieDateParser();

@Test
public void testConstructor()
{
    assertNotNull(new ExcelConverter());
}

@Test
public void testConvertCellTypeName()
{
    assertEquals( "<unknown>", ExcelConverter.cellTypeName( -1 ) );
    assertEquals( "BLANK", ExcelConverter.cellTypeName( Cell.CELL_TYPE_BLANK ) );
    assertEquals( "BOOLEAN", ExcelConverter.cellTypeName( Cell.CELL_TYPE_BOOLEAN ) );
    assertEquals( "ERROR", ExcelConverter.cellTypeName( Cell.CELL_TYPE_ERROR ) );
    assertEquals( "FORMULA", ExcelConverter.cellTypeName( Cell.CELL_TYPE_FORMULA ) );
    assertEquals( "NUMERIC", ExcelConverter.cellTypeName( Cell.CELL_TYPE_NUMERIC ) );
    assertEquals( "STRING", ExcelConverter.cellTypeName( Cell.CELL_TYPE_STRING ) );
}

@Test
public void testGetCellContents()
{
    Workbook wb = new XSSFWorkbook();
    CreationHelper createHelper = wb.getCreationHelper();
    Sheet sheet = wb.createSheet("new sheet");

    // Create a row and put some cells in it. Rows are 0 based.
    Row row = sheet.createRow((short)0);
    // Create a cell and put a value in it.
    Cell cell = row.createCell(0);
    cell.setCellValue(1);

    // Or do it on one line.
    row.createCell(1).setCellValue(1.2);
    row.createCell(2).setCellValue(
            createHelper.createRichTextString("Hello World!"));
    row.createCell(3).setCellValue( true );
    row.createCell(4).setCellValue( (String)null );
    row.createCell(5).setCellValue( false );

    assertEquals("NUMERIC - 1.0", ExcelConverter.cellContents( row.getCell( 0 ) ) );
    assertEquals( "NUMERIC - 1.2", ExcelConverter.cellContents( row.getCell( 1 ) ) );
    assertEquals( "STRING - \"Hello World!\"", ExcelConverter.cellContents( row.getCell( 2 ) ) );
    assertEquals( "BOOLEAN - <TRUE>", ExcelConverter.cellContents( row.getCell( 3 ) ) );
    assertEquals( "BLANK - <NULL>", ExcelConverter.cellContents( row.getCell( 4 ) ) );
    assertEquals( "BOOLEAN - <FALSE>", ExcelConverter.cellContents( row.getCell( 5 ) ) );
    
}

@Test
public void testExtractDateFromCellMMDDYYHMM()
{
    Workbook wb = new XSSFWorkbook();
    CreationHelper createHelper = wb.getCreationHelper();
    Date stamp = parser.parse( "01/JAN/2012 05:14:26" ).getTime();
    Sheet sheet = wb.createSheet("new sheet");
    Date extracted = null;

    // Create a row and put some cells in it. Rows are 0 based.
    Row row = sheet.createRow((short)0);
    // Create a cell and put a value in it.
    Cell cell = row.createCell(0);
    CellStyle cellStyle = wb.createCellStyle();
    cellStyle.setDataFormat(
            createHelper.createDataFormat().getFormat("mm/dd/yy h:mm"));
    cell = row.createCell(1);
    cell.setCellValue( stamp );
    cell.setCellStyle(cellStyle);
    
    
    extracted = ExcelConverter.extractDateFromCell( cell );
    assertNotNull( extracted );
    
    assertEquals( stamp, extracted );
}

@Test
public void testExtractDateFromCellMMminusDDminusYYHMM()
{
    Workbook wb = new XSSFWorkbook();
    CreationHelper createHelper = wb.getCreationHelper();
    Date stamp = parser.parse( "01/JAN/2012 05:14:26" ).getTime();
    Sheet sheet = wb.createSheet("new sheet");
    Date extracted = null;

    // Create a row and put some cells in it. Rows are 0 based.
    Row row = sheet.createRow((short)0);
    // Create a cell and put a value in it.
    Cell cell = row.createCell(0);
    CellStyle cellStyle = wb.createCellStyle();
    cellStyle.setDataFormat(
            createHelper.createDataFormat().getFormat("mm-dd-yy h:mm"));
    cell = row.createCell(1);
    cell.setCellValue( stamp );
    cell.setCellStyle(cellStyle);

    extracted = ExcelConverter.extractDateFromCell( cell );
    assertNotNull( extracted );

    assertEquals( stamp, extracted );
}

@Test
public void testExtractDateFromCellDDslashminusMMMMslashminusYY()
{
    Workbook wb = new XSSFWorkbook();
    CreationHelper createHelper = wb.getCreationHelper();
    Date stamp = parser.parse( "01/JAN/2012 05:14:26" ).getTime();
    Sheet sheet = wb.createSheet("new sheet");
    Date extracted = null;

    // Create a row and put some cells in it. Rows are 0 based.
    Row row = sheet.createRow((short)0);
    // Create a cell and put a value in it.
    Cell cell = row.createCell(0);
    CellStyle cellStyle = wb.createCellStyle();
    cellStyle.setDataFormat(
            createHelper.createDataFormat().getFormat("dd\\-mmm\\-yy h:mm"));
    cell = row.createCell(1);
    cell.setCellValue( stamp );
    cell.setCellStyle(cellStyle);

    extracted = ExcelConverter.extractDateFromCell( cell );
    assertNotNull( extracted );

    assertEquals( stamp, extracted );
}

@Test
public void testExtractDateFromCellDDminusMMMMminusYY()
{
    Workbook wb = new XSSFWorkbook();
    CreationHelper createHelper = wb.getCreationHelper();
    Date stamp = parser.parse( "01/JAN/2012 05:14:26" ).getTime();
    Sheet sheet = wb.createSheet("new sheet");
    Date extracted = null;

    // Create a row and put some cells in it. Rows are 0 based.
    Row row = sheet.createRow((short)0);
    // Create a cell and put a value in it.
    Cell cell = row.createCell(0);
    CellStyle cellStyle = wb.createCellStyle();
    cellStyle.setDataFormat(
            createHelper.createDataFormat().getFormat("dd-mmm-yy hh:mm:ss.ttt blah blah blah"));
    cell = row.createCell(1);
    cell.setCellValue( stamp );
    cell.setCellStyle(cellStyle);

    extracted = ExcelConverter.extractDateFromCell( cell );
    assertNotNull( extracted );

    assertEquals( stamp, extracted );
}

@Test
public void testEndEighteenNinetyNine()
{
    Calendar cal = parser.parse( "31/12/1899" );
    Date when = cal.getTime();
    Date result = ExcelConverter.convert( 0 );
    assertNotNull( result );
    assertEquals( when, result );
}

@Test
public void testFirstJanuaryNineteenHundred()
{
    Calendar cal = parser.parse( "1/1/1900" );
    Date when = cal.getTime();
    Date result = ExcelConverter.convert( 1 );
    assertNotNull( result );
    assertEquals( when, result );
}

@Test
public void testTwentyEighthFebruary1900()
{
    Calendar cal = parser.parse( "28/FEB/1900" );
    Date when = cal.getTime();
    Date result = ExcelConverter.convert( 59 );
    assertNotNull( result );
    assertEquals( when, result );

}

@Test
public void testFirstMarch1900()
{
    Calendar cal = parser.parse( "1/MAR/1900" );
    Date when = cal.getTime();
    Date result = ExcelConverter.convert( 61 );
    assertNotNull( result );
    assertEquals( when, result );
}


@Test
public void testSecondMarch1900()
{
    Calendar cal = parser.parse( "2/MAR/1900" );
    Date when = cal.getTime();
    Date result = ExcelConverter.convert( 62 );
    assertNotNull( result );
    assertEquals( when, result );
}

@Test
public void testFirstJanuaryNineteenHundredAndOne()
{
    Calendar cal = parser.parse( "1/1/1901" );
    Date when = cal.getTime();
    Date result = ExcelConverter.convert( 367 );
    assertNotNull( result );
    assertEquals( when, result );
}


@Test
public void testDavesBirthday2006()
{
    Calendar cal = parser.parse( "13/6/2006" );
    Date when = cal.getTime();
    Date result = ExcelConverter.convert( 38881.0 );
    assertNotNull( result );
    assertEquals( when, result );
}

@Test
public void testDavesActualBirthday()
{
    Calendar cal = parser.parse( "13/6/1968" );
    Date when = cal.getTime();
    Date result = ExcelConverter.convert( 25002.0 );
    assertNotNull( result );
    assertEquals( when, result );
}

@Test
public void testEndNineteenNinetyNine()
{
    Calendar cal = parser.parse( "31/12/1999" );
    Date when = cal.getTime();
    Date result = ExcelConverter.convert( 36525.0 );
    assertNotNull( result );
    assertEquals( when, result );
}

@Test
public void testStartTwoThousand()
{
    Calendar cal = parser.parse( "1st January 2000" );
    Date when = cal.getTime();
    Date result = ExcelConverter.convert( 36526.0 );
    assertNotNull( result );
    assertEquals( when, result );
}

@Test
public void testFourteenthAugust2005()
{
    Calendar cal = parser.parse( "14th August 2005" );
    Date when = cal.getTime();
    Date result = ExcelConverter.convert( 38578.0 );
    String resultText;
    assertNotNull( result );
    assertEquals(when, result);
    resultText = result.toString();
    assertTrue( resultText.contains( "Aug" ) );
    assertTrue( resultText.contains( "14" ) );
    assertTrue( resultText.contains( "2005" ) );
}
@Test
public void testFirstJan2012TwentySixMinutesAndFourteenSecondsPastFive()
{
    Calendar cal = parser.parse( "1st January 2012 05:14:26" );
    Date when = cal.getTime();
    Date result = ExcelConverter.convert( 40909.21835648148 );
    assertNotNull( result );
    assertEquals(when, result);
}


public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( ExcelConverterTest.class );
}

}
