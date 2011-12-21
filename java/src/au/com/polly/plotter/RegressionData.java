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

package au.com.polly.plotter;


public class RegressionData<T extends Number,U extends Number>
{
    double correlationCoefficient = 0.0;
    double gradient = 0.0;
    double intercept = 0.0;

    public RegressionData( PlotData<T,U> plot )
    {
        this( plot.getXAxisData(), plot.getYAxisData() );
    }

    public RegressionData( DataSeries<T> x, DataSeries<U> y)
    {
        double sumx = 0.0;
        double sumy = 0.0;
        double sumx2 = 0.0;
        double sumy2 = 0.0;
        double sumxy = 0.0;
        double x0;
        double y0;
        double x2;
        double y2;
        int n;

        if ( ( x == null ) || ( y == null ) )
        {
            throw new NullPointerException( "Cannot process null data series." );
        }

        if ( x.size() != y.size() )
        {
            throw new IllegalArgumentException( "X data series has " + x.size() + " elements, y data series has " + y.size() + " elements. They must both be of the same length." );
        }

        int i;
        for( i = 0; i < x.size(); i++ )
        {
            x0 = x.getData().get( i ).doubleValue();
            y0 = y.getData().get( i ).doubleValue();
            x2 = x0 * x0;
            y2 = y0 * y0;
            sumx += x0;
            sumx2 += x2;
            sumy += y0;
            sumy2 += y2;
            sumxy += ( x0 * y0 );
        }

        n = x.size();

        double varianceX = ( sumx2 / (double)n ) - ( ( sumx * sumx ) / (double)( n * n ) );
        double varianceY = ( sumy2 / (double)n ) - ( ( sumy * sumy ) / (double)( n * n ) );
        double covariance = ( sumxy / (double)n ) - ( ( sumx * sumy ) / (double)( n * n ) );
        this.correlationCoefficient = covariance / Math.sqrt(varianceX * varianceY);
        this.gradient = ( ( sumx * sumy ) - ( (double)n * sumxy ) ) / ( ( sumx * sumx ) - ( (double)n * sumx2 ) );
        this.intercept = ( ( sumx * sumxy ) - ( sumy * sumx2 ) ) / ( ( sumx * sumx ) - ( (double)n * sumx2 ) );
    }

    double getCorrelationDetermination()
    {
        return correlationCoefficient * correlationCoefficient;
    }

    double getCorrelationCoefficient()
    {
        return correlationCoefficient;
    }

    double getGradient()
    {
        return gradient;
    }

    double getIntercept()
    {
        return intercept;
    }
}
