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



protected WellMeasurementType primaryIndicator = null;
protected WellMeasurementType secondaryIndicator = null;

public SimpleDiscontinuityDataRateReducerV2()
{

}



@Override
public GasWellDataSet reduce( GasWellDataSet original, int maxIntervals )
{
    GasWellDataSet result;
    List<Double> unsorted = new ArrayList<Double>();
    List<Double> sorted = new ArrayList<Double>();
    List<GasWellDataEntry> dataEntries;
    double median;
    double discontinuityTriggerLimit;
    double current = 0.0;
    double previous = 0.0;
    List<GasWellDataBoundary> boundaries = new ArrayList<GasWellDataBoundary>();

    result = new GasWellDataSet( original.getWell() );

    dataEntries = original.getData();
    
    if ( primaryIndicator != null )
    {
        
    }

    for( GasWellDataEntry entry : dataEntries )
    {
        unsorted.add( entry.getMeasurement( primaryIndicator ) );
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

    boundaries.add( new GasWellDataBoundary( original.from(), "Start of data" ) );

    for( int i = 0; i < dataEntries.size(); i++ )
    {
        GasWellDataEntry entry = dataEntries.get( i );
        current = entry.getMeasurement( primaryIndicator );
        if ( i > 0 )
        {
            do {
                // moving to a zero value triggers a discontinuity...
                // ------------------------------------------------------------------------
                if ( ( previous >= 0.1 ) && ( current < 0.1 ) )
                {
                    logger.debug( "i=" + i + " .... Change from " + previous + " to " + current + " TRIGGERS discontinuity..." + entry.from() );
                    boundaries.add( new GasWellDataBoundary( entry.from(), "Outage starts" ) );
                    break;
                }

                // moving out of a zero value triggers a discontinuity...
                // ------------------------------------------------------------------------
                if  ( ( previous < 0.1 ) && ( current >= 0.1 ) )
                {
                    logger.debug( "i=" + i + " .... Change from " + previous + " to " + current + " TRIGGERS discontinuity..." + entry.from() );
                    boundaries.add( new GasWellDataBoundary( entry.from(), "Outage ends" ) );
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

    boundaries.add( new GasWellDataBoundary( original.until(), "End of data" ) );


    GasWellDataBoundary[] boundaryArray = new GasWellDataBoundary[ boundaries.size() ];
    boundaries.toArray( boundaryArray );


    result = new GasWellDataSet( original, boundaries );

    logger.debug( result );

    return result;  //To change body of implemented methods use File | Settings | File Templates.
}

public WellMeasurementType getPrimaryIndicator()
{
    return primaryIndicator;
}

public void setPrimaryIndicator(WellMeasurementType primaryIndicator)
{
    this.primaryIndicator = primaryIndicator;
}

public WellMeasurementType getSecondaryIndicator()
{
    return secondaryIndicator;
}

public void setSecondaryIndicator(WellMeasurementType secondaryIndicator)
{
    this.secondaryIndicator = secondaryIndicator;
}

}
