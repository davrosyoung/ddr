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

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Used to extract gas well data sets from the standardized excel workbook format.
 *
 */
public class ExcelStandardizedWorkbookExplorer implements ExcelWorkbookExplorer
{
private final static Logger logger = Logger.getLogger(ExcelStandardizedWorkbookExplorer.class);
private Workbook spreadsheet;
private List<GasWellDataLocator> locations=null;
private final static Pattern dateTimeIdentifierPattern = Pattern.compile( "^[dD][aA][tT][eE]" );

public ExcelStandardizedWorkbookExplorer( Workbook spreadsheet )
{
    this.spreadsheet = spreadsheet;    
    this.locations = new ArrayList<GasWellDataLocator>();
}

public void process()
{
    Sheet worksheet = null;
    String sheetName = null;
    GasWellDataLocator locator = null;
    
    logger.debug( "we have " + spreadsheet.getNumberOfSheets() + " worksheets." );
    
    for( int i = 0; i < spreadsheet.getNumberOfSheets(); i++ )
    {
        worksheet = spreadsheet.getSheetAt( i );
        sheetName = worksheet.getSheetName();
        
        if ( ( locator = interrogateSheet( worksheet ) ) != null )
        {
            locations.add( locator );
        }
        
    }
}

/**
 * Find out where the gas data is to be found within the specified worksheet
 * 
 * @param sheet the worksheet within the spreadsheet.
 * @return null if no gas data found, otherwise the location where it's to be read from.
 */
protected GasWellDataLocator interrogateSheet( Sheet sheet )
{
    ExcelCellLocation cursor= null;
    Cell cell = null;
    Row row = null;
    String textValue;
    boolean dateColumnHeadingLocated = false;
    GasWellDataLocator result = null;
    int dateCellRow=-1;
    Date firstDate;
    boolean stillSearchingForDates = true;
    int firstDateRow = 0;
    int lastDateRow = 0;
    Date lastDate = null;
    Date when = null;
    
    cursor = new ExcelCellLocation( sheet.getSheetName(), sheet.getFirstRowNum(), 0 );

    // look in the first five rows for a column heading which starts with
    // date .... expect the date column identifier within the first five
    // columns ....
    //
    // ----------------------------------------------------------------------
    for( int j = 0; j < 5 && ( result == null ); j++ )
    {
        row = sheet.getRow( cursor.getRow() );
        if ( row != null )
        {
            cursor.setColumn( row.getFirstCellNum() );
            for( int i = 0; i < 5 && ( result == null ); i++ )
            {
                cell = row.getCell( cursor.getColumn() );
                if ( ( cell.getCellType() == Cell.CELL_TYPE_STRING )
                        && ( ( textValue = cell.getStringCellValue() ) != null ) )
                {
                    if (
                            ( textValue.trim().toLowerCase().startsWith( "date" ) )
                        ||  ( textValue.trim().toLowerCase().startsWith( "time" ) )
                        )
                    {
                        result = new GasWellDataLocator();
                        result.setWellName( sheet.getSheetName() );
                        result.setDateColumn( cursor.getColumn() );
                        dateCellRow = cursor.getRow();
                    }
                }
                cursor.moveRight();
            }
        }

        cursor.moveDown();
    }
    
    logger.debug( "dateCellRow=" + dateCellRow + ", result=" + result + " ... about to look for first row with date...");

    // if we found the date column heading, let's look for the first row with
    // a date in it... the cursor is already on the cell below the cell that
    // the date field was in...
    // -----------------------------------------------------------------------
    if ( result != null )
    {
        cursor.setColumn( result.getDateColumn() );
        do {

            if (
                    ( ( row = sheet.getRow( cursor.getRow() ) ) != null )
                &&  ( ( cell = row.getCell( cursor.getColumn() ) ) != null )
                &&  ( ( firstDate = ExcelConverter.extractDateFromCell( cell ) ) != null )
            )
            {
                stillSearchingForDates = false;
                firstDateRow = cursor.getRow();
                logger.debug( "found first date " + firstDate + " at location " + cursor );
            }
            cursor.moveDown();
        } while( stillSearchingForDates );

        // ok, now let's look for the first row which isn't a date....
        // -------------------------------------------------------------
        do {
            when = null;
            if ( ( row = sheet.getRow( cursor.getRow() ) ) != null )
            {
                cell = row.getCell( cursor.getColumn() );
                if ( ( when = ExcelConverter.extractDateFromCell( cell ) ) != null )
                {
                    lastDateRow = cursor.getRow();
                    lastDate = when;
                }
                cursor.moveDown();
            }
        } while( ( row != null ) && ( when != null ) );
        
        // ok ... now locate the gas, oil, water and condensate flow column headings...
        // ------------------------------------------------------------------------------
        cursor.setRow( dateCellRow );
        cursor.setColumn(result.getDateColumn());
        result.setStartDataRow(firstDateRow);
        result.setEndDataRow(lastDateRow);

        // look in the rows between where the date cell was found and upto (but not
        // including) the first row of data...
        // -------------------------------------------------------------------------
        for( int j = dateCellRow; j < firstDateRow; j++ )
        {
            row = sheet.getRow( j );
            int maxCol = result.getDateColumn() + 10 > row.getLastCellNum() ? row.getLastCellNum() : result.getDateColumn() + 10;

            for( int i = result.getDateColumn() + 1; i <= maxCol; i++ )
            {
                if ( ( ( cell = row.getCell( i ) ) != null ) && ( cell.getCellType() == Cell.CELL_TYPE_STRING ) )
                {
                    textValue = cell.getStringCellValue().toLowerCase().trim();
                    cursor.setRow( j );
                    cursor.setColumn( i );
                    
                    if (
                            ( result.getIntervalLengthLocation() == null )
                        &&  (
                                    ( ( textValue.contains( "interval" ) && textValue.contains( "length" ) ) )
                               ||   ( textValue.startsWith( "duration" ) )
                             )
                        )
                    {
                        result.setIntervalLengthLocation( cursor.copy() );
                        continue;
                    }

                    if (
                            ( result.getCondensateCellLocation() == null )
                        &&  ( textValue.startsWith( "cond" ) || ( ( textValue.startsWith( "gas" ) && ( textValue.contains( "cond" ) ) ) ) ) )

                    {
                        result.setMeasurementCellLocation( WellMeasurementType.CONDENSATE_FLOW, cursor.copy() );
                        continue;
                    }

                    if (
                            ( result.getOilCellLocation() == null )
                        &&  ( textValue.toLowerCase().startsWith( "oil" ) )
                        )
                    {
                        result.setMeasurementCellLocation( WellMeasurementType.OIL_FLOW, cursor.copy() );
                        continue;
                    }

                    if ( ( result.getGasCellLocation() == null ) && ( textValue.toLowerCase().startsWith( "gas" ) ) )
                    {
                        result.setMeasurementCellLocation( WellMeasurementType.GAS_FLOW, cursor.copy() );
                        continue;
                    }

                    if ( ( result.getWaterCellLocation() == null ) && ( textValue.toLowerCase().startsWith( "water" ) ) )
                    {
                        result.setMeasurementCellLocation( WellMeasurementType.WATER_FLOW, cursor.copy() );
                        continue;
                    }
                }
            }
        }
    }
    
    logger.debug( "method returns with result=" + result + " for sheet \"" + sheet.getSheetName() + "\"" );

    return result;
}


@Override
public List<GasWellDataLocator> getLocations()
{
    return this.locations;
}

}