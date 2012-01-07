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

import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import au.com.polly.util.DateRange;
import au.com.polly.util.ProcessStatus;
import junit.framework.JUnit4TestAdapter;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
public class CSVGasWellDataExtractorTest
{
private final static double ACCEPTABLE_ERROR = 1E-6;
Logger logger = Logger.getLogger( CSVGasWellDataExtractorTest.class );
GasWellDataExtractorFactory factory;
Workbook emptyBook;
Sheet emptySheet;
Sheet sheetAlpha;
Sheet sheetBeta;
Workbook testBook;
DateParser parser;
StringReader testCSVReader = null;
StringReader SAA10ST1CSVReader = null;
StringReader dummyCSVReader = null;
StringReader saa2CSVReader = null;


public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( CSVGasWellDataExtractorTest.class );
}

@Before
public void setup()
{
    StringBuilder testCSVData = new StringBuilder();
    testCSVData.append( "# well \"osho-5\"\n" );
    testCSVData.append( "# client: dirty big oil company\n" );
    testCSVData.append( "#\n" );
    testCSVData.append( "date/time,well,interval length,oil flow,gas flow,water flow\n" );
    testCSVData.append( "13/JUN/2011 05:00:00, osho-2, 24, 117.6, 006.4, 325.1,\"start of data\"\n" );
    testCSVData.append( "14/JUN/2011 05:00:00, osho-2, 24, 121.3, 008.1, 335.5,\n" );
    testCSVData.append( "15/JUN/2011 05:00:00, osho-2, 24, 124.1, 007.6, 337.2\n" );
    testCSVData.append( "16/JUN/2011 05:00:00, osho-2, 24, 118.2, 012.2, 342.6\n" );
    testCSVData.append( "17/JUN/2011 05:00:00, osho-2, 24, 115.3, 013.9, 317.5\n" );
    testCSVData.append( "13/JUN/2011 05:00:00, osho-9b, 24, 517.6, 106.4, 925.1\n" );
    testCSVData.append( "14/JUN/2011 05:00:00, osho-9b, 24, 521.3, 108.1, 935.5\n" );
    testCSVData.append( "15/JUN/2011 05:00:00, osho-9b, 24, 524.1, 107.6, 937.2\n" );
    testCSVData.append( "16/JUN/2011 05:00:00, osho-9b, 24, 518.2, 112.2, 942.6\n" );
    testCSVData.append( "17/JUN/2011 05:00:00, osho-9b, 24, 515.3, 113.9, 917.5\n" );
    testCSVData.append( "18/JUN/2011 05:00:00, osho-9b, 24, 519.3, 110.9, 913.5, \"end of data\"\n" );
    testCSVReader = new StringReader( testCSVData.toString() );

    StringBuilder text = new StringBuilder();
    text.append( "# Well:SAA-10ST1" );
    text.append( "# Date Range:16/JUN/2006 00:00 - 01/AUG/2011 00:00" );
    text.append( "# Duration: 1872 days  0 hours  0 mins  0 seconds.   |" );
    text.append( "well,date/time,interval length (hours),oil flow,gas flow,water flow,comment" );
    text.append( "SAA-10ST1,16/JUN/2006 00:00,00504.0000,001594.70857,000000.64952,000000.00000,\"Start of data\"" );
    text.append( "SAA-10ST1,07/JUL/2006 00:00,00192.0000,000000.00000,000000.00000,000000.00000,\"Outage starts on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,15/JUL/2006 00:00,01872.0000,001694.36821,000000.62538,000009.50744,\"Outage ends on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,01/OCT/2006 00:00,00072.0000,001088.42000,000000.41333,000103.81000,\"automated regular boundary marker\"" );
    text.append( "SAA-10ST1,04/OCT/2006 00:00,00024.0000,000000.00000,000000.00000,000000.00000,\"Outage starts on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,05/OCT/2006 00:00,00264.0000,002023.91727,000000.80182,000309.24273,\"Outage ends on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,16/OCT/2006 00:00,00024.0000,000000.00000,000000.00000,000000.00000,\"Outage starts on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,17/OCT/2006 00:00,00600.0000,001784.13400,000000.65480,000247.59520,\"Outage ends on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,11/NOV/2006 00:00,00072.0000,000000.00000,000000.00000,000000.00000,\"Outage starts on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,14/NOV/2006 00:00,01151.0000,000972.34655,000000.36886,000328.06645,\"Outage ends on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,01/JAN/2007 00:00,02161.0000,000777.59345,000000.31674,000699.32346,\"automated regular boundary marker\"" );
    text.append( "SAA-10ST1,01/APR/2007 00:00,01968.0000,000510.40951,000000.26659,000727.57415,\"automated regular boundary marker\"" );
    text.append( "SAA-10ST1,22/JUN/2007 00:00,00144.0000,000000.00000,000000.00000,000000.00000,\"Outage starts on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,28/JUN/2007 00:00,00072.0000,000302.85667,000000.14000,000075.92000,\"Outage ends on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,01/JUL/2007 00:00,02208.0000,000596.36870,000000.34076,000709.37902,\"automated regular boundary marker\"" );
    text.append( "SAA-10ST1,01/OCT/2007 00:00,02207.0000,000709.02122,000000.40159,000896.13466,\"automated regular boundary marker\"" );
    text.append( "SAA-10ST1,01/JAN/2008 00:00,02185.0000,000737.67509,000000.40311,001243.29128,\"automated regular boundary marker\"" );
    text.append( "SAA-10ST1,01/APR/2008 00:00,02184.0000,000583.43857,000000.34659,001230.13088,\"automated regular boundary marker\"" );
    text.append( "SAA-10ST1,01/JUL/2008 00:00,00744.0000,000306.18806,000000.21000,001116.46323,\"automated regular boundary marker\"" );
    text.append( "SAA-10ST1,01/AUG/2008 00:00,00120.0000,000000.00000,000000.00000,000000.00000,\"Outage starts on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,06/AUG/2008 00:00,01344.0000,000314.97964,000000.18071,001223.67768,\"Outage ends on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,01/OCT/2008 00:00,02207.0000,000834.11451,000000.36343,001395.15143,\"automated regular boundary marker\"" );
    text.append( "SAA-10ST1,01/JAN/2009 00:00,00408.0000,000317.91588,000000.17824,000465.51588,\"automated regular boundary marker\"" );
    text.append( "SAA-10ST1,18/JAN/2009 00:00,00048.0000,000000.00000,000000.00000,000000.00000,\"Outage starts on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,20/JAN/2009 00:00,00456.0000,000272.37684,000000.16158,000558.12263,\"Outage ends on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,08/FEB/2009 00:00,00096.0000,000000.00000,000000.00000,000000.00000,\"Outage starts on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,12/FEB/2009 00:00,01153.0000,000433.54907,000000.24604,000842.68371,\"Outage ends on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,01/APR/2009 00:00,02184.0000,000943.40341,000000.44527,001151.16209,\"automated regular boundary marker\"" );
    text.append( "SAA-10ST1,01/JUL/2009 00:00,01008.0000,000790.77690,000000.43190,001038.30976,\"automated regular boundary marker\"" );
    text.append( "SAA-10ST1,12/AUG/2009 00:00,00072.0000,000000.00000,000000.00000,000000.00000,\"Outage starts on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,15/AUG/2009 00:00,01128.0000,000676.63234,000000.40298,000882.39702,\"Outage ends on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,01/OCT/2009 00:00,02208.0000,000788.50707,000000.40478,001010.60641,\"automated regular boundary marker\"" );
    text.append( "SAA-10ST1,01/JAN/2010 00:00,02160.0000,000601.36022,000000.27378,000943.18544,\"automated regular boundary marker\"" );
    text.append( "SAA-10ST1,01/APR/2010 00:00,01368.0000,000517.95316,000000.23228,000952.27000,\"automated regular boundary marker\"" );
    text.append( "SAA-10ST1,28/MAY/2010 00:00,00120.0000,000000.00000,000000.00000,000000.00000,\"Outage starts on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,02/JUN/2010 00:00,00696.0000,000455.27379,000000.20448,000973.12172,\"Outage ends on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,01/JUL/2010 00:00,02208.0000,000361.27239,000000.14772,001016.53043,\"automated regular boundary marker\"" );
    text.append( "SAA-10ST1,01/OCT/2010 00:00,02208.0000,000363.55717,000000.12565,001431.82196,\"automated regular boundary marker\"" );
    text.append( "SAA-10ST1,01/JAN/2011 00:00,02160.0000,000488.70444,000000.22078,001442.29789,\"automated regular boundary marker\"" );
    text.append( "SAA-10ST1,01/APR/2011 00:00,00552.0000,000456.29435,000000.18870,001360.85261,\"automated regular boundary marker\"" );
    text.append( "SAA-10ST1,24/APR/2011 00:00,00048.0000,000000.00000,000000.00000,000000.00000,\"Outage starts on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,26/APR/2011 00:00,01584.0000,000415.27727,000000.16667,001281.18909,\"Outage ends on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,01/JUL/2011 00:00,00216.0000,000426.95667,000000.17444,001296.30000,\"automated regular boundary marker\"" );
    text.append( "SAA-10ST1,10/JUL/2011 00:00,00096.0000,000000.00000,000000.00000,000000.00000,\"Outage starts on primary indicator (OIL_FLOW)\"" );
    text.append( "SAA-10ST1,14/JUL/2011 00:00,00432.0000,000442.94833,000000.17222,001285.64889,\"Outage ends on primary indicator (OIL_FLOW)\"" );
    SAA10ST1CSVReader = new StringReader( text.toString() );

    StringWriter sw = new StringWriter();
    PrintWriter writer = new PrintWriter( sw );
    TestGasWellDataSet.getDummyDataSet().outputCSV( writer );
    dummyCSVReader = new StringReader( sw.toString() );

    sw = new StringWriter();
    writer = new PrintWriter( sw );
    TestGasWellDataSet.getSAA2FragmentDataSet().outputCSV( writer );
    saa2CSVReader = new StringReader( sw.toString() );

    parser = new AussieDateParser();
}

