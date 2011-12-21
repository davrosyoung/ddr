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
import com.sun.tools.javac.resources.javac;
import junit.framework.JUnit4TestAdapter;
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
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 23/11/11
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class GasWellTest
{


public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( GasWellTest.class );
}

@Test( expected=NullPointerException.class )
public void testNullConstructor()
{
    GasWell well = new GasWell( null );
}

@Test( expected=IllegalArgumentException.class )
public void testBlankConstructor()
{
    GasWell well = new GasWell( "" );
}

@Test
public void testEquality()
{
    GasWell alpha = new GasWell( "alpha" );
    GasWell beta = new GasWell( "beta" );
    GasWell alphaAgain = new GasWell( "alpha" );
    GasWell bigAlpha = new GasWell( "Alpha" );
    GasWell anotherBigAlpha = new GasWell( " Alpha" );

    assertNotNull( alpha );
    assertNotNull( beta );
    assertFalse( alpha.equals( beta ) );
    assertNotNull( alphaAgain );
    assertEquals( alpha, alphaAgain );
    assertNotNull( bigAlpha );
    assertNotNull( anotherBigAlpha );
    assertEquals( bigAlpha, anotherBigAlpha );
    assertFalse( alpha.equals( bigAlpha ) );
}

@Test
public void testEqualityAgainstADateObject()
{
    assertFalse( new GasWell("alpha").equals( new Date() ) );
}


@Test
public void testHashCode()
{
    GasWell alpha = new GasWell( "alpha" );
    GasWell beta = new GasWell( "beta" );
    GasWell alphaAgain = new GasWell( "alpha" );
    GasWell bigAlpha = new GasWell( "Alpha" );
    GasWell anotherBigAlpha = new GasWell( " Alpha" );

    assertNotNull( alpha );
    assertNotNull( beta );
    assertFalse( alpha.hashCode() == beta.hashCode() );
    assertNotNull( alphaAgain );
    assertEquals( alpha.hashCode(), alphaAgain.hashCode() );
    assertNotNull( bigAlpha );
    assertNotNull( anotherBigAlpha );
    assertEquals( bigAlpha.hashCode(), anotherBigAlpha.hashCode() );
    assertFalse( alpha.hashCode() == bigAlpha.hashCode() );
}

@Test
public void testCompareTo()
{
     assertTrue( (new GasWell( "alpha" )).compareTo( new GasWell( "alpha" ) ) == 0);
     assertTrue( (new GasWell( "alpha" )).compareTo( new GasWell( "alpha0" ) ) < 0);
     assertTrue( (new GasWell( "alpha" )).compareTo( new GasWell( "beta" ) ) < 0);
     assertTrue( (new GasWell( "alpha" )).compareTo( new Date()) == 0);
     assertTrue( (new GasWell( "beta" )).compareTo( new GasWell( "alpha" ) ) > 0);

}



@Test
public void testSerialization()
{
    GasWell davesWell = new GasWell( "Dave's Well" );

    ObjectOutputStream oos = null;
    try
    {
        oos = new ObjectOutputStream( new FileOutputStream( new File( "daves_well.obj" ) ) );
    } catch (IOException e) {
        fail("Failed to create object output stream to file \"daves_well.obj\"");
    }

    try
    {
        oos.writeObject( davesWell );
        oos.close();
    } catch (IOException e)
    {
        fail( "Failed to write out gas well data entry!!" + e.getClass().getName() + " - " + e.getMessage() );
    }

    ObjectInputStream ois = null;
    try {
        ois = new ObjectInputStream( new FileInputStream( new File( "daves_well.obj" ) ) );
    } catch( IOException ioe ) {
        fail( "Failed to open file \"daves_well.obj\" for reading." + ioe.getMessage() );
    }

    GasWell extract = null;
    try
    {
        extract = (GasWell)ois.readObject();

        ois.close();
    } catch ( Exception e) {
        fail("Failed to extract gas well from daves_well.obj!! " + e.getClass().getName() + " - " + e.getMessage());
    }

    assertNotNull( extract );
    assertEquals( "Dave's Well", extract.getName() );
    assertEquals( davesWell, extract );
    assertEquals( davesWell.hashCode(), extract.hashCode() );


}

}
