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

import junit.framework.JUnit4TestAdapter;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 *
 * @author Dave Young
 *
 */

public class StreamParserTest
{
private static Logger logger = Logger.getLogger( StreamParserTest.class );

	ByteArrayInputStream is = null;
	StreamParser parser = null;
	byte[] buff = ("rec__id,User Name,option\n" +
		"1,tester ${option},0401985984\n" +
		"2,shaun ${option},0411742598\n"
		).getBytes();

	FieldAliases aliases[] = {
		new FieldAliases("name",false,new String [] {"name","User Name"}),
		new FieldAliases("id",false,new String [] {"id","rec__id"})
	};

    @Before
	public void setUp() throws Exception
    {
		is = new ByteArrayInputStream(buff);
		parser = new StreamParser(is,aliases);
		parser.interrogateColumnHeadings();
	}

    @Test
	public void testSubstitutionFieldDelimitersCorrectlySet() {
		String field = "*!User Name* field";
		parser.setSubsitutionFieldStart("*!");
		parser.setSubsitutionFieldEnd("*");

		String result = parser._getNextSubstitutionField(field);

		if ( logger.isDebugEnabled() ) {
			logger.debug("result = ["+result+"]");
		}

		assertEquals("name",result);
	}

    @Test
	public void testSubstitutionFieldDelimitersIncorrectlySet() {
		String field = "*!User Name* field";

		String result = parser._getNextSubstitutionField(field);

		if ( logger.isDebugEnabled() ) {
			logger.debug("result = ["+result+"]");
		}

		assertNull(result);
	}

    @Test
	public void testGetNextSubstitutionFieldWithSubstitutionFieldInString() {
		String field = "${User Name} field";
		String result = parser._getNextSubstitutionField(field);

		if ( logger.isDebugEnabled() ) {
			logger.debug("result = ["+result+"]");
		}

		assertEquals("name",result);
	}

    @Test
	public void testGetNextSubstitutionFieldWithNullString() {
		String result = "";
		try {
			result = parser._getNextSubstitutionField(null);
		} catch (Exception e) {
			if ( logger.isDebugEnabled() ) { logger.debug( e ); }
			fail(e.getMessage());
		}

		assertNull(result);
	}

	public void testGetNextSubstitutionFieldWithEmptyString() {
		String field = "";
		String result = parser._getNextSubstitutionField(field);

		if ( logger.isDebugEnabled() ) {
			logger.debug("result = ["+result+"]");
		}

		assertNull(result);
	}

	public void testGetNextSubstitutionFieldWithNormalString() {
		String field = "field";
		String result = parser._getNextSubstitutionField(field);

		if ( logger.isDebugEnabled() ) {
			logger.debug("result = ["+result+"]");
		}

		assertNull(result);
	}

	public void testDoSubstitutionWithSimpleSubstitution() {
		HashMap record = new HashMap();

		record.put("id","rec ${option}");
		record.put("name","tom");
		record.put("option","1");

		try {
			parser._doSubstitution("id",record);
		} catch (Exception e) {
			if ( logger.isDebugEnabled() ) {e.printStackTrace(System.err);}
			fail(e.getMessage());
		}

		if ( logger.isDebugEnabled() ) {
			logger.debug("record.get(\"id\") = ["+record.get("id")+"]");
			logger.debug("record.get(\"name\") = ["+record.get("name")+"]");
			logger.debug("record.get(\"option\") = ["+record.get("option")+"]");
		}

		assertEquals("rec 1", record.get("id"));
		assertEquals("tom", record.get("name"));
		assertEquals("1", record.get("option"));
	}

	public void testDoSubstitutionWithCascadingSubstitution() {
		HashMap record = new HashMap();

		record.put("id","rec ${User Name}");
		record.put("name","${option} : tom");
		record.put("option","1");

		try {
			parser._doSubstitution("id",record);
		} catch (Exception e) {
			if ( logger.isDebugEnabled() ) {e.printStackTrace(System.err);}
			fail(e.getMessage());
		}

		if ( logger.isDebugEnabled() ) {
			logger.debug("record.get(\"id\") = ["+record.get("id")+"]");
			logger.debug("record.get(\"name\") = ["+record.get("name")+"]");
			logger.debug("record.get(\"option\") = ["+record.get("option")+"]");
		}

		assertEquals("rec 1 : tom", record.get("id"));
		assertEquals("1 : tom", record.get("name"));
		assertEquals("1", record.get("option"));
	}

