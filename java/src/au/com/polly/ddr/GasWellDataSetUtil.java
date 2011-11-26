package au.com.polly.ddr;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility functions for gas well data sets..
 *
 *
 *
 */
public class GasWellDataSetUtil
{
private final static Logger logger = Logger.getLogger( GasWellDataSetUtil.class );

/**
 *
 * @param alpha set of gas well data measurements
 * @param beta set of gas well data measurements
 * @return the actual difference between the measurements in alpha and beta.
 */
public static Map<WellMeasurementType,Double> getDifference( GasWellDataSet alpha, GasWellDataSet beta )
{
    Map<WellMeasurementType,Double> result = null;
    return result;
}

/**
 * used to determine how good (or bad) an approximation of one set of data intervals is against
 * another. we use this method after we've reduced the number of data intervals we use to
 * represent a set of well measurements.
 *
 *
 * @param alpha set of gas well data measurements
 * @param beta set of gas well data measurements
 * @return the sum of all differences between intervals between alpha and beta. it is assumed that
 * the difference is zero.
 */
public static Map<WellMeasurementType,Double> getError( GasWellDataSet alpha, GasWellDataSet beta )
{
    Map<WellMeasurementType,Double> result = new HashMap<WellMeasurementType,Double>();
    GasWellDataSet a = null;
    GasWellDataSet b = null;

    // check parameter validity.........
    // ------------------------------------
    if ( ( alpha == null ) || ( beta == null ) )
    {
        throw new NullPointerException( "A null data set was specified!!" );
    }

    // data sets must cover the same time period....
    // ----------------------------------------------
    if ( ( ( alpha.from().getTime() / 1000 ) != ( beta.from().getTime() / 1000 ) )
            || ( ( alpha.until().getTime() / 1000 ) != ( beta.until().getTime() / 1000 ) ) )
    {
        StringBuilder errorText = new StringBuilder();
        if ( ( alpha.from().getTime() / 1000 ) != ( beta.from().getTime() / 1000 ) )
        {
            errorText.append( "start times of [" + alpha.from() + ":" + alpha.from().getTime() + "] and [" + beta.from() + ":" + beta.from().getTime() + "] do not match. " );
        }
        if ( ( alpha.from().getTime() / 1000 ) != ( beta.from().getTime() / 1000 ) )
        {
            errorText.append( "end times of [" + alpha.until() + ":" + alpha.until().getTime() + "] and [" + beta.until() + ":" + beta.until().getTime() + "] do not match. " );
        }
        logger.error( errorText.toString() );
        throw new IllegalArgumentException( errorText.toString() );
    }

    // a will be the data set with the finer granularity...
    // b will be the data set with rougher granularity....
    a = ( alpha.getData().size() > beta.getData().size() ) ? alpha : beta;
    b = ( alpha.getData().size() > beta.getData().size() ) ? beta : alpha;
/*
    if ( logger.isDebugEnabled() )
    {
        logger.debug( "a.size()=" + a.getData().size() + ", b.size()=" + b.getData().size() );
    }
  */
    for( GasWellDataEntry entry : a.getData() )
    {
        // obtain a slice representing the data from 'b' for the time period within 'a'....
        // ---------------------------------------------------------------------------------
        GasWellDataEntry bSlice = b.consolidateEntries( entry.getStartInterval(), entry.until() );

        for( WellMeasurementType wmt : WellMeasurementType.values() )
        {
            if ( bSlice.containsMeasurement( wmt ) && entry.containsMeasurement( wmt ) )
            {
                double aMeasurement = entry.getMeasurement( wmt );
                double bMeasurement = bSlice.getMeasurement( wmt );
                double error = Math.abs( aMeasurement - bMeasurement );
                double existing = result.containsKey( wmt ) ? result.get( wmt ) : 0.0;
                result.put( wmt, error + existing );
                if ( logger.isTraceEnabled() )
                {
                    logger.trace( "slice[ " + entry.getStartInterval() + " - " + entry.until() + "] " + wmt + " ... error=" + error + ", existing=" + existing + ", aValue=" + aMeasurement + ", bMeasurement=" + bMeasurement );
                }
            }
        }
    }

    return result;
}

}
