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

package au.com.polly.ddr.ui;

import au.com.polly.ddr.ExcelWorkbookExplorer;
import au.com.polly.ddr.ExcelWorkbookExplorerFactory;
import au.com.polly.ddr.GasWell;
import au.com.polly.ddr.GasWellDataExtractor;
import au.com.polly.ddr.GasWellDataExtractorFactory;
import au.com.polly.ddr.GasWellDataLocator;
import au.com.polly.ddr.GasWellDataSet;
import au.com.polly.ddr.MultipleWellDataMap;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UI to enable gas well data to be extracted from an excel file...
 */
public class Liberator extends JPanel implements ActionListener
{
private final static Logger logger = Logger.getLogger( Liberator.class );
private final static String CHOOSE_FILE = "chooseFilePanel";
private final static String LOADING_FILE = "loadingFilePanel";
//private final static String SELECT_WORKSHEET = "worksheetSelectorPanel";
private final static String SELECT_WELLS = "wellSelectorPanel";
private final static NumberFormat percentageFormatter = NumberFormat.getPercentInstance();
private static File dataDirectory;
private static File sourceDirectory;

static {
    String text = null;
    if ( ( text = System.getProperty( "ddr.data.directory" ) ) != null )
    {
        dataDirectory = new File( text );
        if ( ! dataDirectory.isDirectory() )
        {
            dataDirectory = null;
        } else {
            logger.info( "Just set dataDirectory to [" +dataDirectory.getAbsolutePath() + "]" );
        }
    }

    if ( ( text = System.getProperty( "ddr.source.directory" ) ) != null )
    {
        sourceDirectory = new File( text );
        if ( ! sourceDirectory.isDirectory() )
        {
            sourceDirectory = ( dataDirectory != null ) ? dataDirectory : null;
        } else {
            logger.info( "Just set sourceDirectory to [" + sourceDirectory.getAbsolutePath() + "]" );
        }
    }
}

JPanel chooseFilePanel;
JPanel loadingFilePanel;
JPanel wellSelectorPanel;
JPanel overlordPanel;

CardLayout cl;

// since the file loading happens in a separate thread, the UI thread needs to know
// when the file has actually been loaded...
// ---------------------------------------------------------------------------------
Date fileLoaded = null;
boolean fileLoadAborted = false;
Object fileLoadedLock = new Date();


Workbook book;
String[] wellNames;

// to do with extracting data from the spreadsheet worksheet.
Sheet sheet = null;
List<GasWellDataLocator> locations = null;
ExcelWorkbookExplorerFactory explorerFactory = ExcelWorkbookExplorerFactory.getInstance();
ExcelWorkbookExplorer explorer = null;

GasWellDataExtractorFactory extractorFactory = GasWellDataExtractorFactory.getInstance();
GasWellDataExtractor extractor = null;


// Components for spreadsheetChooser
JFileChooser spreadsheetFileChooser;

// Components for loading file
JLabel memoryUsedLabel;
JTextField memoryUsedField;

// Components for selecting wells
//------------------------------------
ButtonGroup wellButtonGroup;
//Map<String,JRadioButton> wellButtons = new HashMap<String,JRadioButton>();
JLabel headingLabel;
JButton extractButton;
JButton wellSelectionCancelButton;

// Components for selecting wells
// -------------------------------
JLabel wellSelectionHeadingLabel;
JButton extractWellDataButton;
JButton cancelWellExtractionButton;
JButton displayWellDataButton;
JFileChooser directoryChooser;
Map<String,JCheckBox> wellCheckboxMap;


public Liberator()
{
    cl = new CardLayout();
    setLayout( cl );

    // for use in anonymous classes..
    overlordPanel = this;


    chooseFilePanel = createChooseFilePanel();
    loadingFilePanel = createLoadingFilePanel();
    wellSelectorPanel = createWellSelectorPanel();
    add( chooseFilePanel, CHOOSE_FILE );
    add( loadingFilePanel, LOADING_FILE );
    add( wellSelectorPanel, SELECT_WELLS );


    cl.show( this, CHOOSE_FILE );
}

protected JPanel createChooseFilePanel()
{
    JPanel result = new JPanel();
    result.setLayout( new GridBagLayout() );
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
    if ( sourceDirectory != null )
    {
        spreadsheetFileChooser.setCurrentDirectory( sourceDirectory );
    }

    result.add( spreadsheetFileChooser, gbc );
    
    return result;
}

protected JPanel createLoadingFilePanel()
{
    JPanel result = new JPanel();
    result.setLayout(new GridBagLayout());
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

    result.add(memoryUsedLabel, gbc);

    gbc.gridx++;

    result.add(memoryUsedField, gbc);

    return result;
}

protected JPanel createWellSelectorPanel()
{
    JPanel result = new JPanel();
    headingLabel = new JLabel( "Please choose wells to extract from" );
    extractButton = new JButton();
    extractButton.addActionListener( this );
    extractButton.setName( "extract" );
    extractButton.setText( "Extract" );

    wellSelectionCancelButton = new JButton();
    wellSelectionCancelButton.addActionListener( this );
    wellSelectionCancelButton.setName("wellSelectionCancelButton");
    wellSelectionCancelButton.setText("Cancel");
    
    return result;
}

public static void createAndShowGUI()
{
    JFrame f = new JFrame( "Data Extractor" );
    f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    f.setSize( 1400, 800 );
    f.setContentPane( new Liberator() );
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
            handleSpreadsheetChooser( e );
        } // end-IF ( spreadsheet file chooser )

