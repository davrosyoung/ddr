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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

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
    assertEquals( 10, locations.size() );

    assertEquals( "SAA-1L", locations.get( 0 ).getWellName() );
    assertNotNull(locations.get(0).getCondensateCellLocation());
    assertEquals("SAA-1L!C1", locations.get(0).getCondensateCellLocation().toString());
    assertNull(locations.get(0).getOilCellLocation());
    assertNotNull(locations.get(0).getGasCellLocation());
    assertEquals("SAA-1L!D1", locations.get(0).getGasCellLocation().toString());
    assertNotNull(locations.get(0).getWaterCellLocation());
    assertEquals("SAA-1L!E1", locations.get(0).getWaterCellLocation().toString());
    assertEquals( 1, locations.get(0).getStartDataRow() );
    assertEquals( 2178, locations.get( 0 ).getEndDataRow() );

    
    assertEquals("SAA-1S", locations.get(1).getWellName());
    assertNull(locations.get(1).getCondensateCellLocation());
    assertNotNull(locations.get(1).getOilCellLocation());
    assertEquals("SAA-1S!C1", locations.get(1).getOilCellLocation().toString());
    assertNotNull( locations.get( 1 ).getGasCellLocation() );
    assertEquals( "SAA-1S!D1", locations.get( 1 ).getGasCellLocation().toString() );
    assertNotNull( locations.get( 1 ).getWaterCellLocation() );
    assertEquals( "SAA-1S!E1", locations.get( 1 ).getWaterCellLocation().toString() );
    assertEquals( 1, locations.get( 1 ).getStartDataRow() );
    assertEquals( 2156, locations.get( 1 ).getEndDataRow() );

    assertEquals( "SAA-2", locations.get( 2 ).getWellName() );
    assertNull( locations.get( 2 ).getCondensateCellLocation() );
    assertNotNull( locations.get( 2 ).getOilCellLocation() );
    assertEquals( "SAA-2!C1", locations.get( 2 ).getOilCellLocation().toString() );
    assertNotNull( locations.get( 2 ).getGasCellLocation() );
    assertEquals( "SAA-2!D1", locations.get( 2 ).getGasCellLocation().toString() );
    assertNotNull( locations.get( 2 ).getWaterCellLocation() );
    assertEquals( "SAA-2!E1", locations.get( 2 ).getWaterCellLocation().toString() );
    assertEquals( 1, locations.get( 2 ).getStartDataRow() );
    assertEquals( 2178, locations.get( 2 ).getEndDataRow() );

    assertEquals( "SAA-4", locations.get( 3 ).getWellName() );
    assertEquals( "SAA-5ST", locations.get( 4 ).getWellName() );
    assertEquals( "SAA-13", locations.get( 5 ).getWellName() );
    assertEquals( "SAA-7", locations.get( 6 ).getWellName() );
    assertEquals( "SAA-10ST1", locations.get( 7 ).getWellName() );
    assertEquals( "SAA-9", locations.get( 8 ).getWellName() );
    
    assertEquals( "SAA-11", locations.get( 9 ).getWellName() );
    assertNull( locations.get( 9 ).getCondensateCellLocation() );
    assertNotNull( locations.get( 9 ).getOilCellLocation() );
    assertEquals( "SAA-11!C1", locations.get( 9 ).getOilCellLocation().toString() );
    assertNotNull( locations.get( 9 ).getGasCellLocation() );
    assertEquals( "SAA-11!D1", locations.get( 9 ).getGasCellLocation().toString() );
    assertNotNull( locations.get( 9 ).getWaterCellLocation() );
    assertEquals( "SAA-11!E1", locations.get( 9 ).getWaterCellLocation().toString() );
    assertEquals( 1, locations.get( 9 ).getStartDataRow() );
    assertEquals( 1574, locations.get( 9 ).getEndDataRow() );


}



} // end ExcelWorkbookExplorerFactoryTest
