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
    private DataPlotter dataPlotter;
    private static String labelPrefix = "Number of button clicks: ";
    private int numClicks = 0;
    AxisConfiguration xAxisConfig = null;
    AxisConfiguration yAxisConfig = null;
    AxisConfiguration y2AxisConfig = null;
    Axis<Long> xAxis = null;
    Axis<Double> yAxis = null;
    Axis<Double> y2Axis = null;

    public Plotter()
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

        // create some data in feed it into the dataPlotter....
        // -------------------------------------------------
        PlotData<Long,Double> data = new PlotData<Long,Double>();
        data.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:00"), 24.6) );
        data.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:01"), 27.2) );
        data.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:02"), 25.1) );
        data.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:03"), 25.0) );
        data.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:04"), 24.7) );
        data.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:05"), 23.9) );
        data.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:06"), 21.6) );
        data.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:07"), 19.2) );
        data.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:08"), 15.4) );
        data.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:09"), 7.3) );
        data.setColour(Color.BLUE);


        // create some data in feed it into the plott dataPlotter....
        // -------------------------------------------------
        PlotData<Long,Double> data2 = new PlotData<Long,Double>();
        data2.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:00"), 14.7 ) );
        data2.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:01"), 16.6 ) );
        data2.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:02"), 17.3 ) );
        data2.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:03"), 19.2 ) );
        data2.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:04"), 21.7 ) );
        data2.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:05"), 25.2 ) );
        data2.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:06"), 23.7 ) );
        data2.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:07"), 27.8 ) );
        data2.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:08"), 32.7 ) );
        data2.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:09"), 37.4 ) );
        data2.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:10"), 39.8 ) );
        data2.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:11"), 41.2 ) );
        data2.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:12"), 41.9 ) );
        data2.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:13"), 41.8 ) );
        data2.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:14"), 41.6 ) );
        data2.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:15"), 41.7 ) );
        data2.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:16"), 41.5 ) );
        data2.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:17"), 41.6 ) );
        data2.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:18"), 41.4 ) );
        data2.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:19"), 41.5 ) );
        data2.setColour(Color.RED);

        yAxis.autoScale( data.getYAxisData() );
        yAxis.autoScale( data2.getYAxisData() );
        xAxis.autoScale( data.getXAxisData() );
        xAxis.autoScale( data2.getXAxisData() );

        // ok, now create some data on a different scale...
        // --------------------------------------------------


        // create some data in feed it into the plott dataPlotter....
        // -------------------------------------------------
        PlotData<Long,Double> data3 = new PlotData<Long,Double>();

        data3.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:00"), 5104.7 ) );
        data3.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:01"), 6156.6 ) );
        data3.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:02"), 4127.3 ) );
        data3.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:03"), 4719.2 ) );
        data3.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:04"), 4421.7 ) );
        data3.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:05"), 3825.2 ) );
        data3.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:06"), 3623.7 ) );
        data3.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:07"), 3927.8 ) );

        data3.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:08"), 4232.7 ) );
        data3.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:09"), 4837.4 ) );
        data3.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:10"), 5439.8 ) );
        data3.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:11"), 5841.2 ) );
        data3.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:12"), 6241.9 ) );
        data3.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:13"), 4141.8 ) );
        data3.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:14"), 3041.6 ) );
        data3.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:15"), 2441.7 ) );
        data3.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:16"), 2641.5 ) );
        data3.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:17"), 2941.6 ) );
        data3.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:18"), 3141.4 ) );
        data3.add( new DataPoint<Long,Double>( knife.parse( "23/AUG/07 15:47:19"), 3241.5 ) );
        data3.setColour( Color.GREEN );


        dataPlotter.addPlotData(data, xAxis, yAxis, xAxisConfig, yAxisConfig);
        dataPlotter.addPlotData(data2, null, null, null, null);
        dataPlotter.addPlotData(data3, null, y2Axis, null, y2AxisConfig);

        JPanel pane = new JPanel();
        pane.setBorder( BorderFactory.createEmptyBorder( 30, 30, 10, 30 ) );
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
