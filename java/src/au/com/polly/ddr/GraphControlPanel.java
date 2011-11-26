package au.com.polly.ddr;

import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import org.apache.log4j.Logger;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Controls to allow the operator to change the measurements type
 * being viewed, change the scale and add cutoff points.
 *
 *
 */
public class GraphControlPanel extends JPanel implements ActionListener
{
static private Logger logger = Logger.getLogger( GraphControlPanel.class );
SimpleDateFormat dateFormatter = new SimpleDateFormat( "dd/MMM/yyyy HH:mm:ss" );
DateParser dateParser = new AussieDateParser();

    JButton oilFlowButton;
    JButton gasFlowButton;
    JButton condensateFlowButton;
    JButton waterFlowButton;
    JButton loadFileButton;
    JFileChooser loadFileBox;
    JLabel  availableDateLabel;
    JLabel  fromDateLabel;
    JLabel  untilDateLabel;
    JTextField fromDateField;
    JTextField untilDateField;
    JButton updateGraphFromDatesButton;
    JButton generateOverlayButton;

    PlotGrapher grapher;
    Date from = null;
    Date until = null;

public GraphControlPanel( Date from, Date until )
{
    oilFlowButton = new JButton();
    oilFlowButton.setEnabled( true );
    oilFlowButton.setName( "oilFlowButton" );
    oilFlowButton.setText( "Oil Flow" );
    oilFlowButton.setVisible( true );
    oilFlowButton.addActionListener( this );

    gasFlowButton = new JButton();
    gasFlowButton.setEnabled( true );
    gasFlowButton.setText( "Gas Flow" );
    gasFlowButton.setName( "gasFlowButton" );
    gasFlowButton.setVisible( true );
    gasFlowButton.addActionListener( this );

    waterFlowButton = new JButton();
    waterFlowButton.setName( "waterFlowButton" );
    waterFlowButton.setText( "Water Flow" );
    waterFlowButton.setEnabled( true );
    waterFlowButton.setVisible( true );
    waterFlowButton.addActionListener( this );

    condensateFlowButton = new JButton();
    condensateFlowButton.setName( "condensateFlowButton" );
    condensateFlowButton.setText( "Condensate" );
    condensateFlowButton.setEnabled( true );
    condensateFlowButton.setVisible( true );
    condensateFlowButton.addActionListener( this );

    loadFileButton = new JButton();
    loadFileButton.setText( "Open ..." );
    loadFileButton.addActionListener( this );
    loadFileButton.setName( "openFileButton" );

    generateOverlayButton = new JButton();
    generateOverlayButton.setText( "reduce" );
    generateOverlayButton.setName("reduce");
    generateOverlayButton.addActionListener( this );

    loadFileBox = new JFileChooser();
    loadFileBox.setDialogTitle( "Data File" );

    fromDateLabel = new JLabel( "from:" );
    untilDateLabel = new JLabel( "until:" );
    StringBuilder dateAvailabilityText = new StringBuilder( "from:" );
    dateAvailabilityText.append( ( from != null ) ? dateFormatter.format( from ) : "unavailable" );
    dateAvailabilityText.append( " until:" );
    dateAvailabilityText.append( ( until != null ) ? dateFormatter.format( until ) : "unavailable" );
    availableDateLabel = new JLabel( dateAvailabilityText.toString() );

    fromDateField = new JTextField();
    if ( from != null )
    {
        fromDateField.setText( dateFormatter.format( from ) );
    } else {
        fromDateField.setEnabled( false );
    }

    untilDateField = new JTextField();
    if ( until != null )
    {
        untilDateField.setText( dateFormatter.format( until ) );
    } else {
        untilDateField.setEnabled( false );
    }

    updateGraphFromDatesButton = new JButton();
    updateGraphFromDatesButton.setText( "update graph" );
    updateGraphFromDatesButton.addActionListener( this );
    updateGraphFromDatesButton.setName( "updateGraph" );


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
    add(oilFlowButton, gbc );

    gbc.gridx = 1;
    add(gasFlowButton, gbc);

    gbc.gridx = 2;
    add(condensateFlowButton, gbc);

    gbc.gridx = 3;
    add(waterFlowButton, gbc );

    gbc.gridx = 4;
    add( availableDateLabel,gbc );

    gbc.gridx = 5;
    add( fromDateLabel, gbc );

    gbc.gridx = 6;
    add( fromDateField, gbc );

    gbc.gridx = 7;
    gbc.gridheight = 2;
    add( updateGraphFromDatesButton, gbc );

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 2;
    gbc.gridheight = 1;
    add( loadFileButton, gbc );

    gbc.gridx = 4;
    gbc.gridwidth = 1;
    add( generateOverlayButton, gbc );

    gbc.gridx = 5;
    add( untilDateLabel, gbc );

    gbc.gridx = 6;
    add( untilDateField, gbc );

    setPreferredSize( new Dimension( 400, 100 ) );
    setVisible( true );
}

public void setPlotGrapher( PlotGrapher grapher )
{
    this.grapher = grapher;
}

@Override
public void actionPerformed(ActionEvent evt)
{
    JButton button;
    Object source;
    logger.debug( "intercepted action event " + evt );


    if ( ( source = evt.getSource() ) instanceof JButton )
    {
        button = (JButton)source;

        logger.debug( "source.name=\"" + button.getName() + "\"");

        do {

            if( button.getName().equals( "openFileButton" ) )
            {
                loadFileBox.showOpenDialog( this );
                break;
            }

            if ( button.getName().equals( "oilFlowButton" ) )
            {
                boolean displayed = grapher.getDisplayMeasurementType( WellMeasurementType.OIL_FLOW );
                grapher.setDisplayMeasurementType( WellMeasurementType.OIL_FLOW, !displayed );
                logger.debug( "setting displayed oil data to " + !displayed );
            }


            if ( button.getName().equals( "waterFlowButton" ) )
            {
                boolean displayed = grapher.getDisplayMeasurementType( WellMeasurementType.WATER_FLOW );
                grapher.setDisplayMeasurementType( WellMeasurementType.WATER_FLOW, !displayed );
                logger.debug( "setting displayed water data to " + !displayed );
            }


            if ( button.getName().equals( "gasFlowButton" ) )
            {
                boolean displayed = grapher.getDisplayMeasurementType( WellMeasurementType.GAS_FLOW );
                grapher.setDisplayMeasurementType( WellMeasurementType.GAS_FLOW, !displayed );
                logger.debug( "setting displayed gas data to " + !displayed );
            }


            if ( button.getName().equals( "condensateFlowButton" ) )
            {
                boolean displayed = grapher.getDisplayMeasurementType( WellMeasurementType.CONDENSATE_FLOW );
                grapher.setDisplayMeasurementType( WellMeasurementType.CONDENSATE_FLOW, !displayed );
                logger.debug( "setting displayed condensate data to " + !displayed );
            }

            if ( button.getName().equals( "updateGraph" ) )
            {
                Calendar from = null;
                Calendar until = null;

                try
                {
                    from = dateParser.parse( fromDateField.getText() );
                    until = dateParser.parse( untilDateField.getText() );
                } catch (Exception e) {
                    JOptionPane.showMessageDialog( this, "Dates should be in dd/mm/yyyy hh:mm:ss format.");
                }

                if ( ( from != null ) && ( until != null ) )
                {
                    grapher.setDisplayDateRange( from.getTime(), until.getTime() );
                }
            }

            if ( button.getName().equals( "reduce" ) )
            {
                DataRateReducer reducer = new SimpleDiscontinuityDataRateReducer();
                grapher.reduce( reducer );
            }

        } while( false );
    }
}
}
