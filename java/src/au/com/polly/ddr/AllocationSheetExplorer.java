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

import au.com.polly.util.DateRange;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 * Attempts to locate well specific data within a specified worksheet.
 *
 */
public class AllocationSheetExplorer implements ExcelWorkbookExplorer
{
protected Sheet sheet = null;
protected List<GasWellDataLocator> locations = null;
private final static Logger logger = Logger.getLogger( AllocationSheetExplorer.class );;

public AllocationSheetExplorer( Sheet sheet )
{
    locations = new ArrayList();
    this.sheet = sheet;

}

public void process()
{
    Row row = sheet.getRow( sheet.getFirstRowNum() );
    ExcelCellLocation cursor = null;
    boolean wellCellFound = false;
    int rowCount = 0;
    ExcelCellLocation wellCellLocation = null;

    cursor = ( row != null ) ? new ExcelCellLocation( row.getRowNum(), row.getFirstCellNum() ) : null;

    logger.trace( "process() method invoked. rowCount=" + rowCount + ", sheet.getFirstRowNum()=" + sheet.getFirstRowNum() + ", cursor=" + cursor );

    // try and locate a row starting with it's first cell saying something like "well"
    // within the first ten rows of data within the spreadsheet...
    // .. look in the first three columns....
    // --------------------------------------------------------------------------------
    while( ( cursor != null ) && !wellCellFound && rowCount < 10 )
    {
        if ( logger.isTraceEnabled() )
        {
            logger.trace( "About to start interrogating row " + cursor.getRow() + " at cursor position " + cursor );
        }

        for( int i = 0; ( i < 3 ) && !wellCellFound ; i++ )
        {
            Cell cell = row.getCell( cursor.getColumn() );

            if ( logger.isTraceEnabled() )
            {
                logger.trace( "cursor=" + cursor + ", rowCount=" + rowCount + ", cell.type=\"" + ExcelConverter.cellTypeName( cell.getCellType() ) + "\",cell.text=\"" + cell.getStringCellValue() + "\"" );
            }

            if ( cell.getCellType() == Cell.CELL_TYPE_STRING )
            {
                String text = cell.getStringCellValue().toLowerCase();
                if ( text.contains( "well" ) )
                {
                    wellCellFound = true;
                    wellCellLocation = cursor.copy();
                }
            }

            cursor.moveRight();
        }
        rowCount++;
        cursor.moveDown();
        row = sheet.getRow( cursor.getRow() );
        cursor.setColumn( row.getFirstCellNum() );
    }



    // if we found the well cell, then check the adjacent columns well names..
    // --------------------------------------------------------------------------------------------
    if ( wellCellFound )
    {
        logger.debug( "Located well cell at " + wellCellLocation );
        extractWellLocations( wellCellLocation );
    } // end-IF( cell containing "well" found )
}

/**
 * Given the location of a text cell with "well" in it, locate where the
 * data sets of the wells start and end....
 *
 *
 * @param wellCellLocation
 */
protected void extractWellLocations( ExcelCellLocation wellCellLocation )
{
    ExcelCellLocation cursor;
    Row row;
    Cell cell;
    int firstDateRow = -1;
    int lastDateRow = -1;
    String wellName = null;
    List<Integer> wellNameColumns = new ArrayList<Integer>();
    List<String> wellNames = new ArrayList<String>();
    boolean stillSearchingForDates = true;
    Date firstDate = null;
    Date lastDate = null;
    String text;
    long t0;
    long t1;
    long t2;
    long t3;
    long t9;


    t0 = System.currentTimeMillis();

    cursor = wellCellLocation.copy();
    row = sheet.getRow( cursor.getRow() );

    logger.debug( "extractWellLocations() invoked. row.getLastCellNum()=" + row.getLastCellNum() + ", cursor=" + cursor);

    // just find well names first.... remember to shift off the
    // initial cell containing the identifier "well"......
    // ---------------------------------------------------------
    cursor.moveRight();
    while( cursor.getColumn() <= row.getLastCellNum() )
    {
        cell = row.getCell( cursor.getColumn() );

        if ( ( cell != null ) && ( cell.getCellType() == Cell.CELL_TYPE_STRING ) )
        {
            text = cell.getStringCellValue();
            if ( ( text != null ) && ( text.trim().length() > 0 ) )
            {
                wellNameColumns.add( cursor.getColumn() );
                wellNames.add( text );

                if( logger.isTraceEnabled() )
                {
                    logger.trace( "found well name \"" + text + "\" at cursor position " + cursor );
                }
            }
        }

        cursor.moveRight();
    }

    if ( logger.isDebugEnabled() )
    {
        logger.debug( "We obtained " + wellNameColumns.size() + " well names" );
        for( int i = 0; i < wellNameColumns.size(); i++ )
        {
            logger.debug( "well[i=" + i + "] starts at col=" + wellNameColumns.get( i ) + ", name=\"" + wellNames.get( i ) + "\"" );
        }
    }

    // how far do the dates extend in this worksheet??
    // the first column will contain the date, let's investigate...
    // --------------------------------------------------------------

    // let's start from the bottom and work our way up!!
    // -------------------------------------------------
    cursor.setRow(sheet.getLastRowNum());
    row = sheet.getRow( cursor.getRow() );
    logger.debug( "for row " + row.getRowNum() + ", firstCellNumber=" + row.getFirstCellNum() + ", lastCellNumber=" + row.getLastCellNum() );
    cursor.setColumn( row.getFirstCellNum() );

    logger.debug( "About to start searching for date cells from the bottom up ... starting at " + cursor );

    while( stillSearchingForDates )
    {
        row = sheet.getRow( cursor.getRow() );
        cursor.setColumn( row.getFirstCellNum() );
        cell = row.getCell( cursor.getColumn() );
/*
        if ( logger.isTraceEnabled() )
        {
            if ( cell != null )
            {
                logger.trace( cursor + ExcelConverter.cellContents( cell ) );
            } else {
                logger.trace( cursor + " - NULL cell" );
            }
        }
*/
        if ( ( lastDate = ExcelConverter.extractDateFromCell( cell ) ) != null )
        {
            stillSearchingForDates = false;
            lastDateRow = cursor.getRow();
        }
        cursor.moveUp();
    }

    // and work our way down from the top for the first date cell..
    // -------------------------------------------------------------
    cursor.setRow( wellCellLocation.getRow() + 1 );
    row = sheet.getRow( cursor.getRow() );
    cursor.setColumn(row.getFirstCellNum());
    stillSearchingForDates = true;

    logger.debug( "About to start searching for date cells from the top down ... starting at " + cursor );

    while( stillSearchingForDates && ( cursor.getRow() < sheet.getLastRowNum() ) )
    {
        row = sheet.getRow( cursor.getRow() );
        cursor.setColumn( row.getFirstCellNum() );
        cell = row.getCell( cursor.getColumn() );
 /*
        if ( logger.isTraceEnabled() )
        {
            if ( cell != null )
            {
                logger.trace( cursor + ExcelConverter.cellContents( cell ) );
            } else {
                logger.trace( cursor + " - NULL cell" );
            }
        }
*/

        if ( ( firstDate = ExcelConverter.extractDateFromCell( cell ) ) != null )
        {
            stillSearchingForDates = false;
            firstDateRow = cursor.getRow();
        }
        cursor.moveDown();
    }

    t1 = System.currentTimeMillis();

    logger.debug( "we have dates from rows " + firstDateRow + " to row " + lastDateRow );
    logger.debug( "first date is " + firstDate + ", lastDate is " + lastDate + " ... took " + ( t1 - t0 ) + "ms so far." );



    // for all but the last column, we know that it can't contain
    // data past the column occupied by the next well name. but for
    // the last well, we don't have that privilege. we are going to
    // check the cells in the row underneath the well name first,
    // to see if it looks like they contain column headings. if so,
    // we'll use those to locate the limits of data, otherwise,
    // we'll see how far across the data extends....
    // --------------------------------------------------------------
    for( int i = 0; i < wellNameColumns.size(); i++ )
    {
        t2 = System.currentTimeMillis();
        GasWellDataLocator locator = new GasWellDataLocator();
        locator.setWellName( wellNames.get( i ) );
        wellName = wellNames.get( i );
        int startColumn = wellNameColumns.get( i );
        int endColumn;

        if (  i < wellNameColumns.size() - 1 )
        {
            endColumn = wellNameColumns.get( i + 1 ) - 1;
        } else {
            if ( startColumn + 10 > row.getLastCellNum() )
            {
                endColumn = row.getLastCellNum();
            } else {
                endColumn = startColumn + 10;
            }
        }

        logger.trace("i=" + i + ", well=\"" + locator.getWellName() + "\", startColumn=" + startColumn + ", endColumn=" + endColumn);

        // let's look between (and including) start column
        // and end column.... we are looking for columns
        // starting with;
        // "oil"
        // "gas"
        // "cond" (or gas condensate).
        // -----------------------------------------------
        cursor = wellCellLocation.copy();
        cursor.setColumn( startColumn );
        cursor.moveDown();

        row = sheet.getRow( cursor.getRow() );

        while( cursor.getColumn() <= endColumn )
        {
            cell = row.getCell( cursor.getColumn() );
            if ( ( cell != null ) && ( cell.getCellType() == Cell.CELL_TYPE_STRING ) )
            {
                text = cell.getStringCellValue().trim().toLowerCase();
                logger.trace(cursor + "=[" + text + "]");

                do
                {
                    if ( ( text == null ) || ( text.length() == 0 ) )
                    {
                        break;
                    }

                    if ( ( locator.getOilCellLocation() == null ) && ( text.startsWith( "oil" ) ) )
                    {
                        locator.setOilCellLocation( cursor.copy() );
                        break;
                    }

                    if ( ( locator.getCondensateCellLocation() == null ) && ( text.startsWith( "cond" ) || ( ( text.startsWith( "gas" ) && ( text.contains( "cond" ) ) ) ) ) )
                    {
                        locator.setCondensateCellLocation( cursor.copy() );
                        break;
                    }

                    if ( ( locator.getGasCellLocation() == null ) && ( text.startsWith( "gas" ) ) )
                    {
                        locator.setGasCellLocation( cursor.copy() );
                        break;
                    }

                    if ( ( locator.getWaterCellLocation() == null ) && ( text.startsWith( "water" ) ) )
                    {
                        locator.setWaterCellLocation( cursor.copy() );
                        break;
                    }
                } while( false );
            }

            cursor.moveRight();
        }

        if (
                ( ( locator.getOilCellLocation() != null ) || ( locator.getCondensateCellLocation() != null ) )
            &&  ( locator.getGasCellLocation() != null )
            &&  ( locator.getWaterCellLocation() != null )
            )
        {
            logger.debug( "For well \"" + wellNames.get( i ) + "\" we have all required fields!! " + locator );

            // ok .... now look for the actual data to get the date range....
            // --------------------------------------------------------------
            for ( WellMeasurementType wmt : WellMeasurementType.values() )
            {
                int measurementDataStartRow = -1;
                int measurementDataEndRow = -1;
                cursor = null;

                switch( wmt )
                {
                    case OIL_FLOW:
                        if ( locator.getOilCellLocation() != null )
                        {
                            cursor = locator.getOilCellLocation().copy();
                        }
                        break;
                    case GAS_FLOW:
                        if ( locator.getGasCellLocation() != null )
                        {
                            cursor = locator.getGasCellLocation().copy();
                        }
                        break;
                    case WATER_FLOW:
                        if ( locator.getWaterCellLocation() != null )
                        {
                            cursor = locator.getWaterCellLocation().copy();
                        }
                        break;
                    case CONDENSATE_FLOW:
                        if ( locator.getCondensateCellLocation() != null )
                        {
                            cursor = locator.getCondensateCellLocation().copy();
                        }
                        break;
                }

                if ( cursor == null )
                {
                    logger.debug( "SKIPPING measurement type " + wmt + " for well " + locator.getWellName() );
                    continue;
                }


                // find the first data column for this measurement type
                // start at the first date entry and search downwards....
                // ---------------------------------------------------------
                cursor.setRow( firstDateRow );

                logger.debug( "Starting data search for well \"" + locator.getWellName() + "\", measurement type " + wmt + " at cell " + cursor );

                while( ( cursor.getRow() <= lastDateRow ) && ( measurementDataStartRow < 0 ) )
                {
                    row = sheet.getRow( cursor.getRow() );
                    cell = row.getCell( cursor.getColumn() );

                    if ( ( cell != null ) && ( cell.getCellType() == Cell.CELL_TYPE_NUMERIC ) )
                    {
                        measurementDataStartRow =  cursor.getRow();
                    }

                    cursor.moveDown();
                }

                // ok ... lets find the last row of measurement data....
                // ------------------------------------------------------
                cursor.setRow( lastDateRow );
                while( ( cursor.getRow() > firstDateRow ) && ( measurementDataEndRow < 0 ) )
                {
                    row = sheet.getRow( cursor.getRow() );
                    cell = row.getCell( cursor.getColumn() );

                    if ( ( cell != null ) && ( cell.getCellType() == Cell.CELL_TYPE_NUMERIC ) )
                    {
                        measurementDataEndRow = cursor.getRow();
                    }

                    cursor.moveUp();
                }

                logger.debug( "For well \"" + wellName + "\", measurementType=" + wmt + ", obtained startDataRow=" + measurementDataStartRow + ", endDataRow=" + measurementDataEndRow );

                if ( locator.getStartDataRow() < 0 )
                {
                    locator.setStartDataRow( measurementDataStartRow );
                } else {
                    if ( locator.getStartDataRow() != measurementDataStartRow )
                    {
                        logger.error( "start row " + measurementDataStartRow + " for well \"" + wellName + "\", measurement type " + wmt + " does not agree with existing value of " + locator.getStartDataRow() );
                    }
                }

                if ( locator.getEndDataRow() < 0 )
                {
                    locator.setEndDataRow( measurementDataEndRow );
                } else {
                    if ( locator.getEndDataRow() != measurementDataEndRow )
                    {
                        logger.error( "end row " + measurementDataEndRow + " for well \"" + wellName + "\", measurement type " + wmt + " does not agree with existing value of " + locator.getEndDataRow() );
                    }
                }

            } // end-FOR( well measurement type )

            if ( ( locator.getStartDataRow() > 0 ) && ( locator.getEndDataRow() > locator.getStartDataRow() ) )
            {
                locations.add( locator );
            }
        } else {
            logger.debug( "For well \"" + wellNames.get( i ) + "\" we DO NOT have all required fields!!" );
        }
        t3 = System.currentTimeMillis();
        logger.debug("It took " + (t3 - t2) + " ms to extract location data for well \"" + locator.getWellName());
    } // end-FOR( each well )
    t9 = System.currentTimeMillis();
    logger.info( "extractWellLocations() took " + ( t9 - t0 ) + "ms to extract " + locations.size() + " well locations." );
}


public List<GasWellDataLocator> getLocations()
{
    return locations;
}

public GasWellDataSet obtainDataSet( GasWell well, GasWellDataLocator locator )
{
    Date intervalStart;
    long intervalLength;
    ExcelCellLocation cursor = null;
    GasWellDataEntry previousEntry = null;
    GasWellDataEntry currentEntry = null;
    Cell cell = null;
    Row row = null;
    double measurement;
    GasWellDataSet result = new GasWellDataSet( well );


    for( int rowIdx = locator.getStartDataRow(); rowIdx <= locator.getEndDataRow(); rowIdx++ )
    {
        row = sheet.getRow(rowIdx);
        cell = row.getCell(row.getFirstCellNum());
        currentEntry = new GasWellDataEntry();
        intervalStart = ExcelConverter.extractDateFromCell( cell );
        DateRange range = new DateRange( intervalStart, new Date( intervalStart.getTime() + 86400000L ) );
        currentEntry.setDateRange(range);

        for( WellMeasurementType wmt : WellMeasurementType.values() )
        {
            switch( wmt )
            {
                case OIL_FLOW:
                    cursor = locator.getOilCellLocation();
                    break;
                case GAS_FLOW:
                    cursor = locator.getGasCellLocation();
                    break;
                case WATER_FLOW:
                    cursor = locator.getWaterCellLocation();
                    break;
                case CONDENSATE_FLOW:
                    cursor = locator.getCondensateCellLocation();
                    break;
            }

            if ( cursor == null ) { break; }

            cursor.setRow( rowIdx );

            cell =  row.getCell( cursor.getColumn() );
            if ( cell.getCellType() == Cell.CELL_TYPE_NUMERIC )
            {
                measurement = cell.getNumericCellValue();
                currentEntry.setMeasurement( wmt, measurement );
            }
        } // end-FOR( each measurement type )

        previousEntry = currentEntry;
        result.addDataEntry( currentEntry );
    } // end-FOR( each row )


    return result;
}


}
