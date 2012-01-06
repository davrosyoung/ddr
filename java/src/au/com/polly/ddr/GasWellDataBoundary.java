/*
 * Copyright (c) 2011-2012 Polly Enterprises Pty Ltd and/or its affiliates.
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
import au.com.polly.util.HashCodeUtil;
import au.com.polly.util.StringArmyKnife;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Represents a boundary to be superimposed upon a gas well data set. Used in the data rate reduction process.
 */
public class GasWellDataBoundary implements Comparable
{
public enum BoundaryType {
    START,
    END,
    AUTOMATED_REGULAR_BOUNDARY,
    PRIMARY_INDICATOR_OUTAGE_START,
    PRIMARY_INDICATOR_OUTAGE_END,
    SECONDARY_INDICATOR_OUTAGE_START,
    SECONDARY_INDICATOR_OUTAGE_END,
    PRIMARY_INDICATOR_MEDIAN_CROSSING,
    UNKNOWN;

public boolean isPrimaryOutage()
{
    return ( ( this == PRIMARY_INDICATOR_OUTAGE_START ) || ( this == PRIMARY_INDICATOR_OUTAGE_END ) );
}

public boolean isSecondaryOutage()
{
    return( ( this == SECONDARY_INDICATOR_OUTAGE_START ) || ( this == SECONDARY_INDICATOR_OUTAGE_END ) );
}

public boolean isAutomated()
{
    return( this == AUTOMATED_REGULAR_BOUNDARY );
}

} // end-ENUM(BoundryType);

protected Date when;
protected String comment;
protected BoundaryType bt;

GasWellDataBoundary( Date when, String comment )
{
    this( when, comment, BoundaryType.UNKNOWN );
}

GasWellDataBoundary(Date when, String comment, BoundaryType bt )
{
    if ( when == null )
    {
        throw new NullPointerException( "Must specify a non-NULL date/time" );
    }
    
    if ( comment == null )
    {
        throw new NullPointerException( "Must specify a non-NULL comment" );
    }
    
    if ( comment.trim().length() == 0 )
    {
        throw new IllegalArgumentException( "Must specify a non-blank comment!" );
    }

    this.when = when;
    this.comment = comment.trim();
    this.bt = bt;
}

public Date getTimestamp()
{
    return when;
}

public String getComment()
{
    return comment;
}

public BoundaryType getBoundaryType()
{
    return bt;
}

@Override
public int compareTo( Object other )
{
    GasWellDataBoundary otherBoundary;
    long a;
    long b;
    int result = 0;
    
    do {
        if ( ( other == null ) || ( ! ( other instanceof GasWellDataBoundary ) ) )
        {
            break;
        }
        
        otherBoundary = (GasWellDataBoundary)other;
        a = getTimestamp().getTime();
        b = otherBoundary.getTimestamp().getTime();
        
        result = ((Long)a).compareTo(b);
    } while( false );
    
    return result;
}


@Override
public boolean equals( Object other )
{
    boolean result = true;
    GasWellDataBoundary otherEntry;

    do {
        if ( !( other instanceof GasWellDataBoundary ) || ( other == null ) )
        {
            result = false;
            break;
        }

        otherEntry = (GasWellDataBoundary)other;
        
        if ( ! ( result = StringArmyKnife.areStringsEqual( getComment(), otherEntry.getComment() ) ) )
        {
            break;
        }

        if ( ! ( result = DateArmyKnife.areDatesEqual( getTimestamp(), otherEntry.getTimestamp() ) ) )
        {
            break;
        }

        result =  getBoundaryType() == otherEntry.getBoundaryType();

        if ( !result ) { break; }


    } while( false );

    return result;
}

@Override
public int hashCode()
{
    int result = HashCodeUtil.SEED;
    result = HashCodeUtil.hash( result, getTimestamp() );
    result = HashCodeUtil.hash( result, getComment() );
    result = HashCodeUtil.hash( result, getBoundaryType() );
    return result;
}


public String toString()
{
    StringBuilder out = new StringBuilder();
    out.append( DateArmyKnife.formatWithMinutes( getTimestamp() ) );
    out.append( " - " );
    out.append( getBoundaryType() );
    out.append( " - \"" );
    out.append( getComment() );
    out.append( "\"" );
    return out.toString();
}

/**
 *
 * @param alpha the first set of gas well data boundaries
 * @param beta the second set of gas well data boundaries
 * @return the combined set of gas well data boundaries.
 */
protected static List<GasWellDataBoundary> merge( List<GasWellDataBoundary> alpha, List<GasWellDataBoundary> beta )
{
    List<GasWellDataBoundary> refinedBeta = new ArrayList<GasWellDataBoundary>();
            
    if ( ( alpha == null ) ||  ( beta == null ) )
    {
        throw new NullPointerException( "Both lists of boundaries must be non-NULL!" );
    }
    
    if (
            ( ( alpha != null ) && ( alpha.size() == 0 ) )
        &&  ( ( beta != null ) && ( beta.size() == 0 ) )

     )
    {
        throw new IllegalArgumentException( "Both lists are empty!" );
    }

    // remove any identical dates from the second list which are also in the first list...
    // (first list takes precedence).
    // ------------------------------------------------------------------------------------
    for( GasWellDataBoundary boundary : beta )
    {
        if ( ! listContains( alpha, boundary.getTimestamp() ) )
        {
            refinedBeta.add( boundary );   
        }
    }

    List<GasWellDataBoundary> result = new ArrayList<GasWellDataBoundary>();
    result.addAll( alpha );
    result.addAll( refinedBeta );
    Collections.sort( result );
    
    return result;
}


/**
 * 
 * @param list
 * @param timestamp
 * @return whether the specified timestamp is present within the list of gas well data boundaries.
 */
public static boolean listContains( List<GasWellDataBoundary> list, Date timestamp )
{
    boolean result = false;
    int i;

    // assume that list is sorted from earliest to latest!!
    // -----------------------------------------------------
    long[] stamps = getTimestampList( list );
    
    i = Arrays.binarySearch(stamps, 0, stamps.length, timestamp.getTime());
    
    return( i >= 0 );
}

/**
 * 
 * @param list list of gas well data boundaries
 * @return the same list, but expressed as an array of longs, each entry in the array being
 * the millisecond representation of the timestamp.
 */
protected static long[] getTimestampList( List<GasWellDataBoundary> list )
{
    long[] result = null;
    
    if ( list == null )
    {
        throw new NullPointerException( "Cannot extract array from null list" );
    }
    
    if ( list.size() == 0 )
    {
        throw new IllegalArgumentException( "Cannot extract array from empty list" );
    }
    
    if ( list.size() > 0 )
    {
        result = new long[ list.size() ];
        for( int i = 0; i < list.size(); i++ )
        {
            GasWellDataBoundary boundary = list.get( i );
            result[ i ] = boundary.getTimestamp().getTime();
        }
    }

    return result;
}


}
