package au.com.polly.ddr;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 12/11/11
 * Time: 12:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExcelCellLocation
{
static Pattern locationPattern = Pattern.compile( "^([a-zA-Z]{1,3})(\\d{1,5})$" );
int row;
int column;
String excelLocation;
boolean updated = false;

public ExcelCellLocation( String location )
{
    Matcher locationMatcher = locationPattern.matcher( location.toLowerCase() );
    int column = 0;

    if ( locationMatcher.matches() )
    {
        String columnSpecifier = locationMatcher.group( 1 );
        String rowSpecifier = locationMatcher.group( 2 );
        setRow( Integer.parseInt( rowSpecifier ) - 1 );
        column = decodeExcelColumnSpecifier( columnSpecifier );
        setColumn( column );
    } else {
        throw new IllegalArgumentException( "Unable to decipher cell location specifier [" + location + "]" );
    }
}

public ExcelCellLocation( int row, int column )
{
    setRow( row );
    setColumn( column );
}

public int getRow()
{
    return this.row;
}

public void setRow(int row)
{
    this.row = row;
    updated = false;
}

public int getColumn()
{
    return this.column;
}

public void setColumn(int column)
{
    this.column = column;
    updated = false;
}

protected void update()
{
    if ( ! updated )
    {
        excelLocation = obtainExcelColumnSpecifier( getColumn() ) + Integer.toString( getRow() + 1 );
        updated = true;
    }
    return;
}

public String toString()
{
    String result;
    StringBuffer out = new StringBuffer();

    if ( !updated )
    {
        update();
    }

    out.append( excelLocation );

    result = out.toString();

    return result;
}

protected static int decodeExcelColumnSpecifier( String specifier )
{
    int multiplier = 1;
    int column = 0;
    int digit;
    for( int i = specifier.length() - 1; i >= 0; i-- )
    {
        char khar = Character.toLowerCase( specifier.charAt( i ) );
        digit = ( i == specifier.length() - 1 ) ? ( khar - 'a' ) : ( khar - 'a' ) + 1;
        column += digit * multiplier;
        multiplier *= 26;
    }
    return column;
}

/**
 * Produce a string which matches a column specifier as used in
 * microsoft excel.
 *
 *
 * @param column where first column is zero!!
 * @return excel column specifier
 */
protected static String obtainExcelColumnSpecifier( int column )
{
    final int factor = 26;
    int x = column;
    List<Integer> a = new ArrayList<Integer>();
    StringBuffer out = new StringBuffer();

    // start with the small digits and work up......
    // -----------------------------------------------
    a.add( x % factor );
    while( x >= factor )
    {
        x = x - factor;
        x = x / factor;
        a.add( x % factor );
    }

    // output the column letters in the correct order...
    // --------------------------------------------------
    for( int i = a.size() - 1; i>= 0; i-- )
    {
        out.append( (char)('A' + a.get( i ) ) );
    }

    return out.toString();

}


/**
 * Produce a string which matches a column specifier as used in
 * microsoft excel.
 *
 *
 * @param column where first column is zero!!
 * @return excel column specifier
 */
protected static String obtainColumnSpecifier( int column )
{
    final int factor = 10;
    int x = column;
    List<Integer> a = new ArrayList<Integer>();
    StringBuffer out = new StringBuffer();

    // start with the small digits and work up......
    // -----------------------------------------------
    a.add( x % factor );
    while( x >= factor )
    {
        x = x - factor;
        x = x / factor;
        a.add( x % factor );
    }

    // output the column letters in the correct order...
    // --------------------------------------------------
    for( int i = a.size() - 1; i>= 0; i-- )
    {
        out.append( (char)('A' + a.get( i ) ) );
    }

    return out.toString();
}


}