	public void testDoSubstitutionWithRecursiveSubstitution() {
		HashMap record = new HashMap();

		record.put("id","rec ${User Name}");
		record.put("name","${option} : tom");
		record.put("option","1 : ${User Name}");

		try {
			parser._doSubstitution("id",record);
			fail("Expected a InvalidSubstitutionConstructException");
		} catch (InvalidSubstitutionConstructException e) {
			if ( logger.isDebugEnabled() ) {e.printStackTrace(System.err);}
			assertEquals("A recursive substitution has been found @ line1",e.getMessage());
		} catch (Exception e) {
			if ( logger.isDebugEnabled() ) {e.printStackTrace(System.err);}
			fail(e.getMessage());
		}

		if ( logger.isDebugEnabled() ) {
			logger.debug("record.get(\"id\") = ["+record.get("id")+"]");
			logger.debug("record.get(\"name\") = ["+record.get("name")+"]");
			logger.debug("record.get(\"option\") = ["+record.get("option")+"]");
		}

		assertEquals("rec ${User Name}", record.get("id"));
		assertEquals("${option} : tom", record.get("name"));
		assertEquals("1 : ${User Name}", record.get("option"));
	}

	public void testDoSubstitutionWithNoSubstitution() {
		HashMap record = new HashMap();

		record.put("id","rec");
		record.put("name","tom");
		record.put("option","1");

		try {
			parser._doSubstitution("id",record);
		} catch (Exception e) {
			if ( logger.isDebugEnabled() ) {e.printStackTrace(System.err);}
			fail(e.getMessage());
		}

		if ( logger.isDebugEnabled() ) {
			logger.debug("record.get(\"id\") = ["+record.get("id")+"]");
			logger.debug("record.get(\"name\") = ["+record.get("name")+"]");
			logger.debug("record.get(\"option\") = ["+record.get("option")+"]");
		}

		assertEquals("rec", record.get("id"));
		assertEquals("tom", record.get("name"));
		assertEquals("1", record.get("option"));
	}

	public void testDoSubstitutionWithNullFieldValue() {
		HashMap record = new HashMap();

		record.put("id","rec ${option}");
		record.put("name","tom");
		record.put("option",null);

		try {
			parser._doSubstitution("id",record);
		} catch (Exception e) {
			if ( logger.isDebugEnabled() ) {e.printStackTrace(System.err);}
			fail(e.getMessage());
		}

		if ( logger.isDebugEnabled() ) {
			logger.debug("record.get(\"id\") = ["+record.get("id")+"]");
			logger.debug("record.get(\"name\") = ["+record.get("name")+"]");
			logger.debug("record.get(\"option\") = ["+record.get("option")+"]");
		}

		assertEquals("rec ", record.get("id"));
		assertEquals("tom", record.get("name"));
		assertNull(record.get("option"));
	}

	public void testDoSubstitutionWithNullField() {
		HashMap record = new HashMap();

		record.put("id","rec ${option}");
		record.put("name","tom");
		record.put("option","1");

		try {
			parser._doSubstitution(null,record);
		} catch (Exception e) {
			if ( logger.isDebugEnabled() ) {e.printStackTrace(System.err);}
			fail(e.getMessage());
		}

		if ( logger.isDebugEnabled() ) {
			logger.debug("record.get(\"id\") = ["+record.get("id")+"]");
			logger.debug("record.get(\"name\") = ["+record.get("name")+"]");
			logger.debug("record.get(\"option\") = ["+record.get("option")+"]");
		}

		assertEquals("rec ${option}", record.get("id"));
		assertEquals("tom", record.get("name"));
		assertEquals("1", record.get("option"));
	}

	public void testDoSubstitutionWithEmptyField() {
		HashMap record = new HashMap();

		record.put("id","rec ${option}");
		record.put("name","tom");
		record.put("option","1");

		try {
			parser._doSubstitution("",record);
		} catch (Exception e) {
			if ( logger.isDebugEnabled() ) {e.printStackTrace(System.err);}
			fail(e.getMessage());
		}

		if ( logger.isDebugEnabled() ) {
			logger.debug("record.get(\"id\") = ["+record.get("id")+"]");
			logger.debug("record.get(\"name\") = ["+record.get("name")+"]");
			logger.debug("record.get(\"option\") = ["+record.get("option")+"]");
		}

		assertEquals("rec ${option}", record.get("id"));
		assertEquals("tom", record.get("name"));
		assertEquals("1", record.get("option"));
	}

