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

import java.util.Date;

/**
 * Set of test data for use between DDR application tests.
 * 
 */
public class TestGasWellDataSet
{
static Logger logger = Logger.getLogger( TestGasWellDataSet.class );
static DateParser parser = new AussieDateParser();
static protected GasWellDataSet nicksDataSet = null;
static protected GasWellDataSet saa2FragmentDataSet = null;
static protected GasWellDataSet dummyDataSet = null;
static protected GasWellDataSet smallDataSet = null;
static protected GasWellDataSet smallReducedDataSet = null;
static protected GasWellDataSet by11DataSet = null;
static protected GasWell saa2Well = new GasWell( "SAA-2" );
static protected GasWell saa11Well = new GasWell( "SAA-11" );
static protected GasWell dummyWell = new GasWell( "Dummy" );

static final private DataSet[] nicksRawData = {
        new OilDataSet( parser.parse( "16/JUL/2006 20:47" ).getTime(), 0000.0, 0000.0, 0000.0, 0277.7000 ),
        new OilDataSet( parser.parse( "28/JUL/2006 10:29" ).getTime(), 1788.0, 1370.0, 0000.0, 0261.9333 ),
        new OilDataSet( parser.parse( "08/AUG/2006 08:25" ).getTime(), 1070.0, 0970.0, 0000.0, 0121.0666 ),
        new OilDataSet( parser.parse( "13/AUG/2006 09:29" ).getTime(), 0000.0, 0000.0, 0000.0, 0029.8333 ),
        new OilDataSet( parser.parse( "14/AUG/2006 15:19" ).getTime(), 1167.0, 1320.0, 0000.0, 0861.7833 ),
        new OilDataSet( parser.parse( "19/SEP/2006 13:06" ).getTime(), 0692.0, 0800.0, 0000.0, 0745.0167 ),
        new OilDataSet( parser.parse( "20/OKT/2006 14:07" ).getTime(), 0827.0, 1340.0, 0000.0, 4949.9167 ),
        new OilDataSet( parser.parse( "14/MAI/2007 20:02" ).getTime(), 0538.0, 1040.0, 0000.0, 0765.8667 ),
        new OilDataSet( parser.parse( "15/JUN/2007 17:54" ).getTime(), 0700.0, 1100.0, 0000.0, 0057.3667 ),
        new OilDataSet( parser.parse( "18/JUN/2007 03:16" ).getTime(), 0841.0, 1170.0, 0000.0, 0098.8333 ),
        new OilDataSet( parser.parse( "22/JUN/2007 06:06" ).getTime(), 0000.0, 0000.0, 0000.0, 0158.8500 ),
        new OilDataSet( parser.parse( "28/JUN/2007 20:57" ).getTime(), 0001.0, 0001.0, 0000.0, 0080.0000 ),
        new OilDataSet( parser.parse( "02/JUL/2007 04:57" ).getTime(), 0612.0, 0870.0, 0017.0, 5136.0000 ),
        new OilDataSet( parser.parse( "01/FEB/2008 04:57" ).getTime(), 0701.0, 1010.0, 0012.0, 3624.0000 ),
        new OilDataSet( parser.parse( "01/JUL/2008 04:57" ).getTime(), 0783.0, 1320.0, 0006.0, 0225.6500 ),
        new OilDataSet( parser.parse( "10/JUL/2008 14:36" ).getTime(), 0923.0, 1550.0, 0012.0, 0514.0167 ),
        new OilDataSet( parser.parse( "01/AUG/2008 00:37" ).getTime(), 0000.0, 0000.0, 0000.0, 0119.0833 ),
        new OilDataSet( parser.parse( "05/AUG/2008 23:42" ).getTime(), 0923.0, 0001.0, 0001.0, 0170.0000 ),
        new OilDataSet( parser.parse( "13/AUG/2008 01:42" ).getTime(), 0702.0, 0980.0, 0016.0, 3784.3333 ),
        new OilDataSet( parser.parse( "17/JAN/2009 18:02" ).getTime(), 0000.0, 0000.0, 0000.0, 0111.5667 ),
        new OilDataSet( parser.parse( "22/JAN/2009 09:36" ).getTime(), 0545.0, 0820.0, 0010.0, 1182.7333 ),
        new OilDataSet( parser.parse( "12/MAR/2009 16:20" ).getTime(), 0000.0, 0000.0, 0000.0, 0077.3333 ),
        new OilDataSet( parser.parse( "15/MAR/2009 21:40" ).getTime(), 0001.0, 0001.0, 0001.0, 0068.1833 ),
        new OilDataSet( parser.parse( "18/MAR/2009 17:51" ).getTime(), 0564.0, 0890.0, 0012.0, 3513.2000 ),
        new OilDataSet( parser.parse( "12/AUG/2009 03:03" ).getTime(), 0000.0, 0000.0, 0000.0, 0092.5000 ),
        new OilDataSet( parser.parse( "15/AUG/2009 23:33" ).getTime(), 0564.0, 0810.0, 0007.0, 6840.0000 ),
        new OilDataSet( parser.parse( "27/MAI/2010 23:33" ).getTime(), 0000.0, 0000.0, 0000.0, 0135.8500 ),
        new OilDataSet( parser.parse( "02/JUN/2010 15:24" ).getTime(), 0513.0, 0620.0, 0006.0, 2561.5500 ),
        new OilDataSet( parser.parse( "17/SEP/2010 08:57" ).getTime(), 0636.0, 0970.0, 0008.5, 0096.0000 ),
        new OilDataSet( parser.parse( "21/SEP/2010 08:57" ).getTime(), 0497.0, 0600.0, 0009.0, 0135.4667 ),
        new OilDataSet( parser.parse( "27/SEP/2010 00:25" ).getTime(), 0000.0, 0000.0, 0000.0, 8760.770 )
};

static final private DataSet[] saa2FragmentRawData = {
        new OilDataSet( parser.parse( "30/Jul/2009 00:00:00" ).getTime(), 286.510,0.190,951.640, 24.0 ),
        new OilDataSet( parser.parse( "31/Jul/2009 00:00:00" ).getTime(), 276.880,0.190,923.830, 24.0 ),
        new OilDataSet( parser.parse( "01/Aug/2009 00:00:00" ).getTime(), 284.430,0.190,926.970, 24.0 ),
        new OilDataSet( parser.parse( "02/Aug/2009 00:00:00" ).getTime(), 290.260,0.190,935.960, 24.0 ),
        new OilDataSet( parser.parse( "03/Aug/2009 00:00:00" ).getTime(), 248.420,0.170,783.260, 24.0 ),
        new OilDataSet( parser.parse( "04/Aug/2009 00:00:00" ).getTime(), 249.490,0.180,840.450, 24.0 ),
        new OilDataSet( parser.parse( "05/Aug/2009 00:00:00" ).getTime(), 223.240,0.150,763.600, 24.0 ),
        new OilDataSet( parser.parse( "06/Aug/2009 00:00:00" ).getTime(), 223.670,0.150,753.610, 24.0 ),
        new OilDataSet( parser.parse( "07/Aug/2009 00:00:00" ).getTime(), 176.540,0.160,741.400, 24.0 ),
        new OilDataSet( parser.parse( "08/Aug/2009 00:00:00" ).getTime(), 216.920,0.160,743.670, 24.0 ),
        new OilDataSet( parser.parse( "09/Aug/2009 00:00:00" ).getTime(), 203.180,0.150,698.740, 24.0 ),
        new OilDataSet( parser.parse( "10/Aug/2009 00:00:00" ).getTime(), 200.820,0.160,691.610, 24.0 ),
        new OilDataSet( parser.parse( "11/Aug/2009 00:00:00" ).getTime(), 219.480,0.150,633.090, 24.0 ),
        new OilDataSet( parser.parse( "12/Aug/2009 00:00:00" ).getTime(), 0.000,0.000,0.000, 24.0 ),
        new OilDataSet( parser.parse( "13/Aug/2009 00:00:00" ).getTime(), 0.000,0.000,0.000, 24.0 ),
        new OilDataSet( parser.parse( "14/Aug/2009 00:00:00" ).getTime(), 0.000,0.000,0.000, 24.0 ),
        new OilDataSet( parser.parse( "15/Aug/2009 00:00:00" ).getTime(), 40.660,0.000,150.730, 24.0 ),
        new OilDataSet( parser.parse( "16/Aug/2009 00:00:00" ).getTime(), 192.040,0.150,673.250, 24.0 ),
        new OilDataSet( parser.parse( "17/Aug/2009 00:00:00" ).getTime(), 181.730,0.140,632.840, 24.0 ),
        new OilDataSet( parser.parse( "18/Aug/2009 00:00:00" ).getTime(), 38.000,0.030,132.740, 24.0 ),
        new OilDataSet( parser.parse( "19/Aug/2009 00:00:00" ).getTime(), 0.000,0.000,0.000, 24.0 ),
        new OilDataSet( parser.parse( "20/Aug/2009 00:00:00" ).getTime(), 11.580,0.010,40.620, 24.0 ),
        new OilDataSet( parser.parse( "21/Aug/2009 00:00:00" ).getTime(), 0.000,0.000,0.000, 24.0 ),
        new OilDataSet( parser.parse( "22/Aug/2009 00:00:00" ).getTime(), 0.000,0.000,0.000, 24.0 ),
        new OilDataSet( parser.parse( "23/Aug/2009 00:00:00" ).getTime(), 105.000,0.090,383.840, 24.0 ),
        new OilDataSet( parser.parse( "24/Aug/2009 00:00:00" ).getTime(), 689.890,0.340,2087.480, 24.0 ),
        new OilDataSet( parser.parse( "25/Aug/2009 00:00:00" ).getTime(), 539.720,0.310,2042.970, 24.0 ),
        new OilDataSet( parser.parse( "26/Aug/2009 00:00:00" ).getTime(), 665.630,0.320,2017.700, 24.0 ),
        new OilDataSet( parser.parse( "27/Aug/2009 00:00:00" ).getTime(), 603.340,0.300,1877.530, 24.0 ),
        new OilDataSet( parser.parse( "28/Aug/2009 00:00:00" ).getTime(), 621.220,0.310,1843.600, 24.0 ),
        new OilDataSet( parser.parse( "29/Aug/2009 00:00:00" ).getTime(), 475.470,0.260,1636.590, 24.0 ),
        new OilDataSet( parser.parse( "30/Aug/2009 00:00:00" ).getTime(), 400.530,0.230,1438.690, 24.0 ),
        new OilDataSet( parser.parse( "31/Aug/2009 00:00:00" ).getTime(), 413.890,0.230,1429.430, 24.0 ),
        new OilDataSet( parser.parse( "01/Sep/2009 00:00:00" ).getTime(), 367.380,0.210,1256.190, 24.0 ),
        new OilDataSet( parser.parse( "02/Sep/2009 00:00:00" ).getTime(), 362.690,0.210,1241.720, 24.0 ),
        new OilDataSet( parser.parse( "03/Sep/2009 00:00:00" ).getTime(), 320.700,0.210,1089.110, 24.0 ),
        new OilDataSet( parser.parse( "04/Sep/2009 00:00:00" ).getTime(), 300.720,0.200,992.560, 24.0 ),
        new OilDataSet( parser.parse( "05/Sep/2009 00:00:00" ).getTime(), 529.740,0.290,1982.060, 24.0 ),
        new OilDataSet( parser.parse( "06/Sep/2009 00:00:00" ).getTime(), 543.560,0.300,1962.030, 24.0 ),
        new OilDataSet( parser.parse( "07/Sep/2009 00:00:00" ).getTime(), 590.910,0.300,1872.500, 24.0 ),
        new OilDataSet( parser.parse( "08/Sep/2009 00:00:00" ).getTime(), 583.420,0.300,1858.520, 24.0 ),
        new OilDataSet( parser.parse( "09/Sep/2009 00:00:00" ).getTime(), 559.420,0.300,1873.810, 24.0 ),
        new OilDataSet( parser.parse( "10/Sep/2009 00:00:00" ).getTime(), 483.930,0.260,1626.890, 24.0 ),
        new OilDataSet( parser.parse( "11/Sep/2009 00:00:00" ).getTime(), 52.800,0.030,194.090, 24.0 ),
        new OilDataSet( parser.parse( "12/Sep/2009 00:00:00" ).getTime(), 104.870,0.060,343.010, 24.0 ),
        new OilDataSet( parser.parse( "13/Sep/2009 00:00:00" ).getTime(), 80.300,0.060,1400.310, 24.0 ),
        new OilDataSet( parser.parse( "14/Sep/2009 00:00:00" ).getTime(), 8.500,0.010,153.480, 24.0 ),
        new OilDataSet( parser.parse( "15/Sep/2009 00:00:00" ).getTime(), 0.000,0.000,0.000, 24.0 ),
        new OilDataSet( parser.parse( "16/Sep/2009 00:00:00" ).getTime(), 250.350,0.120,936.930, 24.0 ),
        new OilDataSet( parser.parse( "17/Sep/2009 00:00:00" ).getTime(), 0.000,0.000,0.000, 24.0 ),
        new OilDataSet( parser.parse( "18/Sep/2009 00:00:00" ).getTime(), 0.000,0.000,0.000, 24.0 ),
        new OilDataSet( parser.parse( "19/Sep/2009 00:00:00" ).getTime(), 528.530,0.230,1692.980, 24.0 ),
        new OilDataSet( parser.parse( "20/Sep/2009 00:00:00" ).getTime(), 0.000,0.000,0.000, 24.0 ),
        new OilDataSet( parser.parse( "21/Sep/2009 00:00:00" ).getTime(), 4466.390,1.850,3958.210, 24.0 ),
        new OilDataSet( parser.parse( "22/Sep/2009 00:00:00" ).getTime(), 5729.890,2.260,5157.110, 24.0 ),
        new OilDataSet( parser.parse( "23/Sep/2009 00:00:00" ).getTime(), 5980.760,2.330,5160.120, 24.0 ),
        new OilDataSet( parser.parse( "24/Sep/2009 00:00:00" ).getTime(), 6289.390,2.160,5224.700, 24.0 ),
        new OilDataSet( parser.parse( "25/Sep/2009 00:00:00" ).getTime(), 5541.010,2.080,5158.150, 24.0 ),
        new OilDataSet( parser.parse( "26/Sep/2009 00:00:00" ).getTime(), 5178.190,2.040,5159.420, 24.0 ),
        new OilDataSet( parser.parse( "27/Sep/2009 00:00:00" ).getTime(), 5812.120,2.040,5107.220, 24.0 ),
        new OilDataSet( parser.parse( "28/Sep/2009 00:00:00" ).getTime(), 5911.900,2.060,5181.130, 24.0 ),
        new OilDataSet( parser.parse( "29/Sep/2009 00:00:00" ).getTime(), 5488.600,2.050,5111.660, 24.0 ),
        new OilDataSet( parser.parse( "30/Sep/2009 00:00:00" ).getTime(), 5609.280,2.050,5094.460, 24.0 ),
        new OilDataSet( parser.parse( "01/Oct/2009 00:00:00" ).getTime(), 5289.740,2.030,5215.900, 24.0 ),
        new OilDataSet( parser.parse( "02/Oct/2009 00:00:00" ).getTime(), 5347.720,1.910,5594.210, 24.0 ),
        new OilDataSet( parser.parse( "03/Oct/2009 00:00:00" ).getTime(), 5166.590,1.900,5570.270, 24.0 ),
        new OilDataSet( parser.parse( "04/Oct/2009 00:00:00" ).getTime(), 5016.190,1.890,5537.160, 24.0 ),
        new OilDataSet( parser.parse( "05/Oct/2009 00:00:00" ).getTime(), 5040.070,1.900,5531.670, 24.0 ),
        new OilDataSet( parser.parse( "06/Oct/2009 00:00:00" ).getTime(), 4839.870,1.830,5594.580, 24.0 ),
        new OilDataSet( parser.parse( "07/Oct/2009 00:00:00" ).getTime(), 4967.750,1.750,5550.450, 24.0 ),
        new OilDataSet( parser.parse( "08/Oct/2009 00:00:00" ).getTime(), 4870.380,1.510,5572.850, 24.0 ),
        new OilDataSet( parser.parse( "09/Oct/2009 00:00:00" ).getTime(), 4423.950,1.610,5673.880, 24.0 ),
        new OilDataSet( parser.parse( "10/Oct/2009 00:00:00" ).getTime(), 4601.240,1.630,5652.240, 24.0 ),
        new OilDataSet( parser.parse( "11/Oct/2009 00:00:00" ).getTime(), 4475.230,1.600,5622.130, 24.0 ),
        new OilDataSet( parser.parse( "12/Oct/2009 00:00:00" ).getTime(), 4624.100,1.590,5552.920, 24.0 ),
        new OilDataSet( parser.parse( "13/Oct/2009 00:00:00" ).getTime(), 4518.170,1.570,5573.950, 24.0 ),
        new OilDataSet( parser.parse( "14/Oct/2009 00:00:00" ).getTime(), 4591.560,1.710,5629.070, 24.0 ),
        new OilDataSet( parser.parse( "15/Oct/2009 00:00:00" ).getTime(), 4245.880,1.570,5645.200, 24.0 ),
        new OilDataSet( parser.parse( "16/Oct/2009 00:00:00" ).getTime(), 4332.210,1.570,5449.550, 24.0 ),
        new OilDataSet( parser.parse( "17/Oct/2009 00:00:00" ).getTime(), 4266.200,1.550,5472.330, 24.0 ),
        new OilDataSet( parser.parse( "18/Oct/2009 00:00:00" ).getTime(), 4228.990,1.550,5460.580, 24.0 ),
        new OilDataSet( parser.parse( "19/Oct/2009 00:00:00" ).getTime(), 4212.030,1.480,5525.490, 24.0 ),
        new OilDataSet( parser.parse( "20/Oct/2009 00:00:00" ).getTime(), 4101.100,1.450,5332.770, 24.0 ),
        new OilDataSet( parser.parse( "21/Oct/2009 00:00:00" ).getTime(), 4108.460,1.450,5328.140, 24.0 ),
        new OilDataSet( parser.parse( "22/Oct/2009 00:00:00" ).getTime(), 4080.160,1.410,5473.070, 24.0 ),
        new OilDataSet( parser.parse( "23/Oct/2009 00:00:00" ).getTime(), 3916.660,1.390,5435.090, 24.0 ),
        new OilDataSet( parser.parse( "24/Oct/2009 00:00:00" ).getTime(), 3818.540,1.380,5421.750, 24.0 ),
        new OilDataSet( parser.parse( "25/Oct/2009 00:00:00" ).getTime(), 3773.760,1.360,5313.200, 24.0 ),
        new OilDataSet( parser.parse( "26/Oct/2009 00:00:00" ).getTime(), 3588.160,1.220,5129.600, 24.0 ),
        new OilDataSet( parser.parse( "27/Oct/2009 00:00:00" ).getTime(), 3773.730,1.360,5271.880, 24.0 ),
        new OilDataSet( parser.parse( "28/Oct/2009 00:00:00" ).getTime(), 3559.290,1.370,5268.750, 24.0 ),
        new OilDataSet( parser.parse( "29/Oct/2009 00:00:00" ).getTime(), 3601.060,1.340,5266.240, 24.0 ),
        new OilDataSet( parser.parse( "30/Oct/2009 00:00:00" ).getTime(), 3476.520,1.290,5239.820, 24.0 ),
        new OilDataSet( parser.parse( "31/Oct/2009 00:00:00" ).getTime(), 3637.910,1.280,5138.240, 24.0 ),
        new OilDataSet( parser.parse( "01/Nov/2009 00:00:00" ).getTime(), 3458.230,1.260,5159.600, 24.0 ),
        new OilDataSet( parser.parse( "02/Nov/2009 00:00:00" ).getTime(), 3417.440,1.260,5168.450, 24.0 ),
        new OilDataSet( parser.parse( "03/Nov/2009 00:00:00" ).getTime(), 3446.800,1.090,5135.850, 24.0 ),
        new OilDataSet( parser.parse( "04/Nov/2009 00:00:00" ).getTime(), 3302.080,1.250,5087.380, 24.0 ),
        new OilDataSet( parser.parse( "05/Nov/2009 00:00:00" ).getTime(), 3395.440,1.200,5033.310, 24.0 ),
        new OilDataSet( parser.parse( "06/Nov/2009 00:00:00" ).getTime(), 3177.080,1.210,5033.140, 24.0 ),
        new OilDataSet( parser.parse( "07/Nov/2009 00:00:00" ).getTime(), 3182.680,1.190,5035.830, 24.0 ),
        new OilDataSet( parser.parse( "08/Nov/2009 00:00:00" ).getTime(), 3110.920,1.190,5066.610, 24.0 ),
        new OilDataSet( parser.parse( "09/Nov/2009 00:00:00" ).getTime(), 3122.840,1.180,5028.100, 24.0 ),
        new OilDataSet( parser.parse( "10/Nov/2009 00:00:00" ).getTime(), 3105.830,1.140,4886.730, 24.0 ),
        new OilDataSet( parser.parse( "11/Nov/2009 00:00:00" ).getTime(), 3026.270,1.140,4947.180, 24.0 ),
        new OilDataSet( parser.parse( "12/Nov/2009 00:00:00" ).getTime(), 2930.510,1.110,4874.180, 24.0 ),
        new OilDataSet( parser.parse( "13/Nov/2009 00:00:00" ).getTime(), 2906.390,1.090,4854.310, 24.0 ),
        new OilDataSet( parser.parse( "14/Nov/2009 00:00:00" ).getTime(), 2788.260,1.100,4864.280, 24.0 ),
        new OilDataSet( parser.parse( "15/Nov/2009 00:00:00" ).getTime(), 2807.000,1.100,4866.570, 24.0 ),
        new OilDataSet( parser.parse( "16/Nov/2009 00:00:00" ).getTime(), 2983.830,1.170,5099.860, 24.0 ),
        new OilDataSet( parser.parse( "17/Nov/2009 00:00:00" ).getTime(), 2964.580,1.210,5256.240, 24.0 ),
        new OilDataSet( parser.parse( "18/Nov/2009 00:00:00" ).getTime(), 3003.290,1.230,5311.940, 24.0 ),
        new OilDataSet( parser.parse( "19/Nov/2009 00:00:00" ).getTime(), 3055.960,1.220,5345.120, 24.0 ),
        new OilDataSet( parser.parse( "20/Nov/2009 00:00:00" ).getTime(), 3100.300,1.200,5304.250, 24.0 ),
        new OilDataSet( parser.parse( "21/Nov/2009 00:00:00" ).getTime(), 3102.090,1.210,5332.120, 24.0 ),
        new OilDataSet( parser.parse( "22/Nov/2009 00:00:00" ).getTime(), 2441.030,0.910,5723.680, 24.0 ),
        new OilDataSet( parser.parse( "23/Nov/2009 00:00:00" ).getTime(), 2310.150,0.900,5721.170, 24.0 ),
        new OilDataSet( parser.parse( "24/Nov/2009 00:00:00" ).getTime(), 2236.950,0.890,5643.950, 24.0 ),
        new OilDataSet( parser.parse( "25/Nov/2009 00:00:00" ).getTime(), 2367.340,0.900,5474.260, 24.0 )

};

static final private DataSet[] dummyRawData = {
        new OilDataSet( parser.parse( "13/06/2011 04:00" ).getTime(), 0000.0, 0.00, 0000.0, 01.0 ),
        new OilDataSet( parser.parse( "13/06/2011 05:00" ).getTime(), 0000.0, 0.00, 0115.0, 01.0 ),
        new OilDataSet( parser.parse( "13/06/2011 06:00" ).getTime(), 0000.0, 0.00, 0237.0, 01.0 ),
        new OilDataSet( parser.parse( "13/06/2011 07:00" ).getTime(), 1567.5, 0.00, 0769.5, 01.0 ),
        new OilDataSet( parser.parse( "13/06/2011 08:00" ).getTime(), 1342.1, 0.00, 2185.2, 01.0 ),
        new OilDataSet( parser.parse( "13/06/2011 09:00" ).getTime(), 1152.6, 0.08, 2497.6, 01.0 ),
        new OilDataSet( parser.parse( "13/06/2011 10:00" ).getTime(), 1133.6, 0.12, 2605.3, 01.0 ),
        new OilDataSet( parser.parse( "13/06/2011 11:00" ).getTime(), 1132.8, 0.15, 2655.3, 01.0 ),
        new OilDataSet( parser.parse( "13/06/2011 12:00" ).getTime(), 1127.6, 0.16, 2597.5, 01.0 ),
        new OilDataSet( parser.parse( "13/06/2011 13:00" ).getTime(), 1128.3, 0.16, 2583.1, 01.0 ),
        new OilDataSet( parser.parse( "13/06/2011 14:00" ).getTime(), 1129.5, 0.16, 2602.0, 01.0 ),
        new OilDataSet( parser.parse( "13/06/2011 15:00" ).getTime(), 1128.6, 0.16, 2702.0, 01.0 ),
        new OilDataSet( parser.parse( "13/06/2011 16:00" ).getTime(), 1153.5, 0.16, 2913.0, 01.0 ),
        new OilDataSet( parser.parse( "13/06/2011 17:00" ).getTime(), 1132.4, 0.16, 2942.0, 01.0 ),
        new OilDataSet( parser.parse( "13/06/2011 18:00" ).getTime(), 1129.1, 0.17, 2935.0, 01.0 ),
        new OilDataSet( parser.parse( "13/06/2011 19:00" ).getTime(), 1128.5, 0.16, 2927.0, 01.0 ),
        new OilDataSet( parser.parse( "13/06/2011 20:00" ).getTime(), 1131.2, 0.17, 2912.0, 01.0 ),
        new OilDataSet( parser.parse( "13/06/2011 21:00" ).getTime(), 1130.0, 0.16, 2903.0, 01.0 ),
        new OilDataSet( parser.parse( "13/06/2011 22:00" ).getTime(), 1131.5, 0.17, 2889.0, 01.0 ),
        new OilDataSet( parser.parse( "13/06/2011 23:00" ).getTime(), 1132.1, 0.16, 2883.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 00:00" ).getTime(), 1132.7, 0.15, 2880.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 01:00" ).getTime(), 1133.1, 0.14, 2892.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 02:00" ).getTime(), 1135.8, 0.14, 2903.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 03:00" ).getTime(), 1138.5, 0.15, 2876.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 04:00" ).getTime(), 1139.1, 0.14, 2576.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 05:00" ).getTime(), 1142.6, 0.15, 2427.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 06:00" ).getTime(), 1140.7, 0.14, 1258.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 07:00" ).getTime(), 1141.2, 0.12, 0763.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 08:00" ).getTime(), 1142.8, 0.08, 0203.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 09:00" ).getTime(), 1140.3, 0.03, 0000.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 10:00" ).getTime(), 0320.5, 0.00, 0000.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 11:00" ).getTime(), 0000.0, 0.00, 0000.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 12:00" ).getTime(), 0000.0, 0.00, 0000.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 13:00" ).getTime(), 0000.0, 0.00, 0000.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 14:00" ).getTime(), 0000.0, 0.00, 0000.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 15:00" ).getTime(), 0000.0, 0.00, 0000.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 16:00" ).getTime(), 0762.5, 0.00, 1953.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 17:00" ).getTime(), 1762.4, 0.02, 2864.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 18:00" ).getTime(), 1482.3, 0.02, 3258.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 19:00" ).getTime(), 1312.5, 0.03, 3682.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 20:00" ).getTime(), 1274.7, 0.04, 3429.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 21:00" ).getTime(), 1082.5, 0.06, 3106.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 22:00" ).getTime(), 0995.7, 0.08, 3195.0, 01.0 ),
        new OilDataSet( parser.parse( "14/06/2011 23:00" ).getTime(), 1127.5, 0.07, 3302.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 00:00" ).getTime(), 1138.5, 0.08, 3268.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 01:00" ).getTime(), 1137.6, 0.08, 3292.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 02:00" ).getTime(), 1139.6, 0.08, 3295.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 03:00" ).getTime(), 1140.6, 0.10, 3312.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 04:00" ).getTime(), 1137.8, 0.11, 3303.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 05:00" ).getTime(), 1137.6, 0.13, 3308.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 06:00" ).getTime(), 1138.2, 0.12, 3482.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 07:00" ).getTime(), 1139.1, 0.13, 3383.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 08:00" ).getTime(), 1142.6, 0.15, 3357.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 09:00" ).getTime(), 1140.7, 0.14, 3324.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 10:00" ).getTime(), 1135.7, 0.13, 3035.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 11:00" ).getTime(), 0970.5, 0.14, 3193.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 12:00" ).getTime(), 0790.7, 0.09, 3085.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 13:00" ).getTime(), 0608.2, 0.07, 2958.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 14:00" ).getTime(), 0432.5, 0.03, 2753.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 15:00" ).getTime(), 0492.2, 0.00, 2458.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 16:00" ).getTime(), 0395.7, 0.00, 2205.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 17:00" ).getTime(), 0403.1, 0.00, 2398.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 18:00" ).getTime(), 0397.5, 0.03, 2459.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 19:00" ).getTime(), 0399.9, 0.09, 2695.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 20:00" ).getTime(), 0401.5, 0.12, 2732.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 21:00" ).getTime(), 0400.0, 0.13, 2678.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 22:00" ).getTime(), 0408.2, 0.13, 2752.0, 01.0 ),
        new OilDataSet( parser.parse( "15/06/2011 23:00" ).getTime(), 0405.3, 0.13, 2732.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 00:00" ).getTime(), 0397.5, 0.14, 2698.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 01:00" ).getTime(), 0401.1, 0.16, 2795.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 02:00" ).getTime(), 0402.6, 0.15, 2742.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 03:00" ).getTime(), 0490.0, 0.12, 2805.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 04:00" ).getTime(), 0580.0, 0.13, 2958.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 05:00" ).getTime(), 0670.0, 0.14, 3105.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 06:00" ).getTime(), 0760.0, 0.13, 3268.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 07:00" ).getTime(), 0850.0, 0.15, 3729.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 08:00" ).getTime(), 0940.0, 0.17, 4015.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 09:00" ).getTime(), 1030.0, 0.19, 3975.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 10:00" ).getTime(), 1120.0, 0.20, 3953.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 11:00" ).getTime(), 1200.0, 0.20, 4035.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 12:00" ).getTime(), 1200.0, 0.20, 4209.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 13:00" ).getTime(), 1203.8, 0.20, 4185.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 14:00" ).getTime(), 1197.6, 0.20, 4167.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 15:00" ).getTime(), 1199.9, 0.20, 4208.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 16:00" ).getTime(), 1202.0, 0.20, 4312.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 17:00" ).getTime(), 1205.5, 0.20, 4268.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 18:00" ).getTime(), 1203.2, 0.20, 4358.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 19:00" ).getTime(), 1202.0, 0.20, 4405.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 20:00" ).getTime(), 1201.0, 0.20, 4397.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 21:00" ).getTime(), 1200.0, 0.20, 4390.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 22:00" ).getTime(), 1203.0, 0.20, 4385.0, 01.0 ),
        new OilDataSet( parser.parse( "16/06/2011 23:00" ).getTime(), 1205.0, 0.20, 4342.0, 01.0 ),
        new OilDataSet( parser.parse( "17/06/2011 00:00" ).getTime(), 1207.0, 0.21, 4325.0, 01.0 ),
        new OilDataSet( parser.parse( "17/06/2011 01:00" ).getTime(), 1209.0, 0.20, 4326.0, 01.0 ),
        new OilDataSet( parser.parse( "17/06/2011 02:00" ).getTime(), 1211.0, 0.20, 4320.0, 01.0 ),
        new OilDataSet( parser.parse( "17/06/2011 03:00" ).getTime(), 1210.0, 0.19, 4317.0, 01.0 ),
        new OilDataSet( parser.parse( "17/06/2011 04:00" ).getTime(), 1210.0, 0.18, 4318.0, 01.0 ),
        new OilDataSet( parser.parse( "17/06/2011 05:00" ).getTime(), 1210.0, 0.19, 4319.0, 01.0 ),
        new OilDataSet( parser.parse( "17/06/2011 06:00" ).getTime(), 1210.0, 0.20, 4320.0, 01.0 ),
        new OilDataSet( parser.parse( "17/06/2011 07:00" ).getTime(), 1210.1, 0.21, 4325.0, 01.0 )
};

