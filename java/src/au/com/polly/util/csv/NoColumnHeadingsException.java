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

public class NoColumnHeadingsException extends ParseException
{
File	file	=	null;

/**
  * @param message describes what went wrong.
  *
  *
  */
public NoColumnHeadingsException( String message )
{
	this( message, (Throwable)null, (File)null );
}


/**
  * @param message describes what went wrong.
  * @param file the file that the error occurred in.
  *
  */
public NoColumnHeadingsException( File file )
{
	this( (String)null, (Throwable)null, file );
}



/**
  * @param message describes what went wrong.
  * @param file the file that the error occurred in.
  *
  */
public NoColumnHeadingsException( String message, File file )
{
	this( message, (Throwable)null, file );
}

/**
  * @param causal what caused this exception??
  *
  */
public NoColumnHeadingsException( Throwable causal )
{
	this( (String)null, causal, (File)null );
}


/**
  * @param causal what caused this exception??
  * @param file the file that the error occurred in.
  *
  */
public NoColumnHeadingsException( Throwable causal, File file )
{
	this( (String)null, causal, (File)file );
}

/**
  * @param message describes what went wrong.
  * @param causal what caused this exception??
  * @param file the file that the error occurred in.
  */
public NoColumnHeadingsException( String message, Throwable causal )
{
	this( message, causal, (File)null );
}

/**
  * @param message describes what went wrong.
  * @param causal what caused this exception??
  * @param file the file that the error occurred in.
  */
public NoColumnHeadingsException( String message, Throwable causal, File file )
{
	super( message, causal, file );
}

}
