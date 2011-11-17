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

public GasWellDataEntry()
{
    measurements = new ConcurrentHashMap<WellMeasurementType,Double>();
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
}

public long getIntervalLength()
{
    return intervalLength;
}

public void setIntervalLength(long intervalLength)
{
    this.intervalLength = intervalLength;
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
        measurements.get( wmt );
    } else {
        throw new IllegalArgumentException( "Measurement type " + wmt + " not defined." );
    }
    return result;
}

}
