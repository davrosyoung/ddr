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

import au.com.polly.util.DateArmyKnife;
import au.com.polly.util.DateRange;
import au.com.polly.util.HashCodeUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Contains the set of daily average flow rates of gas, oil, condensate and water
 * for a given oil well. Written to enable the production of reduced rate data
 * for a given data set.
 *
 *
 */
public class GasWellDataSet implements Serializable
{
final static Logger logger = Logger.getLogger( GasWellDataSet.class );
private GasWell well;
private List<GasWellDataEntry> list;
transient final static String[] monthNames = new String[] { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
transient private GasWellDataEntry minimumEntry;
transient private GasWellDataEntry maximumEntry;
transient private Map<WellMeasurementType,Boolean> containsMeasurement = new HashMap<WellMeasurementType,Boolean>();
static private NumberFormat fiveDotFour = null;

static {
    fiveDotFour = NumberFormat.getNumberInstance();
    fiveDotFour.setGroupingUsed( false );
    fiveDotFour.setMinimumIntegerDigits( 5 );
    fiveDotFour.setMinimumFractionDigits( 4 );
    fiveDotFour.setMaximumFractionDigits( 4 );
}

/**
 *
 * @param well the gas well to which the data pertains.
 */
public GasWellDataSet( GasWell well )
{
    if ( well == null ) { throw new NullPointerException( "Well must not be null!! What are you thinking?" ); }
    this.well = well;
    list = new ArrayList<GasWellDataEntry>();
}

/**
 * Creates a new set of gas well data measurements, given an original set of measurements and a list of
 * date/time boundaries (must be in ascending order) from which to construct the boundaries in the
 * resultant data set.
 *
 *
 * @param original data to base new data set upon.
 * @param intervalBoundaries date/time boundaries. must be ascending. need to have more boundaries than in
 * the original data set!!
 */
public GasWellDataSet( GasWellDataSet original, Date[] intervalBoundaries )
{
    this(original.getWell());
    if( original == null )
    {
        throw new NullPointerException( "Need non-NULL well to extract data from!!");
    }

    if ( intervalBoundaries == null )
    {
        throw new NullPointerException( "Need non-NULL array of dates to specify interval boundaries." );
    }

    if ( intervalBoundaries.length < 2 )
    {
        throw new IllegalArgumentException( "Need an array of at least TWO dates." );
    }

    if( intervalBoundaries[ 0 ].before( original.from() ) )
    {
        throw new IllegalArgumentException( "Initial date/time (" + intervalBoundaries[ 0 ] + ") precedes start of specified data set (" + original.from() + ")" );
    }

    if( intervalBoundaries[ intervalBoundaries.length - 1 ].after(original.until()) )
    {
        throw new IllegalArgumentException( "Terminal date/time (" + intervalBoundaries[ intervalBoundaries.length - 1 ] + ") beyond end of specified data set (" + original.until() + ")" );
    }


    for( int i = 0; i < intervalBoundaries.length - 1; i++ )
    {
        if ( intervalBoundaries[ i ].getTime() >=  intervalBoundaries[ i + 1 ].getTime() )
        {
            throw new IllegalArgumentException( "interval boundary array must contain date/times which get larger from start to finish." );
        }
    }

    // all params checked ... let's get going through these intervals....
    // -------------------------------------------------------------------
    for( int i = 0; i < intervalBoundaries.length - 1; i++ )
    {
        boolean lastInterval = ( i == intervalBoundaries.length - 2 );
        long untilSpecifier = intervalBoundaries[ i + 1 ].getTime();
//        if ( ! lastInterval ) { untilSpecifier-= 1000; } // end-boundary is one second before start of next boundary!!
        Date until = new Date( untilSpecifier );
        GasWellDataEntry entry = original.consolidateEntries( intervalBoundaries[ i ], until );
        logger.debug( "Adding " + entry + " to consolidated data array" );
        addDataEntry( entry );
    }
}


/**
 * Add another set of measurements to the existing data set. It is assumed that this
 * entry comes directly after the last measurement added to the data set!!!
 *
 * @param entry
 */
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
    
    

    // if our minimum entry contains a start date/time later than the one specified,
    // then update the date/time in the minimum entry to represent the earliest date/time
    // for the dataset.
    // -----------------------------------------------------------------------------------
    if ( minimumEntry.from().getTime() > entry.from().getTime() )
    {
        minimumEntry.setDateRange(new DateRange(entry.from(), entry.getIntervalLengthMS(), 1000L));
    }


    // if the interval "until" timestamp comes after the current maximum entry, then
    // update the maximum entry to contain the timestamp in the data entry being added.
    // -----------------------------------------------------------------------------------
    if ( maximumEntry.until().getTime() < entry.until().getTime() )
    {
        maximumEntry.setDateRange(new DateRange(entry.from(), entry.getIntervalLengthMS(), 1000L));
    }

    // for each measurement type, if the flow rate specified is smaller than the previous
    // minimum or larger than the previous maximum, update the minima or maxima appropriately...
    // ------------------------------------------------------------------------------------------
    for( WellMeasurementType wmt : WellMeasurementType.values() )
    {
        if ( entry.containsMeasurement( wmt ) )
        {
            double flow = entry.getMeasurement( wmt );

            if ( ( ! minimumEntry.containsMeasurement( wmt ) ) || ( minimumEntry.getMeasurement( wmt ) > flow ) )
            {
                minimumEntry.setMeasurement( wmt, flow );
            }

            if ( ( ! maximumEntry.containsMeasurement( wmt ) ) || ( maximumEntry.getMeasurement( wmt ) < flow ) )
            {
                maximumEntry.setMeasurement( wmt, flow );
            }
            
            if ( ( ! containsMeasurement.containsKey( wmt ) ) || ( containsMeasurement.get( wmt ) == false ) )
            {
                containsMeasurement.put( wmt, true );
            }
        }
    }
}

public String getWellName()
{
    String result = null;

    result = ( well != null ) ? well.getName() : null;

    return result;
}

public boolean containsMeasurement( WellMeasurementType wmt )
{
    return containsMeasurement.containsKey( wmt ) && containsMeasurement.get( wmt );
}

public GasWell getWell()
{
    return well;
}

/**
 *
 * @return the date/time from which this data set commences.
 */
public Date from()
{
    Date result = null;

    if ( minimumEntry != null )
    {
        result = minimumEntry.from();
    }

    return result;
}

/**
 *
 * @return the date/time up until which (inclusively) this data sets finishes.
 */
public Date until()
{
    Date result = null;

    if ( maximumEntry != null )
    {
        result = maximumEntry.until();
    }

    return result;
}

/**
 *
 * @return iterator over the data entries in this data set.
 */
public Iterator<GasWellDataEntry> getIterator()
{
    return list.iterator();
}

/**
 *
 * @return the data entries in this data set.
 */
public List<GasWellDataEntry> getData()
{
    return list;
}

/**
 *
 * @param wmt the type of measurement
 * @return the minimum value recorded for this measurement type. Double.MAX_VALUE
 * is returned if no measurements have been recorded for the specified measurement type.
 */
public double getMinimum( WellMeasurementType wmt )
{
    double result = Double.MAX_VALUE;

    if ( ( minimumEntry != null ) && minimumEntry.containsMeasurement( wmt ) )
    {
        result = minimumEntry.getMeasurement( wmt );
    }
    return result;
}

/**
 *
 * @param wmt the type of measurement
 * @return the maximum value recorded for the specified measurement type. Double.MIN_VALUE
 * is returned if no measurements have been recorded for the specified measurement type.
 */
public double getMaximum( WellMeasurementType wmt )
{
    double result = Double.MIN_VALUE;

    if ( ( maximumEntry != null ) && maximumEntry.containsMeasurement( wmt ) )
    {
        result = maximumEntry.getMeasurement( wmt );
    }

    return result;
}

/**
 *
 * @deprecated
 */
protected GasWellDataEntry getMaxima()
{
    return maximumEntry;
}

/**
 *
 * @deprecated
 */
protected GasWellDataEntry getMinima()
{
    return minimumEntry;
}

/**
 *
 * @param when
 * @return the data entry for the specified date..
 */
public GasWellDataEntry getEntry( Date when )
{
    GasWellDataEntry result = null;
    GasWellDataEntry candidate = null;

    // let's check to see if the date is within bounds...
    // -------------------------------------------------
    if ( when == null )
    {
        throw new NullPointerException( "Cannot find data entry for NULL date. What were you thinking?" );
    }

    int entryIdx;

    if ( ( entryIdx = locateEntryIndex( when ) ) >= 0 )
    {
        result = getData().get( entryIdx );
    }

    return result;
}

public GasWellDataEntry getEntry( int idx )
{
    if ( ( idx < 0 ) || ( idx > ( getData().size() - 1 ) ) )
    {
        throw new IllegalArgumentException( "index supplied " + idx + " out of range. valid values 0 ... " + ( getData().size() - 1 ) );
    }

    return getData().get( idx );
}

/**
 *
 * @param when
 * @return the index within the list of data entries corresponding to the specified date or -1 if
 * no matching entry was located.
 */
protected int locateEntryIndex( Date when )
{
    int result = -1;
    GasWellDataEntry candidate = null;

    // let's check to see if the date is within bounds...
    // -------------------------------------------------
    if ( when == null )
    {
        throw new NullPointerException( "Cannot find data entry for NULL date. What were you thinking?" );
    }

    do {

        if ( ( when.before( from() ) ) || ( when.after( until() ) ) )
        {
            logger.error( "Date specified " + when + " is not within the start/end range of this data set. start=" + from() + ", end=" + until() );
            break;
        }

        // simple (but slow) implementation ... just search from start to end ... smarter solution would
        // be some kind of binary search ... but later on!!
        // todo: make this search a lot smarter.
        // -----------------------------------------------------------------------------------------------
        for( int i = 0; i < getData().size() && ( result < 0 ); i++ )
        {
            candidate = getData().get( i );
            if  ( candidate.getDateRange().contains( when ) )
            {
                if ( logger.isDebugEnabled() )
                {
                    logger.debug( "For specified date/time:" + when + " ... found matching time interval at i=" + i + ", when=" + when + ", startInterval=" + candidate.from() + ", intervalLength=" + candidate.getIntervalLengthMS() + "seconds" );

                }
                result = i;
            }
            if ( logger.isTraceEnabled() )
            {
                logger.trace("i=" + i + ", startInterval=" + candidate.from() + ", until=" + candidate.until() + ",result=" + result);
                logger.trace( "i=" + i + ", startInterval.time=" + candidate.from().getTime() + ", until.time=" + candidate.until().getTime() + ", when.time=" + when.getTime() + ", result=" + result );
            }
        }

        if ( result < 0 )
        {
            logger.warn( "FAILED to find data entry for date/time=" + when );
        }

    } while( false );

    return result;

}

/**
 * Produces a single gas well data entry containing an average value across the entire date/time range
 * specified.
 *
 *
 * @param segmentStart when to start the measurements from
 * @param segmentEnd when to end the measurements at
 * @return a gas well data entry which contains the combined (and averaged) measurement entries recorded
 * for the dates provided.
 */
public GasWellDataEntry consolidateEntries( Date segmentStart, Date segmentEnd )
{
    DateRange range = new DateRange( segmentStart, segmentEnd );
    return consolidateEntries( range );
}

/**
 * Produces a single gas well data entry containing an average value across the entire date/time range
 * specified.
 *
 *
 * @param range the date/time range to obtain the measurements for.
 * @return a gas well data entry which contains the combined (and averaged) measurement entries recorded
 * for the dates provided.
 */
public GasWellDataEntry consolidateEntries( DateRange range )
{
    GasWellDataEntry result = null;
    int cursor;
    long segmentLength;
    DateRange ourRange = new DateRange( from(), until() );

    if ( range == null )
    {
        throw new NullPointerException( "null date range specified!! Unable to consolidate entries." );
    }
    
    if ( ! ourRange.contains( range ) )
    {
        throw new IllegalArgumentException( "Requested date range " + range + " extends beyond range of measurement data " + ourRange );
    }

    segmentLength = ourRange.overlap( range ) / 1000;

    result = new GasWellDataEntry();
    result.setWell(getWell());
    result.setDateRange( range );

    cursor = locateEntryIndex( range.from() );

    if ( cursor < 0 )
    {
        logger.error( "Unable to obtain data entry index for segmentStart=" + range.from() );
        logger.error( "from=" + from() + ", until=" + until() );
        throw new IllegalArgumentException( "Unable to obtain data entry index for segmentStart=" + range.from() );
    }

    GasWellDataEntry interval;
    double factor;
    long overlapDuration;
    do {
        interval = getEntry( cursor++ );
        DateRange intervalRange = range.common( interval.getDateRange() );
        overlapDuration = intervalRange.span() / 1000;
        factor = (double)overlapDuration / (double)segmentLength;

        if ( logger.isTraceEnabled() )
        {
            logger.trace( "cursor=" + cursor + ", overlapDuration=" + overlapDuration + "seconds, factor=" + factor );
        }

        for( WellMeasurementType wmt : WellMeasurementType.values() )
        {
            double existingResult = 0;
            if ( result.containsMeasurement( wmt ) )
            {
                existingResult = result.getMeasurement( wmt );
            }
            if ( interval.containsMeasurement( wmt ) )
            {
                existingResult += interval.getMeasurement( wmt ) * factor;
                result.setMeasurement( wmt, existingResult );
            }
        }

    } while( interval.until().getTime() < range.until().getTime() );


    return result;
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
    GasWellDataEntry firstEntry = null;
    GasWellDataEntry lastEntry = null;
    Date firstDate = null;
    Date lastDate = null;
    Calendar firstCal = null;
    Calendar lastCal = null;
    Calendar cal = null;
    long durationSeconds = 0;
    long ds = 0;
    long dm = 0;
    long dh = 0;
    long dd = 0;

    if ( ( list != null ) && ( list.size() > 0 ) )
    {
        firstEntry = list.get( 0 );
        lastEntry = list.get( list.size() - 1 );
        firstDate = firstEntry.from();
        lastDate = new Date( lastEntry.from().getTime() + lastEntry.getIntervalLengthMS() );
        firstCal = Calendar.getInstance();
        firstCal.setTime( firstDate );
        lastCal = Calendar.getInstance();
        lastCal.setTime( lastDate );
        cal = Calendar.getInstance();
        durationSeconds = ( lastDate.getTime() - firstDate.getTime() ) / 1000;
        ds = durationSeconds % 60;
        dm = ( durationSeconds / 60 ) % 60;
        dh = ( durationSeconds / 3600 ) % 24;
        dd = durationSeconds / 86400;
    }

    logger.debug( "durationSeconds=" + durationSeconds + ", ds=" + ds + ", dm=" + dm + ", dh=" + dh + ", dd=" + dd );

    writer.append( "+-----------------------------------------------------------------------------------------------------------------+\n" );
    if ( durationSeconds > 0 )
    {
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
                writer.println( DateArmyKnife.formatWithMinutes( entry.from() ) + "," + entry.getDateRange().span() / 3600000.0 );
                cal.setTime( entry.from() );
                formatter.format( "| %02d/%3s/%04d %02d:%02d:%02d |",
                        cal.get( Calendar.DAY_OF_MONTH ),
                        monthNames[ cal.get( Calendar.MONTH ) ],
                        cal.get( Calendar.YEAR ),
                        cal.get( Calendar.HOUR_OF_DAY ),
                        cal.get( Calendar.MINUTE ),
                        cal.get( Calendar.SECOND )
                );

                cal.add( Calendar.SECOND, (int)entry.getIntervalLengthMS() - 1 );

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
                    formatter.format( "     %9.3f |", entry.getMeasurement( WellMeasurementType.GAS_FLOW ) );
                } else {
                    writer.append( "        ------ |" );
                }

                if ( entry.containsMeasurement( WellMeasurementType.CONDENSATE_FLOW ) )
                {
                    formatter.format( "        %6d |", (int)Math.round( entry.getMeasurement( WellMeasurementType.CONDENSATE_FLOW ) ) );
                } else {
                    writer.append( "          ------ |" );
                }

                if ( entry.containsMeasurement( WellMeasurementType.WATER_FLOW ) )
                {
                    formatter.format( "          %6d |\n", (int)Math.round( entry.getMeasurement( WellMeasurementType.WATER_FLOW ) ) );
                } else {
                    writer.append( "          ------ |\n" );
                }

            } // end-FOR( each gas well data entry )

