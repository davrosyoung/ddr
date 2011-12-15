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

import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import au.com.polly.util.DateRange;
import au.com.polly.util.ProcessStatus;
import junit.framework.JUnit4TestAdapter;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Battery of tests for the gas well data extractor factory class.
 * 
 */
@RunWith(JUnit4.class)
public class GasWellDataExtractorFactoryTest
{
private final static double ACCEPTABLE_ERROR = 1E-6;
Logger logger = Logger.getLogger( GasWellDataExtractorFactoryTest.class );
GasWellDataExtractorFactory factory;
Workbook emptyBook;
Sheet emptySheet;
Sheet sheetAlpha;
Sheet sheetBeta;
Workbook testBook;
DateParser parser;

public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( GasWellDataExtractorFactoryTest.class );
}

@Before
public void setup()
{
    factory = GasWellDataExtractorFactory.getInstance();
    ObjectOutputStream oos;
    FileOutputStream fos;
    GasWellDataSet dataSet;
    GasWell well;
    GasWellDataEntry entry;
    InputStream is = null;
    
    well = new GasWell( "Dave's well" );

    emptyBook = new SXSSFWorkbook();

    try
    {
        emptySheet = new SXSSFSheet((SXSSFWorkbook) emptyBook, null );
    } catch (IOException e) {
        logger.fatal( "What the!?!?!" );
        logger.fatal( e.getClass().getName() + " - " + e.getMessage() );
    }


    try
    {
        is = new FileInputStream( "test_data/TestStandardized.xlsx" );
        testBook = WorkbookFactory.create( is );
    } catch ( Exception e ) {
        logger.fatal("Failed to load data from test_data/TestStandardized.xlsx");
        logger.fatal( e.getClass().getName() + " - " + e.getMessage() );
    }

    File objFile = new File( "test_data/daves_obj.obj" ); 
    DateRange blah = new DateRange( new Date(), 3600000L );

    try
    {
        fos = new FileOutputStream( objFile );
        oos = new ObjectOutputStream( fos );
        oos.writeObject( blah );
        oos.close();
        fos.close();
    } catch (IOException e)
    {
        logger.fatal( "Failed to write data object out to test_data/daves_obj.obj" );
        logger.fatal( e.getClass().getName() + " - " + e.getMessage()  );
    }
    
    parser = new AussieDateParser();

}

@Test(expected=NullPointerException.class)
public void testConstructingJavaSerializedExtractorWithNullInputStream()
{
    GasWellDataExtractor extractor = null;

    try {
        extractor = GasWellDataExtractorFactory.getInstance().getJavaSerializedObjectExtractor(null);
    } catch (IOException e )  {
        logger.error( e.getClass().getName() + " - " + e.getMessage() );
        fail( "IO Exception trying to read object file." );
    } catch (ClassNotFoundException e) {
        logger.error( e.getClass().getName() + " - " + e.getMessage() );
        fail( "Failed to locate class!!" );
    }

}

@Test(expected=ClassCastException.class)
public void testConstructingJavaSerializedExtractorWithWrongObjectType()
{
    GasWellDataExtractor extractor = null;
    File objFile = new File( "test_data/daves_obj.obj" );

    FileInputStream fis =null;
    ObjectInputStream ois = null;

    try
    {
        fis = new FileInputStream( objFile );   // this file contains a serialized DateRange object!!!
        ois = new ObjectInputStream( fis );
    } catch (IOException e) {
        logger.fatal( "Failed to read open serialized object file \"" + objFile.getAbsolutePath() + "\"" );
    }

    try
    {
        extractor = GasWellDataExtractorFactory.getInstance().getJavaSerializedObjectExtractor( ois );
    } catch (IOException e )  {
        logger.error( e.getClass().getName() + " - " + e.getMessage() );
        fail( "IO Exception trying to read object file." );
    } catch (ClassNotFoundException e) {
        logger.error( e.getClass().getName() + " - " + e.getMessage() );
        fail( "Failed to locate class!!" );

    }

    try
    {
        ois.close();
    } catch (IOException whatThe) {
        logger.error( "Failed to close object input stream!" );
    }
}

