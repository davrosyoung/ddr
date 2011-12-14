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
static DateParser dateParser = new AussieDateParser();
static protected GasWellDataSet nicksDataSet = null;
static protected GasWellDataSet saa2FragmentDataSet = null;
static protected GasWellDataSet dummyDataSet = null;
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
        new DataSet( dateParser.parse( "15/AUG/2009 23:33" ).getTime(), 0564.0, 0810.0, 0007.0, 6975.8500 ),
        new DataSet( dateParser.parse( "27/MAI/2010 23:33" ).getTime(), 0000.0, 0000.0, 0000.0, 0135.8500 ),
        new DataSet( dateParser.parse( "02/JUN/2010 15:24" ).getTime(), 0513.0, 0620.0, 0006.0, 2561.5500 ),
        new DataSet( dateParser.parse( "17/SEP/2010 08:57" ).getTime(), 0636.0, 0970.0, 0008.5, 0096.0000 ),
        new DataSet( dateParser.parse( "21/SEP/2010 08:57" ).getTime(), 0497.0, 0600.0, 0009.0, 0135.4667 ),
        new DataSet( dateParser.parse( "27/SEP/2010 00:25" ).getTime(), 0000.0, 0000.0, 0000.0, 8760.770 )
};

static final private DataSet[] saa2FragmentRawData = {
        new DataSet( dateParser.parse( "30/JUL/09" ).getTime(), 0287.0, 0.19, 0952.0, 24.0 ),
        new DataSet( dateParser.parse( "31/JUL/09" ).getTime(), 0277.0, 0.19, 0924.0, 24.0 ),
        new DataSet( dateParser.parse( "01/AUG/09" ).getTime(), 0284.0, 0.19, 0927.0, 24.0 ),
        new DataSet( dateParser.parse( "02/AUG/09" ).getTime(), 0290.0, 0.19, 0936.0, 24.0 ),
        new DataSet( dateParser.parse( "03/AUG/09" ).getTime(), 0248.0, 0.17, 0783.0, 24.0 ),
        new DataSet( dateParser.parse( "04/AUG/09" ).getTime(), 0249.0, 0.18, 0840.0, 24.0 ),
        new DataSet( dateParser.parse( "05/AUG/09" ).getTime(), 0223.0, 0.15, 0764.0, 24.0 ),
        new DataSet( dateParser.parse( "06/AUG/09" ).getTime(), 0224.0, 0.15, 0754.0, 24.0 ),
        new DataSet( dateParser.parse( "07/AUG/09" ).getTime(), 0177.0, 0.16, 0741.0, 24.0 ),
        new DataSet( dateParser.parse( "08/AUG/09" ).getTime(), 0217.0, 0.16, 0744.0, 24.0 ),
        new DataSet( dateParser.parse( "09/AUG/09" ).getTime(), 0203.0, 0.15, 0699.0, 24.0 ),
        new DataSet( dateParser.parse( "10/AUG/09" ).getTime(), 0201.0, 0.16, 0692.0, 24.0 ),
        new DataSet( dateParser.parse( "11/AUG/09" ).getTime(), 0219.0, 0.15, 0633.0, 24.0 ),
        new DataSet( dateParser.parse( "12/AUG/09" ).getTime(), 0000.0, 0.00, 0000.0, 24.0 ),
        new DataSet( dateParser.parse( "13/AUG/09" ).getTime(), 0000.0, 0.00, 0000.0, 24.0 ),
        new DataSet( dateParser.parse( "14/AUG/09" ).getTime(), 0000.0, 0.00, 0000.0, 24.0 ),
        new DataSet( dateParser.parse( "15/AUG/09" ).getTime(), 0041.0, 0.00, 0151.0, 24.0 ),
        new DataSet( dateParser.parse( "16/AUG/09" ).getTime(), 0192.0, 0.15, 0673.0, 24.0 ),
        new DataSet( dateParser.parse( "17/AUG/09" ).getTime(), 0182.0, 0.14, 0633.0, 24.0 ),
        new DataSet( dateParser.parse( "18/AUG/09" ).getTime(), 0038.0, 0.03, 0133.0, 24.0 ),
        new DataSet( dateParser.parse( "19/AUG/09" ).getTime(), 0000.0, 0.00, 0000.0, 24.0 ),
        new DataSet( dateParser.parse( "20/AUG/09" ).getTime(), 0012.0, 0.01, 0041.0, 24.0 ),
        new DataSet( dateParser.parse( "21/AUG/09" ).getTime(), 0000.0, 0.00, 0000.0, 24.0 ),
        new DataSet( dateParser.parse( "22/AUG/09" ).getTime(), 0000.0, 0.00, 0000.0, 24.0 ),
        new DataSet( dateParser.parse( "23/AUG/09" ).getTime(), 0105.0, 0.09, 0384.0, 24.0 ),
        new DataSet( dateParser.parse( "24/AUG/09" ).getTime(), 0690.0, 0.34, 2087.0, 24.0 ),
        new DataSet( dateParser.parse( "25/AUG/09" ).getTime(), 0540.0, 0.31, 2043.0, 24.0 ),
        new DataSet( dateParser.parse( "26/AUG/09" ).getTime(), 0666.0, 0.32, 2018.0, 24.0 ),
        new DataSet( dateParser.parse( "27/AUG/09" ).getTime(), 0603.0, 0.30, 1878.0, 24.0 ),
        new DataSet( dateParser.parse( "28/AUG/09" ).getTime(), 0621.0, 0.31, 1844.0, 24.0 ),
        new DataSet( dateParser.parse( "29/AUG/09" ).getTime(), 0475.0, 0.26, 1637.0, 24.0 ),
        new DataSet( dateParser.parse( "30/AUG/09" ).getTime(), 0401.0, 0.23, 1439.0, 24.0 ),
        new DataSet( dateParser.parse( "31/AUG/09" ).getTime(), 0414.0, 0.23, 1429.0, 24.0 ),
        new DataSet( dateParser.parse( "01/SEP/09" ).getTime(), 0367.0, 0.21, 1256.0, 24.0 ),
        new DataSet( dateParser.parse( "02/SEP/09" ).getTime(), 0363.0, 0.21, 1242.0, 24.0 ),
        new DataSet( dateParser.parse( "03/SEP/09" ).getTime(), 0321.0, 0.21, 1089.0, 24.0 ),
        new DataSet( dateParser.parse( "04/SEP/09" ).getTime(), 0301.0, 0.20, 0993.0, 24.0 ),
        new DataSet( dateParser.parse( "05/SEP/09" ).getTime(), 0530.0, 0.29, 1982.0, 24.0 ),
        new DataSet( dateParser.parse( "06/SEP/09" ).getTime(), 0544.0, 0.30, 1962.0, 24.0 ),
        new DataSet( dateParser.parse( "07/SEP/09" ).getTime(), 0591.0, 0.30, 1873.0, 24.0 ),
        new DataSet( dateParser.parse( "08/SEP/09" ).getTime(), 0583.0, 0.30, 1859.0, 24.0 ),
        new DataSet( dateParser.parse( "09/SEP/09" ).getTime(), 0559.0, 0.30, 1874.0, 24.0 ),
        new DataSet( dateParser.parse( "10/SEP/09" ).getTime(), 0484.0, 0.26, 1627.0, 24.0 ),
        new DataSet( dateParser.parse( "11/SEP/09" ).getTime(), 0053.0, 0.03, 0194.0, 24.0 ),
        new DataSet( dateParser.parse( "12/SEP/09" ).getTime(), 0105.0, 0.06, 0343.0, 24.0 ),
        new DataSet( dateParser.parse( "13/SEP/09" ).getTime(), 0080.0, 0.06, 1400.0, 24.0 ),
        new DataSet( dateParser.parse( "14/SEP/09" ).getTime(), 0009.0, 0.01, 0153.0, 24.0 ),
        new DataSet( dateParser.parse( "15/SEP/09" ).getTime(), 0000.0, 0.00, 0000.0, 24.0 ),
        new DataSet( dateParser.parse( "16/SEP/09" ).getTime(), 0250.0, 0.12, 0937.0, 24.0 ),
        new DataSet( dateParser.parse( "17/SEP/09" ).getTime(), 0000.0, 0.00, 0000.0, 24.0 ),
        new DataSet( dateParser.parse( "18/SEP/09" ).getTime(), 0000.0, 0.00, 0000.0, 24.0 ),
        new DataSet( dateParser.parse( "19/SEP/09" ).getTime(), 0529.0, 0.23, 1693.0, 24.0 ),
        new DataSet( dateParser.parse( "20/SEP/09" ).getTime(), 0000.0, 0.00, 0000.0, 24.0 ),
        new DataSet( dateParser.parse( "21/SEP/09" ).getTime(), 4466.0, 1.85, 3958.0, 24.0 ),
        new DataSet( dateParser.parse( "22/SEP/09" ).getTime(), 5730.0, 2.26, 5157.0, 24.0 ),
        new DataSet( dateParser.parse( "23/SEP/09" ).getTime(), 5981.0, 2.33, 5160.0, 24.0 ),
        new DataSet( dateParser.parse( "24/SEP/09" ).getTime(), 6289.0, 2.16, 5225.0, 24.0 ),
        new DataSet( dateParser.parse( "25/SEP/09" ).getTime(), 5541.0, 2.08, 5158.0, 24.0 ),
        new DataSet( dateParser.parse( "26/SEP/09" ).getTime(), 5178.0, 2.04, 5159.0, 24.0 ),
        new DataSet( dateParser.parse( "27/SEP/09" ).getTime(), 5812.0, 2.04, 5107.0, 24.0 ),
        new DataSet( dateParser.parse( "28/SEP/09" ).getTime(), 5912.0, 2.06, 5181.0, 24.0 ),
        new DataSet( dateParser.parse( "29/SEP/09" ).getTime(), 5489.0, 2.05, 5112.0, 24.0 ),
        new DataSet( dateParser.parse( "30/SEP/09" ).getTime(), 5609.0, 2.05, 5094.0, 24.0 ),
        new DataSet( dateParser.parse( "01/OKT/09" ).getTime(), 5290.0, 2.03, 5216.0, 24.0 ),
        new DataSet( dateParser.parse( "02/OKT/09" ).getTime(), 5348.0, 1.91, 5594.0, 24.0 ),
        new DataSet( dateParser.parse( "03/OKT/09" ).getTime(), 5167.0, 1.90, 5570.0, 24.0 ),
        new DataSet( dateParser.parse( "04/OKT/09" ).getTime(), 5016.0, 1.89, 5537.0, 24.0 ),
        new DataSet( dateParser.parse( "05/OKT/09" ).getTime(), 5040.0, 1.90, 5532.0, 24.0 ),
        new DataSet( dateParser.parse( "06/OKT/09" ).getTime(), 4840.0, 1.83, 5595.0, 24.0 ),
        new DataSet( dateParser.parse( "07/OKT/09" ).getTime(), 4968.0, 1.75, 5550.0, 24.0 ),
        new DataSet( dateParser.parse( "08/OKT/09" ).getTime(), 4870.0, 1.51, 5573.0, 24.0 ),
        new DataSet( dateParser.parse( "09/OKT/09" ).getTime(), 4424.0, 1.61, 5674.0, 24.0 ),
        new DataSet( dateParser.parse( "10/OKT/09" ).getTime(), 4601.0, 1.63, 5652.0, 24.0 ),
        new DataSet( dateParser.parse( "11/OKT/09" ).getTime(), 4475.0, 1.60, 5622.0, 24.0 ),
        new DataSet( dateParser.parse( "12/OKT/09" ).getTime(), 4624.0, 1.59, 5553.0, 24.0 ),
        new DataSet( dateParser.parse( "13/OKT/09" ).getTime(), 4518.0, 1.57, 5574.0, 24.0 ),
        new DataSet( dateParser.parse( "14/OKT/09" ).getTime(), 4592.0, 1.71, 5629.0, 24.0 ),
        new DataSet( dateParser.parse( "15/OKT/09" ).getTime(), 4246.0, 1.57, 5645.0, 24.0 ),
        new DataSet( dateParser.parse( "16/OKT/09" ).getTime(), 4332.0, 1.57, 5450.0, 24.0 ),
        new DataSet( dateParser.parse( "17/OKT/09" ).getTime(), 4266.0, 1.55, 5472.0, 24.0 ),
        new DataSet( dateParser.parse( "18/OKT/09" ).getTime(), 4229.0, 1.55, 5461.0, 24.0 ),
        new DataSet( dateParser.parse( "19/OKT/09" ).getTime(), 4212.0, 1.48, 5525.0, 24.0 ),
        new DataSet( dateParser.parse( "20/OKT/09" ).getTime(), 4101.0, 1.45, 5333.0, 24.0 ),
        new DataSet( dateParser.parse( "21/OKT/09" ).getTime(), 4108.0, 1.45, 5328.0, 24.0 ),
        new DataSet( dateParser.parse( "22/OKT/09" ).getTime(), 4080.0, 1.41, 5473.0, 24.0 ),
        new DataSet( dateParser.parse( "23/OKT/09" ).getTime(), 3917.0, 1.39, 5435.0, 24.0 ),
        new DataSet( dateParser.parse( "24/OKT/09" ).getTime(), 3819.0, 1.38, 5422.0, 24.0 ),
        new DataSet( dateParser.parse( "25/OKT/09" ).getTime(), 3774.0, 1.36, 5313.0, 24.0 ),
        new DataSet( dateParser.parse( "26/OKT/09" ).getTime(), 3588.0, 1.22, 5130.0, 24.0 ),
        new DataSet( dateParser.parse( "27/OKT/09" ).getTime(), 3774.0, 1.36, 5272.0, 24.0 ),
        new DataSet( dateParser.parse( "28/OKT/09" ).getTime(), 3559.0, 1.37, 5269.0, 24.0 ),
        new DataSet( dateParser.parse( "29/OKT/09" ).getTime(), 3601.0, 1.34, 5266.0, 24.0 ),
        new DataSet( dateParser.parse( "30/OKT/09" ).getTime(), 3477.0, 1.29, 5240.0, 24.0 ),
        new DataSet( dateParser.parse( "31/OKT/09" ).getTime(), 3638.0, 1.28, 5138.0, 24.0 ),
        new DataSet( dateParser.parse( "01/NOV/09" ).getTime(), 3458.0, 1.26, 5160.0, 24.0 ),
        new DataSet( dateParser.parse( "02/NOV/09" ).getTime(), 3417.0, 1.26, 5168.0, 24.0 ),
        new DataSet( dateParser.parse( "03/NOV/09" ).getTime(), 3447.0, 1.09, 5136.0, 24.0 ),
        new DataSet( dateParser.parse( "04/NOV/09" ).getTime(), 3302.0, 1.25, 5087.0, 24.0 ),
        new DataSet( dateParser.parse( "05/NOV/09" ).getTime(), 3385.0, 1.20, 5033.0, 24.0 ),
        new DataSet( dateParser.parse( "06/NOV/09" ).getTime(), 3177.0, 1.21, 5033.0, 24.0 ),
        new DataSet( dateParser.parse( "07/NOV/09" ).getTime(), 3183.0, 1.19, 5036.0, 24.0 ),
        new DataSet( dateParser.parse( "08/NOV/09" ).getTime(), 3111.0, 1.19, 5067.0, 24.0 ),
        new DataSet( dateParser.parse( "09/NOV/09" ).getTime(), 3123.0, 1.18, 5028.0, 24.0 ),
        new DataSet( dateParser.parse( "10/NOV/09" ).getTime(), 3106.0, 1.14, 4887.0, 24.0 ),
        new DataSet( dateParser.parse( "11/NOV/09" ).getTime(), 3026.0, 1.14, 4947.0, 24.0 ),
        new DataSet( dateParser.parse( "12/NOV/09" ).getTime(), 2931.0, 1.11, 4874.0, 24.0 ),
        new DataSet( dateParser.parse( "13/NOV/09" ).getTime(), 2906.0, 1.09, 4854.0, 24.0 ),
        new DataSet( dateParser.parse( "14/NOV/09" ).getTime(), 2788.0, 1.10, 4864.0, 24.0 ),
        new DataSet( dateParser.parse( "15/NOV/09" ).getTime(), 2807.0, 1.10, 4867.0, 24.0 ),
        new DataSet( dateParser.parse( "16/NOV/09" ).getTime(), 2984.0, 1.17, 5100.0, 24.0 ),
        new DataSet( dateParser.parse( "17/NOV/09" ).getTime(), 2965.0, 1.21, 5256.0, 24.0 ),
        new DataSet( dateParser.parse( "18/NOV/09" ).getTime(), 3003.0, 1.23, 5312.0, 24.0 ),
        new DataSet( dateParser.parse( "19/NOV/09" ).getTime(), 3056.0, 1.22, 5345.0, 24.0 ),
        new DataSet( dateParser.parse( "20/NOV/09" ).getTime(), 3100.0, 1.20, 5304.0, 24.0 ),
        new DataSet( dateParser.parse( "21/NOV/09" ).getTime(), 3102.0, 1.21, 5332.0, 24.0 ),
        new DataSet( dateParser.parse( "22/NOV/09" ).getTime(), 2441.0, 0.91, 5724.0, 24.0 ),
        new DataSet( dateParser.parse( "23/NOV/09" ).getTime(), 2310.0, 0.90, 5721.0, 24.0 ),
        new DataSet( dateParser.parse( "24/NOV/09" ).getTime(), 2237.0, 0.89, 5644.0, 24.0 ),
        new DataSet( dateParser.parse( "25/NOV/09" ).getTime(), 2367.0, 0.90, 5474.0, 24.0 )

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
        new DataSet( dateParser.parse( "17/06/2011 06:00" ).getTime(), 1207.0, 0.21, 4325.0, 01.0 ),
        new DataSet( dateParser.parse( "17/06/2011 07:00" ).getTime(), 1209.0, 0.20, 4326.0, 01.0 ),
        new DataSet( dateParser.parse( "17/06/2011 08:00" ).getTime(), 1211.0, 0.20, 4320.0, 01.0 ),
        new DataSet( dateParser.parse( "17/06/2011 09:00" ).getTime(), 1210.0, 0.19, 4317.0, 01.0 ),
        new DataSet( dateParser.parse( "17/06/2011 10:00" ).getTime(), 1210.0, 0.18, 4318.0, 01.0 ),
        new DataSet( dateParser.parse( "17/06/2011 11:00" ).getTime(), 1210.0, 0.19, 4319.0, 01.0 ),
        new DataSet( dateParser.parse( "17/06/2011 12:00" ).getTime(), 1210.0, 0.20, 4320.0, 01.0 ),
        new DataSet( dateParser.parse( "17/06/2011 13:00" ).getTime(), 1210.1, 0.21, 4325.0, 01.0 )
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

protected static GasWellDataSet populateFromRawData( GasWell well, DataSet[] rawData )
{
    GasWellDataSet result;
    GasWellDataEntry entry;
    Date from;
    Date until;

    
    result = new GasWellDataSet( well );

    // populate the data set representing nick's reduced data flow data set.
    // ---------------------------------------------------------------------
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
                    logger.error( "i=" + i + " ... duration is short by " + delta + "seconds." );
                }
                if ( span < ( rawData[ i ].durationHours * 3600 ) )
                {
                    logger.error( "i=" + i + " ... duration is too long by " + delta + "seconds." );
                }
            }
            until = new Date( from.getTime() + ( (long)span * 3600000L ) );
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
