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

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Initial naive implementation of a data rate reducer...
 */
public class SimpleDiscontinuityDataRateReducerV2 implements DataRateReducer
{
static Logger logger = Logger.getLogger( SimpleDiscontinuityDataRateReducerV2.class );

private final static long MEDIAN_CROSSING_MIN_GAP_MS = 7 * 86400 * 1000L;
private final static long THREE_DAYS = 86400 * 3 * 1000L;
private final static long FORTNIGHT = 86400 * 14 * 1000L;

protected ReductionParameters recipe = null;

public SimpleDiscontinuityDataRateReducerV2( ReductionParameters params )
{
    if ( params == null )
    {
        throw new NullPointerException( "Must specify a set of reduction parameters!" );
    }
    this.recipe = params;
}


@Override
public GasWellDataSet reduce( GasWellDataSet original )
{
    GasWellDataSet result;
    List<Double> unsorted = new ArrayList<Double>();
    List<Double> sorted = new ArrayList<Double>();
    List<GasWellDataEntry> dataEntries;
    double median;
    double discontinuityTriggerLimit;
    double current = 0.0;
    double previous = 0.0;
    double currentSecondary = 0.0;
    double previousSecondary = 0.0;
    List<GasWellDataBoundary> discontinuityBoundaries = new ArrayList<GasWellDataBoundary>();
    List<GasWellDataBoundary> regularBoundaries = null;
    List<GasWellDataBoundary> combinedBoundaries = null;
    int discontinuityIndex = 0;
    int regularBoundaryIndex = 0;
    GasWellDataBoundary prevDiscontinuityBoundary = null;
    GasWellDataBoundary currentDiscontinuityBoundary = null;
    GasWellDataBoundary nextDiscontinuityBoundary = null;
    GasWellDataBoundary prevRegularBoundary = null;
    GasWellDataBoundary currentRegularBoundary = null;
    GasWellDataBoundary nextRegularBoundary = null;
    Date regularBoundaryStart = null;
    Calendar boundaryCalendar;
    Date boundaryStamp;
    boolean fini = false;
    boolean wasUnderC45 = true;
    boolean wasOverC55 = true;

    result = new GasWellDataSet( original.getWell() );

    dataEntries = original.getData();
    
    if ( recipe.primaryIndicator == null )
    {
        recipe.primaryIndicator = original.containsMeasurement( WellMeasurementType.OIL_FLOW ) ? WellMeasurementType.OIL_FLOW : WellMeasurementType.CONDENSATE_FLOW;
    }

    // todo: let's determine the regular boundaries...
    // ------------------------------------
    if ( ( recipe.regularBoundaryStart != null ) && ( recipe.regularBoundarySpanQuantity > 0 ) && ( recipe.regularBoundarySpanUnit != null ) )
    {
        regularBoundaries = new ArrayList<GasWellDataBoundary>();
        // if the start boundary is within a day of the actual data boundary, then just use the start boundary!!
        // --------------------------------------------------------------------------------------------------------
        if ( recipe.regularBoundaryStart.getTime() < original.from().getTime() + 86400000L )
        {
            regularBoundaryStart = original.from();
        } else {
            regularBoundaryStart = recipe.regularBoundaryStart;
        }
        
        boundaryCalendar = Calendar.getInstance();
        boundaryCalendar.setTime( regularBoundaryStart );
        
        do {
            boundaryCalendar.add( recipe.regularBoundarySpanUnit.getCalendarField(), recipe.regularBoundarySpanQuantity  );
            boundaryStamp = boundaryCalendar.getTime();
            if ( boundaryStamp.getTime() < original.until().getTime() )
            {
                boolean startStamp = boundaryStamp.getTime() == original.from().getTime();
                regularBoundaries.add( new GasWellDataBoundary( boundaryStamp, "automated regular boundary marker", startStamp ? GasWellDataBoundary.BoundaryType.START : GasWellDataBoundary.BoundaryType.AUTOMATED_REGULAR_BOUNDARY ) );
            }
            
        } while( boundaryStamp.before( original.until() ) );
        
    }


    for( GasWellDataEntry entry : dataEntries )
    {
        unsorted.add( entry.containsMeasurement( recipe.primaryIndicator ) ? entry.getMeasurement( recipe.primaryIndicator ) : 0.0 );
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

    double c45 = unsorted.get( (int)Math.floor( unsorted.size() * 0.45 ) );
    double c55 = unsorted.get( (int)Math.ceil( unsorted.size() * 0.55 ) );

    logger.debug( "min=" + unsorted.get( 0 ) );
    logger.debug( "max=" + unsorted.get( unsorted.size() - 1 ) );
    logger.debug( "median=" + median + ", c45=" + c45 + ", c55=" + c55 );

    // only add the start of data marker if we haven't already added the same marker in the
    // regular boundaries.
    if ( ( regularBoundaries == null ) || ( regularBoundaries.get( 0 ).getTimestamp().getTime() > original.from().getTime() ) )
    {
        discontinuityBoundaries.add( new GasWellDataBoundary(original.from(), "Start of data", GasWellDataBoundary.BoundaryType.START ) );
    }

    for( int i = 0; i < dataEntries.size(); i++ )
    {
        GasWellDataEntry entry = dataEntries.get( i );
        current = entry.containsMeasurement( recipe.primaryIndicator ) ? entry.getMeasurement( recipe.primaryIndicator ) : 0.0;
        if ( ( recipe.secondaryIndicator != null ) && ( recipe.secondaryIndicator != recipe.primaryIndicator )  )
        {
            currentSecondary = entry.containsMeasurement( recipe.secondaryIndicator ) ? entry.getMeasurement( recipe.secondaryIndicator ) : 0.0;
        }
        long sinceLast = entry.from().getTime() - discontinuityBoundaries.get( discontinuityBoundaries.size() - 1 ).getTimestamp().getTime();
        if ( i > 0 )
        {
            do {
                // moving to a zero value triggers a discontinuity...
                // ------------------------------------------------------------------------
                if ( ( previous >= 0.1 ) && ( current < 0.1 ) )
                {
                    logger.debug( "i=" + i + " .... Change from " + previous + " to " + current + " TRIGGERS discontinuity..." + entry.from() );
                    discontinuityBoundaries.add( new GasWellDataBoundary( entry.from(), "Outage starts on primary indicator (" + recipe.primaryIndicator + ")", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_START ) );
                    break;
                }
                
                // secondary indicator moving to zero, if the primary indicator is not already at zero triggers
                // a discontinuity...
                // ---------------------------------------------------------------------------------------------
                if ( ( ( recipe.secondaryIndicator != null ) && ( recipe.secondaryIndicator != recipe.primaryIndicator ) ) && ( ( ( previousSecondary >= 0.1 ) && ( currentSecondary < 0.1 ) ) && ( current >= 0.1 ) ) )
                {
                    logger.debug( "i=" + i + " ... secondary change from " + previousSecondary + " to " + currentSecondary + " TRIGGERS discontinuity..." + entry.from() );
                    discontinuityBoundaries.add( new GasWellDataBoundary( entry.from(), "Outage starts on secondary indicator (" + recipe.secondaryIndicator + ")", GasWellDataBoundary.BoundaryType.SECONDARY_INDICATOR_OUTAGE_START ) );
                }

                // moving out of a zero value triggers a discontinuity...
                // ------------------------------------------------------------------------
                if  ( ( previous < 0.1 ) && ( current >= 0.1 ) )
                {
                    logger.debug( "i=" + i + " .... Change from " + previous + " to " + current + " TRIGGERS discontinuity..." + entry.from() );
                    discontinuityBoundaries.add( new GasWellDataBoundary( entry.from(), "Outage ends on primary indicator ("+ recipe.primaryIndicator + ")", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_OUTAGE_END ) );
                    break;
                }

                // secondary indicator moving out of outage, if the primary indicator is not still at zero triggers
                // a discontinuity...
                // ---------------------------------------------------------------------------------------------
                if ( ( ( recipe.secondaryIndicator != null ) && ( recipe.secondaryIndicator != recipe.primaryIndicator ) ) && ( ( ( previousSecondary < 0.1 ) && ( currentSecondary >= 0.1 ) ) && ( current >= 0.1 ) ) )
                {
                    logger.debug( "i=" + i + " ... secondary change from " + previousSecondary + " to " + currentSecondary + " TRIGGERS discontinuity..." + entry.from() );
                    discontinuityBoundaries.add( new GasWellDataBoundary( entry.from(), "Outage ends on secondary indicator (" + recipe.secondaryIndicator + ")", GasWellDataBoundary.BoundaryType.SECONDARY_INDICATOR_OUTAGE_END  ) );
                }
                
                if ( recipe.detectMedianCrossers && ( previous < c55 ) && ( current >= c55 ) && ( wasUnderC45 ) && ( sinceLast > FORTNIGHT ) )
                {
                    logger.debug( "i=" + i + " .... primary indicator rises above c55 at " + entry.from() );
                    discontinuityBoundaries.add( new GasWellDataBoundary( entry.from(), "Primary indicator (" + recipe.primaryIndicator + ") rises above 55th percentile", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_MEDIAN_CROSSING ) );
                    wasUnderC45 = false;
                    wasOverC55 = true;
                }
                
                if ( recipe.detectMedianCrossers && ( previous >= c45 ) && ( current < c45 ) && ( wasOverC55 ) && ( sinceLast > FORTNIGHT ) )
                {
                    logger.debug( "i=" + i + " .... primary indicator falls below c45 at " + entry.from() );
                    discontinuityBoundaries.add( new GasWellDataBoundary( entry.from(), "Primary indicator (" + recipe.primaryIndicator + ") falls below 55th percentile", GasWellDataBoundary.BoundaryType.PRIMARY_INDICATOR_MEDIAN_CROSSING ) );
                    wasOverC55 = false;
                    wasUnderC45 = true;
                }

            } while( false );

        }
        previous = current;
        previousSecondary = currentSecondary;
    }
    
    // ok ... now we have to merge them....
    // ------------------------------------
    if ( regularBoundaries != null )
    {
        combinedBoundaries = GasWellDataBoundary.merge( regularBoundaries, discontinuityBoundaries );
    } else {
        combinedBoundaries = discontinuityBoundaries;
    }

    result = new GasWellDataSet( original, combinedBoundaries );

    return result;  //To change body of implemented methods use File | Settings | File Templates.
}

/**
 * 
 */
protected List<GasWellDataBoundary> merge( List<GasWellDataBoundary> alpha, List<GasWellDataBoundary> beta )
{
    List<GasWellDataBoundary> result = null;

    return result;
}



}
