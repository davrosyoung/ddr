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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Obtains gas well data sets from an excel workbook.
 */
public class ExcelStandardizedGasWellDataExtractor extends BaseGasWellDataExtractor implements GasWellDataExtractor
{
private Workbook spreadsheet;
protected Map<GasWell,GasWellDataSet> dataSetMap;

protected ExcelStandardizedGasWellDataExtractor( Workbook spreadsheet, List<GasWellDataLocator> locatorList )
{
    this.spreadsheet = spreadsheet;
    this.dataSetMap = new HashMap<GasWell,GasWellDataSet>();
    Map<WellMeasurementType,Integer> measurementColumnIdxMap;
    
    for( GasWellDataLocator locator : locatorList )
    {
        Sheet sheet = spreadsheet.getSheet(locator.getWellName());
        int dateColumn = locator.getDateColumn();
        Row row;
        Cell cell;
        Date when;
        
        ExcelCellLocation dateCursor;
        measurementColumnIdxMap = new HashMap<WellMeasurementType,Integer>();
        
        if ( locator.getOilCellLocation() != null )
        {
            measurementColumnIdxMap.put( WellMeasurementType.OIL_FLOW, locator.getOilCellLocation().getColumn() );
        }
        
        if ( locator.getGasCellLocation() != null )
        {
            measurementColumnIdxMap.put( WellMeasurementType.GAS_FLOW, locator.getGasCellLocation().getColumn() );
        }
        
        if ( locator.getCondensateCellLocation() != null )
        {
            measurementColumnIdxMap.put( WellMeasurementType.CONDENSATE_FLOW, locator.getCondensateCellLocation().getColumn() );
        }
        
        if ( locator.getWaterCellLocation() != null )
        {
            measurementColumnIdxMap.put( WellMeasurementType.WATER_FLOW, locator.getWaterCellLocation().getColumn() );
        }

        // for each data row...
        // -------------------------
        for( int rowIdx = locator.getStartDataRow(); rowIdx <= locator.getEndDataRow(); rowIdx++ )
        {
            GasWellDataEntry entry;
            // let's start at the first data row....
            // --------------------------------------
            row = sheet.getRow( rowIdx );
            cell = row.getCell( dateColumn );

            if ( ( when = ExcelConverter.extractDateFromCell( cell ) ) != null )
            {
                // yayy!!
            }

            // how to work out the time span??
        }
    }
    
    for( int i = 0; i < spreadsheet.getNumberOfSheets(); i++ )
    {
        Sheet sheet = spreadsheet.getSheetAt( i );
        GasWell well = new GasWell( sheet.getSheetName() );
        GasWellDataSet dataSet = new GasWellDataSet( well );
        dataSetMap.put( well, dataSet );
    }
}

/**
 *
 * @param ids names of the gas wells for which data should be extracted.
 * @return
 */
@Override
public Map<GasWell, GasWellDataSet> extract(String[] ids)
{
    return dataSetMap;
}

}
