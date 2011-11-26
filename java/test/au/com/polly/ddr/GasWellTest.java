package au.com.polly.ddr;

import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
