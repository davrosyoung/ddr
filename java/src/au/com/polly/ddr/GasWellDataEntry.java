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

import au.com.polly.util.HashCodeUtil;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Contains the measurements for a single entry...
 *
 */
public final class GasWellDataEntry implements Serializable
{
private GasWell well;
private Date startInterval;
private long intervalLength;
private Map<WellMeasurementType,Double> measurements;
private transient boolean intervalLengthUpdated = false;
private transient Date until;
private transient boolean untilStale = true;

public GasWellDataEntry()
{
    measurements = new ConcurrentHashMap<WellMeasurementType,Double>();
}

public GasWellDataEntry copy()
{
    GasWellDataEntry copy = new GasWellDataEntry();
    copy.setWell( getWell() );
    copy.setStartInterval( getStartInterval() );
    copy.setIntervalLength( getIntervalLength() );
    for( WellMeasurementType wmt : WellMeasurementType.values() )
    {
        if ( containsMeasurement( wmt ) )
        {
            copy.setMeasurement( wmt, getMeasurement( wmt ));
        }
    }

    return copy;
}

public GasWell getWell()
{
    return well;
}

public void setWell(GasWell well)
{
    this.well = well;
}

public Date getStartInterval()
{
    return startInterval;
}

public void setStartInterval(Date startInterval)
{
    this.startInterval = startInterval;
    this.untilStale = true;
}

public long getIntervalLength()
{
    return intervalLength;
}

public void setIntervalLength(long intervalLength)
{
    this.intervalLength = intervalLength;
    this.untilStale = true;
}

/**
 * Date/time containing the last second that this interval extends unto.
 *
 * @return
 */
public Date until()
{
    if ( untilStale )
    {
        if ( startInterval != null )
        {
            untilStale = false;
            until = new Date( startInterval.getTime() + ( ( intervalLength - 1 ) * 1000 ) );
        }
    }

    return until;
}

public void setMeasurement( WellMeasurementType wmt, double value )
{
    measurements.put( wmt, value );
}

public boolean containsMeasurement( WellMeasurementType wmt )
{
    return measurements.containsKey( wmt );
}

public double getMeasurement( WellMeasurementType wmt )
{
    double result = Double.MAX_VALUE;

    if ( containsMeasurement( wmt ) )
    {
        result = measurements.get( wmt );
    } else {
        throw new IllegalArgumentException( "Measurement type " + wmt + " not defined." );
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
 * @throws InvalidObjectException
 */
private void readObject( ObjectInputStream stream )
    throws InvalidObjectException
{
    throw new InvalidObjectException( "Proxy required." );
}

@Override
public boolean equals( Object other )
{
    boolean result = true;
    GasWellDataEntry otherEntry;

    do {
        if ( !( other instanceof GasWellDataEntry ) || ( other == null ) )
        {
            result = false;
            break;
        }

        otherEntry = (GasWellDataEntry)other;

        if ( ( getWell() != null ) && ( otherEntry.getWell() != null ) )
        {
            if ( ! ( result = getWell().equals( otherEntry.getWell() ) ) )
            {
                result = false;
            }
        } else {
            result = ( getWell() == null ) && ( otherEntry.getWell() == null );
        }

        if ( !result ) { break; }

        // check equality with accuracy of one second. date/times are stored in milliseconds. so divide by 1000 to
        // make sure we are only comparing to within one second of each other!!
        // ------------------------------------------------------------------------------------------------
        if ( this.startInterval != null )
        {
            result = ( otherEntry.startInterval != null ) && ( startInterval.getTime()/1000 == otherEntry.startInterval.getTime()/1000 );
        } else {
            result = otherEntry.startInterval == null;
        }

        if ( !result ) { break; }

        result = ( this.intervalLength == otherEntry.intervalLength );
        if ( ! result ) { break; }

        for( WellMeasurementType wmt : WellMeasurementType.values() )
        {
            if ( result )
            {
                if (
                        ( this.containsMeasurement( wmt ) && !otherEntry.containsMeasurement( wmt ) )
                     || ( !this.containsMeasurement( wmt ) && otherEntry.containsMeasurement( wmt ) )
                )
                {
                    result = false;
                    continue;
                }

                if ( this.containsMeasurement( wmt ) && otherEntry.containsMeasurement( wmt ) )
                {
                    result = Math.abs( this.getMeasurement( wmt ) - otherEntry.getMeasurement( wmt ) ) < 0.0000001;
                }
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
    if ( startInterval != null )
    {
        result = HashCodeUtil.hash( result, startInterval );
    }

    result = HashCodeUtil.hash( result, intervalLength );
    for( WellMeasurementType wmt : WellMeasurementType.values() )
    {
        if ( measurements.containsKey( wmt ) )
        {
            result = HashCodeUtil.hash( result, measurements.get( wmt ) );
        }

    }
    return result;
}


/**
 * human readable representation of the gas well data measurement entry....
 * @return
 */
public String toString()
{
    StringBuilder out = new StringBuilder();
    if ( getWell() != null )
    {
        out.append( "well:" + getWell().getName() + ", " );
    }
    out.append( "from:" + getStartInterval() + ", " );
    out.append( "until:" + until() + ", " );
    for( WellMeasurementType wmt : WellMeasurementType.values() )
    {
        if ( measurements.containsKey( wmt ) )
        {
            out.append( wmt + "=" + measurements.get(WellMeasurementType.OIL_FLOW ) + " " );
        } else {
            out.append( "no " + wmt + " ");
        }
    }
    return out.toString();
}

/**
 * Provides the serialization and deserialization for a gas well data entry object.
 */
private static class SerializationProxy implements Serializable
{
    private GasWell well;
    private Date startInterval;
    private long intervalLength;
    private double oilFlowMeasurement = 0.0;
    private double gasFlowMeasurement = 0.0;
    private double waterFlowMeasurement = 0.0;
    private double condensateFlowMeasurement = 0.0;
    private boolean containsOilFlowMeasurement;
    private boolean containsGasFlowMeasurement;
    private boolean containsWaterFlowMeasurement;
    private boolean containsCondensateFlowMeasurement;
    
    SerializationProxy( GasWellDataEntry entry )
    {
        this.well = entry.well;
        this.startInterval = entry.startInterval;
        this.intervalLength = entry.intervalLength;

        if ( this.containsOilFlowMeasurement = entry.containsMeasurement( WellMeasurementType.OIL_FLOW ) )
        {
            this.oilFlowMeasurement = entry.getMeasurement( WellMeasurementType.OIL_FLOW );        
        }
        
        if ( this.containsGasFlowMeasurement = entry.containsMeasurement( WellMeasurementType.GAS_FLOW ) )
        {
            this.gasFlowMeasurement = entry.getMeasurement( WellMeasurementType.GAS_FLOW );        
        }
        
        if ( this.containsCondensateFlowMeasurement = entry.containsMeasurement( WellMeasurementType.CONDENSATE_FLOW ) )
        {
            this.condensateFlowMeasurement = entry.getMeasurement( WellMeasurementType.CONDENSATE_FLOW );
        }

        if ( this.containsWaterFlowMeasurement = entry.containsMeasurement( WellMeasurementType.WATER_FLOW ) )
        {
            this.waterFlowMeasurement = entry.getMeasurement( WellMeasurementType.WATER_FLOW );
        }
    }

    /**
     *
     * @return a gas well data entry created from this proxy object...
     */
    private Object readResolve()
    {
        GasWellDataEntry result = new GasWellDataEntry();
        result.setWell( well );
        result.setStartInterval( this.startInterval );
        result.setIntervalLength( this.intervalLength );
        if ( this.containsOilFlowMeasurement )
        {
            result.setMeasurement( WellMeasurementType.OIL_FLOW, this.oilFlowMeasurement );
        }

        if( this.containsGasFlowMeasurement )
        {
            result.setMeasurement( WellMeasurementType.GAS_FLOW, this.gasFlowMeasurement );
        }

        if ( this.containsCondensateFlowMeasurement )
        {
            result.setMeasurement( WellMeasurementType.CONDENSATE_FLOW, this.condensateFlowMeasurement );
        }

        if ( this.containsWaterFlowMeasurement )
        {
            result.setMeasurement( WellMeasurementType.WATER_FLOW, this.waterFlowMeasurement );
        }

        return result;
    }


    private static final long serialVersionID = 0x5826f8a8;
}

}
