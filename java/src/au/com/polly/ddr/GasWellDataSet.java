package au.com.polly.ddr;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 17/11/11
 * Time: 8:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class GasWellDataSet
{
GasWell well;
List<GasWellDataEntry> list;

public GasWellDataSet( GasWell well )
{
    this.well = well;
    list = new ArrayList<GasWellDataEntry>();
}

public void addDataEntry( GasWellDataEntry entry )
{
    list.add( entry );
}

public String toString()
{
    StringBuilder out = new StringBuilder();

    out.append( "+----------------------------------------------------------------------------------------------------------------+\n" );
    out.append( "| Well: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx      From: mm/dd/yyyy hh:mm:ss  Until mm/dd/yyyy hh:ss  |\n" );
    out.append( "|                                                             Duration: nnnn days hh hours mm mins ss seconds.   |\n" );
    out.append( "+----------------------+---------------------+---------------+---------------+-----------------+-----------------+\n" );
    out.append( "| From                 | Until               | Oil Flow Rate | Gas Flow Rate | Water Flow Rate | Cond. Flow Rate |\n" );
    out.append( "|                      |                     | (barrels/day) | (MMscf/day)   | (barrels/day)   | (barrels/day)   |\n" );
    out.append( "+----------------------+---------------------+---------------+---------------+-----------------+-----------------+\n" );
    out.append( "| dd/mmm/yyyy hh:mm:ss | dd/mm/yyyy hh:mm:ss |        nnnnnn |       nnn.nnn |          nnnnnn |          nnnnnn |\n" );
    out.append( "+----------------------+---------------------+---------------+---------------+-----------------+-----------------+\n" );

    return out.toString();
}

}
