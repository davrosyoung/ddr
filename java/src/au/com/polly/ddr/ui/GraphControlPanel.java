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

import au.com.polly.ddr.ApplicationConfiguration;
import au.com.polly.ddr.GasWell;
import au.com.polly.ddr.GasWellDataExtractor;
import au.com.polly.ddr.GasWellDataExtractorFactory;
import au.com.polly.ddr.GasWellDataSet;
import au.com.polly.ddr.MultipleWellDataMap;
import au.com.polly.ddr.ReductionParameters;
import au.com.polly.ddr.SimpleDiscontinuityDataRateReducerV2;
import au.com.polly.ddr.WellMeasurementType;
import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Controls to allow the operator to change the measurements type
 * being viewed, change the scale and add cutoff points.
 *
 *
 */
public class GraphControlPanel extends JPanel implements ActionListener, ReductionPanelListener, IntervalEditorListener
{
static private Logger logger = Logger.getLogger( GraphControlPanel.class );
SimpleDateFormat dateFormatter = new SimpleDateFormat( "dd/MMM/yyyy HH:mm:ss" );
DateParser dateParser = new AussieDateParser();
private final static ApplicationConfiguration appConfig = ApplicationConfiguration.getInstance();

JButton oilFlowButton;
JButton gasFlowButton;
JButton condensateFlowButton;
JButton waterFlowButton;
JButton loadFileButton;
JButton editOverlayButton;
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

JDialog reductionDialog = null;
DiscontinuityReducerControlPanel reducerControlPanel = null;

JDialog intervalEditorDialog = null;
IntervalEditorPane intervalEditorPane = null;

public GraphControlPanel( Date from, Date until )
{
    this.from = from;
    this.until = until;
    String label;

    oilFlowButton = new JButton();
    oilFlowButton.setEnabled( false );
    oilFlowButton.setName( "oilFlowButton" );
    oilFlowButton.setText( "Oil Flow" );
    oilFlowButton.setVisible( true );
    oilFlowButton.addActionListener( this );

    gasFlowButton = new JButton();
    gasFlowButton.setEnabled( false );
    gasFlowButton.setText( "Gas Flow" );
    gasFlowButton.setName( "gasFlowButton" );
    gasFlowButton.setVisible( true );
    gasFlowButton.addActionListener( this );

    waterFlowButton = new JButton();
    waterFlowButton.setName( "waterFlowButton" );
    waterFlowButton.setText( "Water Flow" );
    waterFlowButton.setEnabled( false );
    waterFlowButton.setVisible( true );
    waterFlowButton.addActionListener( this );

    condensateFlowButton = new JButton();
    condensateFlowButton.setName( "condensateFlowButton" );
    condensateFlowButton.setText( "Condensate" );
    condensateFlowButton.setEnabled( false );
    condensateFlowButton.setVisible( true );
    condensateFlowButton.addActionListener( this );

    label = appConfig.getOpenBackgroundDataButtonLabel();
    loadFileButton = new JButton();
    loadFileButton.setText( label );
    loadFileButton.addActionListener( this );
    loadFileButton.setName( "openFileButton" );


    label = appConfig.getOpenOverlayDataButtonLabel();
    loadOverlayFileButton = new JButton();
    loadOverlayFileButton.setText( label );
    loadOverlayFileButton.addActionListener( this );
    loadOverlayFileButton.setName( "openOverlayFileButton" );
    loadOverlayFileButton.setEnabled( false );

    label = appConfig.getEditOverlayDataButtonLabel();
    editOverlayButton = new JButton();
    editOverlayButton.setText( label );
    editOverlayButton.addActionListener( this );
    editOverlayButton.setName( "editOverlayData" );
    editOverlayButton.setEnabled( false );

    generateOverlayButton = new JButton();
    generateOverlayButton.setText( "reduce" );
    generateOverlayButton.setName("reduce");
    generateOverlayButton.addActionListener( this );
    generateOverlayButton.setEnabled( false );

    label = appConfig.getSaveOverlayDataButtonLabel();
    saveOverlayFileButton = new JButton();
    saveOverlayFileButton.setText( label );
    saveOverlayFileButton.addActionListener( this );
    saveOverlayFileButton.setName( "saveOverlayFileButton" );
    saveOverlayFileButton.setEnabled( false );

    loadFileBox = new JFileChooser();
    loadFileBox.setDialogTitle( "Open Original CSV Data File" );
    loadFileBox.setFileFilter( new DataSourceFileFilter() );
    loadFileBox.setCurrentDirectory( appConfig.getDefaultOriginalCSVDirectory() );

    loadOverlayFileBox = new JFileChooser();
    loadOverlayFileBox.setDialogTitle("Open Averaged CSV Data File" );
    loadOverlayFileBox.setFileFilter(new DataSourceFileFilter());
    loadOverlayFileBox.setCurrentDirectory( appConfig.getDefaultAveragedCSVDirectory() );

    saveOverlayFileBox = new JFileChooser();
    saveOverlayFileBox.setDialogTitle( "Save Averaged CSV Data File" );
    saveOverlayFileBox.setCurrentDirectory( appConfig.getDefaultAveragedCSVDirectory() );

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
    updateGraphFromDatesButton.setEnabled( false );


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
    
    gbc.gridx = 2;
    add( editOverlayButton, gbc );

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
                GasWellDataSet newData = loadDataFromFileChooser( loadFileBox );
                if ( newData != null )
                {
                    from = newData.from();
                    until = newData.until();
                    grapher.setDisplayDateRange( from, until );
                    grapher.loadData( newData );
                    StringBuilder dateAvailabilityText = new StringBuilder( "from:" );
                    dateAvailabilityText.append( ( from != null ) ? dateFormatter.format( from ) : "unavailable" );
                    dateAvailabilityText.append(" until:");
                    dateAvailabilityText.append((until != null) ? dateFormatter.format(until) : "unavailable");
                    availableDateLabel.setText(dateAvailabilityText.toString());
                    fromDateField.setText(dateFormatter.format(from));
                    untilDateField.setText(dateFormatter.format(until));
                    fromDateField.setEnabled( true );
                    untilDateField.setEnabled( true );
                    updateGraphFromDatesButton.setEnabled(true );
                    oilFlowButton.setEnabled( true );
                    gasFlowButton.setEnabled( true );
                    condensateFlowButton.setEnabled( true );
                    waterFlowButton.setEnabled( true );
                    loadFileButton.setEnabled( true );
                    loadOverlayFileButton.setEnabled( true );
                    generateOverlayButton.setEnabled( true );

                }
                break;
            }

