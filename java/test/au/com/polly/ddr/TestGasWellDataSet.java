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

import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import au.com.polly.util.DateRange;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;

/**
 * Set of test data for use between DDR application tests.
 * 
 */
public class TestGasWellDataSet
{
static Logger logger = Logger.getLogger( TestGasWellDataSet.class );
static DateParser dateParser = new AussieDateParser();
static protected GasWellDataSet nicksDataSet = null;
static protected GasWellDataSet saa2FragmentDataSet = null;
static protected GasWellDataSet dummyDataSet = null;
static protected GasWellDataSet smallDataSet = null;
static protected GasWellDataSet smallReducedDataSet = null;
static protected GasWell saa2Well = new GasWell( "SAA-2" );
static protected GasWell saa11Well = new GasWell( "SAA-11" );
static protected GasWell dummyWell = new GasWell( "Dummy" );

static final private DataSet[] nicksRawData = {
        new DataSet( dateParser.parse( "16/JUL/2006 20:47" ).getTime(), 0000.0, 0000.0, 0000.0, 0277.7000 ),
        new DataSet( dateParser.parse( "28/JUL/2006 10:29" ).getTime(), 1788.0, 1370.0, 0000.0, 0261.9333 ),
        new DataSet( dateParser.parse( "08/AUG/2006 08:25" ).getTime(), 1070.0, 0970.0, 0000.0, 0121.0666 ),
        new DataSet( dateParser.parse( "13/AUG/2006 09:29" ).getTime(), 0000.0, 0000.0, 0000.0, 0029.8333 ),
        new DataSet( dateParser.parse( "14/AUG/2006 15:19" ).getTime(), 1167.0, 1320.0, 0000.0, 0861.7833 ),
        new DataSet( dateParser.parse( "19/SEP/2006 13:06" ).getTime(), 0692.0, 0800.0, 0000.0, 0745.0167 ),
        new DataSet( dateParser.parse( "20/OKT/2006 14:07" ).getTime(), 0827.0, 1340.0, 0000.0, 4949.9167 ),
        new DataSet( dateParser.parse( "14/MAI/2007 20:02" ).getTime(), 0538.0, 1040.0, 0000.0, 0765.8667 ),
        new DataSet( dateParser.parse( "15/JUN/2007 17:54" ).getTime(), 0700.0, 1100.0, 0000.0, 0057.3667 ),
        new DataSet( dateParser.parse( "18/JUN/2007 03:16" ).getTime(), 0841.0, 1170.0, 0000.0, 0098.8333 ),
        new DataSet( dateParser.parse( "22/JUN/2007 06:06" ).getTime(), 0000.0, 0000.0, 0000.0, 0158.8500 ),
        new DataSet( dateParser.parse( "28/JUN/2007 20:57" ).getTime(), 0001.0, 0001.0, 0000.0, 0080.0000 ),
        new DataSet( dateParser.parse( "02/JUL/2007 04:57" ).getTime(), 0612.0, 0870.0, 0017.0, 5136.0000 ),
        new DataSet( dateParser.parse( "01/FEB/2008 04:57" ).getTime(), 0701.0, 1010.0, 0012.0, 3624.0000 ),
        new DataSet( dateParser.parse( "01/JUL/2008 04:57" ).getTime(), 0783.0, 1320.0, 0006.0, 0225.6500 ),
        new DataSet( dateParser.parse( "10/JUL/2008 14:36" ).getTime(), 0923.0, 1550.0, 0012.0, 0514.0167 ),
        new DataSet( dateParser.parse( "01/AUG/2008 00:37" ).getTime(), 0000.0, 0000.0, 0000.0, 0119.0833 ),
        new DataSet( dateParser.parse( "05/AUG/2008 23:42" ).getTime(), 0923.0, 0001.0, 0001.0, 0170.0000 ),
        new DataSet( dateParser.parse( "13/AUG/2008 01:42" ).getTime(), 0702.0, 0980.0, 0016.0, 3784.3333 ),
        new DataSet( dateParser.parse( "17/JAN/2009 18:02" ).getTime(), 0000.0, 0000.0, 0000.0, 0111.5667 ),
        new DataSet( dateParser.parse( "22/JAN/2009 09:36" ).getTime(), 0545.0, 0820.0, 0010.0, 1182.7333 ),
        new DataSet( dateParser.parse( "12/MAR/2009 16:20" ).getTime(), 0000.0, 0000.0, 0000.0, 0077.3333 ),
        new DataSet( dateParser.parse( "15/MAR/2009 21:40" ).getTime(), 0001.0, 0001.0, 0001.0, 0068.1833 ),
        new DataSet( dateParser.parse( "18/MAR/2009 17:51" ).getTime(), 0564.0, 0890.0, 0012.0, 3513.2000 ),
        new DataSet( dateParser.parse( "12/AUG/2009 03:03" ).getTime(), 0000.0, 0000.0, 0000.0, 0092.5000 ),
        new DataSet( dateParser.parse( "15/AUG/2009 23:33" ).getTime(), 0564.0, 0810.0, 0007.0, 6840.0000 ),
        new DataSet( dateParser.parse( "27/MAI/2010 23:33" ).getTime(), 0000.0, 0000.0, 0000.0, 0135.8500 ),
        new DataSet( dateParser.parse( "02/JUN/2010 15:24" ).getTime(), 0513.0, 0620.0, 0006.0, 2561.5500 ),
        new DataSet( dateParser.parse( "17/SEP/2010 08:57" ).getTime(), 0636.0, 0970.0, 0008.5, 0096.0000 ),
        new DataSet( dateParser.parse( "21/SEP/2010 08:57" ).getTime(), 0497.0, 0600.0, 0009.0, 0135.4667 ),
        new DataSet( dateParser.parse( "27/SEP/2010 00:25" ).getTime(), 0000.0, 0000.0, 0000.0, 8760.770 )
};

static final private DataSet[] saa2FragmentRawData = {
        new DataSet( dateParser.parse( "30/Jul/2009 00:00:00" ).getTime(), 286.510,0.190,951.640, 24.0 ),
        new DataSet( dateParser.parse( "31/Jul/2009 00:00:00" ).getTime(), 276.880,0.190,923.830, 24.0 ),
        new DataSet( dateParser.parse( "01/Aug/2009 00:00:00" ).getTime(), 284.430,0.190,926.970, 24.0 ),
        new DataSet( dateParser.parse( "02/Aug/2009 00:00:00" ).getTime(), 290.260,0.190,935.960, 24.0 ),
        new DataSet( dateParser.parse( "03/Aug/2009 00:00:00" ).getTime(), 248.420,0.170,783.260, 24.0 ),
        new DataSet( dateParser.parse( "04/Aug/2009 00:00:00" ).getTime(), 249.490,0.180,840.450, 24.0 ),
        new DataSet( dateParser.parse( "05/Aug/2009 00:00:00" ).getTime(), 223.240,0.150,763.600, 24.0 ),
        new DataSet( dateParser.parse( "06/Aug/2009 00:00:00" ).getTime(), 223.670,0.150,753.610, 24.0 ),
        new DataSet( dateParser.parse( "07/Aug/2009 00:00:00" ).getTime(), 176.540,0.160,741.400, 24.0 ),
        new DataSet( dateParser.parse( "08/Aug/2009 00:00:00" ).getTime(), 216.920,0.160,743.670, 24.0 ),
        new DataSet( dateParser.parse( "09/Aug/2009 00:00:00" ).getTime(), 203.180,0.150,698.740, 24.0 ),
        new DataSet( dateParser.parse( "10/Aug/2009 00:00:00" ).getTime(), 200.820,0.160,691.610, 24.0 ),
        new DataSet( dateParser.parse( "11/Aug/2009 00:00:00" ).getTime(), 219.480,0.150,633.090, 24.0 ),
        new DataSet( dateParser.parse( "12/Aug/2009 00:00:00" ).getTime(), 0.000,0.000,0.000, 24.0 ),
        new DataSet( dateParser.parse( "13/Aug/2009 00:00:00" ).getTime(), 0.000,0.000,0.000, 24.0 ),
        new DataSet( dateParser.parse( "14/Aug/2009 00:00:00" ).getTime(), 0.000,0.000,0.000, 24.0 ),
        new DataSet( dateParser.parse( "15/Aug/2009 00:00:00" ).getTime(), 40.660,0.000,150.730, 24.0 ),
        new DataSet( dateParser.parse( "16/Aug/2009 00:00:00" ).getTime(), 192.040,0.150,673.250, 24.0 ),
        new DataSet( dateParser.parse( "17/Aug/2009 00:00:00" ).getTime(), 181.730,0.140,632.840, 24.0 ),
        new DataSet( dateParser.parse( "18/Aug/2009 00:00:00" ).getTime(), 38.000,0.030,132.740, 24.0 ),
        new DataSet( dateParser.parse( "19/Aug/2009 00:00:00" ).getTime(), 0.000,0.000,0.000, 24.0 ),
        new DataSet( dateParser.parse( "20/Aug/2009 00:00:00" ).getTime(), 11.580,0.010,40.620, 24.0 ),
        new DataSet( dateParser.parse( "21/Aug/2009 00:00:00" ).getTime(), 0.000,0.000,0.000, 24.0 ),
        new DataSet( dateParser.parse( "22/Aug/2009 00:00:00" ).getTime(), 0.000,0.000,0.000, 24.0 ),
        new DataSet( dateParser.parse( "23/Aug/2009 00:00:00" ).getTime(), 105.000,0.090,383.840, 24.0 ),
        new DataSet( dateParser.parse( "24/Aug/2009 00:00:00" ).getTime(), 689.890,0.340,2087.480, 24.0 ),
        new DataSet( dateParser.parse( "25/Aug/2009 00:00:00" ).getTime(), 539.720,0.310,2042.970, 24.0 ),
        new DataSet( dateParser.parse( "26/Aug/2009 00:00:00" ).getTime(), 665.630,0.320,2017.700, 24.0 ),
        new DataSet( dateParser.parse( "27/Aug/2009 00:00:00" ).getTime(), 603.340,0.300,1877.530, 24.0 ),
        new DataSet( dateParser.parse( "28/Aug/2009 00:00:00" ).getTime(), 621.220,0.310,1843.600, 24.0 ),
        new DataSet( dateParser.parse( "29/Aug/2009 00:00:00" ).getTime(), 475.470,0.260,1636.590, 24.0 ),
        new DataSet( dateParser.parse( "30/Aug/2009 00:00:00" ).getTime(), 400.530,0.230,1438.690, 24.0 ),
        new DataSet( dateParser.parse( "31/Aug/2009 00:00:00" ).getTime(), 413.890,0.230,1429.430, 24.0 ),
        new DataSet( dateParser.parse( "01/Sep/2009 00:00:00" ).getTime(), 367.380,0.210,1256.190, 24.0 ),
        new DataSet( dateParser.parse( "02/Sep/2009 00:00:00" ).getTime(), 362.690,0.210,1241.720, 24.0 ),
        new DataSet( dateParser.parse( "03/Sep/2009 00:00:00" ).getTime(), 320.700,0.210,1089.110, 24.0 ),
        new DataSet( dateParser.parse( "04/Sep/2009 00:00:00" ).getTime(), 300.720,0.200,992.560, 24.0 ),
        new DataSet( dateParser.parse( "05/Sep/2009 00:00:00" ).getTime(), 529.740,0.290,1982.060, 24.0 ),
        new DataSet( dateParser.parse( "06/Sep/2009 00:00:00" ).getTime(), 543.560,0.300,1962.030, 24.0 ),
        new DataSet( dateParser.parse( "07/Sep/2009 00:00:00" ).getTime(), 590.910,0.300,1872.500, 24.0 ),
        new DataSet( dateParser.parse( "08/Sep/2009 00:00:00" ).getTime(), 583.420,0.300,1858.520, 24.0 ),
        new DataSet( dateParser.parse( "09/Sep/2009 00:00:00" ).getTime(), 559.420,0.300,1873.810, 24.0 ),
        new DataSet( dateParser.parse( "10/Sep/2009 00:00:00" ).getTime(), 483.930,0.260,1626.890, 24.0 ),
        new DataSet( dateParser.parse( "11/Sep/2009 00:00:00" ).getTime(), 52.800,0.030,194.090, 24.0 ),
        new DataSet( dateParser.parse( "12/Sep/2009 00:00:00" ).getTime(), 104.870,0.060,343.010, 24.0 ),
        new DataSet( dateParser.parse( "13/Sep/2009 00:00:00" ).getTime(), 80.300,0.060,1400.310, 24.0 ),
        new DataSet( dateParser.parse( "14/Sep/2009 00:00:00" ).getTime(), 8.500,0.010,153.480, 24.0 ),
        new DataSet( dateParser.parse( "15/Sep/2009 00:00:00" ).getTime(), 0.000,0.000,0.000, 24.0 ),
        new DataSet( dateParser.parse( "16/Sep/2009 00:00:00" ).getTime(), 250.350,0.120,936.930, 24.0 ),
        new DataSet( dateParser.parse( "17/Sep/2009 00:00:00" ).getTime(), 0.000,0.000,0.000, 24.0 ),
        new DataSet( dateParser.parse( "18/Sep/2009 00:00:00" ).getTime(), 0.000,0.000,0.000, 24.0 ),
        new DataSet( dateParser.parse( "19/Sep/2009 00:00:00" ).getTime(), 528.530,0.230,1692.980, 24.0 ),
        new DataSet( dateParser.parse( "20/Sep/2009 00:00:00" ).getTime(), 0.000,0.000,0.000, 24.0 ),
        new DataSet( dateParser.parse( "21/Sep/2009 00:00:00" ).getTime(), 4466.390,1.850,3958.210, 24.0 ),
        new DataSet( dateParser.parse( "22/Sep/2009 00:00:00" ).getTime(), 5729.890,2.260,5157.110, 24.0 ),
        new DataSet( dateParser.parse( "23/Sep/2009 00:00:00" ).getTime(), 5980.760,2.330,5160.120, 24.0 ),
        new DataSet( dateParser.parse( "24/Sep/2009 00:00:00" ).getTime(), 6289.390,2.160,5224.700, 24.0 ),
        new DataSet( dateParser.parse( "25/Sep/2009 00:00:00" ).getTime(), 5541.010,2.080,5158.150, 24.0 ),
        new DataSet( dateParser.parse( "26/Sep/2009 00:00:00" ).getTime(), 5178.190,2.040,5159.420, 24.0 ),
        new DataSet( dateParser.parse( "27/Sep/2009 00:00:00" ).getTime(), 5812.120,2.040,5107.220, 24.0 ),
        new DataSet( dateParser.parse( "28/Sep/2009 00:00:00" ).getTime(), 5911.900,2.060,5181.130, 24.0 ),
        new DataSet( dateParser.parse( "29/Sep/2009 00:00:00" ).getTime(), 5488.600,2.050,5111.660, 24.0 ),
        new DataSet( dateParser.parse( "30/Sep/2009 00:00:00" ).getTime(), 5609.280,2.050,5094.460, 24.0 ),
        new DataSet( dateParser.parse( "01/Oct/2009 00:00:00" ).getTime(), 5289.740,2.030,5215.900, 24.0 ),
        new DataSet( dateParser.parse( "02/Oct/2009 00:00:00" ).getTime(), 5347.720,1.910,5594.210, 24.0 ),
        new DataSet( dateParser.parse( "03/Oct/2009 00:00:00" ).getTime(), 5166.590,1.900,5570.270, 24.0 ),
        new DataSet( dateParser.parse( "04/Oct/2009 00:00:00" ).getTime(), 5016.190,1.890,5537.160, 24.0 ),
        new DataSet( dateParser.parse( "05/Oct/2009 00:00:00" ).getTime(), 5040.070,1.900,5531.670, 24.0 ),
        new DataSet( dateParser.parse( "06/Oct/2009 00:00:00" ).getTime(), 4839.870,1.830,5594.580, 24.0 ),
        new DataSet( dateParser.parse( "07/Oct/2009 00:00:00" ).getTime(), 4967.750,1.750,5550.450, 24.0 ),
        new DataSet( dateParser.parse( "08/Oct/2009 00:00:00" ).getTime(), 4870.380,1.510,5572.850, 24.0 ),
        new DataSet( dateParser.parse( "09/Oct/2009 00:00:00" ).getTime(), 4423.950,1.610,5673.880, 24.0 ),
        new DataSet( dateParser.parse( "10/Oct/2009 00:00:00" ).getTime(), 4601.240,1.630,5652.240, 24.0 ),
        new DataSet( dateParser.parse( "11/Oct/2009 00:00:00" ).getTime(), 4475.230,1.600,5622.130, 24.0 ),
        new DataSet( dateParser.parse( "12/Oct/2009 00:00:00" ).getTime(), 4624.100,1.590,5552.920, 24.0 ),
        new DataSet( dateParser.parse( "13/Oct/2009 00:00:00" ).getTime(), 4518.170,1.570,5573.950, 24.0 ),
        new DataSet( dateParser.parse( "14/Oct/2009 00:00:00" ).getTime(), 4591.560,1.710,5629.070, 24.0 ),
        new DataSet( dateParser.parse( "15/Oct/2009 00:00:00" ).getTime(), 4245.880,1.570,5645.200, 24.0 ),
        new DataSet( dateParser.parse( "16/Oct/2009 00:00:00" ).getTime(), 4332.210,1.570,5449.550, 24.0 ),
        new DataSet( dateParser.parse( "17/Oct/2009 00:00:00" ).getTime(), 4266.200,1.550,5472.330, 24.0 ),
        new DataSet( dateParser.parse( "18/Oct/2009 00:00:00" ).getTime(), 4228.990,1.550,5460.580, 24.0 ),
        new DataSet( dateParser.parse( "19/Oct/2009 00:00:00" ).getTime(), 4212.030,1.480,5525.490, 24.0 ),
        new DataSet( dateParser.parse( "20/Oct/2009 00:00:00" ).getTime(), 4101.100,1.450,5332.770, 24.0 ),
        new DataSet( dateParser.parse( "21/Oct/2009 00:00:00" ).getTime(), 4108.460,1.450,5328.140, 24.0 ),
        new DataSet( dateParser.parse( "22/Oct/2009 00:00:00" ).getTime(), 4080.160,1.410,5473.070, 24.0 ),
        new DataSet( dateParser.parse( "23/Oct/2009 00:00:00" ).getTime(), 3916.660,1.390,5435.090, 24.0 ),
        new DataSet( dateParser.parse( "24/Oct/2009 00:00:00" ).getTime(), 3818.540,1.380,5421.750, 24.0 ),
        new DataSet( dateParser.parse( "25/Oct/2009 00:00:00" ).getTime(), 3773.760,1.360,5313.200, 24.0 ),
        new DataSet( dateParser.parse( "26/Oct/2009 00:00:00" ).getTime(), 3588.160,1.220,5129.600, 24.0 ),
        new DataSet( dateParser.parse( "27/Oct/2009 00:00:00" ).getTime(), 3773.730,1.360,5271.880, 24.0 ),
        new DataSet( dateParser.parse( "28/Oct/2009 00:00:00" ).getTime(), 3559.290,1.370,5268.750, 24.0 ),
        new DataSet( dateParser.parse( "29/Oct/2009 00:00:00" ).getTime(), 3601.060,1.340,5266.240, 24.0 ),
        new DataSet( dateParser.parse( "30/Oct/2009 00:00:00" ).getTime(), 3476.520,1.290,5239.820, 24.0 ),
        new DataSet( dateParser.parse( "31/Oct/2009 00:00:00" ).getTime(), 3637.910,1.280,5138.240, 24.0 ),
        new DataSet( dateParser.parse( "01/Nov/2009 00:00:00" ).getTime(), 3458.230,1.260,5159.600, 24.0 ),
        new DataSet( dateParser.parse( "02/Nov/2009 00:00:00" ).getTime(), 3417.440,1.260,5168.450, 24.0 ),
        new DataSet( dateParser.parse( "03/Nov/2009 00:00:00" ).getTime(), 3446.800,1.090,5135.850, 24.0 ),
        new DataSet( dateParser.parse( "04/Nov/2009 00:00:00" ).getTime(), 3302.080,1.250,5087.380, 24.0 ),
        new DataSet( dateParser.parse( "05/Nov/2009 00:00:00" ).getTime(), 3395.440,1.200,5033.310, 24.0 ),
        new DataSet( dateParser.parse( "06/Nov/2009 00:00:00" ).getTime(), 3177.080,1.210,5033.140, 24.0 ),
        new DataSet( dateParser.parse( "07/Nov/2009 00:00:00" ).getTime(), 3182.680,1.190,5035.830, 24.0 ),
        new DataSet( dateParser.parse( "08/Nov/2009 00:00:00" ).getTime(), 3110.920,1.190,5066.610, 24.0 ),
        new DataSet( dateParser.parse( "09/Nov/2009 00:00:00" ).getTime(), 3122.840,1.180,5028.100, 24.0 ),
        new DataSet( dateParser.parse( "10/Nov/2009 00:00:00" ).getTime(), 3105.830,1.140,4886.730, 24.0 ),
        new DataSet( dateParser.parse( "11/Nov/2009 00:00:00" ).getTime(), 3026.270,1.140,4947.180, 24.0 ),
        new DataSet( dateParser.parse( "12/Nov/2009 00:00:00" ).getTime(), 2930.510,1.110,4874.180, 24.0 ),
        new DataSet( dateParser.parse( "13/Nov/2009 00:00:00" ).getTime(), 2906.390,1.090,4854.310, 24.0 ),
        new DataSet( dateParser.parse( "14/Nov/2009 00:00:00" ).getTime(), 2788.260,1.100,4864.280, 24.0 ),
        new DataSet( dateParser.parse( "15/Nov/2009 00:00:00" ).getTime(), 2807.000,1.100,4866.570, 24.0 ),
        new DataSet( dateParser.parse( "16/Nov/2009 00:00:00" ).getTime(), 2983.830,1.170,5099.860, 24.0 ),
        new DataSet( dateParser.parse( "17/Nov/2009 00:00:00" ).getTime(), 2964.580,1.210,5256.240, 24.0 ),
        new DataSet( dateParser.parse( "18/Nov/2009 00:00:00" ).getTime(), 3003.290,1.230,5311.940, 24.0 ),
        new DataSet( dateParser.parse( "19/Nov/2009 00:00:00" ).getTime(), 3055.960,1.220,5345.120, 24.0 ),
        new DataSet( dateParser.parse( "20/Nov/2009 00:00:00" ).getTime(), 3100.300,1.200,5304.250, 24.0 ),
        new DataSet( dateParser.parse( "21/Nov/2009 00:00:00" ).getTime(), 3102.090,1.210,5332.120, 24.0 ),
        new DataSet( dateParser.parse( "22/Nov/2009 00:00:00" ).getTime(), 2441.030,0.910,5723.680, 24.0 ),
        new DataSet( dateParser.parse( "23/Nov/2009 00:00:00" ).getTime(), 2310.150,0.900,5721.170, 24.0 ),
        new DataSet( dateParser.parse( "24/Nov/2009 00:00:00" ).getTime(), 2236.950,0.890,5643.950, 24.0 ),
        new DataSet( dateParser.parse( "25/Nov/2009 00:00:00" ).getTime(), 2367.340,0.900,5474.260, 24.0 )

};

static final private DataSet[] dummyRawData = {
        new DataSet( dateParser.parse( "13/06/2011 04:00" ).getTime(), 0000.0, 0.00, 0000.0, 01.0 ),
        new DataSet( dateParser.parse( "13/06/2011 05:00" ).getTime(), 0000.0, 0.00, 0115.0, 01.0 ),
        new DataSet( dateParser.parse( "13/06/2011 06:00" ).getTime(), 0000.0, 0.00, 0237.0, 01.0 ),
        new DataSet( dateParser.parse( "13/06/2011 07:00" ).getTime(), 1567.5, 0.00, 0769.5, 01.0 ),
        new DataSet( dateParser.parse( "13/06/2011 08:00" ).getTime(), 1342.1, 0.00, 2185.2, 01.0 ),
        new DataSet( dateParser.parse( "13/06/2011 09:00" ).getTime(), 1152.6, 0.08, 2497.6, 01.0 ),
        new DataSet( dateParser.parse( "13/06/2011 10:00" ).getTime(), 1133.6, 0.12, 2605.3, 01.0 ),
        new DataSet( dateParser.parse( "13/06/2011 11:00" ).getTime(), 1132.8, 0.15, 2655.3, 01.0 ),
        new DataSet( dateParser.parse( "13/06/2011 12:00" ).getTime(), 1127.6, 0.16, 2597.5, 01.0 ),
        new DataSet( dateParser.parse( "13/06/2011 13:00" ).getTime(), 1128.3, 0.16, 2583.1, 01.0 ),
        new DataSet( dateParser.parse( "13/06/2011 14:00" ).getTime(), 1129.5, 0.16, 2602.0, 01.0 ),
        new DataSet( dateParser.parse( "13/06/2011 15:00" ).getTime(), 1128.6, 0.16, 2702.0, 01.0 ),
        new DataSet( dateParser.parse( "13/06/2011 16:00" ).getTime(), 1153.5, 0.16, 2913.0, 01.0 ),
        new DataSet( dateParser.parse( "13/06/2011 17:00" ).getTime(), 1132.4, 0.16, 2942.0, 01.0 ),
        new DataSet( dateParser.parse( "13/06/2011 18:00" ).getTime(), 1129.1, 0.17, 2935.0, 01.0 ),
        new DataSet( dateParser.parse( "13/06/2011 19:00" ).getTime(), 1128.5, 0.16, 2927.0, 01.0 ),
        new DataSet( dateParser.parse( "13/06/2011 20:00" ).getTime(), 1131.2, 0.17, 2912.0, 01.0 ),
        new DataSet( dateParser.parse( "13/06/2011 21:00" ).getTime(), 1130.0, 0.16, 2903.0, 01.0 ),
        new DataSet( dateParser.parse( "13/06/2011 22:00" ).getTime(), 1131.5, 0.17, 2889.0, 01.0 ),
        new DataSet( dateParser.parse( "13/06/2011 23:00" ).getTime(), 1132.1, 0.16, 2883.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 00:00" ).getTime(), 1132.7, 0.15, 2880.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 01:00" ).getTime(), 1133.1, 0.14, 2892.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 02:00" ).getTime(), 1135.8, 0.14, 2903.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 03:00" ).getTime(), 1138.5, 0.15, 2876.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 04:00" ).getTime(), 1139.1, 0.14, 2576.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 05:00" ).getTime(), 1142.6, 0.15, 2427.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 06:00" ).getTime(), 1140.7, 0.14, 1258.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 07:00" ).getTime(), 1141.2, 0.12, 0763.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 08:00" ).getTime(), 1142.8, 0.08, 0203.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 09:00" ).getTime(), 1140.3, 0.03, 0000.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 10:00" ).getTime(), 0320.5, 0.00, 0000.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 11:00" ).getTime(), 0000.0, 0.00, 0000.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 12:00" ).getTime(), 0000.0, 0.00, 0000.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 13:00" ).getTime(), 0000.0, 0.00, 0000.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 14:00" ).getTime(), 0000.0, 0.00, 0000.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 15:00" ).getTime(), 0000.0, 0.00, 0000.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 16:00" ).getTime(), 0762.5, 0.00, 1953.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 17:00" ).getTime(), 1762.4, 0.02, 2864.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 18:00" ).getTime(), 1482.3, 0.02, 3258.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 19:00" ).getTime(), 1312.5, 0.03, 3682.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 20:00" ).getTime(), 1274.7, 0.04, 3429.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 21:00" ).getTime(), 1082.5, 0.06, 3106.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 22:00" ).getTime(), 0995.7, 0.08, 3195.0, 01.0 ),
        new DataSet( dateParser.parse( "14/06/2011 23:00" ).getTime(), 1127.5, 0.07, 3302.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 00:00" ).getTime(), 1138.5, 0.08, 3268.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 01:00" ).getTime(), 1137.6, 0.08, 3292.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 02:00" ).getTime(), 1139.6, 0.08, 3295.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 03:00" ).getTime(), 1140.6, 0.10, 3312.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 04:00" ).getTime(), 1137.8, 0.11, 3303.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 05:00" ).getTime(), 1137.6, 0.13, 3308.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 06:00" ).getTime(), 1138.2, 0.12, 3482.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 07:00" ).getTime(), 1139.1, 0.13, 3383.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 08:00" ).getTime(), 1142.6, 0.15, 3357.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 09:00" ).getTime(), 1140.7, 0.14, 3324.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 10:00" ).getTime(), 1135.7, 0.13, 3035.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 11:00" ).getTime(), 0970.5, 0.14, 3193.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 12:00" ).getTime(), 0790.7, 0.09, 3085.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 13:00" ).getTime(), 0608.2, 0.07, 2958.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 14:00" ).getTime(), 0432.5, 0.03, 2753.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 15:00" ).getTime(), 0492.2, 0.00, 2458.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 16:00" ).getTime(), 0395.7, 0.00, 2205.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 17:00" ).getTime(), 0403.1, 0.00, 2398.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 18:00" ).getTime(), 0397.5, 0.03, 2459.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 19:00" ).getTime(), 0399.9, 0.09, 2695.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 20:00" ).getTime(), 0401.5, 0.12, 2732.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 21:00" ).getTime(), 0400.0, 0.13, 2678.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 22:00" ).getTime(), 0408.2, 0.13, 2752.0, 01.0 ),
        new DataSet( dateParser.parse( "15/06/2011 23:00" ).getTime(), 0405.3, 0.13, 2732.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 00:00" ).getTime(), 0397.5, 0.14, 2698.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 01:00" ).getTime(), 0401.1, 0.16, 2795.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 02:00" ).getTime(), 0402.6, 0.15, 2742.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 03:00" ).getTime(), 0490.0, 0.12, 2805.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 04:00" ).getTime(), 0580.0, 0.13, 2958.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 05:00" ).getTime(), 0670.0, 0.14, 3105.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 06:00" ).getTime(), 0760.0, 0.13, 3268.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 07:00" ).getTime(), 0850.0, 0.15, 3729.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 08:00" ).getTime(), 0940.0, 0.17, 4015.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 09:00" ).getTime(), 1030.0, 0.19, 3975.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 10:00" ).getTime(), 1120.0, 0.20, 3953.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 11:00" ).getTime(), 1200.0, 0.20, 4035.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 12:00" ).getTime(), 1200.0, 0.20, 4209.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 13:00" ).getTime(), 1203.8, 0.20, 4185.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 14:00" ).getTime(), 1197.6, 0.20, 4167.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 15:00" ).getTime(), 1199.9, 0.20, 4208.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 16:00" ).getTime(), 1202.0, 0.20, 4312.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 17:00" ).getTime(), 1205.5, 0.20, 4268.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 18:00" ).getTime(), 1203.2, 0.20, 4358.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 19:00" ).getTime(), 1202.0, 0.20, 4405.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 20:00" ).getTime(), 1201.0, 0.20, 4397.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 21:00" ).getTime(), 1200.0, 0.20, 4390.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 22:00" ).getTime(), 1203.0, 0.20, 4385.0, 01.0 ),
        new DataSet( dateParser.parse( "16/06/2011 23:00" ).getTime(), 1205.0, 0.20, 4342.0, 01.0 ),
        new DataSet( dateParser.parse( "17/06/2011 00:00" ).getTime(), 1207.0, 0.21, 4325.0, 01.0 ),
        new DataSet( dateParser.parse( "17/06/2011 01:00" ).getTime(), 1209.0, 0.20, 4326.0, 01.0 ),
        new DataSet( dateParser.parse( "17/06/2011 02:00" ).getTime(), 1211.0, 0.20, 4320.0, 01.0 ),
        new DataSet( dateParser.parse( "17/06/2011 03:00" ).getTime(), 1210.0, 0.19, 4317.0, 01.0 ),
        new DataSet( dateParser.parse( "17/06/2011 04:00" ).getTime(), 1210.0, 0.18, 4318.0, 01.0 ),
        new DataSet( dateParser.parse( "17/06/2011 05:00" ).getTime(), 1210.0, 0.19, 4319.0, 01.0 ),
        new DataSet( dateParser.parse( "17/06/2011 06:00" ).getTime(), 1210.0, 0.20, 4320.0, 01.0 ),
        new DataSet( dateParser.parse( "17/06/2011 07:00" ).getTime(), 1210.1, 0.21, 4325.0, 01.0 )
};

