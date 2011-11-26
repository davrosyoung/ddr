package au.com.polly.util;

import org.apache.log4j.Logger;
import sun.security.util.Debug;

import java.util.ArrayDeque;

/**
 * Keeps track of the most recent 'n' entries in a data series.
 */
public class WeightedAverageCalculator
{
static Logger logger = Logger.getLogger( WeightedAverageCalculator.class );
double[] weight;
int numberEntries = 0;
int sampleSize;
ArrayDeque<Double> entries = null;
double weightedAverage = 0.0;
boolean weightedAverageStale = true;

public WeightedAverageCalculator()
{
    this( 5 );
}

public WeightedAverageCalculator(int size)
{
    if ( size < 2 )
    {
        throw new IllegalArgumentException( "No point in having a weighted argument calculator with less than TWO entries. You specified size=" + size + " ... sheeshh ... some people!!" );
    }
    this.sampleSize = size;
    entries = new ArrayDeque<Double>( size );

    // setup the weights for the weighted average calculation...
    // ----------------------------------------------------------
    double remainder = 1.0;
    double w = 0.5;
    this.weight = new double[ this.sampleSize ];
    for( int i = 0; i < this.sampleSize; i++ )
    {
        weight[ i ] = w;
        remainder -= w;
        w *= 0.5;
    }

    weight[ 0 ] += remainder;

    numberEntries = 0;
}

public double getWeightedAverage()
{
    double result = 0;
    if  ( weightedAverageStale )
    {
        weightedAverage = calculateAverage();
    }
    result = weightedAverage;
    return result;
}

public double add( double entry )
{
    double result = Double.MIN_VALUE;

    if ( numberEntries < sampleSize  )
    {
        numberEntries++;
    }

    logger.debug( "About to add entry=" + entry + " to queue. numberEntries=" + numberEntries + ", entries.size()=" + entries.size() );

    // if the queue is already full, then remove the last element...
    // -------------------------------------------------------------
    if ( entries.size() == sampleSize )
    {
        logger.debug( "About to remove last element before adding first element....");
        entries.removeLast();
    }
    entries.addFirst(entry);

    weightedAverage = calculateAverage();
    weightedAverageStale = false;
    result = weightedAverage;

    return result;
}

public double getEntry( int idx )
{
    double result = Double.MIN_VALUE;

    return result;
}

protected double[] __unit_test_only_getEntries()
{
    return getEntries();
}

private double[] getEntries()
{
    Double[] x = new Double[ numberEntries ];
    double[] result = new double[ numberEntries ];
    entries.toArray( x );
    logger.debug( "about to populate double[" + numberEntries + "] array." );

    if ( x== null )
    {
        logger.error( "What the?!? x is NULL!! Stop the lights!!");
    } else {
        logger.debug( "x.length=" + x.length );
    }
    if ( result == null )
    {
        logger.error( "What the!?! result is NULL!!!" );
    } else {
        logger.debug( "result.length=" + result.length );
    }

    for( int i = 0; i < result.length; i++ )
    {
        logger.debug( "x[ i=" + i + "]=" + ( ( x[ i ] != null ) ? Double.toString( x[ i ].doubleValue() ) : "NULL" ) );
        result[ i ] = ( x[ i ] != null ) ? x[ i ].doubleValue() : 0.0;
    }

    return result;
}

protected double calculateAverage()
{
    double result = 0.0;
    double[] x = getEntries();
    double remainder = 1.0;

    for( int i = 0; i < numberEntries; i++ )
    {
        logger.debug( "entries[ i=" + i + " ]=" + x[ i ] + ", weight[ i=" + i + "]=" + weight[ i ] );
        result += x[ i ] * weight[ i ];
        remainder -= weight[ i ];
    }
    result += x[ 0 ] * remainder;

    logger.debug( "result=" + result );

    return result;
}

public int getSampleSize()
{
    return this.sampleSize;
}

}
