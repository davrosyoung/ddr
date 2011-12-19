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
import java.util.Vector;

/**
  * Exception thrown when mandatory columns are not found within the 
  * column headings...
  *
  * @author Dave Young
  */
public class ColumnHeadingsMissingException extends ColumnsMissingException
{

/**
  * @param message describes what went wrong.
  * @param columns vector containing the names of the missing columns.
  */
public ColumnHeadingsMissingException( String message, Vector columns )
{
	this( message, (Throwable)null, (File)null, -1, columns );
}



/**
  * @param message describes what went wrong.
  */
public ColumnHeadingsMissingException( String message )
{
	this( message, (Throwable)null, (File)null, -1, (Vector)null );
}

/**
  * @param message describes what went wrong.
  * @param line the line within the data source that the syntax error occurs.
  * @param columns vector containing the names of the missing columns.
  */
public ColumnHeadingsMissingException( String message, int line, Vector columns )
{
	this( message, (Throwable)null, (File)null, line, columns );
}

/**
  * @param message describes what went wrong.
  * @param line the line within the data source that the syntax error occurs.
  */
public ColumnHeadingsMissingException( String message, int line )
{
	this( message, (Throwable)null, (File)null, line, (Vector) null );
}


/**
  * @param message describes what went wrong.
  * @param file the file representing the data source.
  */
public ColumnHeadingsMissingException( String message, File file )
{
	this( message, (Throwable)null, file, -1, (Vector) null );
}


/**
  * @param message describes what went wrong.
  * @param file the file representing the data source.
  * @param columns vector containing the names of the missing columns.
  */
public ColumnHeadingsMissingException( String message, File file, Vector columns )
{
	this( message, (Throwable)null, file, -1, columns );
}



/**
  * @param message describes what went wrong.
  * @param file the file representing the data source.
  * @param line the line within the data source that the syntax error occurs.
  */
public ColumnHeadingsMissingException( String message, File file, int line )
{
	this( message, (Throwable)null, file, line, (Vector)null );
}


/**
  * @param message describes what went wrong.
  * @param file the file representing the data source.
  * @param line the line within the data source that the syntax error occurs.
  * @param columns vector containing the names of the missing columns.
  */
public ColumnHeadingsMissingException( String message, File file, int line, Vector columns )
{
	this( message, (Throwable)null, file, line, columns );
}


/**
  * @param causal what caused this exception??
  */
public ColumnHeadingsMissingException( Throwable causal )
{
	this( (String)null, causal, (File)null, -1, (Vector)null );
}


/**
  * @param causal what caused this exception??
  * @param columns vector containing the names of the missing columns.
  */
public ColumnHeadingsMissingException( Throwable causal, Vector columns )
{
	this( (String)null, causal, (File)null, -1, columns );
}


/**
  * @param causal what caused this exception??
  * @param file the file representing the data source.
  */
public ColumnHeadingsMissingException( Throwable causal, File file )
{
	this( (String)null, causal, file, -1, (Vector)null );
}



/**
  * @param causal what caused this exception??
  * @param file the file representing the data source.
  * @param columns vector containing the names of the missing columns.
  */
public ColumnHeadingsMissingException( Throwable causal, File file, Vector columns )
{
	this( (String)null, causal, file, -1, columns );
}


/**
  * @param causal what caused this exception??
  * @param line the line within the data source that the syntax error occurs.
  */
public ColumnHeadingsMissingException( Throwable causal, int line )
{
	this( (String)null, causal, (File)null, line );
}


/**
  * @param causal what caused this exception??
  * @param line the line within the data source that the syntax error occurs.
  * @param columns vector containing the names of the missing columns.
  */
public ColumnHeadingsMissingException( Throwable causal, int line, Vector columns )
{
	this( (String)null, causal, (File)null, line, columns );
}




/**
  * @param causal what caused this exception??
  * @param file the file representing the data source.
  * @param line the line within the data source that the syntax error occurs.
  */
public ColumnHeadingsMissingException( Throwable causal, File file, int line )
{
	this( null, causal, file, line, (Vector)null );
}




/**
  * @param causal what caused this exception??
  * @param file the file representing the data source.
  * @param line the line within the data source that the syntax error occurs.
  * @param columns vector containing the names of the missing columns.
  */
public ColumnHeadingsMissingException( Throwable causal, File file, int line, Vector columns )
{
	this( null, causal, file, line, columns );
}


/**
  * @param message describes what went wrong.
  * @param causal what caused this exception??
  */
public ColumnHeadingsMissingException( String message, Throwable causal )
{
	this( message, causal, (File)null, -1, (Vector)null );
}


/**
  * @param message describes what went wrong.
  * @param causal what caused this exception??
  * @param columns vector containing the names of the missing columns.
  */
public ColumnHeadingsMissingException( String message, Throwable causal, Vector columns )
{
	this( message, causal, (File)null, -1, columns );
}


/**
  * @param message describes what went wrong.
  * @param causal what caused this exception??
  * @param file the file representing the data source.
  */
public ColumnHeadingsMissingException( String message, Throwable causal, File file )
{
	this( message, causal, file, -1, (Vector)null );
}




/**
  * @param message describes what went wrong.
  * @param causal what caused this exception??
  * @param file the file representing the data source.
  * @param columns vector containing the names of the missing columns.
  */
public ColumnHeadingsMissingException( String message, Throwable causal, File file, Vector columns )
{
	this( message, causal, file, -1, columns );
}


/**
  * @param message describes what went wrong.
  * @param causal what caused this exception??
  * @param line the line within the data source that the syntax error occurs.
  */
public ColumnHeadingsMissingException( String message, Throwable causal, int line )
{
	this( message, causal, (File)null, line, (Vector)null );
}

/**
  * @param message describes what went wrong.
  * @param causal what caused this exception??
  * @param line the line within the data source that the syntax error occurs.
  * @param columns vector containing the names of the missing columns.
  */
public ColumnHeadingsMissingException( String message, Throwable causal, int line, Vector columns )
{
	this( message, causal, (File)null, line, columns);
}


/**
  * @param message describes what went wrong.
  * @param causal what caused this exception??
  * @param file the file representing the data source.
  * @param line the line within the data source that the syntax error occurs.
  */
public ColumnHeadingsMissingException( String message, Throwable causal, File file, int line )
{
	this( message, causal, file, line, (Vector)null );
}


/**
  * @param message describes what went wrong.
  * @param causal what caused this exception??
  * @param file the file representing the data source.
  * @param line the line within the data source that the syntax error occurs.
  * @param columns vector containing the names of the missing columns.
  */
public ColumnHeadingsMissingException( String message, Throwable causal, File file, int line, Vector columns )
{
	super( message, causal, file , line, columns);
}


}
