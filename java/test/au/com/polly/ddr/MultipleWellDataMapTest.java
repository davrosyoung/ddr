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
import com.sun.tools.javac.resources.javac;
import junit.framework.JUnit4TestAdapter;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 21/11/11
 * Time: 4:28 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class MultipleWellDataMapTest
{
static Logger logger = Logger.getLogger( MultipleWellDataMapTest.class );
private final static double ACCEPTABLE_ERROR = 1E-5;
GasWellDataSet littleDataSet;
DateParser dateParser;
GasWellDataSet smallDataSet = null;



public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( MultipleWellDataMapTest.class );
}

@Before
public void setupData()
{
    dateParser = new AussieDateParser();

    TestGasWellDataSet.repopulate();
 
    smallDataSet = TestGasWellDataSet.getSmallDataSet();

    // populate some dummy data into the gas well data set....
    // --------------------------------------------------------
    this.littleDataSet = TestGasWellDataSet.getDummyDataSet();

    TestGasWellDataSet.getSAA2FragmentDataSet();
    TestGasWellDataSet.getNicksDataSet();
}

@Test
public void testConstructor()
{
    MultipleWellDataMap mwdm = new MultipleWellDataMap();
    assertNotNull( mwdm );
    assertNotNull(mwdm.getDataMap());
    assertEquals(0, mwdm.getDataMap().keySet().size());
}

@Test
public void testEqualsNullMap()
{
    MultipleWellDataMap mwdm = new MultipleWellDataMap();
    assertFalse(mwdm.equals(null));
}
@Test
public void testEqualsEmptyMaps()
{
    MultipleWellDataMap mwdm = new MultipleWellDataMap();
    MultipleWellDataMap other = new MultipleWellDataMap();
    assertEquals(mwdm, other);
    assertEquals( mwdm.hashCode(), other.hashCode() );
}

@Test
public void testEqualsWithWrongObjectClass()
{
    MultipleWellDataMap mwdm = new MultipleWellDataMap();
    Object other = new java.util.Date();
    assertFalse(mwdm.equals(other));
}

@Test
public void testEqualsWithUnequallyPopulatedObjects()
{
    MultipleWellDataMap mwdm = new MultipleWellDataMap();
    MultipleWellDataMap other = new MultipleWellDataMap();

    mwdm.addDataSet(TestGasWellDataSet.getDummyDataSet());
    mwdm.addDataSet( TestGasWellDataSet.getNicksDataSet() );
    
    other.addDataSet( TestGasWellDataSet.getSAA2FragmentDataSet() );
    other.addDataSet( TestGasWellDataSet.getDummyDataSet() );
    
    assertFalse( mwdm.equals( other ) );
    assertFalse( mwdm.hashCode() == other.hashCode() );
}

@Test
public void testEqualsWithEquallyPopulatedObjectsInDifferentOrder()
{
    MultipleWellDataMap mwdm = new MultipleWellDataMap();
    MultipleWellDataMap other = new MultipleWellDataMap();

    mwdm.addDataSet( TestGasWellDataSet.getDummyDataSet() );
    mwdm.addDataSet(TestGasWellDataSet.getNicksDataSet());

    other.addDataSet( TestGasWellDataSet.getNicksDataSet() );
    other.addDataSet( TestGasWellDataSet.getDummyDataSet() );

    assertEquals( mwdm, other );
    assertEquals( mwdm.hashCode(), other.hashCode() );
}

@Test
public void testEqualsWithDifferentNumberOfGasWellDataSets()
{
    MultipleWellDataMap mwdm = new MultipleWellDataMap();
    MultipleWellDataMap other = new MultipleWellDataMap();

    mwdm.addDataSet( TestGasWellDataSet.getDummyDataSet() );
    mwdm.addDataSet( TestGasWellDataSet.getNicksDataSet() );
    mwdm.addDataSet( TestGasWellDataSet.getSAA2FragmentDataSet() );

    other.addDataSet( TestGasWellDataSet.getNicksDataSet() );
    other.addDataSet( TestGasWellDataSet.getDummyDataSet() );

    assertFalse( mwdm.equals( other ) );
    assertFalse( mwdm.hashCode() == other.hashCode() );
}

