package au.com.polly.ddr;

import au.com.polly.util.Sequencer;
import org.apache.log4j.Logger;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Initial naive implementation of a data rate reducer...
 */
public class SimpleBestFitDataRateReducer implements DataRateReducer
{
static Logger logger = Logger.getLogger( SimpleBestFitDataRateReducer.class );
private final static int TIME_DIVISIONS=15;

public SimpleBestFitDataRateReducer()
{

}

@Override
public GasWellDataSet reduce(GasWellDataSet original, int maxIntervals)
{
    GasWellDataSet result = null;
    int[] minErrorPositions = null;
    double minOilFlowError = Double.MAX_VALUE;
    double minCondensateFlowError = Double.MAX_VALUE;
    Date[] segmentDateStart;

    if ( maxIntervals >= TIME_DIVISIONS )
    {
        throw new IllegalArgumentException( "Can only support upto " + (TIME_DIVISIONS - 1 ) + " intervals. Sorry!!" );
    }

    // let's place down ten interval markers in two hundred possible locations
    // find which has the smallest error..
    // !200/!190 = lots of possible combinations!!
    // ----------------------------------------------------------------------------
    long firstDate = original.from().getTime();
    long lastDate = original.until().getTime();
    long timeRange = lastDate - firstDate;
    long timeSegmentSize = timeRange / TIME_DIVISIONS;

    // ok ... we try all possible combinations of placing down time segment ranges.
    // -----------------------------------------------------------------------------
    Sequencer seq = new Sequencer( maxIntervals, TIME_DIVISIONS - 2 );
    int[] positions;

    long then = System.currentTimeMillis();
    long combinationCount = 0;

    while( ( positions = seq.getNext() ) != null )
    {
        segmentDateStart = new Date[ positions.length + 1];
        segmentDateStart[ 0 ] = new Date( original.from().getTime() );
        for( int i = 0; i < positions.length; i++ )
        {
            segmentDateStart[ i + 1 ] = new Date( firstDate + ( timeSegmentSize * ( positions[ i ] + 1  ) ) );
        }
        segmentDateStart[ segmentDateStart.length - 1 ] = original.until();

        GasWellDataSet candidate = new GasWellDataSet( original, segmentDateStart );
        Map<WellMeasurementType,Double> errorMap = GasWellDataSetUtil.getError( original, candidate );
        // let's just investigate the oil flow or condensate flow for now!!
        if ( errorMap.containsKey( WellMeasurementType.OIL_FLOW ) )
        {
            if ( errorMap.get( WellMeasurementType.OIL_FLOW ) < minOilFlowError )
            {
                minOilFlowError = errorMap.get( WellMeasurementType.OIL_FLOW );
                minErrorPositions = Arrays.copyOf( positions, positions.length );
                if ( logger.isDebugEnabled() )
                {
                    logger.debug( "just recorded minOilErrorFlow of " + minOilFlowError + ", combinationCount=" + combinationCount );
                }
            }
        } else {
            if ( logger.isDebugEnabled() )
            {
                logger.debug( "for combinationCount=" + combinationCount + " ... no minOilFlowError determined." );
            }
        }
        combinationCount++;
    }
    long now = System.currentTimeMillis();
    logger.info( "It took " + ( now - then ) + "ms to try out " + combinationCount + " different combinations of intervals." );

    // if we found a combination of positions which produced the smallest error, then
    // re-use those positions to produce a result....
    // --------------------------------------------------------------------------------
    if ( minErrorPositions != null )
    {
        segmentDateStart = new Date[ minErrorPositions.length + 1 ];
        segmentDateStart[ 0 ] = new Date( original.from().getTime() );
        for( int i = 0; i < minErrorPositions.length; i++ )
        {
            segmentDateStart[ i + 1 ] = new Date( firstDate + ( timeSegmentSize * ( minErrorPositions[ i ] + 1  )  ) );
        }
        segmentDateStart[ segmentDateStart.length - 1 ] = original.until();
        result = new GasWellDataSet( original, segmentDateStart );
    }

    return result;
}

}