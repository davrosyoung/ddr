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

import au.com.polly.util.DateArmyKnife;
import au.com.polly.util.DateRange;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
 * 
 * 
 * if the datasets alpha and beta do not cover exactly the same period of time, then the
 * result will be applied across the common period of time between them. if there is no
 * common period of time, then an IllegalArgumentException will be raised.
 */
public static Map<WellMeasurementType,Double> getDelta(GasWellDataSet alpha, GasWellDataSet beta)
{
    DateRange commonRange;
    if ( ( alpha == null ) || ( beta == null ) )
    {
        throw new NullPointerException( "Cannot calculate differences between NULL data sets!! Get real!!" );
    }
    
    DateRange alphaDateRange = new DateRange( alpha.from(), alpha.until() );
    DateRange betaDateRange = new DateRange( beta.from(), beta.until() );
    commonRange = alphaDateRange.common( betaDateRange );
    
    Map<WellMeasurementType,Double> alphaVolume = calculateTotalVolume( alpha, commonRange );
    Map<WellMeasurementType,Double> betaVolume = calculateTotalVolume( beta, commonRange );
    
    Map<WellMeasurementType,Double> result = new HashMap<WellMeasurementType,Double>();
    for( WellMeasurementType wmt : WellMeasurementType.values() )
    {
        if ( alphaVolume.containsKey( wmt ) || ( betaVolume.containsKey( wmt ) ) )
        {
            double alphaValue = alphaVolume.containsValue( wmt ) ? alphaVolume.get( wmt ) : 0;
            double betaValue = alphaVolume.containsValue( wmt ) ? alphaVolume.get( wmt ) : 0;
            double delta = Math.abs( alphaValue - betaValue );
            result.put( wmt, delta );
        }
    }
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
        GasWellDataEntry bSlice = b.consolidateEntries( entry.from(), entry.until() );

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
                    logger.trace( "slice[ " + entry.from() + " - " + entry.until() + "] " + wmt + " ... error=" + error + ", existing=" + existing + ", aValue=" + aMeasurement + ", bMeasurement=" + bMeasurement );
                }
            }
        }
    }

    return result;
}

/**
 *
 * @param dataSet the set of measurements for a given gas well
 * @return
 */
public static Map<WellMeasurementType,Double> calculateTotalVolume( GasWellDataSet dataSet )
{
    return calculateTotalVolume( dataSet, new DateRange( dataSet.from(), dataSet.until() ) );
}

/**
 *
 * @param dataSet the set of measurements for a given gas well
 * @param from  if not-null, then the date that the calculation should start from...
 * @param until if not-null, then the date that the calculation should extend until.
 * @return
 */
public static Map<WellMeasurementType,Double> calculateTotalVolume( GasWellDataSet dataSet, Date from, Date until )
{
    return calculateTotalVolume( dataSet, new DateRange( from, until ) );
}
/**
 *
 * @param dataSet the set of measurements for a given gas well
 * @param range if non null, the period of time that the volume should be calculated for.
 * @return
 */
public static Map<WellMeasurementType,Double> calculateTotalVolume( GasWellDataSet dataSet, DateRange range )
{
    Map<WellMeasurementType,Double> result = new HashMap();
    Date fromStamp;
    Date untilStamp;
    long overlapMS;

    if ( dataSet == null )
    {
        throw new NullPointerException( "Must specify averagedDataSet, you specified NULL!!" );
    }
    
    fromStamp = ( range != null ) ? range.from() : dataSet.from();
    untilStamp = ( range != null ) ? range.until() : dataSet.until();
    
    if ( fromStamp.before( dataSet.from() ) )
    {
        throw new IllegalArgumentException( "Start date \"" + fromStamp + "\" specified is BEFORE the measurement data starts \"" + dataSet.from() + "\"" );
    }

    if ( untilStamp.after(dataSet.until()) )
    {
        throw new IllegalArgumentException( "End date \"" + fromStamp + "\" specified is AFTER the measurement data ends \"" + dataSet.until() + "\"" );
    }
    
    // let's add up the total volume between the dates specified...
    // ---------------------------------------------------------------
    for( GasWellDataEntry entry : dataSet.getData() )
    {
        if ( ( overlapMS = entry.getDateRange().overlap(new DateRange( fromStamp, untilStamp, 1000L ) ) ) > 0 )
        {
            // how many "days" do we cover within this interval??
            // -------------------------------------------------
            double intervalLengthDays = overlapMS / 86400000.0; // remember that flow rates are in volume units per day!!
            
            for( WellMeasurementType wmt : WellMeasurementType.values() )
            {
                if ( entry.containsMeasurement( wmt ) )
                {
                    double value = result.containsKey( wmt ) ? result.get( wmt ) : 0.0;
                    value += ( intervalLengthDays * entry.getMeasurement( wmt ) );
                    result.put( wmt, value );
                }
            } // end-FOR( each measurement type )
        } // end-IF( our date range falls within this measurement entry's interval )
    } // end-FOR( each measurement entry )

    return result;
}

