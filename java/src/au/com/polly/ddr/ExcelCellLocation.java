package au.com.polly.ddr;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a location within an excel worksheet. It does not include the
 * worksheet identifier. Things peculiar to an excel location;
 * rows start at 1, not 0. we store internally from 0.
 * column specifiers are letters, but are not strictly a base 26 number using
 * letters as digits. All locations are absolute, not relative.
 *
 */
public class ExcelCellLocation
{
static Pattern locationPattern = Pattern.compile( "^([a-zA-Z]{1,3})(\\d{1,5})$" );
private int row;
private int column;
private String excelLocation;
private boolean updated = false;

/**
 *
 * @param location cell location in excell notation format (eg; AB535)
 */
public ExcelCellLocation( String location )
{
    Matcher locationMatcher = locationPattern.matcher( location.toLowerCase() );
    int column;

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

/**
 *
 * @param row identifies the row in the worksheet, with first row being number zero.
 * @param column identifies the column in the worksheet, with first column being number zero.
 */
public ExcelCellLocation( int row, int column )
{
    setRow( row );
    setColumn( column );
}

/**
 *
 * @return a duplicate of the location object that this method is invoked upon.
 */
public ExcelCellLocation copy()
{
    return new ExcelCellLocation( getRow(), getColumn()  );
}

@Override
public boolean equals( Object other )
{
    boolean result = false;
    ExcelCellLocation b;

    do {
        if ( ! ( other instanceof ExcelCellLocation ) )
        {
            break;
        }

        b = (ExcelCellLocation)other;

        result = ( this.getColumn() == b.getColumn() ) && ( this.getRow() == b.getRow() );

    } while( false );

    return result;
}

@Override
public int hashCode()
{
    int result;
    result = this.getColumn() * ( 26 * 26 * 27 );
    result += this.getRow();
    return result;
}

public void moveRight()
{
    moveRight( 1 );
}

public void moveRight( int cols )
{
    this.column += cols;
    updated = false;
}

public void moveDown()
{
    moveDown( 1 );
}

public void moveDown( int rows )
{
    this.row += rows;
    updated = false;
}

public void moveLeft()
{
    moveLeft( 1 );
}

public void moveLeft( int cols )
{
    if ( this.column > cols )
    {
        this.column -= cols;
    } else {
        this.column = 0;
    }
    updated = false;

}

public void moveUp()
{
    moveUp( 1 );
}

public void moveUp( int rows )
{
    if ( this.row > rows )
    {
        this.row -= rows;
        updated = false;
    } else {
        this.row = 0;
    }
    updated = false;
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
}

public String toString()
{
    String result;
    StringBuilder out = new StringBuilder();

    update();
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
        char khar = Character.toLowerCase(specifier.charAt(i));
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
    StringBuilder out = new StringBuilder();

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
