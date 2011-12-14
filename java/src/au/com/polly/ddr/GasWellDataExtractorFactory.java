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

import org.apache.poi.ss.usermodel.Workbook;
import sun.net.idn.StringPrep;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class GasWellDataExtractorFactory
{
static GasWellDataExtractorFactory singleton = new GasWellDataExtractorFactory();


protected GasWellDataExtractorFactory()
{
    
}

/**
 *
 * @return the factory object which provides methods to create the actual extractors.
 */
public static GasWellDataExtractorFactory getInstance()
{
    return singleton;
}

/**
 *
 * @param serializedObjectInputStream stream containing java serialized object(s). we are expecting this
 *                                    stream to contain a List&lt;GasWellDataSet&gt;
 *
 * @return extractor from which the gas well data sets may be extracted.
 */
public GasWellDataExtractor getJavaSerializedObjectExtractor( ObjectInputStream serializedObjectInputStream )
throws IOException, ClassCastException, ClassNotFoundException
{
    GasWellDataExtractor result = new JavaSerializedGasWellDataExtractor( serializedObjectInputStream );
    return result;
}


/**
 *
 * @param spreadsheet the spreadsheet from which the data is to be extracted.
 * @return  extractor from which the gas well data sets may be extracted.
 */
public GasWellDataExtractor getExcelStandardizedGasWellDataExtractor( Workbook spreadsheet )
{
    if ( spreadsheet == null )
    {
        throw new NullPointerException( "NULL spreadsheet specified!!" );
    }
    
    if ( spreadsheet.getNumberOfSheets() == 0 )
    {
        throw new IllegalArgumentException( "Spreadsheet specified is EMPTY!!" );
    }

    return new ExcelStandardizedGasWellDataExtractor( spreadsheet );
}



/**
 *
 * @param is   input stream containing gas well data in log ascii standard v3.0 format.
 *
 * @return   extractor from which the gas well data sets may be extracted.
 */
public GasWellDataExtractor getLASGasWellDataExtractor( InputStream is )
{
    return null;
}

}