static final private DataSet[] smallRawData = {
        new OilDataSet( parser.parse( "23/APR/2011 05:00" ).getTime(), 0037.6, 0.89, 0015.2, 01.0 ),
        new OilDataSet( parser.parse( "23/APR/2011 06:00" ).getTime(), 0037.8, 0.86, 0015.1, 01.0 ),
        new OilDataSet( parser.parse( "23/APR/2011 07:00" ).getTime(), 0037.9, 0.87, 0015.3, 01.0 ),
        new OilDataSet( parser.parse( "23/APR/2011 08:00" ).getTime(), 0037.6, 0.86, 0015.4, 01.0 ),
        new OilDataSet( parser.parse( "23/APR/2011 09:00" ).getTime(), 0037.7, 0.85, 0015.6, 01.0 ),
        new OilDataSet( parser.parse( "23/APR/2011 10:00" ).getTime(), 0037.5, 0.86, 0015.7, 01.0 ),
        new OilDataSet( parser.parse( "23/APR/2011 11:00" ).getTime(), 0037.3, 0.84, 0015.8, 01.0 )
};

static final private DataSet[] smallRawReducedData = {
        new OilDataSet( parser.parse( "23/APR/2011 05:00" ).getTime(), 0037.725, 0.8700, 15.25, 4.0 ),
        new OilDataSet( parser.parse( "23/APR/2011 09:00" ).getTime(), 0037.500, 0.8500, 15.70, 3.0 )
};

