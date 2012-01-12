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

package au.com.polly.ddr;

import au.com.polly.plotter.TimeUnit;
import au.com.polly.util.DateRange;

import java.io.PrintWriter;
import java.text.NumberFormat;

/**
 * Output a reduced version of the BY11 data..
 */
public class p52
{

	/*
private final static DateRange[] intervalDates = new DateRange[] {
	new DateRange( "16/Jul/06 20:47", "08/Aug/06 08:25" ),
	new DateRange( "08/Aug/06 08:25", "14/Aug/06 15:19" ),
	new DateRange( "14/Aug/06 15:19", "19/Sep/06 13:06" ),
	new DateRange( "19/Sep/06 13:06", "20/Oct/06 14:07" ),
	new DateRange( "20/Oct/06 14:07", "14/May/07 20:02" ),
	new DateRange( "14/May/07 20:02", "01/Jun/07 04:57" ),
	new DateRange( "01/Jun/07 04:57", "05/Jun/07 07:47" ),
	new DateRange( "22/Jun/07 06:06", "02/Jul/07 04:57" ),
	new DateRange( "02/Jun/07 04:57", "01/Feb/08 04:57" ),
	new DateRange( "01/Feb/08 04:57", "01/Jul/08 04:57" ),
	new DateRange( "01/Jul/08 04:57", "01/Aug/08 04:57" ),
	new DateRange( "01/Aug/08 04:57", "05/Aug/08 23:42" ),
	new DateRange( "05/Aug/08 23:42", "13/Aug/08 01:42" ),
	new DateRange( "13/Aug/08 01:42", "17/Jan/09 18:02" ),
	new DateRange( "17/Jan/09 18:02", "22/Jan/09 09:36" ),
	new DateRange( "22/Jan/09 09:36", "12/Mar/09 16:20" ),
	new DateRange( "12/Mar/09 16:20", "18/Mar/09 17:51" ),
	new DateRange( "18/Mar/09 17:51", "12/Aug/09 03:03" ),
	new DateRange( "12/Aug/09 03:03", "15/Aug/09 23:33" ),
	new DateRange( "15/Aug/09 23:33", "27/May/10 23:33" ),
	new DateRange( "27/May/10 23:33", "02/Jun/10 15:24" ),
	new DateRange( "02/Jun/10 15:24", "17/Sep/10 08:57" ),
	new DateRange( "17/Sep/10 08:57", "21/Sep/10 08:57" ),
	new DateRange( "21/Sep/10 08:57", "27/Sep/10 12:00" ),
	new DateRange( "21/Sep/10 08:57", "27/Sep/11 01:11" )
};
	          */
	private final static DateRange[] intervalDates = new DateRange[] {
			new DateRange( "16/Jul/2006 20:47", "08/Aug/2006 08:25" ),
			new DateRange( "08/Aug/2006 08:25", "14/Aug/2006 15:19" ),
			new DateRange( "14/Aug/2006 15:19", "19/Sep/2006 13:06" ),
			new DateRange( "19/Sep/2006 13:06", "20/Oct/2006 14:07" ),
			new DateRange( "20/Oct/2006 14:07", "14/May/2007 20:02" ),
			new DateRange( "14/May/2007 20:02", "01/Jun/2007 04:57" ),
			new DateRange( "01/Jun/2007 04:57", "22/Jun/2007 06:06" ),
			new DateRange( "22/Jun/2007 06:06", "02/Jul/2007 04:57" ),
			new DateRange( "02/Jul/2007 04:57", "01/Feb/2008 04:57" ),
			new DateRange( "01/Feb/2008 04:57", "01/Jul/2008 04:57" ),
			new DateRange( "01/Jul/2008 04:57", "01/Aug/2008 04:57" ),
			new DateRange( "01/Aug/2008 04:57", "05/Aug/2008 23:42" ),
			new DateRange( "05/Aug/2008 23:42", "13/Aug/2008 01:42" ),
			new DateRange( "13/Aug/2008 01:42", "17/Jan/2009 18:02" ),
			new DateRange( "17/Jan/2009 18:02", "22/Jan/2009 09:36" ),
			new DateRange( "22/Jan/2009 09:36", "12/Mar/2009 16:20" ),
			new DateRange( "12/Mar/2009 16:20", "18/Mar/2009 17:51" ),
			new DateRange( "18/Mar/2009 17:51", "12/Aug/2009 03:03" ),
			new DateRange( "12/Aug/2009 03:03", "15/Aug/2009 23:33" ),
			new DateRange( "15/Aug/2009 23:33", "27/May/2010 23:33" ),
			new DateRange( "27/May/2010 23:33", "02/Jun/2010 15:24" ),
			new DateRange( "02/Jun/2010 15:24", "17/Sep/2010 08:57" ),
			new DateRange( "17/Sep/2010 08:57", "21/Sep/2010 08:57" ),
			new DateRange( "21/Sep/2010 08:57", "27/Sep/2010 12:00" ),
			new DateRange( "27/Sep/2010 12:00", "27/Sep/2011 01:11" ),

	};


	public static void main( String[] argv )
	{
		GasWellDataSet original = TestGasWellDataSet.getNicksDataSet();
		GasWellDataSet modified = new GasWellDataSet( original.getWell() );

		for( DateRange range : intervalDates )
		{
			GasWellDataEntry entry = original.consolidateEntries( range );
			modified.addDataEntry(  entry );
		}

		PrintWriter writer = new PrintWriter( System.out );

		modified.outputCSV(  writer  );
		
//		reducedBY11Data.outputCSV( writer, true, false );
		writer.flush();
		System.exit(  0  );
	}

}
