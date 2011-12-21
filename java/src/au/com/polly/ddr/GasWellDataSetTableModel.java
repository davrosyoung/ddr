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

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Presents a gas well data set to a swing jtable so that it can be displayed and
 * modified. Columns are;
 * DATE/TIME
 * INTERVAL LENGTH
 * OIL FLOW RATE
 * CONDENSATE FLOW RATE
 * GAS FLOW RATE
 * WATER FLOW RATE
 * 
 *
 * 
 * @author Dave Young
 */
public class GasWellDataSetTableModel extends AbstractTableModel
{
protected GasWellDataSet actualData = null;
enum ColumnType {START_TIMESTAMP, UNTIL_TIMESTAMP, INTERVAL_LENGTH, OIL_FLOW, CONDENSATE_FLOW, GAS_FLOW, WATER_FLOW };
List<ColumnType> columnList = null;
protected static Map<WellMeasurementType,ColumnType> columnTypeLUT = new HashMap<WellMeasurementType,ColumnType>();
protected static Map<ColumnType,WellMeasurementType> wmtLUT = new HashMap<ColumnType,WellMeasurementType>();
protected static Map<ColumnType,String> columnHeadingLUT = new HashMap<ColumnType,String>();
protected static Map<ColumnType,Class> columnClassLUT = new HashMap<ColumnType,Class>();

static {
    columnTypeLUT.put( WellMeasurementType.CONDENSATE_FLOW, ColumnType.CONDENSATE_FLOW );
    columnTypeLUT.put( WellMeasurementType.WATER_FLOW, ColumnType.WATER_FLOW );
    columnTypeLUT.put( WellMeasurementType.GAS_FLOW, ColumnType.GAS_FLOW );
    columnTypeLUT.put( WellMeasurementType.OIL_FLOW, ColumnType.OIL_FLOW );
    
    wmtLUT.put( ColumnType.CONDENSATE_FLOW, WellMeasurementType.CONDENSATE_FLOW );
    wmtLUT.put( ColumnType.GAS_FLOW, WellMeasurementType.GAS_FLOW );
    wmtLUT.put( ColumnType.WATER_FLOW, WellMeasurementType.WATER_FLOW );
    wmtLUT.put( ColumnType.OIL_FLOW, WellMeasurementType.OIL_FLOW );
    
    columnHeadingLUT.put( ColumnType.START_TIMESTAMP, "Start Date/Time" );
    columnHeadingLUT.put( ColumnType.UNTIL_TIMESTAMP, "End Date/Time" );
    columnHeadingLUT.put( ColumnType.INTERVAL_LENGTH, "Interval Length" );
    columnHeadingLUT.put( ColumnType.OIL_FLOW, "Oil Flow" );
    columnHeadingLUT.put( ColumnType.GAS_FLOW, "Gas Flow" );
    columnHeadingLUT.put( ColumnType.WATER_FLOW, "Water Flow" );
    columnHeadingLUT.put( ColumnType.CONDENSATE_FLOW, "Condensate Flow" );
    
    columnClassLUT.put(ColumnType.START_TIMESTAMP, Date.class);
    columnClassLUT.put(ColumnType.UNTIL_TIMESTAMP, Date.class);
    columnClassLUT.put(ColumnType.INTERVAL_LENGTH, Double.class);
    columnClassLUT.put(ColumnType.OIL_FLOW, Double.class);
    columnClassLUT.put(ColumnType.GAS_FLOW, Double.class);
    columnClassLUT.put(ColumnType.WATER_FLOW, Double.class);
    columnClassLUT.put(ColumnType.CONDENSATE_FLOW, Double.class);
}
        

public GasWellDataSetTableModel(GasWellDataSet actualData)
{
    this.actualData = actualData;
    columnList = new ArrayList<ColumnType>();
    columnList.add( ColumnType.START_TIMESTAMP);
    columnList.add( ColumnType.UNTIL_TIMESTAMP);
    columnList.add( ColumnType.INTERVAL_LENGTH );
    for( WellMeasurementType wmt : WellMeasurementType.values() )
    {
        if ( actualData.containsMeasurement( wmt ) )
        {
            columnList.add( columnTypeLUT.get( wmt ) );
        }
    }
    
}

@Override
public int getRowCount()
{
    return actualData.getData().size();
}

@Override
public int getColumnCount()
{
    return columnList.size();
}

@Override
public Object getValueAt(int rowIndex, int columnIndex)
{
    Object result = null;
    GasWellDataEntry entry = actualData.getData().get( rowIndex );
    
    switch( columnList.get( columnIndex ) )
    {
        case START_TIMESTAMP:
            result = entry.getDateRange().from();
            break;
        case UNTIL_TIMESTAMP:
            result = entry.getDateRange().until();
            break;
        case INTERVAL_LENGTH:
            result = new Double( entry.getIntervalLengthMS() / 3600000.0 );
            break;
        case WATER_FLOW:
        case GAS_FLOW:
        case OIL_FLOW:
        case CONDENSATE_FLOW:
            WellMeasurementType wmt = wmtLUT.get( columnList.get( columnIndex ) );
            result = new Double( entry.getMeasurement( wmt ) );
            break;
    }
    return result;
}

@Override
public String getColumnName( int column )
{
    return columnHeadingLUT.get( columnList.get( column ) );
}

public String[] getColumnNames()
{
    List<String> columnNameList = new ArrayList<String>();
    String[] result;
    
    for( ColumnType ct : columnList )
    {
        columnNameList.add( columnHeadingLUT.get( ct ) );
    }
    
    result = new String[ columnNameList.size() ];
    columnNameList.toArray( result );

    return result;
}

public Class getColumnClass( int column )
{
    return columnClassLUT.get( columnList.get( column ) );
}

@Override
public boolean isCellEditable(int rowIndex, int columnIndex)
{
    return ( ( rowIndex > 0 ) && ( ( columnIndex == 0 ) || ( columnIndex == 2 ) ) );
}

public void setValueAt(Object value, int row, int col) {
//    data[row][col] = value;
    fireTableCellUpdated(row, col);
}
}
