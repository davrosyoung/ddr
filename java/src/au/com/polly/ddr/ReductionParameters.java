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

import au.com.polly.plotter.TimeUnit;
import au.com.polly.util.DateArmyKnife;

import java.util.Date;

/**
 * Set of parameters used to carry out a discontinuity data flow reduction.
 * 
 */
public class ReductionParameters
{
protected WellMeasurementType primaryIndicator = null;
protected WellMeasurementType secondaryIndicator = null;
protected int regularBoundarySpanQuantity = -1;
protected TimeUnit regularBoundarySpanUnit = null;
protected Date regularBoundaryStart = null;
protected boolean detectMedianCrossers = false;

public ReductionParameters( WellMeasurementType indicator )
{
    this( indicator, null );
}

public ReductionParameters( WellMeasurementType primary, WellMeasurementType secondary ) 
{
        this( primary, secondary, false, null, -1, null );    
}


public ReductionParameters( WellMeasurementType primary, WellMeasurementType secondary, Date regularBoundaryStart, int boundarySpanQuantity, TimeUnit boundarySpanUnit )
{
    this( primary, secondary, true, regularBoundaryStart, boundarySpanQuantity, boundarySpanUnit );
}

public ReductionParameters( WellMeasurementType primary, WellMeasurementType secondary, boolean insertRegularBoundaries, Date regularBoundaryStart, int boundarySpanQuantity, TimeUnit boundarySpanUnit )
{
    this.primaryIndicator = primary;
    this.secondaryIndicator = secondary;
    if ( insertRegularBoundaries )
    {
        regularBoundaryStart = regularBoundaryStart;
        regularBoundarySpanQuantity = boundarySpanQuantity;
        regularBoundarySpanUnit = boundarySpanUnit;
    }
}

public String toString()
{
    StringBuilder out = new StringBuilder();
    
    out.append( "primary indicator=" + primaryIndicator );
    if ( ( secondaryIndicator != null ) && ( secondaryIndicator != primaryIndicator ) )
    {
        out.append( ", secondary indicator=" + secondaryIndicator );
    }
    out.append( detectMedianCrossers ? ", do" : ", don't" );
    out.append( "detected median crossers" );
    if ( regularBoundaryStart != null )
    {
        out.append( ", regular boundaries starting at:" );
        out.append(DateArmyKnife.formatWithSeconds( regularBoundaryStart ) );
        out.append( " every ");
        out.append( regularBoundarySpanQuantity );
        out.append( regularBoundarySpanUnit );
    }

    return out.toString();
}

}
