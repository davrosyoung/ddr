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

import java.io.File;

public abstract class ParseException extends Exception
{
File	file	=	null;


/**
  * Default constructor.
  *
  */
public ParseException()
{
}

/**
  * @param message describes what went wrong.
  *
  *
  */
public ParseException( String message )
{
	this( message, (Throwable)null, (File)null );
}


/**
  * @param message describes what went wrong.
  * @param file the file that the error occurred in.
  *
  */
public ParseException( String message, File file )
{
	this( message, (Throwable)null, file );
}

/**
  * @param causal what caused this exception??
  *
  */
public ParseException( Throwable causal )
{
	this( (String)null, causal, (File)null );
}


/**
  * @param causal what caused this exception??
  * @param file the file that the error occurred in.
  *
  */
public ParseException( Throwable causal, File file )
{
	this( (String)null, causal, (File)file );
}

/**
  * @param message describes what went wrong.
  * @param causal what caused this exception??
  */
public ParseException( String message, Throwable causal )
{
	this( message, causal, (File)null );
}

/**
  * @param message describes what went wrong.
  * @param causal what caused this exception??
  * @param file the file that the error occurred in.
  */
public ParseException( String message, Throwable causal, File file )
{
	super( message, causal );
	setFile( file );
}

/**
  * @param file the file that this exception is in relation to.
  *
  */
public void setFile( File file )
{
	this.file = file;
}


/**
  * @return message describing the error, in this case, possibly
  *   prepended with a filename and/or line number.
  *
  */
public String getMessage()
{
	StringBuffer out = new StringBuffer();

	if ( file != null )
	{
		out.append( "file: \"" + file.getAbsolutePath() + "\" " );
	}

	out.append( super.getMessage() );

	return out.toString();
}

}
