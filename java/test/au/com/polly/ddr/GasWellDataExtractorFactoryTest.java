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

import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
GasWellDataExtractorFactory factory;


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
    
    well = new GasWell( "Dave's well" );
}

@Test(expected=NullPointerException.class)
public void testConstructingJavaSerializedExtractorWithNullInputStream()
{
    GasWellDataExtractor extractor = null;

    extractor = GasWellDataExtractorFactory.getInstance().getJavaSerializedObjectExtractor(null);
}

@Test(expected=ClassCastException.class)
public void testConstructingJavaSerializedExtractorWithWrongObjectType()
{
    GasWellDataExtractor extractor = null;

    extractor = GasWellDataExtractorFactory.getInstance().getJavaSerializedObjectExtractor( null );
}

@Test(expected=ClassCastException.class)
public void testConstructingJavaSerializedExtractor()
{
    GasWellDataExtractor extractor = null;

    extractor = GasWellDataExtractorFactory.getInstance().getJavaSerializedObjectExtractor( null );
}



@Test(expected=NullPointerException.class)
public void testConstructingExcelStandardizedExtractorWithNullWorkbook()
{
    
}

@Test(expected=IllegalArgumentException.class)
public void testConstructingExcelStandardizedExtractorWithDuplicatedWellIDs()
{
    
}

@Test(expected=IllegalArgumentException.class)
public void testConstructingExcelStandardizedExtractorWithEmptyIDArray()
{

}

@Test(expected=IllegalArgumentException.class)
public void testConstructingExcelStandardizedExtractorWithIDsSpecified()
{

}



} // end GasWellDataExtractorFactory
