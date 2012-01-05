/*
 * Copyright (c) 2011-2012 Polly Enterprises Pty Ltd and/or its affiliates.
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
 * Represents either a vertical or horizontal axis. The implmenting classes ensure
 * that an appropriate axis is generated for the data set fed to this class.
 */
public interface Axis<T extends Number>
{

/**
 * Invoking this method will cause the axis to determine the most appropriate scaling and
 * intervals for the data set.
 */
public void autoScale(DataSeries<T> data);

/**
 *
 * @param min
 * @param max
 *
 *
 * This method is invoked when arbitrary constraints are to be set upon the axis.
 */
public void scale(Number min, Number max);


/**
 *
 * @param value the value from the data series, applicable to this axis, to be plotted.
 * @return the number of pixels along this axis that the specified value should be plotted at.
 *
 */
public int getPosition(double value, AxisConfiguration configuration);

/**
 *
  * @return the number of intervals that this axis covers. each interval will be delineated
 * by a tick mark when rendered.
 */
public int getNumberIntervals();

/**
 *
  * @return the value represented by the start of the first interval, this is generally smaller
 * than the actual minimum data value that was submitted to the autoScale() method.
 */
public double getMinimumValue();


/**
 *
  * @return the value represented by the end of the last interval, this is generally larger
 * than the actual maximum data value that was submitted to the autoScale() method.
 */
public double getMaximumValue();

/**
 *
  * @return the length/size of each interval
 */
public double getIntervalSize();

/**
 *
  * @param value the data point to obtain a label for
 * @return appropriate text for the data point.
 */
public String getDataLabel(T value);

}