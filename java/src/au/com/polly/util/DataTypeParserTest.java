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

import static org.junit.Assert.assertEquals;

/**
 *
 * Battery of tests for prioritized timing data.
 *
 */
public class DataTypeParserTest {
    @Test
    public void testParsingDataType()
    {
        assertEquals( DataType.UNKNOWN, DataTypeParser.parse(null) );
        assertEquals( DataType.UNKNOWN, DataTypeParser.parse("") );
        assertEquals( DataType.LONG, DataTypeParser.parse("long") );
        assertEquals( DataType.LONG, DataTypeParser.parse("LONG") );
        assertEquals( DataType.LONG, DataTypeParser.parse(" long ") );
        assertEquals( DataType.UNKNOWN, DataTypeParser.parse("a long ") );
        assertEquals( DataType.UNKNOWN, DataTypeParser.parse(" llong ") );
        assertEquals( DataType.UNKNOWN, DataTypeParser.parse(" longg ") );

        assertEquals( DataType.DOUBLE, DataTypeParser.parse("double") );
        
        assertEquals( DataType.UNKNOWN, DataTypeParser.parse("time") );
        assertEquals( DataType.UNKNOWN, DataTypeParser.parse("date/time") );
        assertEquals( DataType.UNKNOWN, DataTypeParser.parse("timestam") );
        assertEquals( DataType.TIMESTAMP, DataTypeParser.parse("timestamp") );
        assertEquals( DataType.TIMESTAMP, DataTypeParser.parse("  timestamp") );
    }

    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( DataTypeParserTest.class );
    }

}
