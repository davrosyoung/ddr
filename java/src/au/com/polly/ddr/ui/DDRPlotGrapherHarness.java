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

import au.com.polly.ddr.GasWellDataExtractor;
import au.com.polly.ddr.GasWellDataExtractorFactory;
import au.com.polly.ddr.GasWellDataSet;
import au.com.polly.ddr.MultipleWellDataMap;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.util.Date;

/**
 * Yoke harness to use the csv graphing/plotting tools with our control panel and
 * interval editor.
 */
public class DDRPlotGrapherHarness extends JPanel
{
private static Logger logger = Logger.getLogger( DDRPlotGrapherHarness.class );
private DDRPlotGrapher overlayGrapher = null;
private GraphControlPanel controlPanel = null;
private GasWellDataSet dataSet = null;
private GasWellDataSet overlayDataSet = null;
private static DDRPlotGrapherHarness harness;
private JFrame frame;


public DDRPlotGrapherHarness( GasWellDataSet dataSet, GasWellDataSet overlayDataSet )
{
    super( new GridBagLayout() );
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 19;
    gbc.weightx = 0.5;
    gbc.weighty = 0.9;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.PAGE_START;
    Date from = null;
    Date until = null;

    this.dataSet = dataSet;
    this.overlayDataSet = overlayDataSet;

    if ( dataSet != null )
    {
        from = dataSet.from();
        until = dataSet.until();
    }

    if ( overlayDataSet != null )
    {
        if ( overlayDataSet.from().before( from ) )
        {
            from = overlayDataSet.from();
        }
        if ( overlayDataSet.until().after( until ) )
        {
            until = overlayDataSet.until();
        }
    }
    overlayGrapher = new DDRPlotGrapher( dataSet, overlayDataSet, 800, 800 );
    controlPanel = new GraphControlPanel( from, until );

    controlPanel.setPlotGrapher( overlayGrapher );
    add( overlayGrapher, gbc );

    gbc.gridx = 0;
    gbc.gridy = 19;
    gbc.weighty = 0.1;
    gbc.gridheight = 1;
    gbc.gridwidth = 1;
    gbc.anchor = GridBagConstraints.PAGE_END;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    add( controlPanel, gbc );
    setVisible( true );
}


public void createAndShowGUI()
{
    frame = new JFrame( "Data Plot" );
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1400, 800);
    frame.setContentPane( this );
    frame.pack();
    frame.setVisible( true );
}

public static void main( String... args )
{
    GasWellDataSet dataSet = null;

    if ( ( args != null ) && ( args.length > 0 ) )
    {
        String filename = args[ 0 ];
        File file = new File( filename );
        FileReader reader = null;
        GasWellDataExtractorFactory factory = GasWellDataExtractorFactory.getInstance();
        GasWellDataExtractor extractor;

        if ( ( ! file.exists() ) || ( ! file.isFile() ) || ( ! file.canRead() ) )
        {
            System.err.println( "Unable to open \"" + filename + "\" for reading!!" );
            System.exit( 1 );
        }

        try
        {
            reader = new FileReader( file );
            extractor = factory.getCSVGasWellDataExtractor( reader );
            MultipleWellDataMap mwdm = extractor.extract();
            dataSet = mwdm.getDataSetList().get( 0 );
            logger.info( "Extracted data set for well \"" + dataSet.getWellName() + "\", contains " + dataSet.getData().size() + " entries." );
        } catch( Exception e ) {
            logger.error( "Failed to extract gas well data set from \"" + filename + "\"" );
            e.printStackTrace( System.err );
            System.exit( 1 );
        }
    }
    
    harness = new DDRPlotGrapherHarness( dataSet, null );

    Runnable doCreateAndShowGUI = new Runnable() {
        public void run() {
            harness.createAndShowGUI();
        }
    };

    SwingUtilities.invokeLater( doCreateAndShowGUI );
}

}
