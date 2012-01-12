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
import au.com.polly.ddr.TestGasWellDataSet;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author
 *
 * todo: finish this harness!!
 *
 *
 */
public class IntervalEditorPaneHarness implements IntervalEditorListener
{
private final static Logger logger = Logger.getLogger( IntervalEditorPaneHarness.class );
private GasWellDataSet dataSet;
private IntervalEditorPane editorPane;

private static IntervalEditorPaneHarness harness;
private static JFrame f;

@Override
public void cancelIntervalEditor()
{
    logger.debug( "cancelIntervalEditor() method invoked!!" );
    f.setVisible(false);
    f.dispose();
}

@Override
public void saveIntervalEditor(GasWellDataSet data)
{
    logger.debug( "saveIntervalEditor() method invoked!!" );

    f.setVisible( false );
    f.dispose();
    PrintWriter writer = new PrintWriter( System.out );
    data.outputCSV( writer );
    writer.flush();
//    System.exit( 0 );
}

public void createAndShowGUI()
{
    f = new JFrame( "Interval Boundaries" );
    f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    f.setSize( 1400, 800 );
    editorPane = new IntervalEditorPane( dataSet.copy(), dataSet );
    editorPane.addEditorListener( harness );
    f.setContentPane( editorPane );
    f.pack();
    f.setVisible( true );
}

public static void main( String... args )
{
    GasWellDataExtractor extractor = null;
    GasWellDataExtractorFactory factory = null;
    List<GasWellDataSet> dataSetList = null;
    
    harness = new IntervalEditorPaneHarness();

    if ( ( args != null ) && ( args.length > 0 ) )
    {
        String filename = args[ 0 ];
        File file = new File( filename );
        FileReader fr = null;

        try
        {
            fr = new FileReader( file );
            factory = GasWellDataExtractorFactory.getInstance();
            extractor = factory.getCSVGasWellDataExtractor( fr );
            MultipleWellDataMap mwdm = extractor.extract();
            dataSetList = mwdm.getDataSetList();
            if ( dataSetList.size() > 0 )
            {
                harness.dataSet = mwdm.getDataSetList().get( 0 );
            } else {
                logger.error("NO gas well data sets retrieved from CSV file \"" + filename + "\"");
            }
        } catch (FileNotFoundException e) {
            logger.error("Failed to load data set from \"" + args[0] + "\".");
            logger.error(e.getClass().getName() + " - " + e.getMessage());
        }
    }

    if ( harness.dataSet == null )
    {
        harness.dataSet = TestGasWellDataSet.getNicksDataSet();
    }

    Runnable doCreateAndShowGUI = new Runnable() {
        public void run() {
            harness.createAndShowGUI();
        }
    };

    SwingUtilities.invokeLater( doCreateAndShowGUI );
}

}
