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
import au.com.polly.plotter.NumericAxis;
import au.com.polly.plotter.PlotData;
import au.com.polly.plotter.TimestampAxis;
import au.com.polly.util.TimestampArmyKnife;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.EnumSet;


/**
 *
 * Graph a sample DDR gas/oil data set.
 *
 * authored by dave young, out of frustration on the lack of available graphing software.
 */
public class DDRDemoPlotter2 extends JFrame implements ActionListener
{
    private final static Logger logger = Logger.getLogger(DDRDemoPlotter2.class);
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

    public DDRDemoPlotter2()
    {
        super( "Disk IO Performance" );
        TimestampArmyKnife knife = new TimestampArmyKnife();


        xAxisConfig = new AxisConfiguration( "Date/Time", null, Color.WHITE,  Color.GRAY, 600 );
        yAxisConfig = new AxisConfiguration( "Oil & Water flow", null, Color.WHITE, Color.GRAY,  600 );
        y2AxisConfig = new AxisConfiguration( "Gas Flow", "bbls/day", Color.YELLOW, 600 );
        xAxis = new TimestampAxis<Long>();
        yAxis = new NumericAxis<Double>();
        y2Axis = new NumericAxis<Double>();

        dataPlotter = new DataPlotter( 800, 800 );
        dataPlotter.setTitle("SAA2 Flow Rates");


        Canvas canvas = new GraphCanvas( dataPlotter );
        canvas.setBounds(0, 0, 800, 800);

        GasWellDataSet ds = TestGasWellDataSet.getBY11DataSet();
        
        PlotData<Long,Double> oilPlotData = ds.getPlotData( WellMeasurementType.CONDENSATE_FLOW );
        oilPlotData.setColour( Color.GREEN );
		oilPlotData.setMarkerSize(8);
        
        PlotData<Long,Double> waterPlotData = ds.getPlotData( WellMeasurementType.WATER_FLOW );
        waterPlotData.setColour( Color.BLUE );
		waterPlotData.setMarkerSize(8);

        PlotData<Long,Double> gasPlotData = ds.getPlotData( WellMeasurementType.GAS_FLOW );
        gasPlotData.setColour( Color.YELLOW );
		gasPlotData.setMarkerSize( 8 );

        // now plot the reduced data too.
        ReductionParameters reductionParameters = new ReductionParameters( WellMeasurementType.CONDENSATE_FLOW );
        DataRateReducer reducer = new SimpleDiscontinuityDataRateReducerV2( reductionParameters );
        GasWellDataSet rds = reducer.reduce( ds );

        PlotData<Long,Double> reducedOilPlotData = rds.getPlotData( WellMeasurementType.CONDENSATE_FLOW );
        reducedOilPlotData.setColour(Color.GREEN);
        reducedOilPlotData.setMarkerSize(2);
        reducedOilPlotData.setLineStyles( EnumSet.of( PlotData.LineStyle.STEPPED, PlotData.LineStyle.FINE_DASHED, PlotData.LineStyle.THICK ) );

        PlotData<Long,Double> reducedWaterPlotData = rds.getPlotData( WellMeasurementType.WATER_FLOW );
        reducedWaterPlotData.setColour( Color.BLUE );
        reducedWaterPlotData.setMarkerSize( 2 );
        reducedWaterPlotData.setLineStyles( EnumSet.of( PlotData.LineStyle.STEPPED, PlotData.LineStyle.FINE_DASHED, PlotData.LineStyle.THICK ) );

        PlotData<Long,Double> reducedGasPlotData = rds.getPlotData( WellMeasurementType.GAS_FLOW );
        reducedGasPlotData.setColour( Color.YELLOW );
        reducedGasPlotData.setMarkerSize( 2 );
        reducedGasPlotData.setLineStyles( EnumSet.of( PlotData.LineStyle.STEPPED, PlotData.LineStyle.FINE_DASHED, PlotData.LineStyle.THICK ) );

        xAxis.autoScale( oilPlotData.getXAxisData() );

        yAxis.autoScale( oilPlotData.getYAxisData() );
		yAxis.autoScale( waterPlotData.getYAxisData() );

		y2Axis.autoScale( gasPlotData.getYAxisData() );
        
        logger.debug( "OilPlotData=" + oilPlotData );
        logger.debug( "WaterPlotData=" + waterPlotData );
        logger.debug( "GasPlotData=" + gasPlotData );
        logger.debug( "ReducedOilPlotData=" + reducedOilPlotData );
        logger.debug( "ReducedWaterPlotData=" + reducedWaterPlotData );
        logger.debug( "ReducedGasPlotData=" + reducedGasPlotData );

        dataPlotter.addPlotData( oilPlotData, xAxis, yAxis, xAxisConfig, yAxisConfig);
        dataPlotter.addPlotData( waterPlotData, xAxis, yAxis, xAxisConfig, yAxisConfig);
        dataPlotter.addPlotData( gasPlotData, xAxis, y2Axis, xAxisConfig, y2AxisConfig);

        dataPlotter.addPlotData( reducedOilPlotData, xAxis, yAxis, xAxisConfig, yAxisConfig);
        dataPlotter.addPlotData( reducedWaterPlotData, xAxis, yAxis, xAxisConfig, yAxisConfig);
        dataPlotter.addPlotData( reducedGasPlotData, xAxis, y2Axis, xAxisConfig, y2AxisConfig);

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

        JFrame frame = new DDRDemoPlotter2();

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
