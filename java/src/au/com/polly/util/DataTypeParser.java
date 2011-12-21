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

/**
 * Utility to enable a data type expressed as a string to be truned into the appropriate
 * enumerated type.
 *
 */
public class DataTypeParser
{

/**
 *
 * @param text textual representation of a data type
 * @return enumerated version of the textual representation.
 */
static public DataType parse( String text )
{
    DataType result = DataType.UNKNOWN;
    String refined = null;


    do {

        if ( text == null )
        {
            break;
        }

        refined = text.toLowerCase().trim();
        
        if ( refined.equals( "long" ) )
        {
            result = DataType.LONG;
            break;
        }

        if ( refined.equals( "double" ) )
        {
            result = DataType.DOUBLE;
            break;
        }

        if ( refined.equals( "timestamp" ) )
        {
            result = DataType.TIMESTAMP;
            break;
        }


    } while( false );

    return result;
}

}
