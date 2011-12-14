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

package au.com.polly.util;

import au.com.polly.ddr.GasWell;
import au.com.polly.ddr.WellMeasurementType;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * Represents an interval of time between two date/times. Since this is so fundamental to the
 * processing of the data flow rate reduction, best to put it all in one place and get it
 * absolutely right!!
 */
public class DateRange implements Serializable
{
protected Date from;
protected Date until;
protected long precision;
static DateParser parser = new AussieDateParser();

/**
 *
 * @param from when the date range starts
 * @param until when the date range ends
 */
public DateRange( Date from, Date until )
{
    this( from, until, 1L );
}

/**
 *
 * @param fromText when the date/time range should commence.
 * @param untilText when the date/time range should end.
 *
 * convenience method for when supplying text in text format..
 */
public DateRange( String fromText, String untilText )
{
    this( fromText, untilText, 1L );
}

/**
 *
 * @param fromText when the date/time range should commence.
 * @param untilText when the date/time range should end.
 *  @param precision how precise the date range should be, in milliseconds.
 *
 * convenience method for when supplying text in text format..
 */
public DateRange( String fromText, String untilText, long precision )
{
    this( parser.parse( fromText ).getTime(), parser.parse( untilText ).getTime(), precision );
}

/**
 *
 * @param from the date/time that this date range starts.
 * @param span the length of this date range in milliseconds.
 */
public DateRange( Date from, long span )
{
    this( from, span, 1L );
}

/**
 *
 * @param from the date/time that this date range starts.
 * @param span the length of this date range in milliseconds.
 * @param precision how precise this date range should be
 */
public DateRange( Date from, long span, long precision )
{
    this( from, new Date( from.getTime() + span) , precision );
}

/**
 * 
 * @param from when the date range starts
 * @param until when the date range ends
 * @param precision how precise the date range should be, in milliseconds.
 */
public DateRange( Date from, Date until, long precision )
{
    long fromStamp;
    long untilStamp;
    this.precision = precision;
    
    if ( from == null )
    {
        throw new NullPointerException( "NULL specified as from date!! Can't work with that!!" );
    }

    if ( until == null )
    {
        throw new NullPointerException( "NULL specified as until date!! Can't work with that!!" );
    }

    if ( ( precision < 1 ) || ( precision > 365 * 86400 * 1000 ) )
    {
        throw new IllegalArgumentException( "Precision must be between 1ms and 1year. You supplied " + precision + "ms." );
    }

    if ( precision > 1 )
    {
        // here is the tricky bit .... for times before 1970, the time is negative. this means that if we
        // "round down" a number through division, we're actually rounding it "up", back towards zero!!!
        long stamp = from.getTime();
        if ( stamp > 0 )
        {
            // after 1970, we can use straight forward division and multiplication to ensure that
            // the "from" stamp becomes a multiple of "precision"
            // -----------------------------------------------------------------------------------
            fromStamp = ( (int)( stamp / precision ) ) * precision;
        } else {
            // ok, if stamp is already a whole multiple of 'precision', we don't need to do anything,
            // otherwise we need to subtract 'precision' from stamp before applying the division and
            // multiplication to obtain our mulitple of 'precision'.
            // -------------------------------------------------------------------------------------
            fromStamp = ( stamp % precision == 0 ) ? stamp : (int)( ( stamp - precision ) / precision ) * precision;
        }


        // make sure that the until stamp is pointing at the "next" unit of time according to the supplied precision.
        // eg; if precision=1000 (1s), then 13/JUN/1968 04:13:26.386 becomes 13/JUN/1968 04:13:27.000
        // if precision=3600000 (1hour), then 13/JUN/1968 04:13:26.386 becomes 13/JUN/1968 05:00:00.000
        // ---------------------------------------------------------------------------------------------------
        stamp = until.getTime();

        if ( ( stamp % precision ) == 0 )
        {
            untilStamp = stamp;
        } else {
            if ( stamp > 0 )
            {
                untilStamp = ( (long)( stamp / precision ) * precision );
            } else {
                untilStamp = ( (long)( ( stamp - precision ) / precision ) * precision );
            }
        }
    } else {
        fromStamp = from.getTime();
        untilStamp = until.getTime();
    }
    
    if ( fromStamp >= untilStamp )
    {
        throw new IllegalArgumentException( "From specifier \"" + from + "\" must come BEFORE until specifier \"" + until + "\"" );
    }
    
    this.from = new Date( fromStamp );
    this.until = new Date( untilStamp );

}

/**
 * 
 * @param candidate
 * @return if the supplied candidate falls within the
 * this date range.
 */
public boolean contains( DateRange candidate )
{
    boolean result = false;
    
    if ( candidate == null )
    {
        throw new NullPointerException( "NULL date range specified!!" );
    }

    // the other date range is still contained, even if it's end (until) boundary lies on the same
    // point in time as this date range!! This differs slightly from how the contains() method
    // works for a single date!!
    // ----------------------------------------------------------------------------------------------
    result = ( candidate.from().getTime() >= from().getTime() ) && ( candidate.until().getTime() <= until().getTime() );
    
    return result;
}

/**
 * 
 * @param candidate the date/time to be tested within the specified date range.
 * @return whether or not the specified date falls within this date/time range.
 */
public boolean contains( Date candidate )
{
    boolean result = false;
   
    if ( candidate == null )
    {
        throw new NullPointerException( "NULL date was specified!!");
    }
                           
    result = ( candidate.getTime() >= from().getTime() && candidate.getTime() < until().getTime() );
    
    return result;
}

/**
 * 
 * @param candidate the other date range to check this date range against.
 * @return the number of milliseconds during which this date range overlaps with the
 * specified candidate date range.
 */
public long overlap( DateRange candidate )
{
    long result = 0L;
    
    if ( candidate == null )
    {
        throw new NullPointerException( "NULL date range specified to overlap() method!!" );
    }
    
    do {
        // if the candidate date range is totally outside of this date range, then
        // avoid any further calculations...
        // ------------------------------------------------------------------------
        if (
                ( candidate.from().getTime() >= until().getTime() )
             || ( candidate.until().getTime() < from().getTime() )
        )
        {
            break;
        }


        long a = from().getTime() < candidate.from().getTime() ? candidate.from().getTime() : from().getTime();
        long b = until.getTime() > candidate.until().getTime() ? candidate.until().getTime() : until().getTime();

        result = b - a;

    } while( false );

    return result;
}

/**
 *
 * @return the number of milliseconds that this date/time range spans in milliseconds.
 */
public long span()
{
   return until.getTime() - from.getTime();
}

/**
 *
 * @return the date/time that this range starts at.
 */
public Date from()
{
    return this.from;
}

/**
 *
 * @return the date/time that this range extends until.
 */
public Date until()
{
    return this.until;
}


@Override
public boolean equals( Object other )
{
    boolean result = true;
    DateRange otherEntry;
    long a, b;

    do {
        if ( !( other instanceof DateRange ) || ( other == null ) )
        {
            result = false;
            break;
        }

        otherEntry = (DateRange)other;

        if ( ( from() != null ) && ( otherEntry.from() != null ) )
        {
            if ( ! ( result = from().equals( otherEntry.from() ) ) )
            {
                result = false;
            }
        } else {
            result = ( from() == null ) && ( otherEntry.from() == null );
        }

        if ( !result ) { break; }

        if ( ( until() != null ) && ( otherEntry.until() != null ) )
        {
            if ( ! ( result = until().equals( otherEntry.until() ) ) )
            {
                result = false;
            }
        } else {
            result = ( until() == null ) && ( otherEntry.until() == null );
        }

        if ( !result ) { break; }
        
        result = this.precision == otherEntry.precision;

    } while( false );

    return result;
}

@Override
public int hashCode()
{
    int result = HashCodeUtil.SEED;
    if ( this.from != null )
    {
        result = HashCodeUtil.hash( result, this.from );
    }
    if ( this.until() != null )
    {
        result = HashCodeUtil.hash( result, this.until );
    }
    
    result = HashCodeUtil.hash( result, this.precision );
    return result;
}


/**
 *
 * @return human readable form for this date range.
 */
public String toString()
{
    StringBuilder out = new StringBuilder();
    

    do {
        if ( precision < 1000 )
        {
            out.append( DateArmyKnife.format( from() ) );
            out.append( " - " );
            out.append( DateArmyKnife.format( until() ) );
            break;
        }
        
        if ( precision < 60000 )
        {
            out.append( DateArmyKnife.formatWithSeconds( from() ) );
            out.append( " - " );
            out.append( DateArmyKnife.formatWithSeconds( until() ) );
            break;
        }
        
        out.append( DateArmyKnife.formatWithMinutes( from() ) );
        out.append( " - " );
        out.append( DateArmyKnife.formatWithMinutes( until() ) );
        
    } while( false );

   return out.toString();
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
 * Provides the serialization and deserialization for a gas well data entry object.
 */
private static class SerializationProxy implements Serializable
{
    private Date from;
    private Date until;
    private long precision;

    SerializationProxy( DateRange range )
    {
        this.from = range.from;
        this.until = range.until;
        this.precision = range.precision;
    }

    /**
     *
     * @return a gas well data entry created from this proxy object...
     */
    private Object readResolve()
    {
        DateRange result = new DateRange( this.from, this.until, this.precision );
        return result;
    }


    private static final long serialVersionID = 0x5826c7e1;
}

}
