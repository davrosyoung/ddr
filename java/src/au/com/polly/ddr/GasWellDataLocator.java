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
    out.append( "well \"" + getWellName() + "\", ");
    return out.toString();
}

}
