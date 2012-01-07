/*
 * Copyright (c) 2011-2012 Polly Enterprises Pty Ltd and/or its affiliates.
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

package au.com.polly.ddr.ui;

import au.com.polly.ddr.GasWellDataEntry;
import au.com.polly.ddr.GasWellDataSet;
import au.com.polly.ddr.WellMeasurementType;
import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateArmyKnife;
import au.com.polly.util.DateParser;
import au.com.polly.util.DateRange;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
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
static final Logger logger = Logger.getLogger( GasWellDataSetTableModel.class );
protected GasWellDataSet actualData = null;
enum ColumnType {
    START_TIMESTAMP( 220, Date.class, true, "Start date", MyDateRenderer.class, MyDateEditor.class ),
    UNTIL_TIMESTAMP( 220, Date.class, true, "End date", MyDateRenderer.class, MyDateEditor.class ),
    INTERVAL_LENGTH( 80, Double.class, false, "Interval length" ),
    OIL_FLOW( 80, Double.class, false, "Oil Flow Rate", true, WellMeasurementType.OIL_FLOW ),
    CONDENSATE_FLOW( 80, Double.class, false, "Cond. Flow Rate", WellMeasurementType.CONDENSATE_FLOW ),
    GAS_FLOW( 80, Double.class, false, "Gas Flow Rate", WellMeasurementType.GAS_FLOW ),
    WATER_FLOW( 80, Double.class, false, "Water Flow Rate", WellMeasurementType.WATER_FLOW ),
    COMMENT( 300, String.class, true, "Comment" ),
    ADD( 120, String.class, true, "Add" ),
    DELETE( 120, String.class, true, "Del" );

    protected int width = 5;
    protected Class klass = null;
    protected String columnHeading = null;
    protected boolean editable = false;
    protected boolean isWellMeasurement = false;
    protected WellMeasurementType wmt = null;
    protected Class rendererClass = null;
    protected Class editorClass = null;
            
    ColumnType( int width, Class klass, boolean editable, String columnHeading )
    {
        this( width, klass, editable, columnHeading, false, null );
    }

    ColumnType( int width, Class klass, boolean editable, String columnHeading, Class rendererClass, Class editorClass )
    {
        this( width, klass, editable, columnHeading, false, null, rendererClass, editorClass );
    }

    ColumnType( int width, Class klass, boolean editable, String columnHeading, WellMeasurementType wmt )
    {
        this( width, klass, editable, columnHeading, true, wmt );
    }

    ColumnType( int width, Class klass, boolean editable, String columnHeading, boolean isWellMeasurement, WellMeasurementType wmt )
    {
        this( width, klass, editable, columnHeading, isWellMeasurement, wmt, null, null );
    }
    
    ColumnType( int width, Class klass, boolean editable, String columnHeading, boolean isWellMeasurement, WellMeasurementType wmt, Class rendererClass, Class editorClass )
    {
        this.width = width;
        this.klass = klass;
        this.columnHeading = columnHeading;
        this.editable = editable;
        this.isWellMeasurement = isWellMeasurement;
        if ( this.isWellMeasurement )
        {
            this.wmt = wmt;
        }
        this.rendererClass = rendererClass;
        this.editorClass = editorClass;
    }
    
    public Class getRendererClass()
    {
        return this.rendererClass;
    }
    
    public Class getEditorClass()
    {
        return this.editorClass;
    }
    
};
List<ColumnType> columnList = null;
protected static Map<WellMeasurementType,ColumnType> wmtColumnTypeLUT = new HashMap<WellMeasurementType,ColumnType>();
protected static Map<ColumnType,WellMeasurementType> columnTypeWmtLUT = new HashMap<ColumnType,WellMeasurementType>();
protected Map<ColumnType,Integer> columnTypeIndexLUT = new HashMap<ColumnType,Integer>();
protected String[] columnNameArray;


static {
    // enable lookup from column type  to well measurement type and vice versa
    for( ColumnType ct : ColumnType.values() )
    {
        if ( ct.isWellMeasurement )
        {
            columnTypeWmtLUT.put(ct, ct.wmt);
            wmtColumnTypeLUT.put(ct.wmt, ct);
        }
    }
}
        
public GasWellDataSetTableModel( GasWellDataSet actualData )
{
    this.actualData = actualData;
    columnList = new ArrayList<ColumnType>();
    columnList.add( ColumnType.START_TIMESTAMP);
    columnList.add( ColumnType.UNTIL_TIMESTAMP);
    columnList.add( ColumnType.INTERVAL_LENGTH );
    for( WellMeasurementType wmt : WellMeasurementType.values() )
    {
        logger.debug( "containsMeasurement( " + wmt + ")=" + actualData.containsMeasurement( wmt ) );
        if ( actualData.containsMeasurement( wmt ) )
        {
            columnList.add(wmtColumnTypeLUT.get(wmt));
        }
    }
    columnList.add( ColumnType.COMMENT );
    columnList.add( ColumnType.ADD );
    columnList.add( ColumnType.DELETE );

    columnTypeIndexLUT = new HashMap<ColumnType,Integer>();
    for( int i = 0; i < columnList.size(); i++ )
    {
        columnTypeIndexLUT.put( columnList.get( i ), i );
    }

    List<String> columnNameList = new ArrayList<String>();
    String[] result;

    for( ColumnType ct : columnList )
    {
        columnNameList.add( ct.columnHeading );
    }

    columnNameArray = new String[ columnNameList.size() ];
    columnNameList.toArray( columnNameArray );
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

/**
 *
 * @param ct the type of column to interrogate
 * @return whether or not the table contains this type of column
 */
