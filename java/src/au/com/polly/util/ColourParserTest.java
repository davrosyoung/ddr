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

import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 *
 * Battery of tests for prioritized timing data.
 *
 */
public class ColourParserTest
{
    @Test
    public void testParsingColours()
    {
        assertEquals( Color.BLACK, ColourParser.parse("black") );
        assertEquals( Color.BLACK, ColourParser.parse("BLack") );
        assertEquals( Color.BLACK, ColourParser.parse(" BLack ") );
        assertNull( ColourParser.parse("blacks") );
        assertNull( ColourParser.parse("lack") );
        assertEquals( Color.BLUE, ColourParser.parse("blue") );
        assertEquals( Color.WHITE, ColourParser.parse("WHITE") );
        assertEquals( Color.YELLOW, ColourParser.parse("yellow") );
        assertEquals( Color.ORANGE, ColourParser.parse("orange") );
        assertEquals( Color.GRAY, ColourParser.parse("grey") );
    }

    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( ColourParserTest.class );
    }

}