@Test( expected=NullPointerException.class )
public void testConstructingCSVGasWellDataExtractorWithNullReader()
{
    new CSVGasWellDataExtractor( null );
}

@Test( expected = NullPointerException.class )
public void testResolvingFieldTypeNullArg()
{
    CSVGasWellDataExtractor.resolveFieldType( null );
}

@Test
public void testResolvingFieldTypeEmptyString()
{
    assertEquals(CSVGasWellDataExtractor.FieldType.UNKNOWN, CSVGasWellDataExtractor.resolveFieldType(""));
}

@Test
public void testResolvingFieldTypes()
{
    assertEquals(CSVGasWellDataExtractor.FieldType.WELL_NAME, CSVGasWellDataExtractor.resolveFieldType("well"));
    assertEquals( CSVGasWellDataExtractor.FieldType.WELL_NAME, CSVGasWellDataExtractor.resolveFieldType( "Well" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.WELL_NAME, CSVGasWellDataExtractor.resolveFieldType( "WELL" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.WELL_NAME, CSVGasWellDataExtractor.resolveFieldType( "well name" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.WELL_NAME, CSVGasWellDataExtractor.resolveFieldType( " well name " ) );

}

@Test
public void testResolvingIntervalLengthFieldType()
{
    assertEquals( CSVGasWellDataExtractor.FieldType.INTERVAL_LENGTH, CSVGasWellDataExtractor.resolveFieldType( "int. length" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.INTERVAL_LENGTH, CSVGasWellDataExtractor.resolveFieldType( "length (hrs)" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.INTERVAL_LENGTH, CSVGasWellDataExtractor.resolveFieldType( "length (hrs)" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.INTERVAL_LENGTH, CSVGasWellDataExtractor.resolveFieldType( "interval length" ) );
}

@Test
public void testResolvingTimestampFieldType()
{
    assertEquals( CSVGasWellDataExtractor.FieldType.TIMESTAMP, CSVGasWellDataExtractor.resolveFieldType( "date/time" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.TIMESTAMP, CSVGasWellDataExtractor.resolveFieldType( "date" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.TIMESTAMP, CSVGasWellDataExtractor.resolveFieldType( "timestamp" ) );
}

@Test
public void testResolvingOilFlowFieldType()
{
    assertEquals( CSVGasWellDataExtractor.FieldType.OIL_FLOW, CSVGasWellDataExtractor.resolveFieldType( "oil flow rate (bbls/hr)" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.OIL_FLOW, CSVGasWellDataExtractor.resolveFieldType( "oil flow rate (gl/sec)" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.OIL_FLOW, CSVGasWellDataExtractor.resolveFieldType( "oil flow rate" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.OIL_FLOW, CSVGasWellDataExtractor.resolveFieldType( "oil rate" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.OIL_FLOW, CSVGasWellDataExtractor.resolveFieldType( "oil flow" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.OIL_FLOW, CSVGasWellDataExtractor.resolveFieldType( "oil" ) );
}

@Test
public void testResolvingGasFlowFieldType()
{
    assertEquals( CSVGasWellDataExtractor.FieldType.GAS_FLOW, CSVGasWellDataExtractor.resolveFieldType( "gas flow rate (bbls/day)" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.GAS_FLOW, CSVGasWellDataExtractor.resolveFieldType( "gas flow rate (km3/sec)" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.GAS_FLOW, CSVGasWellDataExtractor.resolveFieldType( "gas flow rate" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.GAS_FLOW, CSVGasWellDataExtractor.resolveFieldType( "gas rate" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.GAS_FLOW, CSVGasWellDataExtractor.resolveFieldType( "gas flow" ) );
}

@Test
public void testResolvingWaterFlowFieldType()
{
    assertEquals( CSVGasWellDataExtractor.FieldType.WATER_FLOW, CSVGasWellDataExtractor.resolveFieldType( "water flow rate (bbls/sec)" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.WATER_FLOW, CSVGasWellDataExtractor.resolveFieldType( "water flow rate (ml/year)" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.WATER_FLOW, CSVGasWellDataExtractor.resolveFieldType( "water flow rate" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.WATER_FLOW, CSVGasWellDataExtractor.resolveFieldType( "water rate" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.WATER_FLOW, CSVGasWellDataExtractor.resolveFieldType( "water flow" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.WATER_FLOW, CSVGasWellDataExtractor.resolveFieldType( "water" ) );
}

@Test
public void testResolvingCommentFieldType()
{
    assertEquals( CSVGasWellDataExtractor.FieldType.COMMENT, CSVGasWellDataExtractor.resolveFieldType( "comment" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.COMMENT, CSVGasWellDataExtractor.resolveFieldType( "description of measurement" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.COMMENT, CSVGasWellDataExtractor.resolveFieldType( "explain it to me baby!!" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.COMMENT, CSVGasWellDataExtractor.resolveFieldType( "explanations are harder than apologies" ) );
}

@Test
public void testResolvingCondensateFlowFieldType()
{
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "gas cond. flow rate" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "gas cond. rate" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "gas cond. flow" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "gas cond." ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "gas cond flow rate" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "gas cond rate" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "gas cond flow" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "gas cond" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "gas condensate flow rate" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "gas condensate rate" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "gas condensate flow" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "gas condensate " ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "condensate flow rate" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "condensate rate" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "condensate flow" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "condensate " ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "cond. flow rate" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "cond. rate" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "cond. flow" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "cond." ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "cond flow rate" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "cond rate" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "cond flow" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW, CSVGasWellDataExtractor.resolveFieldType( "cond" ) );
}

@Test
public void testResolvingUnknownFieldTypes()
{
    assertEquals( CSVGasWellDataExtractor.FieldType.UNKNOWN, CSVGasWellDataExtractor.resolveFieldType( "well of course" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.UNKNOWN, CSVGasWellDataExtractor.resolveFieldType( "unwell" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.UNKNOWN, CSVGasWellDataExtractor.resolveFieldType( "some well" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.UNKNOWN, CSVGasWellDataExtractor.resolveFieldType( "the well" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.UNKNOWN, CSVGasWellDataExtractor.resolveFieldType( "soil" ) );
    assertEquals( CSVGasWellDataExtractor.FieldType.UNKNOWN, CSVGasWellDataExtractor.resolveFieldType("H2O") );
    assertEquals( CSVGasWellDataExtractor.FieldType.UNKNOWN, CSVGasWellDataExtractor.resolveFieldType( "gas" ) );
}

@Test
public void testProcessingColumnHeadings()
{
    List<CSVGasWellDataExtractor.FieldType> columnHeadingList = null;
    
    columnHeadingList = CSVGasWellDataExtractor.processColumnHeadings( "time,oil flow rate,gas flow rate,water flow rate" );
    assertNotNull( columnHeadingList );
    assertEquals( 4, columnHeadingList.size() );
    assertTrue( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.TIMESTAMP ) );
    assertTrue( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.OIL_FLOW ) );
    assertTrue( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.GAS_FLOW ) );
    assertTrue( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.WATER_FLOW ) );
    assertFalse( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.WELL_NAME ) );
    assertFalse( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.INTERVAL_LENGTH ) );
    assertFalse( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW ) );
    assertFalse( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.COMMENT ) );

    
    columnHeadingList = CSVGasWellDataExtractor.processColumnHeadings( "well,time,interval,cond.,gas rate,water flow" );
    assertNotNull( columnHeadingList );
    assertEquals( 6, columnHeadingList.size() );
    assertTrue( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.TIMESTAMP ) );
    assertFalse( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.OIL_FLOW ) );
    assertTrue( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.GAS_FLOW ) );
    assertTrue( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.WATER_FLOW ) );
    assertTrue( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.WELL_NAME ) );
    assertTrue( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.INTERVAL_LENGTH ) );
    assertTrue( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW ) );
    assertFalse( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.COMMENT ) );
    assertFalse( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.UNKNOWN ) );


    columnHeadingList = CSVGasWellDataExtractor.processColumnHeadings( "well,time,interval,cond.,gas rate,water flow,comment" );
    assertNotNull( columnHeadingList );
    assertEquals( 7, columnHeadingList.size() );
    assertTrue( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.TIMESTAMP ) );
    assertFalse( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.OIL_FLOW ) );
    assertTrue( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.GAS_FLOW ) );
    assertTrue( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.WATER_FLOW ) );
    assertTrue( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.WELL_NAME ) );
    assertTrue( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.INTERVAL_LENGTH ) );
    assertTrue( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW ) );
    assertTrue( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.COMMENT ) );
    assertFalse( columnHeadingList.contains( CSVGasWellDataExtractor.FieldType.UNKNOWN ) );
    
}

@Test(expected = NullPointerException.class )
public void testProcessingColumnHeadingsNullArg()
{
    
    CSVGasWellDataExtractor.processColumnHeadings( null );
 
    
}

@Test(expected = IllegalArgumentException.class)
public void testProcessingColumnHeadingsEmptyLine()
{
    List<CSVGasWellDataExtractor.FieldType> columnHeadingList = null;
    
    columnHeadingList = CSVGasWellDataExtractor.processColumnHeadings( "" );
}

@Test
public void testProcessingDataLine()
{
    List<CSVGasWellDataExtractor.FieldType> columns = new ArrayList<CSVGasWellDataExtractor.FieldType>();
    columns.add( CSVGasWellDataExtractor.FieldType.WELL_NAME );
    columns.add( CSVGasWellDataExtractor.FieldType.TIMESTAMP );
    columns.add( CSVGasWellDataExtractor.FieldType.INTERVAL_LENGTH );
    columns.add( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW );
    columns.add( CSVGasWellDataExtractor.FieldType.WATER_FLOW );
    columns.add( CSVGasWellDataExtractor.FieldType.GAS_FLOW );
    
    String text = "osho2,13/JUN/2011 05:25:00,1.0,130.6,285.2,12.3";
    GasWellDataEntry entry = CSVGasWellDataExtractor.processDataLine( text, columns, 1, null );
    assertNotNull(entry);
    assertTrue(entry.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.GAS_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.WATER_FLOW));
    assertFalse(entry.containsMeasurement(WellMeasurementType.OIL_FLOW));
    assertNotNull( entry.getWell() );
    assertEquals("osho2", entry.getWell().getName());
    assertEquals( parser.parse( "13/JUN/2011 05:25" ).getTime(), entry.from() );
    assertEquals( parser.parse( "13/JUN/2011 06:25" ).getTime(), entry.until() );
    
    assertEquals( 130.6, entry.getMeasurement( WellMeasurementType.CONDENSATE_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 285.2, entry.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 12.3, entry.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
}

@Test
public void testProcessingDataLineWithNullValue()
{
    List<CSVGasWellDataExtractor.FieldType> columns = new ArrayList<CSVGasWellDataExtractor.FieldType>();
    columns.add( CSVGasWellDataExtractor.FieldType.WELL_NAME );
    columns.add( CSVGasWellDataExtractor.FieldType.TIMESTAMP );
    columns.add( CSVGasWellDataExtractor.FieldType.INTERVAL_LENGTH );
    columns.add( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW );
    columns.add( CSVGasWellDataExtractor.FieldType.WATER_FLOW );
    columns.add( CSVGasWellDataExtractor.FieldType.GAS_FLOW );

    String text = "osho2,13/JUN/2011 05:25:00,1.0,130.6,------,12.3";
    GasWellDataEntry entry = CSVGasWellDataExtractor.processDataLine( text, columns, 1, null );
    assertNotNull(entry);
    assertTrue(entry.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.GAS_FLOW));
    assertFalse(entry.containsMeasurement(WellMeasurementType.WATER_FLOW));
    assertFalse(entry.containsMeasurement(WellMeasurementType.OIL_FLOW));
    assertNotNull( entry.getWell() );
    assertEquals("osho2", entry.getWell().getName());
    assertEquals( parser.parse( "13/JUN/2011 05:25" ).getTime(), entry.from() );
    assertEquals( parser.parse( "13/JUN/2011 06:25" ).getTime(), entry.until() );

    assertEquals( 130.6, entry.getMeasurement( WellMeasurementType.CONDENSATE_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 12.3, entry.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
}

@Test
public void testProcessingDataLineWithNullValueAndComment()
{
    List<CSVGasWellDataExtractor.FieldType> columns = new ArrayList<CSVGasWellDataExtractor.FieldType>();
    columns.add( CSVGasWellDataExtractor.FieldType.WELL_NAME );
    columns.add( CSVGasWellDataExtractor.FieldType.TIMESTAMP );
    columns.add( CSVGasWellDataExtractor.FieldType.INTERVAL_LENGTH );
    columns.add( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW );
    columns.add( CSVGasWellDataExtractor.FieldType.WATER_FLOW );
    columns.add( CSVGasWellDataExtractor.FieldType.GAS_FLOW );
    columns.add( CSVGasWellDataExtractor.FieldType.COMMENT );

    String text = "osho2,13/JUN/2011 05:25:00,1.0,130.6,------,12.3,\"dave's world\"";
    GasWellDataEntry entry = CSVGasWellDataExtractor.processDataLine( text, columns, 1, null );
    assertNotNull(entry);
    assertTrue(entry.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.GAS_FLOW));
    assertFalse(entry.containsMeasurement(WellMeasurementType.WATER_FLOW));
    assertFalse(entry.containsMeasurement(WellMeasurementType.OIL_FLOW));
    assertNotNull(entry.getWell());
    assertEquals("osho2", entry.getWell().getName());
    assertEquals( parser.parse( "13/JUN/2011 05:25" ).getTime(), entry.from() );
    assertEquals( parser.parse( "13/JUN/2011 06:25" ).getTime(), entry.until() );

    assertEquals( 130.6, entry.getMeasurement( WellMeasurementType.CONDENSATE_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 12.3, entry.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( "dave's world", entry.getComment() );
}

@Test
public void testProcessingDataLineWithNullValueAndNakedComment()
{
    List<CSVGasWellDataExtractor.FieldType> columns = new ArrayList<CSVGasWellDataExtractor.FieldType>();
    columns.add( CSVGasWellDataExtractor.FieldType.WELL_NAME );
    columns.add( CSVGasWellDataExtractor.FieldType.TIMESTAMP );
    columns.add( CSVGasWellDataExtractor.FieldType.INTERVAL_LENGTH );
    columns.add( CSVGasWellDataExtractor.FieldType.CONDENSATE_FLOW );
    columns.add( CSVGasWellDataExtractor.FieldType.WATER_FLOW );
    columns.add( CSVGasWellDataExtractor.FieldType.GAS_FLOW );
    columns.add( CSVGasWellDataExtractor.FieldType.COMMENT );

    String text = "osho2,13/JUN/2011 05:25:00,1.0,130.6,------,12.3,dave's world";
    GasWellDataEntry entry = CSVGasWellDataExtractor.processDataLine( text, columns, 1, null );
    assertNotNull(entry);
    assertTrue(entry.containsMeasurement(WellMeasurementType.CONDENSATE_FLOW));
    assertTrue(entry.containsMeasurement(WellMeasurementType.GAS_FLOW));
    assertFalse(entry.containsMeasurement(WellMeasurementType.WATER_FLOW));
    assertFalse(entry.containsMeasurement(WellMeasurementType.OIL_FLOW));
    assertNotNull( entry.getWell() );
    assertEquals("osho2", entry.getWell().getName());
    assertEquals( parser.parse( "13/JUN/2011 05:25" ).getTime(), entry.from() );
    assertEquals( parser.parse( "13/JUN/2011 06:25" ).getTime(), entry.until() );

    assertEquals( 130.6, entry.getMeasurement( WellMeasurementType.CONDENSATE_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 12.3, entry.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( "dave's world", entry.getComment() );
}

@Test
public void testExtractingDataFromTestCSV()
{
    CSVGasWellDataExtractor extractor = new CSVGasWellDataExtractor( testCSVReader );
    MultipleWellDataMap mwdm = extractor.extract();
    assertNotNull( mwdm );
    
    GasWell osho2 = new GasWell( "osho-2" );
    GasWell osho9b = new GasWell( "osho-9b" );
    
    Map<GasWell,GasWellDataSet> dataSetMap = mwdm.getDataMap();
    assertNotNull( dataSetMap );
    assertNotNull( dataSetMap.keySet() );
    assertEquals(2, dataSetMap.keySet().size() );
    assertTrue( dataSetMap.containsKey( osho2 ) ) ;
    assertTrue( dataSetMap.containsKey( osho9b ) ) ;
    
    GasWellDataSet dataSet = dataSetMap.get( osho2 );
    assertNotNull( dataSet );
    assertEquals( 5, dataSet.getData().size() );
    assertEquals( parser.parse( "13/JUN/2011 05:00" ).getTime(), dataSet.from() );
    assertEquals( parser.parse( "18/JUN/2011 05:00" ).getTime(), dataSet.until() );

    dataSet = dataSetMap.get( osho9b );
    assertNotNull(dataSet);
    assertEquals(6, dataSet.getData().size());
    assertEquals( parser.parse( "13/JUN/2011 05:00" ).getTime(), dataSet.from() );
    assertEquals( parser.parse( "19/JUN/2011 05:00" ).getTime(), dataSet.until() );
}

@Test
public void testExtractingDataFromSAA2CSV()
{
    CSVGasWellDataExtractor extractor = new CSVGasWellDataExtractor( saa2CSVReader );
    MultipleWellDataMap mwdm = extractor.extract();
    assertNotNull( mwdm );

    GasWell saa2 = new GasWell( "SAA-2" );

    Map<GasWell,GasWellDataSet> dataSetMap = mwdm.getDataMap();
    assertNotNull( dataSetMap );
    assertNotNull(dataSetMap.keySet());
    assertEquals(1, dataSetMap.keySet().size());
    assertTrue(dataSetMap.containsKey(saa2)) ;

    GasWellDataSet dataSet = dataSetMap.get( saa2 );
    assertNotNull( dataSet );
    assertEquals( 119, dataSet.getData().size() );
    assertEquals( parser.parse( "30/JUL/2009" ).getTime(), dataSet.from() );
    assertEquals( parser.parse( "26/NOV/2009" ).getTime(), dataSet.until() );
}


@Test
public void testExtractingDataFromReducedSAA10ST1CSVData()
{
    CSVGasWellDataExtractor extractor = new CSVGasWellDataExtractor( SAA10ST1CSVReader );
    MultipleWellDataMap mwdm = extractor.extract();
    assertNotNull( mwdm );

    GasWell saa2 = new GasWell( "SAA-10" );

    Map<GasWell,GasWellDataSet> dataSetMap = mwdm.getDataMap();
    assertNotNull( dataSetMap );
    assertNotNull(dataSetMap.keySet());
    assertEquals(1, dataSetMap.keySet().size());
    assertTrue(dataSetMap.containsKey(saa2)) ;

    GasWellDataSet dataSet = dataSetMap.get( saa2 );
    assertNotNull( dataSet );
    assertEquals( 60, dataSet.getData().size() );
    assertEquals( parser.parse( "16/JUN/2006" ).getTime(), dataSet.from() );
    assertEquals( parser.parse( "1/AUG/2011" ).getTime(), dataSet.until() );
}





} // end GasWellDataExtractorFactory
