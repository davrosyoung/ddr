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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Program to help us run the allocation sheet harness.
 */
public class Extractor extends JPanel implements ActionListener
{
private final static Logger logger = Logger.getLogger( Extractor.class );
private final static String CHOOSE_FILE = "chooseFilePanel";
private final static String LOADING_FILE = "loadingFilePanel";
private final static String SELECT_WORKSHEET = "worksheetSelectorPanel";
private final static String SELECT_WELLS = "wellSelectorPanel";
private final static NumberFormat percentageFormatter = NumberFormat.getPercentInstance();

JPanel chooseFilePanel;
JPanel loadingFilePanel;
JPanel worksheetSelectorPanel;
JPanel wellSelectorPanel;
JPanel overlordPanel;

CardLayout cl;

Date fileLoaded = null;
boolean fileLoadAborted = false;
Object fileLoadedLock = new Date();
Workbook book;
String[] wellNames;

// to do with extracting data from the spreadsheet worksheet.
Sheet sheet = null;
List<GasWellDataLocator> locations = null;
AllocationSheetExplorer explorer = null;



// Components for spreadsheetChooser
JFileChooser spreadsheetFileChooser;

// Components for loading file
JLabel memoryUsedLabel;
JTextField memoryUsedField;

// Components for selecting worksheet
//------------------------------------
ButtonGroup worksheetButtonGroup;
Map<String,JRadioButton> worksheetButtons = new HashMap<String,JRadioButton>();
JLabel headingLabel;
JButton extractButton;
JButton worksheetSelectionCancelButton;

// Components for selecting wells
// -------------------------------
JLabel wellSelectionHeadingLabel;
JButton extractWellDataButton;
JButton cancelWellExtractionButton;
JButton displayWellDataButton;
JFileChooser directoryChooser;
Map<String,JCheckBox> wellCheckboxMap;


public Extractor()
{
    cl = new CardLayout();
    setLayout( cl );

    // for use in anonymous classes..
    overlordPanel = this;

    chooseFilePanel = new JPanel();
    loadingFilePanel = new JPanel();
    worksheetSelectorPanel = new JPanel();
    wellSelectorPanel = new JPanel();

    populateChooseFilePanel( chooseFilePanel );
    populateLoadingFilePanel( loadingFilePanel );
    populateWorksheetSelectorPanel(worksheetSelectorPanel);
    populateWellSelectorPanel(wellSelectorPanel);
    add(chooseFilePanel, CHOOSE_FILE);
    add(loadingFilePanel, LOADING_FILE);
    add(worksheetSelectorPanel, SELECT_WORKSHEET);
    add(wellSelectorPanel, SELECT_WELLS);


    cl.show( this, CHOOSE_FILE );
}

protected void populateChooseFilePanel( JPanel panel )
{
    panel.setLayout( new GridBagLayout() );
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.gridheight = 1;
    gbc.weightx = 0.5;
    gbc.weighty = 0.9;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.PAGE_START;

    spreadsheetFileChooser = new JFileChooser();
    spreadsheetFileChooser.setFileFilter( new ExcelFileFilter() );
    spreadsheetFileChooser.addActionListener( this );
    spreadsheetFileChooser.setName( "spreadsheetFileChooser" );

    panel.add( spreadsheetFileChooser, gbc );
}

protected void populateLoadingFilePanel( JPanel panel )
{
    panel.setLayout( new GridBagLayout() );
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.weightx = 0.5;
    gbc.weighty = 0.9;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.PAGE_START;

    memoryUsedLabel = new JLabel();
    memoryUsedLabel.setText( "Memory used" );
    memoryUsedField = new JTextField();
    memoryUsedField.setEditable(false);
    memoryUsedField.setText(percentageFormatter.format( getPercentageMemoryUsed()) );

    panel.add( memoryUsedLabel, gbc );

    gbc.gridx++;

    panel.add( memoryUsedField, gbc );

}

protected void populateWorksheetSelectorPanel( JPanel panel )
{
    headingLabel = new JLabel( "Please choose a worksheet to extract from" );
    extractButton = new JButton();
    extractButton.addActionListener( this );
    extractButton.setName( "extract" );
    extractButton.setText( "Extract" );

    worksheetSelectionCancelButton = new JButton();
    worksheetSelectionCancelButton.addActionListener( this );
    worksheetSelectionCancelButton.setName( "worksheetSelectionCancelButton" );
    worksheetSelectionCancelButton.setText( "Cancel" );
}

protected void populateWellSelectorPanel( JPanel panel )
{
    panel.setLayout( new GridBagLayout() );
    GridBagConstraints gbc = new GridBagConstraints();

    wellSelectionHeadingLabel = new JLabel( "Select one or more wells..." );

    extractWellDataButton = new JButton( "save data" );
    extractWellDataButton.setName( "extractWellData" );
    extractWellDataButton.setText("save");
    extractWellDataButton.addActionListener( this );

    displayWellDataButton = new JButton( "display data" );
    displayWellDataButton.setName( "displayWellData" );
    displayWellDataButton.setText( "display data" );
    displayWellDataButton.addActionListener( this );

    cancelWellExtractionButton = new JButton();
    cancelWellExtractionButton.setName( "cancelWellExtraction" );
    cancelWellExtractionButton.setText( "cancel" );
    cancelWellExtractionButton.addActionListener( this );

}

public static void createAndShowGUI()
{
    JFrame f = new JFrame( "Data Extractor" );
    f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    f.setSize( 1400, 800 );
    f.setContentPane( new Extractor() );
    f.pack();
    f.setVisible( true );
}

@Override
public void actionPerformed(ActionEvent e)
{
    logger.debug( e );
    JComponent source = (JComponent)e.getSource();
    File file = null;
    FileInputStream fis = null;
    long then;
    long now;

    do {

        logger.debug( "source.name=\"" + source.getName() + "\"...." );

        if ( source.getName().equals( "spreadsheetFileChooser" ) )
        {
            processSpreadsheetChooser( e );


        } // end-IF ( spreadsheet file chooser )

        if( source.getName().equals( "extract" ) )
        {
            handleExtractSpreadsheetButton( e );
        }

        if ( source.getName().startsWith("extractWellData") )
        {
            processWellExtraction( e );
        }

        if ( source.getName().startsWith( "cancelWellExtraction" ) )
        {
            cl.show( this, SELECT_WORKSHEET );
        }

        if ( source.getName().equals( "worksheetSelectionCancelButton" ) )
        {
            book = null;
            sheet = null;
            cl.show( this, CHOOSE_FILE );
        }

    } while( false );
}

/**
 * invoked to handle events triggered by the spreadsheet open file dialog.
 *
 * @param evt
 */
protected void processSpreadsheetChooser( ActionEvent evt )
{
    File file;

    logger.debug( "Event from the spreadsheet filechooser!! e=" + evt );

    if ( evt.getActionCommand().equals( JFileChooser.APPROVE_SELECTION ) )
    {
        if ( ( file = spreadsheetFileChooser.getSelectedFile() ) != null )
        {
            logger.debug( "We've been requested to open file \"" + file.getAbsolutePath() + "\"" );
            cl.show( this, LOADING_FILE );
            MemoryUsedUpdater updater = new MemoryUsedUpdater( memoryUsedField );
            Thread runner = new Thread( updater );
            runner.start();
            final File[] fileArray = new File[] { file };

            Runnable task = new Runnable()
            {
                public void run() {
                    FileInputStream fis;
                    long then;
                    long now;

                    try
                    {
                        fis = new FileInputStream( fileArray[ 0 ] ); // workaround to get to non-final
                                                                    // 'file' variable!!
                        then = System.currentTimeMillis();
                        book = WorkbookFactory.create( fis );
                        now = System.currentTimeMillis();
                        fis.close();
                        logger.debug( "It took " + ( now - then ) + "ms to load spreadsheet from file " + fileArray[ 0 ].getAbsolutePath() + "." );
                        synchronized( fileLoadedLock )
                        {
                            fileLoaded = new Date();
                        }


                        SwingUtilities.invokeAndWait( new Runnable() {
                            public void run()
                            {
                                showAvailableSheets( book );
                            }
                        });


                    } catch ( Throwable error ) {

                        logger.error( "Runtime exception trying to load spreadsheet \"" + fileArray[ 0 ].getAbsolutePath() + "\"" );
                        logger.error( error.getClass().getName() + " - " + error.getMessage() );
                        final Throwable[] errArray = new Throwable[] { error };

                        try
                        {
                            SwingUtilities.invokeAndWait( new Runnable() {
                                public void run()
                                {
                                    String msg =  "Unable to load excel file \"" + fileArray[ 0 ].getAbsolutePath() + "\" - " + errArray[ 0 ].getClass().getName() + " - " + errArray[ 0 ].getMessage();
                                    JOptionPane.showMessageDialog( overlordPanel, msg, "Error opening spreadsheet", JOptionPane.ERROR_MESSAGE );
                                    cl.show( overlordPanel, CHOOSE_FILE );
                                }

                            });
                        } catch (Exception ex0 )
                        {
                            logger.error( "Failed to display error dialogue about failing to load spreadsheet from file \"" + fileArray[ 0 ].getAbsolutePath() + "\" ");
                            logger.error( error.getClass().getName() + " - " + error.getMessage() );
                        } finally {
                            synchronized( fileLoadedLock )
                            {
                                fileLoadAborted = true;
                            }
                        }

                    }
                }
            }; // end-runnable(definition)



            // start reading the excel spreadsheet in a separate thread!!
            Thread anotherRunner = new Thread( task );
            anotherRunner.start();


        }
    }

    if ( evt.getActionCommand().equals( JFileChooser.CANCEL_SELECTION ) )
    {
        logger.info( "User requested CANCEL ... exiting application" );
        System.exit( 0 );
    }
}

protected void handleExtractSpreadsheetButton( ActionEvent e )
{
    String selectedSheetName = null;

    // determine which radio button has been selected...
    // --------------------------------------------------
    for( String label : worksheetButtons.keySet() )
    {
        JRadioButton candidate = worksheetButtons.get( label );
        logger.debug( "Examining button for sheet \"" + label + "\". selected=" + candidate.isSelected() );

        if ( candidate.isSelected() )
        {
            logger.debug( "Worksheet \"" + label + "\" has been selected!!" );
            selectedSheetName = label;
            break;
        }
    }

    if ( selectedSheetName != null )
    {
        sheet = book.getSheet( selectedSheetName );
        explorer = new AllocationSheetExplorer( sheet );
        explorer.process();

        locations = explorer.getLocations();
        if ( ( locations != null ) && ( locations.size() > 0 ) )
        {
            logger.info( "Found " + locations.size() + " data sets for gas/oil wells.");
            Iterator<GasWellDataLocator> iterator = locations.iterator();
            while( iterator.hasNext() )
            {
                GasWellDataLocator locator = iterator.next();
                logger.info( locator );
            }
        }
        this.wellNames = new String[ locations.size()  ];
        for( int i = 0; i < locations.size(); i++ )
        {
            this.wellNames[ i ] = locations.get( i ).getWellName();
        }
        showAvailableWells( this.wellNames );
    }
}

protected void processWellExtraction( ActionEvent evt )
{
    StringBuilder msg = new StringBuilder( "Wrote out data files " );
    // let's extract the data sets one by one ....
    // --------------------------------------------
    for( GasWellDataLocator location : locations )
    {
        GasWell well = new GasWell( location.getWellName() );

        // is this gas well selected??
        // ---------------------------
        if ( ( wellCheckboxMap.containsKey( well.getName() ) ) && wellCheckboxMap.get( well.getName() ).isSelected() )
        {
            GasWellDataSet data = explorer.obtainDataSet(well, location);
            String outputFilename = "data" + File.separator + location.getWellName().toLowerCase().replaceAll( "\\s+\\-_\\(\\)", "" ) + ".txt";
            try
            {
                logger.debug( "About to open file '" + outputFilename + "' for writing data for well \"" + location.getWellName() );
                FileOutputStream fos = new FileOutputStream( outputFilename );
                PrintWriter writer = new PrintWriter( fos );
                writer.print( data );
                writer.close();
                fos.close();

                outputFilename = "data" + File.separator + location.getWellName().toLowerCase() .replaceAll( "\\s+\\-_\\(\\)", "" ) + ".obj";
                msg.append( "\n" + outputFilename );
                fos = new FileOutputStream( outputFilename );
                ObjectOutputStream oos = new ObjectOutputStream( fos );
                oos.writeObject( data );
                oos.close();
            } catch (IOException e)
            {
                logger.error( "Failed to write out data file " + outputFilename );
                logger.error(e);
            }
        } else {
            logger.debug( "Well \"" + well.getName() + "\" isn't selected." );
        }
    }

    JOptionPane.showMessageDialog( this, msg.toString(), "Wrote out data files", JOptionPane.INFORMATION_MESSAGE );
}

protected void showAvailableSheets( Workbook book )
{
    JRadioButton button = null;
    int numberSheets;
    int i;
    worksheetSelectorPanel.removeAll();
    worksheetSelectorPanel.setLayout( new GridBagLayout() );
    worksheetButtonGroup = new ButtonGroup();
    Sheet sheet;
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.weightx = 0.5;
    gbc.weighty = 0.9;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.PAGE_START;

    worksheetSelectorPanel.add(headingLabel, gbc);
    gbc.gridy++;

    numberSheets = book.getNumberOfSheets();
    for( i = 0; i < numberSheets; i++ )
    {
        gbc.gridx = i % 2;
        sheet = book.getSheetAt( i );
        button = new JRadioButton( sheet.getSheetName() );
        button.setName( sheet.getSheetName() );
        button.addActionListener( this );
        worksheetButtons.put( sheet.getSheetName(), button );
        worksheetSelectorPanel.add( button, gbc );
        if ( ( i% 2 ) == 1 )
        {
            gbc.gridy++;
        }
    }

    gbc.gridx = 1;
    gbc.gridwidth = 1;
    worksheetSelectorPanel.add( extractButton, gbc );

    gbc.gridx = 2;
    worksheetSelectorPanel.add( worksheetSelectionCancelButton, gbc );

    cl.show( this, SELECT_WORKSHEET );
}

protected void showAvailableWells( String[] wellNames )
{
    JCheckBox button = null;
    int numberSheets;
    int i;
    wellSelectorPanel.removeAll();
    wellSelectorPanel.setLayout( new GridBagLayout() );
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.gridheight = 1;
    gbc.weightx = 0.5;
    gbc.weighty = 0.9;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.PAGE_START;


    wellSelectorPanel.add( wellSelectionHeadingLabel, gbc);
    gbc.gridy++;

    wellCheckboxMap = new HashMap<String,JCheckBox>();

    for( i = 0; i < wellNames.length; i++ )
    {
        gbc.gridwidth = 1;
        gbc.gridx = 0;

        button = new JCheckBox();
        button.setName( "well=" + wellNames[ i ] );
        button.setText(wellNames[ i ]);
        button.addActionListener(this);
        wellSelectorPanel.add(button, gbc);
        wellCheckboxMap.put(wellNames[i], button);

        gbc.gridx = 1;
        JButton displayButton = new JButton();
        displayButton.setName( "display(well=" + i + ")" );
        displayButton.setText("view data");
        displayButton.addActionListener( this );
        wellSelectorPanel.add( displayButton, gbc );
        gbc.gridy++;
    }

    gbc.gridx = 0;
    gbc.gridwidth = 1;

    wellSelectorPanel.add( extractWellDataButton, gbc );

    gbc.gridx = 1;
    wellSelectorPanel.add( cancelWellExtractionButton, gbc );

    cl.show( this, SELECT_WELLS );
}

public static void main( String... args )
{

    Runnable doCreateAndShowGUI = new Runnable() {
        public void run() {
            createAndShowGUI();
        }
    };

    SwingUtilities.invokeLater( doCreateAndShowGUI );
}

public static double getPercentageMemoryUsed()
{
    Runtime rt = Runtime.getRuntime();
    long totalMemory = rt.totalMemory();
    long freeMemory = rt.freeMemory();
    long usedMemory = totalMemory - freeMemory;
    return (double)usedMemory / (double)totalMemory;
}

protected static class ExcelFileFilter extends FileFilter
{
    @Override
    public boolean accept(File f)
    {
        boolean result = false;
        result = f.isDirectory() || f.getName().toLowerCase().endsWith( ".xls" ) || f.getName().toLowerCase().endsWith( ".xlsx" );
        return result;
    }

