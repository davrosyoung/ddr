package au.com.polly.ddr;

import com.sun.tools.corba.se.idl.StringGen;

import java.util.Date;

/**
 * For a given gas well, specifies where the data is located within an excel worksheet.
 *
 *
 */
public class GasWellDataLocator
{
    String wellName;
    ExcelCellLocation startCell;
    ExcelCellLocation endCell;
    int dateColumn;
    Date startDate;
    Date endDate;

public GasWellDataLocator()
{

}

public GasWellDataLocator( String wellName, ExcelCellLocation startCell, ExcelCellLocation endCell, Date startDate, Date endDate )
{
    setWellName( wellName );
    setStartCell( startCell );
    setEndCell( endCell);
    setStartDate( startDate );
    setEndDate( endDate );
}

public String getWellName()
{
    return wellName;
}

public void setWellName(String wellName)
{
    this.wellName = wellName;
}

public ExcelCellLocation getStartCell()
{
    return startCell;
}

public void setStartCell(ExcelCellLocation startCell)
{
    this.startCell = startCell;
}

public ExcelCellLocation getEndCell()
{
    return endCell;
}

public void setEndCell(ExcelCellLocation endCell)
{
    this.endCell = endCell;
}

public int getDateColumn()
{
    return dateColumn;
}

public void setDateColumn(int dateColumn)
{
    this.dateColumn = dateColumn;
}

public Date getStartDate()
{
    return startDate;
}

public void setStartDate(Date startDate)
{
    this.startDate = startDate;
}

public Date getEndDate()
{
    return endDate;
}

public void setEndDate(Date endDate)
{
    this.endDate = endDate;
}

public String toString()
{
    StringBuffer out = new StringBuffer();
    out.append( "well \"" + getWellName() + "\", ");
    return out.toString();
}

}
