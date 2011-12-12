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

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 12/11/11
 * Time: 12:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExcelConverter
{
private final static Pattern dateFormatPattern = Pattern.compile( "^mm\\/dd\\/yy(yy)?.*" );

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
            result="NUMBER";
            break;

        case Cell.CELL_TYPE_STRING:
            result="STRING";
            break;
    }
    return result;
}

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
            out.append( "<NULL>" );
            break;
    }

    return out.toString();
}


public static Date extractDateFromCell( Cell cell )
{
    Date result = null;
    Matcher dateFormatMatcher;

    if ( ( cell != null ) && ( cell.getCellType() == Cell.CELL_TYPE_NUMERIC ) )
    {
        CellStyle style = cell.getCellStyle();
        String format = style.getDataFormatString();
        dateFormatMatcher = dateFormatPattern.matcher( format );
        if ( dateFormatMatcher.matches() )
        {
            result = ExcelDateConverter.getInstance().convert( cell.getNumericCellValue() );
        }
    }
    return result;
}


}
