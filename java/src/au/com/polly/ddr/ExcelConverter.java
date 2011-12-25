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

package au.com.polly.ddr;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility routines to aid in converting excel data.
 *
 */
public class ExcelConverter
{
//private final static Pattern dateFormatPattern = Pattern.compile( "^mm\\/dd\\/yy(yy)?.*" );

protected static Calendar dayDot;

static {
    dayDot = Calendar.getInstance();
    dayDot.set( Calendar.YEAR, 1899 );
    dayDot.set( Calendar.MONTH, Calendar.DECEMBER  );
    dayDot.set( Calendar.DAY_OF_MONTH, 31 );
    dayDot.set( Calendar.HOUR_OF_DAY, 0 );
    dayDot.set( Calendar.MINUTE, 0 );
    dayDot.set( Calendar.SECOND, 0 );
    dayDot.set( Calendar.MILLISECOND, 0 );
}

protected static Date convert( double xlDate )
{
    Calendar cal;
    Date result;

    cal = convertToCalendar( xlDate );
    result = cal.getTime();
    return result;
}

protected static Calendar convertToCalendar( double xlDate )
{
    int days =(int) Math.floor( xlDate );
    int seconds = (int)Math.round( ( xlDate - days ) * 86400 );

    // excel incorrectly considers 1900 a leap year. which means that it incorrectly
    // matches serial day 60 as february 29th 1900, when it should be 1st march 1900.
    // so, if we've been given a day after 29th february 1900, we need to subtract
    // one day to make it match the real calendar, rather than bill gate's version.
    // ------------------------------------------------------------------------------
    if ( days > 60 ) { days--; }

    Calendar result = (Calendar) dayDot.clone();
    result.add( Calendar.DATE, days );
    result.add( Calendar.SECOND, seconds );
    // force recomputation of fields...
    result.getTime();

    return result;
}
/**
 *
 * @param type the type of cell
 * @return human readable version of the cell type.
 */
public static String cellTypeName( int type )
{
    String result = "<unknown>";

    switch( type )
    {
        case Cell.CELL_TYPE_BLANK:
            result = "BLANK";
            break;

        case Cell.CELL_TYPE_BOOLEAN:
            result="BOOLEAN";
            break;

        case Cell.CELL_TYPE_ERROR:
            result="ERROR";
            break;

        case Cell.CELL_TYPE_FORMULA:
            result="FORMULA";
            break;

        case Cell.CELL_TYPE_NUMERIC:
            result="NUMERIC";
            break;

        case Cell.CELL_TYPE_STRING:
            result="STRING";
            break;
    }
    return result;
}

/**
 *
 * @param cell excel cell to be interrogated.
 * @return a human readable version of the cell contents including it's type
 */
public static String cellContents( Cell cell )
{
    StringBuilder out = new StringBuilder();
    out.append( cellTypeName( cell.getCellType() ) );
    switch( cell.getCellType() )
    {
        case Cell.CELL_TYPE_STRING:
            out.append( " - \"" + cell.getStringCellValue() + "\"" );
            break;

        case Cell.CELL_TYPE_NUMERIC:
            out.append( " - " + cell.getNumericCellValue() );
            break;

        case Cell.CELL_TYPE_BOOLEAN:
            out.append( " - " );
            out.append( cell.getBooleanCellValue() ? "<TRUE>" : "<FALSE>" );
            break;
        case Cell.CELL_TYPE_BLANK:
        case Cell.CELL_TYPE_ERROR:
        default:
            out.append( " - <NULL>" );
            break;
    }

    return out.toString();
}


/**
 *
 * @param cell excel cell to be interrogated for a date/time value.
 * @return if the cell contains a date, then this method will extract it, if not, then a
 * NULL result is returned.
 */
public static Date extractDateFromCell( Cell cell )
{
    Date result = null;
    Matcher dateFormatMatcher;

    if ( ( cell != null ) && ( cell.getCellType() == Cell.CELL_TYPE_NUMERIC ) )
    {
        CellStyle style = cell.getCellStyle();
        String format = style.getDataFormatString().toLowerCase();
        if ( format.contains( "mm" ) && format.contains( "dd" ) && format.contains( "yy" ) )
        {
            result = ExcelConverter.convert( cell.getNumericCellValue() );
        }
    }

        /* old way
        dateFormatMatcher = dateFormatPattern.matcher( format );
        if ( dateFormatMatcher.matches() )
        {
        }
        */

    return result;
}


}
