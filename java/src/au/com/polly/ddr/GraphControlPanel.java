package au.com.polly.ddr;

import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import org.apache.log4j.Logger;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static javax.swing.JOptionPane.*;

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
    JButton loadOverlayFileButton;
    JButton saveOverlayFileButton;
    JFileChooser loadFileBox;
    JFileChooser loadOverlayFileBox;
    JFileChooser saveOverlayFileBox;
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
    this.from = from;
    this.until = until;

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
    loadFileButton.setText( "Open Background data ..." );
    loadFileButton.addActionListener( this );
    loadFileButton.setName( "openFileButton" );

    loadOverlayFileButton = new JButton();
    loadOverlayFileButton.setText( "Open Overlay data..." );
    loadOverlayFileButton.addActionListener( this );
    loadOverlayFileButton.setName( "openOverlayFileButton" );

    generateOverlayButton = new JButton();
    generateOverlayButton.setText( "reduce" );
    generateOverlayButton.setName("reduce");
    generateOverlayButton.addActionListener( this );

    saveOverlayFileButton = new JButton();
    saveOverlayFileButton.setText( " Save Overlay Data..." );
    saveOverlayFileButton.addActionListener( this );
    saveOverlayFileButton.setName( "saveOverlayFileButton" );

    loadFileBox = new JFileChooser();
    loadFileBox.setDialogTitle( "Open Background Data File" );
    loadFileBox.setFileFilter( new ObjectFileFilter() );

    loadOverlayFileBox = new JFileChooser();
    loadOverlayFileBox.setDialogTitle( "Open Overlay Data File" );
    loadOverlayFileBox.setFileFilter( new ObjectFileFilter() );

    saveOverlayFileBox = new JFileChooser();
    saveOverlayFileBox.setDialogTitle( "Save Overlay Data File" );

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
    gbc.gridwidth = 1;
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
    gbc.gridwidth = 1;
    gbc.gridheight = 1;

    add( generateOverlayButton, gbc );

    gbc.gridx = 5;
    add( untilDateLabel, gbc );
    gbc.gridx = 6;
    add( untilDateField, gbc );

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    add( loadFileButton, gbc );

    gbc.gridx = 2;
    add( loadOverlayFileButton, gbc );

    gbc.gridx = 4;
    add( saveOverlayFileButton, gbc );

    setPreferredSize( new Dimension( 500, 150 ) );
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
    GasWellDataSet overlayData;


    if ( ( source = evt.getSource() ) instanceof JButton )
    {
        button = (JButton)source;

        logger.debug( "source.name=\"" + button.getName() + "\"");

        do {

            if( button.getName().equals( "openFileButton" ) )
            {
                int retval = loadFileBox.showOpenDialog( this );
                if ( retval == JFileChooser.APPROVE_OPTION )
                {
                    File file = loadFileBox.getSelectedFile();
                    if ( file != null )
                    {
                        // load the file!!
                        // ---------------------------
                        GasWellDataSet newData = null;
                        FileInputStream fis = null;
                        ObjectInputStream ois = null;
                        Object blah = null;
                        try
                        {
                            fis = new FileInputStream( file );
                            ois = new ObjectInputStream( fis );
                            blah = ois.readObject();
                            if ( ! ( blah instanceof GasWellDataSet ) )
                            {
                                JOptionPane.showMessageDialog( this, "File \"" + file.getAbsolutePath() + "\" does not contain gas well data.", "Error loading data", JOptionPane.ERROR_MESSAGE );
                            } else {
                                newData = (GasWellDataSet)blah;
                                from = newData.from();
                                until = newData.until();
                                grapher.setDisplayDateRange( from, until );
                                grapher.loadData( newData );
                                StringBuilder dateAvailabilityText = new StringBuilder( "from:" );
                                dateAvailabilityText.append( ( from != null ) ? dateFormatter.format( from ) : "unavailable" );
                                dateAvailabilityText.append( " until:" );
                                dateAvailabilityText.append( ( until != null ) ? dateFormatter.format( until ) : "unavailable" );
                                availableDateLabel.setText(dateAvailabilityText.toString());
                                fromDateField.setText( dateFormatter.format(from) );
                                untilDateField.setText( dateFormatter.format( until ) );
                            }
                        } catch ( Exception e )
                        {
                            logger.error( "Failed to load gas well data set from file \"" + file.getAbsolutePath() + "\" - " + e.getClass().getName() + "  - " + e.getMessage() );
                            JOptionPane.showMessageDialog( this, "Failed to load gas well data from \"" + file.getAbsolutePath() + "\"", "Error loading data", JOptionPane.ERROR_MESSAGE );
                        }
                    }

                }
                break;
            }
            if( button.getName().equals( "openOverlayFileButton" ) )
            {
                int retval = loadOverlayFileBox.showOpenDialog( this );
                if ( retval == JFileChooser.APPROVE_OPTION )
                {
                    File file = loadFileBox.getSelectedFile();
                    if ( file != null )
                    {
                        // load the file!!
                        // ---------------------------
                        GasWellDataSet newData = null;
                        FileInputStream fis = null;
                        ObjectInputStream ois = null;
                        Object blah = null;
                        try
                        {
                            fis = new FileInputStream( file );
                            ois = new ObjectInputStream( fis );
                            blah = ois.readObject();
                            if ( ! ( blah instanceof GasWellDataSet ) )
                            {
                                JOptionPane.showMessageDialog( this, "File \"" + file.getAbsolutePath() + "\" does not contain gas well data.", "Error loading data", JOptionPane.ERROR_MESSAGE );
                            } else {
                                newData = (GasWellDataSet)blah;
                                grapher.loadOverlayData( newData );
                                StringBuilder dateAvailabilityText = new StringBuilder( "from:" );
                            }
                        } catch ( Exception e )
                        {
                            logger.error( "Failed to load gas well data set from file \"" + file.getAbsolutePath() + "\" - " + e.getClass().getName() + "  - " + e.getMessage() );
                            JOptionPane.showMessageDialog( this, "Failed to load gas well data from \"" + file.getAbsolutePath() + "\"", "Error loading data", JOptionPane.ERROR_MESSAGE );
                        }
                    }

                }
                break;
            }


            if ( button.getName().equals( "saveOverlayFileButton" ) )
            {
                if ( ( overlayData = grapher.getOverlayData() ) != null )
                {
                    if ( saveOverlayFileBox.showSaveDialog( this ) == JFileChooser.APPROVE_OPTION )
                    {
                        logger.info( "THEY WANT TO SAVE!!" );
                        File file = saveOverlayFileBox.getSelectedFile();
                        if ( file != null )
                        {
                            FileOutputStream fos = null;
                            ObjectOutputStream oos = null;

                            try
                            {
                                fos = new FileOutputStream( file );
                                oos = new ObjectOutputStream( fos );
                                oos.writeObject( grapher.getOverlayData() );
                            } catch (IOException e)
                            {
                                JOptionPane.showMessageDialog( this, "Failed to save overlay data to \"" + file.getAbsolutePath() + "\"" );
                                logger.error( "Failed to save overlay data to file \"" + file.getAbsolutePath() + "\"" );
                                logger.error( e.getClass().getName() + " - " + e.getMessage() );
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog( this, "No overlay data to save", "Cannot save overlay", JOptionPane.WARNING_MESSAGE );
                }
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
                Calendar fromInput = null;
                Calendar untilInput = null;

                try
                {
                    fromInput = dateParser.parse( fromDateField.getText() );
                } catch (Exception e) {
                    JOptionPane.showMessageDialog( this, "Dates should be in dd/mm/yyyy hh:mm:ss format.", "Invalid From Date", JOptionPane.ERROR_MESSAGE );
                    fromDateField.setText( dateFormatter.format( from ) );
                }

                try
                {
                    untilInput = dateParser.parse( untilDateField.getText() );
                } catch (Exception e)
                {
                    JOptionPane.showMessageDialog(this, "Dates should be in dd/mm/yyyy hh:mm:ss format.", "Invalid Until Date", JOptionPane.ERROR_MESSAGE);
                    untilDateField.setText( dateFormatter.format( until ) );
                }


                if ( ( fromInput != null ) && ( untilInput != null ) )
                {
                    grapher.setDisplayDateRange( fromInput.getTime(), untilInput.getTime() );
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


/* ImageFilter.java is used by FileChooserDemo2.java. */
public class ObjectFileFilter extends FileFilter
{
    //Accept all directories and all gif, jpg, tiff, or png files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        return f.getName().endsWith( ".obj" );
    }

    //The description of this filter
    public String getDescription() {
        return "Just Images";
    }
}
}
