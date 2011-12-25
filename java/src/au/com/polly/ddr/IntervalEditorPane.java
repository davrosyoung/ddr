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

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Displays the data interval boundaries to the operator.
 * 
 * @author Dave Young
 * 
 */
public class IntervalEditorPane extends JPanel implements ActionListener, TableModelListener
{
private final static Logger logger = Logger.getLogger(  IntervalEditorPane.class  );
protected GasWellDataSet dataSet = null;

protected JButton saveButton;
protected JButton cancelButton;
protected JScrollPane scrollPane;
protected JTable dataDisplayTable;
protected GasWellDataSetTableModel dataTableModel;


protected IntervalEditorPane( GasWellDataSet dataSet )
{
    dataTableModel = new GasWellDataSetTableModel( dataSet );
	scrollPane = new JScrollPane();
    populate();
}

protected void populate()
{
    saveButton = new JButton();
    saveButton.setName( "save" );
    saveButton.setText("save");
    saveButton.setEnabled( false );
    saveButton.addActionListener( this );
    
    cancelButton = new JButton();
    cancelButton.setName( "cancel" );
    cancelButton.setText("cancel");
    cancelButton.addActionListener( this );
    
    dataDisplayTable = new JTable( dataTableModel );
    dataDisplayTable.setEnabled( true );
    dataDisplayTable.setVisible( true );
    dataDisplayTable.setName( "dataTable" );
	dataTableModel.addTableModelListener( this );

    for( int i = 0; i < dataTableModel.getColumnCount(); i++ )
    {
        dataDisplayTable.getColumnModel().getColumn( i ).setPreferredWidth( dataTableModel.getColumnType( i ).width );
    }


	scrollPane.add( dataDisplayTable );
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
    add( dataDisplayTable, gbc );

    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.gridx = 8;
    gbc.gridy = 11;
    
    add( saveButton, gbc );
    
    gbc.gridx=9;
    
    add( cancelButton, gbc );
    setVisible( true );
}

@Override
public void actionPerformed(ActionEvent e)
{
	logger.debug(  "received action, command=\"" + e.getActionCommand() + "\", source=\"" + e.getSource() + "\"" );
	
	if ( e.getActionCommand().equals(  "cancel" ) )
	{
		// close down this panel!!! How to do this?!?
		// todo: close down this panel
		this.setVisible(  false );
	}
    //To change body of implemented methods use File | Settings | File Templates.
}

	@Override
	public void tableChanged( TableModelEvent tableModelEvent )
	{
		logger.debug(
				"source=" + tableModelEvent.getSource() + ", "
			+	"type=" + getTableModelEventTypeText( tableModelEvent.getType() ) + ", "
			+	"column=" + tableModelEvent.getColumn() + ", "
			+	"firstRow=" + tableModelEvent.getFirstRow() + ", "
			+	"lastRow=" + tableModelEvent.getLastRow()
		);


	}
	
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