	public void testDoSubstitutionsWithSimpleSubstitution() {
		HashMap record = new HashMap();

		record.put("id","rec ${option} - ${User Name}");
		record.put("name","tom");
		record.put("option","1");

		try {
			parser._doFieldSubstitutions(record);
		} catch (Exception e) {
			if ( logger.isDebugEnabled() ) {e.printStackTrace(System.err);}
			fail(e.getMessage());
		}

		if ( logger.isDebugEnabled() ) {
			logger.debug("record.get(\"id\") = ["+record.get("id")+"]");
			logger.debug("record.get(\"name\") = ["+record.get("name")+"]");
			logger.debug("record.get(\"option\") = ["+record.get("option")+"]");
		}

		assertEquals("rec 1 - tom", record.get("id"));
		assertEquals("tom", record.get("name"));
		assertEquals("1", record.get("option"));
	}

	public void testDoSubstitutionsWithCascadingSubstitution() {
		HashMap record = new HashMap();

		record.put("id","rec ${User Name}");
		record.put("name","${option} : tom");
		record.put("option","1");

		try {
			parser._doFieldSubstitutions(record);
		} catch (Exception e) {
			if ( logger.isDebugEnabled() ) {e.printStackTrace(System.err);}
			fail(e.getMessage());
		}

		if ( logger.isDebugEnabled() ) {
			logger.debug("record.get(\"id\") = ["+record.get("id")+"]");
			logger.debug("record.get(\"name\") = ["+record.get("name")+"]");
			logger.debug("record.get(\"option\") = ["+record.get("option")+"]");
		}

		assertEquals("rec 1 : tom", record.get("id"));
		assertEquals("1 : tom", record.get("name"));
		assertEquals("1", record.get("option"));
	}

	public void testDoSubstitutionsWithRecursiveSubstitution() {
		HashMap record = new HashMap();

		record.put("id","rec ${User Name}");
		record.put("name","${option} : tom");
		record.put("option","1 : ${User Name}");

		try {
			parser._doFieldSubstitutions(record);
		} catch (Exception e) {
			if ( logger.isDebugEnabled() ) {e.printStackTrace(System.err);}
			fail(e.getMessage());
		}

		if ( logger.isDebugEnabled() ) {
			logger.debug("record.get(\"id\") = ["+record.get("id")+"]");
			logger.debug("record.get(\"name\") = ["+record.get("name")+"]");
			logger.debug("record.get(\"option\") = ["+record.get("option")+"]");
		}

		assertEquals("rec ${User Name}", record.get("id"));
		assertEquals("${option} : tom", record.get("name"));
		assertEquals("1 : ${User Name}", record.get("option"));
	}

	public void testDoSubstitutionsWithNoSubstitution() {
		HashMap record = new HashMap();

		record.put("id","rec");
		record.put("name","tom");
		record.put("option","1");

		try {
			parser._doFieldSubstitutions(record);
		} catch (Exception e) {
			if ( logger.isDebugEnabled() ) {e.printStackTrace(System.err);}
			fail(e.getMessage());
		}

		if ( logger.isDebugEnabled() ) {
			logger.debug("record.get(\"id\") = ["+record.get("id")+"]");
			logger.debug("record.get(\"name\") = ["+record.get("name")+"]");
			logger.debug("record.get(\"option\") = ["+record.get("option")+"]");
		}

		assertEquals("rec", record.get("id"));
		assertEquals("tom", record.get("name"));
		assertEquals("1", record.get("option"));
	}

	public void testDoSubstitutionsWithNullFieldValue() {
		HashMap record = new HashMap();

		record.put("id","rec ${option}");
		record.put("name","tom");
		record.put("option",null);

		try {
			parser._doFieldSubstitutions(record);
		} catch (Exception e) {
			if ( logger.isDebugEnabled() ) {e.printStackTrace(System.err);}
			fail(e.getMessage());
		}

		if ( logger.isDebugEnabled() ) {
			logger.debug("record.get(\"id\") = ["+record.get("id")+"]");
			logger.debug("record.get(\"name\") = ["+record.get("name")+"]");
			logger.debug("record.get(\"option\") = ["+record.get("option")+"]");
		}

		assertEquals("rec ", record.get("id"));
		assertEquals("tom", record.get("name"));
		assertNull(record.get("option"));
	}