public boolean containsColummnType( ColumnType ct )
{
    return columnTypeIndexLUT.containsKey( ct );
}


/**
 *
 * @param ct the type of column to interrogate
 * @return the index of the column type in the table.
 */
public int getColumnIndex( ColumnType ct )
{
    int result = -1;
    result = containsColummnType( ct ) ? columnTypeIndexLUT.get( ct ) : -1;
    return result;
}

/**
 *
 * @param i the index of the column into the table.
 * @return description of the type of column at index i
 */
protected ColumnType getColumnType( int i )
{
    return columnList.get( i );
}

@Override
public Object getValueAt( int rowIndex, int columnIndex )
{
    Object result = null;
    GasWellDataEntry entry = actualData.getData().get( rowIndex );
    ColumnType ct = columnList.get( columnIndex );
    
    switch( ct )
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
            WellMeasurementType wmt = columnTypeWmtLUT.get( columnList.get( columnIndex ) );
            result = entry.containsMeasurement( wmt ) ? new Double( entry.getMeasurement( wmt ) ) : new Double( 0.0 );
            break;
        case COMMENT:
            result = entry.getComment();
            break;
        case ADD:
            result = "Add";
            break;
        case DELETE:
            result = "Del";
            break;
    }
    return result;
}

@Override
public String getColumnName( int column )
{
    String result = null;
    
    if ( ( column >= 0 ) && ( column < getColumnCount() ) )
    {
        result = columnList.get( column ).columnHeading;
    }
    return result;
}

public String[] getColumnNames()
{
    return columnNameArray;
}

public Class getColumnClass( int column )
{
    Class result = null;
    if ( ( column >= 0 ) && ( column < getColumnCount() ) )
    {
        result = columnList.get( column ).klass;
    }
    return result;
}

@Override
public boolean isCellEditable( int rowIndex, int columnIndex )
{
    return (
                ( rowIndex >= 0 )
            &&  ( rowIndex < getRowCount() )
            &&  ( columnIndex >= 0 )
            &&  ( columnIndex < getColumnCount() )
            &&  columnList.get( columnIndex ).editable
    );
}

