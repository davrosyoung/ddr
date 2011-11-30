package au.com.polly.ddr;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Initial naive implementation of a data rate reducer...
 */
public class SimpleDiscontinuityDataRateReducer implements DataRateReducer
{
static Logger logger = Logger.getLogger( SimpleDiscontinuityDataRateReducer.class );

public SimpleDiscontinuityDataRateReducer()
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
    List<Date> boundaries = new ArrayList<Date>();


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


    median = unsorted.get( unsorted.size() / 2 );
    discontinuityTriggerLimit = median / 4.0;

    double c10 = unsorted.get( (int)Math.floor( unsorted.size() * 0.1 ) );
    double c90 = unsorted.get( (int)Math.ceil( unsorted.size() * 0.9 ) );

    logger.debug( "min=" + unsorted.get( 0 ) );
    logger.debug( "max=" + unsorted.get( unsorted.size() - 1 ) );
    logger.debug( "median=" + median );

    logger.debug( "c10=" + c10 + ", c50=" + median + ", c90=" + c90 );

    boundaries.add( original.from() );

    for( int i = 0; i < dataEntries.size(); i++ )
    {
        GasWellDataEntry entry = dataEntries.get( i );
        current = entry.getMeasurement(WellMeasurementType.OIL_FLOW);
        if ( i > 0 )
        {
            do {
                // moving to a zero value or out of a zero value triggers a discontinuity...
                // ------------------------------------------------------------------------
                if (
                        ( ( previous >= 0.1 ) && ( current < 0.1 ) )
                    || ( ( previous < 0.1 ) && ( current >= 0.1 ) )
                    )
                {
                    logger.debug( "i=" + i + " .... Change from " + previous + " to " + current + " TRIGGERS discontinuity..." + entry.getStartInterval() );
                    boundaries.add( entry.getStartInterval() );
                    break;
                }

                if(
                        ( previous < c90 ) && ( current >= c90 )
                    ||  ( previous >= c90 ) && ( current < c90 )
                        )
                {
                    logger.debug( "i=" + i + " .... Change from " + previous + " to " + current + " TRIGGERS discontinuity... at " + entry.getStartInterval()  );
                    boundaries.add( entry.getStartInterval() );
                    break;
                }

                if(
                        ( previous < c10 ) && ( current >= c10 )
                    ||  ( previous >= c10 ) && ( current < c10 )
                        )
                {
                    logger.debug( "i=" + i + " .... Change from " + previous + " to " + current + " TRIGGERS discontinuity... at " + entry.getStartInterval() );
                    boundaries.add( entry.getStartInterval() );
                    break;
                }
            } while( false );

/*
            double delta = Math.abs( current - previous );
            if ( delta > discontinuityTriggerLimit )
            {
                logger.debug( "i=" + i + " .... Change from " + previous + " to " + current + " TRIGGERS discontinuity..." );
            }
 */
        }
        previous = current;
    }

    logger.debug( "We have " + boundaries.size() + " discontinuities..." );

    boundaries.add( original.until() );


    Date[] boundaryArray = new Date[ boundaries.size() ];
    boundaries.toArray( boundaryArray );


    result = new GasWellDataSet( original, boundaryArray );

    logger.debug( result );

    return result;  //To change body of implemented methods use File | Settings | File Templates.
}

}