@Test
public void testConstructingJavaSerializedExtractor()
{
    GasWellDataExtractor extractor = null;
    Map<GasWell,GasWellDataSet>  dataMap;
    GasWell sa11Well;
    GasWell sa2Well;
    GasWell dummyWell;

    dummyWell = TestGasWellDataSet.getDummyDataSet().getWell();
    sa11Well = TestGasWellDataSet.getNicksDataSet().getWell();
    sa2Well = TestGasWellDataSet.getSAA2FragmentDataSet().getWell();

    // construct a mwdm!!
    MultipleWellDataMap mwdm = new MultipleWellDataMap();
    mwdm.addDataSet( TestGasWellDataSet.getDummyDataSet() );
    mwdm.addDataSet( TestGasWellDataSet.getNicksDataSet() );
    mwdm.addDataSet( TestGasWellDataSet.getSAA2FragmentDataSet() );
    
    File testDataFile = null;
    
    FileInputStream fis;
    FileOutputStream fos;
    ObjectInputStream ois;
    ObjectOutputStream oos;


    try {
        testDataFile = new File( "test_data/mwdm.obj" );
        fos = new FileOutputStream( testDataFile );
        oos = new ObjectOutputStream( fos );
        oos.writeObject( mwdm );
        oos.close();
        fos.close();

        fis = new FileInputStream( testDataFile );
        ois = new ObjectInputStream( fis );
        extractor = GasWellDataExtractorFactory.getInstance().getJavaSerializedObjectExtractor( ois );
        
        assertNotNull( extractor );
        assertTrue( extractor instanceof JavaSerializedGasWellDataExtractor );
        
        dataMap = extractor.extract();
        
        assertNotNull( dataMap );
        assertTrue(dataMap.containsKey(dummyWell));
        assertTrue(dataMap.containsKey(sa11Well));
        assertTrue( dataMap.containsKey( sa2Well ) );

    } catch (IOException e )  {
        logger.error( e.getClass().getName() + " - " + e.getMessage() );
        fail( "IO Exception trying to read object file." );
    } catch (ClassNotFoundException e) {
        logger.error( e.getClass().getName() + " - " + e.getMessage() );
        fail( "Failed to locate class!!" );
    }

}



@Test(expected=NullPointerException.class)
public void testConstructingExcelStandardizedExtractorWithNullWorkbook()
{
    GasWellDataExtractor extractor = null;
    
    extractor = GasWellDataExtractorFactory.getInstance().getExcelStandardizedGasWellDataExtractor( null, null );    
}


@Test(expected=IllegalArgumentException.class)
public void testConstructingExcelStandardizedExtractorWithEmptySpreadsheet()
{
    GasWellDataExtractor extractor = null;
    ExcelWorkbookExplorer explorer = ExcelWorkbookExplorerFactory.getInstance().createExcelStandardizedWorkbookExplorer( testBook );
    List<GasWellDataLocator> locations = explorer.getLocations();

    extractor = GasWellDataExtractorFactory.getInstance().getExcelStandardizedGasWellDataExtractor( emptyBook, locations );
}

@Test
public void testConstructingExcelStandardizedExtractor()
{
    GasWellDataExtractor extractor = null;
    ExcelWorkbookExplorer explorer = null;
    ProcessStatus status = null;
    List<GasWellDataLocator> locations = null;
    Map<GasWell,GasWellDataSet> dataMap;
    String[] wellName = new String[] { "SAA-1L", "SAA-1S", "SAA-2", "SAA-4", "SAA-5ST", "SAA-13", "SAA-7", "SAA-10ST1", "SAA-9", "SAA-11" };
    boolean[] wellFound = new boolean[] { false, false, false, false, false, false, false, false, false, false };
    
    
    explorer = ExcelWorkbookExplorerFactory.getInstance().createExcelStandardizedWorkbookExplorer( testBook );
    locations = explorer.getLocations();
    assertNotNull( locations );
    extractor = GasWellDataExtractorFactory.getInstance().getExcelStandardizedGasWellDataExtractor( testBook, locations );
    status = extractor.getStatus();
    assertNotNull( status );
    assertEquals( "waiting", status.getPhase() );
    assertEquals( 0, status.getPercentageComplete() );
    dataMap = extractor.extract();
    assertEquals( "finished", status.getPhase() );
    assertEquals( 100, status.getPercentageComplete() );
    assertNotNull( dataMap );
    assertEquals( 10, dataMap.size() );
    
    
    for( GasWell well : dataMap.keySet() )
    {
        for( int i = 0; i < wellName.length; i++ )
        {
            if ( well.getName().equals( wellName[ i ] ) )
            {
                if ( wellFound[ i ] )
                {
                    fail( "aarrgh!! Well \"" + wellName[ i ] + "\" is already found!!" );
                } else {
                    wellFound[ i ] = true;
                }
            }
        }
    }
    
    for( int i = 0; i < wellFound.length; i++ )
    {
        assertTrue( "well \"" + wellName[ i ] + "\" was NOT found!?!", wellFound[ i ] );
    }
    
    GasWellDataSet saa2DataSet = dataMap.get( new GasWell( "SAA-2" ) );
    assertNotNull( saa2DataSet );
    
    assertEquals( parser.parse( "14/AUG/2005" ).getTime(), saa2DataSet.from() );
    assertEquals( parser.parse( "01/AUG/2011" ).getTime(), saa2DataSet.until() );

    Map<WellMeasurementType,Double> totalVolumes = GasWellDataSetUtil.calculateTotalVolume( saa2DataSet );
    assertNotNull( totalVolumes );
    
    assertEquals( 16025830.95, totalVolumes.get( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 6105.46, totalVolumes.get( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 7162231.04, totalVolumes.get( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
    assertFalse( totalVolumes.containsKey( WellMeasurementType.CONDENSATE_FLOW ) );
}



} // end GasWellDataExtractorFactory
