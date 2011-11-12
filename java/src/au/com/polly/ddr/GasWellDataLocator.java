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
    int startRow;
    int endRow;
    int startColumn;
    int endColumn;
    int dateColumn;
    Date startDate;
    Date endDate;

public GasWellDataLocator()
{

}

public GasWellDataLocator( String wellName, int startRow, int endRow, int startColumn, int endColumn, Date startDate, Date endDate )
{
    setWellName( wellName );
    setStartRow( startRow );
    setEndRow( endRow );
    setStartColumn( startColumn );
    setEndColumn( endColumn );
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

public int getStartRow()
{
    return startRow;
}

public void setStartRow(int startRow)
{
    this.startRow = startRow;
}

public int getEndRow()
{
    return endRow;
}

public void setEndRow(int endRow)
{
    this.endRow = endRow;
}

public int getStartColumn()
{
    return startColumn;
}

public void setStartColumn(int startColumn)
{
    this.startColumn = startColumn;
}

public int getEndColumn()
{
    return endColumn;
}

public void setEndColumn(int endColumn)
{
    this.endColumn = endColumn;
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