static final private DataSet[] by11RawData = {
        new CondensateDataSet( parser.parse( "27/OCT/2009" ).getTime(),  22.38,   7.28,  79.07, 24.0 ),
        new CondensateDataSet( parser.parse( "28/OCT/2009" ).getTime(),  22.39,   5.25,  81.48, 24.0 ),
        new CondensateDataSet( parser.parse( "29/OCT/2009" ).getTime(),  22.18,   7.02,  76.50, 24.0 ),
        new CondensateDataSet( parser.parse( "30/OCT/2009" ).getTime(),  22.09,   6.67,  79.19, 24.0 ),
        new CondensateDataSet( parser.parse( "31/OCT/2009" ).getTime(),  22.10,   6.98,  77.36, 24.0 ),
        new CondensateDataSet( parser.parse( "01/NOV/2009" ).getTime(),  22.18,   7.34,  78.60, 24.0 ),
        new CondensateDataSet( parser.parse( "02/NOV/2009" ).getTime(),  22.03,   7.05,  77.95, 24.0 ),
        new CondensateDataSet( parser.parse( "03/NOV/2009" ).getTime(),  22.15,   7.17,  75.86, 24.0 ),
        new CondensateDataSet( parser.parse( "04/NOV/2009" ).getTime(),  22.37,   6.93,  78.23, 24.0 ),
        new CondensateDataSet( parser.parse( "05/NOV/2009" ).getTime(),  21.70,   7.40,  76.12, 24.0 ),
        new CondensateDataSet( parser.parse( "06/NOV/2009" ).getTime(),  21.72,   6.63,  75.16, 24.0 ),
        new CondensateDataSet( parser.parse( "07/NOV/2009" ).getTime(),  21.67,   6.07,  75.89, 24.0 ),
        new CondensateDataSet( parser.parse( "08/NOV/2009" ).getTime(),  21.65,   7.05,  77.14, 24.0 ),
        new CondensateDataSet( parser.parse( "09/NOV/2009" ).getTime(),  21.67,   6.54,  77.97, 24.0 ),
        new CondensateDataSet( parser.parse( "10/NOV/2009" ).getTime(),  21.79,   6.11,  76.80, 24.0 ),
        new CondensateDataSet( parser.parse( "11/NOV/2009" ).getTime(),  21.84,   6.92,  76.66, 24.0 ),
        new CondensateDataSet( parser.parse( "12/NOV/2009" ).getTime(),  21.84,   4.31,  77.33, 24.0 ),
        new CondensateDataSet( parser.parse( "13/NOV/2009" ).getTime(),  21.65,   7.71,  77.77, 24.0 ),
        new CondensateDataSet( parser.parse( "14/NOV/2009" ).getTime(),  21.62,   6.31,  77.96, 24.0 ),
        new CondensateDataSet( parser.parse( "15/NOV/2009" ).getTime(),  21.60,   7.91,  79.57, 24.0 ),
        new CondensateDataSet( parser.parse( "16/NOV/2009" ).getTime(),  21.49,   7.25,  80.73, 24.0 ),
        new CondensateDataSet( parser.parse( "17/NOV/2009" ).getTime(),  21.89,   7.62,  78.01, 24.0 ),
        new CondensateDataSet( parser.parse( "18/NOV/2009" ).getTime(),  21.97,   7.82,  82.83, 24.0 ),
        new CondensateDataSet( parser.parse( "19/NOV/2009" ).getTime(),  21.89,   7.13,  79.29, 24.0 ),
        new CondensateDataSet( parser.parse( "20/NOV/2009" ).getTime(),  21.77,   7.47,  77.78, 24.0 ),
        new CondensateDataSet( parser.parse( "21/NOV/2009" ).getTime(),  21.64,   7.52,  74.93, 24.0 ),
        new CondensateDataSet( parser.parse( "22/NOV/2009" ).getTime(),  21.81,  11.15,  82.97, 24.0 ),
        new CondensateDataSet( parser.parse( "23/NOV/2009" ).getTime(),  21.78,  14.60,  81.46, 24.0 ),
        new CondensateDataSet( parser.parse( "24/NOV/2009" ).getTime(),  21.77,   7.46,  74.21, 24.0 ),
        new CondensateDataSet( parser.parse( "25/NOV/2009" ).getTime(),  21.76,   6.70,  73.71, 24.0 ),
        new CondensateDataSet( parser.parse( "26/NOV/2009" ).getTime(),  21.74,   7.80,  76.51, 24.0 ),
        new CondensateDataSet( parser.parse( "27/NOV/2009" ).getTime(),  21.74,   6.93,  80.47, 24.0 ),
        new CondensateDataSet( parser.parse( "28/NOV/2009" ).getTime(),  21.97,   9.83,  82.52, 24.0 ),
        new CondensateDataSet( parser.parse( "29/NOV/2009" ).getTime(),   0.20,   0.23,   0.76, 24.0 ),
        new CondensateDataSet( parser.parse( "30/NOV/2009" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "01/DEC/2009" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "02/DEC/2009" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "03/DEC/2009" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "04/DEC/2009" ).getTime(),   3.87,   2.27,  12.74, 24.0 ),
        new CondensateDataSet( parser.parse( "05/DEC/2009" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "06/DEC/2009" ).getTime(),  20.67,   6.78,  84.51, 24.0 ),
        new CondensateDataSet( parser.parse( "07/DEC/2009" ).getTime(),  13.54,   4.41,  51.53, 24.0 ),
        new CondensateDataSet( parser.parse( "08/DEC/2009" ).getTime(),  13.54,   4.41,  51.53, 24.0 ),
        new CondensateDataSet( parser.parse( "09/DEC/2009" ).getTime(),  22.92,   6.68,  78.02, 24.0 ),
        new CondensateDataSet( parser.parse( "10/DEC/2009" ).getTime(),  23.22,   8.78,  87.33, 24.0 ),
        new CondensateDataSet( parser.parse( "11/DEC/2009" ).getTime(),  23.54,   7.60,  87.54, 24.0 ),
        new CondensateDataSet( parser.parse( "12/DEC/2009" ).getTime(),  23.49,   6.82,  85.59, 24.0 ),
        new CondensateDataSet( parser.parse( "13/DEC/2009" ).getTime(),  23.16,   7.72,  87.44, 24.0 ),
        new CondensateDataSet( parser.parse( "14/DEC/2009" ).getTime(),  23.32,   7.52,  89.09, 24.0 ),
        new CondensateDataSet( parser.parse( "15/DEC/2009" ).getTime(),  23.56,   7.39,  80.51, 24.0 ),
        new CondensateDataSet( parser.parse( "16/DEC/2009" ).getTime(),  23.58,   7.32,  84.17, 24.0 ),
        new CondensateDataSet( parser.parse( "17/DEC/2009" ).getTime(),  24.40,   5.72,  91.19, 24.0 ),
        new CondensateDataSet( parser.parse( "18/DEC/2009" ).getTime(),  24.37,   7.03,  90.68, 24.0 ),
        new CondensateDataSet( parser.parse( "19/DEC/2009" ).getTime(),  21.00,   7.92,  85.07, 24.0 ),
        new CondensateDataSet( parser.parse( "20/DEC/2009" ).getTime(),  21.07,   6.82,  85.85, 24.0 ),
        new CondensateDataSet( parser.parse( "21/DEC/2009" ).getTime(),  21.07,   5.69,  84.44, 24.0 ),
        new CondensateDataSet( parser.parse( "22/DEC/2009" ).getTime(),  21.22,   5.69,  83.64, 24.0 ),
        new CondensateDataSet( parser.parse( "23/DEC/2009" ).getTime(),  21.27,   7.79,  81.21, 24.0 ),
        new CondensateDataSet( parser.parse( "24/DEC/2009" ).getTime(),  21.29,   8.28,  86.55, 24.0 ),
        new CondensateDataSet( parser.parse( "25/DEC/2009" ).getTime(),  21.26,   8.25,  82.49, 24.0 ),
        new CondensateDataSet( parser.parse( "26/DEC/2009" ).getTime(),  21.27,   8.80,  84.33, 24.0 ),
        new CondensateDataSet( parser.parse( "27/DEC/2009" ).getTime(),  21.34,   8.21,  77.11, 24.0 ),
        new CondensateDataSet( parser.parse( "28/DEC/2009" ).getTime(),  20.12,   8.86,  81.97, 24.0 ),
        new CondensateDataSet( parser.parse( "29/DEC/2009" ).getTime(),  20.11,   7.13,  82.08, 24.0 ),
        new CondensateDataSet( parser.parse( "30/DEC/2009" ).getTime(),  20.11,   8.88,  79.68, 24.0 ),
        new CondensateDataSet( parser.parse( "31/DEC/2009" ).getTime(),  20.07,   8.63,  77.13, 24.0 ),
        new CondensateDataSet( parser.parse( "01/JAN/2010" ).getTime(),  20.08,   8.05,  76.88, 24.0 ),
        new CondensateDataSet( parser.parse( "02/JAN/2010" ).getTime(),  20.18,   7.60,  67.06, 24.0 ),
        new CondensateDataSet( parser.parse( "03/JAN/2010" ).getTime(),  19.85,   8.04,  93.82, 24.0 ),
        new CondensateDataSet( parser.parse( "04/JAN/2010" ).getTime(),  20.40,  18.18,  76.35, 24.0 ),
        new CondensateDataSet( parser.parse( "05/JAN/2010" ).getTime(),   3.47,   2.74,  13.19, 24.0 ),
        new CondensateDataSet( parser.parse( "06/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "07/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "08/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "09/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "10/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "11/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "12/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "13/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "14/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "15/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "16/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "17/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "18/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "19/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "20/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "21/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "22/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "23/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "24/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "25/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "26/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "27/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "28/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "29/JAN/2010" ).getTime(),  19.32,   6.76,  78.64, 24.0 ),
        new CondensateDataSet( parser.parse( "30/JAN/2010" ).getTime(),  19.26,   7.43,  78.33, 24.0 ),
        new CondensateDataSet( parser.parse( "31/JAN/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "01/FEB/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "02/FEB/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "03/FEB/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "04/FEB/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "05/FEB/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "06/FEB/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "07/FEB/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "08/FEB/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "09/FEB/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "10/FEB/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "11/FEB/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "12/FEB/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "13/FEB/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "14/FEB/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "15/FEB/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "16/FEB/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "17/FEB/2010" ).getTime(),   9.88,   4.87,  37.88, 24.0 ),
        new CondensateDataSet( parser.parse( "18/FEB/2010" ).getTime(),  20.72,   7.94,  79.59, 24.0 ),
        new CondensateDataSet( parser.parse( "19/FEB/2010" ).getTime(),  20.21,   7.61,  74.86, 24.0 ),
        new CondensateDataSet( parser.parse( "20/FEB/2010" ).getTime(),  20.25,   5.46,  80.02, 24.0 ),
        new CondensateDataSet( parser.parse( "21/FEB/2010" ).getTime(),  20.14,   7.13,  76.69, 24.0 ),
        new CondensateDataSet( parser.parse( "22/FEB/2010" ).getTime(),  20.06,   7.02,  78.96, 24.0 ),
        new CondensateDataSet( parser.parse( "23/FEB/2010" ).getTime(),  20.09,   8.26,  74.02, 24.0 ),
        new CondensateDataSet( parser.parse( "24/FEB/2010" ).getTime(),  20.00,   7.73,  73.70, 24.0 ),
        new CondensateDataSet( parser.parse( "25/FEB/2010" ).getTime(),  20.02,   8.00,  77.19, 24.0 ),
        new CondensateDataSet( parser.parse( "26/FEB/2010" ).getTime(),  20.01,   8.88,  74.20, 24.0 ),
        new CondensateDataSet( parser.parse( "27/FEB/2010" ).getTime(),  27.65,   7.25, 110.12, 24.0 ),
        new CondensateDataSet( parser.parse( "28/FEB/2010" ).getTime(),  27.61,   7.39, 102.41, 24.0 ),
        new CondensateDataSet( parser.parse( "01/MAR/2010" ).getTime(),  27.52,   8.79, 100.25, 24.0 ),
        new CondensateDataSet( parser.parse( "02/MAR/2010" ).getTime(),  27.42,   7.48,  98.26, 24.0 ),
        new CondensateDataSet( parser.parse( "03/MAR/2010" ).getTime(),  27.38,   7.36,  97.65, 24.0 ),
        new CondensateDataSet( parser.parse( "04/MAR/2010" ).getTime(),  27.41,   8.12, 103.13, 24.0 ),
        new CondensateDataSet( parser.parse( "05/MAR/2010" ).getTime(),  27.43,   7.31, 103.86, 24.0 ),
        new CondensateDataSet( parser.parse( "06/MAR/2010" ).getTime(),  27.56,   8.25, 102.07, 24.0 ),
        new CondensateDataSet( parser.parse( "07/MAR/2010" ).getTime(),  27.63,   7.88, 101.82, 24.0 ),
        new CondensateDataSet( parser.parse( "08/MAR/2010" ).getTime(),  27.63,   7.43, 101.31, 24.0 ),
        new CondensateDataSet( parser.parse( "09/MAR/2010" ).getTime(),  27.55,   7.60, 100.49, 24.0 ),
        new CondensateDataSet( parser.parse( "10/MAR/2010" ).getTime(),  27.62,   7.45, 102.00, 24.0 ),
        new CondensateDataSet( parser.parse( "11/MAR/2010" ).getTime(),  27.53,   7.11, 102.43, 24.0 ),
        new CondensateDataSet( parser.parse( "12/MAR/2010" ).getTime(),  27.56,   7.57, 102.28, 24.0 ),
        new CondensateDataSet( parser.parse( "13/MAR/2010" ).getTime(),  27.51,   8.10,  99.51, 24.0 ),
        new CondensateDataSet( parser.parse( "14/MAR/2010" ).getTime(),  27.54,   9.00,  99.55, 24.0 ),
        new CondensateDataSet( parser.parse( "15/MAR/2010" ).getTime(),  27.50,   8.22,  93.25, 24.0 ),
        new CondensateDataSet( parser.parse( "16/MAR/2010" ).getTime(),  27.42,   9.28,  98.43, 24.0 ),
        new CondensateDataSet( parser.parse( "17/MAR/2010" ).getTime(),  36.67,   8.79, 105.73, 24.0 ),
        new CondensateDataSet( parser.parse( "18/MAR/2010" ).getTime(),  27.76,   7.96,  87.98, 24.0 ),
        new CondensateDataSet( parser.parse( "19/MAR/2010" ).getTime(),  27.71,  10.01,  88.12, 24.0 ),
        new CondensateDataSet( parser.parse( "20/MAR/2010" ).getTime(),  27.83,  10.07,  83.54, 24.0 ),
        new CondensateDataSet( parser.parse( "21/MAR/2010" ).getTime(),  27.85,   9.41,  85.51, 24.0 ),
        new CondensateDataSet( parser.parse( "22/MAR/2010" ).getTime(),  27.91,   9.27,  87.99, 24.0 ),
        new CondensateDataSet( parser.parse( "23/MAR/2010" ).getTime(),  27.44,   9.16,  84.44, 24.0 ),
        new CondensateDataSet( parser.parse( "24/MAR/2010" ).getTime(),  27.48,  11.03,  85.14, 24.0 ),
        new CondensateDataSet( parser.parse( "25/MAR/2010" ).getTime(),  27.48,  10.24,  91.54, 24.0 ),
        new CondensateDataSet( parser.parse( "26/MAR/2010" ).getTime(),  21.17,   8.00,  64.19, 24.0 ),
        new CondensateDataSet( parser.parse( "27/MAR/2010" ).getTime(),  18.71,   6.63,  58.61, 24.0 ),
        new CondensateDataSet( parser.parse( "28/MAR/2010" ).getTime(),  28.19,  11.61,  86.31, 24.0 ),
        new CondensateDataSet( parser.parse( "29/MAR/2010" ).getTime(),  28.06,   9.73,  93.42, 24.0 ),
        new CondensateDataSet( parser.parse( "30/MAR/2010" ).getTime(),  27.94,  10.70,  86.26, 24.0 ),
        new CondensateDataSet( parser.parse( "31/MAR/2010" ).getTime(),  27.78,   9.47,  88.71, 24.0 ),
        new CondensateDataSet( parser.parse( "01/APR/2010" ).getTime(),  27.65,   9.88,  80.22, 24.0 ),
        new CondensateDataSet( parser.parse( "02/APR/2010" ).getTime(),  27.62,   9.35,  88.69, 24.0 ),
        new CondensateDataSet( parser.parse( "03/APR/2010" ).getTime(),  27.59,   8.26,  87.29, 24.0 ),
        new CondensateDataSet( parser.parse( "04/APR/2010" ).getTime(),  27.33,   7.80,  93.39, 24.0 ),
        new CondensateDataSet( parser.parse( "05/APR/2010" ).getTime(),  26.79,   7.20,  87.05, 24.0 ),
        new CondensateDataSet( parser.parse( "06/APR/2010" ).getTime(),  27.28,   7.27,  88.46, 24.0 ),
        new CondensateDataSet( parser.parse( "07/APR/2010" ).getTime(),  27.35,   7.94,  92.77, 24.0 ),
        new CondensateDataSet( parser.parse( "08/APR/2010" ).getTime(),  27.33,   8.12,  88.92, 24.0 ),
        new CondensateDataSet( parser.parse( "09/APR/2010" ).getTime(),  26.99,   7.99,  39.23, 24.0 ),
        new CondensateDataSet( parser.parse( "10/APR/2010" ).getTime(),  27.05,   8.63,  86.07, 24.0 ),
        new CondensateDataSet( parser.parse( "11/APR/2010" ).getTime(),  27.09,   7.25,  87.16, 24.0 ),
        new CondensateDataSet( parser.parse( "12/APR/2010" ).getTime(),  27.07,   7.39,  86.89, 24.0 ),
        new CondensateDataSet( parser.parse( "13/APR/2010" ).getTime(),  26.98,   8.51,  81.35, 24.0 ),
        new CondensateDataSet( parser.parse( "14/APR/2010" ).getTime(),  27.02,   7.26,  88.86, 24.0 ),
        new CondensateDataSet( parser.parse( "15/APR/2010" ).getTime(),  27.79,   7.04,  93.92, 24.0 ),
        new CondensateDataSet( parser.parse( "16/APR/2010" ).getTime(),  27.78,   9.73,  90.21, 24.0 ),
        new CondensateDataSet( parser.parse( "17/APR/2010" ).getTime(),  27.42,   7.74,  85.84, 24.0 ),
        new CondensateDataSet( parser.parse( "18/APR/2010" ).getTime(),  28.17,  15.51,  83.65, 24.0 ),
        new CondensateDataSet( parser.parse( "19/APR/2010" ).getTime(),  28.51,  14.69,  81.71, 24.0 ),
        new CondensateDataSet( parser.parse( "20/APR/2010" ).getTime(),  27.83,  15.92,  81.22, 24.0 ),
        new CondensateDataSet( parser.parse( "21/APR/2010" ).getTime(),  28.31,  15.62,  84.06, 24.0 ),
        new CondensateDataSet( parser.parse( "22/APR/2010" ).getTime(),  28.78,   9.17,  95.79, 24.0 ),
        new CondensateDataSet( parser.parse( "23/APR/2010" ).getTime(),  28.10,   7.79,  98.09, 24.0 ),
        new CondensateDataSet( parser.parse( "24/APR/2010" ).getTime(),  28.73,  10.68,  93.87, 24.0 ),
        new CondensateDataSet( parser.parse( "25/APR/2010" ).getTime(),  29.36,   8.42, 102.38, 24.0 ),
        new CondensateDataSet( parser.parse( "26/APR/2010" ).getTime(),  26.76,   7.22,  88.97, 24.0 ),
        new CondensateDataSet( parser.parse( "27/APR/2010" ).getTime(),  23.71,   7.76,  90.06, 24.0 ),
        new CondensateDataSet( parser.parse( "28/APR/2010" ).getTime(),  27.86,   8.12,  89.92, 24.0 ),
        new CondensateDataSet( parser.parse( "29/APR/2010" ).getTime(),  27.59,   7.50,  88.66, 24.0 ),
        new CondensateDataSet( parser.parse( "30/APR/2010" ).getTime(),  27.64,   8.42,  83.37, 24.0 ),
        new CondensateDataSet( parser.parse( "01/MAY/2010" ).getTime(),  27.39,   7.68,  88.95, 24.0 ),
        new CondensateDataSet( parser.parse( "02/MAY/2010" ).getTime(),  26.82,   7.11,  87.85, 24.0 ),
        new CondensateDataSet( parser.parse( "03/MAY/2010" ).getTime(),  27.46,  16.38,  88.89, 24.0 ),
        new CondensateDataSet( parser.parse( "04/MAY/2010" ).getTime(),  29.52,  17.86,  90.09, 24.0 ),
        new CondensateDataSet( parser.parse( "05/MAY/2010" ).getTime(),  27.58,  16.78,  87.63, 24.0 ),
        new CondensateDataSet( parser.parse( "06/MAY/2010" ).getTime(),  28.76,  16.94,  86.75, 24.0 ),
        new CondensateDataSet( parser.parse( "07/MAY/2010" ).getTime(),  28.14,   2.87,  81.55, 24.0 ),
        new CondensateDataSet( parser.parse( "08/MAY/2010" ).getTime(),  28.52,   7.74,  76.44, 24.0 ),
        new CondensateDataSet( parser.parse( "09/MAY/2010" ).getTime(),  28.52,   7.28,  76.24, 24.0 ),
        new CondensateDataSet( parser.parse( "10/MAY/2010" ).getTime(),  24.97,  11.09,  77.25, 24.0 ),
        new CondensateDataSet( parser.parse( "11/MAY/2010" ).getTime(),  24.61,   9.79,  81.40, 24.0 ),
        new CondensateDataSet( parser.parse( "12/MAY/2010" ).getTime(),  24.62,  10.81,  80.05, 24.0 ),
        new CondensateDataSet( parser.parse( "13/MAY/2010" ).getTime(),  24.66,   9.26,  80.83, 24.0 ),
        new CondensateDataSet( parser.parse( "14/MAY/2010" ).getTime(),  28.75,   9.27,  76.92, 24.0 ),
        new CondensateDataSet( parser.parse( "15/MAY/2010" ).getTime(),  28.43,   6.64,  80.45, 24.0 ),
        new CondensateDataSet( parser.parse( "16/MAY/2010" ).getTime(),  28.48,   7.38,  81.85, 24.0 ),
        new CondensateDataSet( parser.parse( "17/MAY/2010" ).getTime(),  28.57,   7.63,  89.33, 24.0 ),
        new CondensateDataSet( parser.parse( "18/MAY/2010" ).getTime(),  28.74,   7.65,  89.74, 24.0 ),
        new CondensateDataSet( parser.parse( "19/MAY/2010" ).getTime(),  27.70,   8.90,  81.85, 24.0 ),
        new CondensateDataSet( parser.parse( "20/MAY/2010" ).getTime(),  27.65,   7.81,  81.32, 24.0 ),
        new CondensateDataSet( parser.parse( "21/MAY/2010" ).getTime(),  27.63,   8.48,  82.64, 24.0 ),
        new CondensateDataSet( parser.parse( "22/MAY/2010" ).getTime(),  27.58,   7.78,  82.49, 24.0 ),
        new CondensateDataSet( parser.parse( "23/MAY/2010" ).getTime(),  27.57,   7.70,  79.97, 24.0 ),
        new CondensateDataSet( parser.parse( "24/MAY/2010" ).getTime(),  28.41,   7.83,  83.05, 24.0 ),
        new CondensateDataSet( parser.parse( "25/MAY/2010" ).getTime(),  27.75,   8.83,  80.64, 24.0 ),
        new CondensateDataSet( parser.parse( "26/MAY/2010" ).getTime(),  27.82,   7.30,  83.35, 24.0 ),
        new CondensateDataSet( parser.parse( "27/MAY/2010" ).getTime(),  27.92,  10.37,  82.16, 24.0 ),
        new CondensateDataSet( parser.parse( "28/MAY/2010" ).getTime(),  27.89,   7.08,  83.53, 24.0 ),
        new CondensateDataSet( parser.parse( "29/MAY/2010" ).getTime(),  27.80,   8.71,  82.16, 24.0 ),
        new CondensateDataSet( parser.parse( "30/MAY/2010" ).getTime(),  27.57,   0.00,  72.90, 24.0 ),
        new CondensateDataSet( parser.parse( "31/MAY/2010" ).getTime(),  27.17,   0.00,  66.06, 24.0 ),
        new CondensateDataSet( parser.parse( "01/JUN/2010" ).getTime(),  27.25,   0.00,  67.88, 24.0 ),
        new CondensateDataSet( parser.parse( "02/JUN/2010" ).getTime(),  27.19,   0.00,  67.18, 24.0 ),
        new CondensateDataSet( parser.parse( "03/JUN/2010" ).getTime(),  27.12,   0.00,  67.58, 24.0 ),
        new CondensateDataSet( parser.parse( "04/JUN/2010" ).getTime(),  27.11,   0.00,  67.29, 24.0 ),
        new CondensateDataSet( parser.parse( "05/JUN/2010" ).getTime(),  27.15,   0.00,  67.59, 24.0 ),
        new CondensateDataSet( parser.parse( "06/JUN/2010" ).getTime(),  27.04,   0.00,  68.00, 24.0 ),
        new CondensateDataSet( parser.parse( "07/JUN/2010" ).getTime(),  27.98,   0.00,  68.89, 24.0 ),
        new CondensateDataSet( parser.parse( "08/JUN/2010" ).getTime(),  27.49,   0.00,  66.58, 24.0 ),
        new CondensateDataSet( parser.parse( "09/JUN/2010" ).getTime(),  27.50,   0.00,  65.15, 24.0 ),
        new CondensateDataSet( parser.parse( "10/JUN/2010" ).getTime(),  27.64,   0.00,  67.77, 24.0 ),
        new CondensateDataSet( parser.parse( "11/JUN/2010" ).getTime(),  27.83,   0.00,  69.88, 24.0 ),
        new CondensateDataSet( parser.parse( "12/JUN/2010" ).getTime(),  27.59,   0.00,  67.85, 24.0 ),
        new CondensateDataSet( parser.parse( "13/JUN/2010" ).getTime(),  27.57,   0.00,  67.21, 24.0 ),
        new CondensateDataSet( parser.parse( "14/JUN/2010" ).getTime(),  27.57,   0.00,  63.64, 24.0 ),
        new CondensateDataSet( parser.parse( "15/JUN/2010" ).getTime(),  27.57,   0.00,  68.68, 24.0 ),
        new CondensateDataSet( parser.parse( "16/JUN/2010" ).getTime(),  27.56,   0.00,  67.95, 24.0 ),
        new CondensateDataSet( parser.parse( "17/JUN/2010" ).getTime(),  27.55,   0.00,  71.40, 24.0 ),
        new CondensateDataSet( parser.parse( "18/JUN/2010" ).getTime(),  27.55,   0.00,  68.93, 24.0 ),
        new CondensateDataSet( parser.parse( "19/JUN/2010" ).getTime(),  27.66,   0.00,  71.82, 24.0 ),
        new CondensateDataSet( parser.parse( "20/JUN/2010" ).getTime(),  27.63,   0.00,  69.50, 24.0 ),
        new CondensateDataSet( parser.parse( "21/JUN/2010" ).getTime(),  27.63,   0.00,  68.88, 24.0 ),
        new CondensateDataSet( parser.parse( "22/JUN/2010" ).getTime(),  27.65,   0.00,  64.73, 24.0 ),
        new CondensateDataSet( parser.parse( "23/JUN/2010" ).getTime(),  27.66,   0.00,  69.69, 24.0 ),
        new CondensateDataSet( parser.parse( "24/JUN/2010" ).getTime(),  27.62,   0.00,  70.48, 24.0 ),
        new CondensateDataSet( parser.parse( "25/JUN/2010" ).getTime(),  26.83,   6.94,  69.65, 24.0 ),
        new CondensateDataSet( parser.parse( "26/JUN/2010" ).getTime(),  26.93,   6.59,  72.13, 24.0 ),
        new CondensateDataSet( parser.parse( "27/JUN/2010" ).getTime(),  26.94,   4.22,  74.33, 24.0 ),
        new CondensateDataSet( parser.parse( "28/JUN/2010" ).getTime(),  27.02,   6.99,  72.28, 24.0 ),
        new CondensateDataSet( parser.parse( "29/JUN/2010" ).getTime(),  26.92,   5.95,  75.70, 24.0 ),
        new CondensateDataSet( parser.parse( "30/JUN/2010" ).getTime(),  26.92,   8.68,  71.44, 24.0 ),
        new CondensateDataSet( parser.parse( "01/JUL/2010" ).getTime(),  26.95,   8.63,  76.34, 24.0 ),
        new CondensateDataSet( parser.parse( "02/JUL/2010" ).getTime(),  26.93,   5.27,  72.94, 24.0 ),
        new CondensateDataSet( parser.parse( "03/JUL/2010" ).getTime(),  26.86,   8.16,  68.89, 24.0 ),
        new CondensateDataSet( parser.parse( "04/JUL/2010" ).getTime(),  26.87,   7.58,  73.76, 24.0 ),
        new CondensateDataSet( parser.parse( "05/JUL/2010" ).getTime(),  26.90,   7.79,  70.49, 24.0 ),
        new CondensateDataSet( parser.parse( "06/JUL/2010" ).getTime(),  26.84,   7.39,  72.65, 24.0 ),
        new CondensateDataSet( parser.parse( "07/JUL/2010" ).getTime(),  27.13,   8.28,  71.63, 24.0 ),
        new CondensateDataSet( parser.parse( "08/JUL/2010" ).getTime(),  26.84,   8.04,  70.55, 24.0 ),
        new CondensateDataSet( parser.parse( "09/JUL/2010" ).getTime(),  26.77,   6.45,  70.90, 24.0 ),
        new CondensateDataSet( parser.parse( "10/JUL/2010" ).getTime(),  26.65,   2.36,  72.62, 24.0 ),
        new CondensateDataSet( parser.parse( "11/JUL/2010" ).getTime(),  26.68,   7.24,  74.54, 24.0 ),
        new CondensateDataSet( parser.parse( "12/JUL/2010" ).getTime(),  26.84,   7.17,  71.67, 24.0 ),
        new CondensateDataSet( parser.parse( "13/JUL/2010" ).getTime(),  26.67,   7.94,  68.74, 24.0 ),
        new CondensateDataSet( parser.parse( "14/JUL/2010" ).getTime(),  26.65,   7.07,  70.43, 24.0 ),
        new CondensateDataSet( parser.parse( "15/JUL/2010" ).getTime(),  26.63,   7.28,  71.96, 24.0 ),
        new CondensateDataSet( parser.parse( "16/JUL/2010" ).getTime(),  26.64,   7.48,  75.28, 24.0 ),
        new CondensateDataSet( parser.parse( "17/JUL/2010" ).getTime(),  26.63,   7.46,  69.98, 24.0 ),
        new CondensateDataSet( parser.parse( "18/JUL/2010" ).getTime(),  26.98,   7.38,  69.82, 24.0 ),
        new CondensateDataSet( parser.parse( "19/JUL/2010" ).getTime(),  26.60,   7.23,  72.15, 24.0 ),
        new CondensateDataSet( parser.parse( "20/JUL/2010" ).getTime(),  26.87,   7.61,  70.02, 24.0 ),
        new CondensateDataSet( parser.parse( "21/JUL/2010" ).getTime(),  26.86,   7.06,  72.12, 24.0 ),
        new CondensateDataSet( parser.parse( "22/JUL/2010" ).getTime(),  26.83,   7.24,  67.49, 24.0 ),
        new CondensateDataSet( parser.parse( "23/JUL/2010" ).getTime(),  27.30,   7.77,  71.90, 24.0 ),
        new CondensateDataSet( parser.parse( "24/JUL/2010" ).getTime(),  27.53,   7.65,  69.86, 24.0 ),
        new CondensateDataSet( parser.parse( "25/JUL/2010" ).getTime(),  27.88,   7.73,  75.29, 24.0 ),
        new CondensateDataSet( parser.parse( "26/JUL/2010" ).getTime(),  27.10,   7.51,  72.66, 24.0 ),
        new CondensateDataSet( parser.parse( "27/JUL/2010" ).getTime(),  26.89,   7.82,  69.51, 24.0 ),
        new CondensateDataSet( parser.parse( "28/JUL/2010" ).getTime(),  26.90,   7.37,  73.33, 24.0 ),
        new CondensateDataSet( parser.parse( "29/JUL/2010" ).getTime(),  26.91,   7.77,  73.34, 24.0 ),
        new CondensateDataSet( parser.parse( "30/JUL/2010" ).getTime(),  26.92,  10.37,  71.20, 24.0 ),
        new CondensateDataSet( parser.parse( "31/JUL/2010" ).getTime(),  26.86,   8.54,  67.46, 24.0 ),
        new CondensateDataSet( parser.parse( "01/AUG/2010" ).getTime(),  26.87,   3.72,  59.95, 24.0 ),
        new CondensateDataSet( parser.parse( "02/AUG/2010" ).getTime(),  25.92,   2.61,  69.35, 24.0 ),
        new CondensateDataSet( parser.parse( "03/AUG/2010" ).getTime(),  26.81,   4.97,  67.59, 24.0 ),
        new CondensateDataSet( parser.parse( "04/AUG/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "05/AUG/2010" ).getTime(),  26.76,   3.55,  90.71, 24.0 ),
        new CondensateDataSet( parser.parse( "06/AUG/2010" ).getTime(),  26.76,   3.86,  73.59, 24.0 ),
        new CondensateDataSet( parser.parse( "07/AUG/2010" ).getTime(),  27.27,   4.07,  73.05, 24.0 ),
        new CondensateDataSet( parser.parse( "08/AUG/2010" ).getTime(),  26.70,   4.59,  73.42, 24.0 ),
        new CondensateDataSet( parser.parse( "09/AUG/2010" ).getTime(),  26.78,   3.73,  68.84, 24.0 ),
        new CondensateDataSet( parser.parse( "10/AUG/2010" ).getTime(),  26.44,   4.16,  74.12, 24.0 ),
        new CondensateDataSet( parser.parse( "11/AUG/2010" ).getTime(),  26.43,   3.96,  70.17, 24.0 ),
        new CondensateDataSet( parser.parse( "12/AUG/2010" ).getTime(),  26.40,   4.47,  72.65, 24.0 ),
        new CondensateDataSet( parser.parse( "13/AUG/2010" ).getTime(),  26.37,   4.13,  68.78, 24.0 ),
        new CondensateDataSet( parser.parse( "14/AUG/2010" ).getTime(),  26.38,   4.29,  69.11, 24.0 ),
        new CondensateDataSet( parser.parse( "15/AUG/2010" ).getTime(),  26.38,   3.97,  67.58, 24.0 ),
        new CondensateDataSet( parser.parse( "16/AUG/2010" ).getTime(),  26.57,   4.27,  67.42, 24.0 ),
        new CondensateDataSet( parser.parse( "17/AUG/2010" ).getTime(),  26.38,   3.98,  73.73, 24.0 ),
        new CondensateDataSet( parser.parse( "18/AUG/2010" ).getTime(),  26.38,   4.27,  68.22, 24.0 ),
        new CondensateDataSet( parser.parse( "19/AUG/2010" ).getTime(),  26.36,   4.11,  70.89, 24.0 ),
        new CondensateDataSet( parser.parse( "20/AUG/2010" ).getTime(),  26.38,   4.31,  72.42, 24.0 ),
        new CondensateDataSet( parser.parse( "21/AUG/2010" ).getTime(),  26.36,   4.22,  71.62, 24.0 ),
        new CondensateDataSet( parser.parse( "22/AUG/2010" ).getTime(),  26.36,   4.02,  71.46, 24.0 ),
        new CondensateDataSet( parser.parse( "23/AUG/2010" ).getTime(),  26.39,   4.33,  71.08, 24.0 ),
        new CondensateDataSet( parser.parse( "24/AUG/2010" ).getTime(),  26.39,   4.36,  69.34, 24.0 ),
        new CondensateDataSet( parser.parse( "25/AUG/2010" ).getTime(),  26.38,   4.06,  70.93, 24.0 ),
        new CondensateDataSet( parser.parse( "26/AUG/2010" ).getTime(),  26.45,   4.14,  69.61, 24.0 ),
        new CondensateDataSet( parser.parse( "27/AUG/2010" ).getTime(),  26.47,   4.22,  64.72, 24.0 ),
        new CondensateDataSet( parser.parse( "28/AUG/2010" ).getTime(),  26.41,   4.02,  70.16, 24.0 ),
        new CondensateDataSet( parser.parse( "29/AUG/2010" ).getTime(),  26.38,   4.10,  69.64, 24.0 ),
        new CondensateDataSet( parser.parse( "30/AUG/2010" ).getTime(),  26.38,   4.11,  65.24, 24.0 ),
        new CondensateDataSet( parser.parse( "31/AUG/2010" ).getTime(),  26.35,   4.05,  68.86, 24.0 ),
        new CondensateDataSet( parser.parse( "01/SEP/2010" ).getTime(),  26.72,   4.29,  67.13, 24.0 ),
        new CondensateDataSet( parser.parse( "02/SEP/2010" ).getTime(),  26.78,   4.15,  72.21, 24.0 ),
        new CondensateDataSet( parser.parse( "03/SEP/2010" ).getTime(),  26.41,   4.11,  69.09, 24.0 ),
        new CondensateDataSet( parser.parse( "04/SEP/2010" ).getTime(),  26.56,   4.54,  74.08, 24.0 ),
        new CondensateDataSet( parser.parse( "05/SEP/2010" ).getTime(),  26.62,   4.36,  73.31, 24.0 ),
        new CondensateDataSet( parser.parse( "06/SEP/2010" ).getTime(),  26.62,   3.97,  71.24, 24.0 ),
        new CondensateDataSet( parser.parse( "07/SEP/2010" ).getTime(),  26.60,   4.15,  70.30, 24.0 ),
        new CondensateDataSet( parser.parse( "08/SEP/2010" ).getTime(),  26.63,   4.13,  73.15, 24.0 ),
        new CondensateDataSet( parser.parse( "09/SEP/2010" ).getTime(),  25.89,   6.76,  61.95, 24.0 ),
        new CondensateDataSet( parser.parse( "10/SEP/2010" ).getTime(),  25.95,   4.61,  66.47, 24.0 ),
        new CondensateDataSet( parser.parse( "11/SEP/2010" ).getTime(),  26.18,   3.82,  74.71, 24.0 ),
        new CondensateDataSet( parser.parse( "12/SEP/2010" ).getTime(),  23.83,   4.49,  70.23, 24.0 ),
        new CondensateDataSet( parser.parse( "13/SEP/2010" ).getTime(),  25.36,   4.46,  81.63, 24.0 ),
        new CondensateDataSet( parser.parse( "14/SEP/2010" ).getTime(),  23.88,   7.02,  83.41, 24.0 ),
        new CondensateDataSet( parser.parse( "15/SEP/2010" ).getTime(),  25.67,   5.36,  86.78, 24.0 ),
        new CondensateDataSet( parser.parse( "16/SEP/2010" ).getTime(),  25.35,   5.16,  77.51, 24.0 ),
        new CondensateDataSet( parser.parse( "17/SEP/2010" ).getTime(),  25.20,   5.84,  97.45, 24.0 ),
        new CondensateDataSet( parser.parse( "18/SEP/2010" ).getTime(),  25.09,   5.50,  68.88, 24.0 ),
        new CondensateDataSet( parser.parse( "19/SEP/2010" ).getTime(),  25.02,   4.40,  66.74, 24.0 ),
        new CondensateDataSet( parser.parse( "20/SEP/2010" ).getTime(),  26.46,   5.73,  70.79, 24.0 ),
        new CondensateDataSet( parser.parse( "21/SEP/2010" ).getTime(),  27.52,   6.97,  73.73, 24.0 ),
        new CondensateDataSet( parser.parse( "22/SEP/2010" ).getTime(),  26.90,   6.41,  73.51, 24.0 ),
        new CondensateDataSet( parser.parse( "23/SEP/2010" ).getTime(),  26.86,   6.35,  73.21, 24.0 ),
        new CondensateDataSet( parser.parse( "24/SEP/2010" ).getTime(),  26.86,   6.35,  71.77, 24.0 ),
        new CondensateDataSet( parser.parse( "25/SEP/2010" ).getTime(),  26.84,   6.86,  68.50, 24.0 ),
        new CondensateDataSet( parser.parse( "26/SEP/2010" ).getTime(),  26.91,   6.21,  73.01, 24.0 ),
        new CondensateDataSet( parser.parse( "27/SEP/2010" ).getTime(),  26.87,   6.68,  73.64, 24.0 ),
        new CondensateDataSet( parser.parse( "28/SEP/2010" ).getTime(),  26.84,   6.55,  69.24, 24.0 ),
        new CondensateDataSet( parser.parse( "29/SEP/2010" ).getTime(),  26.87,   6.43,  73.66, 24.0 ),
        new CondensateDataSet( parser.parse( "30/SEP/2010" ).getTime(),  26.58,   6.39,  66.90, 24.0 ),
        new CondensateDataSet( parser.parse( "01/OCT/2010" ).getTime(),  26.16,   0.00,  70.08, 24.0 ),
        new CondensateDataSet( parser.parse( "02/OCT/2010" ).getTime(),  25.15,   0.02,  70.31, 24.0 ),
        new CondensateDataSet( parser.parse( "03/OCT/2010" ).getTime(),  24.21,   6.00,  67.78, 24.0 ),
        new CondensateDataSet( parser.parse( "04/OCT/2010" ).getTime(),  24.20,   5.47,  66.86, 24.0 ),
        new CondensateDataSet( parser.parse( "05/OCT/2010" ).getTime(),  24.25,   5.15,  65.44, 24.0 ),
        new CondensateDataSet( parser.parse( "06/OCT/2010" ).getTime(),  24.25,   5.66,  63.39, 24.0 ),
        new CondensateDataSet( parser.parse( "07/OCT/2010" ).getTime(),  24.27,   5.19,  65.56, 24.0 ),
        new CondensateDataSet( parser.parse( "08/OCT/2010" ).getTime(),  24.25,   5.16,  65.86, 24.0 ),
        new CondensateDataSet( parser.parse( "09/OCT/2010" ).getTime(),  24.29,   5.25,  63.96, 24.0 ),
        new CondensateDataSet( parser.parse( "10/OCT/2010" ).getTime(),  25.62,   0.05,  66.87, 24.0 ),
        new CondensateDataSet( parser.parse( "11/OCT/2010" ).getTime(),  26.08,  15.83,  68.55, 24.0 ),
        new CondensateDataSet( parser.parse( "12/OCT/2010" ).getTime(),  26.56,  16.29,  63.53, 24.0 ),
        new CondensateDataSet( parser.parse( "13/OCT/2010" ).getTime(),  25.79,   0.03,  70.23, 24.0 ),
        new CondensateDataSet( parser.parse( "14/OCT/2010" ).getTime(),  24.95,   6.35,  66.94, 24.0 ),
        new CondensateDataSet( parser.parse( "15/OCT/2010" ).getTime(),  25.19,   6.44,  67.81, 24.0 ),
        new CondensateDataSet( parser.parse( "16/OCT/2010" ).getTime(),  25.58,   8.41,  64.44, 24.0 ),
        new CondensateDataSet( parser.parse( "17/OCT/2010" ).getTime(),  24.82,   8.37,  68.25, 24.0 ),
        new CondensateDataSet( parser.parse( "18/OCT/2010" ).getTime(),  25.09,   6.17,  68.05, 24.0 ),
        new CondensateDataSet( parser.parse( "19/OCT/2010" ).getTime(),  24.39,   3.86,  65.52, 24.0 ),
        new CondensateDataSet( parser.parse( "20/OCT/2010" ).getTime(),  24.40,   5.71,  68.65, 24.0 ),
        new CondensateDataSet( parser.parse( "21/OCT/2010" ).getTime(),  25.32,   6.81,  70.76, 24.0 ),
        new CondensateDataSet( parser.parse( "22/OCT/2010" ).getTime(),  25.81,   6.01,  68.64, 24.0 ),
        new CondensateDataSet( parser.parse( "23/OCT/2010" ).getTime(),  25.52,   6.75,  69.82, 24.0 ),
        new CondensateDataSet( parser.parse( "24/OCT/2010" ).getTime(),  25.33,   6.25,  64.25, 24.0 ),
        new CondensateDataSet( parser.parse( "25/OCT/2010" ).getTime(),  25.19,   6.14,  66.18, 24.0 ),
        new CondensateDataSet( parser.parse( "26/OCT/2010" ).getTime(),  26.35,   6.35,  71.31, 24.0 ),
        new CondensateDataSet( parser.parse( "27/OCT/2010" ).getTime(),  26.67,   6.88,  72.16, 24.0 ),
        new CondensateDataSet( parser.parse( "28/OCT/2010" ).getTime(),  25.53,   6.26,  67.14, 24.0 ),
        new CondensateDataSet( parser.parse( "29/OCT/2010" ).getTime(),  25.53,   0.00,  74.48, 24.0 ),
        new CondensateDataSet( parser.parse( "30/OCT/2010" ).getTime(),  25.12,   6.63,  72.21, 24.0 ),
        new CondensateDataSet( parser.parse( "31/OCT/2010" ).getTime(),  24.76,   6.10,  70.64, 24.0 ),
        new CondensateDataSet( parser.parse( "01/NOV/2010" ).getTime(),  25.67,   6.52,  64.74, 24.0 ),
        new CondensateDataSet( parser.parse( "02/NOV/2010" ).getTime(),  25.48,   6.61,  70.45, 24.0 ),
        new CondensateDataSet( parser.parse( "03/NOV/2010" ).getTime(),  25.71,   6.16,  70.61, 24.0 ),
        new CondensateDataSet( parser.parse( "04/NOV/2010" ).getTime(),  25.55,   6.32,  66.42, 24.0 ),
        new CondensateDataSet( parser.parse( "05/NOV/2010" ).getTime(),  25.28,   6.28,  68.96, 24.0 ),
        new CondensateDataSet( parser.parse( "06/NOV/2010" ).getTime(),  25.64,   6.67,  70.65, 24.0 ),
        new CondensateDataSet( parser.parse( "07/NOV/2010" ).getTime(),  25.52,   5.25,  69.53, 24.0 ),
        new CondensateDataSet( parser.parse( "08/NOV/2010" ).getTime(),  26.68,   8.43,  71.50, 24.0 ),
        new CondensateDataSet( parser.parse( "09/NOV/2010" ).getTime(),  26.19,   6.74,  66.36, 24.0 ),
        new CondensateDataSet( parser.parse( "10/NOV/2010" ).getTime(),  26.28,   6.53,  67.35, 24.0 ),
        new CondensateDataSet( parser.parse( "11/NOV/2010" ).getTime(),  26.51,   6.78,  68.27, 24.0 ),
        new CondensateDataSet( parser.parse( "12/NOV/2010" ).getTime(),  26.01,   6.53,  66.51, 24.0 ),
        new CondensateDataSet( parser.parse( "13/NOV/2010" ).getTime(),  25.79,   6.34,  63.74, 24.0 ),
        new CondensateDataSet( parser.parse( "14/NOV/2010" ).getTime(),  25.58,   6.33,  72.31, 24.0 ),
        new CondensateDataSet( parser.parse( "15/NOV/2010" ).getTime(),  26.10,   6.77,  63.10, 24.0 ),
        new CondensateDataSet( parser.parse( "16/NOV/2010" ).getTime(),  26.05,   6.62,  70.74, 24.0 ),
        new CondensateDataSet( parser.parse( "17/NOV/2010" ).getTime(),  25.63,   6.31,  70.98, 24.0 ),
        new CondensateDataSet( parser.parse( "18/NOV/2010" ).getTime(),  16.14,   7.00,  51.05, 24.0 ),
        new CondensateDataSet( parser.parse( "19/NOV/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "20/NOV/2010" ).getTime(),   0.00,   0.00,   0.00, 24.0 ),
        new CondensateDataSet( parser.parse( "21/NOV/2010" ).getTime(),  19.39,   9.17, 119.26, 24.0 ),
        new CondensateDataSet( parser.parse( "22/NOV/2010" ).getTime(),  21.32,   4.43,  76.86, 24.0 ),
        new CondensateDataSet( parser.parse( "23/NOV/2010" ).getTime(),  25.41,   7.82,  80.07, 24.0 ),
        new CondensateDataSet( parser.parse( "24/NOV/2010" ).getTime(),  24.62,   7.26,  76.02, 24.0 ),
        new CondensateDataSet( parser.parse( "25/NOV/2010" ).getTime(),  24.43,   6.86,  68.02, 24.0 ),
        new CondensateDataSet( parser.parse( "26/NOV/2010" ).getTime(),  25.28,   7.18,  67.85, 24.0 ),
        new CondensateDataSet( parser.parse( "27/NOV/2010" ).getTime(),  25.44,   9.07,  66.98, 24.0 ),
        new CondensateDataSet( parser.parse( "28/NOV/2010" ).getTime(),  25.10,   6.82,  70.91, 24.0 ),
        new CondensateDataSet( parser.parse( "29/NOV/2010" ).getTime(),  25.47,   6.96,  71.91, 24.0 ),
        new CondensateDataSet( parser.parse( "30/NOV/2010" ).getTime(),  25.85,   6.80,  71.51, 24.0 ),
        new CondensateDataSet( parser.parse( "01/DEC/2010" ).getTime(),  26.03,   6.73,  72.43, 24.0 ),
        new CondensateDataSet( parser.parse( "02/DEC/2010" ).getTime(),  25.35,   6.71,  65.66, 24.0 ),
        new CondensateDataSet( parser.parse( "03/DEC/2010" ).getTime(),  25.31,   6.27,  66.81, 24.0 ),
        new CondensateDataSet( parser.parse( "04/DEC/2010" ).getTime(),  25.42,   6.81,  69.66, 24.0 ),
        new CondensateDataSet( parser.parse( "05/DEC/2010" ).getTime(),  26.23,   6.42,  73.41, 24.0 ),
        new CondensateDataSet( parser.parse( "06/DEC/2010" ).getTime(),  25.88,   6.68,  67.54, 24.0 ),
        new CondensateDataSet( parser.parse( "07/DEC/2010" ).getTime(),  25.47,   6.60,  64.24, 24.0 ),
        new CondensateDataSet( parser.parse( "08/DEC/2010" ).getTime(),  25.48,   7.17,  70.50, 24.0 ),
        new CondensateDataSet( parser.parse( "09/DEC/2010" ).getTime(),  25.40,   6.71,  67.00, 24.0 ),
        new CondensateDataSet( parser.parse( "10/DEC/2010" ).getTime(),  15.13,   3.71,  37.29, 24.0 ),
        new CondensateDataSet( parser.parse( "11/DEC/2010" ).getTime(),  26.29,   6.10,  71.50, 24.0 ),
        new CondensateDataSet( parser.parse( "12/DEC/2010" ).getTime(),  26.43,   7.05,  73.76, 24.0 ),
        new CondensateDataSet( parser.parse( "13/DEC/2010" ).getTime(),  26.01,   6.93,  72.55, 24.0 ),
        new CondensateDataSet( parser.parse( "14/DEC/2010" ).getTime(),  26.20,   7.07,  68.15, 24.0 ),
        new CondensateDataSet( parser.parse( "15/DEC/2010" ).getTime(),  26.18,   7.05,  64.48, 24.0 ),
        new CondensateDataSet( parser.parse( "16/DEC/2010" ).getTime(),  26.40,   6.34,  71.77, 24.0 ),
        new CondensateDataSet( parser.parse( "17/DEC/2010" ).getTime(),  25.95,   6.91,  70.61, 24.0 ),
        new CondensateDataSet( parser.parse( "18/DEC/2010" ).getTime(),  24.73,   6.92,  64.55, 24.0 ),
        new CondensateDataSet( parser.parse( "19/DEC/2010" ).getTime(),  24.82,   5.98,  69.65, 24.0 ),
        new CondensateDataSet( parser.parse( "20/DEC/2010" ).getTime(),  25.27,   7.43,  62.36, 24.0 ),
        new CondensateDataSet( parser.parse( "21/DEC/2010" ).getTime(),  25.59,   6.68,  61.94, 24.0 ),
        new CondensateDataSet( parser.parse( "22/DEC/2010" ).getTime(),  25.69,   7.38,  73.34, 24.0 ),
        new CondensateDataSet( parser.parse( "23/DEC/2010" ).getTime(),  25.94,   6.29,  69.05, 24.0 ),
        new CondensateDataSet( parser.parse( "24/DEC/2010" ).getTime(),  25.69,   6.61,  69.15, 24.0 ),
        new CondensateDataSet( parser.parse( "25/DEC/2010" ).getTime(),  25.83,   6.59,  71.77, 24.0 ),
        new CondensateDataSet( parser.parse( "26/DEC/2010" ).getTime(),  25.89,   6.02,  70.61, 24.0 ),
        new CondensateDataSet( parser.parse( "27/DEC/2010" ).getTime(),  25.86,   6.22,  66.38, 24.0 ),
        new CondensateDataSet( parser.parse( "28/DEC/2010" ).getTime(),  26.30,   7.19,  62.73, 24.0 ),
        new CondensateDataSet( parser.parse( "29/DEC/2010" ).getTime(),  25.72,   8.10,  68.81, 24.0 ),
        new CondensateDataSet( parser.parse( "30/DEC/2010" ).getTime(),  25.87,   6.37,  67.96, 24.0 ),
        new CondensateDataSet( parser.parse( "31/DEC/2010" ).getTime(),  26.09,   6.81,  69.66, 24.0 ),
        new CondensateDataSet( parser.parse( "01/JAN/2011" ).getTime(),  26.14,   6.59,  74.70, 24.0 ),
        new CondensateDataSet( parser.parse( "02/JAN/2011" ).getTime(),  26.41,   6.07,  70.25, 24.0 ),
        new CondensateDataSet( parser.parse( "03/JAN/2011" ).getTime(),  25.94,   6.36,  77.36, 24.0 ),
        new CondensateDataSet( parser.parse( "04/JAN/2011" ).getTime(),  25.92,   6.37,  72.24, 24.0 ),
        new CondensateDataSet( parser.parse( "05/JAN/2011" ).getTime(),  26.19,   5.91,  68.37, 24.0 ),
        new CondensateDataSet( parser.parse( "06/JAN/2011" ).getTime(),  26.24,   6.99,  69.00, 24.0 ),
        new CondensateDataSet( parser.parse( "07/JAN/2011" ).getTime(),  26.12,   6.71,  74.33, 24.0 ),
        new CondensateDataSet( parser.parse( "08/JAN/2011" ).getTime(),  26.24,   6.58,  75.14, 24.0 ),
        new CondensateDataSet( parser.parse( "09/JAN/2011" ).getTime(),  25.92,   6.67,  70.16, 24.0 ),
        new CondensateDataSet( parser.parse( "10/JAN/2011" ).getTime(),  22.37,   5.09,  61.22, 24.0 ),
        new CondensateDataSet( parser.parse( "11/JAN/2011" ).getTime(),  26.38,   6.53,  74.17, 24.0 ),
        new CondensateDataSet( parser.parse( "12/JAN/2011" ).getTime(),  26.36,   6.51,  72.72, 24.0 ),
        new CondensateDataSet( parser.parse( "13/JAN/2011" ).getTime(),  26.63,   6.80,  68.48, 24.0 ),
        new CondensateDataSet( parser.parse( "14/JAN/2011" ).getTime(),  26.47,   6.22,  69.38, 24.0 ),
        new CondensateDataSet( parser.parse( "15/JAN/2011" ).getTime(),  26.41,   5.95,  74.55, 24.0 ),
        new CondensateDataSet( parser.parse( "16/JAN/2011" ).getTime(),  26.46,   6.61,  75.52, 24.0 ),
        new CondensateDataSet( parser.parse( "17/JAN/2011" ).getTime(),  26.27,   6.54,  69.27, 24.0 ),
        new CondensateDataSet( parser.parse( "18/JAN/2011" ).getTime(),  26.36,   6.32,  68.07, 24.0 ),
        new CondensateDataSet( parser.parse( "19/JAN/2011" ).getTime(),  26.13,   6.41,  66.96, 24.0 ),
        new CondensateDataSet( parser.parse( "20/JAN/2011" ).getTime(),  26.59,   6.52,  73.99, 24.0 ),
        new CondensateDataSet( parser.parse( "21/JAN/2011" ).getTime(),  26.99,   6.63,  74.76, 24.0 ),
        new CondensateDataSet( parser.parse( "22/JAN/2011" ).getTime(),  27.08,   6.90,  71.03, 24.0 ),
        new CondensateDataSet( parser.parse( "23/JAN/2011" ).getTime(),  27.72,   7.26,  71.15, 24.0 ),
        new CondensateDataSet( parser.parse( "24/JAN/2011" ).getTime(),  28.49,   8.96,  70.07, 24.0 ),
        new CondensateDataSet( parser.parse( "25/JAN/2011" ).getTime(),  27.80,   7.30,  80.09, 24.0 ),
        new CondensateDataSet( parser.parse( "26/JAN/2011" ).getTime(),  27.54,   6.60,  67.44, 24.0 ),
        new CondensateDataSet( parser.parse( "27/JAN/2011" ).getTime(),  27.51,   6.86,  69.88, 24.0 ),
        new CondensateDataSet( parser.parse( "28/JAN/2011" ).getTime(),  27.44,   7.64,  67.51, 24.0 ),
        new CondensateDataSet( parser.parse( "29/JAN/2011" ).getTime(),  27.94,   4.17,  70.15, 24.0 ),
        new CondensateDataSet( parser.parse( "30/JAN/2011" ).getTime(),  27.16,   7.89,  59.83, 24.0 ),
        new CondensateDataSet( parser.parse( "31/JAN/2011" ).getTime(),  27.78,   7.29,  68.91, 24.0 ),
        new CondensateDataSet( parser.parse( "01/FEB/2011" ).getTime(),  26.25,   7.32,  58.07, 24.0 ),
        new CondensateDataSet( parser.parse( "02/FEB/2011" ).getTime(),  27.19,   8.47,  53.98, 24.0 ),
        new CondensateDataSet( parser.parse( "03/FEB/2011" ).getTime(),  27.45,   6.44,  61.21, 24.0 ),
        new CondensateDataSet( parser.parse( "04/FEB/2011" ).getTime(),  27.38,   8.82,  56.99, 24.0 ),
        new CondensateDataSet( parser.parse( "05/FEB/2011" ).getTime(),  27.41,   6.65,  59.15, 24.0 ),
        new CondensateDataSet( parser.parse( "06/FEB/2011" ).getTime(),  27.44,   6.68,  57.37, 24.0 ),
        new CondensateDataSet( parser.parse( "07/FEB/2011" ).getTime(),  27.06,   4.72,  59.69, 24.0 ),
        new CondensateDataSet( parser.parse( "08/FEB/2011" ).getTime(),  27.57,   5.99,  60.46, 24.0 ),
        new CondensateDataSet( parser.parse( "09/FEB/2011" ).getTime(),  26.94,   5.40,  56.01, 24.0 ),
        new CondensateDataSet( parser.parse( "10/FEB/2011" ).getTime(),  26.25,   5.33,  51.55, 24.0 ),
        new CondensateDataSet( parser.parse( "11/FEB/2011" ).getTime(),  26.19,   5.14,  53.56, 24.0 ),
        new CondensateDataSet( parser.parse( "12/FEB/2011" ).getTime(),  26.34,   6.16,  54.26, 24.0 ),
        new CondensateDataSet( parser.parse( "13/FEB/2011" ).getTime(),  26.48,   6.04,  55.67, 24.0 ),
        new CondensateDataSet( parser.parse( "14/FEB/2011" ).getTime(),  26.51,   5.88,  53.21, 24.0 ),
        new CondensateDataSet( parser.parse( "15/FEB/2011" ).getTime(),  26.48,   6.23,  56.65, 24.0 ),
        new CondensateDataSet( parser.parse( "16/FEB/2011" ).getTime(),  26.56,   6.58,  55.64, 24.0 ),
        new CondensateDataSet( parser.parse( "17/FEB/2011" ).getTime(),  26.53,   6.74,  55.58, 24.0 ),
        new CondensateDataSet( parser.parse( "18/FEB/2011" ).getTime(),  26.55,   6.73,  54.38, 24.0 ),
        new CondensateDataSet( parser.parse( "19/FEB/2011" ).getTime(),  26.50,   6.60,  55.95, 24.0 ),
        new CondensateDataSet( parser.parse( "20/FEB/2011" ).getTime(),  26.54,   5.59,  55.46, 24.0 ),
        new CondensateDataSet( parser.parse( "21/FEB/2011" ).getTime(),  27.10,   7.40,  54.44, 24.0 ),
        new CondensateDataSet( parser.parse( "22/FEB/2011" ).getTime(),  26.25,   6.44,  51.61, 24.0 ),
        new CondensateDataSet( parser.parse( "23/FEB/2011" ).getTime(),  26.06,   5.94,  53.37, 24.0 ),
        new CondensateDataSet( parser.parse( "24/FEB/2011" ).getTime(),  25.95,   5.89,  55.85, 24.0 ),
        new CondensateDataSet( parser.parse( "25/FEB/2011" ).getTime(),  26.28,   6.35,  52.02, 24.0 ),
        new CondensateDataSet( parser.parse( "26/FEB/2011" ).getTime(),  26.32,   6.91,  56.56, 24.0 ),
        new CondensateDataSet( parser.parse( "27/FEB/2011" ).getTime(),  26.37,   5.98,  53.99, 24.0 ),
        new CondensateDataSet( parser.parse( "28/FEB/2011" ).getTime(),  26.34,   6.43,  52.81, 24.0 ),
        new CondensateDataSet( parser.parse( "01/MAR/2011" ).getTime(),  25.38,   6.55,  55.15, 24.0 ),
        new CondensateDataSet( parser.parse( "02/MAR/2011" ).getTime(),  25.54,   5.55,  55.02, 24.0 ),
        new CondensateDataSet( parser.parse( "03/MAR/2011" ).getTime(),  25.92,   5.91,  51.25, 24.0 ),
        new CondensateDataSet( parser.parse( "04/MAR/2011" ).getTime(),  26.26,   6.12,  51.26, 24.0 ),
        new CondensateDataSet( parser.parse( "05/MAR/2011" ).getTime(),  26.15,   5.81,  53.48, 24.0 ),
        new CondensateDataSet( parser.parse( "06/MAR/2011" ).getTime(),  26.21,   6.27,  50.87, 24.0 ),
        new CondensateDataSet( parser.parse( "07/MAR/2011" ).getTime(),  26.56,   6.28,  56.75, 24.0 ),
        new CondensateDataSet( parser.parse( "08/MAR/2011" ).getTime(),  26.37,   5.98,  54.69, 24.0 ),
        new CondensateDataSet( parser.parse( "09/MAR/2011" ).getTime(),  26.46,   6.24,  50.41, 24.0 ),
        new CondensateDataSet( parser.parse( "10/MAR/2011" ).getTime(),  26.41,   6.56,  55.86, 24.0 ),
        new CondensateDataSet( parser.parse( "11/MAR/2011" ).getTime(),  26.39,   6.59,  56.47, 24.0 ),
        new CondensateDataSet( parser.parse( "12/MAR/2011" ).getTime(),  26.42,   6.20,  52.26, 24.0 ),
        new CondensateDataSet( parser.parse( "13/MAR/2011" ).getTime(),  26.14,   6.46,  51.71, 24.0 ),
        new CondensateDataSet( parser.parse( "14/MAR/2011" ).getTime(),  26.96,  10.93,  52.38, 24.0 ),
        new CondensateDataSet( parser.parse( "15/MAR/2011" ).getTime(),  28.97,  13.37,  54.15, 24.0 ),
        new CondensateDataSet( parser.parse( "16/MAR/2011" ).getTime(),  27.59,   6.24,  56.85, 24.0 ),
        new CondensateDataSet( parser.parse( "17/MAR/2011" ).getTime(),  26.07,   5.92,  49.48, 24.0 ),
        new CondensateDataSet( parser.parse( "18/MAR/2011" ).getTime(),  26.45,   0.18,  47.99, 24.0 ),
        new CondensateDataSet( parser.parse( "19/MAR/2011" ).getTime(),  26.84,  11.98,  54.20, 24.0 ),
        new CondensateDataSet( parser.parse( "20/MAR/2011" ).getTime(),  27.71,   0.91,  55.57, 24.0 ),
        new CondensateDataSet( parser.parse( "21/MAR/2011" ).getTime(),  26.73,   5.61,  56.16, 24.0 ),
        new CondensateDataSet( parser.parse( "22/MAR/2011" ).getTime(),  25.83,   5.46,  54.21, 24.0 ),
        new CondensateDataSet( parser.parse( "23/MAR/2011" ).getTime(),  24.88,   5.72,  51.25, 24.0 ),
        new CondensateDataSet( parser.parse( "24/MAR/2011" ).getTime(),  13.23,   2.75,  26.39, 24.0 ),
        new CondensateDataSet( parser.parse( "25/MAR/2011" ).getTime(),  17.24,   3.60,  34.38, 24.0 ),
        new CondensateDataSet( parser.parse( "26/MAR/2011" ).getTime(),  25.05,   5.76,  51.72, 24.0 ),
        new CondensateDataSet( parser.parse( "27/MAR/2011" ).getTime(),  24.86,   4.90,  51.78, 24.0 ),
        new CondensateDataSet( parser.parse( "28/MAR/2011" ).getTime(),  24.40,   4.99,  50.62, 24.0 ),
        new CondensateDataSet( parser.parse( "29/MAR/2011" ).getTime(),  23.49,   5.24,  50.26, 24.0 ),
        new CondensateDataSet( parser.parse( "30/MAR/2011" ).getTime(),  24.77,   5.58,  50.94, 24.0 ),
        new CondensateDataSet( parser.parse( "31/MAR/2011" ).getTime(),  25.00,   5.22,  52.22, 24.0 ),
        new CondensateDataSet( parser.parse( "01/APR/2011" ).getTime(),  25.04,   5.39,  48.40, 24.0 ),
        new CondensateDataSet( parser.parse( "02/APR/2011" ).getTime(),  24.65,   5.05,  48.69, 24.0 ),
        new CondensateDataSet( parser.parse( "03/APR/2011" ).getTime(),  24.97,   5.08,  50.67, 24.0 ),
        new CondensateDataSet( parser.parse( "04/APR/2011" ).getTime(),  25.02,   5.29,  50.44, 24.0 ),
        new CondensateDataSet( parser.parse( "05/APR/2011" ).getTime(),  25.56,   5.63,  53.86, 24.0 ),
        new CondensateDataSet( parser.parse( "06/APR/2011" ).getTime(),  25.81,   5.21,  50.30, 24.0 ),
        new CondensateDataSet( parser.parse( "07/APR/2011" ).getTime(),  26.06,   4.99,  52.32, 24.0 ),
        new CondensateDataSet( parser.parse( "08/APR/2011" ).getTime(),  25.60,   4.80,  52.41, 24.0 ),
        new CondensateDataSet( parser.parse( "09/APR/2011" ).getTime(),  25.41,   5.30,  47.77, 24.0 ),
        new CondensateDataSet( parser.parse( "10/APR/2011" ).getTime(),  25.45,   5.34,  48.32, 24.0 ),
        new CondensateDataSet( parser.parse( "11/APR/2011" ).getTime(),  25.58,   6.27,  52.61, 24.0 ),
        new CondensateDataSet( parser.parse( "12/APR/2011" ).getTime(),  25.35,   4.73,  48.08, 24.0 ),
        new CondensateDataSet( parser.parse( "13/APR/2011" ).getTime(),  25.46,   5.68,  52.70, 24.0 ),
        new CondensateDataSet( parser.parse( "14/APR/2011" ).getTime(),  25.45,   5.89,  49.87, 24.0 ),
        new CondensateDataSet( parser.parse( "15/APR/2011" ).getTime(),  25.41,   5.01,  53.58, 24.0 ),
        new CondensateDataSet( parser.parse( "16/APR/2011" ).getTime(),  25.11,   5.24,  53.36, 24.0 ),
        new CondensateDataSet( parser.parse( "17/APR/2011" ).getTime(),  25.21,   5.44,  53.35, 24.0 ),
        new CondensateDataSet( parser.parse( "18/APR/2011" ).getTime(),  25.35,   5.19,  48.58, 24.0 ),
        new CondensateDataSet( parser.parse( "19/APR/2011" ).getTime(),  25.09,   5.31,  51.87, 24.0 ),
        new CondensateDataSet( parser.parse( "20/APR/2011" ).getTime(),  25.12,   5.03,  51.62, 24.0 ),
        new CondensateDataSet( parser.parse( "21/APR/2011" ).getTime(),  25.16,   5.07,  51.73, 24.0 ),
        new CondensateDataSet( parser.parse( "22/APR/2011" ).getTime(),  26.25,   4.61,  51.53, 24.0 ),
        new CondensateDataSet( parser.parse( "23/APR/2011" ).getTime(),  24.85,   4.68,  52.65, 24.0 ),
        new CondensateDataSet( parser.parse( "24/APR/2011" ).getTime(),  24.78,   5.64,  48.43, 24.0 ),
        new CondensateDataSet( parser.parse( "25/APR/2011" ).getTime(),  24.88,   5.05,  48.15, 24.0 ),
        new CondensateDataSet( parser.parse( "26/APR/2011" ).getTime(),  24.87,   4.71,  51.59, 24.0 ),
        new CondensateDataSet( parser.parse( "27/APR/2011" ).getTime(),  24.81,   5.53,  49.05, 24.0 ),
        new CondensateDataSet( parser.parse( "28/APR/2011" ).getTime(),  24.82,   5.82,  51.84, 24.0 ),
        new CondensateDataSet( parser.parse( "29/APR/2011" ).getTime(),  24.89,   4.91,  48.87, 24.0 ),
        new CondensateDataSet( parser.parse( "30/APR/2011" ).getTime(),  24.88,   5.06,  51.21, 24.0 ),
        new CondensateDataSet( parser.parse( "01/MAY/2011" ).getTime(),  24.87,   5.25,  49.49, 24.0 ),
        new CondensateDataSet( parser.parse( "02/MAY/2011" ).getTime(),  24.92,   5.15,  48.65, 24.0 ),
        new CondensateDataSet( parser.parse( "03/MAY/2011" ).getTime(),  24.87,   5.41,  55.42, 24.0 ),
        new CondensateDataSet( parser.parse( "04/MAY/2011" ).getTime(),  24.63,   5.01,  51.69, 24.0 ),
        new CondensateDataSet( parser.parse( "05/MAY/2011" ).getTime(),  24.93,   5.33,  46.66, 24.0 ),
        new CondensateDataSet( parser.parse( "06/MAY/2011" ).getTime(),  25.23,   5.41,  50.74, 24.0 ),
        new CondensateDataSet( parser.parse( "07/MAY/2011" ).getTime(),  25.43,   5.16,  53.70, 24.0 ),
        new CondensateDataSet( parser.parse( "08/MAY/2011" ).getTime(),  25.46,   5.69,  47.36, 24.0 ),
        new CondensateDataSet( parser.parse( "09/MAY/2011" ).getTime(),  25.68,   4.80,  52.66, 24.0 ),
        new CondensateDataSet( parser.parse( "10/MAY/2011" ).getTime(),  25.45,   5.44,  46.35, 24.0 ),
        new CondensateDataSet( parser.parse( "11/MAY/2011" ).getTime(),  25.65,   5.67,  51.40, 24.0 ),
        new CondensateDataSet( parser.parse( "12/MAY/2011" ).getTime(),  25.84,   6.35,  50.08, 24.0 ),
        new CondensateDataSet( parser.parse( "13/MAY/2011" ).getTime(),  25.69,   5.64,  48.57, 24.0 ),
        new CondensateDataSet( parser.parse( "14/MAY/2011" ).getTime(),  25.63,   5.74,  47.89, 24.0 ),
        new CondensateDataSet( parser.parse( "15/MAY/2011" ).getTime(),  25.66,   5.00,  50.55, 24.0 ),
        new CondensateDataSet( parser.parse( "16/MAY/2011" ).getTime(),  25.63,   5.72,  50.33, 24.0 ),
        new CondensateDataSet( parser.parse( "17/MAY/2011" ).getTime(),  25.61,   5.43,  51.24, 24.0 ),
        new CondensateDataSet( parser.parse( "18/MAY/2011" ).getTime(),  25.62,   5.27,  51.46, 24.0 ),
        new CondensateDataSet( parser.parse( "19/MAY/2011" ).getTime(),  25.61,   5.34,  51.28, 24.0 ),
        new CondensateDataSet( parser.parse( "20/MAY/2011" ).getTime(),  25.62,   5.53,  52.24, 24.0 ),
        new CondensateDataSet( parser.parse( "21/MAY/2011" ).getTime(),  25.61,   5.38,  48.47, 24.0 ),
        new CondensateDataSet( parser.parse( "22/MAY/2011" ).getTime(),  25.54,   5.09,  45.86, 24.0 ),
        new CondensateDataSet( parser.parse( "23/MAY/2011" ).getTime(),  25.50,   5.63,  51.20, 24.0 ),
        new CondensateDataSet( parser.parse( "24/MAY/2011" ).getTime(),  25.49,   6.09,  50.33, 24.0 ),
        new CondensateDataSet( parser.parse( "25/MAY/2011" ).getTime(),  25.50,   4.93,  46.85, 24.0 ),
        new CondensateDataSet( parser.parse( "26/MAY/2011" ).getTime(),  25.49,   5.59,  52.84, 24.0 ),
        new CondensateDataSet( parser.parse( "27/MAY/2011" ).getTime(),  25.48,   5.56,  49.51, 24.0 ),
        new CondensateDataSet( parser.parse( "28/MAY/2011" ).getTime(),  25.46,   5.49,  47.02, 24.0 ),
        new CondensateDataSet( parser.parse( "29/MAY/2011" ).getTime(),  25.45,   5.05,  48.32, 24.0 ),
        new CondensateDataSet( parser.parse( "30/MAY/2011" ).getTime(),  25.47,   5.37,  48.55, 24.0 ),
        new CondensateDataSet( parser.parse( "31/MAY/2011" ).getTime(),  25.57,   5.91,  48.54, 24.0 ),
        new CondensateDataSet( parser.parse( "01/JUN/2011" ).getTime(),  25.43,   6.15,  49.55, 24.0 ),
        new CondensateDataSet( parser.parse( "02/JUN/2011" ).getTime(),  25.42,   6.14,  49.35, 24.0 ),
        new CondensateDataSet( parser.parse( "03/JUN/2011" ).getTime(),  25.42,   5.78,  50.54, 24.0 ),
        new CondensateDataSet( parser.parse( "04/JUN/2011" ).getTime(),  25.36,   5.39,  50.98, 24.0 ),
        new CondensateDataSet( parser.parse( "05/JUN/2011" ).getTime(),  25.36,   5.00,  50.34, 24.0 ),
        new CondensateDataSet( parser.parse( "06/JUN/2011" ).getTime(),  25.33,   5.73,  46.58, 24.0 ),
        new CondensateDataSet( parser.parse( "07/JUN/2011" ).getTime(),  25.34,   5.07,  50.41, 24.0 ),
        new CondensateDataSet( parser.parse( "08/JUN/2011" ).getTime(),  25.38,   5.68,  50.71, 24.0 ),
        new CondensateDataSet( parser.parse( "09/JUN/2011" ).getTime(),  25.34,   5.54,  50.31, 24.0 ),
        new CondensateDataSet( parser.parse( "10/JUN/2011" ).getTime(),  25.37,   5.55,  45.27, 24.0 ),
        new CondensateDataSet( parser.parse( "11/JUN/2011" ).getTime(),  25.30,   5.42,  46.16, 24.0 ),
        new CondensateDataSet( parser.parse( "12/JUN/2011" ).getTime(),  25.30,   6.13,  50.62, 24.0 ),
        new CondensateDataSet( parser.parse( "13/JUN/2011" ).getTime(),  25.32,   5.33,  49.92, 24.0 )
};


static {
    repopulate();
}

/**
 * to ensure that unit tests can repopulate the data anew, in case a
 * previous unit test might have altered it!!
 */
public static void repopulate()
{
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

    // four years of gas condensate data..
    by11DataSet = populateFromRawData( new GasWell( "BY11" ), by11RawData );

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

public static GasWellDataSet getBY11DataSet()
{
    return by11DataSet;
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
//            until = new Date( from.getTime() + ( (long)span * 1000L ) );
        } else {
            until = new Date( from.getTime () + (long)(rawData[ i ].durationHours * 3600000L ) ) ;
        }
        entry.setDateRange( new DateRange( from, until, 1000L ) );
        if ( rawData[ i ] instanceof OilDataSet )
        {
            entry.setMeasurement( WellMeasurementType.OIL_FLOW, ((OilDataSet)rawData[ i ]).oilFlowRate );
        }
        if ( rawData[ i ] instanceof CondensateDataSet )
        {
            entry.setMeasurement( WellMeasurementType.CONDENSATE_FLOW, ((CondensateDataSet)rawData[ i ]).condensateFlowRate );
        }
        entry.setMeasurement( WellMeasurementType.GAS_FLOW, rawData[ i ].gasFlowRate );
        entry.setMeasurement( WellMeasurementType.WATER_FLOW, rawData[ i ].waterFlowRate );
        entry.setComment( "" );
        result.addDataEntry( entry );
    }
    return result;
}


protected static class DataSet
{
    Date from;
    double gasFlowRate;
    double waterFlowRate;
    double durationHours;

    protected DataSet( Date from, double gasFlowRate, double waterFlowRate, double durationHours )
    {
        this.from = from;
        this.gasFlowRate = gasFlowRate;
        this.waterFlowRate = waterFlowRate;
        this.durationHours = durationHours;
    }
}

protected static class OilDataSet extends DataSet
{
    double oilFlowRate;

    protected OilDataSet( Date from, double oilFlowRate, double gasFlowRate, double waterFlowRate, double durationHours )
    {
        super( from, gasFlowRate, waterFlowRate, durationHours );
        this.oilFlowRate = oilFlowRate;
    }
}

protected static class CondensateDataSet extends DataSet
{
    double condensateFlowRate;

    protected CondensateDataSet( Date from, double condensateFlowRate, double gasFlowRate, double waterFlowRate, double durationHours )
    {
        super( from, gasFlowRate, waterFlowRate, durationHours );
        this.condensateFlowRate = condensateFlowRate;
    }
}

}
