package au.com.polly.ddr;

import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Contains the measurements for a single entry...
 *
 */
public class GasWellDataEntry
{
private GasWell well;
private Date startInterval;
private long intervalLength;
private Map<WellMeasurementType,Double> measurements;
private boolean intervalLengthUpdated = false;
private volatile Date until;
private volatile boolean untilStale = true;

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
public Date getUntil()
{
    if ( untilStale )
    {
        untilStale = false;
        until = new Date( startInterval.getTime() + ( ( intervalLength - 1 ) * 1000 ) );
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

}
