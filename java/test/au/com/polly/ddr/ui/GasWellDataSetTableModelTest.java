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

package au.com.polly.ddr.ui;

import au.com.polly.ddr.GasWellDataEntry;
import au.com.polly.ddr.TestGasWellDataSet;
import au.com.polly.ddr.WellMeasurementType;
import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import junit.framework.JUnit4TestAdapter;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 21/11/11
 * Time: 4:28 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class GasWellDataSetTableModelTest
{
static Logger logger = Logger.getLogger( GasWellDataSetTableModelTest.class );
private final static double ACCEPTABLE_ERROR = 1E-5;


public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( GasWellDataSetTableModelTest.class );
}

DateParser parser;

@Before
public void setupData()
{
    parser = new AussieDateParser();
    TestGasWellDataSet.repopulate();

}

@Test( expected = NullPointerException.class )
public void testConstructingWithNullArg()
{
    new GasWellDataSetTableModel( null );
}

@Test
public void testConstructingWithSAA2FragmentData()
{
    GasWellDataSetTableModel model = new GasWellDataSetTableModel( TestGasWellDataSet.getSAA2FragmentDataSet() );
    assertEquals( 119, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );
    assertEquals(GasWellDataSetTableModel.ColumnType.START_TIMESTAMP, model.getColumnType(0) );
    assertEquals(GasWellDataSetTableModel.ColumnType.UNTIL_TIMESTAMP, model.getColumnType(1) );
    assertEquals(GasWellDataSetTableModel.ColumnType.INTERVAL_LENGTH, model.getColumnType(2) );
    assertEquals(GasWellDataSetTableModel.ColumnType.OIL_FLOW, model.getColumnType(3) );
    assertEquals(GasWellDataSetTableModel.ColumnType.GAS_FLOW, model.getColumnType(4) );
    assertEquals(GasWellDataSetTableModel.ColumnType.WATER_FLOW, model.getColumnType(5) );
    assertEquals(GasWellDataSetTableModel.ColumnType.COMMENT, model.getColumnType( 6 ) );

    assertFalse( model.containsColummnType( GasWellDataSetTableModel.ColumnType.CONDENSATE_FLOW ));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.START_TIMESTAMP ));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.UNTIL_TIMESTAMP ));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.INTERVAL_LENGTH));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.OIL_FLOW ));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.GAS_FLOW ));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.WATER_FLOW ));
    
}

@Test
public void testContainsColummnTypeWithSAA2FragmentData()
{
    GasWellDataSetTableModel model = new GasWellDataSetTableModel( TestGasWellDataSet.getSAA2FragmentDataSet() );
    assertEquals( 119, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );
    assertEquals(GasWellDataSetTableModel.ColumnType.START_TIMESTAMP, model.getColumnType(0) );
    assertEquals(GasWellDataSetTableModel.ColumnType.UNTIL_TIMESTAMP, model.getColumnType(1) );
    assertEquals(GasWellDataSetTableModel.ColumnType.INTERVAL_LENGTH, model.getColumnType(2) );
    assertEquals(GasWellDataSetTableModel.ColumnType.OIL_FLOW, model.getColumnType(3) );
    assertEquals(GasWellDataSetTableModel.ColumnType.GAS_FLOW, model.getColumnType(4) );
    assertEquals(GasWellDataSetTableModel.ColumnType.WATER_FLOW, model.getColumnType(5) );
    assertEquals(GasWellDataSetTableModel.ColumnType.COMMENT, model.getColumnType( 6 ) );

    assertFalse( model.containsColummnType( GasWellDataSetTableModel.ColumnType.CONDENSATE_FLOW ));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.START_TIMESTAMP ));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.UNTIL_TIMESTAMP ));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.INTERVAL_LENGTH));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.OIL_FLOW ));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.GAS_FLOW ));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.WATER_FLOW ));
 
}

@Test
public void testContainsGetValueWithSAA2FragmentData()
{
    GasWellDataSetTableModel model = new GasWellDataSetTableModel( TestGasWellDataSet.getSAA2FragmentDataSet() );
    assertEquals( 119, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );
    
    assertEquals( parser.parse( "30/JUL/2009").getTime(), model.getValueAt( 0, 0 ) );
    assertEquals( parser.parse( "31/JUL/2009").getTime(), model.getValueAt( 0, 1 ) );
    assertEquals( 24.0, (Double)model.getValueAt( 0, 2 ), ACCEPTABLE_ERROR );
    assertEquals( 286.51, (Double)model.getValueAt( 0, 3 ), ACCEPTABLE_ERROR );
    assertEquals( 0.19, (Double)model.getValueAt( 0, 4 ), ACCEPTABLE_ERROR );
    assertEquals( 951.64, (Double)model.getValueAt( 0, 5 ), ACCEPTABLE_ERROR );
    assertEquals( null, model.getValueAt( 0, 6 ) );

    assertEquals( parser.parse( "25/NOV/2009").getTime(), model.getValueAt( 118, 0 ) );
    assertEquals( parser.parse( "26/NOV/2009").getTime(), model.getValueAt( 118, 1 ) );
    assertEquals( 24.0,     (Double)model.getValueAt( 118, 2 ), ACCEPTABLE_ERROR );
    assertEquals( 2367.34,   (Double)model.getValueAt( 118, 3 ), ACCEPTABLE_ERROR );
    assertEquals( 0.9,     (Double)model.getValueAt( 118, 4 ), ACCEPTABLE_ERROR );
    assertEquals( 5474.26,   (Double)model.getValueAt( 118, 5 ), ACCEPTABLE_ERROR );
    assertEquals( null, model.getValueAt( 118, 6 ) );
}

@Test
public void testGetColumnNameWithSAA2FragmentData()
{
    GasWellDataSetTableModel model = new GasWellDataSetTableModel( TestGasWellDataSet.getSAA2FragmentDataSet() );
    assertEquals( 119, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );

    assertEquals( "Start date", model.getColumnName( 0 ) );
    assertEquals( "End date", model.getColumnName( 1 ) );
    assertEquals( "Interval length", model.getColumnName( 2 ) );
    assertEquals( "Oil Flow Rate", model.getColumnName( 3 ) );
    assertEquals( "Gas Flow Rate", model.getColumnName( 4 ) );
    assertEquals( "Water Flow Rate", model.getColumnName( 5 ) );
    assertEquals( "Comment", model.getColumnName( 6 ) );
    
    String columnNames[] = model.getColumnNames();
    assertEquals( "Start date", columnNames[ 0 ] );
    assertEquals( "End date", columnNames[ 1 ] );
    assertEquals( "Interval length", columnNames[ 2 ] );
    assertEquals( "Oil Flow Rate", columnNames[ 3 ] );
    assertEquals( "Gas Flow Rate", columnNames[ 4 ] );
    assertEquals( "Water Flow Rate", columnNames[ 5 ] );
    assertEquals( "Comment", columnNames[ 6 ] );

}