static final private DataSet[] smallRawData = {
        new DataSet( dateParser.parse( "23/APR/2011 05:00" ).getTime(), 0037.6, 0.89, 0015.2, 01.0 ),
        new DataSet( dateParser.parse( "23/APR/2011 06:00" ).getTime(), 0037.8, 0.86, 0015.1, 01.0 ),
        new DataSet( dateParser.parse( "23/APR/2011 07:00" ).getTime(), 0037.9, 0.87, 0015.3, 01.0 ),
        new DataSet( dateParser.parse( "23/APR/2011 08:00" ).getTime(), 0037.6, 0.86, 0015.4, 01.0 ),
        new DataSet( dateParser.parse( "23/APR/2011 09:00" ).getTime(), 0037.7, 0.85, 0015.6, 01.0 ),
        new DataSet( dateParser.parse( "23/APR/2011 10:00" ).getTime(), 0037.5, 0.86, 0015.7, 01.0 ),
        new DataSet( dateParser.parse( "23/APR/2011 11:00" ).getTime(), 0037.3, 0.84, 0015.8, 01.0 )
};

static final private DataSet[] smallRawReducedData = {
        new DataSet( dateParser.parse( "23/APR/2011 05:00" ).getTime(), 0037.725, 0.8700, 15.25, 4.0 ),
        new DataSet( dateParser.parse( "23/APR/2011 09:00" ).getTime(), 0037.500, 0.8500, 15.70, 3.0 )
};


