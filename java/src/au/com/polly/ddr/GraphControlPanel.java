package au.com.polly.ddr;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controls to allow the operator to change the measurements type
 * being viewed, change the scale and add cutoff points.
 *
 *
 */
public class GraphControlPanel extends JPanel implements ActionListener
{
static private Logger logger = Logger.getLogger( GraphControlPanel.class );

    JButton oilFlowButton;
    JButton gasFlowButton;
    JButton condensateFlowButton;
    JButton waterFlowButton;
    JFileChooser loadFileButton;

public GraphControlPanel()
{
    oilFlowButton = new JButton();
    oilFlowButton.setEnabled( true );
    oilFlowButton.setName( "oilFlowButton" );
    oilFlowButton.setText( "Oil Flow" );
    oilFlowButton.setVisible( true );

    gasFlowButton = new JButton();
    gasFlowButton.setEnabled( true );
    gasFlowButton.setText( "Gas Flow" );
    gasFlowButton.setName( "gasFlowButton" );
    gasFlowButton.setVisible( true );

    waterFlowButton = new JButton();
    waterFlowButton.setName( "waterFlowButton" );
    waterFlowButton.setText( "Water Flow" );
    waterFlowButton.setEnabled( true );
    waterFlowButton.setVisible( true );

    condensateFlowButton = new JButton();
    condensateFlowButton.setName( "condensateFlowButton" );
    condensateFlowButton.setText( "Condensate" );
    condensateFlowButton.setEnabled( true );
    condensateFlowButton.setVisible( true );

    loadFileButton = new JFileChooser();
    loadFileButton.setName( "loadFileButton" );
    loadFileButton.setDialogTitle( "Data File" );

    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.weightx = 0.5;
    gbc.weighty = 0.5;
    add(oilFlowButton, gbc );

    gbc.gridx = 1;
    add(gasFlowButton, gbc);

    gbc.gridx = 2;
    add(condensateFlowButton, gbc);

    gbc.gridx = 3;
    add(waterFlowButton, gbc );

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 2;
    add( loadFileButton, gbc );

    setPreferredSize( new Dimension( 400, 40 ) );
    setVisible( true );
}

@Override
public void actionPerformed(ActionEvent e)
{
    logger.debug( "intercepted action event " + e );
}
}
