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

/**
 * Represents either a vertical or horizontal axis.
 */
public class NumericAxis<T extends Number>
extends BaseAxis<T>    
implements Axis<T>
{
boolean empty = true;

/**
 * Invoking this method will cause the axis to determine the most appropriate scaling and
 * intervals for the data set.
 */
public void autoScale( DataSeries<T> data )
{
    double min;
    double max;
    boolean changed = false;

    // determine the minimum and maximum values...
    // ------------------------------------------
    if ( empty || data.getMin().doubleValue() < this.min )
    {
        min = data.getMin().doubleValue();
        changed = true;
    } else {
        min = this.min;
    }

    if ( empty || data.getMax().doubleValue() > this.max)
    {
        max = data.getMax().doubleValue();
        changed = true;
    } else {
        max = this.max;
    }

    // only recalculate the axis, if the min or max values have been modified...
    // -----------------------------------------------------------------------------
    if ( changed )
    {
        calculate( min, max );
    }
}

public void scale( Number min, Number max )
{
    calculate( min.doubleValue(), max.doubleValue() );
}

/**
 *
 * @param min the minimum value to be displayed on the axis.
 * @param max the maximum value to be displayed upon the axis.
 *
 * adjusts the display of the axis, for such things as interval sizes, and where the axis will begin/end
 * depending upon the values specified....
 */
public void calculate( double min, double max )
{
    double range = 0.0;
    double intervalFactor;

    range = max - min;
    intervalFactor = calculateIntervalFactor( range );

    // now calculate an appropriate interval size, to provide us with
    // between five and ten intervals...
    // ----------------------------------------------------------------
    do {

        // if we already satisfy our criteria, then just stick with
        // what we've got..................
        // ---------------------------------------------------------
        if ( ( range / intervalFactor) > 5 )
        {
            this.intervalSize = intervalFactor;
            break;
        }

        // If we've already got at least three intervals,
        // then create interval size of half the interval factor.
        // ---------------------------------------------------------
        if ( ( range / intervalFactor) >= 3 )
        {
            this.intervalSize = Math.ceil(intervalFactor / 2);
            break;
        }

        // Create the interval size to be a fifth of the
        // interval factor, this will give us between 5 and 10 intervals.
        // ---------------------------------------------------------
        this.intervalSize = Math.ceil(intervalFactor / 5);

    } while( false ); // end-do-WHILE(false)


    int minInterval = (int) Math.floor(min / intervalSize);
    int maxInterval = (int) Math.ceil(max / intervalSize);
    this.numberIntervals = maxInterval - minInterval;
    this.min = minInterval * intervalSize;
    this.max = maxInterval * intervalSize;
    // reset the empty flag.
    empty = false;
}

    

}
