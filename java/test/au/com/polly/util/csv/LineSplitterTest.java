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

package au.com.polly.util.csv;


import junit.framework.JUnit4TestAdapter;
import junit.framework.TestSuite;
import org.apache.log4j.Logger;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
  * Exercise as much of the serial generator class as possible.
  *
  * @author Dave Young
  *
  */
public class LineSplitterTest
{
    private static Logger logger = Logger.getLogger(LineSplitterTest.class);
    public final static boolean DEBUG = false;
    final static char LF = 0x0a;
    final static char CR = 0x0d;

    /**
      * Demonstrate splitting a string......
      */
    @Test
    public void testSplitString()
    {
        String data = "eins,zwei,drei,vier";
        LineSplitter splitter = new LineSplitter();
        String[] bits = null;
        try {
            bits = splitter.splitLine( data );

            assertTrue( bits.length == 4 );
            assertEquals( "eins", bits[ 0 ] );
            assertEquals( "zwei", bits[ 1 ] );
            assertEquals( "drei", bits[ 2 ] );
            assertEquals( "vier", bits[ 3 ] );
        } catch( ParseSyntaxException e ) {
            fail( "Did not expect to catch a parse syntax exception" );
        }
    }



    /**
      * Demonstrate splitting a string......
      */
    @Test
    public void testSplitStringWithGermanCharacters()
    {
        String data = "mu\u00DFen,wir,sch\u00F6n,sein";
        LineSplitter splitter = new LineSplitter();
        String[] bits = null;
        try {
            bits = splitter.splitLine( data );

            assertTrue( bits.length == 4 );
            assertEquals( "mu\u00DFen", bits[ 0 ] );
            assertEquals( "wir", bits[ 1 ] );
            assertEquals( "sch\u00F6n", bits[ 2 ] );
            assertEquals( "sein", bits[ 3 ] );
        } catch( ParseSyntaxException e ) {
            fail( "Did not expect to catch a parse syntax exception" );
        }
    }




    /**
      * Demonstrate splitting a string......
      */
    @Test
    public void testSplitStringWithEmptyFields()
    {
        String data = "eins,zwei,,vier";
        LineSplitter splitter = new LineSplitter();
        String[] bits = null;

        try {
            bits = splitter.splitLine( data );

            assertTrue( bits.length == 4 );
            assertEquals( "eins", bits[ 0 ] );
            assertEquals( "zwei", bits[ 1 ] );
            assertEquals( "", bits[ 2 ] );
            assertEquals( "vier", bits[ 3 ] );
        } catch( ParseSyntaxException e ) {
            fail( "Did not expect to catch a parse syntax exception" );
        }
    }


    /**
      * Demonstrate splitting a string......
      */
    @Test
    public void testSplitDelimitedStringWithEmptyFields()
    {
        String data = "\"eins\",'zwei',,vier";
        LineSplitter splitter = new LineSplitter();
        String[] bits = null;

        try {
            bits = splitter.splitLine( data );

            assertTrue( bits.length == 4 );
            assertEquals( "eins", bits[ 0 ] );
            assertEquals( "zwei", bits[ 1 ] );
            assertEquals( "", bits[ 2 ] );
            assertEquals( "vier", bits[ 3 ] );
        } catch( ParseSyntaxException e ) {
            fail( "Did not expect to catch a parse syntax exception" );
        }
    }



    /**
      * Demonstrate splitting a string with backslash-n......
      */
    @Test
    public void testSplitDelimitedStringWithBackslashN()
    {
        String data = "\"eins\",'zwei\\ndrei',,vier";
        LineSplitter splitter = new LineSplitter();
        String[] bits = null;

        try {
            bits = splitter.splitLine( data );

            assertTrue( bits.length == 4 );
            assertEquals( "eins", bits[ 0 ] );
            assertEquals( "zwei\\ndrei", bits[ 1 ] );
            assertEquals( "", bits[ 2 ] );
            assertEquals( "vier", bits[ 3 ] );
        } catch( ParseSyntaxException e ) {
            fail( "Did not expect to catch a parse syntax exception" );
        }
    }




    /**
      * Demonstrate that an unexpected EOL exception is generated
      * if a line with an embedded newline in the data is attempted
      * to be parsed.
      */
    public void testSplitDelimitedStringEndingAbruptly()
    {
        String data = "\"eins\",'zwei";
        LineSplitter splitter = new LineSplitter();
        try
        {
            splitter.splitLine( data );
        } catch (UnexpectedEndOfLineException hooray ) {
        } catch (ParseSyntaxException e) {
            logger.error( e );
            fail("did not expect " + e.getClass().getName() + " - " + e.getMessage());
        }
    }


    /**
      * Demonstrate that an unexpected EOL exception is generated
      * if a line with an embedded newline in the data is attempted
      * to be parsed.
      */
    public void testSplitDelimitedStringWithEmbeddedCarriageReturn()
    {
        String data = "\"eins\",'zwei\\n" + CR + "drei',,vier";
        LineSplitter splitter = new LineSplitter();

        try
        {
            splitter.splitLine( data );
        } catch ( UnexpectedEndOfLineException yayy )  {
        } catch (ParseSyntaxException e)  {
            logger.error( e );
            fail("did not expect " + e.getClass().getName() + " - " + e.getMessage());
        }
    }


