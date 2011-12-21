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

package au.com.polly.ddr;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * @author
 *
 * todo: finish this harness!!
 *
 *
 */
public class IntervalEditorPaneHarness
{
private final static Logger logger = Logger.getLogger(IntervalEditorPaneHarness.class);
private static GasWellDataSet dataSet;

public static void createAndShowGUI()
{
    JFrame f = new JFrame( "Interval Boundaries" );
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setSize(1400, 800);
    f.setContentPane( new IntervalEditorPane( dataSet ) );
    f.pack();
    f.setVisible( true );
}

public static void main( String... args )
{
    GasWellDataExtractor extractor = null;
    GasWellDataExtractorFactory factory = null;
    List<GasWellDataSet> dataSetList = null;

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
                IntervalEditorPaneHarness.dataSet = mwdm.getDataSetList().get( 0 );
                
                Runnable doCreateAndShowGUI = new Runnable() {
                    public void run() {
                        createAndShowGUI();
                    }
                };
    
                SwingUtilities.invokeLater( doCreateAndShowGUI );
            } else {
                logger.fatal( "NO gas well data sets retrieved from CSV file \"" + filename + "\"" );
            }
        } catch (FileNotFoundException e) {
            logger.fatal( "Failed to load data set from \"" + args[ 0 ] + "\"." );
            logger.fatal(e.getClass().getName() + " - " + e.getMessage());
        }

    } else {
        logger.fatal( "No csv input file specified!!" );
    }

}

}