            writer.append( "+----------------------+----------------------+---------------+---------------+-----------------+-----------------+\n" );


    } else {
        formatter.format( "| Well: %-48s  NO DATA                                             |\n", well.getName() );
    }
}



/**
 * Used to output this data set to CSV file. Written a couple of weeks after output() method, and it shows!!
 *
 *
 * @param writer
 */
public void outputCSV( PrintWriter writer )
{
    outputCSV(writer, true, false );
}

/**
 * Used to output this data set to CSV file. Written a couple of weeks after output() method, and it shows!!
 *
 *
 * @param writer
 */
public void outputCSV( PrintWriter writer, boolean outputColumnHeadings, boolean outputAllColumns )
{
    Formatter formatter = new Formatter( writer, Locale.UK );
    long durationSeconds = 0;
    long ds = 0;
    long dm = 0;
    long dh = 0;
    long dd = 0;

    durationSeconds = ( until().getTime() - from().getTime() ) / 1000L;
    ds = durationSeconds % 60;
    dm = ( durationSeconds / 60 ) % 60;
    dh = ( durationSeconds / 3600 ) % 24;
    dd = durationSeconds / 86400;

    logger.debug( "durationSeconds=" + durationSeconds + ", ds=" + ds + ", dm=" + dm + ", dh=" + dh + ", dd=" + dd );

    writer.println( "# Well:" + well.getName() );
    writer.println( "# Date Range:" + DateArmyKnife.formatWithMinutes( from() ) + " - " + DateArmyKnife.formatWithMinutes( until() ) );
    formatter.format( "# Duration: %4d days %2d hours %2d mins %2d seconds.   |\n", dd, dh, dm, ds );

    if ( outputColumnHeadings )
    {
        writer.print("well,date/time,interval length (hours)");
        for( WellMeasurementType wmt : WellMeasurementType.values() )
        {
            if ( containsMeasurement( wmt ) || outputAllColumns )
            {
                String name = wmt.name().toLowerCase();
                String refined = name.replace( "_", " " );
                writer.print( "," + refined );
            }
        }
        writer.println();
    }

    for( GasWellDataEntry entry : list )
    {
        writer.print( entry.getWell().getName() + "," );
        writer.print( DateArmyKnife.formatWithMinutes( entry.from() ) + "," + fiveDotFour.format( entry.getIntervalLengthMS() / 3600000.0 ) );
        
        for( WellMeasurementType wmt : WellMeasurementType.values() )
        {
            if ( containsMeasurement( wmt ) )
            {
                formatter.format( ",%012.5f", entry.getMeasurement( wmt ) );
            } else {
                if ( outputAllColumns )
                {
                    writer.println( ",------------" );
                }
            }
        }
        
        writer.println();
        
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

/**
 *
 * @return an identical copy of this data set.
 */
public GasWellDataSet copy()
{
    GasWellDataSet result = new GasWellDataSet( this.well );
    for( GasWellDataEntry entry : list )
    {
        result.addDataEntry( entry );
    }
    return result;
}

@Override
public boolean equals( Object other )
{
    boolean result = true;
    GasWellDataSet otherSet;

    do {
        if ( ! ( other instanceof GasWellDataSet ) )
        {
            result = false;
            break;
        }

        otherSet = (GasWellDataSet)other;
        if ( ( this.well != null ) && ( otherSet.well != null ) )
        {
            if ( ! ( result = this.well.equals( otherSet.well ) ) )
            {
                break;
            }
        }

        if (
                ( ( this.well == null ) && ( otherSet.well != null ) )
            ||  ( ( this.well != null ) && ( otherSet.well == null ) )
            )
        {
            result = false;
            break;
        }

        // now test the actual data contents...
        // -------------------------------------
        if (
                ( ( this.list == null ) && ( otherSet.list != null ) )
            ||  ( ( this.list != null ) && ( otherSet.list == null ) )
                )
        {
            result = false;
            break;
        }

        // make both measurement sets are not null
        // ----------------------------------------------------
        if ( ( this.list != null ) && ( otherSet.list != null ) )
        {
            // do they contain the same number of elements??
            // -----------------------------------------------------
            if ( ! ( result = ( this.list.size() == otherSet.list.size() ) ) )
            {
                break;
            }

            for( int i = 0; ( i < this.list.size() ) && result; i++ )
            {
                GasWellDataEntry alpha = this.list.get( i );
                GasWellDataEntry beta = otherSet.list.get( i );
                result = alpha.equals( beta );
            }

        }


    } while( false );

    return result;
}


@Override
public int hashCode()
{
    int result = HashCodeUtil.SEED;
    if ( well != null )
    {
        result = HashCodeUtil.hash( result, well.getName() );
    }

    for( GasWellDataEntry entry : list )
    {
        result = HashCodeUtil.hash( result, entry );
    }

    return result;
}

/**
 * Instead of serializing this object, we serialize a proxy representation of it instead :-)
 *
 *
 * @return the serialization proxy to be written to the wire.
 */
private Object writeReplace()
{
    return new SerializationProxy( this );
}

/**
 *
 * Oh no you don't!!
 * @param stream
 * @throws java.io.InvalidObjectException
 */
private void readObject( ObjectInputStream stream )
    throws InvalidObjectException
{
    throw new InvalidObjectException( "Proxy required." );
}


/**
 * Provides the serialization and deserialization for a set of measurements
 * for a gas well.
 */
private static class SerializationProxy implements Serializable
{
    private GasWell well;
    private List<GasWellDataEntry> list;

    SerializationProxy( GasWellDataSet dataSet )
    {
        this.well = dataSet.well;
        this.list = dataSet.list;
    }

    /**
     *
     * @return a gas well data entry created from this proxy object...
     */
    private Object readResolve()
    {
        GasWellDataSet result = new GasWellDataSet( this.well );
        if ( ( this.list != null ) && ( this.list.size() > 0 ) )
        {
            for( GasWellDataEntry entry : list )
            {
                result.addDataEntry( entry );
            }
        }

        return result;
    }

    private static final long serialVersionID = 0xa4f87d4f;
}


}
