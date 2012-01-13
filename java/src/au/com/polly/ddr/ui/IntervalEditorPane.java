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
import au.com.polly.util.DateArmyKnife;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * Displays the data interval boundaries to the operator.
 * 
 * @author Dave Young
 * 
 */
public class IntervalEditorPane extends JPanel implements ActionListener, TableModelListener
{
private final static Logger logger = Logger.getLogger(  IntervalEditorPane.class  );
protected GasWellDataSet averagedDataSet = null;
protected GasWellDataSet originalDataSet = null;

protected JLabel spacerLabel;
protected JButton saveButton;
protected JButton cancelButton;
protected JButton csvButton;
protected JScrollPane scrollPane;
protected JTable dataDisplayTable;
protected GasWellDataSetTableModel dataTableModel;
protected List<IntervalEditorListener> editorListenerList;


protected IntervalEditorPane( GasWellDataSet averagedDataSet, GasWellDataSet originalDataSet )
{
	this.averagedDataSet = averagedDataSet;
	this.originalDataSet = originalDataSet;
    editorListenerList = new ArrayList<IntervalEditorListener>();
    dataTableModel = new GasWellDataSetTableModel( averagedDataSet, originalDataSet );
    populate();
}

protected void populate()
{
    spacerLabel = new JLabel();
    spacerLabel.setText( " " );
    
    saveButton = new JButton();
    saveButton.setName( "save" );
    saveButton.setText( "Apply");
    saveButton.setActionCommand( "saveIntervalEditor" );
    saveButton.setEnabled( true );
    saveButton.addActionListener( this );
    
    cancelButton = new JButton();
    cancelButton.setName( "cancel" );
    cancelButton.setText( "Cancel" );
    cancelButton.setActionCommand( "cancel" );
    cancelButton.addActionListener( this );
    
    csvButton = new JButton();
    csvButton.setName("csv");
    csvButton.setText("csv");
    csvButton.setActionCommand( "csv" );
    csvButton.setEnabled( true );
    csvButton.addActionListener(this);

    dataDisplayTable = new JTable( dataTableModel );
    dataDisplayTable.setEnabled( true );
    dataDisplayTable.setVisible( true );
    dataDisplayTable.setName( "dataTable" );
    dataDisplayTable.setRowHeight( 32 );

    scrollPane = new JScrollPane( dataDisplayTable );
	dataTableModel.addTableModelListener( this );
    
    Action addRow = new AbstractAction( "Add" )
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            JTable table = (JTable) e.getSource();
            int row = Integer.valueOf(e.getActionCommand());
            GasWellDataSetTableModel model = (GasWellDataSetTableModel)table.getModel();
            model.addRow( row );
        }
    };
    
    ButtonColumn addColumn = new ButtonColumn( dataDisplayTable, addRow, dataTableModel.getColumnIndex( GasWellDataSetTableModel.ColumnType.ADD ) );

    Action delRow = new AbstractAction( "Del" )
    {
        @Override
        public void actionPerformed( ActionEvent e )
        {
            JTable table = (JTable) e.getSource();
            int row = Integer.valueOf( e.getActionCommand() );
            GasWellDataSetTableModel model = (GasWellDataSetTableModel)table.getModel();
			if ( row > 0 )
			{
				model.deleteRow( row  );

				ListSelectionModel lsm = table.getSelectionModel();
				lsm.setSelectionInterval( row - 1, row - 1 );
			}
        }
    };

    ButtonColumn removeColumn = new ButtonColumn( dataDisplayTable, delRow, dataTableModel.getColumnIndex( GasWellDataSetTableModel.ColumnType.DELETE ) );


    for( int i = 0; i < dataTableModel.getColumnCount(); i++ )
    {
        TableCellRenderer renderer = null;
        TableCellEditor editor = null;
        TableColumn column = null;
        GasWellDataSetTableModel.ColumnType ct = null;
        
        column = dataDisplayTable.getColumnModel().getColumn( i );
        ct = dataTableModel.getColumnType( i );
        
        column.setPreferredWidth( ct.width );
        Class klass;
        if ( ( klass = ct.getRendererClass() ) != null )
        {
            try
            {
                renderer = (TableCellRenderer)klass.getConstructor().newInstance();
                column.setCellRenderer( renderer );
            } catch ( Throwable t ) {
                t.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
        
        if ( ( klass = ct.getEditorClass() ) != null )
        {

            if ( klass == GasWellDataSetTableModel.MyDateEditor.class )
            {
                column.setCellEditor( new GasWellDataSetTableModel.MyDateEditor( new JTextField() ) );
            }

        }
/*
        if ( ( editor = ct.getEditor() ) != null )
        {
            column.setCellEditor( editor );
        }
*/
    }
    
//	scrollPane.add( dataDisplayTable );
    dataDisplayTable.setFillsViewportHeight( true );

    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.weightx = 0.5;
    gbc.weighty = 0.5;
    gbc.anchor = GridBagConstraints.PAGE_START;
    gbc.fill = GridBagConstraints.HORIZONTAL;

	gbc.gridheight = 1;
	gbc.gridwidth = 10;
	add( dataDisplayTable.getTableHeader(), gbc );

	gbc.gridy = 1;
    gbc.gridwidth = 10;
    gbc.gridheight = 9;
    add( scrollPane, gbc );

    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    
    gbc.gridwidth = 6;
    gbc.gridx = 1;
    add( spacerLabel, gbc );


	//  CSV button is only really for debugging ... not intended for production purposes...
	gbc.gridx = 7;
	gbc.gridy = 11;
	gbc.gridwidth = 1;
	add( csvButton, gbc );


    gbc.gridwidth = 1;
    
    gbc.gridx = 8;
    gbc.gridy = 11;
    add( saveButton, gbc );

    gbc.gridx=9;
    
    add( cancelButton, gbc );


    scrollPane.setPreferredSize( new Dimension( 1200, 550 ) );
    setPreferredSize(new Dimension( 1200, 600 ) );
    setVisible( true );
}

@Override
public void actionPerformed( ActionEvent e )
{
	logger.debug( "ACTION COMMAND> received action, command=\"" + e.getActionCommand() + "\", source=\"" + e.getSource() + "\"" );
	
	if ( e.getActionCommand().equals(  "cancel" ) )
	{
		// close down this panel!!! How to do this?!?
		// todo: close down this panel
		this.setVisible(  false );
        for( IntervalEditorListener listener : editorListenerList )
        {
            listener.cancelIntervalEditor();
        }
        
	}
    
    if ( e.getActionCommand().equals( "saveIntervalEditor" ) )
    {
        logger.debug( "requested to saveIntervalEditor the data!!" );
        for( IntervalEditorListener listener : editorListenerList )
        {
            listener.saveIntervalEditor(dataTableModel.averagedData );
        }
    }
    
    if ( e.getActionCommand().equals( "csv" ) )
    {
        logger.debug( "requested to output csv" );
        logger.debug( "we have " + dataTableModel.getRowCount() + " rows in model. averagedData.getSize()=" + dataTableModel.averagedData.getData().size() );
        PrintWriter writer = new PrintWriter( System.out );
        dataTableModel.averagedData.outputCSV( writer );
        writer.flush();
        logger.debug( "csv output finished" );
		System.out.println( "-------------------------------------------" );
		System.out.println( "JAVA DATE RANGES" ) ;
		for( GasWellDataEntry entry : dataTableModel.averagedData.getData() )
		{
			System.out.println( "new DateRange( \"" + DateArmyKnife.formatWithMinutes( entry.from() ) + "\", \"" + DateArmyKnife.formatWithMinutes( entry.until() ) + "\" )," );
		}
		System.out.println( "-------------------------------------------" );
		System.out.println( "from,until,duration,oil,condensate,gas,water" );
		for( GasWellDataEntry entry : dataTableModel.averagedData.getData() )
		{
			Calendar cal = Calendar.getInstance();

			cal.setTime( entry.from() );
			System.out.print( "=DATE(" + cal.get( Calendar.YEAR ) );
			System.out.print( ";" + ( cal.get(  Calendar.MONTH ) + 1 ) );
			System.out.print( ";" + cal.get( Calendar.DAY_OF_MONTH ) + ")+TIME(" );
			System.out.print(  cal.get( Calendar.HOUR_OF_DAY) + ";" + cal.get( Calendar.MINUTE ) + ";" + cal.get(  Calendar.SECOND  ) + ")" );
			System.out.print(  "," );

			cal.setTime( entry.until() );
			System.out.print( "=DATE(" + cal.get(  Calendar.YEAR ) );
			System.out.print(  ";" + ( cal.get(  Calendar.MONTH ) + 1 ) );
			System.out.print(  ";" + cal.get(  Calendar.DAY_OF_MONTH ) + ")+TIME(" );
			System.out.print(  cal.get( Calendar.HOUR_OF_DAY) + ";" + cal.get( Calendar.MINUTE ) + ";" + cal.get(  Calendar.SECOND  ) + ")" );
			System.out.print(  "," );
			Formatter formatter = new Formatter( writer, Locale.UK );
			formatter.format(  "%10.4f", entry.getIntervalLengthMS() / 3600000.0 );
			for( WellMeasurementType wmt : WellMeasurementType.values() )
			{
				if ( entry.containsMeasurement( wmt ) )
				{
					formatter.format(  ",%12.8f", entry.getMeasurement( wmt ) );
				} else {
					writer.print(  ",    0.00000000" );
				}
			}
			writer.println();
			writer.flush();
		}
		System.out.flush();
    }
    
    if ( e.getActionCommand().startsWith( "add" ) )
    {
        logger.info( "Received ADD action command ... \"" + e.getActionCommand() + "\"" );
    }
    
    if ( e.getActionCommand().startsWith( "del" ) )
    {
        logger.info( "Received DEL action command ... \"" + e.getActionCommand() + "\"" );

    }
    //To change body of implemented methods use File | Settings | File Templates.
}

public void addEditorListener( IntervalEditorListener listener )
{
    if ( ( editorListenerList != null ) && ! editorListenerList.contains( listener ) )
    {
        editorListenerList.add( listener );
    }
    return;
}


	@Override
	public void tableChanged( TableModelEvent tableModelEvent )
	{
		logger.debug(
				"TABLE CHANGED --> source=" + tableModelEvent.getSource() + ", "
			+	"type=" + getTableModelEventTypeText( tableModelEvent.getType() ) + ", "
			+	"column=" + tableModelEvent.getColumn() + ", "
			+	"firstRow=" + tableModelEvent.getFirstRow() + ", "
			+	"lastRow=" + tableModelEvent.getLastRow()
		);


	}

/**
 * Internal convenience method. Enables us to obtain a human readable version of
 * a table model event type.
 *
 * @param type
 * @return
 */
	protected static String getTableModelEventTypeText( int type )
	{           
		String result = "unbekannt (" + type + ")";
		switch( type )
		{
			case TableModelEvent.DELETE:
				result = "DELETE";
				break;

			case TableModelEvent.INSERT:
				result="INSERT";
				break;

			case TableModelEvent.UPDATE:
				result="UPDATE";
				break;
		}
				
		return result;
	}
}
