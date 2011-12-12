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

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * Creates excel workbook explorers.
 * 
 */
public class ExcelWorkbookExplorerFactory
{
static ExcelWorkbookExplorerFactory singleton = new ExcelWorkbookExplorerFactory();

/**
 * no public access to this constructor!!
 */
protected ExcelWorkbookExplorerFactory()
{
    
}

/**
 *
 * @return a factory which can be used to get explorers
 */
public static ExcelWorkbookExplorerFactory getInstance()
{
    return singleton;
}

public ExcelWorkbookExplorer createAllocationSheetExplorer( Sheet allocationSheet )
{
    AllocationSheetExplorer result;
    
    result = new AllocationSheetExplorer( allocationSheet );
    result.process();

    return result;
}

public ExcelWorkbookExplorer createExcelStandardizedWorkbookExplorer( Workbook spreadsheet )
{
    if ( spreadsheet == null )
    {
        throw new NullPointerException( "NULL spreadsheet specified. Must specify an actual spreadsheet!!" );
    }

    ExcelWorkbookExplorer result;

    result = new ExcelStandardizedWorkbookExplorer( spreadsheet );

    return null;
}


}
