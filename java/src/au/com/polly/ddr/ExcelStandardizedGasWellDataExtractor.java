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

import au.com.polly.util.DateArmyKnife;
import au.com.polly.util.DateRange;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Obtains gas well data sets from an excel workbook.
 */
public class ExcelStandardizedGasWellDataExtractor extends BaseGasWellDataExtractor implements GasWellDataExtractor
{
private final static Logger logger = Logger.getLogger(ExcelStandardizedGasWellDataExtractor.class);
private Workbook spreadsheet;
protected List<GasWellDataLocator> locatorList = null;
protected MultipleWellDataMap mwdm;

/**
 *
 * @param spreadsheet
 * @param locatorList
 *
 * creates an extractor which can obtain measurement data sets from the specified spreadsheet.
 */
protected ExcelStandardizedGasWellDataExtractor( Workbook spreadsheet, List<GasWellDataLocator> locatorList )
{
    super();
    this.spreadsheet = spreadsheet;
    this.locatorList = locatorList;
}

public void process()
{
    Date lastStamp = null;
    Date stamp = null;
    long span;
    long lastSpan=0;
    long minSpan = Long.MAX_VALUE;
    long maxSpan = Long.MIN_VALUE;

    mwdm = new MultipleWellDataMap();


    
    int sheetCount = 0;
    Map<WellMeasurementType,Integer> measurementColumnIdxMap;
    
    status.setPhase( "processing", 0 );
    
    for( GasWellDataLocator locator : locatorList )
    {
        GasWellDataSet wellDataSet = null;
        GasWell well;
        Sheet sheet = spreadsheet.getSheet( locator.getWellName() );
        well = new GasWell( locator.getWellName() );
        wellDataSet = new GasWellDataSet( well );
        int dateColumn = locator.getDateColumn();
        Row row;
        Cell cell;
        Date when;
        long intervalUnitsMS = 0;
        ExcelCellLocation errorCursor = null;
        
        status.setPhase( "processing (" + well.getName() + ")", ( ( sheetCount * 100 ) / locatorList.size() ) );
        sheetCount++;
        
        ExcelCellLocation dateCursor;

        // let's see if the interval length is specified in a column, and if so, what units it is expressed in..
        // assume hours if not specified...
        // -----------------------------------------------------------------------------------------------------
        if ( locator.getIntervalLengthLocation() != null )
        {
            row = sheet.getRow( locator.getIntervalLengthLocation().getRow() );
            cell = row.getCell( locator.getIntervalLengthLocation().getColumn() );
            if ( cell.getCellType() == Cell.CELL_TYPE_STRING )
            {
                String text = cell.getStringCellValue().trim().toLowerCase();
                do {
                    if ( text.contains( "minute") || text.contains( "(min)" ) || text.contains( "mins" ) )
                    {
                         intervalUnitsMS = 60000L;
                    }
                    
                    if ( text.contains( "second") || text.contains( "(sec)" ) || text.contains( "secs" ) )
                    {
                         intervalUnitsMS = 1000L;
                    }
                    
                    if ( ( text.contains( "days") ) || ( text.contains( "dys" ) ) )
                    {
                         intervalUnitsMS = 86400000L;               
                    }
                    
                    // default to an interval unit of one hour..
                    // -----------------------------------------
                    intervalUnitsMS = 3600000L;

                } while( false );
                
            } // end-IF( text cell )
        } else {
            // let's check to see if the timing intervals are all the same...
            // --------------------------------------------------------------
            minSpan = Long.MAX_VALUE;
            maxSpan = Long.MIN_VALUE;

            
            for( int rowIdx = locator.getStartDataRow(); rowIdx < locator.getEndDataRow(); rowIdx++ )
            {
                row = sheet.getRow( rowIdx );
                cell = row.getCell( locator.getDateColumn() );
                if ( ( stamp = ExcelConverter.extractDateFromCell( cell ) ) != null )
                {
                    if ( lastStamp != null )
                    {
                        span = stamp.getTime() - lastStamp.getTime();

                        // be wary of one off 23 and 25 hour days!! these are due to daylight savings!!
                        // -----------------------------------------------------------------------------
                        if ( ( lastSpan == 86400000L ) && ( ( span == 82800000L ) || ( span == 90000000L ) ) )
                        {
                            // ignore one off 23 and 25 hour days, due to daylight savings!!!
                            // ---------------------------------------------------------------
                            logger.debug( "Ignoring " + ( span / 3600000 ) + " hour day on date " + DateArmyKnife.formatWithMinutes( stamp ) + " at rowIdx=" + rowIdx + ". I think it's just a daylight savings anomoly!!" );
                        } else {

                            if ( span < minSpan )
                            {
                                logger.debug( "sheet \"" + locator.getWellName() + "\": changing minspan at rowIdx=" + rowIdx + " from  " + minSpan + " to " + span );
                                minSpan = span;
                            }

                            if ( span > maxSpan )
                            {
                                logger.debug( "sheet \"" + locator.getWellName() + "\": changing maxspan at rowIdx=" + rowIdx + " from  " + maxSpan + " to " + span );
                                maxSpan = span;
                            }
                        }
                        lastSpan = span;
                    }
                    lastStamp = stamp;
                }
            }
            logger.info( "for sheet \"" + locator.getWellName() + "\" ... ended up with .... minSpan=" + minSpan + ", maxSpan=" + maxSpan );
            if ( minSpan < maxSpan )
            {
                status.addErrorMessage("Inconsistent interval lengths for well \"" + locator.getWellName() + "\". Min=" + minSpan / 3600000L + " hours, max=" + maxSpan / 3600000L + "hours. NO interval column detected for this well!!");
            }
        }

        stamp = null;
        span = 0;
        lastStamp = null;
        lastSpan = 0;
        
        // for each data row...
        // -------------------------
        for( int rowIdx = locator.getStartDataRow(); rowIdx <= locator.getEndDataRow(); rowIdx++ )
        {
            GasWellDataEntry entry = null;
            DateRange range = null;

            // let's start at the first data row....
            // --------------------------------------
            row = sheet.getRow( rowIdx );
            cell = row.getCell( dateColumn );

            if ( ( stamp = ExcelConverter.extractDateFromCell( cell ) ) != null )
            {
                entry = new GasWellDataEntry();
                entry.setWell( well );
                
                // if there is an interval length column, then use the length value from that...
                // ----------------------------------------------------------------------------
                if ( locator.getIntervalLengthLocation() != null )
                {
                    cell = row.getCell( locator.getIntervalLengthLocation().getColumn() );
                    if ( ( cell != null ) && ( cell.getCellType() == Cell.CELL_TYPE_NUMERIC ) )
                    {
                        span = (long)cell.getNumericCellValue() * intervalUnitsMS;
                        range = new DateRange( stamp, span, 1000L );
                    } else {
                        errorCursor = new ExcelCellLocation( locator.getWellName(), rowIdx, locator.getIntervalLengthLocation().getColumn() );
                        logger.error( "Failed to extract numeric value for interval length from cell \"" + errorCursor.toString() + "\"" );
                        status.addErrorMessage( "Failed to extract numeric value for interval length from cell \"" + errorCursor.toString() + "\"" );
                    }
                } else {
                    range = new DateRange( stamp, minSpan, 1000L );
                }
                
                if ( range != null )
                {
                    entry.setDateRange( range );
                }
                
                // ok, now let's extract the measurement values...
                // -----------------------------------------------
                for( WellMeasurementType wmt : WellMeasurementType.values() )
                {
                    if ( locator.containsMeasurementCellLocation( wmt ) )
                    {
                        cell = row.getCell( locator.getMeasurementCellLocation( wmt ).getColumn() );
                        if ( ( cell != null ) && ( cell.getCellType() == Cell.CELL_TYPE_NUMERIC ) )
                        {
                            entry.setMeasurement( wmt, cell.getNumericCellValue() );
                        } else {
                            errorCursor = new ExcelCellLocation( rowIdx, locator.getMeasurementCellLocation(wmt).getColumn() );
                            logger.error( "Failed to extract numeric value for " + wmt + " measurement from cell \"" + errorCursor.toString() + "\"" );
                            status.addErrorMessage( "Failed to extract numeric value for interval length from cell \"" + errorCursor.toString() + "\"" );
                        }
                    }
                }

                wellDataSet.addDataEntry( entry );
                
            } else {
                errorCursor = new ExcelCellLocation( locator.getWellName(), rowIdx, dateColumn );
                logger.error( "Failed to extract date from cell " + errorCursor.toString() );
                ExcelCellLocation cursor = new ExcelCellLocation( locator.getWellName(), rowIdx, dateColumn );
                status.addErrorMessage( "Failed to extract date from cell " + errorCursor.toString() );
            }
        } // end-FOR( each row )

        mwdm.addDataSet(wellDataSet);

    } // end-FOR( each well/sheet)
    status.setPhase( "finished", 100 );
    
}

public MultipleWellDataMap extract()
{
    if ( mwdm == null )
    {
        process();
    }
    return mwdm;
}

}
