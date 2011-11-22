package au.com.polly.ddr;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 17/11/11
 * Time: 8:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class GasWellDataSet
{
final static Logger logger = Logger.getLogger( GasWellDataSet.class );
private GasWell well;
private List<GasWellDataEntry> list;
final static String[] monthNames = new String[] { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
private GasWellDataEntry minimumEntry;
private GasWellDataEntry maximumEntry;

public GasWellDataSet( GasWell well )
{
    this.well = well;
    list = new ArrayList<GasWellDataEntry>();
}

public void addDataEntry( GasWellDataEntry entry )
{
    list.add( entry );
    if ( minimumEntry == null )
    {
        minimumEntry = entry.copy();
    }

    if ( maximumEntry == null )
    {
        maximumEntry = entry.copy();
    }

    if ( minimumEntry.getStartInterval().getTime() > entry.getStartInterval().getTime() )
    {
        minimumEntry.setStartInterval( entry.getStartInterval() );
    }

    if ( maximumEntry.getUntil().getTime() < entry.getUntil().getTime() )
    {
        maximumEntry.setStartInterval( entry.getStartInterval() );
        maximumEntry.setIntervalLength( entry.getIntervalLength() );
    }

    for( WellMeasurementType wmt : WellMeasurementType.values() )
    {
        if ( entry.containsMeasurement( wmt ) )
        {
            if ( ( ! minimumEntry.containsMeasurement( wmt ) ) || ( minimumEntry.getMeasurement( wmt ) > entry.getMeasurement( wmt ) ) )
            {
                minimumEntry.setMeasurement( wmt, entry.getMeasurement( wmt ) );
            }

            if ( ( ! maximumEntry.containsMeasurement( wmt ) ) || ( maximumEntry.getMeasurement( wmt ) < entry.getMeasurement( wmt ) ) )
            {
                maximumEntry.setMeasurement( wmt, entry.getMeasurement( wmt ) );
            }
        }
    }
}

public Date from()
{
    Date result = null;

    if ( minimumEntry != null )
    {
        result = minimumEntry.getStartInterval();
    }

    return result;
}

public Date until()
{
    Date result = null;

    if ( maximumEntry != null )
    {
        result = maximumEntry.getUntil();
    }

    return result;
}

public Iterator<GasWellDataEntry> getIterator()
{
    return list.iterator();
}

public List<GasWellDataEntry> getData()
{
    return list;
}

public double getMinimum( WellMeasurementType wmt )
{
    double result = Double.MAX_VALUE;

    if ( minimumEntry != null )
    {
        result = minimumEntry.getMeasurement( wmt );
    }
    return result;
}

public double getMaximum( WellMeasurementType wmt )
{
    double result = Double.MIN_VALUE;

    if( maximumEntry != null )
    {
        result = maximumEntry.getMeasurement( wmt );
    }

    return result;
}

protected GasWellDataEntry getMaxima()
{
    return maximumEntry;
}

protected GasWellDataEntry getMinima()
{
    return minimumEntry;
}

/**
 * Outputs the contents of the dataset to the specified print stream...
 * ... internal toString() method uses this.
 *
 *
 * @param writer
 */
public void output( PrintStream writer )
{
    Formatter formatter = new Formatter( writer, Locale.UK );
    GasWellDataEntry firstEntry = list.get( 0 );
    GasWellDataEntry lastEntry = list.get( list.size() - 1 );
    Date firstDate = firstEntry.getStartInterval();
    Date lastDate = new Date( lastEntry.getStartInterval().getTime() + lastEntry.getIntervalLength() );
    Calendar firstCal = Calendar.getInstance();
    firstCal.setTime( firstDate );
    Calendar lastCal = Calendar.getInstance();
    lastCal.setTime( lastDate );
    Calendar cal = Calendar.getInstance();

    long durationSeconds = ( lastDate.getTime() - firstDate.getTime() ) / 1000;
    long ds = durationSeconds % 60;
    long dm = ( durationSeconds / 60 ) % 60;
    long dh = ( durationSeconds / 3600 ) % 24;
    long dd = durationSeconds / 86400;

    logger.debug( "durationSeconds=" + durationSeconds + ", ds=" + ds + ", dm=" + dm + ", dh=" + dh + ", dd=" + dd );

    writer.append( "+-----------------------------------------------------------------------------------------------------------------+\n" );
    formatter.format( "| Well: %-48s   From: %2d/%3s/%4d %02d:%02d:%02d  Until %2d/%3s/%4d %2d:%02d:%02d |\n",
            well.getName(),
            firstCal.get( Calendar.DAY_OF_MONTH ),
            monthNames[ firstCal.get( Calendar.MONTH ) ],
            firstCal.get( Calendar.YEAR ),
            firstCal.get( Calendar.HOUR_OF_DAY ),
            firstCal.get( Calendar.MINUTE),
            firstCal.get( Calendar.SECOND ),
            lastCal.get( Calendar.DAY_OF_MONTH ),
            monthNames[ lastCal.get( Calendar.MONTH ) ],
            lastCal.get( Calendar.YEAR ),
            lastCal.get( Calendar.HOUR_OF_DAY ),
            lastCal.get( Calendar.MINUTE),
            lastCal.get( Calendar.SECOND )
            );
    writer.append( "|                                                              " );
    formatter.format( "Duration: %4d days %2d hours %2d mins %2d seconds.   |\n", dd, dh, dm, ds );
    
    writer.append( "+----------------------+----------------------+---------------+---------------+-----------------+-----------------+\n" );
    writer.append( "| From                 | Until                | Oil Flow Rate | Gas Flow Rate | Cond. Flow Rate | Water Flow Rate |\n" );
    writer.append( "|                      |                      | (barrels/day) | (MMscf/day)   | (barrels/day)   | (barrels/day)   |\n" );
    writer.append( "+----------------------+----------------------+---------------+---------------+-----------------+-----------------+\n" );

    for( GasWellDataEntry entry : list )
    {
        cal.setTime( entry.getStartInterval() );
        formatter.format( "| %02d/%3s/%04d %02d:%02d:%02d |",
                cal.get( Calendar.DAY_OF_MONTH ),
                monthNames[ cal.get( Calendar.MONTH ) ],
                cal.get( Calendar.YEAR ),
                cal.get( Calendar.HOUR_OF_DAY ),
                cal.get( Calendar.MINUTE ),
                cal.get( Calendar.SECOND )
        );

        cal.add( Calendar.SECOND, (int)entry.getIntervalLength() - 1 );

        formatter.format( " %02d/%3s/%04d %02d:%02d:%02d |",
                cal.get( Calendar.DAY_OF_MONTH ),
                monthNames[ cal.get( Calendar.MONTH ) ],
                cal.get( Calendar.YEAR ),
                cal.get( Calendar.HOUR_OF_DAY ),
                cal.get( Calendar.MINUTE ),
                cal.get( Calendar.SECOND )
        );

        if ( entry.containsMeasurement( WellMeasurementType.OIL_FLOW ) )
        {
            formatter.format( "        %6d |", (int)Math.round( entry.getMeasurement( WellMeasurementType.OIL_FLOW ) ) );
        } else {
            writer.append( "        ------ |" );
        }

        if ( entry.containsMeasurement( WellMeasurementType.GAS_FLOW ) )
        {
            formatter.format( "       %6.3f |", entry.getMeasurement( WellMeasurementType.GAS_FLOW ) );
        } else {
            writer.append( "        ------ |" );
        }


        if ( entry.containsMeasurement( WellMeasurementType.CONDENSATE_FLOW ) )
        {
            formatter.format( "        %6d |", (int)Math.round( entry.getMeasurement( WellMeasurementType.CONDENSATE_FLOW ) ) );
        } else {
            writer.append( "         ------ |" );
        }



        if ( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ) )
        {
            formatter.format( "        %6d |\n", (int)Math.round( entry.getMeasurement( WellMeasurementType.WATER_FLOW ) ) );
        } else {
            writer.append( "         ------ |\n" );
        }

    } // end-FOR( each gas well data entry )
}

public String toString()
{
    StringBuilder out = new StringBuilder();
    OutputStream os = new OutputStream()
    {
        private StringBuilder string = new StringBuilder();

        @Override
        public void write(int b) throws IOException {
            this.string.append((char) b );
        }

        //Netbeans IDE automatically overrides this toString()
        public String toString(){
            return this.string.toString();
        }
    };
    PrintStream stream = new PrintStream( os );
    output( stream );

    return os.toString();
}

}