public void setValueAt( Object value, int row, int col)
{
    Date stamp;
    long span;
    DateRange range;
    Double intervalLengthHours;
    WellMeasurementType wmt;
    Double measurement;
    String text;
    
    if (
            ( col >= 0)
       &&   ( col < getColumnCount() )
       &&   ( row >= 0 )
       &&   ( row < getRowCount() ) )
    {
        GasWellDataEntry entry = actualData.getEntry( row );
        ColumnType ct = getColumnType( col );
        switch( ct )
        {
            case START_TIMESTAMP:
                stamp = (Date)value;

                // this next line will update BOTH this entry and the subsequent... OR throw an error!!
                // -------------------------------------------------------------------------------------
                try {
                    if ( row > 0 )
                    {
                        actualData.moveBoundary( entry.from(), stamp );
                        fireTableCellUpdated( row, getColumnIndex( ColumnType.INTERVAL_LENGTH ));
                        fireTableCellUpdated( row, getColumnIndex( ColumnType.UNTIL_TIMESTAMP ));

                        fireTableCellUpdated( row - 1, getColumnIndex( ColumnType.INTERVAL_LENGTH ) );
                        fireTableCellUpdated( row - 1, getColumnIndex( ColumnType.START_TIMESTAMP ) );
                        fireTableCellUpdated( row - 1, getColumnIndex( ColumnType.UNTIL_TIMESTAMP ) );

                    } else {
                        // todo: inform the user that they may not alter the start of data date!!
                        // ------------------------------------------------------------------------
                    }

                } catch( IllegalArgumentException iae ) {
                    // reject the update...
                    // todo: inform the user that the modification has not taken!!
                }

                break;
            
            case UNTIL_TIMESTAMP:
                stamp = (Date)value;

                // this next line will update BOTH this entry and the subsequent... OR throw an error!!
                // -------------------------------------------------------------------------------------
                try {
                    actualData.moveBoundary( entry.until(), stamp );
                    fireTableCellUpdated( row, getColumnIndex( ColumnType.INTERVAL_LENGTH ));
                    fireTableCellUpdated( row, getColumnIndex( ColumnType.UNTIL_TIMESTAMP ));

                    // update the start field of the subsequent row...
                    if ( row < ( actualData.getData().size() - 1 ) )
                    {
                        fireTableCellUpdated( row + 1, getColumnIndex( ColumnType.INTERVAL_LENGTH ) );
                        fireTableCellUpdated( row + 1, getColumnIndex( ColumnType.START_TIMESTAMP ) );
                        fireTableCellUpdated( row + 1, getColumnIndex( ColumnType.UNTIL_TIMESTAMP ) );
                    }

                } catch( IllegalArgumentException iae ) {
                    // reject the update...
                }
                break;
            
            case INTERVAL_LENGTH:
                // todo: either comment this out or make it work properly!! The user can edit the interval
                // start and end dates ... which is probably sufficient!!
                intervalLengthHours = (Double)value;
                span = (long)Math.round( intervalLengthHours * 3600.0 ) * 1000L;
                range = new DateRange( entry.from(), new Date( entry.from().getTime() + span ) );
                entry.setDateRange( range );
                fireTableCellUpdated( row, getColumnIndex( ColumnType.INTERVAL_LENGTH));
                fireTableCellUpdated( row, getColumnIndex( ColumnType.START_TIMESTAMP));
                fireTableCellUpdated( row, getColumnIndex( ColumnType.UNTIL_TIMESTAMP ));
                break;
            
            case OIL_FLOW:
            case GAS_FLOW:
            case WATER_FLOW:
            case CONDENSATE_FLOW:
                if ( ( ct.isWellMeasurement ) && ( ct.wmt != null ) )
                {
                    measurement = (Double)value;
                    entry.setMeasurement( ct.wmt, measurement );
                }
                break;
            
            case COMMENT:
                text = (String)value;
                if ( ( text != null ) && ( text.trim().length() > 0 ) )
                {
                    entry.setComment( text.trim() );
                }
                fireTableCellUpdated( row, getColumnIndex( ColumnType.COMMENT ) );
                break;
        }
    }
//    data[row][col] = value;
    fireTableCellUpdated(row, col);
}

/**
 * Add a new row below the currently specified row...
 * ... default action is to split the current row into
 * two, with the second "half" of the interval of this row
 * being placed into the row below.
 * 
 * @param row
 */