@Test
public void testGetColumnIndexWithSAA2FragmentData()
{
    GasWellDataSetTableModel model = new GasWellDataSetTableModel( TestGasWellDataSet.getSAA2FragmentDataSet() );
    assertEquals( 119, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );
    
    assertEquals( 0, model.getColumnIndex( GasWellDataSetTableModel.ColumnType.START_TIMESTAMP ) );
    assertEquals( 1, model.getColumnIndex( GasWellDataSetTableModel.ColumnType.UNTIL_TIMESTAMP ) );
    assertEquals( 2, model.getColumnIndex( GasWellDataSetTableModel.ColumnType.INTERVAL_LENGTH ) );
    assertEquals( 3, model.getColumnIndex( GasWellDataSetTableModel.ColumnType.OIL_FLOW ) );
    assertEquals( 4, model.getColumnIndex( GasWellDataSetTableModel.ColumnType.GAS_FLOW ) );
    assertEquals( 5, model.getColumnIndex( GasWellDataSetTableModel.ColumnType.WATER_FLOW ) );
    assertEquals( 6, model.getColumnIndex( GasWellDataSetTableModel.ColumnType.COMMENT ) );
    assertEquals( -1, model.getColumnIndex( GasWellDataSetTableModel.ColumnType.CONDENSATE_FLOW ) );
}



@Test
public void testSetValueWithSAA2FragmentData()
{
    GasWellDataSetTableModel model = new GasWellDataSetTableModel( TestGasWellDataSet.getSAA2FragmentDataSet() );
    assertEquals( 119, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );
    
    assertEquals( parser.parse( "30/JUL/2009").getTime(), model.getValueAt( 0, 0 ) );
    assertEquals( parser.parse( "31/JUL/2009").getTime(), model.getValueAt( 0, 1 ) );
    assertEquals( 24.0, (Double)model.getValueAt( 0, 2 ), ACCEPTABLE_ERROR );
    assertEquals( 286.51, (Double)model.getValueAt( 0, 3 ), ACCEPTABLE_ERROR );
    assertEquals( 0.19, (Double)model.getValueAt( 0, 4 ), ACCEPTABLE_ERROR );
    assertEquals( 951.64, (Double)model.getValueAt( 0, 5 ), ACCEPTABLE_ERROR );
    assertEquals( null, model.getValueAt( 0, 6 ) );
    
    model.setValueAt( parser.parse( "30/JUL/2009 12:00" ).getTime(), 0, 0 );
    model.setValueAt( 18.0, 0, 2 );
    model.setValueAt( 287.51, 0, 3 );
    model.setValueAt( 1.19, 0, 4 );
    model.setValueAt( 952.64, 0, 5 );
    model.setValueAt( "You betcha!?!", 0, 6 );

    assertEquals( parser.parse( "30/JUL/2009 12:00").getTime(), model.getValueAt( 0, 0 ) );
    assertEquals( parser.parse( "31/JUL/2009 06:00").getTime(), model.getValueAt( 0, 1 ) );
    assertEquals( 18.0, (Double)model.getValueAt( 0, 2 ), ACCEPTABLE_ERROR );
    assertEquals( 287.51, (Double)model.getValueAt( 0, 3 ), ACCEPTABLE_ERROR );
    assertEquals( 1.19, (Double)model.getValueAt( 0, 4 ), ACCEPTABLE_ERROR );
    assertEquals( 952.64, (Double)model.getValueAt( 0, 5 ), ACCEPTABLE_ERROR );
    assertEquals( "You betcha!?!", model.getValueAt( 0, 6 ) );
    
    model.setValueAt( parser.parse( "31/JUL/2009 09:00" ).getTime(), 0, 1 );
    assertEquals( parser.parse( "30/JUL/2009 12:00").getTime(), model.getValueAt( 0, 0 ) );
    assertEquals( parser.parse( "31/JUL/2009 09:00").getTime(), model.getValueAt( 0, 1 ) );
    assertEquals( 21.0, (Double)model.getValueAt( 0, 2 ), ACCEPTABLE_ERROR );

    model.setValueAt( parser.parse( "30/JUL/2009" ).getTime(), 0, 0 );
    model.setValueAt( parser.parse( "31/JUL/2009" ).getTime(), 0, 1 );
    model.setValueAt( 24.0, 0, 2 );
    model.setValueAt( 286.51, 0, 3 );
    model.setValueAt( 0.19, 0, 4 );
    model.setValueAt( 951.64, 0, 5 );
    model.setValueAt( "What the!?!", 0, 6 );

    assertEquals( parser.parse( "30/JUL/2009").getTime(), model.getValueAt( 0, 0 ) );
    assertEquals( parser.parse( "31/JUL/2009").getTime(), model.getValueAt( 0, 1 ) );
    assertEquals( 24.0, (Double)model.getValueAt( 0, 2 ), ACCEPTABLE_ERROR );
    assertEquals( 286.51, (Double)model.getValueAt( 0, 3 ), ACCEPTABLE_ERROR );
    assertEquals( 0.19, (Double)model.getValueAt( 0, 4 ), ACCEPTABLE_ERROR );
    assertEquals( 951.64, (Double)model.getValueAt( 0, 5 ), ACCEPTABLE_ERROR );
    assertEquals( "What the!?!", model.getValueAt( 0, 6 ) );
}


