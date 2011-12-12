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

import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import junit.framework.JUnit4TestAdapter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Exercise the excel date conversion routines. These tests are especially important because the
 * Bill Gate's calendar considers 1900 a leap year!! This means that dates after 28th March 1900
 * are represented by a number one larger than they actually should be!! Notice the difference
 * between the value used to test 28th february 1900 and 1st march 1900!! Also that we have
 * to supply a value of 367 to represent 1st january 1901 (366 larger than 1st january 1900,
 * as opposed to 365 larger!!). Thanks Bill!!
 *
 *
 */
@RunWith(JUnit4.class)
public class ExcelConverterTest
{

@Test
public void testConvertCellTypeName()
{
    assertEquals( "<unknown>", ExcelConverter.cellTypeName( -1 ) );
    assertEquals( "BLANK", ExcelConverter.cellTypeName( Cell.CELL_TYPE_BLANK ) );
    assertEquals( "BOOLEAN", ExcelConverter.cellTypeName( Cell.CELL_TYPE_BOOLEAN ) );
    assertEquals( "ERROR", ExcelConverter.cellTypeName( Cell.CELL_TYPE_ERROR ) );
    assertEquals( "FORMULA", ExcelConverter.cellTypeName( Cell.CELL_TYPE_FORMULA ) );
    assertEquals( "NUMERIC", ExcelConverter.cellTypeName( Cell.CELL_TYPE_NUMERIC ) );
    assertEquals( "STRING", ExcelConverter.cellTypeName( Cell.CELL_TYPE_STRING ) );
}

@Test
public void testGetNumericCellContents()
{
    Workbook book = WorkbookFactory.create();
    Cell cell = new XSSFCell();
}


public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( ExcelConverterTest.class );
}

}
