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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 12/11/11
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(JUnit4.class)
public class ExcelCellLocationTest
{


@Test
public void testObtainExcelColumnSpecifier()
{
    assertEquals( "A", ExcelCellLocation.obtainExcelColumnSpecifier( 0 ) );
    assertEquals( "B", ExcelCellLocation.obtainExcelColumnSpecifier( 1 ) );
    assertEquals( "Z", ExcelCellLocation.obtainExcelColumnSpecifier( 25 ) );
    assertEquals( "AA", ExcelCellLocation.obtainExcelColumnSpecifier( 26 ) );
    assertEquals( "AB", ExcelCellLocation.obtainExcelColumnSpecifier( 27 ) );
    assertEquals( "AZ", ExcelCellLocation.obtainExcelColumnSpecifier( 51 ) );
    assertEquals( "BA", ExcelCellLocation.obtainExcelColumnSpecifier( 52 ) );
    assertEquals( "ZZ", ExcelCellLocation.obtainExcelColumnSpecifier( ( 26 * 27 ) - 1 ) );
    assertEquals( "AAA", ExcelCellLocation.obtainExcelColumnSpecifier( 26 * 27 ) );
    assertEquals( "AAB", ExcelCellLocation.obtainExcelColumnSpecifier( ( 26 * 27 ) + 1 ) );
    assertEquals( "AAC", ExcelCellLocation.obtainExcelColumnSpecifier( ( 26 * 27 ) + 2 ) );
    assertEquals( "AAZ", ExcelCellLocation.obtainExcelColumnSpecifier( ( 26 * 27 ) + 25 ) );
    assertEquals( "ABA", ExcelCellLocation.obtainExcelColumnSpecifier( ( 26 * 27 ) + 26 ) );
}

@Test
public void testDecodingColumnSpecifier()
{
    assertEquals( 0, ExcelCellLocation.decodeExcelColumnSpecifier("A") );
    assertEquals( 1, ExcelCellLocation.decodeExcelColumnSpecifier("B") );
    assertEquals( 25, ExcelCellLocation.decodeExcelColumnSpecifier("Z") );
    assertEquals( 26, ExcelCellLocation.decodeExcelColumnSpecifier("AA") );
    assertEquals( 27, ExcelCellLocation.decodeExcelColumnSpecifier("AB") );
    assertEquals( 51, ExcelCellLocation.decodeExcelColumnSpecifier("AZ") );
    assertEquals( 52, ExcelCellLocation.decodeExcelColumnSpecifier("BA") );
    assertEquals( 53, ExcelCellLocation.decodeExcelColumnSpecifier("BB") );
    assertEquals( 676, ExcelCellLocation.decodeExcelColumnSpecifier("ZA") );
    assertEquals( 701, ExcelCellLocation.decodeExcelColumnSpecifier("ZZ") );
    assertEquals( 702, ExcelCellLocation.decodeExcelColumnSpecifier("AAA") );
    assertEquals( 703, ExcelCellLocation.decodeExcelColumnSpecifier("AAB") );
}

@Test( expected=NullPointerException.class)
public void testNullConstructor()
{
    ExcelCellLocation loc = new ExcelCellLocation( null );
}

@Test( expected=IllegalArgumentException.class)
public void testEmptyConstructor()
{
    ExcelCellLocation loc = new ExcelCellLocation( "" );
}

@Test( expected=IllegalArgumentException.class)
public void testBackToFrontConstructor()
{
    ExcelCellLocation loc = new ExcelCellLocation( "1A" );
}

@Test( expected=IllegalArgumentException.class)
public void testSillyConstructor()
{
    ExcelCellLocation loc = new ExcelCellLocation( "AAAAA999999" );
}

@Test
public void testSheetAlphaCellA1()
{
    ExcelCellLocation a1 = new ExcelCellLocation( "Alpha!A1" );
    assertNotNull( a1.getSheetName() );
    assertEquals( "Alpha", a1.getSheetName() );
    assertEquals( 0, a1.getRow() );
    assertEquals( 0, a1.getColumn() );
    assertEquals( "Alpha!A1", a1.toString() );
}

@Test
public void testSheetA1()
{
    ExcelCellLocation a1 = new ExcelCellLocation( "A1" );
    assertNull( a1.getSheetName() );
    assertEquals( 0, a1.getRow() );
    assertEquals( 0, a1.getColumn() );
    assertEquals( "A1", a1.toString() );
}

@Test
public void testRowZeroColumnZero()
{
    ExcelCellLocation a1 = new ExcelCellLocation( 0, 0 );
    assertEquals( 0, a1.getRow() );
    assertEquals( 0, a1.getColumn() );
    assertEquals( "A1", a1.toString() );
}

@Test
public void testB2()
{
    ExcelCellLocation locator = new ExcelCellLocation( "B2" );
    assertEquals( 1, locator.getRow() );
    assertEquals( 1, locator.getColumn() );
    assertEquals( "B2", locator.toString() );
}

@Test
public void testD2()
{
    ExcelCellLocation locator = new ExcelCellLocation( "D2" );
    assertEquals( 1, locator.getRow() );
    assertEquals( 3, locator.getColumn() );
    assertEquals( "D2", locator.toString() );
}

@Test
public void testD5()
{
    ExcelCellLocation locator = new ExcelCellLocation( "D5" );
    assertEquals( 4, locator.getRow() );
    assertEquals( 3, locator.getColumn() );
    assertEquals( "D5", locator.toString() );
}

@Test
public void testRowOneColumnOne()
{
    ExcelCellLocation a1 = new ExcelCellLocation( 1, 1 );
    assertEquals( 1, a1.getRow() );
    assertEquals( 1, a1.getColumn() );
    assertEquals( "B2", a1.toString() );
}


@Test
public void testAA2()
{
    ExcelCellLocation locator = new ExcelCellLocation( "AA2" );
    assertEquals( 26, locator.getColumn() );
    assertEquals( 1, locator.getRow() );
    assertEquals( "AA2", locator.toString() );
}

@Test
public void testRow27ColumnOne()
{
    ExcelCellLocation loc = new ExcelCellLocation( 1, 26 );
    assertEquals(1, loc.getRow());
    assertEquals( 26, loc.getColumn() );
    assertEquals("AA2", loc.toString());
}

@Test
public void testRow1001Column702()
{
    ExcelCellLocation loc = new ExcelCellLocation( 1001, 702 );
    assertEquals( 1001, loc.getRow() );
    assertEquals( 702, loc.getColumn() );
    assertEquals( "AAA1002", loc.toString() );

}

@Test
public void testAAA1001()
{
    ExcelCellLocation loc = new ExcelCellLocation( "AAA1001" );
    assertEquals( 1000, loc.getRow() );
    assertEquals( 702, loc.getColumn() );
    assertEquals("AAA1001", loc.toString());
}

@Test
public void testCopyLocation()
{
    ExcelCellLocation a = new ExcelCellLocation( "ABA5825");
    ExcelCellLocation b = a.copy();
    assertTrue( a.equals( b ) );
    assertEquals(a, b);
    assertEquals( a.getColumn(), b.getColumn() );
    assertEquals( a.getRow(), b.getRow() );
    assertEquals( a.toString(), b.toString() );
}



@Test
public void testEquals()
{
    ExcelCellLocation a = new ExcelCellLocation( "ABA5825");
    ExcelCellLocation b = a.copy();
    assertTrue( a.equals( b ) );
    assertEquals(a, b);
    assertEquals( a.getColumn(), b.getColumn() );
    assertEquals( a.getRow(), b.getRow() );
    assertEquals( a.toString(), b.toString() );

    b.moveDown();
    assertFalse(a.getRow() == b.getRow());
    assertTrue(a.getColumn() == b.getColumn());

    b = a.copy();
    assertEquals(a, b);
    assertEquals( a.getColumn(), b.getColumn() );
    assertEquals( a.getRow(), b.getRow() );
    assertEquals( a.toString(), b.toString() );

    b.moveRight();
    assertTrue(!a.equals(b));
    assertEquals(a.getRow(), b.getRow());
    assertFalse(a.getColumn() == b.getColumn());
}

@Test
public void testMovingCell()
{
    ExcelCellLocation a = new ExcelCellLocation( "E15");
    assertEquals(4, a.getColumn());
    assertEquals( 14, a.getRow() );
    assertEquals( "E15", a.toString() );

    a.moveUp();
    assertEquals( 4, a.getColumn() );
    assertEquals(13, a.getRow());
    assertEquals( "E14", a.toString() );

    a.moveRight();
    assertEquals( 5, a.getColumn() );
    assertEquals( 13, a.getRow() );
    assertEquals("F14", a.toString());

    a.moveDown();
    assertEquals( 5, a.getColumn() );
    assertEquals( 14, a.getRow() );

    a.moveLeft();
    assertEquals( 4, a.getColumn() );
    assertEquals( 14, a.getRow() );

    a.moveLeft( 3 );

    assertEquals( 1, a.getColumn() );
    assertEquals( 14, a.getRow() );
    a.moveLeft();
    assertEquals( 0, a.getColumn() );
    assertEquals( 14, a.getRow() );
    a.moveLeft();
    assertEquals( 0, a.getColumn() );
    assertEquals( 14, a.getRow()  );

    a.moveUp( 13 );

    assertEquals( 0, a.getColumn() );
    assertEquals( 1, a.getRow() );

    a.moveUp();

    assertEquals( 0, a.getColumn() );
    assertEquals( 0, a.getRow() );

    a.moveUp();

    assertEquals( 0, a.getColumn() );
    assertEquals( 0, a.getRow() );
}

@Test
public void testMovingCellAndSheet()
{
    ExcelCellLocation a = new ExcelCellLocation( "SA11!E15");
    assertNotNull(a.getSheetName());
    assertEquals( "SA11", a.getSheetName() );
    assertEquals( 4, a.getColumn() );
    assertEquals(14, a.getRow());
    assertEquals( "SA11!E15", a.toString() );

    a.moveUp();
    assertEquals( 4, a.getColumn() );
    assertEquals(13, a.getRow());
    assertEquals( "SA11!E14", a.toString() );

    a.moveRight();
    assertEquals( "SA11", a.getSheetName() );
    assertEquals(5, a.getColumn());
    assertEquals( 13, a.getRow() );
    assertEquals( "SA11!F14", a.toString() );

    a.setSheetName("BA12");
    assertEquals( "BA12", a.getSheetName() );
    assertEquals( 5, a.getColumn() );
    assertEquals(13, a.getRow());
    assertEquals( "BA12!F14", a.toString() );

    a.moveDown();
    assertEquals(5, a.getColumn());
    assertEquals( 14, a.getRow() );

    a.moveLeft();
    assertEquals(4, a.getColumn());
    assertEquals( 14, a.getRow() );

    a.moveLeft(3);

    assertEquals(1, a.getColumn());
    assertEquals( 14, a.getRow() );
    a.moveLeft();
    assertEquals(0, a.getColumn());
    assertEquals( 14, a.getRow() );
    a.moveLeft();
    assertEquals(0, a.getColumn());
    assertEquals( 14, a.getRow()  );

    a.moveUp( 13 );

    assertEquals( 0, a.getColumn() );
    assertEquals( 1, a.getRow() );

    a.moveUp();

    assertEquals( 0, a.getColumn() );
    assertEquals( 0, a.getRow() );

    a.moveUp();

    assertEquals( 0, a.getColumn() );
    assertEquals( 0, a.getRow() );
    assertEquals( "BA12!A1", a.toString() );
}

@Test
public void testHashCodesForSimpleCells()
{
     ExcelCellLocation alpha = new ExcelCellLocation( "A1" );
     ExcelCellLocation beta = new ExcelCellLocation( "A1" );
    
    assertNotSame( "alpha and beta should be separate objects!!", alpha, beta );
    assertEquals( alpha.hashCode(), beta.hashCode() );
}

@Test
public void testHashCodesForAdjacentSimpleCells()
{
     ExcelCellLocation alpha = new ExcelCellLocation( "C8" );
     ExcelCellLocation beta = new ExcelCellLocation( "C9" );
    
    assertNotSame( "alpha and beta should be separate objects!!", alpha, beta );
    assertFalse(alpha.hashCode() == beta.hashCode());
    assertFalse( alpha.equals( beta ) );
    assertEquals( "C8", alpha.toString() );
    assertEquals( "C9", beta.toString() );

    alpha.moveDown();
    
    assertEquals( alpha.hashCode(), beta.hashCode() );
    assertEquals( alpha, beta );
    assertEquals( "C9", alpha.toString() );
    assertEquals( "C9", beta.toString() );
    
    alpha.moveRight();
    
    assertFalse( alpha.hashCode() == beta.hashCode() );
    assertFalse( alpha.equals( beta ) );
    assertEquals("D9", alpha.toString());
    assertEquals( "C9", beta.toString() );

}

@Test
public void testHashCodesForCellsWithSameSheetNames()
{
     ExcelCellLocation alpha = new ExcelCellLocation( "Bonkers!A1" );
     ExcelCellLocation beta = new ExcelCellLocation( "Bonkers!A1" );
    
    assertNotSame( "alpha and beta should be separate objects!!", alpha, beta );
    assertEquals(alpha.hashCode(), beta.hashCode());
    assertEquals( "Bonkers!A1", alpha.toString() );
    assertEquals( "Bonkers!A1", beta.toString() );
    
}

@Test
public void testHashCodesForCellsWithVerySimilarSheetNames()
{
     ExcelCellLocation alpha = new ExcelCellLocation( "Bonkers!C8" );
     ExcelCellLocation beta = new ExcelCellLocation( "bonkers!C8" );
    
    assertNotSame( "alpha and beta should be separate objects!!", alpha, beta );
    assertFalse( alpha.hashCode() == beta.hashCode() );
    assertEquals("Bonkers!C8", alpha.toString());
    assertEquals("bonkers!C8", beta.toString());
    
}


@Test
public void testEqualityAgainstSomeOtherTypeOfObject()
{
    ExcelCellLocation a = new ExcelCellLocation( "Sheet1!AA15" );
    Object o = new Date();
    assertFalse( a.equals( o ) );

}

@Test
public void testHashCodeAfterMovingLeft()
{
    ExcelCellLocation alpha = new ExcelCellLocation( "B1" );
    ExcelCellLocation beta = new ExcelCellLocation( "B1" );

    assertNotSame( "alpha and beta should be separate objects!!", alpha, beta );
    assertEquals(alpha.hashCode(), beta.hashCode());
    assertEquals( alpha, beta );
    assertEquals( "B1", alpha.toString() );
    assertEquals( "B1", beta.toString() );

    assertNotSame("alpha and beta should be separate objects!!", alpha, beta);
    assertEquals( alpha.hashCode(), beta.hashCode() );
    assertEquals( alpha, beta );
    
    beta.moveLeft();

    assertNotSame( "alpha and beta should be separate objects!!", alpha, beta );
    assertFalse(alpha.hashCode() == beta.hashCode());
    assertFalse(alpha.equals(beta));
    assertEquals( "B1", alpha.toString() );
    assertEquals( "A1", beta.toString() );

    
    alpha.moveLeft();
    assertNotSame( "alpha and beta should be separate objects!!", alpha, beta );
    assertEquals( alpha.hashCode(), beta.hashCode() );
    assertEquals( alpha, beta );
    assertEquals( "A1", alpha.toString() );
    assertEquals( "A1", beta.toString() );


    // can't move left past column A!!!
    alpha.moveLeft();
    assertNotSame( "alpha and beta should be separate objects!!", alpha, beta );
    assertEquals( alpha.hashCode(), beta.hashCode() );
    assertEquals( alpha, beta );
    assertEquals( "A1", alpha.toString() );
    assertEquals( "A1", beta.toString() );
}


@Test
public void testHashCodeAfterMovingRight()
{
    ExcelCellLocation alpha = new ExcelCellLocation( "B1" );
    ExcelCellLocation beta = new ExcelCellLocation( "B1" );

    assertNotSame( "alpha and beta should be separate objects!!", alpha, beta );
    assertEquals( alpha.hashCode(), beta.hashCode() );
    assertEquals( alpha, beta );
    assertEquals("B1", alpha.toString());
    assertEquals( "B1", beta.toString() );

    assertNotSame("alpha and beta should be separate objects!!", alpha, beta);
    assertEquals(alpha.hashCode(), beta.hashCode());
    assertEquals(alpha, beta);

    beta.moveRight();

    assertNotSame("alpha and beta should be separate objects!!", alpha, beta);
    assertFalse(alpha.hashCode() == beta.hashCode());
    assertFalse(alpha.equals(beta));
    assertEquals( "B1", alpha.toString() );
    assertEquals( "C1", beta.toString() );


    alpha.moveRight();
    assertNotSame("alpha and beta should be separate objects!!", alpha, beta);
    assertEquals(alpha.hashCode(), beta.hashCode());
    assertEquals( alpha, beta );
    assertEquals( "C1", alpha.toString() );
    assertEquals( "C1", beta.toString() );

    alpha.moveRight();
    assertNotSame( "alpha and beta should be separate objects!!", alpha, beta );
    assertFalse(alpha.hashCode() == beta.hashCode());
    assertFalse(alpha.equals(beta));
    assertEquals( "D1", alpha.toString() );
    assertEquals( "C1", beta.toString() );
}

@Test
public void testHashCodeAfterMovingUp()
{
    ExcelCellLocation alpha = new ExcelCellLocation( "D2" );
    ExcelCellLocation beta = new ExcelCellLocation( "D2" );

    assertNotSame( "alpha and beta should be separate objects!!", alpha, beta );
    assertEquals( alpha.hashCode(), beta.hashCode() );
    assertEquals( alpha, beta );
    assertEquals( "D2", alpha.toString() );
    assertEquals( "D2", beta.toString() );

    assertNotSame("alpha and beta should be separate objects!!", alpha, beta);
    assertEquals( alpha.hashCode(), beta.hashCode() );
    assertEquals( alpha, beta );

    beta.moveUp();

    assertNotSame( "alpha and beta should be separate objects!!", alpha, beta );
    assertFalse(alpha.hashCode() == beta.hashCode());
    assertFalse(alpha.equals(beta));
    assertEquals( "D2", alpha.toString() );
    assertEquals( "D1", beta.toString() );


    alpha.moveUp();
    assertNotSame( "alpha and beta should be separate objects!!", alpha, beta );
    assertEquals( alpha.hashCode(), beta.hashCode() );
    assertEquals( alpha, beta );
    assertEquals( "D1", alpha.toString() );
    assertEquals( "D1", beta.toString() );


    // can't move up past first row.
    alpha.moveUp();
    assertNotSame( "alpha and beta should be separate objects!!", alpha, beta );
    assertEquals( alpha.hashCode(), beta.hashCode() );
    assertEquals( alpha, beta );
    assertEquals( "D1", alpha.toString() );
    assertEquals( "D1", beta.toString() );
}

@Test
public void testHashCodeAfterMovingDown()
{
    ExcelCellLocation alpha = new ExcelCellLocation( "D2" );
    ExcelCellLocation beta = new ExcelCellLocation( "D2" );

    assertNotSame( "alpha and beta should be separate objects!!", alpha, beta );
    assertEquals( alpha.hashCode(), beta.hashCode() );
    assertEquals( alpha, beta );
    assertEquals( "D2", alpha.toString() );
    assertEquals( "D2", beta.toString() );

    assertNotSame("alpha and beta should be separate objects!!", alpha, beta);
    assertEquals( alpha.hashCode(), beta.hashCode() );
    assertEquals( alpha, beta );

    beta.moveDown();

    assertNotSame( "alpha and beta should be separate objects!!", alpha, beta );
    assertFalse( alpha.hashCode() == beta.hashCode());
    assertFalse( alpha.equals( beta ) );
    assertEquals( "D2", alpha.toString() );
    assertEquals( "D3", beta.toString() );


    alpha.moveDown();
    assertNotSame( "alpha and beta should be separate objects!!", alpha, beta );
    assertEquals( alpha.hashCode(), beta.hashCode() );
    assertEquals( alpha, beta );
    assertEquals( "D3", alpha.toString() );
    assertEquals( "D3", beta.toString() );


    // can move down past second row.
    alpha.moveDown();
    assertNotSame( "alpha and beta should be separate objects!!", alpha, beta );
    assertFalse(alpha.hashCode() == beta.hashCode());
    assertFalse(alpha.equals(beta));
    assertEquals( "D4", alpha.toString() );
    assertEquals( "D3", beta.toString() );

}

@Test
public void testHashCodeAfterChangingSheet()
{
    ExcelCellLocation alpha = new ExcelCellLocation( "Osho!D2" );
    ExcelCellLocation beta = new ExcelCellLocation( "Osho!D2" );

    assertNotSame( "alpha and beta should be separate objects!!", alpha, beta );
    assertEquals( alpha.hashCode(), beta.hashCode() );
    assertEquals( alpha, beta );
    assertEquals( "Osho!D2", alpha.toString() );
    assertEquals( "Osho!D2", beta.toString() );
    
    alpha.setSheetName( "osho" );
    assertNotSame( "alpha and beta should be separate objects!!", alpha, beta );
    assertFalse(alpha.hashCode() == beta.hashCode());
    assertFalse(alpha.equals(beta));
    assertEquals( "osho!D2", alpha.toString() );
    assertEquals( "Osho!D2", beta.toString() );

    beta.setSheetName( "osho" );
    assertEquals(alpha.hashCode(), beta.hashCode());
    assertEquals( alpha, beta );
    assertEquals( "osho!D2", alpha.toString() );
    assertEquals( "osho!D2", beta.toString() );

}

public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( ExcelCellLocationTest.class );
}


}
