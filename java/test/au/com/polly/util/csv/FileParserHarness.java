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

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Iterator;

public class FileParserHarness implements ParserListener
{
protected static Logger logger = Logger.getLogger( FileParserHarness.class );

 public static FieldAliases[] aliases= {
    new FieldAliases( "firstName", new String [] {
                    "first name",
                    "First Name"
                    } ),
    new FieldAliases( "familyName", new String [] {
                    "family name",
                    "Family Name",
                    "last name",
                    "surname",
                    "Last Name"
                    } ),
    new FieldAliases( "mobileNumber", new String [] {
                    "mobile number",
                    "cellur number",
                    "mob",
                    "cell phone",
                    "hand phone"
                    } ),
  };

private String			path;
private int				line;
private	String[]		columnLUT	= null;
private	BufferedReader	reader		= null;
private	LineSplitter	splitter	= null;


public static void main( String[] argv )
{
	String				path		=	null;
	FileParserHarness harness		=	null;

    if ( argv.length > 0 )
    {
        path = argv[ 0 ];
    } else {
        path = "test_data/sample.csv";
    }

	harness = new FileParserHarness( path, FileParserHarness.aliases );
}

  public FileParserHarness( String path, FieldAliases[] aliases )
  {
    FileParser daley 		= null;		//	as in Arthur Daley, the infamous
                      //	importer/exporter from the
                      //	TV series "Minder".
    Thread		runner		=	null;


    try {
      daley = new FileParser( path, FileParserHarness.aliases, this, null );
      runner	= new Thread( daley );
      System.out.println( "---> parser starts.." );
      runner.start();
    } catch( Exception e ) {
      System.err.println( "Sorry, could not open [" + path + "]" );
      System.exit( 1 );
    }

    System.out.println( "---> All over red rover." );
  }

/**

  *
  */
public void processRecord( Parser parser, HashMap record, Object zusaetzlich, boolean error )
{
	Iterator	matilda	=	null;
	String		key		=	null;
	String		data	=	null;

	if ( parser.getFile() != null )
	{
		logger.info("File: " + parser.getFile().getAbsolutePath() + ", line:" + parser.getLineNumber());
	} else {
		logger.info("Line:" + parser.getLineNumber());
 	}

	if ( record != null )
	{

		logger.info("---------------< start of record  " + parser.getRecordNumber() + " >----------------");

		matilda = record.keySet().iterator();
		while( matilda.hasNext() )
		{
			key		= (String) matilda.next();
			data	= (String) record.get( key );
			logger.info("record{" + key + "}=[" + data + "]");
		}

		logger.info("---------------< end of record " + parser.getRecordNumber() + " >----------------");
	}
}


}