@Test( expected = NullPointerException.class )
public void testAddingNullDataSet()
{
    MultipleWellDataMap mwdm = new MultipleWellDataMap();
    assertNotNull( mwdm );
    assertNotNull( mwdm.getDataMap() );
    assertEquals( 0, mwdm.getDataMap().keySet().size() );

    mwdm.addDataSet( (GasWellDataSet)null);
}

@Test
public void testAddingDataSets()
{
    MultipleWellDataMap mwdm = new MultipleWellDataMap();
    GasWellDataSet dataSet = null;
    assertNotNull( mwdm );
    assertNotNull( mwdm.getDataMap() );
    assertEquals( 0, mwdm.getDataMap().keySet().size() );
    
    long before = mwdm.hashCode();
    mwdm.addDataSet( TestGasWellDataSet.getSAA2FragmentDataSet() );
    long after = mwdm.hashCode();
    assertFalse( before == after );

    before = mwdm.hashCode();
    mwdm.addDataSet(TestGasWellDataSet.getNicksDataSet());
    after = mwdm.hashCode();
    assertFalse(before == after);
    
    assertNotNull( mwdm.getDataMap() );
    assertEquals( 2, mwdm.getDataMap().keySet().size() );
    assertTrue(mwdm.getDataMap().keySet().contains(new GasWell("SAA-2")));
    assertTrue(mwdm.getDataMap().keySet().contains(new GasWell("SAA-11")));
    
    dataSet = mwdm.getDataMap().get( new GasWell( "SAA-2" ) );
    assertNotNull(dataSet);
    assertEquals(dateParser.parse("30/JUL/2009").getTime(), dataSet.from());
    assertEquals(dateParser.parse("26/NOV/2009").getTime(), dataSet.until());

    dataSet = mwdm.getDataMap().get( new GasWell( "SAA-11" ) );
    assertNotNull( dataSet );
    assertEquals(dateParser.parse("16/JUL/2006 20:47:00").getTime(), dataSet.from());
    assertEquals( dateParser.parse( "27/SEP/2011 01:11:12").getTime(), dataSet.until() );
}

@Test
public void testCSVWriteAndRetrieve()
{
    MultipleWellDataMap mwdm = new MultipleWellDataMap();
    GasWellDataSet dataSet = null;
    assertNotNull( mwdm );
    assertNotNull( mwdm.getDataMap() );
    assertEquals( 0, mwdm.getDataMap().keySet().size() );

    mwdm.addDataSet( TestGasWellDataSet.getSAA2FragmentDataSet() );
    mwdm.addDataSet( TestGasWellDataSet.getNicksDataSet() );

    StringWriter sw = new StringWriter();
    PrintWriter writer = new PrintWriter( sw );
    mwdm.outputCSV(writer);
    
    Reader csvReader = new StringReader( sw.toString() );
    GasWellDataExtractorFactory factory = null;
    GasWellDataExtractor extractor = null;
    
    factory = GasWellDataExtractorFactory.getInstance();
    extractor = factory.getCSVGasWellDataExtractor(csvReader);
    MultipleWellDataMap extractedCSV = extractor.extract();
    assertNotNull( extractedCSV );
    assertEquals( 2, extractedCSV.getDataMap().size() );
    assertTrue( mwdm.equals( extractedCSV ) );
    assertEquals( mwdm, extractedCSV );
    assertEquals( mwdm.hashCode(), extractedCSV.hashCode() );
}