static {
    // populate some dummy data into the gas well data set....
    // --------------------------------------------------------
    nicksDataSet = populateFromRawData( saa11Well, nicksRawData );

    // populate the SAA2 Fragment data set...
    // --------------------------------------
    saa2FragmentDataSet = populateFromRawData( saa2Well, saa2FragmentRawData );
    
    // populate the dummy data set...
    // -----------------------------------
    dummyDataSet = populateFromRawData( dummyWell,  dummyRawData );

    // populate small set of test data, used within the GasWellDataSetTest class.
    // ---------------------------------------------------------------------------
    smallDataSet = populateFromRawData( new GasWell( "Dave's Well" ), smallRawData );

    // reduced version of the small data set ... with just two intervals..
    // ---------------------------------------------------------------------------
    smallReducedDataSet = populateFromRawData( new GasWell( "Dave's Well" ), smallRawReducedData );
}

/**
 * 
 * @return the {@link GasWellDataSet} containing Nick's reduced data flow set.
 */
public static GasWellDataSet getNicksDataSet()
{
    return nicksDataSet;
}


/**
 *
 * @return the {@link GasWellDataSet} containing flow rate data on a daily basis for
 * SAA-2 well from 27th July 2009 until 25th November 2009
 */
public static GasWellDataSet getSAA2FragmentDataSet()
{
    return saa2FragmentDataSet;
}


