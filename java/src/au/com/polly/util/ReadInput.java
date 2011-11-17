package au.com.polly.util;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Date;

/**
 * Utility classes for reading from the console.
 */
public class ReadInput
{
private static InputStream stream;
private static BufferedReader reader;
private static PrintWriter consoleWriter;


static {
    stream = System.in;
    reader = new BufferedReader( new InputStreamReader( System.in ) );
    consoleWriter = new PrintWriter( System.out );
}

public static void setReader( Reader replacementReader )
{
    if ( ! ( replacementReader instanceof BufferedReader ) )
    {
        ReadInput.reader = new BufferedReader( replacementReader );
    } else {
        ReadInput.reader = (BufferedReader)replacementReader;
    }
}

public static void setConsoleWriter( PrintWriter writer )
{
    consoleWriter = writer;
}

public static String readLine()
{
    return readLine( "?" );
}

public static String readLine( String prompt )
{
    return readLine( prompt, null );
}

public static String readLine( String prompt, String defaultInput )
{
    String input;

    try
    {
        consoleWriter.print( prompt );
        if ( ( input = reader.readLine() ) == null )
        {
            input = defaultInput;
        }
    } catch (IOException e) {
        input = defaultInput;
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }

    return input;
}

public static int readInteger()
{
    return readInteger( "?" );
}

public static int readInteger( String prompt )
{
    return readInteger( prompt, Integer.MIN_VALUE );
}

public static int readInteger( String prompt, int defaultValue )
{
    return readInteger( prompt, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE );
}

public static int readInteger( String prompt, int defaultValue, int min, int max )
{
    String textInput;
    int result = -1;
    boolean validInput = false;

    do {
        textInput = readLine( prompt, defaultValue > Integer.MIN_VALUE ? Integer.toString( defaultValue ) : null );
        if ( textInput != null )
        {
            try
            {
                result = Integer.parseInt( textInput );
                validInput = ( result >= min ) && ( result <= max );
                if ( !validInput )
                {
                    consoleWriter.println( "--> value " + result + " out of range, must be between " + min + " & " + max + "." );
                }
            } catch (NumberFormatException e)
            {
                consoleWriter.println( "--> invalid input [" + textInput + "]. please enter integer value." );
            }
        }
    } while( !validInput );

    return result;
}

public static double readFloat( String prompt )
{
    return readFloat( prompt, Double.MIN_VALUE );
}

public static double readFloat( String prompt, double defaultValue )
{
    return readFloat( prompt, defaultValue, Double.MIN_VALUE, Double.MAX_VALUE );
}

public static double readFloat( String prompt, double defaultValue, double min, double max )
{
    throw new IllegalStateException( "Not yet implemented!" );
}

public static Date readDateTime( String prompt )
{
    return readDateTime( prompt, null );
}

public static Date readDateTime( String prompt, Date defaultValue )
{
    return readDateTime( prompt, defaultValue, null, null );
}

public static Date readDateTime( String prompt, Date defaultValue, Date earliest, Date latest )
{
    throw new IllegalStateException( "Not yet implemented." );
}

}