@Test
public void testIsCellEditableWithSAA2FragmentData()
{
    GasWellDataSetTableModel model = new GasWellDataSetTableModel( TestGasWellDataSet.getSAA2FragmentDataSet() );
    assertEquals( 119, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );
    assertEquals(GasWellDataSetTableModel.ColumnType.START_TIMESTAMP, model.getColumnType( 0 ) );
    assertEquals(GasWellDataSetTableModel.ColumnType.UNTIL_TIMESTAMP, model.getColumnType( 1 ) );
    assertEquals(GasWellDataSetTableModel.ColumnType.INTERVAL_LENGTH, model.getColumnType( 2 ) );
    assertEquals(GasWellDataSetTableModel.ColumnType.OIL_FLOW, model.getColumnType( 3 ) );
    assertEquals(GasWellDataSetTableModel.ColumnType.GAS_FLOW, model.getColumnType( 4 ) );
    assertEquals(GasWellDataSetTableModel.ColumnType.WATER_FLOW, model.getColumnType( 5 ) );
    assertEquals(GasWellDataSetTableModel.ColumnType.COMMENT, model.getColumnType( 6 ) );
    
    assertTrue( model.isCellEditable( 0, 0 ) );
    assertTrue( model.isCellEditable( 0, 1 ) );
    assertTrue( model.isCellEditable( 0, 2 ) );
    assertFalse( model.isCellEditable( 0, 3 ) );
    assertFalse( model.isCellEditable( 0, 4 ) );
    assertFalse( model.isCellEditable( 0, 5 ) );
    assertFalse( model.isCellEditable( 0, 9 ) );


    assertTrue( model.isCellEditable( 118, 0 ) );
    assertTrue( model.isCellEditable( 118, 1 ) );
    assertTrue( model.isCellEditable( 118, 2 ) );
    assertFalse( model.isCellEditable( 118, 3 ) );
    assertFalse( model.isCellEditable( 118, 4 ) );
    assertFalse( model.isCellEditable( 118, 5 ) );
    assertFalse( model.isCellEditable( 118, 9 ) );

    assertFalse( model.isCellEditable( 119, 0 ) );
    assertFalse( model.isCellEditable( 119, 1 ) );
    assertFalse( model.isCellEditable( 119, 2 ) );
    assertFalse( model.isCellEditable( 119, 3 ) );
    assertFalse( model.isCellEditable( 119, 4 ) );
    assertFalse( model.isCellEditable( 119, 5 ) );
    assertFalse( model.isCellEditable( 119, 6 ) );
    assertFalse( model.isCellEditable( 119, 7 ) );
    
    assertFalse( model.isCellEditable( -1, -1 ) );
    assertFalse( model.isCellEditable( -1, 0 ) );
    assertFalse( model.isCellEditable( -1, 1 ) );
    assertFalse( model.isCellEditable( -1, 2 ) );
    assertFalse( model.isCellEditable( -1, 3 ) );
    assertFalse( model.isCellEditable( -1, 4 ) );
    assertFalse( model.isCellEditable( -1, 5 ) );
    assertFalse( model.isCellEditable( -1, 6 ) );
    assertFalse( model.isCellEditable( -1, 7 ) );
}


@Test
public void testConstructingWithBY11Data()
{
    GasWellDataSetTableModel model = new GasWellDataSetTableModel( TestGasWellDataSet.getBY11DataSet() );
    assertEquals( 595, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );
    assertEquals(GasWellDataSetTableModel.ColumnType.START_TIMESTAMP, model.getColumnType(0) );
    assertEquals(GasWellDataSetTableModel.ColumnType.UNTIL_TIMESTAMP, model.getColumnType(1) );
    assertEquals(GasWellDataSetTableModel.ColumnType.INTERVAL_LENGTH, model.getColumnType(2) );
    assertEquals(GasWellDataSetTableModel.ColumnType.CONDENSATE_FLOW, model.getColumnType(3) );
    assertEquals(GasWellDataSetTableModel.ColumnType.GAS_FLOW, model.getColumnType(4) );
    assertEquals(GasWellDataSetTableModel.ColumnType.WATER_FLOW, model.getColumnType(5) );
    assertEquals(GasWellDataSetTableModel.ColumnType.COMMENT, model.getColumnType( 6 ) );

    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.CONDENSATE_FLOW ));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.START_TIMESTAMP ));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.UNTIL_TIMESTAMP ));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.INTERVAL_LENGTH));
    assertFalse( model.containsColummnType( GasWellDataSetTableModel.ColumnType.OIL_FLOW ));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.GAS_FLOW ));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.WATER_FLOW ));

}

@Test
public void testContainsColummnTypeWithBY11Data()
{
    GasWellDataSetTableModel model = new GasWellDataSetTableModel( TestGasWellDataSet.getBY11DataSet() );
    assertEquals( 595, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );
    assertEquals(GasWellDataSetTableModel.ColumnType.START_TIMESTAMP, model.getColumnType(0) );
    assertEquals(GasWellDataSetTableModel.ColumnType.UNTIL_TIMESTAMP, model.getColumnType(1) );
    assertEquals(GasWellDataSetTableModel.ColumnType.INTERVAL_LENGTH, model.getColumnType(2) );
    assertEquals(GasWellDataSetTableModel.ColumnType.CONDENSATE_FLOW, model.getColumnType(3) );
    assertEquals(GasWellDataSetTableModel.ColumnType.GAS_FLOW, model.getColumnType(4) );
    assertEquals(GasWellDataSetTableModel.ColumnType.WATER_FLOW, model.getColumnType(5) );
    assertEquals(GasWellDataSetTableModel.ColumnType.COMMENT, model.getColumnType( 6 ) );

    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.CONDENSATE_FLOW ));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.START_TIMESTAMP ));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.UNTIL_TIMESTAMP ));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.INTERVAL_LENGTH));
    assertFalse( model.containsColummnType( GasWellDataSetTableModel.ColumnType.OIL_FLOW ));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.GAS_FLOW ));
    assertTrue( model.containsColummnType( GasWellDataSetTableModel.ColumnType.WATER_FLOW ));

}

@Test
public void testContainsGetValueWithBY11Data()
{
    GasWellDataSetTableModel model = new GasWellDataSetTableModel( TestGasWellDataSet.getBY11DataSet() );
    assertEquals( 595, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );

    assertEquals( parser.parse( "27/OCT/2009").getTime(), model.getValueAt( 0, 0 ) );
    assertEquals( parser.parse( "28/OCT/2009").getTime(), model.getValueAt( 0, 1 ) );
    assertEquals( 24.0, (Double)model.getValueAt( 0, 2 ), ACCEPTABLE_ERROR );

    assertEquals( 22.38, (Double)model.getValueAt( 0, 3 ), ACCEPTABLE_ERROR );
    assertEquals( 7.28, (Double)model.getValueAt( 0, 4 ), ACCEPTABLE_ERROR );
    assertEquals( 79.07, (Double)model.getValueAt( 0, 5 ), ACCEPTABLE_ERROR );
    assertEquals( null, model.getValueAt( 0, 6 ) );

    assertEquals( parser.parse( "13/JUN/2011").getTime(), model.getValueAt( 594, 0 ) );
    assertEquals( parser.parse( "14/JUN/2011").getTime(), model.getValueAt( 594, 1 ) );
    assertEquals( 24.0,     (Double)model.getValueAt( 594, 2 ), ACCEPTABLE_ERROR );

    // 25.32,   5.33,  49.92
    assertEquals( 25.32,   (Double)model.getValueAt( 594, 3 ), ACCEPTABLE_ERROR );
    assertEquals( 5.33,     (Double)model.getValueAt( 594, 4 ), ACCEPTABLE_ERROR );
    assertEquals( 49.92,   (Double)model.getValueAt( 594, 5 ), ACCEPTABLE_ERROR );
    assertEquals( null, model.getValueAt( 594, 6 ) );
}

