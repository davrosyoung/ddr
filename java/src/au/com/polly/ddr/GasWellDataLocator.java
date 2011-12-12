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

/**
 * For a given gas well, specifies where the data is located within an excel worksheet.
 *
 *
 */
public class GasWellDataLocator
{
    private ExcelCellLocation wellCellLocation = null;
    private ExcelCellLocation oilCellLocation = null;
    private ExcelCellLocation gasCellLocation = null;
    private ExcelCellLocation waterCellLocation = null;
    private ExcelCellLocation condensateCellLocation = null;
    private String wellName = null;
    private int dateColumn = -1;
    private int startDataRow = -1;
    private int endDataRow = -1;

public GasWellDataLocator()
{

}

public ExcelCellLocation getWellCellLocation()
{
    return wellCellLocation;
}

public void setWellCellLocation(ExcelCellLocation wellCellLocation)
{
    this.wellCellLocation = wellCellLocation;
}

public ExcelCellLocation getOilCellLocation()
{
    return oilCellLocation;
}

public void setOilCellLocation(ExcelCellLocation oilCellLocation)
{
    this.oilCellLocation = oilCellLocation;
}

public ExcelCellLocation getGasCellLocation()
{
    return gasCellLocation;
}

public void setGasCellLocation(ExcelCellLocation gasCellLocation)
{
    this.gasCellLocation = gasCellLocation;
}

public ExcelCellLocation getWaterCellLocation()
{
    return waterCellLocation;
}

public void setWaterCellLocation(ExcelCellLocation waterCellLocation)
{
    this.waterCellLocation = waterCellLocation;
}

public ExcelCellLocation getCondensateCellLocation()
{
    return condensateCellLocation;
}

public void setCondensateCellLocation(ExcelCellLocation condensateCellLocation)
{
    this.condensateCellLocation = condensateCellLocation;
}

public String getWellName()
{
    return wellName;
}

public void setWellName(String wellName)
{
    this.wellName = wellName;
}

public int getDateColumn()
{
    return dateColumn;
}

public void setDateColumn(int dateColumn)
{
    this.dateColumn = dateColumn;
}

public int getStartDataRow()
{
    return startDataRow;
}

public void setStartDataRow(int startDataRow)
{
    this.startDataRow = startDataRow;
}

public int getEndDataRow()
{
    return endDataRow;
}

public void setEndDataRow(int endDataRow)
{
    this.endDataRow = endDataRow;
}

public String toString()
{
    StringBuffer out = new StringBuffer();
    out.append( "well \"" + getWellName() + "\"");

    if ( startDataRow >= 0 )
    {
        out.append( ", startDataRow=" + startDataRow );
    }

    if ( endDataRow >= 0 )
    {
        out.append( ", endDataRow=" + endDataRow );
    }

    if ( dateColumn >= 0 )
    {
        out.append( ", dateColumn=" + dateColumn );
    }

    if ( gasCellLocation != null )
    {
        out.append( ", gas cell location=" + gasCellLocation );
    }

    if ( gasCellLocation != null )
    {
        out.append( ", condensate cell location=" + condensateCellLocation );
    }

    if ( gasCellLocation != null )
    {
        out.append( ", oil cell location=" + oilCellLocation );
    }

    if ( waterCellLocation != null )
    {
        out.append( ", water cell location=" + waterCellLocation );
    }

    return out.toString();
}

}
