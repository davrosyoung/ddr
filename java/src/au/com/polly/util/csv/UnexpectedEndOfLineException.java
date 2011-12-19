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

public class UnexpectedEndOfLineException extends ParseSyntaxException
{
int		line	= -1;

public UnexpectedEndOfLineException()
{
	this( (String)null, (Throwable)null, (File)null, -1 );
}

/**
  * @param message describes what went wrong.
  *
  *
  */
public UnexpectedEndOfLineException( String message )
{
	this( message, (Throwable)null, (File)null, -1 );
}



/**
  * @param message describes what went wrong.
  * @param line the line within the data source that the syntax error occurs.
  */
public UnexpectedEndOfLineException( String message, int line )
{
	this( message, (Throwable)null, (File)null, line );
}



/**
  * @param message describes what went wrong.
  * @param file the file representing the data source.
  *
  */
public UnexpectedEndOfLineException( String message, File file )
{
	this( message, (Throwable)null, file, -1 );
}



/**
  * @param message describes what went wrong.
  * @param file the file representing the data source.
  * @param line the line within the data source that the syntax error occurs.
  *
  */
public UnexpectedEndOfLineException( String message, File file, int line )
{
	this( message, (Throwable)null, file, line );
}


/**
  * @param causal what caused this exception??
  *
  */
public UnexpectedEndOfLineException( Throwable causal )
{
	this( (String)null, causal, (File)null, -1 );
}


/**
  * @param causal what caused this exception??
  * @param file the file representing the data source.
  *
  */
public UnexpectedEndOfLineException( Throwable causal, File file )
{
	this( (String)null, causal, file, -1 );
}


/**
  * @param causal what caused this exception??
  * @param file the file representing the data source.
  * @param line the line within the data source that the syntax error occurs.
  *
  */
public UnexpectedEndOfLineException( Throwable causal, int line )
{
	this( (String)null, causal, (File)null, line );
}




/**
  * @param causal what caused this exception??
  * @param file the file representing the data source.
  * @param line the line within the data source that the syntax error occurs.
  *
  */
public UnexpectedEndOfLineException( Throwable causal, File file, int line )
{
	this( null, causal, file, line );
}


/**
  * @param message describes what went wrong.
  * @param causal what caused this exception??
  *
  */
public UnexpectedEndOfLineException( String message, Throwable causal )
{
	this( message, causal, (File)null, -1 );
}


/**
  * @param message describes what went wrong.
  * @param causal what caused this exception??
  * @param file the file representing the data source.
  *
  */
public UnexpectedEndOfLineException( String message, Throwable causal, File file )
{
	this( message, causal, file, -1 );
}


/**
  * @param message describes what went wrong.
  * @param causal what caused this exception??
  * @param line the line within the data source that the syntax error occurs.
  *
  */
public UnexpectedEndOfLineException( String message, Throwable causal, int line )
{
	this( message, causal, (File)null, line );
}


/**
  * @param message describes what went wrong.
  * @param causal what caused this exception??
  * @param file the file representing the data source.
  * @param line the line within the data source that the syntax error occurs.
  *
  */
public UnexpectedEndOfLineException( String message, Throwable causal, File file, int line )
{
	super( message, causal, file, line );
}


}
