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

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class LineSplitter
{
    Logger logger = Logger.getLogger( LineSplitter.class );

    private final static int	INIT			= 0x00;
    private final static int	START_DELIMITER	= 0x01;
    private final static int	END_DELIMITER	= 0x02;
    private final static int	DATA			= 0x03;
    private final static int	COMMA			= 0x04;
    private final static int	FINI			= 0x05;
    private final static int	ERROR			= 0xff;

    private final static char	CR				= 0x0d;
    private final static char	NL				= 0x0a;

    private boolean	escape 			=	false;
    private	char	escapeCharacter	=	'\\';
    private char	separator		=	',';

    public LineSplitter()
    {
        this( ',' );
    }

    /**
      * @param separator character separating one field from another.
      *
      */
    public LineSplitter( char separator )
    {
        this.separator = separator;
    }

    /**
     *
     * @param line
     * @return
     * @throws ParseSyntaxException
     */
    public String[] splitLine( String line )
    throws ParseSyntaxException
    {
        List bits	=	new ArrayList();
        int			cursor	=	0;
        String[]	result	=	null;
        int			i		=	0;
        Node node	=	null;

        while( ( node = nextNode( line.substring( cursor ) ) ) != null )
        {
            bits.add( node.getPayload() );
            if ( logger.isTraceEnabled() )
            {
                logger.trace("added [" + node.getPayload() + "]");
            }
            cursor += node.getLength();
            if ( logger.isTraceEnabled() ) { logger.trace( "node=" + node + ", cursor=" + cursor ); }
        }

        result = new String[ bits.size() ];
        for( i = 0; i < bits.size(); i++ )
        {
            result[ i ] = (String) bits.get( i );
        }

        return result;
    }

    /**
      * @return node comprising content and length for a field.
      *
      * @param input remainder of the input data line.
      *
      * @throws UnexpectedEndOfLineException if the end of line was encountered,
      *    when in fact more data was expected.
      * @throws ExpectedCommaException if a comma (field separator) was expected,
      *		but other data was encountered instead.
      */
    public Node nextNode( String input )
    throws UnexpectedEndOfLineException, ExpectedCommaException
    {
        int				cursor		=	0;
        int				state		=	INIT;
        Node result		=	null;
        StringBuffer	buffer		=	null;
        char			delimiter	=	' ';
        boolean			escaped		=	false;
        boolean			delimited	=	false;
        int				length		=	0;
        char			khar		=	' ';

        buffer	= new StringBuffer();

        do
        {
            if ( cursor < input.length() )
            {
                khar = input.charAt( cursor );
                cursor++;
                length++;

                if ( logger.isTraceEnabled() ) { logger.trace( "khar=[" + khar + "], state=" + getStateText( state ) ); }

            } else {

                if ( logger.isTraceEnabled() ) { logger.trace( "state=" + getStateText( state ) + ", delimted=" + delimited ); }

                switch( state )
                {
                    case DATA:
                        if ( delimited )
                        {
                            throw new UnexpectedEndOfLineException( "Data line ended unexpectedly" );
                        }
                        result = new Node( buffer.toString().trim(), length );
                        break;

                    case END_DELIMITER:
                        result = new Node( buffer.toString().trim(), length );
                        break;

                    case COMMA:
                    case START_DELIMITER:
                        throw new UnexpectedEndOfLineException( "Data line ended unexpectedly" );
    //					break;
                }
                break;
            }

            switch( state )
            {
                case INIT:
                    if ( khar == '"' )
                    {
                        delimiter = '"';
                        delimited	=	true;
                        state = START_DELIMITER;
                        break;
                    }

                    if ( khar == '\'' )
                    {
                        delimiter = '\'';
                        delimited	=	true;
                        state = START_DELIMITER;
                        break;
                    }

                    if ( khar == separator )
                    {
                        result = new Node( "", length );
                        state = FINI;
                        break;
                    }

                    if ( ! Character.isWhitespace( khar  ) )
                    {
                        buffer.append( khar );
                        state = DATA;
                    }

                    break;

                case START_DELIMITER:
                    if ( khar == delimiter )
                    {
                        state = END_DELIMITER;
                        break;
                    }

                    state = DATA;
                    buffer.append( khar );
                    break;

                case END_DELIMITER:
                    if ( khar == separator )
                    {
                        state = FINI;
                        result = new Node( buffer.toString().trim(), length );
                        break;
                    }

                    //	hmm ... we expect a comma before any more data...
                    //	-------------------------------------------------
                    if ( !Character.isWhitespace( khar ) )
                    {
                        if ( logger.isTraceEnabled() ) { logger.trace( "state=END_DELIMITER, got khar=[" + khar + "], expected a comma!!" ); }
                        throw new ExpectedCommaException( "missing field separator" );
                    }
                    break;

                case DATA:

                    //	we don't expected newline or carriage return
                    //	characters in the middle of our data.
                    //	----------------------------------------------
                    if ( ( khar == CR ) || ( khar == NL ) )
                    {
                        throw new UnexpectedEndOfLineException(
                            "Data line ended unexpectedly"
                        );
                    }

                    //	if the previous character was an escape, then no
                    //	matter what the current character is, just append
                    //	it into our data buffer.
                    //	----------------------------------------------
                    if ( escaped )
                    {
                        //	special hack to maintain \n sequences for DMS...
                        //	... DY 24/APR/2002
                        //	------------------------------------------------
                        if ( khar == 'n' )
                        {
                            buffer.append( '\\' );
                        }
                        buffer.append( khar );
                        escaped = false;
                        break;
                    }

                    //	if we encounter the escape character, make a note..
                    //	---------------------------------------------------
                    if ( khar == escapeCharacter )
                    {
                        escaped = true;
                        break;
                    }


                    //	if we are in a delimited string, then
                    //	react appropriately if the end delimiter is
                    //	encountered.  Still need to check for doubled
                    //	delimiters and comma follows delimiter.
                    //	------------------------------------------------------
                    if (
                            ( delimited )
                        &&	( khar == delimiter )
                        &&	( cursor < input.length() )
                        &&	( input.charAt( cursor ) == delimiter )
                    )
                    {
                        escaped = true;
                        break;
                    }

                    if ( ( delimited ) && ( khar == delimiter ) )
                    {
                        state = END_DELIMITER;
                        break;
                    }

                    if ( ( !delimited ) && ( khar == separator ) )
                    {
                        state = FINI;
                        result = new Node( buffer.toString().trim(), length );
                        break;
                    }

                    //	looks like we just put this one into the data buffer..
                    //	------------------------------------------------------
                    buffer.append( khar );
                    break;

                case COMMA:

                    break;

                default:
                    break;
            }
        } while( ( state != FINI ) && ( state != ERROR ) );

        if ( logger.isTraceEnabled() )
        {
            logger.trace( "state=[" + getStateText( state ) + "]" );
            logger.trace( "buffer=[" + buffer + "]" );
            logger.trace( "length=" + length + ", cursor=" + cursor );
        }

        return result;
    }

    /**
     *
     * @param state
     * @return String
     */
    public static String getStateText( int state )
    {
        String result = null;

        switch( state )
        {
            case INIT:
                result = "INIT";
                break;

            case START_DELIMITER:
                result = "START_DELIMITER";
                break;

            case END_DELIMITER:
                result = "END_DELIMITER";
                break;

            case DATA:
                result = "DATA";
                break;

            case COMMA:
                result = "COMMA";
                break;

            case FINI:
                result = "FINI";
                break;

            case ERROR:
                result = "ERROR";
                break;

            default:
                result = "<unknown=" + state + ">";
                break;
        }

        return result;
    }

}

/**
  * When we are processing the line, it is likely that the bits of data
  * retrieved, actually occupied more space what with delimiters,
  * escaped characters and so on. This class holds both the resultant
  * bit of data and also records how many characters in the source string
  * were used to represent the data (payload).
  */
class Node
{
    int		length;
    String	payload;

    public Node( String payload, int length )
    {
        this.length 	= length;
        this.payload	=	payload;
    }

    public int getLength()
    {
        return this.length;
    }

    public String getPayload()
    {
        return this.payload;
    }

    public String toString()
    {
        return( "[" + getLength() + "=" + getPayload() + "]" );
    }

}
