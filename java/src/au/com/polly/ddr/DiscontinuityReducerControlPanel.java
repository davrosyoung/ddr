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

import au.com.polly.plotter.TimeUnit;
import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateArmyKnife;
import au.com.polly.util.DateParser;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides the operator some controls to determine how the data reduction will take place..
 *
 *
 */
public class DiscontinuityReducerControlPanel extends JPanel implements ActionListener, ListSelectionListener
{
private final static Logger logger = Logger.getLogger( DiscontinuityReducerControlPanel.class );
ReductionPanelListener listener;
GasWellDataSet dataSet;
JLabel primaryIndicatorLabel;
JComboBox primaryIndicatorMenu;
JLabel secondaryIndicatorLabel;
JComboBox secondaryIndicatorMenu;
JLabel medianCrosserLabel;
JCheckBox medianCrosserBox;
JLabel medianCrosserBoundaryDistanceLabel;
JTextField medianCrosserBoundaryDistanceQuantityField;
JComboBox medianCrosserBoundaryDistanceUnitMenu;
JLabel regularIntervalLabel;
JCheckBox regularIntervalBox;
JLabel intervalStartLabel;
JTextField intervalStartField;
JLabel intervalRepetitionLabel;
JTextField intervalRepetitionQuantityField;
JComboBox intervalRepetitionUnitMenu;
JButton reduceButton;
JButton cancelButton;
DateParser parser;

IndicatorListModel primaryIndicatorListModel;
IndicatorListModel secondaryIndicatorListModel;


protected DiscontinuityReducerControlPanel()
{
    parser = new AussieDateParser();
    populate();
}

public void setData( GasWellDataSet dataSet )
{
    this.dataSet = dataSet;
    if ( this.dataSet != null )
    {
        this.intervalStartField.setText( DateArmyKnife.formatWithMinutes( dataSet.from() ) );
    }
    
    this.primaryIndicatorListModel.update();
    this.secondaryIndicatorListModel.update();
    
    if ( dataSet.containsMeasurement( WellMeasurementType.OIL_FLOW ) )
    {
        this.primaryIndicatorMenu.setSelectedItem( WellMeasurementType.OIL_FLOW );
        this.secondaryIndicatorMenu.setSelectedItem( WellMeasurementType.GAS_FLOW );
    } else {
        if ( dataSet.containsMeasurement( WellMeasurementType.CONDENSATE_FLOW ) )
        {
            this.primaryIndicatorMenu.setSelectedItem( WellMeasurementType.CONDENSATE_FLOW );
            this.secondaryIndicatorMenu.setSelectedItem( WellMeasurementType.GAS_FLOW );
        } else {
            this.primaryIndicatorMenu.setSelectedItem( WellMeasurementType.GAS_FLOW );
            this.secondaryIndicatorMenu.setSelectedItem( WellMeasurementType.WATER_FLOW );
        }
    }
    
}

protected GasWellDataSet getDataSet()
{
    return dataSet;
}
        

protected void populate()
{
    GridBagLayout gridBagLayout = new GridBagLayout();
    setLayout( gridBagLayout );
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.gridheight = 1;
    gbc.weightx = 0.5;
    gbc.weighty = 0.5;
    gbc.anchor = GridBagConstraints.PAGE_START;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    primaryIndicatorLabel = new JLabel( "Primary Indicator" );
    add( primaryIndicatorLabel, gbc );

    primaryIndicatorMenu = new JComboBox();
    primaryIndicatorMenu.setName("primaryIndicatorMenu");
    primaryIndicatorMenu.addActionListener(this);
    primaryIndicatorListModel = new IndicatorListModel();
    primaryIndicatorMenu.setModel(primaryIndicatorListModel);
    primaryIndicatorMenu.setActionCommand( "updatePrimaryIndicator" );
    gbc.gridx = 2;
    gbc.gridwidth = 1;
    add(primaryIndicatorMenu, gbc );
    
    gbc.gridy++;
    gbc.gridx = 0;
    gbc.gridwidth = 2;
    secondaryIndicatorLabel = new JLabel( "Secondary Indicator" );
    add( secondaryIndicatorLabel, gbc );
    
    gbc.gridx = 2;
    gbc.gridwidth = 1;
    secondaryIndicatorMenu = new JComboBox();
    secondaryIndicatorMenu.setName( "secondaryIndicatorMenu" );
    secondaryIndicatorListModel = new IndicatorListModel();
    secondaryIndicatorMenu.setModel(secondaryIndicatorListModel);
    primaryIndicatorMenu.setActionCommand( "updateSecondaryIndicator" );
    add( secondaryIndicatorMenu, gbc );
    
    gbc.gridy++;
    gbc.gridx = 0;
    gbc.gridwidth=2;
    medianCrosserLabel = new JLabel( "detect median crossovers?");
    add( medianCrosserLabel, gbc );
    
    gbc.gridx = 2;
    gbc.gridwidth = 1;
    medianCrosserBox = new JCheckBox();
    medianCrosserBox.setName( "medianCrosserBox" );
    medianCrosserBox.setActionCommand( "medianCrosserBox" );
    medianCrosserBox.setSelected( false );
    add( medianCrosserBox, gbc );

    gbc.gridx = 0;
    gbc.gridy++;
    gbc.gridwidth = 2;
    medianCrosserBoundaryDistanceLabel = new JLabel( "min median crosser boundary distance" );
    medianCrosserBoundaryDistanceLabel.setEnabled( false );
    add( medianCrosserBoundaryDistanceLabel, gbc );
    
    gbc.gridx += gbc.gridwidth;
    gbc.gridwidth = 1;
    medianCrosserBoundaryDistanceQuantityField = new JTextField();
    medianCrosserBoundaryDistanceQuantityField.setText( "1" );
    medianCrosserBoundaryDistanceQuantityField.setName("medianCrosserDistanceQty");
    medianCrosserBoundaryDistanceQuantityField.setActionCommand("medianCrosserDistanceQty");
    medianCrosserBoundaryDistanceQuantityField.setEnabled( false );
    add( medianCrosserBoundaryDistanceQuantityField, gbc );
    
    gbc.gridx += gbc.gridwidth;
    medianCrosserBoundaryDistanceUnitMenu = new JComboBox( new TimeUnit[] { TimeUnit.DAY, TimeUnit.WEEK, TimeUnit.MONTH } );
    medianCrosserBoundaryDistanceUnitMenu.setSelectedIndex(1);
    medianCrosserBoundaryDistanceUnitMenu.setEnabled( false );
    add( medianCrosserBoundaryDistanceUnitMenu, gbc );

    gbc.gridx = 0;
    gbc.gridy++;
    gbc.gridwidth = 2;
    regularIntervalLabel = new JLabel( "Add regular intervals?" );
    add( regularIntervalLabel, gbc );
    
    gbc.gridx = 2;
    gbc.gridwidth = 1;
    regularIntervalBox = new JCheckBox();
    regularIntervalBox.setName( "regularIntervalBox" );
    regularIntervalBox.setSelected(false);
    regularIntervalBox.setActionCommand( "updateRegularIntervalBox" );
    regularIntervalBox.addActionListener( this );
    add(regularIntervalBox, gbc);
    
    gbc.gridx = 0;
    gbc.gridy++;
    gbc.gridwidth = 1;
    intervalStartLabel = new JLabel( "Starting at:" );
    intervalStartLabel.setEnabled( false );
    add(intervalStartLabel, gbc);
    
    gbc.gridx++;
    gbc.gridwidth = 2;
    intervalStartField = new JTextField();
    intervalStartField.setName( "intervalStartField" );
    intervalStartField.setEnabled(false);
    add(intervalStartField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy++;
    gbc.gridwidth = 1;
    intervalRepetitionLabel = new JLabel( "every" );
    intervalRepetitionLabel.setEnabled( false );
    add(intervalRepetitionLabel, gbc);
    
    gbc.gridx++;
    intervalRepetitionQuantityField = new JTextField();
    intervalRepetitionQuantityField.setName( "intervalRepetitionQuantityField" );
    intervalRepetitionQuantityField.setText("3");
    intervalRepetitionQuantityField.addActionListener(this);
    intervalRepetitionQuantityField.setEnabled( false );
    add(intervalRepetitionQuantityField, gbc);

    gbc.gridx++;
    intervalRepetitionUnitMenu = new JComboBox( new TimeUnit[] { TimeUnit.DAY, TimeUnit.WEEK, TimeUnit.MONTH } );
    intervalRepetitionUnitMenu.setName( "intervalRepititionUnit");
    intervalRepetitionUnitMenu.setActionCommand("intervalRepetitionUnit");
    intervalRepetitionUnitMenu.setEnabled( false );
    intervalRepetitionUnitMenu.addActionListener(this);
    add(intervalRepetitionUnitMenu, gbc );
    
    gbc.gridy++;
    gbc.gridx = 0;
    gbc.gridwidth = 1;
    cancelButton = new JButton( "Cancel" );
    cancelButton.setActionCommand( "cancel" );
    cancelButton.setName("Cancel");
    cancelButton.addActionListener( this );
    add(cancelButton, gbc);
    
    gbc.gridx = 1;
    gbc.gridwidth = 2;
    reduceButton = new JButton( "reduce" );
    reduceButton.setActionCommand("reduce");
    reduceButton.setName( "reduce" );
    reduceButton.addActionListener( this );
    add( reduceButton, gbc  );
    
}

public void setListener( ReductionPanelListener listener )
{
    this.listener = listener;
}

@Override
public void valueChanged(ListSelectionEvent e)
{
    logger.debug( "Called with event=[" + e + "], source=\"" + e.getSource() + "\"" );
}

@Override
public void actionPerformed( ActionEvent e )
{
    WellMeasurementType primaryIndicator;
    WellMeasurementType secondaryIndicator;
    JCheckBox box;
    
    logger.debug( "Invoked with source=[" + e.getSource() + "], actionCommand=\"" + e.getActionCommand() + "\", evt=[" + e + "]" );
    do {
        if ( e.getActionCommand().equals( "updateRegularIntervalBox" ) )
        {
            box = (JCheckBox)e.getSource();
            intervalStartLabel.setEnabled( box.isSelected() );
            intervalStartField.setEnabled( box.isSelected() );
            intervalRepetitionLabel.setEnabled( box.isSelected() );
            intervalRepetitionQuantityField.setEnabled( box.isSelected() );
            intervalRepetitionUnitMenu.setEnabled( box.isSelected() );
        }
        
        if( e.getActionCommand().equals( "cancel" ) )
        {
            if ( listener != null )
            {
                listener.cancelReduction();
            }
        }
        
        if( e.getActionCommand().equals( "reduce" ) )
        {
            if ( listener != null )
            {
                ReductionParameters reductionParameters;
                if ( regularIntervalBox.isSelected() )
                {
                    reductionParameters = new ReductionParameters(
                        (WellMeasurementType)primaryIndicatorMenu.getSelectedItem(),
                        (WellMeasurementType)secondaryIndicatorMenu.getSelectedItem(),
                        true,
                        parser.parse( intervalStartField.getText() ).getTime(),
                        Integer.parseInt( intervalRepetitionQuantityField.getText() ),
                        (TimeUnit)intervalRepetitionUnitMenu.getSelectedItem()
                    );
                } else {
                    reductionParameters = new ReductionParameters(
                            (WellMeasurementType)primaryIndicatorMenu.getSelectedItem(),
                            (WellMeasurementType)secondaryIndicatorMenu.getSelectedItem()
                            );

                }
                listener.reduce( reductionParameters );
            }
        }

    } while( false );
}

class IndicatorListModel extends DefaultComboBoxModel implements ComboBoxModel
{
    List<WellMeasurementType> indicators = null;
    
    IndicatorListModel()
    {
    }
    
    boolean containsMeasurement( WellMeasurementType wmt )
    {
        return indicators.contains( wmt );        
    }
    
    
    protected void update()
    {
        if ( dataSet != null  )
        {
            indicators = new ArrayList<WellMeasurementType>();
            
            for( WellMeasurementType wmt : WellMeasurementType.values() )
            {
                if ( dataSet.containsMeasurement( wmt ) )
                {
                    indicators.add( wmt );
                }
            }
            
        } else {
            indicators = null;
        }
    }
    
    @Override
    public int getSize()
    {
        return indicators != null ? indicators.size() : 0;
    }

    @Override
    public Object getElementAt(int index)
    {
        return ( indicators != null ) ? indicators.get( index ) : 0;
    }

   
}
}
