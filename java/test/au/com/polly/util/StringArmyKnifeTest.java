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

package au.com.polly.util;

import junit.framework.Assert;
import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Exercise the string army knife
 */
@RunWith(JUnit4.class)
public class StringArmyKnifeTest
{

@Test
public void testTwoNullStrings()
{
    assertTrue( StringArmyKnife.areStringsEqual( null, null ) );
}


@Test
public void testFirstStringNull()
{
    assertFalse(StringArmyKnife.areStringsEqual( null, "dave" ) );
}


@Test
public void testSecondStringNull()
{
    assertFalse(StringArmyKnife.areStringsEqual( "dave", null ) );
}

@Test
public void testEmptyAndNullString()
{
    assertFalse(StringArmyKnife.areStringsEqual( "", null ) );
}

@Test
public void testTwoEmptyStrings()
{
    assertTrue(StringArmyKnife.areStringsEqual("", ""));
}

@Test
public void testTwoBlankStrings()
{
    assertTrue(StringArmyKnife.areStringsEqual(" ", " "));
}

@Test
public void testBlankAgainstEmptyString()
{
    assertFalse(StringArmyKnife.areStringsEqual( "", " " ));
}

@Test
public void testTwoEqualStrings()
{
    assertTrue(StringArmyKnife.areStringsEqual("dave", "dave"));
}

@Test
public void testTwoVerySimilarStrings()
{
    assertFalse( StringArmyKnife.areStringsEqual( "dave", "dave " ) );
    assertFalse( StringArmyKnife.areStringsEqual( "dave", " dave" ) );
    assertFalse( StringArmyKnife.areStringsEqual( "dave", "Dave" ) );
    assertFalse(StringArmyKnife.areStringsEqual("Dave", "dave"));
}

@Test( expected = NullPointerException.class )
public void testRemoveUnwantedCharactersWithNullArgs()
{
    StringArmyKnife.removeUnwantedCharacters( null, null );
}


@Test( expected = NullPointerException.class )
public void testRemoveUnwantedCharactersWithFirstArgNull()
{
    StringArmyKnife.removeUnwantedCharacters( null, ".:;," );
}


@Test( expected = NullPointerException.class )
public void testRemoveUnwantedCharactersWithSecondArgNull()
{
    StringArmyKnife.removeUnwantedCharacters( "abcra cadabra", null );
}

@Test
public void testRemoveUnwantedCharactersWhereOriginalAllUnwanted()
{
    String result = StringArmyKnife.removeUnwantedCharacters( ".:$#-+", ".:$#-+" );
    Assert.assertNotNull( result );
    Assert.assertEquals("", result);
}

@Test
public void testRemoveUnwantedCharactersWhereNoneUnwanted()
{
    String original = "The quick brown fox jumped over the lazy dog.";
    String result = StringArmyKnife.removeUnwantedCharacters( original, ",:;'\"" );
    Assert.assertNotNull( result );
    assertEquals(original, result);
    assertTrue(original == result);
}

@Test( expected=NullPointerException.class )
public void testReplaceNullString()
{
    StringArmyKnife.replace( null, null, null );
    
}

@Test( expected=NullPointerException.class )
public void testReplaceNullSearchString()
{
    StringArmyKnife.replace( "hello world", null, "goodbye" );
}

@Test
public void testReplaceNullReplacementString()
{
    String result;
    result = StringArmyKnife.replace( "hello world", "hello", null );
    assertNotNull( result );
    Assert.assertEquals( " world", result );

    result = StringArmyKnife.replace( "hello world", "world", null );
    assertNotNull( result );
    Assert.assertEquals("hello ", result);
}

@Test
public void testValidReplacement()
{
    String result;
    result = StringArmyKnife.replace( "hello there world", "there", "small" );
    assertNotNull( result );
    Assert.assertEquals( "hello small world", result );

}
@Test
public void testValidReplacementAtStartOfString()
{
    String result;
    result = StringArmyKnife.replace( "hello there world", "hello", "goodbye" );
    assertNotNull( result );
    Assert.assertEquals( "goodbye there world", result );

}
@Test
public void testValidReplacementAtEndOfString()
{
    String result;
    result = StringArmyKnife.replace( "hello there world", "world", "Earth" );
    assertNotNull( result );
    Assert.assertEquals( "hello there Earth", result );

}

@Test
public void testMultipleValidReplacement()
{
    String result;
    result = StringArmyKnife.replace( "hello hello world", "hello", "bye" );
    assertNotNull( result );
    Assert.assertEquals( "bye bye world", result );
}


public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( StringArmyKnifeTest.class );
}

}