/**
 * 
 * @return set of dummy made up data from 13th June 2011 04:00 to 17th June 2011 14:00
 */
public static GasWellDataSet getDummyDataSet()
{
    return dummyDataSet;
}

/**
 *
 * @return small set of dummy data, with hourly intervals from 23/APR/2011 05:00 through to 23/APR/2011 11:59:59
 */
public static GasWellDataSet getSmallDataSet()
{
    return smallDataSet;
}

/**
 *
 * @return reduced version of the small data set with just two intervals.
 */
public static GasWellDataSet getSmallReducedDataSet()
{
    return smallReducedDataSet;
}

protected static GasWellDataSet populateFromRawData( GasWell well, DataSet[] rawData )
{
    GasWellDataSet result;
    GasWellDataEntry entry;
    Date from;
    Date until;

    
    result = new GasWellDataSet( well );


    for( int i = 0; i < rawData.length; i++ )
    {
        entry = new GasWellDataEntry();
        entry.setWell( result.getWell() );

        from = rawData[ i ].from;
        if ( i < rawData.length - 1 )
        {
            until = rawData[ i + 1 ].from;
            double span = ( until.getTime() - from.getTime() ) / 1000.0;
            double delta = Math.abs( span - ( rawData[ i ].durationHours * 3600.0 ) );
            if ( delta > 0.5 )
            {
                if ( span > ( rawData[ i ].durationHours * 3600 ) )
                {
                    logger.error( "well:" + well.getName() + " ... i=" + i + " ... duration is short by " + delta + "seconds. from=" + from + ", until=" + until  );
                }
                if ( span < ( rawData[ i ].durationHours * 3600 ) )
                {
                    logger.error( "well:" + well.getName() + " ... i=" + i + " ... duration is too long by " + delta + "seconds. from=" + from + ", until=" + until );
                }
            }
            until = new Date( from.getTime() + ( (long)span * 1000L ) );
        } else {
            until = new Date( from.getTime () + (long)(rawData[ i ].durationHours * 3600000L ) ) ;
        }
        entry.setDateRange( new DateRange( from, until, 1000L ) );
        entry.setMeasurement( WellMeasurementType.OIL_FLOW, rawData[ i ].oilFlowRate );
        entry.setMeasurement( WellMeasurementType.GAS_FLOW, rawData[ i ].gasFlowRate );
        entry.setMeasurement( WellMeasurementType.WATER_FLOW, rawData[ i ].waterFlowRate );
        result.addDataEntry( entry );
    }
    return result;
}


protected final static class DataSet
{
    Date from;
    double oilFlowRate;
    double gasFlowRate;
    double waterFlowRate;
    double durationHours;

    protected DataSet( Date from, double oilFlowRate, double gasFlowRate, double waterFlowRate, double durationHours )
    {
        this.from = from;
        this.oilFlowRate = oilFlowRate;
        this.gasFlowRate = gasFlowRate;
        this.waterFlowRate = waterFlowRate;
        this.durationHours = durationHours;
    }
}
}
