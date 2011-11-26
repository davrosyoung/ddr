package au.com.polly.ddr;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Date;

/**
 * Simple yoke harness to enable us to display a plot of graph data with
 * some controls...
 */
public class SimplePlotGrapherHarness extends JPanel
{
    private static Logger logger = Logger.getLogger( SimplePlotGrapherHarness.class );
    static DataVsTimeSource dataSource = new DummyGasWellDataSet();
    SimplePlotGrapher grapher = null;
    SimpleOverlayPlotGrapher overlayGrapher = null;
    GraphControlPanel controlPanel = null;
    static GasWellDataSet dataSet = null;
    static GasWellDataSet overlayDataSet = null;


public SimplePlotGrapherHarness()
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
        overlayGrapher = new SimpleOverlayPlotGrapher( dataSet, overlayDataSet );
    } else {
        grapher = new SimplePlotGrapher( dataSet );
    }
    controlPanel = new GraphControlPanel( from, until );
    if ( overlayDataSet != null )
    {
        controlPanel.setPlotGrapher( overlayGrapher );
        add( overlayGrapher, gbc );

    } else {
        controlPanel.setPlotGrapher( grapher );
        add( grapher, gbc );

    }

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


public static void createAndShowGUI()
{
    JFrame f = new JFrame( "Data Plot" );
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setSize(1400, 800);
    f.setContentPane(new SimplePlotGrapherHarness());
    f.pack();
    f.setVisible( true );
}

public static void main( String... args )
{
    if ( ( args != null ) && ( args.length > 0 ) )
    {
        String filename = args[ 0 ];
        File file = new File( filename );
        FileInputStream fis;
        ObjectInputStream ois;

        if ( ( ! file.exists() ) || ( ! file.isFile() ) || ( ! file.canRead() ) )
        {
            System.err.println( "Unable to open \"" + filename + "\" for reading!!" );
            System.exit( 1 );
        }

        try
        {
            fis = new FileInputStream( file );
            ois = new ObjectInputStream( fis );
            dataSet = (GasWellDataSet)ois.readObject();
            logger.info( "Extracted data set for well \"" + dataSet.getWellName() + "\", contains " + dataSet.getData().size() + " entries." );
        } catch( Exception e )
        {
            logger.error( "Failed to extract gas well data set from \"" + filename + "\"" );
            e.printStackTrace( System.err );
            System.exit( 1 );
        }

        // was a second file specified??
        // ------------------------------
        if ( args.length > 1 )
        {
            filename = args[ 1 ];
            file = new File( filename );

            if ( ( ! file.exists() ) || ( ! file.isFile() ) || ( ! file.canRead() ) )
            {
                System.err.println( "Unable to open \"" + filename + "\" for reading!!" );
                System.exit( 1 );
            }

            try
            {
                fis = new FileInputStream( file );
                ois = new ObjectInputStream( fis );
                overlayDataSet = (GasWellDataSet)ois.readObject();
                logger.info( "Extracted overlay data set for well \"" + overlayDataSet.getWellName() + "\", contains " + overlayDataSet.getData().size() + " entries." );
            } catch( Exception e )
            {
                logger.error( "Failed to extract gas well data set from \"" + filename + "\"" );
                e.printStackTrace( System.err );
                System.exit( 1 );
            }
        }

    }

    Runnable doCreateAndShowGUI = new Runnable() {
        public void run() {
            createAndShowGUI();
        }
    };

    SwingUtilities.invokeLater( doCreateAndShowGUI );
}

}
