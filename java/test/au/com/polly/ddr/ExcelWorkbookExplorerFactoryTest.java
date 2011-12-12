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

import junit.framework.JUnit4TestAdapter;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.PaneInformation;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.AutoFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellRange;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Battery of tests for the gas well data extractor factory class.
 * 
 */
@RunWith(JUnit4.class)
public class ExcelWorkbookExplorerFactoryTest
{
Logger logger = Logger.getLogger(ExcelWorkbookExplorerFactoryTest.class);
ExcelWorkbookExplorerFactory factory;
Workbook emptyBook;
Sheet emptySheet;
Workbook testBook;
Workbook testStandardizedBook;
Sheet allocationSheet = null;

public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( ExcelWorkbookExplorerFactoryTest.class );
}

@Before
public void setup()
{
    factory = ExcelWorkbookExplorerFactory.getInstance();

    emptyBook = new SXSSFWorkbook();

    try
    {
        emptySheet = new SXSSFSheet((SXSSFWorkbook) emptyBook, null );
    } catch (IOException e) {
        logger.fatal( "What the!?!?!" );
        logger.fatal( e.getClass() + " - " + e.getMessage() );
    }

    File excelFile;
    FileInputStream fis;

    try
    {
        excelFile = new File( "test_data/TinyJustAllocation.xlsx" );
        fis = new FileInputStream( excelFile );
        testBook = WorkbookFactory.create( fis );
        allocationSheet = testBook.getSheet( "allocation" );
        
        excelFile = new File( "test_data/TestStandardized.xlsx" );
        fis = new FileInputStream( excelFile );
        testStandardizedBook = WorkbookFactory.create( fis );
        
        
    } catch ( Exception e) {
        logger.fatal( "Failed to open test_data/TinyJustAllocation.xlsx" );
    }

}

@Test( expected = NullPointerException.class)
public void testConstructingAllocationSheetExplorerWithNullSheet()
{
    ExcelWorkbookExplorer explorer;
    
    explorer = factory.createAllocationSheetExplorer( null );
}

@Test
public void testConstructingAllocationSheetExplorerWithEmptySheet()
{
    ExcelWorkbookExplorer explorer;
    List<GasWellDataLocator> locations;

    explorer = factory.createAllocationSheetExplorer( emptySheet );
    assertNotNull( explorer );
    assertTrue( explorer instanceof AllocationSheetExplorer );
    
    locations = explorer.getLocations();
    assertNotNull(locations);
    assertEquals( 0, locations.size() );
}

@Test
public void testConstructingAllocationSheetExplorerWithValidSheet()
{
    ExcelWorkbookExplorer explorer;
    List<GasWellDataLocator> locations;
    
    assertNotNull( allocationSheet );
    assertEquals("allocation", allocationSheet.getSheetName());
    assertEquals(0, allocationSheet.getFirstRowNum());

    explorer = factory.createAllocationSheetExplorer( allocationSheet );
    assertNotNull( explorer );
    assertTrue( explorer instanceof AllocationSheetExplorer );

    locations = explorer.getLocations();
    assertNotNull( locations );
    assertEquals(5, locations.size());
}

@Test(expected=NullPointerException.class)
public void testConstructingExcelStandardizedExplorerWithNullWorkbook()
{
    ExcelWorkbookExplorer explorer;

    explorer = factory.createExcelStandardizedWorkbookExplorer( null );
}

@Test
public void testConstructingExcelStandardizedExplorerWithEmptyWorkbook()
{
    ExcelWorkbookExplorer explorer;
    List<GasWellDataLocator> locations;


    explorer = factory.createExcelStandardizedWorkbookExplorer( emptyBook );
    assertNotNull( explorer );
    assertTrue( explorer instanceof ExcelStandardizedWorkbookExplorer );

    locations = explorer.getLocations();

    assertNotNull( locations );
    assertEquals( 0, locations.size() );
}


@Test
public void testConstructingExcelStandardizedExplorerWithValidWorkbook()
{
    ExcelWorkbookExplorer explorer;
    List<GasWellDataLocator> locations;


    explorer = factory.createExcelStandardizedWorkbookExplorer( testStandardizedBook );
    assertNotNull( explorer );
    assertTrue( explorer instanceof ExcelStandardizedWorkbookExplorer );

    locations = explorer.getLocations();

    assertNotNull( locations );
    assertEquals( 5, locations.size() );

    assertEquals( "SAA-7", locations.get( 0 ).getWellName() );

}



} // end ExcelWorkbookExplorerFactoryTest
