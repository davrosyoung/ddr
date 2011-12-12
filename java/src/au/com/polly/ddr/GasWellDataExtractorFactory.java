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
 * @return
 */
public GasWellDataExtractor getJavaSerializedObjectExtractor( ObjectInputStream serializedObjectInputStream )
{
    return getJavaSerializedObjectExtractor( null );
}

/**
 *
 * @param serializedObjectInputStream stream containing java serialized object(s). we are expecting this
 *                                    stream to contain a List&lt;GasWellDataSet&gt;
 *
 * @param ids one or more gas well names to be extracted. if a null list is provided, then data for ALL wells will
 *            be returned.
 * @return extractor from which the gas well data sets may be extracted.
 */
public GasWellDataExtractor getJavaSerializedObjectExtractor( ObjectInputStream serializedObjectInputStream, String[] ids )
{
    return null;
}


/**
 *
 * @param spreadsheet the spreadsheet from which the data is to be extracted.
 * @return  extractor from which the gas well data sets may be extracted.
 */
public GasWellDataExtractor getExcelStandardizedGasWellDataExtractor( Workbook spreadsheet )
{
    return getExcelStandardizedGasWellDataExtractor( null );
}

/**
 *
 * @param spreadsheet the spreadsheet from which the data is to be extracted.
 * @param ids one or more gas well names to be extracted. if a null list is provided, then data for ALL wells will
 *            be returned.
 * @return   extractor from which the gas well data sets may be extracted.
 */
public GasWellDataExtractor getExcelStandardizedGasWellDataExtractor( Workbook spreadsheet, String[] ids )
{
    return null;
}


/**
 *
 * @param is   input stream containing gas well data in log ascii standard v3.0 format.
 *
 * @return   extractor from which the gas well data sets may be extracted.
 */
public GasWellDataExtractor getLASGasWellDataExtractor( InputStream is )
{
    return getLASGasWellDataExtractor( null );
}

/**
 *
 * @param is   input stream containing gas well data in log ascii standard v3.0 format.
 *  @param ids one or more gas well names to be extracted. if a null list is provided, then data for ALL wells will
 *            be returned.
 * @return   extractor from which the gas well data sets may be extracted.
 */
public GasWellDataExtractor getLASGasWellDataExtractor( InputStream is, String[] ids )
{
    return null;
}

}