    @Override
    public String getDescription()
    {
        return "Used to dispay excel files for selection by the DDR extractor";
    }
}


public class MemoryUsedUpdater implements Runnable
{
    JTextField field;

    MemoryUsedUpdater( JTextField field )
    {
        this.field = field;
    }

    @Override
    public void run()
    {
        boolean isFileLoaded = false;
        boolean isFileAborted = false;

        // update how much memory we've used four times a second....
        // ----------------------------------------------------------
        do {
            try
            {
                Thread.currentThread().sleep( 250 );
            } catch (InterruptedException e)
            {
                logger.error( "Caught " + e.getClass().getName() + " - " + e.getMessage() );
            }

            synchronized( fileLoadedLock )
            {
                isFileLoaded = ( fileLoaded != null );
                isFileAborted = fileLoadAborted;
            }

            SwingUtilities.invokeLater( new Runnable() {
                @Override
                public void run()
                {
                    field.setText( percentageFormatter.format( getPercentageMemoryUsed()));
                    logger.debug( "Just updated memory used percentage to " + getPercentageMemoryUsed() );
                }
            }
            );
        } while( ! isFileLoaded  && !isFileAborted );
        //To change body of implemented methods use File | Settings | File Templates.

        // thread exits.
        logger.debug( "Memory usage updater thread exits...." );
    }
}


}
