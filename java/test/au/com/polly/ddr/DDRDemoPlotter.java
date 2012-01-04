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

package au.com.polly.ddr;

import au.com.polly.plotter.Axis;
import au.com.polly.plotter.AxisConfiguration;
import au.com.polly.plotter.DataPlotter;
import au.com.polly.plotter.DataPoint;
import au.com.polly.plotter.NumericAxis;
import au.com.polly.plotter.PlotData;
import au.com.polly.plotter.TimestampAxis;
import au.com.polly.util.TimestampArmyKnife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


/**
 *
 * Graph a sample DDR gas/oil data set.
 *
 * authored by dave young, out of frustration on the lack of available graphing software.
 */
public class DDRDemoPlotter extends JFrame implements ActionListener
{
    private JLabel label;
    private DataPlotter dataPlotter;
    private static String labelPrefix = "Number of button clicks: ";
    private int numClicks = 0;
    AxisConfiguration xAxisConfig = null;
    AxisConfiguration yAxisConfig = null;
    AxisConfiguration y2AxisConfig = null;
    Axis<Long> xAxis = null;
    Axis<Double> yAxis = null;
    Axis<Double> y2Axis = null;

    public DDRDemoPlotter()
    {
        super( "Disk IO Performance" );
        TimestampArmyKnife knife = new TimestampArmyKnife();


        xAxisConfig = new AxisConfiguration( "Date/Time", null, Color.WHITE,  600 );
        yAxisConfig = new AxisConfiguration( "Writes/Second", null, Color.WHITE,  600 );
        y2AxisConfig = new AxisConfiguration( "Msgs/second", null, Color.GREEN,  600 );
        xAxis = new TimestampAxis<Long>();
        yAxis = new NumericAxis<Double>();
        y2Axis = new NumericAxis<Double>();

        dataPlotter = new DataPlotter( 800, 800 );
        dataPlotter.setTitle("Disk IO performance");


        Canvas canvas = new GraphCanvas( dataPlotter );
        canvas.setBounds( 0, 0, 800, 800 );
        label = new JLabel(labelPrefix + "0    ");

        GasWellDataSet ds = TestGasWellDataSet.getSAA2FragmentDataSet();
        
        PlotData<Long,Double> oilPlotData = ds.getPlotData( WellMeasurementType.OIL_FLOW );
        oilPlotData.setColour( Color.RED );

        xAxis.autoScale( oilPlotData.getXAxisData() );
        yAxis.autoScale( oilPlotData.getYAxisData() );

        dataPlotter.addPlotData( oilPlotData, xAxis, yAxis, xAxisConfig, yAxisConfig);

        JPanel pane = new JPanel();
        pane.setBorder( BorderFactory.createEmptyBorder( 30, 30, 10, 30 ) );
        pane.setLayout( new GridBagLayout());
        pane.add( canvas );
        setContentPane( pane );
    }

    public void actionPerformed(ActionEvent e) {
        numClicks++;
        label.setText(labelPrefix + numClicks);
    }

    public static void main(String[] args) {

        JFrame frame = new DDRDemoPlotter();

        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        frame.addWindowListener(l);

        frame.pack();
        frame.setVisible( true );
    }


    class GraphCanvas extends Canvas
    {
        DataPlotter plotter = null;
        
        public GraphCanvas( DataPlotter plotter )
        {
            this.plotter = plotter;
        }
        
        public void paint( Graphics g )
        {
            plotter.paint( g );
        }
    }

}