@Test
public void testGetColumnNameWithBY11Data()
{
    GasWellDataSetTableModel model = new GasWellDataSetTableModel( TestGasWellDataSet.getBY11DataSet() );
    assertEquals( 595, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );

    assertEquals( "Start date", model.getColumnName( 0 ) );
    assertEquals( "End date", model.getColumnName( 1 ) );
    assertEquals( "Interval length", model.getColumnName( 2 ) );
    assertEquals( "Cond. Flow Rate", model.getColumnName( 3 ) );
    assertEquals( "Gas Flow Rate", model.getColumnName( 4 ) );
    assertEquals( "Water Flow Rate", model.getColumnName( 5 ) );
    assertEquals( "Comment", model.getColumnName( 6 ) );

    String columnNames[] = model.getColumnNames();
    assertEquals( "Start date", columnNames[ 0 ] );
    assertEquals( "End date", columnNames[ 1 ] );
    assertEquals( "Interval length", columnNames[ 2 ] );
    assertEquals( "Cond. Flow Rate", columnNames[ 3 ] );
    assertEquals( "Gas Flow Rate", columnNames[ 4 ] );
    assertEquals( "Water Flow Rate", columnNames[ 5 ] );
    assertEquals( "Comment", columnNames[ 6 ] );

}


@Test
public void testGetColumnIndexWithBY11Data()
{
    GasWellDataSetTableModel model = new GasWellDataSetTableModel( TestGasWellDataSet.getBY11DataSet() );
    assertEquals( 595, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );

    assertEquals( 0, model.getColumnIndex( GasWellDataSetTableModel.ColumnType.START_TIMESTAMP ) );
    assertEquals( 1, model.getColumnIndex( GasWellDataSetTableModel.ColumnType.UNTIL_TIMESTAMP ) );
    assertEquals( 2, model.getColumnIndex( GasWellDataSetTableModel.ColumnType.INTERVAL_LENGTH ) );
    assertEquals( -1, model.getColumnIndex( GasWellDataSetTableModel.ColumnType.OIL_FLOW ) );
    assertEquals( 4, model.getColumnIndex( GasWellDataSetTableModel.ColumnType.GAS_FLOW ) );
    assertEquals( 5, model.getColumnIndex( GasWellDataSetTableModel.ColumnType.WATER_FLOW ) );
    assertEquals( 6, model.getColumnIndex( GasWellDataSetTableModel.ColumnType.COMMENT ) );
    assertEquals( 3, model.getColumnIndex( GasWellDataSetTableModel.ColumnType.CONDENSATE_FLOW ) );
}



@Test
public void testSetValueWithBY11Data()
{
    GasWellDataSetTableModel model = new GasWellDataSetTableModel( TestGasWellDataSet.getBY11DataSet() );
    assertEquals( 595, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );

    assertEquals( parser.parse( "27/OCT/2009").getTime(), model.getValueAt( 0, 0 ) );
    assertEquals( parser.parse( "28/OCT/2009").getTime(), model.getValueAt( 0, 1 ) );
    assertEquals( 24.0, (Double)model.getValueAt( 0, 2 ), ACCEPTABLE_ERROR );
    assertEquals( 22.38, (Double)model.getValueAt( 0, 3 ), ACCEPTABLE_ERROR );
    assertEquals( 7.28, (Double)model.getValueAt( 0, 4 ), ACCEPTABLE_ERROR );
    assertEquals( 79.07, (Double)model.getValueAt( 0, 5 ), ACCEPTABLE_ERROR );
    assertEquals( null, model.getValueAt( 0, 6 ) );


    model.setValueAt( parser.parse( "27/OCT/2009 12:00" ).getTime(), 0, 0 );
    model.setValueAt( parser.parse( "28/OCT/2009 09:00" ).getTime(), 0, 1 );
    model.setValueAt( 23.38, 0, 3 );
    model.setValueAt( 8.28, 0, 4 );
    model.setValueAt( 80.07, 0, 5 );
    model.setValueAt( "You betcha!?!", 0, 6 );

    assertEquals( parser.parse( "27/OCT/2009 12:00").getTime(), model.getValueAt( 0, 0 ) );
    assertEquals( parser.parse( "28/OCT/2009 09:00").getTime(), model.getValueAt( 0, 1 ) );
    assertEquals( 21.0, (Double)model.getValueAt( 0, 2 ), ACCEPTABLE_ERROR );

    assertEquals( 23.38, (Double)model.getValueAt( 0, 3 ), ACCEPTABLE_ERROR );
    assertEquals( 8.28, (Double)model.getValueAt( 0, 4 ), ACCEPTABLE_ERROR );
    assertEquals( 80.07, (Double)model.getValueAt( 0, 5 ), ACCEPTABLE_ERROR );
    assertEquals( "You betcha!?!", model.getValueAt( 0, 6 ) );

    model.setValueAt( parser.parse( "27/OCT/2009" ).getTime(), 0, 0 );
    model.setValueAt( parser.parse( "28/OCT/2009" ).getTime(), 0, 1 );
    model.setValueAt( 24.0, 0, 2 );
    model.setValueAt( 22.38, 0, 3 );
    model.setValueAt( 7.28, 0, 4 );
    model.setValueAt( 79.07, 0, 5 );
    model.setValueAt( "What the!?!", 0, 6 );

    assertEquals( parser.parse( "27/OCT/2009").getTime(), model.getValueAt( 0, 0 ) );
    assertEquals( parser.parse( "28/OCT/2009").getTime(), model.getValueAt( 0, 1 ) );
    assertEquals( 24.0, (Double)model.getValueAt( 0, 2 ), ACCEPTABLE_ERROR );
    assertEquals( 22.38, (Double)model.getValueAt( 0, 3 ), ACCEPTABLE_ERROR );
    assertEquals( 7.28, (Double)model.getValueAt( 0, 4 ), ACCEPTABLE_ERROR );
    assertEquals( 79.07, (Double)model.getValueAt( 0, 5 ), ACCEPTABLE_ERROR );
    assertEquals( "What the!?!", model.getValueAt( 0, 6 ) );
}