        if( source.getName().equals( "extract" ) )
        {
            handleExtractButton(e);
        }

        if ( source.getName().startsWith( "wellSelectionCancelButton" ) )
        {
            cl.show( this, CHOOSE_FILE );
        }

    } while( false );
}

/**
 * invoked to handle events triggered by the spreadsheet open file dialog.
 *
 * @param evt
 */
protected void handleSpreadsheetChooser( ActionEvent evt )
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
                public void run()
                {
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
                                if ( ( locations = showAvailableWells( book ) ) != null )
                                {
                                    cl.show( overlordPanel, SELECT_WELLS );
                                } else {
                                    JOptionPane.showMessageDialog( overlordPanel, "No gas well data available", "No data available", JOptionPane.ERROR_MESSAGE );
                                    cl.show( overlordPanel, CHOOSE_FILE );
                                }
                            }
                        });


                    } catch ( Throwable error ) {
                        
                        logger.error( "Runtime exception trying to load spreadsheet \"" + fileArray[ 0 ].getAbsolutePath() + "\"" );
                        logger.error( error.getClass().getName() + " - " + error.getMessage() );
                        error.printStackTrace( System.err);
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


protected void handleExtractButton(ActionEvent evt)
{
    File file = null;

    extractorFactory = GasWellDataExtractorFactory.getInstance();
    extractor = extractorFactory.getExcelStandardizedGasWellDataExtractor( book, locations );

    MultipleWellDataMap mwdm = extractor.extract();
    Map<GasWell,GasWellDataSet> dataMap = mwdm.getDataMap();
    JFileChooser chooser = new JFileChooser();
    if ( dataDirectory != null )
    {
        chooser.setCurrentDirectory( dataDirectory );
    }

    // choose directory to save files in...
    // ------------------------------------
    int returnVal = chooser.showSaveDialog( this );

    if ( returnVal == JFileChooser.APPROVE_OPTION )
    {
        file = chooser.getSelectedFile();
        if ( ! file.getName().toLowerCase().endsWith( ".csv" ) )
        {
            Object[] options = { "Yes I am sure", "Whoops!! No thank you" };
            if ( JOptionPane.showOptionDialog(
                    overlordPanel,
                    "You have chosen to save the data to a non .csv file. This is not recommended. Are you sure you wish to proceed?",
                    "Dubious file suffix",
                    JOptionPane.WARNING_MESSAGE,
                    JOptionPane.OK_CANCEL_OPTION,
                    (Icon)null,
                    options,
                    options[1]
            ) == JOptionPane.CANCEL_OPTION )
            {
                file = null;
            }
        }
        //This is where a real application would open the file.
        logger.debug( "Opening: " + file.getName() + "." );
    } else {
        logger.warn( "Open command cancelled by user." );
    }
    
    if ( file != null )
    {
        MultipleWellDataMap setsToSave = new MultipleWellDataMap();
        
        // copy just the wells which have been selected to be saved.
        // ---------------------------------------------------------
        for( GasWell well : dataMap.keySet() )
        {
            // is this gas well selected??
            // ---------------------------
            if ( ( wellCheckboxMap.containsKey( well.getName() ) ) && wellCheckboxMap.get( well.getName() ).isSelected() )
            {
                setsToSave.addDataSet( mwdm.getDataMap().get( well ) );
            }
        }
        
        try
        {
            long then = System.currentTimeMillis();
            logger.debug( "About to open file '" + file.getAbsolutePath() + "' for saving data" );
            FileOutputStream fos = new FileOutputStream( file );
            PrintWriter writer = new PrintWriter( fos );
            setsToSave.outputCSV( writer );
            writer.close();
            fos.close();
            long now = System.currentTimeMillis();
            logger.info( "It took " + ( now - then ) + "ms to save data to [" + file.getAbsolutePath() + "]" );
            JOptionPane.showMessageDialog( this, "Saved data to file \"" + file.getAbsolutePath() + "\"", "Gas data saved", JOptionPane.INFORMATION_MESSAGE );
        } catch ( IOException e ) {
            logger.error( "Failed to write out data file " + file.getAbsolutePath() );
            logger.error(e);
            String errorMsg = "Failed to save data to file \"" +file.getAbsolutePath() + "\" - " + e.getMessage();
            JOptionPane.showMessageDialog( overlordPanel, errorMsg, "Oh dear!!", JOptionPane.ERROR_MESSAGE );
        }
    }

}

protected List<GasWellDataLocator> showAvailableWells( Workbook book )
{
    JCheckBox checkBox = null;
    List<GasWellDataLocator> result = null;
    int numberWells;
    int i;
    wellSelectorPanel.removeAll();
    wellSelectorPanel.setLayout( new GridBagLayout() );
    wellButtonGroup = new ButtonGroup();
    wellCheckboxMap = new HashMap<String,JCheckBox>();
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

    wellSelectorPanel.add(headingLabel, gbc);
    gbc.gridy++;


    explorer = ExcelWorkbookExplorerFactory.getInstance().createExcelStandardizedWorkbookExplorer( book );
    if ( explorer != null )
    {
        result = explorer.getLocations();
        if ( ( result != null ) && ( result.size() > 0 )  )
        {
            // we can extract the data and proceed to the save file selection page...
            // -----------------------------------------------------------------------
            numberWells = result.size();
            for( i = 0; i < numberWells; i++ )
            {
                gbc.gridx = i % 2;
                GasWellDataLocator locator = result.get( i );
                checkBox = new JCheckBox( locator.getWellName() );
                checkBox.setSelected( true );
                wellCheckboxMap.put( locator.getWellName(), checkBox );
                checkBox.setName( locator.getWellName() );
                checkBox.addActionListener( this );
//                    wellButtons.put(locator.getWellName(), checkBox);
                wellSelectorPanel.add( checkBox, gbc );
                if ( ( i% 2 ) == 1 )
                {
                    gbc.gridy++;
                }
            }


            gbc.gridx = 1;
            gbc.gridwidth = 1;
            wellSelectorPanel.add( extractButton, gbc );

            gbc.gridx = 2;
            wellSelectorPanel.add(wellSelectionCancelButton, gbc );

            cl.show( this, SELECT_WELLS );

        } else {
            // if we didn't find any data, then display an error message and take the
            // user back to the file chooser pane.
            // --------------------------------------------------------------------
            JOptionPane.showMessageDialog( this, "No gas well data found in spreadsheet.", "No data found", JOptionPane.ERROR_MESSAGE );
            cl.show( this, CHOOSE_FILE );
        }
    }

    return result;
}

public static void main( String[] argv )
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

public static long getMemoryUsed()
{
    Runtime rt = Runtime.getRuntime();
    long totalMemory = rt.totalMemory();
    long freeMemory = rt.freeMemory();
    long usedMemory = totalMemory - freeMemory;
    return usedMemory;

}

public static long getMemoryFree()
{
    Runtime rt = Runtime.getRuntime();
    return rt.freeMemory();
}

protected static class ExcelFileFilter extends FileFilter
{
    @Override
    public boolean accept(File f)
    {
        boolean result = false;
        result = f.isDirectory() || f.getName().toLowerCase().endsWith( ".xls" ) || f.getName().toLowerCase().endsWith( ".xlsx" ) || f.getName().toLowerCase().endsWith( ".csv" );
        return result;
    }

    @Override
    public String getDescription()
    {
        return "Excel files";
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
                    logger.debug( "Just updated memory used percentage to " + getPercentageMemoryUsed() + ", memory used=" + getMemoryUsed() + ", memoryFree=" + getMemoryFree() );
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
