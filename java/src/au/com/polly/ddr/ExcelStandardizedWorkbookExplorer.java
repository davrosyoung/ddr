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

import java.util.ArrayList;
import java.util.List;

/**
 * Used to extract gas well data sets from the standardized excel workbook format.
 *
 */
public class ExcelStandardizedWorkbookExplorer implements ExcelWorkbookExplorer
{
Workbook spreadsheet;
List<GasWellDataLocator> locations=null;

public ExcelStandardizedWorkbookExplorer( Workbook spreadsheet )
{
    this.spreadsheet = spreadsheet;    
    locations = new ArrayList<>();
}

public void process()
{
    Sheet worksheet = null;
    String sheetName = null;
    
    for( int i = 0; i < spreadsheet.getNumberOfSheets(); i++ )
    {
        worksheet = spreadsheet.getSheetAt( i );
        sheetName = worksheet.getSheetName();
        
    }
}

@Override
public List<GasWellDataLocator> getLocations()
{
    return null;  //To change body of implemented methods use File | Settings | File Templates.
}
}