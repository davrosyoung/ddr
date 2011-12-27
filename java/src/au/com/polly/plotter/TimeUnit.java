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

import java.util.Calendar;

/**
 * Represents the units of time which can be used to represent the horizontal time axis upon a graph.
 * The year unit is intentionally approximate, and is sufficient for presenting data upon a graph
 * where the time axis unit is in years. Months cannot be sufficiently expressed in seconds and
 * need to be calculated differently.
 * 
 * 
 */
public enum TimeUnit
{
    MILLISECOND( Calendar.MILLISECOND, 1L, "milliseconds" ),
    SECOND( Calendar.SECOND, 1000L, "seconds" ),
    MINUTE( Calendar.MINUTE, 60 * 1000L, "minutes" ),
    HOUR( Calendar.HOUR, 3600 * 1000L, "hours" ),
    DAY( Calendar.DAY_OF_YEAR, 86400 * 1000L, "days" ),
    WEEK( Calendar.WEEK_OF_YEAR, 7 * 86400 * 1000L, "weeks" ),
    MONTH( Calendar.MONTH, -1, "months" ),
    YEAR( Calendar.YEAR, 365243 * 86400L, "years" );

    private int calendarField;
    private long milliSeconds;
    private String menuLabel;

    private TimeUnit( int calendarField, long ms, String menuLabel )
    {
        this.calendarField = calendarField;
        this.milliSeconds = ms;
        this.menuLabel = menuLabel;
    }

    public String toString()
    {
        return menuLabel;
    }

    public int getCalendarField()
    {
        return calendarField;
    }

}