@Test
public void testIsCellEditableWithBY11Data()
{
    GasWellDataSetTableModel model = new GasWellDataSetTableModel( TestGasWellDataSet.getBY11DataSet() );
    assertEquals( 595, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );
    assertEquals(GasWellDataSetTableModel.ColumnType.START_TIMESTAMP, model.getColumnType(0) );
    assertEquals(GasWellDataSetTableModel.ColumnType.UNTIL_TIMESTAMP, model.getColumnType(1) );
    assertEquals(GasWellDataSetTableModel.ColumnType.INTERVAL_LENGTH, model.getColumnType(2) );
    assertEquals(GasWellDataSetTableModel.ColumnType.CONDENSATE_FLOW, model.getColumnType(3) );
    assertEquals(GasWellDataSetTableModel.ColumnType.GAS_FLOW, model.getColumnType(4) );
    assertEquals(GasWellDataSetTableModel.ColumnType.WATER_FLOW, model.getColumnType(5) );
    assertEquals(GasWellDataSetTableModel.ColumnType.COMMENT, model.getColumnType( 6 ) );

    assertTrue( model.isCellEditable( 0, 0 ) );
    assertTrue( model.isCellEditable( 0, 1 ) );
    assertTrue( model.isCellEditable( 0, 2 ) );
    assertFalse( model.isCellEditable( 0, 3 ) );
    assertFalse( model.isCellEditable( 0, 4 ) );
    assertFalse( model.isCellEditable( 0, 5 ) );
    assertFalse( model.isCellEditable( 0, 9 ) );

    assertTrue( model.isCellEditable( 594, 0 ) );
    assertTrue( model.isCellEditable( 594, 1 ) );
    assertTrue( model.isCellEditable( 594, 2 ) );
    assertFalse( model.isCellEditable( 594, 3 ) );
    assertFalse( model.isCellEditable( 594, 4 ) );
    assertFalse( model.isCellEditable( 594, 5 ) );
    assertFalse( model.isCellEditable( 594, 9 ) );

    assertFalse( model.isCellEditable( 595, 0 ) );
    assertFalse( model.isCellEditable( 595, 1 ) );
    assertFalse( model.isCellEditable( 595, 2 ) );
    assertFalse( model.isCellEditable( 595, 3 ) );
    assertFalse( model.isCellEditable( 595, 4 ) );
    assertFalse( model.isCellEditable( 595, 5 ) );
    assertFalse( model.isCellEditable( 595, 6 ) );
    assertFalse( model.isCellEditable( 595, 7 ) );


    assertFalse(model.isCellEditable(-1, -1));
    assertFalse( model.isCellEditable( -1, 0 ) );
    assertFalse( model.isCellEditable( -1, 1 ) );
    assertFalse( model.isCellEditable( -1, 2 ) );
    assertFalse( model.isCellEditable( -1, 3 ) );
    assertFalse( model.isCellEditable( -1, 4 ) );
    assertFalse( model.isCellEditable( -1, 5 ) );
    assertFalse( model.isCellEditable( -1, 6 ) );
    assertFalse( model.isCellEditable( -1, 7 ) );
}

