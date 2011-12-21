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

package au.com.polly.plotter;

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
 * Graph a data set.
 *
 * authored by dave young, out of frustration on the lack of available graphing software.
 */
public class Plotter2 extends JFrame implements ActionListener
{
    private JLabel label;
    private PlotCanvas canvas;
    private static String labelPrefix = "No of button clicks: ";
    private int numClicks = 0;
    AxisConfiguration xAxisConfig = null;
    AxisConfiguration yAxisConfig = null;
    Axis<Double> xAxis = null;
    Axis<Double> yAxis = null;

    public Plotter2() {
        super("HelloSwing");
        TimestampArmyKnife knife = new TimestampArmyKnife();

        xAxisConfig = new AxisConfiguration( "Date/Time", null, Color.BLACK,  300 );
        yAxisConfig = new AxisConfiguration( "Writes/Second", null, Color.BLACK,  300 );
        xAxis = new NumericAxis<Double>();
        yAxis = new NumericAxis<Double>();

        canvas = new PlotCanvas();
        canvas.setBounds( 0, 0, 800, 800 );
        canvas.setTitle( "Regression Test" );

        // create some data in feed it into the plot canvas....
        // -------------------------------------------------
        PlotData<Double,Double> data = new PlotData<Double,Double>();
/*
        DataPoint<Double,Double> p0 = new DataPoint<Double,Double>( 2.0, 3.8 );
        DataPoint<Double,Double> p1 = new DataPoint<Double,Double>( 3.0, 5.9 );
        DataPoint<Double,Double> p2 = new DataPoint<Double,Double>( 4.0, 8.2 );
        DataPoint<Double,Double> p3 = new DataPoint<Double,Double>( 5.0, 9.7 );
        DataPoint<Double,Double> p4 = new DataPoint<Double,Double>( 6.0, 12.2 );
        DataPoint<Double,Double> p5 = new DataPoint<Double,Double>( 7.0, 14.0 );
        DataPoint<Double,Double> p6 = new DataPoint<Double,Double>( 8.0, 15.7 );
        DataPoint<Double,Double> p7 = new DataPoint<Double,Double>( 9.0, 18.2 );
        DataPoint<Double,Double> p8 = new DataPoint<Double,Double>( 10.0, 19.7 );
        DataPoint<Double,Double> p9 = new DataPoint<Double,Double>( 11.0, 22.8 );
*/
/*
        DataPoint<Double,Double> p0 = new DataPoint<Double,Double>( 186.3, 24.6);
        DataPoint<Double,Double> p1 = new DataPoint<Double,Double>( 245.2, 27.2);
        DataPoint<Double,Double> p2 = new DataPoint<Double,Double>( 197.8, 25.1);
        DataPoint<Double,Double> p3 = new DataPoint<Double,Double>( 192.6, 25.0);
        DataPoint<Double,Double> p4 = new DataPoint<Double,Double>( 187.1, 24.7);
        DataPoint<Double,Double> p5 = new DataPoint<Double,Double>( 185.4, 23.9);
        DataPoint<Double,Double> p6 = new DataPoint<Double,Double>( 175.2, 21.6);
        DataPoint<Double,Double> p7 = new DataPoint<Double,Double>( 168.5, 19.2);
        DataPoint<Double,Double> p8 = new DataPoint<Double,Double>( 142.0, 15.4);
        DataPoint<Double,Double> p9 = new DataPoint<Double,Double>( 117.6, 7.3);
*/
        DataPoint<Double,Double> p0 = new DataPoint<Double,Double>( 50.0, 1600.0 );
        DataPoint<Double,Double> p1 = new DataPoint<Double,Double>( 100.0, 1500.0 );
        DataPoint<Double,Double> p2 = new DataPoint<Double,Double>( 150.0, 1398.7 );
        DataPoint<Double,Double> p3 = new DataPoint<Double,Double>( 200.0, 1311.6 );
        DataPoint<Double,Double> p4 = new DataPoint<Double,Double>( 250.0, 1204.8 );
        DataPoint<Double,Double> p5 = new DataPoint<Double,Double>( 300.0, 1098.6 );

        data.add( p0 );
        data.add( p1 );
        data.add( p2 );
        data.add( p3 );
        data.add( p4 );
        data.add( p5 );
/*
        data.add( p5 );
        data.add( p6 );
        data.add( p7 );
        data.add( p8 );
        data.add( p9 );
*/        
        data.setColour( Color.BLUE );

        yAxis.autoScale( data.getYAxisData() );
        xAxis.autoScale( data.getXAxisData() );


        canvas.addPlotData( data, xAxis, yAxis, xAxisConfig, yAxisConfig );

        JPanel pane = new JPanel();
        pane.setBorder( BorderFactory.createEmptyBorder(5, 5, 5, 5) );
        pane.setLayout( new GridBagLayout());
        pane.add( canvas );

        setContentPane(pane);
    }

    public void actionPerformed(ActionEvent e) {
        numClicks++;
        label.setText(labelPrefix + numClicks);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Couldn't use the cross-platform "
                             + "look and feel: " + e);
        }

        JFrame frame = new Plotter2();

        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        frame.addWindowListener(l);

        frame.pack();
        frame.setVisible(true);
    }


}