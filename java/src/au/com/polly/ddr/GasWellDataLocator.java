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

import java.util.HashMap;
import java.util.Map;

/**
 * For a given gas well, specifies where the data is located within an excel worksheet.
 *
 *
 */
public class GasWellDataLocator
{
private Map<WellMeasurementType,ExcelCellLocation> locationMap;
private ExcelCellLocation intervalLengthLocation = null;
private ExcelCellLocation wellCellLocation = null;
private String wellName = null;
private int dateColumn = -1;
private int startDataRow = -1;
private int endDataRow = -1;

public GasWellDataLocator()
{
    locationMap = new HashMap<WellMeasurementType,ExcelCellLocation>();
}

public ExcelCellLocation getWellCellLocation()
{
    return wellCellLocation;
}

public void setWellCellLocation(ExcelCellLocation wellCellLocation)
{
    this.wellCellLocation = wellCellLocation;
}


public ExcelCellLocation getIntervalLengthLocation()
{
    return intervalLengthLocation;
}

public void setIntervalLengthLocation(ExcelCellLocation intervalLengthLocation)
{
    this.intervalLengthLocation = intervalLengthLocation;
}

public boolean containsMeasurementCellLocation( WellMeasurementType wmt )
{
    return locationMap.containsKey( wmt );
}

public ExcelCellLocation getMeasurementCellLocation( WellMeasurementType wmt )
{
    ExcelCellLocation result = null;
    
    if ( containsMeasurementCellLocation( wmt ) )
    {
        result = locationMap.get( wmt );    
    }
    
    return result;
}

public void setMeasurementCellLocation( WellMeasurementType wmt, ExcelCellLocation location )
{
    ExcelCellLocation result = null;
    
    locationMap.put( wmt, location );
}

public ExcelCellLocation getOilCellLocation()
{
    return getMeasurementCellLocation( WellMeasurementType.OIL_FLOW );
}


public ExcelCellLocation getGasCellLocation()
{
    return getMeasurementCellLocation( WellMeasurementType.GAS_FLOW );
}

public ExcelCellLocation getWaterCellLocation()
{
    return getMeasurementCellLocation( WellMeasurementType.WATER_FLOW );
}

public ExcelCellLocation getCondensateCellLocation()
{
    return getMeasurementCellLocation( WellMeasurementType.CONDENSATE_FLOW );
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
    
    if ( intervalLengthLocation != null )
    {
        out.append( ", interval length location=" + intervalLengthLocation );
    }     else {
        out.append( ", NO interval length location" );
    }

    if ( locationMap.containsKey( WellMeasurementType.GAS_FLOW ) )
    {
        out.append( ", gas cell location=" + getGasCellLocation() );
    } else {
        out.append( ", NO gas cell location" );
    }

    if ( locationMap.containsKey( WellMeasurementType.CONDENSATE_FLOW ) )
    {
        out.append( ", condensate cell location=" + getCondensateCellLocation() );
    } else {
        out.append( ", NO condensate cell location" );
    }

    if ( locationMap.containsKey( WellMeasurementType.OIL_FLOW ) )
    {
        out.append( ", oil cell location=" + getOilCellLocation() );
    } else {
        out.append( ", NO oil cell location" );
    }

    if ( locationMap.containsKey( WellMeasurementType.WATER_FLOW  ) )
    {
        out.append( ", water cell location=" + getWaterCellLocation() );
    } else {
        out.append( ", NO water cell location" );
    }

    return out.toString();
}

}