@Test
public void testRemovingFirstTwoRowsFromReductionTestData()
{
    GasWellDataSetTableModel model = new GasWellDataSetTableModel( TestGasWellDataSet.getReductionTestDataSet() );
    assertEquals( 26, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );
    assertEquals( GasWellDataSetTableModel.ColumnType.START_TIMESTAMP, model.getColumnType(0) );
    assertEquals( GasWellDataSetTableModel.ColumnType.UNTIL_TIMESTAMP, model.getColumnType(1) );
    assertEquals( GasWellDataSetTableModel.ColumnType.INTERVAL_LENGTH, model.getColumnType(2) );
    assertEquals( GasWellDataSetTableModel.ColumnType.OIL_FLOW, model.getColumnType(3) );
    assertEquals( GasWellDataSetTableModel.ColumnType.GAS_FLOW, model.getColumnType(4) );
    assertEquals( GasWellDataSetTableModel.ColumnType.WATER_FLOW, model.getColumnType(5) );
    assertEquals( GasWellDataSetTableModel.ColumnType.COMMENT, model.getColumnType( 6 ) );
    
    GasWellDataEntry firstRow;
    GasWellDataEntry secondRow;
    GasWellDataEntry thirdRow;
    GasWellDataEntry fourthRow;

    firstRow = model.actualData.getData().get( 0 );
    secondRow = model.actualData.getData().get( 1 );
    thirdRow = model.actualData.getData().get( 2 );
    fourthRow = model.actualData.getData().get( 3 );

    assertEquals( parser.parse( "01/JUN/2012").getTime(), firstRow.from() );
    assertEquals( parser.parse( "02/JUN/2012").getTime(), firstRow.until() );
    assertEquals( 10.0, firstRow.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, firstRow.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 8.0, firstRow.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
    
    assertEquals( parser.parse( "02/JUN/2012").getTime(), secondRow.from() );
    assertEquals( parser.parse( "03/JUN/2012").getTime(), secondRow.until() );
    assertEquals( 11.0, secondRow.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, secondRow.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 9.0, secondRow.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
    
    assertEquals( parser.parse( "03/JUN/2012").getTime(), thirdRow.from() );
    assertEquals( parser.parse( "04/JUN/2012").getTime(), thirdRow.until() );
    assertEquals( 9.5, thirdRow.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, thirdRow.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 8.2, thirdRow.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

    assertEquals( parser.parse( "04/JUN/2012").getTime(), fourthRow.from() );
    assertEquals( parser.parse( "05/JUN/2012").getTime(), fourthRow.until() );
    assertEquals( 5.0, fourthRow.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, fourthRow.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 6.0, fourthRow.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

    
    model.deleteRow( 1 );

    assertEquals( 25, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );
    assertEquals( GasWellDataSetTableModel.ColumnType.START_TIMESTAMP, model.getColumnType(0) );
    assertEquals( GasWellDataSetTableModel.ColumnType.UNTIL_TIMESTAMP, model.getColumnType(1) );
    assertEquals( GasWellDataSetTableModel.ColumnType.INTERVAL_LENGTH, model.getColumnType(2) );
    assertEquals( GasWellDataSetTableModel.ColumnType.OIL_FLOW, model.getColumnType(3) );
    assertEquals( GasWellDataSetTableModel.ColumnType.GAS_FLOW, model.getColumnType(4) );
    assertEquals( GasWellDataSetTableModel.ColumnType.WATER_FLOW, model.getColumnType(5) );
    assertEquals( GasWellDataSetTableModel.ColumnType.COMMENT, model.getColumnType( 6 ) );

    firstRow = model.actualData.getData().get( 0 );
    secondRow = model.actualData.getData().get( 1 );
    thirdRow = model.actualData.getData().get( 2 );
    fourthRow = model.actualData.getData().get( 3 );

    assertEquals( parser.parse( "01/JUN/2012").getTime(), firstRow.from() );
    assertEquals( parser.parse( "03/JUN/2012").getTime(), firstRow.until() );
    assertEquals( 10.5, firstRow.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, firstRow.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 8.5, firstRow.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

    assertEquals( parser.parse( "03/JUN/2012").getTime(), secondRow.from() );
    assertEquals( parser.parse( "04/JUN/2012").getTime(), secondRow.until() );
    assertEquals( 9.5, secondRow.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, secondRow.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 8.2, secondRow.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

    assertEquals( parser.parse( "04/JUN/2012").getTime(), thirdRow.from() );
    assertEquals( parser.parse( "05/JUN/2012").getTime(), thirdRow.until() );
    assertEquals( 5.0, thirdRow.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, thirdRow.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 6.0, thirdRow.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
    
    assertEquals( parser.parse( "05/JUN/2012").getTime(), fourthRow.from() );
    assertEquals( parser.parse( "06/JUN/2012").getTime(), fourthRow.until() );
    assertEquals( 0.0, fourthRow.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, fourthRow.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 2.0, fourthRow.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );



    model.deleteRow( 1 );

    assertEquals( 24, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );
    assertEquals( GasWellDataSetTableModel.ColumnType.START_TIMESTAMP, model.getColumnType(0) );
    assertEquals( GasWellDataSetTableModel.ColumnType.UNTIL_TIMESTAMP, model.getColumnType(1) );
    assertEquals( GasWellDataSetTableModel.ColumnType.INTERVAL_LENGTH, model.getColumnType(2) );
    assertEquals( GasWellDataSetTableModel.ColumnType.OIL_FLOW, model.getColumnType(3) );
    assertEquals( GasWellDataSetTableModel.ColumnType.GAS_FLOW, model.getColumnType(4) );
    assertEquals( GasWellDataSetTableModel.ColumnType.WATER_FLOW, model.getColumnType(5) );
    assertEquals( GasWellDataSetTableModel.ColumnType.COMMENT, model.getColumnType( 6 ) );

    firstRow = model.actualData.getData().get( 0 );
    secondRow = model.actualData.getData().get( 1 );
    thirdRow = model.actualData.getData().get( 2 );
    fourthRow = model.actualData.getData().get( 3 );

    assertEquals( parser.parse( "01/JUN/2012").getTime(), firstRow.from() );
    assertEquals( parser.parse( "04/JUN/2012").getTime(), firstRow.until() );
    assertEquals( ( 10.0 + 11.0 + 9.5 ) / 3.0, firstRow.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, firstRow.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( ( 8.0 + 9.0 + 8.2 ) / 3.0, firstRow.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

    assertEquals( parser.parse( "04/JUN/2012").getTime(), secondRow.from() );
    assertEquals( parser.parse( "05/JUN/2012").getTime(), secondRow.until() );
    assertEquals( 5.0, secondRow.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, secondRow.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 6.0, secondRow.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

    assertEquals( parser.parse( "05/JUN/2012").getTime(), thirdRow.from() );
    assertEquals( parser.parse( "06/JUN/2012").getTime(), thirdRow.until() );
    assertEquals( 0.0, thirdRow.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, thirdRow.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 2.0, thirdRow.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

    assertEquals( parser.parse( "06/JUN/2012").getTime(), fourthRow.from() );
    assertEquals( parser.parse( "07/JUN/2012").getTime(), fourthRow.until() );
    assertEquals( 0.0, fourthRow.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, fourthRow.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, fourthRow.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

}


@Test
public void testRemovingLastTwoRowsFromReductionTestData()
{
    GasWellDataSetTableModel model = new GasWellDataSetTableModel( TestGasWellDataSet.getReductionTestDataSet() );
    assertEquals( 26, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );
    assertEquals( GasWellDataSetTableModel.ColumnType.START_TIMESTAMP, model.getColumnType(0) );
    assertEquals( GasWellDataSetTableModel.ColumnType.UNTIL_TIMESTAMP, model.getColumnType(1) );
    assertEquals( GasWellDataSetTableModel.ColumnType.INTERVAL_LENGTH, model.getColumnType(2) );
    assertEquals( GasWellDataSetTableModel.ColumnType.OIL_FLOW, model.getColumnType(3) );
    assertEquals( GasWellDataSetTableModel.ColumnType.GAS_FLOW, model.getColumnType(4) );
    assertEquals( GasWellDataSetTableModel.ColumnType.WATER_FLOW, model.getColumnType(5) );
    assertEquals( GasWellDataSetTableModel.ColumnType.COMMENT, model.getColumnType( 6 ) );

    GasWellDataEntry lastRow;
    GasWellDataEntry secondLastRow;
    GasWellDataEntry thirdLastRow;
    GasWellDataEntry fourthLastRow;
    
    lastRow = model.actualData.getData().get( 25 );
    secondLastRow = model.actualData.getData().get( 24 );
    thirdLastRow = model.actualData.getData().get( 23 );
    fourthLastRow = model.actualData.getData().get( 22 );
    
    assertEquals( parser.parse( "26/JUN/2012" ).getTime(), lastRow.from() );
    assertEquals( parser.parse( "27/JUN/2012" ).getTime(), lastRow.until() );
    assertEquals( 10.0, lastRow.getMeasurement( WellMeasurementType.OIL_FLOW ));
    assertEquals( 00.0, lastRow.getMeasurement( WellMeasurementType.GAS_FLOW ));
    assertEquals( 11.8, lastRow.getMeasurement( WellMeasurementType.WATER_FLOW ));
    
    assertEquals( parser.parse( "25/JUN/2012" ).getTime(), secondLastRow.from() );
    assertEquals( parser.parse( "26/JUN/2012" ).getTime(), secondLastRow.until() );
    assertEquals( 10.9, secondLastRow.getMeasurement( WellMeasurementType.OIL_FLOW ));
    assertEquals( 00.0, secondLastRow.getMeasurement( WellMeasurementType.GAS_FLOW ));
    assertEquals( 13.0, secondLastRow.getMeasurement( WellMeasurementType.WATER_FLOW ));
    
    assertEquals( parser.parse( "24/JUN/2012" ).getTime(), thirdLastRow.from() );
    assertEquals( parser.parse( "25/JUN/2012" ).getTime(), thirdLastRow.until() );
    assertEquals( 10.4, thirdLastRow.getMeasurement( WellMeasurementType.OIL_FLOW ));
    assertEquals( 00.0, thirdLastRow.getMeasurement( WellMeasurementType.GAS_FLOW ));
    assertEquals( 00.0, thirdLastRow.getMeasurement( WellMeasurementType.WATER_FLOW ));
    
    
    assertEquals( parser.parse( "23/JUN/2012" ).getTime(), fourthLastRow.from() );
    assertEquals( parser.parse( "24/JUN/2012" ).getTime(), fourthLastRow.until() );
    assertEquals( 11.0, fourthLastRow.getMeasurement( WellMeasurementType.OIL_FLOW ));
    assertEquals( 00.0, fourthLastRow.getMeasurement( WellMeasurementType.GAS_FLOW ));
    assertEquals( 00.0, fourthLastRow.getMeasurement( WellMeasurementType.WATER_FLOW ));

    model.deleteRow( 25 );

    assertEquals( 25, model.getRowCount() );

    lastRow = model.actualData.getData().get( 24 );
    secondLastRow = model.actualData.getData().get( 23 );
    thirdLastRow = model.actualData.getData().get( 22 );
    fourthLastRow = model.actualData.getData().get( 21 );

    assertEquals( parser.parse( "25/JUN/2012" ).getTime(), lastRow.from() );
    assertEquals( parser.parse( "27/JUN/2012" ).getTime(), lastRow.until() );
    assertEquals( ( 10.0 + 10.9 ) / 2.0, lastRow.getMeasurement( WellMeasurementType.OIL_FLOW ));
    assertEquals( 00.0, lastRow.getMeasurement( WellMeasurementType.GAS_FLOW ));
    assertEquals( ( 11.8 + 13.0 ) / 2.0, lastRow.getMeasurement( WellMeasurementType.WATER_FLOW ));

    assertEquals( parser.parse( "24/JUN/2012" ).getTime(), secondLastRow.from() );
    assertEquals( parser.parse( "25/JUN/2012" ).getTime(), secondLastRow.until() );
    assertEquals( 10.4, secondLastRow.getMeasurement( WellMeasurementType.OIL_FLOW ));
    assertEquals( 00.0, secondLastRow.getMeasurement( WellMeasurementType.GAS_FLOW ));
    assertEquals( 00.0, secondLastRow.getMeasurement( WellMeasurementType.WATER_FLOW ));

    assertEquals( parser.parse( "23/JUN/2012" ).getTime(), thirdLastRow.from() );
    assertEquals( parser.parse( "24/JUN/2012" ).getTime(), thirdLastRow.until() );
    assertEquals( 11.0, thirdLastRow.getMeasurement( WellMeasurementType.OIL_FLOW ));
    assertEquals( 00.0, thirdLastRow.getMeasurement( WellMeasurementType.GAS_FLOW ));
    assertEquals( 00.0, thirdLastRow.getMeasurement( WellMeasurementType.WATER_FLOW ));
    
    assertEquals( parser.parse( "22/JUN/2012" ).getTime(), fourthLastRow.from() );
    assertEquals( parser.parse( "23/JUN/2012" ).getTime(), fourthLastRow.until() );
    assertEquals( 12.6, fourthLastRow.getMeasurement( WellMeasurementType.OIL_FLOW ));
    assertEquals( 00.0, fourthLastRow.getMeasurement( WellMeasurementType.GAS_FLOW ));
    assertEquals( 00.0, fourthLastRow.getMeasurement( WellMeasurementType.WATER_FLOW ));
    
}

@Test
public void testAddingFirstRowIntoReductionTestData()
{
    GasWellDataSetTableModel model = new GasWellDataSetTableModel( TestGasWellDataSet.getReductionTestDataSet() );
    assertEquals( 26, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );
    assertEquals( GasWellDataSetTableModel.ColumnType.START_TIMESTAMP, model.getColumnType(0) );
    assertEquals( GasWellDataSetTableModel.ColumnType.UNTIL_TIMESTAMP, model.getColumnType(1) );
    assertEquals( GasWellDataSetTableModel.ColumnType.INTERVAL_LENGTH, model.getColumnType(2) );
    assertEquals( GasWellDataSetTableModel.ColumnType.OIL_FLOW, model.getColumnType(3) );
    assertEquals( GasWellDataSetTableModel.ColumnType.GAS_FLOW, model.getColumnType(4) );
    assertEquals( GasWellDataSetTableModel.ColumnType.WATER_FLOW, model.getColumnType(5) );
    assertEquals( GasWellDataSetTableModel.ColumnType.COMMENT, model.getColumnType( 6 ) );
    
    GasWellDataEntry firstRow;
    GasWellDataEntry secondRow;
    GasWellDataEntry thirdRow;
    GasWellDataEntry fourthRow;

    firstRow = model.actualData.getData().get( 0 );
    secondRow = model.actualData.getData().get( 1 );
    thirdRow = model.actualData.getData().get( 2 );
    fourthRow = model.actualData.getData().get( 3 );

    assertEquals( parser.parse( "01/JUN/2012").getTime(), firstRow.from() );
    assertEquals( parser.parse( "02/JUN/2012").getTime(), firstRow.until() );
    assertEquals( 10.0, firstRow.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, firstRow.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 8.0, firstRow.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
    
    assertEquals( parser.parse( "02/JUN/2012").getTime(), secondRow.from() );
    assertEquals( parser.parse( "03/JUN/2012").getTime(), secondRow.until() );
    assertEquals( 11.0, secondRow.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, secondRow.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 9.0, secondRow.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
    
    assertEquals( parser.parse( "03/JUN/2012").getTime(), thirdRow.from() );
    assertEquals( parser.parse( "04/JUN/2012").getTime(), thirdRow.until() );
    assertEquals( 9.5, thirdRow.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, thirdRow.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 8.2, thirdRow.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

    assertEquals( parser.parse( "04/JUN/2012").getTime(), fourthRow.from() );
    assertEquals( parser.parse( "05/JUN/2012").getTime(), fourthRow.until() );
    assertEquals( 5.0, fourthRow.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, fourthRow.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 6.0, fourthRow.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

    
    model.addRow( 0 );

    assertEquals( 27, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );
    assertEquals( GasWellDataSetTableModel.ColumnType.START_TIMESTAMP, model.getColumnType(0) );
    assertEquals( GasWellDataSetTableModel.ColumnType.UNTIL_TIMESTAMP, model.getColumnType(1) );
    assertEquals( GasWellDataSetTableModel.ColumnType.INTERVAL_LENGTH, model.getColumnType(2) );
    assertEquals( GasWellDataSetTableModel.ColumnType.OIL_FLOW, model.getColumnType(3) );
    assertEquals( GasWellDataSetTableModel.ColumnType.GAS_FLOW, model.getColumnType(4) );
    assertEquals( GasWellDataSetTableModel.ColumnType.WATER_FLOW, model.getColumnType(5) );
    assertEquals( GasWellDataSetTableModel.ColumnType.COMMENT, model.getColumnType( 6 ) );

    firstRow = model.actualData.getData().get( 0 );
    secondRow = model.actualData.getData().get( 1 );
    thirdRow = model.actualData.getData().get( 2 );
    fourthRow = model.actualData.getData().get( 3 );

    assertEquals( parser.parse( "01/JUN/2012 00:00").getTime(), firstRow.from() );
    assertEquals( parser.parse( "01/JUN/2012 12:00").getTime(), firstRow.until() );
    assertEquals( 10.0, firstRow.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, firstRow.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 8.0, firstRow.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

    assertEquals( parser.parse( "01/JUN/2012 12:00").getTime(), secondRow.from() );
    assertEquals( parser.parse( "02/JUN/2012 00:00").getTime(), secondRow.until() );
    assertEquals( 10.0, secondRow.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, secondRow.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 8.0, secondRow.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );

    assertEquals( parser.parse( "02/JUN/2012").getTime(), thirdRow.from() );
    assertEquals( parser.parse( "03/JUN/2012").getTime(), thirdRow.until() );
    assertEquals( 11.0, thirdRow.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, thirdRow.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 9.0, thirdRow.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
    
    assertEquals( parser.parse( "03/JUN/2012").getTime(), fourthRow.from() );
    assertEquals( parser.parse( "04/JUN/2012").getTime(), fourthRow.until() );
    assertEquals( 9.5, fourthRow.getMeasurement( WellMeasurementType.OIL_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 0.0, fourthRow.getMeasurement( WellMeasurementType.GAS_FLOW ), ACCEPTABLE_ERROR );
    assertEquals( 8.2, fourthRow.getMeasurement( WellMeasurementType.WATER_FLOW ), ACCEPTABLE_ERROR );
}

@Test
public void testAddingLastRowIntoReductionTestData()
{
    GasWellDataSetTableModel model = new GasWellDataSetTableModel( TestGasWellDataSet.getReductionTestDataSet() );
    assertEquals( 26, model.getRowCount() );
    assertEquals( 9, model.getColumnCount() );
    assertEquals( GasWellDataSetTableModel.ColumnType.START_TIMESTAMP, model.getColumnType(0) );
    assertEquals( GasWellDataSetTableModel.ColumnType.UNTIL_TIMESTAMP, model.getColumnType(1) );
    assertEquals( GasWellDataSetTableModel.ColumnType.INTERVAL_LENGTH, model.getColumnType(2) );
    assertEquals( GasWellDataSetTableModel.ColumnType.OIL_FLOW, model.getColumnType(3) );
    assertEquals( GasWellDataSetTableModel.ColumnType.GAS_FLOW, model.getColumnType(4) );
    assertEquals( GasWellDataSetTableModel.ColumnType.WATER_FLOW, model.getColumnType(5) );
    assertEquals( GasWellDataSetTableModel.ColumnType.COMMENT, model.getColumnType( 6 ) );

    GasWellDataEntry lastRow;
    GasWellDataEntry secondLastRow;
    GasWellDataEntry thirdLastRow;
    GasWellDataEntry fourthLastRow;

    lastRow = model.actualData.getData().get( 25 );
    secondLastRow = model.actualData.getData().get( 24 );
    thirdLastRow = model.actualData.getData().get( 23 );
    fourthLastRow = model.actualData.getData().get( 22 );

    assertEquals( parser.parse( "26/JUN/2012" ).getTime(), lastRow.from() );
    assertEquals( parser.parse( "27/JUN/2012" ).getTime(), lastRow.until() );
    assertEquals( 10.0, lastRow.getMeasurement( WellMeasurementType.OIL_FLOW ));
    assertEquals( 00.0, lastRow.getMeasurement( WellMeasurementType.GAS_FLOW ));
    assertEquals( 11.8, lastRow.getMeasurement( WellMeasurementType.WATER_FLOW ));

    assertEquals( parser.parse( "25/JUN/2012" ).getTime(), secondLastRow.from() );
    assertEquals( parser.parse( "26/JUN/2012" ).getTime(), secondLastRow.until() );
    assertEquals( 10.9, secondLastRow.getMeasurement( WellMeasurementType.OIL_FLOW ));
    assertEquals( 00.0, secondLastRow.getMeasurement( WellMeasurementType.GAS_FLOW ));
    assertEquals( 13.0, secondLastRow.getMeasurement( WellMeasurementType.WATER_FLOW ));

    assertEquals( parser.parse( "24/JUN/2012" ).getTime(), thirdLastRow.from() );
    assertEquals( parser.parse( "25/JUN/2012" ).getTime(), thirdLastRow.until() );
    assertEquals( 10.4, thirdLastRow.getMeasurement( WellMeasurementType.OIL_FLOW ));
    assertEquals( 00.0, thirdLastRow.getMeasurement( WellMeasurementType.GAS_FLOW ));
    assertEquals( 00.0, thirdLastRow.getMeasurement( WellMeasurementType.WATER_FLOW ));


    assertEquals( parser.parse( "23/JUN/2012" ).getTime(), fourthLastRow.from() );
    assertEquals( parser.parse( "24/JUN/2012" ).getTime(), fourthLastRow.until() );
    assertEquals( 11.0, fourthLastRow.getMeasurement( WellMeasurementType.OIL_FLOW ));
    assertEquals( 00.0, fourthLastRow.getMeasurement( WellMeasurementType.GAS_FLOW ));
    assertEquals( 00.0, fourthLastRow.getMeasurement( WellMeasurementType.WATER_FLOW ));
    
    model.addRow( 25 );

    assertEquals( 27, model.getRowCount() );


    lastRow = model.actualData.getData().get( 26 );
    secondLastRow = model.actualData.getData().get( 25 );
    thirdLastRow = model.actualData.getData().get( 24 );
    fourthLastRow = model.actualData.getData().get( 23 );

    assertEquals( parser.parse( "26/JUN/2012 12:00" ).getTime(), lastRow.from() );
    assertEquals( parser.parse( "27/JUN/2012" ).getTime(), lastRow.until() );
    assertEquals( 10.0, lastRow.getMeasurement( WellMeasurementType.OIL_FLOW ));
    assertEquals( 00.0, lastRow.getMeasurement( WellMeasurementType.GAS_FLOW ));
    assertEquals( 11.8, lastRow.getMeasurement( WellMeasurementType.WATER_FLOW ));

    assertEquals( parser.parse( "26/JUN/2012" ).getTime(), secondLastRow.from() );
    assertEquals( parser.parse( "26/JUN/2012 12:00" ).getTime(), secondLastRow.until() );
    assertEquals( 10.0, secondLastRow.getMeasurement( WellMeasurementType.OIL_FLOW ));
    assertEquals( 00.0, secondLastRow.getMeasurement( WellMeasurementType.GAS_FLOW ));
    assertEquals( 11.8, secondLastRow.getMeasurement( WellMeasurementType.WATER_FLOW ));

    assertEquals( parser.parse( "25/JUN/2012" ).getTime(), thirdLastRow.from() );
    assertEquals( parser.parse( "26/JUN/2012" ).getTime(), thirdLastRow.until() );
    assertEquals( 10.9, thirdLastRow.getMeasurement( WellMeasurementType.OIL_FLOW ));
    assertEquals( 00.0, thirdLastRow.getMeasurement( WellMeasurementType.GAS_FLOW ));
    assertEquals( 13.0, thirdLastRow.getMeasurement( WellMeasurementType.WATER_FLOW ));

    assertEquals( parser.parse( "24/JUN/2012" ).getTime(), fourthLastRow.from() );
    assertEquals( parser.parse( "25/JUN/2012" ).getTime(), fourthLastRow.until() );
    assertEquals( 10.4, fourthLastRow.getMeasurement( WellMeasurementType.OIL_FLOW ));
    assertEquals( 00.0, fourthLastRow.getMeasurement( WellMeasurementType.GAS_FLOW ));
    assertEquals( 00.0, fourthLastRow.getMeasurement( WellMeasurementType.WATER_FLOW ));
}

}
