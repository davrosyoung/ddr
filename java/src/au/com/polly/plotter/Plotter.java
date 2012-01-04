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
public class Plotter extends JFrame implements ActionListener
{
    private JLabel label;
    private PlotCanvas canvas;
    private static String labelPrefix = "Number of button clicks: ";
    private int numClicks = 0;
    AxisConfiguration xAxisConfig = null;
    AxisConfiguration yAxisConfig = null;
    AxisConfiguration y2AxisConfig = null;
    Axis<Long> xAxis = null;
    Axis<Double> yAxis = null;
    Axis<Double> y2Axis = null;

    public Plotter() {
        super("HelloSwing");
        TimestampArmyKnife knife = new TimestampArmyKnife();

        JButton button = new JButton("I'm a Swing button!");
        button.setMnemonic('i');
        button.addActionListener(this);
        button.getAccessibleContext().setAccessibleDescription(
            "When you click this button, the label is updated "
              + "to display the total number of button clicks."
        );

        xAxisConfig = new AxisConfiguration( "Date/Time", null, Color.BLACK,  600 );
        yAxisConfig = new AxisConfiguration( "Writes/Second", null, Color.BLACK,  600 );
        y2AxisConfig = new AxisConfiguration( "Msgs/second", null, Color.GREEN,  600 );
        xAxis = new TimestampAxis<Long>();
        yAxis = new NumericAxis<Double>();
        y2Axis = new NumericAxis<Double>();


        label = new JLabel(labelPrefix + "0    ");
        canvas = new PlotCanvas();
        canvas.setBounds( 0, 0, 800, 800 );
        canvas.setTitle( "Disk IO performance" );

        // create some data in feed it into the plott canvas....
        // -------------------------------------------------
        PlotData<Long,Double> data = new PlotData<Long,Double>();
        DataPoint<Long,Double> p0 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:00"), 24.6);
        DataPoint<Long,Double> p1 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:01"), 27.2);
        DataPoint<Long,Double> p2 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:02"), 25.1);
        DataPoint<Long,Double> p3 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:03"), 25.0);
        DataPoint<Long,Double> p4 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:04"), 24.7);
        DataPoint<Long,Double> p5 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:05"), 23.9);
        DataPoint<Long,Double> p6 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:06"), 21.6);
        DataPoint<Long,Double> p7 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:07"), 19.2);
        DataPoint<Long,Double> p8 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:08"), 15.4);
        DataPoint<Long,Double> p9 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:09"), 7.3);
        data.add( p0 );
        data.add( p1 );
        data.add( p2 );
        data.add( p3 );
        data.add( p4 );
        data.add( p5 );
        data.add( p6 );
        data.add( p7 );
        data.add( p8 );
        data.add( p9 );
        data.setColour( Color.BLUE );


        // create some data in feed it into the plott canvas....
        // -------------------------------------------------
        PlotData<Long,Double> data2 = new PlotData<Long,Double>();
        DataPoint<Long,Double> pp0 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:00"), 14.7 );
        DataPoint<Long,Double> pp1 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:01"), 16.6 );
        DataPoint<Long,Double> pp2 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:02"), 17.3 );
        DataPoint<Long,Double> pp3 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:03"), 19.2 );
        DataPoint<Long,Double> pp4 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:04"), 21.7 );
        DataPoint<Long,Double> pp5 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:05"), 25.2 );
        DataPoint<Long,Double> pp6 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:06"), 23.7 );
        DataPoint<Long,Double> pp7 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:07"), 27.8 );
        DataPoint<Long,Double> pp8 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:08"), 32.7 );
        DataPoint<Long,Double> pp9 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:09"), 37.4 );
        DataPoint<Long,Double> ppa = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:10"), 39.8 );
        DataPoint<Long,Double> ppb = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:11"), 41.2 );
        DataPoint<Long,Double> ppc = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:12"), 41.9 );
        DataPoint<Long,Double> ppd = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:13"), 41.8 );
        DataPoint<Long,Double> ppe = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:14"), 41.6 );
        DataPoint<Long,Double> ppf = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:15"), 41.7 );
        DataPoint<Long,Double> ppg = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:16"), 41.5 );
        DataPoint<Long,Double> pph = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:17"), 41.6 );
        DataPoint<Long,Double> ppi = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:18"), 41.4 );
        DataPoint<Long,Double> ppj = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:19"), 41.5 );
        data2.add( pp0 );
        data2.add( pp1 );
        data2.add( pp2 );
        data2.add( pp3 );
        data2.add( pp4 );
        data2.add( pp5 );
        data2.add( pp6 );
        data2.add( pp7 );
        data2.add( pp8 );
        data2.add( pp9 );
        data2.add( ppa );
        data2.add( ppb );
        data2.add( ppc );
        data2.add( ppd );
        data2.add( ppe );
        data2.add( ppf );
        data2.add( ppg );
        data2.add( pph );
        data2.add( ppi );
        data2.add( ppj );
        data2.setColour( Color.RED );

        yAxis.autoScale( data.getYAxisData() );
        yAxis.autoScale( data2.getYAxisData() );
        xAxis.autoScale( data.getXAxisData() );
        xAxis.autoScale( data2.getXAxisData() );

        // ok, now create some data on a different scale...
        // --------------------------------------------------


        // create some data in feed it into the plott canvas....
        // -------------------------------------------------
        PlotData<Long,Double> data3 = new PlotData<Long,Double>();
        DataPoint<Long,Double> po0 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:00"), 5104.7 );
        DataPoint<Long,Double> po1 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:01"), 6156.6 );
        DataPoint<Long,Double> po2 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:02"), 4127.3 );
        DataPoint<Long,Double> po3 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:03"), 4719.2 );
        DataPoint<Long,Double> po4 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:04"), 4421.7 );
        DataPoint<Long,Double> po5 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:05"), 3825.2 );
        DataPoint<Long,Double> po6 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:06"), 3623.7 );
        DataPoint<Long,Double> po7 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:07"), 3927.8 );
        DataPoint<Long,Double> po8 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:08"), 4232.7 );
        DataPoint<Long,Double> po9 = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:09"), 4837.4 );
        DataPoint<Long,Double> poa = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:10"), 5439.8 );
        DataPoint<Long,Double> pob = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:11"), 5841.2 );
        DataPoint<Long,Double> poc = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:12"), 6241.9 );
        DataPoint<Long,Double> pod = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:13"), 4141.8 );
        DataPoint<Long,Double> poe = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:14"), 3041.6 );
        DataPoint<Long,Double> pof = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:15"), 2441.7 );
        DataPoint<Long,Double> pog = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:16"), 2641.5 );
        DataPoint<Long,Double> poh = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:17"), 2941.6 );
        DataPoint<Long,Double> poi = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:18"), 3141.4 );
        DataPoint<Long,Double> poj = new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:19"), 3241.5 );
        data3.add( po0 );
        data3.add( po1 );
        data3.add( po2 );
        data3.add( po3 );
        data3.add( po4 );
        data3.add( po5 );
        data3.add( po6 );
        data3.add( po7 );
        data3.add( po8 );
        data3.add( po9 );
        data3.add( poa );
        data3.add( pob );
        data3.add( poc );
        data3.add( pod );
        data3.add( poe );
        data3.add( pof );
        data3.add( pog );
        data3.add( poh );
        data3.add( poi );
        data3.add( poj );
        data3.setColour( Color.GREEN );


        canvas.addPlotData( data, xAxis, yAxis, xAxisConfig, yAxisConfig );
        canvas.addPlotData( data2, null, null, null, null );
        canvas.addPlotData( data3, null, y2Axis, null, y2AxisConfig );

        JPanel pane = new JPanel();
        pane.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        pane.setLayout( new GridBagLayout());
//        pane.add(button);
//        pane.add( label );
        pane.add( canvas );

        setContentPane( pane );
    }

    public void actionPerformed(ActionEvent e) {
        numClicks++;
        label.setText(labelPrefix + numClicks);
    }

    public static void main(String[] args) {

        JFrame frame = new Plotter();

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
