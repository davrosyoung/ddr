package au.com.polly.ddr;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.util.SystemOutLogger;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Attempts to locate well specific data within a specified worksheet.
 *
 */
public class AllocationSheetExplorer
{
Sheet sheet;
List<GasWellDataLocator> locations;

public AllocationSheetExplorer( Sheet sheet )
{
    locations = new ArrayList();
}

public void process()
{

    Row row = sheet.getRow( sheet.getFirstRowNum() );
    boolean wellCellFound = false;
    int rowCount = 0;
    ExcelCellLocation wellCellLocation = null;

    // try and locate a row starting with it's first cell saying something like "well"
    // within the first ten rows of data within the spreadsheet...
    // --------------------------------------------------------------------------------
    while( !wellCellFound && rowCount < 10 )
    {
        Cell cell = row.getCell( row.getFirstCellNum() );
        if ( cell.getCellType() == Cell.CELL_TYPE_STRING )
        {
            if ( cell.getStringCellValue().toLowerCase().contains("well ") )
            {
                wellCellFound = true;
                wellCellLocation = new ExcelCellLocation( row.getRowNum(), cell.getColumnIndex() );
            }
        }
    }

    // if we found the well cell, then check the adject columns well names..
    // --------------------------------------------------------------------------------------------
    if ( wellCellFound )
    {
        ExcelCellLocation cursor = wellCellLocation.copy();
        cursor.moveRight();
        row = sheet.getRow( cursor.getRow() );
        Cell cell = row.getCell( cursor.getColumn() );
        System.out.println( cell.toString() );
        cursor.moveRight();
        cell = row.getCell( cursor.getColumn() );
        System.out.println( cell.toString() );
        cell = row.getCell( cursor.getColumn() );
        System.out.println( cell.toString() );
        cell = row.getCell( cursor.getColumn() );
        System.out.println( cell.toString() );
        cell = row.getCell( cursor.getColumn() );
        System.out.println( cell.toString() );
    }

}

public List<GasWellDataLocator> getLocations()
{
    return locations;
}


}
