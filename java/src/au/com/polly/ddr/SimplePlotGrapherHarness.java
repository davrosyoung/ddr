package au.com.polly.ddr;

import javax.swing.*;
import java.awt.*;

/**
 * Simple yoke harness to enable us to display a plot of graph data with
 * some controls...
 */
public class SimplePlotGrapherHarness extends JPanel
{
    static DataVsTimeSource dataSource = new DummyGasWellDataSet();
    SimplePlotGrapher grapher = null;
    GraphControlPanel controlPanel = null;


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
    grapher = new SimplePlotGrapher( dataSource );
    controlPanel = new GraphControlPanel();
    add( grapher, gbc );

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

    Runnable doCreateAndShowGUI = new Runnable() {
        public void run() {
            createAndShowGUI();
        }
    };

    SwingUtilities.invokeLater( doCreateAndShowGUI );
}

}