            if( button.getName().equals( "openOverlayFileButton" ) )
            {
                GasWellDataSet newData = loadDataFromFileChooser( loadOverlayFileBox );
                if ( newData != null )
                {
                    editOverlayButton.setEnabled( true );
                    saveOverlayFileButton.setEnabled( true );
                    grapher.loadOverlayData( newData );
                    ((JComponent)grapher).repaint();
                }
                break;
            }
            
            if ( button.getName().equals( "editOverlayData" ) )
            {
                if ( grapher.getOverlayData() != null )
                {
                    generateOverlayButton.setEnabled( false );
                    if ( intervalEditorDialog == null )
                    {
                        Frame frame = JOptionPane.getFrameForComponent( this );
                        intervalEditorDialog = new JDialog( frame, appConfig.getIntervalEditorTitle(), Dialog.ModalityType.MODELESS );
                        intervalEditorPane = new IntervalEditorPane( grapher.getOverlayData().copy(), grapher.getData() );
                        intervalEditorDialog.setContentPane( intervalEditorPane );
                        intervalEditorPane.setVisible( true );
                        intervalEditorDialog.pack();
                        intervalEditorDialog.setVisible( true );
                        intervalEditorPane.addEditorListener( this );
                    }
                } else {
                    // todo : show error message explaining that we can't edit reduced/overlay data
                    // until it's been generated!!
                }
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
                            try {
                                FileWriter writer = new FileWriter( file );
                                if ( file.getName().toLowerCase().endsWith( ".csv" ) )
                                {
                                    grapher.getOverlayData().outputCSV( new PrintWriter( writer )  );
                                } else {
                                    grapher.getOverlayData().outputAmitsFormat(new PrintWriter(writer));
                                }
                                writer.close();
                            } catch (IOException e) {
                                JOptionPane.showMessageDialog( this, "Failed to saveIntervalEditor overlay data to \"" + file.getAbsolutePath() + "\"" );
                                logger.error( "Failed to saveIntervalEditor overlay data to file \"" + file.getAbsolutePath() + "\"" );
                                logger.error( e.getClass().getName() + " - " + e.getMessage() );
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog( this, "No overlay data to saveIntervalEditor", "Cannot saveIntervalEditor overlay", JOptionPane.WARNING_MESSAGE );
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
                Frame frame = JOptionPane.getFrameForComponent(this);
                reductionDialog = new JDialog( frame, "Flow Rate Reduction Parameters", Dialog.ModalityType.MODELESS );
                reducerControlPanel = new DiscontinuityReducerControlPanel();
                reducerControlPanel.setListener( this );
                reducerControlPanel.setData( grapher.getData() );
                reductionDialog.setContentPane(reducerControlPanel);
                reducerControlPanel.setVisible(true);
                reductionDialog.pack();
                reductionDialog.setVisible( true );
            }

        } while( false );
    }
}

/**
 * Loads a gas well data set from an external file using a file chooser. handles both .obj and .csv files.
 * todo: add LAS suport.
 * 
 * @param chooser the file chooser to be used.
 * @return gas well data set, or null if an error happened or the user chickened out.
 */
