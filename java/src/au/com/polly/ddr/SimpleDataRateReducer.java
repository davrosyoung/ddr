package au.com.polly.ddr;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Initial naive implementation of a data rate reducer...
 */
public class SimpleDataRateReducer implements DataRateReducer
{
static Logger logger = Logger.getLogger( SimpleDataRateReducer.class );

public SimpleDataRateReducer()
{

}

@Override
public GasWellDataSet reduce(GasWellDataSet original, int maxIntervals)
{
    GasWellDataSet result;
    List<Double> unsorted = new ArrayList<Double>();
    List<Double> sorted = new ArrayList<Double>();
    List<GasWellDataEntry> dataEntries;
    double median;
    double discontinuityTriggerLimit;
    double current = 0.0;
    double previous = 0.0;


    result = new GasWellDataSet( original.getWell() );

    dataEntries = original.getData();

    for( GasWellDataEntry entry : dataEntries )
    {
        unsorted.add( entry.getMeasurement( WellMeasurementType.OIL_FLOW ) );
    }

    Collections.sort( unsorted, new Comparator()
    {
        @Override
        public int compare(Object o1, Object o2)
        {
            Double d1 = (Double)o1;
            Double d2 = (Double)o2;
            int result;
            if ( d1.doubleValue() == d2.doubleValue() )
            {
                result = 0;
            } else {
                result = d1.doubleValue() < d2.doubleValue() ? -1 : 1;
            }
            return result;
        }
    });

    logger.debug( "min=" + unsorted.get( 0 ) );
    logger.debug( "max=" + unsorted.get( 99 ) );
    logger.debug( "median=" + unsorted.get( 50 ) );

    median = unsorted.get( 50 );
    discontinuityTriggerLimit = median / 10.0;

    for( int i = 0; i < dataEntries.size(); i++ )
    {
        current = dataEntries.get( i ).getMeasurement( WellMeasurementType.OIL_FLOW );
        if ( i > 0 )
        {
            double delta = Math.abs( current - previous );
            if ( delta > discontinuityTriggerLimit )
            {
                logger.debug( "i=" + i + " .... Change from " + previous + " to " + current + " TRIGGERS discontinuity..." );
            }
        }
        previous = current;
    }

    return result;  //To change body of implemented methods use File | Settings | File Templates.
}

}
