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

import au.com.polly.util.ReadInput;

public class LineSplitterHarness
{

public static void main( String[] argv )
{
	String			input		=	null;
	LineSplitter splitter	=	null;
	String[]		bits		=	null;
	int				i			=	0;

	splitter = new LineSplitter();

	while( true )
	{
		input = ReadInput.readLine("your input sire", "hello,world,,there");
		try {
			bits	=	splitter.splitLine( input );
			System.out.println( "There are " + bits.length + " bits." );
			for( i  = 0; i < bits.length; i++ )
			{
				System.out.println( "bit[" + i + "]=[" + bits[ i ] + "]" );
			}
		} catch( Exception e ) {
			System.out.println( "caught " + e.getClass().getName() + " - " + e.getMessage() );	
			e.printStackTrace( System.out );
		}
	}
}

}