@Test
public void testGetWellList()
{
    MultipleWellDataMap mwdm = new MultipleWellDataMap();
    mwdm.addDataSet( TestGasWellDataSet.getSAA2FragmentDataSet() );
    mwdm.addDataSet( TestGasWellDataSet.getNicksDataSet() );
    mwdm.addDataSet( TestGasWellDataSet.getDummyDataSet() );
    mwdm.addDataSet( TestGasWellDataSet.getSmallDataSet() );

    List<GasWell> list = mwdm.getWellList();
    assertNotNull(list);
    assertEquals( 4, list.size() );

    assertEquals( "Dave's Well", list.get( 0 ).getName() );
    assertEquals( "Dummy", list.get( 1 ).getName() );
    assertEquals( "SAA-11", list.get( 2 ).getName() );
    assertEquals( "SAA-2", list.get( 3 ).getName() );

    mwdm = new MultipleWellDataMap();
    mwdm.addDataSet( TestGasWellDataSet.getSmallDataSet() );
    mwdm.addDataSet( TestGasWellDataSet.getDummyDataSet() );
    mwdm.addDataSet( TestGasWellDataSet.getNicksDataSet() );
    mwdm.addDataSet( TestGasWellDataSet.getSAA2FragmentDataSet() );

    list = mwdm.getWellList();
    assertNotNull( list );
    assertEquals( 4, list.size() );

    assertEquals( "Dave's Well", list.get( 0 ).getName() );
    assertEquals( "Dummy", list.get( 1 ).getName() );
    assertEquals( "SAA-11", list.get( 2 ).getName() );
    assertEquals( "SAA-2", list.get( 3 ).getName() );

}

@Test
public void testGetDataSetList()
{
    MultipleWellDataMap mwdm = new MultipleWellDataMap();
    mwdm.addDataSet( TestGasWellDataSet.getSAA2FragmentDataSet() );
    mwdm.addDataSet( TestGasWellDataSet.getNicksDataSet() );
    mwdm.addDataSet( TestGasWellDataSet.getDummyDataSet() );
    mwdm.addDataSet( TestGasWellDataSet.getSmallDataSet() );

    List<GasWellDataSet> list = mwdm.getDataSetList();
    assertNotNull( list );
    assertEquals(4, list.size());

    assertEquals( "Dave's Well", list.get( 0 ).getWellName() );
    assertEquals( "Dummy", list.get( 1 ).getWellName() );
    assertEquals( "SAA-11", list.get( 2 ).getWellName() );
    assertEquals( "SAA-2", list.get( 3 ).getWellName() );

    assertEquals( TestGasWellDataSet.getSmallDataSet(), list.get( 0 ) );
    assertEquals( TestGasWellDataSet.getDummyDataSet(), list.get( 1 ) );
    assertEquals( TestGasWellDataSet.getNicksDataSet(), list.get( 2 ) );
    assertEquals( TestGasWellDataSet.getSAA2FragmentDataSet(), list.get( 3 ) );

    mwdm = new MultipleWellDataMap();
    mwdm.addDataSet( TestGasWellDataSet.getSmallDataSet() );
    mwdm.addDataSet( TestGasWellDataSet.getDummyDataSet() );
    mwdm.addDataSet( TestGasWellDataSet.getNicksDataSet() );
    mwdm.addDataSet( TestGasWellDataSet.getSAA2FragmentDataSet() );

    list = mwdm.getDataSetList();
    assertNotNull( list );
    assertEquals( 4, list.size() );

    assertEquals( "Dave's Well", list.get( 0 ).getWellName() );
    assertEquals( "Dummy", list.get( 1 ).getWellName() );
    assertEquals( "SAA-11", list.get( 2 ).getWellName() );
    assertEquals( "SAA-2", list.get( 3 ).getWellName() );

    assertEquals( TestGasWellDataSet.getSmallDataSet(), list.get( 0 ) );
    assertEquals( TestGasWellDataSet.getDummyDataSet(), list.get( 1 ) );
    assertEquals( TestGasWellDataSet.getNicksDataSet(), list.get( 2 ) );
    assertEquals( TestGasWellDataSet.getSAA2FragmentDataSet(), list.get( 3 ) );
}

}