	public void testSuccessfulRun() {
		is = new ByteArrayInputStream(buff);
		parser = new StreamParser(is,aliases);

		if ( logger.isDebugEnabled() ) {
			parser.addListener( new ParserListener() {
				public void processRecord( Parser parser, HashMap data,
						Object auxillary, boolean error )
				{
					if ( error ) {
						logger.debug( "--> error condition" );
						logger.debug( parser.getError().getClass().getName() + " - " + parser.getError().getMessage() );
						parser.getError().printStackTrace( System.err );

						return;
					}

					if ( data == null ) {
						logger.debug( "--> null record... isError()=" + parser.isError() + ", isFinished()=" + parser.isFinished() );
						logger.debug( "--> special null record ... we have finished reading in the data" );
					} else {
						logger.debug( data );
					}

					return;
				}}
			);
		}

		parser.run();

		assertTrue(parser.isFinished());

		if ( logger.isDebugEnabled() ) {
			logger.debug(parser.getErrors());
		}
		assertTrue(parser.getErrors()!=null);
		assertTrue(parser.getErrors().size()==0);

		if( logger.isDebugEnabled() && parser.isError()) {
			logger.debug(parser.getError().getMessage());
		}
		assertTrue(!parser.isError());
	}

	public void testUnsuccessfulRun1() {
		byte[] buff = ("rec__id,User Name,option\n" +
				"1,tester ${option},${User Name}0401985984\n" +
				"2,shaun ${option},0411742598\n"
			).getBytes();
		is = new ByteArrayInputStream(buff);
		parser = new StreamParser(is,aliases);

		if ( logger.isDebugEnabled() ) {
			parser.addListener( new ParserListener() {
				public void processRecord( Parser parser, HashMap data,
						Object auxillary, boolean error )
				{
					if ( error ) {
						logger.debug( "--> error condition" );
						logger.debug( parser.getError().getClass().getName() + " - " + parser.getError().getMessage() );
						parser.getError().printStackTrace( System.err );
						return;
					}
					if ( data == null ) {
						logger.debug( "--> null record... isError()=" + parser.isError() + ", isFinished()=" + parser.isFinished() );
						logger.debug( "--> special null record ... we have finished reading in the data" );
					} else {
						logger.debug( data );
					}

					return;
				}}
			);
		}

		parser.run();

		assertTrue(parser.isFinished());

		if ( logger.isDebugEnabled() ) {
			logger.debug(parser.getErrors());
		}
		assertTrue(parser.getErrors()!=null);
		assertTrue(parser.getErrors().size()==2);

		if( logger.isDebugEnabled() && parser.isError()) {
			logger.debug(parser.getError().getMessage());
		}
		assertTrue(!parser.isError());
	}

	public void testUnsuccessfulRun2() {
		byte[] buff = ("rec__id,User Name,option\n" +
				"1 ${rec__id},tester ${option},0401985984\n" +
				"2,shaun ${option},0411742598\n"
			).getBytes();
		is = new ByteArrayInputStream(buff);
		parser = new StreamParser(is,aliases);

		if ( logger.isDebugEnabled() ) {
			parser.addListener( new ParserListener() {
				public void processRecord( Parser parser, HashMap data,
						Object auxillary, boolean error )
				{
					if ( error ) {
						logger.debug( "--> error condition" );
						logger.debug( parser.getError().getClass().getName() + " - " + parser.getError().getMessage() );
						parser.getError().printStackTrace( System.err );
						return;
					}
					if ( data == null ) {
						logger.debug( "--> null record... isError()=" + parser.isError() + ", isFinished()=" + parser.isFinished() );
						logger.debug( "--> special null record ... we have finished reading in the data" );
					} else {
						logger.debug( data );
					}

					return;
				}}
			);
		}

		parser.run();

		assertTrue(parser.isFinished());

		if ( logger.isDebugEnabled() ) {
			logger.debug(parser.getErrors());
		}
		assertTrue(parser.getErrors()!=null);
		assertTrue(parser.getErrors().size()==1);

		if( logger.isDebugEnabled() && parser.isError()) {
			logger.debug(parser.getError().getMessage());
		}
		assertTrue(!parser.isError());
	}

public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( StreamParserTest.class );
}
}