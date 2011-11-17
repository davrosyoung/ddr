package au.com.polly.util;

import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Battery of tests for the ReadInput class.
 */
@RunWith(JUnit4.class)
public class ReadInputTest
{
StringReader stringReader;
StringWriter stringWriter;

@Before
public void setup()
{
//    ReadInput.setReader( );
}

@Test
public void testReadingTextWithNoPrompt()
{
    Reader stringReader = new StringReader( "Davros" );
    StringWriter stringWriter = new StringWriter();
    PrintWriter consoleWriter = new PrintWriter( stringWriter );
    ReadInput.setReader( stringReader );
    ReadInput.setConsoleWriter( consoleWriter );
    String input;

    input = ReadInput.readLine();
    assertNotNull( input );
    assertEquals( "Davros", input );
    assertNotNull( stringWriter.toString() );
    assertEquals( "?", stringWriter.toString() );
}

@Test
public void testReadingTextWithPrompt()
{
    Reader stringReader = new StringReader( "Davros" );
    StringWriter stringWriter = new StringWriter();
    PrintWriter consoleWriter = new PrintWriter( stringWriter );
    ReadInput.setReader( stringReader );
    ReadInput.setConsoleWriter( consoleWriter );
    String input;

    input = ReadInput.readLine( "What is your name?" );
    assertNotNull( input );
    assertEquals( "Davros", input );
    assertNotNull( stringWriter.toString() );
    assertEquals( "What is your name?", stringWriter.toString() );
}


@Test
public void testReadingNoTextWithDefault()
{
    Reader stringReader = new StringReader( "" );
    StringWriter stringWriter = new StringWriter();
    PrintWriter consoleWriter = new PrintWriter( stringWriter );
    ReadInput.setReader( stringReader );
    ReadInput.setConsoleWriter( consoleWriter );
    String input;

    input = ReadInput.readLine( "What is your name?", "Alan" );
    assertNotNull( input );
    assertEquals( "Alan", input );
    assertNotNull( stringWriter.toString() );
    assertEquals( "What is your name?", stringWriter.toString() );
}

@Test
public void testReadingTextWithDefault()
{
    Reader stringReader = new StringReader( "Foolos" );
    StringWriter stringWriter = new StringWriter();
    PrintWriter consoleWriter = new PrintWriter( stringWriter );
    ReadInput.setReader( stringReader );
    ReadInput.setConsoleWriter( consoleWriter );
    String input;

    input = ReadInput.readLine( "What is your name?", "Alan" );
    assertNotNull( input );
    assertEquals( "Foolos", input );
    assertNotNull( stringWriter.toString() );
    assertEquals( "What is your name?", stringWriter.toString() );
}

@Test
public void testReadingIntegerNoPrompt()
{
    Reader stringReader = new StringReader( "42" );
    StringWriter stringWriter = new StringWriter();
    PrintWriter consoleWriter = new PrintWriter( stringWriter );
    ReadInput.setReader( stringReader );
    ReadInput.setConsoleWriter( consoleWriter );
    int input;

    input = ReadInput.readInteger();
    assertEquals( 42, input );
    assertNotNull( stringWriter.toString() );
    assertEquals( "?", stringWriter.toString() );
}

@Test
public void testReadingIntegerWithPrompt()
{
    Reader stringReader = new StringReader( "42" );
    StringWriter stringWriter = new StringWriter();
    PrintWriter consoleWriter = new PrintWriter( stringWriter );
    ReadInput.setReader( stringReader );
    ReadInput.setConsoleWriter( consoleWriter );
    int input;

    input = ReadInput.readInteger( "What is the answer to life, the universe and everything?" );
    assertEquals( 42, input );
    assertNotNull( stringWriter.toString() );
    assertEquals( "What is the answer to life, the universe and everything?", stringWriter.toString() );
}

@Test
public void testReadingIntegerWithInvalidInputAndDefaultValue()
{
    Reader stringReader = new StringReader( "forty two" );
    StringWriter stringWriter = new StringWriter();
    PrintWriter consoleWriter = new PrintWriter( stringWriter );
    ReadInput.setReader( stringReader );
    ReadInput.setConsoleWriter( consoleWriter );
    int input;

    input = ReadInput.readInteger( "What is the answer to life, the universe and everything?", 42 );
    assertEquals( 42, input );
    assertNotNull( stringWriter.toString() );
    assertEquals( "What is the answer to life, the universe and everything?--> invalid input [forty two]. please enter integer value.\nWhat is the answer to life, the universe and everything?", stringWriter.toString() );
}

@Test
public void testReadingOutOfBoundsIntegerWithDefaultValue()
{
    Reader stringReader = new StringReader( "34\n43\n42" );
    StringWriter stringWriter = new StringWriter();
    PrintWriter consoleWriter = new PrintWriter( stringWriter );
    ReadInput.setReader( stringReader );
    ReadInput.setConsoleWriter( consoleWriter );
    int input;

    input = ReadInput.readInteger( "What is your temperature?", 37, 35, 42 );
    assertEquals( 42, input );
    assertNotNull( stringWriter.toString() );
    assertEquals( "What is your temperature?--> value 34 out of range, must be between 35 & 42.\nWhat is your temperature?--> value 43 out of range, must be between 35 & 42.\nWhat is your temperature?", stringWriter.toString() );
}


public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( ReadInputTest.class );
}


}