/**
 * @param original the original date/time boundary
 * @param updated the revised date/time boundary.
 *
 * @throws IllegalArgumentException if the original date could not be found, or if the updated
 * date/time is either before the start of the previous boundary, or the original date/time is the
 * start/end of all data or the updated dte/time is after the end of the next boundary.
 */
public static void moveAveragedDataBoundary( Date original, Date updated, GasWellDataSet originalData, GasWellDataSet averagedData )
{
	List<Date> stampList;
	int pos;
	GasWellDataEntry entry;
	GasWellDataEntry previousEntry;

	// some rudimentary sanity checks....
	// -----------------------------------
	if ( original == null )
	{
		throw new NullPointerException( "original date/time must NOT be NULL" );
	}

	if ( updated == null )
	{
		throw new NullPointerException( "updated date/time must NOT be NULL" );
	}

	if ( averagedData.from().after( original ) || averagedData.from().equals( original ) )
	{
		throw new IllegalArgumentException( "original date/time (" + original + ") on or BEFORE start of data (" + averagedData.from() + ")" );
	}

	if ( averagedData.until().before( original ) || averagedData.until().equals( original ) )
	{
		throw new IllegalArgumentException( "original date/time (" + original + ") on or AFTER end of data (" + averagedData.until() + ")" );
	}

	if ( averagedData.from().after( updated ) || averagedData.from().equals( updated ) )
	{
		throw new IllegalArgumentException( "updated date/time (" + updated + ") on or BEFORE start of data (" + averagedData.from() + ")" );
	}

	if ( averagedData.until().before( updated ) || averagedData.until().equals( updated ) )
	{
		throw new IllegalArgumentException( "updated date/time (" + updated + ") on or AFTER end of data (" + averagedData.until() + ")" );
	}

	// endeavour to find the original timestamp ....
	// ----------------------------------------------
	stampList = averagedData.getBoundaryTimestamps();
	pos = Collections.binarySearch( stampList, original );

	if ( pos < 0 )
	{
		throw new IllegalArgumentException( "Oh no!!! The original date could not be found!!!" );
	}

	entry = averagedData.getEntry( pos );
	previousEntry = averagedData.getEntry( pos - 1 );

	logger.debug( "pos=" + pos );
	logger.debug( "entry=" + entry );
	logger.debug( "previousEntry=" + previousEntry );

	if ( updated.getTime() <= previousEntry.from().getTime() )
	{
		throw new IllegalArgumentException( "updated date/time (" + updated + ") is on or BEFORE previous interval start date/time " + previousEntry.from() );
	}

	if ( updated.getTime() >= entry.until().getTime() )
	{
		throw new IllegalArgumentException( "updated date/time (" + updated + ") is on or AFTER next interval start date/time " + entry.until() );
	}

	GasWellDataEntry replacementPreviousEntry = originalData.consolidateEntries( previousEntry.from(), updated );
	replacementPreviousEntry.setComment( previousEntry.getComment() );
	GasWellDataEntry replacementEntry = originalData.consolidateEntries( updated, entry.until() );
	replacementEntry.setComment( entry.getComment() );

	averagedData.getData().set( pos, replacementEntry );
	averagedData.getData().set( pos - 1, replacementPreviousEntry );
}


}