    /**
      * Demonstrate that an unexpected EOL exception is generated
      * if a line with an embedded newline in the data is attempted
      * to be parsed.
      */
    @Test
    public void testSplitDelimitedStringWithEmbeddedCarriageReturnLineFeed()
    {
        String data = "\"eins\",'zwei" + CR + LF + "drei',,vier";
        LineSplitter splitter = new LineSplitter();
        String[] bits = null;

        try {
            bits = splitter.splitLine( data );
            fail( "Expected to catch an UnexpectedEndOfLineException" );
        } catch( UnexpectedEndOfLineException e ) {
            //	hooray!!
        } catch( ParseSyntaxException e ) {
            fail( "Expected EOL exception, but got " + e.getClass().getName() + " - " + e.getMessage()  );
        }
    }


    /**
      * Demonstrate that an unexpected EOL exception is generated
      * if a line with an embedded newline in the data is attempted
      * to be parsed.
      */
    @Test
    public void testSplitDelimitedStringWithEmbeddedLineFeed()
    {
        String data = "\"eins\",'zwei" + LF + "drei',,vier";
        LineSplitter splitter = new LineSplitter();
        String[] bits = null;

        try {
            bits = splitter.splitLine( data );
            fail( "Expected to catch an UnexpectedEndOfLineException" );
        } catch( UnexpectedEndOfLineException e ) {
            //	hooray!!
        } catch( ParseSyntaxException e ) {
            fail( "Expected EOL exception, but got " + e.getClass().getName() + " - " + e.getMessage() );
        }
    }


    /**
      * Demonstrate splitting a string with backslash-o......
      */
    @Test
    public void testSplitDelimitedStringWithBackslashO()
    {
        String data = "\"eins\",'zwei\\odrei',,vier";
        LineSplitter splitter = new LineSplitter();
        String[] bits = null;

        try {
            bits = splitter.splitLine( data );

            assertTrue( bits.length == 4 );
            assertEquals( "eins", bits[ 0 ] );
            assertEquals( "zweiodrei", bits[ 1 ] );
            assertEquals( "", bits[ 2 ] );
            assertEquals( "vier", bits[ 3 ] );
        } catch( ParseSyntaxException e ) {
            fail( "Did not expect to catch a parse syntax exception" );
        }
    }



    /**
      * Demonstrate splitting a string using colon as a separator......
      */
    @Test
    public void testSplitStringSemiColonSeparated()
    {
        String data = "eins;zwei;drei;vier";
        LineSplitter splitter = new LineSplitter( ';' );
        String[] bits = null;

        try {
            bits = splitter.splitLine( data );
            assertTrue( bits.length == 4 );
            assertEquals( "eins", bits[ 0 ] );
            assertEquals( "zwei", bits[ 1 ] );
            assertEquals( "drei", bits[ 2 ] );
            assertEquals( "vier", bits[ 3 ] );
        } catch( ParseSyntaxException e ) {
            fail( "Did not expect to catch a parse syntax exception" );
        }
    }


    /**
      * Demonstrate splitting a string..... using colon as a separator.
      */
    @Test
    public void testSplitStringWithEmptyFieldsSemiColonSeparated()
    {
        String data = "eins;zwei;;vier";
        LineSplitter splitter = new LineSplitter( ';' );
        String[] bits = null;

        try {
            bits = splitter.splitLine( data );
            assertTrue( bits.length == 4 );
            assertEquals( "eins", bits[ 0 ] );
            assertEquals( "zwei", bits[ 1 ] );
            assertEquals( "", bits[ 2 ] );
            assertEquals( "vier", bits[ 3 ] );
        } catch( ParseSyntaxException e ) {
            fail( "Did not expect to catch a parse syntax exception" );
        }
    }



    /**
      * Demonstrate splitting a string..... using colon as a separator.
      */
    @Test
    public void testSplitInvalidString()
    {
        String data = "'eins';zwei;;'vier";
        LineSplitter splitter = new LineSplitter( ';' );
        String[] bits = null;

        try {
            bits = splitter.splitLine( data );
            fail( "Expected to catch a parse exception" );
        } catch( ParseSyntaxException e ) {
            // who hoo!!
        }
    }

    /**
     *  Demonstrate that splitting a string that has a quote in it works.
     */
    @Test
    public void testSplitValidStringWithQuoteInIt()
    {
        String data = "\"test\",\"te\"\"st\",\"test\"";
        LineSplitter splitter = new LineSplitter();
        String[] bits = null;

        try {
            bits = splitter.splitLine( data );
            assertEquals( 3, bits.length );
            assertEquals( "test", bits[ 0 ] );
            assertEquals( "te\"st", bits[ 1 ] );
            assertEquals( "test", bits[ 2 ] );
        } catch( ParseSyntaxException e ) {
            fail( "Did not expect to catch a parse syntax exception" );
        }


    }

public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( LineSplitterTest.class );
}
}