public void addRow( int row )
{
    GasWellDataEntry existing = actualData.getEntry( row );
    GasWellDataEntry another = existing.copy();
    long span = existing.getDateRange().span();
    Date midway = new Date( existing.from().getTime() + ( span / 2 ) );
    existing.setDateRange( new DateRange( existing.from(), midway ) );
    another.setDateRange( new DateRange( midway, another.until() ) );
    another.setComment( "" );
    
    actualData.getData().add(row + 1, another);
    fireTableRowsInserted( row, row + 1 );
}

/**
 * The interval represented by this row will be subsumed by the row before it.
 * Unable to delete the first row!!
 * 
 * @param row
 */
public void deleteRow( int row )
{
    if ( row == 0 )
    {
        logger.error( "Attempt to delete the first row!!" );
        return;
    }
    
    // need to delete this row, then replace the row above this row, with
    // a revised version containing the data from both rows...
    // ----------------------------------------------------------------
    GasWellDataEntry priorEntry = actualData.getEntry( row - 1 );
    GasWellDataEntry redundantEntry = actualData.getEntry( row );
    GasWellDataEntry replacementEntry = actualData.consolidateEntries( priorEntry.from(), redundantEntry.until() );
    replacementEntry.setComment( priorEntry.getComment() );
    
    actualData.getData().remove( row );
    actualData.getData().remove( row - 1 );
    actualData.getData().add( row - 1, replacementEntry );
}

/**
 * Let's render dates we like them :-)
 */
static class MyDateRenderer extends DefaultTableCellRenderer
{
	public MyDateRenderer() { super(); }

	public void setValue(Object value) {
        if ( logger.isTraceEnabled() )
        {
            logger.trace( "MyDateRenderer::setValue(): invoked with value=" + value );
        }
		setText(  ( value == null ) || ( ! ( value instanceof Date ) ) ? "" : DateArmyKnife.formatWithSeconds( (Date)value ) );
	}
}

/**
 * Let's parse entered dates as we like them too!!
 */
static class MyDateEditor extends DefaultCellEditor
{
    DateParser parser;
    JTextField textEntryField;

    public MyDateEditor(JCheckBox checkBox)
    {
        super(checkBox);
    }

    public MyDateEditor(JComboBox comboBox)
    {
        super(comboBox);
    }
    
    public MyDateEditor( JTextField textField )
    {
        super( textField );    // we don't want to call super!!!!   but we must apparently!?!!
        logger.debug( "MyDateEditor::constructor() INVOKED!!" );
        this.textEntryField = textField;
        parser = new AussieDateParser();
        editorComponent = textField;
        this.clickCountToStart = 2;
        delegate = new EditorDelegate() {
            public void setValue( Object value )
            {
                String text = (value != null) ? DateArmyKnife.formatWithSeconds((Date) value) : "";
                logger.debug( "setValue():: About to set textEntryField value to [" + text + "]" );
                textEntryField.setText( text );
            }

            public Object getCellEditorValue() {
                Object result;
                result = parser.parse( textEntryField.getText() ).getTime();
                logger.debug( "getCellEditorValue():: invoked. text field value=[" + textEntryField.getText() + "], about to return [" + result + "]" );
                return result;
            }
        };
        textField.addActionListener( delegate );
    }
}

static class MyActionButtonRenderer extends JPanel implements TableCellRenderer
{
    JButton addButton = null;
    JButton removeButton = null;
    JPanel panel;
    ActionListener listener = null;
    JPanel blah;
    
    public MyActionButtonRenderer( ActionListener listener )
    {
        super();
    }
    
    public MyActionButtonRenderer()
    {
        super();
    }
    
    public void setActionListener( ActionListener listener )
    {
        this.listener = listener;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        if ( panel == null )
        {
            panel = new JPanel();
            addButton = new JButton( "add" );
            addButton.setActionCommand( "add,row=" + row );

            removeButton = new JButton( "del" );
            removeButton.setActionCommand( "del,row=" + row );
            if ( listener != null )
            {
                addButton.addActionListener( listener );
                removeButton.addActionListener( listener );
            }
            panel.add( addButton );
            panel.add( removeButton );
        }
        return panel;
    }
}
}