protected GasWellDataSet loadDataFromFileChooser( JFileChooser chooser )
{
    GasWellDataSet result = null;
    int retval = chooser.showOpenDialog( this );
    if ( retval == JFileChooser.APPROVE_OPTION )
    {
        File file = chooser.getSelectedFile();
        if ( file != null )
        {
            // is it a csv or a .obj file??
            // -----------------------------
            GasWellDataExtractor extractor = null;
            GasWellDataExtractorFactory factory = GasWellDataExtractorFactory.getInstance();
            if ( file.getName().toLowerCase().endsWith( ".csv" ) )
            {
                logger.info( "About to attempt to extract data from file \"" + file.getAbsolutePath() + "\"" );
                try
                {
                    FileReader fr = new FileReader( file );
                    extractor = factory.getCSVGasWellDataExtractor( fr );
                } catch ( FileNotFoundException e ) {
                    // almost impossible!!! we've just chosen the file!!
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

            if ( file.getName().toLowerCase().endsWith( ".obj") )
            {
                try
                {
                    FileInputStream fis = new FileInputStream( file );
                    ObjectInputStream ois = new ObjectInputStream( fis );
                    extractor = factory.getJavaSerializedObjectExtractor( ois );
                } catch ( Exception e ) {
                    JOptionPane.showMessageDialog( this, "Failed to load gas well data from \"" + file.getAbsolutePath() + "\"", "Error loading data", JOptionPane.ERROR_MESSAGE );
                }
            }

            GasWellDataSet newData = null;

            if ( extractor != null )
            {
                logger.debug( "extractor is of class \""+ extractor.getClass().getName() + "\"" );
                MultipleWellDataMap mwdm = extractor.extract();
                if ( mwdm.getDataMap().keySet().size() > 1 )
                {
                    // need to get the operator to select a single well from the multiple
                    // wells which are available.
                    // -------------------------------------------------------------------
                    GasWell[] wells = new GasWell[ mwdm.getDataMap().keySet().size() ];
                    mwdm.getDataMap().keySet().toArray( wells );
                    String[] wellNames = new String[ wells.length ];
                    for( int i = 0; i < wells.length; i++ )
                    {
                        wellNames[ i ] = wells[ i ].getName();
                    }

                    String s = (String)JOptionPane.showInputDialog(
                            this,
                            "Select a well to plot",
                            "Well Selection",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            wellNames,
                            wellNames[ 0 ]);

                    if ( (s != null) && (s.length() > 0) )
                    {
                        GasWell well = new GasWell( s );
                        logger.debug( "About to obtain data for well \"" + well.getName() + "\"" );
                        result = mwdm.getDataMap().get( well );
                    } else {
                        result = null;
                    }

                } else {
                    GasWell well = (GasWell)mwdm.getDataMap().keySet().toArray()[ 0 ];
                    result = mwdm.getDataMap().get( well );
                }
            }
        }  else {
            logger.debug( "NO file selected from open dialog" );
        }
    }
    
    return result;
}

@Override
public void cancelReduction()
{
    reducerControlPanel.setVisible( false );
    reducerControlPanel.removeAll();

    reductionDialog.setVisible( false );
    reductionDialog.removeAll();
    reductionDialog.dispose();
    reductionDialog = null;
    reducerControlPanel = null;
}

@Override
public void reduce( ReductionParameters reductionParameters )
{
    SimpleDiscontinuityDataRateReducerV2 reducer = new SimpleDiscontinuityDataRateReducerV2( reductionParameters );

    logger.debug( "Just about to reduce" );

    grapher.reduce(reducer);

    logger.debug( "Just finished reduction" );

    saveOverlayFileButton.setEnabled( true );
    editOverlayButton.setEnabled( true );

    reducerControlPanel.setVisible( false );
    reducerControlPanel.removeAll();

    reductionDialog.setVisible( false );
    reductionDialog.removeAll();
    reductionDialog.dispose();
    reductionDialog = null;
    reducerControlPanel = null;

}


@Override
public void cancelIntervalEditor()
{
    intervalEditorPane.setVisible( false );
    intervalEditorDialog.dispose();
    intervalEditorDialog = null;
    intervalEditorPane = null;
}

@Override
public void saveIntervalEditor(GasWellDataSet data)
{
    logger.debug( "Called by interval editor to replace overlay/reduced data." );
    
    // update our graph's reduced data with the specified data set!!
    // -------------------------------------------------------------
    grapher.loadOverlayData( data );
    
    intervalEditorPane.setVisible( false );
    intervalEditorDialog.dispose();
    intervalEditorDialog = null;
    intervalEditorPane = null;

    ((JComponent)grapher).repaint();
}

/* ImageFilter.java is used by FileChooserDemo2.java. */
public class DataSourceFileFilter extends FileFilter
{
    //Accept all directories and all gif, jpg, tiff, or png files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        return f.getName().endsWith( ".obj" ) || f.getName().toLowerCase().endsWith( ".csv" );
    }

    //The description of this filter
    public String getDescription() {
        return "Just Images";
    }
}
}
